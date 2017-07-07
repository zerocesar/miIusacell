package mx.com.iusacell.services.miusacell.call;

import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.WalletsVO;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceSCobro {

	public String aplicaCobro(final String dn, final int cantidad, final int idAjuste) throws ServiceException {

		String url = ResourceAccess.getParametroFromXML("urlSCobros");			 	
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : aplicaCobro");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     cantidad               : " + cantidad);
		Logger.write("     idAjuste               : " + idAjuste);

		StringBuilder sXMLRequest = new StringBuilder();				
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		OracleProcedures oracleProcedures = new OracleProcedures();
		String sResponse = "";
		String contrasena = "2XOXKiqwxTNe";
		String login = "SCOBROSMIUNEFON1";
		String idApp = "COMUN";
		int modulo = 7;	
		try {
			contrasena = oracleProcedures.getValorParametro(156);
		} catch (Exception e) {
			contrasena = "2XOXKiqwxTNe";
		}
		
		try {
			login = oracleProcedures.getValorParametro(157);
		} catch (Exception e) {
			login = "SCOBROSMIUNEFON1";
		}

		try
		{								  
			sXMLRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ws=\"http://ws.cobros.midtelco.iusacell.com.mx\" xmlns:xsd=\"http://comun.midtelco.iusacell.com.mx/xsd\">");
			sXMLRequest.append("<soap:Header/>");
			sXMLRequest.append("<soap:Body>");
			sXMLRequest.append("<ws:aplicaCobro>");
			sXMLRequest.append("<ws:mdn>"+dn+"</ws:mdn>");
			sXMLRequest.append("<ws:cantidad>"+cantidad+"</ws:cantidad>");
			sXMLRequest.append("<ws:idAjuste>"+idAjuste+"</ws:idAjuste>");
			sXMLRequest.append("<ws:usuario>");					
			sXMLRequest.append("<xsd:contrasena>"+contrasena+"</xsd:contrasena>");					
			sXMLRequest.append("<xsd:id>0</xsd:id>");
			sXMLRequest.append("<xsd:idAplicacion>"+idApp+"</xsd:idAplicacion>");
			sXMLRequest.append("<xsd:login>"+login+"</xsd:login>");
			sXMLRequest.append("<xsd:modulo>"+modulo+"</xsd:modulo>");
			sXMLRequest.append("</ws:usuario>");
			sXMLRequest.append("</ws:aplicaCobro>");
			sXMLRequest.append("</soap:Body>");
			sXMLRequest.append("</soap:Envelope>");

			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));

			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : "+ Utilerias.pintaLogRequest(sResponse));

		}
		catch(Exception e)
		{
			Logger.write("     Detail        : " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return sResponse;

	}	
	public String reversarCobro(final String dn, final int cantidad,final int idCobro, final List<WalletsVO> wallets) throws ServiceException {

		String url = ResourceAccess.getParametroFromXML("urlSCobros");			 	
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : reversoCobro");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     cantidad               : " + cantidad);
		Logger.write("     idCobro                : " + idCobro);

		StringBuilder sXMLRequest = new StringBuilder();				
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		OracleProcedures oracleProcedures = new OracleProcedures();
		String sResponse = "";
		String contrasena = "2XOXKiqwxTNe";
		String login = "SCOBROSMIUNEFON1";
		String idApp = "COMUN";
		int modulo = 7;				

		try {
			contrasena = oracleProcedures.getValorParametro(156);
		} catch (Exception e) {
			contrasena = "2XOXKiqwxTNe";
		}
		
		try {
			login = oracleProcedures.getValorParametro(157);
		} catch (Exception e) {
			login = "SCOBROSMIUNEFON1";
		}
		
		try
		{								  
			sXMLRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ws=\"http://ws.cobros.midtelco.iusacell.com.mx\" xmlns:xsd=\"http://comun.midtelco.iusacell.com.mx/xsd\">");
			sXMLRequest.append("<soap:Header/>");
			sXMLRequest.append("<soap:Body>");
			sXMLRequest.append("<ws:reversoCobro>");

			sXMLRequest.append("<ws:mdn>"+dn+"</ws:mdn>");
			//<!--Zero or more repetitions:-->
			if(wallets != null && wallets.size() > 0)
			{
				for(WalletsVO wallet : wallets)
				{
					sXMLRequest.append("<ws:wallets>");				
					sXMLRequest.append("<xsd:cantidad>"+wallet.getCantidad()+"</xsd:cantidad>");
					sXMLRequest.append("<xsd:id>"+wallet.getId()+"</xsd:id>");
					sXMLRequest.append("<xsd:vigencia>"+wallet.isVigencia()+"</xsd:vigencia>");
					sXMLRequest.append("<xsd:unidad>"+wallet.getUnidad()+"</xsd:unidad>");
					sXMLRequest.append("</ws:wallets>");
				}
			}
			sXMLRequest.append("<ws:usuario>");			
			sXMLRequest.append("<xsd:contrasena>"+contrasena+"</xsd:contrasena>");			
			sXMLRequest.append("<xsd:id>0</xsd:id>");
			sXMLRequest.append("<xsd:idAplicacion>"+idApp+"</xsd:idAplicacion>");
			sXMLRequest.append("<xsd:login>"+login+"</xsd:login>");
			sXMLRequest.append("<xsd:modulo>"+modulo+"</xsd:modulo>");
			sXMLRequest.append("</ws:usuario>");
			sXMLRequest.append("<ws:idCobro>"+idCobro+"</ws:idCobro>");
			sXMLRequest.append("</ws:reversoCobro>");
			sXMLRequest.append("</soap:Body>");
			sXMLRequest.append("</soap:Envelope>");

			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));

			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : "+ Utilerias.pintaLogRequest(sResponse));

		}
		catch(Exception e)
		{
			Logger.write("     Detail        : " + e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return sResponse;

	}

}
