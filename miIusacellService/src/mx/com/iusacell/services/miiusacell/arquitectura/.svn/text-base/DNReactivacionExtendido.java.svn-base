package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.DNReactivacionVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.DispatcherVO;
import mx.com.iusacell.services.miusacell.call.CallServiceCambioStatus;
import mx.com.iusacell.services.miusacell.call.CallServiceConsultaPrepago;
import mx.com.iusacell.services.miusacell.call.CallServiceDNReactivacion;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceServiciosContratos;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

import com.iusacell.middleware.serviciosBSCS.vo.AltaBajaServiciosVO;
import com.iusacell.middleware.serviciosBSCS.vo.RespuestaAltaBajaServiciosVO;

public abstract class DNReactivacionExtendido
{
    private static int SUSPENSION_ETAK     = 1;
    private static int REACTIVACION_ETAK   = 2;
    private static int SUSPENCION_LEGACY   = 1;
    private static int REACTIVACION_LEGACY = 2;
    
	public static DNReactivacionVO flujo (final String dn, final String reason, final int tipo, final String idIdentificador) throws ServiceException
	{
		String sResponse="";
		String sResponseAux="";
		CallServiceDNReactivacion CallDNreactivacion = new CallServiceDNReactivacion();
		DNReactivacionVO dnreactivacion = new DNReactivacionVO();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		CallServiceCambioStatus cambioLegacy = new CallServiceCambioStatus();
		CallServiceConsultaPrepago consultaPrepago = new CallServiceConsultaPrepago();
		CallServiceServiciosContratos consultaStatus = new CallServiceServiciosContratos();
		OracleProcedures oracle = new OracleProcedures();
		String idLinea = "";
		String statusLinea = "";

		try{

			sResponse = focalizacion.focalizacion(dn);
			sResponseAux = sResponse;
			if(sResponse != null && !sResponse.equals("")){
				descripcion = ParseXMLFile.parseFocalizacion(sResponse);
			}
			if(descripcion.getServicio() != null && descripcion.getServicio().equals("80") && descripcion.getSubservicio() != null && descripcion.getSubservicio().equals("80")){
				if(tipo == 1){
					Logger.write("   Suspension plataforma ETAK");
					
					try{
					  oracle.suspensionSET(idIdentificador,dn , reason, 2121);
					}catch (Exception e) {
                        Logger.write("Detail al consultar el metodo :: suspensionSET");
					}
					
					if(sResponseAux != null && !sResponseAux.equals("")){
						statusLinea = ParseXMLFile.parseStatusFocalizacionEtak(sResponseAux);
					}

					if(statusLinea != null && statusLinea.toLowerCase().equals("frozen")){
						Logger.write("Status linea: " + statusLinea);
						throw new ServiceException("La linea ya se encuentra suspendida");
					}
					
					sResponse = CallDNreactivacion.DNSuspension(dn, reason);
					if(sResponse != null && !sResponse.equals("")){
						dnreactivacion = ParseXMLFile.ParseDnSuspension(sResponse);
						if(dnreactivacion != null && dnreactivacion.getOperationCode() != null && dnreactivacion.getOperationCode().equals("1"))
							dnreactivacion.setOperationCode("0");
					}
					
					if(dnreactivacion != null && !dnreactivacion.equals("")){
						try{
							oracle.suspensionReactivacionesRES(idIdentificador, 1, dnreactivacion.getOperationCode(), dnreactivacion.getMessageCode(), 2121,"","");

						}catch (Exception e) {
							Logger.write("Detail al consultar el metodo :: suspensionReactivacionesRES");
						}
					}
				}
				if(tipo == 2){
					Logger.write("   Reactivacion plataforma ETAK");
					
					try{
					  oracle.reactivacionSET(idIdentificador, dn, reason, 2121);
					}catch (Exception e) {
							Logger.write("Detail al ejecutar el metodo :: reactivacionSET");
					}
					
					if(sResponseAux != null && !sResponseAux.equals("")){
						statusLinea = ParseXMLFile.parseStatusFocalizacionEtak(sResponseAux);
					}

					if(statusLinea != null && (statusLinea.toLowerCase().equals("r1") || statusLinea.toLowerCase().equals("r2") || statusLinea.toLowerCase().equals("r0"))){
						Logger.write("Status linea: " + statusLinea);
						throw new ServiceException("La linea ya se encuentra activa");
					}
					
					sResponse = CallDNreactivacion.DNReactivacion(dn, reason);
					if(sResponse != null && !sResponse.equals("")){
						dnreactivacion = ParseXMLFile.ParseDnReactivacion(sResponse);
						if(dnreactivacion != null && dnreactivacion.getOperationCode() != null && dnreactivacion.getOperationCode().equals("1"))
							dnreactivacion.setOperationCode("0");
						
						if(dnreactivacion != null && !dnreactivacion.equals("")){
							try{
								oracle.suspensionReactivacionesRES(idIdentificador, 2, dnreactivacion.getOperationCode(), dnreactivacion.getMessageCode(), 2121,"","");

							}catch (Exception e) {
								Logger.write("Detail al consultar el metodo :: suspensionReactivacionesRES");
							}
						}
					}
				}
			}else if(descripcion.getServicio() != null &&  !descripcion.getServicio().equals("0") && !descripcion.getServicio().equals("80"))
			{
				sResponse = consultaPrepago.consultaPrepago(dn);
				if(sResponse != null && !sResponse.equals(""))
					idLinea = ParseXMLFile.ParseConsultaPrepago(sResponse);
				if(idLinea == null || idLinea.equals("")){
					idLinea = descripcion.getIdPrepago();
				}
				if(idLinea == null || idLinea.equals("")){
					idLinea = descripcion.getCoID();
				}
				
				if(tipo == 1){
					
					Logger.write("   Suspension plataforma LEGACY");

					if(sResponseAux != null && !sResponseAux.equals("")){
						statusLinea = ParseXMLFile.parseStatusFocalizacion(sResponseAux);
					}

					if(statusLinea.equals("a")){
						Logger.write("Status linea: " + statusLinea);
						throw new ServiceException("La linea ya se encuentra suspendida");
					}
					
					if(descripcion != null && descripcion.getIsPostpagoOrHibrido() != null && descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
						sResponse = consultaStatus.getStatusLineaPospago(dn);
						if(sResponse != null && !sResponse.equals("")){
							DispatcherVO status = ParseXMLFile.ParseServiciosStatusPrepago(sResponse);
							if(status != null && status.getContrato() != null){
								idLinea = status.getContrato();
							}
						}
					}
					
						try{
							oracle.cambiaStatusSET(idIdentificador, idLinea, "s", dn, reason, "", 2121);
						}catch (Exception e) {
							Logger.write("Detail al ejecutar el metodo :: cambiaStatusSET");
						}
						
					sResponse = cambioLegacy.cambioStatus(idLinea, "s", dn, reason, "");
					if(sResponse != null && !sResponse.equals(""))
						dnreactivacion = ParseXMLFile.ParseDnReactivacionLegacy(sResponse);
				}else if(tipo == 2){
					Logger.write("   Reactivacion plataforma LEGACY");
					
					if(sResponseAux != null && !sResponseAux.equals("")){
						statusLinea = ParseXMLFile.parseStatusFocalizacion(sResponseAux);
					}
					
					if(statusLinea.equals("d")){
						Logger.write("Status linea: " + statusLinea);
						throw new ServiceException("La linea ya se encuentra activa");
					}
					
					if(descripcion != null && descripcion.getIsPostpagoOrHibrido() != null && descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
						sResponse = consultaStatus.getStatusLineaPospago(dn);
						if(sResponse != null && !sResponse.equals("")){
							DispatcherVO status = ParseXMLFile.ParseServiciosStatusPrepago(sResponse);
							if(status != null && status.getContrato() != null){
								idLinea = status.getContrato();
							}
						}
					}
					
					try{
						oracle.cambiaStatusSET(idIdentificador, idLinea, "a", dn, reason, "", 2121);
					}catch (Exception e) {
						Logger.write("Detail al ejecutar el metodo :: cambiaStatusSET");
					}
					
					sResponse = cambioLegacy.cambioStatus(idLinea, "a", dn, reason, "");
					if(sResponse != null && !sResponse.equals(""))
						dnreactivacion = ParseXMLFile.ParseDnReactivacionLegacy(sResponse);
				}
				
				if(dnreactivacion != null && !dnreactivacion.equals("")){
					try{
						oracle.suspensionReactivacionesRES(idIdentificador, 3, dnreactivacion.getOperationCode(), dnreactivacion.getMessageCode(), 2121,"","");
					}catch (Exception e) {
						Logger.write("Detail al consultar el metodo :: suspensionReactivacionesRES");
					}
				}
				
			}else{
				Logger.write("   IdServicio: " + descripcion.getServicio());
				throw new ServiceException("IdServicio Invalido");
			}
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return dnreactivacion; 
	}
	
	public static DNReactivacionVO flujoBSCS (final String dn, final String reason, final int tipo, final String idIdentificador) throws ServiceException
	{
	    String sResponse    = "";
        String sResponseAux = "";
        String idLinea      = "";
        String statusLinea  = "";
        
        DNReactivacionVO              dnreactivacion     = null;
        OracleProcedures              oracle             = new OracleProcedures();
        AltaBajaServiciosVO           requestBSCS        = new AltaBajaServiciosVO();
        DetalleFocalizacionVO         descripcion        = new DetalleFocalizacionVO();
        CallServiceFocalizacion       focalizacion       = new CallServiceFocalizacion();
        CallServiceDNReactivacion     CallDNreactivacion = new CallServiceDNReactivacion();
        CallServiceConsultaPrepago    consultaPrepago    = new CallServiceConsultaPrepago();
        RespuestaAltaBajaServiciosVO  responseBSCS       = null;
        CallServiceServiciosContratos consultaStatus     = new CallServiceServiciosContratos();

        try
        {
            sResponse    = focalizacion.focalizacion(dn);
            sResponseAux = sResponse;
            
            if(sResponse != null && !sResponse.equals("")){
                descripcion = ParseXMLFile.parseFocalizacion(sResponse);
            }
            
            if(descripcion != null){
                requestBSCS.setCoID(descripcion.getCoID());
                requestBSCS.setTmCode(descripcion.getTmCode());
                requestBSCS.setServicios("302");
                requestBSCS.setType(String.valueOf(tipo));
                requestBSCS.setUserMod("iusacellService");
            }
            
            if(esTecnologiaLEGACY(descripcion))
            {
                sResponse = consultaPrepago.consultaPrepago(dn);
                
                if(sResponse != null && !sResponse.equals("")){
                    idLinea = ParseXMLFile.ParseConsultaPrepago(sResponse);
                } if(idLinea == null || idLinea.equals("")){
                    idLinea = descripcion.getIdPrepago();
                } if(idLinea == null || idLinea.equals("")){
                    idLinea = descripcion.getCoID();
                }
                
                if(SUSPENCION_LEGACY == tipo)
                {
                    Logger.write(" (>) Suspension plataforma LEGACY");
                    
                    if(sResponseAux != null && !sResponseAux.equals("")){
                        statusLinea = ParseXMLFile.parseStatusFocalizacion(sResponseAux);
                    }
                    
                    Logger.write(" (i) Status linea           : " + statusLinea);
                    
                    if(statusLinea.equals("a")){
                        throw new ServiceException("La linea ya se encuentra suspendida");
                    }
                    
                    if(descripcion != null && descripcion.getIsPostpagoOrHibrido() != null && descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido"))
                    {
                        sResponse = consultaStatus.getStatusLineaPospago(dn);
                        
                        if(sResponse != null && !sResponse.equals(""))
                        {
                            DispatcherVO status = ParseXMLFile.ParseServiciosStatusPrepago(sResponse);
                            if(status != null && status.getContrato() != null){
                                idLinea = status.getContrato();
                            }
                        }
                    }
                    
                    try{ oracle.cambiaStatusSET(idIdentificador, idLinea, "s", dn, reason, "", 2121); }
                    catch (Exception e) { Logger.write(" (X) Exception              : Detail al ejecutar el metodo :: cambiaStatusSET"); }
                    
//                    sResponse = cambioLegacy.cambioStatus(idLinea, "s", dn, reason, "");
//                    
//                    if(sResponse != null && !sResponse.equals("")){
//                        dnreactivacion = ParseXMLFile.ParseDnReactivacionLegacy(sResponse);
//                    }
                    
                    responseBSCS = CallDNreactivacion.altaBajaServiciosBSCS(requestBSCS);
                    
                    if(responseBSCS != null && responseBSCS.getMsgError() != null){
                        dnreactivacion = new DNReactivacionVO();
                        dnreactivacion.setOperationCode(responseBSCS.getMsgError().equalsIgnoreCase("EXITO")?"1":"0");
                        dnreactivacion.setMessageCode(responseBSCS.getMsgError());
                    }
                }
                else if(REACTIVACION_LEGACY == 2)
                {
                    Logger.write(" (>) Reactivacion plataforma LEGACY");
                    
                    if(sResponseAux != null && !sResponseAux.equals("")){
                        statusLinea = ParseXMLFile.parseStatusFocalizacion(sResponseAux);
                    }
                    
                    Logger.write(" (i) Status linea           : " + statusLinea);
                    
                    if(statusLinea.equals("d")){
                        throw new ServiceException("La linea ya se encuentra activa");
                    }
                    
                    if(descripcion != null && descripcion.getIsPostpagoOrHibrido() != null && descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido"))
                    {
                        sResponse = consultaStatus.getStatusLineaPospago(dn);
                        if(sResponse != null && !sResponse.equals(""))
                        {
                            DispatcherVO status = ParseXMLFile.ParseServiciosStatusPrepago(sResponse);
                            if(status != null && status.getContrato() != null){
                                idLinea = status.getContrato();
                            }
                        }
                    }
                    
                    try{ oracle.cambiaStatusSET(idIdentificador, idLinea, "a", dn, reason, "", 2121); } 
                    catch (Exception e) { Logger.write(" (X) Exception              : Detail al ejecutar el metodo :: cambiaStatusSET"); }
                    
//                    sResponse = cambioLegacy.cambioStatus(idLinea, "a", dn, reason, "");
//                    
//                    if(sResponse != null && !sResponse.equals("")){
//                        dnreactivacion = ParseXMLFile.ParseDnReactivacionLegacy(sResponse);
//                    }
                    
                    responseBSCS = CallDNreactivacion.altaBajaServiciosBSCS(requestBSCS);
                    
                    if(responseBSCS != null && responseBSCS.getMsgError() != null){
                        dnreactivacion = new DNReactivacionVO();
                        dnreactivacion.setOperationCode(responseBSCS.getMsgError().equalsIgnoreCase("EXITO")?"1":"0");
                        dnreactivacion.setMessageCode(responseBSCS.getMsgError());
                    }
                }
                
                if(dnreactivacion != null)
                {
                    try{ oracle.suspensionReactivacionesRES(idIdentificador, 3, dnreactivacion.getOperationCode(), dnreactivacion.getMessageCode(), 2121,"",""); }
                    catch (Exception e) { Logger.write(" (X) Exception              : Detail al consultar el metodo :: suspensionReactivacionesRES"); }
                }
            }
            else
            {
                Logger.write(" (i) idServicio             : " + descripcion.getServicio());
                throw new ServiceException("IdServicio Invalido");
            }
        }
        catch (Exception e)
        {
            Logger.write(" (x) Exception.Suspencion   : " + descripcion.getServicio());
            throw new ServiceException(e.getMessage());
        }
        finally{
            sResponse          = null;
            sResponseAux       = null;
            CallDNreactivacion = null;
            focalizacion       = null;
            descripcion        = null;
            consultaPrepago    = null;
            consultaStatus     = null;
            oracle             = null;
            idLinea            = null;
            statusLinea        = null;
            responseBSCS       = null;
            requestBSCS        = null;
        }
        
        return dnreactivacion; 
	}
	
	private static boolean esTecnologiaLEGACY(DetalleFocalizacionVO datos){
	    boolean respuesta = false;
	    if( datos.getServicio() != null &&  
	       !datos.getServicio().equals("0") && 
	       !datos.getServicio().equals("80")){
	        respuesta = true;
	    }
	    return respuesta;
	}
}
