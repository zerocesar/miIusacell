package mx.com.iusacell.services.miusacell.call;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

import mx.com.iusacell.services.SOAPManager.SOAPManagerAxis2;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.vo.autorizador.CardVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.KeyedTransactionVO;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceAutorizador {

	public String applyKeyedCharge(final KeyedTransactionVO transaction) throws ServiceException {

		String url = ResourceAccess.getParametroFromXML("urlAutorizador");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : applyKeyedCharge");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     BankCardNumber         : " + transaction.getBankCardNumber());
		Logger.write("     CardCvv2               : XXX");
		Logger.write("     CardExpirationMonth    : " + transaction.getCardExpirationMonth());
		Logger.write("     CardExpirationYear     : " + transaction.getCardExpirationYear());
		Logger.write("     CardType               : " + transaction.getCardType());
		Logger.write("     ChargeAmount           : " + transaction.getChargeAmount());
		Logger.write("     ChargeBusinessID       : " + transaction.getChargeBusinessID());
		Logger.write("     ChargeChargeID         : " + transaction.getChargeChargeID());
		Logger.write("     ChargePaymentPeriod    : " + transaction.getChargePaymentPeriod());
		Logger.write("     ChargeRegionID         : " + transaction.getChargeRegionID());
		Logger.write("     ChargeStoreID          : " + transaction.getChargeStoreID());
		Logger.write("     ChargeSystemID         : " + transaction.getChargeSystemID());
		Logger.write("     CustomerBankPhoneNumber: " + transaction.getCustomerBankPhoneNumber());
		Logger.write("     CustomerCountry        : " + transaction.getCustomerCountry());
		Logger.write("     CustomerCounty         : " + transaction.getCustomerCounty());
		Logger.write("     CustomerEmail          : " + transaction.getCustomerEmail());
		Logger.write("     CustomerFirstName      : " + transaction.getCustomerFirstName());
		Logger.write("     CustomerIpAddress      : " + transaction.getCustomerIpAddress());
		Logger.write("     CustomerLastName       : " + transaction.getCustomerLastName());
		Logger.write("     CustomerNeighborhood   : " + transaction.getCustomerNeighborhood());
		Logger.write("     CustomerPostalCode     : " + transaction.getCustomerPostalCode());
		Logger.write("     CustomerState          : " + transaction.getCustomerState());
		Logger.write("     CustomerStreet         : " + transaction.getCustomerStreet());			
		Logger.write("     CustomerStreetNumber   : " + transaction.getCustomerStreetNumber());
		Logger.write("     DeviceFingerprintId    : " + transaction.getDeviceFingerprintId());
		Logger.write("     ProductName            : " + transaction.getProductName());

		String sResponse="";

		try{
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace bean = fac.createOMNamespace("http://bean.at.iusacell.com", "bean");			
			OMNamespace wsNs = fac.createOMNamespace("http://vo.at.iusacell.com", "vo");

			OMElement applyKeyedCharge = fac.createOMElement("applyKeyedCharge", bean);
			OMElement transactionVO = fac.createOMElement("transaction", bean);

			OMElement vobankCardNumber = fac.createOMElement("bankCardNumber", wsNs);
			OMElement vocardCvv2 = fac.createOMElement("cardCvv2", wsNs);
			OMElement vocardExpirationMonth = fac.createOMElement("cardExpirationMonth", wsNs);
			OMElement vocardExpirationYear = fac.createOMElement("cardExpirationYear", wsNs);
			OMElement vocardType = fac.createOMElement("cardType", wsNs);
			OMElement vochargeAmount = fac.createOMElement("chargeAmount", wsNs);
			OMElement vochargeBusinessID = fac.createOMElement("chargeBusinessID", wsNs);
			OMElement vochargeChargeID = fac.createOMElement("chargeChargeID", wsNs);
			OMElement vochargePaymentPeriod = fac.createOMElement("chargePaymentPeriod", wsNs);
			OMElement vochargeRegionID = fac.createOMElement("chargeRegionID", wsNs);
			OMElement vochargeStoreID = fac.createOMElement("chargeStoreID", wsNs);
			OMElement vochargeSystemID = fac.createOMElement("chargeSystemID", wsNs);			
			OMElement vocustomerBankPhoneNumber = fac.createOMElement("customerBankPhoneNumber", wsNs);
			//			OMElement vocustomerCellPhoneNumber = fac.createOMElement("customerCellPhoneNumber", wsNs);
			OMElement vocustomerCountry = fac.createOMElement("customerCountry", wsNs);
			OMElement vocustomerCounty = fac.createOMElement("customerCounty", wsNs);
			OMElement vocustomerEmail = fac.createOMElement("customerEmail", wsNs);
			OMElement vocustomerFirstName = fac.createOMElement("customerFirstName", wsNs);
			OMElement vocustomerIpAddress = fac.createOMElement("customerIpAddress", wsNs);
			OMElement vocustomerLastName = fac.createOMElement("customerLastName", wsNs);
			OMElement vocustomerNeighborhood = fac.createOMElement("customerNeighborhood", wsNs);
			OMElement vocustomerPostalCode = fac.createOMElement("customerPostalCode", wsNs);
			OMElement vocustomerState = fac.createOMElement("customerState", wsNs);
			OMElement vocustomerStreet = fac.createOMElement("customerStreet", wsNs);			
			OMElement vocustomerStreetNumber = fac.createOMElement("customerStreetNumber", wsNs);
			OMElement vodeviceFingerprintId = fac.createOMElement("deviceFingerprintId", wsNs);
			OMElement voproductName = fac.createOMElement("productName", wsNs);


			vobankCardNumber.setText(transaction.getBankCardNumber());
			vocardCvv2.setText(transaction.getCardCvv2());
			vocardExpirationMonth.setText(transaction.getCardExpirationMonth());
			vocardExpirationYear.setText(transaction.getCardExpirationYear());
			vocardType.setText(transaction.getCardType());
			vochargeAmount.setText(transaction.getChargeAmount()+"");
			vochargeBusinessID.setText(transaction.getChargeBusinessID()+"");
			vochargeChargeID.setText(transaction.getChargeChargeID()+"");
			vochargePaymentPeriod.setText(transaction.getChargePaymentPeriod()+"");
			vochargeRegionID.setText(transaction.getChargeRegionID());
			vochargeStoreID.setText(transaction.getChargeStoreID());
			vochargeSystemID.setText(transaction.getChargeSystemID()+"");
			vocustomerBankPhoneNumber.setText(transaction.getCustomerBankPhoneNumber());
			//			vocustomerCellPhoneNumber.setText(transaction.get());
			vocustomerCountry.setText(transaction.getCustomerCountry());
			vocustomerCounty.setText(transaction.getCustomerCounty());
			vocustomerEmail.setText(transaction.getCustomerEmail());
			vocustomerFirstName.setText(transaction.getCustomerFirstName());
			vocustomerIpAddress.setText(transaction.getCustomerIpAddress());
			vocustomerLastName.setText(transaction.getCustomerLastName());
			vocustomerNeighborhood.setText(transaction.getCustomerNeighborhood());
			vocustomerPostalCode.setText(transaction.getCustomerPostalCode());
			vocustomerState.setText(transaction.getCustomerState());
			vocustomerStreet.setText(transaction.getCustomerStreet());			
			vocustomerStreetNumber.setText(transaction.getCustomerStreetNumber());
			vodeviceFingerprintId.setText(transaction.getDeviceFingerprintId());
			voproductName.setText(transaction.getProductName());

			if(transaction.getBankCardNumber() != null){
				transactionVO.addChild(vobankCardNumber);}
			if(transaction.getCardCvv2() != null){
				transactionVO.addChild(vocardCvv2);}
			if(transaction.getCardExpirationMonth()!= null){
				transactionVO.addChild(vocardExpirationMonth);}
			if(transaction.getCardExpirationYear() != null){
				transactionVO.addChild(vocardExpirationYear);}
			if(transaction.getCardType() != null){
				transactionVO.addChild(vocardType);}
			if(vochargeAmount != null){
				transactionVO.addChild(vochargeAmount);}
			if(vochargeBusinessID != null){
				transactionVO.addChild(vochargeBusinessID);}
			if(vochargeChargeID != null){
				transactionVO.addChild(vochargeChargeID);}
			if(vochargePaymentPeriod != null){
				transactionVO.addChild(vochargePaymentPeriod);}
			if(transaction.getChargeRegionID() != null){
				transactionVO.addChild(vochargeRegionID);}
			if(transaction.getChargeStoreID() != null){
				transactionVO.addChild(vochargeStoreID);}
			if(vochargeSystemID != null){
				transactionVO.addChild(vochargeSystemID);}

			if(transaction.getCustomerBankPhoneNumber() != null){
				transactionVO.addChild(vocustomerBankPhoneNumber);}
			if(transaction.getCustomerCountry() != null){
				transactionVO.addChild(vocustomerCountry);}
			if(transaction.getCustomerCounty() != null){
				transactionVO.addChild(vocustomerCounty);}
			if(transaction.getCustomerEmail() != null){
				transactionVO.addChild(vocustomerEmail);}
			if(transaction.getCustomerFirstName() != null){
				transactionVO.addChild(vocustomerFirstName);}
			if(transaction.getCustomerIpAddress() != null){
				transactionVO.addChild(vocustomerIpAddress);}

			if(transaction.getCustomerLastName() != null){
				transactionVO.addChild(vocustomerLastName);}
			if(transaction.getCustomerNeighborhood() != null){
				transactionVO.addChild(vocustomerNeighborhood);}
			if(transaction.getCustomerPostalCode() != null){
				transactionVO.addChild(vocustomerPostalCode);}
			if(transaction.getCustomerState() != null){
				transactionVO.addChild(vocustomerState);}
			if(transaction.getCustomerStreet() != null){
				transactionVO.addChild(vocustomerStreet);}
			if(transaction.getCustomerStreetNumber() != null){
				transactionVO.addChild(vocustomerStreetNumber);}

			if(transaction.getDeviceFingerprintId() != null){
				transactionVO.addChild(vodeviceFingerprintId);}
			if(transaction.getProductName() != null){
				transactionVO.addChild(voproductName);}

			applyKeyedCharge.addChild(transactionVO);

			Logger.write("     xmlRequest             : " + applyKeyedCharge.toString());

			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, applyKeyedCharge);			

		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		finally{
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
		}

		return sResponse;
	}

	public String executeGeneralCharge(final int chargeId, final CardVO card, final double amount, final int mesesSinIntereses, 
			final int businessId, final String clientIp, final int usoTarjeta, final int idSistema) throws ServiceException {
		String url = ResourceAccess.getParametroFromXML("urlAutorizador");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : executeGeneralCharge");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");	
		Logger.write("     chargeId               : " + chargeId);
		Logger.write("     CardVO                 : " );
		Logger.write("        CardNumber          : " + card.getCardNumber());
		Logger.write("        Cvv2                : XXX");
		Logger.write("        ExpirationMonth     : " + card.getExpirationMonth());
		Logger.write("        ExpirationYear      : " + card.getExpirationYear());
		Logger.write("        Type                : " + card.getType());
		Logger.write("        CreationDate        : " + gregorianCalendarToString(card.getCreationDate()));
		Logger.write("     amount                 : " + amount);
		Logger.write("     mesesSinIntereses      : " + mesesSinIntereses);
		Logger.write("     businessId             : " + businessId);
		Logger.write("     clientIp               : " + clientIp);
		Logger.write("     usoTarjeta             : " + usoTarjeta);
		Logger.write("     idSistema              : " + idSistema);
		

		String sResponse="";

		try{
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace bean = fac.createOMNamespace("http://bean.at.iusacell.com", "bean");			
			OMNamespace wsNs = fac.createOMNamespace("http://vo.at.iusacell.com", "vo");

			OMElement executeGeneralCharge = fac.createOMElement("executeGeneralCharge", bean);
			OMElement cardVO = fac.createOMElement("card", bean);										

			OMElement vocardNumber = fac.createOMElement("cardNumber", wsNs);
			OMElement vocreationDate = fac.createOMElement("creationDate", wsNs);
			OMElement vocvv2 = fac.createOMElement("cvv2", wsNs);
			OMElement voexpirationMonth = fac.createOMElement("expirationMonth", wsNs);
			OMElement voexpirationYear = fac.createOMElement("expirationYear", wsNs);
			OMElement votype = fac.createOMElement("type", wsNs);

			OMElement beanchargeId = fac.createOMElement("chargeId", bean);
			OMElement beanamount = fac.createOMElement("amount", bean);
			OMElement beanmesesSinIntereses = fac.createOMElement("mesesSinIntereses", bean);			
			OMElement beanbussinesId = fac.createOMElement("bussinesId", bean);	
			OMElement beanclientIp = fac.createOMElement("clientIp", bean);
			OMElement beanusoTarjeta = fac.createOMElement("usoTarjeta", bean);
			OMElement beanidSystem = fac.createOMElement("idSystem", bean);	


			vocardNumber.setText(card.getCardNumber());			
			vocreationDate.setText(gregorianCalendarToString(card.getCreationDate()));
			vocvv2.setText(card.getCvv2());
			voexpirationMonth.setText(card.getExpirationMonth());
			voexpirationYear.setText(card.getExpirationYear());
			votype.setText(card.getType()+"");

			beanchargeId.setText(String.valueOf(chargeId));
			beanamount.setText(amount+"");
			beanmesesSinIntereses.setText(mesesSinIntereses+"");
			beanbussinesId.setText(businessId+"");
			beanclientIp.setText(clientIp);
			beanusoTarjeta.setText(usoTarjeta+"");
			beanidSystem.setText(idSistema+"");		

			if(vocardNumber != null){
				cardVO.addChild(vocardNumber);}
			if(vocreationDate != null){
				cardVO.addChild(vocreationDate);}		
			if(vocvv2 != null){
				cardVO.addChild(vocvv2);}
			if(voexpirationMonth != null){
				cardVO.addChild(voexpirationMonth);}
			if(voexpirationYear != null){
				cardVO.addChild(voexpirationYear);}
			if(votype != null){
				cardVO.addChild(votype);}

			if(beanchargeId != null){
				executeGeneralCharge.addChild(beanchargeId);}
			if(beanamount != null){
				executeGeneralCharge.addChild(beanamount);}
			if(beanmesesSinIntereses != null){
				executeGeneralCharge.addChild(beanmesesSinIntereses);}
			if(beanbussinesId != null){
				executeGeneralCharge.addChild(beanbussinesId);}
			if(beanclientIp != null){
				executeGeneralCharge.addChild(beanclientIp);}
			if(beanusoTarjeta != null){
				executeGeneralCharge.addChild(beanusoTarjeta);}
			if(beanidSystem != null){
				executeGeneralCharge.addChild(beanidSystem);}


			executeGeneralCharge.addChild(cardVO);						

			Logger.write("     xmlRequest             : " + Utilerias.pintaLogRequest(executeGeneralCharge.toString()));

			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, executeGeneralCharge);			

		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		finally{
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
		}

		return sResponse;
	}

	public String applyKeyedReverse(final KeyedTransactionVO transaction) throws ServiceException {

		String url = ResourceAccess.getParametroFromXML("urlAutorizador");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : applyKeyedReverse");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");	
		Logger.write("     BankCardNumber         : " + transaction.getBankCardNumber());
		Logger.write("     CardCvv2               : XXX");
		Logger.write("     CardExpirationMonth    : " + transaction.getCardExpirationMonth());
		Logger.write("     CardExpirationYear     : " + transaction.getCardExpirationYear());
		Logger.write("     CardType               : " + transaction.getCardType());
		Logger.write("     ChargeAmount           : " + transaction.getChargeAmount());
		Logger.write("     ChargeBusinessID       : " + transaction.getChargeBusinessID());
		Logger.write("     ChargeChargeID         : " + transaction.getChargeChargeID());
		Logger.write("     ChargePaymentPeriod    : " + transaction.getChargePaymentPeriod());
		Logger.write("     ChargeRegionID         : " + transaction.getChargeRegionID());
		Logger.write("     ChargeStoreID          : " + transaction.getChargeStoreID());
		Logger.write("     ChargeSystemID         : " + transaction.getChargeSystemID());
		Logger.write("     CustomerBankPhoneNumber: " + transaction.getCustomerBankPhoneNumber());
		Logger.write("     CustomerCountry        : " + transaction.getCustomerCountry());
		Logger.write("     CustomerEmail          : " + transaction.getCustomerEmail());
		Logger.write("     CustomerFirstName      : " + transaction.getCustomerFirstName());
		Logger.write("     CustomerIpAddress      : " + transaction.getCustomerIpAddress());
		Logger.write("     CustomerLastName       : " + transaction.getCustomerLastName());
		Logger.write("     CustomerNeighborhood   : " + transaction.getCustomerNeighborhood());
		Logger.write("     CustomerPostalCode     : " + transaction.getCustomerPostalCode());
		Logger.write("     CustomerState          : " + transaction.getCustomerState());
		Logger.write("     CustomerStreet         : " + transaction.getCustomerStreet());			
		Logger.write("     CustomerStreetNumber   : " + transaction.getCustomerStreetNumber());
		Logger.write("     DeviceFingerprintId    : " + transaction.getDeviceFingerprintId());
		Logger.write("     ProductName            : " + transaction.getProductName());

		String sResponse="";

		try{
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace bean = fac.createOMNamespace("http://bean.at.iusacell.com", "bean");			
			OMNamespace wsNs = fac.createOMNamespace("http://vo.at.iusacell.com", "vo");

			OMElement applyKeyedCharge = fac.createOMElement("applyKeyedReverse", bean);
			OMElement transactionVO = fac.createOMElement("transaction", bean);

			OMElement vobankCardNumber = fac.createOMElement("bankCardNumber", wsNs);
			OMElement vocardCvv2 = fac.createOMElement("cardCvv2", wsNs);
			OMElement vocardExpirationMonth = fac.createOMElement("cardExpirationMonth", wsNs);
			OMElement vocardExpirationYear = fac.createOMElement("cardExpirationYear", wsNs);
			OMElement vocardType = fac.createOMElement("cardType", wsNs);
			OMElement vochargeAmount = fac.createOMElement("chargeAmount", wsNs);
			OMElement vochargeBusinessID = fac.createOMElement("chargeBusinessID", wsNs);
			OMElement vochargeChargeID = fac.createOMElement("chargeChargeID", wsNs);
			OMElement vochargePaymentPeriod = fac.createOMElement("chargePaymentPeriod", wsNs);
			OMElement vochargeRegionID = fac.createOMElement("chargeRegionID", wsNs);
			OMElement vochargeStoreID = fac.createOMElement("chargeStoreID", wsNs);
			OMElement vochargeSystemID = fac.createOMElement("chargeSystemID", wsNs);			
			OMElement vocustomerBankPhoneNumber = fac.createOMElement("customerBankPhoneNumber", wsNs);
			//			OMElement vocustomerCellPhoneNumber = fac.createOMElement("customerCellPhoneNumber", wsNs);
			OMElement vocustomerCountry = fac.createOMElement("customerCountry", wsNs);
			//			OMElement vocustomerCounty = fac.createOMElement("customerCounty", wsNs);
			OMElement vocustomerEmail = fac.createOMElement("customerEmail", wsNs);
			OMElement vocustomerFirstName = fac.createOMElement("customerFirstName", wsNs);
			OMElement vocustomerIpAddress = fac.createOMElement("customerIpAddress", wsNs);
			OMElement vocustomerLastName = fac.createOMElement("customerLastName", wsNs);
			OMElement vocustomerNeighborhood = fac.createOMElement("customerNeighborhood", wsNs);
			OMElement vocustomerPostalCode = fac.createOMElement("customerPostalCode", wsNs);
			OMElement vocustomerState = fac.createOMElement("customerState", wsNs);
			OMElement vocustomerStreet = fac.createOMElement("customerStreet", wsNs);			
			OMElement vocustomerStreetNumber = fac.createOMElement("customerStreetNumber", wsNs);
			OMElement vodeviceFingerprintId = fac.createOMElement("deviceFingerprintId", wsNs);
			OMElement voproductName = fac.createOMElement("productName", wsNs);
			OMElement voreservedA = fac.createOMElement("reservedA", wsNs);


			vobankCardNumber.setText(transaction.getBankCardNumber());
			vocardCvv2.setText(transaction.getCardCvv2());
			vocardExpirationMonth.setText(transaction.getCardExpirationMonth());
			vocardExpirationYear.setText(transaction.getCardExpirationYear());
			vocardType.setText(transaction.getCardType());
			vochargeAmount.setText(transaction.getChargeAmount()+"");
			vochargeBusinessID.setText(transaction.getChargeBusinessID()+"");
			vochargeChargeID.setText(transaction.getChargeChargeID()+"");
			vochargePaymentPeriod.setText(transaction.getChargePaymentPeriod()+"");
			vochargeRegionID.setText(transaction.getChargeRegionID());
			vochargeStoreID.setText(transaction.getChargeStoreID());
			vochargeSystemID.setText(transaction.getChargeSystemID()+"");
			vocustomerBankPhoneNumber.setText(transaction.getCustomerBankPhoneNumber());
			//			vocustomerCellPhoneNumber.setText(transaction.get());
			vocustomerCountry.setText(transaction.getCustomerCountry());
			//			vocustomerCounty.setText(transaction.get());
			vocustomerEmail.setText(transaction.getCustomerEmail());
			vocustomerFirstName.setText(transaction.getCustomerFirstName());
			vocustomerIpAddress.setText(transaction.getCustomerIpAddress());
			vocustomerLastName.setText(transaction.getCustomerLastName());
			vocustomerNeighborhood.setText(transaction.getCustomerNeighborhood());
			vocustomerPostalCode.setText(transaction.getCustomerPostalCode());
			vocustomerState.setText(transaction.getCustomerState());
			vocustomerStreet.setText(transaction.getCustomerStreet());			
			vocustomerStreetNumber.setText(transaction.getCustomerStreetNumber());
			vodeviceFingerprintId.setText(transaction.getDeviceFingerprintId());
			voproductName.setText(transaction.getProductName());
			voreservedA.setText(transaction.getReservedA());

			if(vobankCardNumber != null){
				transactionVO.addChild(vobankCardNumber);}
//			if(vocardCvv2 != null){
//				transactionVO.addChild(vocardCvv2);}
			if(vocardExpirationMonth!= null){
				transactionVO.addChild(vocardExpirationMonth);}
			if(vocardExpirationYear != null){
				transactionVO.addChild(vocardExpirationYear);}
			if(vocardType != null){
				transactionVO.addChild(vocardType);}
			if(vochargeAmount != null){
				transactionVO.addChild(vochargeAmount);}
			if(vochargeBusinessID != null){
				transactionVO.addChild(vochargeBusinessID);}
			if(vochargeChargeID != null){
				transactionVO.addChild(vochargeChargeID);}
			if(vochargePaymentPeriod != null){
				transactionVO.addChild(vochargePaymentPeriod);}
			if(vochargeRegionID != null){
				transactionVO.addChild(vochargeRegionID);}
			if(vochargeStoreID != null){
				transactionVO.addChild(vochargeStoreID);}
			if(vochargeSystemID != null){
				transactionVO.addChild(vochargeSystemID);}

//			if(vocustomerBankPhoneNumber != null){
//				transactionVO.addChild(vocustomerBankPhoneNumber);}
//			if(vocustomerCountry != null){
//				transactionVO.addChild(vocustomerCountry);}
//			if(vocustomerEmail != null){
//				transactionVO.addChild(vocustomerEmail);}
//			if(vocustomerFirstName != null){
//				transactionVO.addChild(vocustomerFirstName);}

//			transactionVO.addChild(vocustomerIpAddress);

//			if(vocustomerLastName != null){
//				transactionVO.addChild(vocustomerLastName);}
//			if(vocustomerNeighborhood != null){
//				transactionVO.addChild(vocustomerNeighborhood);}
//			if(vocustomerPostalCode != null){
//				transactionVO.addChild(vocustomerPostalCode);}
//			if(vocustomerState != null){
//				transactionVO.addChild(vocustomerState);}
//			if(vocustomerStreet != null){
//				transactionVO.addChild(vocustomerStreet);}
//			if(vocustomerStreetNumber != null){
//				transactionVO.addChild(vocustomerStreetNumber);}

//			if(vodeviceFingerprintId != null){
//				transactionVO.addChild(vodeviceFingerprintId);}
			if(voproductName != null){
				transactionVO.addChild(voproductName);}
			if(voreservedA != null)
				transactionVO.addChild(voreservedA);


			applyKeyedCharge.addChild(transactionVO);

			Logger.write("     xmlRequest             : " + applyKeyedCharge.toString());

			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, applyKeyedCharge);		

		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		finally{
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
		}

		return sResponse;
	}
	
	public String executeGeneralReverse(
			final int chargeId, final CardVO card, final double amount, 
			final int period, final int businessId, final String clientIp, 
			final int cardReadMode, final int idSistema) 
	throws ServiceException {
		String url = ResourceAccess.getParametroFromXML("urlAutorizador");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : executeGeneralReverse");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");	
		Logger.write("     chargeId               : " + chargeId);
		Logger.write("     CardVO                 : " );
		Logger.write("        CardNumber          : " + card.getCardNumber());
		Logger.write("        Cvv2                : XXX");
		Logger.write("        ExpirationMonth     : " + card.getExpirationMonth());
		Logger.write("        ExpirationYear      : " + card.getExpirationYear());
		Logger.write("        Type                : " + card.getType());
		Logger.write("        CreationDate        : " + gregorianCalendarToString(card.getCreationDate()));
		Logger.write("     amount                 : " + amount);
		Logger.write("     mesesSinIntereses      : " + period);
		Logger.write("     businessId             : " + businessId);
		Logger.write("     clientIp               : " + clientIp);
		Logger.write("     usoTarjeta             : " + cardReadMode);
		Logger.write("     idSistema              : " + idSistema);

		String sResponse="";

		try{
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace bean = fac.createOMNamespace("http://bean.at.iusacell.com", "bean");			
			OMNamespace wsNs = fac.createOMNamespace("http://vo.at.iusacell.com", "vo");

			OMElement executeGeneralReverse = fac.createOMElement("executeGeneralReverse", bean);
			OMElement cardVO = fac.createOMElement("card", bean);										

			OMElement vocardNumber = fac.createOMElement("cardNumber", wsNs);
//			OMElement vocreationDate = fac.createOMElement("creationDate", wsNs);
			OMElement vocvv2 = fac.createOMElement("cvv2", wsNs);
			OMElement voexpirationMonth = fac.createOMElement("expirationMonth", wsNs);
			OMElement voexpirationYear = fac.createOMElement("expirationYear", wsNs);
			OMElement votype = fac.createOMElement("type", wsNs);

			OMElement beanchargeId = fac.createOMElement("chargeId", bean);
			OMElement beanamount = fac.createOMElement("amount", bean);
			OMElement beanmesesSinIntereses = fac.createOMElement("mesesSinIntereses", bean);			
			OMElement beanbussinesId = fac.createOMElement("bussinesId", bean);	
			OMElement beanclientIp = fac.createOMElement("clientIp", bean);
			OMElement beanusoTarjeta = fac.createOMElement("usoTarjeta", bean);
			OMElement beanidSystem = fac.createOMElement("idSystem", bean);	


			vocardNumber.setText(card.getCardNumber());			
//			vocreationDate.setText(gregorianCalendarToString(card.getCreationDate()));
			vocvv2.setText(card.getCvv2());
			voexpirationMonth.setText(card.getExpirationMonth());
			voexpirationYear.setText(card.getExpirationYear());
			votype.setText(card.getType()+"");

			beanchargeId.setText(String.valueOf(chargeId));
			beanamount.setText(amount+"");
			beanmesesSinIntereses.setText(period+"");
			beanbussinesId.setText(businessId+"");
			beanclientIp.setText(clientIp);
			beanusoTarjeta.setText(cardReadMode+"");
			beanidSystem.setText(idSistema+"");		

			if(vocardNumber != null){
				cardVO.addChild(vocardNumber);}
//			if(vocreationDate != null){
//				cardVO.addChild(vocreationDate);}		
//			if(vocvv2 != null){
//				cardVO.addChild(vocvv2);}
			if(voexpirationMonth != null){
				cardVO.addChild(voexpirationMonth);}
			if(voexpirationYear != null){
				cardVO.addChild(voexpirationYear);}
			if(votype != null){
				cardVO.addChild(votype);}

			if(beanchargeId != null){
				executeGeneralReverse.addChild(beanchargeId);}
			if(beanamount != null){
				executeGeneralReverse.addChild(beanamount);}
			if(beanmesesSinIntereses != null){
				executeGeneralReverse.addChild(beanmesesSinIntereses);}
			if(beanbussinesId != null){
				executeGeneralReverse.addChild(beanbussinesId);}
			if(beanclientIp != null){
				executeGeneralReverse.addChild(beanclientIp);}
			if(beanusoTarjeta != null){
				executeGeneralReverse.addChild(beanusoTarjeta);}
			if(beanidSystem != null){
				executeGeneralReverse.addChild(beanidSystem);}


			executeGeneralReverse.addChild(cardVO);						

			Logger.write("     xmlRequest             : " + Utilerias.pintaLogRequest(executeGeneralReverse.toString()));

			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, executeGeneralReverse);			

		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		finally{
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
		}

		return sResponse;
	}
	
	private String gregorianCalendarToString(Calendar calendar) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS");
		
		return dateFormat.format(calendar.getTime());
	}
}
