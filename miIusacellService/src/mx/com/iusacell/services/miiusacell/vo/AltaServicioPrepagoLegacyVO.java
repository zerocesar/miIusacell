package mx.com.iusacell.services.miiusacell.vo;

public class AltaServicioPrepagoLegacyVO {
	private String serviciosId;
	private String servicioOrigen;
	private String vigenciasUnidad;
	private String vigenciasCantidad;
	private String operacion;
	public String getServiciosId() {
		return serviciosId;
	}
	public void setServiciosId(final String serviciosId) {
		this.serviciosId = serviciosId;
	}
	public String getServicioOrigen() {
		return servicioOrigen;
	}
	public void setServicioOrigen(final String servicioOrigen) {
		this.servicioOrigen = servicioOrigen;
	}
	public String getVigenciasUnidad() {
		return vigenciasUnidad;
	}
	public void setVigenciasUnidad(final String vigenciasUnidad) {
		this.vigenciasUnidad = vigenciasUnidad;
	}
	public String getVigenciasCantidad() {
		return vigenciasCantidad;
	}
	public void setVigenciasCantidad(final String vigenciasCantidad) {
		this.vigenciasCantidad = vigenciasCantidad;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(final String operacion) {
		this.operacion = operacion;
	}
}
