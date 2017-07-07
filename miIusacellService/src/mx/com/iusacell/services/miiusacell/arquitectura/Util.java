package mx.com.iusacell.services.miiusacell.arquitectura;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;

import com.iusacell.EncryptStandAlone.Encriptar;

public abstract class Util {
	/**	
	 * @param milisegundosOrigen Recibe como parámetro el número de milisegundos que se convertirán a minutos. 
	 * @return String Regresará una cadena con el formato 00m:00s:00ms
	 */
	public static String tipoRespuesta(long milisegundosOrigen)
	{
		milisegundosOrigen = System.currentTimeMillis() - milisegundosOrigen;
		
		String respuesta = "";
		
		long minutos;
		long segundos;
		long milisegundos;
		
		milisegundos = milisegundosOrigen % 1000;
		segundos = (milisegundosOrigen - milisegundos) / 1000;
		minutos = (segundos - (segundos % 60))/60;
		segundos = segundos % 60;
		
		if(String.valueOf(minutos).length() == 1)
			respuesta += "0" + minutos + "m:";
		else
			respuesta += minutos + "m:";
		
		if(String.valueOf(segundos).length() == 1)
			respuesta += "0" + segundos + "s:";
		else
			respuesta += segundos + "s:";
		
		if(String.valueOf(milisegundos).length() == 1)
			respuesta += "00" +  milisegundos;
		else if (String.valueOf(milisegundos).length() == 2)
			respuesta += "0" +  milisegundos;
		else
			respuesta += milisegundos;
		
		return respuesta + "ms";
	}
	
	public static Date convierteStringADate(final String fechaDDMMYYYY){
		Date fecha = null;
		SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");	
		try {
			fecha = formateadorFecha.parse(fechaDDMMYYYY);
		} catch (ParseException e) {	
			fecha = null;
		} 
		return fecha;
	}
	
	public static boolean isInteger(final String cadena) {	
		  try {
			  Integer.parseInt(cadena);
			  return true;
		  }
		  catch (NumberFormatException e) {
			  return false;
		  }
	  }
	
	public static boolean validaFormatoFecha(final String fechaDDMMYYYY){
		boolean fechaValida = false;
		Date fecha = null;
		SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");	
		try {
			fecha = formateadorFecha.parse(fechaDDMMYYYY);
			if(fecha != null)
				fechaValida = true;
		} catch (ParseException e) {	
			fechaValida = false;
		}
		return fechaValida;
	}
	
	public static String desencriptaTDC(final String tarjetaEncriptada){
		String numeroTDC = "";
		Encriptar encripta = new Encriptar();
		try{
			numeroTDC = encripta.desencriptarTDC(tarjetaEncriptada);
			int longNumTDC = numeroTDC.length();
			if(longNumTDC > 4) numeroTDC = numeroTDC.substring(longNumTDC - 4, longNumTDC);
		}
		catch(Exception e){
			numeroTDC = "";
		}
		return numeroTDC;
	}
	
	public static String[] getParametrosConexion() throws ServiceException{
	    OracleProcedures oracleProcedures = new OracleProcedures();
	    String[] parametros = new String[3];
	    String ulrJDBC      = null;
	    String userBD       = null;
	    String passBD       = null;
	    String ipBD         = null;
	    String schemaBD     = null;
	    
	    String esConexionProductiva = oracleProcedures.getValorParametro(20);
	    String parametrosConexion = oracleProcedures.getValorParametro(21);
	    String[] parametrosSplit = parametrosConexion.split("\\|");
	    if(parametrosSplit.length == 4){
	      ipBD     = parametrosSplit[0];
	      schemaBD = parametrosSplit[1];
	      userBD   = parametrosSplit[2];
	      passBD   = parametrosSplit[3];
	    }
	    
	    if(esConexionProductiva.equals("1")){
	      /** produccion  **/
	    	if(ipBD != null){
	    		String[] ipsProduccion = ipBD.split("-");
	    		ulrJDBC = "jdbc:oracle:thin:@(DESCRIPTION = (FAILOVER=on)(ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = " + ipsProduccion[0] + ")(PORT = 1521))(ADDRESS = (PROTOCOL = TCP)(HOST = " + ipsProduccion[1] + ")(PORT = 1521)))(CONNECT_DATA = (FAILOVER_MODE = (TYPE = session)(METHOD = basic)(RETRIES = 180))(SERVER = dedicated)(SERVICE_NAME = " + schemaBD +")))";
	    	}
	    }
	    else{
	      /** test **/
	      ulrJDBC = "jdbc:oracle:thin:@" + ipBD + ":1521:" + schemaBD;     
	    }
	    parametros[0] = ulrJDBC;
	    parametros[1] = userBD;
	    parametros[2] = passBD;
	    
	    return parametros;
	  }  
}