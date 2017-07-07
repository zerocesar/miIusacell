package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.ReprogramacionVO;
import mx.com.iusacell.services.miusacell.call.CallServiceActivacion;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceMsisdn;
import mx.com.iusacell.services.miusacell.call.CallServiceReprogramacion;
import mx.com.iusacell.services.miusacell.call.CallServiceSuscribir;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class Reprogramacion {

	public static ReprogramacionVO flujo(final String tecnologia, final String t_msisdn) throws ServiceException
    {
    	
    	CallServiceReprogramacion Callreprogramacion = new CallServiceReprogramacion();
    	ReprogramacionVO reprogramacionVo = new ReprogramacionVO();
    	CallServiceSuscribir subscribir = new CallServiceSuscribir();
    	CallServiceMsisdn  callServiceMsisdn = new CallServiceMsisdn();
    	CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
    	DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		CallServiceActivacion consultaNumeros = new CallServiceActivacion();
    	String sResponse = "";
    	String dealerName = "";
    	String t_imsi = "";
    	String idOperador = "";
    	
            if(tecnologia==null || tecnologia.isEmpty())
            {
                throw new ServiceException("tecnologia no puede ir vacio");
            }
            if(t_msisdn==null || t_msisdn.isEmpty())
            {
               throw new ServiceException("t_msisdn no puede ir vacio");
            }
            
            try
    		{
            	try{
            		sResponse = focalizacion.focalizacion(t_msisdn);
            		if(sResponse != null && !sResponse.equals("")){
            			descripcion = ParseXMLFile.parseFocalizacion(sResponse);
            		}
            	}catch (ServiceException e) {
            		descripcion = new DetalleFocalizacionVO();
            	}
					
            	if(descripcion.getServicio() != null && descripcion.getServicio().equals("80") && descripcion.getSubservicio() != null && descripcion.getSubservicio().equals("80")){
            		Logger.write("   Reprogramacion plataforma ETAK");

            		if(t_msisdn != null && !t_msisdn.equals("")){
            			sResponse = callServiceMsisdn.msisdn(t_msisdn);
            			if(sResponse != null && !sResponse.equals(""))
            				dealerName = ParseXMLFile.ParseMsisdn(sResponse);
            		}
            		
            		if(dealerName.toLowerCase().equals("iusacell")){
                		idOperador = "7";
                		sResponse = subscribir.callSuscribir(t_msisdn, "1110000135", "16soiHEa");
                		if(sResponse != null && !sResponse.equals(""))
            				t_imsi = ParseXMLFile.ParseSuscribir(sResponse);
                	}

        			if(dealerName.toLowerCase().equals("unefon")){
        				idOperador = "5";
        				sResponse = subscribir.callSuscribir(t_msisdn, "1110000136", "hY2eRclH");
        				if(sResponse != null && !sResponse.equals(""))
            				t_imsi = ParseXMLFile.ParseSuscribir(sResponse);
        			}


            	}else if(descripcion.getServicio() != null &&  !descripcion.getServicio().equals("0") && !descripcion.getServicio().equals("80")){
            		Logger.write("   Reprogramacion plataforma LEGACY");

            		try{
            			if(descripcion != null && !descripcion.getIdOperador().trim().equals("")){
            				if(descripcion.getIdOperador().equals("7")){
            					idOperador = "7";
            				}else if(descripcion.getIdOperador().equals("5")){
            					idOperador = "5";
            				}
            			}
            		}catch (Exception e) {
            			descripcion = new DetalleFocalizacionVO();
            		}
            		
        				sResponse = consultaNumeros.consultaNumerosPrepago(t_msisdn);
        				if(sResponse != null && !sResponse.equals("")){
        					t_imsi = ParseXMLFile.parseConsultaNumeros(sResponse);
        				}
            	}
				
            	sResponse = Callreprogramacion.reprogramacion("E2EtoOTARep", "--$EnEUser2014", tecnologia, idOperador, t_msisdn, t_imsi);
            	if(sResponse != null && !sResponse.equals(""))
      		       reprogramacionVo = ParseXMLFile.Parsereprogramacion(sResponse);
    		}
    		catch (Exception e)
    		{
    			throw new ServiceException(e.getMessage());
    		}
    		return reprogramacionVo;
    }
}
