package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.AltaServicioLegacyVO;
import mx.com.iusacell.services.miiusacell.vo.ContratarServiciosVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosContratarVO;
import mx.com.iusacell.services.miusacell.call.CallServiceAltaBajaServicios;
import mx.com.iusacell.services.miusacell.call.CallServiceBajaServicios;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceServiciosContratos;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class BajaServicio {
	
	public static ContratarServiciosVO flujo(final String mdn, final String idServicio, final AltaServicioLegacyVO datosAlta) throws ServiceException
    {
		
		String sResponse = "";
		int idOperador = 0;
		CallServiceBajaServicios servicesBajaServicios = new CallServiceBajaServicios();
		CallServiceAltaBajaServicios servicioLEGACY = new CallServiceAltaBajaServicios();
		ContratarServiciosVO returnContratoServ = new ContratarServiciosVO();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
				
		try{
			sResponse = focalizacion.focalizacion(mdn);
			if(sResponse != null && !sResponse.equals(""))
				descripcion = ParseXMLFile.parseFocalizacion(sResponse);
			
			try{
				if(descripcion != null && descripcion.getIdOperador() != null && !descripcion.getIdOperador().trim().equals("")){
					idOperador = Integer.parseInt(descripcion.getIdOperador());
				}
			}catch (Exception e) {
				idOperador = 0;
			}
			
			if(descripcion != null && descripcion.getServicio() != null && descripcion.getSubservicio() != null && !descripcion.getServicio().equals("0") && !descripcion.getSubservicio().equals("0")){
				if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
					sResponse = servicesBajaServicios.bajaServiciosETAK(mdn, idOperador, idServicio);
					if(sResponse != null && !sResponse.equals("")){
						returnContratoServ = ParseXMLFile.parseBajarServ(sResponse);
					}
				}
				else{
					if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
						OracleProcedures oracle = new OracleProcedures();
						String userMod = "APP";
						try
						{
							userMod = oracle.getValorParametro(138);
						}
						catch (Exception e) {
							Logger.write(e.getLocalizedMessage());
						}
						sResponse = servicioLEGACY.altaBajaServicios(descripcion.getCoID(), datosAlta.getDnUsa(), datosAlta.getContinenteFavortio(), datosAlta.getServicios(), descripcion.getTmCode(), "2", userMod);
						if(sResponse != null && !sResponse.equals("")){
							returnContratoServ = ParseXMLFile.parseContratarServLegacy(sResponse);
						}
					}
				}
			}
			else{
				if(descripcion.getServicio() != null){
					Logger.write("   IdServicio Invalido: " + descripcion.getServicio());
				}else{
					Logger.write("   IdServicio Invalido: ");
				}
				throw new ServiceException("IdServicio Invalido");
			}
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		
		return returnContratoServ;
    }
	
	
	public static ContratarServiciosVO bajaServicio(final String mdn, final AltaServicioLegacyVO datosAlta) throws ServiceException
    {
		
		String sResponse = "";
		CallServiceAltaBajaServicios servicioLEGACY = new CallServiceAltaBajaServicios();
		ContratarServiciosVO returnContratoServ = new ContratarServiciosVO();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		CallServiceServiciosContratos serviciosContratados =  new CallServiceServiciosContratos();
		String validaFecha="";
		String estatusContrato = "";
		String estatusCuenta = "";
		boolean activoRegistro = true;
		String idServicios = "";
		
		try{
			sResponse = focalizacion.focalizacion(mdn);
			if(sResponse != null && !sResponse.equals(""))
				descripcion = ParseXMLFile.parseFocalizacion(sResponse);
			

			
		//***************	
			OracleProcedures oracle = new OracleProcedures();
			String meses = "6";
			
		    try {
		    	meses = oracle.getValorParametro(132);
			} catch (Exception e) {
				meses = "6";
			}
			
			if (descripcion.getStatusContrato() != null && !descripcion.getStatusContrato().equals(""))
				estatusContrato = descripcion.getStatusContrato();
			
			if (descripcion.getStatusCuenta() != null && !descripcion.getStatusContrato().equals(""))
				estatusCuenta = descripcion.getStatusCuenta();
			
			if(datosAlta.getServicios() != null && !datosAlta.getServicios().equals(""))
				idServicios = datosAlta.getServicios(); 
			
			
			if(descripcion != null && descripcion.getCoID() != null && estatusContrato.toLowerCase().equals("a") && estatusCuenta.toLowerCase().equals("a") && !idServicios.equals("")){
				List<ServiciosContratarVO> serviciosContratadosLst = new ArrayList<ServiciosContratarVO>(); 

				sResponse = serviciosContratados.serviciosContratados(descripcion.getCoID());
				 
				if(sResponse != null && !sResponse.equals(""))
				{
					serviciosContratadosLst =  ParseXMLFile.ParseServiciosContratados(sResponse);
					Date hoy=new Date();
					Calendar cal = Calendar.getInstance();
					Calendar cal2 = Calendar.getInstance();
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
					cal.setTime(hoy);

					cal.add(Calendar.MONTH,-Integer.parseInt(meses));

					if(serviciosContratadosLst != null)
					{  

						for (ServiciosContratarVO item : serviciosContratadosLst) {
							if(idServicios.equals(item.getIdServicio())){
								activoRegistro =true;	
								if(item.getLastDateServices() != null){

									String dia =  item.getLastDateServices().substring(4, 6);
									String mes =  item.getLastDateServices().substring(2, 4);
									String anio = item.getLastDateServices().substring(0, 2);

									String fecha = dia+ "-" + mes + "-"+ anio;

									Date date = formatter.parse(fecha);
									cal2.setTime(date);

									Logger.write("Fecha cumplimiento  :: " + cal.getTime());
									Logger.write("Fecha alta servicio :: " + cal2.getTime());

									if(cal.compareTo(cal2) > 0){
										validaFecha = "mayor";
									}else{
										validaFecha = "menor";
									}
								}
								break;
							}else{
								activoRegistro =false;
							}
						}
					}
				}
			}
			
	  //**********************************************
			
			//**********************************************
//    		TODO: German Se Valida si es SNCODE de Device Insurance
			String cadenaSplit="";
            String[] psncodesSplit = {""} ;
            
            try {
            	cadenaSplit = oracle.getValorParametro(226);
            } catch (Exception e) {
            	cadenaSplit = "991,992,993";
			}
            if (!cadenaSplit.equals("")){
                psncodesSplit = cadenaSplit.split(",");
            }
            for (String tmpSncodeBD : psncodesSplit){
               if (datosAlta.getServicios().equalsIgnoreCase(tmpSncodeBD)){
            	   validaFecha = "mayor";
               }
            }
			  
			if(descripcion != null && descripcion.getServicio() != null && descripcion.getSubservicio() != null && !descripcion.getServicio().equals("0") && !descripcion.getSubservicio().equals("0") && activoRegistro){
					if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
					   if(validaFecha.equals("mayor")){	
					        String userMod = "APP";
							try
							  {
								userMod = oracle.getValorParametro(138);
							   }
							catch (Exception e) {
								Logger.write(e.getLocalizedMessage());
							}
							sResponse = servicioLEGACY.altaBajaServicios(descripcion.getCoID(), datosAlta.getDnUsa(), datosAlta.getContinenteFavortio(), datosAlta.getServicios(), descripcion.getTmCode(), "2", userMod);
								
							if(sResponse != null && !sResponse.equals("")){
								returnContratoServ = ParseXMLFile.parseContratarServLegacy(sResponse);
							}
					   }else{
						  throw new ServiceException(" [CTRL] El servicio :: " + datosAlta.getServicios() + " debe de cumplir una vigencia mayor a "+ meses +" meses para su cancelacion ");
					   }
					}
			}
			else{
				
				if(!activoRegistro){
				   throw new ServiceException(" [CTRL] El servicio no se encuentra activado :: " + idServicios);	
				}
				
				if(descripcion.getServicio() != null){
					Logger.write("   IdServicio Invalido: " + idServicios);
				}else{
					Logger.write("   IdServicio Invalido: ");
				}
				throw new ServiceException(" [CTRL] IdServicio Invalido");
			}
			
			
			
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		
		return returnContratoServ;
    }

}
