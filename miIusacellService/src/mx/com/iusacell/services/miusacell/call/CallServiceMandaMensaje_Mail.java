package mx.com.iusacell.services.miusacell.call;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Date;

import javax.crypto.NoSuchPaddingException;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import mx.com.iusacell.mapas.security.AlgoritmoAes;
import mx.com.iusacell.message.services.SvMessageHttpBindingStub;
import mx.com.iusacell.message.services.SvMessageLocator;
import mx.com.iusacell.message.vo.MessageMailVO;
import mx.com.iusacell.message.vo.RespuestaMensajeVO;
import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.masivo.security.GeneraToken;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.Utilerias;

import org.apache.axis.AxisFault;

public class CallServiceMandaMensaje_Mail {
	
	SOAPManager soapManager = null;
	
	public String enviaMail(final String  mailBody, final String mailSaludo, final String  mailSubject, final String mailTo, final int compania)  throws ServiceException
	{	
		String url = ResourceAccess.getParametroFromXML("urlE2EMessenger");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : enviaMail");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     mailBody               : " + mailBody);
		Logger.write("     mailSaludo             : " + mailSaludo);
		Logger.write("     mailSubject            : " + mailSubject);
		Logger.write("     mailTo                 : " + mailTo);
		Logger.write("     compania               : " + compania);
		
		StringBuffer sXMLRequest = new StringBuffer();
		soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		String token = GeneraToken.generaToken("");
		
