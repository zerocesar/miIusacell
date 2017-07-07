package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.security.GeneraToken;
import mx.com.iusacell.services.miiusacell.vo.TiposDeAtencionVO;
import mx.com.iusacell.services.miusacell.call.CallServiceAltaCita;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class GetTiposDeAtencion {
	
	public static List<TiposDeAtencionVO> flujo() throws ServiceException {
		String sResponse = "";
		String tokenSeguridad = "";
		CallServiceAltaCita tiposDeAtencion = new CallServiceAltaCita();
		List<TiposDeAtencionVO> listTipoDeAtencion = new ArrayList<TiposDeAtencionVO>();
		
		try{
			tokenSeguridad = GeneraToken.generaToken("");
			
			sResponse = tiposDeAtencion.tiposDeAtencion(tokenSeguridad);
			
			if(sResponse != null && !sResponse.equals("")){
				listTipoDeAtencion = ParseXMLFile.ParseTiposDeAtencion(sResponse);
			}
			
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
		return listTipoDeAtencion;
	}
}
