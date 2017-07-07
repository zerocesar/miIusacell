package mx.com.iusacell.services.miiusacell.arquitectura;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.message.vo.ArchivoAdjunto;
import mx.com.iusacell.message.vo.EmailVO;
import mx.com.iusacell.message.vo.Imagen64VO;
import mx.com.iusacell.message.vo.MessageMailVO;
import mx.com.iusacell.message.vo.RespuestaMensajeVO;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.ConsumosTablaVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFacturaVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleSaldoVO;
import mx.com.iusacell.services.miiusacell.vo.EstadoCuentaVO;
import mx.com.iusacell.services.miiusacell.vo.FacturaVirtualDetalleVO;
import mx.com.iusacell.services.miiusacell.vo.MensajeMailVO;
import mx.com.iusacell.services.miiusacell.vo.messageMail.MessageMailBean;
import mx.com.iusacell.services.miiusacell.vo.messageMail.RespuestaMensajeBean;
import mx.com.iusacell.services.miusacell.call.CallServiceMandaMensaje_Mail;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class EnviaCorreoDetalle {
    
	public MensajeMailVO flujo(final String dn, final String tipoEvento, final String mail, String nombre, final int idServicio, final String ip) throws ServiceException{
		CallServiceMandaMensaje_Mail sendMessage = new CallServiceMandaMensaje_Mail();
		OracleProcedures oracle = new OracleProcedures();
		
		MensajeMailVO respuesta = new MensajeMailVO();
		String sResponse = "";
		String mailBody = "";
		//String compania=(idServicio==1)?"Mi Iusacell":"Mi Unefon";
		String compania="";
		compania = Utilerias.retornacompania(idServicio);
		String[] remitente=new String[2];
		
		StringBuilder htmlText = new StringBuilder();
		String tipoDetalle = "";
		String consumeMail = ""; 
		
		try {
			consumeMail = oracle.getValorParametro(155);
		} catch (Exception e) {
			consumeMail = "0";
		}
		
		
		DetalleConsumosTabla consumos = new DetalleConsumosTabla();
		ConsumosTablaVO respuestaConsumo = new ConsumosTablaVO();
		try{
			if(nombre != null && !nombre.equals("")){
				nombre = Utilerias.formatoCadena(nombre).trim();
			}
			
			respuestaConsumo = consumos.flujo(dn, tipoEvento, true);
			
			if(tipoEvento.equals("1") || tipoEvento.equals("5") || tipoEvento.equals("6")){
				if(respuestaConsumo != null && respuestaConsumo.getDetalleConsumo() != null && respuestaConsumo.getDetalleConsumo().size() > 0){
					if(consumeMail.equals("1")){
					  htmlText.append(construyeMail(dn, respuestaConsumo, "1", idServicio));
					}else{
					  htmlText.append(construyeMailCabecero(dn, respuestaConsumo, "1", idServicio,nombre));	
					}
					
					htmlText.append("&lt;td colspan=\"6\" style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
					htmlText.append("Total");
					htmlText.append("&lt;/td&gt;");

					htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
					htmlText.append(respuestaConsumo.getTotalTA());
					htmlText.append("&lt;/td&gt;");

				}
			}else if(tipoEvento.equals("2")){
				if(respuestaConsumo != null && respuestaConsumo.getDetalleConsumo() != null && respuestaConsumo.getDetalleConsumo().size() > 0){
					if(consumeMail.equals("1")){
					  htmlText.append(construyeMail(dn, respuestaConsumo, "2", idServicio));
					}else{
					  htmlText.append(construyeMailCabecero(dn, respuestaConsumo, "2", idServicio,nombre));	
					}
				}
			}else if(tipoEvento.equals("4")){
				if(respuestaConsumo != null && respuestaConsumo.getDetalleConsumo() != null && respuestaConsumo.getDetalleConsumo().size() > 0){
					if(consumeMail.equals("1")){
					  htmlText.append(construyeMail(dn, respuestaConsumo, "4", idServicio));
					}else{
					  htmlText.append(construyeMailCabecero(dn, respuestaConsumo, "4", idServicio,nombre));	
					}
					
					htmlText.append("&lt;td colspan=\"2\" style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
					htmlText.append("Total");
					htmlText.append("&lt;/td&gt;");
					
					htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
					htmlText.append(respuestaConsumo.getTotalDatos());
					htmlText.append("&lt;/td&gt;");
					
				}
			}
			
			htmlText.append("&lt;/table&gt;");
			
			htmlText.append("&lt;table width=\"500\" id=\"tablaConsumo\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:11px; margin-bottom:0px; margin-top:20px;\"&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td  style=\"text-align:right;\"&gt;*ND - No disponible&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td  style=\"text-align:center;\"&gt;Los consumos de las últimas 48 horas pueden no aparecer en este detalle. Los minutos incluidos en tu plan se contabilizan hasta la facturación por lo que que algunas llamadas incluidas pueden aparecer con costos.&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");
			
		
			mailBody = htmlText.toString();
			
			if(tipoEvento.equals("1"))
				tipoDetalle = "Llamadas";
			else if(tipoEvento.equals("2"))
				tipoDetalle = "Mensajes";
			else if(tipoEvento.equals("4"))
				tipoDetalle = "Navegación";
			else if(tipoEvento.equals("5"))
				tipoDetalle = "Llamadas Comunidad";
			else if(tipoEvento.equals("6"))
				tipoDetalle = "Llamadas Otras Compañias";
			
		
			
			if(consumeMail.equals("1")){
		        sResponse = sendMessage.enviaMail(mailBody, "Buen día "+nombre+",", compania + " - Detalle de "+ tipoDetalle, mail, idServicio);
		        
		        if(sResponse != null && !sResponse.equals(""))
					respuesta = ParseXMLFile.ParseMensajeMail(sResponse);
		        
			}else{
   			   remitente = Utilerias.retornaRemitente(idServicio);
			   sResponse = sendMessage.enviaCorreo(ip, compania + " - Detalle de "+ tipoDetalle, remitente[1], nombre, mailBody, mail, "",idServicio,"", remitente[0]);
			   
			   if(sResponse != null && !sResponse.equals(""))
					respuesta = ParseXMLFile.ParseMensajeCorreo(sResponse);
			   
			}

			
			
		}catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return respuesta;
	}
	
	public MensajeMailVO flujoTotales(final String dn, final String mail, final String nombre, final int idServicio) throws ServiceException{
//		DetalleConsumos consumos = new DetalleConsumos();
//		ConsumosVO consumoResponse = new ConsumosVO();
//		MensajeMailVO respuesta = new MensajeMailVO();
//		String sResponse = "";
//		CallServiceMandaMensaje_Mail sendMessage = new CallServiceMandaMensaje_Mail();
//		StringBuilder htmlText = new StringBuilder();
//		String mailBody = "";
//		Integer totalTiempo = 0;
//		double totalCosto = 0;
//		double totalMegas = 0;
//		double costoMegas = 0;
//		double pivote = 0;
//		double salAnt = 0;
//		double salDes = 0;
//		OracleProcedures oracle = new OracleProcedures();
//		ObtenerDescripcionPlanesVO respuestaPlan = new ObtenerDescripcionPlanesVO();
//		DecimalFormat formatNumber = new DecimalFormat("#.##");
//		String compania=(idServicio==1)?"Mi Iusacell":"Mi Unefon";
//		try{
//			
//			consumoResponse = consumos.flujo(dn, "1");
//			if(consumoResponse != null && consumoResponse.getDetalleConsumo() != null && consumoResponse.getDetalleConsumo().size() > 0){
//				htmlText.append(construyeMail(dn, consumoResponse, "1", idServicio));
//				
//				for (ConsumoFechaVO consumo : consumoResponse.getDetalleConsumo()) {
//					for (ConsumoDetalleVO detalle : consumo.getDetalle()) {
//						if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
//							try{
//								totalTiempo += Integer.parseInt(detalle.getDurMinRedn());
//							}catch (NumberFormatException e) {
//								totalTiempo += 0; 
//							}
//						}
//						if(consumoResponse.getIsPostPagoHibrido() != null && !consumoResponse.getIsPostPagoHibrido().equals("")){
//							if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
//								try{
//									pivote = Double.parseDouble(detalle.getMontoFree());
//									if(pivote < 0)
//										totalCosto += (pivote*(-1));
//									else
//										totalCosto += pivote;
//								}catch (NumberFormatException e) {
//									totalCosto += 0; 
//								}
//							}
//						}else{
//							if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
//								try{
//									pivote = Double.parseDouble(detalle.getMontoReal());
//									if(pivote < 0)
//										totalCosto += (pivote*(-1));
//									else
//										totalCosto += pivote;
//								}catch (NumberFormatException e) {
//									totalCosto += 0; 
//								}
//							}
//						}
//					}
//				}
//				
//				htmlText.append("&lt;td colspan=\"5\" style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
//				htmlText.append("Total");
//				htmlText.append("&lt;/td&gt;");
//
//				htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
//				htmlText.append(totalTiempo + " min");
//				htmlText.append("&lt;/td&gt;");
//				
//				htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
//				htmlText.append("$ " + formatNumber.format(totalCosto));
//				htmlText.append("&lt;/td&gt;");
//			}
//			
//			
//			
//			consumoResponse = new ConsumosVO();
//			consumoResponse = consumos.flujo(dn, "2");
//			totalCosto = 0;
//			if(consumoResponse != null && consumoResponse.getDetalleConsumo() != null && consumoResponse.getDetalleConsumo().size() > 0){
//				htmlText.append(construyeMail(dn, consumoResponse, "2", idServicio));
//				
//				for (ConsumoFechaVO consumo : consumoResponse.getDetalleConsumo()) {
//					for (ConsumoDetalleVO detalle : consumo.getDetalle()) {
//						if(consumoResponse.getIsPostPagoHibrido() != null && !consumoResponse.getIsPostPagoHibrido().equals("")){
//							if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
//								try{
//									pivote = Double.parseDouble(detalle.getMontoFree());
//									if(pivote < 0)
//										totalCosto += (pivote*(-1));
//									else
//										totalCosto += pivote;
//								}catch (NumberFormatException e) {
//									totalCosto += 0; 
//								}
//							}
//						}else{
//							if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
//								try{
//									pivote = Double.parseDouble(detalle.getMontoReal());
//									if(pivote < 0)
//										totalCosto += (pivote*(-1));
//									else
//										totalCosto += pivote;
//								}catch (NumberFormatException e) {
//									totalCosto += 0; 
//								}
//							}
//						}
//					}
//				}
//				
//				htmlText.append("&lt;td colspan=\"5\" style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
//				htmlText.append("Total");
//				htmlText.append("&lt;/td&gt;");
//				
//				htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
//				htmlText.append("$ " + formatNumber.format(totalCosto));
//				htmlText.append("&lt;/td&gt;");
//				
//			}
//			
//			
//			
//			consumoResponse = new ConsumosVO();
//			consumoResponse = consumos.flujo(dn, "4");
//			totalCosto = 0;
//			if(consumoResponse != null && consumoResponse.getDetalleConsumo() != null && consumoResponse.getDetalleConsumo().size() > 0){
//				htmlText.append(construyeMail(dn, consumoResponse, "4", idServicio));
//				
//				respuestaPlan = oracle.obtenerDescripcionPlanes("", consumoResponse.getDescripcionPlan(), 212121, "10.10.10.10");
//				try{
//					if(respuestaPlan.getMegaadc() != null && !respuestaPlan.getMegaadc().equals(""))
//						costoMegas = Double.parseDouble(respuestaPlan.getMegaadc().replaceAll("[^\\d.]", ""));
//					else
//						costoMegas = 0;
//				}catch (Exception e) {
//					costoMegas = 0;
//				}
//				
//				for (ConsumoFechaVO consumo : consumoResponse.getDetalleConsumo()) {
//					for (ConsumoDetalleVO detalle : consumo.getDetalle()) {
//						try{
//							salAnt = Double.parseDouble(detalle.getSaldoAntes());
//							salDes = Double.parseDouble(detalle.getSaldoDespues());
//							}catch (Exception e) {
//								salAnt = 0;
//								salDes = 0;
//							}
//						
//						if(detalle.getDurMinRedn() != null && !detalle.getDurMinRedn().equals("")){
//							try{
//								totalMegas += ((salAnt - salDes)/1024);
//							}catch (NumberFormatException e) {
//								totalMegas += 0; 
//							}
//						}
//					}
//				}
//				
//				htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
//				htmlText.append("Total");
//				htmlText.append("&lt;/td&gt;");
//				
//				htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
//				htmlText.append(formatNumber.format(totalMegas) + "MB");
//				htmlText.append("&lt;/td&gt;");
//				
//				htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
//				htmlText.append("$ " + formatNumber.format(totalMegas * costoMegas));
//				htmlText.append("&lt;/td&gt;");
//			}
//			
//			
//			
//			
//			htmlText.append("&lt;/table&gt;");
//			htmlText.append("&lt;div style=\"padding:20px; text-align:center; font-size:11px; width:90%; margin:auto;\"&gt;");
//			htmlText.append("&lt;p&gt;Los consumos de las ï¿½ltimas 48 horas pueden no aparecer en este detalle. Los minutos incluidos en tu plan se contabilizan hasta la facturaciï¿½n por lo que que algunas llamadas incluidas pueden aparecer con costos.&lt;/p&gt;");
//			htmlText.append("&lt;/div&gt;");
//			mailBody = htmlText.toString();
//			
//			sResponse = sendMessage.enviaMail(mailBody, "Buen dï¿½a "+nombre+",", "Detalle de consumos - " + compania, mail, idServicio);
//
//			if(sResponse != null && !sResponse.equals(""))
//				respuesta = ParseXMLFile.ParseMensajeMail(sResponse);
//
//		}catch (Exception e) {
//			throw new ServiceException("     E R R O R  : (Exception) " + e.getLocalizedMessage());
//		}
		return new MensajeMailVO();
	}
	
	@SuppressWarnings("deprecation")
	public StringBuilder construyeMail(final String dn, final ConsumosTablaVO consumoResponse, final String tipoEvento, final int idServicio) throws ServiceException{
		
		String tipoDetalle = "";
		Date periodo = new Date();
		StringBuilder htmlText = new StringBuilder();
		String[] tipos = null;
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		
		String year = new SimpleDateFormat("yyyy").format(currentDate);
		
		String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		
		try{
		if(tipoEvento.equals("1")){
			tipos = new String[]{"","Fecha","Hora","Numero","Compañia","Destino","Minutos"};
			tipoDetalle = "Llamadas";
		}
		else if(tipoEvento.equals("2")){
			tipos = new String[]{"","Fecha","Hora","Numero","Compañia","Destino"};
			tipoDetalle = "Mensajes";
		}
		else if(tipoEvento.equals("3")){
			tipos = new String[]{"Fecha","Hora","Monto Free","Monto Real","Numero Origen","Numero Destino","Operador Origen","Operador Destino"};
			tipoDetalle = "";
		}
		else if(tipoEvento.equals("4")){
			tipos = new String[]{"","Fecha","MB Consumidos"};
			tipoDetalle = "Navegación";
		}
		
		if(consumoResponse != null){
			if(consumoResponse.getDetalleConsumo() != null && consumoResponse.getDetalleConsumo().size() > 0){
				if(consumoResponse.getDetalleConsumo().get(0) != null && consumoResponse.getDetalleConsumo().get(0).getDetalle() != null && consumoResponse.getDetalleConsumo().get(0).getDetalle().size() > 0){
					
					htmlText.append("&lt;table width=\"500\" id=\"tablaConsumo\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:12px; margin-bottom:0px; margin-top:20px;\"&gt;");
					htmlText.append("&lt;tr&gt;");
					htmlText.append("&lt;td&gt;&lt;div &gt;Detalle de consumo - &lt;span style=\"color:#c40100;\"&gt;"+tipoDetalle+"&lt;/span&gt;&lt;/div&gt;&lt;/td&gt;");
					try{
						periodo = new SimpleDateFormat("dd MMMM").parse(consumoResponse.getDetalleConsumo().get(0).getFecha().replace("de ", "").replace("/", " "));
						if((periodo.getMonth()) >= 0 && (periodo.getMonth()) <= 11)
							htmlText.append("&lt;td style=\"text-align:right;\"&gt;Período - &lt;span style=\"color:#c40100;\"&gt;"+(meses[periodo.getMonth()]) + " " + year +"&lt;/span&gt;&lt;/td&gt;");
						else
							htmlText.append("&lt;td style=\"text-align:right;\"&gt;Período - &lt;span style=\"color:#c40100;\"&gt;"+(new SimpleDateFormat("MMMM").format(periodo)) + " " + year +"&lt;/span&gt;&lt;/td&gt;");
					}catch (ParseException e) {
						htmlText.append("&lt;td style=\"text-align:right;\"&gt;Período - &lt;span style=\"color:#c40100;\"&gt;"+ meses[calendar.get(Calendar.MONTH)] + " " + year +"&lt;/span&gt;&lt;/td&gt;");
					}catch (Exception e) {
						htmlText.append("&lt;td style=\"text-align:right;\"&gt;Período - &lt;span style=\"color:#c40100;\"&gt;"+ meses[calendar.get(Calendar.MONTH)] + " " + year +"&lt;/span&gt;&lt;/td&gt;");
					}
					htmlText.append("&lt;/tr&gt;");
					htmlText.append("&lt;/table&gt;");

					htmlText.append("&lt;img class='image_fix' src=\"cid:division1.jpg\"/&gt;");

					htmlText.append("&lt;table width=\"500\" id=\"tablaConsumo\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:12px; margin-bottom:20px; margin-top:20px;\"&gt;");

					htmlText.append("&lt;tr&gt;");
					try{
						if(tipos != null){
							if(tipos[0] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[0]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[1] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[1]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[2] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[2]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[3] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[3]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[4] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[4]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[5] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[5]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[6] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[6]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[7] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[7]);
								htmlText.append("&lt;/td&gt;");
							}
						}
					}catch (Exception e) {
					}
					
					htmlText.append("&lt;/tr&gt;");
					for(int i=0; i<consumoResponse.getDetalleConsumo().size(); i++){
						htmlText.append("&lt;tr&gt;");
						for(int y=0; y<consumoResponse.getDetalleConsumo().get(i).getDetalle().size(); y++){
							if(tipoEvento.equals("1")){
								htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
								htmlText.append(consumoResponse.getDetalleConsumo().size() - i);
								htmlText.append("&lt;/td&gt;");
								if(i%2 == 0){
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getFecha());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getHora());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getNumero());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getCompania());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getDestino());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getTiempo());
									htmlText.append("&lt;/td&gt;");
									
								}
								else{
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getFecha());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getHora());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getNumero());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getCompania());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getDestino());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getTiempo());
									htmlText.append("&lt;/td&gt;");
									
								}

							}else if(tipoEvento.equals("2")){
								htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
								htmlText.append(consumoResponse.getDetalleConsumo().size() - i);
								htmlText.append("&lt;/td&gt;");
								if(i%2 == 0){
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getFecha());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getHora());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getNumero());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getCompania());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getDestino());
									htmlText.append("&lt;/td&gt;");
									
								}else{
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getFecha());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getHora());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getNumero());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getCompania());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getDestino());
									htmlText.append("&lt;/td&gt;");
									
								}
								
							}else if(tipoEvento.equals("4")){
								double saldoPintar = 0;
								try{
									saldoPintar = Double.parseDouble(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getMbConsumidos());
								}catch (NumberFormatException e) {
									saldoPintar = 0;
								}
								if(saldoPintar > 0.00){
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().size() - i);
									htmlText.append("&lt;/td&gt;");
									if(i%2 == 0){
										htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
										htmlText.append(consumoResponse.getDetalleConsumo().get(i).getFecha());
										htmlText.append("&lt;/td&gt;");

										htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
										htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getMbConsumidos());
										htmlText.append("&lt;/td&gt;");

									}else{
										htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
										htmlText.append(consumoResponse.getDetalleConsumo().get(i).getFecha());
										htmlText.append("&lt;/td&gt;");

										htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
										htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getMbConsumidos());
										htmlText.append("&lt;/td&gt;");

									}
								}
							}
						}
						htmlText.append("&lt;/tr&gt;");
					}
				}
			}
		}
		}catch (Exception e) {
			throw new ServiceException("     E R R O R  : (Exception) " + e.getLocalizedMessage());
		}
		
		return htmlText;
	}
		
	@SuppressWarnings("deprecation")
	public MensajeMailVO flujo(final String dn, final String tipoEvento, final String mail, String nombre, final int idServicio,final String url, final String ip) throws ServiceException{
		CallServiceMandaMensaje_Mail sendMessage = new CallServiceMandaMensaje_Mail();
		MensajeMailVO respuesta = new MensajeMailVO();
		OracleProcedures oracle = new OracleProcedures();
		
		String sResponse = "";
		String mailBody = "";
		Date periodo = new Date();
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		String year = new SimpleDateFormat("yyyy").format(currentDate);
		String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		
		//String compania=(idServicio==1)?"Mi Iusacell":"Mi Unefon";
		String compania="";
		compania = Utilerias.retornacompania(idServicio);
		String[] remitente=new String[2];
		
		StringBuilder htmlText = new StringBuilder();
		
		EstadoCuentaVO estCuentaVO = new EstadoCuentaVO();
		String rentas="";
		String serAdic="";
		String consumosPer="$0.00";
		String ajustes="$0.00";
		String subtotal="";
		String iva="";
		String totalFactura="";
		String perFactura="";
		String consumeMail = "";
				
		try{
			if(nombre != null && !nombre.equals("")){
				nombre = Utilerias.formatoCadena(nombre).trim();
			}
			
			estCuentaVO = DesgloseFactura.flujo(dn,url);
			
			if(estCuentaVO.getRentas().size() > 0) {
				try {
					rentas = estCuentaVO.getRentas().get(0).getCosto(); //Rentas	
				} catch (Exception e) {
					rentas = "0.00";
				}
				
			}
			
			if(estCuentaVO.getServiciosConPromocion().size() > 0) {
				try {
					serAdic = estCuentaVO.getServiciosConPromocion().get(0).getCosto(); //Servicios Adicionales Contratados	
				} catch (Exception e) {
					serAdic = "0.00";
				}
			}
			
			
//			if(estCuentaVO.getConsumosPeriodo().size() > 0) {
//			    try{
//			    	consumosPer = estCuentaVO.getConsumosPeriodo().get(0).getCosto();	
//			    }catch(Exception e){
//			    	consumosPer = "0.00";
//			    }
//			}
			
			if(estCuentaVO.getAjustes().size() > 0) {
				try {
					subtotal = estCuentaVO.getAjustes().get(0).getCosto(); //Subtotal
				} catch (Exception e) {
					subtotal = "0.00";
				}
				
				try {
					iva = estCuentaVO.getAjustes().get(1).getCosto(); //IVA
				}catch (Exception e) {
					iva = "0.00";
				}
			}
			

			if(estCuentaVO.getSaldos().size() > 0) {
				try{
					totalFactura = estCuentaVO.getSaldos().get(5).getCosto(); //Total de Factura
				}catch (Exception e) {
					totalFactura = "0.00";
				}
					
			}
			
			
			if(estCuentaVO.getSaldos().size() > 0) {
				try{
					ajustes = estCuentaVO.getSaldos().get(4).getCosto(); //ajuste por redondeo
				}catch (Exception e) {
					ajustes = "0.00";
				}
					
			}
			
			
			
			if(estCuentaVO.getPeriodo() != null) {
				try {
					perFactura = estCuentaVO.getPeriodo();	
				} catch (Exception e) {
					perFactura = "";
				}
			}
			
			try {
				consumeMail = oracle.getValorParametro(155);	
			} catch (Exception e) {
				consumeMail = "0";
			}
			
			if(!consumeMail.equals("1")){
				htmlText.append("&lt;table width=\"500\" id=\"tablaImagen\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:12px; margin-bottom:0px; margin-top:20px;\"&gt;");
				htmlText.append("&lt;tr&gt;");
				htmlText.append("&lt;td  width=\"450\" &gt;");
				htmlText.append("Buen día " + nombre);
				htmlText.append("&lt;/td&gt;");
				htmlText.append("&lt;td width=\"50\" aling=\"left\" &gt;");
				htmlText.append("&lt;img src=\"cid:logoIusacell\" class=\"logoMiIusacell\" /&gt;");
				htmlText.append("&lt;/td&gt;");
				htmlText.append("&lt;tr&gt;");
				htmlText.append("&lt;/tr&gt;");
				htmlText.append("&lt;tr&gt;");
				htmlText.append("&lt;td colspan=\"2\" &gt;");
				htmlText.append("&lt;img src=\"cid:bannerIusacell\" class=\"iusacell\" /&gt;");
				htmlText.append("&lt;/td&gt;");
				htmlText.append("&lt;/tr&gt;");
				htmlText.append("&lt;/table&gt;");
			}
			
			
			htmlText.append("&lt;table width=\"500\" id=\"tablaConsumo\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:12px; margin-bottom:0px; margin-top:20px;\"&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td&gt;&lt;div &gt;Detalle de Factura &lt;/div&gt;&lt;/td&gt;");

			try{
				if((periodo.getMonth()) >= 0 && (periodo.getMonth()) <= 11)
					htmlText.append("&lt;td style=\"text-align:right;\"&gt;Período - &lt;span style=\"color:#c40100;\"&gt;"+(meses[periodo.getMonth()]) + " " + year +"&lt;/span&gt;&lt;/td&gt;");
				else
					htmlText.append("&lt;td style=\"text-align:right;\"&gt;Período - &lt;span style=\"color:#c40100;\"&gt;"+(new SimpleDateFormat("MMMM").format(periodo)) + " " + year +"&lt;/span&gt;&lt;/td&gt;");
			}catch (Exception e) {
				htmlText.append("&lt;td style=\"text-align:right;\"&gt;Período - &lt;span style=\"color:#c40100;\"&gt;"+ meses[calendar.get(Calendar.MONTH)] + " " + year +"&lt;/span&gt;&lt;/td&gt;");
			}
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");
			
			htmlText.append("&lt;br&gt;");
			htmlText.append("&lt;br&gt;");
			htmlText.append("&lt;br&gt;");
			
			
			htmlText.append("&lt;table class=\"tablaFactura2\" width=\"350\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin:auto; font-family: Arial;\"&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td&gt;");
			htmlText.append("&lt;div style=\"color:#bbbbbb; font-size:22px; \" &gt;"+nombre+"&lt;/div&gt;");
			htmlText.append("&lt;br&gt;");
			htmlText.append("&lt;div style=\"color:#666666; font-size:13px; \" &gt;Aquí podrás descargar tu factura completa de &lt;div&gt;"+perFactura+"&lt;/div&gt;&lt;/div&gt;");
			htmlText.append("&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");
			
			if(!consumeMail.equals("1")){
				htmlText.append("&lt;br&gt;");
				htmlText.append("&lt;table width=\"500\" id=\"tablaImagen\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:12px; margin-bottom:0px; margin-top:20px;\"&gt;");
				htmlText.append("&lt;img class='image_fix' src=\"cid:division1.jpg\"/&gt;");
				htmlText.append("&lt;/table&gt;");
				htmlText.append("&lt;br&gt;");
			}else{
				htmlText.append("&lt;br&gt;");
				htmlText.append("&lt;img class='image_fix' src=\"cid:division1.jpg\"/&gt;");
				htmlText.append("&lt;br&gt;");
			}
			
			htmlText.append("&lt;table class=\"tablaFactura2\" width=\"350\"  border=\"0\" cellpadding=\"1\" cellspacing=\"0\" align=\"center\" style=\"margin:auto; font-size:14px; font-family:Arial; font-weight:normal; color:#999999;\"&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"color:#666666; font-size:16px; font-weight:100; padding:20px 0;\"&gt;Resumen de factura&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:12px 0;\"&gt;Rentas&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:12px 0;\" align=\"right\"&gt;"+rentas+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:12px 0;\"&gt;Servicios adicionales&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:12px 0;\" align=\"right\"&gt;"+serAdic+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:12px 0;\"&gt;Consumos del periodo&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:12px 0;\" align=\"right\"&gt;"+consumosPer+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:12px 0;\"&gt;Subtotal&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:12px 0;\" align=\"right\"&gt;"+subtotal+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:12px 0;\"&gt;IVA (16%)&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:12px 0;\" align=\"right\"&gt;"+iva+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:12px 0;\"&gt;Ajustes&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:12px 0;\" align=\"right\"&gt;"+ajustes+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");
			
			if(!consumeMail.equals("1")){
				htmlText.append("&lt;table width=\"500\" id=\"tablaImagen\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:12px; margin-bottom:0px; margin-top:20px;\"&gt;");
				htmlText.append("&lt;img class='image_fix' src=\"cid:division1.jpg\"/&gt;");
				htmlText.append("&lt;/table&gt;");
			}else{
				htmlText.append("&lt;img class='image_fix' src=\"cid:division1.jpg\"/&gt;");
			}
				
			htmlText.append("&lt;table class=\"tablaFactura2\"  width=\"350\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" align=\"center\" style=\"margin:auto; font-size:13px; font-family:Arial; font-weight:normal; color:#999999;\"&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"color:#666666; font-size:16px; font-weight:100; padding:20px 0;\"&gt;Total a pagar&lt;/td&gt;");
			htmlText.append("&lt;td style=\"color:#666666; font-size:16px; font-weight:100; padding:20px 0;\" align=\"right\"&gt;"+totalFactura+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");

				
			htmlText.append("&lt;table  width=\"270\"  border=\"0\" cellpadding=\"7\" cellspacing=\"0\" align=\"center\"  style=\"margin:auto; background:#ef0700; color:#fff; text-align:center; font-family:Arial; font-weight:normal; \"&gt;");
			htmlText.append("&lt;tr&gt;");
			if (compania.equals("Mi Iusacell"))
			  htmlText.append("&lt;td style=\"padding:10px 0;\"&gt;&lt;a href=\""+url+"\" style=\"text-decoration:none; color:#ffffff; font-size:17px;\"&gt; Descarga tu factura completa&lt;/a&gt;&lt;/td&gt;");
				
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");
			mailBody = htmlText.toString();
		
			

			
			if(consumeMail.equals("1")){
			  sResponse = sendMessage.enviaMail(mailBody, "Buen día "+nombre+",", compania + " - Detalle de Facturación", mail, idServicio);
			  
			  if(sResponse != null && !sResponse.equals(""))
					respuesta = ParseXMLFile.ParseMensajeMail(sResponse);
			  
			}else{
			  remitente = Utilerias.retornaRemitente(idServicio);
			  sResponse = sendMessage.enviaCorreo(ip, compania + " - Detalle de facturación", remitente[1], nombre, mailBody, mail, "",idServicio,"", remitente[0]);
			
			  if(sResponse != null && !sResponse.equals(""))
					respuesta = ParseXMLFile.ParseMensajeCorreo(sResponse);
			
			}

			
			
		}catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return respuesta;
	}
	
	
public DetalleFacturaVO detalleFactura(final String dn, final String url) throws ServiceException{
		
		DetalleFacturaVO respuesta = new DetalleFacturaVO();
		EstadoCuentaVO estCuentaVO = new EstadoCuentaVO();
		String cuenta="";
		String factura="";
		String rentas="";
		String serAdic="0.00";
		String consumosPer="0.00";
		String subtotal="0.00";
		String iva="0.00";
		String ajustes="0.00";
		String totalFactura="";
		String perFactura="";
		String limite = "";	
		String otrosCargos = "0.00";
		String porcentajeIVA = "0";
		try{
						
            estCuentaVO = DesgloseFactura.flujo(dn, url);
			
            if(estCuentaVO.getNoCuenta() != null) {
				try {
					cuenta = estCuentaVO.getNoCuenta(); //numero de cuenta	
				} catch (Exception e) {
					cuenta = "0.00";
				}
				
			}
            
            if(estCuentaVO.getNoFactura() != null) {
				try {
					factura = estCuentaVO.getNoFactura(); //numero de factura	
				} catch (Exception e) {
					factura = "0.00";
				}
				
			}
            
            /*Rentas*/
            if(estCuentaVO.getRentasParser() != null) {
            	try {
					rentas = estCuentaVO.getRentasParser(); //Rentas	
				} catch (Exception e) {
					rentas = "0.00";
				}
            }
            
           
//           if(estCuentaVO.getRentas().size() > 0) {
//				try {
//					rentas = estCuentaVO.getRentas().get(0).getCosto(); //Rentas	
//				} catch (Exception e) {
//					rentas = "0.00";
//				}
//				
//			}
			
			if(estCuentaVO.getServiciosConPromocion().size() > 0) {
				try {
					serAdic = estCuentaVO.getServiciosConPromocion().get(0).getCosto(); //Servicios Adicionales Contratados	
				} catch (Exception e) {
					serAdic = "0.00";
				}
			}
			
			
			if(estCuentaVO.getConsumosPeriodo().size() > 0) {
			    try{
			    	consumosPer = estCuentaVO.getConsumosPeriodo().get(0).getCosto();	//Consumo de periodo
			    }catch(Exception e){
			    	consumosPer = "0.00";
			    }
			  }
			//}else consumosPer = "0.00";
			int  tamanio = estCuentaVO.getSaldos().size();
		    int tamanioAjuste = estCuentaVO.getAjustes().size();
			
		    if(estCuentaVO.getSubtotal() != null) {
				try{
						subtotal = estCuentaVO.getSubtotal(); //Subtotal
				}catch (Exception e) {
					subtotal = "0.0";
				}	
			}
		    
//			if(estCuentaVO.getAjustes().size() > 0) {
//				try {
//					subtotal = estCuentaVO.getAjustes().get(0).getCosto(); //Subtotal
//				} catch (Exception e) {
//					subtotal = "0.00";
//				}
//				
//			}
			
		    
		    //iva (16%)
		    String comparativoIVA = "agregado iva (";
		    if(tamanioAjuste> 0) {
				try{
					for (int i=0; i<=tamanioAjuste;i++){
						String paso = estCuentaVO.getAjustes().get(i).getTipo().toLowerCase().trim();
						if (paso.contains(comparativoIVA)){
							iva = estCuentaVO.getAjustes().get(i).getCosto(); //IVA
							break;
						}
					}
					
				}catch (Exception e) {
					iva = "0.00";
				}
			}
		
		    //monto porcentaje IVA
		    if(estCuentaVO.getPorcentajeIVA() != null) {
				try {
					porcentajeIVA = estCuentaVO.getPorcentajeIVA(); //numero de factura	
				} catch (Exception e) {
					porcentajeIVA = "0.00";
				}
				
			}
		    
            if(estCuentaVO.getAjusteRedondeo() != null) {
				try {
					ajustes = estCuentaVO.getAjusteRedondeo(); // ajuste Redondeo
				} catch (Exception e) {
					ajustes = "0.00";
				}
             }
            
            //otroCArgo
            if(estCuentaVO.getOtroCargo() != null) {
				try {
					otrosCargos = estCuentaVO.getOtroCargo(); // otros cargos
				} catch (Exception e) {
					otrosCargos = "0.00";
				}
             }
            
            //else{
//				String comparativoLimite = "+ ajuste por red";
//			    if(tamanio> 0) {
//					try{
//						for (int i=0; i<=tamanio;i++){
//							String paso = estCuentaVO.getSaldos().get(i).getTipo().toLowerCase().trim();
//							if (paso.contains(comparativoLimite)){
//								ajustes = estCuentaVO.getSaldos().get(i).getCosto(); //limite de pago
//								break;
//							}
//						}
//						
//					}catch (Exception e) {
//						ajustes = "0.00";
//					}	
//				}
//				
//			}

			
		    
            if(estCuentaVO.getTotalFacturaActual() != null) {
				try{
						totalFactura = estCuentaVO.getTotalFacturaActual(); //total factura actual
				}catch (Exception e) {
					totalFactura = "0.0";
				}	
			}
			//TOTAL  A PAGAR
			
			if(estCuentaVO.getPeriodo() != null) {
				try {
					perFactura = estCuentaVO.getPeriodo();	//periodo de pago
				} catch (Exception e) {
					perFactura = "";
				}
			}
		    
			String comparativoLimite = "pague  antes";
		    if(tamanio> 0) {
				try{
					for (int i=0; i<=tamanio;i++){
						String paso = estCuentaVO.getSaldos().get(i).getTipo().toLowerCase().trim();
						if (paso.contains(comparativoLimite)){
							limite = estCuentaVO.getSaldos().get(i).getCosto(); //limite de pago
							break;
						}
					}
					
					
				}catch (Exception e) {
					limite = "";
				}	
			}
			
			respuesta.setCuenta(cuenta);
			respuesta.setFactura(factura);
			respuesta.setRentas(rentas);
			respuesta.setServiciosAdicionales(serAdic);
			respuesta.setConsumosPeriodo(consumosPer);
			respuesta.setSubtotal(subtotal);
			respuesta.setIva(iva);
			respuesta.setAjuste(ajustes);
			respuesta.setTotal(totalFactura);
			respuesta.setPeriodo(perFactura);
			respuesta.setFechaLimite(limite);
			respuesta.setOtrosCargos(otrosCargos);
			respuesta.setPorcentajeIVA(porcentajeIVA);
			
		}catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return respuesta;
	}

	
public MensajeMailVO flujoFacturaVirtual(final String dn, final String tipoEvento, final int idServicio, final String mailFrom, final String nameFrom, final FacturaVirtualDetalleVO facturaVirtual,final String ip) throws ServiceException{
	CallServiceMandaMensaje_Mail sendMessage = new CallServiceMandaMensaje_Mail();
	MensajeMailVO respuesta = new MensajeMailVO();
	OracleProcedures oracle = new OracleProcedures();

	String sResponse = "";
	String mailBody = "";
	Date periodo = new Date();
	Date currentDate = new Date();
	Calendar calendar = Calendar.getInstance();
	String year = new SimpleDateFormat("yyyy").format(currentDate);
	String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
	//String compania= "Mi Iusacell";
	String compania="";
	compania = Utilerias.retornacompania(idServicio);
	
	String[] remitente=new String[2];
	
	StringBuilder htmlText = new StringBuilder();
	
	String nombre="";
	String mail="";
	
	String cSS="";
	String consumeMail="";
			
	try{
		nombre=facturaVirtual.getNombre();
		
		if(nombre != null && !nombre.equals("")){
			nombre=Utilerias.formatoCadena(nombre.trim());
		}
				
		if (facturaVirtual.getSaldoPendiente().contains("-")){
			facturaVirtual.setSaldoPendiente("0.00");
		}
		
		
		htmlText.append("&lt;center&gt;");
		htmlText.append("&lt;div style=\"height:20px;\"&gt;&lt;/div&gt;");
		htmlText.append("&lt;table style=\"font-family: Arial; font-weight:normal; margin:auto; box-shadow:0 0 3px #999999;\"&gt;");
		htmlText.append("&lt;tr&gt;");
		htmlText.append("&lt;td&gt;");
		htmlText.append("&lt;div style=\"height:20px;\"&gt;&lt;/div&gt;");
		htmlText.append("&lt;table class=\"tablaFactura1\" width=\"550\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" &gt;");
		htmlText.append("&lt;tr&gt;");
		htmlText.append("&lt;td align=\"center\"&gt;");
		htmlText.append("&lt;img class='image_fix' src=\"cid:cabezaFactura.jpg\"/&gt;");
		htmlText.append("&lt;/td&gt;");
		htmlText.append("&lt;/tr&gt;");
		htmlText.append("&lt;/table&gt;");
		
		htmlText.append("&lt;table style=\"height:30px;\"&gt;&lt;tr&gt;&lt;td&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;");
		htmlText.append("&lt;table class=\"tablaFactura2\" width=\"350\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin:auto; font-family: Arial;\"&gt;");
		
		htmlText.append("&lt;tr&gt;");
		htmlText.append("&lt;td&gt;");
		
		htmlText.append("&lt;div style=\"color:#bbbbbb; font-size:22px; padding:5px 0px; font-family:Arial; text-align:left; font-weight:100; padding:1px 5px;\"&gt;"+nombre+"&lt;/div&gt;");
		htmlText.append("&lt;div style=\"color:#666666; font-size:16px; padding:1px 5px; text-align:left;\"&gt;Gracias por realizar su pago &lt;/div&gt;");
			
		htmlText.append("&lt;/td&gt;");
		htmlText.append("&lt;/tr&gt;");
		htmlText.append("&lt;/table&gt;");
		
		htmlText.append("&lt;div style=\"height:30px;\"&gt;&lt;/div&gt;");
		htmlText.append("&lt;img class='image_fix' src=\"cid:rayitasombra.jpg\"/&gt;");
		
		
		htmlText.append("&lt;table class=\"tablaFactura2\" width=\"350\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\" align=\"center\" style=\"margin:auto; font-family: Arial; font-size:14px; color:#999999;\"&gt;");
		htmlText.append("&lt;tr&gt;");
		htmlText.append("&lt;td style=\"font-size:14px; font-family:Arial; font-weight:normal; color:#999999; padding:1px 5px;\" align=\"left\" &gt;");
		
		try{
			if(facturaVirtual.getPeriodoXMes() != null)
				htmlText.append("Período:  &lt;span style=\"color:#666666;  padding:1px 5px; \"&gt;"+ facturaVirtual.getPeriodoXMes() +"&lt;/span&gt;");
			else
				htmlText.append("Período:  &lt;span style=\"color:#666666;  padding:1px 5px; \"&gt;"+(new SimpleDateFormat("MMMM").format(periodo)) + " " + year +"&lt;/span&gt;");
		}catch (Exception e) {
			htmlText.append("Período:  &lt;span style=\"color:#666666;  padding:1px 5px; \"&gt;"+ meses[calendar.get(Calendar.MONTH)] + " " + year +"&lt;/span&gt;");
		}
		
		htmlText.append("&lt;/td&gt;");
		htmlText.append("&lt;/tr&gt;");
		htmlText.append("&lt;/table&gt;");
		
		htmlText.append("&lt;div style=\"height:10px;\"&gt;&lt;/div&gt;");
		htmlText.append("&lt;img class='image_fix' src=\"cid:rayitasombra.jpg\"/&gt;");
		htmlText.append("&lt;div style=\"height:10px;\"&gt;&lt;/div&gt;");
		
		htmlText.append("&lt;table class=\"tablaFactura2\" width=\"350\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\" align=\"center\" style=\"margin:auto; font-family: Arial; font-size:14px; color:#999999;\"&gt;");
		htmlText.append("&lt;tr&gt;");
		
		htmlText.append("&lt;td style=\"padding:12px 5px;\" align=\"left\" &gt; Número Celular &lt;/td &gt;");
		htmlText.append("&lt;td style=\"padding:12px 5px; color:#444;\" align=\"right\" &gt;" + dn + "&lt;/td &gt;");
		
		htmlText.append("&lt;/tr&gt;");
		
		//htmlText.append("&lt;tr&gt;");
		//htmlText.append("&lt;td style=\"padding:12px 5px;\" align=\"left\" &gt; No. de Factura &lt;/td &gt;");
		//htmlText.append("&lt;td style=\"padding:12px 5px;\" align=\"right\" &gt;" + facturaVirtual.getNoFactura() + " &lt;/td&gt; ");
		
		//htmlText.append("&lt;/tr&gt;");
		
		htmlText.append("&lt;tr&gt;");
		//htmlText.append("&lt;td style=\"padding:12px 5px;\" align=\"left\" &gt; No. de Cuenta &lt;/td&gt; ");
		//htmlText.append("&lt;td style=\"padding:12px 5px;\" align=\"right\" &gt;" + facturaVirtual.getNoCuenta() + " &lt;/td&gt; ");
		htmlText.append("&lt;/tr&gt;");
		
		htmlText.append("&lt;/table&gt;");
		
		
		htmlText.append("&lt;div style=\"height:5px;\"&gt;&lt;/div&gt; ");
		htmlText.append("&lt;img class='image_fix' src=\"cid:rayitasombra.jpg\"/&gt;");
		htmlText.append("&lt;div style=\"height:10px;\"&gt;&lt;/div&gt; ");
		
		htmlText.append("&lt;table width=\"350\" id=\"tablaFactura2\" cellpadding=\"1\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:14px; margin:auto; font-weight:normal; color:#999999;\"&gt;");
		
		htmlText.append("&lt;tr&gt;");
		
		htmlText.append("&lt;td style=\"padding:12px 5px;\" align=\"left\" &gt; Fecha de Pago &lt;/td &gt; ");
		htmlText.append("&lt;td style=\"padding:12px 5px;\" align=\"right\" &gt;" + facturaVirtual.getFechaPago() + " &lt;/td&gt; ");
		htmlText.append("&lt;/tr&gt;");
		
		htmlText.append("&lt;tr&gt;");
		
		htmlText.append("&lt;/table&gt;");
		
		htmlText.append("&lt;table class=\"tablaFactura2\" width=\"350\"  border=\"0\" cellpadding=\"1\" cellspacing=\"0\" align=\"center\" style=\"margin:auto; font-size:14px; font-family:Arial; font-weight:normal; color:#999999;\"&gt;");
		
		htmlText.append("&lt;tr&gt;");
		
		htmlText.append("&lt;td style=\"padding:12px 5px; background:#f9f9f9; width:240px;\" width=\"240\" align=\"left\" &gt; Saldo al Corte (&lt;span&gt;" + facturaVirtual.getFechaCorte() + ")&lt;/span &gt; &lt;/td &gt; ");
		htmlText.append("&lt;td style=\"padding:12px 5px; background:#f9f9f9\"&gt;$&lt;/td&gt;");
		htmlText.append("&lt;td style=\"padding:12px 5px; background:#f9f9f9;\" align=\"right\" &gt;" + facturaVirtual.getSaldoCorte() + " &lt;/td&gt; ");
		htmlText.append("&lt;/tr&gt;");
		
		htmlText.append("&lt;tr&gt;");
		htmlText.append("&lt;td style=\"padding:12px 5px; background:#f9f9f9; width:240px \" align=\"left\" &gt; Su Pago: &lt;/td&gt; ");
		htmlText.append("&lt;td style=\"padding:12px 5px; background:#f9f9f9\"&gt;$&lt;/td&gt;");
		htmlText.append("&lt;td style=\"padding:12px 5px; background:#f9f9f9;\" align=\"right\"&gt;" + facturaVirtual.getPago() + " &lt;/td&gt; ");
		htmlText.append("&lt;/tr&gt;");

		htmlText.append("&lt;/table&gt;");
		
		htmlText.append("&lt;table width=\"350\" id=\"tablaFactura2\" cellpadding=\"1\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:14px; margin:auto; font-weight:normal; color:#999999;\"&gt;");
		
		htmlText.append("&lt;tr&gt;");
		htmlText.append("&lt;td style=\"padding:12px 5px; width:240px; color:#444;\"  width=\"240\" align=\"left\" &gt; Por Pagar &lt;/td &gt; ");
		htmlText.append("&lt;td style=\"padding:12px 5px;\" &gt;$&lt;/td&gt;");
		htmlText.append("&lt;td style=\"padding:12px 5px; color:#444;\" align=\"right\" &gt;" + facturaVirtual.getSaldoPendiente() + " &lt;/td&gt; ");
		
		htmlText.append("&lt;/tr&gt;");
		htmlText.append("&lt;/table&gt;");
		
		htmlText.append("&lt;table width=\"350\" id=\"tablaFactura2\" cellpadding=\"1\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:14px; margin:auto; font-weight:normal; color:#999999;\"&gt;");
		
		htmlText.append("&lt;tr&gt;");
		htmlText.append("&lt;td style=\"padding:12px 5px;\" align=\"left\" &gt; Código de Autorización &lt;/td &gt; ");
		htmlText.append("&lt;td style=\"padding:12px 5px;\" align=\"right\" &gt;" + facturaVirtual.getCodigoAutorizacion() + " &lt;/td&gt; ");
		
		htmlText.append("&lt;/tr&gt;");
		htmlText.append("&lt;/table&gt;");
		
		htmlText.append("&lt;/td&gt;");
		htmlText.append("&lt;/tr&gt;");
		htmlText.append("&lt;/table&gt;");
		htmlText.append("&lt;/center&gt;");
		
		cSS="@media only screen and (max-width: 480px) {"+
			".encabezado1{"+
			"display:none!important;"+
    		"}"+
			".image_fix {"+
            "height:auto !important;"+
            "max-width:600px !important;"+
            "width: 100% !important;"+
    		"}"+
   			
    		"#backgroundTable {margin:0 auto; padding:0;  line-height: 100% !important; max-width:600px !important; width: 100% !important;}"+
			".tablaFactura1 {margin:0 auto; padding:0;  line-height: 100% !important;  width: 100% !important;}"+
			".tablaFactura2 {margin:0 auto; padding:0;  line-height: 100% !important;   width: 85% !important;}"+

			"#tablaConsumo {margin:0 auto; padding:0;  line-height: 100% !important; max-width:500px !important; width: 97% !important;}"+
			"#tablaCitas {margin:0 auto; padding:0;  line-height: 100% !important; max-width:400px !important; width: 99% !important;}"+

			"a[href^=&quot;tel&quot;], a[href^=&quot;sms&quot;] {"+
                            "text-decoration: none;"+
                            "color: blue; /* or whatever your want */"+
                            "pointer-events: none;"+
                            "cursor: default;"+
                    "}"+

			".mobile_link a[href^=&quot;tel&quot;], .mobile_link a[href^=&quot;sms&quot;] {"+
                            "text-decoration: default;"+
                            "color: orange !important;"+
                            "pointer-events: auto;"+
                            "cursor: default;"+
                    "}"+

			"}"+

			"@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) {"+
			"a[href^=&quot;tel&quot;], a[href^=&quot;sms&quot;] {"+
                            "text-decoration: none;"+
                            "color: blue; /*o el color que quieran */"+
                            "pointer-events: none;"+
                            "cursor: default;"+
                    "}"+

			".mobile_link a[href^=&quot;tel&quot;], .mobile_link a[href^=&quot;sms&quot;] {"+
                            "text-decoration: default;"+
                            "color: orange !important;"+
                            "pointer-events: auto;"+
                            "cursor: default;"+
                    "}"+
			"}";
		
		mailBody = htmlText.toString();
		
		mail=facturaVirtual.getMail();
		
		try {
			consumeMail = oracle.getValorParametro(155);	
		} catch (Exception e) {
			consumeMail = "0";
		}
		 
		 if(consumeMail.equals("1")){
			sResponse = sendMessage.sendMail(mailBody, compania + " - Notificación de Pago de Servicio", mail, nombre,idServicio,mailFrom,nameFrom,cSS,tipoEvento);
		  
			if(sResponse != null && !sResponse.equals(""))
				respuesta = ParseXMLFile.ParseMensajeMailPago(sResponse);
		 
		 }else{
			
			remitente = Utilerias.retornaRemitente(idServicio);
			sResponse = sendMessage.enviaCorreo(ip, compania + " - Notificación de Pago de Servicio", remitente[1], nombre, mailBody, mail, cSS,idServicio,tipoEvento,remitente[0]);
			
			if(sResponse != null && !sResponse.equals(""))
				respuesta = ParseXMLFile.ParseMensajeCorreo(sResponse);

		 }
		
		
			
	}catch (Exception e) {
		throw new ServiceException(e.getLocalizedMessage());
	}
	return respuesta;
}
	
	public MensajeMailVO flujo_sendMail(final String tipoEvento, final int idServicio,String url,final String mailFrom, final String nameFrom,final FacturaVirtualDetalleVO facturaVirtual,final String ip) throws ServiceException{
		CallServiceMandaMensaje_Mail sendMessage = new CallServiceMandaMensaje_Mail();
		OracleProcedures oracle = new OracleProcedures();
		
		MensajeMailVO respuesta = new MensajeMailVO();
		String sResponse = "";
		String mailBody = "";

		//String compania=(idServicio==1)?"Mi Iusacell":"Mi Unefon";
		String compania="";
		compania = Utilerias.retornacompania(idServicio);
		String[] remitente=new String[2];
		
		StringBuilder htmlText = new StringBuilder();
		
		String rentas="";
		String serAdic="";
		String consumosPer="$0.00";
		String ajustes="$0.00";
		String subtotal="";
		String iva="";
		String totalFactura="";
		String perFactura="";
		String nombre="";
		String mail="";
		String cSS="";
		//String dnFormateado="";
		String otrosCargos="";
		String consumeMail ="";
	
			
		try{
			nombre=facturaVirtual.getNombre();
			if(nombre != null && !nombre.equals("")){
				nombre = Utilerias.formatoCadena(nombre).trim();
			}
			
    		rentas=facturaVirtual.getRentas();
			if(rentas.equals("")) {
					rentas = "0.00";
			}
			serAdic=facturaVirtual.getServiciosAdicionales();
			if(serAdic.equals("")) {
					serAdic = "0.00";
			}
			
			consumosPer=facturaVirtual.getConsumosPeriodo();
			if(consumosPer.equals("")) {
			    	consumosPer = "0.00";
			}
			
			subtotal=facturaVirtual.getSubtotal();
			if(subtotal.equals("")) {
					subtotal = "0.00";	
			}
			iva=facturaVirtual.getIva();
			if(iva.equals("")) {
				iva = "0.00";	
			}
			
			otrosCargos=facturaVirtual.getOtrosCargos();
			if(otrosCargos.equals("")) {
				otrosCargos = "0.00";	
			}
			
			totalFactura=facturaVirtual.getTotalPagar();
			if(totalFactura.equals("")) {
					totalFactura = "0.00";	
			}
			
			ajustes=facturaVirtual.getAjustes();
			if(ajustes.equals("")) {
					ajustes = "0.00";	
			}
			
			perFactura=facturaVirtual.getPeriodoXFecha();
			
			htmlText.append("&lt;center&gt;");

			htmlText.append("&lt;table width=\"507px\" height=\"672\" align=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:auto; text-aling:left; font-family: Arial; table-layout:fixed;\"&gt;");
			htmlText.append("&lt;colgroup&gt;");
			htmlText.append("&lt;col style=\"width:15px;\"&gt;&lt;/col&gt;");
			htmlText.append("&lt;col style=\"width:477px;\"&gt;&lt;/col&gt;");
			htmlText.append("&lt;col style=\"width:15px;\"&gt;&lt;/col&gt;");
			htmlText.append("&lt;/colgroup&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td height=\"85\" width=\"507\" colspan=\"3\"&gt; &lt;div style=\"width: 507px !important; height: 85px !important; overflow: hidden;\"&gt; &lt;img src=\"cid:header.jpg\" style=\"display:block;\" class=\"image_fix\"&gt; &lt;/div&gt; &lt;/td&gt;");  
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td width=\"15px\"&gt; &lt;img src=\"cid:ladIzq.jpg\" style=\"display:block;\" class=\"image_fix\"&gt;&lt;/td&gt;");
			htmlText.append("&lt;td width=\"477px\"&gt;");
			htmlText.append("&lt;table width=\"477\" height=\"\" align=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:auto; text-aling:left; font-family: Arial; table-layout:fixed;\"&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td&gt;");
			htmlText.append("&lt;table class=\"tablaFactura2\" width=\"400px\"  border=\"0\" cellpadding=\"1\" cellspacing=\"0\" align=\"center\" style=\"margin:0px auto; font-size:14px; font-family:Arial; font-weight:normal; color:#ACACAC; table-layout:fixed;\"&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td align=\"left\" height=\"40px\"&gt;");
			htmlText.append("&lt;div style=\"padding-left: 0%; height:17px !important; font-size: 18px; color: #B9B8B8; text-aling:left; font-family:Arial;\"&gt;Detalle de Factura&lt;/div&gt;");
			htmlText.append("&lt;div style=\"padding-left: 0%; height:15px !important; margin-top:5px; font-size: 15px; color: #757579; text-aling:left; font-family: Arial;\"&gt;"+facturaVirtual.getPeriodoXMes()+"&lt;/div&gt;");
			htmlText.append("&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");
			htmlText.append("&lt;/td&gt;");						
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td&gt;");
			htmlText.append("&lt;table class=\"tablaFactura2\" width=\"350px\"  border=\"0\" cellpadding=\"1\" cellspacing=\"0\" align=\"center\" style=\"margin:0px auto; font-size:14px; font-family:Arial; font-weight:normal; color:#ACACAC; table-layout:fixed;\"&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td align=\"left\" height=\"45\"&gt;");
			htmlText.append("&lt;div style=\"padding-left: 0%; height:23px !important; margin-top: 15px; height:20px; font-size: 18px; color: #CBCBCB; text-aling:left; font-family: Arial;\"&gt;"+nombre+"&lt;/div&gt;");
			htmlText.append("&lt;div style=\"padding-left: 0%; height:15px !important; margin-top:3px; font-size: 12px; color: #757579; text-aling:left; font-family: Arial;\"&gt;Aquí podrás descargar tu factura completa&lt;/div&gt;");
			htmlText.append("&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");
			htmlText.append("&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");					
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td height=\"20\"&gt;&lt;img height=\"10px\" src=\"cid:line.jpg\" style=\"display:block; margin-top: 10px;\" class=\"image_fix\"&gt;&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td height=\"169\"&gt;");
			htmlText.append("&lt;table class=\"tablaFactura2\" width=\"350px\"  border=\"0\" cellpadding=\"1\" cellspacing=\"0\" align=\"center\" style=\"margin:10px auto; font-size:14px; font-family:Arial; font-weight:normal; color:#ACACAC; table-layout:fixed;\"&gt;");
			htmlText.append("&lt;colgroup&gt;");
			htmlText.append("&lt;col style=\"width:65%;\"&gt;&lt;/col&gt;");
			htmlText.append("&lt;col style=\"width:5%;\"&gt;&lt;/col&gt;");
			htmlText.append("&lt;col style=\"width:30%;\"&gt;&lt;/col&gt;");
			htmlText.append("&lt;/colgroup&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td class=\"nombreOwn2\" align=\"left\" style=\"color:#757579; height:25px !important; font-size:16px; font-weight:100; padding:0px 0 2px 0;\" colspan=\"3\"&gt;Resumen de factura&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td align=\"left\" style=\"color:#ACACAC; font-size:11px; font-weight:100; padding:0px 0px 5px 0px; height:15px !important;\"  colspan=\"3\"&gt;"+perFactura+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"left\" width=\"70%\"&gt;Rentas&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"right\"&gt;$&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"right\"&gt;"+rentas+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"left\" width=\"70%\" &gt;Servicios adicionales&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"right\"&gt;$&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"right\"&gt;"+serAdic+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"left\" width=\"70%\" &gt;Consumos del periodo&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"right\"&gt;$&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"right\"&gt;"+consumosPer+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"left\" width=\"70%\"&gt;Ajustes&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"right\"&gt;$&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"right\"&gt;"+ajustes+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");
			htmlText.append("&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td height=\"10\"&gt;&lt;img height=\"10px\" src=\"cid:line.jpg\" style=\"display:block; margin-top: 0px;\" class=\"image_fix\"&gt;&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td height=\"98\"&gt;");
			htmlText.append("&lt;table class=\"tablaFactura2\" width=\"350\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin:3px auto; font-size:14px; font-family:Arial; font-weight:normal; color:#ACACAC; table-layout:fixed;\"&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important; color:#515151; margin-top:0px;\" align=\"left\" width=\"65%\"&gt;Subtotal&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"right\" width=\"5%\"&gt;$&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important; color:#515151;\" align=\"right\" width=\"30%\"&gt;"+subtotal+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important; color: #939393;\" align=\"left\" width=\"65%\"&gt;IVA ("+facturaVirtual.getPorcentajeIVA()+"%)&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"right\" width=\"5%\"&gt;$&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important; color:#939393;\" align=\"right\" width=\"30%\"&gt;"+iva+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important; color: #939393;\" align=\"left\" width=\"65%\"&gt;Otros&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important;\" align=\"right\" width=\"5%\"&gt;$&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:4px 0; height:15px !important; color:#939393;\" align=\"right\" width=\"30%\"&gt;"+otrosCargos+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");
			htmlText.append("&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td height=\"10\"&gt;&lt;img height=\"10px\" src=\"cid:line.jpg\" style=\"display:block; margin-top: 0px;\" class=\"image_fix\"&gt;&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td height=\"114\"&gt;");
			htmlText.append("&lt;table class=\"tablaFactura2\" width=\"350\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin:auto; font-size:14px; font-family:Arial; font-weight:normal; color:#999999;\"&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:8px 0; color: #757579; font-size: 17px; height:25px !important;\" align=\"left\" width=\"65%\"&gt;Total&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:8px 0; color:#C8C7C7; font-size: 20px;\" align=\"right\" width=\"5%\"&gt;$&lt;/td&gt;");
			htmlText.append("&lt;td style=\"padding:8px 0; font-size: 20px; color:#757579; height:25px !important;\" align=\"right\" width=\"30%\"&gt;"+totalFactura+"&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");
			htmlText.append("&lt;table class=\"tablaFactura2\"  width=\"350\" height=\"25px\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"  style=\"margin:18px auto 0px auto; background:#ef0700; color:#fff; text-align:center; font-family:Arial; font-weight:normal;\"&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td style=\"padding:8px 10px; height:25px !important;\"&gt;&lt;a href=\""+url+"\" target=\"_blanck\" class=\"descarga\" style=\"text-decoration:none; color:#ffffff; font-size:15xpx;\"&gt; Descarga tu factura completa&lt;/a&gt;&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");
			htmlText.append("&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");
			htmlText.append("&lt;/td&gt;");
			htmlText.append("&lt;td width=\"15px\"&gt; &lt;img src=\"cid:ladDer.jpg\" style=\"display:block;\" class=\"image_fix\"&gt;&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;tr&gt;");
			htmlText.append("&lt;td height=\"81\" width=\"507\" colspan=\"3\"&gt; &lt;div style=\"width: 507px !important; height: 85px !important; overflow: hidden;\"&gt; &lt;img src=\"cid:footer.jpg\" style=\"display:block;\" class=\"image_fix\"&gt; &lt;/div&gt;&lt;/td&gt;");
			htmlText.append("&lt;/tr&gt;");
			htmlText.append("&lt;/table&gt;");

			htmlText.append("&lt;/center&gt;");
			
			
			mailBody = htmlText.toString();
		
			mail=facturaVirtual.getMail();
			
			cSS="@media only screen and (max-width: 480px) {"+

				".encabezado1{"+
				"display:none!important;"+
        		"}"+
				".image_fix {"+
                "display: block;"+
        		"}"+

        		"#backgroundTable {margin:0 auto; padding:0;  line-height: 100% !important; max-width:600px!important; width: 100% !important;}"+
				".tablaFactura1 {margin:0 auto; padding:0;  line-height: 100% !important;  width: 100%!important;}"+
				".tablaFactura1 tr td a{ width:60% !important; margin: auto;}"+
				"table tr td {border-spacing: 0px !important;}"+
				".tablaFactura2 {margin:0 auto; padding:0;  line-height: 100% !important;   width: 85%!important;}"+
				".nombreOwn{font-size: 18px!important;}"+
				".nombreOwn2{font-size: 14px!important;}"+

				"table tr td a{"+
				"border:0px !important;"+
				"text-decoration: none !important;"+
				"color: white !important;"+
				"padding: 0px !important;"+
				"margin: 0px !important;"+
				"}"+

				"#tablaConsumo {margin:0 auto; padding:0;  line-height: 100% !important; max-width:500px !important; width: 97% !important;}"+
				"#tablaCitas {margin:0 auto; padding:0;  line-height: 100% !important; max-width:400px !important; width: 99% !important;}"+
				"a[href^=&quot;tel&quot;], a[href^=&quot;sms&quot;] {"+
                                "text-decoration: none;"+
                                "color: white; /* or whatever your want */"+
                                "pointer-events: none;"+
                                "cursor: default;"+
                        "}"+

				".mobile_link a[href^=&quot;tel&quot;], .mobile_link a[href^=&quot;sms&quot;] {"+
                                "text-decoration: default;"+
                                "pointer-events: auto;"+
                                "cursor: default;"+
                        "}"+
				".descarga{font-size: 14px !important;}"+

				"}";
			
			try {
				consumeMail = oracle.getValorParametro(155);	
			} catch (Exception e) {
				consumeMail = "0";
			}
			 
			
			 if(consumeMail.equals("1")){
			    sResponse = sendMessage.sendMail(mailBody, compania + " - Detalle de Facturación", mail, nombre, idServicio, mailFrom, nameFrom, cSS, tipoEvento);
			    
			    if(sResponse != null && !sResponse.equals(""))
			    	respuesta = ParseXMLFile.ParseMensajeMailPago(sResponse);
			    
			 }else{
			
			   remitente = Utilerias.retornaRemitente(idServicio);
			   sResponse = sendMessage.enviaCorreo(ip, compania + " - Detalle de facturación", remitente[1], nombre, mailBody, mail, cSS,idServicio,tipoEvento,remitente[0]);
			   
			   if(sResponse != null && !sResponse.equals(""))
					respuesta = ParseXMLFile.ParseMensajeCorreo(sResponse);
			 }
			
		}catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return respuesta;
	}

	
	public DetalleSaldoVO detalleSaldo(final String dn, final String url) throws ServiceException{
		
		DetalleSaldoVO respuesta = new DetalleSaldoVO();

		try{
						
			respuesta = DesgloseFactura.flujoSaldo(dn, url);
			
		}catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return respuesta;
	}
	
	@SuppressWarnings("deprecation")
	public StringBuilder construyeMailCabecero(final String dn, final ConsumosTablaVO consumoResponse, final String tipoEvento, final int idServicio, final String nombre) throws ServiceException{
		
		String tipoDetalle = "";
		Date periodo = new Date();
		StringBuilder htmlText = new StringBuilder();
		String[] tipos = null;
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		String logo="";
		String imgNotificaciones="";
		
		String year = new SimpleDateFormat("yyyy").format(currentDate);
		
		String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		
		try{
		if(tipoEvento.equals("1")){
			tipos = new String[]{"","Fecha","Hora","Numero","Compañia","Destino","Minutos"};
			tipoDetalle = "Llamadas";
		}
		else if(tipoEvento.equals("2")){
			tipos = new String[]{"","Fecha","Hora","Numero","Compañia","Destino"};
			tipoDetalle = "Mensajes";
		}
		else if(tipoEvento.equals("3")){
			tipos = new String[]{"Fecha","Hora","Monto Free","Monto Real","Numero Origen","Numero Destino","Operador Origen","Operador Destino"};
			tipoDetalle = "";
		}
		else if(tipoEvento.equals("4")){
			tipos = new String[]{"","Fecha","MB Consumidos"};
			tipoDetalle = "Navegación";
		}
		
		if(idServicio == 1 ){
			logo = "logoIusacell.jpg";
			imgNotificaciones = "bannerIusacell.jpg";
		}else {
			logo = "logoUnefon.jpg";
			imgNotificaciones = "bannerUnefon.jpg";
		}
		
		
		if(consumoResponse != null){
			if(consumoResponse.getDetalleConsumo() != null && consumoResponse.getDetalleConsumo().size() > 0){
				if(consumoResponse.getDetalleConsumo().get(0) != null && consumoResponse.getDetalleConsumo().get(0).getDetalle() != null && consumoResponse.getDetalleConsumo().get(0).getDetalle().size() > 0){
					
					htmlText.append("&lt;table width=\"500\" id=\"tablaImagen\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:12px; margin-bottom:0px; margin-top:20px;\"&gt;");
					htmlText.append("&lt;tr&gt;");
					htmlText.append("&lt;td  width=\"450\" &gt;");
					htmlText.append("Buen día " + nombre);
					htmlText.append("&lt;/td&gt;");
					htmlText.append("&lt;td width=\"50\" aling=\"left\" &gt;");
					htmlText.append("&lt;img src=\"cid:"+logo+"\" class=\"logoMiIusacell\" /&gt;");
					htmlText.append("&lt;/td&gt;");
					htmlText.append("&lt;tr&gt;");
					htmlText.append("&lt;/tr&gt;");
					
					htmlText.append("&lt;tr&gt;");
					htmlText.append("&lt;td colspan=\"2\" &gt;");
					htmlText.append("&lt;img src=\"cid:"+imgNotificaciones+"\" class=\"iusacell\" /&gt;");
					htmlText.append("&lt;/td&gt;");
					htmlText.append("&lt;/tr&gt;");
					htmlText.append("&lt;/table&gt;");
					
					
					htmlText.append("&lt;table width=\"500\" id=\"tablaConsumo\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:12px; margin-bottom:0px; margin-top:20px;\"&gt;");
					htmlText.append("&lt;tr&gt;");
					htmlText.append("&lt;td&gt;&lt;div &gt;Detalle de consumo - &lt;span style=\"color:#c40100;\"&gt;"+tipoDetalle+"&lt;/span&gt;&lt;/div&gt;&lt;/td&gt;");
					try{
						periodo = new SimpleDateFormat("dd MMMM").parse(consumoResponse.getDetalleConsumo().get(0).getFecha().replace("de ", "").replace("/", " "));
						if((periodo.getMonth()) >= 0 && (periodo.getMonth()) <= 11)
							htmlText.append("&lt;td style=\"text-align:right;\"&gt;Período - &lt;span style=\"color:#c40100;\"&gt;"+(meses[periodo.getMonth()]) + " " + year +"&lt;/span&gt;&lt;/td&gt;");
						else
							htmlText.append("&lt;td style=\"text-align:right;\"&gt;Período - &lt;span style=\"color:#c40100;\"&gt;"+(new SimpleDateFormat("MMMM").format(periodo)) + " " + year +"&lt;/span&gt;&lt;/td&gt;");
					}catch (ParseException e) {
						htmlText.append("&lt;td style=\"text-align:right;\"&gt;Período - &lt;span style=\"color:#c40100;\"&gt;"+ meses[calendar.get(Calendar.MONTH)] + " " + year +"&lt;/span&gt;&lt;/td&gt;");
					}catch (Exception e) {
						htmlText.append("&lt;td style=\"text-align:right;\"&gt;Período - &lt;span style=\"color:#c40100;\"&gt;"+ meses[calendar.get(Calendar.MONTH)] + " " + year +"&lt;/span&gt;&lt;/td&gt;");
					}
					htmlText.append("&lt;/tr&gt;");
					htmlText.append("&lt;/table&gt;");
                    
					htmlText.append("&lt;table width=\"500\" id=\"tablaImagen\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:12px; margin-bottom:0px; margin-top:20px;\"&gt;");
					htmlText.append("&lt;img class='image_fix' src=\"cid:division1.jpg\"/&gt;");
					htmlText.append("&lt;/table&gt;");

					htmlText.append("&lt;table width=\"500\" id=\"tablaConsumo\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" align=\"center\" style=\"font-family:Arial; font-size:12px; margin-bottom:20px; margin-top:20px;\"&gt;");

					htmlText.append("&lt;tr&gt;");
					try{
						if(tipos != null){
							if(tipos[0] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[0]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[1] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[1]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[2] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[2]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[3] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[3]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[4] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[4]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[5] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[5]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[6] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[6]);
								htmlText.append("&lt;/td&gt;");
							}
							if(tipos[7] != null){
								htmlText.append("&lt;td style=\"text-align:center;\"&gt;");
								htmlText.append(tipos[7]);
								htmlText.append("&lt;/td&gt;");
							}
						}
					}catch (Exception e) {
					}
					
					htmlText.append("&lt;/tr&gt;");
					for(int i=0; i<consumoResponse.getDetalleConsumo().size(); i++){
						htmlText.append("&lt;tr&gt;");
						for(int y=0; y<consumoResponse.getDetalleConsumo().get(i).getDetalle().size(); y++){
							if(tipoEvento.equals("1")){
								htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
								htmlText.append(consumoResponse.getDetalleConsumo().size() - i);
								htmlText.append("&lt;/td&gt;");
								if(i%2 == 0){
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getFecha());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getHora());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getNumero());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getCompania());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getDestino());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getTiempo());
									htmlText.append("&lt;/td&gt;");
									
								}
								else{
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getFecha());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getHora());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getNumero());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getCompania());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getDestino());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getTiempo());
									htmlText.append("&lt;/td&gt;");
									
								}

							}else if(tipoEvento.equals("2")){
								htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
								htmlText.append(consumoResponse.getDetalleConsumo().size() - i);
								htmlText.append("&lt;/td&gt;");
								if(i%2 == 0){
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getFecha());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getHora());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getNumero());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getCompania());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getDestino());
									htmlText.append("&lt;/td&gt;");
									
								}else{
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getFecha());
									htmlText.append("&lt;/td&gt;");

									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getHora());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getNumero());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getCompania());
									htmlText.append("&lt;/td&gt;");
									
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getDestino());
									htmlText.append("&lt;/td&gt;");
									
								}
								
							}else if(tipoEvento.equals("4")){
								double saldoPintar = 0;
								try{
									saldoPintar = Double.parseDouble(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getMbConsumidos());
								}catch (NumberFormatException e) {
									saldoPintar = 0;
								}
								if(saldoPintar > 0.00){
									htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
									htmlText.append(consumoResponse.getDetalleConsumo().size() - i);
									htmlText.append("&lt;/td&gt;");
									if(i%2 == 0){
										htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
										htmlText.append(consumoResponse.getDetalleConsumo().get(i).getFecha());
										htmlText.append("&lt;/td&gt;");

										htmlText.append("&lt;td style=\"border:solid 1px #aaa; text-align:right;\"&gt;");
										htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getMbConsumidos());
										htmlText.append("&lt;/td&gt;");

									}else{
										htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
										htmlText.append(consumoResponse.getDetalleConsumo().get(i).getFecha());
										htmlText.append("&lt;/td&gt;");

										htmlText.append("&lt;td style=\"border:solid 1px #aaa; background:#f5f5f5; text-align:right;\"&gt;");
										htmlText.append(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getMbConsumidos());
										htmlText.append("&lt;/td&gt;");

									}
								}
							}
						}
						htmlText.append("&lt;/tr&gt;");
					}
				}
			}
		}
		}catch (Exception e) {
			throw new ServiceException("     E R R O R  : (Exception) " + e.getLocalizedMessage());
		}
		
		return htmlText;
	}
	
	public MensajeMailVO flujoFacturaVirtualATT(final String dn, final String tipoEvento, final int idServicio, final String mailFrom, final String nameFrom, final FacturaVirtualDetalleVO facturaVirtual,final String ip) throws ServiceException{
		CallServiceMandaMensaje_Mail sendMessage = new CallServiceMandaMensaje_Mail();
		MensajeMailVO respuesta = new MensajeMailVO();

		String sResponse = "";
		String mailBody = "";
		
		String compania="";
		compania = Utilerias.retornacompania(idServicio);
		
		StringBuilder htmlText = new StringBuilder();
		
		String nombre="";
		String mail="";
		
		String cSS="";

				
		try{
			nombre=facturaVirtual.getNombre();
			
			if(nombre != null && !nombre.equals("")){
				nombre=Utilerias.formatoCadena(nombre.trim());
			}
					
			if (facturaVirtual.getSaldoPendiente().contains("-")){
				facturaVirtual.setSaldoPendiente("0.00");
			}
			
			
			htmlText.append("<center>");
			
			htmlText.append("	<table width='535px' height='854' align='top' border='0' cellpadding='0' cellspacing='0' style='margin:auto; text-aling:left; font-family: Arial; table-layout:fixed; min-width: 535px !important;'>");
			htmlText.append("		<colgroup>");
			htmlText.append("			<col style='width:24px;'></col>");
			htmlText.append("			<col style='width:488px;'></col>");
			htmlText.append("			<col style='width:23px;'></col>");
			htmlText.append("		</colgroup>");
			htmlText.append("		<tr>");
			htmlText.append("			<td height='165' width='535' colspan='3'> <div style='width: 535px !important; min-width: 535px !important; height: 165px !important; overflow: hidden;'> <img class='image_fix' src='cid:headerATT.jpg' style='display:block;'> </div> </td>  ");
			htmlText.append("		</tr>");
			htmlText.append("		<tr>");
			htmlText.append("			<td width='24px' height='571'> <img class='image_fix' src='cid:leftATT.jpg' style='display:block;' class='image_fix'></td>");
			htmlText.append("			<td width='488px' height='571' align='top' style='min-width: 488px !important;'>");
			htmlText.append("				<center>");
			htmlText.append("					<table width='420' height='571' align='top' border='0' cellpadding='0' cellspacing='0' style='margin:auto !important; text-aling:left; font-size:13px; font-family: Arial, sans-serif; table-layout:fixed;'>");
			htmlText.append("						<tr><td height='15' colspan='2'></td></tr>");
			htmlText.append("						<tr>");
			htmlText.append("							<td colspan='2' height='25' style='color:#ef6f00; font-size:17px; text-align: left !important;'>Hola,</td>");
			htmlText.append("						</tr>");
			htmlText.append("						<tr>");
			htmlText.append("							<td colspan='2' height='25' style='color:#bbbbbb; font-size:17px; text-align: left !important;'>"+nombre+"</td>");
			htmlText.append("						</tr>");
			htmlText.append("						<tr>");
			htmlText.append("							<td height='45' style='color:#ef6f00; font-size:13px; text-align: left !important;'>Notificación de Pago</td>");
			htmlText.append("							<td style='color:#ef6f00; font-size:13px; text-align: right !important;'>Periódo: <span style='color: #bbbbbb;'>"+facturaVirtual.getPeriodoXMes()+"</span></td>");
			htmlText.append("						</tr>");
			htmlText.append("						<tr>");
			htmlText.append("							<td colspan='2' height='20' align='center'><img class='image_fix' src='cid:lineATT.jpg' alt=''></td>");
			htmlText.append("						</tr>");
			htmlText.append("						<tr>");
			htmlText.append("							<td colspan='2' height='50q' style='color:#666666; font-size:15px; text-align: left !important;'>Número de celular: <span style='color:#ef6f00;'>"+dn+"</span></td>");
			htmlText.append("						</tr>");
			htmlText.append("						<tr>");
			htmlText.append("							<td valign='top' colspan='2'>");
			htmlText.append("								<center>");
			htmlText.append("									<table width='350px'  align='top' border='0' cellpadding='0' cellspacing='0' style='margin:auto; text-aling:left; font-family: Arial, sans-serif; table-layout:fixed; color:#bbbbbb; min-width: 350px !important;'>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='35' style='text-align: left !important;'>Fecha de pago</td>");
			htmlText.append("											<td colspan='2' style='text-align: right !important;'>"+facturaVirtual.getFechaPago()+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='35' colspan='2' style='text-align: left !important;'>Saldo al corte ("+facturaVirtual.getFechaCorte()+")</td>");
			htmlText.append("											<td style='text-align: right !important;'>$"+facturaVirtual.getSaldoCorte()+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='35' colspan='2' style='text-align: left !important;'>Su pago</td>");
			htmlText.append("											<td style='text-align: right !important;'>$"+facturaVirtual.getPago()+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='35' colspan='2' style='color: #999; font-weight: bold; text-align: left !important;'>Por pagar</td>");
			htmlText.append("											<td style='text-align: right !important; color: #666;'>$"+facturaVirtual.getSaldoPendiente()+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='20' colspan='3'><img class='image_fix' src='cid:lineATT.jpg' alt=''></td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='30' colspan='3' style='color:#666666; text-align: left !important;'>Código de Autorización</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='70' colspan='3' style='text-align: center !important; color:#666666; font-size:20px;'>"+facturaVirtual.getCodigoAutorizacion()+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td colspan='3'><img class='image_fix' src='cid:lineATT.jpg' alt=''></td>");
			htmlText.append("										</tr>");
			htmlText.append("									</table>");
			htmlText.append("								</center>");
			htmlText.append("							</td>");
			htmlText.append("						</tr>");
			htmlText.append("					</table>");
			htmlText.append("				</center>");
			htmlText.append("			</td>");
			htmlText.append("			<td width='23px' height='571'> <img class='image_fix' src='cid:rightATT.jpg' style='display:block;' class='image_fix'></td>");
			htmlText.append("		</tr>");
			htmlText.append("		<tr>");
			htmlText.append("			<td height='118' width='535' colspan='3'> <div style='width: 535px !important; min-width: 535px !important; height: 118px !important; overflow: hidden;'> <img class='image_fix' src='cid:footerATT.jpg' style='display:block;'> </div></td>");
			htmlText.append("		</tr>");
			htmlText.append("	</table>");


			htmlText.append("</center>");
						
			mailBody = htmlText.toString();
			mailBody= mailBody.replaceAll("'", "\\\"");
			mailBody= mailBody.replaceAll("<", "&lt;");
			mailBody= mailBody.replaceAll(">", "&gt;");
			
			cSS="@media only screen and (max-width: 480px) {"+
				".encabezado1{"+
				"display:none!important;"+
	    		"}"+
				".image_fix {"+
	            "height:auto !important;"+
	            "max-width:600px !important;"+
	            "width: 100% !important;"+
	    		"}"+
	   			
	    		"#backgroundTable {margin:0 auto; padding:0;  line-height: 100% !important; max-width:600px !important; width: 100% !important;}"+
				".tablaFactura1 {margin:0 auto; padding:0;  line-height: 100% !important;  width: 100% !important;}"+
				".tablaFactura2 {margin:0 auto; padding:0;  line-height: 100% !important;   width: 85% !important;}"+

				"#tablaConsumo {margin:0 auto; padding:0;  line-height: 100% !important; max-width:500px !important; width: 97% !important;}"+
				"#tablaCitas {margin:0 auto; padding:0;  line-height: 100% !important; max-width:400px !important; width: 99% !important;}"+

				"a[href^=&quot;tel&quot;], a[href^=&quot;sms&quot;] {"+
	                            "text-decoration: none;"+
	                            "color: blue; /* or whatever your want */"+
	                            "pointer-events: none;"+
	                            "cursor: default;"+
	                    "}"+

				".mobile_link a[href^=&quot;tel&quot;], .mobile_link a[href^=&quot;sms&quot;] {"+
	                            "text-decoration: default;"+
	                            "color: orange !important;"+
	                            "pointer-events: auto;"+
	                            "cursor: default;"+
	                    "}"+

				"}"+

				"@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) {"+
				"a[href^=&quot;tel&quot;], a[href^=&quot;sms&quot;] {"+
	                            "text-decoration: none;"+
	                            "color: blue; /*o el color que quieran */"+
	                            "pointer-events: none;"+
	                            "cursor: default;"+
	                    "}"+

				".mobile_link a[href^=&quot;tel&quot;], .mobile_link a[href^=&quot;sms&quot;] {"+
	                            "text-decoration: default;"+
	                            "color: orange !important;"+
	                            "pointer-events: auto;"+
	                            "cursor: default;"+
	                    "}"+
				"}";
			
			
			mail=facturaVirtual.getMail();
			
				sResponse = sendMessage.enviaCorreo(ip, compania + " - Notificación de Pago de Servicio", mailFrom, nombre, mailBody, mail, cSS,idServicio,tipoEvento,nameFrom);
				
				if(sResponse != null && !sResponse.equals(""))
					respuesta = ParseXMLFile.ParseMensajeCorreo(sResponse);

				
		}catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return respuesta;
	}
	
	public MensajeMailVO flujo_sendMailATT(final String tipoEvento, final int idServicio,final String url,final String mailFrom, final String nameFrom,final FacturaVirtualDetalleVO facturaVirtual,final String ip) throws ServiceException{
		CallServiceMandaMensaje_Mail sendMessage = new CallServiceMandaMensaje_Mail();
		
		MensajeMailVO respuesta = new MensajeMailVO();
		String sResponse = "";
		String mailBody = "";
		
		String compania="";
		compania = Utilerias.retornacompania(idServicio);
		
		StringBuilder htmlText = new StringBuilder();
		
		String rentas="";
		String serAdic="";
		String consumosPer="$0.00";
		String ajustes="$0.00";
		String subtotal="";
		String iva="";
		String totalFactura="";
		String nombre="";
		String mail="";
		String cSS="";
		String otrosCargos="";
	
			
		try{
			nombre=facturaVirtual.getNombre();
			if(nombre != null && !nombre.equals("")){
				nombre = Utilerias.formatoCadena(nombre).trim();
			}
			
    		rentas=facturaVirtual.getRentas();
			if(rentas.equals("")) {
					rentas = "0.00";
			}
			serAdic=facturaVirtual.getServiciosAdicionales();
			if(serAdic.equals("")) {
					serAdic = "0.00";
			}
			
			consumosPer=facturaVirtual.getConsumosPeriodo();
			if(consumosPer.equals("")) {
			    	consumosPer = "0.00";
			}
			
			subtotal=facturaVirtual.getSubtotal();
			if(subtotal.equals("")) {
					subtotal = "0.00";	
			}
			iva=facturaVirtual.getIva();
			if(iva.equals("")) {
				iva = "0.00";	
			}
			
			otrosCargos=facturaVirtual.getOtrosCargos();
			if(otrosCargos.equals("")) {
				otrosCargos = "0.00";	
			}
			
			totalFactura=facturaVirtual.getTotalPagar();
			if(totalFactura.equals("")) {
					totalFactura = "0.00";	
			}
			
			ajustes=facturaVirtual.getAjustes();
			if(ajustes.equals("")) {
					ajustes = "0.00";	
			}
			
			
			htmlText.append("<center>");
			
			htmlText.append("	<table width='535px' valign='top' align='top' border='0' cellpadding='0' cellspacing='0' style='margin:auto; text-aling:left; font-family: Arial; table-layout:fixed; min-width: 535px !important;'>");
			htmlText.append("		<colgroup>");
			htmlText.append("			<col style='width:24px;'></col>");
			htmlText.append("			<col style='width:488px;'></col>");
			htmlText.append("			<col style='width:23px;'></col>");
			htmlText.append("		</colgroup>");
			htmlText.append("		<tr>");
			htmlText.append("			<td height='165' width='535' colspan='3'> <div style='width: 535px !important; min-width: 535px !important; height: 165px !important; overflow: hidden;'> <img class='image_fix' src='cid:headerATT.jpg' style='display:block;'> </div> </td>  ");
			htmlText.append("		</tr>");
			htmlText.append("		<tr>");
			htmlText.append("			<td width='24px' height='571'> <img class='image_fix' src='cid:leftATT.jpg' style='display:block;'></td>");
			htmlText.append("			<td width='488px' height='571' align='top' style='min-width: 488px !important;'>");
			htmlText.append("				<center>");
			htmlText.append("					<table width='420' height='571' align='top' border='0' cellpadding='0' cellspacing='0' style='margin:auto; text-aling:left; font-size:13px; font-family: Arial, sans-serif; table-layout:fixed; min-width: 420px !important;'>");
			htmlText.append("						<tr><td height='15' colspan='2'></td></tr>");
			htmlText.append("						<tr>");
			htmlText.append("							<td colspan='2' height='25' style='color:#ef6f00; font-size:17px; text-align: left !important;'>Hola,</td>");
			htmlText.append("						</tr>");
			htmlText.append("						<tr>");
			htmlText.append("							<td colspan='2' height='25' style='color:#bbbbbb; font-size:17px; text-align: left !important;'>"+nombre+"</td>");
			htmlText.append("						</tr>");
			htmlText.append("						<tr>");
			htmlText.append("							<td height='45' style='color:#ef6f00; font-size:13px; text-align: left !important;'>Detalle de Factura</td>");
			htmlText.append("							<td style='color:#ef6f00; font-size:13px; text-align: right !important;'>Periódo: <span style='color: #bbbbbb;'>"+facturaVirtual.getPeriodoXMes()+"</span></td>");
			htmlText.append("						</tr>");
			htmlText.append("						<tr>");
			htmlText.append("							<td colspan='2' height='20' align='center'><img class='image_fix' src='cid:lineATT.jpg' alt=''></td>");
			htmlText.append("						</tr>");
			htmlText.append("						<tr>");
			htmlText.append("							<td valign='top' colspan='2'>");
			htmlText.append("								<center>");
			htmlText.append("									<table width='350px' align='top' border='0' cellpadding='0' cellspacing='0' style='margin:auto; min-width:350px !important; text-aling:left; font-family: Arial, sans-serif; table-layout:fixed; color:#bbbbbb;'>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='40' colspan='2' style='font-size:15px; color:#666666;'>Resumen de factura</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='30' style='text-align: left !important;'>Rentas</td>");
			htmlText.append("											<td style='text-align: right !important;'>$"+rentas+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='30' style='text-align: left !important;'>Sevicios adicionales</td>");
			htmlText.append("											<td style='text-align: right !important;'>$"+serAdic+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='30' style='text-align: left !important;'>Consumos del periódo</td>");
			htmlText.append("											<td style='text-align: right !important;'>$"+consumosPer+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='30' style='text-align: left !important;'>Ajustes</td>");
			htmlText.append("											<td style='text-align: right !important;'>$"+ajustes+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='20' colspan='2'><img class='image_fix' src='cid:lineATT.jpg' alt=''></td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='35' style='color: #999; text-align: left !important;'>Subtotal</td>");
			htmlText.append("											<td style='text-align: right !important; color: #999;'>$"+subtotal+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='35' style='color: #999; text-align: left !important; '>IVA("+facturaVirtual.getPorcentajeIVA()+"%)</td>");
			htmlText.append("											<td style='text-align: right !important; color: #999;'>$"+iva+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='35' style='color: #999; text-align: left !important;'>Otros</td>");
			htmlText.append("											<td style='text-align: right !important; color: #999;'>$"+otrosCargos+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td colspan='2'><img class='image_fix' src='cid:lineATT.jpg' alt=''></td>");
			htmlText.append("										</tr>");
			htmlText.append("										<tr>");
			htmlText.append("											<td height='35' style='color: #666; font-size:15px; text-align: left !important;'>Total a pagar</td>");
			htmlText.append("											<td style='text-align: right !important; color: #666; font-size:15px;'>$"+totalFactura+"</td>");
			htmlText.append("										</tr>");
			htmlText.append("									</table>");
			htmlText.append("								</center>");
			htmlText.append("							</td>");
			htmlText.append("						</tr>");
			htmlText.append("					</table>");
			htmlText.append("				</center>");
			htmlText.append("			</td>");
			htmlText.append("			<td width='23px' height='571'> <img class='image_fix' src='cid:rightATT.jpg' style='display:block;' class='image_fix'></td>");
			htmlText.append("		</tr>");
			htmlText.append("		<tr>");
			htmlText.append("			<td height='118' width='535' colspan='3'> <div style='width: 535px !important; min-width: 535px !important; height: 118px !important; overflow: hidden;'> <img class='image_fix' src='cid:footerATT.jpg' style='display:block;'> </div></td>");
			htmlText.append("		</tr>");
			htmlText.append("	</table>");


			htmlText.append("</center>");

			
			
			mailBody = htmlText.toString();
			mailBody= mailBody.replaceAll("'", "\\\"");
			mailBody= mailBody.replaceAll("<", "&lt;");
			mailBody= mailBody.replaceAll(">", "&gt;");
		
			mail=facturaVirtual.getMail();
			
			cSS="@media only screen and (max-width: 480px) {"+

				".encabezado1{"+
				"display:none!important;"+
        		"}"+
				".image_fix {"+
                "display: block;"+
        		"}"+

        		"#backgroundTable {margin:0 auto; padding:0;  line-height: 100% !important; max-width:600px!important; width: 100% !important;}"+
				".tablaFactura1 {margin:0 auto; padding:0;  line-height: 100% !important;  width: 100%!important;}"+
				".tablaFactura1 tr td a{ width:60% !important; margin: auto;}"+
				"table tr td {border-spacing: 0px !important;}"+
				".tablaFactura2 {margin:0 auto; padding:0;  line-height: 100% !important;   width: 85%!important;}"+
				".nombreOwn{font-size: 18px!important;}"+
				".nombreOwn2{font-size: 14px!important;}"+

				"table tr td a{"+
				"border:0px !important;"+
				"text-decoration: none !important;"+
				"color: white !important;"+
				"padding: 0px !important;"+
				"margin: 0px !important;"+
				"}"+

				"#tablaConsumo {margin:0 auto; padding:0;  line-height: 100% !important; max-width:500px !important; width: 97% !important;}"+
				"#tablaCitas {margin:0 auto; padding:0;  line-height: 100% !important; max-width:400px !important; width: 99% !important;}"+
				"a[href^=&quot;tel&quot;], a[href^=&quot;sms&quot;] {"+
                                "text-decoration: none;"+
                                "color: white; /* or whatever your want */"+
                                "pointer-events: none;"+
                                "cursor: default;"+
                        "}"+

				".mobile_link a[href^=&quot;tel&quot;], .mobile_link a[href^=&quot;sms&quot;] {"+
                                "text-decoration: default;"+
                                "pointer-events: auto;"+
                                "cursor: default;"+
                        "}"+
				".descarga{font-size: 14px !important;}"+

				"}";
			
			   sResponse = sendMessage.enviaCorreo(ip, compania + " - Detalle de facturación",mailFrom, nombre, mailBody, mail, cSS,idServicio,tipoEvento, nameFrom);
			   
			   if(sResponse != null && !sResponse.equals(""))
					respuesta = ParseXMLFile.ParseMensajeCorreo(sResponse);
						
		}catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return respuesta;
	}
	
	
	public MensajeMailVO flujoATTSMS(final String dn, final String tipoEvento, final String mail, String nombre, final int idServicio, final String ip, final String mailFrom, final String nameFrom) throws ServiceException{
		CallServiceMandaMensaje_Mail sendMessage = new CallServiceMandaMensaje_Mail();
		
		MensajeMailVO respuesta = new MensajeMailVO();
		String sResponse = "";
		String mailBody = "";
		
		String compania="";
		compania = Utilerias.retornacompania(idServicio);
		
		StringBuilder htmlText = new StringBuilder();
		String tipoDetalle = "";

		
		
		DetalleConsumosTabla consumos = new DetalleConsumosTabla();
		ConsumosTablaVO respuestaConsumo = new ConsumosTablaVO();
		try{
			if(nombre != null && !nombre.equals("")){
				nombre = Utilerias.formatoCadena(nombre).trim();
			}
			
			respuestaConsumo = consumos.flujo(dn, tipoEvento, true);
			
			htmlText.append("<center>");
					
			htmlText.append("	<table width='535px' valign='top' align='top' border='0' cellpadding='0' cellspacing='0' style='margin:auto; text-aling:left; font-family: Arial; table-layout:fixed;  min-width: 535px !important;'>");
			htmlText.append("		<colgroup>");
			htmlText.append("			<col style='width:24px;'></col>");
			htmlText.append("			<col style='width:488px;'></col>");
			htmlText.append("			<col style='width:23px;'></col>");
			htmlText.append("		</colgroup>");
			htmlText.append("		<tr>");
			htmlText.append("			<td height='165' width='535' colspan='3'> <div style='width: 535px !important;  min-width: 535px !important; height: 165px !important; overflow: hidden;'> <img class='image_fix' src='cid:headerATT.jpg' style='display:block;'> </div> </td>  ");
			htmlText.append("		</tr>");
			htmlText.append("		<tr>");
			htmlText.append("			<td width='24px' height='571'> <img class='image_fix' src='cid:leftATT.jpg' style='display:block;'></td>");
			htmlText.append("			<td width='488px' height='571' align='top' style=' min-width: 488px !important;'>");
			
			
			if(tipoEvento.equals("1") || tipoEvento.equals("5") || tipoEvento.equals("6")){
					  htmlText.append(construyeMailCabeceroATT(dn, respuestaConsumo, "1", idServicio,nombre));	
				
			}else if(tipoEvento.equals("2")){
					  htmlText.append(construyeMailCabeceroATT(dn, respuestaConsumo, "2", idServicio,nombre));	
					
			}else if(tipoEvento.equals("4")){
					  htmlText.append(construyeMailCabeceroATT(dn, respuestaConsumo, "4", idServicio,nombre));	
			}
			
			htmlText.append("			</td>");
			htmlText.append("			<td width='23px' height='571'> <img class='image_fix' src='cid:rightATT.jpg' style='display:block;'></td>");
			htmlText.append("		</tr>");
			htmlText.append("		<tr>");
			htmlText.append("			<td height='118' width='535' colspan='3'> <div style='width: 535px !important;  min-width: 535px !important; height: 118px !important; overflow: hidden;'> <img class='image_fix' src=\'cid:footerATT.jpg' style='display:block;'> </div></td>");
			htmlText.append("		</tr>");
			htmlText.append("	</table>");


			htmlText.append("</center>");
			
						
			mailBody = htmlText.toString();
			mailBody= mailBody.replaceAll("'", "\\\"");
			mailBody= mailBody.replaceAll("<", "&lt;");
			mailBody= mailBody.replaceAll(">", "&gt;");
			
			if(tipoEvento.equals("1"))
				tipoDetalle = "Llamadas";
			else if(tipoEvento.equals("2"))
				tipoDetalle = "Mensajes";
			else if(tipoEvento.equals("4"))
				tipoDetalle = "Navegación";
			else if(tipoEvento.equals("5"))
				tipoDetalle = "Llamadas Comunidad";
			else if(tipoEvento.equals("6"))
				tipoDetalle = "Llamadas Otras Compañias";
			
				   sResponse = sendMessage.enviaCorreo(ip, compania + " - Detalle de "+ tipoDetalle, mailFrom, nombre, mailBody, mail, "",idServicio,"", nameFrom);
			   
			   if(sResponse != null && !sResponse.equals(""))
					respuesta = ParseXMLFile.ParseMensajeCorreo(sResponse);
			
		}catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return respuesta;
	}

@SuppressWarnings("deprecation")
public StringBuilder construyeMailCabeceroATT(final String dn, final ConsumosTablaVO consumoResponse, final String tipoEvento, final int idServicio, final String nombre) throws ServiceException{
		
		String tipoDetalle = "";
		Date periodo = new Date();
		String fechaPeriodo="";
		StringBuilder htmlText = new StringBuilder();
		String[] tipos = null;
		String[] tiposWidth = null;
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		String colsPan="6";
		
		String year = new SimpleDateFormat("yyyy").format(currentDate);
		
		String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		
		try{
		if(tipoEvento.equals("1")){
			tipos = new String[]{"Número","Fecha","Duración","Hora","Compañia","Destino"};
			tiposWidth= new String[]{"80","60","60","60","60","100"};
			tipoDetalle = "Llamadas";
		}
		else if(tipoEvento.equals("2")){
			tipos = new String[]{"Número","Fecha","Hora","Compañia","Destino"};
			tiposWidth= new String[]{"80","60","60","60","100"};
			tipoDetalle = "Mensajes";
			colsPan="5";
		}
		else if(tipoEvento.equals("3")){
			tipos = new String[]{"Número Origen","Fecha","Hora","Monto Free","Monto Real","Numero Destino","Operador Origen","Operador Destino"};
			tipoDetalle = "";
			tiposWidth= new String[]{"80","60","60","60","60","100","100","100"};
		}
		else if(tipoEvento.equals("4")){
			tipos = new String[]{"Fecha","MB Consumidos"};
			tiposWidth= new String[]{"210","210"};
			tipoDetalle = "Navegación";
			colsPan="2";
		}
		
		try{
			periodo = new SimpleDateFormat("dd MMMM").parse(consumoResponse.getDetalleConsumo().get(0).getFecha().replace("de ", "").replace("/", " "));
			if((periodo.getMonth()) >= 0 && (periodo.getMonth()) <= 11)
				fechaPeriodo=(meses[periodo.getMonth()]) + " " + year;
			else
				fechaPeriodo=(new SimpleDateFormat("MMMM").format(periodo)) + " " + year;
		}catch (ParseException e) {
			fechaPeriodo=meses[calendar.get(Calendar.MONTH)] + " " + year;
		}catch (Exception e) {
			fechaPeriodo=meses[calendar.get(Calendar.MONTH)] + " " + year;
		}
		
		
		htmlText.append("				<center>");
		htmlText.append("					<table width='420' height='571' align='top' border='0' cellpadding='0' cellspacing='0' style='margin:auto; text-aling:left;  min-width: 420px !important; font-size:13px; font-family: Arial, sans-serif; table-layout:fixed;'>");
		htmlText.append("						<tr><td height='15' colspan='2'></td></tr>");
		htmlText.append("						<tr>");
		htmlText.append("							<td valign='left' colspan='2' height='25' style='color:#ef6f00; font-size:17px; text-align: left !important;'>Hola,</td>");
		htmlText.append("						</tr>");
		htmlText.append("						<tr>");
		htmlText.append("							<td valign='left' colspan='2' height='25' style='color:#bbbbbb; font-size:17px; text-align: left !important;'>"+nombre+"</td>");
		htmlText.append("						</tr>");
		htmlText.append("						<tr>");
		htmlText.append("							<td height='45' style='color:#ef6f00; font-size:13px; text-align: left !important;'>"+tipoDetalle+"</td>");
		htmlText.append("							<td style='color:#ef6f00; font-size:13px; text-align: right !important;'>Periódo: <span style='color: #bbbbbb;'>"+fechaPeriodo+"</span></td>");
		htmlText.append("						</tr>");
		htmlText.append("						<tr>");
		htmlText.append("							<td colspan='2' height='20' align='center'><img class='image_fix' src='cid:lineATT.jpg' alt=''></td>");
		htmlText.append("						</tr>");
		htmlText.append("						<tr>");
		htmlText.append("							<td valign='top' colspan='2'>");
		htmlText.append("								<center>");
		htmlText.append("									<div style='height: 400px !important; overflow:auto !important;'>");
		htmlText.append("										<table width='420' align='top' border='0' cellpadding='0' cellspacing='0' style=' min-width: 420px !important; margin:auto; text-aling:left; font-size:11px; font-family: Arial, sans-serif; table-layout:fixed; color: #666;'>");
		htmlText.append("											<tr>");
			
		if(consumoResponse != null){
			if(consumoResponse.getDetalleConsumo() != null && consumoResponse.getDetalleConsumo().size() > 0){
				if(consumoResponse.getDetalleConsumo().get(0) != null && consumoResponse.getDetalleConsumo().get(0).getDetalle() != null && consumoResponse.getDetalleConsumo().get(0).getDetalle().size() > 0){
					
					try{
						if(tipos != null){
							if(tipos[0] != null){
								htmlText.append("							<td width='"+tiposWidth[0]+"' height='30' align='center' style='color:#333; font-size:13px; max-width:"+tiposWidth[0]+"px !important;'>"+tipos[0]+"</td>");
							}
							if(tipos[1] != null){
								htmlText.append("							<td width='"+tiposWidth[1]+"' height='30' align='center' style='color:#333; font-size:13px; max-width:"+tiposWidth[1]+"px !important;'>"+tipos[1]+"</td>");
							}
							if(tipos[2] != null){
								htmlText.append("							<td width='"+tiposWidth[2]+"' height='30' align='center' style='color:#333; font-size:13px; max-width:"+tiposWidth[2]+"px !important;'>"+tipos[2]+"</td>");
							}
							if(tipos[3] != null){
								htmlText.append("							<td width='"+tiposWidth[3]+"' height='30' align='center' style='color:#333; font-size:13px; max-width:"+tiposWidth[3]+"px !important;'>"+tipos[3]+"</td>");
							}
							if(tipos[4] != null){
								htmlText.append("							<td width='"+tiposWidth[4]+"' height='30' align='center' style='color:#333; font-size:13px; max-width:"+tiposWidth[4]+"px !important;'>"+tipos[4]+"</td>");
							}
							if(tipos[5] != null){
								htmlText.append("							<td width='"+tiposWidth[5]+"' height='30' align='center' style='color:#333; font-size:13px; max-width:"+tiposWidth[5]+"px !important;'>"+tipos[5]+"</td>");
							}
							if(tipos[6] != null){
								htmlText.append("							<td width='"+tiposWidth[6]+"' height='30' align='center' style='color:#333; font-size:13px; max-width:"+tiposWidth[6]+"px !important;'>"+tipos[6]+"</td>");
							}
							if(tipos[7] != null){
								htmlText.append("							<td width='"+tiposWidth[7]+"' height='30' align='center' style='color:#333; font-size:13px; max-width:"+tiposWidth[7]+"px !important;'>"+tipos[7]+"</td>");
							}
						}
					}catch (Exception e) {
					}

				htmlText.append("									</tr>");
				htmlText.append("									<tr>");
				htmlText.append("										<td colspan='"+colsPan+"' height='8' align='center'><img class='image_fix' src='cid:lineATT.jpg' alt=''></td>");
				htmlText.append("									</tr>");


					for(int i=0; i<consumoResponse.getDetalleConsumo().size(); i++){
						htmlText.append("							<tr>");
						for(int y=0; y<consumoResponse.getDetalleConsumo().get(i).getDetalle().size(); y++){
							if(tipoEvento.equals("1")){
								htmlText.append("							<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getNumero()+"</td>");
								htmlText.append("							<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getFecha()+"</td>");
								htmlText.append("							<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getTiempo()+"</td>");
								htmlText.append("							<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getHora()+"</td>");
								htmlText.append("							<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getCompania()+"</td>");
								htmlText.append("							<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getDestino()+"</td>");
																	
							}else if(tipoEvento.equals("2")){
								htmlText.append("							<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getNumero()+"</td>");
								htmlText.append("							<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getFecha()+"</td>");
								htmlText.append("							<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getHora()+"</td>");
								htmlText.append("							<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getCompania()+"</td>");
								htmlText.append("							<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getDestino()+"</td>");
									
							}else if(tipoEvento.equals("4")){
								double saldoPintar = 0;
								try{
									saldoPintar = Double.parseDouble(consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getMbConsumidos());
								}catch (NumberFormatException e) {
									saldoPintar = 0;
								}
								if(saldoPintar > 0.00){
									htmlText.append("						<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getFecha()+"</td>");
									htmlText.append("						<td height='30' align='center'>"+consumoResponse.getDetalleConsumo().get(i).getDetalle().get(y).getMbConsumidos()+"</td>");
								}
							}
						 }
						htmlText.append("							</tr>");
						htmlText.append("							<tr>");
						htmlText.append("									<td colspan='"+colsPan+"' height='8' align='center'><img class='image_fix' src='cid:lineATT.jpg' alt=''></td>");
						htmlText.append("							</tr>");
					}
				
				}
			}
			
		}
		
		htmlText.append("										</table>");
		htmlText.append("									</div>");
		htmlText.append("								</center>");
		htmlText.append("							</td>");
		htmlText.append("						</tr>");
		htmlText.append("					</table>");
		htmlText.append("				</center>");

		}catch (Exception e) {
			throw new ServiceException("     E R R O R  : (Exception) " + e.getLocalizedMessage());
		}
		
		return htmlText;
	}

    public RespuestaMensajeBean utilEnviarCorreoDelegate(final MessageMailBean datos, final String ip){
        CallServiceMandaMensaje_Mail call = new CallServiceMandaMensaje_Mail();
        RespuestaMensajeBean respuesta = new RespuestaMensajeBean();
        RespuestaMensajeVO response = null;
        
        MessageMailVO messageMail = new MessageMailVO();
        
        if(datos != null){
            if(datos.getEstilos() != null){     messageMail.setEstilos(datos.getEstilos());         }
            if(datos.getMailBody() != null){    messageMail.setMailBody(datos.getMailBody());       }
            if(datos.getMailSubject() != null){ messageMail.setMailSubject(datos.getMailSubject()); }
            
//          private EmailBean mailFrom;
            if(datos.getMailFrom() != null){
                messageMail.setMailFrom(new EmailVO(datos.getMailFrom().getDireccion(), 
                        datos.getMailFrom().getNombre()));
            }
            
//          private EmailBean[] mailCc;
            if(datos.getMailCc() != null && datos.getMailCc().length > 0){
                int tamanioArray = datos.getMailCc().length;
                EmailVO[] mailCc = new EmailVO[tamanioArray];
                for(int indice= 0; indice < tamanioArray; indice++){
                    mailCc[indice] = new EmailVO(datos.getMailCc()[indice].getDireccion(), 
                            datos.getMailCc()[indice].getNombre());
                }
                messageMail.setMailCc(mailCc);
            }
            
//          private EmailBean[] mailCco;
            if(datos.getMailCco() != null && datos.getMailCco().length > 0){
                int tamanioArray = datos.getMailCco().length;
                EmailVO[] mailCco = new EmailVO[tamanioArray];
                for(int indice= 0; indice < tamanioArray; indice++){
                    mailCco[indice] = new EmailVO(datos.getMailCco()[indice].getDireccion(), 
                            datos.getMailCco()[indice].getNombre());
                }
                messageMail.setMailCco(mailCco);
            }
            
//          private EmailBean[] mailTo;
            if(datos.getMailTo() != null && datos.getMailTo().length > 0){
                int tamanioArray = datos.getMailTo().length;
                EmailVO[] mailTo = new EmailVO[tamanioArray];
                for(int indice= 0; indice < tamanioArray; indice++){
                    mailTo[indice] = new EmailVO(datos.getMailTo()[indice].getDireccion(), 
                            datos.getMailTo()[indice].getNombre());
                }
                messageMail.setMailTo(mailTo);
            }
            
//          private EmailBean[] replyTo;
            if(datos.getReplyTo() != null && datos.getReplyTo().length > 0){
                int tamanioArray = datos.getReplyTo().length;
                EmailVO[] replyTo = new EmailVO[tamanioArray];
                for(int indice= 0; indice < tamanioArray; indice++){
                    replyTo[indice] = new EmailVO(datos.getReplyTo()[indice].getDireccion(), 
                            datos.getReplyTo()[indice].getNombre());
                }
                messageMail.setReplyTo(replyTo);
            }
            
            
            if(datos.getArchivosAdjuntos() != null && datos.getArchivosAdjuntos().length > 0)
            {
                int tamanioArray = datos.getArchivosAdjuntos().length;
                ArchivoAdjunto[] adjuntos = new ArchivoAdjunto[tamanioArray];
                
                for(int indice = 0; indice < tamanioArray; indice++){
                    adjuntos[indice] = new ArchivoAdjunto(
                            datos.getArchivosAdjuntos()[indice].getArchivoBase64(),
                            datos.getArchivosAdjuntos()[indice].getExtension(),
                            datos.getArchivosAdjuntos()[indice].getMimeType(),
                            datos.getArchivosAdjuntos()[indice].getNombre());
                }
                messageMail.setArchivosAdjuntos(adjuntos);
            }
            
            if(datos.getImagenes() != null && datos.getImagenes().length > 0){
                int tamanioArray = datos.getImagenes().length;
                Imagen64VO[] imagenes = new Imagen64VO[tamanioArray];
                
                for(int indice = 0; indice < tamanioArray; indice++){
                    imagenes[indice] = new Imagen64VO(
                            datos.getImagenes()[indice].getImagenBase64(),
                            datos.getImagenes()[indice].getNombre());
                }
                messageMail.setImagenes(imagenes);
            }
        }
        
        try{
            response = call.enviaCorreo(messageMail, ip);
            respuesta.setEnviado(response.getEnviado()); 
            respuesta.setMensaje(response.getMensaje());
        }catch (Exception e) {
            respuesta.setEnviado(false);
            respuesta.setMensaje(e.getMessage());
            Logger.write("utilEnviarCorreoDelegate (Exception) :: " + e.getMessage());
        }finally{
            call        = null;
            response    = null;
            messageMail = null;
        }
        
        return respuesta;
    }
	
}
