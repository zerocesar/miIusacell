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

public abstract class DNReactivacion {
	public static DNReactivacionVO flujo (final String dn, final String reason, final int tipo, final String idIdentificador) throws ServiceException
	{
		String sResponse="";
		CallServiceDNReactivacion CallDNreactivacion = new CallServiceDNReactivacion();
		DNReactivacionVO dnreactivacion = new DNReactivacionVO();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		CallServiceCambioStatus cambioLegacy = new CallServiceCambioStatus();
		CallServiceConsultaPrepago consultaPrepago = new CallServiceConsultaPrepago();
		OracleProcedures oracle = new OracleProcedures();
		CallServiceServiciosContratos consultaStatus = new CallServiceServiciosContratos();
		String idLinea = "";

		try{

			sResponse = focalizacion.focalizacion(dn);
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
					
					sResponse = CallDNreactivacion.DNSuspension(dn, reason);
					if(sResponse != null && !sResponse.equals("")){
						
						dnreactivacion = ParseXMLFile.ParseDnSuspension(sResponse);
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
					
					sResponse = CallDNreactivacion.DNReactivacion(dn, reason);
					if(sResponse != null && !sResponse.equals("")){
						dnreactivacion = ParseXMLFile.ParseDnReactivacion(sResponse);
						
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
}
