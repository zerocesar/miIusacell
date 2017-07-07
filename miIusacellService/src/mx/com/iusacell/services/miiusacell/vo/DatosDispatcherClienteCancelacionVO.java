package mx.com.iusacell.services.miiusacell.vo;

public class DatosDispatcherClienteCancelacionVO {
	private String custCode;
	private String customerID;
	private String esn;
	private String nombreCliente;
	private String apellidos;
	private String contrato;
	private String statusContrato;
	private String statusCuenta;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(final String custCode) {
		this.custCode = custCode;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(final String customerID) {
		this.customerID = customerID;
	}
	public String getEsn() {
		return esn;
	}
	public void setEsn(final String esn) {
		this.esn = esn;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(final String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(final String apellidos) {
		this.apellidos = apellidos;
	}
	public String getContrato() {
		return contrato;
	}
	public void setContrato(final String contrato) {
		this.contrato = contrato;
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
}
