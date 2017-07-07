package mx.com.iusacell.services.miiusacell.vo;

public class DetalleTotalesLlamadas {
	private Llamadas llamadas;
	private MinutosTodoDestino minutosTodoDestino;
	private MinutosComunidad minutosComunidad;
	private String tiempoAireAbonado;
	private String tiempoAireTotal;
	private String tiempoAireDeRegalo;
	public Llamadas getLlamadas() {
		return llamadas;
	}
	public void setLlamadas(final Llamadas llamadas) {
		this.llamadas = llamadas;
	}
	public MinutosTodoDestino getMinutosTodoDestino() {
		return minutosTodoDestino;
	}
	public void setMinutosTodoDestino(final MinutosTodoDestino minutosTodoDestino) {
		this.minutosTodoDestino = minutosTodoDestino;
	}
	public MinutosComunidad getMinutosComunidad() {
		return minutosComunidad;
	}
	public void setMinutosComunidad(final MinutosComunidad minutosComunidad) {
		this.minutosComunidad = minutosComunidad;
	}
	public String getTiempoAireAbonado() {
		return tiempoAireAbonado;
	}
	public void setTiempoAireAbonado(final String tiempoAireAbonado) {
		this.tiempoAireAbonado = tiempoAireAbonado;
	}
	public String getTiempoAireTotal() {
		return tiempoAireTotal;
	}
	public void setTiempoAireTotal(final String tiempoAireTotal) {
		this.tiempoAireTotal = tiempoAireTotal;
	}
	public String getTiempoAireDeRegalo() {
		return tiempoAireDeRegalo;
	}
	public void setTiempoAireDeRegalo(final String tiempoAireDeRegalo) {
		this.tiempoAireDeRegalo = tiempoAireDeRegalo;
	}
}
