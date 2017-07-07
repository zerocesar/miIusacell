package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class AltaNumeroFrecuente {
	
	public static String registraNumeroFrecuente(final String dn, final String nombre, final String telefono, final int usuarioId) throws ServiceException
	{
		String respuesta = "";
		String sResponse = "";
		try
		{
			OracleProcedures oracle = new OracleProcedures();		
			CallServiceFocalizacion datosfocalizacion = new CallServiceFocalizacion();
			DetalleFocalizacionVO respuestaFoca = new DetalleFocalizacionVO();
			int idOperador = 0;
			validaNumeroFrecuente(dn, nombre, telefono);


			sResponse = datosfocalizacion.focalizacion(telefono);
			if(sResponse != null && !sResponse.equals(""))
				respuestaFoca = ParseXMLFile.parseFocalizacion(sResponse);

			if(respuestaFoca != null && respuestaFoca.getIdOperador() != null)
			{
				try {
					idOperador = Integer.valueOf(respuestaFoca.getIdOperador());
				} catch (NumberFormatException e) {
					Logger.write("     NumberFormatException idOperador : " + respuestaFoca.getIdOperador());
				}				
			}


			int codigo = oracle.altaNumeroFrecuente(dn, nombre, telefono, usuarioId, idOperador);
			if(codigo == 0)
				respuesta = "numero frecuente registrado correctamente";
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return respuesta;
	}
	
	private static void validaNumeroFrecuente(final String dn, final String nombre, final String telefono) throws ServiceException
	{
		if(dn==null || dn.trim().equals(""))
		{			
			throw new ServiceException("Parametros de entrada :: El dn no puede ir vacio");			

		}
		if(nombre==null || nombre.trim().equals(""))
		{
			throw new ServiceException("Parametros de entrada :: El nombre no puede ir vacio");			
		}
		
		if(telefono==null || telefono.trim().equals(""))
		{
			throw new ServiceException("Parametros de entrada :: El telefono no puede ir vacio");			
		}
	}

}
