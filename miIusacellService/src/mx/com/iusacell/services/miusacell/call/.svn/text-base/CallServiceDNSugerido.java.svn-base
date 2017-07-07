package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Constantes;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceDNSugerido {
	
	public String DNSugerido (final String dn, final String cantidad, final String localidad, final String mdnuser, final String mdnpass,final String Mo) throws ServiceException 
	{
	    StringBuffer sXMLRequest = new StringBuffer();
		String sResponse="";
		String url = ResourceAccess.getParametroFromXML("urlsDNSugerido");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : DNSugerido");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     Cantidad               : " + cantidad);
		Logger.write("     Localidad              : " + localidad);
		Logger.write("     mdnuser                : " + mdnuser);
		Logger.write("     mdnpass                : " + mdnpass);
		Logger.write("     Mo                     : " + Mo);
        
		try {
			if(dn == null || dn.equals("")){
                sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.cambiodn.telco.iusacell.com.mx\" xmlns:head=\"http://www.example.org/Header\" xmlns:xsd=\"http://vo.bl.telco.iusacell.com.mx/xsd\" xmlns:xsd1=\"http://vo.telco.com.mx/xsd\">");
                sXMLRequest.append(   "<soapenv:Header/>");
                sXMLRequest.append(   "<soapenv:Body>");
                sXMLRequest.append(      "<ser:obtenMDN>");
                sXMLRequest.append(         "<ser:Header>");
                sXMLRequest.append(            "<head:paisID>1</head:paisID>");
                sXMLRequest.append(            "<head:canalID>1</head:canalID>");
                sXMLRequest.append(            "<head:sucursalID>1</head:sucursalID>");
                sXMLRequest.append(            "<head:sistemaID>1</head:sistemaID>");
                sXMLRequest.append(            "<head:servicioID>1</head:servicioID>");
                sXMLRequest.append(         "</ser:Header>");
                sXMLRequest.append(         "<ser:obtenMDNRequest>");
                sXMLRequest.append(            "<xsd:acceso>");
                sXMLRequest.append(               "<xsd1:idTransaccion>"+Constantes.ACCESO_TRANSACCION_ID+"</xsd1:idTransaccion>");
                sXMLRequest.append(               "<xsd1:idUserSaeo>"+Constantes.ACCESO_USERSAEO_ID+"</xsd1:idUserSaeo>");
                sXMLRequest.append(               "<xsd1:origen>"+Constantes.ACCESO_ORIGEN+"</xsd1:origen>");
                sXMLRequest.append(            "</xsd:acceso>");
                sXMLRequest.append(            "<xsd:cantidad>"+cantidad+"</xsd:cantidad>");
                sXMLRequest.append(            "<xsd:localidad>"+localidad+"</xsd:localidad>");
                sXMLRequest.append(            "<xsd:pwdETAK>"+mdnpass+"</xsd:pwdETAK>");
                sXMLRequest.append(            "<xsd:userETAK>"+mdnuser+"</xsd:userETAK>");
                sXMLRequest.append(            "<xsd:vmoETAK>"+Mo+"</xsd:vmoETAK>");
                sXMLRequest.append(         "</ser:obtenMDNRequest>");
                sXMLRequest.append(      "</ser:obtenMDN>");
                sXMLRequest.append(   "</soapenv:Body>");
                sXMLRequest.append("</soapenv:Envelope>");
			}else if(localidad == null || !localidad.equals("")){
                sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.cambiodn.telco.iusacell.com.mx\" xmlns:head=\"http://www.example.org/Header\" xmlns:xsd=\"http://vo.bl.telco.iusacell.com.mx/xsd\" xmlns:xsd1=\"http://vo.telco.com.mx/xsd\">");
                sXMLRequest.append(   "<soapenv:Header/>");
                sXMLRequest.append(   "<soapenv:Body>");
                sXMLRequest.append(      "<ser:obtenMDN>");
                sXMLRequest.append(         "<ser:Header>");
                sXMLRequest.append(            "<head:paisID>1</head:paisID>");
                sXMLRequest.append(            "<head:canalID>1</head:canalID>");
                sXMLRequest.append(            "<head:sucursalID>1</head:sucursalID>");
                sXMLRequest.append(            "<head:sistemaID>1</head:sistemaID>");
                sXMLRequest.append(            "<head:servicioID>1</head:servicioID>");
                sXMLRequest.append(         "</ser:Header>");
                sXMLRequest.append(         "<ser:obtenMDNRequest>");
                sXMLRequest.append(            "<xsd:acceso>");
                sXMLRequest.append(               "<xsd1:idTransaccion>"+Constantes.ACCESO_TRANSACCION_ID+"</xsd1:idTransaccion>");
                sXMLRequest.append(               "<xsd1:idUserSaeo>"+Constantes.ACCESO_USERSAEO_ID+"</xsd1:idUserSaeo>");
                sXMLRequest.append(               "<xsd1:origen>"+Constantes.ACCESO_ORIGEN+"</xsd1:origen>");
                sXMLRequest.append(            "</xsd:acceso>");
                sXMLRequest.append(            "<xsd:cantidad>"+cantidad+"</xsd:cantidad>");
                sXMLRequest.append(            "<xsd:mdn>"+dn+"</xsd:mdn>");
                sXMLRequest.append(            "<xsd:pwdETAK>"+mdnpass+"</xsd:pwdETAK>");
                sXMLRequest.append(            "<xsd:userETAK>"+mdnuser+"</xsd:userETAK>"); 
                sXMLRequest.append(            "<xsd:vmoETAK>"+Mo+"</xsd:vmoETAK>");
                sXMLRequest.append(         "</ser:obtenMDNRequest>");
                sXMLRequest.append(      "</ser:obtenMDN>");
                sXMLRequest.append(   "</soapenv:Body>");
                sXMLRequest.append("</soapenv:Envelope>");
			}
			
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
