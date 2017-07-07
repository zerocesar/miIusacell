package mx.com.iusacell.services.miiusacell.vo;

public class BankCardAdditionalInfoVO {
	private String cardType;
	private String productName;
	private String cardTypeDescription;
	private String issuer;
	private String appliesPromotion;
	
	public String getCardType() {
		return cardType;
	}
	public void setCardType(final String cardType) {
		this.cardType = cardType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(final String productName) {
		this.productName = productName;
	}
	public String getCardTypeDescription() {
		return cardTypeDescription;
	}
	public void setCardTypeDescription(String cardTypeDescription) {
		this.cardTypeDescription = cardTypeDescription;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(final String issuer) {
		this.issuer = issuer;
	}
	public String getAppliesPromotion() {
		return appliesPromotion;
	}
	public void setAppliesPromotion(final String appliesPromotion) {
		this.appliesPromotion = appliesPromotion;
	}
}
