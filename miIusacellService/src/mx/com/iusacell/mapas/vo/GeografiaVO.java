package mx.com.iusacell.mapas.vo;

import java.util.List;

public class GeografiaVO {

  private int         version;
  private List<GeoVO> geografia;

  public void setVersion(int version) {
    this.version = version;
  }

  public int getVersion() {
    return version;
  }

  public void setGeografia(List<GeoVO> geografia) {
    this.geografia = geografia;
  }

  public List<GeoVO> getGeografia() {
    return geografia;
  }

}