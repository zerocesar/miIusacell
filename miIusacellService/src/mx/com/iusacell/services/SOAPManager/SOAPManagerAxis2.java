package mx.com.iusacell.services.SOAPManager;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.Util;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;

public class SOAPManagerAxis2 {

    public String send(final String url, final OMElement sendRequest) throws ServiceException{
        EndpointReference targetEPR = new EndpointReference(url);
        OMElement result = null;
        String response = "";
        long timeIni = 0;
        ServiceClient sender;
        try {
            Logger.write("   + Enviando operacion     + ");
            timeIni = System.currentTimeMillis();
            Options options = new Options();
            
//          /*Con Proxy*/
//          HttpTransportProperties.ProxyProperties pp = new HttpTransportProperties().new ProxyProperties();
//          //pp.setProxyName("10.189.48.205");
//          pp.setProxyName("10.203.0.207");
//          //pp.setProxyPort(80);
//          pp.setProxyPort(8080);
//          options.setProperty(HTTPConstants.PROXY,pp);
//          /*Con Proxy*/
            
            options.setTo(targetEPR);
            
            // creates a new connection manager and a http client object
            MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
            HttpClient httpClient = new HttpClient(httpConnectionManager);

            options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Boolean.TRUE);
            options.setProperty(HTTPConstants.CACHED_HTTP_CLIENT, httpClient);
            sender = new ServiceClient();
            sender.setOptions(options);
            result = sender.sendReceive(sendRequest);
            
            Logger.timeWs(url, "     Tiempo ejecucion ws A2 : " + Util.tipoRespuesta(timeIni));
            httpConnectionManager.closeIdleConnections(0);
            httpConnectionManager.shutdown();
        } catch (AxisFault axisFault) {
            throw new ServiceException(axisFault.getMessage());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        
        if(result != null){
            response = result.toString();
        }

        return response;
    }
    
    public String send(final String url, final OMElement sendRequest, OMElement header) throws ServiceException{
        EndpointReference targetEPR = new EndpointReference(url);
        OMElement result = null;
        String response = "";
        long timeIni = 0;
        ServiceClient sender;
        try {
            Logger.write("   + Enviando operacion     + ");
            timeIni = System.currentTimeMillis();
            Options options = new Options();
            
//          /*Con Proxy*/
//          HttpTransportProperties.ProxyProperties pp = new HttpTransportProperties().new ProxyProperties();
//          //pp.setProxyName("10.189.48.205");
//          pp.setProxyName("10.203.0.207");
//          //pp.setProxyPort(80);
//          pp.setProxyPort(8080);
//          options.setProperty(HTTPConstants.PROXY,pp);
//          /*Con Proxy*/
            
            options.setTo(targetEPR);
            
            // creates a new connection manager and a http client object
            MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
            HttpClient httpClient = new HttpClient(httpConnectionManager);

            options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Boolean.TRUE);
            options.setProperty(HTTPConstants.CACHED_HTTP_CLIENT, httpClient);

            sender = new ServiceClient();
            sender.setOptions(options);
            sender.addHeader(header);
            result = sender.sendReceive(sendRequest);
            
            Logger.timeWs(url, "     Tiempo ejecucion ws A2 : " + Util.tipoRespuesta(timeIni));
            
//          sender.cleanupTransport();
//          sender.cleanup();
          
          httpConnectionManager.closeIdleConnections(0);
          httpConnectionManager.shutdown();

        } catch (AxisFault axisFault) {
            throw new ServiceException(axisFault.getMessage());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        
        if(result != null){
            response = result.toString();
        }

        return response;
    }
}
