package mx.com.iusacell.services.miiusacell.vo;

public class TarjetaVO {
	private int marcaTarjeta; 
	private String numeroTarjeta; 
	private  String mesVencimiento; 
	private String anioVencimiento; 
	private String nombre; 
	private String aPaterno; 
	private String aMaterno; 
	private String cp; 
	private String ultimosDigitos; 
	private String dn; 		
	
	public int getMarcaTarjeta() {
		return marcaTarjeta;
	}
	public void setMarcaTarjeta(final int marcaTarjeta) {
		this.marcaTarjeta = marcaTarjeta;
	}
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}
	public void setNumeroTarjeta(final String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public String getMesVencimiento() {
		return mesVencimiento;
	}
	public void setMesVencimiento(final String mesVencimiento) {
		this.mesVencimiento = mesVencimiento;
	}
	public String getAnioVencimiento() {
		return anioVencimiento;
	}
	public void setAnioVencimiento(final String anioVencimiento) {
		this.anioVencimiento = anioVencimiento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}
	public String getaPaterno() {
		return aPaterno;
	}
	public void setaPaterno(final String aPaterno) {
		this.aPaterno = aPaterno;
	}
	public String getaMaterno() {
		return aMaterno;
	}
	public void setaMaterno(final String aMaterno) {
		this.aMaterno = aMaterno;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(final String cp) {
		this.cp = cp;
	}
	public String getUltimosDigitos() {
		return ultimosDigitos;
	}
	public void setUltimosDigitos(final String ultimosDigitos) {
		this.ultimosDigitos = ultimosDigitos;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(final String dn) {
		this.dn = dn;
	}	
}
