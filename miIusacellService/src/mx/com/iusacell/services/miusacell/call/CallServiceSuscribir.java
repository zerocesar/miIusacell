package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceSuscribir {
	
	public String callSuscribir(final String msisdn, final String user, final String loginPa) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		String cadena="";
		String paisID="";
		String canalID="";
		String sucursalID="";
		String sistemaID="";
		String servicioID="";
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse="";
		String url = ResourceAccess.getParametroFromXML("urlSuscribir");
		
		try{
		   cadena = oracle.getValorParametro(55);
		   if(cadena !=null && !cadena.equals(""))
		   {
			   String[] parametrosSplit = cadena.split("\\|");
			   url = parametrosSplit[0];
			   paisID = parametrosSplit[1];
			   canalID = parametrosSplit[2];
			   sucursalID = parametrosSplit[3];
			   sistemaID = parametrosSplit[4];
			   servicioID = parametrosSplit[5];
		   }
		}catch (Exception e) {
			Logger.write("Ocurrio un detail al consultar en BD metodo ::  MdnPrepago");
		}
		
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : MdnPrepago");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     msisdn                 : " + msisdn);
		Logger.write("     user                   : " + user);
		Logger.write("     password               : " + loginPa);
		
		try {
			
            sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://bus.core.iusacell.com/ws\" xmlns:head=\"http://www.example.org/Header\">");
            sXMLRequest.append("<soapenv:Header/>");
            sXMLRequest.append("    <soapenv:Body>");
            sXMLRequest.append("        <ws:querySubscriber>");
            sXMLRequest.append("            <ws:Header>");
            sXMLRequest.append("                <head:paisID>"+paisID+"</head:paisID>");
            sXMLRequest.append("                <head:canalID>"+canalID+"</head:canalID>");
            sXMLRequest.append("                <head:sucursalID>"+sucursalID+"</head:sucursalID>");
            sXMLRequest.append("                <head:sistemaID>"+sistemaID+"</head:sistemaID>");
            sXMLRequest.append("                <head:servicioID>"+servicioID+"</head:servicioID>");
            sXMLRequest.append("            </ws:Header>");
            sXMLRequest.append("            <ws:msisdn>"+msisdn+"</ws:msisdn>");
            sXMLRequest.append("            <ws:user>"+user+"</ws:user>");
            sXMLRequest.append("            <ws:password>"+loginPa+"</ws:password>");
            sXMLRequest.append("        </ws:querySubscriber>");
            sXMLRequest.append("    </soapenv:Body>");
            sXMLRequest.append("</soapenv:Envelope>");
			
			Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(sXMLRequest.toString()));
			SOAPManager soapManager = new SOAPManager();
			soapManager.createSOAPManager(url,new MensajeLogBean());
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}finally{
            sXMLRequest=null;
        }
        return sResponse;
	}
}