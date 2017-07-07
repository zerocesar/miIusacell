package mx.com.iusacell.services.miusacell.call;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

import BAdministraFF.iusacell.com.mx.RespuestaFF;

import mx.com.iusacell.comun.Falta;
import mx.com.iusacell.comun.Linea;
import mx.com.iusacell.comun.Usuario;
import mx.com.iusacell.middleware.servicios.gestion.IAdministracionFaF;
import mx.com.iusacell.middleware.servicios.gestion.RegistroGrupoFaF;
import mx.com.iusacell.middleware.servicios.gestion.Binding2.SAdministracionFaFWS_IAdministracionFaFHttpServiceLocator;
import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.SOAPManager.SOAPManagerAxis2;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.vo.Addons;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceNumerosFrecuentes {

	public String activacionServiciosAdicionales(final String idLinea, List<Addons> servicios) throws ServiceException {

		String url = ResourceAccess.getParametroFromXML("urlSServiciosNvo");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : CallServiceNumerosFrecuentes");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     idLinea                : " + idLinea);										

		StringBuilder sXMLRequest = new StringBuilder();				
		SOAPManager soapManager = new SOAPManager();
		soapManager.createSOAPManager(url,new MensajeLogBean());
		String sResponse = "";				
		int modulo = 18;
		String idUsuario = "unefon";
		int operacion = 0;


		try
		{								  
			sXMLRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:impl=\"http://impl.servicios.iusacell.com.mx\" xmlns:xsd=\"http://comun.midtelco.iusacell.com.mx/xsd\" xmlns:xsd1=\"http://vo.servicios.iusacell.com.mx/xsd\">");
			sXMLRequest.append("<soap:Header/>");
			sXMLRequest.append("<soap:Body>");
			sXMLRequest.append("<impl:operacionServicio>");         
			sXMLRequest.append("<impl:linea>");            
			sXMLRequest.append("<xsd:ICCID xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:MIN xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:contratacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:contrato xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:dn xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:id>"+idLinea+"</xsd:id>");            
			sXMLRequest.append("<xsd:idCiudad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:idOperador xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:instalacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:modalidad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:moroso xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:motivoCambio xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:plan>");               
			sXMLRequest.append("<xsd:costoRenta xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:descripcion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:id xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:matriz xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:tmcode xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:validoDesde xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("</xsd:plan>");            
			sXMLRequest.append("<xsd:plazo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:region xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:segmentacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("<!--Zero or more repetitions:-->");

			if(servicios != null && servicios.size() > 0)
			{
				sXMLRequest.append("<xsd:servicios>");  
				for(Addons servicio : servicios)
				{
					sXMLRequest.append("<xsd:activacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
					sXMLRequest.append("<xsd:codDevengado xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
					sXMLRequest.append("<xsd:costo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
					sXMLRequest.append("<xsd:desactivacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
					sXMLRequest.append("<xsd:descripcion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
					sXMLRequest.append("<xsd:facturable xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
					sXMLRequest.append("<xsd:id>"+servicio.getIdServicio()+"</xsd:id>");               
					sXMLRequest.append("<xsd:origen>0</xsd:origen>");               
					sXMLRequest.append("<xsd:saldo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
					sXMLRequest.append("<xsd:status xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
					sXMLRequest.append("<xsd:tipo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
					sXMLRequest.append("<xsd:tipoRecurrencia xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
					sXMLRequest.append("<xsd:vigencia>");                  
					sXMLRequest.append("<xsd:cantidad>"+ servicio.getCantidadVigencia()+"</xsd:cantidad>");                  
					sXMLRequest.append("<xsd:unidad>0</xsd:unidad>");
					sXMLRequest.append("</xsd:vigencia>");
				}
				sXMLRequest.append("</xsd:servicios>");  
			}
			sXMLRequest.append("<xsd:sistemaTasado xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:status xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:tecnologia xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:terminal>");               
			sXMLRequest.append("<xsd:ESN>");                  
			sXMLRequest.append("<xsd:esnDec xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");                  
			sXMLRequest.append("<xsd:esnHex xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");                  
			sXMLRequest.append("<xsd:status xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("</xsd:ESN>");               
			sXMLRequest.append("<xsd:codigo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:descripcion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:fabricante xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:modelo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:precio xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:propietario xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:tecnologia xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("</xsd:terminal>");            
			sXMLRequest.append("<xsd:tipo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:vencimiento xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:vendedor>");               
			sXMLRequest.append("<xsd:CURP xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:RFC xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:apellidoMaterno xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:apellidoPaterno xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:edoCivil xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:folioIdentificacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:nacimiento xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:nacionalidad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:nombre xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:ocupacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:segundoNombre xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:sexo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:tipoIdentificacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:vigenciaIdentificacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:canal xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:distribuidor xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:id xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:puntoVenta xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("</xsd:vendedor>");            
			sXMLRequest.append("<xsd:vmo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("<!--Zero or more repetitions:-->");
			sXMLRequest.append("<xsd:wallets>");               
			sXMLRequest.append("<xsd:cantidad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:id xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:periodo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:unidad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:vencimiento xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");               
			sXMLRequest.append("<xsd:vigencia xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("</xsd:wallets>");
			sXMLRequest.append("</impl:linea>");         
			sXMLRequest.append("<impl:usuario>");            
			sXMLRequest.append("<xsd:CURP xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:RFC xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:apellidoMaterno xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:apellidoPaterno xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:edoCivil xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:folioIdentificacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:nacimiento xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:nacionalidad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:nombre xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:ocupacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:segundoNombre xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:sexo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:tipoIdentificacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:vigenciaIdentificacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:contrasena xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:descripcion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:id>"+idUsuario+"</xsd:id>");            
			sXMLRequest.append("<xsd:login xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");            
			sXMLRequest.append("<xsd:modulo>"+modulo+"</xsd:modulo>");
			sXMLRequest.append("</impl:usuario>");         
			sXMLRequest.append("<impl:cobro>");                                    
			sXMLRequest.append("<xsd1:folioCobro xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
			sXMLRequest.append("</impl:cobro>");
			sXMLRequest.append("<impl:operacion>"+operacion+"</impl:operacion>");
			sXMLRequest.append("</impl:operacionServicio>");
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
	
	public String altaNumerosFrecuentes(final String idLinea, final List<String> numerosFrecuentes) throws ServiceException
	{
		String sResponse="";
		String userID = "miiusacell";		
		String url = ResourceAccess.getParametroFromXML("urlNumFrecuente");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : numerosFrecuentesLegacy");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     idLinea                : " + idLinea);
		Logger.write("     numerosFrecuentes      : " + numerosFrecuentes.size());		
		
		try{
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace ges = fac.createOMNamespace("http://gestion.servicios.middleware.iusacell.com.mx", "ges");
			OMNamespace wsNs = fac.createOMNamespace("", "");
			OMElement numerosFrecuentesMain = fac.createOMElement("modificarGrupoFaF", ges);			
			
			OMElement linea = fac.createOMElement("linea", wsNs);
			OMElement Vlineaid = fac.createOMElement("id", wsNs);
			OMElement VlineaFamilia = fac.createOMElement("familia", wsNs);
			OMElement VlineaTerminal = fac.createOMElement("terminal", wsNs);
			OMElement VTerminalEsn = fac.createOMElement("ESN", wsNs);
			OMElement servicios = fac.createOMElement("servicios", wsNs);
			OMElement vigencias = fac.createOMElement("vigencias", wsNs);
			OMElement cuentaContable = fac.createOMElement("cuentaContable", wsNs);
			OMElement cuenta = fac.createOMElement("cuenta", wsNs);
			OMElement IVA = fac.createOMElement("IVA", wsNs);
			OMElement region = fac.createOMElement("region", wsNs);
			OMElement wallets = fac.createOMElement("wallets", wsNs);
			OMElement vendedor = fac.createOMElement("vendedor", wsNs);
			OMElement historico = fac.createOMElement("historico", wsNs);
			OMElement historicoPreactivacion = fac.createOMElement("historicoPreactivacion", wsNs);		
			
			OMElement usuario = fac.createOMElement("usuario", wsNs);
			OMElement usuarioId = fac.createOMElement("id", wsNs);
			
			
			OMElement miembrosFaf = fac.createOMElement("miembrosFaF", wsNs);
			OMElement gruposFaf = null;
			OMElement indiceFaf = null;
			OMElement dnFaf = null;																									
			
			Vlineaid.setText(idLinea);
			usuarioId.setText(userID);
			
			cuentaContable.addChild(cuenta);
			cuentaContable.addChild(IVA);
			cuentaContable.addChild(region);							
			servicios.addChild(vigencias);
			servicios.addChild(cuentaContable);		
			
			cuentaContable = fac.createOMElement("cuentaContable", wsNs);
			cuenta = fac.createOMElement("cuenta", wsNs);
			IVA = fac.createOMElement("IVA", wsNs);
			region = fac.createOMElement("region", wsNs);
			cuentaContable.addChild(cuenta);
			cuentaContable.addChild(IVA);
			cuentaContable.addChild(region);
			vigencias = fac.createOMElement("vigencias", wsNs);
			historico.addChild(cuentaContable);
			historico.addChild(vigencias);
			
			cuentaContable = fac.createOMElement("cuentaContable", wsNs);
			cuenta = fac.createOMElement("cuenta", wsNs);
			IVA = fac.createOMElement("IVA", wsNs);
			region = fac.createOMElement("region", wsNs);
			cuentaContable.addChild(cuenta);
			cuentaContable.addChild(IVA);
			cuentaContable.addChild(region);
			vigencias = fac.createOMElement("vigencias", wsNs);
			historicoPreactivacion.addChild(cuentaContable);
			historicoPreactivacion.addChild(vigencias);
				
			VlineaTerminal.addChild(VTerminalEsn);
			linea.addChild(Vlineaid);	
			linea.addChild(VlineaFamilia);
			linea.addChild(VlineaTerminal);
			linea.addChild(servicios);
			linea.addChild(wallets);
			linea.addChild(vendedor);
			linea.addChild(historico);
			linea.addChild(historicoPreactivacion);
			usuario.addChild(usuarioId);				
			String posicion = "";		
			for(int pos = 0; pos < numerosFrecuentes.size(); pos++)
			{
				posicion = pos+1+"";
				gruposFaf = fac.createOMElement("grupoFaF", wsNs);
				indiceFaf = fac.createOMElement("indice", wsNs);
				dnFaf = fac.createOMElement("dn", wsNs);	
				indiceFaf.setText(posicion);					
				dnFaf.setText(numerosFrecuentes.get(pos));
				gruposFaf.addChild(indiceFaf);
				gruposFaf.addChild(dnFaf);
				miembrosFaf.addChild(gruposFaf);
			}			
			
			numerosFrecuentesMain.addChild(linea);
			numerosFrecuentesMain.addChild(usuario);
			numerosFrecuentesMain.addChild(miembrosFaf);			
			
			Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(numerosFrecuentesMain.toString()));
			
			SOAPManagerAxis2 send = new SOAPManagerAxis2();
			sResponse = send.send(url, numerosFrecuentesMain);
			
			Logger.write("   + Respuesta              + ");
   		    Logger.write("     XMLRespuesta           :" +  Utilerias.pintaLogRequest(sResponse));

		}catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return sResponse;
	}		
	
	public RespuestaFF altaNumerosFrecuentesLegacy(final String idLinea, final List<String> numerosFrecuentes) throws ServiceException
	{
		String sResponse="";
		String userID = "miiusacell";		
		String url = ResourceAccess.getParametroFromXML("urlNumFrecuente");
		Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
		Logger.write("     EndPoint               : " + url);
		Logger.write("     Operacion              : altaNumerosFrecuentesLegacy");
		Logger.write("     requesting             : " + new Date().toString());
		Logger.write("   + Parametros             + ");
		Logger.write("     idLinea                : " + idLinea);
		Logger.write("     numerosFrecuentes      : " + numerosFrecuentes.size());		
		
		SAdministracionFaFWS_IAdministracionFaFHttpServiceLocator serviceLocator = new SAdministracionFaFWS_IAdministracionFaFHttpServiceLocator();
		serviceLocator.setSAdministracionFaFWS_IAdministracionFaFHttpPortEndpointAddress(url);
		RespuestaFF responseService = null;
		try{							
			
//			Logger.write("     SOAPRequestXML         : "  +  Utilerias.pintaLogRequest(numerosFrecuentesMain.toString()));
			IAdministracionFaF serviceMethods = serviceLocator.getSAdministracionFaFWS_IAdministracionFaFHttpPort();
			Linea linea = new Linea();
			linea.setId(idLinea);
			
			Usuario usuario = new Usuario();
			usuario.setId(userID);
			
			RegistroGrupoFaF[] miembrosFaF = new RegistroGrupoFaF[numerosFrecuentes.size()];
			for(int pos = 0; pos < numerosFrecuentes.size(); pos++)
			{
				String indice = pos +1 + "";
				miembrosFaF[pos] = new RegistroGrupoFaF();
				miembrosFaF[pos].setIndice(indice);
				miembrosFaF[pos].setDn(numerosFrecuentes.get(pos));
			}
			
			responseService = serviceMethods.modificarGrupoFaF(usuario, linea, miembrosFaF);
						
			Logger.write("   + Respuesta              + ");
			
			if(responseService != null)
			{
				Logger.write("     XMLRespuesta           : respuesta     :" + responseService.getRespuesta());
				Logger.write("     XMLRespuesta           : IdTx          : " + responseService.getIdTx());
			}
   		    Logger.write("     XMLRespuesta           :" +  Utilerias.pintaLogRequest(sResponse));

		} catch (ServiceException e) {
			throw new ServiceException(e.getLocalizedMessage());
		} catch (Falta e) {
			throw new ServiceException(e.getExceptionType() + " " + e.getMessage1());
		} catch (RemoteException e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return responseService;
	}
}
