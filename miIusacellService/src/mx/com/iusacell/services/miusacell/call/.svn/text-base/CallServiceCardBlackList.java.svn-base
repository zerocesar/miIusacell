package mx.com.iusacell.services.miusacell.call;

import java.util.Date;
import javax.xml.rpc.ServiceException;

import com.iusacell.EncryptStandAlone.Encriptar;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.util.AlgoritmoAes;
import mx.com.iusacell.services.miusacell.util.Utilerias;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public class CallServiceCardBlackList {
	public boolean consultaCardBlackList(String numTarjeta) throws ServiceException
	{
		OracleProcedures oracle = new OracleProcedures();
		String url = "";
		try{
			url = oracle.getValorParametro(163);
		}catch (Exception e) {
			url = "";
			throw new ServiceException("No se pudo obtener la url del service cardBlackList de la BD");
		}
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : consultaCardBlackList");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     NumTarjeta             : "+ numTarjeta );
		
		//Decencripta de aes128 y encripta en TDC
		AlgoritmoAes algo=new AlgoritmoAes();
		Encriptar encripta=new Encriptar();
	
		StringBuffer sXMLRequest = new StringBuffer();
		String sResponse 	= "";
		SOAPManager soapManager 		= new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		boolean respuesta=false;
		try{
			//Decencripta de aes128 y encripta en TDC
			numTarjeta = algo.decrypt(numTarjeta, "GrUPoSaLInaSsACv".getBytes());
			numTarjeta = encripta.encriptarTDC(numTarjeta);
			
		}catch (Exception e) { 
			throw new ServiceException("Ocurrio un error al encriptar TDC para consultar BlackList");
		}
		
		try
		{
		    sXMLRequest.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://services.blacklist.iusacell.com.mx/'>");
		    sXMLRequest.append(    "<soapenv:Header/>");
		    sXMLRequest.append(    "<soapenv:Body>");
		    sXMLRequest.append(        "<ser:cardBlackList>");
		    sXMLRequest.append(            "<card>"+numTarjeta+"</card>");
		    sXMLRequest.append(        "</ser:cardBlackList>");
		    sXMLRequest.append(    "</soapenv:Body>");
		    sXMLRequest.append("</soapenv:Envelope>");
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));
				
			//********************************
			//Enviamos la peticion al servidor
			//********************************
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));
			respuesta=Boolean.parseBoolean(ParseXMLFile.parseCardBlackList(sResponse));
		}
		catch(Exception e)
		{
			sResponse = "";
			throw new ServiceException(e.getLocalizedMessage());
		}
		finally
		{
		    sXMLRequest = null;
		}
		
		return respuesta;
	}
}
