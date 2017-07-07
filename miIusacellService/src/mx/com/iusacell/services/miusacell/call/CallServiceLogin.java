package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.security.GeneraToken;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceLogin {
	
	  public String callServiceLogin(final String numeroEmpleado, final int empresa, final String secureToken) throws ServiceException {
		    
		    String url = ResourceAccess.getParametroFromXML("urlUsuario");
			Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
			Logger.write("     EndPoint               : " + url);
			Logger.write("     Operacion              : callServiceLogin");
			Logger.write("     requesting             : " + new Date().toString());
			Logger.write("   + Parametros             + ");
			Logger.write("     NumeroEmpleado         : " + numeroEmpleado);
			Logger.write("     Empresa                : " + empresa);
			Logger.write("     secureToken            : " + secureToken);
		  
			StringBuffer sXMLRequest = new StringBuffer();
			String generaToken = "";
			SOAPManager soapManager = new SOAPManager();
			soapManager.createSOAPManager(url,new MensajeLogBean());
			String sResponse = "";
			
			try
			{
			    generaToken = GeneraToken.generaToken(numeroEmpleado);
				
                sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.distribuidor.iusacell.com.mx\">" );
                sXMLRequest.append(   "<soapenv:Header/>");
                sXMLRequest.append(   "<soapenv:Body>");
                sXMLRequest.append(      "<ser:iusacellUsuario>");
                sXMLRequest.append(         "<ser:in0>AGhAxzwOwKEbI12XQ1KNjQ=-</ser:in0>");
                sXMLRequest.append(         "<ser:in1>AGhAxzwOwKEbI12XQ1KNjQ=-</ser:in1>");
                sXMLRequest.append(         "<ser:in2>"+numeroEmpleado+"</ser:in2>");
                sXMLRequest.append(         "<ser:in3>"+empresa+"</ser:in3>");
                sXMLRequest.append(         "<ser:in4>0</ser:in4>");
                sXMLRequest.append(         "<ser:in5>"+secureToken+"</ser:in5>");
                sXMLRequest.append(      "<ser:in6>"+generaToken+"</ser:in6>");
                sXMLRequest.append(      "</ser:iusacellUsuario>");
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
