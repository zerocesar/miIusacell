package mx.com.iusacell.mapas.xstream.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import mx.com.iusacell.mapas.log.MensajeLog;
import mx.com.iusacell.mapas.vo.GeoVO;

import java.util.List;

public class GeoVOConverter implements Converter {

  @SuppressWarnings("unchecked")
  public boolean canConvert(Class clazz) {
    return clazz.equals(GeoVO.class);//true;
  }

  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

  }

  @SuppressWarnings("unchecked")
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    GeoVO geo = new GeoVO();
    reader.moveDown();
    MensajeLog.debug(reader.getNodeName() + " = " + reader.getValue());
    geo.setId(Integer.parseInt(reader.getValue()));
    reader.moveUp();
    reader.moveDown();
    MensajeLog.debug(reader.getNodeName() + " = " + reader.getValue());
    geo.setDescripcion(reader.getValue());
    reader.moveUp();
    reader.moveDown();
    MensajeLog.debug(reader.getNodeName() + " = " + reader.getValue());
    geo.setBounds(reader.getValue());
    reader.moveUp();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if ("geografia".equals(reader.getNodeName())) {
        List<GeoVO> geografia = (List<GeoVO>) context.convertAnother(geo, List.class);
        geo.setGeografia(geografia);
      } else if ("poligonos".equals(reader.getNodeName())) {
        List<List<String>> poligonos = (List<List<String>>) context.convertAnother(geo, List.class);
        geo.setPoligonos(poligonos);
      }
      reader.moveUp();
    }
    return geo;
  }

}