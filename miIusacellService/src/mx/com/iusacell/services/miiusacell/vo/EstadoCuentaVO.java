package mx.com.iusacell.services.miiusacell.vo;

import java.util.List;

public class EstadoCuentaVO {

	private String noCuenta;
	private String noFactura;
	private String periodo;
	private String ajusteRedondeo;
	private String subtotal;
	private String totalFacturaActual;
	private String otroCargo;
	private String porcentajeIVA; 
	private String rentasParser;
	private List<FacturaVO> rentas;
	private List<FacturaVO> serviciosConPromocion;
	private List<FacturaVO> serviciosSinPromocion;
	private List<FacturaVO> consumosPeriodo;
	private List<FacturaVO> otrosCargos;
	private List<FacturaVO> ajustes;
	private List<FacturaVO> saldos;
	
	public String getNoCuenta() {
		return noCuenta;
	}
	public void setNoCuenta(final String noCuenta) {
		this.noCuenta = noCuenta;
	}
	public String getNoFactura() {
		return noFactura;
	}
	public void setNoFactura(final String noFactura) {
		this.noFactura = noFactura;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(final String periodo) {
		this.periodo = periodo;
	}
	public String getAjusteRedondeo() {
		return ajusteRedondeo;
	}
	public void setAjusteRedondeo(final String ajusteRedondeo) {
		this.ajusteRedondeo = ajusteRedondeo;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(final String subtotal) {
		this.subtotal = subtotal;
	}
	public String getTotalFacturaActual() {
		return totalFacturaActual;
	}
	public void setTotalFacturaActual(final String totalFacturaActual) {
		this.totalFacturaActual = totalFacturaActual;
	}
	public String getOtroCargo() {
		return otroCargo;
	}
	public void setOtroCargo(final String otroCargo) {
		this.otroCargo = otroCargo;
	}
	public String getPorcentajeIVA() {
		return porcentajeIVA;
	}
	public void setPorcentajeIVA(final String porcentajeIVA) {
		this.porcentajeIVA = porcentajeIVA;
	}
	public List<FacturaVO> getRentas() {
		return rentas;
	}
	public void setRentas(final List<FacturaVO> rentas) {
		this.rentas = rentas;
	}
	public List<FacturaVO> getServiciosConPromocion() {
		return serviciosConPromocion;
	}
	public void setServiciosConPromocion(final List<FacturaVO> serviciosConPromocion) {
		this.serviciosConPromocion = serviciosConPromocion;
	}
	public List<FacturaVO> getServiciosSinPromocion() {
		return serviciosSinPromocion;
	}
	public void setServiciosSinPromocion(final List<FacturaVO> serviciosSinPromocion) {
		this.serviciosSinPromocion = serviciosSinPromocion;
	}
	public List<FacturaVO> getConsumosPeriodo() {
		return consumosPeriodo;
	}
	public void setConsumosPeriodo(final List<FacturaVO> consumosPeriodo) {
		this.consumosPeriodo = consumosPeriodo;
	}
	public List<FacturaVO> getOtrosCargos() {
		return otrosCargos;
	}
	public void setOtrosCargos(final List<FacturaVO> otrosCargos) {
		this.otrosCargos = otrosCargos;
	}
	public List<FacturaVO> getAjustes() {
		return ajustes;
	}
	public void setAjustes(final List<FacturaVO> ajustes) {
		this.ajustes = ajustes;
	}
	public List<FacturaVO> getSaldos() {
		return saldos;
	}
	public void setSaldos(final List<FacturaVO> saldos) {
		this.saldos = saldos;
	}
	public String getRentasParser() {
		return rentasParser;
	}
	public void setRentasParser(final String rentasParser) {
		this.rentasParser = rentasParser;
	}
	
}
