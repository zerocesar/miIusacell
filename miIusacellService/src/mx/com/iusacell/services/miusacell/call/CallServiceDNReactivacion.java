package mx.com.iusacell.services.miusacell.call;

import java.rmi.RemoteException;
import java.util.Date;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import com.iusacell.middleware.serviciosBSCS.vo.AltaBajaServiciosVO;
import com.iusacell.middleware.serviciosBSCS.vo.RespuestaAltaBajaServiciosVO;
import com.iusacell.middleware.serviciosBSCS.ws.ServiciosBSCSServiceLocator;
import com.iusacell.middleware.serviciosBSCS.ws.ServiciosBSCSSoapBindingStub;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miusacell.util.Constantes;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceDNReactivacion {
	public String DNReactivacion(final String dn, final String reason) throws ServiceException {
	    StringBuffer sXMLRequest = new StringBuffer();
		String sResponse="";
		
		String url = ResourceAccess.getParametroFromXML("urlsDNReactivacion");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : DNReactivacion");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     DN                     : " + dn);
		Logger.write("     Reason                 : " + reason);
		
		try{
            sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sges=\"http://www.example.org/SGestionReactivacionWS/\" xmlns:head=\"http://www.example.org/Header\">");
            sXMLRequest.append(   "<soapenv:Header/>");
            sXMLRequest.append(   "<soapenv:Body>");
            sXMLRequest.append(      "<sges:reactivaServicio>");
            sXMLRequest.append(         "<sges:Header>");
            sXMLRequest.append(            "<head:paisID>1</head:paisID>");
            sXMLRequest.append(            "<head:canalID>1</head:canalID>");
            sXMLRequest.append(            "<head:sucursalID>0060</head:sucursalID>");
            sXMLRequest.append(            "<head:sistemaID>8</head:sistemaID>");
            sXMLRequest.append(            "<head:servicioID>1</head:servicioID>");
            sXMLRequest.append(         "</sges:Header>");
            sXMLRequest.append(         "<Acceso>");
            sXMLRequest.append(            "<idSAEO>"+Constantes.ACCESO_USERSAEO_ID+"</idSAEO>");
            sXMLRequest.append(            "<idTransaccion>"+Constantes.ACCESO_TRANSACCION_ID+"</idTransaccion>");
            sXMLRequest.append(            "<origen>"+Constantes.ACCESO_ORIGEN+"</origen>");
            sXMLRequest.append(         "</Acceso>");
            sXMLRequest.append(         "<dn>"+dn+"</dn>");
            sXMLRequest.append(         "<reason>"+reason+"</reason>");
            sXMLRequest.append(      "</sges:reactivaServicio>");
            sXMLRequest.append(   "</soapenv:Body>");
            sXMLRequest.append("</soapenv:Envelope>");
			
			Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(sXMLRequest.toString()));
			SOAPManager soapManager = new SOAPManager();
			soapManager.createSOAPManager(url,new MensajeLogBean());
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}finally{
            sXMLRequest=null;
        }

		return sResponse;
	}

	public String DNSuspension(final String dn, final String reason) throws ServiceException {
	    StringBuffer sXMLRequest = new StringBuffer();
		String sResponse="";
		
		String url = ResourceAccess.getParametroFromXML("urlsDNSuspension");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : DNSuspension");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     DN                     : " + dn);
		Logger.write("     Reason                 : " + reason);
		
		try{
            sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sges=\"http://www.example.org/SGestionSuspensionWS/\" xmlns:head=\"http://www.example.org/Header\">");
            sXMLRequest.append(        "<soapenv:Header/>");
            sXMLRequest.append(        "<soapenv:Body>");
            sXMLRequest.append(           "<sges:suspendeServicio>");
            sXMLRequest.append(              "<sges:Header>");
            sXMLRequest.append(                 "<head:paisID>1</head:paisID>");
            sXMLRequest.append(                 "<head:canalID>1</head:canalID>");
            sXMLRequest.append(                 "<head:sucursalID>0060</head:sucursalID>");
            sXMLRequest.append(                 "<head:sistemaID>8</head:sistemaID>");
            sXMLRequest.append(                 "<head:servicioID>1</head:servicioID>");
            sXMLRequest.append(              "</sges:Header>");
            sXMLRequest.append(              "<Acceso>");
            sXMLRequest.append(                 "<idSAEO>"+Constantes.ACCESO_USERSAEO_ID+"</idSAEO>");
            sXMLRequest.append(                 "<idTransaccion>"+Constantes.ACCESO_TRANSACCION_ID+"</idTransaccion>");
            sXMLRequest.append(                 "<origen>"+Constantes.ACCESO_ORIGEN+"</origen>");
            sXMLRequest.append(              "</Acceso>");
            sXMLRequest.append(              "<dn>"+dn+"</dn>");
            sXMLRequest.append(              "<reason>"+reason+"</reason>");
            sXMLRequest.append(           "</sges:suspendeServicio>");
            sXMLRequest.append(        "</soapenv:Body>");
            sXMLRequest.append(     "</soapenv:Envelope>");
			
			Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(sXMLRequest.toString()));
			
			SOAPManager soapManager = new SOAPManager();
			soapManager.createSOAPManager(url,new MensajeLogBean());
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}finally{
            sXMLRequest=null;
        }

		return sResponse;
	}
	
	public RespuestaAltaBajaServiciosVO altaBajaServiciosBSCS(AltaBajaServiciosVO requestVO) throws ServiceException
	{
	    RespuestaAltaBajaServiciosVO responseVO = null;
	    ServiciosBSCSSoapBindingStub stub       = null;
	    ServiciosBSCSServiceLocator  locator    = new ServiciosBSCSServiceLocator();
	    
	    String endPointAddress = ResourceAccess.getParametroFromXML("urlServiciosBSCSWeb");
	    
	    locator.setServiciosBSCSEndpointAddress(endPointAddress);
	    
	    String  mensajeError = "";
        boolean errorEnFlujo = false;
        
        Logger.write(" >>> CALL_WEB_SERVICE       : ServiciosBSCSWeb.ServiciosBSCS" );
        Logger.write("     EndPoint               : " + endPointAddress);
        Logger.write("     Operacion              : altaBajaServicios");
        Logger.write("     requesting             : " + new Date().toString());
        Logger.write("   + Parametros             + ");
        Logger.write("     coID                   : " + requestVO.getCoID());
        Logger.write("     tmCode                 : " + requestVO.getTmCode());
        Logger.write("     servicios              : " + requestVO.getServicios());
        Logger.write("     type                   : " + requestVO.getType());
        Logger.write("     userMod                : " + requestVO.getUserMod());
	    
	    try
	    {
	        stub = (ServiciosBSCSSoapBindingStub) locator.getServiciosBSCS();
	        responseVO = stub.altaBajaServicios(requestVO);
	    }
	    catch (ServiceException exception){
	        errorEnFlujo = true;
	        mensajeError = exception.getMessage();
        }catch (RemoteException exception){
            errorEnFlujo = true;
            mensajeError = exception.getMessage();
        }
        finally
        {
            try
            {
                Logger.write("   - SOAPRequestXML         : "  +  stub._getCall().getTargetEndpointAddress());
                Logger.write("   - SOAPRequestXML         : "  +  stub._getCall().getMessageContext().getRequestMessage().getSOAPPart().getEnvelope().toString());
                Logger.write("   - SOAPResponseXML        : "  +  stub._getCall().getMessageContext().getResponseMessage().getSOAPPart().getEnvelope().toString());
            }
            catch (SOAPException exception)
            {
                Logger.write("  (x)Exception              : (SOAPException) Ocurrió un error al intentar obtener request/response de la operación :: " + exception.getMessage());
            }
            
            if(errorEnFlujo){
                throw new ServiceException(mensajeError);
            }
            Logger.write(" <<< CALL_WEB_SERVICE       : ServiciosBSCSWeb.ServiciosBSCS" );
        }
        
        return responseVO;
	}
}
