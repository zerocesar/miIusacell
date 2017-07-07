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

public class CallServiceCambioMdn {
	
	public String cambioMdn(final String dnNuevo, final String minNuevo, final String idLinea, final String idCiudad, final String motCambio) throws ServiceException {
				
		String url = ResourceAccess.getParametroFromXML("urlCambioMDN");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");		
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CambioMDN");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dnNuevo                : " + dnNuevo);
		Logger.write("     minNuevo               : " + minNuevo);
		Logger.write("     idLinea                : " + idLinea);
		Logger.write("     idCiudad               : " + idCiudad);
		Logger.write("     motCambio              : " + motCambio);
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		try{
			
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://BSCambioMDN/ICambioMDN", "icam");
			OMNamespace xsdNs = fac.createOMNamespace("", "");

			OMElement cambioMDN = fac.createOMElement("cambioMDN", wsNs);
			
			OMElement dnNuevoVo   = fac.createOMElement("dnNuevo", xsdNs);
			OMElement minNuevoVo  = fac.createOMElement("minNuevo", xsdNs);
			OMElement idLineaVo   = fac.createOMElement("idLinea", xsdNs);
			OMElement idCiudadVo  = fac.createOMElement("idCiudad", xsdNs);
			OMElement motivoCambioVo = fac.createOMElement("motivoCambio", xsdNs);

			OMElement usuario  = fac.createOMElement("vUsuario", xsdNs);

			cambioMDN.addChild(dnNuevoVo);
			cambioMDN.addChild(minNuevoVo);
			cambioMDN.addChild(idLineaVo);
			cambioMDN.addChild(idCiudadVo);
			cambioMDN.addChild(motivoCambioVo);
			cambioMDN.addChild(usuario);
			
			dnNuevoVo.setText(dnNuevo);
			minNuevoVo.setText(minNuevo);
			idLineaVo.setText(idLinea);
			idCiudadVo.setText(idCiudad);
			motivoCambioVo.setText(motCambio);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(cambioMDN.toString()));
			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, cambioMDN);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		
		return sResponse;
	}

}
