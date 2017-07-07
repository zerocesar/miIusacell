package mx.com.iusacell.services.miiusacell.vo;

public class DetalleWalletsVO {
	private String tiempoAireDeRegalo;
	private String tiempoAireTotal;
	private String minutosDeRegalo;
	private String mensajesDeTexto;
	private String navegacion;
	public String getTiempoAireDeRegalo() {
		return tiempoAireDeRegalo;
	}
	public void setTiempoAireDeRegalo(final String tiempoAireDeRegalo) {
		this.tiempoAireDeRegalo = tiempoAireDeRegalo;
	}
	public String getMinutosDeRegalo() {
		return minutosDeRegalo;
	}
	public void setMinutosDeRegalo(final String minutosDeRegalo) {
		this.minutosDeRegalo = minutosDeRegalo;
	}
	public String getMensajesDeTexto() {
		return mensajesDeTexto;
	}
	public void setMensajesDeTexto(final String mensajesDeTexto) {
		this.mensajesDeTexto = mensajesDeTexto;
	}
	public void setNavegacion(final String navegacion) {
		this.navegacion = navegacion;
	}
	public String getNavegacion() {
		return navegacion;
	}
	public void setTiempoAireTotal(final String tiempoAireTotal) {
		this.tiempoAireTotal = tiempoAireTotal;
	}
	public String getTiempoAireTotal() {
		return tiempoAireTotal;
	}
}
