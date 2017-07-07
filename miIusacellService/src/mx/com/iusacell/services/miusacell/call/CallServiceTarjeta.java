package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.repositorio.token.GenerationToken;
import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.vo.autorizador.ClienteTarjetaVO;
import mx.com.iusacell.services.miusacell.util.ParseXMLServices;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceTarjeta {
	
	public ClienteTarjetaVO getTarjetasPorDn (final String mdn) throws Exception 
	{
		String sXMLRequest = "";
		String sResponse="";		
		ClienteTarjetaVO responseGetClienteVo = null;
		String urlServiceTarjetas =  ResourceAccess.getParametroFromXML("urlRepoTarjetas");
		SOAPManager soapManager 		= new SOAPManager();
		soapManager.createSOAPManager(urlServiceTarjetas,new MensajeLogBean());
		  
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + urlServiceTarjetas);
		Logger.write("     Operacion              : getTarjetasPorDn");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");		
		Logger.write("     mdn                    : " + mdn);		
		final String token=GenerationToken.generaToken();
		try {
			
			sXMLRequest="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bean=\"http://bean.repositorio.iusacell.com/\">"+
					    "<soapenv:Header/>"+
					    "<soapenv:Body>"+
					    "   <bean:getClient>"+
					    "      <loginValidation>"+
					    "         <userName>3/rApsbapWPthTyp3HsiL0tluRa0YDOq</userName>"+
					    "         <password>idV02ExvUrJnk7cebOsv80qJLIUgjVLA</password>"+
					    "         <token>"+token+"</token>"+
					    "      </loginValidation>"+
					    "      <mdn>"+mdn+"</mdn>"+
					    "      <account></account>"+
					    "      <idService></idService>"+
					    "   </bean:getClient>"+
					    "</soapenv:Body>"+
					    "</soapenv:Envelope>";
			
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			responseGetClienteVo=ParseXMLServices.parseGetClienteTarjeta(sResponse);
	
   		    
		}catch(Exception e){
			sResponse = "";
			throw new ServiceException(e.getLocalizedMessage());
		}
		return responseGetClienteVo;
	}	
	
}
