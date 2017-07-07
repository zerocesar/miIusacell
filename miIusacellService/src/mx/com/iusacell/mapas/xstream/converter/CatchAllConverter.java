package mx.com.iusacell.mapas.xstream.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CatchAllConverter implements Converter {

  @SuppressWarnings("unchecked")
  public boolean canConvert(Class clazz) {
    return true;
  }

  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {}

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    return null;
  }

}