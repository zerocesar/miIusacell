package mx.com.iusacell.services.miiusacell.vo;

public class Addons {
	
	private String idServicio;	
	private int cantidadVigencia;
	private int unidadVigencia;	
	private int origen;
	private int operacion;
	
	public String getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(final String idServicio) {
		this.idServicio = idServicio;
	}
	public int getCantidadVigencia() {
		return cantidadVigencia;
	}
	public void setCantidadVigencia(final int cantidadVigencia) {
		this.cantidadVigencia = cantidadVigencia;
	}
	public int getUnidadVigencia() {
		return unidadVigencia;
	}
	public void setUnidadVigencia(final int unidadVigencia) {
		this.unidadVigencia = unidadVigencia;
	}
	public int getOrigen() {
		return origen;
	}
	public void setOrigen(final int origen) {
		this.origen = origen;
	}
	public int getOperacion() {
		return operacion;
	}
	public void setOperacion(final int operacion) {
		this.operacion = operacion;
	}
			
}
