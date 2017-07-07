package mx.com.iusacell.services.miiusacell.vo;

import java.util.List;

public class CajaRegistraMovimientoVO {

	private String errorCode;
	private int idRegistro;
	private List<TarjetasCajaVO> tarjetas;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(final String errorCode) {
		this.errorCode = errorCode;
	}
	public int getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(final int idRegistro) {
		this.idRegistro = idRegistro;
	}
	public List<TarjetasCajaVO> getTarjetas() {
		return tarjetas;
	}
	public void setTarjetas(final List<TarjetasCajaVO> tarjetas) {
		this.tarjetas = tarjetas;
	}
}
