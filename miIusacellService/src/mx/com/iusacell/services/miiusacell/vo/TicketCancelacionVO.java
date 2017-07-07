package mx.com.iusacell.services.miiusacell.vo;

public class TicketCancelacionVO {
	private String captureTime;
	private String openTime;
	private String categoryCreateTicket;
	private String noteCreateTicket;
	private String status;
	public String getCaptureTime() {
		return captureTime;
	}
	public void setCaptureTime(final String captureTime) {
		this.captureTime = captureTime;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(final String openTime) {
		this.openTime = openTime;
	}
	public String getCategoryCreateTicket() {
		return categoryCreateTicket;
	}
	public void setCategoryCreateTicket(final String categoryCreateTicket) {
		this.categoryCreateTicket = categoryCreateTicket;
	}
	public String getNoteCreateTicket() {
		return noteCreateTicket;
	}
	public void setNoteCreateTicket(final String noteCreateTicket) {
		this.noteCreateTicket = noteCreateTicket;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}
}