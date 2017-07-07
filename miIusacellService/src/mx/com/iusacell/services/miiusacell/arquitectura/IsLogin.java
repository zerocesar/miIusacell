package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.*;

public abstract class IsLogin {

	public static DatosLogin getDatosLogin(final String dn) throws ServiceException
	{
		DatosLogin datosLogin = new DatosLogin();
		try
		{
			if((dn==null || dn.trim().equals("")))
			{				 
				throw new ServiceException("Parametros de entrada incorrectos.");

			}
			OracleProcedures oracle = new OracleProcedures();
			datosLogin = oracle.login(dn);
		}
		catch (Exception e) 
		{			 
			throw new ServiceException(e.getMessage());
		}

		return datosLogin;
	}
	
	public static boolean validaLogin(final String dn, final String password) throws ServiceException
	{
		boolean datosLogin = false;
		try
		{
			if((dn==null || dn.trim().equals("")) || (password==null || password.trim().equals("")))
			{				 
				throw new ServiceException("Parametros de entrada incorrectos.");

			}
			OracleProcedures oracle = new OracleProcedures();
			datosLogin = oracle.validaLogin(dn, password);
		}
		catch (Exception e) 
		{			 
			throw new ServiceException(e.getMessage());
		}
		return datosLogin;
	}
}
