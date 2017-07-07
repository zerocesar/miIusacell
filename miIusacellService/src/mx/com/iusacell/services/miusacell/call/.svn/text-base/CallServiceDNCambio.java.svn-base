package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.util.Constantes;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceDNCambio {
	
	public String DNCambio(final String dnActual, final String dnNuevo,final String idCiudad, final String region, final String idOperador) throws ServiceException {
	    StringBuffer sXMLRequest = new StringBuffer();
		String sResponse="";
		
		String url = ResourceAccess.getParametroFromXML("urlsDNCambio");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : DNCambio");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     DNactual               : " + dnActual);
		Logger.write("     DNnuevo                : " + dnNuevo);
		
		OracleProcedures oracle = new OracleProcedures();
		String ver = "0";
		
		try{
			ver = oracle.getValorParametro(11);
		}catch (Exception e) {
            Logger.write("No se encontro parametro");
		}
		
		try{
			
		    if(ver.equalsIgnoreCase("0")){
                sXMLRequest.append("\n<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.dn.telco.iusacell.com.mx\" xmlns:head=\"http://www.example.org/Header\" xmlns:xsd=\"http://vo.bl.telco.iusacell.com.mx/xsd\" xmlns:xsd1=\"http://vo.telco.com.mx/xsd\">\n");
                sXMLRequest.append(   "<soapenv:Header/>\n");
                sXMLRequest.append(   "<soapenv:Body>\n");
                sXMLRequest.append(      "<ser:cambioMDN>\n");
                sXMLRequest.append(         "<ser:Header>\n");
                sXMLRequest.append(            "<head:paisID>1</head:paisID>\n");
                sXMLRequest.append(            "<head:canalID>46</head:canalID>\n");
                sXMLRequest.append(            "<head:sucursalID>98744</head:sucursalID>\n");
                sXMLRequest.append(            "<head:sistemaID>8</head:sistemaID>\n");
                sXMLRequest.append(            "<head:servicioID>17</head:servicioID>\n");
                sXMLRequest.append(         "</ser:Header>\n");
                sXMLRequest.append(         "<ser:cambioDNLNRequest>\n");
                sXMLRequest.append(            "<xsd:acceso>\n");
                sXMLRequest.append(               "<xsd1:idTransaccion>"+Constantes.ACCESO_TRANSACCION_ID+"</xsd1:idTransaccion>\n");
                sXMLRequest.append(               "<xsd1:idUserSaeo>"+Constantes.ACCESO_USERSAEO_ID+"</xsd1:idUserSaeo>\n");
                sXMLRequest.append(               "<xsd1:origen>"+Constantes.ACCESO_ORIGEN+"</xsd1:origen>\n");
                sXMLRequest.append(            "</xsd:acceso>\n");
                sXMLRequest.append(            "<xsd:dnActual>"+dnActual+"</xsd:dnActual>\n");
                sXMLRequest.append(            "<xsd:dnNuevo>"+dnNuevo+"</xsd:dnNuevo>\n");
                sXMLRequest.append(         "</ser:cambioDNLNRequest>\n");
                sXMLRequest.append(      "</ser:cambioMDN>\n");
                sXMLRequest.append(   "</soapenv:Body>\n");
                sXMLRequest.append("</soapenv:Envelope>");
			}else{
                sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.cambiomdn.telco.iusacell.com\" xmlns:head=\"http://www.example.org/Header\" xmlns:xsd=\"http://vo.cambiomdn.telco.iusacell.com/xsd\" xmlns:xsd1=\"http://vo.telco.com.mx/xsd\">");
                sXMLRequest.append(   "<soapenv:Header/>");
                sXMLRequest.append(   "<soapenv:Body>");
                sXMLRequest.append(      "<ser1:cambioMDN xmlns:ser1=\"http://service.dn.telco.iusacell.com.mx\">");
                sXMLRequest.append(         "<ser1:Header>");
                sXMLRequest.append(            "<head:paisID>1</head:paisID>");
                sXMLRequest.append(            "<head:canalID>1</head:canalID>");
                sXMLRequest.append(            "<head:sucursalID>0009</head:sucursalID>");
                sXMLRequest.append(            "<head:sistemaID>8</head:sistemaID>");
                sXMLRequest.append(            "<head:servicioID>17</head:servicioID>");
                sXMLRequest.append(         "</ser1:Header>");
                sXMLRequest.append(         "<ser1:cambioDNLNRequest>");
                sXMLRequest.append(            "<xsd2:acceso xmlns:xsd2=\"http://vo.bl.telco.iusacell.com.mx/xsd\">");
                sXMLRequest.append(               "<xsd1:idTransaccion>1</xsd1:idTransaccion>");
                sXMLRequest.append(               "<xsd1:idUserSaeo>400891</xsd1:idUserSaeo>");
                sXMLRequest.append(               "<xsd1:origen>E2E</xsd1:origen>");
                sXMLRequest.append(            "</xsd2:acceso>");
                sXMLRequest.append(            "<xsd2:dnNuevo xmlns:xsd2=\"http://vo.bl.telco.iusacell.com.mx/xsd\">"+dnNuevo+"</xsd2:dnNuevo>");
                sXMLRequest.append(            "<xsd2:linea xmlns:xsd2=\"http://vo.bl.telco.iusacell.com.mx/xsd\">");
                sXMLRequest.append(               "<xsd3:dn xmlns:xsd3=\"http://comun.telco.iusacell.com.mx/xsd\">"+dnActual+"</xsd3:dn>");
                sXMLRequest.append(               "<xsd3:idCiudad xmlns:xsd3=\"http://comun.telco.iusacell.com.mx/xsd\">"+idCiudad+"</xsd3:idCiudad>");
                sXMLRequest.append(               "<xsd3:idOperador xmlns:xsd3=\"http://comun.telco.iusacell.com.mx/xsd\">"+idOperador+"</xsd3:idOperador>");
                sXMLRequest.append(               "<xsd3:region xmlns:xsd3=\"http://comun.telco.iusacell.com.mx/xsd\">"+region+"</xsd3:region>");
                sXMLRequest.append(               "<xsd3:terminal xmlns:xsd3=\"http://comun.telco.iusacell.com.mx/xsd\">");
                sXMLRequest.append(                  "<xsd3:propietario>0</xsd3:propietario>");
                sXMLRequest.append(               "</xsd3:terminal>");
                sXMLRequest.append(                 "<xsd3:usuario xmlns:xsd3=\"http://comun.telco.iusacell.com.mx/xsd\">400891</xsd3:usuario>");
                sXMLRequest.append(              "</xsd2:linea>");
                sXMLRequest.append(         "</ser1:cambioDNLNRequest>");
                sXMLRequest.append(      "</ser1:cambioMDN>");
                sXMLRequest.append(   "</soapenv:Body>");
                sXMLRequest.append("</soapenv:Envelope>");
			}
			
			Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(sXMLRequest.toString()));
				
			SOAPManager soapManager = new SOAPManager();
			soapManager.createSOAPManager(url,new MensajeLogBean());
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XMLRespuesta           :" +  Utilerias.pintaLogRequest(sResponse));
			
		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}finally{
            sXMLRequest=null;
        }
		
		return sResponse;
	}
}
