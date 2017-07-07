package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

import mx.com.iusacell.services.SOAPManager.SOAPManagerAxis2;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceActivacion {
	public String consultaClavesSensales() throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlsServiceActivacion");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceActivacion");
		Logger.write("     requesting             : " + new Date().toString());
		String sResponse = "";
		try
		{			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://ws.telco.iusacell.com", "ws");
			OMNamespace xsdNs = fac.createOMNamespace("http://dto.telco.iusacell.com/xsd", "xsd");

			OMElement ObtenerCiudades = fac.createOMElement("obtenerCiudades", wsNs);
			OMElement Usuario         = fac.createOMElement("usuario", wsNs);
			OMElement Usuarioxsd      = fac.createOMElement("usuario", xsdNs);

			ObtenerCiudades.addChild(Usuario);
			Usuario.addChild(Usuarioxsd);
			Usuarioxsd.setText("PVS");
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(ObtenerCiudades.toString()));
			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, ObtenerCiudades);

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
	
	public String consultaNumerosPrepago(final String dn) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlsServiceActivacion");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : consultaNumerosPrepago");
		Logger.write("     requesting             : " + new Date().toString());
		String sResponse = "";
		try
		{			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://ws.telco.iusacell.com", "ws");
			OMNamespace xsdNs = fac.createOMNamespace("http://dto.telco.iusacell.com/xsd", "xsd");

			OMElement ObtenerCiudades = fac.createOMElement("getNumberByMdn", wsNs);
			OMElement Numero         = fac.createOMElement("numero", wsNs);
			OMElement Usuario         = fac.createOMElement("usuario", wsNs);
			
			OMElement mdn      = fac.createOMElement("mdn", xsdNs);
			OMElement Usuarioxsd      = fac.createOMElement("usuario", xsdNs);
			
			mdn.setText(dn);
			Usuarioxsd.setText("PVS");
			Usuario.addChild(Usuarioxsd);
			Numero.addChild(mdn);
			ObtenerCiudades.addChild(Numero);
			ObtenerCiudades.addChild(Usuario);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(ObtenerCiudades.toString()));
			
			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, ObtenerCiudades);
			 
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
