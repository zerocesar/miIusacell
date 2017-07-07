package mx.com.iusacell.mapas.database.connection;

import mx.com.iusacell.mapas.log.MensajeLog;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;
import javax.xml.rpc.ServiceException;

public class OracleConnectionPool {

  private BasicDataSource basicDataSource = null;
  private DataSource      dataSource;

  public OracleConnectionPool(File XMLFilePath) throws ServiceException {
    Properties propertiesConnection = new Properties();
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(XMLFilePath);
      propertiesConnection.loadFromXML(inputStream);
      basicDataSource = new BasicDataSource();
      basicDataSource.setDriverClassName(propertiesConnection.getProperty("driver"));
      basicDataSource.setUsername(propertiesConnection.getProperty("user"));
      basicDataSource.setPassword(propertiesConnection.getProperty("password"));
      basicDataSource.setUrl(propertiesConnection.getProperty("url"));
      if (propertiesConnection.getProperty("maxActive") != null) {
        basicDataSource.setMaxActive(Integer.parseInt(propertiesConnection.getProperty("maxActive")));
      }
      if (propertiesConnection.getProperty("initialSize") != null) {
        basicDataSource.setInitialSize(Integer.parseInt(propertiesConnection.getProperty("initialSize")));
      }
      if (propertiesConnection.getProperty("minIdle") != null) {
        basicDataSource.setMinIdle(Integer.parseInt(propertiesConnection.getProperty("minIdle")));
      }
      if (propertiesConnection.getProperty("maxIdle") != null) {
        basicDataSource.setMaxIdle(Integer.parseInt(propertiesConnection.getProperty("maxIdle")));
      }
      if (propertiesConnection.getProperty("maxWait") != null) {
        basicDataSource.setMaxWait(Integer.parseInt(propertiesConnection.getProperty("maxWait")));
      }
      if (propertiesConnection.getProperty("validationQuery") != null) {
        basicDataSource.setValidationQuery(propertiesConnection.getProperty("validationQuery"));
      }
      basicDataSource.setTestOnBorrow(true);
      basicDataSource.setTestOnReturn(true);
      basicDataSource.setTestWhileIdle(true);
      basicDataSource.setTimeBetweenEvictionRunsMillis(30000);
      dataSource = basicDataSource;
    } catch (Exception e) {
      throw new ServiceException(e.getLocalizedMessage());
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public OracleConnectionPool(String XMLFilePath) throws ServiceException {
    Properties propertiesConnection = new Properties();
    InputStream inputStream = null;
    try {
      inputStream = getClass().getResourceAsStream(XMLFilePath);
      propertiesConnection.loadFromXML(inputStream);
      basicDataSource = new BasicDataSource();
      basicDataSource.setDriverClassName(propertiesConnection.getProperty("driver"));
      basicDataSource.setUsername(propertiesConnection.getProperty("user"));
      basicDataSource.setPassword(propertiesConnection.getProperty("password"));
      basicDataSource.setUrl(propertiesConnection.getProperty("url"));
      if (propertiesConnection.getProperty("maxActive") != null) {
        basicDataSource.setMaxActive(Integer.parseInt(propertiesConnection.getProperty("maxActive")));
      }
      if (propertiesConnection.getProperty("initialSize") != null) {
        basicDataSource.setInitialSize(Integer.parseInt(propertiesConnection.getProperty("initialSize")));
      }
      if (propertiesConnection.getProperty("minIdle") != null) {
        basicDataSource.setMinIdle(Integer.parseInt(propertiesConnection.getProperty("minIdle")));
      }
      if (propertiesConnection.getProperty("maxIdle") != null) {
        basicDataSource.setMaxIdle(Integer.parseInt(propertiesConnection.getProperty("maxIdle")));
      }
      if (propertiesConnection.getProperty("maxWait") != null) {
        basicDataSource.setMaxWait(Integer.parseInt(propertiesConnection.getProperty("maxWait")));
      }
      if (propertiesConnection.getProperty("validationQuery") != null) {
        basicDataSource.setValidationQuery(propertiesConnection.getProperty("validationQuery"));
      }
      basicDataSource.setTestOnBorrow(true);
      basicDataSource.setTestOnReturn(true);
      basicDataSource.setTestWhileIdle(true);
      basicDataSource.setTimeBetweenEvictionRunsMillis(30000);
      dataSource = basicDataSource;
    } catch (Exception e) {
      throw new ServiceException(e.getLocalizedMessage());
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  public DataSource getDataSource() {
    int activeConnections = basicDataSource.getNumActive();
    if (activeConnections >= 8) {
      MensajeLog.warning(basicDataSource.getUrl() + " :: " + basicDataSource.getNumActive() + " Conexiones Activas");
    }
    return dataSource;
  }

  public int getNumActiveConnections() {
    return basicDataSource.getNumActive();
  }
}