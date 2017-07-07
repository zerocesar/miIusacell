package mx.com.iusacell.mapas.consume.vo;

public class RespuestaLlaveVO {
  /** ValidaLlaveMaestra **/
  /****/
  private String  fechaCad;   //*Ambos
  private Boolean auth;       //*Ambos
  /****/
  /** validaLlaveMaestraIusacell **/
  private Boolean cambioLlave;
  private String  mensaje;

  public RespuestaLlaveVO() {
    auth = false;
  }


  public String getFechaCad() {
    return fechaCad;
  }

  public void setFechaCad(String fechaCad) {
    this.fechaCad = fechaCad;
  }

  public Boolean getAuth() {
    return auth;
  }

  public void setAuth(Boolean auth) {
    this.auth = auth;
  }

  public Boolean getCambioLlave() {
    return cambioLlave;
  }

  public void setCambioLlave(Boolean cambioLlave) {
    this.cambioLlave = cambioLlave;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

}
