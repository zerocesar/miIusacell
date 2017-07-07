package mx.com.iusacell.services.miiusacell.vo;

public class DatosFocalizacionUsuarioExtendido {

	private int compania;
	private DatosLogin datosUsuario;
	private ObtenerDescripcionPlanesVO1 datosPlan;
	private DatosFocalizacionExtendido datosFocalizacion;
	private DatosFocalizacionVO datosFocalizacionUsuario;
	private ConfiguracionXUsuarioVO configuracion;
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
	public void setCompania(int compania) {
		this.compania = compania;
	}
	public int getCompania() {
		return compania;
	}
	public void setDatosFocalizacionUsuario(final DatosFocalizacionVO datosFocalizacionUsuario) {
		this.datosFocalizacionUsuario = datosFocalizacionUsuario;
	}
	public DatosFocalizacionVO getDatosFocalizacionUsuario() {
		return datosFocalizacionUsuario;
	}
	public void setConfiguracion(final ConfiguracionXUsuarioVO configuracion) {
		this.configuracion = configuracion;
	}
	public ConfiguracionXUsuarioVO getConfiguracion() {
		return configuracion;
	}
	public void setDatosFocalizacion(final DatosFocalizacionExtendido datosFocalizacion) {
		this.datosFocalizacion = datosFocalizacion;
	}
	public DatosFocalizacionExtendido getDatosFocalizacion() {
		return datosFocalizacion;
	}
}
