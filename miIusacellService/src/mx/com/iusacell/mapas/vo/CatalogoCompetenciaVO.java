package mx.com.iusacell.mapas.vo;

public class CatalogoCompetenciaVO {

  private int    idCompetencia;
  private String descCompetencia;
  private int    idCanal;
  private double latitud;
  private double longitud;
  private double povLatitud;
  private double povLongitud;
  private double povHeading;
  private double povPitch;
  private double povZoom;
  private String direccion;
  private String telefono;
  /**************************/
  private int    idPlaza;
  private int    idRegion;

  public int getIdCompetencia() {
    return idCompetencia;
  }

  public void setIdCompetencia(int idCompetencia) {
    this.idCompetencia = idCompetencia;
  }

  public String getDescCompetencia() {
    return descCompetencia;
  }

  public void setDescCompetencia(String descCompetencia) {
    this.descCompetencia = descCompetencia;
  }

  public int getIdCanal() {
    return idCanal;
  }

  public void setIdCanal(int idCanal) {
    this.idCanal = idCanal;
  }

  public double getLatitud() {
    return latitud;
  }

  public void setLatitud(double latitud) {
    this.latitud = latitud;
  }

  public double getLongitud() {
    return longitud;
  }

  public void setLongitud(double longitud) {
    this.longitud = longitud;
  }

  public double getPovLatitud() {
    return povLatitud;
  }

  public void setPovLatitud(double povLatitud) {
    this.povLatitud = povLatitud;
  }

  public double getPovLongitud() {
    return povLongitud;
  }

  public void setPovLongitud(double povLongitud) {
    this.povLongitud = povLongitud;
  }

  public double getPovPitch() {
    return povPitch;
  }

  public void setPovPitch(double povPitch) {
    this.povPitch = povPitch;
  }

  public double getPovZoom() {
    return povZoom;
  }

  public void setPovZoom(double povZoom) {
    this.povZoom = povZoom;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public void setPovHeading(double povHeading) {
    this.povHeading = povHeading;
  }

  public double getPovHeading() {
    return povHeading;
  }

//  public void setIdDivision(int idDivision) {
//    this.idDivision = idDivision;
//  }
//
//  public int getIdDivision() {
//    return idDivision;
//  }

  public void setIdPlaza(int idPlaza) {
    this.idPlaza = idPlaza;
  }

  public int getIdPlaza() {
    return idPlaza;
  }

  public void setIdRegion(int idRegion) {
    this.idRegion = idRegion;
  }

  public int getIdRegion() {
    return idRegion;
  }
}
