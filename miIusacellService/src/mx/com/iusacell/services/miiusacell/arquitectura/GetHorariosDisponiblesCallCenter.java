package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.GetHorariosDisponiblesCallCenterVO;
import mx.com.iusacell.services.miusacell.call.CallServiceAltaCita;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class GetHorariosDisponiblesCallCenter {
	public static List<GetHorariosDisponiblesCallCenterVO> flujo(final String fecha, final String in3, final String in4) throws ServiceException
	{
       	
		CallServiceAltaCita  horariosDisponiblesCallCenter = new CallServiceAltaCita();
		List<GetHorariosDisponiblesCallCenterVO> listHorariosDisponiblesCallCenter =  new ArrayList<GetHorariosDisponiblesCallCenterVO>();
		
		String sResponse="";
		
		try
		{
			sResponse = horariosDisponiblesCallCenter.getHorariosDisponiblesCallCenter(fecha, in3, in4);
			listHorariosDisponiblesCallCenter =  ParseXMLFile.ParseHorariosDisponibles(sResponse);
			
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
		
	   return listHorariosDisponiblesCallCenter;
	}
}
