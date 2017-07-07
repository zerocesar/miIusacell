package mx.com.iusacell.services.miiusacell.vo;

import java.util.List;

public class ConsultaWalletGeneralVO {
	
	private List<ConsultaWalletListVO>    consultaWalletsDinero;
	private List<ConsultaWalletListVO>       consultaWalletsVoz;
	private List<ConsultaWalletListVO>       consultaWalletsSms;
	private List<ConsultaWalletListVO>     consultaWalletsDatos;
	private List<ConsultaWalletListVO> consultaWalletsIlimitado;
	private List<ConsultaWalletListVO>    consultaWalletsUnidad;
	
	private String totalWalletsDinero;
	private String totalWalletsVoz;
	private String totalWalletsSms;
	private String totalWalletsDatos;
	private String totalRedesIlimitadas;
	
	private String planDescripcion = "";
	
	private String serviceClass="";
	
	
	public List<ConsultaWalletListVO> getConsultaWalletsDinero() {
		return consultaWalletsDinero;
	}
	public void setConsultaWalletsDinero(
	        final List<ConsultaWalletListVO> consultaWalletsDinero) {
		this.consultaWalletsDinero = consultaWalletsDinero;
	}
	public List<ConsultaWalletListVO> getConsultaWalletsVoz() {
		return consultaWalletsVoz;
	}
	public void setConsultaWalletsVoz(final List<ConsultaWalletListVO> consultaWalletsVoz) {
		this.consultaWalletsVoz = consultaWalletsVoz;
	}
	public List<ConsultaWalletListVO> getConsultaWalletsSms() {
		return consultaWalletsSms;
	}
	public void setConsultaWalletsSms(final List<ConsultaWalletListVO> consultaWalletsSms) {
		this.consultaWalletsSms = consultaWalletsSms;
	}
	public List<ConsultaWalletListVO> getConsultaWalletsDatos() {
		return consultaWalletsDatos;
	}
	public void setConsultaWalletsDatos(
	        final List<ConsultaWalletListVO> consultaWalletsDatos) {
		this.consultaWalletsDatos = consultaWalletsDatos;
	}
	public List<ConsultaWalletListVO> getConsultaWalletsIlimitado() {
		return consultaWalletsIlimitado;
	}
	public void setConsultaWalletsIlimitado(
			List<ConsultaWalletListVO> consultaWalletsIlimitado) {
		this.consultaWalletsIlimitado = consultaWalletsIlimitado;
	}
	public String getTotalWalletsDinero() {
		return totalWalletsDinero;
	}
	public void setTotalWalletsDinero(final String totalWalletsDinero) {
		this.totalWalletsDinero = totalWalletsDinero;
	}
	public String getTotalWalletsVoz() {
		return totalWalletsVoz;
	}
	public void setTotalWalletsVoz(final String totalWalletsVoz) {
		this.totalWalletsVoz = totalWalletsVoz;
	}
	public String getTotalWalletsSms() {
		return totalWalletsSms;
	}
	public void setTotalWalletsSms(final String totalWalletsSms) {
		this.totalWalletsSms = totalWalletsSms;
	}
	public String getTotalWalletsDatos() {
		return totalWalletsDatos;
	}
	public void setTotalWalletsDatos(final String totalWalletsDatos) {
		this.totalWalletsDatos = totalWalletsDatos;
	}
	public void setTotalRedesIlimitadas(String totalRedesIlimitadas) {
		this.totalRedesIlimitadas = totalRedesIlimitadas;
	}
	public String getTotalRedesIlimitadas() {
		return totalRedesIlimitadas;
	}
	public String getPlanDescripcion() {
		return planDescripcion;
	}
	public void setPlanDescripcion(final String planDescripcion) {
		this.planDescripcion = planDescripcion;
	}
	public void setServiceClass(final String serviceClass) {
		this.serviceClass = serviceClass;
	}
	public String getServiceClass() {
		return serviceClass;
	}
	public List<ConsultaWalletListVO> getConsultaWalletsUnidad() {
		return consultaWalletsUnidad;
	}
	public void setConsultaWalletsUnidad(
			List<ConsultaWalletListVO> consultaWalletsUnidad) {
		this.consultaWalletsUnidad = consultaWalletsUnidad;
	}
	
}

