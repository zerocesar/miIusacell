package mx.com.iusacell.services.miiusacell.arquitectura.autorizador;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang3.StringUtils;

import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.autorizador.AddressVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.BankResponseVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.CardVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.ClienteTarjetaVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.KeyedTransactionResponseVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.KeyedTransactionVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.Transaction;
import mx.com.iusacell.services.miusacell.call.CallServiceAutorizador;
import mx.com.iusacell.services.miusacell.call.CallServiceTarjeta;
import mx.com.iusacell.services.miusacell.util.AlgoritmoAes;
import mx.com.iusacell.services.miusacell.util.ParseXMLServices;
import mx.com.iusacell.tesoreria.security.TesoIusaCipher;


public class Autorizador {

	private static final String RESPONSE_CODE_EXITO = "00";	
	private static final String RESPONSE_CODE_EXITO_AMEX = "000";
	private static final String PAYMENT = "P";
	private static final String REVERSE = "R";
	private static final int MODO_DIGITADA = 1;
	private static final int VERSION_BANORTE = 2;
	private static final int VERSION_BAZ = 1;
	private static final int AUTOR_SYSTEM_ID = 1;
	private static final String CANCELACION = "CANCELACION";
	private static final String KeyValueIusa = "GrUPoSaLInaSsACv";
	private static final String KeyValueAutoriz = "N9RkwOyuL83EsvAx";
	private static final int APPTA_SYSTEMID = 17;
	private static final int APPPS_SYSTEMID = 18;
	private static final int WEBTA_SYSTEMID = 19;
	private static final int WEBPS_SYSTEMID = 20;
	private static final int MI_UNEFON_WEB  = 23;
	private static final int MI_UNEFON_APP  = 24;
	private static final int ABONOTA = 1;
	private static final int PAGOSERVICIO = 2;	

