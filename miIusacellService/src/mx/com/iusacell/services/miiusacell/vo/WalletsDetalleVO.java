package mx.com.iusacell.services.miiusacell.vo;

import java.util.List;

public class WalletsDetalleVO {
	
	private List<ConsumoFechaTablaVO> detalleConsumo;
	private String isPostPagoHibrido;
	public List<ConsumoFechaTablaVO> getDetalleConsumo() {
		return detalleConsumo;
	}
	public void setDetalleConsumo(final List<ConsumoFechaTablaVO> detalleConsumo) {
		this.detalleConsumo = detalleConsumo;
	}
	public String getIsPostPagoHibrido() {
		return isPostPagoHibrido;
	}
	public void setIsPostPagoHibrido(final String isPostPagoHibrido) {
		this.isPostPagoHibrido = isPostPagoHibrido;
	}
}
