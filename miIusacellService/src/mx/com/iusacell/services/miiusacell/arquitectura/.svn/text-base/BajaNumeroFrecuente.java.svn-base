package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;

public abstract class BajaNumeroFrecuente {


	public static int flujo(final String dn, final String telefono, final int usuarioId) throws ServiceException
	{
		if(dn==null || dn.isEmpty())
		{
			throw new ServiceException("El dn no puede ser nulo o vacio");
		}
		if(usuarioId<0)
		{
			throw new ServiceException("El usuarioId no puede ser menor a cero");
		}

		OracleProcedures oracle = new OracleProcedures();
		int codigo = -1;
		try{

			codigo = oracle.bajaNumeroFrecuente(dn, telefono, usuarioId);
			if(codigo != 0){
				throw new ServiceException("Ocurrio un error al eliminar el numero");	
			}

		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return codigo;
	}

}
