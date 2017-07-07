package mx.com.iusacell.services.miusacell.call;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceAbonoTiempoAireLegacy {

	public String abonoTiempoAireLegacy(final String dn, final Double importe, final Long secuencia) throws ServiceException
	{	
		String url = ResourceAccess.getParametroFromXML("urlVentaTALEGACY");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : callServiceVentaTALEGACY");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     importe                : " + importe);
		Logger.write("     secuencia              : " + secuencia);

		StringBuffer sXMLRequest = new StringBuffer();

		SOAPManager soapManager = new SOAPManager();	
		soapManager.createSOAPManager(url,new MensajeLogBean());
		soapManager.sSoapAction = "\"http://bean.recargase.iusacell.com\""+"abonoDN";
		String sResponse = "";

		Calendar actual 		= GregorianCalendar.getInstance();
		SimpleDateFormat sdf	= new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSSZ");
		java.util.Date dateFin	= actual.getTime();
		String fechaFin			= sdf.format(dateFin);
		fechaFin 				= fechaFin.substring(0, 10)+"T"+fechaFin.substring(10, fechaFin.length()-2)+":"+fechaFin.substring(fechaFin.length()-2, fechaFin.length());

		Calendar menosMesAbono = GregorianCalendar.getInstance();
		menosMesAbono.add(Calendar.DATE, -60);
		java.util.Date dateInicio=menosMesAbono.getTime();
		String fechaInicio=sdf.format(dateInicio);
		fechaInicio = fechaInicio.substring(0, 10)+"T"+fechaInicio.substring(10, fechaInicio.length()-2)+":"+fechaInicio.substring(fechaInicio.length()-2, fechaInicio.length());

		try
		{
		    sXMLRequest.append("<v:Envelope xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:d=\"http://www.w3.org/2001/XMLSchema\" xmlns:c=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		    sXMLRequest.append(    "<v:Header/>");
		    sXMLRequest.append(    "<v:Body>");
		    sXMLRequest.append(        "<n0:abonoDN xmlns:n0=\"http://bean.recargase.iusacell.com\" id=\"o0\" c:root=\"1\">");
		    sXMLRequest.append(            "<mdn>"+dn+"</mdn>");
		    sXMLRequest.append(            "<monto>"+importe+"</monto>");
		    sXMLRequest.append(            "<secuencia>"+secuencia+"</secuencia>");
		    sXMLRequest.append(            "<negocio>022</negocio>");
		    sXMLRequest.append(            "<sucursal>00018</sucursal>");
		    sXMLRequest.append(            "<fechaLocal>"+fechaFin+"</fechaLocal>");
		    sXMLRequest.append(        "</n0:abonoDN>");
		    sXMLRequest.append(    "</v:Body>");
		    sXMLRequest.append("</v:Envelope>");

			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));

			//********************************
			//Enviamos la peticion al servidor
			//********************************
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());

			Logger.write("   + Respuesta              +");
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
