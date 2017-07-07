package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

import com.iusacell.EncryptStandAlone.Encriptar;

import mx.com.iusacell.services.SOAPManager.SOAPManagerAxis2;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.BankCardAdditionalInfoVO;
import mx.com.iusacell.services.miusacell.util.AlgoritmoAes;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceSemaphore {
	public boolean SemaphoreSaveCustomerInfo(final String DN, final String customerName, final String customerLastName, final String customerMaidenName, String bankCardNumber, 
	        final String cardHolder, final String expirationDate, final String postalCode) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		String url = "";
		String banckCardPrefix="";
		BankCardAdditionalInfoVO cardAdditionalInfo;
		
		try{
			url = oracle.getValorParametro(166);
		}catch (Exception e) {
			url = "";
			throw new ServiceException(" [ctrl] No se pudo obtener la url del service Semaphore de la BD");
		}
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : SemaphoreSaveCustomerInfo");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     DN                     : "+ DN );
		Logger.write("     Nombre Cliente         : "+ customerName );
		Logger.write("     Primer Apellido        : "+ customerLastName );
		Logger.write("     Apellido de Soltero    : "+ customerMaidenName );
		Logger.write("     NumTarjeta             : "+ bankCardNumber );
		Logger.write("     Titular Tarjeta        : "+ cardHolder );
		Logger.write("     Fecha que expira       : "+ expirationDate );
		Logger.write("     Codigo Postal          : "+ postalCode );

		String sResponse 	= "";
		boolean respuesta=false;
		
		try
		{
			//Decencripta de aes128 y encripta en TDC
			AlgoritmoAes algo=new AlgoritmoAes();
			Encriptar encripta=new Encriptar();
			boolean  flagBankCardIssuer=false;
			String bankCardIssuer="";
			String bankCardType="";
			try{
				//Decencripta de aes128 y encripta en TDC
				bankCardNumber = algo.decrypt(bankCardNumber, "GrUPoSaLInaSsACv".getBytes());
				banckCardPrefix=bankCardNumber.substring(0,6);
				bankCardNumber = encripta.encriptarTDC(bankCardNumber);
			}catch (Exception e) { 
				throw new ServiceException(" [ctrl] Ocurrio un error al encriptar TDC para consultar Semaforo saveCustomerInfo");
			}
			
			try {
				
				Utilerias leerArchivoBines=new Utilerias();
				cardAdditionalInfo=leerArchivoBines.getBankCardAdditionalInfoFile(banckCardPrefix);

				if (cardAdditionalInfo.getCardTypeDescription()!=null){
					bankCardIssuer=cardAdditionalInfo.getCardTypeDescription();
					if(cardAdditionalInfo.getCardType().equals("1")){
						bankCardType="CREDITO";
					}else{
						bankCardType="DEBITO";
					}
				
				}else{
					throw new ServiceException();
				}
			}catch (Exception e) { 
				throw new ServiceException(" [ctrl] El prefijo de tarjeta no se encontró en el archivo BINES");
			}
			
			String idBusiness=oracle.getValorParametro(168);
			String catBankCardIssuer =oracle.getValorParametro(169)+oracle.getValorParametro(170)+oracle.getValorParametro(171)+oracle.getValorParametro(172)+oracle.getValorParametro(173)+oracle.getValorParametro(174);
			String[] listBankCardIssuer = catBankCardIssuer.split("\\|");
			
			for(int a=0; a< listBankCardIssuer.length; a++)
			{
				if(bankCardIssuer.toUpperCase().equals(listBankCardIssuer[a])){
					flagBankCardIssuer=true;
					break;
				}
			}
			if (!flagBankCardIssuer){
				throw new ServiceException(" [ctrl] El banco emisor no se encuentra en el catálogo");
			}
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace coreNs = fac.createOMNamespace("http://core.semaphore.iusacell.com", "core");
			
			OMElement saveCustomerInfo = fac.createOMElement("saveCustomerInfo", coreNs);
			OMElement mobileNumberElement                 = fac.createOMElement("mobileNumber", coreNs);
			OMElement customerNameElement                 = fac.createOMElement("customerName", coreNs);
			OMElement customerLastNameElement             = fac.createOMElement("customerLastName", coreNs);
			OMElement customerMaidenNameElement           = fac.createOMElement("customerMaidenName", coreNs);
			OMElement bankCardNumberElement               = fac.createOMElement("bankCardNumber", coreNs);
			OMElement cardHolderElement                   = fac.createOMElement("cardHolder", coreNs);
			OMElement expirationDateElement               = fac.createOMElement("expirationDate", coreNs);
			OMElement bankCardTypeElement                 = fac.createOMElement("bankCardType", coreNs);
			OMElement bankCardIssuerElement               = fac.createOMElement("bankCardIssuer", coreNs);
			OMElement postalCodeElement                   = fac.createOMElement("postalCode", coreNs);
			OMElement idBusinessElement               	  = fac.createOMElement("idBusiness", coreNs);
			
			mobileNumberElement.setText(DN);
			customerNameElement.setText(customerName);
			customerMaidenNameElement.setText(customerMaidenName);
			customerLastNameElement.setText(customerLastName);
			customerMaidenNameElement.setText(customerMaidenName);
			bankCardNumberElement.setText(bankCardNumber);
			cardHolderElement.setText(cardHolder);
			expirationDateElement.setText(expirationDate);
			bankCardTypeElement.setText(bankCardType);
			bankCardIssuerElement.setText(bankCardIssuer);
			postalCodeElement.setText(postalCode);
			idBusinessElement.setText(idBusiness);
			
			saveCustomerInfo.addChild(mobileNumberElement);
			saveCustomerInfo.addChild(customerNameElement);
			saveCustomerInfo.addChild(customerLastNameElement);
			saveCustomerInfo.addChild(customerMaidenNameElement);
			saveCustomerInfo.addChild(bankCardNumberElement);
			saveCustomerInfo.addChild(cardHolderElement);
			saveCustomerInfo.addChild(expirationDateElement);
			saveCustomerInfo.addChild(bankCardTypeElement);
			saveCustomerInfo.addChild(bankCardIssuerElement);
			saveCustomerInfo.addChild(postalCodeElement);
			saveCustomerInfo.addChild(idBusinessElement);
			
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(saveCustomerInfo.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			
			//********************************
			//Enviamos la peticion al servidor
			//********************************
			sResponse = send.send(url, saveCustomerInfo);
			
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			respuesta=Boolean.parseBoolean(ParseXMLFile.parseSemaphoresaveCustomerInfo(sResponse));
		}
		catch(Exception e)
		{
			sResponse = "";
			throw new ServiceException("[SemaphoreSaveCustomerInfo]"+e.getLocalizedMessage());
		}
		return respuesta;
	}
	
	public boolean SemaphoreDeleteBankCardInfo(final String dn, String bankCardNumber) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		String url = "";
		try{
			url = oracle.getValorParametro(166);
		}catch (Exception e) {
			url = "";
		throw new ServiceException("No se pudo obtener la url del service Semaphore de la BD");
		}
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : SemaphoreDeleteBankCardInfo");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     DN                     : "+ dn );
		Logger.write("     NumTarjeta             : "+ bankCardNumber );
		
		String sResponse 	= "";
		boolean respuesta=false;
		
		try
		{
			//Decencripta de aes128 y encripta en TDC
			AlgoritmoAes algo=new AlgoritmoAes();
			Encriptar encripta=new Encriptar();
			try{
				//Decencripta de aes128 y encripta en TDC
				bankCardNumber = algo.decrypt(bankCardNumber, "GrUPoSaLInaSsACv".getBytes());
				bankCardNumber = encripta.encriptarTDC(bankCardNumber);
			}catch (Exception e) { 
				throw new ServiceException(" [ctrl] Ocurrio un error al encriptar TDC para consultar el Semaforo deleteBankCardInfo");
			}
		
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace coreNs = fac.createOMNamespace("http://core.semaphore.iusacell.com", "core");
			
			OMElement deleteBankCardInfo = fac.createOMElement("deleteBankCardInfo", coreNs);
			OMElement mobileNumberElement                 = fac.createOMElement("mobileNumber", coreNs);
			OMElement bankCardNumberElement               = fac.createOMElement("bankCardNumber", coreNs);
			
			mobileNumberElement.setText(dn);
			bankCardNumberElement.setText(bankCardNumber);
			
			deleteBankCardInfo.addChild(mobileNumberElement);
			deleteBankCardInfo.addChild(bankCardNumberElement);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(deleteBankCardInfo.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			
			//********************************
			//Enviamos la peticion al servidor
			//********************************
			sResponse = send.send(url, deleteBankCardInfo);
			
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			respuesta=Boolean.parseBoolean(ParseXMLFile.parseSemaphoreDeleteBankCardInfo(sResponse));
		}
		catch(Exception e)
		{
			sResponse = "";
			throw new ServiceException(e.getLocalizedMessage());
		}
		return respuesta;
	}
	
	public boolean SemaphoreApplyCharge(final String mobileNumber, String bankCardNumber, final String amountToCharge, final String postalCode, final String idBusiness) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		String url = "";
		try{
			url = oracle.getValorParametro(166);
		}catch (Exception e) {
			url = "";
			throw new ServiceException("[ctrl] No se pudo obtener la url del service Semaphore de la BD");
		}
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : SemaphoreApplyCharge");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     mobileNumber           : "+ mobileNumber );
		Logger.write("     bankCardNumber         : "+ bankCardNumber );
		Logger.write("     amountToCharge         : "+ amountToCharge );
		Logger.write("     postalCode             : "+ postalCode );
		Logger.write("     idBusiness             : "+ idBusiness );
		
		
		String sResponse 	= "";
		boolean respuesta=false;
		
		try
		{
			//Decencripta de aes128 y encripta en TDC
			AlgoritmoAes algo=new AlgoritmoAes();
			Encriptar encripta=new Encriptar();
			try{
				//Decencripta de aes128 y encripta en TDC
				bankCardNumber = algo.decrypt(bankCardNumber, "GrUPoSaLInaSsACv".getBytes());
				bankCardNumber = encripta.encriptarTDC(bankCardNumber);
			}catch (Exception e) { 
				throw new ServiceException(" [ctrl] Ocurrio un error al encriptar TDC para consultar el Semaforo SemaphoreApplyCharge");
			}
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace coreNs = fac.createOMNamespace("http://core.semaphore.iusacell.com", "core");
			
			OMElement applyCharge = fac.createOMElement("applyCharge", coreNs);
			OMElement mobileNumberElement                 = fac.createOMElement("mobileNumber", coreNs);
			OMElement bankCardNumberElement               = fac.createOMElement("bankCardNumber", coreNs);
			OMElement amountToChargeElement               = fac.createOMElement("amountToCharge", coreNs);
			OMElement postalCodeElement                   = fac.createOMElement("postalCode", coreNs);
			OMElement idBusinessElement                   = fac.createOMElement("idBusiness", coreNs);
			
			mobileNumberElement.setText(mobileNumber);
			bankCardNumberElement.setText(bankCardNumber);
			amountToChargeElement.setText(amountToCharge);
			postalCodeElement.setText(postalCode);
			idBusinessElement.setText(idBusiness);
			
			applyCharge.addChild(mobileNumberElement);
			applyCharge.addChild(bankCardNumberElement);
			applyCharge.addChild(amountToChargeElement);
			applyCharge.addChild(postalCodeElement);
			applyCharge.addChild(idBusinessElement);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(applyCharge.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			
			//********************************
			//Enviamos la peticion al servidor
			//********************************
			sResponse = send.send(url, applyCharge);
			
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			respuesta=Boolean.parseBoolean(ParseXMLFile.parseSemaphoreApplyCharge(sResponse));
		}catch(Exception e)
		{
			sResponse = "";
			throw new ServiceException("[SemaphoreApplyCharge]"+ e.getLocalizedMessage());
		}
		return respuesta;
	}
	public boolean SemaphoreUpdateBankCardInfo(final String dn, String bankCardNumber, final String cardHolder, final String expirationDate, 
	        final 	String postalCode, final String customerName, final String customerLastName, final String customerMaidenName) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		String url = "";
		String banckCardPrefix="";
		BankCardAdditionalInfoVO cardAdditionalInfo;
		try{
			url = oracle.getValorParametro(166);
		}catch (Exception e) {
			url = "";
			throw new ServiceException(" [ctrl] No se pudo obtener la url del service Semaphore de la BD");
		}
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : SemaphoreUpdateBankCardInfo");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     DN                     : "+ dn );
		Logger.write("     NumTarjeta             : "+ bankCardNumber );
		Logger.write("     Titular Tarjeta         : "+ cardHolder );
		Logger.write("     Fecha que expira       : "+ expirationDate );
		Logger.write("     Codigo Postal          : "+ postalCode );
		Logger.write("     Nombre Cliente         : "+ customerName );
		Logger.write("     Primer Apellido        : "+ customerLastName );
		Logger.write("     Apellido de Soltero    : "+ customerMaidenName );
		
		
		String sResponse 	= "";
		boolean respuesta=false;
		
		try
		{
			//Decencripta de aes128 y encripta en TDC
			AlgoritmoAes algo=new AlgoritmoAes();
			Encriptar encripta=new Encriptar();
			boolean  flagBankCardIssuer=false;
			String bankCardIssuer="";
			String bankCardType="";
			try{
				//Decencripta de aes128 y encripta en TDC
				bankCardNumber = algo.decrypt(bankCardNumber, "GrUPoSaLInaSsACv".getBytes());
				banckCardPrefix=bankCardNumber.substring(0,6);
				bankCardNumber = encripta.encriptarTDC(bankCardNumber);
			}catch (Exception e) { 
				throw new ServiceException(" [ctrl] Ocurrio un error al encriptar TDC para consultar Semaforo SemaphoreUpdateBankCardInfo");
			}
			
			try {
				
				Utilerias leerArchivoBines=new Utilerias();
				cardAdditionalInfo=leerArchivoBines.getBankCardAdditionalInfoFile(banckCardPrefix);
				
				if (cardAdditionalInfo.getCardTypeDescription()!=null){
					bankCardIssuer=cardAdditionalInfo.getCardTypeDescription();
					if(cardAdditionalInfo.getCardType().equals("1")){
						bankCardType="CREDITO";
					}else{
						bankCardType="DEBITO";
					}
				
				}else{
					throw new ServiceException();
				}
			}catch (Exception e) { 
				throw new ServiceException("[ctrl] El prefijo de tarjeta no se encontró en el archivo BINES");
			}
			
			String catBankCardIssuer =oracle.getValorParametro(169)+oracle.getValorParametro(170)+oracle.getValorParametro(171)+oracle.getValorParametro(172)+oracle.getValorParametro(173)+oracle.getValorParametro(174);
			String[] listBankCardIssuer = catBankCardIssuer.split("\\|");
			
			for(int a=0; a< listBankCardIssuer.length; a++)
			{
				if(bankCardIssuer.toUpperCase().equals(listBankCardIssuer[a])){
					flagBankCardIssuer=true;
					break;
				}
			}
			if (!flagBankCardIssuer){
				throw new ServiceException(" [ctrl] El banco emisor no se encuentra en el catálogo");
			}
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace coreNs = fac.createOMNamespace("http://core.semaphore.iusacell.com", "core");
			
			OMElement saveCustomerInfo = fac.createOMElement("updateBankCardInfo", coreNs);
			OMElement mobileNumberElement                 = fac.createOMElement("mobileNumber", coreNs);
			OMElement bankCardNumberElement               = fac.createOMElement("bankCardNumber", coreNs);
			OMElement cardHolderElement                   = fac.createOMElement("cardHolder", coreNs);
			OMElement expirationDateElement               = fac.createOMElement("expirationDate", coreNs);
			OMElement bankCardTypeElement                 = fac.createOMElement("bankCardType", coreNs);
			OMElement bankCardIssuerElement               = fac.createOMElement("bankCardIssuer", coreNs);
			OMElement postalCodeElement                   = fac.createOMElement("postalCode", coreNs);
			OMElement customerNameElement                 = fac.createOMElement("customerName", coreNs);
			OMElement customerLastNameElement             = fac.createOMElement("customerLastName", coreNs);
			OMElement customerMaidenNameElement           = fac.createOMElement("customerMaidenName", coreNs);
			
			mobileNumberElement.setText(dn);
			bankCardNumberElement.setText(bankCardNumber);
			cardHolderElement.setText(cardHolder);
			expirationDateElement.setText(expirationDate);
			bankCardTypeElement.setText(bankCardType);
			bankCardIssuerElement.setText(bankCardIssuer);
			postalCodeElement.setText(postalCode);
			customerNameElement.setText(customerName);
			customerMaidenNameElement.setText(customerMaidenName);
			customerLastNameElement.setText(customerLastName);
			customerMaidenNameElement.setText(customerMaidenName);
			
			saveCustomerInfo.addChild(mobileNumberElement);
			saveCustomerInfo.addChild(bankCardNumberElement);
			saveCustomerInfo.addChild(cardHolderElement);
			saveCustomerInfo.addChild(expirationDateElement);
			saveCustomerInfo.addChild(bankCardTypeElement);
			saveCustomerInfo.addChild(bankCardIssuerElement);
			saveCustomerInfo.addChild(postalCodeElement);
			saveCustomerInfo.addChild(customerNameElement);
			saveCustomerInfo.addChild(customerLastNameElement);
			saveCustomerInfo.addChild(customerMaidenNameElement);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(saveCustomerInfo.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			
			//********************************
			//Enviamos la peticion al servidor
			//********************************
			sResponse = send.send(url, saveCustomerInfo);
			
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			respuesta=Boolean.parseBoolean(ParseXMLFile.parseSemaphoreUpdateBankCardInfo(sResponse));
			
		}catch(Exception e)
		{
			sResponse = "";
			throw new ServiceException(e.getLocalizedMessage());
		}
		return respuesta;
	}
}
