package mx.com.iusacell.services.miiusacell.database.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;

public class OracleConnection {

  private static OracleConnectionPool poolBankiusa = null;
  private static OracleConnectionPoolConsumos poolConsumos = null;
  private static OracleConnectionPoolConsumosReplica poolConsumosReplica = null;
  private static OracleConnectionPoolServicios poolConsumosServicios = null;
  private static OracleConnectionPoolOfertaComercial poolOfertaComercial = null;
  private static OracleConnectionPoolPortal poolPortal = null;
  private static OracleConnectionPoolBUCADM poolBUCADM = null;
  
  public static Connection getConnection(final MensajeLogBean mensajeLog) throws ServiceException {
	    Logger.write("     Abriendo conexion a BD  : ");
        int activeConnections = 0;
        Connection connection = null;  
        
        if (poolBankiusa != null) activeConnections = poolBankiusa.getNumActiveConnections();
        
        if (activeConnections >= 150) poolBankiusa = null;
        String path = System.getProperty("user.home");
        File file = new File(path + "/miIusacellService.xml");
        Logger.write("     Path Conexion          : " + file.getAbsolutePath());
        if(!file.exists()){
            Logger.write("     Detail  : No se encontro archivo de conexion a BD ");
            throw new ServiceException("Ocurrio un error al cargar la informacion");
          }
        if (poolBankiusa == null) poolBankiusa = new OracleConnectionPool(1,file);
        try {
          connection = poolBankiusa.getDataSource().getConnection();
        }
        catch (SQLException e) {
        	Logger.write("     Detail  : (SQLException) " + e.getMessage());
          throw new javax.xml.rpc.ServiceException(e.getMessage());
        }     
      return connection;
  }
  
  public static Connection getConnectionConsumos(final MensajeLogBean mensajeLog) throws ServiceException {	  
   
    String consultaBiccarReplica = "0";  
    OracleConnection conexion = new OracleConnection();
    Connection connection = null;      
    try
    {
    	OracleProcedures oraclep = new OracleProcedures();
    	consultaBiccarReplica = oraclep.getValorParametro(130);    	
    }
    catch (Exception e) {		
	}
    
    if(consultaBiccarReplica.equals("1"))
    {
    	Logger.write("     Abriendo conexion a BD ConsumosReplica  : ");
    	connection = conexion.conexionDBConsumosReplica(mensajeLog);
    }
    else
    {
    	Logger.write("     Abriendo conexion a BD Consumos  : ");
    	connection = conexion.conexionDBConsumos(mensajeLog);
    }
               
      return connection;
  }
  
  private Connection conexionDBConsumos(final MensajeLogBean mensajeLog) throws ServiceException
  {
	  int activeConnections = 0;
	  Connection connection = null;  
      if (poolConsumos != null) activeConnections = poolConsumos.getNumActiveConnections();
      if (activeConnections >= 150) poolConsumos = null;
      String path = System.getProperty("user.home");
      File file = new File(path + "/miIusacellService.xml");
      Logger.write("     Path Conexion          : " + file.getAbsolutePath());
      if(!file.exists()){
          Logger.write("     Detail  : No se encontro archivo de conexion a BD ");
          throw new ServiceException("Ocurrio un error al cargar la informacion");
        }
      if (poolConsumos == null) poolConsumos = new OracleConnectionPoolConsumos(2,file);
      try {
        connection = poolConsumos.getDataSource().getConnection();
      }
      catch (SQLException e) {
      	Logger.write("     Detail  : (SQLException) " + e.getMessage());
        throw new javax.xml.rpc.ServiceException(e.getMessage());
      }     
    return connection;
  }
  
  private Connection conexionDBConsumosReplica(final MensajeLogBean mensajeLog) throws ServiceException
  {	  
	  int activeConnections = 0;
	  Connection connection = null;  
      if (poolConsumosReplica != null) activeConnections = poolConsumosReplica.getNumActiveConnections();
      if (activeConnections >= 150) poolConsumosReplica = null;
      String path = System.getProperty("user.home");
      File file = new File(path + "/miIusacellService.xml");
      Logger.write("     Path Conexion          : " + file.getAbsolutePath());
      if(!file.exists()){
          Logger.write("     Detail  : No se encontro archivo de conexion a BD ");
          throw new ServiceException("Ocurrio un error al cargar la informacion");
        }
      if (poolConsumosReplica == null) poolConsumosReplica = new OracleConnectionPoolConsumosReplica(2,file);
      try {
        connection = poolConsumosReplica.getDataSource().getConnection();
      }
      catch (SQLException e) {
      	Logger.write("     Detail  : (SQLException) " + e.getMessage());
        throw new javax.xml.rpc.ServiceException(e.getMessage());
      }     
    return connection;
  }
  
