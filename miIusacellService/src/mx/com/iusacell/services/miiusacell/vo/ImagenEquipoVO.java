package mx.com.iusacell.services.miiusacell.vo;

public class ImagenEquipoVO {

	private String equipo;
	private String imagenEquipoB64;
	private String imagenSOB64;
	private String sistemaOper;
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(final String equipo) {
		this.equipo = equipo;
	}
	public String getImagenEquipoB64() {
		return imagenEquipoB64;
	}
	public void setImagenEquipoB64(final String imagenEquipoB64) {
		this.imagenEquipoB64 = imagenEquipoB64;
	}
	public String getSistemaOper() {
		return sistemaOper;
	}
	public void setSistemaOper(final String sistemaOper) {
		this.sistemaOper = sistemaOper;
	}
	public void setImagenSOB64(final String imagenSOB64) {
		this.imagenSOB64 = imagenSOB64;
	}
	public String getImagenSOB64() {
		return imagenSOB64;
	}
}
