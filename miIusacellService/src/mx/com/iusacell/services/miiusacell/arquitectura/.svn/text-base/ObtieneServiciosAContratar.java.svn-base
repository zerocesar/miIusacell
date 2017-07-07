package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.DatosServiciosAContratarVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceServiciosContratos;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class ObtieneServiciosAContratar {
	public static List<DatosServiciosAContratarVO> flujo(final String dn) throws ServiceException
	{
		String sResponse="";
		CallServiceServiciosContratos serviciosContratados = new CallServiceServiciosContratos();
		List<DatosServiciosAContratarVO> response = new ArrayList<DatosServiciosAContratarVO>();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		
		try
		{
			sResponse = focalizacion.focalizacion(dn);
			if(sResponse != null && !sResponse.equals(""))
				descripcion = ParseXMLFile.parseFocalizacion(sResponse);
			
			if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
				sResponse = serviciosContratados.serviciosXContratar(descripcion.getCoID(), descripcion.getTmCode());

				if(sResponse != null && !sResponse.equals(""))
					response = ParseXMLFile.ParseServiciosXContratar(sResponse);
			}
		}
		catch (Exception e)
		{
			Logger.write("   Excepcion al obtener servicios por contratar" + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
}
