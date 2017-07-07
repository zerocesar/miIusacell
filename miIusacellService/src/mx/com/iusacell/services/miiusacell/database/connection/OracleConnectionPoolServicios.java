package mx.com.iusacell.services.miiusacell.database.connection;

import mx.com.iusacell.services.miiusacell.arquitectura.Logger;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;

public class OracleConnectionPoolServicios {
  
  private BasicDataSource  basicDataSource = null;
  private DataSource       dataSource;

  public OracleConnectionPoolServicios(final int option, final File XMLFilePath) {
    Properties propertiesConnection = new Properties();
    InputStream inputStream = null;
    try {
    	inputStream = new FileInputStream(XMLFilePath);
      propertiesConnection.loadFromXML(inputStream);
      
      basicDataSource = new BasicDataSource();
      basicDataSource.setDriverClassName(propertiesConnection.getProperty("driverServicios"));
	  basicDataSource.setUsername(propertiesConnection.getProperty("userServicios"));
	  basicDataSource.setPassword(propertiesConnection.getProperty("passwordServicios"));
	  basicDataSource.setUrl(propertiesConnection.getProperty("urlServicios"));  
	  
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
    }
    catch (Throwable e) {
      try {
    	  if(inputStream != null)
    		  inputStream.close();
      }
      catch (IOException e1) {
//        e1.printStackTrace();
        Logger.write("OracleConnectionPoolServicios.OracleConnectionPoolServicios (IOException e1) :: " + e1.getMessage());
      }
//      e.printStackTrace();
      Logger.write("OracleConnectionPoolServicios.OracleConnectionPoolServicios (Throwable) :: " + e.getMessage());
    }finally{
      try {
    	  if(inputStream != null)
    		  inputStream.close();
      }
      catch (IOException e) {
//        e.printStackTrace();
          Logger.write("OracleConnectionPoolServicios.OracleConnectionPoolServicios (IOException) :: " + e.getMessage());
      }
    }
  }
  public DataSource getDataSource() {
    int activeConnections = basicDataSource.getNumActive();
    if (activeConnections >= 150) {
      Logger.write(" [DBA] :: (!) " + basicDataSource.getUrl() + " :: [" + basicDataSource.getNumActive() + "]");
    }
    return dataSource;
  }
  public int getNumActiveConnections() {
    return basicDataSource.getNumActive();
  }
}