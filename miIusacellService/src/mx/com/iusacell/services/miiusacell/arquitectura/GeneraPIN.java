package mx.com.iusacell.services.miiusacell.arquitectura;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.xml.rpc.ServiceException;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.call.CallServiceMandaMensaje_Mail;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class GeneraPIN {

	public static int flujo(final String dn, final String pin, final int idServicio, final String ip) throws ServiceException, IOException, SQLException {

		OracleProcedures oracle = new OracleProcedures();
		CallServiceMandaMensaje_Mail mandaMsj = new CallServiceMandaMensaje_Mail();
		//String compania=(idServicio==1)?"Mi Iusacell":"Mi Unefon";
		String compania="";
		String caracterEspecialSMS="";
		compania = Utilerias.retornacompania(idServicio);
		if (idServicio==4){
			try {
				caracterEspecialSMS=oracle.getValorParametro(180);
				compania=compania.replace("&amp;", caracterEspecialSMS);
			} catch (Exception e) {
				compania="Mi ATT";
			}
		}
		
		int pinCreado = 0;
		String pinDevuelto = "";
		String consumeMail="";
		
		if(pin!= null && !pin.equals("")){
			Logger.write("   PIN lleno");
			pinDevuelto = validarPeriodoVidaPin(dn);
			if(!pin.equals(pinDevuelto)){
				throw new ServiceException("PIN MENSAJE :: Pin Incorrecto.");
			}else{
				pinCreado = Integer.parseInt(pinDevuelto);
			}
		}else{
			
			try {
				consumeMail = oracle.getValorParametro(161);
			} catch (Exception e) {
				consumeMail = "1";
			}
			
			Logger.write("   DN lleno y PIN vacio");
			String existePin = oracle.validarDnEnPin(dn,new MensajeLogBean());
	
			if(existePin != null && !existePin.equals(""))
			{
				pinDevuelto = validarPeriodoVidaPin(dn);
				if(!pinDevuelto.equals(""))
				{
					throw new ServiceException("PIN MENSAJE :: Verifica el codigo enviado por sms."); 
				}
				else
				{
					//Se crea un nuevo Pin por que ya caduco el que tenia
					oracle.cambiarPinEstatusNoUtilizado(dn);
					pinCreado = Utilerias.generaPin();
					
					int estatusBD  = oracle.guardaPin(dn, pinCreado, new MensajeLogBean());
					if(estatusBD != 0)
					{
						throw new ServiceException("Error al generar el codigo de seguridad");
					}
					if (consumeMail.equals("0")){
						mandaMsj.enviaSMS(dn, "Tu codigo para " + compania + " es: "+pinCreado);
					}else{
						mandaMsj.enviaNuevoSMS(ip, dn, "Tu codigo para " + compania + " es: "+pinCreado);	
					}
				}
			}
			else
			{
				//se crea un nuevo pin
				oracle.cambiarPinEstatusNoUtilizado(dn);
				pinCreado = Utilerias.generaPin();
				
				int estatusBD  = oracle.guardaPin(dn, pinCreado, new MensajeLogBean());

				if(estatusBD != 0)
				{
					throw new ServiceException("Error al generar el codigo de seguridad");
				}
				if (consumeMail.equals("0")){
					mandaMsj.enviaSMS(dn, "Tu codigo para " + compania + " es: "+pinCreado);
				}else{
					mandaMsj.enviaNuevoSMS(ip, dn, "Tu codigo para " + compania + " es: "+pinCreado);	
				}
			}
		}
		return pinCreado;
	}
	
	@SuppressWarnings("deprecation")
	public static String validarPeriodoVidaPin(final String dn) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		List<Object> datos = oracle.getPinPorUsuario(dn);
		String pin = "";
		String tiempoVida = "";
		int valor = 0;
		if(!datos.isEmpty())
		{
			pin = (String) datos.get(0);
			Timestamp fechaAlta= (Timestamp) datos.get(1);
			Integer estatus=(Integer) datos.get(2);
			Timestamp fechaAltaMas5= new Timestamp(fechaAlta.getTime());
			tiempoVida= oracle.getValorParametro(131);
			valor = Integer.parseInt(tiempoVida);
			
			//fechaAltaMas5.setMinutes(fechaAltaMas5.getMinutes()+3);  ////parametrizar
			fechaAltaMas5.setMinutes(fechaAltaMas5.getMinutes()+valor);
			
			java.util.Date actual= new java.util.Date();
			Timestamp times=new Timestamp(actual.getTime());
            
			Logger.write(" +   validarPeriodoVidaPin");
			Logger.write("     DN                    : " + dn);
			Logger.write("     Tiempo de vida        : " + valor);
			Logger.write("     Fecha de generacion   : " + fechaAlta);
			Logger.write("     Fecha actual          : " + fechaAltaMas5);
			
			
			if(!times.after(fechaAltaMas5))
			{
				if(estatus==0)
				{
					return pin;	
				}
			}
		}
		return "";
	}

	public static String creaPIN(final String dn, final String compania, final String ip) throws ServiceException, IOException, SQLException {
		OracleProcedures oracle = new OracleProcedures();
		String pinCreado = "0";
		String cia = ""; 
		String consumeMail="";
		String caracterEspecialSMS="";
		
		/*if (compania.equals("1")){
			cia = "Mi Iusacell";
		}else cia = "Mi Unefon";
		*/
		
		if (Utilerias.isNumeric(compania)){
			cia=Utilerias.retornacompania(Integer.parseInt(compania));
			if (compania.equals("4")){
				try {
					caracterEspecialSMS=oracle.getValorParametro(180);
					cia=cia.replace("&amp;", caracterEspecialSMS);
				} catch (Exception e) {
					cia="Mi ATT";
				}
			}
			
		}else{
			cia="";
		}
		
		try {
			consumeMail = oracle.getValorParametro(161);
		} catch (Exception e) {
			consumeMail = "1";
		}
		
		CallServiceMandaMensaje_Mail mandaMsj = new CallServiceMandaMensaje_Mail();
		try{
		  String existePin = oracle.retornaPin(dn,new MensajeLogBean());
		  if(existePin != null && !existePin.equals("")){
			  pinCreado = validarPeriodoVidaPin(dn);
			  if (pinCreado.equals(existePin)){
				  pinCreado = existePin;  
			  }else {
				  oracle.cambiarPinEstatusNoUtilizado (dn);
				  int valorPIN = Utilerias.generaPin();
				  pinCreado = String.valueOf(valorPIN);
				  int estatusBD  = oracle.insertaPIN(dn, pinCreado, new MensajeLogBean());
				  if(estatusBD != 0)
				  {
					throw new ServiceException("Error al generar el codigo de seguridad");
				  }	
			  }
		  }else { 
		    int valorPIN = Utilerias.generaPin();
		    pinCreado = String.valueOf(valorPIN);
		    int estatusBD  = oracle.insertaPIN(dn, pinCreado, new MensajeLogBean());
			if(estatusBD != 0)
			{
			  throw new ServiceException("Error al generar el codigo de seguridad");
			}	
		  }
		  if (consumeMail.equals("0")){
				mandaMsj.enviaSMS(dn, "Tu codigo para " + cia + " es: "+pinCreado);
			}else{
				mandaMsj.enviaNuevoSMS(ip, dn, "Tu codigo para " + cia + " es: "+pinCreado);	
			}
		}catch(Exception e){
		  throw new ServiceException(e.getLocalizedMessage());
		}
		
		return pinCreado;
	}
	
	public static boolean validaPIN(final String dn, final String pin) throws ServiceException, IOException, SQLException {
	  boolean valida = false;
	  String pinDevuelto = validarPeriodoVidaPin(dn);
	  if (pinDevuelto.equals(pin)){
		valida = true;
		return valida;
	  }else{
		valida = false;
		return valida;
      }
	}
	@SuppressWarnings("deprecation")
	public static boolean caducaPIN(final String dn) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		List<Object> datos = oracle.getPinPorUsuario(dn);
		boolean resultado = false;
		if(!datos.isEmpty())
		{
			String parametro = oracle.getValorParametro(125);
			int tiempoCaducidad = Integer.parseInt(parametro);
			Timestamp fechaAlta= (Timestamp) datos.get(1);
			Integer estatus=(Integer) datos.get(2);
			Timestamp fechaAltaMas5= new Timestamp(fechaAlta.getTime());
			fechaAltaMas5.setMinutes(fechaAltaMas5.getMinutes()+ tiempoCaducidad);

			java.util.Date actual= new java.util.Date();
			Timestamp times=new Timestamp(actual.getTime());

			if(!times.after(fechaAltaMas5))
			{
				if(estatus==0)
				{
					resultado = true;
					return resultado;	
				}
			}
		}
		return resultado;
	}
	
	public String generaEnviaPin(final String dn,final String  ip) throws ServiceException
	{
		String retorno = "0";
		CallServiceMandaMensaje_Mail serviceNotificacion = new CallServiceMandaMensaje_Mail();
		OracleProcedures oracle = new OracleProcedures();
		String pinGenerado = "";
		String idComp = "";
		String compania = "1";
		String companiaDesc = "";
		String consumeMail="";
		String caracterEspecialSMS="";

		try
		{
			int usuario = 122;
			
			try{
				usuario = Integer.parseInt(dn.substring(2));
			}catch (NumberFormatException e) {Logger.write("[WARN] NumberFormatException          : " + e.getLocalizedMessage());}
			catch (IndexOutOfBoundsException e) {Logger.write("[WARN] IndexOutOfBoundsException          : " + e.getLocalizedMessage());}
			
			pinGenerado = oracle.generaPinBD(dn, usuario);
			
			try{
				idComp = oracle.retornaCia(dn,new MensajeLogBean());
			}catch (ServiceException e) {Logger.write("[WARN] ServiceException          : " + e.getLocalizedMessage());}
		
			if(idComp != null && !idComp.equals("")){
				compania = idComp;
			}
			
			//companiaDesc = compania.equals("1") ? "Mi Iusacell" : "Mi Unefon";
			if (Utilerias.isNumeric(compania)){
				
				companiaDesc = Utilerias.retornacompania(Integer.parseInt(compania));
				
				if (compania.equals("4")){
					try {
						caracterEspecialSMS=oracle.getValorParametro(180);
						companiaDesc=companiaDesc.replace("&amp;", caracterEspecialSMS);
					} catch (Exception e) {
						companiaDesc="Mi ATT";
					}
				}
			}else{
				companiaDesc ="";
			}
			try {
				consumeMail = oracle.getValorParametro(161);
			} catch (Exception e) {
				consumeMail = "1";
			}
			
			if (consumeMail.equals("0")){
				serviceNotificacion.enviaSMS(dn, "Tu codigo para " + companiaDesc + " es: "+pinGenerado);
			}else{
				serviceNotificacion.enviaNuevoSMS(ip, dn, "Tu codigo para " + companiaDesc + " es: "+pinGenerado);	
			}
				
		}
		catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}							
		return retorno;
	}
	
	public boolean validacionPin(final String dn, String pin) throws ServiceException
	{
		boolean pinValido = false;
		OracleProcedures oracle = new OracleProcedures();
		String pinBD = "";
		try
		{
			pinBD = oracle.validaPinBD(dn);
			if(pinBD != null)
			{
				pin = pin.trim();
				pinBD = pinBD.trim();
				if(pin.equalsIgnoreCase(pinBD))
					pinValido = true;
			}
		}
		catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		
		return pinValido;
	}
	
}
