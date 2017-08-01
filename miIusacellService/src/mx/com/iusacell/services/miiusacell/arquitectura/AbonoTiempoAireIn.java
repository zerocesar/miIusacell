package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.AbonoTiempoAireVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.AddressVO;

public interface AbonoTiempoAireIn {
	
	public AbonoTiempoAireVO flujo(final String token,final String dn,final String dnParaAbono,final int anioExpira, 
	        final String cdgSeguridad,final String concepto,final  Double importe,final int mesExpira,final String numTarjeta, 
	        String tipoTarjeta,final String ip,final Long secuencia,final String password, 
	        final int tipoPlataforma,final String idIdentificar) throws ServiceException;
	
	public AbonoTiempoAireVO flujoFingerPrint(final String token,final String dn,final String dnParaAbono,final int anioExpira, 
	        final String cdgSeguridad,final String concepto,final  Double importe,final int mesExpira,final String numTarjeta, 
	        String tipoTarjeta,final String ip,final Long secuencia,final String password, 
	        final int tipoPlataforma,final String idIdentificar, final String email, final String fingerPrint, final AddressVO address, final boolean isFlowATT) throws ServiceException;
	
	
}
