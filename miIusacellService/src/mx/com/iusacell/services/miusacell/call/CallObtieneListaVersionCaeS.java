package mx.com.iusacell.services.miusacell.call;

import java.util.Date;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.rpc.ServiceException;
import sun.misc.BASE64Encoder;
import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.security.GeneraToken;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallObtieneListaVersionCaeS {

	
		@SuppressWarnings("static-access")
		public String callObtieneListaVersionCaeS(final int canal,final int idAplicacion,final int version) throws ServiceException {

		 	String url = ResourceAccess.getParametroFromXML("urlMapas");
			Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
			Logger.write("     EndPoint               : " + url);
			Logger.write("     Operacion              : callObtieneListaVersionCaeS");
			Logger.write("     requesting             : " + new Date().toString());
			Logger.write("   + Parametros             + ");
			Logger.write("     canal                  : " + canal);
			Logger.write("     idAplicacion           : " + idAplicacion);
			Logger.write("     version                : " + version);
			
			StringBuffer sXMLRequest = new StringBuffer();
			String generaToken = "";
			SOAPManager soapManager = new SOAPManager();
			soapManager.createSOAPManager(url,new MensajeLogBean());
			String sResponse = "";
			String phraseToken = "AGhAxzwOwKEbI12X";			
			try
			{
				    generaToken = GeneraToken.generaToken(canal+"");
				    try{
				    	CallObtieneListaVersionCaeS cifrado = new CallObtieneListaVersionCaeS();
						generaToken = cifrado.encrypt3(generaToken,phraseToken.getBytes());
					}catch (Exception e) {
						throw new ServiceException("Ocurrio un error al encriptar");
					}
			    
					sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.mapas.iusacell.com.mx\">");
					sXMLRequest.append(    "<soapenv:Header/>");
					sXMLRequest.append(    "<soapenv:Body>");
					sXMLRequest.append(        "<ser:MapaGetCaeVersion>");
					sXMLRequest.append(            "<ser:in0>AGhAxzwOwKEbI12XQ1KNjQ*M/</ser:in0>");
					sXMLRequest.append(            "<ser:in1>AGhAxzwOwKEbI12XQ1KNjQ*G/</ser:in1>");
					sXMLRequest.append(            "<ser:in2>" + canal        + "</ser:in2>");
					sXMLRequest.append(            "<ser:in3>" + idAplicacion + "</ser:in3>");
					sXMLRequest.append(            "<ser:in4>" + version      + "</ser:in4>");
					sXMLRequest.append(            "<ser:in5>" + generaToken  + "</ser:in5>");
					sXMLRequest.append(        "</ser:MapaGetCaeVersion>");
					sXMLRequest.append(    "</soapenv:Body>");
					sXMLRequest.append("</soapenv:Envelope>");
			    
			    Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));
			    
				sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
				
				Logger.write("   + Respuesta              + ");
				Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			    
			}
			catch(Exception e)
			{
				Logger.write("     Detail        : " + e.getMessage());
				throw new ServiceException("Ocurrio un Error");
			}
			finally
			{
			    sXMLRequest = null;
			}
			
			return sResponse;
			
		}
		
		public String mapaObtieneColXCPSepomex(final String codigoPostal) throws ServiceException {

		 	String url = ResourceAccess.getParametroFromXML("urlMapas");
			Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
			Logger.write("     EndPoint               : " + url);
			Logger.write("     Operacion              : mapaObtieneColXCPSepomex");
			Logger.write("     requesting             : " + new Date().toString());
			Logger.write("   + Parametros             + ");
			Logger.write("     codigoPostal           : " + codigoPostal);
			
			
			StringBuffer sXMLRequest = new StringBuffer();
			String generaToken = "";
			SOAPManager soapManager = new SOAPManager();
			soapManager.createSOAPManager(url,new MensajeLogBean());
			String sResponse = "";
			final String phraseToken = "AGhAxzwOwKEbI12XQ1KNjQ*S/";				
			final byte[] KeyValue = phraseToken.substring(0, 16).getBytes();
			
			
			try
			{
				    generaToken = Utilerias.GetUTCdatetimeAsString(new Date()) + codigoPostal;
				    try{				    	
						generaToken = encrypt3(generaToken,KeyValue);
					}catch (Exception e) {
						throw new ServiceException("Ocurrio un error al encriptar");
					}
			    
					sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.mapas.iusacell.com.mx\">");
					sXMLRequest.append(    "<soapenv:Header/>");
					sXMLRequest.append(    "<soapenv:Body>");
					sXMLRequest.append(        "<ser:MapaObtieneColXCPSepomex>");
					sXMLRequest.append(            "<ser:in0>AGhAxzwOwKEbI12XQ1KNjQ*S/</ser:in0>");
					sXMLRequest.append(            "<ser:in1>AGhAxzwOwKEbI12XQ1KNjQ*M/</ser:in1>");
					sXMLRequest.append(            "<ser:in2>" + codigoPostal        + "</ser:in2>");
					sXMLRequest.append(            "<ser:in3>" + generaToken + "</ser:in3>");				
					sXMLRequest.append(        "</ser:MapaObtieneColXCPSepomex>");
					sXMLRequest.append(    "</soapenv:Body>");
					sXMLRequest.append("</soapenv:Envelope>");
			    
			    Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));
			    
				sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
				
				Logger.write("   + Respuesta              + ");
				Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			    
			}
			catch(Exception e)
			{
				Logger.write("     Detail        : " + e.getMessage());
				throw new ServiceException("Ocurrio un Error");
			}
			finally
			{
			    sXMLRequest = null;
			}
			
			return sResponse;
			
		}
		
		public static String encrypt3(final String x, final byte[] KeyValue) throws Exception, NoSuchPaddingException
		{
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ivSpec = new IvParameterSpec(KeyValue);
			SecretKeySpec keySpec = new SecretKeySpec(KeyValue, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			byte[] encrypted = cipher.doFinal(x.getBytes());
			String encryptedValue = new BASE64Encoder().encode(encrypted);
			return encryptedValue;
		}
	
}
