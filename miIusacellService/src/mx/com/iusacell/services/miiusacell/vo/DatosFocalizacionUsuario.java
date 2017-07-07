package mx.com.iusacell.services.miiusacell.vo;

public class DatosFocalizacionUsuario {

	private int compania;
	private DatosLogin datosUsuario;
	private ObtenerDescripcionPlanesVO1 datosPlan;
	private DatosFocalizacion datosFocalizacion;
	private DatosFocalizacionFocaVO datosFocalizacionUsuario;
	public DatosLogin getDatosUsuario() {
		return datosUsuario;
	}
	public void setDatosUsuario(final DatosLogin datosUsuario) {
		this.datosUsuario = datosUsuario;
	}
	public ObtenerDescripcionPlanesVO1 getDatosPlan() {
		return datosPlan;
	}
	public void setDatosPlan(final ObtenerDescripcionPlanesVO1 datosPlan) {
		this.datosPlan = datosPlan;
	}
	public void setCompania(final int compania) {
		this.compania = compania;
	}
	public int getCompania() {
		return compania;
	}
	public void setDatosFocalizacion(final DatosFocalizacion datosFocalizacion) {
		this.datosFocalizacion = datosFocalizacion;
	}
	public DatosFocalizacion getDatosFocalizacion() {
		return datosFocalizacion;
	}
	public void setDatosFocalizacionUsuario(final DatosFocalizacionFocaVO datosFocalizacionUsuario) {
		this.datosFocalizacionUsuario = datosFocalizacionUsuario;
	}
	public DatosFocalizacionFocaVO getDatosFocalizacionUsuario() {
		return datosFocalizacionUsuario;
	}
}
