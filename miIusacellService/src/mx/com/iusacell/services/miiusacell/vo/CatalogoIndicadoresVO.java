package mx.com.iusacell.services.miiusacell.vo;

import java.util.List;

public class CatalogoIndicadoresVO {

	private List<IndicadoresVO> datos;
	private int reporteID;
	private String titulo;

	public int getReporteID() {
		return reporteID;
	}
	public void setReporteID(final int reporteID) {
		this.reporteID = reporteID;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}
	public void setDatos(final List<IndicadoresVO> datos) {
		this.datos = datos;
	}
	public List<IndicadoresVO> getDatos() {
		return datos;
	}
}
