package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosContratarVO;
import mx.com.iusacell.services.miusacell.call.CallServiceCompraBundle;
import mx.com.iusacell.services.miusacell.call.CallServiceConsultaPrepago;
import mx.com.iusacell.services.miusacell.call.CallServiceConsultaServiciosLEGACY;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceServiciosContratos;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class ConsultaServicioXUsuExt {
	public static List<ServiciosContratarVO> flujo(final String sNumDN) throws ServiceException
	{
		String sResponse="";
		CallServiceConsultaServiciosLEGACY consultaLegacy = new CallServiceConsultaServiciosLEGACY();
		CallServiceServiciosContratos serviciosContratados = new CallServiceServiciosContratos();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		List<ServiciosContratarVO> response = new ArrayList<ServiciosContratarVO>();
		List<ServiciosContratarVO> responseLegacyPrepago = new ArrayList<ServiciosContratarVO>();
		CallServiceConsultaPrepago consultaPrepago = new CallServiceConsultaPrepago();
		CallServiceCompraBundle consultaBundle = new CallServiceCompraBundle();
		OracleProcedures oracle = new OracleProcedures();
		String idLinea = "";
		String consultaBundlePorServicio = "";
		
		try
		{
			try{
				consultaBundlePorServicio = oracle.getValorParametro(58);
			}catch (ServiceException e) {
				consultaBundlePorServicio = "0";
			}
			
			sResponse = focalizacion.focalizacion(sNumDN);
			
			if(sResponse != null && !sResponse.equals("")){
				descripcion = ParseXMLFile.parseFocalizacion(sResponse);
			}
			
			if(descripcion.getServicio() != null && descripcion.getServicio().equals("80") && descripcion.getSubservicio() != null && descripcion.getSubservicio().equals("80"))
			{
				Logger.write("   Consultando Catalogo de servicios contratados por plataforma ETALK");
				try {
					if(consultaBundlePorServicio.equals("1")){
						sResponse = consultaBundle.consultaServicioLegacyPrepagoBundle(sNumDN);
						if(sResponse != null && !sResponse.equals(""))
							responseLegacyPrepago = ParseXMLFile.parseConsultaPrepagoBundle(sResponse);
					}else{
						responseLegacyPrepago = oracle.servicioLegacy(sNumDN);
					}
					if(responseLegacyPrepago != null && responseLegacyPrepago.size() > 0){
						response.addAll(responseLegacyPrepago);
					}
                }catch (Exception e) {
					Logger.write("Detail al ejecutar el SP listServEtak");
				}
			}	
			else if(descripcion.getServicio() != null &&  !descripcion.getServicio().equals("0") && !descripcion.getServicio().equals("80"))
			{
				if(descripcion.getCoID() == null || descripcion.getCoID().equals("")){
					sResponse = consultaPrepago.consultaPrepago(sNumDN);
					if(sResponse != null && !sResponse.equals(""))
						idLinea = ParseXMLFile.ParseConsultaPrepago(sResponse);
				}else{
					idLinea = descripcion.getCoID();
				}
				
				if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
					Logger.write("   Consultando Catalogo de servicios contratados por plataforma LEGACY Pospago-Hibrido");
					
					sResponse = serviciosContratados.serviciosContratados(idLinea);
					if(sResponse != null && !sResponse.equals(""))
						response = ParseXMLFile.ParseServiciosContratados(sResponse);
					
				}else
				{
					Logger.write("   Consultando Catalogo de servicios contratados por plataforma LEGACY Prepago");
					try{
					sResponse = consultaLegacy.consultaServiciosLEGACY(sNumDN);
					if(sResponse != null && !sResponse.equals(""))
						response = ParseXMLFile.ParseConsultaPrepagoServicios(sResponse);
					}catch (Exception e) {
						Logger.write("Detail al ejecutar el consultaServiciosLEGACY");
					}
					
					try {
						if(consultaBundlePorServicio.equals("1")){
							sResponse = consultaBundle.consultaServicioLegacyPrepagoBundle(sNumDN);
							if(sResponse != null && !sResponse.equals(""))
								responseLegacyPrepago = ParseXMLFile.parseConsultaPrepagoBundle(sResponse);
						}else{
							responseLegacyPrepago = oracle.servicioLegacy(sNumDN);
						}
						if(responseLegacyPrepago != null && responseLegacyPrepago.size() > 0){
							response.addAll(responseLegacyPrepago);
						}
                    }catch (Exception e) {
						Logger.write("Detail al ejecutar el SP listServLegacy");
					}
				}
			}else{
				Logger.write("   IdServicio: " + descripcion.getServicio());
				throw new ServiceException("IdServicio Invalido");
			}
			
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
	
	public static List<ServiciosContratarVO> flujoFromWallet(final String sNumDN, final DetalleFocalizacionVO descripcion) throws ServiceException
	{
		String sResponse="";
		CallServiceConsultaServiciosLEGACY consultaLegacy = new CallServiceConsultaServiciosLEGACY();
		CallServiceServiciosContratos serviciosContratados = new CallServiceServiciosContratos();
//		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
//		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		List<ServiciosContratarVO> response = new ArrayList<ServiciosContratarVO>();
		List<ServiciosContratarVO> responseLegacyPrepago = new ArrayList<ServiciosContratarVO>();
		CallServiceConsultaPrepago consultaPrepago = new CallServiceConsultaPrepago();
		CallServiceCompraBundle consultaBundle = new CallServiceCompraBundle();
		OracleProcedures oracle = new OracleProcedures();
		String idLinea = "";
		String consultaBundlePorServicio = "";
		
		try
		{
			try{
				consultaBundlePorServicio = oracle.getValorParametro(58);
			}catch (ServiceException e) {
				consultaBundlePorServicio = "0";
			}
			
//			sResponse = focalizacion.focalizacion(sNumDN);
//			
//			if(sResponse != null && !sResponse.equals("")){
//				descripcion = ParseXMLFile.parseFocalizacion(sResponse);
//			}
			
			if(descripcion.getServicio() != null && descripcion.getServicio().equals("80") && descripcion.getSubservicio() != null && descripcion.getSubservicio().equals("80"))
			{
				Logger.write("   Consultando Catalogo de servicios contratados por plataforma ETALK");
				try {
					if(consultaBundlePorServicio.equals("1")){
						sResponse = consultaBundle.consultaServicioLegacyPrepagoBundle(sNumDN);
						if(sResponse != null && !sResponse.equals(""))
							responseLegacyPrepago = ParseXMLFile.parseConsultaPrepagoBundle(sResponse);
					}else{
						responseLegacyPrepago = oracle.servicioLegacy(sNumDN);
					}
					if(responseLegacyPrepago != null && responseLegacyPrepago.size() > 0){
						response.addAll(responseLegacyPrepago);
					}
                }catch (Exception e) {
					Logger.write("Detail al ejecutar el SP listServEtak");
				}
			}	
			else if(descripcion.getServicio() != null &&  !descripcion.getServicio().equals("0") && !descripcion.getServicio().equals("80"))
			{
				if(descripcion.getCoID() == null || descripcion.getCoID().equals("")){
					sResponse = consultaPrepago.consultaPrepago(sNumDN);
					if(sResponse != null && !sResponse.equals(""))
						idLinea = ParseXMLFile.ParseConsultaPrepago(sResponse);
				}else{
					idLinea = descripcion.getCoID();
				}
				
				if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
					Logger.write("   Consultando Catalogo de servicios contratados por plataforma LEGACY Pospago-Hibrido");
					
					sResponse = serviciosContratados.serviciosContratados(idLinea);
					if(sResponse != null && !sResponse.equals(""))
						response = ParseXMLFile.ParseServiciosContratados(sResponse);
					
				}else
				{
					Logger.write("   Consultando Catalogo de servicios contratados por plataforma LEGACY Prepago");
					try{
					sResponse = consultaLegacy.consultaServiciosLEGACY(sNumDN);
					if(sResponse != null && !sResponse.equals(""))
						response = ParseXMLFile.ParseConsultaPrepagoServicios(sResponse);
					}catch (Exception e) {
						Logger.write("Detail al ejecutar el consultaServiciosLEGACY");
					}
					
					try {
						if(consultaBundlePorServicio.equals("1")){
							sResponse = consultaBundle.consultaServicioLegacyPrepagoBundle(sNumDN);
							if(sResponse != null && !sResponse.equals(""))
								responseLegacyPrepago = ParseXMLFile.parseConsultaPrepagoBundle(sResponse);
						}else{
							responseLegacyPrepago = oracle.servicioLegacy(sNumDN);
						}
						if(responseLegacyPrepago != null && responseLegacyPrepago.size() > 0){
							response.addAll(responseLegacyPrepago);
						}
                    }catch (Exception e) {
						Logger.write("Detail al ejecutar el SP listServLegacy");
					}
				}
			}else{
				Logger.write("   IdServicio: " + descripcion.getServicio());
				throw new ServiceException("IdServicio Invalido");
			}
			
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
	
	
	public static List<ServiciosContratarVO> serviciosContratar(final String sNumDN, final String servicio, final String subServicio,final String coID, final String IsPostpagoOrHibrido) throws ServiceException
	{
		String sResponse="";
		CallServiceConsultaServiciosLEGACY consultaLegacy = new CallServiceConsultaServiciosLEGACY();
		CallServiceServiciosContratos serviciosContratados = new CallServiceServiciosContratos();
		List<ServiciosContratarVO> response = new ArrayList<ServiciosContratarVO>();
		List<ServiciosContratarVO> responseLegacyPrepago = new ArrayList<ServiciosContratarVO>();
		CallServiceConsultaPrepago consultaPrepago = new CallServiceConsultaPrepago();
		CallServiceCompraBundle consultaBundle = new CallServiceCompraBundle();
		OracleProcedures oracle = new OracleProcedures();
		String idLinea = "";
		String consultaBundlePorServicio = "";
		
		try
		{
			try{
				consultaBundlePorServicio = oracle.getValorParametro(58);
			}catch (ServiceException e) {
				consultaBundlePorServicio = "0";
			}
			
			if(servicio != null && servicio.equals("80") && subServicio != null && subServicio.equals("80"))
			{
				Logger.write("   Consultando Catalogo de servicios contratados por plataforma ETALK");
				try {
					if(consultaBundlePorServicio.equals("1")){
						sResponse = consultaBundle.consultaServicioLegacyPrepagoBundle(sNumDN);
						if(sResponse != null && !sResponse.equals(""))
							responseLegacyPrepago = ParseXMLFile.parseConsultaPrepagoBundle(sResponse);
					}else{
						responseLegacyPrepago = oracle.servicioLegacy(sNumDN);
					}
					if(responseLegacyPrepago != null && responseLegacyPrepago.size() > 0){
						response.addAll(responseLegacyPrepago);
					}
                }catch (Exception e) {
					Logger.write("Detail al ejecutar el SP listServEtak");
				}
			}	
			else if(servicio != null &&  !servicio.equals("0") && !servicio.equals("80"))
			{
				if(coID == null || coID.equals("")){
					sResponse = consultaPrepago.consultaPrepago(sNumDN);
					if(sResponse != null && !sResponse.equals(""))
						idLinea = ParseXMLFile.ParseConsultaPrepago(sResponse);
				}else{
					idLinea = coID;
				}
				
				if(IsPostpagoOrHibrido != null && !IsPostpagoOrHibrido.equals("")){
					Logger.write("   Consultando Catalogo de servicios contratados por plataforma LEGACY Pospago-Hibrido");
					
					sResponse = serviciosContratados.serviciosContratados(idLinea);
					if(sResponse != null && !sResponse.equals(""))
						response = ParseXMLFile.ParseServiciosContratados(sResponse);
					
				}else
				{
					Logger.write("   Consultando Catalogo de servicios contratados por plataforma LEGACY Prepago");
					try{
					sResponse = consultaLegacy.consultaServiciosLEGACY(sNumDN);
					if(sResponse != null && !sResponse.equals(""))
						response = ParseXMLFile.ParseConsultaPrepagoServicios(sResponse);
					}catch (Exception e) {
						Logger.write("Detail al ejecutar el consultaServiciosLEGACY");
					}
					
					try {
						if(consultaBundlePorServicio.equals("1")){
							sResponse = consultaBundle.consultaServicioLegacyPrepagoBundle(sNumDN);
							if(sResponse != null && !sResponse.equals(""))
								responseLegacyPrepago = ParseXMLFile.parseConsultaPrepagoBundle(sResponse);
						}else{
							responseLegacyPrepago = oracle.servicioLegacy(sNumDN);
						}
						if(responseLegacyPrepago != null && responseLegacyPrepago.size() > 0){
							response.addAll(responseLegacyPrepago);
						}
                    }catch (Exception e) {
						Logger.write("Detail al ejecutar el SP listServLegacy");
					}
				}
			}else{
				Logger.write("   IdServicio: " + servicio);
				throw new ServiceException("IdServicio Invalido");
			}
			
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
	
	
}
