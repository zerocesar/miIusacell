package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Constantes;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceConsultaServiciosXUsuETAK {
	public String consultaServiciosXUsuETAK(final String sDN, final String idOperador)  throws ServiceException
	{
		String url = ResourceAccess.getParametroFromXML("urlObtieneServiciosETAK");
		Logger.write("   >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : callServiceObtieneServiciosETAK");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     sDN                    : " + sDN);
		Logger.write("     idOperador             : " + idOperador);

		StringBuffer sXMLRequest = new StringBuffer();
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";

		try
		{			
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.integracion.telco.iusacell.com.mx\" xmlns:head=\"http://www.example.org/Header\" xmlns:xsd=\"http://vo.integracion.telco.iusacell.com.mx/xsd\" xmlns:xsd1=\"http://comun.telco.iusacell.com.mx/xsd\">");
		    sXMLRequest.append(   "<soapenv:Header/>");
		    sXMLRequest.append(   "<soapenv:Body>");
		    sXMLRequest.append(      "<ws:obtieneServicios>\n");
		    sXMLRequest.append(         "<ws:Header>\n");
		    sXMLRequest.append(            "<head:paisID>"+Constantes.ALTA_HEADER_PAIS_ID+"</head:paisID>");
		    sXMLRequest.append(            "<head:canalID>"+Constantes.ALTA_HEADER_CANAL_ID+"</head:canalID>");
		    sXMLRequest.append(            "<head:sucursalID>"+Constantes.ALTA_HEADER_SUCURSAL_ID+"</head:sucursalID>");
		    sXMLRequest.append(            "<head:sistemaID>"+Constantes.ALTA_HEADER_SISTEMA_ID+"</head:sistemaID>");
		    sXMLRequest.append(            "<head:servicioID>"+Constantes.ALTA_HEADER_SERVICIO_ID+"</head:servicioID>");
		    sXMLRequest.append(         "</ws:Header>\n");
		    sXMLRequest.append(         "<ws:obtieneServicios>\n");
		    sXMLRequest.append(            "<xsd:acceso>\n");
		    sXMLRequest.append(                "<xsd1:idTransaccion>"+Constantes.ALTA_TRANSACCION_ID+"</xsd1:idTransaccion>");
		    sXMLRequest.append(                "<xsd1:idUserSaeo>"+Constantes.ALTA_USERSAEO_ID+"</xsd1:idUserSaeo>");
		    sXMLRequest.append(                "<xsd1:origen>"+Constantes.ALTA_ORIGEN+"</xsd1:origen>");
		    sXMLRequest.append(            "</xsd:acceso>\n");
		    sXMLRequest.append(            "<xsd:linea>");
		    sXMLRequest.append(               "<xsd1:MIN xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
		    sXMLRequest.append(               "<xsd1:dn>"+sDN+"</xsd1:dn>");
		    sXMLRequest.append(               "<xsd1:idOperador>"+idOperador+"</xsd1:idOperador>");
		    sXMLRequest.append(            "</xsd:linea>");
		    sXMLRequest.append(         "</ws:obtieneServicios>\n");
		    sXMLRequest.append(      "</ws:obtieneServicios>\n");
		    sXMLRequest.append(   "</soapenv:Body>\n");
		    sXMLRequest.append("</soapenv:Envelope>\n");
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));
			
			//********************************
			//Enviamos la peticion al servidor
			//********************************
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());

			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			sResponse = "";
			throw new ServiceException(e.getMessage());
		}
		finally
		{
		    sXMLRequest = null;
		}
		
		return sResponse;
	}
}
