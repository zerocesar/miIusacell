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

public class CallServiceCompraBundle {

	public String  compraServicioLegacyPrepago(final String dn, final String idServicio, final String origen) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlAltaServiciosLEGACYPrepago");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceCompraBundle");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     idServicio             : " + idServicio);
		Logger.write("     origen                 : " + origen);

		String sResponse = "";

		try
		{
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace gwb = fac.createOMNamespace("http://gwBundleWS/", "gwb");
			OMNamespace empty = fac.createOMNamespace("", "");

			OMElement bundleActivation = fac.createOMElement("bundleActivation", gwb);
			OMElement channel         = fac.createOMElement("channel", empty);
			OMElement mobileNumber         = fac.createOMElement("mobileNumber", empty);
			OMElement keyword         = fac.createOMElement("keyword", empty);
			OMElement salesDetail         = fac.createOMElement("salesDetail", empty);
			OMElement charging         = fac.createOMElement("charging", empty);

			channel.setText(origen);
			mobileNumber.setText(dn);
			keyword.setText(idServicio);
			salesDetail.setText("6117085#200196");
			charging.setText("1");
			
			bundleActivation.addChild(channel);
			bundleActivation.addChild(mobileNumber);
			bundleActivation.addChild(keyword);
			bundleActivation.addChild(salesDetail);
			bundleActivation.addChild(charging);
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(bundleActivation.toString()));
			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, bundleActivation);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			Logger.write("     Detail        : (Exception) " + e.getMessage());
			throw new ServiceException("Ocurrio un Error");
		}
		return sResponse;
	}
	
	public String consultaServicioLegacyPrepagoBundle(final String dn) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlAltaServiciosLEGACYPrepago");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceGetActiveBundle");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);

		String sResponse = "";

		try
		{
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace gwb = fac.createOMNamespace("http://gwBundleWS/", "gwb");
			OMNamespace empty = fac.createOMNamespace("", "");

			OMElement getActiveBundle = fac.createOMElement("getActiveBundle", gwb);
			OMElement mobileNumber     = fac.createOMElement("mobileNumber", empty);

			mobileNumber.setText(dn);
			getActiveBundle.addChild(mobileNumber);

			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(getActiveBundle.toString()));
			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, getActiveBundle);
						 
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
