package mx.com.iusacell.services.miiusacell.arquitectura;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import javax.xml.rpc.ServiceException;

import mx.com.iusacell.comun.Linea;
import mx.com.iusacell.comun.Servicio;
import mx.com.iusacell.comun.Usuario;
import mx.com.iusacell.comun.Vigencia;
import mx.com.iusacell.middleware.servicios.gestion.Cobro;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.AltaServicioEtakVO;
import mx.com.iusacell.services.miiusacell.vo.AltaServicioLegacyVO;
import mx.com.iusacell.services.miiusacell.vo.AltaServicioPrepagoLegacyVO;
import mx.com.iusacell.services.miiusacell.vo.ContratarServiciosVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miusacell.call.CallServiceAltaBajaServicios;
import mx.com.iusacell.services.miusacell.call.CallServiceCompraBundle;
import mx.com.iusacell.services.miusacell.call.CallServiceConsultaPrepago;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceGestionServicios;
import mx.com.iusacell.services.miusacell.call.CallServiceObtenerDetallesService;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public abstract class AltaServicioXUsuExt {

	public static ContratarServiciosVO flujo(final String dn, final AltaServicioEtakVO datosAltaEtak, final AltaServicioLegacyVO datosAlta, final AltaServicioPrepagoLegacyVO datosAltaPrepago, final String numero) throws ServiceException, IOException, InvocationTargetException, SQLException
	{
		ContratarServiciosVO returnContratoServ = new ContratarServiciosVO();
		String sResponse = "";
		int idOperador = 0;
		CallServiceAltaBajaServicios servicioLEGACY = new CallServiceAltaBajaServicios();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		CallServiceCompraBundle bundles = new CallServiceCompraBundle();
		CallServiceConsultaPrepago consultaPrepago = new CallServiceConsultaPrepago();
		
		CallServiceObtenerDetallesService obtieneIdLinea = new CallServiceObtenerDetallesService();
		
		OracleProcedures oracle = new OracleProcedures();
		mx.com.iusacell.middleware.servicios.gestion.RespuestaServicios altaPrepago = null;
		final CallServiceGestionServicios wsFoca = new CallServiceGestionServicios();
		String idLineaPrepago = "";
		String[] equivalencia = new String[2];
		boolean valida = false;
		try
		{

			sResponse = focalizacion.focalizacion(dn);
			if(sResponse != null && !sResponse.equals(""))
				descripcion = ParseXMLFile.parseFocalizacion(sResponse);

			try{
				if(descripcion != null && descripcion.getIdOperador() != null && !descripcion.getIdOperador().trim().equals("")){
					idOperador = Integer.parseInt(descripcion.getIdOperador());
				}
			}catch (Exception e) {
				idOperador = 0;
			}
			
			if(descripcion != null && descripcion.getServicio() != null && descripcion.getSubservicio() != null && !descripcion.getServicio().equals("0") && !descripcion.getSubservicio().equals("0")){

				if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
					if(datosAltaEtak != null){
						
						try {
						    oracle.compraServicioEtakSET(numero, dn, idOperador, datosAltaEtak.getCosto(), datosAltaEtak.getId(), datosAltaEtak.getIdHistorico(), datosAltaEtak.getVigencia(), datosAltaEtak.getMonto(), 2021);
						}catch (Exception e) {
                            Logger.write("Detail a ejecutar el metodo :: compraServicioEtakSET");
						}
						//BORRAR
//						sResponse = serviceCompraServicioETAK.compraServicioETAK(dn, idOperador, datosAltaEtak.getCosto(), datosAltaEtak.getId(), datosAltaEtak.getIdHistorico(), datosAltaEtak.getVigencia(), datosAltaEtak.getMonto());
//						
//						if(sResponse != null && !sResponse.equals("")){
//							returnContratoServ = ParseXMLFile.parseContratarServ(sResponse);
//						}
						
						sResponse = bundles.compraServicioLegacyPrepago(dn, datosAltaEtak.getId(), "MIIUSACELL");
						if(sResponse != null && !sResponse.equals("")){
							returnContratoServ = ParseXMLFile.parseAltaLegacyPrepago(sResponse);
						}
						
                        if(returnContratoServ != null && !returnContratoServ.getOperationCode().equals("")) {
							String respMessage = "";	
							respMessage = Utilerias.respCodePrepagoLegacy(returnContratoServ.getOperationCode());
							returnContratoServ.setMessageCode(respMessage);	
						}
						
						if(returnContratoServ != null && !returnContratoServ.equals("")){
							try {
								oracle.compraServicioRespuesta(numero, 1, returnContratoServ.getMessageCode(),returnContratoServ.getOperationCode() , 2121,"","");							    
							}catch (Exception e) {
	                            Logger.write("Detail a ejecutar el metodo :: compraServicioRespuesta");
							}
						}
					}else{
						throw new ServiceException("Datos incompletos");
					}
				}
				else{
					if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
						
		                String serviciosPermitidos;              
		                serviciosPermitidos = oracle.getValorParametro(276);             
		                serviciosPermitidos = (serviciosPermitidos != null) ? serviciosPermitidos.toLowerCase() : "";
		                final String[] arrayServPerm = serviciosPermitidos.split(",");
                        
		                for(final String msg : arrayServPerm)
		                {
		                    if(datosAlta.getServicios().contains(msg))
		                    {
		                        valida = true;
		                        if(descripcion.getIsPostpagoOrHibrido().equals("POSPAGO")) valida = false;
		                        break;
		                    }
		                }
		                
		                String userMod = "APP";
						try
						{
							userMod = oracle.getValorParametro(138);
						}
						catch (Exception e) {
							Logger.write(e.getLocalizedMessage());
						}
		                
						if((descripcion.getIsPostpagoOrHibrido().equals("HIBRIDO")) && (valida))
			            {
							String responseEq = obtieneIdLinea.obtenerEquivalencia(datosAlta.getServicios());
							if(responseEq != null && !responseEq.equals("")){
			                    equivalencia = ParseXMLFile.parseEquivalencia(responseEq);
			                }
							
			                String sResponseP = "";
			                sResponseP = obtieneIdLinea.obtenerIdLinea(dn);
			                if(sResponseP != null && !sResponseP.equals("")){
			                    idLineaPrepago = ParseXMLFile.parseIdLinea(sResponseP);
			                }
			                if(idLineaPrepago == null || idLineaPrepago.length() == 0){
			                	altaPrepago = new mx.com.iusacell.middleware.servicios.gestion.RespuestaServicios();
			                	altaPrepago.setRespuesta(false);
			                	altaPrepago.setMensaje("Sin informacion de la linea");
			                	returnContratoServ.setMessageCode(altaPrepago.getMensaje());
			                    returnContratoServ.setOperationCode("01");
			                    throw new ServiceException(altaPrepago.getMensaje());
			                }else{
			                	Vigencia[] vigencias = null;
			                	vigencias = new Vigencia[1];
			                	vigencias[0] = new Vigencia();
			                	vigencias[0].setCantidad(0);
			                	Servicio[] servicios = null;
			                	servicios = new Servicio[1];
			                	servicios[0] = new Servicio();
			                	servicios[0].setId(equivalencia[0]);
			                	servicios[0].setVigencias(vigencias);

			                	final Linea   linea     = new Linea();
			                	linea.setId(idLineaPrepago);
			                	linea.setTipo(Integer.parseInt(equivalencia[1]));
			                	linea.setServicios(servicios);
			                	final Usuario usuario   = new Usuario();
			                	usuario.setId("VTAPTALES");
			                	usuario.setModulo(13);
			                	final Cobro   cobro     = null; 
			                	final int     operacion = 0;
			                	altaPrepago = wsFoca.operacionServicio(linea, usuario, cobro, operacion);      
			                	if (altaPrepago.getRespuesta()){
			                		returnContratoServ.setMessageCode("Servicio contratado.");
			                		returnContratoServ.setOperationCode("0");
			                	} else{
			                		returnContratoServ.setMessageCode("No se pudo realizar la contrataci?n del servicio.");
			                		returnContratoServ.setOperationCode("1");
			                		throw new ServiceException("No se pudo realizar la contratacion del servicio.");
			                	}

			                	//Notifica a BSCS
			                	try{
			                		oracle.compraServicioLEGACYSET(numero, descripcion.getCoID(), datosAlta.getDnUsa(), datosAlta.getContinenteFavortio(), datosAlta.getServicios(), descripcion.getTmCode(), "1", userMod, 2121);
			                	}catch (Exception e) {
			                		Logger.write("Detail a ejecutar el metodo :: compraServicioLEGACYSET");
			                	}
			                	sResponse = servicioLEGACY.altaBajaServicios(descripcion.getCoID(), datosAlta.getDnUsa(), datosAlta.getContinenteFavortio(), datosAlta.getServicios(), descripcion.getTmCode(), "1", userMod);

			                	if(sResponse != null && !sResponse.equals("")){
			                		returnContratoServ = ParseXMLFile.parseContratarServLegacy(sResponse);
			                	}

			                	if(returnContratoServ != null && !returnContratoServ.getOperationCode().equals("")){
			                		try {
			                			oracle.compraServicioRespuesta(numero, 2, returnContratoServ.getMessageCode(),returnContratoServ.getOperationCode() , 2121,"","");                                        
			                		}catch (Exception e) {
			                			Logger.write("Detail a ejecutar el metodo :: compraServicioRespuesta");
			                		}
			                	}



			                }
			            }
						
						if((datosAlta != null) && (valida == false)){							
							try{
								oracle.compraServicioLEGACYSET(numero, descripcion.getCoID(), datosAlta.getDnUsa(), datosAlta.getContinenteFavortio(), datosAlta.getServicios(), descripcion.getTmCode(), "1", userMod, 2121);
							}catch (Exception e) {
								Logger.write("Detail a ejecutar el metodo :: compraServicioLEGACYSET");
							}

							sResponse = servicioLEGACY.altaBajaServicios(descripcion.getCoID(), datosAlta.getDnUsa(), datosAlta.getContinenteFavortio(), datosAlta.getServicios(), descripcion.getTmCode(), "1", userMod);

							if(sResponse != null && !sResponse.equals("")){
								returnContratoServ = ParseXMLFile.parseContratarServLegacy(sResponse);
							}
							
                            if(returnContratoServ != null && !returnContratoServ.getOperationCode().equals("")){
								try {
									oracle.compraServicioRespuesta(numero, 2, returnContratoServ.getMessageCode(),returnContratoServ.getOperationCode() , 2121,"","");							    
								}catch (Exception e) {
		                            Logger.write("Detail a ejecutar el metodo :: compraServicioRespuesta");
								}
							}
							
						}else if (valida == false){
							throw new ServiceException("Datos incompletos");
						}
					}else{
						if(datosAltaPrepago != null){
							String idPrepago = "";
							sResponse = consultaPrepago.consultaPrepago(dn);
							if(sResponse != null && !sResponse.equals("")){
								idPrepago = ParseXMLFile.ParseConsultaPrepago(sResponse);
							
								
    						try{
	     						oracle.altaLEGACYSET(numero, idPrepago, datosAltaPrepago.getServiciosId(), datosAltaPrepago.getServicioOrigen(), datosAltaPrepago.getVigenciasUnidad() , datosAltaPrepago.getVigenciasCantidad(), datosAltaPrepago.getOperacion(), 2121);
								}catch (Exception e) {
	                               Logger.write("Detail al ejecutar el metodo :: altaLEGACYSET");
								}

//							sResponse = servicioLegacy.altaLegacy(idPrepago, datosAltaPrepago.getServiciosId(), datosAltaPrepago.getServicioOrigen(), datosAltaPrepago.getVigenciasUnidad(), datosAltaPrepago.getVigenciasCantidad(), datosAltaPrepago.getOperacion());
							sResponse = bundles.compraServicioLegacyPrepago(dn, datosAltaPrepago.getServiciosId(), "MIIUSACELL");
							if(sResponse != null && !sResponse.equals(""))
								returnContratoServ = ParseXMLFile.parseAltaLegacyPrepago(sResponse);
							}
							
                            if(returnContratoServ != null && !returnContratoServ.getOperationCode().equals("")) {
								String respMessage = "";	
								respMessage = Utilerias.respCodePrepagoLegacy(returnContratoServ.getOperationCode());
								returnContratoServ.setMessageCode(respMessage);	
							}
							
							if(returnContratoServ != null && !returnContratoServ.equals("")){
								try {
									oracle.compraServicioRespuesta(numero, 3, returnContratoServ.getMessageCode(),returnContratoServ.getOperationCode() , 2121,"","");							    
								}catch (Exception e) {
		                            Logger.write("Detail a ejecutar el metodo :: compraServicioRespuesta");
								}
						    }
						}else{
							throw new ServiceException("Datos incompletos");
						}
					}
						
				}
			}else{
				if(descripcion.getServicio() != null){
					Logger.write("   IdServicio: " + descripcion.getServicio());
				}else{
					Logger.write("   IdServicio Invalido");
				}
				throw new ServiceException("IdServicio Invalido");
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return returnContratoServ;
	}
}
