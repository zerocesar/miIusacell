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
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.util.Utilerias;
import mx.com.iusacell.services.surtimiento.arquitectura.AesUtil;

public class CallServiceAbonoTiempoAireETAK {
	
	public String abonoTiempoAireETAK(final String dn, final Double importe,  final Long secuencia) throws ServiceException
	{
		String url = ResourceAccess.getParametroFromXML("urlVentaTAETAK");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceabonoTiempoAireETAK");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     importe                : " + importe);
		Logger.write("     secuencia              : " + secuencia);
		
		OracleProcedures oracle = new OracleProcedures();
		String concatena = "";
		String phraseToken = "";
		try{
			concatena = oracle.getValorParametro(8);
			phraseToken = oracle.getValorParametro(9);
			concatena = concatena.trim();
			phraseToken = phraseToken.trim();
		}catch (Exception e) {
			concatena = "pvc1svtaetws2etak3prod4";
			phraseToken = "pvc1svtaetws2etak3prod4";
		}
		
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse 	= "";
		SOAPManager soapManager 		= new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String token = concatena + dn;
		try{
			AesUtil cifrado = new AesUtil();
			token = cifrado.encrypt(phraseToken, token);
		}catch (Exception e) {
			throw new ServiceException("Ocurrio un error al encriptar");
		}
		
		Calendar actual 			= GregorianCalendar.getInstance();
		SimpleDateFormat sdf		= new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSSZ");
		java.util.Date dateFin		= actual.getTime();
		String fechaFin				= sdf.format(dateFin);
		fechaFin = fechaFin.substring(0, 10)+"T"+fechaFin.substring(10, fechaFin.length()-2)+":"+fechaFin.substring(fechaFin.length()-2, fechaFin.length());
		
		Calendar menosMesAbono = GregorianCalendar.getInstance();
		menosMesAbono.add(Calendar.DATE, -60);
		java.util.Date dateInicio = menosMesAbono.getTime();
		String fechaInicio=sdf.format(dateInicio);
		fechaInicio = fechaInicio.substring(0, 10)+"T"+fechaInicio.substring(10, fechaInicio.length()-2)+":"+fechaInicio.substring(fechaInicio.length()-2, fechaInicio.length());
		
		try
		{
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ws='http://ws.et.telco.iusacell.com.mx' xmlns:head='http://www.example.org/Header' xmlns:xsd='http://vo.bl.telco.iusacell.com.mx/xsd'>");
		    sXMLRequest.append(   "<soapenv:Header/>");
		    sXMLRequest.append(   "<soapenv:Body>");
		    sXMLRequest.append(      "<ws:abonoDN>");
		    sXMLRequest.append(         "<ws:Header>");
		    sXMLRequest.append(            "<head:paisID>1</head:paisID>");
		    sXMLRequest.append(            "<head:canalID>1</head:canalID>");
		    sXMLRequest.append(            "<head:sucursalID>V10018</head:sucursalID>");
		    sXMLRequest.append(            "<head:sistemaID>23</head:sistemaID>");
		    sXMLRequest.append(            "<head:servicioID>57</head:servicioID>");
		    sXMLRequest.append(         "</ws:Header>");
		    sXMLRequest.append(         "<ws:abonoDNVo>");
		    sXMLRequest.append(            "<xsd:contrasena>"+token+"</xsd:contrasena>");
		    sXMLRequest.append(            "<xsd:fechaLocal>"+fechaFin+"</xsd:fechaLocal>");
		    sXMLRequest.append(            "<xsd:mdn>"+dn+"</xsd:mdn>");
		    sXMLRequest.append(            "<xsd:monto>"+importe+"</xsd:monto>");
		    sXMLRequest.append(            "<xsd:negocio>022</xsd:negocio>");
		    sXMLRequest.append(            "<xsd:secuencia>"+secuencia+"</xsd:secuencia>");
		    sXMLRequest.append(            "<xsd:sucursal>00018</xsd:sucursal>");
		    sXMLRequest.append(         "</ws:abonoDNVo>");
		    sXMLRequest.append(      "</ws:abonoDN>");
		    sXMLRequest.append(   "</soapenv:Body>");
		    sXMLRequest.append("</soapenv:Envelope>");
			
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
