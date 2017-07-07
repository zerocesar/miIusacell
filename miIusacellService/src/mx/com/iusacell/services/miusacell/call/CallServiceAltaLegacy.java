package mx.com.iusacell.services.miusacell.call;

import java.rmi.RemoteException;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

import mx.com.iusacell.comun.Falta;
import mx.com.iusacell.comun.Linea;
import mx.com.iusacell.comun.Servicio;
import mx.com.iusacell.comun.Usuario;
import mx.com.iusacell.comun.Vigencia;
import mx.com.iusacell.middleware.servicios.gestion.Cobro;
import mx.com.iusacell.middleware.servicios.gestion.IGestionServicios;
import mx.com.iusacell.middleware.servicios.gestion.RespuestaServicios;
import mx.com.iusacell.middleware.servicios.gestion.Binding3.GestionServiciosWS_IGestionServiciosHttpServiceLocator;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.SOAPManager.SOAPManagerAxis2;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceAltaLegacy {
	
	SOAPManager soapManager = null;
	
	public String altaLegacy(final String lineaId,final String serviciosId,	final String servicioOrigen, final String vigenciasUnidad,final String vigenciasCantidad, final String tipoLinea, final String operacion) throws ServiceException
	{
		
		String sResponse="";
		String usuarioTest = "miiusacell";	
		String usuarioModulo = "13";
		String url = ResourceAccess.getParametroFromXML("urlsServiceAltaLegacy");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : altaLegacy");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     lineaId                : " + lineaId);
		Logger.write("     serviciosId            : " + serviciosId);
		Logger.write("     servicioOrigen         : " + servicioOrigen);
		Logger.write("     vigenciasUnidad        : " + vigenciasUnidad);
		Logger.write("     vigenciasCantidad      : " + vigenciasCantidad);
		Logger.write("     usuarioTest            : " + usuarioTest);
		Logger.write("     usuarioModulo          : " + usuarioModulo);
		Logger.write("     tipoLinea              : " + tipoLinea);
		Logger.write("     operacion              : " + operacion);
		
		try{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace ges = fac.createOMNamespace("http://gestion.servicios.middleware.iusacell.com.mx", "ges");
			OMNamespace wsNs = fac.createOMNamespace("", "");
			OMElement ServiciosContratados = fac.createOMElement("operacionServicio", ges);
			
			OMElement linea = fac.createOMElement("linea", wsNs);
			OMElement Vlineaid = fac.createOMElement("id", wsNs);
			OMElement VlineaTipo = fac.createOMElement("tipo", wsNs);
			OMElement VlineaPlan = fac.createOMElement("plan", wsNs);
			OMElement VlineaFamilia = fac.createOMElement("familia", wsNs);
			OMElement VlineaTerminal = fac.createOMElement("terminal", wsNs);
			OMElement VTerminalEsn = fac.createOMElement("ESN", wsNs);
			
			OMElement servicios = fac.createOMElement("servicios", wsNs);
			OMElement VServicioId = fac.createOMElement("id", wsNs);
			OMElement VServicioOrigen = fac.createOMElement("origen", wsNs);
			OMElement vigencias = fac.createOMElement("vigencias", wsNs);
			OMElement VVigenciasUnidad = fac.createOMElement("unidad", wsNs);
			OMElement VVigenciaCantidad = fac.createOMElement("cantidad", wsNs);
			
			OMElement wallets = fac.createOMElement("wallets", wsNs);
			OMElement vendedor = fac.createOMElement("vendedor", wsNs);
			
			OMElement historico = fac.createOMElement("historico", wsNs);
			OMElement cuentaContable = fac.createOMElement("cuentaContable", wsNs);
			OMElement cuenta = fac.createOMElement("cuenta", wsNs);
			OMElement IVA = fac.createOMElement("IVA", wsNs);
			OMElement region = fac.createOMElement("region", wsNs);
			
			OMElement historicoPreactivacion = fac.createOMElement("historicoPreactivacion", wsNs);			
			
			OMElement usuario = fac.createOMElement("usuario", wsNs);
			OMElement usuarioId = fac.createOMElement("id", wsNs);
			OMElement modulo = fac.createOMElement("modulo", wsNs);
			OMElement operacionV = fac.createOMElement("operacion", wsNs);
			OMElement cobro = fac.createOMElement("cobro", wsNs);
			
			Vlineaid.setText(lineaId);
			VlineaTipo.setText(tipoLinea);
			VServicioId.setText(serviciosId);
			VServicioOrigen.setText(servicioOrigen);
			VVigenciasUnidad.setText(vigenciasUnidad);
			VVigenciaCantidad.setText(vigenciasCantidad);
			usuarioId.setText(usuarioTest);
			modulo.setText(usuarioModulo);
			operacionV.setText(operacion);
			
			usuario.addChild(usuarioId);
			usuario.addChild(modulo);
			
			cuentaContable.addChild(cuenta);
			cuentaContable.addChild(IVA);
			cuentaContable.addChild(region);																					
			
			vigencias.addChild(VVigenciasUnidad);
			vigencias.addChild(VVigenciaCantidad);
			servicios.addChild(VServicioId);
			servicios.addChild(VServicioOrigen);
			servicios.addChild(vigencias);
			servicios.addChild(cuentaContable);		
			
			cuentaContable = fac.createOMElement("cuentaContable", wsNs);
			cuenta = fac.createOMElement("cuenta", wsNs);
			IVA = fac.createOMElement("IVA", wsNs);
			region = fac.createOMElement("region", wsNs);
			cuentaContable.addChild(cuenta);
			cuentaContable.addChild(IVA);
			cuentaContable.addChild(region);
			vigencias = fac.createOMElement("vigencias", wsNs);
			historico.addChild(cuentaContable);
			historico.addChild(vigencias);
			
			cuentaContable = fac.createOMElement("cuentaContable", wsNs);
			cuenta = fac.createOMElement("cuenta", wsNs);
			IVA = fac.createOMElement("IVA", wsNs);
			region = fac.createOMElement("region", wsNs);
			cuentaContable.addChild(cuenta);
			cuentaContable.addChild(IVA);
			cuentaContable.addChild(region);
			vigencias = fac.createOMElement("vigencias", wsNs);
			historicoPreactivacion.addChild(cuentaContable);
			historicoPreactivacion.addChild(vigencias);
			
			VlineaTerminal.addChild(VTerminalEsn);
			linea.addChild(Vlineaid);
			linea.addChild(VlineaTipo);
			linea.addChild(VlineaPlan);
			linea.addChild(VlineaFamilia);
			linea.addChild(VlineaTerminal);
			linea.addChild(servicios);						
			
			linea.addChild(wallets);
			linea.addChild(vendedor);
			linea.addChild(historico);
			linea.addChild(historicoPreactivacion);
			
			ServiciosContratados.addChild(linea);
			ServiciosContratados.addChild(usuario);
			ServiciosContratados.addChild(cobro);
			ServiciosContratados.addChild(operacionV);
			
			Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(ServiciosContratados.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, ServiciosContratados);
			
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XMLRespuesta           :" +  Utilerias.pintaLogRequest(sResponse));

		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return sResponse;
	}
	
	public RespuestaServicios altaServicioLegacy(final String lineaId, final String serviciosId, final int servicioOrigen, final int vigenciasUnidad,final int vigenciasCantidad, 
	        final int tipoLinea, final int operacion) throws ServiceException
	{

		String sResponse="";
		String usuarioTest = "miiusacell";	
		int usuarioModulo = 13;
		String url = ResourceAccess.getParametroFromXML("urlSServicios");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : altaServicioLegacy");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     lineaId                : " + lineaId);
		Logger.write("     serviciosId            : " + serviciosId);
		Logger.write("     servicioOrigen         : " + servicioOrigen);
		Logger.write("     vigenciasUnidad        : " + vigenciasUnidad);
		Logger.write("     vigenciasCantidad      : " + vigenciasCantidad);
		Logger.write("     usuarioTest            : " + usuarioTest);
		Logger.write("     usuarioModulo          : " + usuarioModulo);
		Logger.write("     tipoLinea              : " + tipoLinea);
		Logger.write("     operacion              : " + operacion);

		GestionServiciosWS_IGestionServiciosHttpServiceLocator serviceLocator = new GestionServiciosWS_IGestionServiciosHttpServiceLocator();
		serviceLocator.setGestionServiciosWS_IGestionServiciosHttpPortEndpointAddress(url);
		RespuestaServicios response = null;
		try{

			IGestionServicios serviceMethods = serviceLocator.getGestionServiciosWS_IGestionServiciosHttpPort();
			
			Linea linea = new Linea();
			linea.setId(lineaId);
			linea.setTipo(tipoLinea);
			Servicio[] servicios = new Servicio[1];
			servicios[0] = new Servicio();
			servicios[0].setId(serviciosId);
			servicios[0].setOrigen(servicioOrigen);	
			Vigencia[] vigencias = new Vigencia[1];
			vigencias[0] = new Vigencia();
			vigencias[0].setUnidad(vigenciasUnidad);
			vigencias[0].setCantidad(vigenciasCantidad);
			servicios[0].setVigencias(vigencias);						
			linea.setServicios(servicios);
			
			Usuario usuario = new Usuario();
			usuario.setId(usuarioTest);
			usuario.setModulo(usuarioModulo);
			
			Cobro cobro = null;						
			
			response = serviceMethods.operacionServicio(linea, usuario, cobro, operacion);						
			
			Logger.write("   + Respuesta              + ");
			if(response != null)
			{
				Logger.write("     XMLRespuesta           : respuesta          :" + response.getRespuesta());
				Logger.write("     XMLRespuesta           : folioPreactivacion : " + response.getFolioPreactivacion());				
			}
			Logger.write("     XMLRespuesta           :" +  Utilerias.pintaLogRequest(sResponse));

		} catch (ServiceException e) {
			throw new ServiceException(e.getLocalizedMessage());
		} catch (Falta e) {
			throw new ServiceException(e.getExceptionType() + " " + e.getMessage1());
		} catch (RemoteException e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return response;
	}
}