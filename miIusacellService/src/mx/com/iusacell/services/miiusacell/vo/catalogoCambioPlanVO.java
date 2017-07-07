package mx.com.iusacell.services.miiusacell.vo;

public class catalogoCambioPlanVO {
	private String serviceClass="";
	private String nombrePlan="";
	private String idPlan="";
	private String costo="";
	private String Descripcion="";
	
	public String getServiceClass() {
		return serviceClass;
	}
	public void setServiceClass(final String serviceClass) {
		this.serviceClass = serviceClass;
	}
	public String getNombrePlan() {
		return nombrePlan;
	}
	public void setNombrePlan(final String nombrePlan) {
		this.nombrePlan = nombrePlan;
	}
	public String getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(final String idPlan) {
		this.idPlan = idPlan;
	}
	public String getCosto() {
		return costo;
	}
	public void setCosto(final String costo) {
		this.costo = costo;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(final String descripcion) {
		Descripcion = descripcion;
	}
	
}
