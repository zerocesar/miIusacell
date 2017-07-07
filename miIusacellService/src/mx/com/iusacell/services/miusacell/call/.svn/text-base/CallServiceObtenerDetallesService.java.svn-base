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

public class CallServiceObtenerDetallesService {
	
	public String obtenerDetallesServiceClass(final String idServiceClass) throws ServiceException
	{
		String url = ResourceAccess.getParametroFromXML("obtenerServiceClass");
		String sResponse = "";
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");		
		Logger.write("     user                   : -PROTEGIDO-");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : obtenerDetallesServiceClass");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     ServiceClass           : " + idServiceClass);
		
		try 
		{
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace xsdNs = fac.createOMNamespace("http://prepago.iusacell.com.mx/xsd", "xsd");
			OMNamespace prepNs = fac.createOMNamespace("http://prepago.iusacell.com.mx", "prep");
			
			OMElement obtenerDetallesServicesClass = fac.createOMElement("obtenerDetallesServiceClass", prepNs);
			OMElement serviceClass                 = fac.createOMElement("serviceClass", prepNs);
			OMElement id                           = fac.createOMElement("id", xsdNs);
			
			obtenerDetallesServicesClass.addChild(serviceClass);
			serviceClass.addChild(id);
			id.setText(idServiceClass);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(obtenerDetallesServicesClass.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			
			sResponse = send.send(url, obtenerDetallesServicesClass);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
			
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return sResponse.toString();
	}
}
