package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.RespuestaVO;
import mx.com.iusacell.services.miiusacell.vo.SCobrosVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosContratarVO;
import mx.com.iusacell.services.miusacell.call.CallServiceAirAdapter;
import mx.com.iusacell.services.miusacell.call.CallServiceCambioPlan;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceSCobro;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class CambioPlan {

	public static boolean cambiarPlan(final String dn, final int idNvoPlan, int montoCargo) throws ServiceException
	{
		boolean respuesta = false;
		String sResponse = "";
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();		
		List<ServiciosContratarVO> serviciosContratados = new ArrayList<ServiciosContratarVO>();
		CallServiceCambioPlan cambioWS = new CallServiceCambioPlan();
		CallServiceSCobro cobroWS = new CallServiceSCobro();
		CallServiceAirAdapter airAdapterWS = new CallServiceAirAdapter();
		RespuestaVO respuestaCambioPlan = null;
		SCobrosVO respuestaCobro = new SCobrosVO();		
		SCobrosVO respuestaReversoCobro = null;
		OracleProcedures oracleProcedures = new OracleProcedures();
		RespuestaVO respuestaUpdateBanderaCargo = null;
		int idBitacora = 0;		
		//int montoCargo = 0;
		int idAjuste = 118;//COBRO POR CAMBIO DE PLAN
		String paramActivaCobro = "";		
		String paramBanderaCobro = "";
		String serviceOfferingValue = "";
		boolean realizarCobro = false;
		boolean cambioPlanSinCosto = false;
		boolean validaBanderaCobro = false;
		String serviceOfferingId = "27";
		String usuarioId = "";

		if(dn == null || dn.equals("")){
			Logger.write("   Dn no puede ir vacio");
			throw new ServiceException("Dn no puede ir vacio");
		}
		usuarioId = dn.substring(3);
		try {

			try{
				sResponse = focalizacion.focalizacion(dn);
				if(sResponse != null && !sResponse.equals("")){
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);
				}	
			}catch (ServiceException e) {
				descripcion = new DetalleFocalizacionVO();
			}

			if(descripcion.getIdPlan() == null || descripcion.getIdPlan().equals("") || !descripcion.getTecnologia().equals("2"))
				throw new ServiceException("El DN no puede realizar cambio de Plan");

			int planActual = 0;			
			try{
				planActual = Integer.parseInt(descripcion.getIdPlan());
			}catch (NumberFormatException e) {
				Logger.write("   NumberFormatException " + e.getLocalizedMessage());
			}
			
			if(planActual == idNvoPlan)
				throw new ServiceException("El plan debe ser distinto al plan actual");
			
			serviciosContratados = ConsultaServicioXUsuExt.serviciosContratar(dn,
					descripcion.getServicio(),descripcion.getSubservicio(),
					descripcion.getCoID(),descripcion.getIsPostpagoOrHibrido());
			
			try{
				paramActivaCobro = oracleProcedures.getValorParametro(152);				
				paramBanderaCobro = oracleProcedures.getValorParametro(162);;
			}
			catch (Exception e) {
				paramActivaCobro = "";				
				paramBanderaCobro = "";
			}
			
			realizarCobro = (paramActivaCobro.equals("1")) ? true : false;
			validaBanderaCobro = (paramBanderaCobro.equals("1")) ? true : false;

			/** APLICA CARGO */		
			if(realizarCobro)
			{
				if(validaBanderaCobro)
				{
					sResponse = airAdapterWS.getDetalleCuenta("52"+dn);
					serviceOfferingValue = ParseXMLFile.parseGetServiceOfferingValue(sResponse,serviceOfferingId);
					cambioPlanSinCosto = (serviceOfferingValue.equals("1")) ? true : false;					
				}
				else
					cambioPlanSinCosto = false;
				
				if(cambioPlanSinCosto)
				{
					montoCargo = 0;
					Logger.write("   >>> C A M B I O  P L A N  S I N  C O S T O <<<");
				}
				
				try {
					try {
						idBitacora = oracleProcedures.bitacoraCobro(dn, montoCargo+"", idAjuste+"", "", usuarioId);
						Logger.write("   idBitacora : " + idBitacora);
					} catch (Exception e) {
						Logger.write("   Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
					}
					sResponse = cobroWS.aplicaCobro(dn,montoCargo,idAjuste);
					respuestaCobro = ParseXMLFile.parseAplicaSCobro(sResponse);
					if(respuestaCobro.getCodigo() != null && !respuestaCobro.getCodigo().equals("1"))
							throw new ServiceException("SCobros [" + respuestaCobro.getCodigo() +"] " +
									respuestaCobro.getMensaje());	
					try {
						oracleProcedures.bitacoraCobroRespuesta(idBitacora, respuestaCobro.getCodigo(), respuestaCobro.getMensaje(), respuestaCobro.getIdCobro()+"", sResponse, usuarioId);						
					} catch (Exception e2) {
						Logger.write("   Bitacora: No se impacto la bitacora - " + e2.getLocalizedMessage());
					}
				} catch (Exception e) {
					try {
						 oracleProcedures.bitacoraCobroRespuesta(idBitacora, respuestaCobro.getCodigo(), e.getLocalizedMessage(), respuestaCobro.getIdCobro()+"", sResponse, usuarioId);						
					} catch (Exception e2) {
						Logger.write("   Bitacora: No se impacto la bitacora - " + e2.getLocalizedMessage());
					}
					throw new ServiceException(e.getLocalizedMessage());
				}
			}

			if((respuestaCobro.getCodigo() != null && respuestaCobro.getCodigo().equals("1"))
					|| !realizarCobro)
			{	
				/** CAMBIO PLAN */	
				int intentos = 0;
				try {
					oracleProcedures.bitacoraCambioPlan(idBitacora, dn, "0", descripcion.getIdPlan(), idNvoPlan+"", "", usuarioId);					
				} catch (Exception e2) {
					Logger.write("   Bitacora: No se impacto la bitacora - " + e2.getLocalizedMessage());
				}
				while(intentos < 2)
				{
					try
					{
						sResponse = cambioWS.realizaCambioPlan(dn, 0, descripcion.getIdPlan(), idNvoPlan, serviciosContratados);				
						respuestaCambioPlan = ParseXMLFile.parseCambioPlan(sResponse);

						respuesta = respuestaCambioPlan.isExito();
						if(!respuestaCambioPlan.isExito())
						{						
							throw new ServiceException("[" + respuestaCambioPlan.getCdgRespuesta() + "] " + respuestaCambioPlan.getMensaje());					
						}
						else
						{							
							try {
								oracleProcedures.bitacoraCambioPlanRespuesta(idBitacora, respuestaCambioPlan.getCdgRespuesta(), respuestaCambioPlan.getMensaje(), respuestaCambioPlan.isExito()+"", sResponse, usuarioId);								
							} catch (Exception e2) {
								Logger.write("   Bitacora: No se impacto la bitacora - " + e2.getLocalizedMessage());
							}
							try
							{
								if(cambioPlanSinCosto)
								{
									sResponse = airAdapterWS.actualizaEstatus("52"+dn, serviceOfferingId, "0");
									respuestaUpdateBanderaCargo = ParseXMLFile.parseUpdateSubscriberSegmentation(sResponse);
									Logger.write("   updateSubscriberSegmentation response : " + respuestaUpdateBanderaCargo.isExito());
									if(!respuestaUpdateBanderaCargo.isExito())
										throw new ServiceException("[" +respuestaUpdateBanderaCargo.getCdgRespuesta() + "] " + 
												respuestaUpdateBanderaCargo.getMensaje());
										
								}
							}
							catch (Exception e) {
								Logger.write("   updateSubscriberSegmentation Exception - " + e.getLocalizedMessage());
							}
							break;
						}

					}
					catch (Exception e) {
						if(intentos == 0){
							Logger.write("      (ServiceException) Cambio Plan["+(intentos+1)+"] : " + e.getLocalizedMessage());	
							intentos++;
						}else{
							try {
								oracleProcedures.bitacoraCambioPlanRespuesta(idBitacora, "", e.getLocalizedMessage(), "", sResponse, usuarioId);								
							} catch (Exception e2) {
								Logger.write("   Bitacora: No se impacto la bitacora - " + e2.getLocalizedMessage());
							}
							/** REVERSO CARGO*/
							String error = e.getLocalizedMessage();
							if(realizarCobro)
							{
								try
								{
									try {
										oracleProcedures.bitacoraReversoCobro(idBitacora, dn, montoCargo+"", respuestaCobro.getIdCobro()+"", "", usuarioId);										
									} catch (Exception e2) {
										Logger.write("   Bitacora: No se impacto la bitacora - " + e2.getLocalizedMessage());
									}

									sResponse = cobroWS.reversarCobro(dn, montoCargo, respuestaCobro.getIdCobro(), respuestaCobro.getWallets());
									respuestaReversoCobro = ParseXMLFile.parseReversoSCobro(sResponse);

									try {
										oracleProcedures.bitacoraReversoCobroRespuesta(idBitacora, respuestaReversoCobro.getCodigo(), respuestaReversoCobro.getMensaje(), respuestaReversoCobro.getIdCobro()+"", sResponse, usuarioId);										
									} catch (Exception e2) {
										Logger.write("   Bitacora: No se impacto la bitacora - " + e2.getLocalizedMessage());
									}
								}
								catch (Exception ex) {
									try {
										oracleProcedures.bitacoraReversoCobroRespuesta(idBitacora, "", ex.getLocalizedMessage(), "", sResponse, usuarioId);										
									} catch (Exception e2) {
										Logger.write("   Bitacora: No se impacto la bitacora - " + e2.getLocalizedMessage());
									}
									error += " reversoCobro: " + ex.getLocalizedMessage(); 
								}
							}
							throw new ServiceException(error);
						}
					}
				}
			}


		} catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return respuesta;
	}

}
