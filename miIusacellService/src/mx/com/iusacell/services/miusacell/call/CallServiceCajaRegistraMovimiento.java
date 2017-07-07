package mx.com.iusacell.services.miusacell.call;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.rpc.ServiceException;

import com.iusacell.EncryptStandAlone.Encriptar;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.util.AlgoritmoAes;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceCajaRegistraMovimiento {
	public String cajaRegistraMovimiento(final String dn,final String user, final String contrasena, final int anioExpira, String cdgSeguridad, final String concepto, final Double importe, final int mesExpira, 
	        String numTarjeta, final String tipoTarjeta, final String ip, final Long secuencia, final String password, final int tipoPlataforma,  
	        final String sCdgCsi, final String sImporteRenta, final String sImporteDeposito, final long sNumTransaccion,
	        final String sRegion, final String sSistema, final String sTipoMovCaja, final String sTipoOperacion, String sTipoTransac, final String sUsuario,
	        final String sVendedor, final String sBancoId, final String sCampo55, final String sIdBenefCh, final String sIdctarjet, final String sMesesSinIntereses, 
	        final String sNomTitularCh, final String sRefBancaria, final String sNumAutor, final String sTipoMovTarjeta, final String sTipoPago, final String sTrack2,
	        final String sIdProducto, final String sCantidad, final String sDescuento, final String sNumSerie, final String sIccid, final String sNumCheque,
	        final String sNumCtaCheque) throws ServiceException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, InvocationTargetException, SQLException
	{
		
		StringBuffer sXMLPeticion = new StringBuffer();
		String sResponse="";
		OracleProcedures oracle = new OracleProcedures();
		String url = ResourceAccess.getParametroFromXML("urlsServiceCaja");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : RegistraMovimiento");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);

		//Decencripta de aes128 y encripta en TDC
		AlgoritmoAes algo=new AlgoritmoAes();
		Encriptar encripta=new Encriptar();
		cdgSeguridad = algo.decrypt(cdgSeguridad, "GrUPoSaLInaSsACv".getBytes());
		cdgSeguridad = encripta.encriptarTDC(cdgSeguridad);
		

		//Decencripta de aes128 y encripta en TDC
		numTarjeta = algo.decrypt(numTarjeta, "GrUPoSaLInaSsACv".getBytes());
		numTarjeta = encripta.encriptarTDC(numTarjeta);
		
		//genera token
		Calendar cal = new GregorianCalendar();
		long fechamil = cal.getTimeInMillis();
		String token = ip + "|" + fechamil;
		String concatenaToken = encripta.encriptarTDC(token);
				
		try
		{
			String nuevaImplementacionCaja = "0";
			try{
				nuevaImplementacionCaja = oracle.getValorParametro(141);
			}
			catch (Exception e) {
				Logger.write("     Detail        : (Exception) " + e.getMessage());
			}
			
			if(nuevaImplementacionCaja.equals("1"))
			{				
				sTipoTransac = "V";
				sXMLPeticion.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:caja=\"http://caja.services.iusacell.com.mx/\" xmlns:vo=\"http://vo.caja.services.iusacell.com.mx/\">");
				sXMLPeticion.append(   "<soapenv:Header/>");
				sXMLPeticion.append(   "<soapenv:Body>");
				sXMLPeticion.append(      "<caja:registraMovimiento>");
				sXMLPeticion.append(         "<caja:user>"+user+"</caja:user>");
				sXMLPeticion.append(         "<caja:pass>"+contrasena+"</caja:pass>");
				sXMLPeticion.append(         "<caja:token>"+concatenaToken+"</caja:token>");
				sXMLPeticion.append(         "<vo:parametrosMovimientosVO>");
				sXMLPeticion.append(            "<vo:sistema>"+sSistema+"</vo:sistema>");
				sXMLPeticion.append(            "<vo:region>"+sRegion+"</vo:region>");
				sXMLPeticion.append(            "<vo:cdgCsi>"+sCdgCsi+"</vo:cdgCsi>");
				sXMLPeticion.append(            "<vo:tipoTransac>"+sTipoTransac+"</vo:tipoTransac>");
				sXMLPeticion.append(            "<vo:importeVenta>"+importe+"</vo:importeVenta>");
				sXMLPeticion.append(            "<vo:importeRenta>"+sImporteRenta+"</vo:importeRenta>");
				sXMLPeticion.append(            "<vo:importeDeDepositos>"+sImporteDeposito+"</vo:importeDeDepositos>");
				sXMLPeticion.append(            "<vo:tipoMovCaja>"+sTipoMovCaja+"</vo:tipoMovCaja>");
				sXMLPeticion.append(            "<vo:numTransaccion>"+sNumTransaccion+"</vo:numTransaccion>");
				sXMLPeticion.append(            "<vo:formasDePago>");
				sXMLPeticion.append(               "<vo:CadenaPagoVO>");
				sXMLPeticion.append(                  "<vo:tipoPago>"+sTipoPago+"</vo:tipoPago>");
				sXMLPeticion.append(                  "<vo:importe>"+importe+"</vo:importe>");
				sXMLPeticion.append(                  "<vo:refBancaria>"+sRefBancaria+"</vo:refBancaria>");
				sXMLPeticion.append(                  "<vo:bancoId>"+sBancoId+"</vo:bancoId>");
				sXMLPeticion.append(                  "<vo:numTarjeta>"+numTarjeta+"</vo:numTarjeta>");
				sXMLPeticion.append(                  "<vo:mesExpira>"+mesExpira+"</vo:mesExpira>");
				sXMLPeticion.append(                  "<vo:anioExpira>"+anioExpira+"</vo:anioExpira>");
				sXMLPeticion.append(                  "<vo:cdgSeguridad>"+cdgSeguridad+"</vo:cdgSeguridad>");
				sXMLPeticion.append(                  "<vo:track2>"+sTrack2+"</vo:track2>");
				sXMLPeticion.append(                  "<vo:campo55>"+sCampo55+"</vo:campo55>");
				sXMLPeticion.append(                  "<vo:mesesSinIntereses>"+sMesesSinIntereses+"</vo:mesesSinIntereses>");
				sXMLPeticion.append(                  "<vo:tipoMovTarjeta>"+sTipoMovTarjeta+"</vo:tipoMovTarjeta>");
				sXMLPeticion.append(                  "<vo:tipoTarjeta>"+tipoTarjeta+"</vo:tipoTarjeta>");
				sXMLPeticion.append(                  "<vo:idctarjet>"+sIdctarjet+"</vo:idctarjet>");
				sXMLPeticion.append(                  "<vo:numAutor>"+sNumAutor+"</vo:numAutor>");
				sXMLPeticion.append(                  "<vo:numCtaCheque>"+sNumCtaCheque+"</vo:numCtaCheque>");
				sXMLPeticion.append(                  "<vo:numCheque>"+sNumCheque+"</vo:numCheque>");
				sXMLPeticion.append(                  "<vo:nomTitularCh>"+sNomTitularCh+"</vo:nomTitularCh>");
				sXMLPeticion.append(                  "<vo:idBenefCh>"+sIdBenefCh+"</vo:idBenefCh>");
				sXMLPeticion.append(               "</vo:CadenaPagoVO>");
				sXMLPeticion.append(            "</vo:formasDePago>");
				sXMLPeticion.append(            "<vo:usuario>"+sUsuario+"</vo:usuario>");
				sXMLPeticion.append(            "<vo:vendedor>"+sVendedor+"</vo:vendedor>");
				sXMLPeticion.append(            "<vo:dn>"+dn+"</vo:dn>");
				sXMLPeticion.append(            "<vo:numCuenta></vo:numCuenta>");
				sXMLPeticion.append(            "<vo:ipAddressUser>"+ip+"</vo:ipAddressUser>");
				sXMLPeticion.append(         "</vo:parametrosMovimientosVO>");
				sXMLPeticion.append(      "</caja:registraMovimiento>");
				sXMLPeticion.append(   "</soapenv:Body>");
				sXMLPeticion.append("</soapenv:Envelope>");
			}
			else
			{
			    sXMLPeticion.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:caja=\"http://caja.services.iusacell.com.mx/\" xmlns:vo=\"http://vo.caja.services.iusacell.com.mx/\">");
			    sXMLPeticion.append(   "<soapenv:Header/>");
			    sXMLPeticion.append(   "<soapenv:Body>");
			    sXMLPeticion.append(      "<caja:registraMovimiento>");
			    sXMLPeticion.append(         "<caja:user>"+user+"</caja:user>");
			    sXMLPeticion.append(         "<caja:pass>"+contrasena+"</caja:pass>");
			    sXMLPeticion.append(         "<caja:token>"+concatenaToken+"</caja:token>");
			    sXMLPeticion.append(         "<vo:parametrosMovimientosVO>");
			    sXMLPeticion.append(            "<vo:tipoOperacion>"+sTipoOperacion+"</vo:tipoOperacion>");
			    sXMLPeticion.append(            "<vo:sistema>"+sSistema+"</vo:sistema>");
			    sXMLPeticion.append(            "<vo:region>"+sRegion+"</vo:region>");
			    sXMLPeticion.append(            "<vo:cdgCsi>"+sCdgCsi+"</vo:cdgCsi>");
			    sXMLPeticion.append(            "<vo:tipoTransac>"+sTipoTransac+"</vo:tipoTransac>");
			    sXMLPeticion.append(            "<vo:importeVenta>"+importe+"</vo:importeVenta>");
			    sXMLPeticion.append(            "<vo:importeRenta>"+sImporteRenta+"</vo:importeRenta>");
			    sXMLPeticion.append(            "<vo:importeDeDepositos>"+sImporteDeposito+"</vo:importeDeDepositos>");
			    sXMLPeticion.append(            "<vo:tipoMovCaja>"+sTipoMovCaja+"</vo:tipoMovCaja>");
			    sXMLPeticion.append(            "<vo:formasDePago>");
			    sXMLPeticion.append(               "<vo:CadenaPagoVO>");
			    sXMLPeticion.append(                  "<vo:tipoPago>"+sTipoPago+"</vo:tipoPago>");
			    sXMLPeticion.append(                  "<vo:importe>"+importe+"</vo:importe>");
			    sXMLPeticion.append(                  "<vo:refBancaria>"+sRefBancaria+"</vo:refBancaria>");
			    sXMLPeticion.append(                  "<vo:bancoId>"+sBancoId+"</vo:bancoId>");
			    sXMLPeticion.append(                  "<vo:numTarjeta>"+numTarjeta+"</vo:numTarjeta>");
			    sXMLPeticion.append(                  "<vo:mesExpira>"+mesExpira+"</vo:mesExpira>");
			    sXMLPeticion.append(                  "<vo:anioExpira>"+anioExpira+"</vo:anioExpira>");
			    sXMLPeticion.append(                  "<vo:cdgSeguridad>"+cdgSeguridad+"</vo:cdgSeguridad>");
			    sXMLPeticion.append(                  "<vo:track2>"+sTrack2+"</vo:track2>");
			    sXMLPeticion.append(                  "<vo:campo55>"+sCampo55+"</vo:campo55>");
			    sXMLPeticion.append(                  "<vo:mesesSinIntereses>"+sMesesSinIntereses+"</vo:mesesSinIntereses>");
			    sXMLPeticion.append(                  "<vo:tipoMovTarjeta>"+sTipoMovTarjeta+"</vo:tipoMovTarjeta>");
			    sXMLPeticion.append(                  "<vo:tipoTarjeta>"+tipoTarjeta+"</vo:tipoTarjeta>");
			    sXMLPeticion.append(                  "<vo:idctarjet>"+sIdctarjet+"</vo:idctarjet>");
			    sXMLPeticion.append(                  "<vo:numAutor>"+sNumAutor+"</vo:numAutor>");
			    sXMLPeticion.append(                  "<vo:numCtaCheque>"+sNumCtaCheque+"</vo:numCtaCheque>");
			    sXMLPeticion.append(                  "<vo:numCheque>"+sNumCheque+"</vo:numCheque>");
			    sXMLPeticion.append(                  "<vo:nomTitularCh>"+sNomTitularCh+"</vo:nomTitularCh>");
			    sXMLPeticion.append(                  "<vo:idBenefCh>"+sIdBenefCh+"</vo:idBenefCh>");
			    sXMLPeticion.append(               "</vo:CadenaPagoVO>");
			    sXMLPeticion.append(            "</vo:formasDePago>");
			    sXMLPeticion.append(            "<vo:usuario>"+sUsuario+"</vo:usuario>");
			    sXMLPeticion.append(            "<vo:vendedor>"+sVendedor+"</vo:vendedor>");
			    sXMLPeticion.append(            "<vo:dn>"+dn+"</vo:dn>");
			    sXMLPeticion.append(            "<vo:numCuenta></vo:numCuenta>");
			    sXMLPeticion.append(            "<vo:ipAddressUser>"+ip+"</vo:ipAddressUser>");
			    sXMLPeticion.append(         "</vo:parametrosMovimientosVO>");
			    sXMLPeticion.append(      "</caja:registraMovimiento>");
			    sXMLPeticion.append(   "</soapenv:Body>");
			    sXMLPeticion.append("</soapenv:Envelope>");
			}
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLPeticion.toString()));
			
			SOAPManager oSOAPrequest = new SOAPManager();
			oSOAPrequest.createSOAPManager(url,new MensajeLogBean());
			sResponse = oSOAPrequest.sendRequestWithXML(sXMLPeticion.toString(),new MensajeLogBean());
			sResponse = Utilerias.limpiaCadena(sResponse);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			
		}
		catch(Exception e)
		{
			Logger.write("     Detail        : (Exception) " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		finally
        {
            sXMLPeticion = null;
        }
		
		return sResponse;
	}
	
	public String cajaRegistraMovimientoFP(final String dn,final String user, final String contrasena, final int anioExpira, String cdgSeguridad, final String concepto, final Double importe, final int mesExpira, 
	        String numTarjeta, final String tipoTarjeta, final String ip, final Long secuencia, final String password, final int tipoPlataforma,  
	        final String sCdgCsi, final String sImporteRenta, final String sImporteDeposito, final long sNumTransaccion,
	        final String sRegion, final String sSistema, final String sTipoMovCaja, final String sTipoOperacion, String sTipoTransac, final String sUsuario,
	        final String sVendedor, final String sBancoId, final String sCampo55, final String sIdBenefCh, final String sIdctarjet, final String sMesesSinIntereses, 
	        final String sNomTitularCh, final String sRefBancaria, final String sNumAutor, final String sTipoMovTarjeta, final String sTipoPago, final String sTrack2,
	        final String sIdProducto, final String sCantidad, final String sDescuento, final String sNumSerie, final String sIccid, final String sNumCheque,
	        final String sNumCtaCheque, final String fingerPrint) throws ServiceException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, InvocationTargetException, SQLException
	{
		
		StringBuffer sXMLPeticion = new StringBuffer();
		String sResponse="";		
		String url = ResourceAccess.getParametroFromXML("urlsServiceCaja");		
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : RegistraMovimiento");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);

		//Decencripta de aes128 y encripta en TDC
		AlgoritmoAes algo=new AlgoritmoAes();
		Encriptar encripta=new Encriptar();
		cdgSeguridad = algo.decrypt(cdgSeguridad, "GrUPoSaLInaSsACv".getBytes());
		cdgSeguridad = encripta.encriptarTDC(cdgSeguridad);
		

		//Decencripta de aes128 y encripta en TDC
		numTarjeta = algo.decrypt(numTarjeta, "GrUPoSaLInaSsACv".getBytes());
		numTarjeta = encripta.encriptarTDC(numTarjeta);
		
		//genera token
		Calendar cal = new GregorianCalendar();
		long fechamil = cal.getTimeInMillis();
		String token = ip + "|" + fechamil;
		String concatenaToken = encripta.encriptarTDC(token);
				
		try
		{								
			sTipoTransac = "V";
			sXMLPeticion.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:caja=\"http://caja.services.iusacell.com.mx/\" xmlns:vo=\"http://vo.caja.services.iusacell.com.mx/\">");
			sXMLPeticion.append(   "<soapenv:Header/>");
			sXMLPeticion.append(   "<soapenv:Body>");
			sXMLPeticion.append(      "<caja:registraMovimiento>");
			sXMLPeticion.append(         "<caja:user>"+user+"</caja:user>");
			sXMLPeticion.append(         "<caja:pass>"+contrasena+"</caja:pass>");
			sXMLPeticion.append(         "<caja:token>"+concatenaToken+"</caja:token>");
			sXMLPeticion.append(         "<vo:parametrosMovimientosVO>");
			sXMLPeticion.append(            "<vo:sistema>"+sSistema+"</vo:sistema>");
			sXMLPeticion.append(            "<vo:region>"+sRegion+"</vo:region>");
			sXMLPeticion.append(            "<vo:cdgCsi>"+sCdgCsi+"</vo:cdgCsi>");
			sXMLPeticion.append(            "<vo:tipoTransac>"+sTipoTransac+"</vo:tipoTransac>");
			sXMLPeticion.append(            "<vo:importeVenta>"+importe+"</vo:importeVenta>");
			sXMLPeticion.append(            "<vo:importeRenta>"+sImporteRenta+"</vo:importeRenta>");
			sXMLPeticion.append(            "<vo:importeDeDepositos>"+sImporteDeposito+"</vo:importeDeDepositos>");
			sXMLPeticion.append(            "<vo:tipoMovCaja>"+sTipoMovCaja+"</vo:tipoMovCaja>");
			sXMLPeticion.append(            "<vo:numTransaccion>"+sNumTransaccion+"</vo:numTransaccion>");
			sXMLPeticion.append(            "<vo:formasDePago>");
			sXMLPeticion.append(               "<vo:CadenaPagoVO>");
			sXMLPeticion.append(                  "<vo:tipoPago>"+sTipoPago+"</vo:tipoPago>");
			sXMLPeticion.append(                  "<vo:importe>"+importe+"</vo:importe>");
			sXMLPeticion.append(                  "<vo:refBancaria>"+sRefBancaria+"</vo:refBancaria>");
			sXMLPeticion.append(                  "<vo:bancoId>"+sBancoId+"</vo:bancoId>");
			sXMLPeticion.append(                  "<vo:numTarjeta>"+numTarjeta+"</vo:numTarjeta>");
			sXMLPeticion.append(                  "<vo:mesExpira>"+mesExpira+"</vo:mesExpira>");
			sXMLPeticion.append(                  "<vo:anioExpira>"+anioExpira+"</vo:anioExpira>");
			sXMLPeticion.append(                  "<vo:cdgSeguridad>"+cdgSeguridad+"</vo:cdgSeguridad>");
			sXMLPeticion.append(                  "<vo:track2>"+sTrack2+"</vo:track2>");
			sXMLPeticion.append(                  "<vo:campo55>"+sCampo55+"</vo:campo55>");
			sXMLPeticion.append(                  "<vo:mesesSinIntereses>"+sMesesSinIntereses+"</vo:mesesSinIntereses>");
			sXMLPeticion.append(                  "<vo:tipoMovTarjeta>"+sTipoMovTarjeta+"</vo:tipoMovTarjeta>");
			sXMLPeticion.append(                  "<vo:tipoTarjeta>"+tipoTarjeta+"</vo:tipoTarjeta>");
			sXMLPeticion.append(                  "<vo:idctarjet>"+sIdctarjet+"</vo:idctarjet>");
			sXMLPeticion.append(                  "<vo:numAutor>"+sNumAutor+"</vo:numAutor>");
			sXMLPeticion.append(                  "<vo:numCtaCheque>"+sNumCtaCheque+"</vo:numCtaCheque>");
			sXMLPeticion.append(                  "<vo:numCheque>"+sNumCheque+"</vo:numCheque>");
			sXMLPeticion.append(                  "<vo:nomTitularCh>"+sNomTitularCh+"</vo:nomTitularCh>");
			sXMLPeticion.append(                  "<vo:idBenefCh>"+sIdBenefCh+"</vo:idBenefCh>");
			sXMLPeticion.append(                  "<vo:fingerprint>"+fingerPrint+"</vo:fingerprint>");
			sXMLPeticion.append(               "</vo:CadenaPagoVO>");
			sXMLPeticion.append(            "</vo:formasDePago>");
			sXMLPeticion.append(            "<vo:usuario>"+sUsuario+"</vo:usuario>");
			sXMLPeticion.append(            "<vo:vendedor>"+sVendedor+"</vo:vendedor>");
			sXMLPeticion.append(            "<vo:dn>"+dn+"</vo:dn>");
			sXMLPeticion.append(            "<vo:numCuenta></vo:numCuenta>");
			sXMLPeticion.append(            "<vo:ipAddressUser>"+ip+"</vo:ipAddressUser>");
			sXMLPeticion.append(         "</vo:parametrosMovimientosVO>");
			sXMLPeticion.append(      "</caja:registraMovimiento>");
			sXMLPeticion.append(   "</soapenv:Body>");
			sXMLPeticion.append("</soapenv:Envelope>");


			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLPeticion.toString()));

			SOAPManager oSOAPrequest = new SOAPManager();
			oSOAPrequest.createSOAPManager(url,new MensajeLogBean());
			sResponse = oSOAPrequest.sendRequestWithXML(sXMLPeticion.toString(),new MensajeLogBean());
			sResponse = Utilerias.limpiaCadena(sResponse);

			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));

		}
		catch(Exception e)
		{
			Logger.write("     Detail        : (Exception) " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		finally
        {
            sXMLPeticion = null;
        }
		
		return sResponse;
	}
	
	public String cajaConfirmacionPago(final int in03, final String ip,final String autorizacionTA, final long  secuenciaTA) throws ServiceException
	{
		StringBuffer sXMLPeticion = new StringBuffer();
		String sResponse="";
		String url = ResourceAccess.getParametroFromXML("urlsServiceCaja");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : ConfirmacionPago");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     idRegistro             : " + in03);

		
		//genera token
		OracleProcedures oracle = new OracleProcedures();
		Encriptar encripta=new Encriptar();
		Calendar cal = new GregorianCalendar();
		long fechamil = cal.getTimeInMillis();
		String token = ip + "|" + fechamil;
		String concatenaToken = encripta.encriptarTDC(token);
		String activaNuevaConfirmacion = "0";
		
		try
		{
			try{
				activaNuevaConfirmacion = oracle.getValorParametro(102);
			}catch (Exception e) {
				activaNuevaConfirmacion = "0";
			}
			
			if(activaNuevaConfirmacion.equals("0"))
			{
			    sXMLPeticion.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:caja=\"http://caja.services.iusacell.com.mx/\">");
			    sXMLPeticion.append(    "<soapenv:Header/>");
			    sXMLPeticion.append(    "<soapenv:Body>");
			    sXMLPeticion.append(        "<caja:confirmacionPago>");
			    sXMLPeticion.append(            "<caja:user>"+"JcDAxzwOwKEbI12XQ1KNjQ*/"+"</caja:user>");
			    sXMLPeticion.append(            "<caja:pass>"+"JcDAxzwOwKEbI12XQ1KNjQ*/"+"</caja:pass>");
			    sXMLPeticion.append(            "<caja:token>"+concatenaToken+"</caja:token>");
			    sXMLPeticion.append(            "<caja:idRegistro>"+in03+"</caja:idRegistro>");
			    sXMLPeticion.append(            "<caja:autorizacionTA>"+autorizacionTA+"</caja:autorizacionTA>");
			    sXMLPeticion.append(            "<caja:secuenciaTA>"+secuenciaTA+"</caja:secuenciaTA>");
			    sXMLPeticion.append(        "</caja:confirmacionPago>");
			    sXMLPeticion.append(    "</soapenv:Body>");
			    sXMLPeticion.append("</soapenv:Envelope>");
			}
			else
			{
			    sXMLPeticion.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:caja='http://caja.services.iusacell.com.mx/' xmlns:vo='http://vo.caja.services.iusacell.com.mx/'>");
			    sXMLPeticion.append(   "<soapenv:Header/>");
			    sXMLPeticion.append(   "<soapenv:Body>");
			    sXMLPeticion.append(   "<caja:confirmacionPago>");
			    sXMLPeticion.append(     "<caja:user>JcDAxzwOwKEbI12XQ1KNjQ*/</caja:user>");
			    sXMLPeticion.append(     "<caja:pass>JcDAxzwOwKEbI12XQ1KNjQ*/</caja:pass>");
			    sXMLPeticion.append(     "<caja:token>"+concatenaToken+"</caja:token>");
			    sXMLPeticion.append(     "<vo:parametrosConfirmacionVO>");
			    sXMLPeticion.append(        "<vo:idRegistro>"+in03+"</vo:idRegistro>");
			    sXMLPeticion.append(        "<vo:tiempoAireVO>");
			    sXMLPeticion.append(           "<vo:autorizacionTA>"+autorizacionTA+"</vo:autorizacionTA>");
			    sXMLPeticion.append(           "<vo:secuenciaTA>"+secuenciaTA+"</vo:secuenciaTA>");
			    sXMLPeticion.append(        "</vo:tiempoAireVO>");
			    sXMLPeticion.append(     "</vo:parametrosConfirmacionVO>");
			    sXMLPeticion.append(   "</caja:confirmacionPago>");
			    sXMLPeticion.append(   "</soapenv:Body>");
			    sXMLPeticion.append("</soapenv:Envelope>");
			}
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLPeticion.toString()));
			
			SOAPManager oSOAPrequest = new SOAPManager();
			oSOAPrequest.createSOAPManager(url, new MensajeLogBean());
			sResponse = oSOAPrequest.sendRequestWithXML(sXMLPeticion.toString(), new MensajeLogBean());
			sResponse = Utilerias.limpiaCadena(sResponse);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		finally
        {
            sXMLPeticion = null;
        }
		
		return sResponse;
	}
	
	public String reversaCaja(final int in03, final String ip) throws ServiceException
	{
		StringBuffer sXMLPeticion = new StringBuffer();
		String sResponse="";
		String url = ResourceAccess.getParametroFromXML("urlsServiceCaja");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : reversaCaja");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     idRegistro             : " + in03);

		//genera token
		Calendar cal = new GregorianCalendar();
		Encriptar encripta=new Encriptar();
		long fechamil = cal.getTimeInMillis();
		String token = ip + "|" + fechamil;
		String concatenaToken = encripta.encriptarTDC(token); 
		
		try
		{
		    sXMLPeticion.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:caja=\"http://caja.services.iusacell.com.mx/\">");
		    sXMLPeticion.append(   "<soapenv:Header/>");
		    sXMLPeticion.append(   "<soapenv:Body>");
		    sXMLPeticion.append(      "<caja:reversoAutorizador>");
		    sXMLPeticion.append(         "<caja:user>JcDAxzwOwKEbI12XQ1KNjQ*/</caja:user>");
		    sXMLPeticion.append(         "<caja:pass>JcDAxzwOwKEbI12XQ1KNjQ*/</caja:pass>");
		    sXMLPeticion.append(         "<caja:token>"+concatenaToken+"</caja:token>");
		    sXMLPeticion.append(         "<caja:idRegistro>"+in03+"</caja:idRegistro>");
		    sXMLPeticion.append(      "</caja:reversoAutorizador>");
		    sXMLPeticion.append(   "</soapenv:Body>");
		    sXMLPeticion.append("</soapenv:Envelope>");
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLPeticion.toString()));
			
			SOAPManager oSOAPrequest = new SOAPManager();
			oSOAPrequest.createSOAPManager(url, new MensajeLogBean());
			sResponse = oSOAPrequest.sendRequestWithXML(sXMLPeticion.toString(), new MensajeLogBean());
			sResponse = Utilerias.limpiaCadena(sResponse);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		finally
        {
            sXMLPeticion = null;
        }
		
		return sResponse;
	}
	
	public String reversarTransaccion(final long numeroTransaccion, final int sistema, final String ip) throws ServiceException
	{
		StringBuffer sXMLPeticion = new StringBuffer();
		String sResponse="";
		String url = ResourceAccess.getParametroFromXML("urlsServiceCaja");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : reversarTransaccion");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     numTransaccion         : " + numeroTransaccion);
		Logger.write("     sistema                : " + sistema);

		//genera token
		Calendar cal = new GregorianCalendar();
		Encriptar encripta=new Encriptar();
		long fechamil = cal.getTimeInMillis();
		String token = ip + "|" + fechamil;
		String concatenaToken = encripta.encriptarTDC(token); 
		
		try
		{
		    sXMLPeticion.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:caja=\"http://caja.services.iusacell.com.mx/\">");
		    sXMLPeticion.append(   "<soapenv:Header/>");
		    sXMLPeticion.append(   "<soapenv:Body>");
		    sXMLPeticion.append(      "<caja:reversarTransaccion>");
		    sXMLPeticion.append(         "<caja:user>JcDAxzwOwKEbI12XQ1KNjQ*/</caja:user>");
		    sXMLPeticion.append(         "<caja:pass>JcDAxzwOwKEbI12XQ1KNjQ*/</caja:pass>");
		    sXMLPeticion.append(         "<caja:token>"+concatenaToken+"</caja:token>");
		    sXMLPeticion.append(         "<caja:reversarTransaccionVO>" );
		    sXMLPeticion.append(         "<vo:numTransaccion xmlns:vo=\"http://vo.caja.services.iusacell.com.mx/\">"+numeroTransaccion+"</vo:numTransaccion>");
		    sXMLPeticion.append(         "<vo:sistema xmlns:vo=\"http://vo.caja.services.iusacell.com.mx/\">"+sistema+"</vo:sistema>" );
		    sXMLPeticion.append(         "</caja:reversarTransaccionVO>" );
		    sXMLPeticion.append(      "</caja:reversarTransaccion>");
		    sXMLPeticion.append(   "</soapenv:Body>");
		    sXMLPeticion.append("</soapenv:Envelope>");
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLPeticion.toString()));
			
			SOAPManager oSOAPrequest = new SOAPManager();
			oSOAPrequest.createSOAPManager(url, new MensajeLogBean());
			sResponse = oSOAPrequest.sendRequestWithXML(sXMLPeticion.toString(), new MensajeLogBean());
			sResponse = Utilerias.limpiaCadena(sResponse);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		finally
        {
            sXMLPeticion = null;
        }
		
		return sResponse;
	}
}