	public Transaction applyKeyedCharge(
			final String cardNumber, final String cvv, final int expireMonth, final int expireYear,
			final double amount, final int systemId, final int businessId,
			final String regionId, final String storeId, final String concepto, 
			final String fingerprint, final int paymentPeriod, final String dn,
			final String ipAddress, final String mail, final int tipoCargo,
			final AddressVO address, final boolean isFlowATT
	) throws ServiceException
	{		
		Logger.write("     [<Inicio>]    A U T O R I Z A D O R               : -applyKeyedCharge-");
		final AlgoritmoAes cipherIusa =new AlgoritmoAes();
		TesoIusaCipher cipherAutorizador = null;
		try{
			cipherAutorizador = TesoIusaCipher.getInstance(KeyValueAutoriz);
		}
		catch (Exception e) {
			throw new ServiceException("Ocurrio un error al crear cipherAutorizador");
		}
		String cdgSeguridad;
		String cipherCard;
		try{
			cdgSeguridad = cipherIusa.decrypt(cvv, KeyValueIusa.getBytes());
		}catch (Exception e) {
			throw new ServiceException("No se pudo cifrar el código de seguridad");
		}

		try{
			cipherCard = cipherIusa.decrypt(cardNumber, KeyValueIusa.getBytes());			
		}catch (Exception e) {
			throw new ServiceException("No se pudo cifrar la tajeta");
		}

		final OracleProcedures oracle = new OracleProcedures();
		String bean = this.obtenerBeanTarjeta(cipherCard);
		String issuer = oracle.getIssuer(bean);
		if (issuer == null ) throw new ServiceException("No se puede identificar el tipo de tarjeta");
		int chargeId=0;
		try{
			chargeId = oracle.getChargeId();
		}catch (Exception e) {
			throw new ServiceException("No se pudo obtener el chargeId");
		}

		int creditDebit = oracle.getCardType(bean);	
		try{
			cipherCard = cipherAutorizador.encryptToHex(cipherCard);
		}
		catch (Exception e) {
			throw new ServiceException("No se pudo cifrar la tarjeta - Autorizador");
		}

		String month = String.valueOf(expireMonth);
		month = (month.matches("[0-9]{2,}")) ? month : "0" + month;

		String year = String.valueOf(expireYear);
		year = (year.matches("[0-9]{2,}")) ? year : "0" + year;				
		
		String firstName = ""; 
		String lastName = ""; 		
		String customerCountry = "";
		String customerCounty = "";
		String customerState = ""; 
		String customerNeighborhood = "";
		String customerStreet = ""; 
		String customerStreetNumber = "";
		String customerPostalCode = "";
		
		if(address != null){
			if(StringUtils.isNoneEmpty(address.getNombres())){
				firstName = address.getNombres();
			}
			if(StringUtils.isNoneEmpty(address.getApellidos())){
				lastName = address.getApellidos();
			}
			if(StringUtils.isNoneEmpty(address.getMunicipio())){
				customerCounty = address.getMunicipio();
			}
			if(StringUtils.isNoneEmpty(address.getEstado())){
				customerState = address.getEstado();
			}
			if(StringUtils.isNoneEmpty(address.getColonia())){
				customerNeighborhood = address.getColonia();
			}
			if(StringUtils.isNoneEmpty(address.getCalle())){
				customerStreet = address.getCalle();
			}
			if(StringUtils.isNoneEmpty(address.getCodigoPostal())){
				customerPostalCode = address.getCodigoPostal();
			}
		}else{
			try{
				final CallServiceTarjeta serviceRepoCards = new CallServiceTarjeta();
				final ClienteTarjetaVO responseRepoCards = serviceRepoCards.getTarjetasPorDn(dn);
				if(responseRepoCards != null && responseRepoCards.getTarjetas() != null && !responseRepoCards.getTarjetas().getItemM().isEmpty()){
					if(responseRepoCards.getTarjetas().getItemM().get(0).getFirstName() != null){
						firstName = responseRepoCards.getTarjetas().getItemM().get(0).getFirstName();
					}
					if(responseRepoCards.getTarjetas().getItemM().get(0).getLastName() != null){
						lastName = responseRepoCards.getTarjetas().getItemM().get(0).getLastName(); 
					}
					if(responseRepoCards.getTarjetas().getItemM().get(0).getMunicipality() != null){
						customerCounty = responseRepoCards.getTarjetas().getItemM().get(0).getMunicipality();
					}
					if(responseRepoCards.getTarjetas().getItemM().get(0).getState() != null){
						customerState = getValueState(responseRepoCards.getTarjetas().getItemM().get(0).getState());
					}
					if(responseRepoCards.getTarjetas().getItemM().get(0).getColony() != null){
						customerNeighborhood = responseRepoCards.getTarjetas().getItemM().get(0).getColony();
					}
					if(responseRepoCards.getTarjetas().getItemM().get(0).getAddress() != null){
						customerStreet = responseRepoCards.getTarjetas().getItemM().get(0).getAddress();
					}
					if(responseRepoCards.getTarjetas().getItemM().get(0).getZip() != null){
						customerPostalCode = responseRepoCards.getTarjetas().getItemM().get(0).getZip();
					}
				}
			}catch (Exception e) {
				Logger.write("Error al consultar repositorio de tarjetas " + e.getLocalizedMessage());
			}	
		}
		customerCountry = "MX";		
		
		if(customerStreet.length() >= 30){
			customerStreet = customerStreet.substring(0, 29);
		}
		if(customerNeighborhood.length() >= 30){
			customerNeighborhood = customerNeighborhood.substring(0, 29);
		}
		if(customerCounty.length() >= 30){
			customerCounty = customerCounty.substring(0, 29);
		}
		
		if(customerCounty.equals("")){ customerCounty = null;}; 
		if(customerState.equals("") || customerState.length() > 2){ customerState = null;}; 
		if(customerNeighborhood.equals("")){ customerNeighborhood = null;}; 
		if(customerStreet.equals("")){ customerStreet = null;}; 
		if(customerStreetNumber.equals("")){ customerStreetNumber = null;}; 
		if(customerPostalCode.equals("")){ customerPostalCode = null;}; 
		if(firstName.equals("")){firstName = null;} 
		if(lastName.equals("")){lastName = null;} 		

		KeyedTransactionVO keyedTransaction= new KeyedTransactionVO();
		keyedTransaction.setBankCardNumber(cipherCard);
		keyedTransaction.setCardCvv2(cdgSeguridad);
		keyedTransaction.setCardExpirationMonth(month);
		keyedTransaction.setCardExpirationYear(year);		
		keyedTransaction.setCardType(issuer);
		keyedTransaction.setChargeAmount(amount);
		keyedTransaction.setChargeBusinessID(businessId);
		keyedTransaction.setChargeChargeID(chargeId);
		keyedTransaction.setChargePaymentPeriod(paymentPeriod);
		keyedTransaction.setChargeRegionID(regionId);
		keyedTransaction.setChargeStoreID(storeId);		
		keyedTransaction.setCustomerBankPhoneNumber(dn);
		keyedTransaction.setCustomerIpAddress(ipAddress);
		keyedTransaction.setDeviceFingerprintId(fingerprint);
		keyedTransaction.setProductName(concepto);
		keyedTransaction.setCustomerFirstName(firstName);
		keyedTransaction.setCustomerLastName(lastName);
		keyedTransaction.setCustomerEmail(mail);

		keyedTransaction.setCustomerCountry(customerCountry);
		keyedTransaction.setCustomerCounty(customerCounty);
		keyedTransaction.setCustomerState(customerState);
		keyedTransaction.setCustomerNeighborhood(customerNeighborhood);
		keyedTransaction.setCustomerStreet(customerStreet);
		keyedTransaction.setCustomerPostalCode(customerPostalCode);
		keyedTransaction.setCustomerStreetNumber(customerStreetNumber);
		
		String activarIdsBanorte = "0";
		try{						
			activarIdsBanorte = oracle.getValorParametro(210);
		}catch (Exception e) {					
			activarIdsBanorte = "0";
		}
		if("1".equals(activarIdsBanorte) && isFlowATT){	
			if(systemId == 3){//Sistema Web MiATT
				if(tipoCargo == ABONOTA){
					keyedTransaction.setChargeSystemID(WEBTA_SYSTEMID);
				}else if(tipoCargo == PAGOSERVICIO){
					keyedTransaction.setChargeSystemID(WEBPS_SYSTEMID);
				}
			}else if(systemId == 4){//Sistema App Unefon
				keyedTransaction.setChargeSystemID(MI_UNEFON_APP);	
			}else if(systemId == 5){//Sistema Web Unefon
				keyedTransaction.setChargeSystemID(MI_UNEFON_WEB);	
			}
			else{//Sistema Movil MiATT
				if(tipoCargo == ABONOTA){
					keyedTransaction.setChargeSystemID(APPTA_SYSTEMID);
				}else if(tipoCargo == PAGOSERVICIO){
					keyedTransaction.setChargeSystemID(APPPS_SYSTEMID);
				}
			}
		}else{	
			//Sistema Iusacell
			keyedTransaction.setChargeSystemID(AUTOR_SYSTEM_ID);			
		}

		Transaction transaction = buildTransactionCharge(keyedTransaction);
		transaction.setIdRegistro(chargeId);
		transaction.setCreditDebit(creditDebit);
		//transaction.setSystemId(systemId);

		KeyedTransactionResponseVO response = null;				
		try {
			Logger.write("     [WSS] :: in                      :: KeyedTransactionVO	  :: " + keyedTransaction.toString());
			final CallServiceAutorizador servicioAutorizador = new CallServiceAutorizador();
			final String responseWS = servicioAutorizador.applyKeyedCharge(keyedTransaction); 
			response = ParseXMLServices.parseApplyKeyedCharge(responseWS);

			Logger.write("     [WSS] :: out                     :: Affiliation             :: " + response.getAffiliation());
			Logger.write("     [WSS] :: out                     :: AffiliationName         :: " + response.getAffiliationName());
			Logger.write("     [WSS] :: out                     :: AuthorizationCode       :: " + response.getAuthorizationCode());
			Logger.write("     [WSS] :: out                     :: BankResponseCode        :: " + response.getBankResponseCode());
			Logger.write("     [WSS] :: out                     :: DescriptionResponse     :: " + response.getDescriptionResponse());
			Logger.write("     [WSS] :: out                     :: TransactionControlNumber:: " + response.getTransactionControlNumber());


			transaction.setAutorizationCode(response.getAuthorizationCode());
			transaction.setDescResp(response.getDescriptionResponse());
			transaction.setBankCode(response.getBankResponseCode());

		} catch (ServiceException e) {
			transaction.setDescResp(e.getMessage());
			Logger.write(e.getMessage());			
			throw new ServiceException("No se pudo realizar la transaccion :: " + e.getMessage());
		} finally {
			Logger.write("     [<Fin>]    A U T O R I Z A D O R               : -applyKeyedCharge-");
		}		

		try
		{
			if (response.getBankResponseCode().equals(RESPONSE_CODE_EXITO)
					|| response.getBankResponseCode().equals(RESPONSE_CODE_EXITO_AMEX)) {

				return transaction;

			} else {
				Logger.write("Error al Autorizar tarjeta applyKeyedCharge, "
						+ response.getDescriptionResponse());
				throw new ServiceException(response.getDescriptionResponse());
			}
		} catch (Exception e) {			
			Logger.write("Error al ejecutar el autorizador : applyKeyedCharge : " + e.getLocalizedMessage());
			throw new ServiceException("Error al Autorizar tarjeta :: applyKeyedCharge : " + e.getLocalizedMessage());
		}
	}

