package mx.com.iusacell.services.miiusacell.vo;

import java.util.List;

public class ConsumosTablaVO {

	private String totalTA;
	private String totalDatos;
	private List<ConsumoFechaTablaVO> detalleConsumo;
	private String isPostPagoHibrido;
	public String getTotalTA() {
		return totalTA;
	}
	public void setTotalTA(final String totalTA) {
		this.totalTA = totalTA;
	}
	
	public String getTotalDatos() {
		return totalDatos;
	}
	public void setTotalDatos(final String totalDatos) {
		this.totalDatos = totalDatos;
	}
	public void setIsPostPagoHibrido(final String isPostPagoHibrido) {
		this.isPostPagoHibrido = isPostPagoHibrido;
	}
	public String getIsPostPagoHibrido() {
		return isPostPagoHibrido;
	}
	public void setDetalleConsumo(final List<ConsumoFechaTablaVO> detalleConsumo) {
		this.detalleConsumo = detalleConsumo;
	}
	public List<ConsumoFechaTablaVO> getDetalleConsumo() {
		return detalleConsumo;
	}
}
