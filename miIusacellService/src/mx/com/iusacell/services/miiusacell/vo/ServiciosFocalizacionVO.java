package mx.com.iusacell.services.miiusacell.vo;

public class ServiciosFocalizacionVO {
//	TODO: German Se agrega la tag para el Servicio PRIP en Prepago
	private String idServicioPrip;
	private String nombreServicioPrip;
	private String descServicioPrip;
	private String fechaFinServPrip;
	private String costoServPrip;
	private String version;
	private boolean flagServPRIP;
	
	public String getIdServicioPrip() {
		return idServicioPrip;
	}
	public void setIdServicioPrip(final String idServicioPrip) {
		this.idServicioPrip = idServicioPrip;
	}
	public String getNombreServicioPrip() {
		return nombreServicioPrip;
	}
	public void setNombreServicioPrip(final String nombreServicioPrip) {
		this.nombreServicioPrip = nombreServicioPrip;
	}
	public String getDescServicioPrip() {
		return descServicioPrip;
	}
	public void setDescServicioPrip(final String descServicioPrip) {
		this.descServicioPrip = descServicioPrip;
	}
	public String getFechaFinServPrip() {
		return fechaFinServPrip;
	}
	public void setFechaFinServPrip(final String fechaFinServPrip) {
		this.fechaFinServPrip = fechaFinServPrip;
	}
	public void setFlagServPRIP(final boolean flagServPRIP) {
		this.flagServPRIP = flagServPRIP;
	}
	public boolean getFlagServPRIP() {
		return flagServPRIP;
	}
	public String getCostoServPrip() {
		return costoServPrip;
	}
	public void setCostoServPrip(final String costoServPrip) {
		this.costoServPrip = costoServPrip;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(final String version) {
		this.version = version;
	}
}
