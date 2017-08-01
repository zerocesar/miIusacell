package mx.com.iusacell.services.miiusacell.vo.autorizador;

public class AddressVO {
	
	private String apellidos;
	private String nombres;
	private String municipio;	
	private String codigoPostal;
	
	private String colonia;
	private String estado;
	private String calle;
	private String numeroExterior;
	private String numeroInterior;	
	private String ciudad; //municipio
	private String pais;
	
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(final String apellidos) {
		this.apellidos = apellidos;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(final String nombres) {
		this.nombres = nombres;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(final String municipio) {
		this.municipio = municipio;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(final String estado) {
		this.estado = estado;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(final String colonia) {
		this.colonia = colonia;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(final String calle) {
		this.calle = calle;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(final String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getNumeroExterior() {
		return numeroExterior;
	}
	public void setNumeroExterior(final String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}
	public String getNumeroInterior() {
		return numeroInterior;
	}
	public void setNumeroInterior(final String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(final String ciudad) {
		this.ciudad = ciudad;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(final String pais) {
		this.pais = pais;
	}	
}