	public Transaction executeGeneralCharge( final String cardNumber, final String cvv, final int expireMonth, final int expireYear,
			final double amount, final int mesesSinIntereses, final int businessId, final String clientIp, final int usoTarjeta, 
			final int systemId, final String concepto,
			final String regionId, final String storeId) throws ServiceException {

		Logger.write("     [<Inicio>]    A U T O R I Z A D O R               : -executeGeneralCharge-");
		final AlgoritmoAes cipherIusa =new AlgoritmoAes();
		TesoIusaCipher cipherAutorizador = null;
		try{
			cipherAutorizador = TesoIusaCipher.getInstance(KeyValueAutoriz);
		}
		catch (Exception e) {
			throw new ServiceException("Ocurrio un error al crear cipherAutorizador");
		}
		String cdgSeguridad;
		String cipherCard;
		try{
			cdgSeguridad = cipherIusa.decrypt(cvv, KeyValueIusa.getBytes());			
		}catch (Exception e) {
			throw new ServiceException("No se pudo cifrar el código de seguridad");
		}

		try{
			cipherCard = cipherIusa.decrypt(cardNumber, KeyValueIusa.getBytes());			
		}catch (Exception e) {
			throw new ServiceException("No se pudo cifrar la tajeta");
		}	

		OracleProcedures oracle = new OracleProcedures();
		String bean = this.obtenerBeanTarjeta(cipherCard);
		String issuer = oracle.getIssuer(bean);
		if (issuer == null ) throw new ServiceException("No se puede identificar el tipo de tarjeta");
		int chargeId=0;
		try{
			chargeId = oracle.getChargeId();			
		}catch (Exception e) {
			throw new ServiceException("No se pudo obtener el chargeId");
		}

		int cardType = oracle.getCardType(bean);
		try{
			cipherCard = cipherAutorizador.encryptToHex(cipherCard);
		}
		catch (Exception e) {
			throw new ServiceException("No se puedo cifrar la tarjeta - Autorizador");
		}

		String month = String.valueOf(expireMonth);
		month = (month.matches("[0-9]{2,}")) ? month : "0" + month;

		String year = String.valueOf(expireYear);
		year = (year.matches("[0-9]{2,}")) ? year : "0" + year;

		CardVO card = new CardVO();									
		Calendar calendar = Calendar.getInstance();			
		card.setCreationDate(calendar);			
		card.setCvv2(cdgSeguridad);
		card.setExpirationMonth(month);
		card.setExpirationYear(year);
		card.setCardNumber(cipherCard);
		card.setType(cardType);				



		Logger.write("     [WSS] :: (+] CallAutorizador     :: autorizador ");			
		Logger.write("     [WSS] :: inn                     :: chargeId                :: " + chargeId);
		Logger.write("     [WSS] :: inn                     :: amount                  :: " + amount);
		Logger.write("     [WSS] :: inn                     :: mesesSinIntereses       :: " + mesesSinIntereses);
		Logger.write("     [WSS] :: inn                     :: bussinesId              :: " + businessId);
		Logger.write("     [WSS] :: inn                     :: clientIp                :: " + clientIp);
		Logger.write("     [WSS] :: inn                     :: usoTarjeta              :: " + usoTarjeta);
		Logger.write("     [WSS] :: inn                     :: idSystem                :: " + AUTOR_SYSTEM_ID);
		Logger.write("     [WSS] :: inn                     :: concepto                :: " + concepto);
		Logger.write("     [WSS] :: inn                     :: regionId                :: " + regionId);
		Logger.write("     [WSS] :: inn                     :: storeId                 :: " + storeId);
		Logger.write("     [WSS] :: CardVO                  :: ");		
		Logger.write("     [WSS] :: inn                     :: CardNumber              :: " + card.getCardNumber());
		Logger.write("     [WSS] :: inn                     :: Cvv2                    :: " + card.getCvv2().isEmpty());
		Logger.write("     [WSS] :: inn                     :: ExpirationMonth         :: " + card.getExpirationMonth());
		Logger.write("     [WSS] :: inn                     :: ExpirationYear          :: " + card.getExpirationYear());		
		Logger.write("     [WSS] :: inn                     :: Type                    :: " + card.getType());

		if(card.getCreationDate()!=null)
			Logger.write("     [WSS] :: inn                     :: CreationDate            :: " 
					+ this.gregorianCalendarToString(card.getCreationDate()) 
			);

		Transaction transaction = new Transaction();
		transaction.setChargeId(chargeId);		
		transaction.setSystemId(systemId);
		transaction.setIdRegistro(chargeId);
		transaction.setBusinessId(businessId);
		transaction.setOperation("P");
		transaction.setCardNumber(card.getCardNumber());
		transaction.setExpireMonth(card.getExpirationMonth());
		transaction.setExpireYear(card.getExpirationYear());
		transaction.setCardType(issuer);
		transaction.setCreditDebit(cardType);
		transaction.setAmount(amount);
		transaction.setConcept(concepto);
		transaction.setRegionId(regionId);
		transaction.setStoreId(storeId);
		transaction.setCardReadMode(usoTarjeta);			
		transaction.setTransactionDate(new Date());
		transaction.setVersion(VERSION_BAZ);

		try {

			BankResponseVO response = null;

			final CallServiceAutorizador servicioAutorizador = new CallServiceAutorizador();
			final String responseWS = servicioAutorizador.executeGeneralCharge(chargeId, card, amount, mesesSinIntereses, businessId, clientIp, usoTarjeta, AUTOR_SYSTEM_ID);			

			response = ParseXMLServices.parseExecuteGeneralCharge(responseWS); 

			transaction.setAutorizationCode(response.getAuthorizationCode());
			transaction.setDescResp(response.getDescriptionResponse());
			transaction.setBankCode(response.getBankResponseCode());

			Logger.write("     [WSS] :: out                     :: Affiliation             :: " + response.getAffiliation());
			Logger.write("     [WSS] :: out                     :: AffiliationName         :: " + response.getAffiliationName());
			Logger.write("     [WSS] :: out                     :: AuthorizationCode       :: " + response.getAuthorizationCode());
			Logger.write("     [WSS] :: out                     :: BankResponseCode        :: " + response.getBankResponseCode());
			Logger.write("     [WSS] :: out                     :: DescriptionResponse     :: " + response.getDescriptionResponse());
			Logger.write("     [WSS] :: out                     :: StandardResponseCode    :: " + response.getStandardResponseCode());
			Logger.write("     [WSS] :: out                     :: TransactionControlNumber:: " + response.getTransactionControlNumber());
			if(response.getTransactionDate()!=null)
				Logger.write("     [WSS] :: out                     :: TransactionDate         :: " 
						+ this.gregorianCalendarToString(response.getTransactionDate())
				);

			if (transaction.getBankCode().equals(
					RESPONSE_CODE_EXITO)
					|| transaction.getBankCode().equals(
							RESPONSE_CODE_EXITO_AMEX)) {

				return transaction;

			} else {
				Logger.write("Error al Autorizar tarjeta, "
						+ response.getDescriptionResponse());
				throw new ServiceException(response.getDescriptionResponse());
			}



		} catch (Exception e) {
			transaction.setDescResp(e.getMessage());
			Logger.write("Error al ejecutar el autorizador : executeGeneralCharge : " + e.getLocalizedMessage());
			throw new ServiceException("Error al ejecutar el autorizador :: executeGeneralCharge : " + e.getLocalizedMessage());
		} finally{
			Logger.write("     [<Fin>]    A U T O R I Z A D O R               : -executeGeneralCharge-");
		}


	}

