package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.arquitectura.autorizador.ValidatorCharges;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.AbonoTiempoAireVO;
import mx.com.iusacell.services.miiusacell.vo.CajaRegistraMovimientoVO;
import mx.com.iusacell.services.miiusacell.vo.TarjetasCajaVO;
import mx.com.iusacell.services.miiusacell.vo.ValidarErrorTarjetaVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.AddressVO;
import mx.com.iusacell.services.miusacell.call.CallServiceAbonoTA;
import mx.com.iusacell.services.miusacell.call.CallServiceAbonoTiempoAireLegacy;
import mx.com.iusacell.services.miusacell.call.CallServiceCajaRegistraMovimiento;
import mx.com.iusacell.services.miusacell.call.CallServiceCardBlackList;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.ParseXMLServices;


public class AbonoTiempoAire implements AbonoTiempoAireIn {
	private static final ValidatorCharges VALIDATORCHARGE = new ValidatorCharges();

	public AbonoTiempoAireVO flujo(final String token,final String dn,final String dnParaAbono,final int anioExpira, 
	        final String cdgSeguridad,final String concepto,final  Double importe,final int mesExpira,final String numTarjeta, 
	        String tipoTarjeta,final String ip,final Long secuencia,final String password, 
	        final int tipoPlataforma,final String idIdentificar) throws ServiceException
	{
//		CallServiceAbonoTiempoAireETAK abonoTiempoAireETAK = new CallServiceAbonoTiempoAireETAK();
		CallServiceAbonoTiempoAireLegacy abonoTiempoaireLegacy = new CallServiceAbonoTiempoAireLegacy();
		CallServiceCajaRegistraMovimiento cajaRegistraMovimiento = new CallServiceCajaRegistraMovimiento();
		OracleProcedures oracle = new OracleProcedures();
		String impactaCaja = "0";
		String abonoTiempoAire = "1";
		String sResponseCaja = "";
		String numTransaccion = "";		
		String ejecutaAbonoTiempoAire= "0";
		AbonoTiempoAireVO tiempoAireVO = new AbonoTiempoAireVO();
		CajaRegistraMovimientoVO cajaRespuesta = new CajaRegistraMovimientoVO();
		ValidarErrorTarjetaVO validaErr = new ValidarErrorTarjetaVO();
		String validaErrTarSQL = "0"; 
		long sNumTransaccion = 0L;
		String sSistema = "2";
		String valorSTipoMovTarjeta = "0";
		
		CallServiceCardBlackList validaTDC = new CallServiceCardBlackList();
		boolean tdcBlacklList= false;
		String validarCardBlacklist="";
		
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
			
			int validaPwd = oracle.validarPassword(dn, password);
			if(validaPwd != 0){
				throw new ServiceException("No se pudo validar el password");
			}
			
			/** Valida operaciones realizadas en el dia*/			
			VALIDATORCHARGE.validaOperacionesRealizadasTA(dn, dnParaAbono);
			/*****/

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
			
			if(tipoPlataforma == 3){
				valorSTipoMovTarjeta = "12";
			}
		
			if(impactaCaja.equals("1")){
				try{
					String user = "JcDAxzwOwKEbI12XQ1KNjQ*/";
					String contrasena = "JcDAxzwOwKEbI12XQ1KNjQ*/";
					String sCdgCsi = "";
					String sImporteRenta = "0";
					String sImporteDeposito = "0";					
					String sRegion = "";					
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
						oracle.abonoTiempoAireCaja(idIdentificar, dnParaAbono, user, contrasena, Expiraanio, "xxx", concepto, importe, mesExpira, numTarjeta, tipoTarjeta, ip, secuencia, password, tipoPlataforma, sCdgCsi, sImporteRenta, sImporteDeposito, numTransaccion, sRegion, sSistema, sTipoMovCaja, sTipoOperacion, sTipoTransac, sUsuario, sVendedor, sBancoId, sCampo55, sIdBenefCh, sIdctarjet, sMesesSinIntereses, sNomTitularCh, sRefBancaria, sNumAutor, sTipoMovTarjeta, sTipoPago, sTrack2, sIdProducto, sCantidad, sDescuento, sNumSerie, sIccid, sNumCheque, sNumCtaCheque, 153);
					}catch (Exception e) {
						Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
					}					

					sResponseCaja = cajaRegistraMovimiento.cajaRegistraMovimiento(dnParaAbono, user, contrasena, anioExpira, cdgSeguridad, concepto, importe, mesExpira, numTarjeta, tipoTarjeta, ip, secuencia, password, tipoPlataforma, sCdgCsi, sImporteRenta, sImporteDeposito, sNumTransaccion, sRegion, sSistema, sTipoMovCaja, sTipoOperacion, sTipoTransac, sUsuario, sVendedor, sBancoId, sCampo55, sIdBenefCh, sIdctarjet, sMesesSinIntereses, sNomTitularCh, sRefBancaria, sNumAutor, sTipoMovTarjeta, sTipoPago, sTrack2, sIdProducto, sCantidad, sDescuento, sNumSerie, sIccid, sNumCheque, sNumCtaCheque);
					if(sResponseCaja != null && !sResponseCaja.equals(""))
						cajaRespuesta = ParseXMLFile.ParseCajaRegistraMovimiento(sResponseCaja);
					else
						throw new ServiceException("Error al consultar servicio de caja");
					
					if(cajaRespuesta != null && cajaRespuesta.getTarjetas() != null) {
						for (int a=0;  a < cajaRespuesta.getTarjetas().size(); a++){
							try{
								oracle.cajaRespuesta(idIdentificar,a,cajaRespuesta.getErrorCode(), cajaRespuesta.getIdRegistro(), cajaRespuesta.getTarjetas().get(a).getNumAut(), cajaRespuesta.getTarjetas().get(a).getNumTarjeta(), 2121,"","");
							}catch (Exception e) {
								Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
							}
						}
					}
				}catch(Exception ex){
					try{
						oracle.cajaRespuesta(idIdentificar,0,"", 0, "", "", 2121,"1",ex.getMessage());
					}catch (Exception e) {
						Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
					}
					Logger.write("Detail   ::  ServiceException [Caja] : " +ex.getMessage());					
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
							    cajaRespuesta.setErrorCode(validaErr.getDescEspaniol());
							}else{
								cajaRespuesta.setErrorCode("La operación fue rechazada por el banco emisor");	
							}
						}else{
							cajaRespuesta.setErrorCode(VALIDATORCHARGE.getDescErrorPorCodigo(codigo));
						}
					}else{
						cajaRespuesta.setErrorCode("La operación fue rechazada por el banco emisor");
					}
					throw new ServiceException("Err [Caja] :" + cajaRespuesta.getErrorCode());
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
				try{
					oracle.reversaCaja(idIdentificar,3,ip,2121,"1",ex.getMessage());
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				
				cajaRegistraMovimiento.reversaCaja(cajaRespuesta.getIdRegistro(),ip);
				
				/** REVERSO DE TRANSACCION**/
				try
				{
					int sistema = 2;
					String activarReversoTrans = "0";
					try{
						activarReversoTrans = oracle.getValorParametro(142);
					}
					catch (Exception e) {
						Logger.write("     Detail        : (Exception) " + e.getMessage());
					}
					try{
						sistema = Integer.parseInt(sSistema);
					}
					catch (NumberFormatException e) {
						Logger.write("NumberFormatException - sistema " + e.getLocalizedMessage());
					}
					if(activarReversoTrans.equals("1"))
					{
						cajaRegistraMovimiento.reversarTransaccion(sNumTransaccion, sistema, ip);
					}
				}
				catch (Exception e) {
					Logger.write("Exception - reversarTransaccion : " + e.getLocalizedMessage());
				}
				/**********/
				
				throw new ServiceException("Err [TiempoAire] : " +ex.getMessage());
			}

			if(impactaCaja.equals("1")){
				try{
					try{
						oracle.cajaConfirmaPago(idIdentificar,cajaRespuesta.getIdRegistro(), ip, tiempoAireVO.getNumeroAutorizacionAbonoTA(), secuencia,2121);
					}catch (Exception e) {
						Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
					}

					sResponse = cajaRegistraMovimiento.cajaConfirmacionPago(cajaRespuesta.getIdRegistro(), ip, tiempoAireVO.getNumeroAutorizacionAbonoTA(), secuencia);
					if(sResponse != null && !sResponse.equals(""))
						numTransaccion = ParseXMLFile.confirmaPagoCaja(sResponse);

					try{
						oracle.cajaConfirmaPagoRes(idIdentificar, numTransaccion, 2121,"","");
					}catch (Exception e) {
						Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
					}
				}catch (Exception e) {
					try{
						oracle.cajaConfirmaPagoRes(idIdentificar, numTransaccion, 2121,"1",e.getMessage());
					}catch (Exception ex) {
						Logger.write("Bitacora: No se impacto la bitacora - " + ex.getLocalizedMessage());
					}
					throw new ServiceException("Err [Caja] : No se confirmo el movimiento");
				}
				
				tiempoAireVO.setNumTransaccionCaja(numTransaccion);
			}else{
				cajaRespuesta.setErrorCode("0");
				cajaRespuesta.setIdRegistro(1234);
				TarjetasCajaVO tarjetas = new TarjetasCajaVO();
				List<TarjetasCajaVO> tarjetasList = new ArrayList<TarjetasCajaVO>();
				tarjetas.setNumAut("12345");
				tarjetas.setNumTarjeta("4567xxxxxxxx1527");
				tarjetasList.add(tarjetas);
				cajaRespuesta.setTarjetas(tarjetasList);
				tiempoAireVO.setNumTransaccionCaja("98741");
			}
			
			if(cajaRespuesta != null && cajaRespuesta.getTarjetas() != null && cajaRespuesta.getTarjetas().get(0)!=null)
				tiempoAireVO.setNumeroAutorizacionCaja(cajaRespuesta.getTarjetas().get(0).getNumAut());
			if(cajaRespuesta != null)
				tiempoAireVO.setIdRegistroCaja(cajaRespuesta.getIdRegistro());
		
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
	        final int tipoPlataforma,final String idIdentificar, final String email, final String fingerPrint, 
	        final AddressVO address, final boolean isFlowATT) throws ServiceException
	{
//		CallServiceAbonoTiempoAireETAK abonoTiempoAireETAK = new CallServiceAbonoTiempoAireETAK();
		CallServiceAbonoTiempoAireLegacy abonoTiempoaireLegacy = new CallServiceAbonoTiempoAireLegacy();
		CallServiceCajaRegistraMovimiento cajaRegistraMovimiento = new CallServiceCajaRegistraMovimiento();
		OracleProcedures oracle = new OracleProcedures();
		String impactaCaja = "0";
		String abonoTiempoAire = "1";
		String sResponseCaja = "";
		String numTransaccion = "";
		String ejecutaAbonoTiempoAire= "0";
		AbonoTiempoAireVO tiempoAireVO = new AbonoTiempoAireVO();
		CajaRegistraMovimientoVO cajaRespuesta = new CajaRegistraMovimientoVO();
		ValidarErrorTarjetaVO validaErr = new ValidarErrorTarjetaVO();
		String validaErrTarSQL = "0"; 
		long sNumTransaccion = 0L;
		String sSistema = "2";
		String valorsTipoMovTarjeta = "0";
		String activarFingerP = "0";		
		
		CallServiceCardBlackList validaTDC = new CallServiceCardBlackList();
		boolean tdcBlacklList= false;
		String validarCardBlacklist="";
		
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
			
			int validaPwd = oracle.validarPassword(dn, password);
			if(validaPwd != 0){
				throw new ServiceException("No se pudo validar el password");
			}
			
			/** Valida operaciones realizadas en el dia*/			
			VALIDATORCHARGE.validaOperacionesRealizadasTA(dn, dnParaAbono);
			/*****/

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
					String sRegion = "";					
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
								importe, mesExpira, numTarjeta, tipoTarjeta, ip, secuencia, password, tipoPlataforma, sCdgCsi, 
								sImporteRenta, sImporteDeposito, numTransaccion, sRegion, sSistema, sTipoMovCaja, sTipoOperacion, 
								sTipoTransac, sUsuario, sVendedor, sBancoId, sCampo55, fingerPrint, sIdctarjet, sMesesSinIntereses, 
								sNomTitularCh, sRefBancaria, sNumAutor, sTipoMovTarjeta, sTipoPago, sTrack2, sIdProducto, sCantidad, 
								sDescuento, sNumSerie, sIccid, sNumCheque, sNumCtaCheque, 153);
					}catch (Exception e) {
						Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
					}
					
					sResponseCaja = cajaRegistraMovimiento.cajaRegistraMovimientoFP(dnParaAbono, user, contrasena, anioExpira, cdgSeguridad, concepto, importe, mesExpira, numTarjeta, tipoTarjeta, ip, secuencia, password, tipoPlataforma, sCdgCsi, sImporteRenta, sImporteDeposito, sNumTransaccion, sRegion, sSistema, sTipoMovCaja, sTipoOperacion, sTipoTransac, sUsuario, sVendedor, sBancoId, sCampo55, sIdBenefCh, sIdctarjet, sMesesSinIntereses, sNomTitularCh, sRefBancaria, sNumAutor, sTipoMovTarjeta, sTipoPago, sTrack2, sIdProducto, sCantidad, sDescuento, sNumSerie, sIccid, sNumCheque, sNumCtaCheque, fingerPrint);
					if(sResponseCaja != null && !sResponseCaja.equals(""))
						cajaRespuesta = ParseXMLFile.ParseCajaRegistraMovimiento(sResponseCaja);
					else
						throw new ServiceException("Error al consultar servicio de caja");
					
					if(cajaRespuesta != null && cajaRespuesta.getTarjetas() != null) {
						for (int a=0;  a < cajaRespuesta.getTarjetas().size(); a++){
							try{
								oracle.cajaRespuesta(idIdentificar,a,cajaRespuesta.getErrorCode(), cajaRespuesta.getIdRegistro(), cajaRespuesta.getTarjetas().get(a).getNumAut(), cajaRespuesta.getTarjetas().get(a).getNumTarjeta(), 2121,"","");
							}catch (Exception e) {
								Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
							}
						}
					}
				}catch(Exception ex){
					try{
						oracle.cajaRespuesta(idIdentificar,0,"", 0, "", "", 2121,"1",ex.getMessage());
					}catch (Exception e) {
						Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
					}
					Logger.write("Detail   ::  ServiceException [Caja] : " +ex.getMessage());					
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
							    cajaRespuesta.setErrorCode(validaErr.getDescEspaniol());
							}else{
								cajaRespuesta.setErrorCode("La operación fue rechazada por el banco emisor");	
							}
						}else{
							cajaRespuesta.setErrorCode(VALIDATORCHARGE.getDescErrorPorCodigo(codigo));
						}
					}else{
						cajaRespuesta.setErrorCode("La operación fue rechazada por el banco emisor");
					}
					throw new ServiceException("Err [Caja] :" + cajaRespuesta.getErrorCode());
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
						if(sResponse != null && !sResponse.equals("")){
							tiempoAireVO = ParseXMLFile.TiempoAireLegacy(sResponse);}
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
				try{
					oracle.reversaCaja(idIdentificar,3,ip,2121,"1",ex.getMessage());
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				
				cajaRegistraMovimiento.reversaCaja(cajaRespuesta.getIdRegistro(),ip);
				
				/** REVERSO DE TRANSACCION**/
				try
				{
					int sistema = 2;
					String activarReversoTrans = "0";
					try{
						activarReversoTrans = oracle.getValorParametro(142);
					}
					catch (Exception e) {
						Logger.write("     Detail        : (Exception) " + e.getMessage());
					}
					try{
						sistema = Integer.parseInt(sSistema);
					}
					catch (NumberFormatException e) {
						Logger.write("NumberFormatException - sistema " + e.getLocalizedMessage());
					}
					if(activarReversoTrans.equals("1"))
					{
						cajaRegistraMovimiento.reversarTransaccion(sNumTransaccion, sistema, ip);
					}
				}
				catch (Exception e) {
					Logger.write("Exception - reversarTransaccion : " + e.getLocalizedMessage());
				}
				/**********/
				
				throw new ServiceException("Err [TiempoAire] : " +ex.getMessage());
			}

			if(impactaCaja.equals("1")){
				try{
					try{
						oracle.cajaConfirmaPago(idIdentificar,cajaRespuesta.getIdRegistro(), ip, tiempoAireVO.getNumeroAutorizacionAbonoTA(), secuencia,2121);
					}catch (Exception e) {
						Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
					}

					sResponse = cajaRegistraMovimiento.cajaConfirmacionPago(cajaRespuesta.getIdRegistro(), ip, tiempoAireVO.getNumeroAutorizacionAbonoTA(), secuencia);
					if(sResponse != null && !sResponse.equals(""))
						numTransaccion = ParseXMLFile.confirmaPagoCaja(sResponse);

					try{
						oracle.cajaConfirmaPagoRes(idIdentificar, numTransaccion, 2121,"","");
					}catch (Exception e) {
						Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
					}
				}catch (Exception e) {
					try{
						oracle.cajaConfirmaPagoRes(idIdentificar, numTransaccion, 2121,"1",e.getMessage());
					}catch (Exception ex) {
						Logger.write("Bitacora: No se impacto la bitacora - " + ex.getLocalizedMessage());
					}
					throw new ServiceException("Err [Caja] : No se confirmo el movimiento");
				}
				
				tiempoAireVO.setNumTransaccionCaja(numTransaccion);
			}else{
				cajaRespuesta.setErrorCode("0");
				cajaRespuesta.setIdRegistro(1234);
				TarjetasCajaVO tarjetas = new TarjetasCajaVO();
				List<TarjetasCajaVO> tarjetasList = new ArrayList<TarjetasCajaVO>();
				tarjetas.setNumAut("12345");
				tarjetas.setNumTarjeta("4567xxxxxxxx1527");
				tarjetasList.add(tarjetas);
				cajaRespuesta.setTarjetas(tarjetasList);
				tiempoAireVO.setNumTransaccionCaja("98741");
			}
			
			if(cajaRespuesta != null && cajaRespuesta.getTarjetas() != null && cajaRespuesta.getTarjetas().get(0)!=null)
				tiempoAireVO.setNumeroAutorizacionCaja(cajaRespuesta.getTarjetas().get(0).getNumAut());
			if(cajaRespuesta != null)
				tiempoAireVO.setIdRegistroCaja(cajaRespuesta.getIdRegistro());
		
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return tiempoAireVO;
	}				
}
