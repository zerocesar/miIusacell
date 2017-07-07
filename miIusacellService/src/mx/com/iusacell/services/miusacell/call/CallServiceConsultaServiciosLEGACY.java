package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

import mx.com.iusacell.services.SOAPManager.SOAPManagerAxis2;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceConsultaServiciosLEGACY {
	public String consultaServiciosLEGACY(final String dn) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlObtieneServiciosLEGACY");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : consultaServiciosLEGACY");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);

		String sResponse = "";
				
		try
		{			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace isc = fac.createOMNamespace("http://BSConsultas/ISConsultasPrepago", "isc");
			OMNamespace xsi = fac.createOMNamespace("http://www.w3.org/2001/XMLSchema-instance", "xsi");
			OMNamespace empty = fac.createOMNamespace("","");
			
			OMAttribute attr = fac.createOMAttribute("nil", xsi, "true");

			OMElement consultaInformacionPrepago = fac.createOMElement("consultaInformacionPrepago", isc);
			OMElement mdn         	= fac.createOMElement("mdn", empty);
			OMElement esn      		= fac.createOMElement("esn", empty);
			OMElement min      		= fac.createOMElement("min", empty);
			OMElement isActiva      = fac.createOMElement("isActiva", empty);
			OMElement filtro      	= fac.createOMElement("filtro", empty);
			OMElement id      		= fac.createOMElement("id", empty);
			OMElement id2      		= fac.createOMElement("id", empty);
			OMElement idSistema     = fac.createOMElement("idSistema", empty);
			OMElement iccid    		= fac.createOMElement("iccid", empty);
			
			esn.addAttribute(attr);
			min.addAttribute(attr);
			iccid.addAttribute(attr);
			
			mdn.setText(dn);
			esn.setText("");
			min.setText("");
			isActiva.setText("1");
			id.setText("1");
			id2.setText("2");
			idSistema.setText("0");
			iccid.setText("");
			
			filtro.addChild(id);
			filtro.addChild(id2);
			
			consultaInformacionPrepago.addChild(mdn);
			consultaInformacionPrepago.addChild(esn);
			consultaInformacionPrepago.addChild(min);
			consultaInformacionPrepago.addChild(isActiva);
			consultaInformacionPrepago.addChild(filtro);
			consultaInformacionPrepago.addChild(idSistema);
			consultaInformacionPrepago.addChild(iccid);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(consultaInformacionPrepago.toString()));
			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, consultaInformacionPrepago);
			 
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			sResponse = "";
			throw new ServiceException(e.getMessage());
		}
		return sResponse;
	}
	
	public String consultaServiciosLEGACYStatus(final String dn, final String status) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlObtieneServiciosLEGACY");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : consultaServiciosLEGACY");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);

		String sResponse = "";
				
		try
		{			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace isc = fac.createOMNamespace("http://BSConsultas/ISConsultasPrepago", "isc");
			OMNamespace xsi = fac.createOMNamespace("http://www.w3.org/2001/XMLSchema-instance", "xsi");
			OMNamespace empty = fac.createOMNamespace("","");
			
			OMAttribute attr = fac.createOMAttribute("nil", xsi, "true");

			OMElement consultaInformacionPrepago = fac.createOMElement("consultaInformacionPrepago", isc);
			OMElement mdn         	= fac.createOMElement("mdn", empty);
			OMElement esn      		= fac.createOMElement("esn", empty);
			OMElement min      		= fac.createOMElement("min", empty);
			OMElement isActiva      = fac.createOMElement("isActiva", empty);
			OMElement filtro      	= fac.createOMElement("filtro", empty);
			OMElement id      		= fac.createOMElement("id", empty);
			OMElement id2      		= fac.createOMElement("id", empty);
			OMElement idSistema     = fac.createOMElement("idSistema", empty);
			OMElement iccid    		= fac.createOMElement("iccid", empty);
			
			esn.addAttribute(attr);
			min.addAttribute(attr);
			iccid.addAttribute(attr);
			
			mdn.setText(dn);
			esn.setText("");
			min.setText("");
			isActiva.setText(status);
			id.setText("1");
			id2.setText("2");
			idSistema.setText("0");
			iccid.setText("");
			
			filtro.addChild(id);
			filtro.addChild(id2);
			
			consultaInformacionPrepago.addChild(mdn);
			consultaInformacionPrepago.addChild(esn);
			consultaInformacionPrepago.addChild(min);
			consultaInformacionPrepago.addChild(isActiva);
			consultaInformacionPrepago.addChild(filtro);
			consultaInformacionPrepago.addChild(idSistema);
			consultaInformacionPrepago.addChild(iccid);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(consultaInformacionPrepago.toString()));
			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, consultaInformacionPrepago);
			 
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			sResponse = "";
			throw new ServiceException(e.getMessage());
		}
		return sResponse;
	}
}
