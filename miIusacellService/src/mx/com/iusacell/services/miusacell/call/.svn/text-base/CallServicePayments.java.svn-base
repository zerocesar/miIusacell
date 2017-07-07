package mx.com.iusacell.services.miusacell.call;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.CardVO;
import mx.com.iusacell.services.miiusacell.vo.PagoVO;
import mx.com.iusacell.services.miusacell.util.AlgoritmoAes;
import mx.com.iusacell.services.miusacell.util.Utilerias;
import mx.com.iusacell.tesoreria.security.TesoIusaCipher;

public class CallServicePayments {
	
	public String consultaCuenta(final String dn) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		String cadena = "";
		String url="";
		String user="";
		String pwwd="";
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse = "";
		try{
		   cadena = oracle.getValorParametro(116);
		   if(cadena !=null && !cadena.equals(""))
		   {
			   String[] parametrosSplit = cadena.split("\\|");
			   url = parametrosSplit[0];
			   user = parametrosSplit[1];
			   pwwd = parametrosSplit[2];
		   }
		}catch (Exception e) {
			Logger.write("Ocurrio un detail al consultar en BD metodo ::  consultaCuenta");
		}
		
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : consultaCuenta");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     user                   : " + user);
		Logger.write("     password               : " + pwwd);
		Logger.write("     dn                     : " + dn);
		
		try {
			//genera token
			TesoIusaCipher teso = TesoIusaCipher.getInstance();
			Calendar cal = new GregorianCalendar();
			long fechamil = cal.getTimeInMillis();
			String token = "10.189.64.45" + "|" + fechamil;
			token = teso.encrypt(token);
			
            sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cli=\"http://cliente.spring.iusacell.com/\">");
            sXMLRequest.append(            "<soapenv:Header/>");
            sXMLRequest.append(            "<soapenv:Body>");
            sXMLRequest.append(               "<cli:consultaCuenta>");
            sXMLRequest.append(                  "<user>"+user+"</user>");
            sXMLRequest.append(                  "<password>"+pwwd+"</password>");
            sXMLRequest.append(                  "<token>"+token+"</token>");
            sXMLRequest.append(                  "<telefono>"+dn+"</telefono>");
            sXMLRequest.append(               "</cli:consultaCuenta>");
            sXMLRequest.append(            "</soapenv:Body>");
            sXMLRequest.append(         "</soapenv:Envelope>");
			
			Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(sXMLRequest.toString()));
			SOAPManager soapManager = new SOAPManager();
			soapManager.createSOAPManager(url,new MensajeLogBean());
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException("PaymentsService: "+ e.getMessage());
		}finally{
            sXMLRequest=null;
        }
        return sResponse;
	}
	
	public String pagoReferenciadoBmx(final PagoVO pago, final CardVO card, final int tipoPlataforma) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		String cadena = "";
		String url="";
		String user="";
		String pwwd="";
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse = "";
		String cdgSeguridad = "";
		String numTarjeta = "";
		String valorVersion = "";
		String activarFingerP = "";
		
		try{
		   cadena = oracle.getValorParametro(116);
		   if(cadena !=null && !cadena.equals(""))
		   {
			   String[] parametrosSplit = cadena.split("\\|");
			   url = parametrosSplit[0];
			   user = parametrosSplit[1];
			   pwwd = parametrosSplit[2];
		   }
		}catch (Exception e) {
			Logger.write("Ocurrio un detail al consultar en BD metodo ::  pagoReferenciadoBmx");
		}
		try{			
			valorVersion = oracle.getValorParametro(203); 
			activarFingerP = oracle.getValorParametro(195); 			
		}catch (ServiceException e) {			
			valorVersion = "0";
			activarFingerP = "0";
		}
		
		if(tipoPlataforma == 3){
			valorVersion = "12";
		}
		
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : pagoReferenciadoBmx");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     user                   : " + user);
		Logger.write("     password               : " + pwwd);
		if(pago != null){
			Logger.write("     getCanal               : " + pago.getCanal());
			Logger.write("     getCuenta              : " + pago.getCuenta());
			Logger.write("     getFecha               : " + pago.getFecha());
			Logger.write("     getFolioElk            : " + pago.getFolioElk());
			Logger.write("     getFormaPago           : " + pago.getFormaPago());
			Logger.write("     getMonto               : " + pago.getMonto());
			Logger.write("     getNumReferencia       : " + pago.getNumReferencia());
			Logger.write("     getNumTransaccion      : " + pago.getNumTransaccion());
			Logger.write("     getOrigen              : " + pago.getOrigen());
			Logger.write("     getTienda              : " + pago.getTienda());
			Logger.write("     getUsuarioAdn          : " + pago.getUsuarioAdn());
		}
		if(card != null){
			Logger.write("     getAmount              : " + card.getAmount());
			Logger.write("     getCardExpiryMonth     : " + card.getCardExpiryMonth());
			Logger.write("     getCardExpiryYear      : " + card.getCardExpiryYear());
			Logger.write("     getCardNumber          : " + card.getCardNumber());
			Logger.write("     getCardSecurityCode    : ***");
			
		}
		
		try {
			//Decencripta de aes128 y encripta en TDC
			AlgoritmoAes algo=new AlgoritmoAes();
			cdgSeguridad = algo.decrypt(card.getCardSecurityCode(), "GrUPoSaLInaSsACv".getBytes());
			cdgSeguridad = TesoIusaCipher.getInstance().encrypt(cdgSeguridad);
			

			//Decencripta de aes128 y encripta en TDC
			numTarjeta = algo.decrypt(card.getCardNumber(), "GrUPoSaLInaSsACv".getBytes());
			numTarjeta = TesoIusaCipher.getInstance().encrypt(numTarjeta);
			
			//genera token
			Calendar cal = new GregorianCalendar();
			long fechamil = cal.getTimeInMillis();
			String token = "10.189.64.45" + "|" + fechamil;
			token = TesoIusaCipher.getInstance().encrypt(token);
			
			sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cli=\"http://cliente.spring.iusacell.com/\">");
            sXMLRequest.append(            "<soapenv:Header/>");
            sXMLRequest.append(            "<soapenv:Body>");
            sXMLRequest.append(               "<cli:pagoReferenciadoBmx>");
            sXMLRequest.append(                  "<user>"+user+"</user>");
            sXMLRequest.append(                  "<password>"+pwwd+"</password>");
            sXMLRequest.append(                  "<token>"+token+"</token>");
            sXMLRequest.append(                  "<pagoVO>");
            sXMLRequest.append(                     "<numReferencia>"+pago.getNumReferencia()+"</numReferencia>");
            sXMLRequest.append(                     "<usuarioAdn>"+pago.getUsuarioAdn()+"</usuarioAdn>");
            sXMLRequest.append(                     "<tienda>"+pago.getTienda()+"</tienda>");
            sXMLRequest.append(                     "<monto>"+pago.getMonto()+"</monto>");
            sXMLRequest.append(                     "<origen>"+pago.getOrigen()+"</origen>");
            sXMLRequest.append(                     "<folioElk>"+pago.getFolioElk()+"</folioElk>");
            sXMLRequest.append(                     "<numTransaccion>"+pago.getNumTransaccion()+"</numTransaccion>");
            sXMLRequest.append(                     "<canal>"+pago.getCanal()+"</canal>");
            sXMLRequest.append(                     "<formaPago>"+pago.getFormaPago()+"</formaPago>");
            sXMLRequest.append(                     "<cuenta>"+pago.getCuenta()+"</cuenta>");
            sXMLRequest.append(                     "<fecha>"+pago.getFecha()+"</fecha>");
            sXMLRequest.append(                  "</pagoVO>");
            sXMLRequest.append(                  "<CardVO>");
            sXMLRequest.append(                     "<amount>"+card.getAmount()+"</amount>");
            sXMLRequest.append(                     "<cardExpiryMonth>"+card.getCardExpiryMonth()+"</cardExpiryMonth>");
            sXMLRequest.append(                     "<cardExpiryYear>"+card.getCardExpiryYear()+"</cardExpiryYear>");
            sXMLRequest.append(                     "<cardNumber>"+numTarjeta+"</cardNumber>");
            sXMLRequest.append(                     "<cardSecurityCode>"+cdgSeguridad+"</cardSecurityCode>");
            if(activarFingerP != null && activarFingerP.equals("1")){
            	sXMLRequest.append(                     "<version>"+valorVersion+"</version>");
            }
            sXMLRequest.append(                  "</CardVO>");
            sXMLRequest.append(               "</cli:pagoReferenciadoBmx>");
            sXMLRequest.append(            "</soapenv:Body>");
            sXMLRequest.append(         "</soapenv:Envelope>");
			
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
	
	public String pagoReferenciadoBmxFingPrint(final PagoVO pago, final CardVO card, String fingerPrint, String version) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		String cadena = "";
		String url="";
		String user="";
		String pwwd="";
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse = "";
		String cdgSeguridad = "";
		String numTarjeta = "";
		
		try{
		   cadena = oracle.getValorParametro(116);
		   if(cadena !=null && !cadena.equals(""))
		   {
			   String[] parametrosSplit = cadena.split("\\|");
			   url = parametrosSplit[0];
			   user = parametrosSplit[1];
			   pwwd = parametrosSplit[2];
		   }
		}catch (Exception e) {
			Logger.write("Ocurrio un detail al consultar en BD metodo ::  pagoReferenciadoBmxFingPrint");
		}		
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : pagoReferenciadoBmxFingPrint");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     user                   : " + user);
		Logger.write("     password               : " + pwwd);
		Logger.write("     fingerPrint            : " + fingerPrint);
		Logger.write("     version                : " + version);
		if(pago != null){
			Logger.write("     getCanal               : " + pago.getCanal());
			Logger.write("     getCuenta              : " + pago.getCuenta());
			Logger.write("     getFecha               : " + pago.getFecha());
			Logger.write("     getFolioElk            : " + pago.getFolioElk());
			Logger.write("     getFormaPago           : " + pago.getFormaPago());
			Logger.write("     getMonto               : " + pago.getMonto());
			Logger.write("     getNumReferencia       : " + pago.getNumReferencia());
			Logger.write("     getNumTransaccion      : " + pago.getNumTransaccion());
			Logger.write("     getOrigen              : " + pago.getOrigen());
			Logger.write("     getTienda              : " + pago.getTienda());
			Logger.write("     getUsuarioAdn          : " + pago.getUsuarioAdn());
		}
		if(card != null){
			Logger.write("     getAmount              : " + card.getAmount());
			Logger.write("     getCardExpiryMonth     : " + card.getCardExpiryMonth());
			Logger.write("     getCardExpiryYear      : " + card.getCardExpiryYear());
			Logger.write("     getCardNumber          : " + card.getCardNumber());
			Logger.write("     getCardSecurityCode    : ***");
			
		}
		
		try {
			//Decencripta de aes128 y encripta en TDC
			AlgoritmoAes algo=new AlgoritmoAes();
			cdgSeguridad = algo.decrypt(card.getCardSecurityCode(), "GrUPoSaLInaSsACv".getBytes());
			cdgSeguridad = TesoIusaCipher.getInstance().encrypt(cdgSeguridad);
			

			//Decencripta de aes128 y encripta en TDC
			numTarjeta = algo.decrypt(card.getCardNumber(), "GrUPoSaLInaSsACv".getBytes());
			numTarjeta = TesoIusaCipher.getInstance().encrypt(numTarjeta);
			
			//genera token
			Calendar cal = new GregorianCalendar();
			long fechamil = cal.getTimeInMillis();
			String token = "10.189.64.45" + "|" + fechamil;
			token = TesoIusaCipher.getInstance().encrypt(token);
			
			sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cli=\"http://cliente.spring.iusacell.com/\">");
            sXMLRequest.append(            "<soapenv:Header/>");
            sXMLRequest.append(            "<soapenv:Body>");
            sXMLRequest.append(               "<cli:pagoReferenciadoBmx>");
            sXMLRequest.append(                  "<user>"+user+"</user>");
            sXMLRequest.append(                  "<password>"+pwwd+"</password>");
            sXMLRequest.append(                  "<token>"+token+"</token>");
            sXMLRequest.append(                  "<pagoVO>");
            sXMLRequest.append(                     "<numReferencia>"+pago.getNumReferencia()+"</numReferencia>");
            sXMLRequest.append(                     "<usuarioAdn>"+pago.getUsuarioAdn()+"</usuarioAdn>");
            sXMLRequest.append(                     "<tienda>"+pago.getTienda()+"</tienda>");
            sXMLRequest.append(                     "<monto>"+pago.getMonto()+"</monto>");
            sXMLRequest.append(                     "<origen>"+pago.getOrigen()+"</origen>");
            sXMLRequest.append(                     "<folioElk>"+pago.getFolioElk()+"</folioElk>");
            sXMLRequest.append(                     "<numTransaccion>"+pago.getNumTransaccion()+"</numTransaccion>");
            sXMLRequest.append(                     "<canal>"+pago.getCanal()+"</canal>");
            sXMLRequest.append(                     "<formaPago>"+pago.getFormaPago()+"</formaPago>");
            sXMLRequest.append(                     "<cuenta>"+pago.getCuenta()+"</cuenta>");
            sXMLRequest.append(                     "<fecha>"+pago.getFecha()+"</fecha>");
            sXMLRequest.append(                  "</pagoVO>");
            sXMLRequest.append(                  "<CardVO>");
            sXMLRequest.append(                     "<amount>"+card.getAmount()+"</amount>");
            sXMLRequest.append(                     "<cardExpiryMonth>"+card.getCardExpiryMonth()+"</cardExpiryMonth>");
            sXMLRequest.append(                     "<cardExpiryYear>"+card.getCardExpiryYear()+"</cardExpiryYear>");
            sXMLRequest.append(                     "<cardNumber>"+numTarjeta+"</cardNumber>");
            sXMLRequest.append(                     "<cardSecurityCode>"+cdgSeguridad+"</cardSecurityCode>");
            sXMLRequest.append(                     "<fingerprint>"+fingerPrint+"</fingerprint>");
            sXMLRequest.append(                     "<version>"+version+"</version>");
            sXMLRequest.append(                  "</CardVO>");
            sXMLRequest.append(               "</cli:pagoReferenciadoBmx>");
            sXMLRequest.append(            "</soapenv:Body>");
            sXMLRequest.append(         "</soapenv:Envelope>");
			
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
	
	public String reversarPago(final String folioPago, final String numCuenta, final String montoPago) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		String cadena = "";
		String url="";
		String user="";
		String pwwd="";
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse = "";
		
		try{
		   cadena = oracle.getValorParametro(116);
		   if(cadena !=null && !cadena.equals(""))
		   {
			   String[] parametrosSplit = cadena.split("\\|");
			   url = parametrosSplit[0];
			   user = parametrosSplit[1];
			   pwwd = parametrosSplit[2];
		   }
		}catch (Exception e) {
			Logger.write("Ocurrio un detail al consultar en BD metodo ::  reversarPago");
		}
		
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : reversarPago");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     user                   : " + user);
		Logger.write("     password               : " + pwwd);
		Logger.write("     folioPago              : " + folioPago);
		Logger.write("     numCuenta              : " + numCuenta);
		Logger.write("     montoPago              : " + montoPago);
		
		try {
			//genera token
			TesoIusaCipher teso = TesoIusaCipher.getInstance();
			Calendar cal = new GregorianCalendar();
			long fechamil = cal.getTimeInMillis();
			String token = "10.189.64.45" + "|" + fechamil;
			token = teso.encrypt(token);
			
            sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cli=\"http://cliente.spring.iusacell.com/\">");
            sXMLRequest.append(             "<soapenv:Header/>");
            sXMLRequest.append(                "<soapenv:Body>");
            sXMLRequest.append(                   "<cli:reversarPago>");
            sXMLRequest.append(                      "<user>"+user+"</user>");
            sXMLRequest.append(                      "<password>"+pwwd+"</password>");
            sXMLRequest.append(                      "<token>"+token+"</token>");
            sXMLRequest.append(                      "<folioPago>"+folioPago+"</folioPago>");
            sXMLRequest.append(                      "<numCuenta>"+numCuenta+"</numCuenta>");
            sXMLRequest.append(                      "<montoPago>"+montoPago+"</montoPago>");
            sXMLRequest.append(                   "</cli:reversarPago>");
            sXMLRequest.append(                "</soapenv:Body>");
            sXMLRequest.append(             "</soapenv:Envelope>");
			
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
	
	public String pagoReferenciado_bis(final PagoVO pago) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		String cadena = "";
		String url="";
		String user="";
		String pwwd="";
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse = "";
		
		try{
		   cadena = oracle.getValorParametro(116);
		   if(cadena !=null && !cadena.equals(""))
		   {
			   String[] parametrosSplit = cadena.split("\\|");
			   url = parametrosSplit[0];
			   user = parametrosSplit[1];
			   pwwd = parametrosSplit[2];
		   }
		}catch (Exception e) {
			Logger.write("Ocurrio un detail al consultar en BD metodo ::  pagoReferenciado_bis");
		}		
		
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : pagoReferenciado_bis");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     user                   : " + user);
		Logger.write("     password               : " + pwwd);
		if(pago != null){
			Logger.write("     getCanal               : " + pago.getCanal());
			Logger.write("     getCuenta              : " + pago.getCuenta());
			Logger.write("     getFecha               : " + pago.getFecha());
			Logger.write("     getFolioElk            : " + pago.getFolioElk());
			Logger.write("     getFormaPago           : " + pago.getFormaPago());
			Logger.write("     getMonto               : " + pago.getMonto());
			Logger.write("     getNumReferencia       : " + pago.getNumReferencia());
			Logger.write("     getNumTransaccion      : " + pago.getNumTransaccion());
			Logger.write("     getOrigen              : " + pago.getOrigen());
			Logger.write("     getTienda              : " + pago.getTienda());
			Logger.write("     getUsuarioAdn          : " + pago.getUsuarioAdn());
		}		
		
		try {						
			//genera token
			Calendar cal = new GregorianCalendar();
			long fechamil = cal.getTimeInMillis();
			String token = "10.189.64.45" + "|" + fechamil;
			token = TesoIusaCipher.getInstance().encrypt(token);
			
			sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cli=\"http://cliente.spring.iusacell.com/\">");
            sXMLRequest.append(            "<soapenv:Header/>");
            sXMLRequest.append(            "<soapenv:Body>");
            sXMLRequest.append(               "<cli:pagoReferenciado_bis>");
            sXMLRequest.append(                  "<user>"+user+"</user>");
            sXMLRequest.append(                  "<password>"+pwwd+"</password>");
            sXMLRequest.append(                  "<token>"+token+"</token>");
            sXMLRequest.append(                  "<numReferencia>"+pago.getNumReferencia()+"</numReferencia>");
            sXMLRequest.append(                  "<usuarioAdn>"+pago.getUsuarioAdn()+"</usuarioAdn>");
            sXMLRequest.append(                  "<tienda>"+pago.getTienda()+"</tienda>");
            sXMLRequest.append(                  "<monto>"+pago.getMonto()+"</monto>");
            sXMLRequest.append(                  "<origen>"+pago.getOrigen()+"</origen>");
            sXMLRequest.append(                  "<folioElk>"+pago.getFolioElk()+"</folioElk>");
            sXMLRequest.append(                  "<numTransaccion>"+pago.getNumTransaccion()+"</numTransaccion>");
            sXMLRequest.append(                  "<canal>"+pago.getCanal()+"</canal>");
            sXMLRequest.append(                  "<formaPago>"+pago.getFormaPago()+"</formaPago>");
            sXMLRequest.append(                  "<cuenta>"+pago.getCuenta()+"</cuenta>");
            sXMLRequest.append(                  "<fecha>"+pago.getFecha()+"</fecha>");              
            sXMLRequest.append(               "</cli:pagoReferenciado_bis>");
            sXMLRequest.append(            "</soapenv:Body>");
            sXMLRequest.append(         "</soapenv:Envelope>");
			
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