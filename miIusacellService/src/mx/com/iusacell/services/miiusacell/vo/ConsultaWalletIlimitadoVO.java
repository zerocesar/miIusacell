package mx.com.iusacell.services.miiusacell.vo;

public class ConsultaWalletIlimitadoVO {
	
	private String idWallet;
	private String wallet;
	private String tipoWallet;
	private String prioridad;
	private String fechaFin;
	private String cantidad;
	
	public String getIdWallet() {
		return idWallet;
	}
	public void setIdWallet(final String idWallet) {
		this.idWallet = idWallet;
	}
	public String getWallet() {
		return wallet;
	}
	public void setWallet(final String wallet) {
		this.wallet = wallet;
	}

	public String getTipoWallet() {
		return tipoWallet;
	}
	public void setTipoWallet(final String tipoWallet) {
		this.tipoWallet = tipoWallet;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(final String prioridad) {
		this.prioridad = prioridad;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(final String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(final String cantidad) {
		this.cantidad = cantidad;
	}
}
