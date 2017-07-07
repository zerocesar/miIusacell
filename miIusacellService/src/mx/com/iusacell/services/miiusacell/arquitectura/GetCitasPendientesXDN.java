package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;
import javax.xml.rpc.ServiceException;
import mx.com.iusacell.services.miiusacell.vo.GetCitasPendientesXDNVO;
import mx.com.iusacell.services.miusacell.call.CallServiceAltaCita;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class GetCitasPendientesXDN {
	public static List<GetCitasPendientesXDNVO> flujo(final String dn) throws ServiceException {
		
		String sResponse=""; 
		Logger.write("       GetCitasPendientesXDN");
		CallServiceAltaCita getCitasPendientes = new CallServiceAltaCita();
        List<GetCitasPendientesXDNVO> lisgetCitasPendientesXDNVO = new ArrayList<GetCitasPendientesXDNVO>();
		 
		 try
		 {
			 sResponse = getCitasPendientes.getCitasPendientesXDN(dn);
			 if(sResponse!= null && !sResponse.equals("")){
				 lisgetCitasPendientesXDNVO = ParseXMLFile.ParseCitasPendientes(sResponse);
			 }
		 }catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return lisgetCitasPendientesXDNVO;
	}
}
