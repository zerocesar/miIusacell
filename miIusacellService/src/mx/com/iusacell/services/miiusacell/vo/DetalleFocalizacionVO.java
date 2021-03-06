package mx.com.iusacell.services.miiusacell.vo;
import java.util.List;

public class DetalleFocalizacionVO {
	
	private String idFormaPago;
	private String idOperador;
	private String modalidad;
	private String servicio;
	private String subservicio;
	private String tecnologia;
	private String isPostpagoOrHibrido;
	private String tiempoAireComprado;
	private String saldoPrepago;
	private String fechaCorte;
	private String vigenciaSalgoPrepago;
	private String descripcionPlan;
	private String coID;
	private String tmCode;
	private String idPrepago;
	private String idPlan;
	private String code;
	private String statusContrato;
	private String statusCuenta;
	private String tipoCliente;
	
	private String fechaContratacion;
	private String fechaVencimiento;
	private String plazoForzoso;
	private String mesesRestantes;
	private String renta;
	private String cuenta;
	private List<ServiciosFocalizacionVO> serviciosFoca;
	
	public String getIdFormaPago() {
		return idFormaPago;
	}
	public void setIdFormaPago(final String idFormaPago) {
		this.idFormaPago = idFormaPago;
	}
	public String getIdOperador() {
		return idOperador;
	}
	public void setIdOperador(final String idOperador) {
		this.idOperador = idOperador;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(final String modalidad) {
		this.modalidad = modalidad;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(final String servicio) {
		this.servicio = servicio;
	}
	public String getSubservicio() {
		return subservicio;
	}
	public void setSubservicio(final String subservicio) {
		this.subservicio = subservicio;
	}
	public String getTecnologia() {
		return tecnologia;
	}
	public void setTecnologia(final String tecnologia) {
		this.tecnologia = tecnologia;
	}
	public String getIsPostpagoOrHibrido() {
		return isPostpagoOrHibrido;
	}
	public void setIsPostpagoOrHibrido(final String isPostpagoOrHibrido) {
		this.isPostpagoOrHibrido = isPostpagoOrHibrido;
	}
	public String getTiempoAireComprado() {
		return tiempoAireComprado;
	}
	public void setTiempoAireComprado(final String tiempoAireComprado) {
		this.tiempoAireComprado = tiempoAireComprado;
	}
	public String getSaldoPrepago() {
		return saldoPrepago;
	}
	public void setSaldoPrepago(final String saldoPrepago) {
		this.saldoPrepago = saldoPrepago;
	}
	public String getFechaCorte() {
		return fechaCorte;
	}
	public void setFechaCorte(final String fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	public String getVigenciaSalgoPrepago() {
		return vigenciaSalgoPrepago;
	}
	public void setVigenciaSalgoPrepago(final String vigenciaSalgoPrepago) {
		this.vigenciaSalgoPrepago = vigenciaSalgoPrepago;
	}
	public String getDescripcionPlan() {
		return descripcionPlan;
	}
	public void setDescripcionPlan(final String descripcionPlan) {
		this.descripcionPlan = descripcionPlan;
	}
	public String getCoID() {
		return coID;
	}
	public void setCoID(final String coID) {
		this.coID = coID;
	}
	public String getTmCode() {
		return tmCode;
	}
	public void setTmCode(final String tmCode) {
		this.tmCode = tmCode;
	}
	public String getIdPrepago() {
		return idPrepago;
	}
	public void setIdPrepago(final String idPrepago) {
		this.idPrepago = idPrepago;
	}
	public String getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(final String idPlan) {
		this.idPlan = idPlan;
	}
	public String getCode() {
		return code;
	}
	public void setCode(final String code) {
		this.code = code;
	}
	public String getStatusContrato() {
		return statusContrato;
	}
	public void setStatusContrato(final String statusContrato) {
		this.statusContrato = statusContrato;
	}
	public String getStatusCuenta() {
		return statusCuenta;
	}
	public void setStatusCuenta(final String statusCuenta) {
		this.statusCuenta = statusCuenta;
	}
	public String getTipoCliente() {
		return tipoCliente;
	}
	public void setTipoCliente(final String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	public String getFechaContratacion() {
		return fechaContratacion;
	}
	public void setFechaContratacion(final String fechaContratacion) {
		this.fechaContratacion = fechaContratacion;
	}
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(final String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public String getPlazoForzoso() {
		return plazoForzoso;
	}
	public void setPlazoForzoso(final String plazoForzoso) {
		this.plazoForzoso = plazoForzoso;
	}
	public String getMesesRestantes() {
		return mesesRestantes;
	}
	public void setMesesRestantes(final String mesesRestantes) {
		this.mesesRestantes = mesesRestantes;
	}
	public String getRenta() {
		return renta;
	}
	public void setRenta(final String renta) {
		this.renta = renta;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(final String cuenta) {
		this.cuenta = cuenta;
	}
	public void setServiciosFoca(final List<ServiciosFocalizacionVO> serviciosFoca) {
		this.serviciosFoca = serviciosFoca;
	}
	public List<ServiciosFocalizacionVO> getServiciosFoca() {
		return serviciosFoca;
	}
}
