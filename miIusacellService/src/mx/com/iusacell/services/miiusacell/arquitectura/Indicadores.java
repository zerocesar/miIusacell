package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.CatalogoIndicadoresVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaIndicadores;

public abstract class Indicadores {

	public static CatalogoIndicadoresVO flujo(final ConsultaIndicadores input) throws ServiceException{
		
		CatalogoIndicadoresVO response = new CatalogoIndicadoresVO();
		OracleProcedures oracle = new OracleProcedures();
		
		if(input.getReporteId() < 0){
			throw new ServiceException("El reporteId debe ser mayor a cero");
		}
		try{
		
		response = oracle.obtieneIndicadores(input.getReporteId(), input.getUnidadNegocio(), input.getPlataforma(),
				input.getFechaIni(), input.getFechaFin(), input.getTipolinea());
		
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
		return response;
	}
}
