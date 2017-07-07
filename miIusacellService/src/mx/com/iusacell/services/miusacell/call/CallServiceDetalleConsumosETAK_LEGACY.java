package mx.com.iusacell.services.miusacell.call;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.rpc.ServiceException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.SOAPManager.SOAPManagerAxis2;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceDetalleConsumosETAK_LEGACY {
	SOAPManager soapManager = null;
	
	public String detalleConsumosETAK_LEGACY(final String dn, final String tipoEvento, final String fechaCorte) throws ServiceException, ParseException 
	{
		String url = ResourceAccess.getParametroFromXML("urlDetalleConsumo");
		String consultaWSconsumosxTibco = "";
		String urlConsumosSinTibco = "";
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : callServiceDetalleConsumoETAK_LEGACY");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     tipoEvento             : " + tipoEvento);

		try{
			OracleProcedures oracle = new OracleProcedures();
			consultaWSconsumosxTibco = oracle.getValorParametro(119);
			urlConsumosSinTibco = oracle.getValorParametro(120);
		}catch (ServiceException e) {
			consultaWSconsumosxTibco = "0";
			urlConsumosSinTibco = "";
		}
		
		String sResponse			= "";
		Calendar actual = GregorianCalendar.getInstance();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSSZ");
		java.util.Date dateFin=actual.getTime();

		int dias = 30;
		if(tipoEvento.equals("7"))
		{
			dias=60;
		}
		else if (tipoEvento.equals("4"))
		{
			dias= 10;
		}

		String fechaInicial="";
		Date datecon = null;

		Calendar calStart = Calendar.getInstance();
		if(fechaCorte != null && !fechaCorte.equals("")){
			datecon = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH).parse(fechaCorte);
			calStart.setTime(datecon);
		}
		Date dateFinal = calStart.getTime();

		String fechaFin=sdf.format(dateFin);
		fechaFin = fechaFin.substring(0, 10)+"T"+fechaFin.substring(10, fechaFin.length()-2)+":"+fechaFin.substring(fechaFin.length()-2, fechaFin.length());

		Calendar menosMesAbono = GregorianCalendar.getInstance();
		menosMesAbono.add(Calendar.DATE, -dias);
		java.util.Date dateInicio=menosMesAbono.getTime();

		if(fechaCorte != null && !fechaCorte.equals(""))
			fechaInicial=sdf.format(dateFinal);
		else
			fechaInicial=sdf.format(dateInicio);

		fechaInicial = fechaInicial.substring(0, 10)+"T"+fechaInicial.substring(10, fechaInicial.length()-2)+":"+fechaInicial.substring(fechaInicial.length()-2, fechaInicial.length());
		StringBuffer SOAPRequestXML= new StringBuffer();
		if(consultaWSconsumosxTibco.equals("1")){
            SOAPRequestXML.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.detalleconsumo.telco.iusacell.com\" xmlns:head=\"http://www.example.org/Header\" xmlns:xsd=\"http://vo.detalleconsumo.telco.iusacell.com/xsd\">");
            SOAPRequestXML.append("<soapenv:Header/>");
            SOAPRequestXML.append("<soapenv:Body>");
            SOAPRequestXML.append("<ws:getDetalleLlamadaBic>");
            SOAPRequestXML.append("<ws:Header>");
            SOAPRequestXML.append("<head:paisID>" + 1 +"</head:paisID>");
            SOAPRequestXML.append("<head:canalID>" + 1 +"</head:canalID>");
            SOAPRequestXML.append("<head:sucursalID>" + "V10018" +"</head:sucursalID>");
            SOAPRequestXML.append("<head:sistemaID>" + 23 +"</head:sistemaID>");
            SOAPRequestXML.append("<head:servicioID>" + 56 +"</head:servicioID>");
            SOAPRequestXML.append("</ws:Header>");
            SOAPRequestXML.append("<ws:vos>");
            SOAPRequestXML.append("<xsd:dniOrigen>"+dn+"</xsd:dniOrigen>");
            SOAPRequestXML.append("<xsd:fechaFin>"+fechaFin+"</xsd:fechaFin>");
            SOAPRequestXML.append("<xsd:fechaInicio>"+fechaInicial+"</xsd:fechaInicio>");
            SOAPRequestXML.append("<xsd:tipoEvento>"+tipoEvento+"</xsd:tipoEvento>");
            SOAPRequestXML.append("</ws:vos>");
            SOAPRequestXML.append("</ws:getDetalleLlamadaBic>");
            SOAPRequestXML.append("</soapenv:Body>");
            SOAPRequestXML.append("</soapenv:Envelope>");
		try 
		{
			Logger.write("     SOAPRequestXML         : " + "detalleConsumosETAK_LEGACY::  " + Utilerias.pintaLogRequest(SOAPRequestXML.toString()));
			SOAPManager oSOAPrequest 	= new SOAPManager();
			oSOAPrequest.createSOAPManager(url,new MensajeLogBean());

			sResponse = oSOAPrequest.sendRequestWithXML(SOAPRequestXML.toString(),new MensajeLogBean());

			Logger.write("     + Respuesta   detalleConsumosETAK_LEGACY +");
			Logger.write("       SOAPResponseXML  : " + Utilerias.pintaLogRequest(sResponse));
		} 
		catch (Exception e) 
		{
			throw new ServiceException(e.getMessage());
		}finally{
            SOAPRequestXML=null;
        }
		
		}else{
			try{
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace ws = fac.createOMNamespace("http://ws.detalleconsumo.telco.iusacell.com", "ws");
			OMNamespace xsd = fac.createOMNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd", "xsd");

			OMElement getDetalleLlamadaBic = fac.createOMElement("getDetalleLlamadaBic", ws);
			OMElement vos             = fac.createOMElement("vos", ws);
			OMElement dniOrigen       = fac.createOMElement("dniOrigen", xsd);
			OMElement fechaFinOM      = fac.createOMElement("fechaFin", xsd);
			OMElement fechaInicio     = fac.createOMElement("fechaInicio", xsd);
			OMElement tipoEventoOM    = fac.createOMElement("tipoEvento", xsd);
			
			dniOrigen.setText(dn);
			fechaFinOM.setText(fechaFin);
			fechaInicio.setText(fechaInicial);
			tipoEventoOM.setText(tipoEvento);
			vos.addChild(dniOrigen);
			vos.addChild(fechaFinOM);
			vos.addChild(fechaInicio);
			vos.addChild(tipoEventoOM);
			getDetalleLlamadaBic.addChild(vos);
			
			Logger.write("     xmlRequest             : " + Utilerias.pintaLogRequest(getDetalleLlamadaBic.toString()));
			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(urlConsumosSinTibco, getDetalleLlamadaBic);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
			
			}catch (Exception e) {
				throw new ServiceException(e.getMessage());
			}
		}
		return sResponse;
	}
	
	public String detalleConsumosETAK_LEGACY(final String dn, final String tipoEvento, final String fechaIni, final String fechaFin) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlDetalleConsumo");
		String consultaWSconsumosxTibco = "";
		String urlConsumosSinTibco = "";
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceDetalleConsumosETAK_LEGACYXFecha");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     tipoEvento             : " + tipoEvento);
		
		try{
			OracleProcedures oracle = new OracleProcedures();
			consultaWSconsumosxTibco = oracle.getValorParametro(119);
			urlConsumosSinTibco = oracle.getValorParametro(120);
		}catch (ServiceException e) {
			consultaWSconsumosxTibco = "0";
			urlConsumosSinTibco = "";
		}
		String sResponse			= "";
		Date datecon = null;
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSSZ");
		
		Calendar calStart = Calendar.getInstance();
		Calendar calStartFin = Calendar.getInstance();
		try{
			if(fechaFin != null && !fechaFin.equals("")){
				datecon = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(fechaIni);
				calStart.setTime(datecon);
			}

			if(fechaFin != null && !fechaFin.equals("")){
				datecon = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(fechaFin);
				calStartFin.setTime(datecon);
			}
			
			if(calStart.compareTo(calStartFin) >= 0){
				throw new Exception("fechaInicial no puede ser mayor a fechaFinal");
			}
			
		}catch (Exception e) {
			throw new ServiceException("Fecha invalida: " + e.getMessage());
		}
		
		Date dateInic = calStart.getTime();
		Date dateFinal = calStartFin.getTime();
		
		String fechaInicial="";
		String fechaTerminal="";
		
		if(fechaFin != null && !fechaFin.equals(""))
			fechaInicial=sdf.format(dateInic);
		
		fechaInicial = fechaInicial.substring(0, 10)+"T"+fechaInicial.substring(10, fechaInicial.length()-2)+":"+fechaInicial.substring(fechaInicial.length()-2, fechaInicial.length());
		
		
		if(fechaIni != null && !fechaIni.equals(""))
			fechaTerminal=sdf.format(dateFinal);
		
		fechaTerminal = fechaTerminal.substring(0, 10)+"T"+fechaTerminal.substring(10, fechaTerminal.length()-2)+":"+fechaTerminal.substring(fechaTerminal.length()-2, fechaTerminal.length());

		
		StringBuffer SOAPRequestXML= new StringBuffer();
		if(consultaWSconsumosxTibco.equals("1")){
            SOAPRequestXML.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.detalleconsumo.telco.iusacell.com\" xmlns:head=\"http://www.example.org/Header\" xmlns:xsd=\"http://vo.detalleconsumo.telco.iusacell.com/xsd\">");
            SOAPRequestXML.append("<soapenv:Header/>");
            SOAPRequestXML.append("<soapenv:Body>");
            SOAPRequestXML.append("<ws:getDetalleLlamadaBic>");
            SOAPRequestXML.append("<ws:Header>");
            SOAPRequestXML.append("<head:paisID>" + 1 +"</head:paisID>");
            SOAPRequestXML.append("<head:canalID>" + 1 +"</head:canalID>");
            SOAPRequestXML.append("<head:sucursalID>" + "V10018" +"</head:sucursalID>");
            SOAPRequestXML.append("<head:sistemaID>" + 23 +"</head:sistemaID>");
            SOAPRequestXML.append("<head:servicioID>" + 56 +"</head:servicioID>");
            SOAPRequestXML.append("</ws:Header>");
            SOAPRequestXML.append("<ws:vos>");
            SOAPRequestXML.append("<xsd:dniOrigen>"+dn+"</xsd:dniOrigen>");
            SOAPRequestXML.append("<xsd:fechaFin>"+fechaTerminal+"</xsd:fechaFin>");
            SOAPRequestXML.append("<xsd:fechaInicio>"+fechaInicial+"</xsd:fechaInicio>");
            SOAPRequestXML.append("<xsd:tipoEvento>"+tipoEvento+"</xsd:tipoEvento>");
            SOAPRequestXML.append("</ws:vos>");
            SOAPRequestXML.append("</ws:getDetalleLlamadaBic>");
            SOAPRequestXML.append("</soapenv:Body>");
            SOAPRequestXML.append("</soapenv:Envelope>");
		try 
		{
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(SOAPRequestXML.toString()));
			SOAPManager oSOAPrequest 	= new SOAPManager();
			oSOAPrequest.createSOAPManager(url,new MensajeLogBean());
			
			sResponse = oSOAPrequest.sendRequestWithXML(SOAPRequestXML.toString(),new MensajeLogBean());
						
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
		} 
		catch (Exception e) 
		{
			throw new ServiceException(e.getMessage());
		}finally{
            SOAPRequestXML=null;
        }
		
		}else{
			try{
				OMFactory fac = OMAbstractFactory.getOMFactory();
				OMNamespace ws = fac.createOMNamespace("http://ws.detalleconsumo.telco.iusacell.com", "ws");
				OMNamespace xsd = fac.createOMNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd", "xsd");

				OMElement getDetalleLlamadaBic = fac.createOMElement("getDetalleLlamadaBic", ws);
				OMElement vos             = fac.createOMElement("vos", ws);
				OMElement dniOrigen       = fac.createOMElement("dniOrigen", xsd);
				OMElement fechaFinOM      = fac.createOMElement("fechaFin", xsd);
				OMElement fechaInicio     = fac.createOMElement("fechaInicio", xsd);
				OMElement tipoEventoOM    = fac.createOMElement("tipoEvento", xsd);
				
				dniOrigen.setText(dn);
				fechaFinOM.setText(fechaFin);
				fechaInicio.setText(fechaInicial);
				tipoEventoOM.setText(tipoEvento);
				vos.addChild(dniOrigen);
				vos.addChild(fechaFinOM);
				vos.addChild(fechaInicio);
				vos.addChild(tipoEventoOM);
				getDetalleLlamadaBic.addChild(vos);
				
				Logger.write("     xmlRequest             : " + Utilerias.pintaLogRequest(getDetalleLlamadaBic.toString()));
				//********************************
				//Enviamos la peticion al servidor con AXIS2 - axis2
				//********************************
				SOAPManagerAxis2 send = new SOAPManagerAxis2();
				sResponse = send.send(urlConsumosSinTibco, getDetalleLlamadaBic);
				
				Logger.write("   + Respuesta              + ");
				Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
				
				}catch (Exception e) {
					throw new ServiceException(e.getMessage());
				}
		}
		return sResponse;
	}
}
