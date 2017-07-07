package mx.com.iusacell.mapas.vo;

import java.util.List;

public class DatosEmpleadoVO {

  private int           idGeografia;
  private int           idNivel;
  private List<Integer> supervision;
  private String        nombre;
  private String        puesto;
  private String        caeAsignado;
  private List<Integer> permisos;
  /**********************************/
  private String        imagen64;

  public int getIdGeografia() {
    return idGeografia;
  }

  public void setIdGeografia(int idGeografia) {
    this.idGeografia = idGeografia;
  }

  public int getIdNivel() {
    return idNivel;
  }

  public void setIdNivel(int idNivel) {
    this.idNivel = idNivel;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getCaeAsignado() {
    return caeAsignado;
  }

  public void setCaeAsignado(String caeAsignado) {
    this.caeAsignado = caeAsignado;
  }

  public void setPermisos(List<Integer> permisos) {
    this.permisos = permisos;
  }

  public List<Integer> getPermisos() {
    return permisos;
  }

  public void setPuesto(String puesto) {
    this.puesto = puesto;
  }

  public String getPuesto() {
    return puesto;
  }

  public void setSupervision(List<Integer> supervision) {
    this.supervision = supervision;
  }

  public List<Integer> getSupervision() {
    return supervision;
  }

  public void setImagen64(String imagen64) {
    this.imagen64 = imagen64;
  }

  public String getImagen64() {
    return imagen64;
  }

}