  public static Connection conexionServicios(final MensajeLogBean mensajeLog) throws ServiceException
  {
	  int activeConnections = 0;
	  Connection connection = null;  
      if (poolConsumosServicios != null) activeConnections = poolConsumosServicios.getNumActiveConnections();
      if (activeConnections >= 150) poolConsumosServicios = null;
      String path = System.getProperty("user.home");
      File file = new File(path + "/miIusacellService.xml");
      Logger.write("     Path Conexion          : " + file.getAbsolutePath());
      if(!file.exists()){
          Logger.write("     Detail  : No se encontro archivo de conexion a BD ");
          throw new ServiceException("Ocurrio un error al cargar la informacion");
        }
      if (poolConsumosServicios == null) poolConsumosServicios = new OracleConnectionPoolServicios(2,file);
      try {
        connection = poolConsumosServicios.getDataSource().getConnection();
      }
      catch (SQLException e) {
      	Logger.write("     Detail  : (SQLException) " + e.getMessage());
        throw new javax.xml.rpc.ServiceException(e.getMessage());
      }     
    return connection;
  }
  
   public static Connection conexionDBOferta(final MensajeLogBean mensajeLog) throws ServiceException
  {	   
	  Logger.write("     Abriendo conexion a BD OfertaComercial : ");
	  int activeConnections = 0;
	  Connection connection = null;  
      if (poolOfertaComercial != null) activeConnections = poolOfertaComercial.getNumActiveConnections();
      if (activeConnections >= 150) poolOfertaComercial = null;
      String path = System.getProperty("user.home");
      File file = new File(path + "/miIusacellService.xml");
      Logger.write("     Path Conexion          : " + file.getAbsolutePath());
      if(!file.exists()){
    	  Logger.write("     Detail  : No se encontro archivo de conexion a BD ");
          throw new ServiceException("Ocurrio un error al cargar la informacion");
        }
      if (poolOfertaComercial == null) poolOfertaComercial = new OracleConnectionPoolOfertaComercial(1,file);
      try {
        connection = poolOfertaComercial.getDataSource().getConnection();
      }
      catch (SQLException e) {
    	  Logger.write("     Detail  : (SQLException) " + e.getMessage());
        throw new javax.xml.rpc.ServiceException(e.getMessage());
      }     
    return connection;
  }
   public static Connection conexionDBPORTAL(final MensajeLogBean mensajeLog) throws ServiceException
   {	   
 	  Logger.write("     Abriendo conexion a BD Portal : ");
 	  int activeConnections = 0;
 	  Connection connection = null;  
       if (poolPortal != null) activeConnections = poolPortal.getNumActiveConnections();
       if (activeConnections >= 150) poolPortal = null;
       String path = System.getProperty("user.home");
       File file = new File(path + "/miIusacellService.xml");
       Logger.write("     Path Conexion          : " + file.getAbsolutePath());
       if(!file.exists()){
     	  Logger.write("     Detail  : No se encontro archivo de conexion a BD ");
           throw new ServiceException("Ocurrio un error al cargar la informacion");
         }
       if (poolPortal == null) poolPortal = new OracleConnectionPoolPortal(1,file);
       try {
         connection = poolPortal.getDataSource().getConnection();
       }
       catch (SQLException e) {
     	  Logger.write("     Detail  : (SQLException) " + e.getMessage());
         throw new javax.xml.rpc.ServiceException(e.getMessage());
       }     
     return connection;
   }
   
   public static Connection getOracleConnectionBUCADM() throws Exception {
	   	Properties propertiesConnection = new Properties();
	    InputStream inputStream = null;
	    Connection conn=null; 
	    String path = System.getProperty("user.home");
	    File file = new File(path + "/miIusacellService.xml");
	    try{
		    inputStream = new FileInputStream(file);
		    propertiesConnection.loadFromXML(inputStream);
		   
		    String driver = propertiesConnection.getProperty("driverBUCADM");
		    String url = propertiesConnection.getProperty("urlBUCADM");
		    String username = propertiesConnection.getProperty("userBUCADM");
		    String password = propertiesConnection.getProperty("passwordBUCADM");
	
		    Class.forName(driver); // load Oracle driver
		    conn = DriverManager.getConnection(url, username, password);
	    }
	       catch (SQLException e) {
	     	  Logger.write("     Detail  : (SQLException) BUCADM " + e.getMessage());
	         throw new javax.xml.rpc.ServiceException(e.getMessage());
	    }	
	    return conn;
	  }
   
   public static Connection conexionDBBUCADM() throws ServiceException
   {	   
 	  Logger.write("     Abriendo conexion a BD BUCADM : ");
 	  int activeConnections = 0;
 	  Connection connection = null;  
       if (poolBUCADM != null) activeConnections = poolBUCADM.getNumActiveConnections();
       if (activeConnections >= 150) poolBUCADM = null;
       String path = System.getProperty("user.home");
       File file = new File(path + "/miIusacellService.xml");
       Logger.write("     Path Conexion          : " + file.getAbsolutePath());
       if(!file.exists()){
     	  Logger.write("     Detail  : No se encontro archivo de conexion a BD ");
           throw new ServiceException("Ocurrio un error al cargar la informacion");
         }
       if (poolBUCADM == null) poolBUCADM = new OracleConnectionPoolBUCADM(file);
       try {
         connection = poolBUCADM.getDataSource().getConnection();
       }
       catch (SQLException e) {
     	  Logger.write("     Detail  : (SQLException) BUCADM " + e.getMessage());
         throw new javax.xml.rpc.ServiceException(e.getMessage());
       }     
     return connection;
   }
   
}