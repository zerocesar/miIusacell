package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.DNSugeridoVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.NumerosVO;
import mx.com.iusacell.services.miiusacell.vo.RetornaDNSugerido;
import mx.com.iusacell.services.miusacell.call.CallServiceActivacion;
import mx.com.iusacell.services.miusacell.call.CallServiceCompNumeros;
import mx.com.iusacell.services.miusacell.call.CallServiceDNSugerido;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceMsisdn;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class DNSugerido {
	
	public static DNSugeridoVO flujo (String dn, final String cantidad, final String localidad, final String cveEdoCen, final String cveMunCen, String cvePobCen, final int compania) throws ServiceException
	{
		String regresa="";
		String dealerName="";
		String sResponse="";
		String estado = "";
		String region = "";
		String idFact = "";
		CallServiceDNSugerido callDnSugerido = new CallServiceDNSugerido();
		CallServiceMsisdn  callServiceMsisdn = new CallServiceMsisdn();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		CallServiceActivacion numeros = new CallServiceActivacion();
		CallServiceCompNumeros compNumeros = new CallServiceCompNumeros();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		NumerosVO numerosResponse = new NumerosVO();
		DNSugeridoVO sugeridoVo = new DNSugeridoVO();
		RetornaDNSugerido retornaDNLegacy = new RetornaDNSugerido();
		try{
			
			if(dn == null || dn.equals("")){
				if(localidad == null || localidad.equals("")){
					throw new ServiceException("DN vacio, se debe informar localidad y compañia");
				}
				if(compania != 1 && compania != 2){
					throw new ServiceException("DN vacio, compañia invalida");
				}
			}else if(localidad == null || localidad.equals("")){
				if(dn == null || dn.equals("")){
					throw new ServiceException("localidad vacia, se debe informar DN");
				}
			}
			
			sResponse = focalizacion.focalizacion(dn);
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
			
			if((dn == null || dn.equals("")) || descripcion.getServicio() != null && descripcion.getServicio().equals("80") && descripcion.getSubservicio() != null && descripcion.getSubservicio().equals("80")){
				if(dn != null && !dn.equals("")){
					regresa = callServiceMsisdn.msisdn(dn);
					if(regresa != null && !regresa.equals(""))
						dealerName = ParseXMLFile.ParseMsisdn(regresa);
				}else{
					dn = "";
					dealerName = (compania == 1 ? "iusacell" : "unefon");
				}

				if(dealerName.toLowerCase().equals("iusacell"))
					regresa = callDnSugerido.DNSugerido(dn, cantidad, localidad,"1110000135","16soiHEa","970813");

				if(dealerName.toLowerCase().equals("unefon"))
					regresa = callDnSugerido.DNSugerido(dn, cantidad, localidad,"1110000136","hY2eRclH","800000");

				if(regresa != null && !regresa.equals(""))
					sugeridoVo = ParseXMLFile.ParseDnSugerido(regresa);
				
			}else if(descripcion.getServicio() != null &&  !descripcion.getServicio().equals("0") && !descripcion.getServicio().equals("80")){
				
				sResponse = numeros.consultaNumerosPrepago(dn);
				if(sResponse != null && !sResponse.equals("")){
					numerosResponse = ParseXMLFile.parseConsultaNumerosReserve(sResponse);
				}
				if(numerosResponse != null && numerosResponse.getStrCveCensal() != null && !numerosResponse.getStrCveCensal().equals("") && numerosResponse.getStrCveCensal().length() >= 5){
					estado = numerosResponse.getStrCveCensal().substring(0,2);
				}
				
				region = ResourceBundle.getBundle("regiones").getString(estado);
				
				if(cvePobCen == null || cvePobCen.equals("")){
					cvePobCen = "0001";
				}
				
				sResponse = compNumeros.doReserveMdn(region, cveEdoCen, cveMunCen, cvePobCen, idFact, descripcion.getIdFormaPago(), descripcion.getModalidad(), descripcion.getIdOperador(), descripcion.getServicio(), descripcion.getSubservicio(), descripcion.getTecnologia(), "PVS");
				if(sResponse != null && !sResponse.equals("")){
					retornaDNLegacy = ParseXMLFile.ParseReserveMdn(sResponse);
				}
				
				sugeridoVo.setDescLocalidad(retornaDNLegacy.getStrDescripcion());
				sugeridoVo.setLocalidad(retornaDNLegacy.getStrCveCENSA());
				List<String> data = new ArrayList<String>();
				data.add(retornaDNLegacy.getStrMDN());
				sugeridoVo.setSugeridoDn(data);
			}
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return sugeridoVo;
	}
}
