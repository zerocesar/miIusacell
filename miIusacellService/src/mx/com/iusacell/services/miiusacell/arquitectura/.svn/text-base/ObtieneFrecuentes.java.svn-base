package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.NumerosFrecuentesVO;
import mx.com.iusacell.services.miiusacell.vo.TarjetasFrecuentesVO;

public abstract class ObtieneFrecuentes {
	
	public static List<NumerosFrecuentesVO> obtieneNumeroFrecuente(final String dn) throws ServiceException
	{
		List<NumerosFrecuentesVO> respuesta = new ArrayList<NumerosFrecuentesVO>();
		OracleProcedures oracle = new OracleProcedures();
		try
		{
			respuesta = oracle.obtieneNumeroFrecuente(dn);
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return respuesta;
	}
	
	public static List<TarjetasFrecuentesVO> obtieneTarjetasFrecuente(final String dn) throws ServiceException
	{
		List<TarjetasFrecuentesVO> respuesta = new ArrayList<TarjetasFrecuentesVO>();
		OracleProcedures oracle = new OracleProcedures();
		try
		{
			respuesta = oracle.obtieneTarjetasFrecuente(dn);
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return respuesta;
	}
}
