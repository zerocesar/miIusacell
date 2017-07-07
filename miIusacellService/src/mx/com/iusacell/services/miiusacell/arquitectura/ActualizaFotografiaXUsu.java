package mx.com.iusacell.services.miiusacell.arquitectura;

import java.awt.image.BufferedImage;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.util.*;

public abstract class ActualizaFotografiaXUsu {
	public static String flujo(final String token,final String dn,String fotografia,final int usuarioID)
    {
   	  Logger.write("       ActualizaFotografiaXUsu");
   	  String respuesta = "";
      try
      {
          if(token==null || token.isEmpty())
          {
        	  Logger.write("El token no puede ir vacio");
          }
          if(dn==null || dn.isEmpty())
          {
        	  Logger.write("El dn no puede ir vacio");
          }
          if(fotografia==null||fotografia.isEmpty())
          {
        	  fotografia="0000000000";
          }
          
          if(fotografia.length()> 200000)
          {
        	  float max, ratio;
        		BufferedImage originalImage = Utilerias.decodeToImage(fotografia);
        		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();	
				if(originalImage.getWidth()>originalImage.getHeight()){
					max=originalImage.getWidth();
				}else{
					max=originalImage.getHeight();
				}
				if(max>300){
					ratio=((300*100)/max)/100;
				}else{
					ratio=1;
				}
				BufferedImage resizeImagePng = Utilerias.resizeImage(originalImage,(int)(originalImage.getWidth()*ratio),(int)(originalImage.getHeight()*ratio), type);
				fotografia = Utilerias.encodeToString(resizeImagePng, "png");
          }
            OracleProcedures oracle = new OracleProcedures();
			int codigo = oracle.actualizaFotografiaXUsu(token, dn, fotografia, usuarioID);
			if(codigo == 0)
				respuesta = "Fotografia actualizada correctamente";
      }
      catch(Exception e)
      {
      	  Logger.write("Detail   ::  ServiceException : " +e.getMessage());
      }
      finally
      {
    	  Logger.write(" INFO  ActualizaFotografiaXUsu");
      }
	return respuesta;
    }
}
