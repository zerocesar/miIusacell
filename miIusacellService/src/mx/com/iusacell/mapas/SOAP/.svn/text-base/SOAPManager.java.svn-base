package mx.com.iusacell.mapas.SOAP;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ServiceException;

import mx.com.iusacell.mapas.log.MensajeLog;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ibm.wsdl.util.IOUtils;

public class SOAPManager {

  public String         sURLWebService    = "";
  public String         sSoapAction       = "";
  public int            CONNECTIONTIMEOUT = 60000;
  private RequestConfig requestConfig     = null;
  private HttpClient    client            = null;

  public boolean createSOAPManager(String sURL) throws ServiceException {
    MensajeLog.write("   *** L l a m a d a  a  W e b  S e r v i c e  S O A P");
    MensajeLog.write("   WSDL                :: " + sURL);
    boolean bresp = false;
    try {
      /** CON PROXY **/
      //      HttpHost host = new HttpHost("10.189.48.205", 80);
      //      HttpHost host = new HttpHost("10.203.0.207", 8080);
      //      requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTIONTIMEOUT).setProxy(host).build();
      /** SIN PROXY **/
      requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTIONTIMEOUT).build();
      client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
      sURLWebService = sURL;
      bresp = true;
    } catch (Exception e) {
      requestConfig = null;
      client = null;
      sURLWebService = null;
      bresp = false;
      throw new ServiceException(e.getMessage());
    } finally {}
    return bresp;
  }

  @SuppressWarnings("deprecation")
  public String sendRequestWithXML(String sXMLRequest) throws ServiceException, IOException {
    Long timeINI = System.currentTimeMillis();
    String sResponse = "";
    Reader reader = null;
    HttpPost httppost = null;
    InputStream isResponse = null;
    HttpResponse response = null;
    try {
      if (sURLWebService != null || !sURLWebService.equals("") || sURLWebService.length() > 0) {
        httppost = new HttpPost(sURLWebService);
        StringEntity se = new StringEntity(sXMLRequest, HTTP.UTF_8);
        if (sSoapAction != null && !sSoapAction.equals("") && sSoapAction.length() > 0) {
          httppost.setHeader("SOAPAction", sSoapAction);
        }
        se.setContentType("text/xml");
        httppost.setHeader("Content-Type", "text/xml;charset=utf-8");
        httppost.setEntity(se);
        /************************ PETICION *******************/
        response = client.execute(httppost);
        /*********************** RESPUESTA *******************/
        isResponse = response.getEntity().getContent();
        InputStream in = isResponse;
        reader = new InputStreamReader(in, "UTF-8");
        sResponse = IOUtils.getStringFromReader(reader);
        /*********************** EXCEPTIONS ******************/
        ExceptionHandle(sResponse);
        /*****************************************************/
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      if (isResponse != null) {
        isResponse.close();
      }
      if (reader != null) {
        reader.close();
      }
      if (httppost != null) {
        httppost.releaseConnection();
      }
      if (response != null && response.getEntity() != null) {
        EntityUtils.consume(response.getEntity());
      }
    }
    MensajeLog.write("   <<<                 :: SOAP", timeINI);
    return sResponse;
  }

  private void ExceptionHandle(String xml) throws ServiceException {
    String tipo = null;
    String msg = null;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
      factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
      DocumentBuilder builder = factory.newDocumentBuilder();
      InputSource src = new InputSource(new StringReader(xml));
      Document doc = builder.parse(src);
      NodeList faultList = doc.getElementsByTagName("soap:Fault");
      Node fault = faultList.item(0);
      if (fault != null) {
        Element fElement = (Element) fault;
        tipo = fElement.getElementsByTagName("faultcode").item(0).getTextContent();
        msg = fElement.getElementsByTagName("faultstring").item(0).getTextContent();
      }
      if (msg != null && !msg.isEmpty() && !msg.equals("")) {
        throw new ServiceException("[" + tipo + "] " + msg);
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
  }

}