package mx.com.iusacell.services.miiusacell.vo.autorizador;

import java.io.Serializable;

public class CardsVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String idUser;
    private String hasCreditCard;
    private String hasHipCredit;
    private String hasAutCredit;
    private String type;
    private String status;
    private String bank;
    private String bankIdProvider;
    private String colony;
    private String municipality;
    private String country;
    private String lastName;
    private String firstName;
    private String telephone;
    private String cardNumber;
    private String expirationYear;
    private String expirationMonth;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String provider;
    private String creationDate;
    
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(final String idUser) {
		this.idUser = idUser;
	}
	public String getHasCreditCard() {
		return hasCreditCard;
	}
	public void setHasCreditCard(final String hasCreditCard) {
		this.hasCreditCard = hasCreditCard;
	}
	public String getHasHipCredit() {
		return hasHipCredit;
	}
	public void setHasHipCredit(final String hasHipCredit) {
		this.hasHipCredit = hasHipCredit;
	}
	public String getHasAutCredit() {
		return hasAutCredit;
	}
	public void setHasAutCredit(final String hasAutCredit) {
		this.hasAutCredit = hasAutCredit;
	}
	public String getType() {
		return type;
	}
	public void setType(final String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(final String bank) {
		this.bank = bank;
	}
	public String getBankIdProvider() {
		return bankIdProvider;
	}
	public void setBankIdProvider(final String bankIdProvider) {
		this.bankIdProvider = bankIdProvider;
	}
	public String getColony() {
		return colony;
	}
	public void setColony(String colony) {
		this.colony = colony;
	}
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(final String country) {
		this.country = country;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(final String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(final String expirationYear) {
		this.expirationYear = expirationYear;
	}
	public String getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(final String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(final String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(final String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(final String zip) {
		this.zip = zip;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(final String provider) {
		this.provider = provider;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(final String creationDate) {
		this.creationDate = creationDate;
	}
}
