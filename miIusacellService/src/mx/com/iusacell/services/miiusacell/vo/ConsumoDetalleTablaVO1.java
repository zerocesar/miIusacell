package mx.com.iusacell.services.miiusacell.vo;

public class ConsumoDetalleTablaVO1 {

	private int idColumna;
	private String fecha;
	private String hora;
	private String numero;
	private String compania;
	private String destino;
	private String tiempo;
	private String costo;
	private String mbConsumidos;
	public String getFecha() {
		return fecha;
	}
	public void setFecha(final String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(final String hora) {
		this.hora = hora;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(final String numero) {
		this.numero = numero;
	}
	public String getCompania() {
		return compania;
	}
	public void setCompania(final String compania) {
		this.compania = compania;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(final String destino) {
		this.destino = destino;
	}
	public String getTiempo() {
		return tiempo;
	}
	public void setTiempo(final String tiempo) {
		this.tiempo = tiempo;
	}
	public String getCosto() {
		return costo;
	}
	public void setCosto(final String costo) {
		this.costo = costo;
	}
	public String getMbConsumidos() {
		return mbConsumidos;
	}
	public void setMbConsumidos(final String mbConsumidos) {
		this.mbConsumidos = mbConsumidos;
	}
	public void setIdColumna(final int idColumna) {
		this.idColumna = idColumna;
	}
	public int getIdColumna() {
		return idColumna;
	}
}
