package mx.com.iusacell.services.miusacell.util;

import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.OfertaComercial;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.AbonoTiempoAireVO;
import mx.com.iusacell.services.miiusacell.vo.AltaCitaVO;
import mx.com.iusacell.services.miiusacell.vo.CajaRegistraMovimientoVO;
import mx.com.iusacell.services.miiusacell.vo.CatalogoCaeGeneralVO;
import mx.com.iusacell.services.miiusacell.vo.CatalogoMovilCaeVO;
import mx.com.iusacell.services.miiusacell.vo.CatalogoVO;
import mx.com.iusacell.services.miiusacell.vo.CitasDisponiblesXHorario;
import mx.com.iusacell.services.miiusacell.vo.CitasXHorarioVO;
import mx.com.iusacell.services.miiusacell.vo.ColoniaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaPrepagoVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaSrScVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaWalletListVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaWalletGeneralVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoDetalleTablaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoDetalleVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoFechaTablaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoFechaVO;
import mx.com.iusacell.services.miiusacell.vo.ContratarServiciosVO;
import mx.com.iusacell.services.miiusacell.vo.DNCambioVO;
import mx.com.iusacell.services.miiusacell.vo.DNReactivacionVO;
import mx.com.iusacell.services.miiusacell.vo.DNSugeridoVO;
import mx.com.iusacell.services.miiusacell.vo.DatosAdicionalesDispatcherClienteCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.DatosDispatcherClienteCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.DatosFocalizacionFocaVO;
import mx.com.iusacell.services.miiusacell.vo.DatosFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.DatosLineaInsuranceVO;
import mx.com.iusacell.services.miiusacell.vo.DatosSQLDispatcherVO;
import mx.com.iusacell.services.miiusacell.vo.DatosServiciosAContratarVO;
import mx.com.iusacell.services.miiusacell.vo.DatosUltimasFacturasVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVOExtendido;
import mx.com.iusacell.services.miiusacell.vo.DetalleWalletsVO;
import mx.com.iusacell.services.miiusacell.vo.DireccionVO;
import mx.com.iusacell.services.miiusacell.vo.DispatcherVO;
import mx.com.iusacell.services.miiusacell.vo.GetCitasPendientesXDNVO;
import mx.com.iusacell.services.miiusacell.vo.GetHorariosDisponiblesCallCenterVO;
import mx.com.iusacell.services.miiusacell.vo.ImagenEquipoVO;
import mx.com.iusacell.services.miiusacell.vo.LoginVO;
import mx.com.iusacell.services.miiusacell.vo.MensajeMailVO;
import mx.com.iusacell.services.miiusacell.vo.NumerosVO;
import mx.com.iusacell.services.miiusacell.vo.ObtenerDescripcionPlanesVO1;
import mx.com.iusacell.services.miiusacell.vo.ObtenerDetallesServicesClassVO;
import mx.com.iusacell.services.miiusacell.vo.OperadorVO;
import mx.com.iusacell.services.miiusacell.vo.PagoFacturaResponseVO;
import mx.com.iusacell.services.miiusacell.vo.ReprogramacionVO;
import mx.com.iusacell.services.miiusacell.vo.ResponseCitaVO;
import mx.com.iusacell.services.miiusacell.vo.RespuestaServiciosVO;
import mx.com.iusacell.services.miiusacell.vo.RespuestaVO;
import mx.com.iusacell.services.miiusacell.vo.RetornaDNSugerido;
import mx.com.iusacell.services.miiusacell.vo.SCobrosVO;
import mx.com.iusacell.services.miiusacell.vo.SaldosDetalleVO;
import mx.com.iusacell.services.miiusacell.vo.SaldosFechaVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosAdicionalesVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosContratarVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.TarjetasCajaVO;
import mx.com.iusacell.services.miiusacell.vo.TiposDeAtencionVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaCuentaResponseVO;
import mx.com.iusacell.services.miiusacell.vo.WalletsVO;
import mx.com.iusacell.services.miusacell.call.CallServiceServiciosContratos;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public abstract class ParseXMLFile {
	private static final String VALOR_KB="KB";
	private static final String VALOR_MB="MB";
	private static final String VALOR_GB="GB";
	private static final String VALOR_TB="TB";
	private static final int VALOR_CONVERSION_BYTES=1024;

	@SuppressWarnings("unchecked")
	public static List<ServiciosContratarVO> parseConsultaServicios(final String resultadoXML) throws ServiceException{
		String error = "";
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		ServiciosContratarVO serviciosContratoVO = new ServiciosContratarVO();
		List<ServiciosContratarVO> servContratadosList = new ArrayList<ServiciosContratarVO>();
		
		try {
			
			

			/** recorre resultadoXML **/
			try{
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("obtieneServiciosResponse", Namespace.getNamespace("http://ws.integracion.telco.iusacell.com.mx")) != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("obtieneServiciosResponse", Namespace.getNamespace("http://ws.integracion.telco.iusacell.com.mx")).getChild("return", Namespace.getNamespace("http://ws.integracion.telco.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("obtieneServiciosResponse", Namespace.getNamespace("http://ws.integracion.telco.iusacell.com.mx")).getChild("return", Namespace.getNamespace("http://ws.integracion.telco.iusacell.com.mx")).getChildren();	
					
					   it = childrens.iterator();
				        while(it.hasNext()){
				        	Element registro = (Element) it.next();
				        	if(registro.getName().equals("servicios")){				        		
				        		if(registro.getChild("descripcion", Namespace.getNamespace("http://comun.telco.iusacell.com.mx/xsd")) != null){
				        			serviciosContratoVO = new ServiciosContratarVO();
				        			serviciosContratoVO.setDescripcionServicio(registro.getChild("descripcion", Namespace.getNamespace("http://comun.telco.iusacell.com.mx/xsd")).getValue());
				        			serviciosContratoVO.setStatusServicios("activo");
				        			if(registro.getChild("id", Namespace.getNamespace("http://comun.telco.iusacell.com.mx/xsd")) != null){
					        			serviciosContratoVO.setIdServicio(registro.getChild("id", Namespace.getNamespace("http://comun.telco.iusacell.com.mx/xsd")).getValue());
					        		}
				        			servContratadosList.add(serviciosContratoVO);
				        		}
				        	}
     			        }					
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("detail").getChild("IObtieneServiciosSGestionException", Namespace.getNamespace("http://ws.integracion.telco.iusacell.com.mx")).getChild("SGestionException", Namespace.getNamespace("http://ws.integracion.telco.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					Logger.write("   Detail        : (Exception) " + e.getMessage());
					throw new ServiceException("Ocurrio un error al consultar los servicios");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return servContratadosList;
	}
	
	@SuppressWarnings("unchecked")
	public static List<HashMap<String,String>> parseConsultaServiciosGSM(final String resultadoXML) throws ServiceException{
		List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		String error = "";
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			try{
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				if(raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChild("Ok", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")) != null){

				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChild("Fault", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChild("Reason", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					Logger.write("   Detail        : (Exception) " + e.getMessage());
					throw new ServiceException("Ocurrio un error al consultar los servicios");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	public static List<ConsumoFechaVO> parseConsumos(String resultadoXML, final String tipoEvento, final String dn) throws ServiceException{
		String error = "";
		ConsumoFechaVO consumosSingle = new ConsumoFechaVO();
		List<ConsumoFechaVO> consumosList = new ArrayList<ConsumoFechaVO>();
		ConsumoDetalleVO consuDetailSingle = new ConsumoDetalleVO();
		List<ConsumoDetalleVO> consuList = new ArrayList<ConsumoDetalleVO>();
		HashMap<String, Object> respuesta =new HashMap<String, Object>();
		int errorFound = 0;
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String numOrigen = "";
		String numDestino = "";
		String textoFormateado = "";

			/** recorre resultadoXML **/
			try{
				if(!resultadoXML.contains("SOAP-ENV:Envelope")){
					resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
					"<soapenv:Header/>"+
					"<soapenv:Body>" + resultadoXML;
					resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
				}
				
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")) != null){

					
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChildren();
					it = childrens.iterator();
					String fecha = "";
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						if(registro.getName().equals("detalleLlamadaBic")){
							consumosSingle = new ConsumoFechaVO();
							consuDetailSingle = new ConsumoDetalleVO();
							consuList = new ArrayList<ConsumoDetalleVO>();
							if(registro.getChild("fechaEvento", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								fecha = registro.getChild("fechaEvento", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue();
							}
							if(registro.getChild("montoFree", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setMontoFree(registro.getChild("montoFree", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}
							if(registro.getChild("montoReal", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setMontoReal(registro.getChild("montoReal", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}
							if(registro.getChild("horaEvento", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setHoraEvento(registro.getChild("horaEvento", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}
							if(registro.getChild("numDestino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								numDestino = registro.getChild("numDestino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue();
								if(numDestino != null && numDestino.length() >= 10){
									numDestino = numDestino.substring(numDestino.length()-10,numDestino.length());
								}
								consuDetailSingle.setNumDestino(numDestino);
							}
							if(registro.getChild("numOrigen", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								numOrigen = registro.getChild("numOrigen", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue();
								if(numOrigen != null && numOrigen.length() >= 10){
									numOrigen = numOrigen.substring(numOrigen.length()-10,numOrigen.length());
								}
								consuDetailSingle.setNumOrigen(numOrigen);
							}
							if(registro.getChild("operadorDestino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								textoFormateado = registro.getChild("operadorDestino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue();
								consuDetailSingle.setOperadorDestino(Utilerias.formatoCadena(textoFormateado));
							}
							if(registro.getChild("operadorOrigen", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								textoFormateado = registro.getChild("operadorOrigen", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue();
								consuDetailSingle.setOperadorOrigen(Utilerias.formatoCadena(textoFormateado));
							}
							if(registro.getChild("durMinRedn", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setDurMinRedn(registro.getChild("durMinRedn", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}
							if(registro.getChild("descTipoTrafico", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setDescTipoTrafico(registro.getChild("descTipoTrafico", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue().replace("SMS SALIENTE ", "").replace("SMS ENTRANTE ", ""));
							}
							if(registro.getChild("saldoAntes", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setSaldoAntes(registro.getChild("saldoAntes", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}
							if(registro.getChild("saldoDespues", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setSaldoDespues(registro.getChild("saldoDespues", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}
							if(registro.getChild("cdescWallet", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setCdescWallet(registro.getChild("cdescWallet", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}
							if(registro.getChild("destino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								textoFormateado = registro.getChild("destino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue(); 
								consuDetailSingle.setCiudadDestino(Utilerias.formatoCadena(textoFormateado));
							}

							if(tipoEvento.equals("4")){

								double saldoAntes = Double.parseDouble(consuDetailSingle.getMontoReal());
								double saldoDespues = Double.parseDouble(consuDetailSingle.getMontoFree());
								double saldoTotal = 0;

								if (fecha != "" || fecha != null){
									if(!consuDetailSingle.getCdescWallet().trim().toLowerCase().equals("datos incluidos")){
										saldoTotal = ((saldoAntes - saldoDespues)/1024);
										if(saldoTotal < 0){
											saldoTotal = (saldoTotal *(-1));
										}
										consuDetailSingle.setTotalNavegacion(Double.toString(saldoTotal));
										respuesta.put(fecha,consuDetailSingle);
									}
								}

								if(!consuDetailSingle.getCdescWallet().trim().toLowerCase().equals("datos incluidos")){
									consuList.add(consuDetailSingle);
									consumosSingle.setFecha(fecha);
									consumosSingle.setDetalle(consuList);
									consumosList.add(consumosSingle);
								}
							}else{
								if(!dn.contains(numDestino)){
									consuList.add(consuDetailSingle);
									consumosSingle.setFecha(fecha);
									consumosSingle.setDetalle(consuList);
									consumosList.add(consumosSingle);
								}
							}
							
						}
						else{
							if(registro.getName().equals("codigo")){
								if(!registro.getValue().equals("1")){
									error += ((" ")+registro.getValue());
									errorFound = 1;
								}
							}
							if(registro.getName().equals("mensaje")){
								error += ((" ")+registro.getValue());
							}							
						}
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
				}
				if(errorFound == 1){
					throw new ServiceException(error);
				}
		} catch (Exception e) {
			if(errorFound == 1){
				throw new ServiceException(error);
			}else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
			}
		}
		return consumosList;
	}
	
	@SuppressWarnings("unchecked")
	public static List<ConsumoFechaTablaVO> parseConsumosTabla(String resultadoXML, final String tipoEvento, final String dn, final DetalleFocalizacionVO descripcion, final boolean resumen) throws ServiceException{
		String error = "";
		ConsumoFechaTablaVO consumosSingle = new ConsumoFechaTablaVO();
		List<ConsumoFechaTablaVO> consumosList = new ArrayList<ConsumoFechaTablaVO>();
		ConsumoDetalleTablaVO consuDetailSingle = new ConsumoDetalleTablaVO();
		List<ConsumoDetalleTablaVO> consuList = new ArrayList<ConsumoDetalleTablaVO>();
		HashMap<String, Object> respuesta =new HashMap<String, Object>();
		OracleProcedures oracle = new OracleProcedures();
		int errorFound = 0;
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String numOrigen = "";
		String numDestino = "";
		String textoFormateado = "";
		DecimalFormat formatNumber = new DecimalFormat("#.##");
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss aa");
		Date dateToHrs = new Date();
		String telefono = "";
		double montoReal = 0;
		double montoFree = 0;
		double salAnt = 0;
		double salDes = 0;
		double saldoTotal = 0;
		String fechaCompleta = "";
		String cDescWallet = "";

			/** recorre resultadoXML **/
			try{
				try{
					fechaCompleta = oracle.getValorParametro(10);
				}catch (Exception e) {
					fechaCompleta = "0";
				}
				
				if(!resultadoXML.contains("SOAP-ENV:Envelope")){
					resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
					"<soapenv:Header/>"+
					"<soapenv:Body>" + resultadoXML;
					resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
				}
				
				
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")) != null){
					
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChildren();
					it = childrens.iterator();
					String fecha = "";
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						if(registro.getName().equals("detalleLlamadaBic")){
							consumosSingle = new ConsumoFechaTablaVO();
							consuDetailSingle = new ConsumoDetalleTablaVO();
							consuList = new ArrayList<ConsumoDetalleTablaVO>();
							if(registro.getChild("fechaEvento", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								fecha = registro.getChild("fechaEvento", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue();
							}
							if(registro.getChild("horaEvento", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								try{
									dateToHrs = dateFormat.parse(registro.getChild("horaEvento", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
								}catch (ParseException e) {
									dateToHrs = new Date();
								}
								if(fechaCompleta.equals("1"))
									consuDetailSingle.setHora(new SimpleDateFormat("hh:mm aa").format(dateToHrs));
								else
									consuDetailSingle.setHora(new SimpleDateFormat("HH:mm").format(dateToHrs));
							}
							
							if(registro.getChild("numDestino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								numDestino = registro.getChild("numDestino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue();
							}
							if(registro.getChild("cdescWallet", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								cDescWallet = registro.getChild("cdescWallet", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue();
							}
							if(registro.getChild("numOrigen", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								numOrigen = registro.getChild("numOrigen", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue();
							}
							if(dn.contains(numOrigen)){
								
								if(registro.getChild("operadorDestino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
									textoFormateado = registro.getChild("operadorDestino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue();
									
									if(textoFormateado != null && textoFormateado.toLowerCase().trim().equals("no identificado"))
										consuDetailSingle.setCompania("ND");
									else
										consuDetailSingle.setCompania(Utilerias.formatoCadena(textoFormateado));
								}
								
								if(numDestino != null && numDestino.length() >= 10){
									telefono = numDestino.substring(numDestino.length()-10,numDestino.length());
									numDestino = numDestino.substring(numDestino.length()-10,numDestino.length());
									try{
										telefono = Integer.toString(Integer.parseInt(telefono));
									}catch (NumberFormatException e) {
									}
								}else{
									telefono = numDestino;
									try{
										telefono = Integer.toString(Integer.parseInt(telefono));
									}catch (NumberFormatException e) {
									}
								}
							}
							else{
								
								if(registro.getChild("operadorOrigen", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
									textoFormateado = registro.getChild("operadorOrigen", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue();
									
									if(textoFormateado != null && textoFormateado.toLowerCase().trim().equals("no identificado"))
										consuDetailSingle.setCompania("ND");
									else
										consuDetailSingle.setCompania(Utilerias.formatoCadena(textoFormateado));
								}
								
								if(numOrigen != null && numOrigen.length() >= 10){
									telefono = numOrigen.substring(numOrigen.length()-10,numOrigen.length());
									try{
										telefono = Integer.toString(Integer.parseInt(telefono));
									}catch (NumberFormatException e) {
									}
								}else{
									telefono = numDestino;
									try{
										telefono = Integer.toString(Integer.parseInt(telefono));
									}catch (NumberFormatException e) {
									}
								}
							}
							consuDetailSingle.setNumero(telefono);
							
							
							if(registro.getChild("destino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								if(telefono != null && telefono.trim().equals("228")){
									textoFormateado = registro.getChild("destino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue(); 
									consuDetailSingle.setDestino("Asistencia telefonica");
								}else{
									textoFormateado = registro.getChild("destino", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue(); 
									consuDetailSingle.setDestino(Utilerias.formatoCadena(textoFormateado));
								}
							}
							
							if(registro.getChild("durMinRedn", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setTiempo(registro.getChild("durMinRedn", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}
							
							if(registro.getChild("montoFree", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								try{
									montoFree = Double.parseDouble(registro.getChild("montoFree", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
									if(montoFree<0)
										montoFree = montoFree * (-1);
								}catch (NumberFormatException e) {
									montoFree = 0;
								}
							}
							if(registro.getChild("montoReal", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								try{
									montoReal = Double.parseDouble(registro.getChild("montoReal", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
									if(montoReal<0)
										montoReal = montoReal * (-1);
								}catch (NumberFormatException e) {
									montoReal = 0;
								}
							}
							
							consuDetailSingle.setCosto("0");
//							if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
//								if(respuestaPlan.getMinutoadc()!=null && !respuestaPlan.getMinutoadc().replaceAll("[^\\d.]", "").equals("") && consuDetailSingle.getTiempo() != null && !consuDetailSingle.getTiempo().replaceAll("[^\\d.]", "").equals("")){
//									consuDetailSingle.setCosto("$ " + formatNumber.format(Double.parseDouble(consuDetailSingle.getTiempo()) * Double.parseDouble(respuestaPlan.getMinutoadc())));
//								}else{
//									consuDetailSingle.setCosto("$ " + formatNumber.format(montoFree));
//								}
//							}
//							else{
//								if(respuestaPlan.getMinutoadc()!=null && !respuestaPlan.getMinutoadc().replaceAll("[^\\d.]", "").equals("") && consuDetailSingle.getTiempo() != null && !consuDetailSingle.getTiempo().replaceAll("[^\\d.]", "").equals("")){
//									consuDetailSingle.setCosto("$ " + formatNumber.format(Double.parseDouble(consuDetailSingle.getTiempo()) * Double.parseDouble(respuestaPlan.getMinutoadc())));
//								}else{
//									consuDetailSingle.setCosto("$ " + formatNumber.format(montoReal));
//								}
//							}

							if(tipoEvento.equals("4")){
								ConsumoDetalleTablaVO validafecha = new ConsumoDetalleTablaVO();

								if(registro.getChild("saldoAntes", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
									try{
										salAnt = Double.parseDouble(registro.getChild("saldoAntes", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
									}catch (NumberFormatException e) {
										salAnt = 0;
									}
								}
								if(registro.getChild("saldoDespues", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
									try{
										salDes = Double.parseDouble(registro.getChild("saldoDespues", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
									}catch (NumberFormatException e) {
										salDes = 0;
									}
								}
								consuDetailSingle.setMbConsumidos(Double.toString((salAnt - salDes)/1024));

								if (fecha != "" || fecha != null){
									if(resumen){
										if(cDescWallet != null && !cDescWallet.trim().toLowerCase().equals("datos incluidos")){
											//										saldoTotal = ((salAnt - salDes)/1024);
											saldoTotal = ((montoFree - montoReal)/1024);
											saldoTotal = Utilerias.round(saldoTotal, 2);
											
											if(respuesta.get(fecha) != null){
												validafecha = (ConsumoDetalleTablaVO) respuesta.get(fecha);
												saldoTotal = Double.parseDouble(validafecha.getMbConsumidos());

												//											saldoTotal = saldoTotal + (((salAnt - salDes)/1024));
												saldoTotal = saldoTotal + (((montoFree - montoReal)/1024));
												if(saldoTotal < 0){
													saldoTotal = (saldoTotal * (-1));
												}

												consuDetailSingle.setMbConsumidos(formatNumber.format(saldoTotal));
												consuDetailSingle.setCosto("0");
//												if(respuestaPlan.getMegaadc()!=null && !respuestaPlan.getMegaadc().replaceAll("[^\\d.]", "").equals(""))
//													consuDetailSingle.setCosto("$ " + formatNumber.format(saldoTotal * Double.parseDouble(respuestaPlan.getMegaadc().replaceAll("[^\\d.]", ""))));
//												else
//													consuDetailSingle.setCosto("$ " + formatNumber.format(saldoTotal * 0));

												respuesta.put(fecha,consuDetailSingle);
											}
											else{
												consuDetailSingle.setMbConsumidos(formatNumber.format(saldoTotal));
												consuDetailSingle.setCosto("0");
//												if(respuestaPlan.getMegaadc()!=null && !respuestaPlan.getMegaadc().replaceAll("[^\\d.]", "").equals(""))
//													consuDetailSingle.setCosto("$ " + formatNumber.format(saldoTotal * Double.parseDouble(respuestaPlan.getMegaadc().replaceAll("[^\\d.]", ""))));
//												else
//													consuDetailSingle.setCosto("$ " + formatNumber.format(saldoTotal * 0));
												respuesta.put(fecha,consuDetailSingle);
											}
										}
									}else{
										if(cDescWallet != null && !cDescWallet.trim().toLowerCase().equals("datos incluidos")){
											saldoTotal = ((montoFree - montoReal)/1024);
											if(saldoTotal < 0){
												saldoTotal = (saldoTotal * (-1));
											}

											consuDetailSingle.setMbConsumidos(formatNumber.format(saldoTotal));
											consuDetailSingle.setCosto("0");
//											if(respuestaPlan.getMegaadc()!=null && !respuestaPlan.getMegaadc().replaceAll("[^\\d.]", "").equals(""))
//												consuDetailSingle.setCosto("$ " + formatNumber.format(saldoTotal * Double.parseDouble(respuestaPlan.getMegaadc().replaceAll("[^\\d.]", ""))));
//											else
//												consuDetailSingle.setCosto("$ " + formatNumber.format(saldoTotal * 0));
										}
									}
								}
							}
							
							if(tipoEvento.equals("4")){
								if(cDescWallet != null && !cDescWallet.trim().toLowerCase().equals("datos incluidos")){
									consuList.add(consuDetailSingle);
									consumosSingle.setFecha(fecha);
									consumosSingle.setDetalle(consuList);
									consumosList.add(consumosSingle);
								}
							}else{
								if(!dn.contains(numDestino)){
									consuList.add(consuDetailSingle);
									consumosSingle.setFecha(fecha);
									consumosSingle.setDetalle(consuList);
									
									if(tipoEvento.equals("5") || tipoEvento.equals("6")){
										if(tipoEvento.equals("5") &&  (consuDetailSingle.getCompania().trim().equals("Iusacell") || consuDetailSingle.getCompania().trim().equals("Unefon"))){
											consumosList.add(consumosSingle);
										}
										else if(tipoEvento.equals("6") &&  (!consuDetailSingle.getCompania().trim().equals("Iusacell") && !consuDetailSingle.getCompania().trim().equals("Unefon"))){
											consumosList.add(consumosSingle);
										}
									}else{
										consumosList.add(consumosSingle);
									}
								}
							}
							
							
						}
						else{
							if(registro.getName().equals("codigo")){
								if(!registro.getValue().equals("1")){
									error += ((" ")+registro.getValue());
									errorFound = 1;
								}
							}
							if(registro.getName().equals("mensaje")){
								error += ((" ")+registro.getValue());
							}							
						}
					}
					
					if(tipoEvento.equals("4")){
						if(resumen){
						Iterator ite = respuesta.entrySet().iterator();
						String FechaSend = "";
						consumosList = new ArrayList<ConsumoFechaTablaVO>();
						while (ite.hasNext()){
							consumosSingle = new ConsumoFechaTablaVO();
							consuList = new ArrayList<ConsumoDetalleTablaVO>();							
							Map.Entry e = (Map.Entry)ite.next();
							consuDetailSingle = (ConsumoDetalleTablaVO) e.getValue();
							FechaSend = String.valueOf(e.getKey());

							consuList.add(consuDetailSingle);
							consumosSingle.setFecha(FechaSend);
							consumosSingle.setDetalle(consuList);
							consumosList.add(consumosSingle);
						}
						}
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					Logger.write("   Detail        : (Exception) " + error);
				}
				if(errorFound == 1){
					throw new ServiceException(error);
				}
		} catch (Exception e) {
			if(errorFound == 1){
				throw new ServiceException(error);
			}else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
			}
		}
		return consumosList;
	}
	
	@SuppressWarnings("unchecked")
	public static List<SaldosFechaVO> parseSaldos(final String resultadoXML) throws ServiceException{
		String error = "";
		SaldosFechaVO consumosSingle = new SaldosFechaVO();
		List<SaldosFechaVO> consumosList = new ArrayList<SaldosFechaVO>();
		SaldosDetalleVO consuDetailSingle = new SaldosDetalleVO();
		List<SaldosDetalleVO> consuList = new ArrayList<SaldosDetalleVO>();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			try{
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")) != null){

					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChildren();
					it = childrens.iterator();
					String fecha = "";
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						if(registro.getName().equals("detalleLlamadaBic")){
							consumosSingle = new SaldosFechaVO();
							consuDetailSingle = new SaldosDetalleVO();
							consuList = new ArrayList<SaldosDetalleVO>();
							if(registro.getChild("fechaEvento", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								fecha = registro.getChild("fechaEvento", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue();
							}
							if(registro.getChild("montoFree", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setMontoFree(registro.getChild("montoFree", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}
							if(registro.getChild("montoReal", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setMontoReal(registro.getChild("montoReal", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}
							if(registro.getChild("saldoAntes", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setSaldoAntes(registro.getChild("saldoAntes", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}
							if(registro.getChild("saldoDespues", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setSaldoDespues(registro.getChild("saldoDespues", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}
							if(registro.getChild("durMinRedn", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")) != null){
								consuDetailSingle.setDurMinRedn(registro.getChild("durMinRedn", Namespace.getNamespace("http://vo.detalleconsumo.telco.iusacell.com/xsd")).getValue());
							}

							consuList.add(consuDetailSingle);
							consumosSingle.setFecha(fecha);
							consumosSingle.setDetalle(consuList);
							consumosList.add(consumosSingle);
						}
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					Logger.write("   Detail        : (Exception) " + error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					Logger.write("   Detail        : (Exception) " + error);
				}
				else{
					Logger.write("   Detail        : (Exception) " + e.getMessage());
				}
			}
		} catch (Exception e) {
			Logger.write("   Detail        : (Exception) " + e.getMessage());
		}
		return consumosList;
	}

	@SuppressWarnings("unchecked")
	public static DetalleFocalizacionVO parseFocalizacion(final String resultadoXML) throws ServiceException{
		String error = "";
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		Element elementos = null;
		Element plan = null;
		Element lineaElement = null;
		Element dateBilled = null;
		Element datosBusqueda = null;
		Element numeros = null;
		Element isPostPago = null;
		Element code = null;
		List<Element> childrens = null;
		List<Element> ChildBalance = null;
		List<Element> walletBalance = null;
		Iterator<Element> it = null;
		Iterator<Element> walletIt = null;
		DetalleFocalizacionVO respuesta = new DetalleFocalizacionVO();
		CallServiceServiciosContratos fechaFac = new CallServiceServiciosContratos();
		OracleProcedures oracle = new OracleProcedures();
		String NameTipoWallet = "";
		String fechaCorte = "";
		String sResponse = "";
		String v_customerID="";
		Element resultadoBusqueda=null;
		int obtenerCustomerId = 0;
		String dataBySqlDispatcher = "";
		String consultaFechaDeCorteActual = "";
		//*** richie
		Element datosCliente = null;
		
		String serviciosFocaBD="";
		String fechaFinServFoca="";
		String[] regServicioFoca;
		ServiciosFocalizacionVO servicioFocaReg;
		List<ServiciosFocalizacionVO> serviciosFoca = new ArrayList<ServiciosFocalizacionVO>();
		List<ServiciosFocalizacionVO> serviciosFocaFinal = new ArrayList<ServiciosFocalizacionVO>();
		List<Element> serviciosList = null;

		Iterator<Element> serviciosIt = null;
		try {
			
			try{
				dataBySqlDispatcher = oracle.getValorParametro(76);
			}catch (ServiceException e) {
				dataBySqlDispatcher = "0";
			}
			
			try{
				consultaFechaDeCorteActual = oracle.getValorParametro(90);
			}catch (ServiceException e) {
				consultaFechaDeCorteActual = "0";
			}
//			TODO: German:[14/06/2017] Se agrega para la tag para el Servicio PRIP en Prepago				
			try{
				serviciosFocaBD = oracle.getValorParametro(275);
				for (String servicioTmp : serviciosFocaBD.split(",")) {
					servicioFocaReg = new ServiciosFocalizacionVO();
					regServicioFoca = servicioTmp.split("\\|");
					servicioFocaReg.setIdServicioPrip(regServicioFoca[0]);
					servicioFocaReg.setNombreServicioPrip(regServicioFoca[1]);
					servicioFocaReg.setDescServicioPrip(regServicioFoca[2]);
					servicioFocaReg.setCostoServPrip(regServicioFoca[3]);
					servicioFocaReg.setVersion(regServicioFoca[4]) ;
					servicioFocaReg.setFlagServPRIP(false);
					serviciosFoca.add(servicioFocaReg);
				}
					
			//	}
			}catch (ServiceException e) {
				servicioFocaReg = new ServiciosFocalizacionVO();
				servicioFocaReg.setIdServicioPrip("3202");
				servicioFocaReg.setNombreServicioPrip("ATT PRIP");
				servicioFocaReg.setDescServicioPrip("Servicio de ATT PRIP");
				servicioFocaReg.setCostoServPrip("20");
				servicioFocaReg.setVersion("1") ;
				servicioFocaReg.setFlagServPRIP(false);
				serviciosFoca.add(servicioFocaReg);
			}
			try{
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return") != null){
					elementos = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return");
					
					if(dataBySqlDispatcher.equals("1")){
						if(elementos.getChild("datosBSCSResponse")!=null){
							if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("resultadoBusqueda")!=null){
								resultadoBusqueda = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("resultadoBusqueda");
								if(resultadoBusqueda.getContentSize()>1){
									obtenerCustomerId = 1;
									List<Element> hijos = null;
									hijos = resultadoBusqueda.getChildren();
									Iterator iter = hijos.iterator();

									while (iter.hasNext()) {
										Element registros = (Element) iter.next();
										if(registros.getName().equals("datosBusqueda")){
											if(registros.getChild("statusContrato").getValue().toLowerCase().equals("s")){
												respuesta.setIdPlan(registros.getChild("tmCode").getValue());
												respuesta.setTmCode(registros.getChild("tmCode").getValue());
												v_customerID = registros.getChild("customerID").getValue();
											}else if(registros.getChild("statusContrato").getValue().toLowerCase().equals("a")){
												respuesta.setIdPlan(registros.getChild("tmCode").getValue());
												respuesta.setTmCode(registros.getChild("tmCode").getValue());
												v_customerID = registros.getChild("customerID").getValue();
												break;
											}
										}
									}
									sResponse = fechaFac.getDatosFacturacion(v_customerID);
									if(sResponse != null && !sResponse.equals("")){
										fechaCorte = ParseXMLFile.ParseDatosFacturacion(sResponse);
										respuesta.setFechaCorte(fechaCorte);
									}
								}
							}
						}
					}
					
					//fin dispatcher
					if(elementos.getChild("datosBSCSResponse")!=null){
						
						//**********Nuevo
						if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("cliente").getChild("datosCliente")!= null){
							datosCliente = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("cliente").getChild("datosCliente");
							if(datosCliente!=null && datosCliente.getChild("tipoCliente")!=null){
								respuesta.setTipoCliente(datosCliente.getChild("tipoCliente").getValue());
							}else{
								respuesta.setTipoCliente("");
							}
						}
						
						
						if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS")!=null){
							isPostPago = elementos.getChild("datosBSCSResponse").getChild("datosBSCS");
							if(isPostPago!=null && isPostPago.getChild("isPostpagoOrHibrido")!=null){
								respuesta.setIsPostpagoOrHibrido(isPostPago.getChild("isPostpagoOrHibrido").getValue());
							}else{
								respuesta.setIsPostpagoOrHibrido("");
							}
						}
						
						if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("plan")!= null){
							plan = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("plan");
							if(plan != null && plan.getChild("descripcionPlan") != null){
								respuesta.setDescripcionPlan(plan.getChild("descripcionPlan").getValue());
								respuesta.setRenta(plan.getChild("costoRentaMensual").getValue());
							}else{
								respuesta.setDescripcionPlan("");
							}
							if(obtenerCustomerId == 0){
								if(plan != null && plan.getChild("plan") != null){
									respuesta.setIdPlan(plan.getChild("plan").getValue());
								}else{
									respuesta.setIdPlan("");
								}
							}
						}
						
						if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("linea")!= null){
							lineaElement = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("linea");
							if(lineaElement != null && lineaElement.getChild("fechaVencimiento") != null){
								respuesta.setFechaVencimiento(lineaElement.getChild("fechaVencimiento").getValue());
							}else{
								respuesta.setFechaVencimiento("");
							}	
							
							if(lineaElement != null && lineaElement.getChild("plazoForzoso") != null){
								respuesta.setPlazoForzoso(lineaElement.getChild("plazoForzoso").getValue());
							}else{
								respuesta.setPlazoForzoso("");
							}
						
							if(lineaElement != null && lineaElement.getChild("termContratoMeses") != null){
								respuesta.setMesesRestantes(lineaElement.getChild("termContratoMeses").getValue());
							}else{
								respuesta.setMesesRestantes("");
							}
							
							if(lineaElement != null && lineaElement.getChild("termContratoMeses") != null){
								respuesta.setMesesRestantes(lineaElement.getChild("termContratoMeses").getValue());
							}else{
								respuesta.setMesesRestantes("");
							}
							
							if(lineaElement != null && lineaElement.getChild("fechaContratacion") != null){
								respuesta.setFechaContratacion(lineaElement.getChild("fechaContratacion").getValue());
							}else{
								respuesta.setFechaContratacion("");
							}
						}
						
						if(obtenerCustomerId == 0){
							if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("serviciosContratados").getChild("serviciosContratados").getChild("csDateBilled")!= null){
								dateBilled = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("serviciosContratados").getChild("serviciosContratados");
								if(dateBilled != null && dateBilled.getChild("csDateBilled") != null){
									respuesta.setFechaCorte(dateBilled.getChild("csDateBilled").getValue().toString());
								}else{
									respuesta.setFechaCorte("");
								}
							}
						}
					}
					//ETAK-inicia
					if(elementos.getChild("datosEtakResponse")!=null){
						if(elementos.getChild("datosEtakResponse").getChild("datosBSCS")!=null){
							isPostPago = elementos.getChild("datosEtakResponse").getChild("datosBSCS");
							if(isPostPago!=null && isPostPago.getChild("isPostpagoOrHibrido")!=null){
								respuesta.setIsPostpagoOrHibrido(isPostPago.getChild("isPostpagoOrHibrido").getValue());
							}else{
								respuesta.setIsPostpagoOrHibrido("");
							}
							if(elementos.getChild("datosEtakResponse").getChild("datosBSCS").getChild("plan")!= null){
								plan = elementos.getChild("datosEtakResponse").getChild("datosBSCS").getChild("plan");
								if(plan != null && plan.getChild("descripcionPlan") != null){
									respuesta.setDescripcionPlan(plan.getChild("descripcionPlan").getValue());
								}else{
									respuesta.setDescripcionPlan("");
								}
								if(plan != null && plan.getChild("plan") != null){
									respuesta.setIdPlan(plan.getChild("plan").getValue());
								}else{
									respuesta.setIdPlan("");
								}
							}
						}
						
					}
					//ETAK-fin
					
					//ETAK-inicia code
					if(elementos.getChild("datosEtakResponse")!=null){
						if(elementos.getChild("datosEtakResponse").getChild("querySubscriberResult")!=null && elementos.getChild("datosEtakResponse").getChild("querySubscriberResult").getChild("values") != null && elementos.getChild("datosEtakResponse").getChild("querySubscriberResult").getChild("values").getChild("pricePlan")!=null &&elementos.getChild("datosEtakResponse").getChild("querySubscriberResult").getChild("values").getChild("pricePlan").getChild("code")!=null){
							code = elementos.getChild("datosEtakResponse").getChild("querySubscriberResult").getChild("values").getChild("pricePlan").getChild("code");
							respuesta.setCode(code.getValue());
						}else{
							respuesta.setCode("");
						}
					}
					//ETAK-fin code
					
					if(elementos.getChild("datosBSCSResponse") != null && elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("resultadoBusqueda").getChild("datosBusqueda")!= null){
						datosBusqueda = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("resultadoBusqueda").getChild("datosBusqueda");
						if(datosBusqueda != null && datosBusqueda.getChild("contrato") != null){
							respuesta.setCoID(datosBusqueda.getChild("contrato").getValue());
							respuesta.setStatusContrato(datosBusqueda.getChild("statusContrato").getValue());
							respuesta.setStatusCuenta(datosBusqueda.getChild("statusCuenta").getValue());
							//CUENTA 
							respuesta.setCuenta(datosBusqueda.getChild("custCode").getValue());
						}else{
							respuesta.setCoID("");
						}
						if(obtenerCustomerId == 0){
							if(datosBusqueda != null && datosBusqueda.getChild("tmCode") != null){
								respuesta.setTmCode(datosBusqueda.getChild("tmCode").getValue());
							}else{
								respuesta.setTmCode("");
							}
						}
					}
					//ETAK-ini
					if(elementos.getChild("datosEtakResponse") != null && elementos.getChild("datosEtakResponse").getChild("datosBSCS") != null && elementos.getChild("datosEtakResponse").getChild("datosBSCS").getChild("resultadoBusqueda").getChild("datosBusqueda")!= null){
						datosBusqueda = elementos.getChild("datosEtakResponse").getChild("datosBSCS").getChild("resultadoBusqueda").getChild("datosBusqueda");
						if(datosBusqueda != null && datosBusqueda.getChild("contrato") != null){
							respuesta.setCoID(datosBusqueda.getChild("contrato").getValue());
							respuesta.setStatusContrato(datosBusqueda.getChild("statusContrato").getValue());
							respuesta.setStatusCuenta(datosBusqueda.getChild("statusCuenta").getValue());
						}else{
							respuesta.setCoID("");
						}
						if(datosBusqueda != null && datosBusqueda.getChild("tmCode") != null){
							respuesta.setTmCode(datosBusqueda.getChild("tmCode").getValue());
						}else{
							respuesta.setTmCode("");
						}
					}
					//ETAK-fin
					if(obtenerCustomerId == 0){
					if(respuesta.getIsPostpagoOrHibrido() != null && !respuesta.getIsPostpagoOrHibrido().equals("") && !respuesta.getIsPostpagoOrHibrido().toLowerCase().equals("prepago")){
						if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("tresUltimasFacturas")!= null){
							plan = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("tresUltimasFacturas").getChild("tresUltimasFacturas");
							if(plan != null && plan.getChild("fechaCorte") != null){
								respuesta.setFechaCorte(plan.getChild("fechaCorte").getValue().toString());
							}else{
								if(respuesta.getFechaCorte()!=null && respuesta.getFechaCorte().equals(""))
									respuesta.setFechaCorte("");
							}
						}
					}
					}
					
					//ETAK-ini
					if(respuesta.getIsPostpagoOrHibrido() != null && !respuesta.getIsPostpagoOrHibrido().equals("") && !respuesta.getIsPostpagoOrHibrido().toLowerCase().equals("prepago")){
						if(elementos.getChild("datosEtakResponse") != null && elementos.getChild("datosEtakResponse").getChild("datosBSCS").getChild("tresUltimasFacturas")!= null){
							plan = elementos.getChild("datosEtakResponse").getChild("datosBSCS").getChild("tresUltimasFacturas").getChild("tresUltimasFacturas");
							if(plan != null && plan.getChild("fechaCorte") != null){
								respuesta.setFechaCorte(plan.getChild("fechaCorte").getValue().toString());
							}else{
								if(respuesta.getFechaCorte()!=null && respuesta.getFechaCorte().equals(""))
									respuesta.setFechaCorte("");
							}
						}
					}
					
					fechaCorte = Utilerias.isThisDateWithin2MonthsRange(respuesta.getFechaCorte(), consultaFechaDeCorteActual);
					respuesta.setFechaCorte(fechaCorte);
					
					if(respuesta.getFechaCorte() == null || respuesta.getFechaCorte().equals("")){
						String dateTimeCorte = "";
						if(respuesta.getIsPostpagoOrHibrido() != null && !respuesta.getIsPostpagoOrHibrido().equals("") && !respuesta.getIsPostpagoOrHibrido().toLowerCase().equals("prepago")){
							if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS")!= null){
								plan = elementos.getChild("datosBSCSResponse").getChild("datosBSCS");
								if(plan != null && plan.getChild("diaCicloFacturacion") != null){
									dateTimeCorte = (Calendar.getInstance().get(Calendar.YEAR) + "-" + (Calendar.getInstance().get(Calendar.MONTH)+1) + "-" + plan.getChild("diaCicloFacturacion").getValue().toString() + " 00:00:00.0");
									respuesta.setFechaCorte(dateTimeCorte);
								}else{
									if(respuesta.getFechaCorte()!=null && respuesta.getFechaCorte().equals(""))
										respuesta.setFechaCorte("");
								}
							}
						}
						fechaCorte = Utilerias.isThisDateWithin2MonthsRange(respuesta.getFechaCorte(), consultaFechaDeCorteActual);
						respuesta.setFechaCorte(fechaCorte);
					}
					
					//ETAK-fin
					
					if(elementos.getChild("datosNumerosResponse").getChild("numeros")!= null){
						numeros = elementos.getChild("datosNumerosResponse").getChild("numeros");
						
						if(numeros != null && numeros.getChild("idFormaPago")!=null){
							respuesta.setIdFormaPago(numeros.getChild("idFormaPago").getValue());
						}else{
							respuesta.setIdFormaPago("");
						}
						
						if(numeros != null && numeros.getChild("idOperador")!=null){
							respuesta.setIdOperador(numeros.getChild("idOperador").getValue());
						}else{
							respuesta.setIdOperador("");
						}
						
						if(numeros != null && numeros.getChild("modalidad")!=null){
							respuesta.setModalidad(numeros.getChild("modalidad").getValue());
						}else{
							respuesta.setModalidad("");
						}
						
						if(numeros != null && numeros.getChild("servicio")!=null){
							respuesta.setServicio(numeros.getChild("servicio").getValue());
						}else{
							respuesta.setServicio("");
						}
						if(numeros != null && numeros.getChild("subservicio")!=null){
							respuesta.setSubservicio(numeros.getChild("subservicio").getValue());
						}else{
							respuesta.setSubservicio("");
						}
						if(numeros != null && numeros.getChild("tecnologia")!=null){
							respuesta.setTecnologia(numeros.getChild("tecnologia").getValue());
						}else{
							respuesta.setTecnologia("");
						}
					}
										
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("wallets") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("wallets").getChildren();
						it = ChildBalance.iterator();
						
						while(it.hasNext()){
							Element registro = (Element) it.next();
							if(registro.getChild("wallet").getChild("tipoWallet").getChild("name") != null)
								NameTipoWallet = registro.getChild("wallet").getChild("tipoWallet").getChild("name").getValue();

							if(NameTipoWallet != null){
								NameTipoWallet = NameTipoWallet.toLowerCase();
								if(NameTipoWallet.equals("main balance") || NameTipoWallet.equals("balance"))
									if(registro.getChild("wallet").getChild("cantidad") != null)
										respuesta.setTiempoAireComprado(registro.getChild("wallet").getChild("cantidad").getValue());
									else
										respuesta.setTiempoAireComprado("");
							}
						}
					}
					
					//ETAK-ini
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosEtakResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosEtakResponse").getChild("walletsEtak") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosEtakResponse").getChild("walletsEtak").getChildren();
						it = ChildBalance.iterator();
						
						while(it.hasNext()){
							
							Element registro = (Element) it.next();
							walletBalance = registro.getChildren();
							walletIt = walletBalance.iterator();
							while(walletIt.hasNext()){
								Element registroWall = (Element) walletIt.next();

								if(registroWall.getChild("tipoWallet") != null && registroWall.getChild("tipoWallet").getChild("name") != null){
									NameTipoWallet = registroWall.getChild("tipoWallet").getChild("name").getValue();

									if(NameTipoWallet != null){
										NameTipoWallet = NameTipoWallet.toLowerCase();
										if(NameTipoWallet.equals("main balance") || NameTipoWallet.equals("balance")){
											if(registroWall.getChild("cantidad") != null)
												respuesta.setSaldoPrepago(registroWall.getChild("cantidad").getValue());
											else
												respuesta.setSaldoPrepago("");
											if(registroWall.getParent().getContent(3) != null){
												respuesta.setVigenciaSalgoPrepago(registroWall.getParent().getContent(3).getValue());
											}
										}
									}
								}
							}
						}
					}
					//ETAK-fin
					
					//Cy-ini
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("wallets") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("wallets").getChildren();
						it = ChildBalance.iterator();
						
						while(it.hasNext()){
							
							Element registro = (Element) it.next();
							walletBalance = registro.getChildren();
							walletIt = walletBalance.iterator();
							while(walletIt.hasNext()){
								Element registroWall = (Element) walletIt.next();

								if(registroWall.getChild("tipoWallet") != null && registroWall.getChild("tipoWallet").getChild("name") != null){
									NameTipoWallet = registroWall.getChild("tipoWallet").getChild("name").getValue();

									if(NameTipoWallet != null){
										NameTipoWallet = NameTipoWallet.toLowerCase();
										if(NameTipoWallet.equals("main balance") || NameTipoWallet.equals("balance")){
											if(registroWall.getChild("cantidad") != null)
												respuesta.setSaldoPrepago(registroWall.getChild("cantidad").getValue());
											else
												respuesta.setSaldoPrepago("");
											if(registroWall.getParent().getContent(3) != null){
												respuesta.setVigenciaSalgoPrepago(registroWall.getParent().getContent(1).getValue());
											}
										}
									}
								}
							}
						}
					}
					//Cy-fin
					
					//Cy-ini Prepago
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("lineas") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("lineas").getChild("linea") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("lineas").getChild("linea").getChildren();
						it = ChildBalance.iterator();

						while(it.hasNext()){
							Element registro = (Element) it.next();
							if(registro.getName().equals("id")){
								respuesta.setIdPrepago(registro.getValue());
							}
							if(respuesta.getIsPostpagoOrHibrido() == null || respuesta.getIsPostpagoOrHibrido().trim().equals("")){
								if(registro.getName().equals("plan")){
									respuesta.setIdPlan(registro.getValue());
								}
							}
						}
					}
					//Cy-fin Prepago
					
//					TODO: German:[14/06/2017] Se agrega para la tag para el Servicio PRIP en Prepago				
					
					//Cy-ini PRIP Prepago
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("lineas") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("lineas").getChild("linea") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("servicios").getChildren();
						it = ChildBalance.iterator();

						while(it.hasNext()){
							Element registroServ = (Element) it.next();
							serviciosList = registroServ.getChildren();
							serviciosIt = serviciosList.iterator();
							while(serviciosIt.hasNext()){
						
								Element registro = (Element) serviciosIt.next();
								if(registro.getName().equals("fecha_fin")){
									fechaFinServFoca = registro.getValue();
								}
								if(registro.getName().equals("id")){
									for (ServiciosFocalizacionVO servicioFocaTmp : serviciosFoca){
										if(registro.getValue().equals(servicioFocaTmp.getIdServicioPrip())){
											ServiciosFocalizacionVO servicioFocaAux = new ServiciosFocalizacionVO();
											servicioFocaAux.setIdServicioPrip(servicioFocaTmp.getIdServicioPrip());
											servicioFocaAux.setCostoServPrip(servicioFocaTmp.getCostoServPrip());
											servicioFocaAux.setNombreServicioPrip(servicioFocaTmp.getNombreServicioPrip());
											servicioFocaAux.setDescServicioPrip(servicioFocaTmp.getDescServicioPrip());
											servicioFocaAux.setFechaFinServPrip(fechaFinServFoca);
											servicioFocaAux.setVersion(servicioFocaTmp.getVersion());
											servicioFocaAux.setFlagServPRIP(true);
											serviciosFocaFinal.add(servicioFocaAux);
										}
									}
								}
							}
						}
						respuesta.setServiciosFoca(serviciosFocaFinal);
					}
					//Cy-fin Prepago
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					Logger.write("   Detail        : (Exception) " + error);
				}
			}catch(Exception e){
				if(error!=null && !error.equals("")){
					Logger.write("   Detail        : (Exception) " + error);
				}
				else{
					Logger.write("   Detail        : (Exception) " + e.getMessage());
				}
			}
		} catch (Exception e) {
			Logger.write("   Detail        : (Exception) " + e.getMessage());
		}
		return respuesta;
	}
	
	@SuppressWarnings("unchecked")
	public static DetalleFocalizacionVOExtendido parseFocalizacionExtendido(final String resultadoXML) throws ServiceException{
		String error = "";
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		Element elementos = null;
		Element plan = null;
		Element dateBilled = null;
		Element datosBusqueda = null;
		Element numeros = null;
		Element isPostPago = null;
		Element code = null;
		List<Element> childrens = null;
		List<Element> ChildBalance = null;
		List<Element> walletBalance = null;
		Iterator<Element> it = null;
		Iterator<Element> walletIt = null;
		DetalleFocalizacionVOExtendido respuesta = new DetalleFocalizacionVOExtendido();
		OracleProcedures oracle = new OracleProcedures();
		CallServiceServiciosContratos fechaFac = new CallServiceServiciosContratos();
		String NameTipoWallet = "";
		String fechaCorte = "";
		String sResponse = "";
		String v_customerID="";
		Element resultadoBusqueda=null;
		int obtenerCustomerId = 0;
		String dataBySqlDispatcher = "";
		try {
			try{
				dataBySqlDispatcher = oracle.getValorParametro(76);
			}catch (ServiceException e) {
				dataBySqlDispatcher = "0";
			}
			try{
				
				
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return") != null){
					elementos = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return");
					
					if(dataBySqlDispatcher.equals("1")){
						if(elementos.getChild("datosBSCSResponse")!=null){
							if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("resultadoBusqueda")!=null){
								resultadoBusqueda = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("resultadoBusqueda");
								if(resultadoBusqueda.getContentSize()>1){
									obtenerCustomerId = 1;
									List<Element> hijos = null;
									hijos = resultadoBusqueda.getChildren();
									Iterator iter = hijos.iterator();

									while (iter.hasNext()) {
										Element registros = (Element) iter.next();
										if(registros.getName().equals("datosBusqueda")){
											if(registros.getChild("statusContrato").getValue().toLowerCase().equals("s")){
												respuesta.setIdPlan(registros.getChild("tmCode").getValue());
												respuesta.setTmCode(registros.getChild("tmCode").getValue());
												v_customerID = registros.getChild("customerID").getValue();
											}else if(registros.getChild("statusContrato").getValue().toLowerCase().equals("a")){
												respuesta.setIdPlan(registros.getChild("tmCode").getValue());
												respuesta.setTmCode(registros.getChild("tmCode").getValue());
												v_customerID = registros.getChild("customerID").getValue();
												break;
											}
										}
									}
									sResponse = fechaFac.getDatosFacturacion(v_customerID);
									if(sResponse != null && !sResponse.equals("")){
										fechaCorte = ParseXMLFile.ParseDatosFacturacion(sResponse);
										respuesta.setFechaCorte(fechaCorte);
									}
								}
							}
						}
					}
					
					if(elementos.getChild("datosBSCSResponse")!=null){
						if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS")!=null){
							isPostPago = elementos.getChild("datosBSCSResponse").getChild("datosBSCS");
							if(isPostPago!=null && isPostPago.getChild("isPostpagoOrHibrido")!=null){
								respuesta.setIsPostpagoOrHibrido(isPostPago.getChild("isPostpagoOrHibrido").getValue());
							}else{
								respuesta.setIsPostpagoOrHibrido("");
							}
						}
						if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("plan")!= null){
							plan = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("plan");
							if(plan != null && plan.getChild("descripcionPlan") != null){
								respuesta.setDescripcionPlan(plan.getChild("descripcionPlan").getValue());
							}else{
								respuesta.setDescripcionPlan("");
							}
							if(obtenerCustomerId == 0){
								if(plan != null && plan.getChild("plan") != null){
									respuesta.setIdPlan(plan.getChild("plan").getValue());
								}else{
									respuesta.setIdPlan("");
								}
							}
						}
						
						if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("linea")!= null){
							plan = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("linea");
							if(plan != null && plan.getChild("descripcionEquipo") != null){
								respuesta.setIdEquipo(plan.getChild("descripcionEquipo").getValue());
							}else{
								respuesta.setIdEquipo("");
							}
						}
						
						if(obtenerCustomerId == 0){
							if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("serviciosContratados").getChild("serviciosContratados").getChild("csDateBilled")!= null){
								dateBilled = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("serviciosContratados").getChild("serviciosContratados");
								if(dateBilled != null && dateBilled.getChild("csDateBilled") != null){
									respuesta.setFechaCorte(dateBilled.getChild("csDateBilled").getValue().toString());
								}else{
									respuesta.setFechaCorte("");
								}
							}
						}
					}
					//ETAK-inicia
					if(elementos.getChild("datosEtakResponse")!=null){
						if(elementos.getChild("datosEtakResponse").getChild("datosBSCS")!=null){
							isPostPago = elementos.getChild("datosEtakResponse").getChild("datosBSCS");
							if(isPostPago!=null && isPostPago.getChild("isPostpagoOrHibrido")!=null){
								respuesta.setIsPostpagoOrHibrido(isPostPago.getChild("isPostpagoOrHibrido").getValue());
							}else{
								respuesta.setIsPostpagoOrHibrido("");
							}
							if(elementos.getChild("datosEtakResponse").getChild("datosBSCS").getChild("plan")!= null){
								plan = elementos.getChild("datosEtakResponse").getChild("datosBSCS").getChild("plan");
								if(plan != null && plan.getChild("descripcionPlan") != null){
									respuesta.setDescripcionPlan(plan.getChild("descripcionPlan").getValue());
								}else{
									respuesta.setDescripcionPlan("");
								}
								if(plan != null && plan.getChild("plan") != null){
									respuesta.setIdPlan(plan.getChild("plan").getValue());
								}else{
									respuesta.setIdPlan("");
								}
							}
						}
						
					}
					//ETAK-fin
					
					//ETAK-inicia code
					if(elementos.getChild("datosEtakResponse")!=null){
						if(elementos.getChild("datosEtakResponse").getChild("querySubscriberResult")!=null && elementos.getChild("datosEtakResponse").getChild("querySubscriberResult").getChild("values") != null && elementos.getChild("datosEtakResponse").getChild("querySubscriberResult").getChild("values").getChild("pricePlan")!=null &&elementos.getChild("datosEtakResponse").getChild("querySubscriberResult").getChild("values").getChild("pricePlan").getChild("code")!=null){
							code = elementos.getChild("datosEtakResponse").getChild("querySubscriberResult").getChild("values").getChild("pricePlan").getChild("code");
							respuesta.setCode(code.getValue());
						}else{
							respuesta.setCode("");
						}
					}
					//ETAK-fin code
					
					if(elementos.getChild("datosBSCSResponse") != null && elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("resultadoBusqueda").getChild("datosBusqueda")!= null){
						datosBusqueda = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("resultadoBusqueda").getChild("datosBusqueda");
						if(datosBusqueda != null && datosBusqueda.getChild("contrato") != null){
							respuesta.setCoID(datosBusqueda.getChild("contrato").getValue());
						}else{
							respuesta.setCoID("");
						}
						if(obtenerCustomerId == 0){
							if(datosBusqueda != null && datosBusqueda.getChild("tmCode") != null){
								respuesta.setTmCode(datosBusqueda.getChild("tmCode").getValue());
							}else{
								respuesta.setTmCode("");
							}
						}
					}
					//ETAK-ini
					if(elementos.getChild("datosEtakResponse") != null && elementos.getChild("datosEtakResponse").getChild("datosBSCS") != null && elementos.getChild("datosEtakResponse").getChild("datosBSCS").getChild("resultadoBusqueda").getChild("datosBusqueda")!= null){
						datosBusqueda = elementos.getChild("datosEtakResponse").getChild("datosBSCS").getChild("resultadoBusqueda").getChild("datosBusqueda");
						if(datosBusqueda != null && datosBusqueda.getChild("contrato") != null){
							respuesta.setCoID(datosBusqueda.getChild("contrato").getValue());
						}else{
							respuesta.setCoID("");
						}
						if(datosBusqueda != null && datosBusqueda.getChild("tmCode") != null){
							respuesta.setTmCode(datosBusqueda.getChild("tmCode").getValue());
						}else{
							respuesta.setTmCode("");
						}
					}
					//ETAK-fin
					if(obtenerCustomerId == 0){
						if(respuesta.getIsPostpagoOrHibrido() != null && !respuesta.getIsPostpagoOrHibrido().equals("") && !respuesta.getIsPostpagoOrHibrido().toLowerCase().equals("prepago")){
							if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("tresUltimasFacturas")!= null){
								plan = elementos.getChild("datosBSCSResponse").getChild("datosBSCS").getChild("tresUltimasFacturas").getChild("tresUltimasFacturas");
								if(plan != null && plan.getChild("fechaCorte") != null){
									respuesta.setFechaCorte(plan.getChild("fechaCorte").getValue().toString());
								}else{
									if(respuesta.getFechaCorte()!=null && respuesta.getFechaCorte().equals(""))
										respuesta.setFechaCorte("");
								}
							}
						}
					}
					
					//ETAK-ini
					if(respuesta.getIsPostpagoOrHibrido() != null && !respuesta.getIsPostpagoOrHibrido().equals("") && !respuesta.getIsPostpagoOrHibrido().toLowerCase().equals("prepago")){
						if(elementos.getChild("datosEtakResponse") != null && elementos.getChild("datosEtakResponse").getChild("datosBSCS").getChild("tresUltimasFacturas")!= null){
							plan = elementos.getChild("datosEtakResponse").getChild("datosBSCS").getChild("tresUltimasFacturas").getChild("tresUltimasFacturas");
							if(plan != null && plan.getChild("fechaCorte") != null){
								respuesta.setFechaCorte(plan.getChild("fechaCorte").getValue().toString());
							}else{
								if(respuesta.getFechaCorte()!=null && respuesta.getFechaCorte().equals(""))
									respuesta.setFechaCorte("");
							}
						}
					}
					
					if(respuesta.getFechaCorte() == null || respuesta.getFechaCorte().equals("")){
						String dateTimeCorte = "";
						if(respuesta.getIsPostpagoOrHibrido() != null && !respuesta.getIsPostpagoOrHibrido().equals("") && !respuesta.getIsPostpagoOrHibrido().toLowerCase().equals("prepago")){
							if(elementos.getChild("datosBSCSResponse").getChild("datosBSCS")!= null){
								plan = elementos.getChild("datosBSCSResponse").getChild("datosBSCS");
								if(plan != null && plan.getChild("diaCicloFacturacion") != null){
									dateTimeCorte = (Calendar.getInstance().get(Calendar.YEAR) + "-" + (Calendar.getInstance().get(Calendar.MONTH)+1) + "-" + plan.getChild("diaCicloFacturacion").getValue().toString() + " 00:00:00.0");
									respuesta.setFechaCorte(dateTimeCorte);
								}else{
									if(respuesta.getFechaCorte()!=null && respuesta.getFechaCorte().equals(""))
										respuesta.setFechaCorte("");
								}
							}
						}
					}
					
					//ETAK-fin
					
					if(elementos.getChild("datosNumerosResponse").getChild("numeros")!= null){
						numeros = elementos.getChild("datosNumerosResponse").getChild("numeros");
						
						if(numeros != null && numeros.getChild("idFormaPago")!=null){
							respuesta.setIdFormaPago(numeros.getChild("idFormaPago").getValue());
						}else{
							respuesta.setIdFormaPago("");
						}
						
						if(numeros != null && numeros.getChild("idOperador")!=null){
							respuesta.setIdOperador(numeros.getChild("idOperador").getValue());
						}else{
							respuesta.setIdOperador("");
						}
						
						if(numeros != null && numeros.getChild("modalidad")!=null){
							respuesta.setModalidad(numeros.getChild("modalidad").getValue());
						}else{
							respuesta.setModalidad("");
						}
						
						if(numeros != null && numeros.getChild("servicio")!=null){
							respuesta.setServicio(numeros.getChild("servicio").getValue());
						}else{
							respuesta.setServicio("");
						}
						if(numeros != null && numeros.getChild("subservicio")!=null){
							respuesta.setSubservicio(numeros.getChild("subservicio").getValue());
						}else{
							respuesta.setSubservicio("");
						}
						if(numeros != null && numeros.getChild("tecnologia")!=null){
							respuesta.setTecnologia(numeros.getChild("tecnologia").getValue());
						}else{
							respuesta.setTecnologia("");
						}
					}
										
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("wallets") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("wallets").getChildren();
						it = ChildBalance.iterator();
						
						while(it.hasNext()){
							Element registro = (Element) it.next();
							if(registro.getChild("wallet").getChild("tipoWallet").getChild("name") != null)
								NameTipoWallet = registro.getChild("wallet").getChild("tipoWallet").getChild("name").getValue();

							if(NameTipoWallet != null){
								NameTipoWallet = NameTipoWallet.toLowerCase();
								if(NameTipoWallet.equals("main balance") || NameTipoWallet.equals("balance"))
									if(registro.getChild("wallet").getChild("cantidad") != null)
										respuesta.setTiempoAireComprado(registro.getChild("wallet").getChild("cantidad").getValue());
									else
										respuesta.setTiempoAireComprado("");
							}
						}
					}
					
					//ETAK-ini
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosEtakResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosEtakResponse").getChild("walletsEtak") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosEtakResponse").getChild("walletsEtak").getChildren();
						it = ChildBalance.iterator();
						
						while(it.hasNext()){
							
							Element registro = (Element) it.next();
							walletBalance = registro.getChildren();
							walletIt = walletBalance.iterator();
							while(walletIt.hasNext()){
								Element registroWall = (Element) walletIt.next();

								if(registroWall.getChild("tipoWallet") != null && registroWall.getChild("tipoWallet").getChild("name") != null){
									NameTipoWallet = registroWall.getChild("tipoWallet").getChild("name").getValue();

									if(NameTipoWallet != null){
										NameTipoWallet = NameTipoWallet.toLowerCase();
										if(NameTipoWallet.equals("main balance") || NameTipoWallet.equals("balance")){
											if(registroWall.getChild("cantidad") != null)
												respuesta.setSaldoPrepago(registroWall.getChild("cantidad").getValue());
											else
												respuesta.setSaldoPrepago("");
											if(registroWall.getParent().getContent(3) != null){
												respuesta.setVigenciaSalgoPrepago(registroWall.getParent().getContent(3).getValue());
											}
										}
									}
								}
							}
						}
					}
					//ETAK-fin
					
					//Cy-ini
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("wallets") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("wallets").getChildren();
						it = ChildBalance.iterator();
						
						while(it.hasNext()){
							
							Element registro = (Element) it.next();
							walletBalance = registro.getChildren();
							walletIt = walletBalance.iterator();
							while(walletIt.hasNext()){
								Element registroWall = (Element) walletIt.next();

								if(registroWall.getChild("tipoWallet") != null && registroWall.getChild("tipoWallet").getChild("name") != null){
									NameTipoWallet = registroWall.getChild("tipoWallet").getChild("name").getValue();

									if(NameTipoWallet != null){
										NameTipoWallet = NameTipoWallet.toLowerCase();
										if(NameTipoWallet.equals("main balance") || NameTipoWallet.equals("balance")){
											if(registroWall.getChild("cantidad") != null)
												respuesta.setSaldoPrepago(registroWall.getChild("cantidad").getValue());
											else
												respuesta.setSaldoPrepago("");
											if(registroWall.getParent().getContent(3) != null){
												respuesta.setVigenciaSalgoPrepago(registroWall.getParent().getContent(1).getValue());
											}
										}
									}
								}
							}
						}
					}
					//Cy-fin
					
					//Cy-ini Prepago
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("lineas") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("lineas").getChild("linea") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("lineas").getChild("linea").getChildren();
						it = ChildBalance.iterator();

						while(it.hasNext()){
							Element registro = (Element) it.next();
							if(registro.getName().equals("id")){
								respuesta.setIdPrepago(registro.getValue());
							}
							if(respuesta.getIsPostpagoOrHibrido() == null || respuesta.getIsPostpagoOrHibrido().trim().equals("")){
								if(registro.getName().equals("plan")){
									respuesta.setIdPlan(registro.getValue());
								}
							}
						}
					}
					//Cy-fin Prepago
					
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					Logger.write("   Detail        : (Exception) " + error);
				}
			}catch(Exception e){
				if(error!=null && !error.equals("")){
					Logger.write("   Detail        : (Exception) " + error);
				}
				else{
					Logger.write("   Detail        : (Exception) " + e.getMessage());
				}
			}
		} catch (Exception e) {
			Logger.write("   Detail        : (Exception) " + e.getMessage());
		}
		return respuesta;
	}
	
	@SuppressWarnings("unchecked")
	public static String parseFocalizacionStatusLinea(final String resultadoXML) throws ServiceException{
		String error = "";
		String statusLinea = "";
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		List<Element> ChildBalance = null;
		Iterator<Element> it = null;
		try {

			try{
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return") != null){
					//Cy-ini Prepago
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("lineas") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("lineas").getChild("linea") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("lineas").getChild("linea").getChildren();
						it = ChildBalance.iterator();

						while(it.hasNext()){
							Element registro = (Element) it.next();
							if(registro.getName().equals("status")){
								statusLinea = registro.getValue();
							}
						}
					}
					//Cy-fin Prepago
					
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					Logger.write("   Detail        : (Exception) " + error);
				}
			}catch(Exception e){
				if(error!=null && !error.equals("")){
					Logger.write("   Detail        : (Exception) " + error);
				}
				else{
					Logger.write("   Detail        : (Exception) " + e.getMessage());
				}
			}
		} catch (Exception e) {
			Logger.write("   Detail        : (Exception) " + e.getMessage());
		}
		return statusLinea;
	}
	
	public static String parseStatusFocalizacion(final String resultadoXML) throws ServiceException{
		Pattern pattern = null;
		Matcher matcher = null;
		String status = "d";
		
		try {
			 pattern = Pattern.compile("<statusRobo>(.+?)</statusRobo>");
			 matcher = pattern.matcher(resultadoXML);
			 if(matcher.find()){
				 status = matcher.group(1);
			 }
			 
			 pattern = Pattern.compile("<id>2032</id>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				status = "a";
			 }
			 
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return status;
	}
	
	public static String parseStatusFocalizacionEtak(String resultadoXML) throws ServiceException{
		Pattern pattern = null;
		Matcher matcher = null;
		String status = "d";
		
		try {
			 pattern = Pattern.compile("<status><code>(.*)</code><description>(.+?)</description></status>");
			 matcher = pattern.matcher(resultadoXML);
			 if(matcher.find()){
				 if(matcher.group(2) != null)
					 status = matcher.group(2);
			 }
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return status;
	}
	
	@SuppressWarnings("unchecked")
	public static DatosSQLDispatcherVO parseCustomerIdCustCode(String resultadoXML) throws ServiceException{
		DatosSQLDispatcherVO response = new DatosSQLDispatcherVO();
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String error = "";
		String statusContrato = "";
		String statusCuenta = "";
		
		try{
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getResultadoBusquedaResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")) != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getResultadoBusquedaResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getResultadoBusquedaReturn") != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getResultadoBusquedaResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getResultadoBusquedaReturn").getChild("datosBusqueda").getChildren();
				it = childrens.iterator();

				while(it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getChild("statusContrato") != null){
						statusContrato = registro.getChild("statusContrato").getValue();
					}
					if(registro.getChild("statusCuenta") != null){
						statusCuenta = registro.getChild("statusCuenta").getValue();
					}
					if(statusContrato.equals("a") && statusCuenta.equals("a")){
						if(registro.getChild("custCode") != null){
							response.setCustCode(registro.getChild("custCode").getValue());
						}
						if(registro.getChild("customerID") != null){
							response.setCustomerId(registro.getChild("customerID").getValue());
						}
						break;
					}
				}
			}
			else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getResultadoBusquedaResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				Logger.write("   Detail        : (Exception) " + error);
			}
		}catch(Exception e){
			if(error!=null && !error.equals("")){
				Logger.write("   Detail        : (Exception) " + error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
			}
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public static String parseGetDatosCliente(String resultadoXML) throws ServiceException{
		String customerIdHigh = "";
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String error = "";
		String dirEnvioFiscal = "";
		
		try{
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDatosClienteResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")) != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDatosClienteResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getDatosClienteReturn") != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDatosClienteResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getDatosClienteReturn").getChild("datosCliente").getChildren();
				it = childrens.iterator();

				while(it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getChild("dirEnvioFiscal") != null){
						dirEnvioFiscal = registro.getChild("dirEnvioFiscal").getValue();
					}
					if(dirEnvioFiscal.equals("FISCAL")){
						if(registro.getChild("customerIdHigh") != null){
							customerIdHigh = registro.getChild("customerIdHigh").getValue();
						}
					}
				}
			}
			else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getResultadoBusquedaResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				Logger.write("   Detail        : (Exception) " + error);
			}
		}catch(Exception e){
			if(error!=null && !error.equals("")){
				Logger.write("   Detail        : (Exception) " + error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
			}
		}
		return customerIdHigh;
	}
	
	@SuppressWarnings("unchecked")
	public static DetalleWalletsVO parseWallets(String resultadoXML) throws ServiceException{
		String error = "";
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		List<Element> ChildBalance = null;
		List<Element> walletBalance = null;
		Iterator<Element> it = null;
		Iterator<Element> walletIt = null;
		DetalleWalletsVO respuesta = new DetalleWalletsVO();
		String NameTipoWallet = "";
		DecimalFormat formatNumber = new DecimalFormat("#.##");
		double pivote = 0;
		double tiempoAireDeRegalo = 0;
		double tiempoAireDeTotal = 0;
		double minutosDeRegalo = 0;
		double mensajesDeTexto = 0;
		double navegacion = 0;
		
		try {

			try{
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return") != null){
										
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("wallets") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("wallets").getChildren();
						it = ChildBalance.iterator();
						
						while(it.hasNext()){
							Element registro = (Element) it.next();
							if(registro.getChild("wallet").getChild("tipoWallet").getChild("name") != null)
								NameTipoWallet = registro.getChild("wallet").getChild("tipoWallet").getChild("name").getValue();

							if(NameTipoWallet != null){
								NameTipoWallet = NameTipoWallet.toLowerCase();
								if(NameTipoWallet.equals("main balance") || NameTipoWallet.equals("balance"))
									if(registro.getChild("wallet").getChild("cantidad") != null){}
//										respuesta.setTiempoAireComprado(registro.getChild("wallet").getChild("cantidad").getValue());
//									else
//										respuesta.setTiempoAireComprado("");
							}
						}
					}
					
					//ETAK-ini
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosEtakResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosEtakResponse").getChild("walletsEtak") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosEtakResponse").getChild("walletsEtak").getChildren();
						it = ChildBalance.iterator();
						
						while(it.hasNext()){
							
							Element registro = (Element) it.next();
							walletBalance = registro.getChildren();
							walletIt = walletBalance.iterator();
							while(walletIt.hasNext()){
								Element registroWall = (Element) walletIt.next();

								if(registroWall.getChild("tipoWallet") != null && registroWall.getChild("tipoWallet").getChild("name") != null){
									NameTipoWallet = registroWall.getChild("tipoWallet").getChild("name").getValue();

									if(NameTipoWallet != null){
										NameTipoWallet = NameTipoWallet.toLowerCase();
										
										//TIEMPO AIRE
										if(NameTipoWallet.equals("main balance") || NameTipoWallet.equals("balance")){
											if(registroWall.getChild("cantidad") != null){
												try{
													if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
														pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
														tiempoAireDeTotal += pivote;
													}else{
														tiempoAireDeTotal += 0;
													}
												}catch (NumberFormatException e) {
													tiempoAireDeTotal += 0;
												}
											}
										}
										
										if(NameTipoWallet.equals("freefree")){
											if(registroWall.getChild("cantidad") != null){
												try{
													if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
														pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
														tiempoAireDeRegalo = pivote;
														tiempoAireDeTotal += pivote;
													}else{
														tiempoAireDeRegalo = 0;
														tiempoAireDeTotal += 0;
													}
												}catch (NumberFormatException e) {
													tiempoAireDeTotal += 0;
												}
											}
										}
										
										if(NameTipoWallet.equals("promotion_balance")){
											if(registroWall.getChild("cantidad") != null){
												try{
													if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
														pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
//														tiempoAireDeRegalo += pivote;
														tiempoAireDeTotal += pivote;
													}else{
//														tiempoAireDeRegalo += 0;
														tiempoAireDeTotal += 0;
													}
												}catch (NumberFormatException e) {
//													tiempoAireDeRegalo += 0;
													tiempoAireDeTotal += 0;
												}
											}
										}
										
										//MINUTOS
										if(NameTipoWallet.equals("time_local")){
											if(registroWall.getChild("cantidad") != null){
												try{
													if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
														pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
														minutosDeRegalo += pivote;
													}else{
														minutosDeRegalo += 0;
													}
												}catch (NumberFormatException e) {
													minutosDeRegalo += 0;
												}
											}
										}
										
										if(NameTipoWallet.equals("time_friends_family")){
											if(registroWall.getChild("cantidad") != null){
												try{
													if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
														pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
														minutosDeRegalo += pivote;
													}else{
														minutosDeRegalo += 0;
													}
												}catch (NumberFormatException e) {
													minutosDeRegalo += 0;
												}
											}
										}
										
										if(NameTipoWallet.equals("freetime")){
											if(registroWall.getChild("cantidad") != null){
												try{
													if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
														pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
														minutosDeRegalo += pivote;
													}else{
														minutosDeRegalo += 0;
													}
												}catch (NumberFormatException e) {
													minutosDeRegalo += 0;
												}
											}
										}
										
										if(NameTipoWallet.equals("time_nacional")){
											if(registroWall.getChild("cantidad") != null){
												try{
													if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
														pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
														minutosDeRegalo += pivote;
													}else{
														minutosDeRegalo += 0;
													}
												}catch (NumberFormatException e) {
													minutosDeRegalo += 0;
												}
											}
										}
										
										if(NameTipoWallet.equals("pktllamen_minutos")){
											if(registroWall.getChild("cantidad") != null){
												try{
													if(registroWall.getChild("minutos de regalo pktllamen").getValue() != null && !registroWall.getChild("Minutos de regalo pktllamen").getValue().equals("")){
														if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
															pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
															minutosDeRegalo += pivote;
														}else{
															minutosDeRegalo += 0;
														}
													}
												}catch (NumberFormatException e) {
													minutosDeRegalo += 0;
												}
											}
										}
										
										//MENSAJES
										if(NameTipoWallet.equals("free_sms_onnet")){
											if(registroWall.getChild("cantidad") != null){
												try{
													if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
														pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
														mensajesDeTexto += pivote;
													}else{
														mensajesDeTexto += 0;
													}
												}catch (NumberFormatException e) {
													mensajesDeTexto += 0;
												}
											}
										}
										
										if(NameTipoWallet.equals("pktllamen_minutos")){
											if(registroWall.getChild("cantidad") != null){
												try{
													if(registroWall.getChild("mensajes de regalo pktllamen").getValue() != null && !registroWall.getChild("Minutos de regalo pktllamen").getValue().equals("")){
														if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
															pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
															mensajesDeTexto += pivote;
														}else{
															mensajesDeTexto += 0;
														}
													}
												}catch (NumberFormatException e) {
													mensajesDeTexto += 0;
												}
											}
										}
										
										//NAVEGACION
										if(NameTipoWallet.equals("pktllamen_datos")){
											if(registroWall.getChild("cantidad") != null){
												try{
													if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
														pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
														navegacion += pivote;
													}else{
														navegacion += 0;
													}
												}catch (NumberFormatException e) {
													navegacion += 0;
												}
											}
										}
									}
								}
							}
						}
					}
					//ETAK-fin
					
					//Cy-ini
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("wallets") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosCyCResponse").getChild("wallets").getChildren();
						it = ChildBalance.iterator();
						
						while(it.hasNext()){
							
							Element registro = (Element) it.next();
							walletBalance = registro.getChildren();
							walletIt = walletBalance.iterator();
							while(walletIt.hasNext()){
								Element registroWall = (Element) walletIt.next();

								if(registroWall.getChild("tipoWallet") != null && registroWall.getChild("tipoWallet").getChild("name") != null){
									NameTipoWallet = registroWall.getChild("tipoWallet").getChild("name").getValue();

									if(NameTipoWallet != null){
										NameTipoWallet = NameTipoWallet.toLowerCase();
										if(NameTipoWallet.equals("main balance") || NameTipoWallet.equals("balance")){
											if(registroWall.getChild("cantidad") != null)
//												respuesta.setSaldoPrepago(registroWall.getChild("cantidad").getValue());
//											else
//												respuesta.setSaldoPrepago("");
											if(registroWall.getParent().getContent(3) != null){
//												respuesta.setVigenciaSalgoPrepago(registroWall.getParent().getContent(1).getValue());
											}
										}
									}
								}
								
								//TIEMPO AIRE
								if(NameTipoWallet.equals("main balance") || NameTipoWallet.equals("balance")){
									if(registroWall.getChild("cantidad") != null){
										try{
											if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
												pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
												tiempoAireDeTotal += pivote;
											}else{
												tiempoAireDeTotal += 0;
											}
										}catch (NumberFormatException e) {
											tiempoAireDeTotal += 0;
										}
									}
								}
								
								if(NameTipoWallet.equals("promotion_balance")){
									if(registroWall.getChild("cantidad") != null){
										try{
											if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
												pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
//												tiempoAireDeRegalo += pivote;
												tiempoAireDeTotal += pivote;
											}else{
//												tiempoAireDeRegalo += 0;
												tiempoAireDeTotal += 0;
											}
										}catch (NumberFormatException e) {
//											tiempoAireDeRegalo += 0;
											tiempoAireDeTotal += 0;
										}
									}
								}
								
								if(NameTipoWallet.equals("freefree")){
									if(registroWall.getChild("cantidad") != null){
										try{
											if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
												pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
												tiempoAireDeRegalo = pivote;
												tiempoAireDeTotal += pivote;
											}else{
												tiempoAireDeRegalo = 0;
												tiempoAireDeTotal += 0;
											}
										}catch (NumberFormatException e) {
											tiempoAireDeTotal += 0;
										}
									}
								}
								
								//MINUTOS
								if(NameTipoWallet.equals("time_friends_family")){
									if(registroWall.getChild("cantidad") != null){
										try{
											if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
												pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
												minutosDeRegalo += pivote;
											}else{
												minutosDeRegalo += 0;
											}
										}catch (NumberFormatException e) {
											minutosDeRegalo += 0;
										}
									}
								}
								
								if(NameTipoWallet.equals("time_local")){
									if(registroWall.getChild("cantidad") != null){
										try{
											if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
												pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
												minutosDeRegalo += pivote;
											}else{
												minutosDeRegalo += 0;
											}
										}catch (NumberFormatException e) {
											minutosDeRegalo += 0;
										}
									}
								}
								
								if(NameTipoWallet.equals("pktllamen_minutos")){
									if(registroWall.getChild("cantidad") != null){
										try{
											if(registroWall.getChild("minutos de regalo pktllamen").getValue() != null && !registroWall.getChild("Minutos de regalo pktllamen").getValue().equals("")){
												if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
													pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
													minutosDeRegalo += pivote;
												}else{
													minutosDeRegalo += 0;
												}
											}
										}catch (NumberFormatException e) {
											minutosDeRegalo += 0;
										}
									}
								}
								
								//MENSAJES
								if(NameTipoWallet.equals("free_sms_onnet")){
									if(registroWall.getChild("cantidad") != null){
										try{
											if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
												pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
												mensajesDeTexto += pivote;
											}else{
												mensajesDeTexto += 0;
											}
										}catch (NumberFormatException e) {
											mensajesDeTexto += 0;
										}
									}
								}
								
								if(NameTipoWallet.equals("freesms")){
									if(registroWall.getChild("cantidad") != null){
										try{
											if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
												pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
												mensajesDeTexto += pivote;
											}else{
												mensajesDeTexto += 0;
											}
										}catch (NumberFormatException e) {
											mensajesDeTexto += 0;
										}
									}
								}
								
								if(NameTipoWallet.equals("pktllamen_minutos")){
									if(registroWall.getChild("cantidad") != null){
										try{
											if(registroWall.getChild("mensajes de regalo pktllamen").getValue() != null && !registroWall.getChild("Minutos de regalo pktllamen").getValue().equals("")){
												if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
													pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
													mensajesDeTexto += pivote;
												}else{
													mensajesDeTexto += 0;
												}
											}
										}catch (NumberFormatException e) {
											mensajesDeTexto += 0;
										}
									}
								}
								//NAVEGACION
								if(NameTipoWallet.equals("pktllamen_datos")){
									if(registroWall.getChild("cantidad") != null){
										try{
											if(registroWall.getChild("cantidad").getValue() != null && !registroWall.getChild("cantidad").getValue().equals("")){
												pivote = Double.parseDouble(registroWall.getChild("cantidad").getValue());
												navegacion += pivote;
											}else{
												navegacion += 0;
											}
										}catch (NumberFormatException e) {
											navegacion += 0;
										}
									}
								}
							}
						}
					}
					//Cy-fin
					
					respuesta.setTiempoAireDeRegalo(formatNumber.format(tiempoAireDeRegalo));
					respuesta.setTiempoAireTotal(formatNumber.format(tiempoAireDeTotal));
					respuesta.setMensajesDeTexto(formatNumber.format(mensajesDeTexto));
					respuesta.setNavegacion(formatNumber.format(navegacion));
					respuesta.setMinutosDeRegalo(formatNumber.format(minutosDeRegalo));
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDetalleLlamadaBicResponse", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.detalleconsumo.telco.iusacell.com")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					Logger.write("   Detail        : (Exception) " + error);
				}
			}catch(Exception e){
				if(error!=null && !error.equals("")){
					Logger.write("   Detail        : (Exception) " + error);
				}
				else{
					Logger.write("   Detail        : (Exception) " + e.getMessage());
				}
			}
		} catch (Exception e) {
			Logger.write("   Detail        : (Exception) " + e.getMessage());
		}
		return respuesta;
	}

	@SuppressWarnings("unchecked")
	public static ContratarServiciosVO parseContratarServ(final String resultadoXML) throws ServiceException{

		String error = "";
		ContratarServiciosVO contratarServicios = new ContratarServiciosVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;
			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("altaServiciosResponse", Namespace.getNamespace("http://ws.altaservicios.telco.iusacell.com")) != null){
			  childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("altaServiciosResponse", Namespace.getNamespace("http://ws.altaservicios.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.altaservicios.telco.iusacell.com")).getChildren(); 
			  it = childrens.iterator();
			  while (it.hasNext()){
				  Element registro = (Element) it.next();
				  if(registro.getName().equals("messageCode"))
				  contratarServicios.setMessageCode(registro.getValue());
				  
				  if(registro.getName().equals("operationCode"))
				  contratarServicios.setOperationCode(registro.getValue());
			  }
			}
			else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				 while (it.hasNext()) {
					 Element registro = (Element) it.next();
					 error += ((" ")+registro.getValue());
				 }
				 throw new ServiceException(error);
			}
			
		} catch (Exception e) {
			throw new ServiceException(error);
		}
		return contratarServicios;
	}
	
	@SuppressWarnings("unchecked")
	public static ContratarServiciosVO parseBajarServ(final String resultadoXML) throws ServiceException{

		String error = "";
		ContratarServiciosVO contratarServicios = new ContratarServiciosVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;
			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("bajaServiciosResponse", Namespace.getNamespace("http://ws.telco.iusacell.com")) != null){
			  childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("bajaServiciosResponse", Namespace.getNamespace("http://ws.telco.iusacell.com")).getChild("return", Namespace.getNamespace("http://ws.telco.iusacell.com")).getChildren(); 
			  it = childrens.iterator();
			  while (it.hasNext()){
				  Element registro = (Element) it.next();
				  if(registro.getName().equals("messageCode"))
				  contratarServicios.setMessageCode(registro.getValue());
				  
				  if(registro.getName().equals("operationCode"))
				  contratarServicios.setOperationCode(registro.getValue());
			  }
			}
			else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				 while (it.hasNext()) {
					 Element registro = (Element) it.next();
					 error += ((" ")+registro.getValue());
				 }
				 throw new ServiceException(error);
			}
			
		} catch (Exception e) {
			throw new ServiceException(error);
		}
		return contratarServicios;
	}
	
	@SuppressWarnings("unchecked")
	public static ContratarServiciosVO parseContratarServLegacy(String resultadoXML) throws ServiceException{

		String error = "";
		ContratarServiciosVO contratarServicios = new ContratarServiciosVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("altaBajaServiciosResponse", Namespace.getNamespace("http://ws.serviciosBSCS.middleware.iusacell.com")) != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("altaBajaServiciosResponse", Namespace.getNamespace("http://ws.serviciosBSCS.middleware.iusacell.com")).getChild("altaBajaServiciosReturn", Namespace.getNamespace("http://ws.serviciosBSCS.middleware.iusacell.com")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("altaBajaServiciosResponse", Namespace.getNamespace("http://ws.serviciosBSCS.middleware.iusacell.com")).getChild("altaBajaServiciosReturn", Namespace.getNamespace("http://ws.serviciosBSCS.middleware.iusacell.com")).getChildren(); 
				it = childrens.iterator();
				while (it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getName().equals("msgError")){
						if(registro != null && registro.getValue() != null){
							if(registro.getValue().toLowerCase().equals("exito")){
								contratarServicios.setMessageCode(registro.getValue());
								contratarServicios.setOperationCode("0");
							}else{
								throw new ServiceException(registro.getValue());
							}
						}
					}
				}
			}
			else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}

		} catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else if(e != null && e.getMessage() != null){
				throw new ServiceException(e.getMessage());
			}
			else{
				throw new ServiceException("Ocurrio un error al contratar el servicio parseo");
			}
		}
		return contratarServicios;
	}

	@SuppressWarnings("unchecked")
	public static AltaCitaVO parseAltaCita(final String resultadoXML) throws ServiceException{
		String error = "";
		AltaCitaVO altacitaVo = new AltaCitaVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("altaCitaResponse", Namespace.getNamespace("http://services.citas.iusacell.com.mx")) != null){

					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("altaCitaResponse", Namespace.getNamespace("http://services.citas.iusacell.com.mx")).getChild("out",Namespace.getNamespace("http://services.citas.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()){
						Element registros = (Element) it.next();
						if(registros.getName().equals("exito"))
							altacitaVo.setExito(registros.getValue());
						if(registros.getName().equals("mensaje"))
							altacitaVo.setMensaje(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar la cita");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return altacitaVo;
	}
	
	@SuppressWarnings("unchecked")
	public static List<CitasDisponiblesXHorario> parseHorarioDisponibleXcae(final String resultadoXML) throws ServiceException{
		String error = "";
		CitasXHorarioVO citasSingle = new CitasXHorarioVO();
		List<CitasXHorarioVO> citasList = new ArrayList<CitasXHorarioVO>();
		List<CitasDisponiblesXHorario> citasDispList = new ArrayList<CitasDisponiblesXHorario>();
		CitasDisponiblesXHorario citasDispSingle = new CitasDisponiblesXHorario();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			List<Element> childrensCitas = null;
			Iterator<Element> it = null;
			Iterator<Element> itCitas = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getHorarioDisponblesXCAEResponse", Namespace.getNamespace("http://services.citas.iusacell.com.mx")) != null){
				
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getHorarioDisponblesXCAEResponse",Namespace.getNamespace("http://services.citas.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://services.citas.iusacell.com.mx")).getChildren(); 
				it = childrens.iterator();
				while(it.hasNext()){
					Element registro = (Element) it.next();
					citasList = new ArrayList<CitasXHorarioVO>();
					childrensCitas = registro.getChild("citasDisponiblesXHorario", Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getChildren();
					itCitas = childrensCitas.iterator();
					while(itCitas.hasNext()){
						citasSingle = new CitasXHorarioVO();
						Element registroCitas = (Element) itCitas.next();
						citasSingle.setDisponibilidad(registroCitas.getChild("disponibilidad", Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
						citasSingle.setIdHorario(registroCitas.getChild("idHorario", Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
						citasSingle.setHorario(registroCitas.getChild("horario", Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
						citasList.add(citasSingle);
					}
					citasDispSingle = new CitasDisponiblesXHorario();
					citasDispSingle.setFecha(registro.getChild("fecha", Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
					citasDispSingle.setHorarios(citasList);
					citasDispList.add(citasDispSingle);
				}
			}
			else if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("detail") != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("detail").getChild("IObtieneServiciosSGestionException", Namespace.getNamespace("http://ws.integracion.telco.iusacell.com.mx")).getChild("SGestionException", Namespace.getNamespace("http://ws.integracion.telco.iusacell.com.mx")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("detail").getChild("IObtieneServiciosSGestionException", Namespace.getNamespace("http://ws.integracion.telco.iusacell.com.mx")).getChild("SGestionException", Namespace.getNamespace("http://ws.integracion.telco.iusacell.com.mx")).getChildren();
				it = childrens.iterator();
				 while (it.hasNext()) {
					 Element registro = (Element) it.next();
					 error += ((" ")+registro.getValue());
				 }
				 throw new ServiceException(error);
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}
			
		} catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				throw new ServiceException("Ocurrio un error al obtener los horarios");
			}
		}
		return citasDispList;
	}
	
	@SuppressWarnings("unchecked")
	public static AbonoTiempoAireVO TiempoAireETAK(final String resultadoXML) throws ServiceException{
		String error = "";
		AbonoTiempoAireVO tiempoAireVo = new AbonoTiempoAireVO();
		
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("abonoDNResponse", Namespace.getNamespace("http://ws.et.telco.iusacell.com.mx")) != null){

					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("abonoDNResponse", Namespace.getNamespace("http://ws.et.telco.iusacell.com.mx")).getChild("return",Namespace.getNamespace("http://ws.et.telco.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()){
						Element registros = (Element) it.next();
						if(registros.getName().equals("codigoRespuesta"))
							tiempoAireVo.setCodigoRespuestaAbonoTA(registros.getValue());
						if(registros.getName().equals("numeroAutorizacion"))
							tiempoAireVo.setNumeroAutorizacionAbonoTA(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar la cita");
				}
			}
		} catch (Exception e) {
			Logger.write("   Detail        : (Exception) " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return tiempoAireVo;
	}
	
	@SuppressWarnings("unchecked")
	public static AbonoTiempoAireVO TiempoAireLegacy(final String resultadoXML) throws ServiceException{
		String error = "";
		AbonoTiempoAireVO tiempoAireVo = new AbonoTiempoAireVO();
		
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("abonoDNResponse", Namespace.getNamespace("http://bean.recargase.iusacell.com")) != null){

					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("abonoDNResponse", Namespace.getNamespace("http://bean.recargase.iusacell.com")).getChild("abonoDNReturn").getChildren();
					it = childrens.iterator();
					while (it.hasNext()){
						Element registros = (Element) it.next();
						if(registros.getName().equals("codigoRespuesta"))
							tiempoAireVo.setCodigoRespuestaAbonoTA(registros.getValue());
						if(registros.getName().equals("numeroAutorizacion"))
							tiempoAireVo.setNumeroAutorizacionAbonoTA(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar el abono de TA");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return tiempoAireVo;
	}
	
	@SuppressWarnings("unchecked")
	public static ReprogramacionVO Parsereprogramacion(final String resultadoXML) throws ServiceException{
		String error = "";
		ReprogramacionVO reprogramacionVo = new ReprogramacionVO();
		
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("reprogramacionResponse", Namespace.getNamespace("http://bus.core.iusacell.com/ws")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("reprogramacionResponse", Namespace.getNamespace("http://bus.core.iusacell.com/ws")).getChild("response").getChildren();
				    
					it = childrens.iterator();
					
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("codigoRespuesta"))
							 reprogramacionVo.setCodigoRespuesta(registros.getValue());
						 if(registros.getName().equals("dn"))
							 reprogramacionVo.setDn(registros.getValue());
						 if(registros.getName().equals("mensaje"))
							 reprogramacionVo.setMensaje(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar Parsereprogramacion");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return reprogramacionVo;
	}	
	
	@SuppressWarnings("unchecked")
	public static String ParseCambioMdn(final String resultadoXML) throws ServiceException{
		String error = "";
		String respuesta = "";
		
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("reprogramacionResponse", Namespace.getNamespace("http://bus.core.iusacell.com/ws")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("reprogramacionResponse", Namespace.getNamespace("http://bus.core.iusacell.com/ws")).getChild("response").getChildren();
				    
					
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar ParseCambioMdn");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return respuesta;
	}
	
	@SuppressWarnings("unchecked")
	public static DNSugeridoVO ParseDnSugerido(final String resultadoXML) throws ServiceException{
		String error = "";
		DNSugeridoVO sugeridoVo = new DNSugeridoVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;
			List<Element> childrensDn = null;
			Iterator<Element> itDn = null;
			List<String> dnSugerido = new ArrayList<String>();

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("obtenMDNResponse", Namespace.getNamespace("http://service.cambiodn.telco.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("obtenMDNResponse", Namespace.getNamespace("http://service.cambiodn.telco.iusacell.com.mx")).getChild("return", Namespace.getNamespace("http://service.cambiodn.telco.iusacell.com.mx")).getChildren();
				    
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("descLocalidad"))
							 sugeridoVo.setDescLocalidad(registros.getValue());
						 
						 if(registros.getName().equals("dnSugerido")){
							childrensDn = registros.getChildren();
							itDn = childrensDn.iterator();
							while(itDn.hasNext()){
								Element dnSug = (Element) itDn.next();
								if(dnSug != null)
									dnSugerido.add(dnSug.getValue().toString());
							}
							sugeridoVo.setSugeridoDn(dnSugerido);
						 }
						  
						 if(registros.getName().equals("localidad"))
							 sugeridoVo.setLocalidad(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar DNSugerido");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		finally{
			Logger.write("ServicesImplementation - DN Sugeridoo F I N");
		}
		
		return sugeridoVo;
	}
	
	@SuppressWarnings("unchecked")
	public static DNCambioVO ParseDnCambio(final String resultadoXML) throws ServiceException{
		String error = "";
		DNCambioVO cambioVo = new DNCambioVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("cambioMDNResponse", Namespace.getNamespace("http://service.dn.telco.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("cambioMDNResponse", Namespace.getNamespace("http://service.dn.telco.iusacell.com.mx")).getChild("return",Namespace.getNamespace("http://service.dn.telco.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("responseCode"))
							 cambioVo.setResponseCode(registros.getValue());
						 
						 if(registros.getName().equals("responseMessage"))
							cambioVo.setResponseMessage(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar DNSugerido");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		finally{
			Logger.write("ServicesImplementation - DN Sugeridoo F I N");
		}
		
		return cambioVo;
	}
	
	@SuppressWarnings("unchecked")
	public static DNReactivacionVO ParseDnReactivacion(final String resultadoXML) throws ServiceException{
		String error = "";
		DNReactivacionVO reactivacionVo = new DNReactivacionVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("reactivaServicioResponse", Namespace.getNamespace("http://www.example.org/SGestionReactivacionWS/")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("reactivaServicioResponse", Namespace.getNamespace("http://www.example.org/SGestionReactivacionWS/")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("operationCode"))
							 reactivacionVo.setOperationCode(registros.getValue());
						 
						 if(registros.getName().equals("messageCode"))
							 reactivacionVo.setMessageCode(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault",Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar Reactivacion");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		finally{
			Logger.write("ServicesImplementation - Reactivacion F I N");
		}
		
		return reactivacionVo;
	}
	
	@SuppressWarnings("unchecked")
	public static DNReactivacionVO ParseDnReactivacionLegacy(final String resultadoXML) throws ServiceException{
		String error = "";
		DNReactivacionVO reactivacionVo = new DNReactivacionVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("return", Namespace.getNamespace("http://ws.cambiostatus.mx.iusacell.com")) != null){
					childrens = raiz.getChild("return", Namespace.getNamespace("http://ws.cambiostatus.mx.iusacell.com")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("respuestaStatus"))
							 reactivacionVo.setOperationCode(registros.getValue());
						 
						 if(registros.getName().equals("msgError"))
							 reactivacionVo.setMessageCode(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault",Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar Reactivacion");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		finally{
			Logger.write("ServicesImplementation - Reactivacion F I N");
		}
		
		return reactivacionVo;
	}
	
	@SuppressWarnings("unchecked")
	public static DNReactivacionVO ParseDnSuspension(final String resultadoXML) throws ServiceException{
		String error = "";
		DNReactivacionVO reactivacionVo = new DNReactivacionVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("suspendeServicioResponse", Namespace.getNamespace("http://www.example.org/SGestionSuspensionWS/")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("suspendeServicioResponse", Namespace.getNamespace("http://www.example.org/SGestionSuspensionWS/")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("operationCode"))
							 reactivacionVo.setOperationCode(registros.getValue());
						 
						 if(registros.getName().equals("messageCode"))
							 reactivacionVo.setMessageCode(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault",Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar DNSugerido");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		finally{
			Logger.write("ServicesImplementation - DN Sugeridoo F I N");
		}
		
		return reactivacionVo;
	}
	
	@SuppressWarnings("unchecked")
	public static CajaRegistraMovimientoVO ParseCajaRegistraMovimiento(final String resultadoXML) throws ServiceException{
		String error = "";
		CajaRegistraMovimientoVO registra = new CajaRegistraMovimientoVO();
		TarjetasCajaVO tarjetaSingle = new TarjetasCajaVO();
		List<TarjetasCajaVO> tarjetaList = new ArrayList<TarjetasCajaVO>();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("registraMovimientoResponse", Namespace.getNamespace("http://caja.services.iusacell.com.mx/")) != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("registraMovimientoResponse", Namespace.getNamespace("http://caja.services.iusacell.com.mx/")).getChild("return",Namespace.getNamespace("http://caja.services.iusacell.com.mx/")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("registraMovimientoResponse", Namespace.getNamespace("http://caja.services.iusacell.com.mx/")).getChild("return",Namespace.getNamespace("http://caja.services.iusacell.com.mx/")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("errorCode"))
							 registra.setErrorCode(registros.getValue());
						 
						 if(registros.getName().equals("idRegistro"))
							 registra.setIdRegistro(Integer.valueOf(registros.getValue()));
						 
						 if(registros.getName().equals("tarjetas")){
							 for(int i=0;i<registros.getContentSize();i++){
								 tarjetaSingle = new TarjetasCajaVO();
								 if(registros.getContent(i) != null)
								 {
									 tarjetaSingle.setNumAut(registros.getContent(i).getValue());
									 tarjetaSingle.setNumTarjeta(registros.getContent(++i).getValue());
									 tarjetaList.add(tarjetaSingle);
								 }
							 }
							 registra.setTarjetas(tarjetaList);
						 }
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault",Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar registrar el movimiento");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		finally{
			Logger.write("ParseCajaRegistraMovimiento F I N");
		}
		
		return registra;
	}
	
	@SuppressWarnings("unchecked")
	public static DNReactivacionVO ParseCajaConfirmaPago(final String resultadoXML) throws ServiceException{
		String error = "";
		DNReactivacionVO reactivacionVo = new DNReactivacionVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("reactivaServicioResponse", Namespace.getNamespace("http://www.example.org/SGestionReactivacionWS/")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("reactivaServicioResponse", Namespace.getNamespace("http://www.example.org/SGestionReactivacionWS/")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("operationCode"))
							 reactivacionVo.setOperationCode(registros.getValue());
						 
						 if(registros.getName().equals("messageCode"))
							 reactivacionVo.setMessageCode(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault",Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar DNSugerido");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		finally{
			Logger.write("ParseCajaConfirmaPago F I N");
		}
		
		return reactivacionVo;
	}
	
	@SuppressWarnings("unchecked")
	public static DNReactivacionVO ParseCajaReversaPago(final String resultadoXML) throws ServiceException{
		String error = "";
		DNReactivacionVO reactivacionVo = new DNReactivacionVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("reactivaServicioResponse", Namespace.getNamespace("http://www.example.org/SGestionReactivacionWS/")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("reactivaServicioResponse", Namespace.getNamespace("http://www.example.org/SGestionReactivacionWS/")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("operationCode"))
							 reactivacionVo.setOperationCode(registros.getValue());
						 
						 if(registros.getName().equals("messageCode"))
							 reactivacionVo.setMessageCode(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault",Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar DNSugerido");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		finally{
			Logger.write("ParseCajaReversaPago F I N");
		}
		
		return reactivacionVo;
	}
	
	@SuppressWarnings("unchecked")
	public static List<CatalogoVO> parseClaveSensalCiudades(final String resultadoXML) throws ServiceException{
		String error = "";
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		CatalogoVO catalogoVO = new CatalogoVO();
		List<CatalogoVO> catalogoList = new ArrayList<CatalogoVO>();
		Element body = null;
		Element children = null;

		try {

			/** recorre resultadoXML **/
			try{
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();

				if(raiz.getChild("return", Namespace.getNamespace("http://ws.telco.iusacell.com")) != null){
					childrens = raiz.getChild("return", Namespace.getNamespace("http://ws.telco.iusacell.com")).getChildren();

					for (int a=0;a< raiz.getContentSize();a++)
					{
						body = (Element) raiz.getChildren().get(a);
						catalogoVO = new CatalogoVO();
						for(int b=0; b<childrens.size();b++)
						{
							children = (Element) body.getChildren().get(b);

							if(children.getName().equals("claveCensal"))   
								catalogoVO.setClaveSensal(children.getValue());

							if(children.getName().equals("descripcion"))   
								catalogoVO.setDescripcion(children.getValue());
						}
						catalogoList.add(catalogoVO);
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("detail").getChild("ConsultasNumerosWSSIRExceptionDTO", Namespace.getNamespace("http://vo.surtimiento.services.iusacell.com.mx")).getChild("SIRExceptionDTO", Namespace.getNamespace("http://vo.surtimiento.services.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch(Exception e){
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al obtener claves sensales");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return catalogoList;
	}
	
	@SuppressWarnings("unchecked")
	public static String parseConsultaNumeros(final String resultadoXML) throws ServiceException{
		String error = "";
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		Element children = null;
		String response = "";

		try {

			/** recorre resultadoXML **/
			try{
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();

				if(raiz.getChild("return", Namespace.getNamespace("http://ws.telco.iusacell.com")) != null){
					childrens = raiz.getChild("return", Namespace.getNamespace("http://ws.telco.iusacell.com")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						children = it.next();
						if(children.getName().equals("imsi1")){
							response = children.getValue();
						}
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("detail").getChild("ConsultasNumerosWSSIRExceptionDTO", Namespace.getNamespace("http://vo.surtimiento.services.iusacell.com.mx")).getChild("SIRExceptionDTO", Namespace.getNamespace("http://vo.surtimiento.services.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch(Exception e){
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al obtener claves sensales");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public static NumerosVO parseConsultaNumerosReserve(final String resultadoXML) throws ServiceException{
		String error = "";
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		Element children = null;
		NumerosVO response = new NumerosVO();

		try {

			/** recorre resultadoXML **/
			try{
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();

				if(raiz.getChild("return", Namespace.getNamespace("http://ws.telco.iusacell.com")) != null){
					childrens = raiz.getChild("return", Namespace.getNamespace("http://ws.telco.iusacell.com")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						children = it.next();
						if(children.getName().equals("imsi1")){
							response.setImsi1(children.getValue());
						}
						if(children.getName().equals("strCveCensal")){
							response.setStrCveCensal(children.getValue());
						}
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("detail").getChild("ConsultasNumerosWSSIRExceptionDTO", Namespace.getNamespace("http://vo.surtimiento.services.iusacell.com.mx")).getChild("SIRExceptionDTO", Namespace.getNamespace("http://vo.surtimiento.services.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch(Exception e){
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					Logger.write(e.getLocalizedMessage());
					throw new ServiceException("Ocurrio un error al obtener claves sensales");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public static String confirmaPagoCaja(final String resultadoXML) throws ServiceException{
		String error = "";
		String numTransaccion = "";
		
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("confirmacionPagoResponse",Namespace.getNamespace("http://caja.services.iusacell.com.mx/")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("confirmacionPagoResponse", Namespace.getNamespace("http://caja.services.iusacell.com.mx/")).getChild("return",Namespace.getNamespace("http://caja.services.iusacell.com.mx/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()){
						Element registros = (Element) it.next();
						if(registros.getName().equals("numTransaccion"))
							numTransaccion = registros.getValue();
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar la cita");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return numTransaccion;
	}
	
	@SuppressWarnings("unchecked")
	public static String ParseMsisdn(final String resultadoXML) throws ServiceException{
		String error = "";
		String dealerName = "";
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("queryDealerIDByMsisdnResponse", Namespace.getNamespace("http://bus.core.iusacell.com/ws")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("queryDealerIDByMsisdnResponse", Namespace.getNamespace("http://bus.core.iusacell.com/ws")).getChild("queryDealerIDByMsisdnResult", Namespace.getNamespace("http://bus.core.iusacell.com/ws")).getChildren();
				    
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("dealerName"))
							 dealerName = registros.getValue();
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al obtener Msisdn");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return dealerName;
	}
	
	@SuppressWarnings("unchecked")
	public static MensajeMailVO ParseMensajeMail(final String resultadoXML) throws ServiceException{
		String error = "";
		MensajeMailVO mensajeMail = new MensajeMailVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("sendMessageResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("sendMessageResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("_mailError"))
							 mensajeMail.setMailError(registros.getValue());
						 
						 if(registros.getName().equals("_mailSended"))
						     mensajeMail.setMailSended(registros.getValue());
						 
						 if(registros.getName().equals("_messageErrors"))
						     mensajeMail.setMessageErrors(registros.getValue());
						 
						 if(registros.getName().equals("_smsError"))
						     mensajeMail.setSmsError(registros.getValue());
						 
						 if(registros.getName().equals("_smsSended"))
						     mensajeMail.setSmsSended(registros.getValue());
					}
				}
				else if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaSMSResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaSMSResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						
						 if(registros.getName().equals("mensaje"))
						     mensajeMail.setSmsError(registros.getValue());
						 
						 if(registros.getName().equals("enviado"))
						     mensajeMail.setSmsSended(registros.getValue());
					}
				}
				else if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaMailResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaMailResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						
						if(registros.getName().equals("mensaje"))
							 mensajeMail.setMailError(registros.getValue());
						 
						 if(registros.getName().equals("enviado"))
						     mensajeMail.setMailSended(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al enviar el mensaje");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return mensajeMail;
	}
	
	@SuppressWarnings("unchecked")
	public static LoginVO ParseLoginUsuario(final String resultadoXML) throws ServiceException{
		
		String error = "";
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		List<Element> Subchildrens = null;
		Iterator<Element> it = null;
		Iterator<Element> itSub = null;
		LoginVO login = new LoginVO();
		int errorFound = 0;
		
		try {
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("iusacellUsuarioResponse", Namespace.getNamespace("http://services.distribuidor.iusacell.com.mx")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("iusacellUsuarioResponse", Namespace.getNamespace("http://services.distribuidor.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://services.distribuidor.iusacell.com.mx")).getChildren();
				it = childrens.iterator();
				while(it.hasNext()){
					Element registros = (Element) it.next();
					
					if(registros.getName().equals("error")){
						if(!registros.getValue().equals("")){
							error += registros.getValue();
							errorFound = 1;
						}
					}
					if(registros.getName().equals("CAEDesc"))
						 login.setCAEDesc(registros.getValue());
					if(registros.getName().equals("apellidoMat"))
						 login.setApellidoMat(registros.getValue());
					if(registros.getName().equals("apellidoPat"))
						 login.setApellidoPat(registros.getValue());
					if(registros.getName().equals("caeID"))
						 login.setCaeID(registros.getValue());
					if(registros.getName().equals("dadoAlta"))
						 login.setDadoAlta(Integer.parseInt(registros.getValue()));
					if(registros.getName().equals("empresaDes"))
						 login.setEmpresaDes(registros.getValue());
					
					if(registros.getName().equals("huellas")){
						if(registros.getChild("Huella",Namespace.getNamespace("http://vo.distribuidor.iusacell.com.mx")) != null){
							Subchildrens = registros.getChild("Huella",Namespace.getNamespace("http://vo.distribuidor.iusacell.com.mx")).getChildren();
							itSub = Subchildrens.iterator();
							while(itSub.hasNext()){
								Element Subregistros = (Element) itSub.next();
								if(Subregistros.getName().equals("hashHuella"))
									login.setHuellas(registros.getValue());
							}
						}
					}
					if(registros.getName().equals("nombre"))
						 login.setNombre(registros.getValue());
					if(registros.getName().equals("perfilPvsDesc"))
						 login.setPerfilPvsDesc(registros.getValue());
					if(registros.getName().equals("perfilPvsID"))
						 login.setPerfilPvsID(Integer.parseInt(registros.getValue()));
					if(registros.getName().equals("perfiles"))
						 login.setPerfiles(registros.getValue());
					if(registros.getName().equals("puesto"))
						 login.setPuesto(registros.getValue());
					if(registros.getName().equals("respuestaToken"))
						 login.setRespuestaToken(Boolean.valueOf(registros.getValue()));
				}
			}
			else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}
			
			if(errorFound == 1){
				throw new ServiceException(error);
			}
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de login");
			}
		}
	   return login;
	}
	
	@SuppressWarnings("unchecked")
	public static List<ServiciosContratarVO> ParseServiciosContratados(String resultadoXML) throws ServiceException{
	   	ServiciosContratarVO servContratados = new ServiciosContratarVO();
		List<ServiciosContratarVO> servContratadosList = new ArrayList<ServiciosContratarVO>();
		String error = "";
		SAXBuilder bulder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		double costo = 0;
		String status = "";
		String descripcionDeServicios = "";
		String idServicio = "";

		try {
			
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";


			 doc = bulder1.build(new StringReader(resultadoXML));
			 raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getServiciosContratadosResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")) != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getServiciosContratadosResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getServiciosContratadosReturn") != null){
			   childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getServiciosContratadosResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getServiciosContratadosReturn").getChild("serviciosContratados").getChildren();
               
			   it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	if(registro.getName().equals("DatosServiciosContratadosVO")){
			        		
			        		try{
			        		if(registro.getChild("costoServicio") != null)
			        			costo = Double.parseDouble(registro.getChild("costoServicio").getValue());
			        		}catch (NumberFormatException e) {
			        			costo = 0;
							}
			        		
			        		if(registro.getChild("statusServicios") != null)
			        			status = registro.getChild("statusServicios").getValue();
			        		
			        		if(registro.getChild("descripcionServicio") != null)
			        			descripcionDeServicios = registro.getChild("descripcionServicio").getValue();
			        		
			        		if(registro.getChild("servicioContratado") != null) 
			        			idServicio = registro.getChild("servicioContratado").getValue();
			        		
			        		if(costo > 0 && status != null && status.toLowerCase().equals("activo") && descripcionDeServicios != null && !descripcionDeServicios.toLowerCase().equals("cuota mensual")){

			        			servContratados = new ServiciosContratarVO();
			        			
			        			if(registro.getChild("descripcionServicio") != null) 
			        				servContratados.setDescripcionServicio(descripcionDeServicios);

			        			if(registro.getChild("statusServicios") != null) 
			        				servContratados.setStatusServicios(status);
			        			
			        			if(registro.getChild("servicioContratado") != null) 
			        				servContratados.setIdServicio(idServicio);

			        			if(registro.getChild("lastDateServices") != null)
			        				servContratados.setLastDateServices(registro.getChild("lastDateServices").getValue());

			        			servContratadosList.add(servContratados);
			        		}
			        	}
			        }
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}
		}catch(Exception e){
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al consultar los servicios contratados");
			}
		}
    	return servContratadosList;
	}
	
	@SuppressWarnings("unchecked")
	public static List<DatosServiciosAContratarVO> ParseServiciosXContratar(String resultadoXML) throws ServiceException{
	   	List<DatosServiciosAContratarVO> servContratadosList = new ArrayList<DatosServiciosAContratarVO>();
	   	DatosServiciosAContratarVO servXContratarSingle = new DatosServiciosAContratarVO();
		String error = "";
		SAXBuilder bulder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;

		try {
			
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";


			 doc = bulder1.build(new StringReader(resultadoXML));
			 raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getServiciosAContratarResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")) != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getServiciosAContratarResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getServiciosAContratarReturn").getChild("serviciosContratar") != null){
			   childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getServiciosAContratarResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getServiciosAContratarReturn").getChild("serviciosContratar").getChildren();
               
			   it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	if(registro.getName().equals("DatosServiciosAContratarVO")){
			        		servXContratarSingle = new DatosServiciosAContratarVO();
			        		
			        	    if(registro.getChild("costoServicio") != null) 
			        	    	servXContratarSingle.setCostoServicio(registro.getChild("costoServicio").getValue());
			        	   
			        	    if(registro.getChild("descripcionServicio") != null) 
			        	    	servXContratarSingle.setDescripcionServicio(registro.getChild("descripcionServicio").getValue());
			        	    
			        	    if(registro.getChild("servicio") != null) 
			        	    	servXContratarSingle.setServicio(registro.getChild("servicio").getValue());
			        	    
			        	    if(registro.getChild("snInd") != null) 
			        	    	servXContratarSingle.setSnInd(registro.getChild("snInd").getValue());
			        	    
			        	    servContratadosList.add(servXContratarSingle);
			        	}
			        }
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}
		}catch(Exception e){
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al consultar los servicios contratados");
			}
		}
    	return servContratadosList;
	}
	
	@SuppressWarnings("unchecked")
	public static DispatcherVO ParseServiciosStatusPrepago(String resultadoXML) throws ServiceException{
		String error = "";
		DispatcherVO response = new DispatcherVO();
		SAXBuilder bulder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;

		try {
			
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";


			 doc = bulder1.build(new StringReader(resultadoXML));
			 raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getResultadoBusquedaResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")) != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getResultadoBusquedaResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getResultadoBusquedaReturn") != null){
			   childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getResultadoBusquedaResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getResultadoBusquedaReturn").getChild("datosBusqueda").getChildren();
               
			   it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	if(registro.getName().equals("DatosBusquedaVO")){
			        		if(registro.getChild("statusContrato") != null)
			        			response.setStatus(registro.getChild("statusContrato").getValue());
			        		if(registro.getChild("contrato") != null)
			        			response.setContrato(registro.getChild("contrato").getValue());
			        	}
			        	break;
			        }
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}
		}catch(Exception e){
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al consultar el status de la linea");
			}
		}
    	return response;
	}
	
	@SuppressWarnings("unchecked")
	public static String ParseDatosFacturacion(String resultadoXML) throws ServiceException{
		String error = "";
		SAXBuilder bulder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String fechaFacturacion = "";

		try {
			
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";


			 doc = bulder1.build(new StringReader(resultadoXML));
			 raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDatosFacturacionResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")) != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDatosFacturacionResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getDatosFacturacionReturn") != null){
			   childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDatosFacturacionResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getDatosFacturacionReturn").getChildren();
               
			   it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	if(registro.getName().equals("fechaFacturacion")){
			        		fechaFacturacion = registro.getValue();
			        	}
			        }
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}
		}catch(Exception e){
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al consultar el status de la linea");
			}
		}
    	return fechaFacturacion;
	}
	
	@SuppressWarnings("unchecked")
	public static List<DatosUltimasFacturasVO> parseUltimasFacturas(String resultadoXML) throws ServiceException{
		String error = "";
		List<DatosUltimasFacturasVO> responseList = new ArrayList<DatosUltimasFacturasVO>();
		DatosUltimasFacturasVO response = new DatosUltimasFacturasVO();
		SAXBuilder bulder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		Iterator<Element> itFactura = null;
		List<Element> childrensFactura = null;

		try {
			
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";

			 doc = bulder1.build(new StringReader(resultadoXML));
			 raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getUltimasFacturasResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")) != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getUltimasFacturasResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getUltimasFacturasReturn") != null){
			   childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getUltimasFacturasResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getUltimasFacturasReturn").getChild("ultimasFacturas").getChildren();
               
			   it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registroFac = (Element) it.next();
			        	if(registroFac.getName().equals("Datos3UltimasFacturasVO")){
			        		childrensFactura = registroFac.getChildren();
			        		itFactura = childrensFactura.iterator();
			        		response = new DatosUltimasFacturasVO();
			        		while(itFactura.hasNext()){
			        			Element registro = (Element) itFactura.next();
			        			if(registro.getName().equals("fechaCorte"))
			        				response.setFechaCorte(registro.getValue());
			        			if(registro.getName().equals("fechaVencimiento"))
			        				response.setFechaVencimiento(registro.getValue());
			        			if(registro.getName().equals("saldoVencido"))
			        				response.setSaldoVencido(registro.getValue());
			        			if(registro.getName().equals("totalPagar"))
			        				response.setTotalPagar(registro.getValue());
			        		}
			        		responseList.add(response);
			        	}
			        }
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}
		}catch(Exception e){
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al consultar el status de la linea");
			}
		}
    	return responseList;
	}
	
	public static DatosFocalizacionVO parseDatosFocalizacion(final String resultadoXML) throws ServiceException{
		DatosFocalizacionVO  Focalizacion = new DatosFocalizacionVO();
		OracleProcedures oracle = new OracleProcedures();
		Pattern pattern = null;
		Matcher matcher = null;
		Pattern patternAux = null;
		Matcher matcherAux = null;
		String data = "";
		int count = 1;
		String dataBySqlDispatcher = "";
		
		try {
			try{
				dataBySqlDispatcher = oracle.getValorParametro(76);
			}catch (ServiceException e) {
				dataBySqlDispatcher = "0";
			}
			if(dataBySqlDispatcher.equals("1")){
				pattern = Pattern.compile("<datosBusqueda>(.+?)</datosBusqueda>");
				matcher = pattern.matcher(resultadoXML);
				while(matcher.find()){
					data = matcher.group(1);

					patternAux = Pattern.compile("<statusContrato>(.+?)</statusContrato>");
					matcherAux = patternAux.matcher(data);
					if(matcherAux.find()){
						if(matcherAux.group(1).equals("s")){
							patternAux = Pattern.compile("<apellidos>(.+?)</apellidos>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								String datas = matcherAux.group(1);
								String[] nom = datas.split(" ");
								if(nom.length>=2){
									Focalizacion.setApPATERNO(nom[0]);
									Focalizacion.setApMATERNO(nom[1]);	 
								}else{
									Focalizacion.setApPATERNO(nom[0]);
								}
							}

							patternAux = Pattern.compile("<contrato>(.+?)</contrato>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setCoid(matcherAux.group(1));
								Focalizacion.setContrato(matcherAux.group(1));
							}

							patternAux = Pattern.compile("<tmCode>(.+?)</tmCode>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setTmcode(matcherAux.group(1));
							}

							patternAux = Pattern.compile("<custCode>(.+?)</custCode>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setCuenta(matcherAux.group(1));
							}

							patternAux = Pattern.compile("<nombreCliente>(.+?)</nombreCliente>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setNombre(matcherAux.group(1));
							}

						}else if(matcherAux.group(1).equals("a")){
							patternAux = Pattern.compile("<apellidos>(.+?)</apellidos>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								String datas = matcherAux.group(1);
								String[] nom = datas.split(" ");
								if(nom.length>=2){
									Focalizacion.setApPATERNO(nom[0]);
									Focalizacion.setApMATERNO(nom[1]);	 
								}else{
									Focalizacion.setApPATERNO(nom[0]);
								}
							}

							patternAux = Pattern.compile("<contrato>(.+?)</contrato>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setCoid(matcherAux.group(1));
								Focalizacion.setContrato(matcherAux.group(1));
							}

							patternAux = Pattern.compile("<tmCode>(.+?)</tmCode>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setTmcode(matcherAux.group(1));
							}

							patternAux = Pattern.compile("<custCode>(.+?)</custCode>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setCuenta(matcherAux.group(1));
							}

							patternAux = Pattern.compile("<nombreCliente>(.+?)</nombreCliente>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setNombre(matcherAux.group(1));
							}
							break;
						}
					}
				}
			}
			 
			 if(Focalizacion.getCoid() == null || Focalizacion.getCoid().equals("")){
				 pattern = Pattern.compile("<contrato>(.+?)</contrato>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setCoid(matcher.group(count));
				 }
			 }
			 
			 if(Focalizacion.getContrato() == null || Focalizacion.getContrato().equals("")){
				 pattern = Pattern.compile("<contrato>(.+?)</contrato>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setContrato(matcher.group(1));
				 }
			 }
			 
			 if(Focalizacion.getTmcode() == null || Focalizacion.getTmcode().equals("")){
				 pattern = Pattern.compile("<tmCode>(.+?)</tmCode>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setTmcode(matcher.group(count));
				 }
			 }
			 
			 if(Focalizacion.getCuenta() == null || Focalizacion.getCuenta().equals("")){
				 pattern = Pattern.compile("<custCode>(.+?)</custCode>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setCuenta(matcher.group(count));
				 }
			 }
			 
			 
			 if(Focalizacion.getApMATERNO() == null || Focalizacion.getApMATERNO().equals("")){
				 pattern = Pattern.compile("<AP_MATERNO>(.+?)</AP_MATERNO>");
				 matcher = pattern.matcher(resultadoXML);
				 if(matcher.find()){
					 Focalizacion.setApMATERNO(matcher.group(1));
				 }
			 }
			 
			 if(Focalizacion.getApPATERNO() == null || Focalizacion.getApPATERNO().equals("")){
				 pattern = Pattern.compile("<AP_PATERNO>(.+?)</AP_PATERNO>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setApPATERNO(matcher.group(1));
				 }
			 }
			 
			 if(Focalizacion.getNombre() == null || Focalizacion.getNombre().equals("")){
				 pattern = Pattern.compile("<NOMBRE>(.+?)</NOMBRE>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setNombre(matcher.group(1));
				 }
			 }
			 
			 if(Focalizacion.getTmcode() == null || Focalizacion.getTmcode().equals("")){
				 pattern = Pattern.compile("<tmcode>(.+?)</tmcode>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setTmcode(matcher.group(1));
				 }
			 }
			 
			 pattern = Pattern.compile("<FECHA_NACIMIENTO>(.+?)</FECHA_NACIMIENTO>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setFechaNacimiento(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<correo>(.+?)</correo>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setCorreo(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<descripcionPlan>(.+?)</descripcionPlan>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				Focalizacion.setDescripcionPlan(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<cuenta>(.+?)</cuenta>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setCuenta(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<idOperador>(.+?)</idOperador>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setIdOperador(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<isPostpagoOrHibrido>(.+?)</isPostpagoOrHibrido>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setIsPostpagoOrHibrido(matcher.group(1));
			 }
			 
			 /// Ingles ****
			 
			 pattern = Pattern.compile("<customerSurname2>(.+?)</customerSurname2>");  //Apellido Paterno
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setApPATERNO(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<customerName>(.+?)</customerName>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setNombre(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<customerBirthday>(.+?)</customerBirthday>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setFechaNacimiento(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<customerEmail>(.+?)</customerEmail>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setCorreo(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<pricePlan>(.+?)</pricePlan>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 String cadena  =  matcher.group(1);
				 
				 pattern = Pattern.compile("<description>(.+?)</description>");
				 matcher = pattern.matcher(cadena);
				 
				 if(matcher.find()){
					 Focalizacion.setDescripcionPlan(matcher.group(1));
				 }
			 }
			 
			/// Espa√±ol ****
			 if(Focalizacion.getApMATERNO() == null || Focalizacion.getApMATERNO().equals("") || Focalizacion.getApMATERNO().equals("null")){
				 pattern = Pattern.compile("<apellidoMaterno>(.+?)</apellidoMaterno>");
				 matcher = pattern.matcher(resultadoXML);
				 if(matcher.find()){
					 Focalizacion.setApMATERNO(matcher.group(1));
				 }
			 }
			 
			 if(Focalizacion.getApPATERNO() == null || Focalizacion.getApPATERNO().equals("") || Focalizacion.getApPATERNO().equals("null")){
				 pattern = Pattern.compile("<apellidoPaterno>(.+?)</apellidoPaterno>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setApPATERNO(matcher.group(1));
				 }
			 }
			 
			 if(Focalizacion.getNombre() == null || Focalizacion.getNombre().equals("") || Focalizacion.getNombre().equals("null")){
				 pattern = Pattern.compile("<nombres>(.+?)</nombres>");
				 matcher = pattern.matcher(resultadoXML);
				 if(matcher.find()){
					 Focalizacion.setNombre(matcher.group(1));
				 }
			 }
			 
			 pattern = Pattern.compile("<birthdate>(.+?)</birthdate>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setFechaNacimiento(matcher.group(1));
			 }
			 
			 
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return Focalizacion;
	}
	
	public static DatosFocalizacionFocaVO parseDatosFocalizacionFoca(final String resultadoXML) throws ServiceException{
		DatosFocalizacionFocaVO  Focalizacion = new DatosFocalizacionFocaVO();
		OracleProcedures oracle = new OracleProcedures();
		Pattern pattern = null;
		Matcher matcher = null;
		Pattern patternAux = null;
		Matcher matcherAux = null;
		String data = "";
		String dataBySqlDispatcher = "";
		
		try {
			try{
				dataBySqlDispatcher = oracle.getValorParametro(76);
			}catch (ServiceException e) {
				dataBySqlDispatcher = "0";
			}
			if(dataBySqlDispatcher.equals("1")){
				pattern = Pattern.compile("<datosBusqueda>(.+?)</datosBusqueda>");
				matcher = pattern.matcher(resultadoXML);
				while(matcher.find()){
					data = matcher.group(1);

					patternAux = Pattern.compile("<statusContrato>(.+?)</statusContrato>");
					matcherAux = patternAux.matcher(data);
					if(matcherAux.find()){
						if(matcherAux.group(1).equals("s")){
							patternAux = Pattern.compile("<apellidos>(.+?)</apellidos>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								String datas = matcherAux.group(1);
								String[] nom = datas.split(" ");
								if(nom.length>=2){
									Focalizacion.setApPATERNOFoca(nom[0]);
									Focalizacion.setApMATERNOFoca(nom[1]);	 
								}else{
									Focalizacion.setApPATERNOFoca(nom[0]);
								}
							}

							patternAux = Pattern.compile("<contrato>(.+?)</contrato>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setCoidFoca(matcherAux.group(1));
								Focalizacion.setContratoFoca(matcherAux.group(1));
							}

							patternAux = Pattern.compile("<tmCode>(.+?)</tmCode>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setTmcodeFoca(matcherAux.group(1));
							}

							patternAux = Pattern.compile("<custCode>(.+?)</custCode>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setCuentaFoca(matcherAux.group(1));
							}

							patternAux = Pattern.compile("<nombreCliente>(.+?)</nombreCliente>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setNombreFoca(matcherAux.group(1));
							}
						}else if(matcherAux.group(1).equals("a")){
							patternAux = Pattern.compile("<apellidos>(.+?)</apellidos>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								String datas = matcherAux.group(1);
								String[] nom = datas.split(" ");
								if(nom.length>=2){
									Focalizacion.setApPATERNOFoca(nom[0]);
									Focalizacion.setApMATERNOFoca(nom[1]);	 
								}else{
									Focalizacion.setApPATERNOFoca(nom[0]);
								}
							}

							patternAux = Pattern.compile("<contrato>(.+?)</contrato>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setCoidFoca(matcherAux.group(1));
								Focalizacion.setContratoFoca(matcherAux.group(1));
							}

							patternAux = Pattern.compile("<tmCode>(.+?)</tmCode>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setTmcodeFoca(matcherAux.group(1));
							}

							patternAux = Pattern.compile("<custCode>(.+?)</custCode>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setCuentaFoca(matcherAux.group(1));
							}

							patternAux = Pattern.compile("<nombreCliente>(.+?)</nombreCliente>");
							matcherAux = patternAux.matcher(data);
							if(matcherAux.find()){
								Focalizacion.setNombreFoca(matcherAux.group(1));
							}
							break;
						}
					}
				}
			}
			
			 if(Focalizacion.getApMATERNOFoca() == null || Focalizacion.getApMATERNOFoca().equals("")){
				 pattern = Pattern.compile("<AP_MATERNO>(.+?)</AP_MATERNO>");
				 matcher = pattern.matcher(resultadoXML);
				 if(matcher.find()){
					 Focalizacion.setApMATERNOFoca(matcher.group(1));
				 }
			 }
			 
			 if(Focalizacion.getApPATERNOFoca() == null || Focalizacion.getApPATERNOFoca().equals("")){
				 pattern = Pattern.compile("<AP_PATERNO>(.+?)</AP_PATERNO>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setApPATERNOFoca(matcher.group(1));
				 }
			 }
			 
			 if(Focalizacion.getNombreFoca() == null || Focalizacion.getNombreFoca().equals("")){
				 pattern = Pattern.compile("<NOMBRE>(.+?)</NOMBRE>");
				 matcher = pattern.matcher(resultadoXML);
				 if(matcher.find()){
					 Focalizacion.setNombreFoca(matcher.group(1));
				 }
			 }
			 
			 pattern = Pattern.compile("<FECHA_NACIMIENTO>(.+?)</FECHA_NACIMIENTO>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setFechaNacimientoFoca(matcher.group(1));
			 }
			 
			 if(Focalizacion.getCoidFoca() == null || Focalizacion.getCoidFoca().equals("")){
				 pattern = Pattern.compile("<contrato>(.+?)</contrato>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setCoidFoca(matcher.group(1));
				 }
			 }
			 
			 if(Focalizacion.getTmcodeFoca() == null || Focalizacion.getTmcodeFoca().equals("")){
				 pattern = Pattern.compile("<tmCode>(.+?)</tmCode>");
				 matcher = pattern.matcher(resultadoXML);
				 if(matcher.find()){
					 Focalizacion.setTmcodeFoca(matcher.group(1));
				 }
			 }
			 
			 if(Focalizacion.getTmcodeFoca() == null || Focalizacion.getTmcodeFoca().equals("")){
				 pattern = Pattern.compile("<tmcode>(.+?)</tmcode>");
				 matcher = pattern.matcher(resultadoXML);
				 if(matcher.find()){
					 Focalizacion.setTmcodeFoca(matcher.group(1));
				 }
			 }
			 
			 pattern = Pattern.compile("<correo>(.+?)</correo>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setCorreoFoca(matcher.group(1));
			 }
			 
			 if(Focalizacion.getCuentaFoca() == null || Focalizacion.getCuentaFoca().equals("")){
				 pattern = Pattern.compile("<custCode>(.+?)</custCode>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setCuentaFoca(matcher.group(1));
				 }
			 }
			 
			 pattern = Pattern.compile("<descripcionPlan>(.+?)</descripcionPlan>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				Focalizacion.setDescripcionPlanFoca(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<cuenta>(.+?)</cuenta>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setCuentaFoca(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<contrato>(.+?)</contrato>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setContratoFoca(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<idOperador>(.+?)</idOperador>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setIdOperadorFoca(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<isPostpagoOrHibrido>(.+?)</isPostpagoOrHibrido>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setIsPostpagoOrHibridoFoca(matcher.group(1));
			 }
			 
			 /// Ingles ****
			 
			 pattern = Pattern.compile("<customerSurname2>(.+?)</customerSurname2>");  //Apellido Paterno
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setApPATERNOFoca(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<customerName>(.+?)</customerName>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setNombreFoca(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<customerBirthday>(.+?)</customerBirthday>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setFechaNacimientoFoca(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<customerEmail>(.+?)</customerEmail>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setCorreoFoca(matcher.group(1));
			 }
			 
			 pattern = Pattern.compile("<pricePlan>(.+?)</pricePlan>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 String cadena  =  matcher.group(1);
				 
				 pattern = Pattern.compile("<description>(.+?)</description>");
				 matcher = pattern.matcher(cadena);
				 
				 if(matcher.find()){
					 Focalizacion.setDescripcionPlanFoca(matcher.group(1));
				 }
			 }
			 
			/// Espanol ****
			 if(Focalizacion.getApMATERNOFoca() == null || Focalizacion.getApMATERNOFoca().equals("") || Focalizacion.getApMATERNOFoca().equals("null")){
				 pattern = Pattern.compile("<apellidoMaterno>(.+?)</apellidoMaterno>");
				 matcher = pattern.matcher(resultadoXML);
				 if(matcher.find()){
					 Focalizacion.setApMATERNOFoca(matcher.group(1));
				 }
			 }
			 
			 if(Focalizacion.getApPATERNOFoca() == null || Focalizacion.getApPATERNOFoca().equals("") || Focalizacion.getApPATERNOFoca().equals("null")){
				 pattern = Pattern.compile("<apellidoPaterno>(.+?)</apellidoPaterno>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setApPATERNOFoca(matcher.group(1));
				 }
			 }
			 
			 if(Focalizacion.getNombreFoca() == null || Focalizacion.getNombreFoca().equals("") || Focalizacion.getNombreFoca().equals("null")){
				 pattern = Pattern.compile("<nombres>(.+?)</nombres>");
				 matcher = pattern.matcher(resultadoXML);

				 if(matcher.find()){
					 Focalizacion.setNombreFoca(matcher.group(1));
				 }
			 }
			 
			 pattern = Pattern.compile("<birthdate>(.+?)</birthdate>");
			 matcher = pattern.matcher(resultadoXML);
			 
			 if(matcher.find()){
				 Focalizacion.setFechaNacimientoFoca(matcher.group(1));
			 }
			 
			 
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return Focalizacion;
	}
	
	@SuppressWarnings("unchecked")
	public static ContratarServiciosVO parseAltaLegacy(String resultadoXML) throws ServiceException{
		String error = "";
		ContratarServiciosVO response = new ContratarServiciosVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			
			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("operacionServicioResponse", Namespace.getNamespace("http://gestion.servicios.middleware.iusacell.com.mx")) != null){

					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("operacionServicioResponse", Namespace.getNamespace("http://gestion.servicios.middleware.iusacell.com.mx")).getChild("respuesta").getChildren();
					it = childrens.iterator();
					while (it.hasNext()){
						Element registros = (Element) it.next();
						if(registros.getName().equals("respuesta")){
							response.setMessageCode(registros.getValue());
						}
						if(registros.getName().equals("folioPreactivacion")){
							response.setOperationCode(registros.getValue());
						}
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar la cita");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public static ContratarServiciosVO parseAltaLegacyPrepago(String resultadoXML) throws ServiceException{
		String error = "";
		ContratarServiciosVO response = new ContratarServiciosVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;
			
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			
			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("bundleActivationResponse", Namespace.getNamespace("http://gwBundleWS/")) != null){

					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("bundleActivationResponse", Namespace.getNamespace("http://gwBundleWS/")).getChild("return").getChildren();
					it = childrens.iterator();
					while (it.hasNext()){
						Element registros = (Element) it.next();
						if(registros.getName().equals("message")){
							response.setMessageCode(registros.getValue());
						}
						if(registros.getName().equals("responseCode")){
							response.setOperationCode(registros.getValue());
						}
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error parseo");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public static List<ServiciosContratarVO> parseConsultaPrepagoBundle(String resultadoXML) throws ServiceException{
		String error = "";
		List<ServiciosContratarVO> response = new ArrayList<ServiciosContratarVO>();
		ServiciosContratarVO responseSingle = new ServiciosContratarVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getActiveBundleResponse", Namespace.getNamespace("http://gwBundleWS/")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getActiveBundleResponse", Namespace.getNamespace("http://gwBundleWS/")).getChildren(); 
					it = childrens.iterator();
					while (it.hasNext()){
						Element registro = (Element) it.next();
						if(registro.getName().equals("return")){
							responseSingle = new ServiciosContratarVO();
							if(registro != null && registro.getValue() != null){
								if(registro.getChild("keyword") != null){
									if(registro.getChild("keyword") != null && registro.getChild("keyword").getValue().length() == 3){
										responseSingle.setIdServicio(registro.getChild("keyword").getValue() + "|00000000");
									}
									else if(registro.getChild("keyword") != null && registro.getChild("keyword").getValue().length() == 6 && registro.getChild("keyword").getValue().toLowerCase().contains("justi")){
										responseSingle.setIdServicio(registro.getChild("keyword").getValue() + "|00000000");
									}
									else{
										responseSingle.setIdServicio(registro.getChild("keyword").getValue());
									}
									responseSingle.setStatusServicios("1");
								}
								if(registro.getChild("message") != null){
									responseSingle.setDescripcionServicio(registro.getChild("message").getValue());
								}
							}
						}
					}
					response.add(responseSingle);
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al generar la cita");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
	
@SuppressWarnings("unchecked")
public static ObtenerDescripcionPlanesVO1 ParseObtenerDescripcionPlanes(final String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		ObtenerDescripcionPlanesVO1 obtenerDescripcionPlanes = new ObtenerDescripcionPlanesVO1();
		
		int errorFound = 0;
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getCaracteristicaPlanResponse", Namespace.getNamespace("http://services.ofertacomercial.iusacell.com")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getCaracteristicaPlanResponse", Namespace.getNamespace("http://services.ofertacomercial.iusacell.com")).getChild("out",Namespace.getNamespace("http://services.ofertacomercial.iusacell.com")).getChildren();
				it = childrens.iterator();
				while(it.hasNext()){
					Element registros = (Element) it.next();
				
					if(registros.getName().equals("minutos"))
						obtenerDescripcionPlanes.setMinutos(registros.getValue());
					if(registros.getName().equals("minutosComunidad"))
						obtenerDescripcionPlanes.setMinutoscomunidad(registros.getValue());
//					if(registros.getName().equals("carrier"))
//						obtenerDescripcionPlanes.setCarrier(registros.getValue());
					if(registros.getName().equals("idPlan"))
						obtenerDescripcionPlanes.setIdPlan(registros.getValue());
					if(registros.getName().equals("megas"))
						obtenerDescripcionPlanes.setMegas(registros.getValue());
					if(registros.getName().equals("megasAdicional"))
						obtenerDescripcionPlanes.setMegaadc(registros.getValue());
					if(registros.getName().equals("mensajes"))
						obtenerDescripcionPlanes.setMensajes(registros.getValue());
					if(registros.getName().equals("minutosAdicional"))
						obtenerDescripcionPlanes.setMinutoadc(registros.getValue());
//					if(registros.getName().equals("minutosCompas"))
//						obtenerDescripcionPlanes.setMinutosCompas(registros.getValue());
					if(registros.getName().equals("minutosComunidad"))
						obtenerDescripcionPlanes.setMinutoscomunidad(registros.getValue());
					if(registros.getName().equals("nombreCortoPlan"))
						obtenerDescripcionPlanes.setNombreCortoPlan(registros.getValue());
//					if(registros.getName().equals("renta"))
//						obtenerDescripcionPlanes.setRenta(registros.getValue());
//					if(registros.getName().equals("tecnologia"))
//						obtenerDescripcionPlanes.setTecnologia(registros.getValue());
					if(registros.getName().equals("mensajesAdicional"))
						obtenerDescripcionPlanes.setMensajeadc(registros.getValue());
//					if(registros.getName().equals("minutosCompas"))
//						obtenerDescripcionPlanes.setMinutocia(registros.getValue());
					if(registros.getName().equals("tiempoAire"))
						obtenerDescripcionPlanes.setTiempoAire(registros.getValue());
				}
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaLineasMdnResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("datosInformacionDetalleResponse").getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}
			
			if(errorFound == 1){
				throw new ServiceException(error);
			}
			
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write(e.getLocalizedMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ObtenerDescripcionPlanesVO");
			}
		}
		return obtenerDescripcionPlanes;
	}

	@SuppressWarnings("unchecked")
	public static List<ServiciosAdicionalesVO> ParseServiciosOpc(final String resultadoXML) throws ServiceException{
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		List<ServiciosAdicionalesVO> listServicioOPC = new ArrayList<ServiciosAdicionalesVO>();
		ServiciosAdicionalesVO serviciosOpc = new ServiciosAdicionalesVO();
		OracleProcedures oracle = new OracleProcedures();
		String concatenaId = "";
		
		try{
			
			try{
				concatenaId = oracle.getValorParametro(16);
			}catch (ServiceException e) {
				concatenaId = "0";
			}
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getServiciosOpcResponse", Namespace.getNamespace("http://services.ofertacomercial.iusacell.com")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getServiciosOpcResponse", Namespace.getNamespace("http://services.ofertacomercial.iusacell.com")).getChild("out", Namespace.getNamespace("http://services.ofertacomercial.iusacell.com")).getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	if(registro.getName().equals("ServiciosOpcVo")){
			        		serviciosOpc = new ServiciosAdicionalesVO();
			        		
			        		 if(registro.getChild("costo",Namespace.getNamespace("http://vo.ofertacomercial.iusacell.com")) != null) 
			        			 serviciosOpc.setCosto(registro.getChild("costo",Namespace.getNamespace("http://vo.ofertacomercial.iusacell.com")).getValue());
				        	 
			        		 if(registro.getChild("descripcion",Namespace.getNamespace("http://vo.ofertacomercial.iusacell.com")) != null) 
			        			 serviciosOpc.setDescripcion(registro.getChild("descripcion",Namespace.getNamespace("http://vo.ofertacomercial.iusacell.com")).getValue());
			        		 
			        		 if(registro.getChild("idServicio",Namespace.getNamespace("http://vo.ofertacomercial.iusacell.com")) != null){
			        			 String idServicioMod = registro.getChild("idServicio",Namespace.getNamespace("http://vo.ofertacomercial.iusacell.com")).getValue();
			        			 
			        			 if(concatenaId.equals("1")){
			        				 if(idServicioMod != null && idServicioMod.toLowerCase().contains("vp")){
			        					 idServicioMod = idServicioMod + "00000000";
			        				 }
			        				 if(idServicioMod != null && idServicioMod.toLowerCase().contains("justi")){
			        					 idServicioMod = idServicioMod + "00000000";
			        				 }
			        			 }
			        			 serviciosOpc.setIdServicio(idServicioMod);
			        		 }
			        		 
			        		 if(registro.getChild("imagenB64",Namespace.getNamespace("http://vo.ofertacomercial.iusacell.com")) != null) 
			        			 serviciosOpc.setImagenB64(registro.getChild("imagenB64",Namespace.getNamespace("http://vo.ofertacomercial.iusacell.com")).getValue());
			        		 
			        		 if(registro.getChild("servicio",Namespace.getNamespace("http://vo.ofertacomercial.iusacell.com")) != null) 
			        			 serviciosOpc.setServicio(registro.getChild("servicio",Namespace.getNamespace("http://vo.ofertacomercial.iusacell.com")).getValue());
			        		 
			        		 if(registro.getChild("version",Namespace.getNamespace("http://vo.ofertacomercial.iusacell.com")) != null) 
			        			 serviciosOpc.setVersion(registro.getChild("version",Namespace.getNamespace("http://vo.ofertacomercial.iusacell.com")).getValue());
			        		 
			        		 serviciosOpc.setStatus(0);
			        		 listServicioOPC.add(serviciosOpc);
			        	}
			        }
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}	
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write(e.getLocalizedMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ObtenerDescripcionPlanesVO");
			}
		}
		
		return listServicioOPC;
	}
	
	@SuppressWarnings("unchecked")
	public static String ParseConsultaPrepago(final String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String id = "";
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChild("consultaInformacionPrepagoResponse", Namespace.getNamespace("http://impl.gsm.sconsultasprepago.prepago.iusacell.com.mx")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChild("consultaInformacionPrepagoResponse", Namespace.getNamespace("http://impl.gsm.sconsultasprepago.prepago.iusacell.com.mx")).getChild("return", Namespace.getNamespace("http://impl.gsm.sconsultasprepago.prepago.iusacell.com.mx")).getChild("cuenta", Namespace.getNamespace("http://comun.telco.iusacell.com.mx/xsd")).getChild("linea", Namespace.getNamespace("http://comun.telco.iusacell.com.mx/xsd")).getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	
			        	if(registro.getName().equals("id"))
			        		 id = registro.getValue();
			        }
			}
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				throw new ServiceException("Ocurrio un error al obtener valores de ParseConsultaPrepago");
			}
		}
         return id;		
	}
	
	@SuppressWarnings("unchecked")
	public static List<ServiciosContratarVO> ParseConsultaPrepagoServicios(String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		List<Element> childrensServicios = null;
		Iterator<Element> it = null;
		Iterator<Element> itServicios = null;
		List<ServiciosContratarVO> response = new ArrayList<ServiciosContratarVO>();
		ServiciosContratarVO elemento = new ServiciosContratarVO();
		
		try{
			
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;
			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaInformacionPrepagoResponse", Namespace.getNamespace("http://BSConsultas/ISConsultasPrepago")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaInformacionPrepagoResponse", Namespace.getNamespace("http://BSConsultas/ISConsultasPrepago")).getChild("cliente").getChild("cuenta").getChild("linea").getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	if(registro.getName().equals("servicios")){
			        		elemento = new ServiciosContratarVO();
			        		childrensServicios = registro.getChildren();
			        		itServicios = childrensServicios.iterator();
			        		while(itServicios.hasNext()){
			        			Element servicio = (Element) itServicios.next();
			        			if(servicio.getName().equals("descripcion")){
			        				elemento.setDescripcionServicio(servicio.getValue());
			        			}
			        			if(servicio.getName().equals("id")){
			        				elemento.setIdServicio(servicio.getValue());
			        				elemento.setStatusServicios("");
			        			}
			        		}
			        		response.add(elemento);
			        	}
			        }
			}
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseConsultaPrepago");
			}
		}
         return response;		
	}
	
	@SuppressWarnings("unchecked")
	public static String ParseConsultaPrepagoStatusLinea(String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String status = "";
		
		try{
			
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;
			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaInformacionPrepagoResponse", Namespace.getNamespace("http://BSConsultas/ISConsultasPrepago")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaInformacionPrepagoResponse", Namespace.getNamespace("http://BSConsultas/ISConsultasPrepago")).getChild("cliente").getChild("cuenta").getChild("linea").getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	if(registro.getName().equals("status")){
			        		status = registro.getValue();
			        	}
			        }
			}
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseConsultaPrepago");
			}
		}
         return status;		
	}
	
	@SuppressWarnings("unchecked")
	public static ConsultaPrepagoVO ParseConsultaPrepagoMin(final String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		ConsultaPrepagoVO response = new ConsultaPrepagoVO();
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChild("consultaInformacionPrepagoResponse", Namespace.getNamespace("http://impl.gsm.sconsultasprepago.prepago.iusacell.com.mx")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChild("consultaInformacionPrepagoResponse", Namespace.getNamespace("http://impl.gsm.sconsultasprepago.prepago.iusacell.com.mx")).getChild("return", Namespace.getNamespace("http://impl.gsm.sconsultasprepago.prepago.iusacell.com.mx")).getChild("cuenta", Namespace.getNamespace("http://comun.telco.iusacell.com.mx/xsd")).getChild("linea", Namespace.getNamespace("http://comun.telco.iusacell.com.mx/xsd")).getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	
			        	if(registro.getName().equals("id"))
			        		 response.setIdLinea(registro.getValue());
			        	
			        	if(registro.getName().equals("MIN"))
			        		 response.setMin(registro.getValue());
			        	
			        	if(registro.getName().equals("tipo"))
			        		 response.setTipoLinea(registro.getValue());
			        }
			}
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseConsultaPrepago");
			}
		}
         return response;		
	}
	
	@SuppressWarnings("unchecked")
	public static String ParseSuscribir(final String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String imsi = "";
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("querySubscriberResponse", Namespace.getNamespace("http://bus.core.iusacell.com/ws")).getChild("querySubscriberResult", Namespace.getNamespace("querySubscriberResult", "http://bus.core.iusacell.com/ws")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("querySubscriberResponse", Namespace.getNamespace("http://bus.core.iusacell.com/ws")).getChild("querySubscriberResult", Namespace.getNamespace("querySubscriberResult", "http://bus.core.iusacell.com/ws")).getChild("values", Namespace.getNamespace("http://bus.core.iusacell.com/ws")).getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	
			        	if(registro.getName().equals("imsi"))
			        		imsi = registro.getValue();
			        }
			}
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseConsultaPrepago");
			}
		}
         return imsi;		
	}
	
	@SuppressWarnings("unchecked")
	public static RetornaDNSugerido ParseReserveMdn(String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		List<Element> childrensPoblacion = null;
		Iterator<Element> it = null;
		Iterator<Element> itPoblacion = null;
		RetornaDNSugerido response = new RetornaDNSugerido();
		
		resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
		"<soapenv:Header/>"+
		"<soapenv:Body>" + resultadoXML;

		resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("doReserveMdnResponse", Namespace.getNamespace("http://rmi.numeros.mindbits.com")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("doReserveMdnResponse", Namespace.getNamespace("http://rmi.numeros.mindbits.com")).getChild("doReserveMdnReturn").getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	
			        	if(registro.getName().equals("longIdTransaccion")){
			        		response.setLongIdTransaccion(registro.getValue());
			        	}
			        	if(registro.getName().equals("strMDN")){
			        		response.setStrMDN(registro.getValue());
			        	}
			        	if(registro.getName().equals("strMIN")){
			        		response.setStrMIN(registro.getValue());
			        	}
			        	
			        	if(registro.getName().equals("poblacionVO")){
			        		childrensPoblacion = registro.getChildren();
			        		itPoblacion = childrensPoblacion.iterator();
			        		while(itPoblacion.hasNext()){
			        			Element pobla = (Element) itPoblacion.next();
			        			if(pobla.getName().equals("strDescripcion")){
			        				response.setStrDescripcion(pobla.getValue());
			        			}
			        			if(pobla.getName().equals("strCveCENSAL")){
			        				response.setStrCveCENSA(pobla.getValue());
			        			}
			        		}
			        	}
			        }
			}
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseReserveMdn");
			}
		}
         return response;		
	}
	
	
	@SuppressWarnings("unchecked")
	public static String ParseChangeMdn(String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String longIdTransaccion = "";
		
		resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
		"<soapenv:Header/>"+
		"<soapenv:Body>" + resultadoXML;

		resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("doChangeMdnResponse", Namespace.getNamespace("http://rmi.numeros.mindbits.com")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("doChangeMdnResponse", Namespace.getNamespace("http://rmi.numeros.mindbits.com")).getChild("doChangeMdnReturn").getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	
			        	if(registro.getName().equals("longIdTransaccion"))
			        		longIdTransaccion = registro.getValue();
			        }
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}	
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseChangeMdn");
			}
		}
         return longIdTransaccion;		
	}
	
	
	@SuppressWarnings("unchecked")
	public static String ParseCommitReserve(String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String CommitReserve = "";
		
		resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
		"<soapenv:Header/>"+
		"<soapenv:Body>" + resultadoXML;

		resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("doCommitReserveResponse", Namespace.getNamespace("http://rmi.numeros.mindbits.com")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("doCommitReserveResponse", Namespace.getNamespace("http://rmi.numeros.mindbits.com")).getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();

			        	if(registro.getName().equals("doCommitReserveReturn"))
			        		CommitReserve = registro.getValue();
			        }
			}
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseCommitReserve");
			}
		}
         return CommitReserve;		
	}
	
	@SuppressWarnings("unchecked")
	public static String ParseAvaible(String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String availableReturn = "";
		
		resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
		"<soapenv:Header/>"+
		"<soapenv:Body>" + resultadoXML;

		resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("doAvailableResponse", Namespace.getNamespace("http://rmi.numeros.mindbits.com")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("doAvailableResponse", Namespace.getNamespace("http://rmi.numeros.mindbits.com")).getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();

			        	if(registro.getName().equals("doAvailableReturn"))
			        		availableReturn = registro.getValue();
			        }
			}
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseAvaible");
			}
		}
         return availableReturn;		
	}
	
	@SuppressWarnings("unchecked")
	public static List<TiposDeAtencionVO> ParseTiposDeAtencion(final String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
        List<TiposDeAtencionVO> listTipoDeAtencion = new ArrayList<TiposDeAtencionVO>();
        TiposDeAtencionVO tipoDeAtencion = new TiposDeAtencionVO();
		
		try{
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getTiposAtencionResponse", Namespace.getNamespace("http://services.citas.iusacell.com.mx")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getTiposAtencionResponse", Namespace.getNamespace("http://services.citas.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://services.citas.iusacell.com.mx")).getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
     		        	if(registro.getName().equals("ResponseTipoAtencion")){
     		        		tipoDeAtencion = new TiposDeAtencionVO();

     		        		if(registro.getChild("descripcion",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
     		        			tipoDeAtencion.setDescripcion(registro.getChild("descripcion",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
     		        		
     		        		if(registro.getChild("idAtencion",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
     		        			tipoDeAtencion.setIdAtencion(registro.getChild("idAtencion",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());

     		        		listTipoDeAtencion.add(tipoDeAtencion);
			        	}
			        }
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}	
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write(e.getLocalizedMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseConsultaPrepago");
			}
		}
         return listTipoDeAtencion;		
	}
	
	@SuppressWarnings("unchecked")
	public static ImagenEquipoVO parseImagenEquipo(final String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		ImagenEquipoVO response = new ImagenEquipoVO();
		
		try{
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getImagenEquipoSOResponse", Namespace.getNamespace("http://services.ofertacomercial.iusacell.com")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getImagenEquipoSOResponse", Namespace.getNamespace("http://services.ofertacomercial.iusacell.com")).getChild("out", Namespace.getNamespace("http://services.ofertacomercial.iusacell.com")).getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
     		        	if(registro.getName().equals("equipo")){
     		        		response.setEquipo(registro.getValue());
			        	}
     		        	if(registro.getName().equals("imagenEquipoB64")){
     		        		response.setImagenEquipoB64(registro.getValue());
			        	}
     		        	if(registro.getName().equals("imagenSOB64")){
     		        		response.setImagenSOB64(registro.getValue());
			        	}
     		        	if(registro.getName().equals("sistemaOper")){
     		        		response.setSistemaOper(registro.getValue());
			        	}
			        }
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}	
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write(e.getLocalizedMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseConsultaPrepago");
			}
		}
         return response;		
	}
	
	@SuppressWarnings("unchecked")
	public static List<GetCitasPendientesXDNVO> ParseCitasPendientes(final String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		ArrayList<GetCitasPendientesXDNVO> listgetCitasPendientesXDNVO =  new ArrayList<GetCitasPendientesXDNVO>();  
		GetCitasPendientesXDNVO getCitasPendientesXDN = new GetCitasPendientesXDNVO(); 
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getCitasPendientesXDNResponse", Namespace.getNamespace("http://services.citas.iusacell.com.mx")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getCitasPendientesXDNResponse", Namespace.getNamespace("http://services.citas.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://services.citas.iusacell.com.mx")).getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	
			        	if(registro.getName().equals("ResponseCitasPendientesXDN")){
                               getCitasPendientesXDN = new GetCitasPendientesXDNVO();
                               
                               if(registro.getChild("apMat",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   getCitasPendientesXDN.setApMat(registro.getChild("apMat",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                        
                               if(registro.getChild("apPat",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   getCitasPendientesXDN.setApPat(registro.getChild("apPat",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               if(registro.getChild("caeClave",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   getCitasPendientesXDN.setCaeClave(registro.getChild("caeClave",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               if(registro.getChild("caeDes",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   getCitasPendientesXDN.setCaeDes(registro.getChild("caeDes",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               if(registro.getChild("correo",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   getCitasPendientesXDN.setCorreo(registro.getChild("correo",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               if(registro.getChild("dn",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   getCitasPendientesXDN.setDn(registro.getChild("dn",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               if(registro.getChild("empleado",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   getCitasPendientesXDN.setEmpleado(registro.getChild("empleado",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               if(registro.getChild("fecha",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   getCitasPendientesXDN.setFecha(registro.getChild("fecha",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               if(registro.getChild("folio",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   getCitasPendientesXDN.setFolio(registro.getChild("folio",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               if(registro.getChild("horaDes",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   getCitasPendientesXDN.setHoraDes(registro.getChild("horaDes",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               if(registro.getChild("horaId",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   getCitasPendientesXDN.setHoraId(registro.getChild("horaId",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               if(registro.getChild("nombre",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   getCitasPendientesXDN.setNombre(registro.getChild("nombre",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               listgetCitasPendientesXDNVO.add(getCitasPendientesXDN);
			        	}
			        }
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}
			
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de GetCitasPendientesXDNVO");
			}
		}
         return listgetCitasPendientesXDNVO;		
	}
	
	@SuppressWarnings("unchecked")
	public static ResponseCitaVO parseCancelaCitaUsuario(final String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		ResponseCitaVO response = new ResponseCitaVO();
		
		try{
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("cancelaCitaUsuarioResponse", Namespace.getNamespace("http://services.citas.iusacell.com.mx")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("cancelaCitaUsuarioResponse", Namespace.getNamespace("http://services.citas.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://services.citas.iusacell.com.mx")).getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
     		        	if(registro.getName().equals("caeDescripcion")){
     		        		response.setCaeDescripcion(registro.getValue());
			        	}
     		        	if(registro.getName().equals("dn")){
     		        		response.setDn(registro.getValue());
			        	}
     		        	if(registro.getName().equals("fecha")){
     		        		response.setFecha(registro.getValue());
			        	}
     		        	if(registro.getName().equals("folio")){
     		        		try {
     		        			response.setFolio(Integer.parseInt(registro.getValue()));								
							} catch (Exception e) {
								response.setFolio(0);
							}
			        	}
     		        	if(registro.getName().equals("hora")){
     		        		response.setHora(registro.getValue());
			        	}
			        }
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}	
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseConsultaPrepago");
			}
		}
         return response;		
	}
	
	@SuppressWarnings("unchecked")
	public static List<GetHorariosDisponiblesCallCenterVO> ParseHorariosDisponibles(final String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		ArrayList<GetHorariosDisponiblesCallCenterVO> listHorariosDisponiblesCallCenter =  new ArrayList<GetHorariosDisponiblesCallCenterVO>();  
		GetHorariosDisponiblesCallCenterVO gethorariosVO = new GetHorariosDisponiblesCallCenterVO();  
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getHorariosDisponiblesCallCenterResponse", Namespace.getNamespace("http://services.citas.iusacell.com.mx")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getHorariosDisponiblesCallCenterResponse", Namespace.getNamespace("http://services.citas.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://services.citas.iusacell.com.mx")).getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	
			        	if(registro.getName().equals("ResponseHoraCita")){
			        		   gethorariosVO = new GetHorariosDisponiblesCallCenterVO();
			        		
                               if(registro.getChild("descripcion",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   gethorariosVO.setDescripcion(registro.getChild("descripcion",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               if(registro.getChild("valor",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")) != null)
                            	   gethorariosVO.setValor(registro.getChild("valor",Namespace.getNamespace("http://vo.citas.iusacell.com.mx")).getValue());
                               
                               listHorariosDisponiblesCallCenter.add(gethorariosVO);
			        	}
			        }
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}
			
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de GetCitasPendientesXDNVO");
			}
		}
         return listHorariosDisponiblesCallCenter;		
	}
	
	@SuppressWarnings("unchecked")
	public static CatalogoMovilCaeVO parseObtieneListaVersionCaeS(final String resultadoXML) throws ServiceException{

		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		List<Element> childrensObj = null;
		List<Element> childrensCaes = null;
		Iterator<Element> it = null;
		Iterator<Element> itObj = null;
		Iterator<Element> caes = null;
		CatalogoMovilCaeVO response = new CatalogoMovilCaeVO();
		ArrayList<CatalogoCaeGeneralVO> listaCatalogoCaeGeneralVO = new ArrayList<CatalogoCaeGeneralVO>();
		CatalogoCaeGeneralVO getCatalogoCaeGeneralVO = new CatalogoCaeGeneralVO();

		try{
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();

			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("MapaGetCaeVersionResponse", Namespace.getNamespace("http://services.mapas.iusacell.com.mx")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("MapaGetCaeVersionResponse", Namespace.getNamespace("http://services.mapas.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://services.mapas.iusacell.com.mx")).getChildren();

				it = childrens.iterator();
				while(it.hasNext()){
					Element registroini = (Element) it.next();

					if(registroini.getName().equals("caes")){
						
						childrensObj = registroini.getChildren();
						itObj = childrensObj.iterator();
						while(itObj.hasNext()){
						Element registro = (Element) itObj.next();
						
						childrensCaes = registro.getChildren();
						caes = childrensCaes.iterator();
						getCatalogoCaeGeneralVO = new CatalogoCaeGeneralVO();
						while(caes.hasNext()){
							Element registrocaes = (Element) caes.next();
							
							if(registrocaes.getName().equals("clave")){
								getCatalogoCaeGeneralVO.setClave(registrocaes.getValue());
							}
							if(registrocaes.getName().equals("codigoPostal")){
								getCatalogoCaeGeneralVO.setCodigoPostal(registrocaes.getValue());
							}
							if(registrocaes.getName().equals("descripcion")){
								getCatalogoCaeGeneralVO.setDescripcion(Utilerias.getStringUTF8(registrocaes.getValue()));
							}
							if(registrocaes.getName().equals("direccion")){
								getCatalogoCaeGeneralVO.setDireccion(Utilerias.getStringUTF8(registrocaes.getValue()));
							}
							if(registrocaes.getName().equals("horario")){
								getCatalogoCaeGeneralVO.setHorario(Utilerias.getStringUTF8(registrocaes.getValue()));
							}
							if(registrocaes.getName().equals("idCae")){
								try{
									getCatalogoCaeGeneralVO.setIdCae(Integer.parseInt(registrocaes.getValue()));
								}catch (Exception e) {
									getCatalogoCaeGeneralVO.setIdCae(0);
								}
							}

							if(registrocaes.getName().equals("latitud")){
								try{
									getCatalogoCaeGeneralVO.setLatitud(Double.parseDouble(registrocaes.getValue()));
								}catch (Exception e) {
									getCatalogoCaeGeneralVO.setLatitud(0);
								}
							}
							if(registrocaes.getName().equals("longitud")){
								try{
									getCatalogoCaeGeneralVO.setLongitud(Double.parseDouble(registrocaes.getValue()));
								}catch (Exception e) {
									getCatalogoCaeGeneralVO.setLongitud(0);  
								}
							}
							if(registrocaes.getName().equals("povHeading")){
								try{
									getCatalogoCaeGeneralVO.setPovHeading(Double.parseDouble(registrocaes.getValue()));
								}catch (Exception e) {
									getCatalogoCaeGeneralVO.setPovHeading(0);
								}
							}
							if(registrocaes.getName().equals("povLatitud")){
								try{
									getCatalogoCaeGeneralVO.setPovLatitud(Double.parseDouble(registrocaes.getValue()));
								}catch (Exception e) {
									getCatalogoCaeGeneralVO.setPovLatitud(0);
								}
							}
							if(registrocaes.getName().equals("povLongitud")){
								try{
									getCatalogoCaeGeneralVO.setPovLongitud(Double.parseDouble(registrocaes.getValue()));
								}catch (Exception e) {
									getCatalogoCaeGeneralVO.setPovLongitud(0);
								}
							}
							if(registrocaes.getName().equals("povPitch")){
								try{
									getCatalogoCaeGeneralVO.setPovPitch(Double.parseDouble(registrocaes.getValue()));
								}catch (Exception e) {
									getCatalogoCaeGeneralVO.setPovPitch(0);
								}

							}
							if(registrocaes.getName().equals("povZoom")){
								try{
									getCatalogoCaeGeneralVO.setPovZoom(Double.parseDouble(registrocaes.getValue()));
								}catch (Exception e) {
									getCatalogoCaeGeneralVO.setPovZoom(0);
								}
							}
							if(registrocaes.getName().equals("telefono")){
								try{
									getCatalogoCaeGeneralVO.setTelefono(registrocaes.getValue());
								}catch (Exception e) {
									getCatalogoCaeGeneralVO.setTelefono("");
								}
							}
						}
						listaCatalogoCaeGeneralVO.add(getCatalogoCaeGeneralVO);
						}
					}

					if(registroini.getName().equals("version")){

						response.setVersion(Integer.parseInt(registroini.getValue()));
					}
					response.setDetalleCatalogoCaeGeneral(listaCatalogoCaeGeneralVO);

				}
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}	
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseConsultaPrepago");
			}
		}
		return response;		
	}

	@SuppressWarnings("unchecked")
	public static List<CatalogoCaeGeneralVO> parseObtieneCaes(final String resultadoXML) throws ServiceException{

		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		List<Element> catalogo = null;
		Iterator<Element> it = null;
		Iterator<Element> itCatalogo = null;
		List<CatalogoCaeGeneralVO> responseList = new ArrayList<CatalogoCaeGeneralVO>();
		CatalogoCaeGeneralVO response = new CatalogoCaeGeneralVO();

		try{
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();

			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("MapaGetCaeResponse", Namespace.getNamespace("http://services.mapas.iusacell.com.mx")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("MapaGetCaeResponse", Namespace.getNamespace("http://services.mapas.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://services.mapas.iusacell.com.mx")).getChildren();

				it = childrens.iterator();
				
				
				while(it.hasNext()){
					Element registroIni = (Element) it.next();
					
					catalogo = registroIni.getChildren();
					itCatalogo = catalogo.iterator();
					response = new CatalogoCaeGeneralVO();
					
					while(itCatalogo.hasNext()){
					Element registro = (Element) itCatalogo.next();
					
					if(registro.getName().equals("clave")){
						
						response.setClave(registro.getValue());
					}
					if(registro.getName().equals("codigoPostal")){
						response.setCodigoPostal(registro.getValue());
					}
					if(registro.getName().equals("descripcion")){
						response.setDescripcion(registro.getValue());
					}
					if(registro.getName().equals("direccion")){
						response.setDireccion(registro.getValue());
					}
					if(registro.getName().equals("horario")){
						response.setHorario(registro.getValue());
					}
					if(registro.getName().equals("idCae")){
						try{
							response.setIdCae(Integer.parseInt(registro.getValue()));
						}catch (Exception e) {
							response.setIdCae(0);
						}
					}

					if(registro.getName().equals("latitud")){
						try{
							response.setLatitud(Double.parseDouble(registro.getValue()));
						}catch (Exception e) {
							response.setLatitud(0);
						}
					}
					if(registro.getName().equals("longitud")){
						try{
							response.setLongitud(Double.parseDouble(registro.getValue()));
						}catch (Exception e) {
							response.setLongitud(0);
						}
					}
					if(registro.getName().equals("povHeading")){
						try{
							response.setPovHeading(Double.parseDouble(registro.getValue()));
						}catch (Exception e) {
							response.setPovHeading(0);
						}
					}
					if(registro.getName().equals("povLatitud")){
						try{
							response.setPovLatitud(Double.parseDouble(registro.getValue()));
						}catch (Exception e) {
							response.setPovLatitud(0);
						}
					}
					if(registro.getName().equals("povLongitud")){
						try{
							response.setPovLongitud(Double.parseDouble(registro.getValue()));
						}catch (Exception e) {
							response.setPovLongitud(0);
						}
					}
					if(registro.getName().equals("povPitch")){
						try{
							response.setPovPitch(Double.parseDouble(registro.getValue()));
						}catch (Exception e) {
							response.setPovPitch(0);
						}

					}
					if(registro.getName().equals("povZoom")){
						try{
							response.setPovZoom(Double.parseDouble(registro.getValue()));
						}catch (Exception e) {
							response.setPovZoom(0);
						}
					}
					if(registro.getName().equals("telefono")){
						try{
							response.setTelefono(registro.getValue());
						}catch (Exception e) {
							response.setTelefono("");
						}
					}
					}
					responseList.add(response);
				}
			}else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}	
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseConsultaPrepago");
			}
		}
		return responseList;		
	}

	@SuppressWarnings("unchecked")
	public static ConsultaCuentaResponseVO parseConsultaCuenta(final String resultadoXML) throws ServiceException{
		ConsultaCuentaResponseVO response = new ConsultaCuentaResponseVO();
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String error = "";
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaCuentaResponse", Namespace.getNamespace("http://cliente.spring.iusacell.com/")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaCuentaResponse", Namespace.getNamespace("http://cliente.spring.iusacell.com/")).getChildren();
				it = childrens.iterator();
				while(it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getChild("cdgError") != null && registro.getChild("descError") != null){
						error += ("[" + registro.getChild("cdgError").getValue() + "] " + registro.getChild("descError").getValue());
						throw new ServiceException(error);
					}		
					if(registro.getChild("contrato") != null){
						response.setContrato(registro.getChild("contrato").getValue());
					}
					if(registro.getChild("estatus") != null){
						response.setEstatus(registro.getChild("estatus").getValue());
					}
					if(registro.getChild("nombreDelCliente") != null){
						response.setNombreDelCliente(registro.getChild("nombreDelCliente").getValue());
					}
					if(registro.getChild("numeroDeCuenta") != null){
						response.setNumeroDeCuenta(registro.getChild("numeroDeCuenta").getValue());
					}
					if(registro.getChild("referencia") != null){
						response.setReferencia(registro.getChild("referencia").getValue());
					}
					if(registro.getChild("saldo") != null){
						response.setSaldo(registro.getChild("saldo").getValue());
					}
					if(registro.getChild("telefono") != null){
						response.setTelefono(registro.getChild("telefono").getValue());
					}
					if(registro.getChild("tipoCliente") != null){
						response.setTipoCliente(registro.getChild("tipoCliente").getValue());
					}							
					if(response.getEstatus() != null && response.getEstatus().toLowerCase().equals("activo")){
						break;	
					}
				}
			}
			else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}
		}catch(Exception e){
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
			}
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public static PagoFacturaResponseVO parsepagoReferenciadoBmx(final String resultadoXML) throws ServiceException{
		PagoFacturaResponseVO response = new PagoFacturaResponseVO();
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String error = "";
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("pagoReferenciadoBmxResponse", Namespace.getNamespace("http://cliente.spring.iusacell.com/")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("pagoReferenciadoBmxResponse", Namespace.getNamespace("http://cliente.spring.iusacell.com/")).getChildren();
				it = childrens.iterator();
				while(it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getChild("folioPago") != null){
						response.setFolioPago(registro.getChild("folioPago").getValue());
					}
					if(registro.getChild("numAutBmx") != null){
						response.setNumAutBmx(registro.getChild("numAutBmx").getValue());
					}
				}
			}
			else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					if(registro.getName() != null && registro.getName().toLowerCase().trim().equals("faultstring"))
						error += ((" ")+registro.getValue());
				}
				throw new ServiceException(error);
			}
		}catch(Exception e){
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
			}
		}
		return response;
	}
	
	public static String parseResultadoBusqueda(String resultadoXML) throws ServiceException{
		String error = "";
		String respuesta ="";
		Pattern pattern = null;
		Matcher matcher = null;
		
		try {
			
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			

			/** recorre resultadoXML **/
			try{
				
				pattern = Pattern.compile("<contrato>(.+?)</contrato>");
				 matcher = pattern.matcher(resultadoXML);
				 if(matcher.find()){
					 respuesta = matcher.group(1);
				 }
				
				
				
				
				
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					Logger.write("   Detail       : (Exception) " + e.getMessage());
					throw new ServiceException("Ocurrio un error al consultar los servicios");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return respuesta;
	}	
	
	@SuppressWarnings("unchecked")
	public static MensajeMailVO ParseMensajeMailPago(final String resultadoXML) throws ServiceException{
		String error = "";
		MensajeMailVO mensajeMail = new MensajeMailVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("sendMessageResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("sendMessageResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("_mailError"))
							 mensajeMail.setMailError(registros.getValue());
						 
						 if(registros.getName().equals("_mailSended"))
						     mensajeMail.setMailSended(registros.getValue());
						 
						 if(registros.getName().equals("_messageErrors"))
						     mensajeMail.setMessageErrors(registros.getValue());
						 
						 if(registros.getName().equals("_smsError"))
						     mensajeMail.setSmsError(registros.getValue());
						 
						 if(registros.getName().equals("_smsSended"))
						     mensajeMail.setSmsSended(registros.getValue());
					}
				}
				else if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaSMSResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaSMSResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						
						 if(registros.getName().equals("mensaje"))
						     mensajeMail.setSmsError(registros.getValue());
						 
						 if(registros.getName().equals("enviado"))
						     mensajeMail.setSmsSended(registros.getValue());
					}
				}
				else if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaMailResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaMailResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						
						if(registros.getName().equals("mensaje"))
							 mensajeMail.setMailError(registros.getValue());
						 
						 if(registros.getName().equals("enviado"))
						     mensajeMail.setMailSended(registros.getValue());
					}
				}
				/**
				 * GAVG 26/03/2015
				 * anexo el nuevo metodo para que pueda parsearlo
				 * sendMailResponse
				*/
				else if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("sendMailResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("sendMailResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						
						if(registros.getName().equals("mensaje"))
							 mensajeMail.setMailError(registros.getValue());
						 
						 if(registros.getName().equals("enviado"))
						     mensajeMail.setMailSended(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al enviar el mensaje");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return mensajeMail;
	}
	
	@SuppressWarnings("unchecked")
	public static List<ConsultaWalletGeneralVO> parseFocalizacionConsultaWallets(final String resultadoXML, final String compania, final String dn) throws ServiceException{
		String error = "";
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		List<Element> ChildBalance = null;
		Iterator<Element> it = null;
		Iterator<Element> itP = null;
    	
		List<ConsultaWalletListVO> listConsultaWalletsDinero = new ArrayList<ConsultaWalletListVO>();
		ConsultaWalletListVO consultaWalletDinero = new ConsultaWalletListVO();
		
		List<ConsultaWalletListVO> listConsultaWalletsVoz = new ArrayList<ConsultaWalletListVO>();
		ConsultaWalletListVO consultaWalletVoz = new ConsultaWalletListVO();
		
		List<ConsultaWalletListVO> listConsultaWalletsSms = new ArrayList<ConsultaWalletListVO>();
		ConsultaWalletListVO consultaWalletSms = new ConsultaWalletListVO();
		
		List<ConsultaWalletListVO> listConsultaWalletsDatos = new ArrayList<ConsultaWalletListVO>();
		ConsultaWalletListVO consultaWalletDatos = new ConsultaWalletListVO();
		
		List<ConsultaWalletListVO> listConsultaWalletIlimitadoVO = new ArrayList<ConsultaWalletListVO>();
		ConsultaWalletListVO consultaWalletsIlimitado =  new ConsultaWalletListVO();
		
		List<ConsultaWalletListVO> listConsultaWalletUnidadVO = new ArrayList<ConsultaWalletListVO>();
		ConsultaWalletListVO consultaWalletsUnidad =  new ConsultaWalletListVO();
		
		List<ConsultaWalletGeneralVO> listConsultaWalletsGeneral = new ArrayList<ConsultaWalletGeneralVO>();
		ConsultaWalletGeneralVO consultaWalletsGeneral = new ConsultaWalletGeneralVO();
		
		long totalDinero = 0;
		long totalVoz    = 0;
		long totalSms    = 0;
		Double totalDatos  = 0.0;
		Double cantidad = 0.0;
		long lcantidad = 0;
				
		String serviceClass = "";
		String descPlan = "";
		boolean redesIlimitadas= false;				
		String idPlan = "";
		
			try{
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaWalletResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return") != null){
					if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaWalletResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("infoWallets") != null){
						ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaWalletResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("infoWallets").getChildren();
						itP = ChildBalance.iterator();
						while(itP.hasNext()){
							Element registroP = (Element) itP.next();
							if(registroP.getName().equals("walletList")){
								it = registroP.getChildren().iterator();
								while(it.hasNext()){
									Element registro = (Element) it.next();
									if(registro.getName().equals("walletList")){
										
										if(registro.getChild("tipoWallet") != null){
											
											if(registro.getChild("tipoWallet").getValue().toLowerCase().equals("dinero") && !registro.getChild("cantidad").getValue().toLowerCase().equals("ilimitado") && Utilerias.isNumber(registro.getChild("cantidad").getValue()) == true ){/////////////////////////////////////////////
												consultaWalletDinero = new ConsultaWalletListVO();
												
												if(registro.getChild("idWallet") != null)
													consultaWalletDinero.setIdWallet(registro.getChild("idWallet").getValue());
												
												if(registro.getChild("wallet") != null){
													consultaWalletDinero.setWallet(Utilerias.getStringUTF8(registro.getChild("wallet").getValue()));
													
													if(registro.getChild("wallet").getValue().toLowerCase().equals("tiempo aire comprado")){
														consultaWalletDinero.setIdValor("1");
													}else if(registro.getChild("wallet").getValue().toLowerCase().equals("tiempo aire incluido o de regalo")){
														consultaWalletDinero.setIdValor("2");
													}
													
												}
													
												if(registro.getChild("tipoWallet") != null)
													consultaWalletDinero.setTipoWallet(registro.getChild("tipoWallet").getValue());
												
												if(registro.getChild("prioridad") != null)
													consultaWalletDinero.setPrioridad(registro.getChild("prioridad").getValue());
												
												if(registro.getChild("fechaFin") != null)
													consultaWalletDinero.setFechaFin(registro.getChild("fechaFin").getValue());
												
												if(registro.getChild("escenario") != null)
													consultaWalletDinero.setEscenario(registro.getChild("escenario").getValue());
												
												
												if(registro.getChild("unidadWallet") != null)
													consultaWalletDinero.setUnidad(registro.getChild("unidadWallet").getValue());
												
												if(registro.getChild("cantidad") != null){
													if(Utilerias.isNumber(registro.getChild("cantidad").getValue())){
														cantidad = Double.valueOf(registro.getChild("cantidad").getValue());
														lcantidad = cantidad.longValue();
														consultaWalletDinero.setCantidad(String.valueOf(lcantidad));
														
														totalDinero = totalDinero +  lcantidad;
													}else{
														consultaWalletDinero.setCantidad(registro.getChild("cantidad").getValue());
													}
												}
												
												listConsultaWalletsDinero.add(consultaWalletDinero);
											}else if(registro.getChild("tipoWallet").getValue().toLowerCase().equals("voz") && !registro.getChild("cantidad").getValue().toLowerCase().equals("ilimitado") && Utilerias.isNumber(registro.getChild("cantidad").getValue()) == true){/////////////////////////////////////////
												consultaWalletVoz = new ConsultaWalletListVO();
												
												if(registro.getChild("idWallet") != null)
													consultaWalletVoz.setIdWallet(registro.getChild("idWallet").getValue());
												
												if(registro.getChild("wallet") != null)
													consultaWalletVoz.setWallet(Utilerias.getStringUTF8(registro.getChild("wallet").getValue()));
												
												if(registro.getChild("tipoWallet") != null)
													consultaWalletVoz.setTipoWallet(registro.getChild("tipoWallet").getValue());
												
												if(registro.getChild("prioridad") != null)
													consultaWalletVoz.setPrioridad(registro.getChild("prioridad").getValue());
												
												if(registro.getChild("fechaFin") != null)
													consultaWalletVoz.setFechaFin(registro.getChild("fechaFin").getValue());
												
												if(registro.getChild("escenario") != null)
													consultaWalletVoz.setEscenario(Utilerias.getStringUTF8(registro.getChild("escenario").getValue()));
												
												if(registro.getChild("unidadWallet") != null)
													consultaWalletVoz.setUnidad(registro.getChild("unidadWallet").getValue());
												
												if(registro.getChild("cantidad") != null){
													if(Utilerias.isNumber(registro.getChild("cantidad").getValue())){
														cantidad = Double.valueOf(registro.getChild("cantidad").getValue());
														lcantidad = cantidad.longValue();	
														
														consultaWalletVoz.setCantidad(String.valueOf(lcantidad));
														totalVoz = totalVoz + lcantidad;
													}else{
														consultaWalletVoz.setCantidad(registro.getChild("cantidad").getValue());
													}
													
												}
												
												listConsultaWalletsVoz.add(consultaWalletVoz);
											}else if(registro.getChild("tipoWallet").getValue().toLowerCase().equals("sms") && !registro.getChild("cantidad").getValue().toLowerCase().equals("ilimitado") && Utilerias.isNumber(registro.getChild("cantidad").getValue()) == true){//////////////////////////////////////////
												consultaWalletSms = new ConsultaWalletListVO();
												
												if(registro.getChild("idWallet") != null)
													consultaWalletSms.setIdWallet(registro.getChild("idWallet").getValue());
												
												if(registro.getChild("wallet") != null)
													consultaWalletSms.setWallet(registro.getChild("wallet").getValue());
												
												if(registro.getChild("tipoWallet") != null)
													consultaWalletSms.setTipoWallet(registro.getChild("tipoWallet").getValue());
												
												if(registro.getChild("prioridad") != null)
													consultaWalletSms.setPrioridad(registro.getChild("prioridad").getValue());
												
												if(registro.getChild("fechaFin") != null)
													consultaWalletSms.setFechaFin(registro.getChild("fechaFin").getValue());
												
												if(registro.getChild("escenario") != null)
													consultaWalletSms.setEscenario(registro.getChild("escenario").getValue());
												
												if(registro.getChild("unidadWallet") != null)
													consultaWalletSms.setUnidad(registro.getChild("unidadWallet").getValue());
												
												if(registro.getChild("cantidad") != null){
													if(Utilerias.isNumber(registro.getChild("cantidad").getValue())){
														cantidad = Double.valueOf(registro.getChild("cantidad").getValue());
														lcantidad = cantidad.longValue();
														
														consultaWalletSms.setCantidad(String.valueOf(lcantidad));
														totalSms = totalSms + lcantidad;
													}else{
														consultaWalletSms.setCantidad(registro.getChild("cantidad").getValue());
													}
												}
												
												listConsultaWalletsSms.add(consultaWalletSms);
											}else if(registro.getChild("tipoWallet").getValue().toLowerCase().equals("datos") && !registro.getChild("cantidad").getValue().toLowerCase().equals("ilimitado") && Utilerias.isNumber(registro.getChild("cantidad").getValue()) == true){////////////////////////////////////////
												consultaWalletDatos = new ConsultaWalletListVO();
												
												if(registro.getChild("idWallet") != null)
													consultaWalletDatos.setIdWallet(registro.getChild("idWallet").getValue());
												
												if(registro.getChild("wallet") != null)
													consultaWalletDatos.setWallet(registro.getChild("wallet").getValue());
												
												
												if(registro.getChild("tipoWallet") != null)
													consultaWalletDatos.setTipoWallet(registro.getChild("tipoWallet").getValue());
												
												if(registro.getChild("prioridad") != null)
													consultaWalletDatos.setPrioridad(registro.getChild("prioridad").getValue());
												
												if(registro.getChild("fechaFin") != null)
													consultaWalletDatos.setFechaFin(registro.getChild("fechaFin").getValue());
												
												if(registro.getChild("escenario") != null)
													consultaWalletDatos.setEscenario(registro.getChild("escenario").getValue());
												
												if(registro.getChild("unidadWallet") != null)
													consultaWalletDatos.setUnidad(registro.getChild("unidadWallet").getValue());
												
												if(registro.getChild("unidadWallet").getValue().toLowerCase().equals("kbytes")||registro.getChild("unidadWallet").getValue().toLowerCase().equals("mbytes")){
													if(registro.getChild("cantidad") != null){
														
														if(Utilerias.isNumber(registro.getChild("cantidad").getValue())){
															if (registro.getChild("unidadWallet").getValue().toLowerCase().equals("mbytes")){
																consultaWalletDatos.setCantidad(registro.getChild("cantidad").getValue().trim());
															}else  consultaWalletDatos.setCantidad(Utilerias.valorMegaBytes(registro.getChild("cantidad").getValue().trim()));
														  totalDatos = totalDatos + Double.valueOf(consultaWalletDatos.getCantidad());
														}else{
															consultaWalletDatos.setCantidad(registro.getChild("cantidad").getValue());	
														}
														
													}
												}
												
												listConsultaWalletsDatos.add(consultaWalletDatos);
											}else if((registro.getChild("tipoWallet").getValue().toLowerCase().equals("unidad") || registro.getChild("tipoWallet").getValue().toLowerCase().equals("min/sms")) && !registro.getChild("cantidad").getValue().toLowerCase().equals("ilimitado") && Utilerias.isNumber(registro.getChild("cantidad").getValue()) == true ){
												consultaWalletsUnidad = new ConsultaWalletListVO();
												
												if(registro.getChild("idWallet") != null)
													consultaWalletsUnidad.setIdWallet(registro.getChild("idWallet").getValue());
												
												if(registro.getChild("wallet") != null)
													consultaWalletsUnidad.setWallet(registro.getChild("wallet").getValue());
												
												
												if(registro.getChild("tipoWallet") != null)
													consultaWalletsUnidad.setTipoWallet(registro.getChild("tipoWallet").getValue());
												
												if(registro.getChild("prioridad") != null)
													consultaWalletsUnidad.setPrioridad(registro.getChild("prioridad").getValue());
												
												if(registro.getChild("fechaFin") != null)
													consultaWalletsUnidad.setFechaFin(registro.getChild("fechaFin").getValue());
												
												if(registro.getChild("escenario") != null)
													consultaWalletsUnidad.setEscenario(registro.getChild("escenario").getValue());
												
												if(registro.getChild("unidadWallet") != null)
													consultaWalletsUnidad.setUnidad(registro.getChild("unidadWallet").getValue());
												
												if(registro.getChild("cantidad") != null){
													if(Utilerias.isNumber(registro.getChild("cantidad").getValue())){
														cantidad = Double.valueOf(registro.getChild("cantidad").getValue());
														lcantidad = cantidad.longValue();
														consultaWalletsUnidad.setCantidad(String.valueOf(lcantidad));
														
													}else{
														consultaWalletsUnidad.setCantidad(registro.getChild("cantidad").getValue());
													}
												}
												
												listConsultaWalletUnidadVO.add(consultaWalletsUnidad);

											}else if(registro.getChild("cantidad").getValue().toLowerCase().contains("ilimitado") || registro.getChild("cantidad").getValue().toLowerCase().contains("limitada")){
												redesIlimitadas = true;
												consultaWalletsIlimitado = new ConsultaWalletListVO();
												
												if(registro.getChild("idWallet")   != null) { consultaWalletsIlimitado.setIdWallet(registro.getChild("idWallet").getValue());     }
												if(registro.getChild("wallet")     != null) { consultaWalletsIlimitado.setWallet(Utilerias.getStringUTF8(registro.getChild("wallet").getValue()));         }
												if(registro.getChild("tipoWallet") != null) { consultaWalletsIlimitado.setTipoWallet(registro.getChild("tipoWallet").getValue()); }
												if(registro.getChild("prioridad")  != null) { consultaWalletsIlimitado.setPrioridad(registro.getChild("prioridad").getValue());   }
												if(registro.getChild("fechaFin")   != null) { consultaWalletsIlimitado.setFechaFin(registro.getChild("fechaFin").getValue());     }
												if(registro.getChild("escenario")  != null) { consultaWalletsIlimitado.setEscenario(Utilerias.getStringUTF8(registro.getChild("escenario").getValue()));   }
												if(registro.getChild("unidadWallet")!= null) { consultaWalletsIlimitado.setUnidad(registro.getChild("unidadWallet").getValue());     }
												if(registro.getChild("cantidad")   != null) { consultaWalletsIlimitado.setCantidad(registro.getChild("cantidad").getValue());     }
												
												listConsultaWalletIlimitadoVO.add(consultaWalletsIlimitado);
											}
										}
									}									
								}								
							}
							
							if(registroP.getName().equals("idPlan")){
								idPlan = registroP.getValue();
							}

							if(registroP.getName().equals("serviceClass")){
								serviceClass = registroP.getValue();
							}
						}																
						
						if(listConsultaWalletsDinero != null && listConsultaWalletsDinero.size() > 0)
						  consultaWalletsGeneral.setConsultaWalletsDinero(listConsultaWalletsDinero);
						
						if(listConsultaWalletsVoz != null && listConsultaWalletsVoz.size() > 0)
						   consultaWalletsGeneral.setConsultaWalletsVoz(listConsultaWalletsVoz);
						
						if(listConsultaWalletsSms != null && listConsultaWalletsSms.size() > 0)
						  consultaWalletsGeneral.setConsultaWalletsSms(listConsultaWalletsSms);
						
						if(listConsultaWalletsDatos != null && listConsultaWalletsDatos.size() > 0)
						  consultaWalletsGeneral.setConsultaWalletsDatos(listConsultaWalletsDatos);
						
						if(listConsultaWalletIlimitadoVO != null && listConsultaWalletIlimitadoVO.size() > 0)
						  consultaWalletsGeneral.setConsultaWalletsIlimitado(listConsultaWalletIlimitadoVO);
						
						if(listConsultaWalletUnidadVO != null && listConsultaWalletUnidadVO.size() > 0)
							  consultaWalletsGeneral.setConsultaWalletsUnidad(listConsultaWalletUnidadVO);
						
						consultaWalletsGeneral.setTotalWalletsDinero(String.valueOf(totalDinero));
					    consultaWalletsGeneral.setTotalWalletsDatos(Utilerias.expoFormat(String.valueOf(totalDatos)));
						consultaWalletsGeneral.setTotalWalletsSms(String.valueOf(totalSms));
						consultaWalletsGeneral.setTotalWalletsVoz(String.valueOf(totalVoz));
						consultaWalletsGeneral.setTotalRedesIlimitadas(String.valueOf(redesIlimitadas));
						
						try
						{
							int servClass = 0;							
							if(serviceClass != null && !serviceClass.equals("")){
								servClass = Integer.parseInt(serviceClass);
								consultaWalletsGeneral.setServiceClass(serviceClass);
							}	
							
							//descPlan =	oracle.getDescPlanXServiceClass(idPlan, servClass, 111111,"10.189.64.53");
							descPlan = OfertaComercial.getDescripcionPlanServiceClass(compania, dn, servClass, idPlan);
						}
						catch (Exception e) {
							Logger.write("   (Exception) Al consultar descripcion plan : " +  e.getLocalizedMessage());
						}
						
						if(descPlan == null || descPlan.equals(""))
						{
							OracleProcedures oracle = new OracleProcedures();
							descPlan = oracle.getValorParametro(148);
						}
						consultaWalletsGeneral.setPlanDescripcion(descPlan);
						
						listConsultaWalletsGeneral.add(consultaWalletsGeneral);
						
					}else{
						childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaWalletResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChildren();
						it = childrens.iterator();
						while (it.hasNext()) {
							Element registro = (Element) it.next();
							error += ((" ")+ "[CTRL] "+registro.getValue());
						}
						throw new ServiceException(error);
					}
					
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaWalletResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+ "[CTRL] "+ registro.getValue());
					}
					throw new ServiceException(error);
					
				}
			}catch(Exception e){
				if(error!=null && !error.equals("")){
					Logger.write("   Detail        : (Exception) " +  error);
				}
				else{
					Logger.write("   Detail        : (Exception) " + e.getMessage());
				}
				throw new ServiceException(error);
			}
	
		return listConsultaWalletsGeneral;
	}
	
	@SuppressWarnings("unchecked")
	public static RespuestaVO parseCambioPlan(final String resultadoXML) throws ServiceException{
		String error = "";		
		RespuestaVO retorno = new RespuestaVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;				

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChild("cambiarPlanResponse", Namespace.getNamespace("http://ws.cambioplan.midtelco.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChild("cambiarPlanResponse", Namespace.getNamespace("http://ws.cambioplan.midtelco.iusacell.com.mx")).getChild("return", Namespace.getNamespace("http://ws.cambioplan.midtelco.iusacell.com.mx")).getChildren();					
					it = childrens.iterator();
					while (it.hasNext())
					{		
						Element registro = (Element) it.next();
						 if(registro.getName().equals("cdgRespuesta"))
							 retorno.setCdgRespuesta(registro.getValue());
						 
						 if(registro.getName().equals("exito"))
							 retorno.setExito(Boolean.valueOf(registro.getValue()));
						 
						 if(registro.getName().equals("mensaje"))
							 retorno.setMensaje(registro.getValue());
					}
				}
				else if( raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")) != null &&
						raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
						.getChild("Fault", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")) != null)
				{					
						childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
						.getChild("Fault", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChildren();
						it = childrens.iterator();
						while (it.hasNext()) {
							Element registro = (Element) it.next();
							error += ((" ")+registro.getValue());
						}
						throw new ServiceException(error);
					
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	public static RespuestaVO ParseNumeroFrecuente(final String resultadoXML) throws ServiceException{
		
		String error="";
		SAXBuilder builder1 = new SAXBuilder();
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		RespuestaVO response = new RespuestaVO();
		
		try{
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChild("operacionServicioResponse", Namespace.getNamespace("http://impl.servicios.iusacell.com.mx")) != null){
				 childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")).getChild("operacionServicioResponse", Namespace.getNamespace("http://impl.servicios.iusacell.com.mx")).getChild("return", Namespace.getNamespace("http://impl.servicios.iusacell.com.mx")).getChild("operacionServicio", Namespace.getNamespace("http://vo.servicios.iusacell.com.mx/xsd")).getChildren();
	             
				 it = childrens.iterator();
			        while(it.hasNext()){
			        	Element registro = (Element) it.next();
			        	
			        	if(registro.getName().equals("respuesta"))
			        		 response.setExito(Boolean.valueOf(registro.getValue()));
			        	
			        	if(registro.getName().equals("folioPreactivacion"))
			        		 response.setFolio(registro.getValue());
			        }
			}
			else if(raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
					.getChild("Fault", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope")) != null)
			{
				response.setExito(false);
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
								.getChild("Fault", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
								.getChild("Detail", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
								.getChild("SServiciosImplExceptionVO", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
								.getChildren();
				it = childrens.iterator();
				while(it.hasNext())
				{
					Element registro = (Element) it.next();
		        	
		        	if(registro.getName().equals("codigoExcepcion"))
		        		 response.setCdgRespuesta(registro.getValue());
		        	
		        	if(registro.getName().equals("mensaje"))
		        		 response.setMensaje(registro.getValue());
				}
			}
		}catch (Exception e) {
			if(error!=null && !error.equals("")){
				throw new ServiceException(error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
				throw new ServiceException("Ocurrio un error al obtener valores de ParseNumeroFrecuente");
			}
		}
         return response;		
	}
	
	@SuppressWarnings("unchecked")
	public static MensajeMailVO ParseMensajeCorreo(final String resultadoXML) throws ServiceException{
		String error = "";
		MensajeMailVO mensajeMail = new MensajeMailVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaMailResponse", Namespace.getNamespace("http://services.message.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaMailResponse", Namespace.getNamespace("http://services.message.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://services.message.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						 if(registros.getName().equals("mensaje"))
						     mensajeMail.setMailError(registros.getValue());
						 
						 if(registros.getName().equals("enviado"))
						     mensajeMail.setMailSended(registros.getValue());
					}
				}
				else if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaSMSResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaSMSResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						
						 if(registros.getName().equals("mensaje"))
						     mensajeMail.setSmsError(registros.getValue());
						 
						 if(registros.getName().equals("enviado"))
						     mensajeMail.setSmsSended(registros.getValue());
					}
				}
				else if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaMailResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaMailResponse", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://messenger.services.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						
						if(registros.getName().equals("mensaje"))
							 mensajeMail.setMailError(registros.getValue());
						 
						 if(registros.getName().equals("enviado"))
						     mensajeMail.setMailSended(registros.getValue());
					}
				}
				else if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaSMSResponse", Namespace.getNamespace("http://services.message.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("enviaSMSResponse", Namespace.getNamespace("http://services.message.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://services.message.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while(it.hasNext()){
						Element registros = (Element) it.next();
						
						if(registros.getName().equals("mensaje"))
							 mensajeMail.setMailError(registros.getValue());
						 
						 if(registros.getName().equals("enviado"))
						     mensajeMail.setMailSended(registros.getValue());
					}
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException("Ocurrio un error al enviar el mensaje ");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return mensajeMail;
	}
	@SuppressWarnings("unchecked")
	public static SCobrosVO parseAplicaSCobro(final String resultadoXML) throws ServiceException{
		String error = "";		
		SCobrosVO retorno = new SCobrosVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;				
			List<Element> wallets = null;
			Iterator<Element> itW = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
						.getChild("aplicaCobroResponse", Namespace.getNamespace("http://ws.cobros.midtelco.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
					.getChild("aplicaCobroResponse", Namespace.getNamespace("http://ws.cobros.midtelco.iusacell.com.mx"))
					.getChild("return", Namespace.getNamespace("http://ws.cobros.midtelco.iusacell.com.mx")).getChildren();					
					it = childrens.iterator();
					retorno.setWallets(new ArrayList<WalletsVO>());
					while (it.hasNext())
					{		
						Element registro = (Element) it.next();
						if(registro.getName().equals("codigo"))
							retorno.setCodigo(registro.getValue());						 						

						if(registro.getName().equals("mensaje"))
							retorno.setMensaje(registro.getValue());

						if(registro.getName().equals("idCobro"))
						{
							if(registro.getValue() != null && !registro.getValue().equals(""))							 
								retorno.setIdCobro(Integer.valueOf(registro.getValue()));
						}

						if(registro.getName().equals("walletsCobrados"))
						{
							wallets = registro.getChildren();
							itW = wallets.iterator();							
							WalletsVO walletVO = new WalletsVO();
							while(itW.hasNext())
							{
								Element wallet = (Element) itW.next();
								if(wallet.getName().equals("id"))
								{
									if(wallet.getValue() != null && !wallet.getValue().equals(""))
										walletVO.setId(Integer.valueOf(wallet.getValue()));
								}

								if(wallet.getName().equals("cantidad"))
								{
									if(wallet.getValue() != null && !wallet.getValue().equals(""))
										walletVO.setCantidad(Integer.valueOf(wallet.getValue()));
								}

								if(wallet.getName().equals("unidad"))
								{
									if(wallet.getValue() != null && !wallet.getValue().equals(""))
										walletVO.setUnidad(Integer.valueOf(wallet.getValue()));
								}

								if(wallet.getName().equals("vigencia"))
								{
									if(wallet.getValue() != null && !wallet.getValue().equals(""))
										walletVO.setVigencia(Boolean.valueOf(wallet.getValue()));
								}
							}
							if(walletVO.getId() != 0)
								retorno.getWallets().add(walletVO);
						}
					}
				}
				else if(raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
						.getChild("Fault", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
						.getChild("Detail", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
						.getChild("CobrosWSMNException", Namespace.getNamespace("http://ws.cobros.midtelco.iusacell.com.mx"))!= null)
				{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
					.getChild("Fault", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
					.getChild("Detail", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
					.getChild("CobrosWSMNException", Namespace.getNamespace("http://ws.cobros.midtelco.iusacell.com.mx"))
					.getChild("MNException", Namespace.getNamespace("http://ws.cobros.midtelco.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						if(registro.getName().equals("codigoError"))
							error += "[" + registro.getValue() + "] ";

						if(registro.getName().equals("mensajeError"))
							error += registro.getValue();
					}
					throw new ServiceException(error);
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			throw new ServiceException("SCobros " +e.getMessage());
		}
		return retorno;
	}

	@SuppressWarnings("unchecked")
	public static SCobrosVO parseReversoSCobro(final String resultadoXML) throws ServiceException{
		String error = "";		
		SCobrosVO retorno = new SCobrosVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;				
			List<Element> wallets = null;
			Iterator<Element> itW = null;

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
						.getChild("reversoCobroResponse", Namespace.getNamespace("http://ws.cobros.midtelco.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
					.getChild("reversoCobroResponse", Namespace.getNamespace("http://ws.cobros.midtelco.iusacell.com.mx"))
					.getChild("return", Namespace.getNamespace("http://ws.cobros.midtelco.iusacell.com.mx")).getChildren();					
					it = childrens.iterator();
					retorno.setWallets(new ArrayList<WalletsVO>());
					while (it.hasNext())
					{		
						Element registro = (Element) it.next();
						if(registro.getName().equals("codigo"))
							retorno.setCodigo(registro.getValue());						 						

						if(registro.getName().equals("mensaje"))
							retorno.setMensaje(registro.getValue());

						if(registro.getName().equals("idCobro"))
						{
							if(registro.getValue() != null && !registro.getValue().equals(""))
								retorno.setIdCobro(Integer.valueOf(registro.getValue()));
						}

						if(registro.getName().equals("walletsCobrados"))
						{
							wallets = registro.getChildren();
							itW = wallets.iterator();							
							WalletsVO walletVO = new WalletsVO();
							while(itW.hasNext())
							{
								Element wallet = (Element) itW.next();
								if(wallet.getName().equals("id"))
								{
									if(wallet.getValue() != null && !wallet.getValue().equals(""))
										walletVO.setId(Integer.valueOf(wallet.getValue()));
								}

								if(wallet.getName().equals("cantidad"))
								{
									if(wallet.getValue() != null && !wallet.getValue().equals(""))
										walletVO.setCantidad(Integer.valueOf(wallet.getValue()));
								}

								if(wallet.getName().equals("unidad"))
								{
									if(wallet.getValue() != null && !wallet.getValue().equals(""))
										walletVO.setUnidad(Integer.valueOf(wallet.getValue()));
								}

								if(wallet.getName().equals("vigencia"))
								{
									if(wallet.getValue() != null && !wallet.getValue().equals(""))
										walletVO.setVigencia(Boolean.valueOf(wallet.getValue()));
								}
							}
							if(walletVO.getId() != 0)
								retorno.getWallets().add(walletVO);
						}
					}
				}
				else if(raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
						.getChild("Fault", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
						.getChild("Detail", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
						.getChild("CobrosWSMNException", Namespace.getNamespace("http://ws.cobros.midtelco.iusacell.com.mx"))!= null)
				{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
					.getChild("Fault", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
					.getChild("Detail", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
					.getChild("CobrosWSMNException", Namespace.getNamespace("http://ws.cobros.midtelco.iusacell.com.mx"))
					.getChild("MNException", Namespace.getNamespace("http://ws.cobros.midtelco.iusacell.com.mx")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						if(registro.getName().equals("codigoError"))
							error += "[" + registro.getValue() + "] ";

						if(registro.getName().equals("mensajeError"))
							error += registro.getValue();
					}
					throw new ServiceException(error);
				}
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			throw new ServiceException("SCobros " +e.getMessage());
		}
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	public static String parseGetServiceOfferingValue(final String resultadoXML, final String serviceOfferingId) throws ServiceException{
		String error = "";		
		String valorServiceOfferingId = "";
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;							

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
						.getChild("getAccountDetailsResponse", Namespace.getNamespace("http://air.prov.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
					.getChild("getAccountDetailsResponse", Namespace.getNamespace("http://air.prov.iusacell.com.mx"))
					.getChild("return", Namespace.getNamespace("http://air.prov.iusacell.com.mx")).getChildren();					
					it = childrens.iterator();					
					while (it.hasNext())
					{		
						Element registro = (Element) it.next();					

						if(registro.getName().equals("serviceOfferings"))
						{																																									
								
								if(registro.getChild("serviceOfferingID", Namespace.getNamespace("http://response.common.air.prov.iusacell.com.mx/xsd")) != null &&
										registro.getChild("serviceOfferingID", Namespace.getNamespace("http://response.common.air.prov.iusacell.com.mx/xsd"))
										.getValue().equals(serviceOfferingId)	)
								{									
									valorServiceOfferingId = registro.getChild("serviceOfferingActiveFlag",
											Namespace.getNamespace("http://response.common.air.prov.iusacell.com.mx/xsd")).getValue();
										break;
								}															
						}
					}
				}				
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return valorServiceOfferingId;
	}
	
	@SuppressWarnings("unchecked")
	public static RespuestaVO parseUpdateSubscriberSegmentation(final String resultadoXML) throws ServiceException{
		String error = "";		
		RespuestaVO response = new RespuestaVO();
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;							

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
						.getChild("updateSubscriberSegmentationResponse", Namespace.getNamespace("http://air.prov.iusacell.com.mx")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://www.w3.org/2003/05/soap-envelope"))
					.getChild("updateSubscriberSegmentationResponse", Namespace.getNamespace("http://air.prov.iusacell.com.mx"))
					.getChild("return", Namespace.getNamespace("http://air.prov.iusacell.com.mx")).getChildren();					
					it = childrens.iterator();					
					while (it.hasNext())
					{		
						Element registro = (Element) it.next();					
						if(registro.getName().equals("response"))																																														
							response.setExito(Boolean.valueOf(registro.getValue()));											
						
						if(registro.getName().equals("responseCode"))						
							response.setCdgRespuesta(registro.getValue());
						
						if(registro.getName().equals("responseMessage"))						
							response.setMensaje(registro.getValue());						
					}
				}				
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
					it = childrens.iterator();
					while (it.hasNext()) {
						Element registro = (Element) it.next();
						error += ((" ")+registro.getValue());
					}
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
	
	public static String parseCardBlackList(final String resultadoXML) throws ServiceException{
		String error = "";		
		String response ="";
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			Element childrens = null;
										

			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			try{
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
						.getChild("cardBlackListResponse", Namespace.getNamespace("http://services.blacklist.iusacell.com.mx/")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
					.getChild("cardBlackListResponse", Namespace.getNamespace("http://services.blacklist.iusacell.com.mx/")).getChild("return");					
					response=childrens.getValue();
				}				
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"));
						error += ((" ")+childrens.getValue());
					
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return response;
	}

	public static String parseSemaphoreDeleteBankCardInfo(String resultadoXML) throws ServiceException{
		String error = "";		
		String response ="";
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			Element childrens = null;

			try{
				
				if(!resultadoXML.contains("SOAP-ENV:Envelope")){
					resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
					"<soapenv:Header/>"+
					"<soapenv:Body>" + resultadoXML;
					resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
				}
				/** recorre resultadoXML **/
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
						.getChild("deleteBankCardInfoResponse", Namespace.getNamespace("http://core.semaphore.iusacell.com")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
					.getChild("deleteBankCardInfoResponse", Namespace.getNamespace("http://core.semaphore.iusacell.com"));					
					response=childrens.getValue().toString();
				}				
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
									.getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"));
					error += ((" ")+childrens.getChild("faultstring").getValue());
					
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return response;
	}
	
	public static String parseSemaphoresaveCustomerInfo(String resultadoXML) throws ServiceException{
		String error = "";		
		String response ="";
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			Element childrens = null;

			try{
				
				if(!resultadoXML.contains("SOAP-ENV:Envelope")){
					resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
					"<soapenv:Header/>"+
					"<soapenv:Body>" + resultadoXML;
					resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
				}
				/** recorre resultadoXML **/
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
						.getChild("saveCustomerInfoResponse", Namespace.getNamespace("http://core.semaphore.iusacell.com")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
					.getChild("saveCustomerInfoResponse", Namespace.getNamespace("http://core.semaphore.iusacell.com"));					
					response=childrens.getValue().toString();
				}				
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
									.getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"));
					error += ((" ")+childrens.getChild("faultstring").getValue());
					
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return response;
	}
	public static String parseSemaphoreApplyCharge(String resultadoXML) throws ServiceException{
		String error = "";		
		String response ="";
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			Element childrens = null;

			try{
				
				if(!resultadoXML.contains("SOAP-ENV:Envelope")){
					resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
					"<soapenv:Header/>"+
					"<soapenv:Body>" + resultadoXML;
					resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
				}
				/** recorre resultadoXML **/
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
						.getChild("applyChargeResponse", Namespace.getNamespace("http://core.semaphore.iusacell.com")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
					.getChild("applyChargeResponse", Namespace.getNamespace("http://core.semaphore.iusacell.com"));					
					response=childrens.getValue().toString();
				}				
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
									.getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"));
					error += ((" ")+childrens.getChild("faultstring").getValue());
					
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return response;
	}
	public static String parseSemaphoreUpdateBankCardInfo(String resultadoXML) throws ServiceException{
		String error = "";		
		String response ="";
		try {
			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			Element childrens = null;

			try{
				
				if(!resultadoXML.contains("SOAP-ENV:Envelope")){
					resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
					"<soapenv:Header/>"+
					"<soapenv:Body>" + resultadoXML;
					resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
				}
				/** recorre resultadoXML **/
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				
				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
						.getChild("updateBankCardInfoResponse", Namespace.getNamespace("http://core.semaphore.iusacell.com")) != null){
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
					.getChild("updateBankCardInfoResponse", Namespace.getNamespace("http://core.semaphore.iusacell.com"));					
					response=childrens.getValue().toString();
				}				
				else{
					childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
									.getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"));
					error += ((" ")+childrens.getChild("faultstring").getValue());
					
					throw new ServiceException(error);
				}
			}catch (Exception e) {
				if(error!=null && !error.equals("")){
					throw new ServiceException(error);
				}
				else{
					throw new ServiceException(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return response;
	}
	
@SuppressWarnings("unchecked")
public static List<ObtenerDetallesServicesClassVO> parseDetalleServiceClass(String resultadoXML) throws ServiceException{
		
		ObtenerDetallesServicesClassVO	obtenerDetalle = new ObtenerDetallesServicesClassVO();
		List<ObtenerDetallesServicesClassVO> listObtenerDetalle = new ArrayList<ObtenerDetallesServicesClassVO>();
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		
		try{
			

			if(!resultadoXML.contains("SOAP-ENV:Envelope")){
				resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
				"<soapenv:Header/>"+
				"<soapenv:Body>" + resultadoXML;
				resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			}
			
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("obtenerDetallesServiceClassResponse", Namespace.getNamespace("http://prepago.iusacell.com.mx")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("obtenerDetallesServiceClassResponse", Namespace.getNamespace("http://prepago.iusacell.com.mx")).getChildren();
				it = childrens.iterator();
				
				while (it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getName().equals("return")){
						obtenerDetalle = new ObtenerDetallesServicesClassVO();
						
						if(registro.getChild("caracteristicaSC", Namespace.getNamespace("http://vo.prepago.iusacell.com.mx/xsd")).getValue() != null)
						    obtenerDetalle.setCaracteristicaSC(registro.getChild("caracteristicaSC", Namespace.getNamespace("http://vo.prepago.iusacell.com.mx/xsd")).getValue());
						else
							obtenerDetalle.setCaracteristicaSC("");
						
						if(registro.getChild("estatus", Namespace.getNamespace("http://vo.prepago.iusacell.com.mx/xsd")).getValue() != null)
						    obtenerDetalle.setEstatus(registro.getChild("estatus", Namespace.getNamespace("http://vo.prepago.iusacell.com.mx/xsd")).getValue());
						else
							obtenerDetalle.setEstatus("");
				
						if(registro.getChild("infoCaracteristica", Namespace.getNamespace("http://vo.prepago.iusacell.com.mx/xsd")).getValue() != null)
						    obtenerDetalle.setInfoCaracteristica(registro.getChild("infoCaracteristica", Namespace.getNamespace("http://vo.prepago.iusacell.com.mx/xsd")).getValue());
						else
							obtenerDetalle.setInfoCaracteristica("");
				
						if(registro.getChild("serviceClass", Namespace.getNamespace("http://vo.prepago.iusacell.com.mx/xsd")).getValue() != null)
						    obtenerDetalle.setServiceClass(registro.getChild("serviceClass", Namespace.getNamespace("http://vo.prepago.iusacell.com.mx/xsd")).getValue());
						else
							obtenerDetalle.setServiceClass("");
						
						listObtenerDetalle.add(obtenerDetalle);
						
					}
				}
			}else{
				obtenerDetalle.setCaracteristicaSC("");
				obtenerDetalle.setEstatus("");
				obtenerDetalle.setInfoCaracteristica("");
				obtenerDetalle.setServiceClass("");
				
				listObtenerDetalle.add(obtenerDetalle);
			}
			
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
	    return 	listObtenerDetalle;
	}

    @SuppressWarnings("unchecked")
	public static RespuestaServiciosVO respuestaAltaServiciosUnefon(final String response) throws ServiceException
    {
        /* --------------------------------------------------------------------
         *                 O b j e t o   d e   R e s p u e s t a
         * ----------------------------------------------------------------- */
        RespuestaServiciosVO respuesta = new RespuestaServiciosVO();
        
        /*---------------------------------------------------------------------
         *                 O b j e t o s    a u x i l i a r e s
         *-------------------------------------------------------------------*/
        String            error     = "";
        SAXBuilder        saxBuider = new SAXBuilder();
        Document          document  = null;
        Element           raiz      = null;
        List<Element>     hijos     = null;
        Iterator<Element> iterator  = null;
        
        try
        {
            document = saxBuider.build(new StringReader(response));
            raiz     = document.getRootElement();
            
            if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("operacionServicioResponse", Namespace.getNamespace("http://gestion.servicios.middleware.iusacell.com.mx")) != null)
            {
                hijos = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("operacionServicioResponse", Namespace.getNamespace("http://gestion.servicios.middleware.iusacell.com.mx")).getChild("respuesta").getChildren();
                
                iterator = hijos.iterator();
             
                while(iterator.hasNext())
                {
                    Element registro = (Element) iterator.next();
                    
                    if(registro.getName().equals("respuesta"))
                    {
                        respuesta.setRespuesta(registro.getValue() != null && !registro.getValue().trim().isEmpty() && registro.getValue().trim().equals("true")?true:false);
                    }
                    else if(registro.getName().equals("folioPreactivacion"))
                    {
                        int r = -1;
                        try{r=Integer.parseInt(registro.getValue().trim());}catch(NumberFormatException e){r=-1;}
                        respuesta.setFolioPreactivacion(r);
                    }
                    else if(registro.getName().equals("idTx"))
                    {
                        int r = -1;
                        try{r=Integer.parseInt(registro.getValue().trim());}catch(NumberFormatException e){r=-1;}
                        respuesta.setIdTx(r);
                    }
                    else if(registro.getName().equals("codigo"))
                    {
                        int r = -1;
                        try{r=Integer.parseInt(registro.getValue().trim());}catch(NumberFormatException e){r=-1;}
                        respuesta.setCodigo(r);
                    }
                    else if(registro.getName().equals("mensaje"))
                    {
                        respuesta.setMensaje(registro.getValue()!= null?registro.getValue():"");
                    }
                    
                    registro = null;
                }
            }
            else
            {
                hijos = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
                
                iterator = hijos.iterator();
                
                while (iterator.hasNext()) {
                    
                    Element registro = (Element) iterator.next();
                    
                    if(registro.getName().equals("Fault"))
                    {
                        hijos = registro.getChildren();
                        Iterator<Element> iterator2 = hijos.iterator();
                        
                        while (iterator2.hasNext())
                        {
                            Element registro2 = (Element) iterator2.next();
                            
                            if(registro2.getName().equals("detail"))
                            {
                                hijos = registro2.getChildren();
                                Iterator<Element> iterator3 = hijos.iterator();
                                
                                while (iterator3.hasNext())
                                {
                                    Element registro3 = (Element) iterator3.next();
                                    if(registro3.getName().equals("Falta_element"))
                                    {
                                        hijos = registro3.getChildren();
                                        Iterator<Element> iterator4 = hijos.iterator();
                                        
                                        while (iterator4.hasNext())
                                        {
                                            Element registro4 = (Element) iterator4.next();
                                            error += ((" ")+registro4.getValue());
                                        }
                                        iterator4 = null;
                                    }
                                }
                                iterator3 = null;
                            }
                            registro2 = null;
                        }
                        iterator2 = null;
                    }
                    registro = null;
                }
                
                throw new ServiceException(error);
            }
        }
        catch (Exception e)
        {
            if(error!=null && !error.equals("")){
                throw new ServiceException(error);
            }
            else{
                throw new ServiceException("No se recibiÛ respuesta XML");
            }
        }
        finally
        {
            error              = null;
            saxBuider          = null;
            document           = null;
            raiz               = null;
            hijos              = null;
            iterator           = null;
        }
        
        
        return respuesta;
    }
    
    @SuppressWarnings("unchecked")
    public static RespuestaServiciosVO respuestaBajaServiciosUnefon(final String response) throws ServiceException
    {
        /* --------------------------------------------------------------------
         *                 O b j e t o   d e   R e s p u e s t a
         * ----------------------------------------------------------------- */
        RespuestaServiciosVO respuesta = new RespuestaServiciosVO();
        
        /*---------------------------------------------------------------------
         *                 O b j e t o s    a u x i l i a r e s
         *-------------------------------------------------------------------*/
        String            error     = "";
        SAXBuilder        saxBuider = new SAXBuilder();
        Document          document  = null;
        Element           raiz      = null;
        List<Element>     hijos     = null;
        Iterator<Element> iterator  = null;
        
        try
        {
            document = saxBuider.build(new StringReader(response));
            raiz     = document.getRootElement();
            
            if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("bajaServiciosResponse", Namespace.getNamespace("http://gestion.servicios.middleware.iusacell.com.mx")) != null)
            {
                hijos = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("bajaServiciosResponse", Namespace.getNamespace("http://gestion.servicios.middleware.iusacell.com.mx")).getChild("respuestaBajas").getChildren();
                
                iterator = hijos.iterator();
             
                while(iterator.hasNext())
                {
                    Element registro = (Element) iterator.next();
                    
                    if(registro.getName().equals("respuesta"))
                    {
                        respuesta.setRespuesta(registro.getValue() != null && !registro.getValue().trim().isEmpty() && registro.getValue().trim().equals("true")?true:false);
                    }
                    else if(registro.getName().equals("folioPreactivacion"))
                    {
                        int r = -1;
                        try{r=Integer.parseInt(registro.getValue().trim());}catch(NumberFormatException e){r=-1;}
                        respuesta.setFolioPreactivacion(r);
                    }
                    else if(registro.getName().equals("idTx"))
                    {
                        int r = -1;
                        try{r=Integer.parseInt(registro.getValue().trim());}catch(NumberFormatException e){r=-1;}
                        respuesta.setIdTx(r);
                    }
                    else if(registro.getName().equals("codigo"))
                    {
                        int r = -1;
                        try{r=Integer.parseInt(registro.getValue().trim());}catch(NumberFormatException e){r=-1;}
                        respuesta.setCodigo(r);
                    }
                    else if(registro.getName().equals("mensaje"))
                    {
                        respuesta.setMensaje(registro.getValue()!= null?registro.getValue():"");
                    }
                    
                    registro = null;
                }
            }
            else
            { 
                hijos = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
                
                iterator = hijos.iterator();
                
                while (iterator.hasNext()) {
                    
                    Element registro = (Element) iterator.next();
                    
                    if(registro.getName().equals("Fault"))
                    {
                        hijos = registro.getChildren();
                        Iterator<Element> iterator2 = hijos.iterator();
                        
                        while (iterator2.hasNext())
                        {
                            Element registro2 = (Element) iterator2.next();
                            
                            if(registro2.getName().equals("detail"))
                            {
                                hijos = registro2.getChildren();
                                Iterator<Element> iterator3 = hijos.iterator();
                                
                                while (iterator3.hasNext())
                                {
                                    Element registro3 = (Element) iterator3.next();
                                    if(registro3.getName().equals("Falta_element"))
                                    {
                                        hijos = registro3.getChildren();
                                        Iterator<Element> iterator4 = hijos.iterator();
                                        
                                        while (iterator4.hasNext())
                                        {
                                            Element registro4 = (Element) iterator4.next();
                                            error += ((" ")+registro4.getValue());
                                        }
                                        iterator4 = null;
                                    }
                                }
                                iterator3 = null;
                            }
                            registro2 = null;
                        }
                        iterator2 = null;
                    }
                    registro = null;
                }
                
                throw new ServiceException(error);
            }
        }
        catch (Exception e)
        {
            if(error!=null && !error.equals("")){
                throw new ServiceException(error);
            }
            else{
                throw new ServiceException("No se recibiÛ respuesta XML");
            }
        }
        finally
        {
            error              = null;
            saxBuider          = null;
            document           = null;
            raiz               = null;
            hijos              = null;
            iterator           = null;
        }
        
        
        return respuesta;
    }  
    
    @SuppressWarnings("unchecked")
    public static OperadorVO parseInfoOwner(final String resultadoXML) throws ServiceException{
    	String error = "";		
    	final OperadorVO operadorVO = new OperadorVO();
    	SAXBuilder builder1 = new SAXBuilder(false);
    	Document doc = null;
    	Element raiz = null;		
    	List<Element> ChildElements = null;
    	Iterator<Element> it = null;
    	try {

    		try{
    			doc = builder1.build(new StringReader(resultadoXML));
    			raiz = doc.getRootElement();
    			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
    					.getChild("getInfoOwnerResponse", Namespace.getNamespace("http://consultasnum.iusacell.com/")).getChild("return") != null){

    				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
    						.getChild("getInfoOwnerResponse", Namespace.getNamespace("http://consultasnum.iusacell.com/")).getChild("return")
    						.getChild("infoOwner") != null &&
    						raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
    						.getChild("getInfoOwnerResponse", Namespace.getNamespace("http://consultasnum.iusacell.com/")).getChild("return")
    						.getChild("infoOwner").getChild("operador") != null){

    					ChildElements = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
    					.getChild("getInfoOwnerResponse", Namespace.getNamespace("http://consultasnum.iusacell.com/")).getChild("return")
    					.getChild("infoOwner").getChild("operador").getChildren();
    					it = ChildElements.iterator();

    					while(it.hasNext()){
    						Element registro = (Element) it.next();
    						if(registro.getName().equals("idOperadorComp")){
    							operadorVO.setIdOperadorComp(registro.getValue());
    						}
    						if(registro.getName().equals("ida")){
    							operadorVO.setIdA(registro.getValue());
    						}
    						if(registro.getName().equals("ido")){
    							operadorVO.setIdO(registro.getValue());
    						}
    						if(registro.getName().equals("operaInt")){
    							operadorVO.setOperaInt(registro.getValue());
    						}
    					}
    				}										
    			}
    		}catch(Exception e){
    			if(error!=null && !error.equals("")){
    				Logger.write("   Detail        : (Exception) " + error);
    			}
    			else{
    				Logger.write("   Detail        : (Exception) " + e.getMessage());
    			}
    		}
    	} catch (Exception e) {
    		Logger.write("   Detail        : (Exception) " + e.getMessage());
    	}
    	return operadorVO;
    }
    
    @SuppressWarnings("unchecked")
    public static OperadorVO parseGetNumberByDN(final String resultadoXML) throws ServiceException{
    	String error = "";		
    	final OperadorVO operadorVO = new OperadorVO();
    	SAXBuilder builder1 = new SAXBuilder(false);
    	Document doc = null;
    	Element raiz = null;		
    	List<Element> childElements = null;
    	Iterator<Element> it = null;
    	try {

    		try{
    			doc = builder1.build(new StringReader(resultadoXML));
    			raiz = doc.getRootElement();
    			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
    					.getChild("getNumberByMDNResponse", Namespace.getNamespace("http://consultasnum.iusacell.com/")).getChild("return") != null){

    				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
    						.getChild("getNumberByMDNResponse", Namespace.getNamespace("http://consultasnum.iusacell.com/")).getChild("return")
    						.getChild("numberByMdn") != null && 
    						raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
    						.getChild("getNumberByMDNResponse", Namespace.getNamespace("http://consultasnum.iusacell.com/")).getChild("return")
    						.getChild("numberByMdn").getChild("numero") != null
    						){

    					childElements = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
    					.getChild("getNumberByMDNResponse", Namespace.getNamespace("http://consultasnum.iusacell.com/")).getChild("return")
    					.getChild("numberByMdn").getChild("numero").getChildren();
    					it = childElements.iterator();

    					while(it.hasNext()){
    						Element registro = (Element) it.next();
    						if(registro.getName().equals("idOperador")){
    							operadorVO.setIdOperadorComp(registro.getValue());
    						}
    						else if(registro.getName().equals("portado")){
    							operadorVO.setPortado(registro.getValue());
    						}    
    						else if(registro.getName().equals("idEstatusNum")){
    							operadorVO.setIdEstatusNum(registro.getValue());
    						}   
    					}
    				}										
    			}
    		}catch(Exception e){
    			if(error!=null && !error.equals("")){
    				Logger.write("   Detail        : (Exception) " + error);
    			}
    			else{
    				Logger.write("   Detail        : (Exception) " + e.getMessage());
    			}
    		}
    	} catch (Exception e) {
    		Logger.write("   Detail        : (Exception) " + e.getMessage());
    	}
    	return operadorVO;
    } 

    @SuppressWarnings("unchecked")
    public static double parseWallets_Cantidad_Datos_Nat(final String resultadoXML) throws ServiceException{
    	String error = "";
    	SAXBuilder builder1 = new SAXBuilder(false);
    	Document doc = null;
    	Element raiz = null;
    	List<Element> childrens = null;
    	List<Element> ChildBalance = null;
    	Iterator<Element> it = null;
    	Iterator<Element> itP = null;
    	String tipoUnidad = "";    			    	
    	Double cantidad = 0.0;    	

    	try{
    		doc = builder1.build(new StringReader(resultadoXML));
    		raiz = doc.getRootElement();
    		if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaWalletResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return") != null){
    			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaWalletResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("infoWallets") != null){
    				ChildBalance = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaWalletResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChild("infoWallets").getChildren();
    				itP = ChildBalance.iterator();
    				while(itP.hasNext()){
    					Element registroP = (Element) itP.next();
    					if(registroP.getName().equals("walletList")){
    						it = registroP.getChildren().iterator();
    						while(it.hasNext()){
    							Element registro = (Element) it.next();
    							if(registro.getName().equals("walletList")){

    								if(registro.getChild("wallet") != null && 
    										registro.getChild("wallet").getValue().toUpperCase().equalsIgnoreCase("DATA_NAT") ){

    									tipoUnidad = registro.getChild("escenario").getValue();

    									if(registro.getChild("cantidad") != null){
    										if(Utilerias.isNumber(registro.getChild("cantidad").getValue())){

    											cantidad = Double.valueOf(registro.getChild("cantidad").getValue());

    											//Convierte a MB segun el campo escenario
    											if(VALOR_KB.equalsIgnoreCase(tipoUnidad)){
    												cantidad = cantidad / VALOR_CONVERSION_BYTES;
    											} else if(VALOR_GB.equalsIgnoreCase(tipoUnidad)){
    												cantidad = cantidad * VALOR_CONVERSION_BYTES;
    											} else if(VALOR_TB.equalsIgnoreCase(tipoUnidad)){
    												cantidad = cantidad * VALOR_CONVERSION_BYTES * VALOR_CONVERSION_BYTES;
    											}else{//se convierte de KB a MB x default
    												if(!VALOR_MB.equalsIgnoreCase(tipoUnidad)){
    													cantidad = cantidad / VALOR_CONVERSION_BYTES;
    												}
    											}    											    											    																					
    										}
    									}																								
    									break;
    								}
    							}									
    						}								
    					}													
    				}																																																			
    			}else{
    				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaWalletResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChildren();
    				it = childrens.iterator();
    				while (it.hasNext()) {
    					Element registro = (Element) it.next();
    					error += ((" ")+ "[CTRL] "+registro.getValue());
    				}
    				throw new ServiceException(error);
    			}

    		}
    		else{
    			childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("consultaWalletResponse", Namespace.getNamespace("http://ws.server.iusacell.com/")).getChild("return").getChildren();
    			it = childrens.iterator();
    			while (it.hasNext()) {
    				Element registro = (Element) it.next();
    				error += ((" ")+ "[CTRL] "+ registro.getValue());
    			}
    			throw new ServiceException(error);

    		}
    	}catch(Exception e){
    		if(error!=null && !error.equals("")){
    			Logger.write("   Detail        : (Exception) " +  error);
    		}
    		else{
    			Logger.write("   Detail        : (Exception) " + e.getMessage());
    		}
    		throw new ServiceException(error);
    	}
    	return cantidad;
    }     
    
    public static ConsultaSrScVO parseConsultaSRSC(final String resultadoXML) throws ServiceException{
    	ConsultaSrScVO respuesta = new ConsultaSrScVO();
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	factory.setExpandEntityReferences(false);
    	DocumentBuilder builder;
    	org.w3c.dom.Document doc;
    	org.w3c.dom.NodeList childrens;
    	
    	try {
			builder = factory.newDocumentBuilder();
			InputSource inputs = new InputSource(new StringReader(resultadoXML));
			doc = builder.parse(inputs);
			doc.normalize();

			childrens = doc.getElementsByTagName("code");
			if(childrens != null && childrens.getLength()>0){
				respuesta.setCode(childrens.item(0).getTextContent().toString());
			}
			childrens = doc.getElementsByTagName("descripcion");
			if(childrens != null && childrens.getLength()>0){
				respuesta.setDescripcion(childrens.item(0).getTextContent().toString());
			}
			childrens = doc.getElementsByTagName("fechaFinRecurenciaActual");
			if(childrens != null && childrens.getLength()>0){
				respuesta.setFechaFinRecurrenciaActual(childrens.item(0).getTextContent().toString());
			}
			childrens = doc.getElementsByTagName("fechaFinal");
			if(childrens != null && childrens.getLength()>0){
				respuesta.setFechaFinal(childrens.item(0).getTextContent().toString());
			}
			childrens = doc.getElementsByTagName("fechaInicial");
			if(childrens != null && childrens.getLength()>0){
				respuesta.setFechaInicial(childrens.item(0).getTextContent().toString());
			}
			childrens = doc.getElementsByTagName("messageCode");
			if(childrens != null && childrens.getLength()>0){
				respuesta.setMessageCode(childrens.item(0).getTextContent().toString());
			}
			childrens = doc.getElementsByTagName("recurrenciaActual");
			if(childrens != null && childrens.getLength()>0){
				respuesta.setRecurrenciaActual(childrens.item(0).getTextContent().toString());
			}
			childrens = doc.getElementsByTagName("recurrenciasTotales");
			if(childrens != null && childrens.getLength()>0){
				respuesta.setRecurrenciaTotales(childrens.item(0).getTextContent().toString());
			}
			childrens = doc.getElementsByTagName("faultstring");
			if(childrens != null && childrens.getLength()>0){
				throw new ServiceException("[ctrl] " + childrens.item(0).getTextContent().toString());
			}
			
			
		} catch (ParserConfigurationException e) {
			Logger.write("   Detail        : (Exception) " + e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		} catch (SAXException e) {
			Logger.write("   Detail        : (Exception) " + e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		} catch (IOException e) {
			Logger.write("   Detail        : (Exception) " + e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		}
		
    	return respuesta;
    }
    public static DatosDispatcherClienteCancelacionVO parseCustomerCancelacion(String resultadoXML) throws ServiceException{
    	DatosDispatcherClienteCancelacionVO respuesta = new DatosDispatcherClienteCancelacionVO();
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	factory.setExpandEntityReferences(false);
    	DocumentBuilder builder;
    	org.w3c.dom.Document doc;
    	org.w3c.dom.NodeList childrens;
    	
    	try {
			builder = factory.newDocumentBuilder();
			InputSource inputs = new InputSource(new StringReader(resultadoXML));
			doc = builder.parse(inputs);
			doc.normalize();
			
			childrens = doc.getElementsByTagName("statusContrato");
			if(childrens != null && childrens.getLength()>0){
				if(childrens != null && childrens.getLength()>0){
					respuesta.setStatusContrato(childrens.item(0).getTextContent().toString());
				}
			}
			childrens = doc.getElementsByTagName("statusCuenta");
			if(childrens != null && childrens.getLength()>0){
				if(childrens != null && childrens.getLength()>0){
					respuesta.setStatusCuenta(childrens.item(0).getTextContent().toString());
				}
			}
			if (respuesta.getStatusContrato()!=null && respuesta.getStatusCuenta()!= null ){
			  if (respuesta.getStatusContrato().equalsIgnoreCase("a") && respuesta.getStatusCuenta().equalsIgnoreCase("a")){
				childrens = doc.getElementsByTagName("apellidos");
				if(childrens != null && childrens.getLength()>0){
					if(childrens != null && childrens.getLength()>0){
						respuesta.setApellidos(childrens.item(0).getTextContent().toString());
					}
				}
				childrens = doc.getElementsByTagName("contrato");
				if(childrens != null && childrens.getLength()>0){
					if(childrens != null && childrens.getLength()>0){
						respuesta.setContrato(childrens.item(0).getTextContent().toString());
					}
				}
				childrens = doc.getElementsByTagName("custCode");
				if(childrens != null && childrens.getLength()>0){
					if(childrens != null && childrens.getLength()>0){
						respuesta.setCustCode(childrens.item(0).getTextContent().toString());
					}
				}
				childrens = doc.getElementsByTagName("customerID");
				if(childrens != null && childrens.getLength()>0){
					if(childrens != null && childrens.getLength()>0){
						respuesta.setCustomerID(childrens.item(0).getTextContent().toString());
					}
				}
				childrens = doc.getElementsByTagName("idHandset");
				if(childrens != null && childrens.getLength()>0){
					if(childrens != null && childrens.getLength()>0){
						respuesta.setEsn(childrens.item(0).getTextContent().toString());
					}
				}
				childrens = doc.getElementsByTagName("nombreCliente");
				if(childrens != null && childrens.getLength()>0){
					if(childrens != null && childrens.getLength()>0){
						respuesta.setNombreCliente(childrens.item(0).getTextContent().toString());
					}
				}
			}
		  }
    	} catch (ParserConfigurationException e) {
			Logger.write("   Detail        : (Exception) " + e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		} catch (SAXException e) {
			Logger.write("   Detail        : (Exception) " + e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		} catch (IOException e) {
			Logger.write("   Detail        : (Exception) " + e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		}
					
		return respuesta;
	}
    public static DatosAdicionalesDispatcherClienteCancelacionVO parseCustomerAdicionalesCancelacion(String resultadoXML) throws ServiceException{
    	DatosAdicionalesDispatcherClienteCancelacionVO respuesta = new DatosAdicionalesDispatcherClienteCancelacionVO();
		
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	factory.setExpandEntityReferences(false);
    	DocumentBuilder builder;
    	org.w3c.dom.Document doc;
    	org.w3c.dom.NodeList childrens;
    	
    	try {
			builder = factory.newDocumentBuilder();
			InputSource inputs = new InputSource(new StringReader(resultadoXML));
			doc = builder.parse(inputs);
			doc.normalize();

			childrens = doc.getElementsByTagName("dirFiscal");
			if(childrens != null && childrens.getLength()>0){
				if(childrens != null && childrens.getLength()>0){
					respuesta.setDirFiscal(childrens.item(0).getTextContent().toString());
				}
			}
			childrens = doc.getElementsByTagName("rfc");
			if(childrens != null && childrens.getLength()>0){
				if(childrens != null && childrens.getLength()>0){
					respuesta.setRFC(childrens.item(0).getTextContent().toString());
				}
			}
			childrens = doc.getElementsByTagName("state");
			if(childrens != null && childrens.getLength()>0){
				if(childrens != null && childrens.getLength()>0){
					respuesta.setState(childrens.item(0).getTextContent().toString());
				}
			}
    	} catch (ParserConfigurationException e) {
			Logger.write("   Detail        : (Exception) " + e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		} catch (SAXException e) {
			Logger.write("   Detail        : (Exception) " + e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		} catch (IOException e) {
			Logger.write("   Detail        : (Exception) " + e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		}
		return respuesta;
	}
    public static DatosLineaInsuranceVO parseDatosLineaInsurance(final String resultadoXML) throws ServiceException{
    	DatosLineaInsuranceVO respuesta = new DatosLineaInsuranceVO();
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	factory.setExpandEntityReferences(false);
    	DocumentBuilder builder;
    	org.w3c.dom.Document doc;
    	org.w3c.dom.NodeList childrens;
    	
    	try {
			builder = factory.newDocumentBuilder();
			InputSource inputs = new InputSource(new StringReader(resultadoXML));
			doc = builder.parse(inputs);
			doc.normalize();
			
			Logger.write("   Detail        :  " + resultadoXML);
			childrens = doc.getElementsByTagName("getDatosLineaReturn");
			if(childrens != null && childrens.getLength()>0){
				childrens=childrens.item(0).getChildNodes();
				for(int i=0 ;i<childrens.getLength();i++){
					if(childrens.item(i).getNodeName().equalsIgnoreCase("fechaContratacion")){
						respuesta.setFechaContratacion(childrens.item(i).getTextContent().toString());
					}
				}
				
			}
			
			childrens = doc.getElementsByTagName("faultstring");
			if(childrens != null && childrens.getLength()>0){
				throw new ServiceException("[ctrl] " + childrens.item(0).getTextContent().toString());
			}
			
			
		} catch (ParserConfigurationException e) {
			Logger.write("   Detail        : (Exception) " + e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		} catch (SAXException e) {
			Logger.write("   Detail        : (Exception) " + e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		} catch (IOException e) {
			Logger.write("   Detail        : (Exception) " + e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		}
		
    	return respuesta;
    }
    
    @SuppressWarnings("unchecked")
	public static String parseIdLinea(String resultadoXML) throws ServiceException{

    	SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String response = "";
		try{
			

			if(!resultadoXML.contains("SOAP-ENV:Envelope")){
				resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
				"<soapenv:Header/>"+
				"<soapenv:Body>" + resultadoXML;
				resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			}
			
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDatosLineaXDnResponse", Namespace.getNamespace("http://prepago.iusacell.com.mx")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDatosLineaXDnResponse", Namespace.getNamespace("http://prepago.iusacell.com.mx")).getChildren();
				it = childrens.iterator();
				
				while (it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getName().equals("return")){
						
						if(registro.getChild("id", Namespace.getNamespace("http://comun.iusacell.com.mx/xsd")).getValue() != null)
						    response = registro.getChild("id", Namespace.getNamespace("http://comun.iusacell.com.mx/xsd")).getValue();
						else
							response = "";
					}
				}
			}else{
				response = "";
			}
			
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
	    return 	response;
	}
    
@SuppressWarnings("unchecked")
public static String[] parseEquivalencia(String resultadoXML) throws ServiceException{
		
		String[] salida = new String[2];
		
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		
		try{
			

			if(!resultadoXML.contains("SOAP-ENV:Envelope")){
				resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
				"<soapenv:Header/>"+
				"<soapenv:Body>" + resultadoXML;
				resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			}
			
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("obtieneEquivalenciaServiciosResponse", Namespace.getNamespace("http://prepago.iusacell.com.mx")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("obtieneEquivalenciaServiciosResponse", Namespace.getNamespace("http://prepago.iusacell.com.mx")).getChildren();
				it = childrens.iterator();
				
				while (it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getName().equals("return")){
						
						if(registro.getChild("id", Namespace.getNamespace("http://comun.iusacell.com.mx/xsd")).getValue() != null)
						    salida[0] = registro.getChild("id", Namespace.getNamespace("http://comun.iusacell.com.mx/xsd")).getValue();
						else
							salida[0] = "";
						
						if(registro.getChild("tipo", Namespace.getNamespace("http://comun.iusacell.com.mx/xsd")).getValue() != null)
						    salida[1] = registro.getChild("tipo", Namespace.getNamespace("http://comun.iusacell.com.mx/xsd")).getValue();
						else
							salida[1] = "";
						
					}
				}
			}else{
				salida[0] = "";
				salida[1] = "";
			}
			
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

		return 	salida;
	}

	@SuppressWarnings("unchecked")
	public static DireccionVO parseColonias(final String resultadoXML) throws ServiceException{

	String error="";
	SAXBuilder builder1 = new SAXBuilder();
	Document doc = null;
	Element raiz = null;
	List<Element> childrens = null;
	List<Element> catalogo = null;
	Iterator<Element> it = null;
	Iterator<Element> itCatalogo = null;	
	DireccionVO direccion = new DireccionVO();
	List<ColoniaVO> colonias = new ArrayList<ColoniaVO>();
	ColoniaVO colonia = null;
	try{
		doc = builder1.build(new StringReader(resultadoXML));
		raiz = doc.getRootElement();

		if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("MapaObtieneColXCPSepomexResponse", Namespace.getNamespace("http://services.mapas.iusacell.com.mx")) != null){
			childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("MapaObtieneColXCPSepomexResponse", Namespace.getNamespace("http://services.mapas.iusacell.com.mx")).getChild("out", Namespace.getNamespace("http://services.mapas.iusacell.com.mx")).getChildren();

			it = childrens.iterator();


			while(it.hasNext()){
				Element registroIni = (Element) it.next();
				
				if(registroIni.getName().equals("colonias")){
					catalogo = registroIni.getChildren();
					itCatalogo = catalogo.iterator();					

					while(itCatalogo.hasNext()){
						colonia = new ColoniaVO();
						Element registro = (Element) itCatalogo.next();
						List<Element> catalogo2 = registro.getChildren();
						Iterator<Element> itCatalogo2 = catalogo2.iterator();
						while(itCatalogo2.hasNext()){
							Element registro2 = (Element) itCatalogo2.next();
							if(registro2.getName().equals("colonia")){

								colonia.setColonia(registro2.getValue());
							}
							if(registro2.getName().equals("idColonia")){
								colonia.setIdColonia(registro2.getValue());
							}							
						}
						colonias.add(colonia);	
					}	
					direccion.setColonias(colonias);
				}
				else if(registroIni.getName().equals("estado")){
					direccion.setEstado(registroIni.getValue());
				}
				else if(registroIni.getName().equals("idEstado")){
					direccion.setIdEstado(registroIni.getValue());
				}
				else if(registroIni.getName().equals("idMunicipio")){
					direccion.setIdMunicipio(registroIni.getValue());
				}
				else if(registroIni.getName().equals("idPais")){
					direccion.setIdPais(registroIni.getValue());
				}
				else if(registroIni.getName().equals("municipio")){
					direccion.setMunicipio(registroIni.getValue());
				}
				else if(registroIni.getName().equals("pais")){
					direccion.setPais(registroIni.getValue());
				}											
			}
		}else{
			childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("Fault", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChildren();
			it = childrens.iterator();
			while (it.hasNext()) {
				Element registro = (Element) it.next();
				error += ((" ")+registro.getValue());
			}
			throw new ServiceException(error);
		}	
	}catch (Exception e) {
		if(error!=null && !error.equals("")){
			throw new ServiceException(error);
		}
		else{
			Logger.write("   Detail        : (Exception) " + e.getMessage());
			throw new ServiceException("Ocurrio un error al obtener valores de parseColonias");
		}
	}
	return direccion;		
	}
	
	@SuppressWarnings("unchecked")
	public static String parseBajaSServicio(String resultadoXML) throws ServiceException{

		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String response = "";
		try{
			

			if(!resultadoXML.contains("SOAP-ENV:Envelope")){
				resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
				"<soapenv:Header/>"+
				"<soapenv:Body>" + resultadoXML;
				resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			}
			
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("bajaServiciosResponse", Namespace.getNamespace("http://impl.servicios.iusacell.com.mx")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("bajaServiciosResponse", Namespace.getNamespace("http://impl.servicios.iusacell.com.mx")).getChildren();
				it = childrens.iterator();
				
				while (it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getName().equals("return")){
						
						if(registro.getChild("idTx", Namespace.getNamespace("http://vo.servicios.iusacell.com.mx/xsd")).getValue() != null)
						    response = registro.getChild("idTx", Namespace.getNamespace("http://vo.servicios.iusacell.com.mx/xsd")).getValue();
						else
							response = "";
					}
				}
			}else{
				response = "";
			}
			
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
	    return 	response;
	}
}