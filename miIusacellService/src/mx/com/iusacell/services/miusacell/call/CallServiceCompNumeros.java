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

public class CallServiceCompNumeros {
	
	public String doReserveMdn(final String idRegion, final String cveEdoCEN, final String cveMunicipioCEN, final String cvePobCEN, final String idSistemaFact, final String idTipoPago, final String idModalidad, final String idOperador, final String cveServicio, final String cveSubservicio, final String tecnologia, final String usuario) throws ServiceException {
	
		String url = ResourceAccess.getParametroFromXML("urlCompNumeros");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : doReserveMdn");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     idRegion               : " + idRegion);
		Logger.write("     CveEdoCEN              : " + cveEdoCEN);
		Logger.write("     CveMunicipioCEN        : " + cveMunicipioCEN);
		Logger.write("     CvePobCEN              : " + cvePobCEN);
		Logger.write("     idSistemaFact          : " + idSistemaFact);
		Logger.write("     idTipoPago             : " + idTipoPago);
		Logger.write("     idModalidad            : " + idModalidad);
		Logger.write("     idOperador             : " + idOperador);
		Logger.write("     cveServicio            : " + cveServicio);
		Logger.write("     cveSubservicio         : " + cveSubservicio);
		Logger.write("     Tecnologia             : " + tecnologia);
		Logger.write("     Usuario                : " + usuario);
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse="";
				
