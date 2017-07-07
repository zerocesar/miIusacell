package mx.com.iusacell.services.miiusacell.arquitectura;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.ConsumoDetalleVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoFechaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosTotalesResponseVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosTotalesVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.ObtenerDescripcionPlanesVO1;
import mx.com.iusacell.services.miiusacell.vo.SaldosFechaVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosAdicionalesVO;
import mx.com.iusacell.services.miusacell.call.CallServiceDetalleConsumosETAK_LEGACY;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
//import mx.com.iusacell.services.miusacell.call.CallServiceObtenerDescripcionPlanes;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class DetalleConsumosTotales {

	public ConsumosTotalesVO flujo(final String dn) throws ServiceException{

		ConsumosTotalesVO response = new ConsumosTotalesVO();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
//		CallServiceObtenerDescripcionPlanes obtenPlanes = new CallServiceObtenerDescripcionPlanes();
		ObtenerDescripcionPlanesVO1 respuestaPlan = new ObtenerDescripcionPlanesVO1();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
		StringBuffer sResponse = new StringBuffer();
		Integer totalTiempo = 0;
		Integer totalMinCom = 0;
		int Minutoscomunidad = 0;
		double totalCosto = 0;
		double totalMegas = 0;
		double costoMegas = 0;
		double pivote = 0;
		double salAnt = 0;
		double salDes = 0;

		sResponse.append(focalizacion.focalizacion(dn));
		if(sResponse != null && !sResponse.equals("")){
			descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
			sResponse.setLength(0);
		}	
		
		if(descripcion.getFechaCorte() == null){
			descripcion.setFechaCorte("");
		}
		if(descripcion != null){
			response.setDescripcionPlan(descripcion.getDescripcionPlan());
			response.setDetalleFocalizacion(descripcion);
		}

		Proceso llamadas = new Proceso("Llamadas","1", dn, descripcion.getFechaCorte());
		Proceso mensajes = new Proceso("Mensajes","2", dn, descripcion.getFechaCorte());
		Proceso navegacion = new Proceso("Navegacion","4", dn, descripcion.getFechaCorte());

		Logger.write("LLamada Status: " + llamadas.t.isAlive());
		Logger.write("LLamada Status: " + llamadas.t.isAlive());
		try{
			llamadas.t.join();
			mensajes.t.join();
			navegacion.t.join();

			response.setDetalleConsumoLlamadas(llamadas.getResponse().getDetalleConsumo());
			response.setDetalleSaldosLlamadas(llamadas.getResponse().getDetalleSaldos());
			response.setDetalleConsumoMensajes(mensajes.getResponse().getDetalleConsumo());
			response.setDetalleSaldosMensajes(mensajes.getResponse().getDetalleSaldos());
			response.setDetalleConsumoNavegacion(navegacion.getResponse().getDetalleConsumo());
			response.setDetalleSaldosNavegacion(navegacion.getResponse().getDetalleSaldos());
			
			try{
//				if(descripcion.getIdPlan() != null && !descripcion.getIdPlan().equals("")){
//					sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes(descripcion.getIdPlan(), "", "0", 212121, "10.10.10"));
//				}else if(descripcion.getDescripcionPlan() != null && !descripcion.getDescripcionPlan().equals("")){
//					sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes("", descripcion.getDescripcionPlan(), "0", 212121, "10.10.10"));
//				}else if(descripcion.getCode() != null && !descripcion.getCode().equals("")){
//					sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes("", "", descripcion.getCode(), 212121, "10.10.10"));
//				}
//				if(sResponse != null && !sResponse.equals("")){
//					respuestaPlan = ParseXMLFile.ParseObtenerDescripcionPlanes(sResponse.toString());
//					sResponse.setLength(0);
//				}				
				respuestaPlan = OfertaComercial.consultaDescripcionPlanes(descripcion.getIdPlan(), descripcion.getDescripcionPlan(), descripcion.getCode(), 212121, "10.10.10.10");
				
			}catch (ServiceException e) {
				respuestaPlan = new ObtenerDescripcionPlanesVO1();
			}
			
			if(respuestaPlan != null){
				response.setDatosPlanes(respuestaPlan);
			}
			
			/*Nuevo*/
			try{
				List<ServiciosAdicionalesVO> listServiciosAdicionales = new ArrayList<ServiciosAdicionalesVO>();
				listServiciosAdicionales = GetServiciosAdicionales.flujoPorDnFromWall(respuestaPlan, descripcion, 0, dn);
				response.setDetalleServiciosAdicionales(listServiciosAdicionales);
			}catch (Exception e) {
				Logger.write(e.getLocalizedMessage());
			}
			

			if(response != null && response.getDetalleConsumoLlamadas() != null && response.getDetalleConsumoLlamadas().size() > 0){
  
				try{
					Minutoscomunidad = Integer.parseInt(respuestaPlan.getMinutoscomunidad().trim());
				}catch (Exception e) {
					Minutoscomunidad = 0;
				}
				
				for (ConsumoFechaVO consumo : response.getDetalleConsumoLlamadas()) {
					for (ConsumoDetalleVO detalle : consumo.getDetalle()) {
						if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
							try{
								if(respuestaPlan != null){
								    //nuevo cambio
									if(Minutoscomunidad > 0  && detalle.getOperadorDestino().trim().equals("Iusacell") || detalle.getOperadorDestino().trim().equals("Unefon"))
									{
										if(totalMinCom < Minutoscomunidad)
										  totalMinCom += Integer.parseInt(detalle.getDurMinRedn());
										else
	   									  totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
									}else{
									   totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
									}
								}else{
								   totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
								}
								
							}catch (NumberFormatException e) {
								totalTiempo += 0; 
							}
						}
						if(response.getDetalleFocalizacion().getIsPostpagoOrHibrido() != null && !response.getDetalleFocalizacion().getIsPostpagoOrHibrido().equals("")){
							if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
								try{
									pivote = Double.parseDouble(detalle.getMontoFree());
									if(pivote < 0)
										totalCosto += (pivote*(-1));
									else
										totalCosto += pivote;
								}catch (NumberFormatException e) {
									totalCosto += 0; 
								}
							}
						}else{
							if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
								try{
									pivote = Double.parseDouble(detalle.getMontoReal());
									if(pivote < 0)
										totalCosto += (pivote*(-1));
									else
										totalCosto += pivote;
								}catch (NumberFormatException e) {
									totalCosto += 0; 
								}
							}
						}
					}
				}
				response.setTotalTA(formatNumber.format(totalTiempo));
				response.setTotalCom(formatNumber.format(totalMinCom));
				response.setTotalVoz(formatNumber.format(totalCosto));
			}
			if(response != null && response.getDetalleConsumoMensajes() != null && response.getDetalleConsumoMensajes().size() > 0){
				totalCosto = 0;
				for (ConsumoFechaVO consumo : response.getDetalleConsumoMensajes()) {
					for (ConsumoDetalleVO detalle : consumo.getDetalle()) {
						if(response.getDetalleFocalizacion().getIsPostpagoOrHibrido() != null && !response.getDetalleFocalizacion().getIsPostpagoOrHibrido().equals("")){
							if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
								try{
									pivote = Double.parseDouble(detalle.getMontoFree());
									if(pivote < 0)
										totalCosto += (pivote*(-1));
									else
										totalCosto += pivote;
								}catch (NumberFormatException e) {
									totalCosto += 0; 
								}
							}
						}else{
							if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
								try{
									pivote = Double.parseDouble(detalle.getMontoReal());
									if(pivote < 0)
										totalCosto += (pivote*(-1));
									else
										totalCosto += pivote;
								}catch (NumberFormatException e) {
									totalCosto += 0; 
								}
							}
						}
					}
				}
				response.setTotalMensaje(formatNumber.format(totalCosto));
			}

			if(response.getDetalleConsumoNavegacion() != null && response.getDetalleConsumoNavegacion().size() > 0){

				try{
					if(respuestaPlan.getMegaadc() != null && !respuestaPlan.getMegaadc().equals(""))
						costoMegas = Double.parseDouble(respuestaPlan.getMegaadc().replaceAll("[^\\d.]", ""));
					else
						costoMegas = 0;
				}catch (NumberFormatException e) {
					costoMegas = 0;
				}

				for (ConsumoFechaVO consumo : response.getDetalleConsumoNavegacion()) {
					for (ConsumoDetalleVO detalle : consumo.getDetalle()) {
						if(detalle.getCdescWallet()!= null && !detalle.getCdescWallet().trim().toLowerCase().equals("datos incluidos")){
							try{
								salAnt = Double.parseDouble(detalle.getMontoReal());
								salDes = Double.parseDouble(detalle.getMontoFree());
							}catch (NumberFormatException e) {
								salAnt = 0;
								salDes = 0;
							}

							if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
								try{
									totalMegas += ((salAnt - salDes)/1024);
								}catch (NumberFormatException e) {
									totalMegas += 0; 
								}
							}
						}
					}
				}
				response.setTotalDatos(formatNumber.format(totalMegas));
				response.setTotalDatosCosto(formatNumber.format(totalMegas * costoMegas));
			}
			
			
			
			if(response.getDetalleConsumoLlamadas() != null && response.getDetalleConsumoLlamadas().size() > 0){
				double montoReal = 0;
				for(int i=0; i<response.getDetalleConsumoLlamadas().size();i++){
					for(int y=0; y<response.getDetalleConsumoLlamadas().get(i).getDetalle().size();y++){
						try{
							montoReal += Double.parseDouble(response.getDetalleConsumoLlamadas().get(i).getDetalle().get(y).getMontoReal());
						}catch (NumberFormatException e) {
							montoReal += 0;
						}
					}
				}
				response.setTotalMontoRealLlamadas(formatNumber.format(montoReal));
			}

		}catch (InterruptedException e) {
			Logger.write("Finish Llamada");
		}catch (NumberFormatException e) {
			Logger.write("Finish Llamad");
		}
		return response;
	}
	
	public ConsumosTotalesResponseVO flujoFromWallets(final String dn, final DetalleFocalizacionVO descripcion) throws ServiceException{

		ConsumosTotalesVO response = new ConsumosTotalesVO();
		ConsumosTotalesVO respProv = new ConsumosTotalesVO();
		ConsumosTotalesResponseVO respuesta = new ConsumosTotalesResponseVO();
//		CallServiceObtenerDescripcionPlanes obtenPlanes = new CallServiceObtenerDescripcionPlanes();
		ObtenerDescripcionPlanesVO1 respuestaPlan = new ObtenerDescripcionPlanesVO1();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
//		StringBuffer sResponse = new StringBuffer();
		DetalleConsumosTotales consumos = new DetalleConsumosTotales();
		OracleProcedures oracle = new OracleProcedures();
		Integer totalTiempo = 0;
		Integer totalMinCom = 0;
		int Minutoscomunidad = 0;
		double totalCosto = 0;
		double totalMegas = 0;
		double costoMegas = 0;
		double pivote = 0;
		double salAnt = 0;
		double salDes = 0;
		String walletsTotalPorHilos = "";
		
		if(descripcion.getFechaCorte() == null){
			descripcion.setFechaCorte("");
		}
		if(descripcion != null){
			response.setDescripcionPlan(descripcion.getDescripcionPlan());
			response.setDetalleFocalizacion(descripcion);
		}
		
		try{
			walletsTotalPorHilos = oracle.getValorParametro(123);
		}catch (Exception e) {
			walletsTotalPorHilos = "1";
		}

		if(walletsTotalPorHilos.equals("1")){
			Proceso llamadas = new Proceso("Llamadas","1", dn, descripcion.getFechaCorte());
			Proceso mensajes = new Proceso("Mensajes","2", dn, descripcion.getFechaCorte());
			Proceso navegacion = new Proceso("Navegacion","4", dn, descripcion.getFechaCorte());

			Logger.write("     LLamada Status         : " + llamadas.t.isAlive());

			try{
				llamadas.t.join();
				mensajes.t.join();
				navegacion.t.join();

				response.setDetalleConsumoLlamadas(llamadas.getResponse().getDetalleConsumo());
				response.setDetalleConsumoMensajes(mensajes.getResponse().getDetalleConsumo());
				response.setDetalleConsumoNavegacion(navegacion.getResponse().getDetalleConsumo());
			}catch (InterruptedException e) {
				Logger.write("Finish Llamada");
			}catch (NumberFormatException e) {
				Logger.write("Finish Llamada");
			}
		}else{
			respProv = consumos.flujoTotal(dn, "1", descripcion.getFechaCorte());
			response.setDetalleConsumoLlamadas(respProv.getDetalleConsumoLlamadas());
			respProv = consumos.flujoTotal(dn, "2", descripcion.getFechaCorte());
			response.setDetalleConsumoMensajes(respProv.getDetalleConsumoMensajes());
			respProv = consumos.flujoTotal(dn, "4", descripcion.getFechaCorte());
			response.setDetalleConsumoNavegacion(respProv.getDetalleConsumoNavegacion());
			response.setDetalleFocalizacion(descripcion);
		}
			
		try{
//			if(descripcion.getIdPlan() != null && !descripcion.getIdPlan().equals("")){
//				sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes(descripcion.getIdPlan(), "", "0", 212121, "10.10.10"));
//			}else if(descripcion.getDescripcionPlan() != null && !descripcion.getDescripcionPlan().equals("")){
//				sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes("", descripcion.getDescripcionPlan(), "0", 212121, "10.10.10"));
//			}else if(descripcion.getCode() != null && !descripcion.getCode().equals("")){
//				sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes("", "", descripcion.getCode(), 212121, "10.10.10"));
//			}
//			if(sResponse != null && !sResponse.equals("")){
//				respuestaPlan = ParseXMLFile.ParseObtenerDescripcionPlanes(sResponse.toString());
//				sResponse.setLength(0);
//			}
			
			respuestaPlan = OfertaComercial.consultaDescripcionPlanes(descripcion.getIdPlan(), descripcion.getDescripcionPlan(), descripcion.getCode(), 212121, "10.10.10.10");
			
		}catch (ServiceException e) {
			respuestaPlan = new ObtenerDescripcionPlanesVO1();
		}

		if(respuestaPlan != null){
			response.setDatosPlanes(respuestaPlan);
		}

		if(response != null && response.getDetalleConsumoLlamadas() != null && response.getDetalleConsumoLlamadas().size() > 0){

			try{
				Minutoscomunidad = Integer.parseInt(respuestaPlan.getMinutoscomunidad().trim());
			}catch (Exception e) {
				Minutoscomunidad = 0;
			}

			for (ConsumoFechaVO consumo : response.getDetalleConsumoLlamadas()) {
				for (ConsumoDetalleVO detalle : consumo.getDetalle()) {
					if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
						try{
							if(respuestaPlan != null){
								//nuevo cambio
								if(Minutoscomunidad > 0  && detalle.getOperadorDestino().toLowerCase().trim().equals("iusacell") || detalle.getOperadorDestino().toLowerCase().trim().equals("unefon"))
								{
									if(totalMinCom < Minutoscomunidad)
										totalMinCom += Integer.parseInt(detalle.getDurMinRedn());
									else
										totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
								}else{
									totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
								}
							}else{
								totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
							}

						}catch (NumberFormatException e) {
							totalTiempo += 0; 
						}
					}
					if(response.getDetalleFocalizacion().getIsPostpagoOrHibrido() != null && !response.getDetalleFocalizacion().getIsPostpagoOrHibrido().equals("")){
						if(detalle.getMontoFree() != null && !detalle.getMontoFree().equals("")){
							try{
								pivote = Double.parseDouble(detalle.getMontoFree());
								if(pivote < 0)
									totalCosto += (pivote*(-1));
								else
									totalCosto += pivote;
							}catch (NumberFormatException e) {
								totalCosto += 0; 
							}
						}
					}else{
						if(detalle.getMontoReal() != null && !detalle.getMontoReal().equals("")){
							try{
								pivote = Double.parseDouble(detalle.getMontoReal());
								if(pivote < 0)
									totalCosto += (pivote*(-1));
								else
									totalCosto += pivote;
							}catch (NumberFormatException e) {
								totalCosto += 0; 
							}
						}
					}
				}
			}
			response.setTotalTA(formatNumber.format(totalTiempo));
			response.setTotalCom(formatNumber.format(totalMinCom));
			response.setTotalVoz(formatNumber.format(totalCosto));
		}

		if(response.getDetalleConsumoNavegacion() != null && response.getDetalleConsumoNavegacion().size() > 0){

			try{
				if(respuestaPlan.getMegaadc() != null && !respuestaPlan.getMegaadc().equals(""))
					costoMegas = Double.parseDouble(respuestaPlan.getMegaadc().replaceAll("[^\\d.]", ""));
				else
					costoMegas = 0;
			}catch (NumberFormatException e) {
				costoMegas = 0;
			}

			for (ConsumoFechaVO consumo : response.getDetalleConsumoNavegacion()) {
				for (ConsumoDetalleVO detalle : consumo.getDetalle()) {
					if(detalle.getCdescWallet()!= null && !detalle.getCdescWallet().trim().toLowerCase().equals("datos incluidos")){
						try{
							salAnt = Double.parseDouble(detalle.getMontoReal());
							salDes = Double.parseDouble(detalle.getMontoFree());
						}catch (NumberFormatException e) {
							salAnt = 0;
							salDes = 0;
						}


						try{
							totalMegas += ((salAnt - salDes)/1024);
						}catch (NumberFormatException e) {
							totalMegas += 0; 
						}
					}
				}
			}
			response.setTotalDatos(formatNumber.format(totalMegas));
			response.setTotalDatosCosto(formatNumber.format(totalMegas * costoMegas));
		}
		
		if(response.getDatosPlanes() != null)
			respuesta.setDatosPlanes(response.getDatosPlanes());
		if(response.getDescripcionPlan() != null)
			respuesta.setDescripcionPlan(response.getDescripcionPlan());
		if(response.getDetalleFocalizacion() != null)
			respuesta.setDetalleFocalizacion(response.getDetalleFocalizacion());
		if(response.getDetalleServiciosAdicionales() != null)
			respuesta.setDetalleServiciosAdicionales(response.getDetalleServiciosAdicionales());
		respuesta.setTotalCom(response.getTotalCom());
		respuesta.setTotalDatos(response.getTotalDatos());
		respuesta.setTotalDatosCosto(response.getTotalDatosCosto());
		if(response.getDetalleConsumoMensajes() != null)
			respuesta.setTotalMensaje(response.getDetalleConsumoMensajes().size()+"");
		respuesta.setTotalMontoRealLlamadas(response.getTotalMontoRealLlamadas());
		respuesta.setTotalTA(response.getTotalTA());
		respuesta.setTotalVoz(response.getTotalVoz());
		return respuesta;
	}
	
	public ConsumosTotalesResponseVO flujoFromWalletsDividido(final String dn, final DetalleFocalizacionVO descripcion, final String tipoEvento) throws ServiceException{

		ConsumosTotalesVO response = new ConsumosTotalesVO();
		ConsumosTotalesResponseVO respuesta = new ConsumosTotalesResponseVO();
//		CallServiceObtenerDescripcionPlanes obtenPlanes = new CallServiceObtenerDescripcionPlanes();
		ObtenerDescripcionPlanesVO1 respuestaPlan = new ObtenerDescripcionPlanesVO1();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
//		StringBuffer sResponse = new StringBuffer();
		DetalleConsumosTotales consumos = new DetalleConsumosTotales();
		Integer totalTiempo = 0;
		Integer totalMinCom = 0;
		int Minutoscomunidad = 0;
		double totalCosto = 0;
		double totalMegas = 0;
		double costoMegas = 0;
		double pivote = 0;
		double salAnt = 0;
		double salDes = 0;
		
		if(descripcion.getFechaCorte() == null){
			descripcion.setFechaCorte("");
		}
		if(descripcion != null){
			response.setDescripcionPlan(descripcion.getDescripcionPlan());
			response.setDetalleFocalizacion(descripcion);
		}

		response = consumos.flujoTotal(dn, tipoEvento, descripcion.getFechaCorte());

		try{
			try{
//				if(descripcion.getIdPlan() != null && !descripcion.getIdPlan().equals("")){
//					sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes(descripcion.getIdPlan(), "", "0", 212121, "10.10.10"));
//				}else if(descripcion.getDescripcionPlan() != null && !descripcion.getDescripcionPlan().equals("")){
//					sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes("", descripcion.getDescripcionPlan(), "0", 212121, "10.10.10"));
//				}else if(descripcion.getCode() != null && !descripcion.getCode().equals("")){
//					sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes("", "", descripcion.getCode(), 212121, "10.10.10"));
//				}
//				if(sResponse != null && !sResponse.equals("")){
//					respuestaPlan = ParseXMLFile.ParseObtenerDescripcionPlanes(sResponse.toString());
//					sResponse.setLength(0);
//				}
				
				respuestaPlan = OfertaComercial.consultaDescripcionPlanes(descripcion.getIdPlan(), descripcion.getDescripcionPlan(), descripcion.getCode(), 212121, "10.10.10.10");
				
			}catch (ServiceException e) {
				respuestaPlan = new ObtenerDescripcionPlanesVO1();
			}
			
			if(respuestaPlan != null){
				response.setDatosPlanes(respuestaPlan);
			}

  
				try{
					Minutoscomunidad = Integer.parseInt(respuestaPlan.getMinutoscomunidad().trim());
				}catch (Exception e) {
					Minutoscomunidad = 0;
				}
				if(response.getDetalleConsumoLlamadas() != null && response.getDetalleConsumoLlamadas().size() > 0){
				for (ConsumoFechaVO consumo : response.getDetalleConsumoLlamadas()) {
					for (ConsumoDetalleVO detalle : consumo.getDetalle()) {
						if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
							try{
								if(respuestaPlan != null){
								    //nuevo cambio
									if(Minutoscomunidad > 0  && detalle.getOperadorDestino().toLowerCase().trim().equals("iusacell") || detalle.getOperadorDestino().toLowerCase().trim().equals("unefon"))
									{
										if(totalMinCom < Minutoscomunidad)
										  totalMinCom += Integer.parseInt(detalle.getDurMinRedn());
										else
	   									  totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
									}else{
									   totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
									}
								}else{
								   totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
								}
								
							}catch (NumberFormatException e) {
								totalTiempo += 0; 
							}
						}
						if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
							if(detalle.getMontoFree() != null && !detalle.getMontoFree().equals("")){
								try{
									pivote = Double.parseDouble(detalle.getMontoFree());
									if(pivote < 0)
										totalCosto += (pivote*(-1));
									else
										totalCosto += pivote;
								}catch (NumberFormatException e) {
									totalCosto += 0; 
								}
							}
						}else{
							if(detalle.getMontoReal() != null && !detalle.getMontoReal().equals("")){
								try{
									pivote = Double.parseDouble(detalle.getMontoReal());
									if(pivote < 0)
										totalCosto += (pivote*(-1));
									else
										totalCosto += pivote;
								}catch (NumberFormatException e) {
									totalCosto += 0; 
								}
							}
						}
					}
				}
				response.setTotalTA(formatNumber.format(totalTiempo));
				response.setTotalCom(formatNumber.format(totalMinCom));
				response.setTotalVoz(formatNumber.format(totalCosto));
				}
				
				if(response.getDetalleConsumoNavegacion() != null && response.getDetalleConsumoNavegacion().size() > 0){

					try{
						if(respuestaPlan.getMegaadc() != null && !respuestaPlan.getMegaadc().equals(""))
							costoMegas = Double.parseDouble(respuestaPlan.getMegaadc().replaceAll("[^\\d.]", ""));
						else
							costoMegas = 0;
					}catch (NumberFormatException e) {
						costoMegas = 0;
					}

					for (ConsumoFechaVO consumo : response.getDetalleConsumoNavegacion()) {
						for (ConsumoDetalleVO detalle : consumo.getDetalle()) {
							if(detalle.getCdescWallet()!= null){
								try{
									salAnt = Double.parseDouble(detalle.getMontoReal());
									salDes = Double.parseDouble(detalle.getMontoFree());
								}catch (NumberFormatException e) {
									salAnt = 0;
									salDes = 0;
								}


								try{
									totalMegas += ((salAnt - salDes)/1024);
								}catch (NumberFormatException e) {
									totalMegas += 0; 
								}
							}
						}
					}
					response.setTotalDatos(formatNumber.format(totalMegas));
					response.setTotalDatosCosto(formatNumber.format(totalMegas * costoMegas));
				}
		}catch (Exception e) {
			Logger.write("Finish Llamada");
		}
		
		if(response.getDatosPlanes() != null)
			respuesta.setDatosPlanes(response.getDatosPlanes());
		if(response.getDescripcionPlan() != null)
			respuesta.setDescripcionPlan(response.getDescripcionPlan());
		if(response.getDetalleFocalizacion() != null)
			respuesta.setDetalleFocalizacion(response.getDetalleFocalizacion());
		if(response.getDetalleServiciosAdicionales() != null)
			respuesta.setDetalleServiciosAdicionales(response.getDetalleServiciosAdicionales());
		if(response.getTotalDatos() != null)
			respuesta.setTotalDatos(response.getTotalDatos());
		if(response.getTotalDatosCosto() != null)
			respuesta.setTotalDatosCosto(response.getTotalDatosCosto());
		if(response.getDetalleConsumoMensajes() != null)
			respuesta.setTotalMensaje(response.getDetalleConsumoMensajes().size()+"");
		respuesta.setTotalCom(response.getTotalCom());
		respuesta.setTotalMontoRealLlamadas(response.getTotalMontoRealLlamadas());
		respuesta.setTotalTA(response.getTotalTA());
		respuesta.setTotalVoz(response.getTotalVoz());
		return respuesta;
	}

	public ConsumosTotalesVO flujoTotal(final String dn, final String tipoEvento, final String fechaCorte) throws ServiceException{
		ConsumosTotalesVO consumos = new ConsumosTotalesVO();
		String sResponse = "";
		List<ConsumoFechaVO> consumosFecha = new ArrayList<ConsumoFechaVO>();
		List<SaldosFechaVO> saldosFecha = new ArrayList<SaldosFechaVO>();
		OracleProcedures oracle = new OracleProcedures();
		String detalleTotalesBDDirecto = "";
		String flujoWallet = "0";
		try {
			try{
				detalleTotalesBDDirecto = oracle.getValorParametro(87);
			}catch (ServiceException e) {
				detalleTotalesBDDirecto = "0";
			}		
			
			try{
				flujoWallet = oracle.getValorParametro(122);
			}catch (ServiceException e) {
				flujoWallet = "0";
			}	
			
			
			if(flujoWallet.equals("1")){
			
				if(detalleTotalesBDDirecto.equals("0")){
					sResponse = oracle.getDetalleConsumoPorDn(dn, Integer.parseInt(tipoEvento));
	
					if(sResponse != null && !sResponse.equals("")){
						consumosFecha = ParseXMLFile.parseConsumos(sResponse, tipoEvento, dn);
					}	
	
					consumosFecha = Utilerias.orderConsumosByDate(1, consumosFecha, fechaCorte);
				}else{
					SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
					Calendar actual = GregorianCalendar.getInstance();
					java.util.Date dateFin=actual.getTime();
					Date datecon = null;
					if(fechaCorte != null && !fechaCorte.equals("")){
						datecon = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH).parse(fechaCorte);
					}else{
						int dias = 30;
						if (tipoEvento.equals("4"))
						{
							dias = 30;
						}
						actual.add(Calendar.DATE,-dias);
						datecon = actual.getTime();
					}
					String fechaFin=sdf.format(dateFin);
					String fechaIni=sdf.format(datecon);
	
					if(tipoEvento.equals("1"))
					{				
						consumosFecha = oracle.getLlamadas(dn, fechaIni, fechaFin);
					}
					else if(tipoEvento.equals("2"))
					{					
						consumosFecha = oracle.getMensajes(dn, fechaIni, fechaFin);				
					}
					else if(tipoEvento.equals("4"))
					{
						consumosFecha = oracle.getDatos(dn, fechaIni, fechaFin);
					}
				}
	
				if(tipoEvento.equals("1")){
					if(consumosFecha != null)
						consumos.setDetalleConsumoLlamadas(consumosFecha);
					if(saldosFecha != null)
						consumos.setDetalleSaldosLlamadas(saldosFecha);
				}
				if(tipoEvento.equals("2")){
					if(consumosFecha != null)
						consumos.setDetalleConsumoMensajes(consumosFecha);
					if(saldosFecha != null)
						consumos.setDetalleSaldosMensajes(saldosFecha);
				}
				if(tipoEvento.equals("4")){
					if(consumosFecha != null)
						consumos.setDetalleConsumoNavegacion(consumosFecha);
					if(saldosFecha != null)
						consumos.setDetalleSaldosNavegacion(saldosFecha);
				}
	
				consumos.setTotalDatos("0");
				consumos.setTotalMensaje("0");
				consumos.setTotalTA("0");
				consumos.setTotalVoz("0");
			}//fin if
			
			/** FLUJO A VALIDAR CONSULTAS **/
			if(flujoWallet.equals("2")){
				if(detalleTotalesBDDirecto.equals("0")){
					sResponse = oracle.getDetalleConsumoPorDn(dn, Integer.parseInt(tipoEvento));
	
					if(sResponse != null && !sResponse.equals("")){
						consumosFecha = ParseXMLFile.parseConsumos(sResponse, tipoEvento, dn);
					}	
	
					consumosFecha = Utilerias.orderConsumosByDate(1, consumosFecha, fechaCorte);
				}else{
					SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
					Calendar actual = GregorianCalendar.getInstance();
					java.util.Date dateFin=actual.getTime();
					Date datecon = null;
					if(fechaCorte != null && !fechaCorte.equals("")){
						datecon = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH).parse(fechaCorte);
					}else{
						int dias = 30;
						if (tipoEvento.equals("4"))
						{
							dias = 30;
						}
						actual.add(Calendar.DATE,-dias);
						datecon = actual.getTime();
					}
					String fechaFin=sdf.format(dateFin);
					String fechaIni=sdf.format(datecon);
	
					if(tipoEvento.equals("1"))
					{				
						consumosFecha = oracle.getLlamadasPlus(dn, fechaIni, fechaFin);
					}
					else if(tipoEvento.equals("2"))
					{					
						consumosFecha = oracle.getMensajesPlus(dn, fechaIni, fechaFin);				
					}
					else if(tipoEvento.equals("4"))
					{
						consumosFecha = oracle.getDatosPlus(dn, fechaIni, fechaFin);
					}
				}
	
				if(tipoEvento.equals("1")){
					if(consumosFecha != null)
						consumos.setDetalleConsumoLlamadas(consumosFecha);
					if(saldosFecha != null)
						consumos.setDetalleSaldosLlamadas(saldosFecha);
				}
				if(tipoEvento.equals("2")){
					if(consumosFecha != null)
						consumos.setDetalleConsumoMensajes(consumosFecha);
					if(saldosFecha != null)
						consumos.setDetalleSaldosMensajes(saldosFecha);
				}
				if(tipoEvento.equals("4")){
					if(consumosFecha != null)
						consumos.setDetalleConsumoNavegacion(consumosFecha);
					if(saldosFecha != null)
						consumos.setDetalleSaldosNavegacion(saldosFecha);
				}
	
				consumos.setTotalDatos("0");
				consumos.setTotalMensaje("0");
				consumos.setTotalTA("0");
				consumos.setTotalVoz("0");
			}
			
		} catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}

		return consumos;
	}


	public ConsumosTotalesVO flujoRango(final String dn, final String fechaIni, final String fechaFin, final DetalleFocalizacionVO descripcion) throws ServiceException{

		ConsumosTotalesVO response = new ConsumosTotalesVO();
//		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
//		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
//		CallServiceObtenerDescripcionPlanes obtenPlanes = new CallServiceObtenerDescripcionPlanes();
		ObtenerDescripcionPlanesVO1 respuestaPlan = new ObtenerDescripcionPlanesVO1();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
//		StringBuffer sResponse = new StringBuffer();
		Integer totalTiempo = 0;
		Integer totalMinCom = 0;
		int Minutoscomunidad = 0;
		double totalCosto = 0;
		double totalMegas = 0;
		double costoMegas = 0;
		double pivote = 0;
		double salAnt = 0;
		double salDes = 0;
		
//		sResponse.append(focalizacion.focalizacion(dn));
//		if(sResponse != null && !sResponse.equals("")){
//			descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
//			sResponse.setLength(0);
//		}	
		if(descripcion.getFechaCorte() == null){
			descripcion.setFechaCorte("");
		}
		if(descripcion != null){
			response.setDescripcionPlan(descripcion.getDescripcionPlan());
			response.setDetalleFocalizacion(descripcion);
		}

		ProcesoRangos llamadas = new ProcesoRangos("Llamadas","1", dn, fechaIni,fechaFin);
		ProcesoRangos mensajes = new ProcesoRangos("Mensajes","2", dn, fechaIni,fechaFin);
		ProcesoRangos navegacion = new ProcesoRangos("Navegacion","4", dn, fechaIni,fechaFin);

		Logger.write("LLamada Status: " + llamadas.t.isAlive());
		Logger.write("LLamada Status: " + llamadas.t.isAlive());
		try{
			llamadas.t.join();
			mensajes.t.join();
			navegacion.t.join();

			response.setDetalleConsumoLlamadas(llamadas.getResponse().getDetalleConsumo());
			response.setDetalleSaldosLlamadas(llamadas.getResponse().getDetalleSaldos());
			response.setDetalleConsumoMensajes(mensajes.getResponse().getDetalleConsumo());
			response.setDetalleSaldosMensajes(mensajes.getResponse().getDetalleSaldos());
			response.setDetalleConsumoNavegacion(navegacion.getResponse().getDetalleConsumo());
			response.setDetalleSaldosNavegacion(navegacion.getResponse().getDetalleSaldos());
			
			
			try{
//				if(descripcion.getIdPlan() != null && !descripcion.getIdPlan().equals("")){
//					sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes(descripcion.getIdPlan(), "", "0", 212121, "10.10.10"));
//				}else if(descripcion.getDescripcionPlan() != null && !descripcion.getDescripcionPlan().equals("")){
//					sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes("", descripcion.getDescripcionPlan(), "0", 212121, "10.10.10"));
//				}else if(descripcion.getCode() != null && !descripcion.getCode().equals("")){
//					sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes("", "", descripcion.getCode(), 212121, "10.10.10"));
//				}
//				if(sResponse != null && !sResponse.equals("")){
//					respuestaPlan = ParseXMLFile.ParseObtenerDescripcionPlanes(sResponse.toString());
//					sResponse.setLength(0);
//				}
				
				respuestaPlan = OfertaComercial.consultaDescripcionPlanes(descripcion.getIdPlan(), descripcion.getDescripcionPlan(), descripcion.getCode(), 212121, "10.10.10.10");
				
			}catch (Exception e) {
				respuestaPlan = new ObtenerDescripcionPlanesVO1();
			}
			
			/*Nuevo*/
			try{
				List<ServiciosAdicionalesVO> listServiciosAdicionales = new ArrayList<ServiciosAdicionalesVO>();
				listServiciosAdicionales = GetServiciosAdicionales.flujoPorDnFromWall(respuestaPlan, descripcion, 0, dn);
				response.setDetalleServiciosAdicionales(listServiciosAdicionales);
			}catch (Exception e) {
				Logger.write(e.getLocalizedMessage());
			}
			
			if(respuestaPlan != null){
				response.setDatosPlanes(respuestaPlan);
			}

			if(response != null && response.getDetalleConsumoLlamadas() != null && response.getDetalleConsumoLlamadas().size() > 0){

				try{
					Minutoscomunidad = Integer.parseInt(respuestaPlan.getMinutoscomunidad().trim());
				}catch (Exception e) {
					Minutoscomunidad = 0;
				}
				
				for (ConsumoFechaVO consumo : response.getDetalleConsumoLlamadas()) {
					for (ConsumoDetalleVO detalle : consumo.getDetalle()) {
						if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
							try{
								if(respuestaPlan != null){
								    //nuevo cambio
									if(Minutoscomunidad > 0  && detalle.getOperadorDestino().toLowerCase().trim().equals("iusacell") || detalle.getOperadorDestino().toLowerCase().trim().equals("unefon"))
									{
										if(totalMinCom < Minutoscomunidad)
										  totalMinCom += Integer.parseInt(detalle.getDurMinRedn());
										else
	   									  totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
									}else{
									   totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
									}
								}else{
								   totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
								}
							}catch (NumberFormatException e) {
								totalTiempo += 0; 
							}
						}
						if(response.getDetalleFocalizacion().getIsPostpagoOrHibrido() != null && !response.getDetalleFocalizacion().getIsPostpagoOrHibrido().equals("")){
							if(detalle.getMontoFree() != null && !detalle.getMontoFree().equals("")){
								try{
									pivote = Double.parseDouble(detalle.getMontoFree());
									if(pivote < 0)
										totalCosto += (pivote*(-1));
									else
										totalCosto += pivote;
								}catch (NumberFormatException e) {
									totalCosto += 0; 
								}
							}
						}else{
							if(detalle.getMontoReal() != null && !detalle.getMontoReal().equals("")){
								try{
									pivote = Double.parseDouble(detalle.getMontoReal());
									if(pivote < 0)
										totalCosto += (pivote*(-1));
									else
										totalCosto += pivote;
								}catch (NumberFormatException e) {
									totalCosto += 0; 
								}
							}
						}
					}
				}
				response.setTotalTA(formatNumber.format(totalTiempo));
				response.setTotalCom(formatNumber.format(totalMinCom));
				response.setTotalVoz(formatNumber.format(totalCosto));
			}

			if(response.getDetalleConsumoNavegacion() != null && response.getDetalleConsumoNavegacion() != null && response.getDetalleConsumoNavegacion().size() > 0){

				try{
					if(respuestaPlan.getMegaadc() != null && !respuestaPlan.getMegaadc().equals(""))
						costoMegas = Double.parseDouble(respuestaPlan.getMegaadc().replaceAll("[^\\d.]", ""));
					else
						costoMegas = 0;
				}catch (Exception e) {
					costoMegas = 0;
				}

				for (ConsumoFechaVO consumo : response.getDetalleConsumoNavegacion()) {
					for (ConsumoDetalleVO detalle : consumo.getDetalle()) {
						if(detalle.getCdescWallet()!= null && !detalle.getCdescWallet().trim().toLowerCase().equals("datos incluidos")){
							try{
								salAnt = Double.parseDouble(detalle.getMontoReal());
								salDes = Double.parseDouble(detalle.getMontoFree());
							}catch (Exception e) {
								salAnt = 0;
								salDes = 0;
							}

							if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
								try{
									totalMegas += ((salAnt - salDes)/1024);
								}catch (NumberFormatException e) {
									totalMegas += 0; 
								}
							}
						}
					}
				}
				response.setTotalDatos(formatNumber.format(totalMegas));
				response.setTotalDatosCosto(formatNumber.format(totalMegas * costoMegas));
			}

		}catch (InterruptedException e) {
			Logger.write("Finish Llamada");
		}
		catch (Exception e) {
			Logger.write(e.getLocalizedMessage());
		}
		return response;
	}	

	public ConsumosTotalesVO flujoTotalRango(final String dn, final String tipoEvento, final String fechaInic, final String fechaFin) throws ServiceException{
		ConsumosTotalesVO consumos = new ConsumosTotalesVO();
		StringBuffer sResponse = new StringBuffer();
		CallServiceDetalleConsumosETAK_LEGACY detalle = new CallServiceDetalleConsumosETAK_LEGACY();
		List<ConsumoFechaVO> consumosFecha = new ArrayList<ConsumoFechaVO>();
		List<SaldosFechaVO> saldosFecha = new ArrayList<SaldosFechaVO>();
		OracleProcedures oracle = new OracleProcedures();
		String detalleTotalesBDDirecto = "";
		String consultaDetalleTotalesBDLocalxFecha = "";
		try {

			try{
				detalleTotalesBDDirecto = oracle.getValorParametro(89);
			}catch (ServiceException e) {
				detalleTotalesBDDirecto = "0";
			}
			
			try{
				consultaDetalleTotalesBDLocalxFecha = oracle.getValorParametro(107);
			}catch (ServiceException e) {
				consultaDetalleTotalesBDLocalxFecha = "0";
			}
			
			if(detalleTotalesBDDirecto.equals("0")){
				
				sResponse.append(detalle.detalleConsumosETAK_LEGACY(dn, tipoEvento, fechaInic,fechaFin));
				if(sResponse != null && !sResponse.equals("")){
					consumosFecha = ParseXMLFile.parseConsumos(sResponse.toString(), tipoEvento, dn);
					sResponse.setLength(0);
				}	
	
				consumosFecha = Utilerias.orderConsumosByDate(1, consumosFecha,fechaInic);
			
			}else{
				SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
				Date datecon = null;
				Date dateFin = null;
				
				dateFin = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(fechaFin);
				datecon = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(fechaInic);
				
				String fechaFinDir=sdf.format(dateFin);
				String fechaIniDir=sdf.format(datecon);

				if(tipoEvento.equals("1")){
					if(consultaDetalleTotalesBDLocalxFecha.equals("1")){
						try{
							consumosFecha = oracle.getLlamadasLocal(dn, fechaIniDir, fechaFinDir);
						}catch(Exception e){
							try{
							  consumosFecha = oracle.getLlamadas(dn, fechaIniDir, fechaFinDir);
							}catch (Exception f) {
								sResponse.append(detalle.detalleConsumosETAK_LEGACY(dn, tipoEvento, fechaInic,fechaFin));
								if(sResponse != null && !sResponse.equals("")){
									consumosFecha = ParseXMLFile.parseConsumos(sResponse.toString(), tipoEvento, dn);
									sResponse.setLength(0);
								}	
								consumosFecha = Utilerias.orderConsumosByDate(1, consumosFecha,fechaInic);
							}
						}
					}else{
						try{
						  consumosFecha = oracle.getLlamadas(dn, fechaIniDir, fechaFinDir);
						}catch (Exception e) {
							sResponse.append(detalle.detalleConsumosETAK_LEGACY(dn, tipoEvento, fechaInic,fechaFin));
							if(sResponse != null && !sResponse.equals("")){
								consumosFecha = ParseXMLFile.parseConsumos(sResponse.toString(), tipoEvento, dn);
								sResponse.setLength(0);
							}	
							consumosFecha = Utilerias.orderConsumosByDate(1, consumosFecha,fechaInic);
						}
					}
				}
				else if(tipoEvento.equals("2")){
					if(consultaDetalleTotalesBDLocalxFecha.equals("1")){
						try{
							consumosFecha = oracle.getMensajesLocal(dn, fechaIniDir, fechaFinDir);
						}catch(Exception e){
							try{
							  consumosFecha = oracle.getMensajes(dn, fechaIniDir, fechaFinDir);
							}catch (Exception f) {
								sResponse.append(detalle.detalleConsumosETAK_LEGACY(dn, tipoEvento, fechaInic,fechaFin));
								if(sResponse != null && !sResponse.equals("")){
									consumosFecha = ParseXMLFile.parseConsumos(sResponse.toString(), tipoEvento, dn);
									sResponse.setLength(0);
								}	
								consumosFecha = Utilerias.orderConsumosByDate(1, consumosFecha,fechaInic);
							}
						}
					}else{
					   try{	
						 consumosFecha = oracle.getMensajes(dn, fechaIniDir, fechaFinDir);
					   }catch (Exception e) {
						   sResponse.append(detalle.detalleConsumosETAK_LEGACY(dn, tipoEvento, fechaInic,fechaFin));
							if(sResponse != null && !sResponse.equals("")){
								consumosFecha = ParseXMLFile.parseConsumos(sResponse.toString(), tipoEvento, dn);
								sResponse.setLength(0);
							}	
							consumosFecha = Utilerias.orderConsumosByDate(1, consumosFecha,fechaInic);
					   }
					}
				}else if(tipoEvento.equals("4")){
					if(consultaDetalleTotalesBDLocalxFecha.equals("1")){
						try{
							consumosFecha = oracle.getDatosLocal(dn, fechaIniDir, fechaFinDir);
						}catch(Exception e){
							try{ 
							consumosFecha = oracle.getDatos(dn, fechaIniDir, fechaFinDir);
							}catch (Exception f) {
								sResponse.append(detalle.detalleConsumosETAK_LEGACY(dn, tipoEvento, fechaInic,fechaFin));
								if(sResponse != null && !sResponse.equals("")){
									consumosFecha = ParseXMLFile.parseConsumos(sResponse.toString(), tipoEvento, dn);
									sResponse.setLength(0);
								}	
								consumosFecha = Utilerias.orderConsumosByDate(1, consumosFecha,fechaInic);
							}
							
						}
					}else{
						try{
						  consumosFecha = oracle.getDatos(dn, fechaIniDir, fechaFinDir);
						}catch (Exception e) {
							sResponse.append(detalle.detalleConsumosETAK_LEGACY(dn, tipoEvento, fechaInic,fechaFin));
							if(sResponse != null && !sResponse.equals("")){
								consumosFecha = ParseXMLFile.parseConsumos(sResponse.toString(), tipoEvento, dn);
								sResponse.setLength(0);
							}	
							consumosFecha = Utilerias.orderConsumosByDate(1, consumosFecha,fechaInic);
						}
					}
				}
			}

			if(tipoEvento.equals("1")){
				if(consumosFecha != null)
					consumos.setDetalleConsumoLlamadas(consumosFecha);
				if(saldosFecha != null)
					consumos.setDetalleSaldosLlamadas(saldosFecha);
			}
			if(tipoEvento.equals("2")){
				if(consumosFecha != null)
					consumos.setDetalleConsumoMensajes(consumosFecha);
				if(saldosFecha != null)
					consumos.setDetalleSaldosMensajes(saldosFecha);
			}
			if(tipoEvento.equals("4")){
				if(consumosFecha != null)
					consumos.setDetalleConsumoNavegacion(consumosFecha);
				if(saldosFecha != null)
					consumos.setDetalleSaldosNavegacion(saldosFecha);
			}

			consumos.setTotalDatos("3000");
			consumos.setTotalMensaje("30");
			consumos.setTotalTA("1000");
			consumos.setTotalVoz("1000");
		} catch (Exception e) {
			Logger.write("       Detail        : (Exception) " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}

		return consumos;
	}
}
