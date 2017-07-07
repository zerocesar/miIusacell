package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.Addons;
import mx.com.iusacell.services.miiusacell.vo.ConsultaPrepagoVO;
import mx.com.iusacell.services.miiusacell.vo.RespuestaServiciosVO;
import mx.com.iusacell.services.miusacell.call.CallServiceConsultaPrepago;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class ServiciosUnefon
{
    public static int contratarServicio(final String dn, final Addons servicios) throws ServiceException
    {
        /* --------------------------------------------------------------------
         *                 O b j e t o   d e   R e s p u e s t a
         * ----------------------------------------------------------------- */
        int    retorno   = 0;
        
        /*---------------------------------------------------------------------
         *                 O b j e t o s    a u x i l i a r e s
         *-------------------------------------------------------------------*/
        CallServiceConsultaPrepago servicePrepago   = new CallServiceConsultaPrepago();
        ConsultaPrepagoVO          prepago          = new ConsultaPrepagoVO();
        RespuestaServiciosVO       responseServicio = null;
        String                     sResponse        = "";
        int                        tipoLinea        = 1;
        
        /*---------------------------------------------------------------------
         *          Se consulta la información de prepago para el DN 
         *-------------------------------------------------------------------*/
        sResponse = servicePrepago.consultaPrepago(dn);
        prepago = ParseXMLFile.ParseConsultaPrepagoMin(sResponse);

        if(prepago.getIdLinea() == null){
            throw new ServiceException("Sin información de la linea");
        }
        
        try
        {
            tipoLinea = Integer.parseInt(prepago.getTipoLinea().trim());
        }
        catch (NumberFormatException e){
            tipoLinea = 1;
        }
        
        responseServicio = servicePrepago.altaServicioLegacyUnefon( prepago.getIdLinea()
                                                                   ,servicios.getIdServicio()
                                                                   ,servicios.getOrigen()
                                                                   ,servicios.getUnidadVigencia()
                                                                   ,servicios.getCantidadVigencia()
                                                                   ,tipoLinea
                                                                   ,servicios.getOperacion());

        if(responseServicio.isRespuesta()){
            retorno = responseServicio.getFolioPreactivacion();
        }
        
        /*---------------------------------------------------------------------
         *              Liberar Recursos y lanzar respuesta
         *-------------------------------------------------------------------*/
        servicePrepago   = null;
        prepago          = null;
        responseServicio = null;
        sResponse        = null;
        
        return retorno;
    }
    
    public static int bajaServicio(final String dn, final Addons servicios) throws ServiceException
    {
        /* --------------------------------------------------------------------
         *                 O b j e t o   d e   R e s p u e s t a
         * ----------------------------------------------------------------- */
        int    retorno   = 0;
        
        /*---------------------------------------------------------------------
         *                 O b j e t o s    a u x i l i a r e s
         *-------------------------------------------------------------------*/
        CallServiceConsultaPrepago servicePrepago   = new CallServiceConsultaPrepago();
        ConsultaPrepagoVO          prepago          = new ConsultaPrepagoVO();
        RespuestaServiciosVO       responseServicio = null;
        String                     sResponse        = "";
        int                        tipoLinea        = 1;
        
        /*---------------------------------------------------------------------
         *          Se consulta la información de prepago para el DN 
         *-------------------------------------------------------------------*/
        sResponse = servicePrepago.consultaPrepago(dn);
        prepago = ParseXMLFile.ParseConsultaPrepagoMin(sResponse);

        if(prepago.getIdLinea() == null){
            throw new ServiceException("Sin información de la linea");
        }
        
        try
        {
            tipoLinea = Integer.parseInt(prepago.getTipoLinea().trim());
        }
        catch (NumberFormatException e){
            tipoLinea = 1;
        }
        
        responseServicio = servicePrepago.bajaServicioLegacyUnefon(prepago.getIdLinea()
                                                                  ,servicios.getIdServicio()
                                                                  ,servicios.getOrigen()
                                                                  ,servicios.getUnidadVigencia()
                                                                  ,servicios.getCantidadVigencia()
                                                                  ,tipoLinea
                                                                  ,servicios.getOperacion());
        
        retorno = responseServicio.getIdTx();
        
        /*---------------------------------------------------------------------
         *              Liberar Recursos y lanzar respuesta
         *-------------------------------------------------------------------*/
        servicePrepago   = null;
        prepago          = null;
        responseServicio = null;
        sResponse        = null;
        
        return retorno;
    }
}
