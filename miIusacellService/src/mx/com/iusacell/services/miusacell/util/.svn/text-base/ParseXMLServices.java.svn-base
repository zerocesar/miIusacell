package mx.com.iusacell.services.miusacell.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.vo.AbonoTiempoAireVO;
import mx.com.iusacell.services.miiusacell.vo.PagoFacturaResponseVO;
import mx.com.iusacell.services.miiusacell.vo.ServicioVO;
import mx.com.iusacell.services.miiusacell.vo.UfmiVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.BankResponseVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.CardsVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.ClienteTarjetaVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.KeyedTransactionResponseVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.TarjetasItemVO;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

public abstract class ParseXMLServices {

	@SuppressWarnings("unchecked")
	public static KeyedTransactionResponseVO parseApplyKeyedCharge(String resultadoXML) throws ServiceException{

		String error = "";
		final KeyedTransactionResponseVO respuesta = new KeyedTransactionResponseVO();
		try {
			if(!resultadoXML.contains("SOAP-ENV:Envelope")){
				resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
				"<soapenv:Header/>"+
				"<soapenv:Body>" + resultadoXML;
				resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			}

			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;
			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("applyKeyedChargeResponse", Namespace.getNamespace("http://bean.at.iusacell.com")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("applyKeyedChargeResponse", Namespace.getNamespace("http://bean.at.iusacell.com")).getChild("applyKeyedChargeReturn", Namespace.getNamespace("http://bean.at.iusacell.com")).getChildren(); 
				it = childrens.iterator();
				while (it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getName().equals("affiliation")){
						respuesta.setAffiliation(Long.valueOf(registro.getValue()));
					}

					if(registro.getName().equals("affiliationName")){
						respuesta.setAffiliationName(registro.getValue());
					}

					if(registro.getName().equals("authorizationCode")){
						respuesta.setAuthorizationCode(registro.getValue());
					}

					if(registro.getName().equals("bankResponseCode")){
						respuesta.setBankResponseCode(registro.getValue());
					}

					if(registro.getName().equals("descriptionResponse")){
						respuesta.setDescriptionResponse(registro.getValue());
					}

					if(registro.getName().equals("transactionControlNumber")){
						respuesta.setTransactionControlNumber(Long.valueOf(registro.getValue()));
					}

					if(registro.getName().equals("transactionDate")){
						respuesta.setTransactionDate(registro.getValue());
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
			throw new ServiceException(error);
		}
		return respuesta;
	}		

	@SuppressWarnings("unchecked")
	public static KeyedTransactionResponseVO parseApplyKeyedReverse(String resultadoXML) throws ServiceException{

		String error = "";
		final KeyedTransactionResponseVO respuesta = new KeyedTransactionResponseVO();
		try {
			if(!resultadoXML.contains("SOAP-ENV:Envelope")){
				resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
				"<soapenv:Header/>"+
				"<soapenv:Body>" + resultadoXML;
				resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			}

			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;
			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("applyKeyedReverseResponse", Namespace.getNamespace("http://bean.at.iusacell.com")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("applyKeyedReverseResponse", Namespace.getNamespace("http://bean.at.iusacell.com")).getChild("applyKeyedReverseReturn", Namespace.getNamespace("http://bean.at.iusacell.com")).getChildren(); 
				it = childrens.iterator();
				while (it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getName().equals("affiliation")){
						respuesta.setAffiliation(Long.valueOf(registro.getValue()));
					}

					if(registro.getName().equals("affiliationName")){
						respuesta.setAffiliationName(registro.getValue());
					}

					if(registro.getName().equals("authorizationCode")){
						respuesta.setAuthorizationCode(registro.getValue());
					}

					if(registro.getName().equals("bankResponseCode")){
						respuesta.setBankResponseCode(registro.getValue());
					}

					if(registro.getName().equals("descriptionResponse")){
						respuesta.setDescriptionResponse(registro.getValue());
					}

					if(registro.getName().equals("transactionControlNumber")){
						if(registro.getValue() != null && !registro.getValue().equals("")){
							respuesta.setTransactionControlNumber(Long.valueOf(registro.getValue()));
						}
					}

					if(registro.getName().equals("transactionDate")){
						respuesta.setTransactionDate(registro.getValue());
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
			throw new ServiceException(error);
		}
		return respuesta;
	}

	@SuppressWarnings("unchecked")
	public static BankResponseVO parseExecuteGeneralCharge(String resultadoXML) throws ServiceException{

		String error = "";
		final BankResponseVO respuesta = new BankResponseVO();
		try {
			if(!resultadoXML.contains("SOAP-ENV:Envelope")){
				resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
				"<soapenv:Header/>"+
				"<soapenv:Body>" + resultadoXML;
				resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			}

			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;
			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("executeGeneralChargeResponse", Namespace.getNamespace("http://bean.at.iusacell.com")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("executeGeneralChargeResponse", Namespace.getNamespace("http://bean.at.iusacell.com")).getChild("executeGeneralChargeReturn", Namespace.getNamespace("http://bean.at.iusacell.com")).getChildren(); 
				it = childrens.iterator();
				while (it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getName().equals("affiliation")){
						respuesta.setAffiliation(registro.getValue());
					}

					if(registro.getName().equals("affiliationName")){
						respuesta.setAffiliationName(registro.getValue());
					}

					if(registro.getName().equals("authorizationCode")){
						respuesta.setAuthorizationCode(registro.getValue());
					}

					if(registro.getName().equals("bankResponseCode")){
						respuesta.setBankResponseCode(registro.getValue());
					}

					if(registro.getName().equals("descriptionResponse")){
						respuesta.setDescriptionResponse(registro.getValue());
					}

					if(registro.getName().equals("transactionControlNumber")){
						if(registro.getValue() != null && !registro.getValue().equals("")){
							respuesta.setTransactionControlNumber(Long.valueOf(registro.getValue()));
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
			throw new ServiceException(error);
		}
		return respuesta;
	}

	@SuppressWarnings("unchecked")
	public static BankResponseVO parseExecuteGeneralReverse(String resultadoXML) throws ServiceException{

		String error = "";
		final BankResponseVO respuesta = new BankResponseVO();
		try {
			if(!resultadoXML.contains("SOAP-ENV:Envelope")){
				resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
				"<soapenv:Header/>"+
				"<soapenv:Body>" + resultadoXML;
				resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			}

			SAXBuilder builder1 = new SAXBuilder(false);
			Document doc = null;
			Element raiz = null;
			List<Element> childrens = null;
			Iterator<Element> it = null;
			/** recorre resultadoXML **/
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("executeGeneralReverseResponse", Namespace.getNamespace("http://bean.at.iusacell.com")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("executeGeneralReverseResponse", Namespace.getNamespace("http://bean.at.iusacell.com")).getChild("executeGeneralReverseReturn", Namespace.getNamespace("http://bean.at.iusacell.com")).getChildren(); 
				it = childrens.iterator();
				while (it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getName().equals("affiliation")){
						respuesta.setAffiliation(registro.getValue());
					}

					if(registro.getName().equals("affiliationName")){
						respuesta.setAffiliationName(registro.getValue());
					}

					if(registro.getName().equals("authorizationCode")){
						respuesta.setAuthorizationCode(registro.getValue());
					}

					if(registro.getName().equals("bankResponseCode")){
						respuesta.setBankResponseCode(registro.getValue());
					}

					if(registro.getName().equals("descriptionResponse")){
						respuesta.setDescriptionResponse(registro.getValue());
					}

					if(registro.getName().equals("transactionControlNumber")){
						respuesta.setTransactionControlNumber(Long.valueOf(registro.getValue()));
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
			throw new ServiceException(error);
		}
		return respuesta;
	}

	@SuppressWarnings("unchecked")
	public static PagoFacturaResponseVO parsePagoReferenciado_bis(final String resultadoXML) throws ServiceException{
		PagoFacturaResponseVO response = new PagoFacturaResponseVO();
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Element children = null;
		Iterator<Element> it = null;
		String error = "";

		try{

			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("pagoReferenciado_bisResponse", Namespace.getNamespace("http://cliente.spring.iusacell.com/")) != null &&
					raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("pagoReferenciado_bisResponse", Namespace.getNamespace("http://cliente.spring.iusacell.com/")).getChild("pagoReferenciado_bisReturn") != null	){
				children = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("pagoReferenciado_bisResponse", Namespace.getNamespace("http://cliente.spring.iusacell.com/")).getChild("pagoReferenciado_bisReturn");
				response.setFolioPago(children.getValue());
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

	@SuppressWarnings("unchecked")
	public static ClienteTarjetaVO parseGetClienteTarjeta(final String resultadoXML) throws ServiceException{

		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> children = null;
		List<Element>     hijos     = null;
		Iterator<Element> it = null;
		final ClienteTarjetaVO  tarjeta = new ClienteTarjetaVO();
		final TarjetasItemVO tarjetasItem= new TarjetasItemVO();
		final List<CardsVO> listTarjetas= new ArrayList<CardsVO>();

		try{
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();				

			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getClientResponse", Namespace.getNamespace("http://bean.repositorio.iusacell.com/")) != null){
				children = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getClientResponse", Namespace.getNamespace("http://bean.repositorio.iusacell.com/")).getChild("return").getChildren();
				it = children.iterator();
				while(it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getName().equals("id")){
						tarjeta.setId(registro.getValue());
					}
					if(registro.getName().equals("mdn")){
						tarjeta.setMdn(registro.getValue());
					}
					if(registro.getName().equals("account")){
						tarjeta.setAccount(registro.getValue());
					}
					if(registro.getName().equals("idMarca")){
						tarjeta.setIdmarca(registro.getValue());
					}
					if(registro.getName().equals("tipoLinea")){
						tarjeta.setTipoLinea(registro.getValue());
					}
					if(registro.getName().equals("nombre")){
						tarjeta.setNombre(Utilerias.getStringUTF8(registro.getValue()));
					}
					if(registro.getName().equals("aPaterno")){
						tarjeta.setaPaterno(Utilerias.getStringUTF8(registro.getValue()));
					}
					if(registro.getName().equals("aMaterno")){
						tarjeta.setaMaterno(Utilerias.getStringUTF8(registro.getValue()));
					}
					if(registro.getName().equals("rfc")){
						tarjeta.setRfc(registro.getValue());
					}
					if(registro.getName().equals("sexo")){
						tarjeta.setSexo(registro.getValue());
					}
					if(registro.getName().equals("fechaActivacion")){
						tarjeta.setFechaActivacion(registro.getValue());
					}
					if(registro.getName().equals("email")){
						tarjeta.setEmail(registro.getValue());
					}
					if(registro.getName().equals("telefonoCasa")){
						tarjeta.setTelefonoCasa(registro.getValue());
					}
					if(registro.getName().equals("tarjetas")){

						hijos = registro.getChildren();
						Iterator<Element> iterator2 = hijos.iterator();
						Element hijosTarjeta = (Element) iterator2.next();

						if(hijosTarjeta.getName().equals("item")){

							hijos = hijosTarjeta.getChildren();
							Iterator<Element> iterator3 = hijos.iterator();
							CardsVO card= new CardsVO();

							while (iterator3.hasNext())
							{
								Element hijosItem = (Element) iterator3.next();

								if(hijosItem.getName().equals("idUser")){
									card.setIdUser(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("hasCreditCard")){
									card.setHasCreditCard(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("hasHipCredit")){
									card.setHasHipCredit(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("hasAutCredit")){
									card.setHasAutCredit(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("type")){
									card.setType(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("status")){
									card.setStatus(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("bank")){
									card.setBank(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("bankIdProvider")){
									card.setBankIdProvider(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("colony")){
									card.setColony(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("municipality")){
									card.setMunicipality(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("country")){
									card.setCountry(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("lastName")){
									card.setLastName(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("firstName")){
									card.setFirstName(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("telephone")){
									card.setTelephone(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("cardNumber")){
									card.setCardNumber(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("expirationYear")){
									card.setExpirationYear(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("expirationMonth")){
									card.setExpirationMonth(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("address")){
									card.setAddress(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("city")){
									card.setCity(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("state")){
									card.setState(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("zip")){
									card.setZip(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("provider")){
									card.setProvider(hijosItem.getValue());
								}
								if(hijosItem.getName().equals("creationDate")){
									card.setCreationDate(hijosItem.getValue());
									listTarjetas.add(card);
								}
							}
							tarjetasItem.setItemM(listTarjetas);    
							tarjeta.setTarjetas(tarjetasItem);                                
						}
					}			        	
				}									
			}
		}catch(Exception e){
			e.getLocalizedMessage();
		}
		return tarjeta;
	}
	
	@SuppressWarnings("unchecked")
	public static CardsVO parseGetDatosCliente(String resultadoXML) throws ServiceException{
		final CardsVO datosCliente = new CardsVO();
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String error = "";		
		
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
					if(registro.getChild("calle") != null){
						datosCliente.setAddress(registro.getChild("calle").getValue());
					}		
					if(registro.getChild("ciudad") != null){
						datosCliente.setCountry(registro.getChild("ciudad").getValue());
						datosCliente.setCity(registro.getChild("ciudad").getValue());
					}
					if(registro.getChild("municipioColonia") != null){
						datosCliente.setColony(registro.getChild("municipioColonia").getValue());
					}
					if(registro.getChild("state") != null){
						datosCliente.setState(registro.getChild("state").getValue());
					}
					if(registro.getChild("nombres") != null){
						datosCliente.setFirstName(Utilerias.getStringUTF8(registro.getChild("nombres").getValue()));
					}
					if(registro.getChild("apellidos") != null){
						datosCliente.setLastName(Utilerias.getStringUTF8(registro.getChild("apellidos").getValue()));
					}
					if(registro.getChild("ccaddr3") != null){
						datosCliente.setMunicipality(registro.getChild("ccaddr3").getValue());
					}
					if(registro.getChild("cp") != null){
						datosCliente.setZip(registro.getChild("cp").getValue());
					}
					break;
				}
			}
			else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("getDatosClienteResponse", Namespace.getNamespace("http://ws.sqldispatcher.middleware.iusacell.com")).getChild("getDatosClienteReturn").getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				Logger.write("   Detail        : (Exception) " + error);
				throw new ServiceException(error);
			}
		}catch(Exception e){
			if(error!=null && !error.equals("")){
				Logger.write("   Detail        : (Exception) " + error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
			}
		}
		return datosCliente;
	}
	
	@SuppressWarnings("unchecked")
	public static AbonoTiempoAireVO parseAbonoTA(String resultadoXML) throws ServiceException{
		final AbonoTiempoAireVO responseTA = new AbonoTiempoAireVO();
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Iterator<Element> it = null;
		String error = "";		
		
		try{
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";
			
			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("abonoDNResponse", Namespace.getNamespace("http://bean.recargase.iusacell.com")) != null && raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("abonoDNResponse", Namespace.getNamespace("http://bean.recargase.iusacell.com")).getChild("return", Namespace.getNamespace("http://bean.recargase.iusacell.com")) != null){
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("abonoDNResponse", Namespace.getNamespace("http://bean.recargase.iusacell.com")).getChild("return", Namespace.getNamespace("http://bean.recargase.iusacell.com")).getChildren();
				it = childrens.iterator();

				while(it.hasNext()){
					Element registro = (Element) it.next();
					if(registro.getName().equals("codigoRespuesta")){
						responseTA.setCodigoRespuestaAbonoTA(registro.getValue());
					}		
					if(registro.getName().equals("numeroAutorizacion")){
						responseTA.setNumeroAutorizacionAbonoTA(registro.getValue());
					}					
				}
			}
			else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/")).getChild("abonoDNResponse", Namespace.getNamespace("http://bean.recargase.iusacell.com")).getChild("return", Namespace.getNamespace("http://bean.recargase.iusacell.com")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				Logger.write("   Detail        : (Exception) " + error);
				throw new ServiceException(error);
			}
		}catch(Exception e){
			if(error!=null && !error.equals("")){
				Logger.write("   Detail        : (Exception) " + error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
			}
		}
		return responseTA;
	}
	
	@SuppressWarnings("unchecked")
	public static String parseReversoTA(String resultadoXML) throws ServiceException{
		String responseTA = "";
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;
		List<Element> childrens = null;
		Element children = null;
		Iterator<Element> it = null;
		String error = "";		

		try{
			resultadoXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"<soapenv:Header/>"+
			"<soapenv:Body>" + resultadoXML;

			resultadoXML = resultadoXML + "</soapenv:Body></soapenv:Envelope>";

			doc = builder1.build(new StringReader(resultadoXML));
			raiz = doc.getRootElement();
			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
					.getChild("reversoResponse", Namespace.getNamespace("http://bean.recargase.iusacell.com")) != null && 
					raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
					.getChild("reversoResponse", Namespace.getNamespace("http://bean.recargase.iusacell.com"))
					.getChild("return", Namespace.getNamespace("http://bean.recargase.iusacell.com")) != null){
				children = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
				.getChild("reversoResponse", Namespace.getNamespace("http://bean.recargase.iusacell.com"))
				.getChild("return", Namespace.getNamespace("http://bean.recargase.iusacell.com"));
								
				responseTA = children.getValue();							
				
			}
			else{
				childrens = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
				.getChild("reversoResponse", Namespace.getNamespace("http://bean.recargase.iusacell.com"))
				.getChild("return", Namespace.getNamespace("http://bean.recargase.iusacell.com")).getChildren();
				it = childrens.iterator();
				while (it.hasNext()) {
					Element registro = (Element) it.next();
					error += ((" ")+registro.getValue());
				}
				Logger.write("   Detail        : (Exception) " + error);
				throw new ServiceException(error);
			}
		}catch(Exception e){
			if(error!=null && !error.equals("")){
				Logger.write("   Detail        : (Exception) " + error);
			}
			else{
				Logger.write("   Detail        : (Exception) " + e.getMessage());
			}
		}
		return responseTA;
	}
	
	@SuppressWarnings("unchecked")
    public static UfmiVO parseUfmiByMdn(final String resultadoXML) throws ServiceException{    	
    	final UfmiVO ufmi = new UfmiVO();
    	SAXBuilder builder1 = new SAXBuilder(false);
    	Document doc = null;
    	Element raiz = null;		
    	List<Element> childElements = null;
    	Iterator<Element> itr = null;
    	try {

    		try{
    			doc = builder1.build(new StringReader(resultadoXML));
    			raiz = doc.getRootElement();
    			if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
    					.getChild("getUfmiByMdnResponse", Namespace.getNamespace("http://consultasnum.iusacell.com/")).getChild("return") != null){

    				if(raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
    						.getChild("getUfmiByMdnResponse", Namespace.getNamespace("http://consultasnum.iusacell.com/")).getChild("return")
    						.getChild("ufmiByMdn") != null){

    					childElements = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
    					.getChild("getUfmiByMdnResponse", Namespace.getNamespace("http://consultasnum.iusacell.com/")).getChild("return")
    					.getChild("ufmiByMdn").getChildren();
    					itr = childElements.iterator();

    					while(itr.hasNext()){
    						Element registro = (Element) itr.next();
    						if(registro.getName().equals("estatusNum")){
    							ufmi.setEstatusNum(registro.getValue());
    						}else if(registro.getName().equals("estatusUfmi")){
    							ufmi.setEstatusUfmi(registro.getValue());
    						}else if(registro.getName().equals("iccid")){
    							ufmi.setIccid(registro.getValue());
    						}else if(registro.getName().equals("imsi1")){
    							ufmi.setImsi1(registro.getValue());
    						}else if(registro.getName().equals("imsi2")){
    							ufmi.setImsi2(registro.getValue());
    						}else if(registro.getName().equals("mdn")){
    							ufmi.setMdn(registro.getValue());
    						}else if(registro.getName().equals("ufmi")){
    							ufmi.setUfmi(registro.getValue());
    						}
    					}
    				}										
    			}
    		}catch(Exception e){    			
    			Logger.write("   Detail        : (Exception) " + e.getMessage());    			
    		}
    	} catch (Exception e) {
    		Logger.write("   Detail        : (Exception) " + e.getMessage());
    	}
    	return ufmi;
    }
	
	@SuppressWarnings("unchecked")
	public static List<ServicioVO> parseFocaConsultaCompraServicios(final String resultadoXML) throws ServiceException{    			
		SAXBuilder builder1 = new SAXBuilder(false);
		Document doc = null;
		Element raiz = null;		
		List<Element> childElements = null;
		Iterator<Element> itr = null;
		Element body = null;
		final List<ServicioVO> servicios = new ArrayList<ServicioVO>();
		ServicioVO servicio = null;
		try {

			try{
				doc = builder1.build(new StringReader(resultadoXML));
				raiz = doc.getRootElement();
				body = raiz.getChild("Body", Namespace.getNamespace("http://schemas.xmlsoap.org/soap/envelope/"))
				.getChild("consultaCompraServiciosResponse", Namespace.getNamespace("http://consultaservicios.att.com/")).getChild("consultaServicioRequestVO");
				if(body != null){


					childElements = body.getChildren();
					itr = childElements.iterator();

					while(itr.hasNext()){
						Element registro = (Element) itr.next();
						if(registro.getName().equals("servicioVO")){
							servicio = new ServicioVO();

							if(registro.getChild("idServicio") != null){ 
								servicio.setIdServicio(registro.getChild("idServicio").getValue());
							}
							if(registro.getChild("descripcion") != null){ 
								servicio.setDescripcion(registro.getChild("descripcion").getValue());
							}
							if(registro.getChild("operador") != null){ 
								servicio.setOperador(registro.getChild("operador").getValue());
							}
							if(registro.getChild("tipoOferta") != null){ 
								servicio.setTipoOferta(registro.getChild("tipoOferta").getValue());
							}
							if(registro.getChild("vigencia") != null){ 
								servicio.setVigencia(Integer.valueOf(registro.getChild("vigencia").getValue()));
							}
							if(registro.getChild("precio") != null){ 
								servicio.setPrecio(Double.parseDouble(registro.getChild("precio").getValue()));
							}
							if(registro.getChild("tipoOferta") != null){ 
								servicio.setTipoOferta(registro.getChild("tipoOferta").getValue());
							}
							if(registro.getChild("unidad") != null){ 
								servicio.setUnidad(Long.valueOf(registro.getChild("unidad").getValue()));
							}
							if(registro.getChild("code") != null){ 
								servicio.setCode(Integer.valueOf(registro.getChild("code").getValue()));
							}

							servicios.add(servicio);
						}
					}

				}
			}catch(Exception e){    			
				Logger.write("   Detail        : (Exception) " + e.getMessage());    			
			}
		} catch (Exception e) {
			Logger.write("   Detail        : (Exception) " + e.getMessage());
		}
		return servicios;
	}
}