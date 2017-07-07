package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.DatosDispatcherClienteCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.DatosLineaInsuranceVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.ObtenerDescripcionPlanesVO1;
import mx.com.iusacell.services.miiusacell.vo.SNCODEInsuranceVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosAdicionalesVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosContratarVO;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceServiciosContratos;
//import mx.com.iusacell.services.miusacell.call.CallServiceGetServiciosAdicionales;
//import mx.com.iusacell.services.miusacell.call.CallServiceObtenerDescripcionPlanes;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public abstract class GetServiciosAdicionales {
	public static List<ServiciosAdicionalesVO> flujo(final int version, final String idPlan) throws ServiceException
	{
		List<ServiciosAdicionalesVO> response = new ArrayList<ServiciosAdicionalesVO>();
//		CallServiceGetServiciosAdicionales obtieneServicios = new CallServiceGetServiciosAdicionales();
//		String respuestaServicio = "";
		
		try
		{
			if(idPlan == null || idPlan.equals("") || version <0 ){
				throw new ServiceException("Parametro de entrada incompletos");
			}
//			respuestaServicio = obtieneServicios.getServiciosAdicionales(version, idPlan,"0");			
//			
//			if(respuestaServicio != null && !respuestaServicio.equals(""))
//				response = ParseXMLFile.ParseServiciosOpc(respuestaServicio);
			
			response = OfertaComercial.consultaServiciosAdicionales(version, idPlan, "0", 401043, "10.10.10.10");
			
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
	
	public static List<ServiciosAdicionalesVO> flujoPorDn(final int version, final String dn) throws ServiceException
	{
		List<ServiciosAdicionalesVO> response = new ArrayList<ServiciosAdicionalesVO>();
//		CallServiceGetServiciosAdicionales obtieneServicios = new CallServiceGetServiciosAdicionales();		
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		ObtenerDescripcionPlanesVO1 obtenerDescripcionP = new ObtenerDescripcionPlanesVO1();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		List<ServiciosContratarVO> respuesta = new ArrayList<ServiciosContratarVO>();
		DatosDispatcherClienteCancelacionVO datosClienteCancelacionDispatcher = null;
		DatosLineaInsuranceVO datosLineaDispatcher = null;
		CallServiceServiciosContratos service = new CallServiceServiciosContratos();
//		String respuestaServicio = "";
		String idPlan = "";
		String sResponse = "";
		//Nuevos Richie
		List<ServiciosAdicionalesVO> ServiciosAdiCorporativos = new ArrayList<ServiciosAdicionalesVO>();
		OracleProcedures oracle = new OracleProcedures();
		String addons = "";
		boolean QuitarServiciosAdionales=false;
		
		String parametroMensajeServicio = "";
		 
		try
		{
			if(dn == null || dn.equals("") || version <0 ){
				Logger.write("   Dn no puede ir vacio");
				throw new ServiceException("Dn no puede ir vacio");
			}
			
			try{
				sResponse = focalizacion.focalizacion(dn);
				if(sResponse != null && !sResponse.equals("")){
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);
				}	
			}catch (ServiceException e) {
				descripcion = new DetalleFocalizacionVO();
			}
			
			try{
//				if(descripcion.getIdPlan() != null && !descripcion.getIdPlan().equals("")){
//					sResponse = obtenPlanes.serviceObtenerDescripcionPlanes(descripcion.getIdPlan(), "", "0", 212121, "10.10.10");
//				}else if(descripcion.getDescripcionPlan() != null && !descripcion.getDescripcionPlan().equals("")){
//					sResponse = obtenPlanes.serviceObtenerDescripcionPlanes("", descripcion.getDescripcionPlan(), "0", 212121, "10.10.10");
//				}else if(descripcion.getCode() != null && !descripcion.getCode().equals("")){
//					sResponse = obtenPlanes.serviceObtenerDescripcionPlanes("", "", descripcion.getCode(), 212121, "10.10.10");
//				}
//				if(sResponse != null && !sResponse.equals(""))
//					obtenerDescripcionP	= ParseXMLFile.ParseObtenerDescripcionPlanes(sResponse);
				
				obtenerDescripcionP = OfertaComercial.consultaDescripcionPlanes(descripcion.getIdPlan(), descripcion.getDescripcionPlan(), descripcion.getCode(), 212121, "10.10.10.10");
				
			}catch (ServiceException e) {
				obtenerDescripcionP = new ObtenerDescripcionPlanesVO1();
			}
			
			if(obtenerDescripcionP.getIdPlan() == null || obtenerDescripcionP.getIdPlan().equals("")){
				if(descripcion.getIsPostpagoOrHibrido() == null || descripcion.getIsPostpagoOrHibrido().equals("")){
					if(descripcion.getIdOperador() != null && descripcion.getIdOperador().equals("7")){
						idPlan = "7";
					}else if(descripcion.getIdOperador() != null && descripcion.getIdOperador().equals("5")){
						idPlan = "79";
					}
				}
			}else{
				idPlan = obtenerDescripcionP.getIdPlan();
			}
			
			if(descripcion != null && descripcion.getServicio() != null && descripcion.getServicio().equals("80") && descripcion.getSubservicio() != null && descripcion.getSubservicio().equals("80")){
				idPlan = "100";
			}
			
//			if(descripcion.getCode() != null && !descripcion.getCode().equals("")){
//				respuestaServicio = obtieneServicios.getServiciosAdicionales(version, "",descripcion.getCode());	
//			}else{
//				respuestaServicio = obtieneServicios.getServiciosAdicionales(version, idPlan,"0");
//			}
//			
//			if(respuestaServicio != null && !respuestaServicio.equals(""))
//				response = ParseXMLFile.ParseServiciosOpc(respuestaServicio);
			
			response = OfertaComercial.consultaServiciosAdicionales(version, idPlan, descripcion.getCode(), 401043, "10.10.10.10");
			
		 	respuesta = ConsultaServicioXUsuExt.serviciosContratar(dn,descripcion.getServicio(),descripcion.getSubservicio(),descripcion.getCoID(),descripcion.getIsPostpagoOrHibrido());
			
		 	 
			
			//validacion cliente
			if(descripcion.getIsPostpagoOrHibrido() == null || descripcion.getIsPostpagoOrHibrido().equals("")){
				QuitarServiciosAdionales = false;
			}else{
				try {
					addons = oracle.getValorParametro(137);
				} catch (Exception e) {
					addons = "especiales,gobierno,corporativo,internos,rentas garantia,corporativos pyme,pago unico,corporativo low,elektra,pret,venta empleado,corporativos";
				}
				
				String[] listAddons = addons.split("\\,");
				
				for(int a=0; a< listAddons.length; a++)
				{
					if(descripcion.getTipoCliente().toLowerCase().equals(listAddons[a])){
						QuitarServiciosAdionales = true;
						break;
					}
				}
			}
			
			String deviceInsuranFlag="0";
			List<SNCODEInsuranceVO> sncodeInsurance = new ArrayList<SNCODEInsuranceVO>();
			String precioMercadoInsurance ="0";
			try {
				deviceInsuranFlag = oracle.getValorParametro(231);
			} catch (Exception e) {
				deviceInsuranFlag="0";
			}
			if(deviceInsuranFlag.equals("1")){
				try {
					String sResponseDispatcher = service.getStatusLineaPospago(dn);
					datosClienteCancelacionDispatcher = ParseXMLFile.parseCustomerCancelacion(sResponseDispatcher);
					
					String sResponseDatosLinea = service.getDatosLinea(datosClienteCancelacionDispatcher.getContrato());
					datosLineaDispatcher = ParseXMLFile.parseDatosLineaInsurance(sResponseDatosLinea);
					
					int dias= Utilerias.diferenciasDeFechas(datosLineaDispatcher.getFechaContratacion());
					if (!(dias>30)){
						String cdgProducto = oracle.getCDG_ProductoInsurance(datosClienteCancelacionDispatcher.getEsn());
						//cdgProducto= oracle.getCDG_ProductoInsurance("118690110562615");
						precioMercadoInsurance = oracle.getPrecioMercadoInsurance(cdgProducto);
					}
				}catch (Exception e)
				{
					Logger.write("[WARN] getServiciosAdicionalesPorDn  " + e.getLocalizedMessage());
				}finally{
					try {
						sncodeInsurance = oracle.getSNCODEInsurance(precioMercadoInsurance);
					}catch (Exception e)
					{
						Logger.write("[WARN] getServiciosAdicionalesPorDn  " + e.getLocalizedMessage());
					}
				}
			}
//			TODO: German:[15/06/2017] Se agrega para la tag para el Servicio PRIP en Prepago			
/*			if(descripcion.getServiciosFoca() != null && descripcion.getServiciosFoca().size()>0){
				for(ServiciosFocalizacionVO regServicioFoca : descripcion.getServiciosFoca()){
					//regServicioFoca.setFechaFinServPrip("2017-06-17T00:00:00-00:00");
					double diasServicioFoca = Utilerias.difDeFecServiciosFoca(regServicioFoca.getFechaFinServPrip());
					if(diasServicioFoca >0){
						ServiciosAdicionalesVO newItem = new ServiciosAdicionalesVO();
						newItem.setIdServicio(regServicioFoca.getIdServicioPrip());
						newItem.setDescripcion(regServicioFoca.getDescServicioPrip());
						newItem.setMensajeServicios(regServicioFoca.getDescServicioPrip());
						newItem.setServicio(regServicioFoca.getNombreServicioPrip());
						newItem.setVersion(regServicioFoca.getVersion());
						newItem.setStatus(1);
						response.add(newItem);
					}
				}
				
			}
*/			
			for(int i=0; i<respuesta.size(); i++){
				for(int y=0; y<response.size(); y++){
					if(respuesta.get(i) != null && response.get(y) != null){
						if(response.get(y).getIdServicio().equals(respuesta.get(i).getIdServicio())){
							response.get(y).setStatus(1);
							
							try {
								if(QuitarServiciosAdionales){
									ServiciosAdiCorporativos.add(response.get(y));
								}
							} catch (Exception e) {
								Logger.write(" validacion de TipoCliente");
							}
							
						}else{
							if(response.get(y).getStatus() != 1)
								response.get(y).setStatus(0);
						}
					}
				}
			}
			if(deviceInsuranFlag.equals("1")){
				for(int y=0; y<response.size(); y++){
					for (SNCODEInsuranceVO tmpSncode : sncodeInsurance)
					{
						if( tmpSncode.getSncode()!= null && tmpSncode.getSncode().equalsIgnoreCase(response.get(y).getIdServicio()) && response.get(y).getStatus()==0){
							if (tmpSncode.isFlagActivo()==0){
								response.remove(y);
								y--;
								break;
							}
						}		
					}
				}
			}
			
			if(QuitarServiciosAdionales){
				 if(ServiciosAdiCorporativos.size()>0){
					   response = new ArrayList<ServiciosAdicionalesVO>();
					   response.addAll(ServiciosAdiCorporativos);
				 }else{
					 if(respuesta.size() < 1)
						 throw new ServiceException("Sin servicios disponibles !!!");
				 }
			}
			
			try{
				parametroMensajeServicio=oracle.getValorParametro(139);
			}catch(ServiceException e){
				Logger.write("[WARN] getServiciosAdicionalesPorDn  " + e.getLocalizedMessage());
				parametroMensajeServicio="0";
			}
			if(parametroMensajeServicio.equals("1")){
				if(descripcion.getIsPostpagoOrHibrido() != null){
					if (!descripcion.getIsPostpagoOrHibrido().equals("")){
						String tiposClienteMasivos="";
						try{
							tiposClienteMasivos=oracle.getValorParametro(140);
						}catch(ServiceException e){
							Logger.write("[WARN] getServiciosAdicionalesPorDn  " + e.getLocalizedMessage());
							tiposClienteMasivos="Empleados,Cliente,EAZT,EIUS,EBAZ,EEKT,EEGS,ECMA,Ventanilla Unica Clie";
						}
						if (tiposClienteMasivos.toLowerCase().contains(descripcion.getTipoCliente().toLowerCase()))
						{
							
							response= obtieneMensajeServicios(response);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
	
	public static List<ServiciosAdicionalesVO> flujoPorDnFromWall(final ObtenerDescripcionPlanesVO1 obtenerDescripcionP, final DetalleFocalizacionVO descripcion, final int version, final String dn) throws ServiceException
	{
		List<ServiciosAdicionalesVO> response = new ArrayList<ServiciosAdicionalesVO>();
//		CallServiceGetServiciosAdicionales obtieneServicios = new CallServiceGetServiciosAdicionales();
		List<ServiciosContratarVO> respuesta = new ArrayList<ServiciosContratarVO>();
//		String respuestaServicio = "";
		String idPlan = "";
		try
		{
			
			if(obtenerDescripcionP.getIdPlan() == null || obtenerDescripcionP.getIdPlan().equals("")){
				if(descripcion.getIsPostpagoOrHibrido() == null || descripcion.getIsPostpagoOrHibrido().equals("")){
					if(descripcion.getIdOperador().equals("7")){
						idPlan = "7";
					}else if(descripcion.getIdOperador().equals("5")){
						idPlan = "79";
					}
				}
			}else{
				idPlan = obtenerDescripcionP.getIdPlan();
			}
			
			if(descripcion != null && descripcion.getServicio() != null && descripcion.getServicio().equals("80") && descripcion.getSubservicio() != null && descripcion.getSubservicio().equals("80")){
				idPlan = "100";
			}
			
//			if(descripcion.getCode() != null && !descripcion.getCode().equals("")){
//				respuestaServicio = obtieneServicios.getServiciosAdicionales(version, "",descripcion.getCode());	
//			}else{
//				respuestaServicio = obtieneServicios.getServiciosAdicionales(version, idPlan,"0");
//			}
//			
//			if(respuestaServicio != null && !respuestaServicio.equals(""))
//				response = ParseXMLFile.ParseServiciosOpc(respuestaServicio);
			
			response =  OfertaComercial.consultaServiciosAdicionales(version, idPlan, descripcion.getCode(), 401043, "10.10.10.10");
			
			respuesta = ConsultaServicioXUsuExt.flujoFromWallet(dn, descripcion);
			
			for(int i=0; i<respuesta.size(); i++){
				for(int y=0; y<response.size(); y++){
					if(respuesta.get(i) != null && response.get(y) != null){
						if(response.get(y).getIdServicio().equals(respuesta.get(i).getIdServicio())){
							response.get(y).setStatus(1);
						}else{
							if(response.get(y).getStatus() != 1)
								response.get(y).setStatus(0);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return response;
	}

	public static List<ServiciosAdicionalesVO> obtieneMensajeServicios(final List<ServiciosAdicionalesVO> obtenerMensajeServicio) throws ServiceException
	{
		List<ServiciosAdicionalesVO> response = new ArrayList<ServiciosAdicionalesVO>();
		OracleProcedures oracle= new OracleProcedures();
		MensajeLogBean mensajeLog=new MensajeLogBean();
		String mensajeServicio="";
		try
		{	
			for(ServiciosAdicionalesVO servicios: obtenerMensajeServicio ){
				
				if(servicios.getIdServicio() != null &&  Utilerias.isNumber(servicios.getIdServicio())){
					  mensajeServicio=oracle.getMensajeServicio(servicios.getIdServicio(), mensajeLog);
					  servicios.setMensajeServicios(mensajeServicio);
					}else
						servicios.setMensajeServicios("");	
				
				response.add(servicios);
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
}
