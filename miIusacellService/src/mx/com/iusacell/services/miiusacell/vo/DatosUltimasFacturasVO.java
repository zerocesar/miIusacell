package mx.com.iusacell.services.miiusacell.vo;

public class DatosUltimasFacturasVO {
	private String fechaCorte;
	private String fechaVencimiento;
	private String saldoVencido;
	private String totalPagar;
	private String link;
	public String getFechaCorte() {
		return fechaCorte;
	}
	public void setFechaCorte(final String fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(final String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public String getSaldoVencido() {
		return saldoVencido;
	}
	public void setSaldoVencido(final String saldoVencido) {
		this.saldoVencido = saldoVencido;
	}
	public String getTotalPagar() {
		return totalPagar;
	}
	public void setTotalPagar(final String totalPagar) {
		this.totalPagar = totalPagar;
	}
	public void setLink(final String link) {
		this.link = link;
	}
	public String getLink() {
		return link;
	}
}
