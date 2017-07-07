package mx.com.iusacell.services.miusacell.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;

public class Formatter {

  private static DecimalFormatSymbols unusualSymbols;
  private static DecimalFormat        formatoMoneda;
  static {
    unusualSymbols = new DecimalFormatSymbols();
    unusualSymbols.setDecimalSeparator('.');
    unusualSymbols.setGroupingSeparator(',');
    formatoMoneda = new DecimalFormat("###.##", unusualSymbols);
  }

  public double roundTwoDecimals(final double number) {
    return Double.valueOf(formatoMoneda.format(number));
  }
  public String timerFormatter(String timeSUM) {
    while (timeSUM.length() <= 3)
      timeSUM = "0" + timeSUM;
    return timeSUM;
  }
  public String formatoMoneda(final String number) {
    String formatedNumber = "0";
    if (isNumeric(number)) formatedNumber = formatoMoneda.format(Double.parseDouble(number));
    return formatedNumber;
  }
  private static boolean isNumeric(final String cadena) {
    try {
      Double.parseDouble(cadena.replace(".", ""));
      return true;
    }
    catch (NumberFormatException e) {
      return false;
    }
  }
  
  public static String pintaLogCadenasLargas(final String dato){
    if(dato != null && dato.length() > 0){
     if(dato.length() > 15)
       return dato.substring(0,10);
    return dato;
    }
    return "";

  }
  
  public static boolean validaContainsArreglo(final String cadenaParsear, final String busqueda){
    boolean respuesta = false;
    
    if(cadenaParsear != null && !cadenaParsear.trim().equals("")){
      String[] datos = cadenaParsear.split(",");
      if(datos.length > 0){
        for (String perfil : datos) {
          if(perfil != null){
            if(perfil.trim().equals(busqueda))
                return true;
          }
        }
      }
      else{
        if(cadenaParsear.equals(busqueda))
          return true;
      }
    }
    return respuesta;
  }
  
  public static String limpiaException(final String cadena){
    String resultado = "";
    if(cadena != null && !cadena.trim().equals("")){
      Pattern pattern = Pattern.compile("(.+?)\n");
      Matcher match = pattern.matcher(cadena);
      if(match.find()){
        if(match.group(1) != null)
          resultado = match.group(1);
      }
    }
    if(!resultado.equals(""))
      resultado = cadena;
    return resultado;
  }
  
  public String[] getParametrosConexion() throws ServiceException{
	    String[] parametros = new String[3];
	    String ulrJDBC = null;
	    String userBD = null;
	    String passBD = null;
	    String ipBD = null;
	    String schemaBD = null;
	    
	    String conexionProductiva = ResourceAccess.getParametroFromXML("conexionProductiva");
	    String parametrosConexion = ResourceAccess.getParametroFromXML("parametrosConexion");
	    String[] parametrosSplit = parametrosConexion.split("\\|");
	    if(parametrosSplit.length == 4){
	      ipBD = parametrosSplit[0];
	      schemaBD = parametrosSplit[1];
	      userBD = parametrosSplit[2];
	      passBD = parametrosSplit[3];
	    }
	    
	    if(conexionProductiva.equals("1")){
	      /** produccion  **/
	    	if(ipBD != null){
	    		String[] ipsProduccion = ipBD.split("-");
	    		//ulrJDBC = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST = (FAILOVER = ON)(ADDRESS = (PROTOCOL = TCP)(HOST = " + ipsProduccion[0] + ")(PORT = 1521))(ADDRESS = (PROTOCOL = TCP)(HOST = " + ipsProduccion[1] + ")(PORT = 1521)))(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = " + schemaBD +")))";
	    		//	      ulrJDBC = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" + ipsProduccion[0] + ")(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SID=" + schemaBD +")))";
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
