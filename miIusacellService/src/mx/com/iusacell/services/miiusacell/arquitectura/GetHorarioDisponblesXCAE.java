package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.security.GeneraToken;
import mx.com.iusacell.services.miiusacell.vo.CitasDisponiblesXHorario;
import mx.com.iusacell.services.miusacell.call.CallServiceAltaCita;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class GetHorarioDisponblesXCAE {
	
	public static List<CitasDisponiblesXHorario> flujo(final String user, final String pass, final String cae, final int motivo, String tokenSeguridad) throws ServiceException
	{
		Logger.write("GetHorarioDisponiblesXCAE");
		List<CitasDisponiblesXHorario> respuesta = new ArrayList<CitasDisponiblesXHorario>();
		CallServiceAltaCita horarioDisponblesXCAE = new CallServiceAltaCita();
	         
	          try
	  		{
	        	tokenSeguridad = GeneraToken.generaToken("");
	        	String sReponse = horarioDisponblesXCAE.getHorarioDisponblesXCAE(user, pass, cae, motivo, tokenSeguridad);
	        	
	        	if(sReponse != null && !sReponse.equals(""))
	        		respuesta = ParseXMLFile.parseHorarioDisponibleXcae(sReponse);
	  		}
	  		catch (ServiceException e)
	  		{
				throw new ServiceException(e.getMessage());
	  		}

	      return respuesta;
	}
}
