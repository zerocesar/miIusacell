package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;

public abstract class EliminaUsuario {

	public static int flujo(final String dn, final int isuarioId) throws ServiceException
	{
		int respuesta = -1;
		try
		{
			if((dn==null || dn.trim().equals("")))
			{				 
				throw new ServiceException("Parametros de entrada incorrectos.");

			}
			OracleProcedures oracle = new OracleProcedures();
			respuesta = oracle.eliminarUsuario(dn, isuarioId);
		}
		catch (Exception e) 
		{			 
			throw new ServiceException(e.getMessage());
		}

		return respuesta;
	}
}


