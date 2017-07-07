package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;

public abstract class EditarPerfil {

	public static int flujo(final String dn, final String nombre, final String apPaterno, final String apMaterno, final String correo, final String password, final String fechaNacimiento, final String fotografia, final int usuarioId) throws ServiceException
	{ 
		int codigo = -1;
		OracleProcedures oracle = new OracleProcedures();
		try
		{
			if(dn==null || dn.isEmpty())
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  dn no puede ir vacio");
			}
			if(nombre==null || nombre.isEmpty())
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  nombre no puede ir vacio");
			}
			if(apPaterno==null || apPaterno.isEmpty())
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  apPaterno no puede ir vacio");
			}
			if(apMaterno==null)
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  apMaterno no puede ir vacio");
			}
			if(correo==null || correo.isEmpty())
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  correo no puede ir vacio");
			}
			if(fechaNacimiento == null)
			{
				throw new ServiceException("Parametros de entrada incorrectos  ::  dn no puede ir vacio");
			}

			codigo = oracle.editarPerfil(dn, nombre, apPaterno, apMaterno, correo, password, fechaNacimiento, fotografia, usuarioId);
			if(codigo != 0){
				throw new ServiceException("Ocurrio un error al editar el perfil");
			}
		}
		catch(ServiceException e)
		{
			throw new ServiceException(e.getMessage());
		}
		return codigo;
	}
}
