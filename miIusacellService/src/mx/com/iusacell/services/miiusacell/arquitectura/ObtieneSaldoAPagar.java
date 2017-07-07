package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.ConsultaCuentaResponseVO;
import mx.com.iusacell.services.miusacell.call.CallServicePayments;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class ObtieneSaldoAPagar {
	
	public static String flujo(final String dn) throws ServiceException
	{
		String response = "";
		String sResponse = "";
		CallServicePayments payment = new CallServicePayments();
		ConsultaCuentaResponseVO paymentResponse = new ConsultaCuentaResponseVO();
		
		try
		{
			sResponse = payment.consultaCuenta(dn);
			if(sResponse != null && !sResponse.equals("")){
				paymentResponse = ParseXMLFile.parseConsultaCuenta(sResponse);
			}
			if(paymentResponse != null){
				response = paymentResponse.getSaldo();
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
}
