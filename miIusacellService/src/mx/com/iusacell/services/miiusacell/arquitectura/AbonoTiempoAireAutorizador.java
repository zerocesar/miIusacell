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
import mx.com.iusacell.services.miusacell.call.CallServiceAbonoTA;
import mx.com.iusacell.services.miusacell.call.CallServiceAbonoTiempoAireLegacy;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.ParseXMLServices;
import mx.com.iusacell.services.miusacell.call.CallServiceCardBlackList;


public class AbonoTiempoAireAutorizador implements AbonoTiempoAireIn{
	private static final ValidatorCharges VALIDATORCHARGE = new ValidatorCharges();

	public AbonoTiempoAireVO flujo(final String token,final String dn,final String dnParaAbono,final int anioExpira, 
			final String cdgSeguridad,final String concepto,final  Double importe,final int mesExpira,final String numTarjeta, 
			String tipoTarjeta,final String ip,final Long secuencia,final String password, 
			final int tipoPlataforma,final String idIdentificar) throws ServiceException
			{
//		CallServiceAbonoTiempoAireETAK abonoTiempoAireETAK = new CallServiceAbonoTiempoAireETAK();
		CallServiceAbonoTiempoAireLegacy abonoTiempoaireLegacy = new CallServiceAbonoTiempoAireLegacy();
		OracleProcedures oracle = new OracleProcedures();
		String impactaCaja = "0";
		String abonoTiempoAire = "1";
		String numTransaccion = "";		
		String ejecutaAbonoTiempoAire= "0";
		AbonoTiempoAireVO tiempoAireVO = new AbonoTiempoAireVO();
		ValidarErrorTarjetaVO validaErr = new ValidarErrorTarjetaVO();
		String validaErrTarSQL = "0"; 
		long sNumTransaccion = 0L;
		String sSistema = "2";
		String valorSTipoMovTarjeta = "0";
		Transaction respAutorizador = null;		
		final Autorizador autorizadorBusiness = new Autorizador();		
		int businessId = 0;		
		final String storeId = "MI-AT&T";
		
		if(tipoPlataforma == 3){
			businessId = 1;		
		}else{
			businessId = 1770;		
		}

		CallServiceCardBlackList validaTDC = new CallServiceCardBlackList();
		boolean tdcBlacklList= false;
		String validarCardBlacklist="";

		int validaPwd = oracle.validarPassword(dn, password);
		if(validaPwd != 0){
			throw new ServiceException("No se pudo validar el password");
		}

		/** Valida operaciones realizadas en el dia*/		
		VALIDATORCHARGE.validaOperacionesRealizadasTA(dn, dnParaAbono);
		/*****/

		try
		{	try
		{	validarCardBlacklist = oracle.getValorParametro(164);
		if (validarCardBlacklist.equals("1")){
			tdcBlacklList=validaTDC.consultaCardBlackList(numTarjeta);
			if (tdcBlacklList){
				try{
					String user = (dn != null) ? dn.substring(2) : "222222";
					oracle.setCardBlackList(numTarjeta, "Abono tiempo aire",String.valueOf(tdcBlacklList),user);							
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
		try
		{
			//				transXTarjeta=oracle.getTransXTarjeta(numTarjeta);
			oracle.getTransXTarjeta(numTarjeta);
		}catch(Exception e){
			if(e.getLocalizedMessage().contains("ORA-20000")){
				throw new ServiceException("ServiceException [TransXTarjeta] :"+ e.getLocalizedMessage());	
			}
		}

		TarjetaSemaforosSaveCustomerApplyCharge.semaphoresSaveCustomerApplyCharge(dn, numTarjeta, importe.toString());	

		try{
			impactaCaja = oracle.getValorParametro(2);
			abonoTiempoAire = oracle.getValorParametro(3);
			ejecutaAbonoTiempoAire = oracle.getValorParametro(4);		
			valorSTipoMovTarjeta = oracle.getValorParametro(203);
		}catch (ServiceException e) {
			impactaCaja = "0";
			abonoTiempoAire = "1";
			ejecutaAbonoTiempoAire = "1";
			valorSTipoMovTarjeta = "0";
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
				String sIdBenefCh = "";
				String sIdctarjet = "";
				String sMesesSinIntereses = "0";
				String sNomTitularCh = "";
				String sRefBancaria = "";
				String sNumAutor = "";
				String sTipoMovTarjeta = valorSTipoMovTarjeta;
				String sTipoPago = "TC";
				String sTrack2 = "";
				String sIdProducto = "";
				String sCantidad = "0";
				String sDescuento = "0";
				String sNumSerie = "";
				String sIccid = "";
				String sNumCheque = "0";
				String sNumCtaCheque = "";
				tipoTarjeta = "";

				String Expiraanio = Integer.toString(anioExpira);
				Calendar cal = new GregorianCalendar();
				sNumTransaccion = cal.getTimeInMillis();
				numTransaccion = String.valueOf(sNumTransaccion);
				try{
					oracle.abonoTiempoAireCaja(idIdentificar, dnParaAbono, user, contrasena, Expiraanio, "xxx", 
							concepto, importe, mesExpira, numTarjeta, tipoTarjeta, ip, secuencia, password, 
							tipoPlataforma, sCdgCsi, sImporteRenta, sImporteDeposito, numTransaccion, sRegion, 
							sSistema, sTipoMovCaja, sTipoOperacion, sTipoTransac, sUsuario, sVendedor, sBancoId, 
							sCampo55, sIdBenefCh, sIdctarjet, sMesesSinIntereses, sNomTitularCh, sRefBancaria, 
							sNumAutor, sTipoMovTarjeta, sTipoPago, sTrack2, sIdProducto, sCantidad, sDescuento, 
							sNumSerie, sIccid, sNumCheque, sNumCtaCheque, 153);
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}					

				respAutorizador = autorizadorBusiness.executeGeneralCharge(numTarjeta, cdgSeguridad, mesExpira, 
						anioExpira, importe, Integer.valueOf(sMesesSinIntereses), businessId, ip, 1, 
						Integer.valueOf(sSistema), concepto, sRegion, storeId);							
				try{
					oracle.cajaRespuesta(idIdentificar,0,respAutorizador.getBankCode(), respAutorizador.getIdRegistro(), 
							respAutorizador.getAutorizationCode(), respAutorizador.getCardNumber(), 2121,
							String.valueOf(respAutorizador.getChargeId()),respAutorizador.getDescResp());
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}		
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
							errorCode = error;	
						}
					}else if(codigo != -1){
						errorCode = VALIDATORCHARGE.getDescErrorPorCodigo(codigo);
					}else{
						errorCode = error;	
					}
				}else{
					errorCode = "La operación fue rechazada por el banco emisor";
				}
				throw new ServiceException("Err [Caja] :" + errorCode);
			}
		}

		String sResponse = "";
		try{
			if (ejecutaAbonoTiempoAire.equals("1"))
			{ 
				int unidadNegocio = 1;								
				try{
					oracle.abonoTAUnidad(idIdentificar, unidadNegocio, dnParaAbono, importe, secuencia,2121);
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}

				if(abonoTiempoAire.equals("0")){//Nuevo Servicio de Abono de TA Jboss
					final CallServiceAbonoTA abonoTANvo = new CallServiceAbonoTA();
					sResponse = abonoTANvo.abonoTA(dnParaAbono, importe, secuencia);
					if(sResponse != null && !sResponse.equals("")){
						tiempoAireVO = ParseXMLServices.parseAbonoTA(sResponse);	}				
				}else{
					sResponse = abonoTiempoaireLegacy.abonoTiempoAireLegacy(dnParaAbono, importe, secuencia);
					if(sResponse != null && !sResponse.equals(""))
						tiempoAireVO = ParseXMLFile.TiempoAireLegacy(sResponse);
				}
				
				try{
					oracle.abonoTAUnidadRespuesta(idIdentificar,unidadNegocio,0,tiempoAireVO.getCodigoRespuestaAbonoTA(),tiempoAireVO.getNumeroAutorizacionAbonoTA(),tiempoAireVO.getNumTransaccionCaja(), tiempoAireVO.getNumeroAutorizacionCaja(),tiempoAireVO.getIdRegistroCaja(),2121,"","");
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				
				VALIDATORCHARGE.validaRespuestaCargoTA(tiempoAireVO.getCodigoRespuestaAbonoTA(), abonoTiempoAire, secuencia);				
				
			}else{
				tiempoAireVO.setCodigoRespuestaAbonoTA("0");
				tiempoAireVO.setNumeroAutorizacionAbonoTA("999999");
			}

		}catch(Exception ex){
			Transaction reversoResponse = null;
			String msgReverso = " [Reverso] ";
			try{
				reversoResponse = autorizadorBusiness.autorizadorReverse(respAutorizador, respAutorizador.getIdRegistro(), Integer.parseInt(sSistema), ip);
				msgReverso += "["+reversoResponse.getAutorizationCode()+"] " + reversoResponse.getDescResp();
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				msgReverso+= e.getMessage();
			}		
			try{
				oracle.reversaCaja(idIdentificar,3,ip,2121,"1",ex.getMessage() + msgReverso);
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
			throw new ServiceException("Err [TiempoAire] : " +ex.getMessage() + msgReverso);
		}

		if(impactaCaja.equals("1")){
			try{
				oracle.cajaConfirmaPago(idIdentificar,respAutorizador.getIdRegistro(), ip, tiempoAireVO.getNumeroAutorizacionAbonoTA(), secuencia,2121);
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}

			try{
				oracle.cajaConfirmaPagoRes(idIdentificar, numTransaccion, 2121,"","");
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
			tiempoAireVO.setNumTransaccionCaja(numTransaccion);
		}else{
			respAutorizador.setAutorizationCode("0548796");
			respAutorizador.setIdRegistro(1234);
			tiempoAireVO.setNumTransaccionCaja("98741");
		}

		tiempoAireVO.setNumeroAutorizacionCaja(respAutorizador.getAutorizationCode());			
		tiempoAireVO.setIdRegistroCaja(respAutorizador.getIdRegistro());

		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return tiempoAireVO;
			}

	public AbonoTiempoAireVO flujoFingerPrint(final String token,final String dn,final String dnParaAbono,final int anioExpira, 
			final String cdgSeguridad,final String concepto,final  Double importe,final int mesExpira,final String numTarjeta, 
			String tipoTarjeta,final String ip,final Long secuencia,final String password, 
			final int tipoPlataforma,final String idIdentificar, final String email, final String fingerPrint, final boolean isFlowATT) throws ServiceException
			{
//		CallServiceAbonoTiempoAireETAK abonoTiempoAireETAK = new CallServiceAbonoTiempoAireETAK();
		CallServiceAbonoTiempoAireLegacy abonoTiempoaireLegacy = new CallServiceAbonoTiempoAireLegacy();
		OracleProcedures oracle = new OracleProcedures();
		String impactaCaja = "0";
		String abonoTiempoAire = "1";
		String numTransaccion = "";
		String ejecutaAbonoTiempoAire= "0";
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
		
		int validaPwd = oracle.validarPassword(dn, password);
		if(validaPwd != 0){
			throw new ServiceException("No se pudo validar el password");
		}

		/** Valida operaciones realizadas en el dia*/		
		VALIDATORCHARGE.validaOperacionesRealizadasTA(dn, dnParaAbono);
		/*****/

		try
		{	try
		{	validarCardBlacklist = oracle.getValorParametro(164);
		if (validarCardBlacklist.equals("1")){
			tdcBlacklList=validaTDC.consultaCardBlackList(numTarjeta);
			if (tdcBlacklList){
				try{
					String user = (dn != null) ? dn.substring(2) : "222222";
					oracle.setCardBlackList(numTarjeta, "Abono tiempo aire",String.valueOf(tdcBlacklList),user);							
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
		try
		{
			//				transXTarjeta=oracle.getTransXTarjeta(numTarjeta);
			oracle.getTransXTarjeta(numTarjeta);
		}catch(Exception e){
			if(e.getLocalizedMessage().contains("ORA-20000")){
				throw new ServiceException("ServiceException [TransXTarjeta] :"+ e.getLocalizedMessage());	
			}
		}

		TarjetaSemaforosSaveCustomerApplyCharge.semaphoresSaveCustomerApplyCharge(dn, numTarjeta, importe.toString());		

		try{
			impactaCaja = oracle.getValorParametro(2);
			abonoTiempoAire = oracle.getValorParametro(3);
			ejecutaAbonoTiempoAire = oracle.getValorParametro(4);
			valorsTipoMovTarjeta = oracle.getValorParametro(194); 
			activarFingerP = oracle.getValorParametro(195); 				
		}catch (ServiceException e) {
			impactaCaja = "0";
			abonoTiempoAire = "1";
			ejecutaAbonoTiempoAire = "1";
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
				String sIdProducto = "";
				String sCantidad = "0";
				String sDescuento = "0";
				String sNumSerie = "";
				String sIccid = "";
				String sNumCheque = "0";
				String sNumCtaCheque = "";
				tipoTarjeta = "";

				String Expiraanio = Integer.toString(anioExpira);
				Calendar cal = new GregorianCalendar();
				sNumTransaccion = cal.getTimeInMillis();
				numTransaccion = String.valueOf(sNumTransaccion);
				try{
					oracle.abonoTiempoAireCaja(idIdentificar, dnParaAbono, user, contrasena, Expiraanio, "xxx", concepto, 
							importe, mesExpira, numTarjeta, tipoTarjeta, ip, secuencia, password, 
							tipoPlataforma, sCdgCsi, sImporteRenta, sImporteDeposito, numTransaccion, 
							sRegion, sSistema, sTipoMovCaja, sTipoOperacion, sTipoTransac, sUsuario, 
							sVendedor, sBancoId, sCampo55, fingerPrint, sIdctarjet, sMesesSinIntereses, sNomTitularCh, 
							sRefBancaria, sNumAutor, sTipoMovTarjeta, sTipoPago, sTrack2, sIdProducto, sCantidad, 
							sDescuento, sNumSerie, sIccid, sNumCheque, sNumCtaCheque, 153);
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}


				respAutorizador = autorizadorBusiness.applyKeyedCharge(numTarjeta, cdgSeguridad, mesExpira, anioExpira,
						importe, tipoPlataforma, businessId, sRegion, storeId, concepto, fingerPrint, Integer.valueOf(sMesesSinIntereses), 
						dnParaAbono, ip, email, tipoCargo, null, isFlowATT); 						

				try{
					oracle.cajaRespuesta(idIdentificar,0,respAutorizador.getBankCode(), respAutorizador.getIdRegistro(), 
							respAutorizador.getAutorizationCode(), respAutorizador.getCardNumber(), 2121,
							String.valueOf(respAutorizador.getChargeId()),respAutorizador.getDescResp());
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
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
							errorCode = error;	
						}
					}else if(codigo != -1){
						errorCode = VALIDATORCHARGE.getDescErrorPorCodigo(codigo);
					}else{
						errorCode = error;	
					}
				}else{
					errorCode = "La operación fue rechazada por el banco emisor";
				}
				throw new ServiceException("Err [Caja] :" + errorCode);
			}
		}

		String sResponse = "";
		try{
			if (ejecutaAbonoTiempoAire.equals("1"))
			{  
				int unidadNegocio = 1;								
				try{
					oracle.abonoTAUnidad(idIdentificar, unidadNegocio, dnParaAbono, importe, secuencia,2121);
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}

				if(abonoTiempoAire.equals("0")){//Nuevo Servicio de Abono de TA Jboss
					final CallServiceAbonoTA abonoTANvo = new CallServiceAbonoTA();
					sResponse = abonoTANvo.abonoTA(dnParaAbono, importe, secuencia);
					if(sResponse != null && !sResponse.equals("")){
						tiempoAireVO = ParseXMLServices.parseAbonoTA(sResponse);	}				
				}else{
					sResponse = abonoTiempoaireLegacy.abonoTiempoAireLegacy(dnParaAbono, importe, secuencia);
					if(sResponse != null && !sResponse.equals(""))
						tiempoAireVO = ParseXMLFile.TiempoAireLegacy(sResponse);
				}
				try{
					oracle.abonoTAUnidadRespuesta(idIdentificar,unidadNegocio,0,tiempoAireVO.getCodigoRespuestaAbonoTA(),tiempoAireVO.getNumeroAutorizacionAbonoTA(),tiempoAireVO.getNumTransaccionCaja(), tiempoAireVO.getNumeroAutorizacionCaja(),tiempoAireVO.getIdRegistroCaja(),2121,"","");
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				
				VALIDATORCHARGE.validaRespuestaCargoTA(tiempoAireVO.getCodigoRespuestaAbonoTA(), abonoTiempoAire, secuencia);

			}else{
				tiempoAireVO.setCodigoRespuestaAbonoTA("0");
				tiempoAireVO.setNumeroAutorizacionAbonoTA("999999");
			}

		}catch(Exception ex){
			Transaction reversoResponse = null;
			String msgReverso = " [Reverso] ";
			try{
				reversoResponse = autorizadorBusiness.autorizadorReverse(respAutorizador, respAutorizador.getIdRegistro(), Integer.parseInt(sSistema), ip);
				msgReverso += "["+reversoResponse.getAutorizationCode()+"] " + reversoResponse.getDescResp();
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				msgReverso+= e.getMessage();
			}

			try{
				oracle.reversaCaja(idIdentificar,3,ip,2121,"1",ex.getMessage() + msgReverso);
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}

			throw new ServiceException("Err [TiempoAire] : " +ex.getMessage() + msgReverso);
		}

		if(impactaCaja.equals("1")){
			try{
				oracle.cajaConfirmaPago(idIdentificar,respAutorizador.getIdRegistro(), ip, tiempoAireVO.getNumeroAutorizacionAbonoTA(), secuencia,2121);
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}	
			try{
				oracle.cajaConfirmaPagoRes(idIdentificar, numTransaccion, 2121,"","");
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
			tiempoAireVO.setNumTransaccionCaja(numTransaccion);
		}else{
			respAutorizador.setAutorizationCode("0548796");
			respAutorizador.setIdRegistro(1234);
			tiempoAireVO.setNumTransaccionCaja("98741");
		}			
		tiempoAireVO.setNumeroAutorizacionCaja(respAutorizador.getAutorizationCode());
		tiempoAireVO.setIdRegistroCaja(respAutorizador.getIdRegistro());

		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return tiempoAireVO;
	}	
}
