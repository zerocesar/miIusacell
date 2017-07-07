package mx.com.iusacell.services.miiusacell.vo;

public class MensajeMailVO {
   private String mailError="";
   private String mailSended="";
   private String messageErrors="";
   private String smsError="";
   private String smsSended="";
   
public String getMailError() {
	return mailError;
}
public void setMailError(final String mailError) {
	this.mailError = mailError;
}
public String getMailSended() {
	return mailSended;
}
public void setMailSended(final String mailSended) {
	this.mailSended = mailSended;
}
public String getSmsError() {
	return smsError;
}
public void setSmsError(final String smsError) {
	this.smsError = smsError;
}
public String getSmsSended() {
	return smsSended;
}
public void setSmsSended(final String smsSended) {
	this.smsSended = smsSended;
}
public void setMessageErrors(final String messageErrors) {
	this.messageErrors = messageErrors;
}
public String getMessageErrors() {
	return messageErrors;
}

}
