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
import mx.com.iusacell.services.miiusacell.masivo.security.GeneraToken;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceAltaCita {
	
	public String altaCita(final String user, final String pass, final int cveHora, final String fecha, final String tienda, final String suscriptor, final String nombre, final String apPaterno, final String apMaterno, final String correo, final String comentario, final int tipoAtencion, final int cveGenerdor, final int empresa, final String tokenSeguridad)  throws ServiceException
	{	
		String url = ResourceAccess.getParametroFromXML("urlControlCitasService");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : callServiceControlCitas");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     user                   : " + user);
		Logger.write("     pass                   : " + pass);
		Logger.write("     cveHora                : " + cveHora);
		Logger.write("     fecha                  : " + fecha);
		Logger.write("     tienda                 : " + tienda);
		Logger.write("     suscriptor             : " + suscriptor);
		Logger.write("     nombre                 : " + nombre);
		Logger.write("     apPaterno              : " + apPaterno);
		Logger.write("     apMaterno              : " + apMaterno);
		Logger.write("     correo                 : " + correo);
		Logger.write("     comentario             : " + comentario);
		Logger.write("     tipoAtencion           : " + tipoAtencion);
		Logger.write("     cveGenerdor            : " + cveGenerdor);
		Logger.write("     empresa                : " + empresa);
		Logger.write("     tokenSeguridad         : " + tokenSeguridad);
		
		OracleProcedures oracle = new OracleProcedures();
		StringBuffer sXMLRequest = new StringBuffer();
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		Calendar actual = GregorianCalendar.getInstance();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSSZ");
		java.util.Date dateFin=actual.getTime();
		String fechaFin=sdf.format(dateFin);
		fechaFin = fechaFin.substring(0, 10)+"T"+fechaFin.substring(10, fechaFin.length()-2)+":"+fechaFin.substring(fechaFin.length()-2, fechaFin.length());
		
		Calendar menosMesAbono = GregorianCalendar.getInstance();
		menosMesAbono.add(Calendar.DATE, -60);
		java.util.Date dateInicio=menosMesAbono.getTime();
		String fechaInicio=sdf.format(dateInicio);
		fechaInicio = fechaInicio.substring(0, 10)+"T"+fechaInicio.substring(10, fechaInicio.length()-2)+":"+fechaInicio.substring(fechaInicio.length()-2, fechaInicio.length());
		String activa =	oracle.getValorParametro(77);
		
		try
		{
			if (activa.equals("1")){ 
			    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://services.citas.iusacell.com.mx'>");
			    sXMLRequest.append(    "<soapenv:Header/>");
			    sXMLRequest.append(    "<soapenv:Body>");
			    sXMLRequest.append(        "<ser:altaCita>");
			    sXMLRequest.append(            "<ser:in0>"+user+"</ser:in0>");
			    sXMLRequest.append(            "<ser:in1>"+pass+"</ser:in1>");
			    sXMLRequest.append(            "<ser:in2>"+cveHora+"</ser:in2>");
			    sXMLRequest.append(            "<ser:in3>"+fecha+"</ser:in3>");
			    sXMLRequest.append(            "<ser:in4>"+tienda+"</ser:in4>");
			    sXMLRequest.append(            "<ser:in5>"+suscriptor+"</ser:in5>");
			    sXMLRequest.append(            "<ser:in6>"+nombre+"</ser:in6>");
			    sXMLRequest.append(            "<ser:in7>"+apPaterno+"</ser:in7>");
			    sXMLRequest.append(            "<ser:in8>"+apMaterno+"</ser:in8>");
			    sXMLRequest.append(            "<ser:in9>"+correo+"</ser:in9>");
			    sXMLRequest.append(            "<ser:in10>"+comentario+"</ser:in10>");
			    sXMLRequest.append(            "<ser:in11>"+tipoAtencion+"</ser:in11>");
			    sXMLRequest.append(            "<ser:in12>"+cveGenerdor+"</ser:in12>");
			    sXMLRequest.append(            "<ser:in13>"+empresa+"</ser:in13>");
			    sXMLRequest.append(            "<ser:in14>"+tokenSeguridad+"</ser:in14>");
			    sXMLRequest.append(        "</ser:altaCita>");
			    sXMLRequest.append(    "</soapenv:Body>");
			    sXMLRequest.append("</soapenv:Envelope>");
			}else{
			    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://services.citas.iusacell.com.mx'>");
			    sXMLRequest.append(    "<soapenv:Header/>");
			    sXMLRequest.append(    "<soapenv:Body>");
			    sXMLRequest.append(        "<ser:altaCita>");
			    sXMLRequest.append(            "<ser:in0>"+user+"</ser:in0>");
			    sXMLRequest.append(            "<ser:in1>"+pass+"</ser:in1>");
			    sXMLRequest.append(            "<ser:in2>"+cveHora+"</ser:in2>");
			    sXMLRequest.append(            "<ser:in3>"+fecha+"</ser:in3>");
			    sXMLRequest.append(            "<ser:in4>"+tienda+"</ser:in4>");
			    sXMLRequest.append(            "<ser:in5>"+suscriptor+"</ser:in5>");
			    sXMLRequest.append(            "<ser:in6>"+nombre+"</ser:in6>");
			    sXMLRequest.append(            "<ser:in7>"+apPaterno+"</ser:in7>");
			    sXMLRequest.append(            "<ser:in8>"+apMaterno+"</ser:in8>");
			    sXMLRequest.append(            "<ser:in9>"+correo+"</ser:in9>");
			    sXMLRequest.append(            "<ser:in10>"+comentario+"</ser:in10>");
			    sXMLRequest.append(            "<ser:in11>"+tipoAtencion+"</ser:in11>");
			    sXMLRequest.append(            "<ser:in12>"+cveGenerdor+"</ser:in12>");
			    sXMLRequest.append(            "<ser:in13>"+tokenSeguridad+"</ser:in13>");
			    sXMLRequest.append(        "</ser:altaCita>");
			    sXMLRequest.append(    "</soapenv:Body>");
			    sXMLRequest.append("</soapenv:Envelope>");
			}
			
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
	
	public String getHorarioDisponblesXCAE (final String user, final String pass, final String cae, final int motivo, final String tokenSeguridad) throws ServiceException{
		String url = ResourceAccess.getParametroFromXML("urlControlCitasService");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		MensajeLogBean mensajeLog = new MensajeLogBean("HorarioDisponibleXcae");
		StringBuffer sXMLRequest = new StringBuffer();
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,mensajeLog);
		String sResponse = "";
		
		Calendar actual = GregorianCalendar.getInstance();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSSZ");
		java.util.Date dateFin=actual.getTime();
		String fechaFin=sdf.format(dateFin);
		fechaFin = fechaFin.substring(0, 10)+"T"+fechaFin.substring(10, fechaFin.length()-2)+":"+fechaFin.substring(fechaFin.length()-2, fechaFin.length());
		
		Calendar menosMesAbono = GregorianCalendar.getInstance();
		menosMesAbono.add(Calendar.DATE, -60);
		java.util.Date dateInicio=menosMesAbono.getTime();
		String fechaInicio=sdf.format(dateInicio);
		fechaInicio = fechaInicio.substring(0, 10)+"T"+fechaInicio.substring(10, fechaInicio.length()-2)+":"+fechaInicio.substring(fechaInicio.length()-2, fechaInicio.length());
		
		try
		{
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.citas.iusacell.com.mx\">");
		    sXMLRequest.append(    "<soapenv:Header/>");
		    sXMLRequest.append(    "<soapenv:Body>");
		    sXMLRequest.append(            "<ser:getHorarioDisponblesXCAE>");
		    sXMLRequest.append(            "<ser:in0>"+user+"</ser:in0>");
		    sXMLRequest.append(            "<ser:in1>"+pass+"</ser:in1>");
		    sXMLRequest.append(            "<ser:in2>"+cae+"</ser:in2>");
		    sXMLRequest.append(            "<ser:in3>"+motivo+"</ser:in3>");
		    sXMLRequest.append(            "<ser:in4>"+tokenSeguridad+"</ser:in4>");
		    sXMLRequest.append(        "</ser:getHorarioDisponblesXCAE>");
		    sXMLRequest.append(    "</soapenv:Body>");
		    sXMLRequest.append("</soapenv:Envelope>");
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),mensajeLog);
        	Logger.write("   + Respuesta              +");
        	Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			sResponse = "";
			Logger.write("     Detail        : (Exception) " + e.getMessage());
			throw new ServiceException("Ocurrio un error");
		}
		finally
        {
            sXMLRequest = null;
        }
		
		return sResponse;
	}
	
	public String tiposDeAtencion(final String token) throws ServiceException
	{
		String url = ResourceAccess.getParametroFromXML("urlControlCitasService");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : tiposDeAtencion");
		Logger.write("     requesting             : " + new Date().toString());
		
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse="";
		
		try{
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.citas.iusacell.com.mx\">");
		    sXMLRequest.append(   "<soapenv:Header/>");
		    sXMLRequest.append(   "<soapenv:Body>");
		    sXMLRequest.append(      "<ser:getTiposAtencion>");
		    sXMLRequest.append(         "<ser:in0>O7k13t9t9TbMTd3[mJN]uj39</ser:in0>");
		    sXMLRequest.append(         "<ser:in1>O7k13t9t9TbMTd3[mJN]uj39</ser:in1>");
		    sXMLRequest.append(         "<ser:in2>"+token+"</ser:in2>");
		    sXMLRequest.append(      "</ser:getTiposAtencion>");
		    sXMLRequest.append(   "</soapenv:Body>");
		    sXMLRequest.append("</soapenv:Envelope>");
			
			Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(sXMLRequest.toString()));
			SOAPManager soapManager = new SOAPManager();
			soapManager.createSOAPManager(url,new MensajeLogBean());
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XML Respuesta          : " +  Utilerias.pintaLogRequest(sResponse));
			
		}
		catch (Exception e) {
			Logger.write(" Excepcion ocurrida " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		finally
        {
            sXMLRequest = null;
        }
		
        return sResponse;     		
	}
	
	public String getCitasPendientesXDN(final String dn) throws ServiceException
	{	
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse="";
		String token = "";
		
		String url = ResourceAccess.getParametroFromXML("urlControlCitasService");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : getCitasPendientesXDN");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     DN                     : " + dn);
		
		token = GeneraToken.generaToken(dn);
		
		try{
			
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.citas.iusacell.com.mx\">");
		    sXMLRequest.append(   "<soapenv:Header/>");
		    sXMLRequest.append(   "<soapenv:Body>");
		    sXMLRequest.append(      "<ser:getCitasPendientesXDN>");
		    sXMLRequest.append(         "<ser:in0>O7k13t9t9TbMTd3[mJN]uj39</ser:in0>");
		    sXMLRequest.append(         "<ser:in1>O7k13t9t9TbMTd3[mJN]uj39</ser:in1>");
		    sXMLRequest.append(         "<ser:in2>"+dn+"</ser:in2>");
		    sXMLRequest.append(         "<ser:in3>"+token+"</ser:in3>");
		    sXMLRequest.append(      "</ser:getCitasPendientesXDN>");
		    sXMLRequest.append(   "</soapenv:Body>");
		    sXMLRequest.append("</soapenv:Envelope>");
			    
			    Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(sXMLRequest.toString()));
				SOAPManager soapManager = new SOAPManager();
				soapManager.createSOAPManager(url,new MensajeLogBean());
				sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
				Logger.write("   + Respuesta              + ");
	   		    Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));
					
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		finally
        {
            sXMLRequest = null;
        }
		
		return sResponse;
	}
	
	public String callCancelaCitaUsuario(final String folio) throws ServiceException {

	 	String url = ResourceAccess.getParametroFromXML("urlControlCitasService");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : callCancelaCitaUsuario");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     folio                  : " + folio);
		
		StringBuffer sXMLRequest = new StringBuffer();
		String generaToken = "";
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		try
		{
		    generaToken = GeneraToken.generaToken(folio);
		    
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.citas.iusacell.com.mx\">");
		    sXMLRequest.append(    "<soapenv:Header/>");
		    sXMLRequest.append(    "<soapenv:Body>");
		    sXMLRequest.append(        "<ser:cancelaCitaUsuario>");
		    sXMLRequest.append(            "<ser:in0>O7k13t9t9TbMTd3[mJN]uj39</ser:in0>");
		    sXMLRequest.append(            "<ser:in1>O7k13t9t9TbMTd3[mJN]uj39</ser:in1>");
		    sXMLRequest.append(            "<ser:in2>"+folio+"</ser:in2>");
		    sXMLRequest.append(            "<ser:in3>"+generaToken+"</ser:in3>");
		    sXMLRequest.append(        "</ser:cancelaCitaUsuario>");
		    sXMLRequest.append(    "</soapenv:Body>");
		    sXMLRequest.append("</soapenv:Envelope>");
		    
		    Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));
		    
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
		    
		}
		catch(Exception e)
		{
			Logger.write("     Detail        : (Exception) " + e.getMessage());
			throw new ServiceException("Ocurrio un Error");
		}
		finally{
		    sXMLRequest = null;
		}
		return sResponse;
		
	}
	
	public String getHorariosDisponiblesCallCenter (final String fecha, final String in3, final String in4) throws ServiceException
	 {
			StringBuffer sXMLRequest = new StringBuffer();
			String sResponse="";
			String token = "";
			
			String url = ResourceAccess.getParametroFromXML("urlControlCitasService");
			Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
			Logger.write("     EndPoint               : " + url);
			Logger.write("     Operacion              : getCitasPendientesXDN");
			Logger.write("     requesting             : " + new Date().toString());
			Logger.write("   + Parametros             + ");
			Logger.write("     fecha                  : " + fecha);
			
			token = GeneraToken.generaToken("");
		 
			try
			{
			    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.citas.iusacell.com.mx\">");
			    sXMLRequest.append(   "<soapenv:Header/>");
			    sXMLRequest.append(   "<soapenv:Body>");
			    sXMLRequest.append(      "<ser:getHorariosDisponiblesCallCenter>");
			    sXMLRequest.append(         "<ser:in0>O7k13t9t9TbMTd3[mJN]uj39</ser:in0>");
			    sXMLRequest.append(         "<ser:in1>O7k13t9t9TbMTd3[mJN]uj39</ser:in1>");
			    sXMLRequest.append(         "<ser:in2>"+fecha+"</ser:in2>");
			    sXMLRequest.append(         "<ser:in3>"+in3+"</ser:in3>");
			    sXMLRequest.append(         "<ser:in4>"+in4+"</ser:in4>");
			    sXMLRequest.append(         "<ser:in5>"+token+"</ser:in5>");
			    sXMLRequest.append(      "</ser:getHorariosDisponiblesCallCenter>");
			    sXMLRequest.append(   "</soapenv:Body>");
			    sXMLRequest.append("</soapenv:Envelope>");
				    
				    Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(sXMLRequest.toString()));
					SOAPManager soapManager = new SOAPManager();
					soapManager.createSOAPManager(url,new MensajeLogBean());
					sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
					Logger.write("   + Respuesta              + ");
		   		    Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));
				
			}catch (Exception e) {
				throw new ServiceException(e.getMessage());
			}
			finally{
			    sXMLRequest = null;
			}
			
		 return sResponse;
	 }
}
