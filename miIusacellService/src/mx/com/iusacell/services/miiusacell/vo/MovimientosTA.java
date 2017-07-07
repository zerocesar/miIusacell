package mx.com.iusacell.services.miiusacell.vo;

public class MovimientosTA {

	private String fecha;
	private String logID;
	private String dn;
	private String dnAbono;
	private String concepto;
	private String importe;
	private String codigoRespuestaAbono;
	private String numeroAutorizacionAbono;
	private String numeroAutorizacionCargo;	
		
	public String getFecha() {
		return fecha;
	}
	public void setFecha(final String fecha) {
		this.fecha = fecha;
	}
	public String getLogID() {
		return logID;
	}
	public void setLogID(final String logID) {
		this.logID = logID;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(final String dn) {
		this.dn = dn;
	}
	public String getDnAbono() {
		return dnAbono;
	}
	public void setDnAbono(final String dnAbono) {
		this.dnAbono = dnAbono;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(final String concepto) {
		this.concepto = concepto;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(final String importe) {
		this.importe = importe;
	}
	public String getCodigoRespuestaAbono() {
		return codigoRespuestaAbono;
	}
	public void setCodigoRespuestaAbono(final String codigoRespuestaAbono) {
		this.codigoRespuestaAbono = codigoRespuestaAbono;
	}
	public String getNumeroAutorizacionAbono() {
		return numeroAutorizacionAbono;
	}
	public void setNumeroAutorizacionAbono(final String numeroAutorizacionAbono) {
		this.numeroAutorizacionAbono = numeroAutorizacionAbono;
	}
	public String getNumeroAutorizacionCargo() {
		return numeroAutorizacionCargo;
	}
	public void setNumeroAutorizacionCargo(final String numeroAutorizacionCargo) {
		this.numeroAutorizacionCargo = numeroAutorizacionCargo;
	}	
}
