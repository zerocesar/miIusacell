package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.SaldosFechaVO;
import mx.com.iusacell.services.miiusacell.vo.SaldosVO;
import mx.com.iusacell.services.miusacell.call.CallServiceDetalleConsumosETAK_LEGACY;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class DetalleSaldos {

	public SaldosVO flujo(final String dn, final String tipoEvento) throws ServiceException{
		SaldosVO consumos = new SaldosVO();
		String sResponse = "";

		CallServiceDetalleConsumosETAK_LEGACY detalle = new CallServiceDetalleConsumosETAK_LEGACY();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		List<SaldosFechaVO> consumosFecha = new ArrayList<SaldosFechaVO>();
		try {
			
			sResponse = focalizacion.focalizacion(dn);
			if(sResponse != null && !sResponse.equals("")){
				descripcion = ParseXMLFile.parseFocalizacion(sResponse);
			}
			if(descripcion.getFechaCorte() == null){
				descripcion.setFechaCorte("");
			}
			
			sResponse = detalle.detalleConsumosETAK_LEGACY(dn, tipoEvento, descripcion.getFechaCorte());
			if(sResponse != null && !sResponse.equals("")){
				consumosFecha = ParseXMLFile.parseSaldos(sResponse);
			}	
			
			consumosFecha = Utilerias.orderSaldosByDate(1, consumosFecha, descripcion.getFechaCorte());
			
			if(descripcion != null)
				consumos.setDetalleFocalizacion(descripcion);
			if(consumosFecha != null)
				consumos.setDetalleConsumo(consumosFecha);
			consumos.setTotalDatos("1000");
			consumos.setTotalMensaje("1000");
			consumos.setTotalTA("1000");
			consumos.setTotalVoz("1000");
		} catch (Exception e) {
			Logger.write("       Detail        : (Exception) " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		
		return consumos;
	}
}
