package mx.com.iusacell.mapas.consume;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ServiceException;

import mx.com.iusacell.mapas.SOAP.SOAPManager;
import mx.com.iusacell.mapas.consume.vo.EmpleadoVO;
import mx.com.iusacell.mapas.consume.vo.RespuestaLlaveVO;
import mx.com.iusacell.mapas.consume.vo.ValidacionTokenVO;
import mx.com.iusacell.mapas.log.MensajeLog;
import mx.com.iusacell.mapas.masivo.model.OracleProcedures;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class VerificaUsuario {

  SOAPManager          soapManager = new SOAPManager();
  private String       sXMLRequest = "";
  private String       sResponse   = "";
  private final String errorParse  = "CAMPOS FALTANTES O DE TIPO INCORRECTO EN LA RESPUESTA DEL SERVICIO: ";

  private String getEndPoint() throws ServiceException {
    String retorno = "";
    try {
      retorno = OracleProcedures.ObtieneParamConfig(2012).getValor();
    } catch (Exception e) {
      retorno = "http://10.203.24.211:39008/VerificaUsuarioService/svAutenticacion";
    }
    return retorno;
  }

  /************************************************************************************************************************************/

  public RespuestaLlaveVO validaLlaveMaestraIusacell(String user, String pass, Integer idApp, String keyApp, String ipSol, Integer numEmpleado, String llaveMaestra, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    RespuestaLlaveVO retorno = new RespuestaLlaveVO();
    try {

      MensajeLog.write("   *** L l a m a d a  a  W e b  S e r v i c e  V e r i f i c a   U s u a r i o");
      MensajeLog.write("   Operacion           :: validaLlaveMaestraIusacell");
      MensajeLog.write("   user                :: -PROTEGIDO-");
      MensajeLog.write("   pass                :: -PROTEGIDO-");
      MensajeLog.write("   idApp               :: " + idApp);
      MensajeLog.write("   keyApp              :: " + keyApp);
      MensajeLog.write("   ipSol               :: " + ipSol);
      MensajeLog.write("   numEmpleado         :: " + numEmpleado);
      MensajeLog.write("   llaveMaestra        :: " + llaveMaestra);
      MensajeLog.write("   tokenUTC            :: " + tokenUTC);

      soapManager.createSOAPManager(getEndPoint());
      sXMLRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.auth.iusacell.com.mx\">" + "<soapenv:Header/>" + "<soapenv:Body>" + "<ser:ValidaLlaveMaestraIusacell>" + "<ser:in0>" + user + "</ser:in0>" + "<ser:in1>" + pass + "</ser:in1>" + "<ser:in2>" + idApp + "</ser:in2>" + "<ser:in3>" + keyApp + "</ser:in3>" + "<ser:in4>" + ipSol + "</ser:in4>" + "<ser:in5>" + numEmpleado + "</ser:in5>" + "<ser:in6>" + llaveMaestra + "</ser:in6>" + "<ser:in7>" + tokenUTC + "</ser:in7>" + "</ser:ValidaLlaveMaestraIusacell>" + "</soapenv:Body>" + "</soapenv:Envelope>";

      sResponse = soapManager.sendRequestWithXML(sXMLRequest);
      soapManager = new SOAPManager();
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      InputSource is = new InputSource(new StringReader(sResponse));
      Document d = builder.parse(is);
      NodeList nodeList = d.getElementsByTagName("ns1:ValidaLlaveMaestraIusacellResponse");
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node validaNode = nodeList.item(0);
        if (validaNode.getNodeType() == Node.ELEMENT_NODE) {
          Element validaElement = (Element) validaNode;

          retorno.setAuth(Boolean.parseBoolean(validaElement.getElementsByTagName("auth").item(0).getTextContent()));
          retorno.setCambioLlave(Boolean.parseBoolean(validaElement.getElementsByTagName("cambioLlave").item(0).getTextContent()));
          retorno.setFechaCad(validaElement.getElementsByTagName("fechaCad").item(0).getTextContent());
          retorno.setMensaje(validaElement.getElementsByTagName("mensaje").item(0).getTextContent());
        }
      }

    } catch (IllegalArgumentException e) {
      throw new ServiceException(errorParse + e.getMessage());
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    MensajeLog.write("   <<<                 :: Procesado en", timeINI);
    return retorno;
  }

  public ValidacionTokenVO validaTokenIusacell(String user, String pass, Integer idApp, String keyApp, String ipSol, Integer numEmpleado, String tokenBaz, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    ValidacionTokenVO retorno = new ValidacionTokenVO();

    MensajeLog.write("   *** L l a m a d a  a  W e b  S e r v i c e  V e r i f i c a   U s u a r i o");
    MensajeLog.write("   Operacion           :: validaTokenIusacell");
    MensajeLog.write("   user                :: -PROTEGIDO-");
    MensajeLog.write("   pass                :: -PROTEGIDO-");
    MensajeLog.write("   idApp               :: " + idApp);
    MensajeLog.write("   keyApp              :: " + keyApp);
    MensajeLog.write("   ipSol               :: " + ipSol);
    MensajeLog.write("   numEmpleado         :: " + numEmpleado);
    MensajeLog.write("   tokenBaz            :: " + tokenBaz);
    MensajeLog.write("   tokenUTC            :: " + tokenUTC);

    try {

      soapManager.createSOAPManager(getEndPoint());
      sXMLRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.auth.iusacell.com.mx\">" + "<soapenv:Header/>" + "<soapenv:Body>" + "<ser:ValidaTokenIusacell>" + "<ser:in0>" + user + "</ser:in0>" + "<ser:in1>" + pass + "</ser:in1>" + "<ser:in2>" + idApp + "</ser:in2>" + "<ser:in3>" + keyApp + "</ser:in3>" + "<ser:in4>" + ipSol + "</ser:in4>" + "<ser:in5>" + numEmpleado + "</ser:in5>" + "<ser:in6>" + tokenBaz + "</ser:in6>" + "<ser:in7>" + tokenUTC + "</ser:in7>" + "</ser:ValidaTokenIusacell>" + "</soapenv:Body>" + "</soapenv:Envelope>";

      sResponse = soapManager.sendRequestWithXML(sXMLRequest);
      soapManager = new SOAPManager();
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      InputSource is = new InputSource(new StringReader(sResponse));
      Document d = builder.parse(is);
      NodeList nodeList = d.getElementsByTagName("ns1:ValidaTokenIusacellResponse");
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node valTokenNode = nodeList.item(0);
        if (valTokenNode.getNodeType() == Node.ELEMENT_NODE) {
          Element valTokenElement = (Element) valTokenNode;
          retorno.setAuth(Boolean.parseBoolean(valTokenElement.getElementsByTagName("auth").item(0).getTextContent()));
          retorno.setMensaje(valTokenElement.getElementsByTagName("mensaje").item(0).getTextContent());
        }
      }
    } catch (IllegalArgumentException e) {
      throw new ServiceException(errorParse + e.getMessage());
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    MensajeLog.write("   <<<                 :: Procesado en", timeINI);
    return retorno;
  }

  public List<EmpleadoVO> ObtieneDatosEmpleadoIusacell(String user, String pass, Integer idApp, String keyApp, String ipSol, List<Integer> lstEmp, String tokenUTC) throws ServiceException {
    List<EmpleadoVO> retorno = new ArrayList<EmpleadoVO>();
    Long timeINI = System.currentTimeMillis();

    try {

      MensajeLog.write("   *** L l a m a d a  a  W e b  S e r v i c e  V e r i f i c a   U s u a r i o");
      MensajeLog.write("   Operacion           :: ObtieneDatosEmpleadoIusacell");
      MensajeLog.write("   user                :: -PROTEGIDO-");
      MensajeLog.write("   pass                :: -PROTEGIDO-");
      MensajeLog.write("   idApp               :: " + idApp);
      MensajeLog.write("   keyApp              :: " + keyApp);
      MensajeLog.write("   ipSol               :: " + ipSol);
      MensajeLog.write("   lstEmp              :: " + lstEmp.size());
      MensajeLog.write("   tokenUTC            :: " + tokenUTC);

      soapManager.createSOAPManager(getEndPoint());
      sXMLRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.auth.iusacell.com.mx\">" + "<soapenv:Header/>" + "<soapenv:Body>" + "<ser:ObtieneDatosEmpleadoIusacell>" + "<ser:in0>" + user + "</ser:in0>" + "<ser:in1>" + pass + "</ser:in1>" + "<ser:in2>" + idApp + "</ser:in2>" + "<ser:in3>" + keyApp + "</ser:in3>" + "<ser:in4>" + ipSol + "</ser:in4>" + "<ser:in5>";
      for (int i = 0; i < lstEmp.size(); i++) {
        sXMLRequest += "<ser:int>" + lstEmp.get(i) + "</ser:int>";
      }
      sXMLRequest += "</ser:in5>" + "<ser:in6>" + tokenUTC + "</ser:in6>" + "</ser:ObtieneDatosEmpleadoIusacell>" + "</soapenv:Body>" + "</soapenv:Envelope>";
      sResponse = soapManager.sendRequestWithXML(sXMLRequest);

      /**************************** RESPUESTA ********************************/
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      InputSource is = new InputSource(new StringReader(sResponse));
      Document d = builder.parse(is);

      NodeList nodeListResp = d.getElementsByTagName("ns2:EmpleadoVO");
      for (int i = 0; i < nodeListResp.getLength(); i++) {
        Node obtieneEmpNode = nodeListResp.item(i);
        if (obtieneEmpNode.getNodeType() == Node.ELEMENT_NODE) {
          EmpleadoVO empleado = new EmpleadoVO();
          Element empElemnt = (Element) obtieneEmpNode;

          try {
            empleado.setIdCentroCostos(Integer.parseInt(empElemnt.getElementsByTagName("idCentroCostos").item(0).getTextContent()));
          } catch (Exception e) {}

          empleado.setIdDivision(empElemnt.getElementsByTagName("idDivision").item(0).getTextContent());

          try {
            empleado.setIdFuncion(Integer.parseInt(empElemnt.getElementsByTagName("idFuncion").item(0).getTextContent()));
          } catch (Exception e) {}

          try {
            empleado.setIdUniOrg(Integer.parseInt(empElemnt.getElementsByTagName("idUniOrg").item(0).getTextContent()));
          } catch (Exception e) {}
          try {
            empleado.setNoEmpleado(Integer.parseInt(empElemnt.getElementsByTagName("noEmpleado").item(0).getTextContent()));
          } catch (Exception e) {}
          empleado.setNombre(empElemnt.getElementsByTagName("nombre").item(0).getTextContent());
          try {
            empleado.setPosicion(Integer.parseInt(empElemnt.getElementsByTagName("posicion").item(0).getTextContent()));
          } catch (Exception e) {}
          empleado.setPuesto(empElemnt.getElementsByTagName("puesto").item(0).getTextContent());
          try {
            empleado.setSup_noEmpleado(Integer.parseInt(empElemnt.getElementsByTagName("sup_noEmpleado").item(0).getTextContent()));
          } catch (Exception e) {}
          empleado.setSup_nombre(empElemnt.getElementsByTagName("sup_nombre").item(0).getTextContent());
          try {
            empleado.setSup_posicion(Integer.parseInt(empElemnt.getElementsByTagName("sup_posicion").item(0).getTextContent()));
          } catch (Exception e) {}
          try {
            empleado.setSup_puesto(empElemnt.getElementsByTagName("sup_puesto").item(0).getTextContent());
          } catch (Exception e) {}
          try {
            empleado.setUniOrg(empElemnt.getElementsByTagName("uniOrg").item(0).getTextContent());

          } catch (Exception e) {}
          try {
            empleado.setCentroCostos(empElemnt.getElementsByTagName("centroCostos").item(0).getTextContent());
          } catch (Exception e) {}
          try {
            empleado.setDivision(empElemnt.getElementsByTagName("division").item(0).getTextContent());
          } catch (Exception e) {}
          try {
            empleado.setFuncion(empElemnt.getElementsByTagName("funcion").item(0).getTextContent());
          } catch (Exception e) {}
          retorno.add(empleado);
        }
      }
    } catch (IllegalArgumentException e) {
      throw new ServiceException(errorParse + e.getMessage());
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    MensajeLog.write("   <<<                 :: Procesado en", timeINI);
    return retorno;
  }

  public String obtieneFotoEmpleado(String user, String pass, Integer idApp, String keyApp, String ipSol, String numEmpleado, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    String retorno = "";

    MensajeLog.write("   *** L l a m a d a  a  W e b  S e r v i c e  V e r i f i c a   U s u a r i o");
    MensajeLog.write("   Operacion           :: ObtieneDatosEmpleadoIusacell");
    MensajeLog.write("   user                :: -PROTEGIDO-");
    MensajeLog.write("   pass                :: -PROTEGIDO-");
    MensajeLog.write("   idApp               :: " + idApp);
    MensajeLog.write("   keyApp              :: " + keyApp);
    MensajeLog.write("   ipSol               :: " + ipSol);
    MensajeLog.write("   numEmpleado         :: " + numEmpleado);
    MensajeLog.write("   tokenUTC            :: " + tokenUTC);

    try {
      soapManager.createSOAPManager(getEndPoint());
      sXMLRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.auth.iusacell.com.mx\">" + "<soapenv:Header/>" + "<soapenv:Body>" + "<ser:ObtieneFotoEmpleadoIusacell>" + "<ser:in0>" + user + "</ser:in0>" + "<ser:in1>" + pass + "</ser:in1>" + "<ser:in2>" + idApp + "</ser:in2>" + "<ser:in3>" + keyApp + "</ser:in3>" + "<ser:in4>" + ipSol + "</ser:in4>" + "<ser:in5>" + numEmpleado + "</ser:in5>" + "<ser:in6>" + tokenUTC + "</ser:in6>" + "</ser:ObtieneFotoEmpleadoIusacell>" + "</soapenv:Body>" + "</soapenv:Envelope>";
      
      sResponse = soapManager.sendRequestWithXML(sXMLRequest);
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      InputSource is = new InputSource(new StringReader(sResponse));
      Document d = builder.parse(is);

      NodeList nodeListResp = d.getElementsByTagName("ns1:out");
      Node obtieneFotoNode = nodeListResp.item(0);
      if (obtieneFotoNode.getNodeType() == Node.ELEMENT_NODE) {
        Element obtieneFotoElement = (Element) obtieneFotoNode;
        retorno = String.valueOf(obtieneFotoElement.getTextContent());
      }

    } catch (IllegalArgumentException e) {
      throw new ServiceException(errorParse + e.getMessage());
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }

    MensajeLog.write("   <<<                 :: Procesado en", timeINI);
    return retorno;
  }
}
