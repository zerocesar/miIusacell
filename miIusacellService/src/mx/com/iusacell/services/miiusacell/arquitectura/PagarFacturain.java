package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.CardVO;
import mx.com.iusacell.services.miiusacell.vo.PagoFacturaResponseVO;

public interface PagarFacturain {

	public PagoFacturaResponseVO flujo(final String numTx, final String user, final String pass, 
			final String dn, final CardVO tarjeta, final int tipoPlataforma, 
			final int compania, final int sistemaOrigen, final int dispositivo, 
			final String password, final String token) throws ServiceException;
	public PagoFacturaResponseVO flujoFingerPrint(final String numTx, final String user, final String pass, 
			final String dn, final CardVO tarjeta, final int tipoPlataforma, final int compania, final int sistemaOrigen, 
			final int dispositivo, final String password, final String ip, final String email, final String fingerPrint, 
			final boolean isFlowATT, final String token) throws ServiceException;
	
}
