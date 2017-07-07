package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.TarjetasFrecuentesVO;
import mx.com.iusacell.services.miiusacell.vo.InfoAdicionalTarjetaSemaforosVO;
import mx.com.iusacell.services.miusacell.call.CallServiceSemaphore;

public class TarjetaSemaforosSaveCustomerApplyCharge {

	public InfoAdicionalTarjetaSemaforosVO infoAdicionalTarjetaSemaforos(final String dn, final String numTarjeta) throws ServiceException{
		InfoAdicionalTarjetaSemaforosVO respuesta=new InfoAdicionalTarjetaSemaforosVO();
		OracleProcedures oracle = new OracleProcedures();
		List<TarjetasFrecuentesVO> tarjetasFrecuentes = oracle.obtieneTarjetasFrecuente(dn);
		
		respuesta.setIdBusiness(oracle.getValorParametro(168));
		
		for (TarjetasFrecuentesVO tarjetasFrecuentesVO : tarjetasFrecuentes) {
			if(tarjetasFrecuentesVO.getNumeroTarjeta().equals(numTarjeta)){
				respuesta.setPostalCode(tarjetasFrecuentesVO.getCp());
				respuesta.setCustomerName(tarjetasFrecuentesVO.getNombre());
				respuesta.setCustomerLastName(tarjetasFrecuentesVO.getApPaterno());
				respuesta.setCustomerMaidenName(tarjetasFrecuentesVO.getApMaterno());
				respuesta.setExpirationDate(tarjetasFrecuentesVO.getMesVencimiento()+"/"+tarjetasFrecuentesVO.getAnioVencimiento());
				respuesta.setCardHolder(tarjetasFrecuentesVO.getNombre()+" "+ tarjetasFrecuentesVO.getApPaterno()+" "+tarjetasFrecuentesVO.getApMaterno());
			}
		}
		
		return respuesta;
	}
	
	public InfoAdicionalTarjetaSemaforosVO infoAdicionalTarjetaBD(final String dn, final String numTarjeta )throws ServiceException{
		
		InfoAdicionalTarjetaSemaforosVO infoTarjeta=infoAdicionalTarjetaSemaforos(dn, numTarjeta);
		
		if (infoTarjeta==null||infoTarjeta.getCustomerName()==null || infoTarjeta.getCustomerName().equals("")){
			throw new ServiceException("[ctrl] No se encontró el registro de la Tarjeta en la BD MiIusacell");
		}
		
		return infoTarjeta ;
	}
	
	public static void semaphoresSaveCustomerApplyCharge(final String dn, final String numTarjeta, final String importe) throws ServiceException{
		String validarSemaphoreService="0";
		String insertarTarjetaBDSemaforos="0";
		OracleProcedures oracle = new OracleProcedures();
		try
		{
			validarSemaphoreService=oracle.getValorParametro(167);
			insertarTarjetaBDSemaforos=oracle.getValorParametro(175);
		}catch(Exception e){
			throw new ServiceException("[ctrl] :"+e.getLocalizedMessage());
		}
		
		CallServiceSemaphore semaforoTDC =new CallServiceSemaphore();
		
		if (validarSemaphoreService.equals("1"))
		{
			InfoAdicionalTarjetaSemaforosVO infoTarjeta=new InfoAdicionalTarjetaSemaforosVO();
			TarjetaSemaforosSaveCustomerApplyCharge tdc=new TarjetaSemaforosSaveCustomerApplyCharge();
			infoTarjeta=tdc.infoAdicionalTarjetaBD(dn, numTarjeta);
			
			if (insertarTarjetaBDSemaforos.equals("1")){
				try{
					semaforoTDC.SemaphoreSaveCustomerInfo(dn, infoTarjeta.getCustomerName(),infoTarjeta.getCustomerLastName(),infoTarjeta.getCustomerMaidenName(), 
						numTarjeta, infoTarjeta.getCardHolder(),infoTarjeta.getExpirationDate(), infoTarjeta.getPostalCode());
				}catch(ServiceException e){
					if(e != null && e.getLocalizedMessage() != null){
						if(e.getLocalizedMessage().contains("registrado con la tarjeta indicada")){
							Logger.write("[ctrl] " + e.getLocalizedMessage());
						}
						else {
							throw new ServiceException("[ctrl] "+e.getLocalizedMessage());
						}
					}
				}
			}
			semaforoTDC.SemaphoreApplyCharge(dn,numTarjeta, importe.toString() , infoTarjeta.getPostalCode(),infoTarjeta.getIdBusiness());
			infoTarjeta=null;
			tdc=null;
		}
		semaforoTDC=null;
		validarSemaphoreService=null;
		insertarTarjetaBDSemaforos=null;
		oracle=null;
		
	}
	
}
