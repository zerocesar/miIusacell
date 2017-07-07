package mx.com.iusacell.services.miiusacell.vo;

public class Mensajes {
	private String totalMensajes;
	private String totalCostoMensajes;
	private String MensajesIncluidos;
	private String mensajesDeTextoDeRegalo;
	private String mensajesExtras;
	public String getTotalMensajes() {
		return totalMensajes;
	}
	public void setTotalMensajes(final String totalMensajes) {
		this.totalMensajes = totalMensajes;
	}
	public String getTotalCostoMensajes() {
		return totalCostoMensajes;
	}
	public void setTotalCostoMensajes(final String totalCostoMensajes) {
		this.totalCostoMensajes = totalCostoMensajes;
	}
	public String getMensajesIncluidos() {
		return MensajesIncluidos;
	}
	public void setMensajesIncluidos(final String mensajesIncluidos) {
		MensajesIncluidos = mensajesIncluidos;
	}
	public void setMensajesDeTextoDeRegalo(final String mensajesDeTextoDeRegalo) {
		this.mensajesDeTextoDeRegalo = mensajesDeTextoDeRegalo;
	}
	public String getMensajesDeTextoDeRegalo() {
		return mensajesDeTextoDeRegalo;
	}
	public void setMensajesExtras(final String mensajesExtras) {
		this.mensajesExtras = mensajesExtras;
	}
	public String getMensajesExtras() {
		return mensajesExtras;
	}
}
