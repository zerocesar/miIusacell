package mx.com.iusacell.services.miusacell.call;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import mx.com.iusacell.services.SOAPManager.SOAPManagerAxis2;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.util.AesTA;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceAbonoTA {		
	
	public String  abonoTA(final String dn, final double monto, final long secuencia) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlRecargasElectronicas");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : abonoDN - RecargasElectronicas");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     dn                     : " + dn);
		Logger.write("     monto                  : " + monto);
		Logger.write("     secuencia              : " + secuencia);
		

		String sResponse = "";
		String token = "";
		String sucursal = "00018";
		String negocio = "022";
		String phraseToken = "";
		String cadenaToken = "";
		String usrHeader = "";
		String loginHeader = "";
		try{
			final OracleProcedures oracle = new OracleProcedures();
			final String valueParam = oracle.getValorParametro(209);
			final String[] accesos = valueParam.split("\\|");
			if(accesos.length == 4){
				phraseToken = accesos[0];
				cadenaToken = accesos[1];
				usrHeader = accesos[2];
				loginHeader = accesos[3];
			}
		}catch (ServiceException e) {
			Logger.write("     Detail        : (Exception) Al consultar parametros : " + e.getMessage());
		}
		try {
			final AesTA aesToken = new AesTA();
			token = aesToken.encrypt(phraseToken, cadenaToken+dn);
		} catch (Exception e) {
			Logger.write("     Detail        : (Exception) Al crear token: " + e.getMessage());
		}		
		String fechaLocal = gregorianCalendarToString(Calendar.getInstance());
		Logger.write("     sucursal               : " + sucursal);
		Logger.write("     negocio                : " + negocio);
		Logger.write("     token                  : " + token);
		Logger.write("     fechaLocal             : " + fechaLocal);
		
		try
		{
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace bean = fac.createOMNamespace("http://bean.recargase.iusacell.com", "bean");			

			OMElement abonoDN = fac.createOMElement("abonoDN", bean);
			OMElement mdnVo         = fac.createOMElement("mdn", bean);
			OMElement montoVo         = fac.createOMElement("monto", bean);
			OMElement secuenciaVo         = fac.createOMElement("secuencia", bean);
			OMElement negocioVo         = fac.createOMElement("negocio", bean);
			OMElement sucursalVo         = fac.createOMElement("sucursal", bean);
			OMElement fechaLocalVo         = fac.createOMElement("fechaLocal", bean);
			OMElement tokenVo         = fac.createOMElement("token", bean);

			mdnVo.setText(dn);
			montoVo.setText(monto+"");
			secuenciaVo.setText(secuencia+"");
			negocioVo.setText(negocio);
			sucursalVo.setText(sucursal);
			fechaLocalVo.setText(fechaLocal);
			tokenVo.setText(token);
			
			abonoDN.addChild(mdnVo);
			abonoDN.addChild(montoVo);
			abonoDN.addChild(secuenciaVo);
			abonoDN.addChild(negocioVo);
			abonoDN.addChild(sucursalVo);
			abonoDN.addChild(fechaLocalVo);
			abonoDN.addChild(tokenVo);
			
			/**  Header **/		
			OMNamespace wsse = fac.createOMNamespace("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse");			
			OMElement securityHeader = fac.createOMElement("Security", wsse);
			OMElement usernameToken = fac.createOMElement("UsernameToken", wsse);
			OMElement username = fac.createOMElement("Username", wsse);
			OMElement password = fac.createOMElement("Password", wsse);
			password.addAttribute("Type","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText",null );
						
			username.setText(usrHeader);
			password.setText(loginHeader);
			
			usernameToken.addChild(username);
			usernameToken.addChild(password);
			securityHeader.addChild(usernameToken);
									
			
			Logger.write("     SOAPHEADERXML          : " + Utilerias.pintaLogRequest(securityHeader.toString()));
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(abonoDN.toString()));
			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, abonoDN, securityHeader);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			Logger.write("     Detail        : (Exception) " + e.getMessage());
			throw new ServiceException("Ocurrio un Error");
		}
		return sResponse;
	}
	
	public String  reversoTA(final long secuencia) throws ServiceException 
	{
		String url = ResourceAccess.getParametroFromXML("urlRecargasElectronicas");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : reverso - RecargasElectronicas");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");		
		Logger.write("     secuencia              : " + secuencia);
		

		String sResponse = "";
		String token = "";
		String sucursal = "00018";
		String negocio = "022";
		String phraseToken = "";
		String cadenaToken = "";
		String usrHeader = "";
		String loginHeader = "";
		try{
			final OracleProcedures oracle = new OracleProcedures();
			final String valueParam = oracle.getValorParametro(209);
			final String[] accesos = valueParam.split("\\|");
			if(accesos.length == 4){
				phraseToken = accesos[0];
				cadenaToken = accesos[1];
				usrHeader = accesos[2];
				loginHeader = accesos[3];
			}
		}catch (ServiceException e) {
			Logger.write("     Detail        : (Exception) Al consultar parametros : " + e.getMessage());
		}
		try {
			final AesTA aesToken = new AesTA();
			token = aesToken.encrypt(phraseToken, cadenaToken+secuencia);
		} catch (Exception e) {
			Logger.write("     Detail        : (Exception) Al crear token: " + e.getMessage());
		}				
		Logger.write("     sucursal               : " + sucursal);
		Logger.write("     negocio                : " + negocio);
		Logger.write("     token                  : " + token);		
		
		try
		{
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace bean = fac.createOMNamespace("http://bean.recargase.iusacell.com", "bean");			

			OMElement reverso = fac.createOMElement("reverso", bean);			
			OMElement secuenciaVo         = fac.createOMElement("secuencia", bean);
			OMElement negocioVo         = fac.createOMElement("negocio", bean);
			OMElement sucursalVo         = fac.createOMElement("sucursal", bean);			
			OMElement tokenVo         = fac.createOMElement("token", bean);
			
			secuenciaVo.setText(secuencia+"");
			negocioVo.setText(negocio);
			sucursalVo.setText(sucursal);			
			tokenVo.setText(token);
						
			reverso.addChild(secuenciaVo);
			reverso.addChild(negocioVo);
			reverso.addChild(sucursalVo);			
			reverso.addChild(tokenVo);
			
			/**  Header **/		
			OMNamespace wsse = fac.createOMNamespace("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse");			
			OMElement securityHeader = fac.createOMElement("Security", wsse);
			OMElement usernameToken = fac.createOMElement("UsernameToken", wsse);
			OMElement username = fac.createOMElement("Username", wsse);
			OMElement password = fac.createOMElement("Password", wsse);
			password.addAttribute("Type","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText",null );
						
			username.setText(usrHeader);
			password.setText(loginHeader);
			
			usernameToken.addChild(username);
			usernameToken.addChild(password);
			securityHeader.addChild(usernameToken);
									
			
			Logger.write("     SOAPHEADERXML          : " + Utilerias.pintaLogRequest(securityHeader.toString()));
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(reverso.toString()));
			//********************************
			//Enviamos la peticion al servidor con AXIS2 - axis2
			//********************************
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, reverso, securityHeader);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     sResponse              : " + Utilerias.pintaLogRequest(sResponse));
		}
		catch(Exception e)
		{
			Logger.write("     Detail        : (Exception) " + e.getMessage());
			throw new ServiceException("Ocurrio un Error");
		}
		return sResponse;
	}
	
	private String gregorianCalendarToString(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS");
        
        return dateFormat.format(calendar.getTime());
  }	
}
