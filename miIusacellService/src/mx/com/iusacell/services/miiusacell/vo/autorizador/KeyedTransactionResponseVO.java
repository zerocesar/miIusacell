package mx.com.iusacell.services.miiusacell.vo.autorizador;

public class KeyedTransactionResponseVO {

	private long affiliation;
	private String affiliationName;
	private String authorizationCode;
	private String bankResponseCode;
	private String descriptionResponse;
	private long transactionControlNumber;
	private String transactionDate;
	
	public long getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(final long affiliation) {
		this.affiliation = affiliation;
	}
	public String getAffiliationName() {
		return affiliationName;
	}
	public void setAffiliationName(final String affiliationName) {
		this.affiliationName = affiliationName;
	}
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(final String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public String getBankResponseCode() {
		return bankResponseCode;
	}
	public void setBankResponseCode(final String bankResponseCode) {
		this.bankResponseCode = bankResponseCode;
	}
	public String getDescriptionResponse() {
		return descriptionResponse;
	}
	public void setDescriptionResponse(final String descriptionResponse) {
		this.descriptionResponse = descriptionResponse;
	}
	public long getTransactionControlNumber() {
		return transactionControlNumber;
	}
	public void setTransactionControlNumber(final long transactionControlNumber) {
		this.transactionControlNumber = transactionControlNumber;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(final String transactionDate) {
		this.transactionDate = transactionDate;
	}
}
