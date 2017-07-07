package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.CuentaCreditoVO;

public class UnidadNegocioBusiness {

	private static final String UNIDAD_NEGOCIO_IUSA = "1";
	private static final String UNIDAD_NEGOCIO_UNEFON = "2";	
	private static final String OPERADOR_IUSA = "7";
	private static final String OPERADOR_UNEFON = "5";	

	public void validaUnidadNegocioCliente(final String dnCliente){
		Logger.write("     Inicia validacion de unidad de negocio para el dn: " + dnCliente);
		final ConsultaOperador operador = new ConsultaOperador();
		final OracleProcedures oracle = new OracleProcedures();
		CuentaCreditoVO datosUnidadNeg = null;
		String idOperador = "";		
		boolean actualizaUnidadN = false;

		try{
			try {
				datosUnidadNeg = oracle.getUnidadNegocioCliente(dnCliente);			
			} catch (ServiceException e) {
				Logger.write("     (ServiceException) Error al consultar unidad de negocio: " + e.getMessage());
			}

			if(datosUnidadNeg != null){
				Logger.write("     UnidadNegocio en BD: " + datosUnidadNeg.getUnidadNegocioId());
				try {
					idOperador = operador.getIdOperador(dnCliente);
				} catch (ServiceException e) {
					Logger.write("     (ServiceException) Error al consultar operador: " + e.getMessage());
				}
				Logger.write("     idOperador actual: " + idOperador);
				if(datosUnidadNeg.getUnidadNegocioId().equals(UNIDAD_NEGOCIO_UNEFON) && idOperador.equals(OPERADOR_IUSA)){
					Logger.write("     Se actualiza UnidadNegocio a 1 (Iusacell) en BD ");
					datosUnidadNeg.setUnidadNegocioId(UNIDAD_NEGOCIO_IUSA);	
					actualizaUnidadN = true;

				}else if(datosUnidadNeg.getUnidadNegocioId().equals(UNIDAD_NEGOCIO_IUSA) && idOperador.equals(OPERADOR_UNEFON)){	
					Logger.write("     Se actualiza UnidadNegocio a 2 (Unefon) en BD ");
					datosUnidadNeg.setUnidadNegocioId(UNIDAD_NEGOCIO_UNEFON);	
					actualizaUnidadN = true;
				}

				if(actualizaUnidadN){
					try {
						final int respUpdate = oracle.setUnidadNegocioCliente(datosUnidadNeg);	
						Logger.write("     Respuesta: " + respUpdate);				
					} catch (Exception e) {
						Logger.write("     (Exception) Error al actualizar unidad de negocio: " + e.getMessage());
					}
				}
			}
		}catch (Exception e) {
			Logger.write("     (Exception) Ocurrio un error: " + e.getMessage());
		}
	}	

}
