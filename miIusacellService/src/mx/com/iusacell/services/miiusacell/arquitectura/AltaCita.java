package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.masivo.security.GeneraToken;
import mx.com.iusacell.services.miiusacell.vo.AltaCitaVO;
import mx.com.iusacell.services.miusacell.call.CallServiceAltaCita;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class AltaCita {
	
	public static AltaCitaVO flujo(final String user, final String pass, final int cveHora, final String fecha, final String tienda, final String suscriptor, final String nombre, final String apPaterno, final String apMaterno, final String correo, final String comentario, final int tipoAtencion, final int cveGenerdor, final int empresa, final String logId) throws ServiceException
	{
		Logger.write("AltaCita");
		CallServiceAltaCita callserviceAlta = new CallServiceAltaCita();
		OracleProcedures oracle = new OracleProcedures();
		String sResponse = "";
		String tokenSeguridad = "";
		
		AltaCitaVO altacitaVo = new AltaCitaVO();	          
		try
		{
			tokenSeguridad = GeneraToken.generaToken(Integer.toString(cveHora));
			
			try{
				oracle.callServiceAltaCita(logId,user, pass, cveHora, fecha, tienda, suscriptor, nombre, apPaterno, apMaterno,correo, comentario, 1, 1, tokenSeguridad, 2);
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
			
			sResponse = callserviceAlta.altaCita(user, pass, cveHora, fecha, tienda, suscriptor, nombre, apPaterno, apMaterno, correo, comentario, tipoAtencion, cveGenerdor, empresa, tokenSeguridad);
			if(sResponse != null && !sResponse.equals(""))
				altacitaVo = ParseXMLFile.parseAltaCita(sResponse);
			
			try{
				oracle.altaCitaResponse(logId, altacitaVo.getExito(),altacitaVo.getMensaje(), "", "", 1);
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		Logger.write(" Termina AltaCita");
		return altacitaVo;
	}
}