		try
		{
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mes=\"http://messenger.services.iusacell.com.mx\" xmlnsvo=\"http://vo.messenger.services.iusacell.com.mx\">");
		    sXMLRequest.append("<soapenv:Header/>");
		    sXMLRequest.append(  "<soapenv:Body>");
		    sXMLRequest.append(     "<mes:enviaMail>");
		    sXMLRequest.append(          "<mes:in0>M3553ng3r53rv1c352to2=-</mes:in0>");
		    sXMLRequest.append(        "<mes:in1>M3553ng3r53rv1c352to2=-</mes:in1>");
		    sXMLRequest.append(        "<mes:in2>"+token+"</mes:in2>");
		    sXMLRequest.append(        "<mes:in3>");
		    sXMLRequest.append(           "<vo:mailBody>" + mailBody + "</vo:mailBody>");
		    sXMLRequest.append(           "<vo:mailCompania>" + compania + "</vo:mailCompania>");
		    sXMLRequest.append(           "<vo:mailImagenes>" );
		    sXMLRequest.append(             "<mes:string></mes:string>" );
		    sXMLRequest.append(         "</vo:mailImagenes>");
		    sXMLRequest.append(           "<vo:mailSaludo>" + mailSaludo + "</vo:mailSaludo>");
		    sXMLRequest.append(           "<vo:mailSubject>" + mailSubject + "</vo:mailSubject>");
		    sXMLRequest.append(           "<vo:mailTo>" + mailTo + "</vo:mailTo>");
		    sXMLRequest.append(        "</mes:in3>");
		    sXMLRequest.append(     "</mes:enviaMail>");
		    sXMLRequest.append(  "</soapenv:Body>");
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
			sResponse = "";
			throw new ServiceException("Al consultar la url: " + url + " :: " + e.getMessage());
		}
		finally{
		    sXMLRequest = null;
		}
		return sResponse;
	}
	
	public String enviaSMS(final String  smsTo, final String smsBody)  throws ServiceException
	{	
		String url = ResourceAccess.getParametroFromXML("urlE2EMessenger");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : enviaSMS");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     smsTo                  : " + smsTo);
		Logger.write("     smsBody                : " + smsBody);
		
		StringBuffer sXMLRequest = new StringBuffer();
		soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		String token = GeneraToken.generaToken("");
				
		try
		{
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mes=\"http://messenger.services.iusacell.com.mx\" xmlns:vo=\"http://vo.messenger.services.iusacell.com.mx\">");
		    sXMLRequest.append("<soapenv:Header/>");
		    sXMLRequest.append(  "<soapenv:Body>");
		    sXMLRequest.append(     "<mes:enviaSMS>");
		    sXMLRequest.append(          "<mes:in0>M3553ng3r53rv1c352to2=-</mes:in0>");
		    sXMLRequest.append(        "<mes:in1>M3553ng3r53rv1c352to2=-</mes:in1>");
		    sXMLRequest.append(        "<mes:in2>"+token+"</mes:in2>");
		    sXMLRequest.append(        "<mes:in3>");
		    sXMLRequest.append(           "<vo:smsBody>" + smsBody + "</vo:smsBody>");
		    sXMLRequest.append(           "<vo:smsTo>" + smsTo + "</vo:smsTo>");
		    sXMLRequest.append(        "</mes:in3>");
		    sXMLRequest.append(     "</mes:enviaSMS>");
		    sXMLRequest.append(  "</soapenv:Body>");
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
			sResponse = "";
			throw new ServiceException(e.getMessage());
		}
		finally
		{
		    sXMLRequest = null;
		}
		return sResponse;
	}
	
	public String enviaNuevoSMS(final String ip, final String DN, final String mailBody)  throws ServiceException
	{	
		String url = ResourceAccess.getParametroFromXML("urlE2EMessengerEnviaMail");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               :" + url);
		Logger.write("     Operacion              : enviaNuevoSMS");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     ip                     : " + ip);
		Logger.write("     smsTo               : " + DN);
		Logger.write("     mailBody               : " + mailBody);
		
		
		StringBuilder sXMLRequest = new StringBuilder();
		soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		OracleProcedures oracle = new OracleProcedures();
		String sResponse = "";
		String idApp = "";
		String llave = "";
		String sToken = "";
		String llaveAplicacion="";
		AlgoritmoAes encryptToken = new AlgoritmoAes();

		try {
			idApp = oracle.getValorParametro(149);
		} catch (Exception e) {
			idApp = "13";
		}
		
		try {
			llave = oracle.getValorParametro(160);
		} catch (Exception e) {
			llave = "i4j5-=fG76YWEeq$il=2ft1#J84Knj";
		}	
		
		String llaveEcrypt = "=/ihpqN24e15stHlmsm1!olvEm=*";
		byte[] KeyValue = llaveEcrypt.substring(0, 16).getBytes();
		
		try {
			sToken=encryptToken.encrypt(Utilerias.GetUTCdatetimeAsString(new Date()),KeyValue);		
			llaveAplicacion=encryptToken.encrypt(llave,KeyValue);
		} catch (Exception e) {
			throw new ServiceException("Al encriptar la llave de la aplicacion  :: " + e.getMessage());
		}
		try
		{
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.message.iusacell.com.mx\" xmlns:vo=\"http://vo.message.iusacell.com.mx\">");
		    sXMLRequest.append(   "<soapenv:Header/>");
		    sXMLRequest.append(   "<soapenv:Body>");
		    sXMLRequest.append(      "<ser:enviaSMS>");
		    sXMLRequest.append(         "<ser:in0>=/ihpqN24e15stHlmsm1!olvEm=*</ser:in0>");      //Usuario
		    sXMLRequest.append(         "<ser:in1>=/ihpqN24e15stHlmsm1!olvEm=*</ser:in1>");      //Contraseña
		    sXMLRequest.append(         "<ser:in2>"+idApp+"</ser:in2>");                         //Id Aplicacion  
		    sXMLRequest.append(         "<ser:in3>"+llaveAplicacion+"</ser:in3>");               //LLave de la Aplicacion
		    sXMLRequest.append(         "<ser:in4>"+ip+"</ser:in4>");                            //IP            
		    sXMLRequest.append(         "<ser:in5>");
		    sXMLRequest.append(             "<vo:smsBody>"+mailBody+"</vo:smsBody>");
		    sXMLRequest.append(            "<vo:smsTo>"+DN+"</vo:smsTo>");
		    sXMLRequest.append(         "</ser:in5>");
		    sXMLRequest.append(         "<ser:in6>"+sToken+"</ser:in6>");
		    sXMLRequest.append(      "</ser:enviaSMS>");
		    sXMLRequest.append(   "</soapenv:Body>");
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
			sResponse = "";
			throw new ServiceException("Al consultar la url: " + url + " :: " + e.getMessage());
		}
		finally
		{
		    sXMLRequest = null;
		}
		
		return sResponse;
	}
	
	
	
	public String sendMessage(final String  smsTo, final String smsBody, final String  mailBody, final String mailSaludo, final String  mailSubject, final String mailTo)  throws ServiceException
	{	
		String url = ResourceAccess.getParametroFromXML("urlE2EMessenger");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : enviaSMS");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     smsTo                  : " + smsTo);
		Logger.write("     smsBody                : " + smsBody);
		Logger.write("     mailBody               : " + mailBody);
		Logger.write("     mailSaludo             : " + mailSaludo);
		Logger.write("     mailSubject            : " + mailSubject);
		Logger.write("     mailTo                 : " + mailTo);
		
		StringBuffer sXMLRequest = new StringBuffer();
		soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		String token = GeneraToken.generaToken("");
				
		try
		{
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mes=\"http://messenger.services.iusacell.com.mx\" xmlns:vo=\"http://vo.messenger.services.iusacell.com.mx\">");
		    sXMLRequest.append("<soapenv:Header/>");
		    sXMLRequest.append(  "<soapenv:Body>");
		    sXMLRequest.append(     "<mes:sendMessage>");
		    sXMLRequest.append(          "<mes:in0>M3553ng3r53rv1c352to2=-</mes:in0>");
		    sXMLRequest.append(        "<mes:in1>M3553ng3r53rv1c352to2=-</mes:in1>");
		    sXMLRequest.append(        "<mes:in2>"+token+"</mes:in2>");
		    sXMLRequest.append(        "<mes:in3>");
		    sXMLRequest.append(           "<vo:mailBody>" + mailBody + "</vo:mailBody>");
		    sXMLRequest.append(           "<vo:mailSaludo>" + mailSaludo + "</vo:mailSaludo>");
		    sXMLRequest.append(           "<vo:mailSubject>" + mailSubject + "</vo:mailSubject>");
		    sXMLRequest.append(           "<vo:mailTo>" + mailTo + "</vo:mailTo>");
		    sXMLRequest.append(           "<vo:smsBody>" + smsBody + "</vo:smsBody>");
		    sXMLRequest.append(           "<vo:smsTo>" + smsTo + "</vo:smsTo>");
		    sXMLRequest.append(        "</mes:in3>");
		    sXMLRequest.append(     "</mes:sendMessage>");
		    sXMLRequest.append(  "</soapenv:Body>");
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
			sResponse = "";
			throw new ServiceException(e.getMessage());
		}
		finally
		{
		    
		}
		
		return sResponse;
	}

	public String sendMail(final String  mailBody, final String  mailSubject, final String mailTo,final String nameTo, final int compania, final String mailFrom,final String nameFrom, final String cSS, final String tipoEvento)  throws ServiceException
	{	
		String url = ResourceAccess.getParametroFromXML("urlE2EMessenger");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : sendMail");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     mailBody               : " + mailBody);
		Logger.write("     mailSubject            : " + mailSubject);
		Logger.write("     mailTo                 : " + mailTo);
		Logger.write("     nameTo                 : " + nameTo);
		Logger.write("     compania               : " + compania);
		Logger.write("     mailFrom               : " + mailFrom);
		Logger.write("     nameFrom               : " + nameFrom);
		
		
		StringBuffer sXMLRequest = new StringBuffer();
		soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		String sToken="";
		String codigoCSS="";
		StringBuffer codigoImagenes=new StringBuffer();
		
		AlgoritmoAes encryptToken= new AlgoritmoAes();
		String llave = "M3553ng3r53rv1c352to2=-";
		byte[] KeyValue = llave.substring(0, 16).getBytes();
		
		try {
			sToken=encryptToken.encrypt(Utilerias.GetUTCdatetimeAsString(new Date()),KeyValue);		
		
		} catch (NoSuchPaddingException e1) {
//			e1.printStackTrace();
			Logger.write("CallServiceMandaMensaje_Mail.sendMail (NoSuchPaddingException) :: " + e1.getMessage());
		} catch (Exception e1) {
//			e1.printStackTrace();
			Logger.write("CallServiceMandaMensaje_Mail.sendMail (Exception) :: " + e1.getMessage());
		}

		try
		{
			if (cSS.equals("")){
				codigoCSS="<vo:estilos xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>";
			}else {
				codigoCSS="<vo:estilos>"+cSS+"</vo:estilos>";
			}
			if(tipoEvento.equals("1")){
			    codigoImagenes.append("<mes:string>header.jpg</mes:string>");
			    codigoImagenes.append("<mes:string>ladDer.jpg</mes:string>");
			    codigoImagenes.append("<mes:string>ladIzq.jpg</mes:string>");
			    codigoImagenes.append("<mes:string>line.jpg</mes:string>");
			    codigoImagenes.append("<mes:string>footer.jpg</mes:string>");
				
				
			}else if (tipoEvento.equals("2")){
				codigoImagenes.append("<mes:string>cabezaFactura.jpg</mes:string><mes:string>rayitasombra.jpg</mes:string>");
			}
			
			sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mes=\"http://messenger.services.iusacell.com.mx\" xmlns:vo=\"http://vo.messenger.services.iusacell.com.mx\">");
			sXMLRequest.append(   "<soapenv:Header/>");
			sXMLRequest.append(   "<soapenv:Body>");
			sXMLRequest.append(      "<mes:sendMail>");
			sXMLRequest.append(         "<mes:in0>M3553ng3r53rv1c352to2=-</mes:in0>");
			sXMLRequest.append(         "<mes:in1>M3553ng3r53rv1c352to2=-</mes:in1>");
			sXMLRequest.append(         "<mes:in2>"+ codigoCSS );
			sXMLRequest.append(            "<vo:imagenes>"+ codigoImagenes.toString() +"</vo:imagenes>");
			sXMLRequest.append(            "<vo:mailBody>"+ mailBody +"</vo:mailBody>");
			sXMLRequest.append(            "<vo:mailCc>"+"</vo:mailCc>");
			sXMLRequest.append(            "<vo:mailCco>"+"</vo:mailCco>");
			sXMLRequest.append(            "<vo:mailFrom>");
			sXMLRequest.append(               "<vo:direccion>"+ mailFrom +"</vo:direccion>");
			sXMLRequest.append(               "<vo:nombre>"+ nameFrom +"</vo:nombre>");
			sXMLRequest.append(            "</vo:mailFrom>");
			sXMLRequest.append(            "<vo:mailSubject>"+ mailSubject +"</vo:mailSubject>");
			sXMLRequest.append(            "<vo:mailTo>");
			sXMLRequest.append(               "<vo:EmailVO>");
			sXMLRequest.append(                  "<vo:direccion>"+ mailTo +"</vo:direccion>");
			sXMLRequest.append(                  "<vo:nombre>" + nameTo+"</vo:nombre>");
			sXMLRequest.append(               "</vo:EmailVO>");
			sXMLRequest.append(            "</vo:mailTo>");
			sXMLRequest.append(            "<vo:replyTo>"+"</vo:replyTo>");
			sXMLRequest.append(         "</mes:in2>");
			sXMLRequest.append(         "<mes:in3>"+ sToken +"</mes:in3>");
			sXMLRequest.append(      "</mes:sendMail>");
			sXMLRequest.append(   "</soapenv:Body>");
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
			sResponse = "";
			throw new ServiceException("Al consultar la url: " + url + " :: " + e.getMessage());
		}
		finally{
		    sXMLRequest = null;
		}
		return sResponse;
	}
	
	public String enviaCorreo(final String ip, final String  mailSubject, final String mailFrom, final String Nombre, final String mailBody, final String mailTo, String CSS, final int compania, final String tipoEvento, final String nameFrom)  throws ServiceException
	{	
		String url = ResourceAccess.getParametroFromXML("urlE2EMessengerEnviaMail");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               :" + url);
		Logger.write("     Operacion              : enviaMail");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     ip                     : " + ip);
		Logger.write("     mailSaludo             : " + mailSubject);
		Logger.write("     mailFrom               : " + mailFrom);
		Logger.write("     nameFrom               : " + nameFrom);
		Logger.write("     Nombre                 : " + Nombre);
		Logger.write("     mailBody               : " + mailBody);
		Logger.write("     mailTo                 : " + mailTo);
		
		StringBuilder sXMLRequest = new StringBuilder();
		soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		OracleProcedures oracle = new OracleProcedures();
		String sResponse = "";
		String idApp = "";
		String llave = "";
		String sToken = "";
		String llaveAplicacion="";
		AlgoritmoAes encryptToken = new AlgoritmoAes();
		String logo="";
		String imgNotificaciones="";
		String encologo="";
		String encoimgNotificaciones="";
		String encoddivision1="";
		String ladIzq = "";
		String encoladIzq = "";
		String ladDer = "";
		String encoladDer = "";
		String linea  = "";
		String encolinea  = "";
			
		try {
			idApp = oracle.getValorParametro(149);
		} catch (Exception e) {
			idApp = "18";
		}
		
		try {
			llave = oracle.getValorParametro(150);
		} catch (Exception e) {
			llave = "-hMejjy0-YyaKJahXVjSIq=)J*Sb60";
		}	
		
		String llaveEcrypt = "=/ihpqN24e15stHlmsm1!olvEm=*";
		byte[] KeyValue = llaveEcrypt.substring(0, 16).getBytes();
		
		try {
			sToken=encryptToken.encrypt(Utilerias.GetUTCdatetimeAsString(new Date()),KeyValue);		
			llaveAplicacion=encryptToken.encrypt(llave,KeyValue);
		} catch (NoSuchPaddingException e1) {
//			e1.printStackTrace();
			Logger.write("CallServiceMandaMensaje_Mail.enviaCorreo (NoSuchPaddingException) :: " + e1.getMessage());
		} catch (Exception e1) {
//			e1.printStackTrace();
			Logger.write("CallServiceMandaMensaje_Mail.enviaCorreo (Exception) :: " + e1.getMessage());
		}
		
		if(compania == 1 && CSS.equals("")){
			logo = "logoIusacell.jpg";
			imgNotificaciones = "bannerIusacell.jpg";
			encologo = Utilerias.imagenBase64(logo);
			encoimgNotificaciones = Utilerias.imagenBase64(imgNotificaciones);
			encoddivision1 = Utilerias.imagenBase64("division1.jpg");
		}else if (compania == 2 && CSS.equals("")){
			logo = "logoUnefon.jpg";
			encologo = Utilerias.imagenBase64(logo);
			imgNotificaciones = "bannerUnefon.jpg";
			encoimgNotificaciones = Utilerias.imagenBase64(imgNotificaciones);
			encoddivision1 = Utilerias.imagenBase64("division1.jpg");
		}else  if(compania == 4 && CSS.equals("")){
			logo = "headerATT.jpg";
			encologo = Utilerias.imagenBase64(logo);
			imgNotificaciones = "footerATT.jpg";
			encoimgNotificaciones = Utilerias.imagenBase64(imgNotificaciones);
			linea = "lineATT.jpg";
			encolinea = Utilerias.imagenBase64(linea);
			ladIzq = "leftATT.jpg";
			encoladIzq = Utilerias.imagenBase64(ladIzq);
			ladDer = "rightATT.jpg";
			encoladDer = Utilerias.imagenBase64(ladDer);
		}
		
		if(!CSS.equals("")){
			CSS = "";
			if(compania == 4){
				logo = "headerATT.jpg";
				encologo = Utilerias.imagenBase64(logo);
				imgNotificaciones = "footerATT.jpg";
				encoimgNotificaciones = Utilerias.imagenBase64(imgNotificaciones);
				linea = "lineATT.jpg";
				encolinea = Utilerias.imagenBase64(linea);
				ladIzq = "leftATT.jpg";
				encoladIzq = Utilerias.imagenBase64(ladIzq);
				ladDer = "rightATT.jpg";
				encoladDer = Utilerias.imagenBase64(ladDer);
			}else if (compania == 1){
				
				if(tipoEvento.equals("1")){	
					logo = "header.jpg";
					encologo = Utilerias.imagenBase64(logo);
					imgNotificaciones = "footer.jpg";
					encoimgNotificaciones = Utilerias.imagenBase64(imgNotificaciones);
					linea = "line.jpg";
					encolinea = Utilerias.imagenBase64(linea);
					ladIzq = "ladIzq.jpg";
					encoladIzq = Utilerias.imagenBase64(ladIzq);
					ladDer = "ladDer.jpg";
					encoladDer = Utilerias.imagenBase64(ladDer);
				  }else if (tipoEvento.equals("2")){
		        	    logo = "cabezaFactura.jpg";
						encologo = Utilerias.imagenBase64(logo);
						imgNotificaciones = "rayitasombra.jpg";
						encoimgNotificaciones = Utilerias.imagenBase64(imgNotificaciones);
				  }
			}
		}
		
		try
		{
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.message.iusacell.com.mx\" xmlns:vo=\"http://vo.message.iusacell.com.mx\">");
		    sXMLRequest.append(   "<soapenv:Header/>");
		    sXMLRequest.append(   "<soapenv:Body>");
		    sXMLRequest.append(      "<ser:enviaMail>");
		    sXMLRequest.append(         "<ser:in0>=/ihpqN24e15stHlmsm1!olvEm=*</ser:in0>");      //Usuario
		    sXMLRequest.append(         "<ser:in1>=/ihpqN24e15stHlmsm1!olvEm=*</ser:in1>");      //Contraseña
		    sXMLRequest.append(         "<ser:in2>"+idApp+"</ser:in2>");                         //Id Aplicacion  
		    sXMLRequest.append(         "<ser:in3>"+llaveAplicacion+"</ser:in3>");               //LLave de la Aplicacion
		    sXMLRequest.append(         "<ser:in4>"+ip+"</ser:in4>");                            //IP            
		    sXMLRequest.append(         "<ser:in5>");
		    sXMLRequest.append(            "<vo:estilos>"+CSS+"</vo:estilos>");                  //Estilos
		    sXMLRequest.append(            "<vo:imagenes>");
		    sXMLRequest.append(               "<vo:Imagen64VO>");
		    sXMLRequest.append(                  "<vo:imagenBase64>"+encologo+"</vo:imagenBase64>");
		    sXMLRequest.append(                  "<vo:nombre>"+logo+"</vo:nombre>");
		    sXMLRequest.append(               "</vo:Imagen64VO>");
		    sXMLRequest.append(               "<vo:Imagen64VO>");
		    sXMLRequest.append(                  "<vo:imagenBase64>"+encoimgNotificaciones+"</vo:imagenBase64>");
		    sXMLRequest.append(                  "<vo:nombre>"+imgNotificaciones+"</vo:nombre>");
		    sXMLRequest.append(               "</vo:Imagen64VO>");
		    sXMLRequest.append(               "<vo:Imagen64VO>");
		    sXMLRequest.append(                  "<vo:imagenBase64>"+encoddivision1+"</vo:imagenBase64>");
		    sXMLRequest.append(                  "<vo:nombre>division1.jpg</vo:nombre>");
		    sXMLRequest.append(               "</vo:Imagen64VO>");
		    sXMLRequest.append(               "<vo:Imagen64VO>");
		    sXMLRequest.append(                  "<vo:imagenBase64>"+encolinea+"</vo:imagenBase64>");
		    sXMLRequest.append(                  "<vo:nombre>"+linea+"</vo:nombre>");
		    sXMLRequest.append(               "</vo:Imagen64VO>");
		    sXMLRequest.append(               "<vo:Imagen64VO>");
		    sXMLRequest.append(                  "<vo:imagenBase64>"+encoladIzq+"</vo:imagenBase64>");
		    sXMLRequest.append(                  "<vo:nombre>"+ladIzq+"</vo:nombre>");
		    sXMLRequest.append(               "</vo:Imagen64VO>");
		    sXMLRequest.append(               "<vo:Imagen64VO>");
		    sXMLRequest.append(                  "<vo:imagenBase64>"+encoladDer+"</vo:imagenBase64>");
		    sXMLRequest.append(                  "<vo:nombre>"+ladDer+"</vo:nombre>");
		    sXMLRequest.append(               "</vo:Imagen64VO>");
		    sXMLRequest.append(            "</vo:imagenes>");
		    sXMLRequest.append(            "<vo:mailBody>"+mailBody+"</vo:mailBody>");           //mailBody
		    sXMLRequest.append(            "<vo:mailCc>");
		    sXMLRequest.append(               "<vo:EmailVO>");
		    sXMLRequest.append(                  "<vo:direccion></vo:direccion>");
		    sXMLRequest.append(                  "<vo:nombre></vo:nombre>");
		    sXMLRequest.append(               "</vo:EmailVO>");
		    sXMLRequest.append(            "</vo:mailCc>");
		    sXMLRequest.append(            "<vo:mailCco>");
		    sXMLRequest.append(               "<vo:EmailVO>");
		    sXMLRequest.append(                  "<vo:direccion></vo:direccion>");
		    sXMLRequest.append(                  "<vo:nombre></vo:nombre>");
		    sXMLRequest.append(               "</vo:EmailVO>");
		    sXMLRequest.append(            "</vo:mailCco>");
		    sXMLRequest.append(            "<vo:mailFrom>");
		    sXMLRequest.append(               "<vo:direccion>"+mailFrom+"</vo:direccion>");         //mailFrom   @it.iusacell.com.mx
		    sXMLRequest.append(               "<vo:nombre>"+nameFrom+"</vo:nombre>");                   //Nombre 
		    sXMLRequest.append(            "</vo:mailFrom>");
		    sXMLRequest.append(            "<vo:mailSubject>"+mailSubject+"</vo:mailSubject>");     //Subject
		    sXMLRequest.append(            "<vo:mailTo>");
		    sXMLRequest.append(               "<vo:EmailVO>");
		    sXMLRequest.append(                  "<vo:direccion>"+mailTo+"</vo:direccion>");
		    sXMLRequest.append(                  "<vo:nombre>"+Nombre+"</vo:nombre>");
		    sXMLRequest.append(               "</vo:EmailVO>");
		    sXMLRequest.append(            "</vo:mailTo>");
		    sXMLRequest.append(            "<vo:replyTo>");
		    sXMLRequest.append(               "<vo:EmailVO>");
		    sXMLRequest.append(                  "<vo:direccion></vo:direccion>");
		    sXMLRequest.append(                  "<vo:nombre></vo:nombre>");
		    sXMLRequest.append(               "</vo:EmailVO>");
		    sXMLRequest.append(            "</vo:replyTo>");
		    sXMLRequest.append(         "</ser:in5>");
		    sXMLRequest.append(         "<ser:in6>"+sToken+"</ser:in6>");
		    sXMLRequest.append(      "</ser:enviaMail>");
		    sXMLRequest.append(   "</soapenv:Body>");
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
			sResponse = "";
			throw new ServiceException("Al consultar la url: " + url + " :: " + e.getMessage());
		}
		return sResponse;
	}
	
	public RespuestaMensajeVO enviaCorreo(final MessageMailVO messageVO, final String ip)  throws ServiceException, AxisFault, SOAPException
	{
	    final AlgoritmoAes encryptToken = new AlgoritmoAes();
	    final OracleProcedures oracle = new OracleProcedures();
	    final String url = ResourceAccess.getParametroFromXML("urlE2EMessengerEnviaMail");
	    
        int    idAplicacion    = 18;  
        String llaveAplicacion = null;
        String token           = null;
        
        try{ idAplicacion    = Integer.parseInt(oracle.getValorParametro(149)); } catch (Exception e) { idAplicacion = 18; }
        try{ llaveAplicacion = oracle.getValorParametro(150); } catch (Exception e) { llaveAplicacion = "-hMejjy0-YyaKJahXVjSIq=)J*Sb60"; }   
        
        String llaveEcrypt = "=/ihpqN24e15stHlmsm1!olvEm=*";
        byte[] KeyValue = llaveEcrypt.substring(0, 16).getBytes();
        
        try {
            token=encryptToken.encrypt(Utilerias.GetUTCdatetimeAsString(new Date()),KeyValue);     
            llaveAplicacion=encryptToken.encrypt(llaveAplicacion,KeyValue);
        } catch (NoSuchPaddingException e1) {
            Logger.write("CallServiceMandaMensaje_Mail.enviaCorreo (NoSuchPaddingException) :: " + e1.getMessage());
        } catch (Exception e1) {
            Logger.write("CallServiceMandaMensaje_Mail.enviaCorreo (Exception) :: " + e1.getMessage());
        }

	    Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
	    Logger.write("     EndPoint               :" + url);
	    Logger.write("     Operacion              : enviaMail.client");
	    Logger.write("     requesting             : " + new Date().toString());
	    Logger.write("   + Parametros             + ");
	    Logger.write("     ip                     : " + ip);
	    
	    RespuestaMensajeVO response = null; 
	    SvMessageHttpBindingStub stub = null;
	    SvMessageLocator locator = new SvMessageLocator();
	    locator.setsvMessageHttpPortEndpointAddress(url);
	    
	    String mensajeError = "";
	    boolean errorEnFlujo = false;
	    
	    
	    try{
	        stub = (SvMessageHttpBindingStub)locator.getsvMessageHttpPort();
	        response = stub.enviaMail("=/ihpqN24e15stHlmsm1!olvEm=*", "=/ihpqN24e15stHlmsm1!olvEm=*", idAplicacion, llaveAplicacion, ip, messageVO, token);
	    }catch (ServiceException exc)
	    {
	        Logger.write("UtilEnviaCorreo (ServiceException) :: " + exc.getMessage());
	        errorEnFlujo = true;
	    }
	    catch (RemoteException exc)
	    {
	        Logger.write("UtilEnviaCorreo (RemoteException) :: " + exc.getMessage());
	        errorEnFlujo = true;
	    }
	    catch (Exception exc)
	    {
	        Logger.write("UtilEnviaCorreo (Exception) :: " + exc.getMessage());
	        errorEnFlujo = true;
	    }
	    finally
	    {
	        Logger.write("  -> SOAPRequestXML         : "  +  stub._getCall().getMessageContext().getRequestMessage().getSOAPPart().getEnvelope().toString());
	        Logger.write("  <- SOAPResponseXML        : "  +  stub._getCall().getMessageContext().getResponseMessage().getSOAPPart().getEnvelope().toString());
	        
	        if(errorEnFlujo){
	            ParseXMLFile.ParseMensajeCorreo(stub._getCall().getMessageContext().getResponseMessage().getSOAPPart().getEnvelope().toString());
	        }
	        
	        stub = null;
	        locator  = null;
	    }
	    
	    return response;
	}
}
