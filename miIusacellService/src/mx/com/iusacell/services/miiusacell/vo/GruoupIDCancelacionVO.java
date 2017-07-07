package mx.com.iusacell.services.miiusacell.vo;

public class GruoupIDCancelacionVO {
	private int group_ID;
	private String group_Desc;
	private int divisional;
	private int group_Category;
	private int status_Active;
	private int transaction_ID;
	private long userID_Owner;
	private int note_Activate;
	private int catalogue_Activate;
	private int mecanica_Asignacion;
	private int canal_Atencion;
	
	public int getGroup_ID() {
		return group_ID;
	}
	public void setGroup_ID(final int groupID) {
		group_ID = groupID;
	}
	public String getGroup_Desc() {
		return group_Desc;
	}
	public void setGroup_Desc(final String groupDesc) {
		group_Desc = groupDesc;
	}
	public int getDivisional() {
		return divisional;
	}
	public void setDivisional(final int divisional) {
		this.divisional = divisional;
	}
	public int getGroup_Category() {
		return group_Category;
	}
	public void setGroup_Category(final int groupCategory) {
		group_Category = groupCategory;
	}
	public int getStatus_Active() {
		return status_Active;
	}
	public void setStatus_Active(final int statusActive) {
		status_Active = statusActive;
	}
	public int getTransaction_ID() {
		return transaction_ID;
	}
	public void setTransaction_ID(final int transactionID) {
		transaction_ID = transactionID;
	}
	public long getUserID_Owner() {
		return userID_Owner;
	}
	public void setUserID_Owner(final long userIDOwner) {
		userID_Owner = userIDOwner;
	}
	public int getNote_Activate() {
		return note_Activate;
	}
	public void setNote_Activate(final int noteActivate) {
		note_Activate = noteActivate;
	}
	public int getCatalogue_Activate() {
		return catalogue_Activate;
	}
	public void setCatalogue_Activate(final int catalogueActivate) {
		catalogue_Activate = catalogueActivate;
	}
	public int getMecanica_Asignacion() {
		return mecanica_Asignacion;
	}
	public void setMecanica_Asignacion(final int mecanicaAsignacion) {
		mecanica_Asignacion = mecanicaAsignacion;
	}
	public int getCanal_Atencion() {
		return canal_Atencion;
	}
	public void setCanal_Atencion(final int canalAtencion) {
		canal_Atencion = canalAtencion;
	}
}
