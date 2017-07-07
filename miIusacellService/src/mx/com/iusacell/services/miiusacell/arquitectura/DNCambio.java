package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ResourceBundle;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.ConsultaPrepagoVO;
import mx.com.iusacell.services.miiusacell.vo.DNCambioVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.NumerosVO;
import mx.com.iusacell.services.miiusacell.vo.ReprogramacionVO;
import mx.com.iusacell.services.miusacell.call.CallServiceActivacion;
import mx.com.iusacell.services.miusacell.call.CallServiceCambioDn;
import mx.com.iusacell.services.miusacell.call.CallServiceCambioMdn;
import mx.com.iusacell.services.miusacell.call.CallServiceCompNumeros;
import mx.com.iusacell.services.miusacell.call.CallServiceConsultaPrepago;
import mx.com.iusacell.services.miusacell.call.CallServiceDNCambio;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceReprogramacion;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class DNCambio {

	@SuppressWarnings("unused")
	public static DNCambioVO flujo (final String dnActual, final String dnNuevo, final String cveEdoCen, final String cveMunCen, String cvePobCen, final String idIdentificador) throws ServiceException
	{
		String sResponse="";
		String poblacion = "";
		String municipio = "";
		String region = "";
		String estado = "";
		String idFact = "";
		String idTransaccion = "";
		String resposeCommit = "";
		String resposeAvailable = "";
		String resposeCambioMdn = "";
		String idLinea = "";
		String cambioBSCS = "";
		String prepagoPorProcess = "";
		CallServiceDNCambio dnCambio = new CallServiceDNCambio();		
		DNCambioVO cambioVo = new DNCambioVO();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		CallServiceConsultaPrepago consultaPrepago = new CallServiceConsultaPrepago();
		CallServiceCompNumeros compomenteNumeros = new CallServiceCompNumeros();
		CallServiceActivacion numeros = new CallServiceActivacion();
		CallServiceCambioDn cambiosPrePagoPospago = new CallServiceCambioDn();
		NumerosVO numerosResponse = new NumerosVO();
		ConsultaPrepagoVO prepago = new ConsultaPrepagoVO();
		CallServiceReprogramacion reprogramacion = new CallServiceReprogramacion();
		ReprogramacionVO reprogramacionResponse = new ReprogramacionVO();
		CallServiceCambioMdn cambioMdn = new CallServiceCambioMdn();
		OracleProcedures oracle = new OracleProcedures();
		
		try{
			
			try{
				cambioBSCS = oracle.getValorParametro(14);
				prepagoPorProcess = oracle.getValorParametro(18);
			}catch (ServiceException e) {
				cambioBSCS = "0";
				prepagoPorProcess = "1";
			}
			
			sResponse = focalizacion.focalizacion(dnActual);
			if(sResponse != null && !sResponse.equals("")){
				descripcion = ParseXMLFile.parseFocalizacion(sResponse);
				if(descripcion.getIsPostpagoOrHibrido() == null){
					idFact = "2";
				}else if(descripcion.getIsPostpagoOrHibrido().equals("")){
					idFact = "9";
				}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
					idFact = "2";
				}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
					idFact = "12";
				}
			}
			if(descripcion.getServicio() != null && descripcion.getServicio().equals("80") && descripcion.getSubservicio() != null && descripcion.getSubservicio().equals("80")){
 			//************************  Nuevo *******************
					String idCiudad ="";
					sResponse = numeros.consultaNumerosPrepago(dnActual);
					if(sResponse != null && !sResponse.equals("")){
						numerosResponse = ParseXMLFile.parseConsultaNumerosReserve(sResponse);
					}
					if(numerosResponse != null && numerosResponse.getStrCveCensal() != null && !numerosResponse.getStrCveCensal().equals("") && numerosResponse.getStrCveCensal().length() >= 5){
						estado = numerosResponse.getStrCveCensal().substring(0,2);
						municipio = numerosResponse.getStrCveCensal().substring(2,5);
						poblacion = numerosResponse.getStrCveCensal().substring(5);
						idCiudad= estado + municipio + poblacion;
					}
					region = ResourceBundle.getBundle("regiones").getString(estado);
					
					Logger.write("   CambioDN plataforma EeTAK");
	
					try{
						oracle.cambioDNrequest(idIdentificador, dnActual, dnNuevo, 2121);
					}catch (Exception e) {
                       Logger.write("Detail al ejecutar el metodo :: cambioDNrequest");
					}
					
					sResponse = dnCambio.DNCambio(dnActual, dnNuevo,idCiudad, region,descripcion.getIdOperador());
					
					if(sResponse != null && !sResponse.equals("")){
						cambioVo = ParseXMLFile.ParseDnCambio(sResponse);
					}
					
					if(cambioVo != null){
					   try{
						   oracle.cambioDNresponse(idIdentificador, cambioVo.getResponseCode(), cambioVo.getResponseMessage(), 2021,"","");
					   }catch (Exception e) {
						 Logger.write("Detail al ejecutar el metodo :: cambioDNresponse");
					   }
					}
					
			}else if(descripcion.getServicio() != null &&  !descripcion.getServicio().equals("0") && !descripcion.getServicio().equals("80"))
			{
				sResponse = numeros.consultaNumerosPrepago(dnActual);
				if(sResponse != null && !sResponse.equals("")){
					numerosResponse = ParseXMLFile.parseConsultaNumerosReserve(sResponse);
				}
				if(numerosResponse != null && numerosResponse.getStrCveCensal() != null && !numerosResponse.getStrCveCensal().equals("") && numerosResponse.getStrCveCensal().length() >= 5){
					estado = numerosResponse.getStrCveCensal().substring(0,2);
					municipio = numerosResponse.getStrCveCensal().substring(2,5);
					poblacion = numerosResponse.getStrCveCensal().substring(5);
				}
				region = ResourceBundle.getBundle("regiones").getString(estado);
				
				if(cvePobCen == null || cvePobCen.equals("")){
					cvePobCen = "0001";
				}
				
				sResponse = consultaPrepago.consultaPrepago(dnActual);
				if(sResponse != null && !sResponse.equals("")){
					prepago = ParseXMLFile.ParseConsultaPrepagoMin(sResponse);
				}
				
				//CambioMDN
				sResponse = numeros.consultaNumerosPrepago(dnNuevo);
				if(sResponse != null && !sResponse.equals("")){
					numerosResponse = ParseXMLFile.parseConsultaNumerosReserve(sResponse);
				}
				
				if(prepago != null && prepago.getIdLinea() != null && !prepago.getIdLinea().equals(""))
					idLinea = prepago.getIdLinea();
				else
					idLinea = descripcion.getIdPrepago();
				
				if(idLinea == null || idLinea.equals("")){
					idLinea = descripcion.getCoID();
				}
				
				try{
					oracle.cambioMdnRequest(idIdentificador, dnNuevo, numerosResponse.getImsi1(), idLinea, (cveEdoCen+cveMunCen+cvePobCen) , "24", 2021);
				}catch (Exception e) {
					Logger.write("Detail al generar metodo :: cambioMdnRequest");
				}
				
				
				if(cambioBSCS.equals("0")){
					//Componente
					//Prepago
					if(descripcion.getIsPostpagoOrHibrido() == null || descripcion.getIsPostpagoOrHibrido().equals("")){
						
						if(prepagoPorProcess.equals("1")){
							sResponse = cambioMdn.cambioMdn(dnNuevo, numerosResponse.getImsi1(), idLinea, (cveEdoCen+cveMunCen+cvePobCen), "24");
							if(sResponse != null && !sResponse.equals("")){
								//					resposeCambioMdn = ParseXMLFile.ParseCambioMdn(sResponse);
							}
						}else{
							sResponse = cambiosPrePagoPospago.MdnPrepagoA2(idLinea, dnNuevo);
							if(sResponse != null && !sResponse.equals("")){
								//					resposeCambioMdn = ParseXMLFile.ParseCambioMdn(sResponse);
							}	
						}
					}

					//PosPago
					if(descripcion.getIsPostpagoOrHibrido() != null && descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
						sResponse = cambiosPrePagoPospago.MdnPospagoA2(dnActual, dnNuevo, "PORTDIST");
						if(sResponse != null && !sResponse.equals("")){
							//					resposeCambioMdn = ParseXMLFile.ParseCambioMdn(sResponse);
						}
					}

					//Hibrido
					if(descripcion.getIsPostpagoOrHibrido() != null && descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
						sResponse = cambiosPrePagoPospago.MdnPrepagoA2(idLinea, dnNuevo);
						if(sResponse != null && !sResponse.equals("")){
							//					resposeCambioMdn = ParseXMLFile.ParseCambioMdn(sResponse);
						}

						sResponse = cambiosPrePagoPospago.MdnPospagoA2(dnActual, dnNuevo, "PORTDIST");
						if(sResponse != null && !sResponse.equals("")){
							//					resposeCambioMdn = ParseXMLFile.ParseCambioMdn(sResponse);
						}
					}
					//Componente
				}
				else{
					//BSCS
					sResponse = cambioMdn.cambioMdn(dnNuevo, numerosResponse.getImsi1(), idLinea, (cveEdoCen+cveMunCen+cvePobCen), "24");
					if(sResponse != null && !sResponse.equals("")){
						//					resposeCambioMdn = ParseXMLFile.ParseCambioMdn(sResponse);
					}
					//BSCS
				}
				//Fin - CambioMDN

				try{
					oracle.doChangeMdnRequest(idIdentificador, dnActual, dnNuevo, region, cveEdoCen, cveMunCen, cvePobCen, idFact, descripcion.getIdFormaPago(), descripcion.getModalidad(), descripcion.getIdOperador(), descripcion.getServicio(), descripcion.getSubservicio(), descripcion.getTecnologia(), "PVS", 2021);
				}catch (Exception e) {
					Logger.write("Detail al ejecutar el metodo :: doChangeMdnRequest");
				}
				
				sResponse = compomenteNumeros.doChangeMdn(dnActual, dnNuevo, region, cveEdoCen, cveMunCen, cvePobCen, idFact, descripcion.getIdFormaPago(), descripcion.getModalidad(), descripcion.getIdOperador(), descripcion.getServicio(), descripcion.getSubservicio(), descripcion.getTecnologia(), "PVS");
				
				if(sResponse != null && !sResponse.equals("")){
					idTransaccion = ParseXMLFile.ParseChangeMdn(sResponse);
				}
				
				if(idTransaccion != null && idTransaccion.equals("")){
					 try{
						  oracle.doChangeMdnResponse(idIdentificador, idTransaccion, "miIusacell", 2021,"","");
					   }catch (Exception e) {
						 Logger.write("Detail al ejecutar el metodo :: doChangeMdnResponse");
					   }
				}
				
				try{
					oracle.doCommitReserve(idIdentificador,idTransaccion , "miIusacell", 2021);
				}catch (Exception e) {
					Logger.write("Detail al ejecutar el metodo :: doCommitReserve");
				}
				
				sResponse = compomenteNumeros.doCommitReserve(idTransaccion, "PVS");
				if(sResponse != null && !sResponse.equals("")){
					resposeCommit = ParseXMLFile.ParseCommitReserve(sResponse);
				}
				
				if(resposeCommit != null && resposeCommit.equals(""))
				{
				   try{
					   oracle.doCommitResponse(idIdentificador, resposeCommit, idTransaccion, "miIusacell", 2021,"","");
				   }catch (Exception e) {
					Logger.write("Detail al ejecutar el metodo :: doCommitResponse");
				   }
				}
				
				try{
					oracle.doAvailableRequest(idIdentificador, dnActual, "miIusacell", 2021);
				}catch (Exception e) {
					Logger.write("Detail al ejecutar el metodo :: doAvailableRequest");
				}
				
				sResponse = compomenteNumeros.doAvailable(dnActual, "PVS");
				
				if(sResponse != null && !sResponse.equals("")){
					resposeAvailable = ParseXMLFile.ParseAvaible(sResponse);
				}
				
				if(resposeAvailable != null && resposeAvailable.equals("")){
					try{
						oracle.doAvailableResponse(idIdentificador, resposeAvailable, 2021,"","");
					}catch (Exception e) {
						Logger.write("Detail al ejecutar el metodo :: doAvailableResponse");
					}
				}
				
				try{
	 				  oracle.programacionRequest(idIdentificador, "E2EtoOTARep", "-$EnEUser2014", "1", descripcion.getIdOperador(), dnNuevo, numerosResponse.getImsi1(), 2021);
 					}catch (Exception e) {
						Logger.write("Detail al ejecutar el metodo :: programacionRequest");
				}
				
				sResponse = reprogramacion.reprogramacion("E2EtoOTARep", "-$EnEUser2014", "1", descripcion.getIdOperador(), dnNuevo, numerosResponse.getImsi1());
				if(sResponse != null && !sResponse.equals("")){
					reprogramacionResponse = ParseXMLFile.Parsereprogramacion(sResponse);
					if(reprogramacionResponse != null){
						Logger.write("      CodigoRespuesta             : " + reprogramacionResponse.getCodigoRespuesta());
						Logger.write("      Dn                          : " + reprogramacionResponse.getDn());
						Logger.write("      Mensaje                     : " + reprogramacionResponse.getMensaje());
					}
				}
				
				if(reprogramacionResponse != null && !reprogramacionResponse.equals("")){
					try {
					   oracle.reProgramacionResponse(idIdentificador, reprogramacionResponse.getCodigoRespuesta() , reprogramacionResponse.getDn(), reprogramacionResponse.getMensaje(), 2021,"","");
					}catch (Exception e) {
						Logger.write("Detail al ejecutarse el metodo :: reProgramacionResponse");
					}
				}
				
				
				int respuesta = -1;
				respuesta = oracle.cambioDNBD(dnActual, dnNuevo, 212121);
				
				if(respuesta != 0){
					throw new ServiceException("El proceso de cambio DN no concluyo correctamente");
				}else{
					cambioVo.setResponseCode("0");
					cambioVo.setResponseMessage("Exito");
				}
				
			}else{
				Logger.write("   IdServicio: " + descripcion.getServicio());
				throw new ServiceException("IdServicio Invalido");
			}
			
			try{
				oracle.actualizaDN(dnNuevo);
			}catch (Exception e) {
				Logger.write("   No se pudo liberar en BD el numero: " + dnNuevo);
			}

		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

		return cambioVo; 
	}
}
