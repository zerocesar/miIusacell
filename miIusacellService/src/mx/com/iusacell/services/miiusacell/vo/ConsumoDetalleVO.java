package mx.com.iusacell.services.miiusacell.vo;

public class ConsumoDetalleVO {

	private String horaEvento;
	private String numDestino;
	private String numOrigen;
	private String montoFree;
	private String montoReal;
	private String operadorDestino;
	private String operadorOrigen;
	private String durMinRedn;
	private String descTipoTrafico;
	private String saldoAntes;
	private String saldoDespues;
	private String totalNavegacion;
	private String ciudadDestino;
	private String cdescWallet;
	
	public String getHoraEvento() {
		return horaEvento;
	}
	public void setHoraEvento(final String horaEvento) {
		this.horaEvento = horaEvento;
	}
	public String getNumDestino() {
		return numDestino;
	}
	public void setNumDestino(final String numDestino) {
		this.numDestino = numDestino;
	}
	public String getNumOrigen() {
		return numOrigen;
	}
	public void setNumOrigen(final String numOrigen) {
		this.numOrigen = numOrigen;
	}
	public String getOperadorDestino() {
		return operadorDestino;
	}
	public void setOperadorDestino(final String operadorDestino) {
		this.operadorDestino = operadorDestino;
	}
	public String getOperadorOrigen() {
		return operadorOrigen;
	}
	public void setOperadorOrigen(final String operadorOrigen) {
		this.operadorOrigen = operadorOrigen;
	}
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
	public void setDurMinRedn(final String durMinRedn) {
		this.durMinRedn = durMinRedn;
	}
	public String getDurMinRedn() {
		return durMinRedn;
	}
	public void setDescTipoTrafico(final String descTipoTrafico) {
		this.descTipoTrafico = descTipoTrafico;
	}
	public String getDescTipoTrafico() {
		return descTipoTrafico;
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
	public String getCiudadDestino() {
		return ciudadDestino;
	}
	public void setCiudadDestino(final String ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}
	public void setTotalNavegacion(final String totalNavegacion) {
		this.totalNavegacion = totalNavegacion;
	}
	public String getTotalNavegacion() {
		return totalNavegacion;
	}
	public void setCdescWallet(final String cdescWallet) {
		this.cdescWallet = cdescWallet;
	}
	public String getCdescWallet() {
		return cdescWallet;
	}	
}
