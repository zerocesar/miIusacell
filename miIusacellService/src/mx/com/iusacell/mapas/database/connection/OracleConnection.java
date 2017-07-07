package mx.com.iusacell.mapas.database.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.rpc.ServiceException;

public class OracleConnection {

  private static OracleConnectionPool poolBankiusa = null;

  public static Connection getConnection(int baseID) throws ServiceException, SQLException {
    int activeConnections = 0;
    Connection connection = null;
    try {
      switch (baseID) {
        case 1: {
          if (poolBankiusa != null)
            activeConnections = poolBankiusa.getNumActiveConnections();
          if (activeConnections >= 10)
            poolBankiusa = null;
          String pathProperties = System.getProperty("user.home");
          pathProperties += File.separator + "MapasService.xml";
          //String pathProperties = "home" + File.separator + "jboss"+File.separator + "MapasService.xml";
          File file = new File(pathProperties);
          if (!file.exists()) {
            throw new ServiceException("Ocurrió un error al cargar la información. No se encontró archivo de conexión a BD : " + file.getAbsolutePath());
          }
          if (poolBankiusa == null)
            poolBankiusa = new OracleConnectionPool(file);
          try {
            connection = poolBankiusa.getDataSource().getConnection();
          } catch (SQLException e) {
            throw new SQLException(e);
          } catch (Exception e) {
            throw new ServiceException(e.getMessage());
          }
          break;
        }
        default: {
          throw new ServiceException("Datos de conexión a la BD no especificados");
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e.getLocalizedMessage(), new ServiceException("hFatalException"));
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    return connection;
  }
}