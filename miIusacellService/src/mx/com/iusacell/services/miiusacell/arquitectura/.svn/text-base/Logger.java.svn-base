package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.Random;

public final class Logger
{

    public static void init(final String origen)
    {
        System.out.println(" [INITX] <" + Thread.currentThread().getId()+ "> :: " + origen);
    }

    public static void init(final String origen, final int unidadNegocio, final boolean aplicaSubString)
    {
    	String unidadPrev = "";
		int unidadDeNegocioPrev=0;
		try{
			if (aplicaSubString){
	    		unidadPrev = unidadNegocio+"";
	    		try{
	    			if(unidadPrev.length()>=1){
	    				unidadDeNegocioPrev = Integer.parseInt(unidadPrev.substring(0,1));
	    			}else{
	    				unidadDeNegocioPrev=unidadNegocio;
	    			}
	    		}catch (Exception e) {
	    			unidadDeNegocioPrev=0;
	    		}
	    	}else{
	    		unidadDeNegocioPrev=unidadNegocio;
	    	}
	    	if (unidadDeNegocioPrev==1){
	        	System.out.println(" [INITX] [IUSACELL]<" + Thread.currentThread().getId()+ "> :: " + origen);
	        }else if (unidadDeNegocioPrev==2){
	        	System.out.println(" [INITX] [UNEFON]<" + Thread.currentThread().getId()+ "> :: " + origen);
	        }else{
	        	System.out.println(" [INITX] <" + Thread.currentThread().getId()+ "> :: " + origen);	
	        }
		}catch (Exception e) {
			System.out.println(" [INITX] <" + Thread.currentThread().getId()+ "> :: " + origen);
		}
    }
    
    public static void init(final String origen, final String unidadNegocio)
    {
    	int unidadNegocioId=0;
    	try {
	    	if (unidadNegocio==null||unidadNegocio.equals("")){
	    		System.out.println(" [INITX] <" + Thread.currentThread().getId()+ "> :: " + origen);
	    	}else{
	    		if(unidadNegocio.length()>=1){
	    			unidadNegocioId=Integer.parseInt(unidadNegocio.substring(0,1));
	    		}else{
	    			unidadNegocioId=Integer.parseInt(unidadNegocio);
	    		}
	    		if (unidadNegocioId==1){
	            	System.out.println(" [INITX] [IUSACELL]<" + Thread.currentThread().getId()+ "> :: " + origen);
	            }else if (unidadNegocioId==2){
	            	System.out.println(" [INITX] [UNEFON]<" + Thread.currentThread().getId()+ "> :: " + origen);
	            }else{
	            	System.out.println(" [INITX] <" + Thread.currentThread().getId()+ "> :: " + origen);	
	            }
	    	}
	    }catch (Exception e) {
			System.out.println(" [INITX] <" + Thread.currentThread().getId()+ "> :: " + origen);
		}
    }
    
    
    public static void write(final String texto)
    {
        System.out.println(" [INFO]  <" + Thread.currentThread().getId() + ">      " + texto);
    }

    public static void write(final String texto, final long tiempo_inicial)
    {
        System.out.println(" [INFO]  <" + Thread.currentThread().getId() + ">      " + texto + " :: " + calcula_tiempo_respuesta(tiempo_inicial));
    }
    
    public static void time(final String origen, final String texto)
    {
        System.out.println(" [TIME]  <" + Thread.currentThread().getId() + ">      :: " + origen + " :: " + texto);
    }
    
    public static void timeTx(final String origen, final String texto)
    {
        System.out.println(" [TIMEBD]<" + Thread.currentThread().getId() + ">      :: " + origen + " :: " + texto);
    }
    
    public static void timeWs(final String origen, final String texto)
    {
        System.out.println(" [TIMEWS]<" + Thread.currentThread().getId() + ">      :: " + origen + " :: " + texto);
    }

    public static void error(final String texto)
    {
        System.out.println(" [ERR]   <" + Thread.currentThread().getId() + ">      " + texto);
    }
    
    public static void warn(final String texto, final String origen)
    {
        System.out.println(" [WARN]  <" + Thread.currentThread().getId() + ">      :: " + origen + " :: " + texto);
    }
    
    public static void error(final String texto, final String origen)
    {
        System.out.println(" [ERR]   <" + Thread.currentThread().getId() + ">      :: " + origen + " :: " + texto);
    }
    
    public static void end(final String origen, final long tiempo_inicial)
    {
        System.out.println(" [FINTX] <" + Thread.currentThread().getId() + "> :: " + origen + " :: " + calcula_tiempo_respuesta(tiempo_inicial));
    }

    public static void end_err(final String origen, final long tiempo_inicial)
    {
        System.out.println(" [ERR]   <" + Thread.currentThread().getId() + "> :: FINTRANS :: " + origen + " :: " + calcula_tiempo_respuesta(tiempo_inicial));
    }

    public static String calcula_tiempo_respuesta(long tiempo_inicial)
    {
        tiempo_inicial = System.currentTimeMillis() - tiempo_inicial;
        
        String tiempo_de_respuesta = "";
        
        long minutos;
        long segundos;
        long milisegundos;
        
        milisegundos = tiempo_inicial % 1000;
        segundos     = (tiempo_inicial - milisegundos) / 1000;
        minutos      = (segundos - (segundos % 60))/60;
        segundos     = segundos % 60;
        
        if(String.valueOf(minutos).length() == 1)
            tiempo_de_respuesta += "0" + minutos + "m:";
        else
            tiempo_de_respuesta += minutos + "m:";
        
        if(String.valueOf(segundos).length() == 1)
            tiempo_de_respuesta += "0" + segundos + "s:";
        else
            tiempo_de_respuesta += segundos + "s:";
        
        if(String.valueOf(milisegundos).length() == 1)
            tiempo_de_respuesta += "00" +  milisegundos;
        else if (String.valueOf(milisegundos).length() == 2)
            tiempo_de_respuesta += "0" +  milisegundos;
        else
            tiempo_de_respuesta += milisegundos;
        
        return tiempo_de_respuesta + "ms";
    }
    
    public static String id_proceso()
    {
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        
        while ( i < 5)
        {
            char c = (char)r.nextInt(255);
            if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') )
            {
                cadenaAleatoria += c;
                i ++;
            }
        }
        
        r = null;
        
        return cadenaAleatoria;
    }
}