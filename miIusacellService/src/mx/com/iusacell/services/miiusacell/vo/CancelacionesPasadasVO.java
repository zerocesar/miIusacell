package mx.com.iusacell.services.miiusacell.vo;

import java.util.Date;

public class CancelacionesPasadasVO {
	private int ticketNumber;
	private String groupname;
	private int GroupId;
	private String levelValue;
	private Date openTime;
	private String noteDesc;
	private String status;
	
	public int getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(final int ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(final String groupname) {
		this.groupname = groupname;
	}
	public int getGroupId() {
		return GroupId;
	}
	public void setGroupId(final int groupId) {
		GroupId = groupId;
	}
	public String getLevelValue() {
		return levelValue;
	}
	public void setLevelValue(final String levelValue) {
		this.levelValue = levelValue;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(final Date openTime) {
		this.openTime = openTime;
	}
	public String getNoteDesc() {
		return noteDesc;
	}
	public void setNoteDesc(final String noteDesc) {
		this.noteDesc = noteDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}
	
}
