package mx.com.iusacell.services.miiusacell.arquitectura;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import javax.xml.rpc.ServiceException;

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
		OracleProcedures oracle = new OracleProcedures();

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
						if(datosAlta != null){
							String userMod = "APP";
							try
							{
								userMod = oracle.getValorParametro(138);
							}
							catch (Exception e) {
								Logger.write(e.getLocalizedMessage());
							}
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
							
						}else{
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
