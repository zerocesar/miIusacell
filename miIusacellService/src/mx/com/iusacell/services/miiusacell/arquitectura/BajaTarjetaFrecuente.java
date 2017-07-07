package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.call.CallServiceSemaphore;

public abstract class BajaTarjetaFrecuente {

	public static int flujo(final int marcaTarjetaId, final String dn, final String numtarjeta, final int usuarioID) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		int codigo = -1;
		
		CallServiceSemaphore semaforoTDC =new CallServiceSemaphore();
		String bajaTarjetaSemaforosBD="";
		try
		{
			if(marcaTarjetaId<1)
			{
				throw new ServiceException("El marcaTarjetaId no puede ser menor a cero");
			}
			if(dn==null || dn.isEmpty())
			{
				throw new ServiceException("El dn no puede ser nulo o vacio");
			}
			if(numtarjeta==null || numtarjeta.isEmpty())
			{
				throw new ServiceException("El numtarjeta no puede ser nulo o vacio");
			}
			if(usuarioID<0)
			{
				throw new ServiceException("El usuarioID no puede ser menor a cero");
			}

			try
			{
				bajaTarjetaSemaforosBD=oracle.getValorParametro(176);
				if (bajaTarjetaSemaforosBD.equals("1"))
				{
					semaforoTDC.SemaphoreDeleteBankCardInfo(dn, numtarjeta);
				}
			}catch (Exception e) {
				throw new ServiceException("ServiceException [SemaphoreDeleteBankCardInfo] :"+e.getLocalizedMessage());
			}
	
			codigo = oracle.bajaTarjeta(marcaTarjetaId, dn, numtarjeta, usuarioID);
			if(codigo != 0){
				throw new ServiceException("Ocurrio un error al eliminar la tarjeta");	
			}

		}catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}

		return codigo;
	}


}
