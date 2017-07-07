package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Constantes;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceReprogramacion {
	public String reprogramacion(final String user, final String pass, final String tecnologia, final String idOperador, final String t_msisdn, final String t_imsi) throws ServiceException
	{
	    StringBuffer sXMLRequest = new StringBuffer();
		String sResponse="";
		String url = ResourceAccess.getParametroFromXML("urlsServicReprogramacion");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : reprogramacion");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     user                   : -PROTEGIDO-");
		Logger.write("     pass                   : -PROTEGIDO-");
		Logger.write("     Tecnologia             : " + tecnologia);
		Logger.write("     IdOperador             : " + idOperador);
		Logger.write("     Msisdn                 : " + t_msisdn);
		Logger.write("     Imsi                   : " + t_imsi);

		try
		{
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://bus.core.iusacell.com/ws\" xmlns:head=\"http://www.example.org/Header\">");
            sXMLRequest.append("<soapenv:Header/>");
            sXMLRequest.append(   "<soapenv:Body>");
            sXMLRequest.append(      "<ws:reprogramacion>");
            sXMLRequest.append(         "<ws:Header>");
            sXMLRequest.append(            "<head:paisID>1</head:paisID>");
            sXMLRequest.append(            "<head:canalID>1</head:canalID>");
            sXMLRequest.append(            "<head:sucursalID>1</head:sucursalID>");
            sXMLRequest.append(            "<head:sistemaID>1</head:sistemaID>");
            sXMLRequest.append(            "<head:servicioID>1</head:servicioID>");
            sXMLRequest.append(         "</ws:Header>");
            sXMLRequest.append(         "<acceso>");
            sXMLRequest.append(         "<idTransaccion>"+Constantes.ACCESO_TRANSACCION_ID+"</idTransaccion>");
            sXMLRequest.append(         "<idUserSaeo>"+Constantes.ACCESO_USERSAEO_ID+"</idUserSaeo>");
            sXMLRequest.append(         "<origen>"+Constantes.ACCESO_ORIGEN+"</origen>");
            sXMLRequest.append(         "</acceso>");
            sXMLRequest.append(         "<user>"+user+"</user>");
            sXMLRequest.append(         "<pass>"+pass+"</pass>");
            sXMLRequest.append(         "<tecnologia>"+tecnologia+"</tecnologia>");
            sXMLRequest.append(         "<idOperador>"+idOperador+"</idOperador>");
            sXMLRequest.append(         "<t_msisdn>"+t_msisdn+"</t_msisdn>");
            sXMLRequest.append(         "<t_imsi>"+t_imsi+"</t_imsi>");
            sXMLRequest.append(      "</ws:reprogramacion>");
            sXMLRequest.append(   "</soapenv:Body>");
            sXMLRequest.append("</soapenv:Envelope>");
			
			Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(sXMLRequest.toString()));
			
			SOAPManager soapManager = new SOAPManager();
			soapManager.createSOAPManager(url,new MensajeLogBean());
			
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			throw new ServiceException("Al consultar la url- " + url + " - " + e.getMessage());
		}finally{
            sXMLRequest=null;
        }
		return sResponse;
	}
}
