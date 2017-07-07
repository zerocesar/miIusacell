package mx.com.iusacell.services.miiusacell.vo;

import java.util.List;

public class ConsumosTotalesResponseVO {
	private String totalTA;
	private String totalVoz;
	private String totalCom;
	private String totalMontoRealLlamadas;
	private String totalDatos;
	private String totalDatosCosto;
	private String totalMensaje;
	private DetalleFocalizacionVO detalleFocalizacion;
	private List<ServiciosAdicionalesVO> detalleServiciosAdicionales;
	private String descripcionPlan;
	private ObtenerDescripcionPlanesVO1 datosPlanes;
	public String getTotalTA() {
		return totalTA;
	}
	public void setTotalTA(final String totalTA) {
		this.totalTA = totalTA;
	}
	public String getTotalVoz() {
		return totalVoz;
	}
	public void setTotalVoz(final String totalVoz) {
		this.totalVoz = totalVoz;
	}
	public String getTotalCom() {
		return totalCom;
	}
	public void setTotalCom(final String totalCom) {
		this.totalCom = totalCom;
	}
	public String getTotalMontoRealLlamadas() {
		return totalMontoRealLlamadas;
	}
	public void setTotalMontoRealLlamadas(final String totalMontoRealLlamadas) {
		this.totalMontoRealLlamadas = totalMontoRealLlamadas;
	}
	public String getTotalDatos() {
		return totalDatos;
	}
	public void setTotalDatos(final String totalDatos) {
		this.totalDatos = totalDatos;
	}
	public String getTotalDatosCosto() {
		return totalDatosCosto;
	}
	public void setTotalDatosCosto(final String totalDatosCosto) {
		this.totalDatosCosto = totalDatosCosto;
	}
	public String getTotalMensaje() {
		return totalMensaje;
	}
	public void setTotalMensaje(final String totalMensaje) {
		this.totalMensaje = totalMensaje;
	}
	public DetalleFocalizacionVO getDetalleFocalizacion() {
		return detalleFocalizacion;
	}
	public void setDetalleFocalizacion(final DetalleFocalizacionVO detalleFocalizacion) {
		this.detalleFocalizacion = detalleFocalizacion;
	}
	public List<ServiciosAdicionalesVO> getDetalleServiciosAdicionales() {
		return detalleServiciosAdicionales;
	}
	public void setDetalleServiciosAdicionales(
	        final List<ServiciosAdicionalesVO> detalleServiciosAdicionales) {
		this.detalleServiciosAdicionales = detalleServiciosAdicionales;
	}
	public String getDescripcionPlan() {
		return descripcionPlan;
	}
	public void setDescripcionPlan(final String descripcionPlan) {
		this.descripcionPlan = descripcionPlan;
	}
	public ObtenerDescripcionPlanesVO1 getDatosPlanes() {
		return datosPlanes;
	}
	public void setDatosPlanes(final ObtenerDescripcionPlanesVO1 datosPlanes) {
		this.datosPlanes = datosPlanes;
	}
}
