package mx.com.iusacell.services.miiusacell.vo.autorizador;

import java.io.Serializable;

public class KeyedTransactionVO implements Serializable{
		
	private static final long serialVersionUID = 1L;
	private String bankCardNumber;
	private String cardCvv2;
	private String cardExpirationMonth;
	private String cardExpirationYear;		
	private String cardType;
	private double chargeAmount;
	private int chargeBusinessID;
	private int chargeChargeID;
	private int chargePaymentPeriod;
	private String chargeRegionID;
	private String chargeStoreID;
	private int chargeSystemID;
	private String customerBankPhoneNumber;
	private String customerIpAddress;
	private String deviceFingerprintId;
	private String productName;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	
	private String customerCountry;
	private String customerCounty;
	private String customerState;
	private String customerNeighborhood;
	private String customerStreet;
	private String customerPostalCode;
	private String customerStreetNumber;
	private String reservedA;
	
	public String getBankCardNumber() {
		return bankCardNumber;
	}
	public void setBankCardNumber(final String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}
	public String getCardCvv2() {
		return cardCvv2;
	}
	public void setCardCvv2(final String cardCvv2) {
		this.cardCvv2 = cardCvv2;
	}
	public String getCardExpirationMonth() {
		return cardExpirationMonth;
	}
	public void setCardExpirationMonth(final String cardExpirationMonth) {
		this.cardExpirationMonth = cardExpirationMonth;
	}
	public String getCardExpirationYear() {
		return cardExpirationYear;
	}
	public void setCardExpirationYear(final String cardExpirationYear) {
		this.cardExpirationYear = cardExpirationYear;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(final String cardType) {
		this.cardType = cardType;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(final double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public int getChargeBusinessID() {
		return chargeBusinessID;
	}
	public void setChargeBusinessID(final int chargeBusinessID) {
		this.chargeBusinessID = chargeBusinessID;
	}
	public int getChargeChargeID() {
		return chargeChargeID;
	}
	public void setChargeChargeID(final int chargeChargeID) {
		this.chargeChargeID = chargeChargeID;
	}
	public int getChargePaymentPeriod() {
		return chargePaymentPeriod;
	}
	public void setChargePaymentPeriod(final int chargePaymentPeriod) {
		this.chargePaymentPeriod = chargePaymentPeriod;
	}
	public String getChargeRegionID() {
		return chargeRegionID;
	}
	public void setChargeRegionID(final String chargeRegionID) {
		this.chargeRegionID = chargeRegionID;
	}
	public String getChargeStoreID() {
		return chargeStoreID;
	}
	public void setChargeStoreID(final String chargeStoreID) {
		this.chargeStoreID = chargeStoreID;
	}
	public int getChargeSystemID() {
		return chargeSystemID;
	}
	public void setChargeSystemID(final int chargeSystemID) {
		this.chargeSystemID = chargeSystemID;
	}
	public String getCustomerBankPhoneNumber() {
		return customerBankPhoneNumber;
	}
	public void setCustomerBankPhoneNumber(final String customerBankPhoneNumber) {
		this.customerBankPhoneNumber = customerBankPhoneNumber;
	}
	public String getCustomerIpAddress() {
		return customerIpAddress;
	}
	public void setCustomerIpAddress(final String customerIpAddress) {
		this.customerIpAddress = customerIpAddress;
	}
	public String getDeviceFingerprintId() {
		return deviceFingerprintId;
	}
	public void setDeviceFingerprintId(final String deviceFingerprintId) {
		this.deviceFingerprintId = deviceFingerprintId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(final String productName) {
		this.productName = productName;
	}
	public String getCustomerFirstName() {
		return customerFirstName;
	}
	public void setCustomerFirstName(final String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	public String getCustomerLastName() {
		return customerLastName;
	}
	public void setCustomerLastName(final String customerLastName) {
		this.customerLastName = customerLastName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(final String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerCountry() {
		return customerCountry;
	}
	public void setCustomerCountry(final String customerCountry) {
		this.customerCountry = customerCountry;
	}
	public String getCustomerState() {
		return customerState;
	}
	public void setCustomerState(final String customerState) {
		this.customerState = customerState;
	}
	public String getCustomerNeighborhood() {
		return customerNeighborhood;
	}
	public void setCustomerNeighborhood(final String customerNeighborhood) {
		this.customerNeighborhood = customerNeighborhood;
	}
	public String getCustomerStreet() {
		return customerStreet;
	}
	public void setCustomerStreet(final String customerStreet) {
		this.customerStreet = customerStreet;
	}
	public String getCustomerPostalCode() {
		return customerPostalCode;
	}
	public void setCustomerPostalCode(final String customerPostalCode) {
		this.customerPostalCode = customerPostalCode;
	}
	public String getCustomerStreetNumber() {
		return customerStreetNumber;
	}
	public void setCustomerStreetNumber(final String customerStreetNumber) {
		this.customerStreetNumber = customerStreetNumber;
	}
	public String getReservedA() {
		return reservedA;
	}
	public void setReservedA(final String reservedA) {
		this.reservedA = reservedA;
	}
	public String getCustomerCounty() {
		return customerCounty;
	}
	public void setCustomerCounty(final String customerCounty) {
		this.customerCounty = customerCounty;
	}
}
