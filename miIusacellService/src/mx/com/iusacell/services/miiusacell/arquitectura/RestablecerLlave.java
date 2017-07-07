package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.call.CallServiceMandaMensaje_Mail;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public abstract class RestablecerLlave {
	
	
	public static int flujo(final String dn, final String preguntaSecreta, final String resPreguntaSecreta, final int idServicio, final int usuarioID) throws ServiceException
    {
		OracleProcedures oracle = new OracleProcedures();
		CallServiceMandaMensaje_Mail sensMessage = new CallServiceMandaMensaje_Mail();
		//String compania=(idServicio==1)?"Mi Iusacell":"Mi Unefon";
		String compania="";
		String caracterEspecialSMS="";
		compania = Utilerias.retornacompania(idServicio);
		if (idServicio==4){
			try{
				caracterEspecialSMS=oracle.getValorParametro(180);
				compania=compania.replace("&amp;", caracterEspecialSMS);
			} catch (Exception e) {
				compania="Mi ATT";
			}
		}
		
		int codigo = 0;
		String correoXDn = "";
		int confirmaDatos = -1;
        try
        {
            if(dn==null || dn.isEmpty())
            {
				throw new ServiceException("Parametros de entrada incorrectos  ::  dn no puede ir vacio");
            }

            if(preguntaSecreta==null || preguntaSecreta.isEmpty())
            {
				throw new ServiceException("Parametros de entrada incorrectos  ::  preguntaSecreta no puede ir vacio");
            }
            
            if(resPreguntaSecreta==null || resPreguntaSecreta.isEmpty())
            {
				throw new ServiceException("Parametros de entrada incorrectos  ::  resPreguntaSecreta no puede ir vacio");
            }
            
            confirmaDatos = oracle.ValidaDatos(dn, preguntaSecreta, resPreguntaSecreta, usuarioID);
            
            if(confirmaDatos != 0){
    			throw new ServiceException("Ocurrio un error al restablecer la contraseña");
            }
            
            String nuevaPa = Utilerias.generaPassword();
            
            correoXDn = oracle.restablecerLlave(dn,nuevaPa, usuarioID);
            
            
            
        	if(correoXDn!=null && !correoXDn.equals(""))
        	{
        		String sMail 	= "&lt;br&gt;&lt;br&gt;Usted ha restablecido su contraseña, su nueva contraseña es: "+nuevaPa+"&lt;br&gt;&lt;br&gt;Favor de no responder este correo, ya que se genera automáticamente.";
        		String sSubject = compania + " - Restablecer contraseña";
        		        			
        		sensMessage.enviaMail(sMail, "Buen día", sSubject, correoXDn, idServicio);
        	}else{
    			throw new ServiceException("Ocurrio un error al restablecer la contraseña");	
        	}
        }
        catch(ServiceException e)
        {
			throw new ServiceException(e.getMessage());
        }
        return codigo;
    }
}
