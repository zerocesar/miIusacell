package mx.com.iusacell.services.miiusacell.vo;

import java.io.Serializable;

public class ServicioVO implements Serializable {
	
	private static final long serialVersionUID = 110420171052L;
	
	private String idServicio;
	private String descripcion;
	private String operador;
	private String tipoOferta;
	private Integer vigencia;
	private Double precio;
	private Long unidad; 
	private Integer idTipoUnidad;
	private Integer code;
	
	public String getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(final String idServicio) {
		this.idServicio = idServicio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}
	public String getOperador() {
		return operador;
	}
	public void setOperador(final String operador) {
		this.operador = operador;
	}
	public String getTipoOferta() {
		return tipoOferta;
	}
	public void setTipoOferta(final String tipoOferta) {
		this.tipoOferta = tipoOferta;
	}
	public Integer getVigencia() {
		return vigencia;
	}
	public void setVigencia(final Integer vigencia) {
		this.vigencia = vigencia;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(final Double precio) {
		this.precio = precio;
	}
	public Long getUnidad() {
		return unidad;
	}
	public void setUnidad(final Long unidad) {
		this.unidad = unidad;
	}
	public Integer getIdTipoUnidad() {
		return idTipoUnidad;
	}
	public void setIdTipoUnidad(final Integer idTipoUnidad) {
		this.idTipoUnidad = idTipoUnidad;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(final Integer code) {
		this.code = code;
	}

}
