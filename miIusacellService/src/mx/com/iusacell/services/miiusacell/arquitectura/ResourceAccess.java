package mx.com.iusacell.services.miiusacell.arquitectura;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class ResourceAccess {
	
	public static String getParametroFromXML(final String key) {
		Properties propertiesConnection = new Properties();
		InputStream inputStream = null;
    	try {
    		String path = System.getProperty("user.home");
    		String ruta = path + "/miIusacellService.xml";
	    	File file = new File(ruta);
	    	if(!file.exists())
	    	{
	    		Logger.write("[ERR] No existe el archivo de configuracion en la ruta: " + ruta); 
	    		throw new FileNotFoundException("Archivo de configuracion no existe");
	    	}
	        inputStream = new FileInputStream(file);
	        propertiesConnection.loadFromXML(inputStream);
	        return propertiesConnection.getProperty(key);
    	} catch (Exception e){
    		Logger.write("Ocurrio un detail al leer el archivo xml: " + e.getMessage());
    	}finally{
    		try {
    			if(inputStream != null)
    				inputStream.close();
    		} catch (IOException e) {
    			Logger.write("Ocurrio un detail al leer el archivo xml: " + e.getMessage());
    		}
    	}
    	return "";
    }
}
