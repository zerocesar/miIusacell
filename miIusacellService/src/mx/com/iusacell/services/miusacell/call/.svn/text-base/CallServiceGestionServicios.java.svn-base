package mx.com.iusacell.services.miusacell.call;

import java.rmi.RemoteException;

import javax.xml.soap.SOAPException;

import org.apache.commons.lang3.StringUtils;

import mx.com.iusacell.comun.Falta;
import mx.com.iusacell.comun.Linea;
import mx.com.iusacell.comun.Usuario;
import mx.com.iusacell.middleware.servicios.gestion.Cobro;
import mx.com.iusacell.middleware.servicios.gestion.RespuestaServicios;
import mx.com.iusacell.middleware.servicios.gestion.Binding3.GestionServiciosWS_IGestionServiciosHttpBindingStub;
import mx.com.iusacell.middleware.servicios.gestion.Binding3.GestionServiciosWS_IGestionServiciosHttpServiceLocator;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;

public class CallServiceGestionServicios
{
    public RespuestaServicios operacionServicio(Linea linea, Usuario usuario, Cobro cobro, int operacion ){
        Logger.write("  (>) [WS] operacion    : GestionServiciosWS.operacionServicio");
        String endPointAddress = ResourceAccess.getParametroFromXML("urlGestionServiciosWS");;
        RespuestaServicios respuestaServicios = null;
        
        GestionServiciosWS_IGestionServiciosHttpBindingStub    stub    = null;
        GestionServiciosWS_IGestionServiciosHttpServiceLocator locator = new GestionServiciosWS_IGestionServiciosHttpServiceLocator();
        locator.setGestionServiciosWS_IGestionServiciosHttpPortEndpointAddress(endPointAddress);
        
        try{
            stub = (GestionServiciosWS_IGestionServiciosHttpBindingStub)locator.getGestionServiciosWS_IGestionServiciosHttpPort();
            respuestaServicios = stub.operacionServicio(linea, usuario, cobro, operacion);
        }catch(Falta exc){
            System.out.println(exc.getMessage());
            respuestaServicios = new RespuestaServicios();
            respuestaServicios.setRespuesta(false);
            if(StringUtils.isNotEmpty(exc.getMessage1())){
                respuestaServicios.setMensaje(exc.getMessage1());
            }else{
                respuestaServicios.setMensaje("No se pudo realizar la contratación del servicio");
            }
            Logger.write("      [WS] Exception    : Falta :: " + respuestaServicios.getMensaje());
        }catch(RemoteException exc){
            respuestaServicios = new RespuestaServicios();
            respuestaServicios.setRespuesta(false);
            respuestaServicios.setMensaje(exc.getMessage());
            Logger.write("      [WS] Exception    : RemoteException :: " + exc.getMessage());
        }catch(Exception exc){
            respuestaServicios = new RespuestaServicios();
            respuestaServicios.setRespuesta(false);
            respuestaServicios.setMensaje(exc.getMessage());
            Logger.write("      [WS] Exception    : Exception :: " + exc.getMessage());
        }finally{
            try{
                Logger.write("      [WS] Endpoint     : "  +  stub._getCall().getTargetEndpointAddress());
                Logger.write("      [WS] Request      : "  +  stub._getCall().getMessageContext().getRequestMessage().getSOAPPart().getEnvelope().toString());
                Logger.write("      [WS] Response     : "  +  stub._getCall().getMessageContext().getResponseMessage().getSOAPPart().getEnvelope().toString());
                
                Logger.write("      [WS] resp.codigo  : " + respuestaServicios.getCodigo());
                Logger.write("      [WS] resp.fPreact : " + respuestaServicios.getFolioPreactivacion());
                Logger.write("      [WS] resp.idTx    : " + respuestaServicios.getIdTx());
                Logger.write("      [WS] resp.mensaje : " + respuestaServicios.getMensaje());
                Logger.write("      [WS] resp.respues : " + respuestaServicios.getRespuesta());
            }
            catch (SOAPException exc){
                Logger.write("      [WS] Exception    : SOAPException :: " + exc.getMessage());
            }
            
            stub    = null;
            locator = null;
        }
        Logger.write("  (<) [WS] operacion    : GestionServiciosWS.operacionServicio");
        return respuestaServicios;
    }
}
