package mx.com.iusacell.mapas.vo;

import java.util.List;

public class GeoVO {

  private int                id;
  private String             descripcion;
  private String             bounds;
  private List<List<String>> poligonos;
  private List<GeoVO>        geografia;

  public GeoVO() {
    this.id = 0;
    this.descripcion = "";
    this.bounds = "";
    this.setPoligonos(null);
    this.geografia = null;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getBounds() {
    return bounds;
  }

  public void setBounds(String bounds) {
    this.bounds = bounds;
  }

  public List<GeoVO> getGeografia() {
    return geografia;
  }

  public void setGeografia(List<GeoVO> geografia) {
    this.geografia = geografia;
  }

  public void setPoligonos(List<List<String>> poligonos) {
    this.poligonos = poligonos;
  }

  public List<List<String>> getPoligonos() {
    return poligonos;
  }

}