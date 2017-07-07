package mx.com.iusacell.services.miusacell.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.vo.DetalleSaldoParserVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleSaldoVO;
import mx.com.iusacell.services.miiusacell.vo.EstadoCuentaVO;
import mx.com.iusacell.services.miiusacell.vo.FacturaVO;

public abstract class ParseHtmlFile {

	public static EstadoCuentaVO parseFactura(final String sResponse) throws Exception{
		Pattern pattern = null;
		Matcher matcher = null;
		EstadoCuentaVO estadoCuenta = new EstadoCuentaVO();

		FacturaVO servicios = new FacturaVO();
		List<FacturaVO> rentasList = new ArrayList<FacturaVO>();
		List<FacturaVO> serviciosList = new ArrayList<FacturaVO>();
		List<FacturaVO> serviciosSinList = new ArrayList<FacturaVO>();
		List<FacturaVO> consumosPeriodoList = new ArrayList<FacturaVO>();
		List<FacturaVO> otrosCargosList = new ArrayList<FacturaVO>();
		List<FacturaVO> ajustesList = new ArrayList<FacturaVO>();
		List<FacturaVO> saldosList = new ArrayList<FacturaVO>();
		String htmlRentas = "";

		try {
			byte ptext[] = sResponse.getBytes();
			String xmlResponse = new String(ptext, "UTF-8");

			//Cuenta
			pattern = Pattern.compile("<span>NO. DE CUENTA:</span></td><td class=\"espacioBlanco2\" width=\"60%\">(.+?)</td></tr>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				estadoCuenta.setNoCuenta(matcher.group(1));
			}
			
			//Factura
			pattern = Pattern.compile("<span>NO. FACTURA:</span></td><td class=\"espacioBlanco2\" width=\"60%\">(.+?)</td></tr>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				estadoCuenta.setNoFactura(matcher.group(1));
			}
			
			//Cuenta
			pattern = Pattern.compile("<span>NO. DE CUENTA:</span></td><td class=\"espacioBlanco2\" width=\"60%\">(.+?)</td></tr>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				estadoCuenta.setNoCuenta(matcher.group(1));
			}
			
			//Periodo
			pattern = Pattern.compile("RESUMEN DE CARGOS DE PER(.+?)<b>(.+?)</b>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
			 String pasa = matcher.group(2).replace("(", "");
			 String periodo = pasa.replace(")", "");
			 estadoCuenta.setPeriodo(periodo);
			}
			
			//ajuste redondeo
			pattern = Pattern.compile("SwitchMenu\\('AjustesCta'\\);changeIcon\\('AjustesCta',1\\);\"><table border=\"0\" cellPadding=\"1\" cellSpacing=\"1\" width=\"100%\"><tbody><tr><td class=\"Header1Regular\" width=\"75%\"><span>(.+?)</span>(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = matcher.group(3);
				estadoCuenta.setAjusteRedondeo(htmlRentas);
			}
			
			//Subtotal
			pattern = Pattern.compile("<span>= Subtot(.+?)</span></td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = matcher.group(2);
				estadoCuenta.setSubtotal(htmlRentas);
			}
			
			//total Factura actual
			pattern = Pattern.compile("<span>= Total de esta Fact(.+?)</span></td><td class=\"TotalHeader1RegularPlus1\" width=\"20%\"><font size=\"3\">(.+?)</font>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = matcher.group(2);
				estadoCuenta.setTotalFacturaActual(htmlRentas);
			}
			
			//otros cargos
			pattern = Pattern.compile("<span>Otros cargos po(.+?)</span></td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = matcher.group(2);
				estadoCuenta.setOtroCargo(htmlRentas);
			}else estadoCuenta.setOtroCargo("0.00");
			
			//Bonificaciones
			pattern = Pattern.compile("<span>Bonifica(.+?)</span></td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				String bonificacion = matcher.group(2);
				bonificacion = bonificacion.trim().replace("$", "").replace(",", "");
				bonificacion = bonificacion.trim().replace("-", "");
				bonificacion = eliminaCaracteresEspeciales(bonificacion);
				
                double bonifica = Double.parseDouble(bonificacion);
				String cargos =estadoCuenta.getOtroCargo();
				cargos = cargos.trim().replace("$", "").replace(",", "");
				cargos = cargos.trim().replace("-", "");
				cargos = eliminaCaracteresEspeciales(cargos);
				double otroCargo = Double.parseDouble(cargos);
				double operacion = (otroCargo - bonifica);
				estadoCuenta.setOtroCargo(String.valueOf(operacion));
			}
			
			
			
			//porcentaje impuesto al valor agregado
			pattern = Pattern.compile("Impuesto al Valor Agregado IVA(.+?)</span>(.+?)<span>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = matcher.group(2);
				estadoCuenta.setPorcentajeIVA(htmlRentas);
			}
			
			//Rentas
			pattern = Pattern.compile("<span>+(.+?)Renta(.+?)</span></td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = matcher.group(3);
				estadoCuenta.setRentasParser(htmlRentas);
			}else estadoCuenta.setRentasParser("0.00");
			
			
			//Saldos
			pattern = Pattern.compile("<div id=\"masterdiv\">([\\s\\S]*?)<div style=\"width:100%; \" onclick=\"SwitchMenu\\('RentasCargosFijos'\\);changeIcon\\('RentasCargosFijos',1\\);\">");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = matcher.group(1);
				if(htmlRentas!= null && !htmlRentas.equals("")){
					pattern = Pattern.compile("<td class=\"Header1Regular\" width=\"75%\"><span>(.+?)</td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						saldosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"75%\" colspan=\"1\"><span>(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						saldosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"70%\">(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						saldosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\"><span>(.+?)</span></td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						saldosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\" colspan=\"1\">(.+?)</td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						saldosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularPlus1\" width=\"70%\">(.+?)</td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						saldosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularPlus1\" width=\"70%\" colspan=\"1\">(.+?)</td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						saldosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularPlus1\" width=\"75%\"><span>TOTAL(.+?)</span></td><td class=\"Header1RegularPlus1\" width=\"20%\">(.+?)<input", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo("TOTAL " + matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						saldosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularPlus1\" width=\"75%\"><span>PAGUE(.+?)</td><td class=\"Header1RegularMinus1\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo("PAGUE " + matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						saldosList.add(servicios);
					}
					pattern = Pattern.compile("color=\"#333333\" size=\"3pt\"><b>(.+?)</b>    </font><font face=\"Tahoma\" color=\"#333333\" size=\"2\"><b>(.+?)</b></font>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						saldosList.add(servicios);
					}
				}
			}

			//Rentas
			pattern = Pattern.compile("<div style=\"width:100%; \" onclick=\"SwitchMenu\\('RentasCargosFijos'\\);changeIcon\\('RentasCargosFijos',1\\);\">([\\s\\S]*?)</SPAN></SPAN>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = matcher.group(1);
				if(htmlRentas!= null && !htmlRentas.equals("")){
					pattern = Pattern.compile("<td class=\"Header1Regular\" width=\"75%\"><span>(.+?)</td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						rentasList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"75%\" colspan=\"1\"><span>(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						rentasList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"70%\">(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						rentasList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\"><span>(.+?)</span></td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						rentasList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\" colspan=\"1\">(.+?)</td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						rentasList.add(servicios);
					}
				}
			}

			//Servicios Adicionales Contratados
			//ServiciosConPromocion
			pattern = Pattern.compile("<div style=\"width:100%; \" onclick=\"SwitchMenu\\('ServiciosAdicionalesContratados'\\);changeIcon\\('ServiciosAdicionalesContratados',1\\);\">([\\s\\S]*?)</SPAN></SPAN></SPAN>",Pattern.MULTILINE);
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = matcher.group(1);
				if(htmlRentas!= null && !htmlRentas.equals("")){
					pattern = Pattern.compile("<td class=\"Header1Regular\" width=\"75%\"><span>(.+?)</td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>|\r|\n", "").trim());
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|\r|\n", "").trim());
						serviciosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"75%\" colspan=\"1\"><span>(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>|\r|\n", "").trim());
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|\r|\n", "").trim());
						serviciosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"75%\">(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>|\r|\n", "").trim());
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|\r|\n", "").trim());
						serviciosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\"><span>(.+?)</span></td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>|\r|\n", "").trim());
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|\r|\n", "").trim());
						serviciosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\" colspan=\"1\">(.+?)</td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>|\r|\n", "").trim());
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|\r|\n", "").trim());
						serviciosList.add(servicios);
					}
				}
			}

			//ServiciosSinPromocion
			pattern = Pattern.compile("<div style=\"width:100%; \" onclick=\"SwitchMenu\\('ServiciosSinPromocion'\\);changeIcon\\('ServiciosSinPromocion',2\\);\">([\\s\\S]*?)</SPAN></SPAN></SPAN>",Pattern.MULTILINE);
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = matcher.group(1);
				if(htmlRentas!= null && !htmlRentas.equals("")){
					pattern = Pattern.compile("<td class=\"Header1Regular\" width=\"75%\"><span>(.+?)</td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>|\r|\n", "").trim());
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|\r|\n", "").trim());
						serviciosSinList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"75%\" colspan=\"1\"><span>(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>|\r|\n", "").trim());
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|\r|\n", "").trim());
						serviciosSinList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"75%\">(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>|\r|\n", "").trim());
						if(matcher.group(2) != null){
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|\r|\n", "").trim());
						}
						serviciosSinList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\"><span>(.+?)</span></td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>|\r|\n", "").trim());
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|\r|\n", "").trim());
						serviciosSinList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\" colspan=\"1\">(.+?)</td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>|\r|\n", "").trim());
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|\r|\n", "").trim());
						serviciosSinList.add(servicios);
					}
				}
			}

			//Consumos del Periodo
			pattern = Pattern.compile("<div style=\"width:100%; \" onclick=\"SwitchMenu\\('ServiciosAdicionales'\\);changeIcon\\('ServiciosAdicionales',1\\);\">([\\s\\S]*?)</SPAN>",Pattern.MULTILINE);
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = matcher.group(1);
				if(htmlRentas!= null && !htmlRentas.equals("")){
					pattern = Pattern.compile("<td class=\"Header1Regular\" width=\"75%\"><span>(.+?)</td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						consumosPeriodoList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"75%\" colspan=\"1\"><span>(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						consumosPeriodoList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"75%\">(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null){
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
							if(servicios.getCosto().contains("<a")){
								servicios.setCosto(servicios.getCosto().substring(0,servicios.getCosto().indexOf("<a")));
							}
						}
						consumosPeriodoList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\"><span>(.+?)</span></td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						consumosPeriodoList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\" colspan=\"1\">(.+?)</td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						consumosPeriodoList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleCargos\" width=\"70%\">(.+?)</td><td class=\"TotalDetalleCargos\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>|\r|\n|", "").trim());
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|\r|\n| ", "").trim());
						consumosPeriodoList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleCargos\" width=\"70%\" colspan=\"1\">(.+?)</td><td class=\"TotalDetalleCargos\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>|\r|\n|", "").trim());
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|\r|\n| ", "").trim());
						consumosPeriodoList.add(servicios);
					}
				}
			}

			//Otros Cargos
			pattern = Pattern.compile("<SPAN class=\"submenu\" id=\"OtrosCargosPor\">([\\s\\S]*?)<SPAN class=\"submenu\" id=\"AjustesCta\">",Pattern.MULTILINE);
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = matcher.group(1);
				if(htmlRentas!= null && !htmlRentas.equals("")){
					pattern = Pattern.compile("<td class=\"Header1Regular\" width=\"75%\"><span>(.+?)</td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						otrosCargosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"75%\" colspan=\"1\"><span>(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						otrosCargosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"75%\">(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						otrosCargosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\"><span>(.+?)</span></td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						otrosCargosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\" colspan=\"1\">(.+?)</td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						otrosCargosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetallePagos\" width=\"70%\" colspan=\"1\">(.+?)</td><td class=\"TotalDetallePagos\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						otrosCargosList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetallePagos\" width=\"70%\">CARGO(.+?)</td><td class=\"TotalDetallePagos\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo("CARGO " + matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						otrosCargosList.add(servicios);
					}
				}
			}

			//Ajustes
			pattern = Pattern.compile("<SPAN class=\"submenu\" id=\"AjustesCta\">(.+?)</span>", Pattern.DOTALL | Pattern.MULTILINE);
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				htmlRentas = xmlResponse.substring(matcher.start());
				if(htmlRentas!= null && !htmlRentas.equals("")){
					pattern = Pattern.compile("<td class=\"Header1Regular\" width=\"75%\"><span>(.+?)</td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						ajustesList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"75%\" colspan=\"1\"><span>(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						ajustesList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularLigthLine\" width=\"75%\">(.+?)</td><td class=\"TotalHeader1RegularLigthLine\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						ajustesList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\"><span>(.+?)</span></td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						ajustesList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"DetalleRentas\" width=\"70%\" colspan=\"1\">(.+?)</td><td class=\"TotalDetalleRentas\" width=\"20%\">(.+?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>", ""));
						ajustesList.add(servicios);
					}
					pattern = Pattern.compile("<td class=\"Header1RegularPlus1\" width=\"75%\"><span>(.+?)</td><td class=\"TotalHeader1RegularPlus1\" width=\"20%\"><font size=\"3\">(.+?)</td>");
					matcher = pattern.matcher(htmlRentas);
					while(matcher.find()){
						servicios = new FacturaVO();
						if(matcher.group(1) != null)
							servicios.setTipo(matcher.group(1).replaceAll("<span>|</span>", ""));
						if(matcher.group(2) != null)
							servicios.setCosto(matcher.group(2).replaceAll("<span>|</span>|</font>", ""));
						ajustesList.add(servicios);
					}
				}
			}

			estadoCuenta.setSaldos(saldosList);
			estadoCuenta.setRentas(rentasList);
			estadoCuenta.setServiciosConPromocion(serviciosList);
			estadoCuenta.setServiciosSinPromocion(serviciosSinList);
			estadoCuenta.setConsumosPeriodo(consumosPeriodoList);
			estadoCuenta.setOtrosCargos(otrosCargosList);
			estadoCuenta.setAjustes(ajustesList);

		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}		
		return estadoCuenta;
	}
	
	public static DetalleSaldoParserVO parseFacturaSaldos(final String sResponse) throws Exception{
		Pattern pattern = null;
		Matcher matcher = null;
		DetalleSaldoParserVO repuesta = new DetalleSaldoParserVO();
		try {
			byte ptext[] = sResponse.getBytes();
			String xmlResponse = new String(ptext, "UTF-8");

			//Saldo Anterior
			pattern = Pattern.compile("<span>(.+?)Saldo Ant(.+?)</span></td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				repuesta.setO1saldoAnterior(matcher.group(3));
			}else repuesta.setO1saldoAnterior("0");
			
			//Su pago GRACIAS 
			
			pattern = Pattern.compile("<span>- Su pago GR(.+?)</span></td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				repuesta.setO2suPagoGracias(matcher.group(2));
			}else repuesta.setO2suPagoGracias("0");
			
			//Ajustes a la cuenta
			pattern = Pattern.compile("<span>(.+?)Ajustes a la c(.+?)</span></td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				repuesta.setO3ajustesCuenta(matcher.group(3));
			}else repuesta.setO3ajustesCuenta("0");
			
			//Total Factura Periodo actual
			pattern = Pattern.compile("<span>(.+?)Total Factura Per(.+?)</span></td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				repuesta.setO4totalFacturaActual(matcher.group(3));
			}else repuesta.setO4totalFacturaActual("0");

			//Subtotal
//			pattern = Pattern.compile("<span>Subtota(.+?)</span></td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
//			matcher = pattern.matcher(xmlResponse);
//			if(matcher.find()){
//				repuesta.setSubtotal(matcher.group(2));
//			} else 	repuesta.setSubtotal("0");
//			
			//Ajuste por redondeo
			pattern = Pattern.compile("<span>(.+?)Ajuste por redond(.+?)</span></td><td class=\"TotalHeader1Regular\" width=\"20%\">(.+?)</td>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				repuesta.setO5ajusteRedondeo(matcher.group(3));
			}	
			
			//Total a pagar
			pattern = Pattern.compile("<span>TOTAL A PAG(.+?)</span></td><td class=\"Header1RegularPlus1\" width=\"20%\">(.+?)<INPUT id=\"hdTOTALPAGAR\" type=\"hidden\" value=\"(.+?)\"></td>");
			matcher = pattern.matcher(xmlResponse);
			if(matcher.find()){
				repuesta.setO6totalPagar(matcher.group(3));
			} else repuesta.setO6totalPagar("0");
			
			
			
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}		
		return repuesta;
	}
	
	public static String eliminaCaracteresEspeciales(String cadena)
	{				
		try
		{
			String proc = java.text.Normalizer.normalize(cadena,java.text.Normalizer.Form.NFD);
			StringBuilder sb = new StringBuilder();
			for (char c : proc.toCharArray()) {
			  if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.BASIC_LATIN) {
			    sb.append(c);
			  }
			}
			cadena = sb.toString();
		}
		catch (Exception e) {		
			Logger.write(e.getLocalizedMessage());
		}				
		
		return cadena;
	}
}
