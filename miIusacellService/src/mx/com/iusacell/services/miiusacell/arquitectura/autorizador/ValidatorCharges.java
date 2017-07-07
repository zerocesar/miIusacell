package mx.com.iusacell.services.miiusacell.arquitectura.autorizador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.CatalogoAbonos;
import mx.com.iusacell.services.miiusacell.vo.MovimientosTA;
import mx.com.iusacell.services.miusacell.call.CallServiceAbonoTA;
import mx.com.iusacell.services.miusacell.util.ParseXMLServices;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class ValidatorCharges {
	
	private static final String ABONOTOREVERSEVAL92 = "92";
	private static final String ABONOTOREVERSEVAL91 = "91";
	public void validaOperacionesRealizadasTA(final String dn,final String dnParaAbono) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		List<MovimientosTA> movimientosTA = new ArrayList<MovimientosTA>();
		String logId = null;
		String fechaInicio = "";
		String fechaFin = "";
		try{
			fechaInicio = Utilerias.fechaActual();
			fechaFin = Utilerias.fechaActual();
			// Valida Movimientos por DN logueado
			movimientosTA = oracle.getOperacionesTAporDN(logId, fechaInicio, fechaFin, dn);
			if(movimientosTA != null)
			{
				for(MovimientosTA mov : movimientosTA)
				{
					if(mov.getCodigoRespuestaAbono() != null && mov.getCodigoRespuestaAbono().trim().equals("0"))
					{
						throw new ServiceException("Err [Caja] : Estimado usuario, sólo se permite realizar una operación de recarga por día");
					}
				}
			}

			//Valida Movimientos por DN a abonar TA			
			movimientosTA = oracle.getOperacionesTAporDN(logId, fechaInicio, fechaFin, null);
			if(movimientosTA != null)
			{
				for(MovimientosTA mov : movimientosTA)
				{
					if((mov.getDnAbono() != null && mov.getDnAbono().trim().equals(dnParaAbono))
							&& (mov.getCodigoRespuestaAbono() != null && mov.getCodigoRespuestaAbono().trim().equals("0")))
					{
						throw new ServiceException("Err [Caja] : Estimado usuario, sólo se permite realizar una operación de recarga por día.");
					}
				}
			}
		} 
		catch (ServiceException e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	public int getCodigoPorDescError(final String error)
	{
		int codigo = -1;
		if(error != null)
		{
			try
			{			
				codigo = error.toLowerCase().contains("approved") == true ? 0 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("processed") == true ? 1 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("declined") == true ? 2 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("expired") == true ? 4 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("funds") == true ? 5 : -1;

				//*********************************************************************
				if(codigo == -1)
					codigo = error.toLowerCase().contains("timed") == true ? 6 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("expired") == true ? 7 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("acquirer") == true ? 8 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("system") == true ? 9 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("supported") == true ? 10 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("contact") == true ? 11 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("aborted") == true ? 12 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("blocked") == true ? 13 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("cancelled") == true ? 14 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("deferred") == true ? 15 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("referred") == true ? 16 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("authentication") == true ? 17 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("invalid") == true ? 18 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("lock") == true ? 19 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("submitted") == true ? 20 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("enrolled") == true ? 21 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("pending") == true ? 22 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("exceeded") == true ? 23 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("duplicate") == true ? 24 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("avs_csc") == true ? 27 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("avs") == true ? 25 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("csc") == true ? 26 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("payment") == true ? 28 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("settlement") == true ? 29 : -1;
				if(codigo == -1)
					codigo = error.toLowerCase().contains("unknown") == true ? 30 : -1;

			}
			catch (Exception e) {
				codigo = -1;
			}
		}
		return codigo;
	}

	public String getDescErrorPorCodigo(final int codigo)
	{
		String descError = "";

		switch (codigo) {
		case 0:
			descError ="Transacción Aprobada";
			break;
		case 1:
			descError = "La transacción no puede ser procesada";
			break;
		case 2:
			descError = "Transacción Rechazada";
			break;
		case 4:
			descError = "La tarjeta ha expirado";
			break;
		case 5:
			descError = "Saldo insuficiente";
			break;
		case 6:
			descError = "Tiempo de espera";
			break;
		case 7:
			descError = "Transaccion Rechazada tarjeta vencida";
			break;
		case 8:
			descError = "Ocurrio un error en el sistema al procesar la transaccion";
			break;
		case 9:
			descError = "Ocurrio un error interno del sistema al procesar la transaccion";
			break;
		case 10:
			descError = "Tipo de transaccion no soportada";
			break;
		case 11:
			descError = "Transaccion rechazada - No hay contacto";
			break;
		case 12:
			descError = "Transaccion rechazada por pagador";
			break;
		case 13:
			descError = "Transaccion cancelada debido a riesgos o reglas de seguridad 3D";
			break;
		case 14:
			descError = "Transaccion cancelada por saldar";
			break;
		case 15:
			descError = "Transaccion diferida y espera de procesamiento";
			break;
		case 16:
			descError = "Transaccion rechazada - consulte emisor";
			break;
		case 17:
			descError = "Autentificación segura 3D falló";
			break;
		case 18:
			descError = "Codigo de seguridad de la tarjeta invalido";
			break;
		case 19:
			descError = "Estado bloqueado - Otra transaccion en proceso para este estado";
			break;
		case 20:
			descError = "Transaccion entregada - respuesta aun no recibida";
			break;
		case 21:
			descError = "Titular de la tarjeta no esta inscrito en 3D";
			break;

		case 22:
			descError = "Transaccion pendiente";
			break;
		case 23:
			descError = "Limite de reintentos para la transaccion";
			break;
		case 24:
			descError = "Transaccion rechazada debido a duplicidad";
			break;
		case 25:
			descError = "Transaccion rechazada debido a direccion";
			break;
		case 26:
			descError = "Transaccion rechazada debido a codigo de seguridad de la tarjeta";
			break;
		case 27:
			descError = "Transaccion rechazada debido a direccion y codigo de seguridad";
			break;
		case 28:
			descError = "Transaccion rechazada debido a plan de pago";
			break;
		case 29:
			descError = "Transaccion aprovada - Pendiente liquidacion";
			break;
		case 30:
			descError = "Respuesta desconocida";
			break;
		default:
			descError = "La operación fue rechazada por el banco emisor";
			break;
		}

		return descError;
	}

	public static void validaCantidadTA(final Double importe) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		List<CatalogoAbonos> respuesta = new ArrayList<CatalogoAbonos>();
		boolean existeCantidad = false;
		Double importeRegistrado = 0.0;
		try{			
			respuesta=oracle.getCatalogoAbonos();
		}
		catch (Exception e) {
			Logger.write(" (Exception) : " + e.getLocalizedMessage());
		}
		for(CatalogoAbonos cantidad : respuesta)
		{
			if(cantidad.getCantidad()!= null)
			{
				try
				{
					importeRegistrado = Double.valueOf(cantidad.getCantidad());
				}
				catch (NumberFormatException e) {
					importeRegistrado = 0.0;
				}
				if(importeRegistrado.equals(importe))
				{
					existeCantidad = true;
					break;
				}
			}
		}

		if(!existeCantidad)
		{
			Logger.write(" El importe es inválido " + importe);
			throw new ServiceException("El importe es inválido");
		}
	}
	
	public void validaRespuestaCargoTA(final String codigoRespuesta, final String tipoServicio, final long secuencia)
	 throws ServiceException
	{
		final String valCero = "0";
		if(valCero.equals(tipoServicio)){//Nuevo Servicio de Abono de TA Jboss
			if(!valCero.equals(codigoRespuesta)){
				String reversoMsj = "";
				final  Map<String,String> catalogoErrores = getCatalogRespCharge();
				try{					
					for(int cont = 0; cont < 5; cont++){
						if(ABONOTOREVERSEVAL92.equals(codigoRespuesta) || ABONOTOREVERSEVAL91.equals(codigoRespuesta)){
							final CallServiceAbonoTA reversoWS = new CallServiceAbonoTA();
							final String resposeReverso = reversoWS.reversoTA(secuencia);
							final String codigoReverso = ParseXMLServices.parseReversoTA(resposeReverso);
							if("0".equals(codigoReverso) || "51".equals(codigoReverso) || "94".equals(codigoReverso)){
								reversoMsj = " (Se reverso el abono exitosamente [" + codigoReverso + "])";
								break;
							}
						}
					}
				}
				catch (Exception e) {
					reversoMsj = " (Error al reversar abono: " + e.getLocalizedMessage() + ")";
				}
				throw new ServiceException("error al realizar abono de TA: [" + codigoRespuesta + "] "
						+ (catalogoErrores.get(codigoRespuesta) == null ? "Otros" : catalogoErrores.get(codigoRespuesta))
						+ reversoMsj
						);
			}
		}else{
			if(!valCero.equals(codigoRespuesta)){
				throw new ServiceException("error al realizar abono de TA codigoError : " + codigoRespuesta);
			}
		}
	}
	
	public void validaRespuestaReversoTA(final String codigoRespuesta)throws ServiceException
	{
		final String valCero = "0";
		if(!valCero.equals(codigoRespuesta)){
			final  Map<String,String> catalogoErrores = getCatalogRespReverse();
			throw new ServiceException("error al realizar reverso de TA: [" + codigoRespuesta + "] "
					+ (catalogoErrores.get(codigoRespuesta) == null ? "Otros" : catalogoErrores.get(codigoRespuesta)));
		}
	}
	
	private Map<String,String> getCatalogRespCharge(){
		Map<String,String> catalogResp = new HashMap<String, String>();
		catalogResp.put("0", "Exitoso");
		catalogResp.put("5", "Otros");
		catalogResp.put("13", "Monto Inválido");
		catalogResp.put("30", "Formato Inválido");
		catalogResp.put("60", "Comunicarse con la Telefonica");
		catalogResp.put("83", "Teléfono Inválido");
		catalogResp.put("89", "Autorizador Abajo");
		catalogResp.put("91", "Tiempo excedido de espera ");
		catalogResp.put("92", "Tiempo excedido de espera");
		catalogResp.put("94", "Transacción Repetida");
				
		return catalogResp;
	}
	
	private Map<String,String> getCatalogRespReverse(){
		Map<String,String> catalogResp = new HashMap<String, String>();
		catalogResp.put("0", "Exitoso");
		catalogResp.put("5", "Otros");
		catalogResp.put("94", "Transacción ya reversada");
		catalogResp.put("51", "Saldo Insuficiente");		
		catalogResp.put("91", "Tiempo excedido de espera ");
		catalogResp.put("92", "Tiempo excedido de espera");		
				
		return catalogResp;
	}
}