	public Transaction autorizadorReverse(Transaction transaction, int idRegistro, int systemId, String clientIp) throws ServiceException {		
		final CallServiceAutorizador servicioAutorizador = new CallServiceAutorizador();

		transaction.setOperation(REVERSE);
		transaction.setTransactionDate(new Date());

		try{
			if (transaction.getCardReadMode() == MODO_DIGITADA && transaction.getVersion() == VERSION_BANORTE){
				Logger.write("     [<Inicio>]    A U T O R I Z A D O R               : -autorizadorReverse        VERSION_BANORTE-");
				KeyedTransactionVO keyedTransactionVO = buildTransactionReverse(transaction); 

				Logger.write("     [WSS] :: in                     :: KeyedTransactionVO		  :: " + keyedTransactionVO.toString());

				KeyedTransactionResponseVO response = null;
				try {					
					final String responseWS = servicioAutorizador.applyKeyedReverse(keyedTransactionVO);
					response = ParseXMLServices.parseApplyKeyedReverse(responseWS);

					Logger.write("     [WSS] :: out                     :: Affiliation             :: " + response.getAffiliation());
					Logger.write("     [WSS] :: out                     :: AffiliationName         :: " + response.getAffiliationName());
					Logger.write("     [WSS] :: out                     :: AuthorizationCode       :: " + response.getAuthorizationCode());
					Logger.write("     [WSS] :: out                     :: BankResponseCode        :: " + response.getBankResponseCode());
					Logger.write("     [WSS] :: out                     :: DescriptionResponse     :: " + response.getDescriptionResponse());				
					Logger.write("     [WSS] :: out                     :: TransactionControlNumber:: " + response.getTransactionControlNumber());

					transaction.setAutorizationCode(response.getAuthorizationCode());
					transaction.setBankCode(response.getBankResponseCode());
					transaction.setDescResp(response.getDescriptionResponse());

				} catch (ServiceException e) {
					Logger.write(e.getMessage());
					throw new ServiceException("No se pudieron reversar las transacciones :: " + e.getMessage());
				}				


			} else {
				Logger.write("     [<Inicio>]    A U T O R I Z A D O R               : -autorizadorReverse        VERSION_BANAMEX-");
				CardVO card = new CardVO();
				card.setCardNumber(transaction.getCardNumber());
				card.setExpirationMonth(transaction.getExpireMonth());
				card.setExpirationYear(transaction.getExpireYear());
				card.setType(transaction.getCreditDebit());						

				int chargeId =  (int) transaction.getChargeId();
				BankResponseVO response = null;
				try {

					Logger.write("     [WSS] :: in                     :: chargeID		  		      :: " + chargeId);
					Logger.write("     [WSS] :: in                     :: CardVO		      		  :: " + card);
					Logger.write("     [WSS] :: in                     :: amount		      		  :: " + transaction.getAmount());
					Logger.write("     [WSS] :: in                     :: period		      		  :: " + transaction.getPeriod());
					Logger.write("     [WSS] :: in                     :: businessId	      		  :: " + transaction.getBusinessId());
					Logger.write("     [WSS] :: in                     :: cardReadMode      		  :: " + transaction.getCardReadMode());
					Logger.write("     [WSS] :: in                     :: systemId      		      :: " + AUTOR_SYSTEM_ID);

					final String responseWS = servicioAutorizador.executeGeneralReverse(chargeId, card, transaction.getAmount(), 
							transaction.getPeriod(), transaction.getBusinessId(), clientIp, 
							transaction.getCardReadMode(), AUTOR_SYSTEM_ID);

					response = ParseXMLServices.parseExecuteGeneralReverse(responseWS);		

					Logger.write("     [WSS] :: out                     :: Affiliation             :: " + response.getAffiliation());
					Logger.write("     [WSS] :: out                     :: AffiliationName         :: " + response.getAffiliationName());
					Logger.write("     [WSS] :: out                     :: AuthorizationCode       :: " + response.getAuthorizationCode());
					Logger.write("     [WSS] :: out                     :: BankResponseCode        :: " + response.getBankResponseCode());
					Logger.write("     [WSS] :: out                     :: DescriptionResponse     :: " + response.getDescriptionResponse());
					Logger.write("     [WSS] :: out                     :: StandardResponseCode    :: " + response.getStandardResponseCode());
					Logger.write("     [WSS] :: out                     :: TransactionControlNumber:: " + response.getTransactionControlNumber());

					transaction.setAutorizationCode(response.getAuthorizationCode());
					transaction.setBankCode(response.getBankResponseCode());
					transaction.setDescResp(response.getDescriptionResponse());
				} catch (ServiceException e) {
					Logger.write(e.getMessage());
					throw new ServiceException("No se pudieron reversar las transacciones :: " + e.getMessage());
				}

			}

		}catch(Exception e){
			transaction.setDescResp(e.getMessage());
			throw new ServiceException(e.getLocalizedMessage());
		} finally{
			Logger.write("     [<Fin>]    A U T O R I Z A D O R               : -autorizadorReverse-");
		}


		if (transaction.getBankCode().equals(
				RESPONSE_CODE_EXITO)
				|| transaction.getBankCode().equals(
						RESPONSE_CODE_EXITO_AMEX)) {

			return transaction;

		} else {
			Logger.write("Error al reversar tarjeta, "
					+ transaction.getDescResp());
			throw new ServiceException("Error al reversar tarjetas, " + transaction.getDescResp());
		}

	}

