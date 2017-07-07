package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Constantes;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceBajaServicios {

	public String  bajaServiciosETAK(final String mdn, final int idOperador, final String  idServicio) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlBajaServiciosETAK");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : bajaServicios");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     mdn                    : " + mdn);
		Logger.write("     idOperador             : " + idOperador);
		Logger.write("     idServicio             : " + idServicio);
		
		StringBuffer sXMLRequest = new StringBuffer();
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		try
		{
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.telco.iusacell.com\" xmlns:head=\"http://www.example.org/Header\" xmlns:ser=\"http://service.telco.iusacell.com\" xmlns:xsd=\"http://vo.telco.iusacell.com/xsd\" xmlns:xsd1=\"http://vo.telco.com.mx/xsd\">");
		    sXMLRequest.append(   "<soapenv:Header/>");
		    sXMLRequest.append(   "<soapenv:Body>");
		    sXMLRequest.append(      "<ws:bajaServicios>");
		    sXMLRequest.append(         "<ws:Header>");
		    sXMLRequest.append(         "<head:paisID>"+Constantes.ALTA_HEADER_PAIS_ID+"</head:paisID>");
		    sXMLRequest.append(         "<head:canalID>"+Constantes.ALTA_HEADER_CANAL_ID+"</head:canalID>");
		    sXMLRequest.append(         "<head:sucursalID>"+Constantes.ALTA_HEADER_SUCURSAL_ID+"</head:sucursalID>");
		    sXMLRequest.append(         "<head:sistemaID>"+Constantes.ALTA_HEADER_SISTEMA_ID+"</head:sistemaID>");
		    sXMLRequest.append(         "<head:servicioID>"+Constantes.ALTA_HEADER_SERVICIO_ID+"</head:servicioID>");
		    sXMLRequest.append(         "</ws:Header>");
		    sXMLRequest.append(         "<ws:input>");
		    sXMLRequest.append(            "<xsd:acceso>");
		    sXMLRequest.append(             "<xsd1:idTransaccion>"+Constantes.ALTA_TRANSACCION_ID+"</xsd1:idTransaccion>");
		    sXMLRequest.append(             "<xsd1:idUserSaeo>"+Constantes.ALTA_USERSAEO_ID+"</xsd1:idUserSaeo>");
		    sXMLRequest.append(             "<xsd1:origen>"+Constantes.ALTA_ORIGEN+"</xsd1:origen>");
		    sXMLRequest.append(            "</xsd:acceso>");
		    sXMLRequest.append(            "<xsd:linea>");
		    sXMLRequest.append(               "<xsd2:dn xmlns:xsd2=\"http://comun.telco.iusacell.com.mx/xsd\">"+mdn+"</xsd2:dn>");
		    sXMLRequest.append(               "<xsd2:idOperador xmlns:xsd2=\"http://comun.telco.iusacell.com.mx/xsd\">"+idOperador+"</xsd2:idOperador>");
		    sXMLRequest.append(               "<xsd2:servicios xmlns:xsd2=\"http://comun.telco.iusacell.com.mx/xsd\">");
		    sXMLRequest.append(                  "<xsd2:id>"+idServicio+"</xsd2:id>");
		    sXMLRequest.append(               "</xsd2:servicios>");
		    sXMLRequest.append(            "</xsd:linea>");
		    sXMLRequest.append(         "</ws:input>");
		    sXMLRequest.append(      "</ws:bajaServicios>");
		    sXMLRequest.append(   "</soapenv:Body>");
		    sXMLRequest.append("</soapenv:Envelope>");
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));
			
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		finally
        {
            sXMLRequest = null;
        }
		
	  return sResponse;
	}
}
