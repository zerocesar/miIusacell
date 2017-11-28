package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.comun.Linea;
import mx.com.iusacell.comun.Servicio;
import mx.com.iusacell.comun.Usuario;
import mx.com.iusacell.comun.Vigencia;
import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.SOAPManager.SOAPManagerAxis2;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Utilerias;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

public class CallServiceSServicios {
	public String  bajaServicios(Linea linea, Usuario usuario, String idServicio) throws ServiceException 
	{
		
		String url = ResourceAccess.getParametroFromXML("urlBajaSServicios");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : bajaServicios");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     Linea                  : " + linea.getId());
		Logger.write("     Usuario                : " + usuario.getId());
		
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		Vigencia[] vigencias = null;
       	vigencias = new Vigencia[1];
       	Servicio[] servicios = null;
       	servicios = new Servicio[1];
       	
		
		try{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace impl = fac.createOMNamespace("http://impl.servicios.iusacell.com.mx", "impl");
			OMNamespace xsd = fac.createOMNamespace("http://comun.midtelco.iusacell.com.mx/xsd", "xsd");
			
			OMElement bajaServicios 	= fac.createOMElement("bajaServicios", impl);
			OMElement voLinea         	= fac.createOMElement("vLinea", impl);
			OMElement lineaIDElement    = fac.createOMElement("id", xsd);
			OMElement serviciosElement  = fac.createOMElement("servicios", xsd);
			OMElement vigenciaElement  	= fac.createOMElement("vigencia", xsd);
			OMElement cantidadElement  	= fac.createOMElement("cantidad", xsd);
			OMElement unidadElement  	= fac.createOMElement("unidad", xsd);
			OMElement servIDElement     = fac.createOMElement("id", xsd);
			OMElement usuarioElement    = fac.createOMElement("vUsuario", impl);
			OMElement userIDElement     = fac.createOMElement("id", xsd);
			OMElement userModElement    = fac.createOMElement("modulo", xsd);

			if(linea.getServicios()!=null){
				servicios = linea.getServicios();
				if(servicios[0].getVigencias() != null){
					vigencias=servicios[0].getVigencias();
				}
			}
			lineaIDElement.setText(linea.getId());
			if(vigencias[0].getCantidad()!=null){
				cantidadElement.setText(vigencias[0].getCantidad().toString());	
			}else{
				cantidadElement.setText("0");
			}
			if (vigencias[0].getUnidad()!=null){
				unidadElement.setText(vigencias[0].getUnidad().toString());
			}else{
				unidadElement.setText("0");
			}
			
			vigenciaElement.addChild(cantidadElement);
			vigenciaElement.addChild(unidadElement);
			
			servIDElement.setText(idServicio);
			serviciosElement.addChild(servIDElement);
			serviciosElement.addChild(vigenciaElement);
			
			voLinea.addChild(lineaIDElement) ;
			voLinea.addChild(serviciosElement);
			
			userIDElement.setText(usuario.getId());
			userModElement.setText(usuario.getModulo().toString());
			
			usuarioElement.addChild(userIDElement);
			usuarioElement.addChild(userModElement);
			
			bajaServicios.addChild(voLinea);
			bajaServicios.addChild(usuarioElement);

			
			Logger.write("     xmlRequest             : " + Utilerias.pintaLogRequest(bajaServicios.toString()));
			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, bajaServicios);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());	
		}

		return sResponse;
	}
}
