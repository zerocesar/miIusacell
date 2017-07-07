package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.security.GeneraToken;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceGetServiciosAdicionales {
SOAPManager soapManager = null;
	
	public String getServiciosAdicionales(final int version,final String  idPlan, final String idElephant) throws ServiceException
	{
		String token = "";
		String url = ResourceAccess.getParametroFromXML("urlDescripcionPlanes");
		String sResponse = "";
		
		token = GeneraToken.generaToken("");
		
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceGetServiciosAdicionales");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     version                : " + version);
		Logger.write("     idPlan                 : " + idPlan); 

		StringBuffer SOAPRequestXML= new StringBuffer();
		
        SOAPRequestXML.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.ofertacomercial.iusacell.com\">");
        SOAPRequestXML.append(" <soapenv:Header/>");
        SOAPRequestXML.append("     <soapenv:Body>");
        SOAPRequestXML.append("         <ser:getServiciosOpc>");
        SOAPRequestXML.append("         <ser:in0>401043</ser:in0>");
        SOAPRequestXML.append("         <ser:in1>10.10.10.10</ser:in1>");
        SOAPRequestXML.append("         <ser:in2>OF32t4COm32ci4lS32Vic3=-</ser:in2>");
        SOAPRequestXML.append("         <ser:in3>OF32t4COm32ci4lS32Vic3=-</ser:in3>");
        SOAPRequestXML.append("         <ser:in4>"+token+"</ser:in4>");
        SOAPRequestXML.append("         <ser:in5>"+version+"</ser:in5>");
        SOAPRequestXML.append("         <ser:in6>"+idElephant+"</ser:in6>");
        SOAPRequestXML.append("         <ser:in7>"+idPlan+"</ser:in7>");
        SOAPRequestXML.append("         </ser:getServiciosOpc>");
        SOAPRequestXML.append("     </soapenv:Body>");
        SOAPRequestXML.append("</soapenv:Envelope>");
	
		Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(SOAPRequestXML.toString()));
		
		try 
		{
			SOAPManager oSOAPrequest = new SOAPManager();
			oSOAPrequest.createSOAPManager(url,new MensajeLogBean());
			sResponse = oSOAPrequest.sendRequestWithXML(SOAPRequestXML.toString(),new MensajeLogBean());

			Logger.write("   + Respuesta              + ");
			if(sResponse != null)
				Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}finally{
            SOAPRequestXML=null;
        }
		return sResponse;
	}

}
