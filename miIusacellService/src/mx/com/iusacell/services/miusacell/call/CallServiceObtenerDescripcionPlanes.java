package mx.com.iusacell.services.miusacell.call;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.masivo.security.GeneraToken;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceObtenerDescripcionPlanes {

	public String serviceObtenerDescripcionPlanes(final String idPlan, final String plan, final String idElephan, final int usuario, final String ip) throws ServiceException
	{
		String url = ResourceAccess.getParametroFromXML("urlDescripcionPlanes");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceObtenerDescripcionPlanes");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     idPlan                 : " + idPlan);
		Logger.write("     plan                   : " + plan);
		Logger.write("     idElephan              : " + idElephan);
		Logger.write("     usuario                : " + usuario);
		Logger.write("     ip                     : " + ip);

		StringBuffer sXMLRequest = new StringBuffer();
		String generaToken = "";
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";
		String consultaConCode = "";
		OracleProcedures oracle = new OracleProcedures();

		try
		{
			generaToken = GeneraToken.generaToken("");

			consultaConCode = oracle.getValorParametro(7);

			if(consultaConCode.equals("1")){
                sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.ofertacomercial.iusacell.com\">");
                sXMLRequest.append("<soapenv:Header/>");
                sXMLRequest.append("    <soapenv:Body>");
                sXMLRequest.append("        <ser:getCaracteristicaPlan>");
                sXMLRequest.append("        <ser:in0>"+usuario+"</ser:in0>");              //numEmpleado
                sXMLRequest.append("        <ser:in1>"+ip+"</ser:in1>");                   //ip
                sXMLRequest.append("        <ser:in2>OF32t4COm32ci4lS32Vic3=-</ser:in2>"); //usuario
                sXMLRequest.append("        <ser:in3>OF32t4COm32ci4lS32Vic3=-</ser:in3>"); //pass
                sXMLRequest.append("        <ser:in4>"+generaToken+"</ser:in4>");          //token
                sXMLRequest.append("        <ser:in5>"+idPlan+"</ser:in5>");               //idPlan
                sXMLRequest.append("        <ser:in6>"+plan+"</ser:in6>");                 //plan
                sXMLRequest.append("        <ser:in7>"+idElephan+"</ser:in7>");         //idElephant
                sXMLRequest.append("        </ser:getCaracteristicaPlan>");
                sXMLRequest.append("    </soapenv:Body>");
                sXMLRequest.append("</soapenv:Envelope>");
			}else{
                sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.ofertacomercial.iusacell.com\">");
                sXMLRequest.append("<soapenv:Header/>");
                sXMLRequest.append("    <soapenv:Body>");
                sXMLRequest.append("        <ser:getCaracteristicaPlan>");
                sXMLRequest.append("        <ser:in0>"+usuario+"</ser:in0>");              //numEmpleado
                sXMLRequest.append("        <ser:in1>"+ip+"</ser:in1>");                   //ip
                sXMLRequest.append("        <ser:in2>OF32t4COm32ci4lS32Vic3=-</ser:in2>"); //usuario
                sXMLRequest.append("        <ser:in3>OF32t4COm32ci4lS32Vic3=-</ser:in3>"); //pass
                sXMLRequest.append("        <ser:in4>"+generaToken+"</ser:in4>");          //token
                sXMLRequest.append("        <ser:in5>"+idPlan+"</ser:in5>");               //idPlan
                sXMLRequest.append("        <ser:in6>"+plan+"</ser:in6>");               //plan
                sXMLRequest.append("        </ser:getCaracteristicaPlan>");
                sXMLRequest.append("    </soapenv:Body>");
                sXMLRequest.append("</soapenv:Envelope>");
			}
			
			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));

		}
		catch(Exception e)
		{
			Logger.write("     Detail        : (Exception) " + e.getMessage());
			throw new ServiceException("Ocurrio un Error");
		}finally{
            sXMLRequest=null;
        }
		return sResponse;
	}

	public String getImagenEquipoSO(final String idEquipo, final String descripcionEquipo) throws ServiceException
	{
		String url = ResourceAccess.getParametroFromXML("urlDescripcionPlanes");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : getImagenEquipoSO");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     idEquipo               : " + idEquipo);
		Logger.write("     descripcionEquipo      : " + descripcionEquipo);
		

		StringBuffer sXMLRequest = new StringBuffer();
		String generaToken = "";
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";

		try
		{
			generaToken = GeneraToken.generaToken("");

            sXMLRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.ofertacomercial.iusacell.com\">");
            sXMLRequest.append(        "<soapenv:Header/>");
            sXMLRequest.append(        "<soapenv:Body>");
            sXMLRequest.append(           "<ser:getImagenEquipoSO>");
            sXMLRequest.append(              "<ser:in0>2121</ser:in0>");
            sXMLRequest.append(              "<ser:in1>10.10.10.10</ser:in1>");
            sXMLRequest.append(              "<ser:in2>OF32t4COm32ci4lS32Vic3=-</ser:in2>");
            sXMLRequest.append(              "<ser:in3>OF32t4COm32ci4lS32Vic3=-</ser:in3>");
            sXMLRequest.append(              "<ser:in4>"+generaToken+"</ser:in4>");
            sXMLRequest.append(              "<ser:in5>"+idEquipo+"</ser:in5>");
            sXMLRequest.append(              "<ser:in6>"+descripcionEquipo+"</ser:in6>");
            sXMLRequest.append(           "</ser:getImagenEquipoSO>");
            sXMLRequest.append(        "</soapenv:Body>");
            sXMLRequest.append(     "</soapenv:Envelope>");

			Logger.write("     SOAPRequestXML         : " + Utilerias.pintaLogRequest(sXMLRequest.toString()));
			sResponse = soapManager.sendRequestWithXML(sXMLRequest.toString(),new MensajeLogBean());
			Logger.write("   + Respuesta              +");
			Logger.write("     SOAPResponseXML        : " + Utilerias.pintaLogRequest(sResponse));

		}
		catch(Exception e)
		{
			Logger.write(e.getLocalizedMessage());
			throw new ServiceException("Ocurrio un Error");
		}finally{
            sXMLRequest=null;
        }
		return sResponse;
	}
}
