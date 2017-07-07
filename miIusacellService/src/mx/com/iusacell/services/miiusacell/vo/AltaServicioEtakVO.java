package mx.com.iusacell.services.miiusacell.vo;

public class AltaServicioEtakVO {

	private String costo;
	private String id;
	private int idHistorico;
	private int vigencia;
	private String monto;
	public String getCosto() {
		return costo;
	}
	public void setCosto(final String costo) {
		this.costo = costo;
	}
	public String getId() {
		return id;
	}
	public void setId(final String id) {
		this.id = id;
	}
	public int getIdHistorico() {
		return idHistorico;
	}
	public void setIdHistorico(final int idHistorico) {
		this.idHistorico = idHistorico;
	}
	public int getVigencia() {
		return vigencia;
	}
	public void setVigencia(final int vigencia) {
		this.vigencia = vigencia;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(final String monto) {
		this.monto = monto;
	}
}