		try{
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace rmi = fac.createOMNamespace("http://rmi.numeros.mindbits.com", "rmi");
			OMNamespace wsNs = fac.createOMNamespace("", "");

			OMElement doReserveMdn = fac.createOMElement("doReserveMdn", rmi);
			OMElement VoidRegion = fac.createOMElement("idRegion", wsNs);
			OMElement VoCveEdoCEN = fac.createOMElement("CveEdoCEN", wsNs);
			OMElement VoCveMunicipioCEN = fac.createOMElement("CveMunicipioCEN", wsNs);
			OMElement VoCvePobCEN = fac.createOMElement("CvePobCEN", wsNs);
			OMElement VoidSistemaFact = fac.createOMElement("idSistemaFact", wsNs);
			OMElement VoidTipoPago = fac.createOMElement("idTipoPago", wsNs);
			OMElement VoidModalidad = fac.createOMElement("idModalidad", wsNs);
			OMElement VoidOperador = fac.createOMElement("idOperador", wsNs);
			OMElement VocveServicio = fac.createOMElement("cveServicio", wsNs);
			OMElement VocveSubservicio = fac.createOMElement("cveSubservicio", wsNs);
			OMElement Votecnologia = fac.createOMElement("Tecnologia", wsNs);
			OMElement Vousuario = fac.createOMElement("Usuario", wsNs);
			
			VoidRegion.setText(idRegion);
			VoCveEdoCEN.setText(cveEdoCEN);
			VoCveMunicipioCEN.setText(cveMunicipioCEN);
			VoCvePobCEN.setText(cvePobCEN);
			VoidSistemaFact.setText(idSistemaFact);
			VoidTipoPago.setText(idTipoPago);
			VoidModalidad.setText(idModalidad);
			VoidOperador.setText(idOperador);
			VocveServicio.setText(cveServicio);
			VocveSubservicio.setText(cveSubservicio);
			Votecnologia.setText(tecnologia);
			Vousuario.setText(usuario);
			
			
			doReserveMdn.addChild(VoidRegion);
			doReserveMdn.addChild(VoCveEdoCEN);
			doReserveMdn.addChild(VoCveMunicipioCEN);
			doReserveMdn.addChild(VoCvePobCEN);
			doReserveMdn.addChild(VoidSistemaFact);
			doReserveMdn.addChild(VoidTipoPago);
			doReserveMdn.addChild(VoidModalidad);
			doReserveMdn.addChild(VoidOperador);
			doReserveMdn.addChild(VocveServicio);
			doReserveMdn.addChild(VocveSubservicio);
			doReserveMdn.addChild(Votecnologia);
			doReserveMdn.addChild(Vousuario);
			
			Logger.write("     xmlRequest             : " + Utilerias.pintaLogRequest(doReserveMdn.toString()));

			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, doReserveMdn);
			 
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		
		return sResponse;
	}
	
	
	public String doChangeMdn(final String mdnOri, final String mdnDes,final String idRegion, final String cveEdoCEN, final String cveMunicipioCEN, final String cvePobCEN, final String idSistemaFact, final String idTipoPago, final String idModalidad, final String idOperador, final String cveServicio, final String cveSubservicio, final String tecnologia, final String usuario) throws ServiceException {
	
		String url = ResourceAccess.getParametroFromXML("urlCompNumeros");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : doChangeMdn");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     mdnOri                 : " + mdnOri);
		Logger.write("     mdnDes                 : " + mdnDes);
		Logger.write("     idRegion               : " + idRegion);
		Logger.write("     CveEdoCEN              : " + cveEdoCEN);
		Logger.write("     CveMunicipioCEN        : " + cveMunicipioCEN);
		Logger.write("     CvePobCEN              : " + cvePobCEN);
		Logger.write("     idSistemaFact          : " + idSistemaFact);
		Logger.write("     idTipoPago             : " + idTipoPago);
		Logger.write("     idModalidad            : " + idModalidad);
		Logger.write("     idOperador             : " + idOperador);
		Logger.write("     cveServicio            : " + cveServicio);
		Logger.write("     cveSubservicio         : " + cveSubservicio);
		Logger.write("     Tecnologia             : " + tecnologia);
		Logger.write("     Usuario                : " + usuario);
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse="";

		try{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace rmi = fac.createOMNamespace("http://rmi.numeros.mindbits.com", "rmi");
			OMNamespace wsNs = fac.createOMNamespace("", "");

			OMElement doChangeMdn = fac.createOMElement("doChangeMdn", rmi);
			
			OMElement VomdnOri = fac.createOMElement("mdnOri", wsNs);
			OMElement VomdnDes = fac.createOMElement("mdnDes", wsNs);
			OMElement VoidRegion = fac.createOMElement("idRegion", wsNs);
			OMElement VoCveEdoCEN = fac.createOMElement("CveEdoCEN", wsNs);
			OMElement VoCveMunicipioCEN = fac.createOMElement("CveMunicipioCEN", wsNs);
			OMElement VoCvePobCEN = fac.createOMElement("CvePobCEN", wsNs);
			OMElement VoidSistemaFact = fac.createOMElement("idSistemaFact", wsNs);
			OMElement VoidTipoPago = fac.createOMElement("idTipoPago", wsNs);
			OMElement VoidModalidad = fac.createOMElement("idModalidad", wsNs);
			OMElement VoidOperador = fac.createOMElement("idOperador", wsNs);
			OMElement VocveServicio = fac.createOMElement("cveServicio", wsNs);
			OMElement VocveSubservicio = fac.createOMElement("cveSubservicio", wsNs);
			OMElement Votecnologia = fac.createOMElement("Tecnologia", wsNs);
			OMElement Vousuario = fac.createOMElement("Usuario", wsNs);
			
			VomdnOri.setText(mdnOri);
			VomdnDes.setText(mdnDes);
			VoidRegion.setText(idRegion);
			VoCveEdoCEN.setText(cveEdoCEN);
			VoCveMunicipioCEN.setText(cveMunicipioCEN);
			VoCvePobCEN.setText(cvePobCEN);
			VoidSistemaFact.setText(idSistemaFact);
			VoidTipoPago.setText(idTipoPago);
			VoidModalidad.setText(idModalidad);
			VoidOperador.setText(idOperador);
			VocveServicio.setText(cveServicio);
			VocveSubservicio.setText(cveSubservicio);
			Votecnologia.setText(tecnologia);
			Vousuario.setText(usuario);
			
			
			doChangeMdn.addChild(VomdnOri);
			doChangeMdn.addChild(VomdnDes);
			doChangeMdn.addChild(VoidRegion);
			doChangeMdn.addChild(VoCveEdoCEN);
			doChangeMdn.addChild(VoCveMunicipioCEN);
			doChangeMdn.addChild(VoCvePobCEN);
			doChangeMdn.addChild(VoidSistemaFact);
			doChangeMdn.addChild(VoidTipoPago);
			doChangeMdn.addChild(VoidModalidad);
			doChangeMdn.addChild(VoidOperador);
			doChangeMdn.addChild(VocveServicio);
			doChangeMdn.addChild(VocveSubservicio);
			doChangeMdn.addChild(Votecnologia);
			doChangeMdn.addChild(Vousuario);
			
			Logger.write("     xmlRequest             : " + Utilerias.pintaLogRequest(doChangeMdn.toString()));

			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, doChangeMdn);
			 
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		
		return sResponse;
	}
	
	
	public String doCommitReserve(final String idTransaccion, final String usuario) throws ServiceException {
		
		String url = ResourceAccess.getParametroFromXML("urlCompNumeros");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : doCommitReserve");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     idTransaccion          : " + idTransaccion);
		Logger.write("     usuario                : " + usuario);

		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse="";

		try{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace rmi = fac.createOMNamespace("http://rmi.numeros.mindbits.com", "rmi");
			OMNamespace wsNs = fac.createOMNamespace("", "");

			OMElement doCommitReserve = fac.createOMElement("doCommitReserve", rmi);
			OMElement VoidTransaccion = fac.createOMElement("idTransaccion", wsNs);
			OMElement Vousuario = fac.createOMElement("Usuario", wsNs);
			
			VoidTransaccion.setText(idTransaccion);
			Vousuario.setText(usuario);
			
			doCommitReserve.addChild(VoidTransaccion);
			doCommitReserve.addChild(Vousuario);
			
			Logger.write("     xmlRequest             : " + Utilerias.pintaLogRequest(doCommitReserve.toString()));
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, doCommitReserve);
			 
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		
		return sResponse;
	}

	public String doAvailable(final String mdn, final String usuario) throws ServiceException {
		
		String url = ResourceAccess.getParametroFromXML("urlCompNumeros");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : doAvailable");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             +");
		Logger.write("     mdn                    : " + mdn);
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse="";
		
		try{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace rmi = fac.createOMNamespace("http://rmi.numeros.mindbits.com", "rmi");
			OMNamespace wsNs = fac.createOMNamespace("", "");

			OMElement doAvailable = fac.createOMElement("doAvailable", rmi);
			OMElement Vomdn = fac.createOMElement("mdn", wsNs);
			OMElement VoUsuario = fac.createOMElement("Usuario", wsNs);
			
			Vomdn.setText(mdn);
			VoUsuario.setText(usuario);
			
			doAvailable.addChild(Vomdn);
			doAvailable.addChild(VoUsuario);
			
			Logger.write("     xmlRequest             : " + Utilerias.pintaLogRequest(doAvailable.toString()));
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, doAvailable);
			 
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));	

		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		
		return sResponse;
	}
}
