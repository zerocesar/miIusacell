package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

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
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceServiciosContratos {
	
	public String serviciosContratados(final String coID) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlServiceContratados");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceServiciosContratos - serviciosContratados");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     coID                   : " + coID);
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		try
		{
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://ws.sqldispatcher.middleware.iusacell.com", "ws");
			OMElement ServiciosContratados = fac.createOMElement("getServiciosContratados", wsNs);
			OMElement VarcoID = fac.createOMElement("coID", wsNs);
		 
			ServiciosContratados.addChild(VarcoID);
			VarcoID.setText(coID);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(ServiciosContratados.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, ServiciosContratados);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e){
			sResponse = "";
			throw new ServiceException(e.getMessage());
		}
		return sResponse;
	}
	
	public String serviciosXContratar(final String coID, final String tmCode) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlServiceContratados");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceServiciosContratos - serviciosXContratar");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     coID                   : " + coID);
		Logger.write("     tmCode                 : " + tmCode);
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		try
		{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://ws.sqldispatcher.middleware.iusacell.com", "ws");
			OMElement ServiciosXContratar = fac.createOMElement("getServiciosAContratar", wsNs);
			OMElement VarcoID = fac.createOMElement("coID", wsNs);
			OMElement VartmCode = fac.createOMElement("tmCode", wsNs);
			
			ServiciosXContratar.addChild(VarcoID);
			ServiciosXContratar.addChild(VartmCode);
			VarcoID.setText(coID);
			VartmCode.setText(tmCode);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(ServiciosXContratar.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, ServiciosXContratar);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e){
			sResponse = "";
			throw new ServiceException(e.getMessage());			
		}
		return sResponse;
	}
	
	public String getStatusLineaPospago(final String dnNum) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlServiceContratados");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceServiciosContratos - getStatusLineaPospago");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dnNum);
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		try
		{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://ws.sqldispatcher.middleware.iusacell.com", "ws");
			OMNamespace empty = fac.createOMNamespace("", "");
			OMElement getResultadoBusqueda = fac.createOMElement("getResultadoBusqueda", wsNs);
			OMElement busqueda = fac.createOMElement("busqueda", empty);
			OMElement dn = fac.createOMElement("dn", empty);
			
			dn.setText(dnNum);
			busqueda.addChild(dn);
			getResultadoBusqueda.addChild(busqueda);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(getResultadoBusqueda.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, getResultadoBusqueda);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e){
			sResponse = "";
			throw new ServiceException(e.getMessage());			
		}
		return sResponse;
	}
	
	public String getDatosFacturacion(final String customerid) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlServiceContratados");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceServiciosContratos - getDatosFacturacion");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     customerid             : " + customerid);
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		try
		{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://ws.sqldispatcher.middleware.iusacell.com", "ws");
			OMNamespace empty = fac.createOMNamespace("", "");
			OMElement getDatosFacturacion = fac.createOMElement("getDatosFacturacion", wsNs);
			OMElement customerID = fac.createOMElement("customerID", empty);
			
			customerID.setText(customerid);
			getDatosFacturacion.addChild(customerID);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(getDatosFacturacion.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, getDatosFacturacion);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e){
			sResponse = "";
			throw new ServiceException(e.getMessage());			
		}
		return sResponse;
	}
	
	public String getUltimasFacturas(final String customerid, final String numeroFacturas) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlServiceContratados");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceServiciosContratos - getUltimasFacturas");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     customerid             : " + customerid);
		Logger.write("     numeroFacturas         : " + numeroFacturas);
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		try
		{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://ws.sqldispatcher.middleware.iusacell.com", "ws");
			OMNamespace empty = fac.createOMNamespace("", "");
			OMElement getUltimasFacturas = fac.createOMElement("getUltimasFacturas", wsNs);
			OMElement customerID = fac.createOMElement("customerID", empty);
			OMElement numFacturas = fac.createOMElement("numFacturas", empty);
			
			customerID.setText(customerid);
			numFacturas.setText(numeroFacturas);
			getUltimasFacturas.addChild(customerID);
			getUltimasFacturas.addChild(numFacturas);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(getUltimasFacturas.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, getUltimasFacturas);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e){
			sResponse = "";
			throw new ServiceException(e.getMessage());			
		}
		return sResponse;
	}
	
	public String getDatosCliente(final String customerid) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlServiceContratados");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceServiciosContratos - getDatosCliente");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     customerid             : " + customerid);
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		try
		{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://ws.sqldispatcher.middleware.iusacell.com", "ws");
			OMNamespace empty = fac.createOMNamespace("", "");
			OMElement getUltimasFacturas = fac.createOMElement("getDatosCliente", wsNs);
			OMElement customerID = fac.createOMElement("customerID", empty);
			
			customerID.setText(customerid);
			getUltimasFacturas.addChild(customerID);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(getUltimasFacturas.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, getUltimasFacturas);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e){
			sResponse = "";
			throw new ServiceException(e.getMessage());			
		}
		return sResponse;
	}
	
	public String getResultadoBusqueda(final String cuenta) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlServiceContratados");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceServiciosContratos - getResultadoBusqueda");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     cuenta                 : " + cuenta);
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		try
		{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://ws.sqldispatcher.middleware.iusacell.com", "ws");
			OMNamespace empty = fac.createOMNamespace("", "");
			OMElement getResultadoBusqueda = fac.createOMElement("getResultadoBusqueda", wsNs);
			
			
			
			OMElement busqueda = fac.createOMElement("busqueda", empty);
			OMElement cuentaID = fac.createOMElement("cuenta", empty);
			
			cuentaID.setText(cuenta);
			busqueda.addChild(cuentaID);
			getResultadoBusqueda.addChild(busqueda);
			
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(getResultadoBusqueda.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, getResultadoBusqueda);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e){
			sResponse = "";
			throw new ServiceException(e.getMessage());			
		}
		return sResponse;
	}
	public String getDatosLinea(final String coID) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlServiceContratados");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceServiciosContratos - getDatosLineaPospago");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     coID                     : " + coID);
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		try
		{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://ws.sqldispatcher.middleware.iusacell.com", "ws");
			OMNamespace empty = fac.createOMNamespace("", "");
			OMElement getDatosLinea = fac.createOMElement("getDatosLinea", wsNs);
			OMElement contrato = fac.createOMElement("coID", empty);
			
			contrato.setText(coID);
			getDatosLinea.addChild(contrato);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(getDatosLinea.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, getDatosLinea);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e){
			sResponse = "";
			throw new ServiceException(e.getMessage());			
		}
		return sResponse;
	}
}
