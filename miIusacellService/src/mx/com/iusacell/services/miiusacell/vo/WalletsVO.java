package mx.com.iusacell.services.miiusacell.vo;

public class WalletsVO {
	private int id;
	private int unidad;
	private int cantidad;
	private boolean vigencia;
	
	public int getId() {
		return id;
	}
	public void setId(final int id) {
		this.id = id;
	}
	public int getUnidad() {
		return unidad;
	}
	public void setUnidad(final int unidad) {
		this.unidad = unidad;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(final int cantidad) {
		this.cantidad = cantidad;
	}
	public boolean isVigencia() {
		return vigencia;
	}
	public void setVigencia(final boolean vigencia) {
		this.vigencia = vigencia;
	}
}
