package mx.com.iusacell.services.miiusacell.arquitectura;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.CardVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaCuentaResponseVO;
import mx.com.iusacell.services.miiusacell.vo.PagoFacturaResponseVO;
import mx.com.iusacell.services.miiusacell.vo.PagoVO;
import mx.com.iusacell.services.miiusacell.vo.ValidarErrorTarjetaVO;
import mx.com.iusacell.services.miusacell.call.CallServiceCardBlackList;
import mx.com.iusacell.services.miusacell.call.CallServicePayments;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public class PagarFactura implements PagarFacturain {
	
	public PagoFacturaResponseVO flujo(final String numTx, final String user, final String pass, final String dn, final CardVO tarjeta, final int tipoPlataforma, final int compania, final int sistemaOrigen, final int dispositivo, final String password, final String token) throws ServiceException
	{
		String sResponse = "";
		CallServicePayments payment = new CallServicePayments();
		OracleProcedures oracle = new OracleProcedures();
		ConsultaCuentaResponseVO paymentResponse = new ConsultaCuentaResponseVO();
		ValidarErrorTarjetaVO validaErr = new ValidarErrorTarjetaVO();
		PagoFacturaResponseVO response = new PagoFacturaResponseVO();
		PagoVO pago = new PagoVO();
		Calendar fecha = Calendar.getInstance();
		
		CallServiceCardBlackList validaTDC = new CallServiceCardBlackList();
		boolean tdcBlacklList= false;
		String validarCardBlacklist="";
//		@SuppressWarnings("unused")
//		int transXTarjeta;

		try
		{																
			try
			{	
				validarCardBlacklist = oracle.getValorParametro(164);
				if (validarCardBlacklist.equals("1")){
					tdcBlacklList=validaTDC.consultaCardBlackList(tarjeta.getCardNumber());
					if (tdcBlacklList){
						try{
							String userBD = (dn != null) ? dn.substring(2) : "222222";
							oracle.setCardBlackList(tarjeta.getCardNumber(), "Pagar factura",String.valueOf(tdcBlacklList),userBD);							
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
//				transXTarjeta=oracle.getTransXTarjeta(tarjeta.getCardNumber());
				oracle.getTransXTarjeta(tarjeta.getCardNumber());
			}catch(Exception e){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("ServiceException [TransXTarjeta] :"+ e.getLocalizedMessage());	
				}
			}
			
			TarjetaSemaforosSaveCustomerApplyCharge.semaphoresSaveCustomerApplyCharge(dn, tarjeta.getCardNumber(), tarjeta.getAmount());
			
			sResponse = payment.consultaCuenta(dn);
			if(sResponse != null && !sResponse.equals("")){
				paymentResponse = ParseXMLFile.parseConsultaCuenta(sResponse);
			}
			if(paymentResponse != null){
				pago.setNumReferencia(paymentResponse.getReferencia());
				pago.setUsuarioAdn("0");
				pago.setTienda("0");
				pago.setMonto(tarjeta.getAmount());
				pago.setOrigen("MI");
				pago.setFolioElk("MI"+numTx);
				pago.setNumTransaccion(numTx);
				pago.setCanal("27");
				pago.setFormaPago("CCH");
				pago.setCuenta(paymentResponse.getNumeroDeCuenta());
				pago.setFecha(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fecha.getTime()));
				//bitacora
				try{
					oracle.pagoFacturaLlamadaMetodo(numTx, tarjeta, pago);
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				sResponse = payment.pagoReferenciadoBmx(pago, tarjeta, tipoPlataforma);
				if(sResponse != null && !sResponse.equals("")){
					response = ParseXMLFile.parsepagoReferenciadoBmx(sResponse);
				}
			}
		}
		catch (Exception ex)
		{
			int codigo = -1;
			if(ex != null && ex.getMessage() != null){
				String error = ex.getMessage();
				codigo = PagarFactura.getCodigoError(error);
				try{
					validaErr = oracle.validarTarjeta(codigo);
				}catch (Exception e) {
					validaErr = new ValidarErrorTarjetaVO();
				}
			}
			if(validaErr != null && validaErr.getDescEspaniol() != null && !validaErr.getDescEspaniol().equals("") && codigo != -1)
				throw new ServiceException("[Caja]: " + validaErr.getDescEspaniol());
			else
				throw new ServiceException(ex.getLocalizedMessage());
		}
		return response;
	}
	
	public PagoFacturaResponseVO flujoFingerPrint(final String numTx, final String user, final String pass, final String dn, final CardVO tarjeta, 
			final int tipoPlataforma, final int compania, final int sistemaOrigen, final int dispositivo, 
			final String password, final String ip, final String email, final String fingerPrint,
			final boolean isFlowATT, final String token) 
	throws ServiceException
	{
		String sResponse = "";
		CallServicePayments payment = new CallServicePayments();
		OracleProcedures oracle = new OracleProcedures();
		ConsultaCuentaResponseVO paymentResponse = new ConsultaCuentaResponseVO();
		ValidarErrorTarjetaVO validaErr = new ValidarErrorTarjetaVO();
		PagoFacturaResponseVO response = new PagoFacturaResponseVO();
		PagoVO pago = new PagoVO();
		Calendar fecha = Calendar.getInstance();
		
		CallServiceCardBlackList validaTDC = new CallServiceCardBlackList();
		boolean tdcBlacklList= false;
		String validarCardBlacklist="";
//		@SuppressWarnings("unused")
//		int transXTarjeta;

		try
		{													
			
			try
			{	
				validarCardBlacklist = oracle.getValorParametro(164);
				if (validarCardBlacklist.equals("1")){
					tdcBlacklList=validaTDC.consultaCardBlackList(tarjeta.getCardNumber());
					if (tdcBlacklList){
						try{
							String userBD = (dn != null) ? dn.substring(2) : "222222";
							oracle.setCardBlackList(tarjeta.getCardNumber(), "Pagar factura",String.valueOf(tdcBlacklList),userBD);							
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
//				transXTarjeta=oracle.getTransXTarjeta(tarjeta.getCardNumber());
				oracle.getTransXTarjeta(tarjeta.getCardNumber());
			}catch(Exception e){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("ServiceException [TransXTarjeta] :"+ e.getLocalizedMessage());	
				}
			}
			
			TarjetaSemaforosSaveCustomerApplyCharge.semaphoresSaveCustomerApplyCharge(dn, tarjeta.getCardNumber(), tarjeta.getAmount());
			
			sResponse = payment.consultaCuenta(dn);
			if(sResponse != null && !sResponse.equals("")){
				paymentResponse = ParseXMLFile.parseConsultaCuenta(sResponse);
			}
			if(paymentResponse != null){
				pago.setNumReferencia(paymentResponse.getReferencia());
				pago.setUsuarioAdn("0");
				pago.setTienda("0");
				pago.setMonto(tarjeta.getAmount());
				pago.setOrigen("MI");
				pago.setFolioElk("MI"+numTx);
				pago.setNumTransaccion(numTx);
				pago.setCanal("27");
				pago.setFormaPago("CCH");
				pago.setCuenta(paymentResponse.getNumeroDeCuenta());
				pago.setFecha(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fecha.getTime()));
				//bitacora
				try{
					oracle.pagoFacturaLlamadaMetodo(numTx, tarjeta, pago);
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				String valorVersion;
				try{					
					valorVersion = oracle.getValorParametro(194); 					
				}catch (ServiceException e) {					
					valorVersion = "1";					
				}
				sResponse = payment.pagoReferenciadoBmxFingPrint(pago, tarjeta, fingerPrint, valorVersion);
				if(sResponse != null && !sResponse.equals("")){
					response = ParseXMLFile.parsepagoReferenciadoBmx(sResponse);
				}
			}
		}
		catch (Exception ex)
		{
			int codigo = -1;
			if(ex != null && ex.getMessage() != null){
				String error = ex.getMessage();
				codigo = PagarFactura.getCodigoError(error);
				try{
					validaErr = oracle.validarTarjeta(codigo);
				}catch (Exception e) {
					validaErr = new ValidarErrorTarjetaVO();
				}
			}
			if(validaErr != null && validaErr.getDescEspaniol() != null && !validaErr.getDescEspaniol().equals("") && codigo != -1)
				throw new ServiceException("[Caja]: " + validaErr.getDescEspaniol());
			else
				throw new ServiceException(ex.getLocalizedMessage());
		}
		return response;
	}
	
	private static int getCodigoError(String error){
		int codigo = -1;
		codigo = error.toLowerCase().contains("approved") == true ? 0 : -1;
		if(codigo == -1)
			codigo = error.toLowerCase().contains("processed") == true ? 1 : -1;
		if(codigo == -1)
			codigo = error.toLowerCase().contains("declined") == true ? 2 : -1;
		if(codigo == -1)
			codigo = error.toLowerCase().contains("expired") == true ? 4 : -1;
		if(codigo == -1)
			codigo = error.toLowerCase().contains("funds") == true ? 5 : -1;
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
		
		return codigo;
	}
}