	private Transaction buildTransactionCharge(final KeyedTransactionVO keyedTransaction){
		Transaction transaction = new Transaction();
		transaction.setChargeId(keyedTransaction.getChargeChargeID());		
		transaction.setSystemId(keyedTransaction.getChargeSystemID());
		transaction.setBusinessId(keyedTransaction.getChargeBusinessID());
		transaction.setOperation(PAYMENT);
		transaction.setCardNumber(keyedTransaction.getBankCardNumber());
		transaction.setExpireMonth(keyedTransaction.getCardExpirationMonth());
		transaction.setExpireYear(keyedTransaction.getCardExpirationYear());
		transaction.setCardType(keyedTransaction.getCardType());
		transaction.setAmount(keyedTransaction.getChargeAmount());
		transaction.setConcept(keyedTransaction.getProductName());
		transaction.setRegionId(keyedTransaction.getChargeRegionID());
		transaction.setStoreId(keyedTransaction.getChargeStoreID());
		transaction.setTransactionDate(new Date());
		transaction.setCardReadMode(1);
		transaction.setVersion(VERSION_BANORTE);

		return transaction;
	}

	private KeyedTransactionVO buildTransactionReverse(final Transaction transaction){

		KeyedTransactionVO keyedTransaction = new KeyedTransactionVO();

		keyedTransaction.setChargeChargeID(transaction.getChargeId());
		keyedTransaction.setChargeSystemID(transaction.getSystemId());		
		keyedTransaction.setChargeBusinessID(transaction.getBusinessId());
		keyedTransaction.setBankCardNumber(transaction.getCardNumber());
		keyedTransaction.setCardExpirationMonth(transaction.getExpireMonth());
		keyedTransaction.setCardExpirationYear(transaction.getExpireYear());
		keyedTransaction.setCardType(transaction.getCardType());
		keyedTransaction.setChargeAmount(transaction.getAmount());
		keyedTransaction.setChargePaymentPeriod(transaction.getPeriod());
		keyedTransaction.setProductName(transaction.getConcept());
		keyedTransaction.setChargeRegionID(transaction.getRegionId());
		keyedTransaction.setChargeStoreID(transaction.getStoreId());
		keyedTransaction.setReservedA(CANCELACION);

		return keyedTransaction;
	}

