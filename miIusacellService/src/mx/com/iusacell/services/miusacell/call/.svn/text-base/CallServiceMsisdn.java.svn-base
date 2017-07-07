package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceMsisdn {
   
	public String msisdn(final String msisdn)  throws ServiceException
	{	
		String url = ResourceAccess.getParametroFromXML("urlsMsisdn");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : DealerIDByMsisdn");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     msisdn                 : " + msisdn);
		
		StringBuffer sXMLRequest = new StringBuffer();
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		
		try {
            sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://bus.core.iusacell.com/ws\" xmlns:head=\"http://www.example.org/Header\">");
            sXMLRequest.append(          "<soapenv:Header/>");
            sXMLRequest.append(          "<soapenv:Body>");
            sXMLRequest.append(             "<ws:queryDealerIDByMsisdn>");
            sXMLRequest.append(                  "<ws:Header>");
            sXMLRequest.append(                     "<head:paisID>1</head:paisID>");
            sXMLRequest.append(                     "<head:canalID>1</head:canalID>");
            sXMLRequest.append(                     "<head:sucursalID>1</head:sucursalID>");
            sXMLRequest.append(                     "<head:sistemaID>1</head:sistemaID>");
            sXMLRequest.append(                     "<head:servicioID>1</head:servicioID>");
            sXMLRequest.append(                  "</ws:Header>");
            sXMLRequest.append(                  "<ws:msisdn>"+ msisdn +"</ws:msisdn>");
            sXMLRequest.append(                  "<ws:user>1110000135</ws:user>");
            sXMLRequest.append(                  "<ws:password>16soiHEa</ws:password>");
            sXMLRequest.append(               "</ws:queryDealerIDByMsisdn>");
            sXMLRequest.append(            "</soapenv:Body>");
            sXMLRequest.append(         "</soapenv:Envelope>");
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));
			
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}finally{
            sXMLRequest=null;
        }
		return sResponse;
	}
}
