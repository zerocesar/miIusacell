package mx.com.iusacell.services.miiusacell.vo;

import java.io.Serializable;

public class DomicilioVO implements Serializable
{
	private static final long serialVersionUID = 20170724174900L;
	
	private int    domicilioID;
	private int    tipoDomicilio;
	private String categoria;
	private String calle;
	private String numeroExterior;
	private String numeroInterior;
	private String colonia;
	private String estado;
	private String ciudad;
	private String pais;
	
	public int getDomicilioID() {
		return domicilioID;
	}
	public void setDomicilioID(final int domicilioID) {
		this.domicilioID = domicilioID;
	}
	public int getTipoDomicilio() {
		return tipoDomicilio;
	}
	public void setTipoDomicilio(final int tipoDomicilio) {
		this.tipoDomicilio = tipoDomicilio;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(final String categoria) {
		this.categoria = categoria;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(final String calle) {
		this.calle = calle;
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
	public String getColonia() {
		return colonia;
	}
	public void setColonia(final String colonia) {
		this.colonia = colonia;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(final String estado) {
		this.estado = estado;
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
