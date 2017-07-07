package mx.com.iusacell.services.miiusacell.arquitectura;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang3.ArrayUtils;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.OperadorVO;
import mx.com.iusacell.services.miusacell.call.CallServiceConsultasNum;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public class ConsultaOperador {

	public String getIdOperador(final String dn) throws ServiceException{		
		String idOperador = "";
		String consultaNuevaImp; 
		final OracleProcedures oracle = new OracleProcedures();
		try{
			consultaNuevaImp = oracle.getValorParametro(272);
		}catch (Exception e) {
			consultaNuevaImp = ""; 
		}
		if("1".equals(consultaNuevaImp)){
			idOperador = getOperadorV2(dn);
		}else{
			idOperador = getOperadorV1(dn);
		}
		return idOperador;
	}
	
	protected String getOperadorV2(final String dnUser) throws ServiceException{
		String idOperador = "";
		final CallServiceConsultasNum consultaOperadorWS = new CallServiceConsultasNum();
		final OracleProcedures oracle = new OracleProcedures();
		String responseWS;
		String[] idosInternos;
		String[] idosIusaUne;
		try{
			idosInternos = oracle.getValorParametro(273).split(",");
			idosIusaUne = oracle.getValorParametro(274).split(",");
		}catch (Exception e) {
			idosInternos = "131,132,134,190".split(",");
			idosIusaUne = "131,132,134".split(",");
		}
		
		responseWS = consultaOperadorWS.getInfoOwner(dnUser);
		final OperadorVO respuestaInfoOwner = ParseXMLFile.parseInfoOwner(responseWS);
		if(isValidObject(respuestaInfoOwner))
		{
			Logger.write("   + Respuesta getInfoOwner  + ");
			Logger.write("   + IdOperadorComp   : " + respuestaInfoOwner.getIdOperadorComp());
			Logger.write("   + IdO              : " + respuestaInfoOwner.getIdO());
			Logger.write("   + IdA              : " + respuestaInfoOwner.getIdA());
			Logger.write("   + operaInt         : " + respuestaInfoOwner.getOperaInt());
			for(final String ido : idosInternos){
				if(ido.equals(respuestaInfoOwner.getIdO())){
					if("2".equals(respuestaInfoOwner.getOperaInt())){
						idOperador = "280";
					}else{
						final int posIdoIusaUne = ArrayUtils.indexOf(idosIusaUne,respuestaInfoOwner.getIdO()); 
						if(posIdoIusaUne > -1){
							responseWS = consultaOperadorWS.getNumberByDN(dnUser);
							final OperadorVO responseNumberByDN = ParseXMLFile.parseGetNumberByDN(responseWS);	
							if(isValidObject(responseNumberByDN))
							{		
								Logger.write("   + Respuesta getNumberByDN  + ");
								Logger.write("   + IdOperadorComp   : " + responseNumberByDN.getIdOperadorComp());
								Logger.write("   + Portado          : " + responseNumberByDN.getPortado());
								Logger.write("   + IdEstatusNum     : " + responseNumberByDN.getIdEstatusNum());
								idOperador = responseNumberByDN.getIdOperadorComp();
							}
						}else{
							idOperador = respuestaInfoOwner.getIdO();
						}
					}
					break;
				}
			}
		}
		
		return idOperador;
	}

	protected String getOperadorV1(final String dnUser) throws ServiceException{
		final CallServiceConsultasNum consultaOperadorWS = new CallServiceConsultasNum();
		String responseWS;
		String idOperador = "";
		boolean consultaInfoOwner = false;

		responseWS = consultaOperadorWS.getNumberByDN(dnUser);
		final OperadorVO responseNumberByDN = ParseXMLFile.parseGetNumberByDN(responseWS);				

		if(isValidObject(responseNumberByDN))
		{		
			Logger.write("   + Respuesta getNumberByDN  + ");
			Logger.write("   + IdOperadorComp   : " + responseNumberByDN.getIdOperadorComp());
			Logger.write("   + Portado          : " + responseNumberByDN.getPortado());
			Logger.write("   + IdEstatusNum     : " + responseNumberByDN.getIdEstatusNum());

			if("8".equalsIgnoreCase(responseNumberByDN.getIdEstatusNum())){
				consultaInfoOwner = true;
			}else{
				if("0".equalsIgnoreCase(responseNumberByDN.getPortado())){					
					idOperador = validaOperador(responseNumberByDN);
				}else{
					consultaInfoOwner = true;
				}
			}
		}
		else{
			consultaInfoOwner = true;
		}

		if(consultaInfoOwner)
		{
			responseWS = consultaOperadorWS.getInfoOwner(dnUser);
			final OperadorVO respuestaInfoOwner = ParseXMLFile.parseInfoOwner(responseWS);

			if(isValidObject(respuestaInfoOwner))
			{
				Logger.write("   + Respuesta getInfoOwner  + ");
				Logger.write("   + IdOperadorComp   : " + respuestaInfoOwner.getIdOperadorComp());
				Logger.write("   + IdO              : " + respuestaInfoOwner.getIdO());
				Logger.write("   + IdA              : " + respuestaInfoOwner.getIdA());
				idOperador = validaOperadorPortado(respuestaInfoOwner, responseNumberByDN);
			}
		}

		return idOperador;
	}
	private String validaOperador(final OperadorVO info)
	{
		if(info.getIdOperadorComp() != null &&
				(info.getIdOperadorComp().equals("190") || 
						info.getIdOperadorComp().equals("7") ||
						info.getIdOperadorComp().equals("5")
				)
		)
		{
			return info.getIdOperadorComp();
		}
		else
		{
			return "";
		}
	}

	private String validaOperadorPortado(final OperadorVO info, final OperadorVO responseNumberDN)
	{
		if(isValidString(info.getIdO()) && info.getIdO().equals("190"))
		{	
			return info.getIdO();
		}		
		else if(isValidString(info.getIdOperadorComp()) && 
				(info.getIdOperadorComp().equals("5") || info.getIdOperadorComp().equals("7")))
		{
			if(info.getIdOperadorComp().equals("5") 
					&& isValidObject(responseNumberDN) 
					&& isValidString(responseNumberDN.getIdOperadorComp()) 
					&& !"8".equals(responseNumberDN.getIdEstatusNum()))
			{
				return responseNumberDN.getIdOperadorComp();
			}
			else{
				return info.getIdOperadorComp();
			}
		}
		else
		{
			Logger.write("   + El IdO retornado no es permitido  + ");
			return "";
		}
	}

	private boolean isValidObject(final Object objeto){
		boolean isValid = true;
		if(objeto == null){
			isValid = false;
		}

		return isValid;
	}

	private boolean isValidString(final String cadena){
		boolean isValid = true;
		if(cadena == null || cadena.trim().equalsIgnoreCase("")){
			isValid = false;
		}

		return isValid;
	}
	
	protected boolean dnValidoParaRegistro(String dnx)
	{
	    boolean validoParaRegistro = true;
	    Long inicio = System.currentTimeMillis();
        String path = System.getProperty("user.home") + java.io.File.separator + "noRegistro_2.txt";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        
        try
        {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String linea;
            while((linea = bufferedReader.readLine()) != null){
                if(linea.trim().equalsIgnoreCase(dnx)){
                    validoParaRegistro = false;
                    break;
                }
            }
            
            fileReader.close();
            bufferedReader.close();
            
        }catch (Exception e) {
            validoParaRegistro = true;
        }
        
        Logger.write("   + El DN " + dnx + (validoParaRegistro?" es valido para registro":"NO es valido para registro (en BlackList)") + " ::: " + Logger.calcula_tiempo_respuesta(inicio));
        
        return validoParaRegistro;
	}
}
