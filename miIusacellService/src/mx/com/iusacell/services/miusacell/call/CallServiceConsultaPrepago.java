package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.vo.RespuestaServiciosVO;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceConsultaPrepago {
	public String consultaPrepago(final String dn) throws ServiceException
	{
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse="";
		String url = ResourceAccess.getParametroFromXML("urlConsultaInformacionPrepago");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : consultaPrepago");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		
		try{
		    sXMLRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:impl=\"http://impl.gsm.sconsultasprepago.prepago.iusacell.com.mx\" xmlns:xsd=\"http://vo.gsm.sconsultasprepago.prepago.iusacell.com.mx/xsd\">");
		    sXMLRequest.append(   "<soap:Header/>");
		    sXMLRequest.append(   "<soap:Body>");
		    sXMLRequest.append(      "<impl:consultaInformacionPrepago>");
		    sXMLRequest.append(         "<impl:mdn>"+dn+"</impl:mdn>");
		    sXMLRequest.append(         "<impl:esn xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
		    sXMLRequest.append(         "<impl:min xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
		    sXMLRequest.append(         "<impl:isActiva>1</impl:isActiva><impl:filtro>");
		    sXMLRequest.append(            "<xsd:id>2</xsd:id>");
		    sXMLRequest.append(         "</impl:filtro>");
		    sXMLRequest.append(         "<impl:idSistema>1</impl:idSistema>");
		    sXMLRequest.append(         "<impl:iccid xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
		    sXMLRequest.append(      "</impl:consultaInformacionPrepago>");
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
	
	public RespuestaServiciosVO altaServicioLegacyUnefon(final String lineaId,final String serviciosId,final int servicioOrigen,final int vigenciasUnidad,final int vigenciasCantidad,final int tipoLinea,final int operacion) throws ServiceException
	{
	    /* --------------------------------------------------------------------
         *                 O b j e t o   d e   R e s p u e s t a
         * ----------------------------------------------------------------- */
	    RespuestaServiciosVO respuestaVO = null;
	    
	    /*---------------------------------------------------------------------
         *                 O b j e t o s    a u x i l i a r e s
         *-------------------------------------------------------------------*/
	    String        url           = ResourceAccess.getParametroFromXML("serviciosUnefonAlta");
	    StringBuilder wsRequest     = new StringBuilder();
        String        sResponse     = "";
        String        usuarioTest   = "miiusacell";  
        int           usuarioModulo = 13;
        
        /*---------------------------------------------------------------------
         *                   L o g   d e   l a   p e t i c i ó n
         *-------------------------------------------------------------------*/
        Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
        Logger.write("     EndPoint               : " + url);
        Logger.write("     Operacion              : consultaFaF");
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

        /*---------------------------------------------------------------------
         *                      R e q u e s t   X M L
         *-------------------------------------------------------------------*/
        
        wsRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ges=\"http://gestion.servicios.middleware.iusacell.com.mx\">");
        wsRequest.append(    "<soapenv:Header/>"                                                 );
        wsRequest.append(    "<soapenv:Body>"                                                    );
        wsRequest.append(        "<ges:consultaFaF>"                                             );
        wsRequest.append(            "<linea>"                                                   );
        wsRequest.append(                "<id>"   + lineaId   + "</id>"                          );
        wsRequest.append(                "<tipo>" + tipoLinea + "</tipo>"                        );
        wsRequest.append(                "<servicios>"                                           );
        wsRequest.append(                   "<id>"     + serviciosId    + "</id>"                );
        wsRequest.append(                   "<origen>" + servicioOrigen + "</origen>"            );
        wsRequest.append(                   "<vigencias>"                                        );
        wsRequest.append(                       "<unidad>"   + vigenciasUnidad   + "</unidad>"   );
        wsRequest.append(                       "<cantidad>" + vigenciasCantidad + "</cantidad>" );
        wsRequest.append(                   "</vigencias>"                                       );
        wsRequest.append(               "</servicios>"                                           );
        wsRequest.append(           "</linea>"                                                   );
        wsRequest.append(           "<usuario>"                                                  );
        wsRequest.append(               "<id>"     + usuarioTest   + "</id>"                     );
        wsRequest.append(               "<modulo>" + usuarioModulo + "</modulo>"                 );
        wsRequest.append(           "</usuario>"                                                 );
        wsRequest.append(       "</ges:consultaFaF>"                                             );
        wsRequest.append(   "</soapenv:Body>"                                                    );
        wsRequest.append("</soapenv:Envelope>"                                                   );
        
        Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(wsRequest.toString()));
        
        /*---------------------------------------------------------------------
         *              E j e c u c i ó n   d e   R e q u e s t
         *-------------------------------------------------------------------*/
        
        SOAPManager soapManager = new SOAPManager();
        soapManager.createSOAPManager(url,new MensajeLogBean());
        
        try
        {
            sResponse = soapManager.sendRequestWithXMLServicioUnefon(wsRequest.toString(),new MensajeLogBean());
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage());
        }
        
        Logger.write("   + Respuesta              + ");
        Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));
        
        /*---------------------------------------------------------------------
         *                P r o c e s a r   R e s p o n s e
         *-------------------------------------------------------------------*/
        
        respuestaVO = ParseXMLFile.respuestaAltaServiciosUnefon(sResponse);
        
        /*---------------------------------------------------------------------
         *              Liberar Recursos y lanzar respuesta
         *-------------------------------------------------------------------*/
        soapManager = null;
        url         = null;
        wsRequest   = null;
        sResponse   = null;
        usuarioTest = null;  
        
        return respuestaVO;
    }
	
	public RespuestaServiciosVO bajaServicioLegacyUnefon(final String lineaId,final String serviciosId,final int servicioOrigen,final int vigenciasUnidad,final int vigenciasCantidad,final int tipoLinea,final int operacion) throws ServiceException
    {
        /* --------------------------------------------------------------------
         *                 O b j e t o   d e   R e s p u e s t a
         * ----------------------------------------------------------------- */
        RespuestaServiciosVO respuestaVO = null;
        
        /*---------------------------------------------------------------------
         *                 O b j e t o s    a u x i l i a r e s
         *-------------------------------------------------------------------*/
        String        url           = ResourceAccess.getParametroFromXML("serviciosUnefonBaja");
        StringBuilder wsRequest     = new StringBuilder();
        String        sResponse     = "";
        String        usuarioTest   = "miiusacell";  
        int           usuarioModulo = 13;
        
        /*---------------------------------------------------------------------
         *                   L o g   d e   l a   p e t i c i ó n
         *-------------------------------------------------------------------*/
        Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
        Logger.write("     EndPoint               : " + url);
        Logger.write("     Operacion              : bajaServicios");
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

        /*---------------------------------------------------------------------
         *                      R e q u e s t   X M L
         *-------------------------------------------------------------------*/
        
        wsRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ges=\"http://gestion.servicios.middleware.iusacell.com.mx\">");
        wsRequest.append(    "<soapenv:Header/>"                                                 );
        wsRequest.append(    "<soapenv:Body>"                                                    );
        wsRequest.append(        "<ges:bajaServicios>"                                           );
        wsRequest.append(            "<linea>"                                                   );
        wsRequest.append(                "<id>"   + lineaId   + "</id>"                          );
        wsRequest.append(                "<tipo>" + tipoLinea + "</tipo>"                        );
        wsRequest.append(                "<servicios>"                                           );
        wsRequest.append(                   "<id>"     + serviciosId    + "</id>"                );
        wsRequest.append(                   "<origen>" + servicioOrigen + "</origen>"            );
        wsRequest.append(                   "<vigencias>"                                        );
        wsRequest.append(                       "<unidad>"   + vigenciasUnidad   + "</unidad>"   );
        wsRequest.append(                       "<cantidad>" + vigenciasCantidad + "</cantidad>" );
        wsRequest.append(                   "</vigencias>"                                       );
        wsRequest.append(               "</servicios>"                                           );
        wsRequest.append(           "</linea>"                                                   );
        wsRequest.append(           "<usuario>"                                                  );
        wsRequest.append(               "<id>"     + usuarioTest   + "</id>"                     );
        wsRequest.append(               "<modulo>" + usuarioModulo + "</modulo>"                 );
        wsRequest.append(           "</usuario>"                                                 );
        wsRequest.append(       "</ges:bajaServicios>"                                           );
        wsRequest.append(   "</soapenv:Body>"                                                    );
        wsRequest.append("</soapenv:Envelope>"                                                   );
        
        Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(wsRequest.toString()));
        
        /*---------------------------------------------------------------------
         *              E j e c u c i ó n   d e   R e q u e s t
         *-------------------------------------------------------------------*/
        
        SOAPManager soapManager = new SOAPManager();
        soapManager.createSOAPManager(url,new MensajeLogBean());
        
        try
        {
            sResponse = soapManager.sendRequestWithXMLServicioUnefon(wsRequest.toString(),new MensajeLogBean());
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage());
        }
        
        Logger.write("   + Respuesta              + ");
        Logger.write("     XMLRespuesta           : " +  Utilerias.pintaLogRequest(sResponse));
        
        /*---------------------------------------------------------------------
         *                P r o c e s a r   R e s p o n s e
         *-------------------------------------------------------------------*/
        
        respuestaVO = ParseXMLFile.respuestaBajaServiciosUnefon(sResponse);
        
        /*---------------------------------------------------------------------
         *              Liberar Recursos y lanzar respuesta
         *-------------------------------------------------------------------*/
        soapManager = null;
        url         = null;
        wsRequest   = null;
        sResponse   = null;
        usuarioTest = null;  
        
        return respuestaVO;
    }
	
}
