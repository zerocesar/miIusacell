package mx.com.iusacell.services.miiusacell.database.connection;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.rpc.ServiceException;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;

public class OracleConnectionJNDI {

	private static Connection getJNDIConnection(final String jndiName) throws ServiceException
	{
		Connection connection = null;      
		DataSource dataSource = null;
		Logger.write("  ++ getDataSource JNDI - Obteniendo DataSource con la referencia: [" + jndiName + "]");
		try {		  		  
			final InitialContext ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup(jndiName);			  
			connection = dataSource.getConnection();
		} catch (NamingException e) {
			Logger.error("     NamingException al obtener dataSource JNDI: " +  e.getLocalizedMessage());  
			throw new ServiceException(e.getMessage());
		} catch (SQLException e) {
			Logger.error("     SQLException : " +  e.getLocalizedMessage());
			throw new ServiceException(e.getLocalizedMessage());
		} 
		return connection;
	}	

	public static Connection getConnectionConsumos(final MensajeLogBean mensajeLog) throws ServiceException {	  

		String consultaBiccarReplica = "0";  
		final OracleConnectionJNDI conexion = new OracleConnectionJNDI();
		Connection connection = null;      
		try
		{
			final OracleProcedures oraclep = new OracleProcedures();
			consultaBiccarReplica = oraclep.getValorParametro(130);    	
		}
		catch (Exception e) {		
		}

		if(consultaBiccarReplica.equals("1"))
		{			
			connection = conexion.conexionDBConsumosReplica(mensajeLog);
		}
		else
		{			
			connection = conexion.conexionDBConsumos(mensajeLog);
		}

		return connection;
	}

	public static Connection getConnection(final MensajeLogBean mensajeLog) throws ServiceException {
		Logger.write("     Abriendo conexion a BD con JNDI : ");        	                      
		return getJNDIConnection("java:/jdbc/IusacellService/IusacellDS");
	}
	
	private Connection conexionDBConsumos(final MensajeLogBean mensajeLog) throws ServiceException
	{	     
		Logger.write("     Abriendo conexion a BD Consumos con JNDI : ");
		return getJNDIConnection("java:/jdbc/IusacellService/ConsumosDS");
	}

	private Connection conexionDBConsumosReplica(final MensajeLogBean mensajeLog) throws ServiceException
	{	      
		Logger.write("     Abriendo conexion a BD ConsumosReplica con JNDI : ");
		return getJNDIConnection("java:/jdbc/IusacellService/ConsumosReplicaDS");
	}	

	public static Connection conexionDBOferta(final MensajeLogBean mensajeLog) throws ServiceException
	{	
		Logger.write("     Abriendo conexion a BD OfertaComercial con JNDI : "); 
		return getJNDIConnection("java:/jdbc/IusacellService/OfertaComercialDS");
	}
	public static Connection conexionDBPORTAL(final MensajeLogBean mensajeLog) throws ServiceException
	{	   
		Logger.write("     Abriendo conexion a BD Portal con JNDI : "); 	      
		return getJNDIConnection("java:/jdbc/IusacellService/PortalDS");
	}	

	public static Connection conexionDBBUCADM() throws ServiceException
	{	   
		Logger.write("     Abriendo conexion a BD BUCADM con JNDI : "); 	   	    
		return getJNDIConnection("java:/jdbc/IusacellService/BUCADMDS");
	}
   
}