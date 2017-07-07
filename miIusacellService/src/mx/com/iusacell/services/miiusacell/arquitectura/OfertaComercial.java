package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.ImagenEquipoVO;
import mx.com.iusacell.services.miiusacell.vo.ObtenerDescripcionPlanesVO1;
import mx.com.iusacell.services.miiusacell.vo.ServiciosAdicionalesVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosBundlesAdicionales;
import mx.com.iusacell.services.miiusacell.vo.ServiciosContratarVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosFocalizacionVO;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceGetServiciosAdicionales;
import mx.com.iusacell.services.miusacell.call.CallServiceObtenerDescripcionPlanes;
import mx.com.iusacell.services.miusacell.util.B64;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public abstract class OfertaComercial {
	public static ObtenerDescripcionPlanesVO1 consultaDescripcionPlanes(
	        final String idPlan, final String descripcionPlan, final String code, final int usuario, final String ip) throws ServiceException
	{		
		ObtenerDescripcionPlanesVO1 descripcionPlanes = new ObtenerDescripcionPlanesVO1();
		try {

			String sResponse = "";
			String planDB = "";
			String planDescBD = "";
			String idElephan = "0";
			String consultaPlanesBD = "0";
			OracleProcedures oracle = new OracleProcedures();
			
			try {
				consultaPlanesBD = oracle.getValorParametro(146);
			} catch (Exception e) {
				consultaPlanesBD = "0";
			}

			if(idPlan != null && !idPlan.equals("")){
				planDB = idPlan;
			}else if(descripcionPlan != null && !descripcionPlan.equals("")){
				planDescBD = descripcionPlan;
			}else if(code != null && !code.equals("") && !code.equals("0")){
				idElephan = code;
			}			

			if(consultaPlanesBD.equals("1"))
			{
				descripcionPlanes = oracle.getObtenerDescripcionPlanes(planDB, planDescBD, idElephan, usuario, ip);
			}
			else
			{
				CallServiceObtenerDescripcionPlanes obtenPlanes = new CallServiceObtenerDescripcionPlanes();
				sResponse = obtenPlanes.serviceObtenerDescripcionPlanes(planDB, planDescBD, idElephan, 212121, "10.10.10.10");
				if(sResponse != null && !sResponse.equals(""))
					descripcionPlanes	= ParseXMLFile.ParseObtenerDescripcionPlanes(sResponse);
			}

		} catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}


		return descripcionPlanes;
	}

	public static List<ServiciosAdicionalesVO> consultaServiciosAdicionales(			
	        final int version,String  idPlan, String idElephant, final int usuario, final String ip)
			throws ServiceException
			{
		List<ServiciosAdicionalesVO> serviciosAdicionales = new ArrayList<ServiciosAdicionalesVO>();
		OracleProcedures oracle = new OracleProcedures();
		try
		{
			String consultaServiciosBD = "0";
			
			try {
				consultaServiciosBD = oracle.getValorParametro(146);
			} catch (Exception e) {
				consultaServiciosBD = "0";
			}

			if(idElephant != null && !idElephant.equals("") && !idElephant.equals("0")){
				idPlan = "";				
			}else{
				idElephant = "0";
			}

			if(consultaServiciosBD.equals("1"))
			{
				serviciosAdicionales = oracle.getServiciosAdicionales(version, idPlan, idElephant, usuario, ip);
			}
			else
			{
				CallServiceGetServiciosAdicionales obtieneServicios = new CallServiceGetServiciosAdicionales();
				String respuestaServicio = obtieneServicios.getServiciosAdicionales(version, idPlan,idElephant);

				if(respuestaServicio != null && !respuestaServicio.equals(""))
					serviciosAdicionales = ParseXMLFile.ParseServiciosOpc(respuestaServicio);
			}
		}
		catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return serviciosAdicionales;
	}
	
	public static ImagenEquipoVO getImagenEquipoSO(final String idEquipo, final String descripcionEquipo) throws ServiceException
	{
		ImagenEquipoVO equipos = new ImagenEquipoVO();
		OracleProcedures oracle = new OracleProcedures();
		try
		{		
			String consultaImgsBD = "0";
			String url = "";
			String img = "";
			
			try {
				consultaImgsBD = oracle.getValorParametro(146);
				url = oracle.getValorParametro(147);
			} catch (Exception e) {
				consultaImgsBD = "0";
				url = "http://10.188.17.240:38101/REPOSITORIO/";
			}

			if(consultaImgsBD.equals("1"))
			{
				equipos = oracle.getImagenEquipoSO(idEquipo, descripcionEquipo, 2121, "10.10.10.10");
				if(equipos != null)
				{	
					String cadena = "";
					if(equipos.getImagenEquipoB64() != null && !equipos.getImagenEquipoB64().equals(""))
					{	
						try
						{
							img = url +equipos.getImagenEquipoB64();
							cadena = B64.remoteImgToBase64(img);						
						}
						catch (Exception e) {
							Logger.write("      Exception -  remoteImgToBase64 : " + e.getLocalizedMessage());
							cadena = "";
						}
						equipos.setImagenEquipoB64(cadena);
					}
					if(equipos.getImagenSOB64() != null && !equipos.getImagenSOB64().equals(""))
					{
						try
						{
							img = url +equipos.getImagenSOB64();
							cadena = B64.remoteImgToBase64(img);
						}
						catch (Exception e) {
							Logger.write("      Exception -  remoteImgToBase64 : " + e.getLocalizedMessage());
							cadena = "";
						}
						equipos.setSistemaOper(cadena);
					}
				}
			}
			else
			{
				CallServiceObtenerDescripcionPlanes equipoImagen = new CallServiceObtenerDescripcionPlanes();
				String sResponse = "";
				sResponse = equipoImagen.getImagenEquipoSO(idEquipo, descripcionEquipo);
				if(sResponse != null && !sResponse.equals("")){
					equipos = ParseXMLFile.parseImagenEquipo(sResponse);
				}
			}

		}
		catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return equipos;
	}
	
	public static List<ServiciosBundlesAdicionales> getServiciosBundles(final int version, final String dn, final int usuarioId, final String ip) throws ServiceException
	{
		List<ServiciosBundlesAdicionales> response = new ArrayList<ServiciosBundlesAdicionales>();		
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		ObtenerDescripcionPlanesVO1 obtenerDescripcionP = new ObtenerDescripcionPlanesVO1();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		List<ServiciosContratarVO> respuesta = new ArrayList<ServiciosContratarVO>();
		String idPlan = "";
		String sResponse = "";
		//Nuevos Richie
		List<ServiciosBundlesAdicionales> ServiciosAdiCorporativos = new ArrayList<ServiciosBundlesAdicionales>();
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
				obtenerDescripcionP =  OfertaComercial.consultaDescripcionPlanes(descripcion.getIdPlan(), descripcion.getDescripcionPlan(), descripcion.getCode(), 212121, "10.10.10.10");
				
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
			
			//response = OfertaComercial.consultaServiciosAdicionales(version, idPlan, descripcion.getCode(), 401043, "10.10.10.10");
			response = oracle.getServiciosBundles(idPlan, usuarioId, ip);
			
			respuesta = ConsultaServicioXUsuExt.flujo(dn);
			
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
			///
//			TODO: German:[15/06/2017] Se agrega para la tag para el Servicio PRIP en Prepago
			try {
				if(descripcion.getServiciosFoca() != null && descripcion.getServiciosFoca().size()>0){
					for(ServiciosFocalizacionVO regServicioFoca : descripcion.getServiciosFoca()){
						//regServicioFoca.setFechaFinServPrip("2017-06-17T00:00:00-00:00");
						double diasServicioFoca = Utilerias.difDeFecServiciosFoca(regServicioFoca.getFechaFinServPrip());
						int vigenciaDias=(int)diasServicioFoca;
						if(diasServicioFoca >0){
							ServiciosBundlesAdicionales newItem = new ServiciosBundlesAdicionales();
							newItem.setIdServicio(regServicioFoca.getIdServicioPrip());
							newItem.setDescripcion(regServicioFoca.getDescServicioPrip());
							newItem.setMensajeServicios(regServicioFoca.getDescServicioPrip());
							newItem.setServicio(regServicioFoca.getNombreServicioPrip());
							newItem.setCosto(regServicioFoca.getCostoServPrip());
							newItem.setVersion(regServicioFoca.getVersion());
							newItem.setVigencia(vigenciaDias);
							newItem.setStatus(1);
							newItem.setMensajes(0);
							newItem.setNavegacion(0);
							newItem.setTipo(1);
							response.add(newItem);
						}
					}
					
				}
			}catch (Exception e) {
				Logger.write("[WARN] getServiciosAdicionalesPorDn [Servicio PRIP] " + e.getLocalizedMessage());
			}
			
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
			
			if(QuitarServiciosAdionales){
				 if(ServiciosAdiCorporativos.size()>0){
					   response = new ArrayList<ServiciosBundlesAdicionales>();
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
							response= OfertaComercial.obtieneMensajeServicios(response);
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
	
	protected static List<ServiciosBundlesAdicionales> obtieneMensajeServicios(final List<ServiciosBundlesAdicionales> obtenerMensajeServicio) throws ServiceException
	{
		List<ServiciosBundlesAdicionales> response = new ArrayList<ServiciosBundlesAdicionales>();
		OracleProcedures oracle= new OracleProcedures();
		MensajeLogBean mensajeLog=new MensajeLogBean();
		String mensajeServicio="";
		try
		{
			for(ServiciosBundlesAdicionales servicios: obtenerMensajeServicio ){
				
				if(servicios.getIdServicio() != null && Utilerias.isNumber(servicios.getIdServicio())){
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
	
	public static String getDescripcionPlanServiceClass(final String companiaId, final String dn, final int serviceClass, final String idPlan) throws ServiceException
	{
		String descripcionPlan = "";
		String paramCompania = "10";
		OracleProcedures oracle = new OracleProcedures();		

		if(companiaId != null && companiaId.trim().equalsIgnoreCase(paramCompania))//Busqueda XidPlan Iusacell
		{	
			CallServiceFocalizacion focalizacionWS = new CallServiceFocalizacion();
			ObtenerDescripcionPlanesVO1 obtenerDescripcionP = new ObtenerDescripcionPlanesVO1();
			String responseFoca = focalizacionWS.focalizacion(dn);
			DetalleFocalizacionVO descripcion = ParseXMLFile.parseFocalizacion(responseFoca);			
			obtenerDescripcionP = consultaDescripcionPlanes(descripcion.getIdPlan(), descripcion.getDescripcionPlan(), descripcion.getCode(), 212121, "10.189.64.53");
			descripcionPlan = obtenerDescripcionP.getNombreCortoPlan();
		}
		else //busqueda x serviceClass Unefon
		{
			descripcionPlan = oracle.getDescPlanXServiceClass(idPlan, serviceClass, 111111,"10.189.64.53");
		}

		return descripcionPlan;
	}
}
