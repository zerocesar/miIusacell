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

public class CallServiceCambioStatus {

	public  String cambioStatus(final String contractID, final String desactivacionType, final String mdn, final String reasonId, final String stringDatos) throws ServiceException
	{
		String url = ResourceAccess.getParametroFromXML("urlCambioStatus");
		Logger.write(" >>> L l a m a d a   W e b  S r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     idLinea                : " + contractID);
		Logger.write("     desactivacion          : " + mdn);
		Logger.write("     reasonId               : " + reasonId);
		Logger.write("     stringDatos            : " + stringDatos);

		String sResponse = "";

		try
		{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://ws.cambiostatus.mx.iusacell.com", "ws");
			OMNamespace xsdNs = fac.createOMNamespace("http://vo.cambiostatus.mx.iusacell.com/xsd", "xsd");

			OMElement cambioStatusOM = fac.createOMElement("cambioStatus", wsNs);
			OMElement contratoVOOM   = fac.createOMElement("contratoVO", wsNs);
			OMElement contactIDOM    = fac.createOMElement("contractID", xsdNs);
			OMElement desactivacionTypeOM  = fac.createOMElement("desactivacionType", xsdNs);
			OMElement mdnOM                = fac.createOMElement("mdn", xsdNs);
			OMElement reasonIdOM      = fac.createOMElement("reasonId", xsdNs);

			contactIDOM.setText(contractID);
			desactivacionTypeOM.setText(desactivacionType);
			mdnOM.setText(mdn);
			reasonIdOM.setText(reasonId);
			contratoVOOM.addChild(contactIDOM);
			contratoVOOM.addChild(desactivacionTypeOM);
			contratoVOOM.addChild(mdnOM);
			contratoVOOM.addChild(reasonIdOM);
			cambioStatusOM.addChild(contratoVOOM);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(cambioStatusOM.toString()));

			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, cambioStatusOM);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return sResponse;
	}
}
