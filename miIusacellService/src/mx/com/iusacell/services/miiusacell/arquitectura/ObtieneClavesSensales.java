package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.CatalogoVO;
import mx.com.iusacell.services.miusacell.call.CallServiceActivacion;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public abstract class ObtieneClavesSensales {

	public static List<CatalogoVO> flujo() throws ServiceException
	{
		String sResponse="";
		CallServiceActivacion consulta = new CallServiceActivacion();
		List<CatalogoVO> response = new ArrayList<CatalogoVO>();
		try
		{
			sResponse = consulta.consultaClavesSensales();
			
			if(sResponse != null && !sResponse.equals("")){
				response = ParseXMLFile.parseClaveSensalCiudades(sResponse);
			}
			
			for(int i=0; i<response.size();i++){
				if(response.get(i) != null){
					response.get(i).setDescripcion(Utilerias.formatoCadena(response.get(i).getDescripcion()));
				}
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
}
