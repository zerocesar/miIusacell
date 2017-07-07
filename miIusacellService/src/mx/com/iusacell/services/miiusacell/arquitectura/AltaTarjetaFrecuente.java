package mx.com.iusacell.services.miiusacell.arquitectura;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.TarjetaVO;
import mx.com.iusacell.services.miiusacell.vo.TarjetasFrecuentesVO;
import mx.com.iusacell.services.miusacell.call.CallServiceCardBlackList;
import mx.com.iusacell.services.miusacell.call.CallServiceSemaphore;

public class AltaTarjetaFrecuente {	
	
	public static String regitraTarjeta(final TarjetaVO datosTarjeta, final int usuarioId) throws ServiceException
	{
		String respuesta = "";
		
		CallServiceCardBlackList validaTDC = new CallServiceCardBlackList();
		boolean tdcBlacklList= false;
		String validarCardBlacklist="";
		OracleProcedures oracle = new OracleProcedures();
		
		CallServiceSemaphore semaforoTDC =new CallServiceSemaphore();
		String altaTarjetaSemaphoreService="";
		try
		{	
			try
			{	
				validarCardBlacklist = oracle.getValorParametro(164);
				if (validarCardBlacklist.equals("1")){
					tdcBlacklList=validaTDC.consultaCardBlackList(datosTarjeta.getNumeroTarjeta());
					if (tdcBlacklList){
						try{	
							String user = (datosTarjeta.getDn() != null) ? datosTarjeta.getDn().substring(2) : usuarioId+"";
							oracle.setCardBlackList(datosTarjeta.getNumeroTarjeta(), "Alta tarjeta",String.valueOf(tdcBlacklList),user);							
						}catch(ServiceException e){
							Logger.write("[WARN]   ::  ServiceException [setCardBlackList]" + e.getLocalizedMessage());
						}
						throw new ServiceException("La TDC se encuentra en BlackList");
					}
				}
			}catch(Exception e){
				if (tdcBlacklList){
					throw new ServiceException("ServiceException [cardBlackList] :"+ e.getLocalizedMessage());
				}
			}
			
			validaDatosTarjeta(datosTarjeta);

			try
			{
				altaTarjetaSemaphoreService=oracle.getValorParametro(177);
				if (altaTarjetaSemaphoreService.equals("1"))
				{
					String cardHolder=datosTarjeta.getNombre()+ " "+datosTarjeta.getaPaterno()+" " +datosTarjeta.getaMaterno();
					String expirationDate=datosTarjeta.getMesVencimiento()+"/"+datosTarjeta.getAnioVencimiento();
					semaforoTDC.SemaphoreSaveCustomerInfo(datosTarjeta.getDn(), datosTarjeta.getNombre(), datosTarjeta.getaPaterno(),datosTarjeta.getaMaterno(), datosTarjeta.getNumeroTarjeta(),cardHolder,expirationDate,datosTarjeta.getCp());
				}
			}catch(Exception e){
				throw new ServiceException("ServiceException : "+ e.getLocalizedMessage());
			}
			
			List<TarjetasFrecuentesVO> tarjetasFrecuentes = oracle.obtieneTarjetasFrecuente(datosTarjeta.getDn());
			if(tarjetasFrecuentes != null)
			{
				if(tarjetasFrecuentes.size() >= 3)
					throw new ServiceException("Haz registrado el máximo de tarjetas permitidas");
			}
			
			int continuar = oracle.altaTarjeta( 
					datosTarjeta.getMarcaTarjeta(), 
					datosTarjeta.getNumeroTarjeta(), 
					datosTarjeta.getMesVencimiento(),
					datosTarjeta.getAnioVencimiento(),
					datosTarjeta.getNombre(),
					datosTarjeta.getaPaterno(),
					datosTarjeta.getaMaterno(),
					datosTarjeta.getCp(),
					datosTarjeta.getUltimosDigitos(),
					datosTarjeta.getDn(),
					usuarioId
					);
			if(continuar == 0)
				respuesta = "tarjeta guardada correctamente";
		}
		catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return respuesta;
	}
	
