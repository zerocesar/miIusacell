package mx.com.iusacell.mapas.vo;

public class CatalogoEmpleadoVO {

  private int    idEmpleado;
  private int    idCae;
  private String nombre;
  private String area;
  private String horaEntrada;
  private String horaSalida;

  public int getIdEmpleado() {
    return idEmpleado;
  }

  public void setIdEmpleado(int idEmpleado) {
    this.idEmpleado = idEmpleado;
  }

  public int getIdCae() {
    return idCae;
  }

  public void setIdCae(int idCae) {
    this.idCae = idCae;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getHoraEntrada() {
    return horaEntrada;
  }

  public void setHoraEntrada(String horaEntrada) {
    this.horaEntrada = horaEntrada;
  }

  public String getHoraSalida() {
    return horaSalida;
  }

  public void setHoraSalida(String horaSalida) {
    this.horaSalida = horaSalida;
  }

}