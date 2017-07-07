package mx.com.iusacell.services.miiusacell.arquitectura;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.ConsumoDetalleTablaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoFechaTablaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoFechaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosTablaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosTotalesVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.SaldosFechaVO;
import mx.com.iusacell.services.miusacell.call.CallServiceDetalleConsumosETAK_LEGACY;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class DetalleConsumosTabla {

	@SuppressWarnings("deprecation")
	public ConsumosTablaVO flujo(final String dn, final String tipoEvento, final boolean resumen) throws ServiceException{
		ConsumosTablaVO consumos = new ConsumosTablaVO();
		String sResponse = "";
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		List<ConsumoFechaTablaVO> consumosFecha = new ArrayList<ConsumoFechaTablaVO>();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
		OracleProcedures oracle = new OracleProcedures();
		String mes [] = {"ene","feb","mar","abr","may","jun","jul","ago","sep","oct","nov","dic"};
		String mesCompleto [] = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		Integer totalTiempo = 0;
		double totalMegas = 0;
		String fechaCompleta = "";
		String formatoFecha = "";
		SimpleDateFormat dateFormatFecha = null;
		Date dateToSimple = new Date();
		String detalleConsumosBDDirecto = "";	
		String camino = "";	
		try {
			
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("DN no puede ir vacio");
			if(tipoEvento == null || tipoEvento.trim().equals(""))
				throw new ServiceException("id evento no puede ir vacio");
			
			try{
				camino = oracle.getValorParametro(121);
			}catch (ServiceException e) {
				camino = "0";
			}
			
			try{
				fechaCompleta = oracle.getValorParametro(10);
			}catch (Exception e) {
				fechaCompleta = "0";
			}
			
		if (camino.equals("1")){	
			try{
				formatoFecha = oracle.getValorParametro(91);
			}catch (Exception e) {
				formatoFecha = "yyyy-MM-dd HH:mm:ss.S";
			}
			
			dateFormatFecha = new SimpleDateFormat(formatoFecha);
			
			sResponse = focalizacion.focalizacion(dn);
			if(sResponse != null && !sResponse.equals("")){
				descripcion = ParseXMLFile.parseFocalizacion(sResponse);
			}	
			if(descripcion.getFechaCorte() == null){
				descripcion.setFechaCorte("");
			}		
			
			try{
				detalleConsumosBDDirecto = oracle.getValorParametro(86);
			}catch (ServiceException e) {
				detalleConsumosBDDirecto = "0";
			}						
			
			if(detalleConsumosBDDirecto.equals("0")){				
				sResponse = oracle.getDetalleConsumoPorDn(dn, Integer.parseInt(tipoEvento));
				
				if(sResponse != null && !sResponse.equals("")){
					consumosFecha = ParseXMLFile.parseConsumosTabla(sResponse, tipoEvento, dn, descripcion, resumen);
				}	
				consumosFecha = Utilerias.orderConsumosByDateTabla(1, consumosFecha, descripcion.getFechaCorte());

			}else{
				SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
				Calendar actual = GregorianCalendar.getInstance();
				java.util.Date dateFin=actual.getTime();
				Date datecon = null;
				if(descripcion.getFechaCorte() != null && !descripcion.getFechaCorte().equals("")){
					datecon = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH).parse(descripcion.getFechaCorte());
				}else{
					int dias = 30;
					if (tipoEvento.equals("4"))
					{
						dias= 30;
					}
					actual.add(Calendar.DATE,-dias);
					datecon = actual.getTime();
				}
				String fechaFin=sdf.format(dateFin);
				String fechaIni=sdf.format(datecon);

				consumosFecha = oracle.getDetalles(dn, fechaIni, fechaFin, tipoEvento);
			}

			for(int i=0; i<consumosFecha.size(); i++){
				if(consumosFecha.get(i).getFecha() != null && !consumosFecha.get(i).getFecha().equals(""))
				{
					try{
						dateToSimple = dateFormatFecha.parse(consumosFecha.get(i).getFecha());
						if(fechaCompleta.equals("1"))
							consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + " de " + mesCompleto[dateToSimple.getMonth()]);
						else
							consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + "/" + mes[dateToSimple.getMonth()]);
					}catch (ParseException e) {}	
				}
				for(int y=0; y<consumosFecha.get(i).getDetalle().size();y++){
					consumosFecha.get(i).getDetalle().get(y).setIdColumna(consumosFecha.size() - i);
				}
			}
			
			if(descripcion != null){
				consumos.setIsPostPagoHibrido(descripcion.getIsPostpagoOrHibrido());
			}
			if(consumosFecha != null)
				consumos.setDetalleConsumo(consumosFecha);

			if(tipoEvento.equals("1")){
				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){

					for (ConsumoFechaTablaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleTablaVO detalle2 : consumo.getDetalle()) {
							if(detalle2.getTiempo() != null && !detalle2.getTiempo().equals("")){
								try{
								    totalTiempo =totalTiempo+ Integer.parseInt(detalle2.getTiempo().replaceAll("[^\\d.]", ""));
								}catch (NumberFormatException e) {
								    totalTiempo = totalTiempo + 0;
								}
							}
						}
					}
					consumos.setTotalTA(formatNumber.format(totalTiempo));
				}
			}

			if(tipoEvento.equals("4")){
				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){

					for (ConsumoFechaTablaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleTablaVO detalle2 : consumo.getDetalle()) {
								try{
									totalMegas = totalMegas+(Double.parseDouble(detalle2.getMbConsumidos()));
								}catch (NumberFormatException e) {
								    totalMegas = totalMegas + 0;
								}
						}
					}
					consumos.setTotalDatos(formatNumber.format(totalMegas));
				}
			}
			
			if(tipoEvento.equals("5")){
				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){

					for (ConsumoFechaTablaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleTablaVO detalle2 : consumo.getDetalle()) {
							if(detalle2.getTiempo() != null && !detalle2.getTiempo().equals("")){
								try{
                                    totalTiempo =totalTiempo+ Integer.parseInt(detalle2.getTiempo().replaceAll("[^\\d.]", ""));
								}catch (NumberFormatException e) {
								    totalTiempo = totalTiempo + 0;
								}
							}
						}
					}
					consumos.setTotalTA(formatNumber.format(totalTiempo));
				}
			}
			
			if(tipoEvento.equals("6")){

				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){

					for (ConsumoFechaTablaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleTablaVO detalle2 : consumo.getDetalle()) {
							if(detalle2.getTiempo() != null && !detalle2.getTiempo().equals("")){
								try{
                                    totalTiempo =totalTiempo+ Integer.parseInt(detalle2.getTiempo().replaceAll("[^\\d.]", ""));
								}catch (NumberFormatException e) {
								    totalTiempo = totalTiempo + 0;
								}
							}
						}
					}
					consumos.setTotalTA(formatNumber.format(totalTiempo));
				}
			}
						} //fin if
			
			/** FLUJO DOS A PRUEBA **/
			if (camino.equals("2")){
				try{
					fechaCompleta = oracle.getValorParametro(10);
				}catch (Exception e) {
					fechaCompleta = "0";
				}
				
				try{
					formatoFecha = oracle.getValorParametro(91);
				}catch (Exception e) {
					formatoFecha = "yyyy-MM-dd HH:mm:ss.S";
				}
				
				dateFormatFecha = new SimpleDateFormat(formatoFecha);
				
				sResponse = focalizacion.focalizacion(dn);
				if(sResponse != null && !sResponse.equals("")){
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);
				}	
				if(descripcion.getFechaCorte() == null){
					descripcion.setFechaCorte("");
				}
				SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
				Calendar actual = GregorianCalendar.getInstance();
				java.util.Date dateFin=actual.getTime();
				Date datecon = null;
				if(descripcion.getFechaCorte() != null && !descripcion.getFechaCorte().equals("")){
					datecon = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH).parse(descripcion.getFechaCorte());
				}else{
					int dias = 30;
					if (tipoEvento.equals("4"))
					{
						dias= 30;
					}
					actual.add(Calendar.DATE,-dias);
					datecon = actual.getTime();
				}
				String fechaFin=sdf.format(dateFin);
				String fechaIni=sdf.format(datecon);
				

		
				
				
				if(tipoEvento.equals("1")){
					consumosFecha = oracle.getDetallesDivisionLlamadas(dn, fechaIni, fechaFin);
					for(int i=0; i<consumosFecha.size(); i++){
						if(consumosFecha.get(i).getFecha() != null && !consumosFecha.get(i).getFecha().equals(""))
						{
							try{
								dateToSimple = dateFormatFecha.parse(consumosFecha.get(i).getFecha());
								if(fechaCompleta.equals("1"))
									consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + " de " + mesCompleto[dateToSimple.getMonth()]);
								else
									consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + "." + mes[dateToSimple.getMonth()]);
							}catch (ParseException e) {}	
						}
						for(int y=0; y<consumosFecha.get(i).getDetalle().size();y++){
							consumosFecha.get(i).getDetalle().get(y).setIdColumna(consumosFecha.size() - i);
						}
					}
					
					if(descripcion != null){
						consumos.setIsPostPagoHibrido(descripcion.getIsPostpagoOrHibrido());
					}
					if(consumosFecha != null)
						consumos.setDetalleConsumo(consumosFecha);
					
					if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){
		
						for (ConsumoFechaTablaVO consumo : consumos.getDetalleConsumo()) {
							for (ConsumoDetalleTablaVO detalle2 : consumo.getDetalle()) {
								if(detalle2.getTiempo() != null && !detalle2.getTiempo().equals("")){
									try{
                                        totalTiempo =totalTiempo+ Integer.parseInt(detalle2.getTiempo().replaceAll("[^\\d.]", ""));
									}catch (NumberFormatException e) {
                                        totalTiempo =totalTiempo+0;
									}
								}
							}
						}
						consumos.setTotalTA(formatNumber.format(totalTiempo));
					}
				}
		        
				if (tipoEvento.equals("2")){
					consumosFecha = oracle.getDetallesDivisionSMS(dn, fechaIni, fechaFin);
					for(int i=0; i<consumosFecha.size(); i++){
						if(consumosFecha.get(i).getFecha() != null && !consumosFecha.get(i).getFecha().equals(""))
						{
							try{
								dateToSimple = dateFormatFecha.parse(consumosFecha.get(i).getFecha());
								if(fechaCompleta.equals("1"))
									consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + " de " + mesCompleto[dateToSimple.getMonth()]);
								else
									consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + "." + mes[dateToSimple.getMonth()]);
							}catch (ParseException e) {}	
						}
						for(int y=0; y<consumosFecha.get(i).getDetalle().size();y++){
							consumosFecha.get(i).getDetalle().get(y).setIdColumna(consumosFecha.size() - i);
						}
					}
					
					if(descripcion != null){
						consumos.setIsPostPagoHibrido(descripcion.getIsPostpagoOrHibrido());
					}
					if(consumosFecha != null)
						consumos.setDetalleConsumo(consumosFecha);
				}
				
				
				if(tipoEvento.equals("4")){
					
					consumosFecha = oracle.getDetallesDivisionDatos(dn, fechaIni, fechaFin);
					for(int i=0; i<consumosFecha.size(); i++){
						if(consumosFecha.get(i).getFecha() != null && !consumosFecha.get(i).getFecha().equals(""))
						{
							try{
								dateToSimple = dateFormatFecha.parse(consumosFecha.get(i).getFecha());
								if(fechaCompleta.equals("1"))
									consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + " de " + mesCompleto[dateToSimple.getMonth()]);
								else
									consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + "." + mes[dateToSimple.getMonth()]);
							}catch (ParseException e) {}	
						}
						for(int y=0; y<consumosFecha.get(i).getDetalle().size();y++){
							consumosFecha.get(i).getDetalle().get(y).setIdColumna(consumosFecha.size() - i);
						}
					}
					
					if(descripcion != null){
						consumos.setIsPostPagoHibrido(descripcion.getIsPostpagoOrHibrido());
					}
					if(consumosFecha != null)
						consumos.setDetalleConsumo(consumosFecha);
					
					
					if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){
		
						for (ConsumoFechaTablaVO consumo : consumos.getDetalleConsumo()) {
							for (ConsumoDetalleTablaVO detalle2 : consumo.getDetalle()) {
									try{
                                        totalMegas =totalMegas+ (Double.parseDouble(detalle2.getMbConsumidos()));
									}catch (NumberFormatException e) {
										totalMegas =totalMegas+0; 
									}
							}
						}
						consumos.setTotalDatos(formatNumber.format(totalMegas));
					}
				}
				
				if(tipoEvento.equals("5")){
					
					
					
					consumosFecha = oracle.getDetallesDivisionComunidad(dn, fechaIni, fechaFin);
					for(int i=0; i<consumosFecha.size(); i++){
						if(consumosFecha.get(i).getFecha() != null && !consumosFecha.get(i).getFecha().equals(""))
						{
							try{
								dateToSimple = dateFormatFecha.parse(consumosFecha.get(i).getFecha());
								if(fechaCompleta.equals("1"))
									consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + " de " + mesCompleto[dateToSimple.getMonth()]);
								else
									consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + "." + mes[dateToSimple.getMonth()]);
							}catch (ParseException e) {}	
						}
						for(int y=0; y<consumosFecha.get(i).getDetalle().size();y++){
							consumosFecha.get(i).getDetalle().get(y).setIdColumna(consumosFecha.size() - i);
						}
					}
					
					if(descripcion != null){
						consumos.setIsPostPagoHibrido(descripcion.getIsPostpagoOrHibrido());
					}
					if(consumosFecha != null)
						consumos.setDetalleConsumo(consumosFecha);
					
					
					if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){
		
						for (ConsumoFechaTablaVO consumo : consumos.getDetalleConsumo()) {
							for (ConsumoDetalleTablaVO detalle2 : consumo.getDetalle()) {
								if(detalle2.getTiempo() != null && !detalle2.getTiempo().equals("")){
									try{
                                        totalTiempo =totalTiempo+ Integer.parseInt(detalle2.getTiempo().replaceAll("[^\\d.]", ""));
									}catch (NumberFormatException e) {
									    totalTiempo =totalTiempo+0; 
									}
								}
							}
						}
						consumos.setTotalTA(formatNumber.format(totalTiempo));
					}
				}
				
				if(tipoEvento.equals("6")){
		
					
					consumosFecha = oracle.getDetallesDivisionOtrasCompanias(dn, fechaIni, fechaFin);
					for(int i=0; i<consumosFecha.size(); i++){
						if(consumosFecha.get(i).getFecha() != null && !consumosFecha.get(i).getFecha().equals(""))
						{
							try{
								dateToSimple = dateFormatFecha.parse(consumosFecha.get(i).getFecha());
								if(fechaCompleta.equals("1"))
									consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + " de " + mesCompleto[dateToSimple.getMonth()]);
								else
									consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + "." + mes[dateToSimple.getMonth()]);
							}catch (ParseException e) {}	
						}
						for(int y=0; y<consumosFecha.get(i).getDetalle().size();y++){
							consumosFecha.get(i).getDetalle().get(y).setIdColumna(consumosFecha.size() - i);
						}
					}
					
					if(descripcion != null){
						consumos.setIsPostPagoHibrido(descripcion.getIsPostpagoOrHibrido());
					}
					if(consumosFecha != null)
						consumos.setDetalleConsumo(consumosFecha);
					
					
					
					
					if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){
		
						for (ConsumoFechaTablaVO consumo : consumos.getDetalleConsumo()) {
							for (ConsumoDetalleTablaVO detalle2 : consumo.getDetalle()) {
								if(detalle2.getTiempo() != null && !detalle2.getTiempo().equals("")){
									try{
                                        totalTiempo =totalTiempo+ Integer.parseInt(detalle2.getTiempo().replaceAll("[^\\d.]", ""));
									}catch (NumberFormatException e) {
									    totalTiempo =totalTiempo+0; 
									}
								}
							}
						}
						consumos.setTotalTA(formatNumber.format(totalTiempo));
					}
				}
		
				
			} // fin flujo 2
			
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
	
	@SuppressWarnings("deprecation")
	public ConsumosTablaVO flujoXPeriodo(final String dn, final String tipoEvento, final String fechaIni, final String fechaFin, final boolean resumen) throws ServiceException{
		ConsumosTablaVO consumos = new ConsumosTablaVO();
		String sResponse = "";
		CallServiceDetalleConsumosETAK_LEGACY detalle = new CallServiceDetalleConsumosETAK_LEGACY();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		List<ConsumoFechaTablaVO> consumosFecha = new ArrayList<ConsumoFechaTablaVO>();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
		OracleProcedures oracle = new OracleProcedures();
		String mes [] = {"ene","feb","mar","abr","may","jun","jul","ago","sep","oct","nov","dic"};
		String mesCompleto [] = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		Integer totalTiempo = 0;
		double totalMegas = 0;
		String fechaCompleta = "";
		String formatoFecha = "";
		SimpleDateFormat dateFormatFecha = null;
		Date dateToSimple = new Date();
		String detalleConsumosBDDirecto = "";
		String consultaConsumosBDLocalxFecha = "";
		try {
			
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("DN no puede ir vacio");
			if(tipoEvento == null || tipoEvento.trim().equals(""))
				throw new ServiceException("id evento no puede ir vacio");
			
			try{
				fechaCompleta = oracle.getValorParametro(10);
			}catch (Exception e) {
				fechaCompleta = "0";
			}
			
			try{
				formatoFecha = oracle.getValorParametro(91);
			}catch (Exception e) {
				formatoFecha = "yyyy-MM-dd HH:mm:ss.S";
			}
			
			try{
				consultaConsumosBDLocalxFecha = oracle.getValorParametro(108);
			}catch (Exception e) {
				consultaConsumosBDLocalxFecha = "yyyy-MM-dd HH:mm:ss.S";
			}
			
			dateFormatFecha = new SimpleDateFormat(formatoFecha);
			
			sResponse = focalizacion.focalizacion(dn);
			if(sResponse != null && !sResponse.equals("")){
				descripcion = ParseXMLFile.parseFocalizacion(sResponse);
			}	
			
			try{
				detalleConsumosBDDirecto = oracle.getValorParametro(88);
			}catch (ServiceException e) {
				detalleConsumosBDDirecto = "0";
			}
			
			if(detalleConsumosBDDirecto.equals("0")){
				if(tipoEvento.equals("5") || tipoEvento.equals("6"))
					sResponse = detalle.detalleConsumosETAK_LEGACY(dn, "1", fechaIni, fechaFin);
				else
					sResponse = detalle.detalleConsumosETAK_LEGACY(dn, tipoEvento, fechaIni, fechaFin);
				if(sResponse != null && !sResponse.equals("")){
					consumosFecha = ParseXMLFile.parseConsumosTabla(sResponse, tipoEvento, dn, descripcion, resumen);
				}	
				
				consumosFecha = Utilerias.orderConsumosByDateTablaxFecha(1, consumosFecha, fechaIni);
			
			}else{
				SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
				Date datecon = null;
				Date dateFin = null;
				
				dateFin = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(fechaFin);
				datecon = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(fechaIni);
				
				String fechaFinDir=sdf.format(dateFin);
				String fechaIniDir=sdf.format(datecon);

				if(consultaConsumosBDLocalxFecha.equals("1")){
					try{
						consumosFecha = oracle.getDetallesLocal(dn, fechaIniDir, fechaFinDir, tipoEvento);
					}catch(Exception e){
						consumosFecha = oracle.getDetalles(dn, fechaIniDir, fechaFinDir, tipoEvento);
					}
				}else{
					consumosFecha = oracle.getDetalles(dn, fechaIniDir, fechaFinDir, tipoEvento);
				}
			}

			for(int i=0; i<consumosFecha.size(); i++){
				if(consumosFecha.get(i).getFecha() != null && !consumosFecha.get(i).getFecha().equals(""))
				{
					try{
						dateToSimple = dateFormatFecha.parse(consumosFecha.get(i).getFecha());
						if(fechaCompleta.equals("1"))
							consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + " de " + mesCompleto[dateToSimple.getMonth()]);
						else
							consumosFecha.get(i).setFecha(new SimpleDateFormat("dd").format(dateToSimple) + "." + mes[dateToSimple.getMonth()]);
					}catch (ParseException e) {}	
				}
				for(int y=0; y<consumosFecha.get(i).getDetalle().size();y++){
					consumosFecha.get(i).getDetalle().get(y).setIdColumna(consumosFecha.size() - i);
				}
			}
			
			if(descripcion != null){
				consumos.setIsPostPagoHibrido(descripcion.getIsPostpagoOrHibrido());
			}
			if(consumosFecha != null)
				consumos.setDetalleConsumo(consumosFecha);

			if(tipoEvento.equals("1")){
				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){

					for (ConsumoFechaTablaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleTablaVO detalle2 : consumo.getDetalle()) {
							if(detalle2.getTiempo() != null && !detalle2.getTiempo().equals("")){
								try{
                                    totalTiempo =totalTiempo+ Integer.parseInt(detalle2.getTiempo().replaceAll("[^\\d.]", ""));
								}catch (NumberFormatException e) {
								    totalTiempo =totalTiempo+0; 
								}
							}
						}
					}
					consumos.setTotalTA(formatNumber.format(totalTiempo));
				}
			}

			if(tipoEvento.equals("4")){
				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){

					for (ConsumoFechaTablaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleTablaVO detalle2 : consumo.getDetalle()) {
								try{
                                    totalMegas =totalMegas+ (Double.parseDouble(detalle2.getMbConsumidos()));
								}catch (NumberFormatException e) {
								    totalMegas=totalMegas+0; 
								}
						}
					}
					consumos.setTotalDatos(formatNumber.format(totalMegas));
				}
			}
			
			if(tipoEvento.equals("5")){
				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){

					for(int i=0; i<consumos.getDetalleConsumo().size();i++){
						for(int y=0; y<consumos.getDetalleConsumo().get(i).getDetalle().size();y++){
							if(!consumos.getDetalleConsumo().get(i).getDetalle().get(y).getCompania().trim().toLowerCase().equals("iusacell") && !consumos.getDetalleConsumo().get(i).getDetalle().get(y).getCompania().trim().toLowerCase().equals("unefon")){
								consumos.getDetalleConsumo().remove(i);
								i--;
								break;
							}
						}
					}
					
					for (ConsumoFechaTablaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleTablaVO detalle2 : consumo.getDetalle()) {
							if(detalle2.getTiempo() != null && !detalle2.getTiempo().equals("")){
								try{
                                    totalTiempo =totalTiempo+ Integer.parseInt(detalle2.getTiempo().replaceAll("[^\\d.]", ""));
								}catch (NumberFormatException e) {
								    totalTiempo =totalTiempo+0; 
								}
							}
						}
					}
					consumos.setTotalTA(formatNumber.format(totalTiempo));
				}
			}
			
			if(tipoEvento.equals("6")){
				if(consumos != null && consumos.getDetalleConsumo() != null && consumos.getDetalleConsumo().size() > 0){

					for(int i=0; i<consumos.getDetalleConsumo().size();i++){
						for(int y=0; y<consumos.getDetalleConsumo().get(i).getDetalle().size();y++){
							if(consumos.getDetalleConsumo().get(i).getDetalle().get(y).getCompania().trim().toLowerCase().equals("iusacell") || consumos.getDetalleConsumo().get(i).getDetalle().get(y).getCompania().trim().toLowerCase().equals("unefon")){
								consumos.getDetalleConsumo().remove(i);
								i--;
								break;
							}
						}
					}
					
					for (ConsumoFechaTablaVO consumo : consumos.getDetalleConsumo()) {
						for (ConsumoDetalleTablaVO detalle2 : consumo.getDetalle()) {
							if(detalle2.getTiempo() != null && !detalle2.getTiempo().equals("")){
								try{
                                    totalTiempo =totalTiempo+ Integer.parseInt(detalle2.getTiempo().replaceAll("[^\\d.]", ""));
								}catch (NumberFormatException e) {
								    totalTiempo =totalTiempo+0; 
								}
							}
						}
					}
					consumos.setTotalTA(formatNumber.format(totalTiempo));
				}
			}
			
		} catch (Exception e) {
			Logger.write("       Detail        : (Exceptioon) " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		
		return consumos;
	}
}
