package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceConsultasNum {
	public String getInfoOwner(final String dn) throws ServiceException {

		String url = ResourceAccess.getParametroFromXML("urlConsultasNum");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : getInfoOwner");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);	

		StringBuffer sXMLRequest = new StringBuffer();	
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";

		try
		{		    		
			sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://consultasnum.iusacell.com/\">" );
			sXMLRequest.append(   "<soapenv:Header/>");
			sXMLRequest.append(   "<soapenv:Body>");
			sXMLRequest.append(      "<con:getInfoOwner>");
			sXMLRequest.append(         "<mdn>"+dn+"</mdn>");            
			sXMLRequest.append(      "</con:getInfoOwner>");
			sXMLRequest.append(   "</soapenv:Body>");
			sXMLRequest.append("</soapenv:Envelope>");

			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));

			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());

			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			Logger.write("     Detail        : (Exception) " + e.getMessage());
			throw new ServiceException("Ocurrio un Error");
		}finally{
			sXMLRequest=null;
		}
		return sResponse;
	}

	public String getNumberByDN(final String dn) throws ServiceException {

		String url = ResourceAccess.getParametroFromXML("urlConsultasNum");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : getNumberByDN");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);	

		StringBuffer sXMLRequest = new StringBuffer();	
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";

		try
		{		    		
			sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://consultasnum.iusacell.com/\">" );
			sXMLRequest.append(   "<soapenv:Header/>");
			sXMLRequest.append(   "<soapenv:Body>");
			sXMLRequest.append(      "<con:getNumberByMDN>");
			sXMLRequest.append(         "<mdn>"+dn+"</mdn>");            
			sXMLRequest.append(      "</con:getNumberByMDN>");
			sXMLRequest.append(   "</soapenv:Body>");
			sXMLRequest.append("</soapenv:Envelope>");

			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));

			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());

			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			Logger.write("     Detail        : (Exception) " + e.getMessage());
			throw new ServiceException("Ocurrio un Error");
		}finally{
			sXMLRequest=null;
		}
		return sResponse;
	}
	
	public String getUfmiByMdn(final String dn) throws ServiceException {

		String url = ResourceAccess.getParametroFromXML("urlConsultasNum");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : getUfmiByMdn");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);	

		StringBuffer sXMLRequest = new StringBuffer();	
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";

		try
		{		    		
			sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://consultasnum.iusacell.com/\">" );
			sXMLRequest.append(   "<soapenv:Header/>");
			sXMLRequest.append(   "<soapenv:Body>");
			sXMLRequest.append(      "<con:getUfmiByMdn>");
			sXMLRequest.append(         "<mdn>"+dn+"</mdn>");            
			sXMLRequest.append(      "</con:getUfmiByMdn>");
			sXMLRequest.append(   "</soapenv:Body>");
			sXMLRequest.append("</soapenv:Envelope>");

			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));

			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());

			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			Logger.write("     Detail        : (Exception) " + e.getMessage());
			throw new ServiceException("Ocurrio un Error");
		}finally{
			sXMLRequest=null;
		}
		return sResponse;
	}
}
