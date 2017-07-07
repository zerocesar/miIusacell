package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.NumerosVO;
import mx.com.iusacell.services.miiusacell.vo.ObtieneEstadoMunicipioVO;
import mx.com.iusacell.services.miusacell.call.CallServiceActivacion;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;



public abstract class ObtieneEstadosMunicipiosInfo {
	
	public static List<ObtieneEstadoMunicipioVO> flujo (final String dn, final String filtro) throws ServiceException
	{
		String sResponse="";
		String estado = "";
		String region = "";
		String[] estados = null;
		CallServiceActivacion numeros = new CallServiceActivacion();
		NumerosVO numerosResponse = new NumerosVO();
		List<ObtieneEstadoMunicipioVO> responseList = new ArrayList<ObtieneEstadoMunicipioVO>();
		List<ObtieneEstadoMunicipioVO> response = new ArrayList<ObtieneEstadoMunicipioVO>();
		OracleProcedures oracle = new OracleProcedures();
		try{
			
				sResponse = numeros.consultaNumerosPrepago(dn);
				if(sResponse != null && !sResponse.equals("")){
					numerosResponse = ParseXMLFile.parseConsultaNumerosReserve(sResponse);
				}
				if(numerosResponse != null && numerosResponse.getStrCveCensal() != null && !numerosResponse.getStrCveCensal().equals("") && numerosResponse.getStrCveCensal().length() >= 5){
					estado = numerosResponse.getStrCveCensal().substring(0,2);
				}
				
				region = ResourceBundle.getBundle("regiones").getString(estado);
				if(ResourceBundle.getBundle("estados").getString(region) != null)
					estados = ResourceBundle.getBundle("estados").getString(region).split(",");
				
				responseList = oracle.serviciosObtieneEdo();
				
				for(int i=0; i<responseList.size(); i++){
					if(estados != null){
						for(int y=0;y<estados.length;y++){
							if(responseList.get(i).getFcveedocen().equals(estados[y])){
								response.add(responseList.get(i));
							}
						}
					}
				}
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
}
