package mx.com.iusacell.services.miiusacell.vo;

public class SaldosDetalleVO {

	private String montoFree;
	private String montoReal;
	private String saldoAntes;
	private String saldoDespues;
	private String durMinRedn;
	
	public String getMontoFree() {
		return montoFree;
	}
	public void setMontoFree(final String montoFree) {
		this.montoFree = montoFree;
	}
	public String getMontoReal() {
		return montoReal;
	}
	public void setMontoReal(final String montoReal) {
		this.montoReal = montoReal;
	}
	public String getSaldoAntes() {
		return saldoAntes;
	}
	public void setSaldoAntes(final String saldoAntes) {
		this.saldoAntes = saldoAntes;
	}
	public String getSaldoDespues() {
		return saldoDespues;
	}
	public void setSaldoDespues(final String saldoDespues) {
		this.saldoDespues = saldoDespues;
	}
	public void setDurMinRedn(final String durMinRedn) {
		this.durMinRedn = durMinRedn;
	}
	public String getDurMinRedn() {
		return durMinRedn;
	}
}
