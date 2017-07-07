package mx.com.iusacell.services.miiusacell.vo;

import java.util.List;

public class ConsumoFechaVO {

	private String fecha;
	private List<ConsumoDetalleVO> detalle;
	public String getFecha() {
		return fecha;
	}
	public void setFecha(final String fecha) {
		this.fecha = fecha;
	}
	public List<ConsumoDetalleVO> getDetalle() {
		return detalle;
	}
	public void setDetalle(final List<ConsumoDetalleVO> detalle) {
		this.detalle = detalle;
	}
}