	private static void validaDatosTarjeta(final TarjetaVO datosTarjeta) throws ServiceException
	{		
		try{
			if(datosTarjeta.getDn()==null || datosTarjeta.getDn().trim().equals(""))
			{       	  
				throw new ServiceException("Parametros de entrada incorrectos  ::  El dn no puede ir vacio");

			}
			if(datosTarjeta.getMarcaTarjeta()<1)
			{       
				throw new ServiceException("Parametros de entrada incorrectos  ::  La marca de la tarjeta no puede ir vacio");         	
			}
			if(datosTarjeta.getNumeroTarjeta()==null || datosTarjeta.getNumeroTarjeta().trim().equals(""))
			{       	
				throw new ServiceException("Parametros de entrada incorrectos  ::  El numero de la tarjeta no puede ir vacio");       	  
			}
			if(datosTarjeta.getMesVencimiento()==null || datosTarjeta.getMesVencimiento().trim().equals(""))
			{       	
				throw new ServiceException("Parametros de entrada incorrectos  ::  El mes de vencimiento no puede ir vacio");       	  
			}
			if(datosTarjeta.getAnioVencimiento()==null || datosTarjeta.getAnioVencimiento().trim().equals(""))
			{       	  
				throw new ServiceException("Parametros de entrada incorrectos  ::  El aï¿½o de vencimiento no puede ir vacio");       	  
			}
			if(datosTarjeta.getNombre()==null || datosTarjeta.getNombre().trim().equals(""))
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  El nombre no puede ir vacio");       	 
			}
			if(datosTarjeta.getaPaterno()==null || datosTarjeta.getaPaterno().trim().equals(""))
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  El apellido paterno no puede ir vacio");       	  
			}
			if(datosTarjeta.getaMaterno()==null || datosTarjeta.getaMaterno().trim().equals(""))
			{
				datosTarjeta.setaMaterno("S/AM");  
			}
			if(datosTarjeta.getCp()==null || datosTarjeta.getCp().trim().equals(""))
			{       	 
				throw new ServiceException("Parametros de entrada incorrectos  ::  El codigo postal no puede ir vacio");       	  
			}
			if(datosTarjeta.getUltimosDigitos()==null || datosTarjeta.getUltimosDigitos().trim().equals(""))
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  Los ultimos cuatro digitos no pueden ir vacio");       	  
			}

			if(Integer.parseInt(datosTarjeta.getMesVencimiento())<1 || Integer.parseInt(datosTarjeta.getMesVencimiento())>12)
			{         	
				throw new ServiceException("Parametros de entrada incorrectos  ::  Mes invalido");         	
			}

			Date hoy=new Date();
			Calendar cal=Calendar.getInstance();
			cal.setTime(hoy);
			int mes = cal.get(Calendar.MONTH)+1;
			String anioString = String.valueOf(cal.get(Calendar.YEAR));
			int anio =Integer.parseInt(anioString.substring(anioString.length()-2, anioString.length()));

			if(Integer.parseInt(datosTarjeta.getAnioVencimiento())<anio)
			{ 			
				throw new ServiceException("Parametros de entrada incorrectos  ::  El año de vencimiento no puede ser menor al año en curso.");         	
			}

			if(Integer.parseInt(datosTarjeta.getAnioVencimiento())==anio)
			{
				if(Integer.parseInt(datosTarjeta.getMesVencimiento())<=mes)
				{     			
					throw new ServiceException("Parametros de entrada incorrectos  ::  El mes de vencimiento no puede ser menor o igual al mes en curso.");             	
				}
			}	
		}
		catch(Exception e)
		{
			throw new ServiceException("Parametros de entrada incorrectos :: " + e .getMessage());
		}
	}
}
