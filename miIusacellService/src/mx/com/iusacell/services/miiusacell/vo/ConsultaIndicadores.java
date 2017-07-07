package mx.com.iusacell.services.miiusacell.vo;

public class ConsultaIndicadores {

	private int reporteId;
	private int plataforma;
	private String unidadNegocio; 
    private String fechaIni;
	private String fechaFin;
	private int tipolinea;
	
	public int getReporteId() {
		return reporteId;
	}
	public void setReporteId(final int reporteId) {
		this.reporteId = reporteId;
	}
	public int getPlataforma() {
		return plataforma;
	}
	public void setPlataforma(final int plataforma) {
		this.plataforma = plataforma;
	}
	public String getUnidadNegocio() {
		return unidadNegocio;
	}
	public void setUnidadNegocio(final String unidadNegocio) {
		this.unidadNegocio = unidadNegocio;
	}
	public String getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(final String fechaIni) {
		this.fechaIni = fechaIni;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(final String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public void setTipolinea(final int tipolinea) {
		this.tipolinea = tipolinea;
	}
	public int getTipolinea() {
		return tipolinea;
	}		
}
