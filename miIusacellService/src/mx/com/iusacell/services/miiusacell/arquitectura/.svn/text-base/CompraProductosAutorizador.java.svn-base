package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.arquitectura.autorizador.Autorizador;
import mx.com.iusacell.services.miiusacell.arquitectura.autorizador.ValidatorCharges;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.AbonoTiempoAireVO;
import mx.com.iusacell.services.miiusacell.vo.ValidarErrorTarjetaVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.Transaction;
import mx.com.iusacell.services.miiusacell.vo.autorizador.TransactionVO;
import mx.com.iusacell.services.miusacell.call.CallServiceCardBlackList;


public class CompraProductosAutorizador{
	private static final ValidatorCharges VALIDATORCHARGE = new ValidatorCharges();

	public AbonoTiempoAireVO compraProducto(final String token,final TransactionVO transaction, final String idIdentificar, final long secuencia, final boolean isFlowATT) throws ServiceException
	{		
		OracleProcedures oracle = new OracleProcedures();
		String impactaCaja = "0";		
		String numTransaccion = "";		
		AbonoTiempoAireVO tiempoAireVO = new AbonoTiempoAireVO();
		ValidarErrorTarjetaVO validaErr = new ValidarErrorTarjetaVO();
		String validaErrTarSQL = "0"; 
		long sNumTransaccion = 0L;
		String sSistema = "2";
		String valorsTipoMovTarjeta = "0";
		String activarFingerP = "0";	
		Transaction respAutorizador = null;		
		final Autorizador autorizadorBusiness = new Autorizador();		
		final int businessId = 1770;		
		final String storeId = "MI-AT&T";
		final int tipoCargo = 1;

		CallServiceCardBlackList validaTDC = new CallServiceCardBlackList();
		boolean tdcBlacklList= false;
		String validarCardBlacklist="";							

		try
		{	try
		{	validarCardBlacklist = oracle.getValorParametro(164);
		if (validarCardBlacklist.equals("1")){
			tdcBlacklList=validaTDC.consultaCardBlackList(transaction.getNumTarjeta());
			if (tdcBlacklList){
				try{
					String user =  "555555";
					oracle.setCardBlackList(transaction.getNumTarjeta(), "compra productos",String.valueOf(tdcBlacklList),user);							
				}catch(ServiceException e){
					Logger.write("[WARN]   ::  ServiceException [setCardBlackList]" + e.getLocalizedMessage());
				}
				throw new ServiceException("La TDC se encuentra en BlackList");
			}
		}
		}catch(Exception e){
			if (tdcBlacklList){
				throw new ServiceException("ServiceException [cardBlackList] :"+ e.getLocalizedMessage());
			}
		}
//		try
//		{
//			oracle.getTransXTarjeta(transaction.getNumTarjeta());
//		}catch(Exception e){
//			if(e.getLocalizedMessage().contains("ORA-20000")){
//				throw new ServiceException("ServiceException [TransXTarjeta] :"+ e.getLocalizedMessage());	
//			}
//		}

		//TarjetaSemaforosSaveCustomerApplyCharge.semaphoresSaveCustomerApplyCharge(dn, numTarjeta, importe.toString());		

		try{
			impactaCaja = oracle.getValorParametro(2);			
			valorsTipoMovTarjeta = oracle.getValorParametro(194); 
			activarFingerP = oracle.getValorParametro(195); 				
		}catch (ServiceException e) {
			impactaCaja = "0";			
			valorsTipoMovTarjeta = "0";				
		}			
		if(impactaCaja.equals("1")){
			try{
				String user = "JcDAxzwOwKEbI12XQ1KNjQ*/";
				String contrasena = "JcDAxzwOwKEbI12XQ1KNjQ*/";
				String sCdgCsi = "";
				String sImporteRenta = "0";
				String sImporteDeposito = "0";					
				String sRegion = "09";					
				String sTipoMovCaja = "";
				String sTipoOperacion = "";
				String sTipoTransac = "";
				String sUsuario = "";
				String sVendedor = "";
				String sBancoId = "";
				String sCampo55 = "";
				//				String sIdBenefCh = "";
				String sIdctarjet = "";
				String sMesesSinIntereses = "0";
				String sNomTitularCh = "";
				String sRefBancaria = "";
				String sNumAutor = "";

				String sTipoMovTarjeta = "11";
				if(activarFingerP.equals("1")){
					sTipoMovTarjeta = valorsTipoMovTarjeta;
				}
				String sTipoPago = "TC";
				String sTrack2 = "";				
				String sCantidad = "0";
				String sDescuento = "0";
				String sNumSerie = "";
				String sIccid = "";
				String sNumCheque = "0";
				String sNumCtaCheque = "";
				String tipoTarjeta = "";

				String Expiraanio = Integer.toString(transaction.getMesExpira());
				Calendar cal = new GregorianCalendar();
				sNumTransaccion = cal.getTimeInMillis();
				numTransaccion = String.valueOf(sNumTransaccion);
				try{
					oracle.abonoTiempoAireCaja(idIdentificar, transaction.getPedido(), user, contrasena, Expiraanio, "xxx", transaction.getConcepto(), 
							transaction.getImporte(), transaction.getMesExpira(), transaction.getNumTarjeta(), tipoTarjeta, transaction.getIpAddress(), 
							secuencia, "", 
							transaction.getTipoPlataforma(), sCdgCsi, sImporteRenta, sImporteDeposito, numTransaccion, 
							sRegion, sSistema, sTipoMovCaja, sTipoOperacion, sTipoTransac, sUsuario, 
							sVendedor, sBancoId, sCampo55, transaction.getFingerPrint(), sIdctarjet, sMesesSinIntereses, sNomTitularCh, 
							sRefBancaria, sNumAutor, sTipoMovTarjeta, sTipoPago, sTrack2, transaction.getProducto(), sCantidad, 
							sDescuento, sNumSerie, sIccid, sNumCheque, sNumCtaCheque, 153);
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}


				respAutorizador = autorizadorBusiness.applyKeyedCharge(transaction.getNumTarjeta(), transaction.getCdgSeguridad(), transaction.getMesExpira(), 
						transaction.getAnioExpira(),transaction.getImporte(), transaction.getTipoPlataforma(), businessId, sRegion, storeId, 
						transaction.getConcepto(), transaction.getFingerPrint(), Integer.valueOf(sMesesSinIntereses), 
						"", transaction.getIpAddress(), transaction.getEmail(), tipoCargo, transaction.getAddress(), isFlowATT); 						

				/**RESGISTRA BITACORAS DE RESPUESTA INI*/
				try{
					oracle.cajaRespuesta(idIdentificar,0,respAutorizador.getBankCode(), respAutorizador.getIdRegistro(), 
							respAutorizador.getAutorizationCode(), respAutorizador.getCardNumber(), 2121,
							String.valueOf(respAutorizador.getChargeId()),respAutorizador.getDescResp());
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				
				try{
					oracle.cajaConfirmaPago(idIdentificar,respAutorizador.getIdRegistro(), transaction.getIpAddress(), "-1", secuencia,2121);
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}	
				try{
					oracle.cajaConfirmaPagoRes(idIdentificar, numTransaccion, 2121,"","");
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				/**RESGISTRA BITACORAS DE RESPUESTA FIN*/
				
				tiempoAireVO.setNumeroAutorizacionCaja(respAutorizador.getAutorizationCode());
				tiempoAireVO.setIdRegistroCaja(respAutorizador.getIdRegistro());
				tiempoAireVO.setNumTransaccionCaja(numTransaccion);
				tiempoAireVO.setCodigoRespuestaAbonoTA("");
				tiempoAireVO.setNumeroAutorizacionAbonoTA("");
				
			}catch(Exception ex){
				try{
					oracle.cajaRespuesta(idIdentificar,0,"", 0, "", "", 2121,"1",ex.getMessage());
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				Logger.write("Detail   ::  ServiceException [Caja] : " +ex.getMessage());				
				String errorCode;
				if(ex != null && ex.getMessage() != null){
					String error = ex.getMessage();						
					int codigo = -1;						
					codigo = VALIDATORCHARGE.getCodigoPorDescError(error);

					try{
						validaErrTarSQL = oracle.getValorParametro(24);
					}catch (ServiceException e) {
						validaErrTarSQL = "0";
					}


					if(validaErrTarSQL.equals("1")){
						try{
							validaErr = oracle.validarTarjeta(codigo);
						}catch (Exception e) {
							Logger.write("Detail al obtener la descripcion :: validarTarjeta");
						}

						if(validaErr != null && !validaErr.equals("") && codigo != -1){
							errorCode = validaErr.getDescEspaniol();
						}else{
							errorCode = "La operación fue rechazada por el banco emisor";	
						}
					}else{
						errorCode = VALIDATORCHARGE.getDescErrorPorCodigo(codigo);
					}
				}else{
					errorCode = "La operación fue rechazada por el banco emisor";
				}
				throw new ServiceException("Err [Caja] :" + errorCode);
			}					
		}else{
			respAutorizador = new Transaction();
			respAutorizador.setAutorizationCode("0548796");
			respAutorizador.setIdRegistro(1234);
			tiempoAireVO.setNumTransaccionCaja("98741");
		}					

		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return tiempoAireVO;
	}	
}
