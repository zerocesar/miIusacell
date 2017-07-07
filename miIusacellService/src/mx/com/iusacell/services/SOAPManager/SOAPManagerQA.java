package mx.com.iusacell.services.SOAPManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.Util;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

import com.ibm.wsdl.util.IOUtils;

public class SOAPManagerQA 
{
    public String sURLWebService        = "";
    public int                  CONNTIMEOUT        = 60000;
    public int                  CONNREQUESTTIMEOUT = 60000;
    public int                  CONNSOTIMEOUT      = 60000;
    public String sSoapAction           = "";

    private RequestConfig requestConfig = null;
    private CloseableHttpClient client            = null;
    private Document document;
    
    public boolean createSOAPManager(final String sURL,final MensajeLogBean mensajeLog)throws ServiceException
    {
        OracleProcedures oracle = new OracleProcedures();
        
        try{ CONNTIMEOUT = Integer.parseInt(oracle.getValorParametro(204)); }
        catch(Exception e){ CONNTIMEOUT = 60000;}
        
        try{ CONNREQUESTTIMEOUT = Integer.parseInt(oracle.getValorParametro(205)); }
        catch(Exception e){ CONNREQUESTTIMEOUT = 60000;}
        
        try{ CONNSOTIMEOUT = Integer.parseInt(oracle.getValorParametro(206)); }
        catch(Exception e){ CONNSOTIMEOUT = 60000;}
        finally{oracle = null;}
        boolean bresp = false;
        try
        {
//          /** CON PROXY **/
//          HttpHost tmpProxy = new HttpHost("10.203.0.207", 8080);
//          //HttpHost tmpProxy = new HttpHost("10.188.177.113", 8080);
//          AuthScope tmpAuthScope = new AuthScope(tmpProxy);
//          BasicCredentialsProvider tmpCredentialsProvider = new BasicCredentialsProvider();
//          //Credentials tmpCredentials = new UsernamePasswordCredentials("iusacell\\dgaliciap", "DAVIDGALICIAPOLO1018");
//          Credentials tmpCredentials = new UsernamePasswordCredentials("iusacell\\fmartinezm", "continuacontinua3");
//          //Credentials tmpCredentials = new UsernamePasswordCredentials("iusacell\\clunal", "restriccion7539");
//          tmpCredentialsProvider.setCredentials(tmpAuthScope, tmpCredentials);
//
//          RequestConfig tmpRequestConfig = RequestConfig.custom()
//          .setConnectTimeout(6000000)
//          .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
//          .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
//          .build();
//
//          client = HttpClients.custom()
//          .setDefaultRequestConfig(tmpRequestConfig)
//          .setProxy(tmpProxy)
//          .setDefaultCredentialsProvider(tmpCredentialsProvider)
//          .build();

            /** SIN PROXY **/
            requestConfig = RequestConfig.custom().setConnectTimeout(CONNTIMEOUT).setConnectionRequestTimeout(CONNREQUESTTIMEOUT).build();
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(CONNSOTIMEOUT).build();
            client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultSocketConfig(socketConfig).build();
            
            sURLWebService  = sURL; 
            bresp           = true;
            Logger.write("   + Iniciando SOAPManager  + ");
        }
        catch(Exception e)
        {
            Logger.write("No se pudo inicializar la clase SOAPManager");
            Logger.write("Message : " + e.getMessage());
            bresp           = false;
//          mensajeLog      = null;
            requestConfig   = null;
            client          = null;
//          mensajeLog      = null;
            sURLWebService  = null; 

            throw new ServiceException(e.getMessage()); 
        }
        finally
        {
        }
        return bresp;
    }
    
    @SuppressWarnings("deprecation")
    public String sendRequestWithXML(final String sXMLRequest,final MensajeLogBean mensajeLog) throws ServiceException, IOException
    {
        String sResponse    = "";
        long timeIni = 0;
        InputStream isResponse  = null;
        HttpPost httppost = null;
        Reader reader = null;
        CloseableHttpResponse response = null;

        try
        {           
            if (sURLWebService != null || !sURLWebService.equals("") || sURLWebService.length() > 0)
            {
                httppost        = new HttpPost(sURLWebService);
                StringEntity se         = new StringEntity(sXMLRequest,HTTP.UTF_8);
                //se.setContentType("charset=UTF-8");
                
                if(sSoapAction != null && !sSoapAction.equals("") && sSoapAction.length() > 0)
                    httppost.setHeader("SOAPAction",sSoapAction);
    
                se.setContentType("text/xml");  
                httppost.setHeader("Content-Type","application/soap+xml;charset=UTF-8");
                httppost.setEntity(se);
                Logger.write("   + Enviando operacion     + ");
                
                //*************************************
                //Enviamos la peticion al servicio web
                timeIni = System.currentTimeMillis();
                response = client.execute(httppost);
                //*************************************
                Logger.timeWs(sURLWebService, "     Tiempo ejecucion ws    : " + Util.tipoRespuesta(timeIni));
                
                //*************************************
                //Obtenemos la respuesta del servicio web           
                isResponse = response.getEntity().getContent();
                InputStream in = isResponse;
                reader = new InputStreamReader(in);
                sResponse = IOUtils.getStringFromReader(reader);
            }
        }
        catch (Exception e)
        {
            Logger.write("Message : " + e.getMessage());
            throw new ServiceException (e.getMessage());
        }finally{
            if(isResponse != null)
            {
                isResponse.close();
            }
            if(reader != null)
            {
                reader.close();
            }
            if(httppost != null)
            {
                httppost.releaseConnection();
            }
            if(response != null && response.getEntity() != null)
            {
                EntityUtils.consume(response.getEntity());
                response.close();
            }
        }
        return sResponse;
    }
    
    public Document getDocument() 
    {
        return document;
    }

    public void setDocument(final Document document) 
    {
        this.document = document;
    }
}