	private String gregorianCalendarToString(final Calendar calendar) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
		"dd/MM/yyyy HH:mm:ss");
		return dateFormat.format(calendar.getTime());
	}

	private String obtenerBeanTarjeta(String cardNum) {
		String bean = null;

		if (cardNum != null && cardNum.length() >= 6) {
			bean = cardNum.substring(0, 6);
		}

		return bean;
	}

	
	private String getValueState(String estado){				
		String response = "";
		if(estado == null){
			response = "";
		}else{	
			Map<String,String> catalogStates = getCatalogStates();
			estado = estado.toUpperCase();
			response = (catalogStates.get(estado) == null) ? "" : catalogStates.get(estado);
		}
		return response;
	}
	
	private Map<String,String> getCatalogStates(){
		Map<String,String> catalogStates = new HashMap<String, String>();
		catalogStates.put("AGS", "AS");//Aguascalientes
		catalogStates.put("BCN", "BC");//Baja California
		catalogStates.put("BCS", "BS");//Baja California Sur
		catalogStates.put("CAM", "CC");//Campeche
		catalogStates.put("CHP", "CS");//Chiapas
		catalogStates.put("CHI", "CH");//Chihuahua
		catalogStates.put("COA", "CL");//Coahuila
		catalogStates.put("COL", "CM");//Colima
		catalogStates.put("DIF", "DF");//Distrito Federal
		catalogStates.put("DUR", "DG");//Durango
		catalogStates.put("GTO", "GT");//Guanajuato
		catalogStates.put("GRO", "GR");//Guerrero
		catalogStates.put("HGO", "HG");//Hidalgo
		catalogStates.put("JAL", "JC");//Jalisco
		catalogStates.put("MEX", "MC");//Estado de México
		catalogStates.put("MIC", "MN");//Michoacán
		catalogStates.put("MOR", "MS");//Morelos
		catalogStates.put("NAY", "NT");//Nayarit
		catalogStates.put("NLE", "NL");//Nuevo León
		catalogStates.put("OAX", "OC");//Oaxaca
		catalogStates.put("PUE", "PL");//Puebla
		catalogStates.put("QRO", "QT");//Querétaro
		catalogStates.put("ROO", "QR");//Quintana Roo
		catalogStates.put("SLP", "SP");//San Luis Potosí
		catalogStates.put("SIN", "SL");//Sinaloa
		catalogStates.put("SON", "SR");//Sonora
		catalogStates.put("TAB", "TC");//Tabasco
		catalogStates.put("TAM", "TS");//Tamaulipas
		catalogStates.put("TLX", "TL");//Tlaxcala
		catalogStates.put("VER", "VZ");//Veracruz
		catalogStates.put("YUC", "YN");//Yucatán
		catalogStates.put("ZAC", "ZS");//Zacatecas
		
		return catalogStates;
	}	
}
