package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceAirAdapter {

	public String getDetalleCuenta(final String dn) throws ServiceException {

		String url = ResourceAccess.getParametroFromXML("AirAdapter");			 	
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : getDetalleCuenta");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);		

		StringBuilder sXMLRequest = new StringBuilder();				
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		OracleProcedures oracleProcedures = new OracleProcedures();
		String sResponse = "";
		String contrasena = "WhPTc9qdgF4us3Lw";
		String user = "USRAPPSPROD";		
		try {
			contrasena = oracleProcedures.getValorParametro(158);
		} catch (Exception e) {
			contrasena = "WhPTc9qdgF4us3Lw";
		}
		
		try {
			user = oracleProcedures.getValorParametro(159);
		} catch (Exception e) {
			user = "USRAPPSPROD";
		}

		try
		{								  
			sXMLRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:air=\"http://air.prov.iusacell.com.mx\" xmlns:xsd=\"http://common.air.prov.iusacell.com.mx/xsd\">");
			sXMLRequest.append("<soap:Header/>");
			sXMLRequest.append("<soap:Body>");
			sXMLRequest.append("<air:getAccountDetails>");				        				         				        
			sXMLRequest.append("<air:subscriberNumber>"+dn+"</air:subscriberNumber>");
			sXMLRequest.append("<air:messageCapabilityFlag>");
			sXMLRequest.append("<xsd:accountActivationFlag xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("<xsd:firstIVRCallSetFlag xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("<xsd:promotionNotificationFlag xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("</air:messageCapabilityFlag>");
			sXMLRequest.append("<air:requestedInformationFlags>");
			sXMLRequest.append("<xsd:allowedServiceClassChangeDateFlag xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("<xsd:requestMasterAccountBalanceFlag xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("</air:requestedInformationFlags>");
			sXMLRequest.append("<air:securityRequest>");
			sXMLRequest.append("<xsd:password>"+contrasena+"</xsd:password>");
			sXMLRequest.append("<xsd:ussername>"+user+"</xsd:ussername>");
			sXMLRequest.append("</air:securityRequest>");
			sXMLRequest.append("</air:getAccountDetails>");
			sXMLRequest.append("</soap:Body>");
			sXMLRequest.append("</soap:Envelope>");;

			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));

			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : "+ Utilerias.pintaLogRequest(sResponse));

		}
		catch(Exception e)
		{
			Logger.write("     Detail        : " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		finally
        {
            sXMLRequest = null;
        }
		return sResponse;

	}	
	public String actualizaEstatus(final String dn, final String serviceOfferingId,final String estatusBandera) throws ServiceException {

		String url = ResourceAccess.getParametroFromXML("AirAdapter");			 	
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : actualizaEstatus");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     serviceOfferingId      : " + serviceOfferingId);
		Logger.write("     estatusBandera         : " + estatusBandera);

		StringBuilder sXMLRequest = new StringBuilder();				
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		OracleProcedures oracleProcedures = new OracleProcedures();
		String sResponse = "";
		String contrasena = "WhPTc9qdgF4us3Lw";
		String user = "USRAPPSPROD";		
		try {
			contrasena = oracleProcedures.getValorParametro(158);
		} catch (Exception e) {
			contrasena = "WhPTc9qdgF4us3Lw";
		}
		
		try {
			user = oracleProcedures.getValorParametro(159);
		} catch (Exception e) {
			user = "USRAPPSPROD";
		}
		
		try
		{								  
			sXMLRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:air=\"http://air.prov.iusacell.com.mx\" xmlns:xsd=\"http://response.common.air.prov.iusacell.com.mx/xsd\" xmlns:xsd1=\"http://common.air.prov.iusacell.com.mx/xsd\">");
			sXMLRequest.append("<soap:Header/>");
			sXMLRequest.append("<soap:Body>");
			sXMLRequest.append("<air:updateSubscriberSegmentation>");
			sXMLRequest.append("<air:subscriberNumberNAI xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("<air:subscriberNumber>"+dn+"</air:subscriberNumber>");
			sXMLRequest.append("<air:originOperatorID xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("<air:accountGroupID xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			//<!--Zero or more repetitions:-->
			sXMLRequest.append("<air:serviceOfferings>");
			sXMLRequest.append("<xsd:serviceOfferingActiveFlag>"+estatusBandera+"</xsd:serviceOfferingActiveFlag>");
			sXMLRequest.append("<xsd:serviceOfferingID>"+serviceOfferingId+"</xsd:serviceOfferingID>");
			sXMLRequest.append("</air:serviceOfferings>");
			sXMLRequest.append("<air:securityRequest>");
			sXMLRequest.append("<xsd1:password>"+contrasena+"</xsd1:password>");
			sXMLRequest.append("<xsd1:ussername>"+user+"</xsd1:ussername>");
			sXMLRequest.append("</air:securityRequest>");
			sXMLRequest.append("</air:updateSubscriberSegmentation>");
			sXMLRequest.append("</soap:Body>");
			sXMLRequest.append("</soap:Envelope>");
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));

			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : "+ Utilerias.pintaLogRequest(sResponse));

		}
		catch(Exception e)
		{
			Logger.write("     Detail        : " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		finally
        {
            sXMLRequest = null;
        }
		return sResponse;

	}

}
