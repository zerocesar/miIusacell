package mx.com.iusacell.services.miiusacell.vo;

public class DetalleFacturaVO {
    
	private String cuenta;
	private String factura;
	private String rentas;
	private String serviciosAdicionales;
	private String consumosPeriodo;
	private String subtotal ;
	private String iva;
	private String ajuste;
	private String total;
	private String periodo;
	private String fechaLimite;
	private String otrosCargos;
	private String porcentajeIVA;
	
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(final String cuenta) {
		this.cuenta = cuenta;
	}
	public String getFactura() {
		return factura;
	}
	public void setFactura(final String factura) {
		this.factura = factura;
	}
	public String getRentas() {
		return rentas;
	}
	public void setRentas(final String rentas) {
		this.rentas = rentas;
	}
	public String getServiciosAdicionales() {
		return serviciosAdicionales;
	}
	public void setServiciosAdicionales(final String serviciosAdicionales) {
		this.serviciosAdicionales = serviciosAdicionales;
	}
	public String getConsumosPeriodo() {
		return consumosPeriodo;
	}
	public void setConsumosPeriodo(final String consumosPeriodo) {
		this.consumosPeriodo = consumosPeriodo;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(final String subtotal) {
		this.subtotal = subtotal;
	}
	public String getIva() {
		return iva;
	}
	public void setIva(final String iva) {
		this.iva = iva;
	}
	public String getAjuste() {
		return ajuste;
	}
	public void setAjuste(final String ajuste) {
		this.ajuste = ajuste;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(final String total) {
		this.total = total;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(final String periodo) {
		this.periodo = periodo;
	}
	public String getFechaLimite() {
		return fechaLimite;
	}
	public void setFechaLimite(final String fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	public String getOtrosCargos() {
		return otrosCargos;
	}
	public void setOtrosCargos(final String otrosCargos) {
		this.otrosCargos = otrosCargos;
	}
	public String getPorcentajeIVA() {
		return porcentajeIVA;
	}
	public void setPorcentajeIVA(final String porcentajeIVA) {
		this.porcentajeIVA = porcentajeIVA;
	}
	
}
