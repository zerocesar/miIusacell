package mx.com.iusacell.mapas.vo;

public class CatalogoControlStreetViewVO {

  private String claveCae;
  private String url;
  private double rotacion;
  private double angulo;
  private double zoom;
  private double latitud;
  private double longitud;

  public void setClaveCae(String claveCae) {
    this.claveCae = claveCae;
  }

  public String getClaveCae() {
    return claveCae;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setRotacion(double rotacion) {
    this.rotacion = rotacion;
  }

  public double getRotacion() {
    return rotacion;
  }

  public void setAngulo(double angulo) {
    this.angulo = angulo;
  }

  public double getAngulo() {
    return angulo;
  }

  public void setZoom(double zoom) {
    this.zoom = zoom;
  }

  public double getZoom() {
    return zoom;
  }

  public void setLatitud(double latitud) {
    this.latitud = latitud;
  }

  public double getLatitud() {
    return latitud;
  }

  public void setLongitud(double longitud) {
    this.longitud = longitud;
  }

  public double getLongitud() {
    return longitud;
  }
}
