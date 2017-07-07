package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceFocalizacion {
	SOAPManager soapManager = null;
	
	public String focalizacion(final String dn) throws ServiceException
	{
		String url = ResourceAccess.getParametroFromXML("urlFocalizacion");
		StringBuffer sResponse = new StringBuffer();
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");		
		Logger.write("     user                   : -PROTEGIDO-");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : callServiceFocalizacion");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		
		StringBuffer SOAPRequestXML=new StringBuffer();
		
        SOAPRequestXML.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.server.iusacell.com/\">");
        SOAPRequestXML.append(" <soapenv:Header/>");
        SOAPRequestXML.append("     <soapenv:Body>");
        SOAPRequestXML.append("         <ws:consultaLineasMdn>");
        SOAPRequestXML.append("             <mdn>"+dn+"</mdn>");
        SOAPRequestXML.append("         </ws:consultaLineasMdn>");
        SOAPRequestXML.append("     </soapenv:Body>");
        SOAPRequestXML.append("</soapenv:Envelope>");
		
		Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(SOAPRequestXML.toString()));
		
		try 
		{
			SOAPManager oSOAPrequest = new SOAPManager();
			oSOAPrequest.createSOAPManager(url,new MensajeLogBean());
			sResponse.append(oSOAPrequest.sendRequestWithXML(SOAPRequestXML.toString(),new MensajeLogBean()));
			
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse.toString()));
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}finally{
            SOAPRequestXML=null;
        }
		return sResponse.toString();
	}
	
	public String focalizacionConsultaWallets(final String dn,final String sistema) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		String filtroWallets = "1";
		String url = ResourceAccess.getParametroFromXML("urlFocalizacion");
		StringBuffer sResponse = new StringBuffer();
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");		
		Logger.write("     user                   : -PROTEGIDO-");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : focalizacionConsultaWallets");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     sistema                : " + sistema);
		
		try{
			filtroWallets = oracle.getValorParametro(145);
		}
		catch (Exception e) {
			filtroWallets = "1";
		}
		
        StringBuffer SOAPRequestXML=new StringBuffer();
        SOAPRequestXML.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.server.iusacell.com/\">");
        SOAPRequestXML.append(  "<soapenv:Header/>");
        SOAPRequestXML.append(   "<soapenv:Body>");
        SOAPRequestXML.append(     "<ws:consultaWallet>");
        SOAPRequestXML.append(        "<mdn>"+dn+"</mdn>");
        SOAPRequestXML.append(        "<sistema>"+sistema+"</sistema>");
        SOAPRequestXML.append(         "<filtroWallet>"+filtroWallets+"</filtroWallet>");
        SOAPRequestXML.append(     "</ws:consultaWallet>");
        SOAPRequestXML.append(   "</soapenv:Body>");
        SOAPRequestXML.append("</soapenv:Envelope>");
		
		Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(SOAPRequestXML.toString()));
		
		try 
		{
			SOAPManager oSOAPrequest = new SOAPManager();
			oSOAPrequest.createSOAPManager(url,new MensajeLogBean());
			sResponse.append(oSOAPrequest.sendRequestWithXML(SOAPRequestXML.toString(),new MensajeLogBean()));
			
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse.toString()));
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}finally{
            SOAPRequestXML=null;
        }
		return sResponse.toString();
	}
	
	public String focalizacionConsultaSRSC(final String dn) throws ServiceException
	{

		String url = ResourceAccess.getParametroFromXML("urlFocalizacion");
		StringBuffer sResponse = new StringBuffer();
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");		
		Logger.write("     user                   : -PROTEGIDO-");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : focalizacionConsultaSRSC");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		
		
        StringBuffer SOAPRequestXML=new StringBuffer();
        SOAPRequestXML.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.server.iusacell.com/\">");
        SOAPRequestXML.append(  "<soapenv:Header/>");
        SOAPRequestXML.append(   "<soapenv:Body>");
        SOAPRequestXML.append(     "<ws:consultaSRSC>");
        SOAPRequestXML.append(        "<mdn>"+dn+"</mdn>");
        SOAPRequestXML.append(     "</ws:consultaSRSC>");
        SOAPRequestXML.append(   "</soapenv:Body>");
        SOAPRequestXML.append("</soapenv:Envelope>");
		
		Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(SOAPRequestXML.toString()));
		
		try 
		{
			SOAPManager oSOAPrequest = new SOAPManager();
			oSOAPrequest.createSOAPManager(url,new MensajeLogBean());
			sResponse.append(oSOAPrequest.sendRequestWithXML(SOAPRequestXML.toString(),new MensajeLogBean()));
			
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse.toString()));
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getLocalizedMessage());
		}finally{
            SOAPRequestXML=null;
        }
		return sResponse.toString();
	}
	
	public String consultaCompraServicios(final String idPlan, final String operador, final String tipoOferta) throws ServiceException
	{

		String url = ResourceAccess.getParametroFromXML("urlFocalizacionConsultaServicios");
		StringBuffer sResponse = new StringBuffer();
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");		
		Logger.write("     user                   : -PROTEGIDO-");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : consultaCompraServicios");
		Logger.write("     requesting             : " + new Date().toString());		


		StringBuffer SOAPRequestXML=new StringBuffer();
		SOAPRequestXML.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://consultaservicios.att.com/\">");
		SOAPRequestXML.append("   <soapenv:Header/>");
		SOAPRequestXML.append("   <soapenv:Body>");
		SOAPRequestXML.append("      <con:consultaCompraServicios>");
		SOAPRequestXML.append("         <consultaServicioRequestVO>");        	
		SOAPRequestXML.append("            <idPlan>"+idPlan+"</idPlan>");
		SOAPRequestXML.append("            <operador>"+operador+"</operador>");
		SOAPRequestXML.append("            <tipoOferta>"+tipoOferta+"</tipoOferta>");
		SOAPRequestXML.append("         </consultaServicioRequestVO>");
		SOAPRequestXML.append("      </con:consultaCompraServicios>");
		SOAPRequestXML.append("   </soapenv:Body>");
		SOAPRequestXML.append("</soapenv:Envelope>");

		Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(SOAPRequestXML.toString()));

		try 
		{
			SOAPManager oSOAPrequest = new SOAPManager();
			oSOAPrequest.createSOAPManager(url,new MensajeLogBean());
			sResponse.append(oSOAPrequest.sendRequestWithXML(SOAPRequestXML.toString(),new MensajeLogBean()));

			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse.toString()));
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getLocalizedMessage());
		}finally{
			SOAPRequestXML=null;
		}
		return sResponse.toString();
	}
}
