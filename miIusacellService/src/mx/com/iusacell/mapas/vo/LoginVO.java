package mx.com.iusacell.mapas.vo;

public class LoginVO {

  private int             respuesta;
  private int             idSesion;
  private String          status;
  private DatosEmpleadoVO datosEmpleado;

  public void setRespuesta(int respuesta) {
    this.respuesta = respuesta;
  }

  public int getRespuesta() {
    return respuesta;
  }

  public void setDatosEmpleado(DatosEmpleadoVO datosEmpleado) {
    this.datosEmpleado = datosEmpleado;
  }

  public DatosEmpleadoVO getDatosEmpleado() {
    return datosEmpleado;
  }

  public void setIdSesion(int idSesion) {
    this.idSesion = idSesion;
  }

  public int getIdSesion() {
    return idSesion;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

}