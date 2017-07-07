package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Constantes;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceCompraServicioETAK {

	public String  compraServicioETAK(final String dn, final int idOperador, final String costo, final String id, final int idHistorico, final int vigencia, final String monto) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlAltaServiciosETAK");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : callServiceAltaServiciosETAK");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     idOperador             : " + idOperador);
		Logger.write("     costo                  : " + costo);
		Logger.write("     id                     : " + id);
		Logger.write("     idHistorico            : " + idHistorico);
		Logger.write("     vigencia               : " + vigencia);
		Logger.write("     monto                  : " + monto);
		
		StringBuffer sXMLRequest = new StringBuffer();
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";

		try
		{
			
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ws='http://ws.altaservicios.telco.iusacell.com' xmlns:head='http://www.example.org/Header' xmlns:xsd='http://vo.altaservicios.telco.iusacell.com/xsd' xmlns:xsd1='http://vo.telco.com.mx/xsd'>");
		    sXMLRequest.append(    "<soapenv:Header/>");
		    sXMLRequest.append(    "<soapenv:Body>");
		    sXMLRequest.append(        "<ws:altaServicios>");
		    sXMLRequest.append(            "<ws:Header>");
		    sXMLRequest.append(                "<head:paisID>"+Constantes.ALTA_HEADER_PAIS_ID+"</head:paisID>");
		    sXMLRequest.append(                "<head:canalID>"+Constantes.ALTA_HEADER_CANAL_ID+"</head:canalID>");
		    sXMLRequest.append(                "<head:sucursalID>"+Constantes.ALTA_HEADER_SUCURSAL_ID+"</head:sucursalID>");
		    sXMLRequest.append(                "<head:sistemaID>"+Constantes.ALTA_HEADER_SISTEMA_ID+"</head:sistemaID>");
		    sXMLRequest.append(                "<head:servicioID>"+Constantes.ALTA_HEADER_SERVICIO_ID+"</head:servicioID>");
		    sXMLRequest.append(            "</ws:Header>");
		    sXMLRequest.append(            "<ws:input>");
		    sXMLRequest.append(                "<xsd:acceso>");
		    sXMLRequest.append(                    "<xsd1:idTransaccion>"+Constantes.ALTA_TRANSACCION_ID+"</xsd1:idTransaccion>");
		    sXMLRequest.append(                    "<xsd1:idUserSaeo>"+Constantes.ALTA_USERSAEO_ID+"</xsd1:idUserSaeo>");
		    sXMLRequest.append(                    "<xsd1:origen>"+Constantes.ALTA_ORIGEN+"</xsd1:origen>");
		    sXMLRequest.append(                "</xsd:acceso>");
		    sXMLRequest.append(                "<xsd:linea>");
		    sXMLRequest.append(                    "<xsd2:dn xmlns:xsd2=\"http://comun.telco.iusacell.com.mx/xsd\">"+dn+"</xsd2:dn>");
		    sXMLRequest.append(                    "<xsd2:idOperador xmlns:xsd2=\"http://comun.telco.iusacell.com.mx/xsd\">"+idOperador+"</xsd2:idOperador>");
		    sXMLRequest.append(                    "<xsd2:servicios xmlns:xsd2=\"http://comun.telco.iusacell.com.mx/xsd\">");
		    sXMLRequest.append(                    "<xsd2:activacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
		    sXMLRequest.append(                    "<xsd2:basico xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
		    sXMLRequest.append(                    "<xsd2:costo>"+costo+"</xsd2:costo>");
		    sXMLRequest.append(                    "<xsd2:id>"+id+"</xsd2:id>");
		    sXMLRequest.append(                    "<xsd2:idHistorico>"+idHistorico+"</xsd2:idHistorico>");
		    sXMLRequest.append(                    "<xsd2:monto>"+monto+"</xsd2:monto>");
		    sXMLRequest.append(                    "<xsd2:vigencia>"+vigencia+"</xsd2:vigencia>");
		    sXMLRequest.append(                    "</xsd2:servicios>");
		    sXMLRequest.append(                "</xsd:linea>");
		    sXMLRequest.append(            "</ws:input>");
		    sXMLRequest.append(        "</ws:altaServicios>");
		    sXMLRequest.append(    "</soapenv:Body>");
		    sXMLRequest.append("</soapenv:Envelope>");
			
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
			Logger.write("     Detail        : (Exception) " + e.getMessage());
			throw new ServiceException("Ocurrio un Error");
		}
		finally
		{
		    sXMLRequest = null;
		}
		
		return sResponse;
	}
}
