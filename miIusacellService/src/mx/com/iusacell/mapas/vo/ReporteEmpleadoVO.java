package mx.com.iusacell.mapas.vo;

public class ReporteEmpleadoVO {

  private int    noEmpleado;
  private int    activacionesPropio;
  private double montoPropio;
  private int    activacionesNuevo;
  private double montoNuevo;
  private int    activacionesTotal;
  private double montoTotal;
  private String nombre;

  public int getNoEmpleado() {
    return noEmpleado;
  }

  public void setNoEmpleado(int noEmpleado) {
    this.noEmpleado = noEmpleado;
  }

  public int getActivacionesPropio() {
    return activacionesPropio;
  }

  public void setActivacionesPropio(int activacionesPropio) {
    this.activacionesPropio = activacionesPropio;
  }

  public double getMontoPropio() {
    return montoPropio;
  }

  public void setMontoPropio(double montoPropio) {
    this.montoPropio = montoPropio;
  }

  public int getActivacionesNuevo() {
    return activacionesNuevo;
  }

  public void setActivacionesNuevo(int activacionesNuevo) {
    this.activacionesNuevo = activacionesNuevo;
  }

  public double getMontoNuevo() {
    return montoNuevo;
  }

  public void setMontoNuevo(double montoNuevo) {
    this.montoNuevo = montoNuevo;
  }

  public void setActivacionesTotal(int activacionesTotal) {
    this.activacionesTotal = activacionesTotal;
  }

  public int getActivacionesTotal() {
    return activacionesTotal;
  }

  public void setMontoTotal(double montoTotal) {
    this.montoTotal = montoTotal;
  }

  public double getMontoTotal() {
    return montoTotal;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

}