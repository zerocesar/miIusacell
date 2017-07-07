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

public class CallServiceAltaBajaServicios{
	
	public String  altaBajaServicios(final String coId, final String dnUsa,final String continenteFavortio, final String servicios, final String tmCode, final String type, final String userMod) throws ServiceException 
	{
		
		String url = ResourceAccess.getParametroFromXML("urlAltaServiciosLEGACY");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : altaBajaServicios");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     coId                   : " + coId);
		Logger.write("     dnUsa                  : " + dnUsa);
		Logger.write("     continenteFavortio     : " + continenteFavortio);
		Logger.write("     servicios              : " + servicios);
		Logger.write("     tmCode                 : " + tmCode);
		Logger.write("     type                   : " + type);
		Logger.write("     userMod                : " + userMod);

		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		
		
		try{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace ws = fac.createOMNamespace("http://ws.serviciosBSCS.middleware.iusacell.com", "ws");
			OMNamespace vo = fac.createOMNamespace("http://vo.serviciosBSCS.middleware.iusacell.com", "vo");

			OMElement altaBajaServicios = fac.createOMElement("altaBajaServicios", ws);
			OMElement voElement         = fac.createOMElement("vo", ws);
			OMElement coIDElement       = fac.createOMElement("coID", vo);
			OMElement serviciosElement  = fac.createOMElement("servicios", vo);
			OMElement tmCodeElement     = fac.createOMElement("tmCode", vo);
			OMElement typeElement       = fac.createOMElement("type", vo);
			OMElement userModElement    = fac.createOMElement("userMod", vo);

			coIDElement.setText(coId);
			serviciosElement.setText(servicios);
			tmCodeElement.setText(tmCode);
			typeElement.setText(type);
			userModElement.setText(userMod);

			voElement.addChild(coIDElement);
			voElement.addChild(serviciosElement);
			voElement.addChild(tmCodeElement);
			voElement.addChild(typeElement);
			voElement.addChild(userModElement);
			altaBajaServicios.addChild(voElement);
			
			Logger.write("     xmlRequest             : " + Utilerias.pintaLogRequest(altaBajaServicios.toString()));
			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, altaBajaServicios);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());	
		}

		return sResponse;
	}
}
