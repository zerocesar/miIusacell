package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.ResponseCitaVO;
import mx.com.iusacell.services.miusacell.call.CallServiceAltaCita;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class CancelaCitaUsuario {

	public static ResponseCitaVO flujo(final String folio) throws ServiceException{
		
		CallServiceAltaCita cancelaCitaUs =  new CallServiceAltaCita();
		ResponseCitaVO respuesta = new ResponseCitaVO();
		String cancela = "";
		Logger.write("CancelaCitaUsuario");
		
		//folio vacio y nulo, llamar call
		if((folio==null || folio.trim().equals("")))
		{				 
			throw new ServiceException("El folio no puede ir vacio");
		}

		cancela = cancelaCitaUs.callCancelaCitaUsuario(folio);
		if(cancela != null || !cancela.equalsIgnoreCase("")){
			respuesta = ParseXMLFile.parseCancelaCitaUsuario(cancela);
		}
		
		return respuesta;
	}
}
