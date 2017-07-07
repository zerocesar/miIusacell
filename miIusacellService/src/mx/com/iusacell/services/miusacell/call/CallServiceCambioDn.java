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

public class CallServiceCambioDn {
	
	public String MdnPospago(final String dn, final String dnNuevo, final String usuario) throws ServiceException
	{
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse="";
		String retencion = "r";
		String url = ResourceAccess.getParametroFromXML("urlCambioDN");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : MdnPospago");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     dnNuevo                : " + dnNuevo);
		Logger.write("     retencion              : " + retencion);
		Logger.write("     usuario                : " + usuario);
		
		try {
			
		    sXMLRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:cam=\"http://cambiodn.telco.iusacell.com.mx\">");
		    sXMLRequest.append(   "<soap:Header/>");
		    sXMLRequest.append(   "<soap:Body>");
		    sXMLRequest.append(      "<cam:cambioDNPospagoGSM>");
		    sXMLRequest.append(         "<cam:dn>"+dn+"</cam:dn>");
		    sXMLRequest.append(         "<cam:dnNuevo>"+dnNuevo+"</cam:dnNuevo>");
		    sXMLRequest.append(         "<cam:retencion>"+retencion+"</cam:retencion>");
		    sXMLRequest.append(         "<cam:usuario>"+usuario+"</cam:usuario>");
		    sXMLRequest.append(      "</cam:cambioDNPospagoGSM>");
		    sXMLRequest.append(   "</soap:Body>");
		    sXMLRequest.append("</soap:Envelope>");
			
			Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(sXMLRequest.toString()));
			SOAPManager soapManager = new SOAPManager();
			soapManager.createSOAPManager(url,new MensajeLogBean());
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		finally{
		    sXMLRequest  = null;
		}
		
		return sResponse;
	}
	
	public String MdnPrepago(final String idLinea, final String nuevoDN) throws ServiceException
	{
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse="";
		String url = ResourceAccess.getParametroFromXML("urlCambioDN");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : MdnPrepago");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     idLinea                : " + idLinea);
		Logger.write("     nuevoDN                : " + nuevoDN);
		
		try {
		    sXMLRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:cam=\"http://cambiodn.telco.iusacell.com.mx\">");
		    sXMLRequest.append(   "<soap:Header/>");
		    sXMLRequest.append(   "<soap:Body>");
		    sXMLRequest.append(      "<cam:cambioMdnPrepagoGSM>");
		    sXMLRequest.append(         "<cam:idLinea>"+idLinea+"</cam:idLinea>");
		    sXMLRequest.append(         "<cam:nuevoDn>"+nuevoDN+"</cam:nuevoDn>");
		    sXMLRequest.append(      "</cam:cambioMdnPrepagoGSM>");
		    sXMLRequest.append(   "</soap:Body>");
		    sXMLRequest.append("</soap:Envelope>");
			
			Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(sXMLRequest.toString()));
			SOAPManager soapManager = new SOAPManager();
			soapManager.createSOAPManager(url,new MensajeLogBean());
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
   		    
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		finally{
		    sXMLRequest = null;
		}
		
        return sResponse;		
	}
	
	
	public String MdnPospagoA2(final String dn, final String dnNuevo, final String usuario) throws ServiceException
	{
		String sResponse="";
		String retencion = "r";
		String url = ResourceAccess.getParametroFromXML("urlCambioDN");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : MdnPospago");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     dnNuevo                : " + dnNuevo);
		Logger.write("     retencion              : " + retencion);
		Logger.write("     usuario                : " + usuario);
		
		try {
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://cambiodn.telco.iusacell.com.mx", "cam");
			
			OMElement cambioDNPospagoGSM = fac.createOMElement("cambioDNPospagoGSM", wsNs);
			OMElement Dn         = fac.createOMElement("dn", wsNs);
			OMElement DnNuevo    = fac.createOMElement("dnNuevo", wsNs);
			OMElement Retencion  = fac.createOMElement("retencion", wsNs);
			OMElement Usuario    = fac.createOMElement("usuario", wsNs);
			
			cambioDNPospagoGSM.addChild(Dn);
			cambioDNPospagoGSM.addChild(DnNuevo);
			cambioDNPospagoGSM.addChild(Retencion);
			cambioDNPospagoGSM.addChild(Usuario);
			
			Dn.setText(dn);
			DnNuevo.setText(dnNuevo);
			Retencion.setText(retencion);
			Usuario.setText(usuario);
			
			Logger.write("     SOAPRequestXML         : "  + Utilerias.pintaLogRequest(cambioDNPospagoGSM.toString()));

			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, cambioDNPospagoGSM);
			
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return sResponse;
	}
	
	public String MdnPrepagoA2(final String idLinea, final String nuevoDN) throws ServiceException
	{
		String sResponse="";
		String url = ResourceAccess.getParametroFromXML("urlCambioDN");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : MdnPrepago");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     idLinea                : " + idLinea);
		Logger.write("     nuevoDN                : " + nuevoDN);
		
		try {
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace wsNs = fac.createOMNamespace("http://cambiodn.telco.iusacell.com.mx", "cam");
			
			OMElement cambioMdnPrepagoGSM = fac.createOMElement("cambioMdnPrepagoGSM", wsNs);
			OMElement IdLinea   = fac.createOMElement("idLinea", wsNs);
			OMElement NuevoDn   = fac.createOMElement("nuevoDn", wsNs);
			
			cambioMdnPrepagoGSM.addChild(IdLinea);
			cambioMdnPrepagoGSM.addChild(NuevoDn);
			
			IdLinea.setText(idLinea);
			NuevoDn.setText(nuevoDN);
			
			Logger.write("     SOAPRequestXML         : "  + Utilerias.pintaLogRequest(cambioMdnPrepagoGSM.toString()));

			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, cambioMdnPrepagoGSM);
			
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));

		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
        return sResponse;		
	}
	
}
