package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;

public abstract class EditarCuenta {

	public static int flujo(final String dn, final String password, final String passwordAnt, final String preguntaSec, final String respuestaSec, final int usuarioId) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		int codigo = -1;
		try
		{
			if(dn==null || dn.isEmpty())
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  dn no puede ir vacio");
			}
			if(password==null|| password.isEmpty())
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  password no puede ir vacio");
			}
			if(preguntaSec==null || preguntaSec.isEmpty())
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  preguntaSec no puede ir vacio");
			}
			if(respuestaSec==null || respuestaSec.isEmpty())
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  respuestaSec no puede ir vacio");
			}

			codigo = oracle.editarCuenta(dn, password, passwordAnt, preguntaSec, respuestaSec, usuarioId);

			if(codigo != 0){
				throw new ServiceException("Ocurrio un error al editar la cuenta");
			}

		}
		catch(ServiceException e)
		{
			throw new ServiceException(e.getMessage());
		}
		return codigo;
	}
}
