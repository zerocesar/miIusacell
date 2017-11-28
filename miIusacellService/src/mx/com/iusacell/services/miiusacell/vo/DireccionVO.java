package mx.com.iusacell.services.miiusacell.vo;

import java.util.List;

public class DireccionVO {
	private String estado;
	private String idEstado;
	private String idMunicipio;
	private String idPais;
	private String municipio;
	private String pais;
	private List<ColoniaVO> colonias;
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	public String getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	public String getIdPais() {
		return idPais;
	}
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public List<ColoniaVO> getColonias() {
		return colonias;
	}
	public void setColonias(List<ColoniaVO> colonias) {
		this.colonias = colonias;
	}
}
