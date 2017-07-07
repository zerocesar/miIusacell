package mx.com.iusacell.services.miiusacell.arquitectura;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

//import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.ConsumoDetalleVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoFechaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosTotalesVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.ObtenerDescripcionPlanesVO1;
import mx.com.iusacell.services.miiusacell.vo.SaldosFechaVO;
import mx.com.iusacell.services.miusacell.call.CallServiceDetalleConsumosETAK_LEGACY;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
//import mx.com.iusacell.services.miusacell.call.CallServiceObtenerDescripcionPlanes;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class DetalleConsumos {

	public ConsumosVO flujo(final String dn, final String tipoEvento) throws ServiceException{
		ConsumosVO consumos = new ConsumosVO();
		String sResponse = "";
		CallServiceDetalleConsumosETAK_LEGACY detalle = new CallServiceDetalleConsumosETAK_LEGACY();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		List<ConsumoFechaVO> consumosFecha = new ArrayList<ConsumoFechaVO>();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
//		CallServiceObtenerDescripcionPlanes obtenPlanes = new CallServiceObtenerDescripcionPlanes();
		ObtenerDescripcionPlanesVO1 respuestaPlan = new ObtenerDescripcionPlanesVO1();
		Integer totalTiempo = 0;
		double totalCosto = 0;
		double costoMegas = 0;
		double pivote = 0;
		double saldoTotal = 0;
		try {
			
			sResponse = focalizacion.focalizacion(dn);
			if(sResponse != null && !sResponse.equals("")){
				descripcion = ParseXMLFile.parseFocalizacion(sResponse);
			}	
			if(descripcion.getFechaCorte() == null){
				descripcion.setFechaCorte("");
			}
			
			if(tipoEvento.equals("5") || tipoEvento.equals("6"))
				sResponse = detalle.detalleConsumosETAK_LEGACY(dn, "1", descripcion.getFechaCorte());
			else
				sResponse = detalle.detalleConsumosETAK_LEGACY(dn, tipoEvento, descripcion.getFechaCorte());
			
			
			if(sResponse != null && !sResponse.equals("")){
				consumosFecha = ParseXMLFile.parseConsumos(sResponse, tipoEvento, dn);
			}	

			consumosFecha = Utilerias.orderConsumosByDate(1, consumosFecha, descripcion.getFechaCorte());

			if(descripcion != null){
				consumos.setDescripcionPlan(descripcion.getDescripcionPlan());
				consumos.setIsPostPagoHibrido(descripcion.getIsPostpagoOrHibrido());
			}
			if(consumosFecha != null)
				consumos.setDetalleConsumo(consumosFecha);

			if(tipoEvento.equals("1")){
				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){

					for (ConsumoFechaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleVO detalle2 : consumo.getDetalle()) {
							if(detalle2.getDurMinRedn() != null && !detalle2.getDurMinRedn().equals("")){
								try{
									totalTiempo += Integer.parseInt(detalle2.getDurMinRedn());
								}catch (NumberFormatException e) {
									totalTiempo += 0; 
								}
							}
							if(consumos.getIsPostPagoHibrido() != null && !consumos.getIsPostPagoHibrido().equals("")){
								if(detalle2.getDurMinRedn() != null && !detalle2.getDurMinRedn().equals("")){
									try{
										pivote = Double.parseDouble(detalle2.getMontoFree());
										if(pivote < 0)
											totalCosto += (pivote*(-1));
										else
											totalCosto += pivote;
									}catch (NumberFormatException e) {
										totalCosto += 0; 
									}
								}
							}else{
								if(detalle2.getDurMinRedn() != null && !detalle2.getDurMinRedn().equals("")){
									try{
										pivote = Double.parseDouble(detalle2.getMontoReal());
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
					consumos.setTotalTA(formatNumber.format(totalTiempo));
					consumos.setTotalVoz(formatNumber.format(totalCosto));
				}
			}

			if(tipoEvento.equals("2")){
				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){
					totalCosto = 0;
					for (ConsumoFechaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleVO detalle2 : consumo.getDetalle()) {
							if(consumos.getIsPostPagoHibrido() != null && !consumos.getIsPostPagoHibrido().equals("")){
								if(detalle2.getDurMinRedn() != null && !detalle2.getDurMinRedn().equals("")){
									try{
										pivote = Double.parseDouble(detalle2.getMontoFree());
										if(pivote < 0)
											totalCosto += (pivote*(-1));
										else
											totalCosto += pivote;
									}catch (NumberFormatException e) {
										totalCosto += 0; 
									}
								}
							}else{
								if(detalle2.getDurMinRedn() != null && !detalle2.getDurMinRedn().equals("")){
									try{
										pivote = Double.parseDouble(detalle2.getMontoReal());
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
					consumos.setTotalMensaje(formatNumber.format(totalCosto));
				}
			}

			if(tipoEvento.equals("4")){
				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){
					try{
//						if(descripcion.getIdPlan() != null && !descripcion.getIdPlan().equals("")){
//							sResponse = obtenPlanes.serviceObtenerDescripcionPlanes(descripcion.getIdPlan(), "", "0", 212121, "10.10.10");
//						}else if(descripcion.getDescripcionPlan() != null && !descripcion.getDescripcionPlan().equals("")){
//							sResponse = obtenPlanes.serviceObtenerDescripcionPlanes("", descripcion.getDescripcionPlan(), "0", 212121, "10.10.10");
//						}else if(descripcion.getCode() != null && !descripcion.getCode().equals("")){
//							sResponse = obtenPlanes.serviceObtenerDescripcionPlanes("", "", descripcion.getCode(), 212121, "10.10.10");
//						}
////					sResponse = obtenPlanes.serviceObtenerDescripcionPlanes("", descripcion.getDescripcionPlan(), 2121, "10.10.10.10");
//					if(sResponse != null && !sResponse.equals(""))
//						respuestaPlan = ParseXMLFile.ParseObtenerDescripcionPlanes(sResponse);
					
						respuestaPlan = OfertaComercial.consultaDescripcionPlanes(descripcion.getIdPlan(), descripcion.getDescripcionPlan(), descripcion.getCode(), 212121, "10.10.10.10");
					
					}catch (ServiceException e) {
						respuestaPlan = new ObtenerDescripcionPlanesVO1();
					}
					
					try{
						if(respuestaPlan.getMegaadc() != null && !respuestaPlan.getMegaadc().equals(""))
							costoMegas = Double.parseDouble(respuestaPlan.getMegaadc().replaceAll("[^\\d.]", ""));
						else
							costoMegas = 0;
					}catch (Exception e) {
						costoMegas = 0;
					}

					for (ConsumoFechaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleVO detalle2 : consumo.getDetalle()) {
							try{
//								salAnt = Double.parseDouble(detalle2.getSaldoAntes());
//								salDes = Double.parseDouble(detalle2.getSaldoDespues());
								saldoTotal += Double.parseDouble(detalle2.getTotalNavegacion());
							}catch (Exception e) {
//								salAnt = 0;
//								salDes = 0;
								saldoTotal += 0;
							}

//							if(detalle2.getDurMinRedn() != null && !detalle2.getDurMinRedn().equals("")){
//								try{
//									totalMegas += ((salAnt - salDes)/1024);
//								}catch (NumberFormatException e) {
//									totalMegas += 0; 
//								}
//							}
						}
					}
					consumos.setTotalDatos(formatNumber.format(saldoTotal));
					consumos.setTotalDatosCosto(formatNumber.format(saldoTotal * costoMegas));
				}
			}
			
			
			if(tipoEvento.equals("5")){
				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){
					
					for(int i=0; i<consumos.getDetalleConsumo().size();i++){
						for(int y=0; y<consumos.getDetalleConsumo().get(i).getDetalle().size();y++){
							if(!consumos.getDetalleConsumo().get(i).getDetalle().get(y).getNumDestino().trim().toLowerCase().equals("iusacell") && !consumos.getDetalleConsumo().get(i).getDetalle().get(y).getNumDestino().trim().toLowerCase().equals("unefon")){
								consumos.getDetalleConsumo().remove(i);
								i--;
								break;
							}
						}
					}
					
					for (ConsumoFechaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleVO detalle2 : consumo.getDetalle()) {
							if(detalle2.getDurMinRedn() != null && !detalle2.getDurMinRedn().equals("")){
								try{
									totalTiempo += Integer.parseInt(detalle2.getDurMinRedn());
								}catch (NumberFormatException e) {
									totalTiempo += 0; 
								}
							}
							if(consumos.getIsPostPagoHibrido() != null && !consumos.getIsPostPagoHibrido().equals("")){
								if(detalle2.getDurMinRedn() != null && !detalle2.getDurMinRedn().equals("")){
									try{
										pivote = Double.parseDouble(detalle2.getMontoFree());
										if(pivote < 0)
											totalCosto += (pivote*(-1));
										else
											totalCosto += pivote;
									}catch (NumberFormatException e) {
										totalCosto += 0; 
									}
								}
							}else{
								if(detalle2.getDurMinRedn() != null && !detalle2.getDurMinRedn().equals("")){
									try{
										pivote = Double.parseDouble(detalle2.getMontoReal());
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
					consumos.setTotalTA(formatNumber.format(totalTiempo));
					consumos.setTotalVoz(formatNumber.format(totalCosto));
				}
			}
			
			if(tipoEvento.equals("6")){
				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){
					
					for(int i=0; i<consumos.getDetalleConsumo().size();i++){
						for(int y=0; y<consumos.getDetalleConsumo().get(i).getDetalle().size();y++){
							if(consumos.getDetalleConsumo().get(i).getDetalle().get(y).getNumDestino().toLowerCase().equals("iusacell") || consumos.getDetalleConsumo().get(i).getDetalle().get(y).getNumDestino().toLowerCase().equals("unefon")){
								consumos.getDetalleConsumo().remove(i);
								i--;
								break;
							}
						}
					}
					
					for (ConsumoFechaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleVO detalle2 : consumo.getDetalle()) {
							if(detalle2.getDurMinRedn() != null && !detalle2.getDurMinRedn().equals("")){
								try{
									totalTiempo += Integer.parseInt(detalle2.getDurMinRedn());
								}catch (NumberFormatException e) {
									totalTiempo += 0; 
								}
							}
							if(consumos.getIsPostPagoHibrido() != null && !consumos.getIsPostPagoHibrido().equals("")){
								if(detalle2.getDurMinRedn() != null && !detalle2.getDurMinRedn().equals("")){
									try{
										pivote = Double.parseDouble(detalle2.getMontoFree());
										if(pivote < 0)
											totalCosto += (pivote*(-1));
										else
											totalCosto += pivote;
									}catch (NumberFormatException e) {
										totalCosto += 0; 
									}
								}
							}else{
								if(detalle2.getDurMinRedn() != null && !detalle2.getDurMinRedn().equals("")){
									try{
										pivote = Double.parseDouble(detalle2.getMontoReal());
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
					consumos.setTotalTA(formatNumber.format(totalTiempo));
					consumos.setTotalVoz(formatNumber.format(totalCosto));
				}
			}
			
		} catch (Exception e) {
			Logger.write("       Detail        : (Exception) " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		
		return consumos;
	}
	
	public ConsumosTotalesVO flujoTotal(final String dn, final String tipoEvento) throws ServiceException{
		ConsumosTotalesVO consumos = new ConsumosTotalesVO();
		String sResponse = "";
		CallServiceDetalleConsumosETAK_LEGACY detalle = new CallServiceDetalleConsumosETAK_LEGACY();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		List<ConsumoFechaVO> consumosFecha = new ArrayList<ConsumoFechaVO>();
		List<SaldosFechaVO> saldosFecha = new ArrayList<SaldosFechaVO>();
		try {
			
			sResponse = focalizacion.focalizacion(dn);
			if(sResponse != null && !sResponse.equals("")){
				descripcion = ParseXMLFile.parseFocalizacion(sResponse);
			}	
			if(descripcion.getFechaCorte() == null){
				descripcion.setFechaCorte("");
			}
			sResponse = detalle.detalleConsumosETAK_LEGACY(dn, tipoEvento, descripcion.getFechaCorte());
			if(sResponse != null && !sResponse.equals("")){
				consumosFecha = ParseXMLFile.parseConsumos(sResponse, tipoEvento, dn);
				saldosFecha = ParseXMLFile.parseSaldos(sResponse);
			}	
			
			consumosFecha = Utilerias.orderConsumosByDate(1, consumosFecha, descripcion.getFechaCorte());
			
			if(descripcion != null)
				consumos.setDescripcionPlan(descripcion.getDescripcionPlan());
				consumos.setDetalleFocalizacion(descripcion);
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
