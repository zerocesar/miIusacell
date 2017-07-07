package mx.com.iusacell.services.miusacell.call;

import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManager;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.ResourceAccess;
import mx.com.iusacell.services.miiusacell.vo.ServiciosContratarVO;
import mx.com.iusacell.services.miusacell.util.Utilerias;

public class CallServiceCambioPlan {
				
			public String realizaCambioPlan(final String dn, final int plazo, final String idPlan, final int idNvoPlan, final List<ServiciosContratarVO> serviciosContratados) throws ServiceException {
								
			 	String url = ResourceAccess.getParametroFromXML("urlCambioPlan");			 	
				Logger.write(" >>> L l a m a d a   W e b   S e r v i c e");
				Logger.write("     EndPoint               : " + url);
				Logger.write("     Operacion              : CallServiceCambioPlan");
				Logger.write("     requesting             : " + new Date().toString());
				Logger.write("   + Parametros             + ");
				Logger.write("     dn                     : " + dn);
				Logger.write("     plazo                  : " + plazo);
				Logger.write("     idPlan                 : " + idPlan);
				Logger.write("     idNvoPlan              : " + idNvoPlan);
				if(serviciosContratados != null && serviciosContratados.size() > 0)
				{
					for(ServiciosContratarVO service : serviciosContratados)
					{
						if(service.getIdServicio() != null && !service.getIdServicio().equals(""))
						{
							Logger.write("     servicio.Descripcion   : " + service.getDescripcionServicio());
							Logger.write("     servicio.Id            : " + service.getIdServicio());
							Logger.write("     servicio.Status        : " + service.getStatusServicios());							
						}					
					}
				}
				else				
					Logger.write("     serviciosAdicionales   : 0");					
				
				
				StringBuilder sXMLRequest = new StringBuilder();				
				SOAPManager soapManager = new SOAPManager();
				soapManager.createSOAPManager(url,new MensajeLogBean());
				String sResponse = "";
				String contrasena = "C4mb10Pl4n";
				String login = "PREPAGO";
				String idApp = "PREPAGO";
				int modulo = 18;
				
				
				try
				{								  
					sXMLRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ws=\"http://ws.cambioplan.midtelco.iusacell.com.mx\" xmlns:xsd=\"http://vo.cambioplan.midtelco.iusacell.com.mx/xsd\" xmlns:xsd1=\"http://comun.midtelco.iusacell.com.mx/xsd\">");
					sXMLRequest.append("<soap:Header/>");
					sXMLRequest.append("<soap:Body>");
					sXMLRequest.append("<ws:cambiarPlan>");
					sXMLRequest.append("<ws:entCambioPlan>");
					sXMLRequest.append("<xsd:lineaOrigen>");
//					sXMLRequest.append("<xsd1:ICCID xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:MIN xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:contratacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:contrato xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
					sXMLRequest.append("<xsd1:dn>"+ dn + "</xsd1:dn>");
//					sXMLRequest.append("<xsd1:enrutamiento xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:id xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:idCiudad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:idOperador xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:idSistemaPrepago xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:instalacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:modalidad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:moroso xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:motivoCambio xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
					sXMLRequest.append("<xsd1:plan>");
//					sXMLRequest.append("<xsd1:costoRenta xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:descripcion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
					sXMLRequest.append("<xsd1:id>"+ idPlan + "</xsd1:id>");
//					sXMLRequest.append("<xsd1:idPlanProv xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:matriz xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:validoDesde xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
					sXMLRequest.append("</xsd1:plan>");
					sXMLRequest.append("<xsd1:plazo>"+plazo+"</xsd1:plazo>");
//					sXMLRequest.append("<xsd1:region>");
//					sXMLRequest.append("<xsd1:descripcion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:id xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:iva xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("</xsd1:region>");
//					sXMLRequest.append("<xsd1:segmentacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
					//<!--Zero or more repetitions:-->
					if(serviciosContratados != null && serviciosContratados.size() > 0)
					{	
						sXMLRequest.append("<xsd1:servicios>");
						for(ServiciosContratarVO service : serviciosContratados)
						{
							if(service.getIdServicio() != null && !service.getIdServicio().equals(""))
							{
//								sXMLRequest.append("<xsd1:activacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//								sXMLRequest.append("<xsd1:codDevengado xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//								sXMLRequest.append("<xsd1:costo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//								sXMLRequest.append("<xsd1:desactivacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
								sXMLRequest.append("<xsd1:descripcion>" + service.getDescripcionServicio() + "</xsd1:descripcion>");
//								sXMLRequest.append("<xsd1:duracion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//								sXMLRequest.append("<xsd1:fecha_fin xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
								sXMLRequest.append("<xsd1:id>" + service.getIdServicio() + "</xsd1:id>");
//								sXMLRequest.append("<xsd1:idServicioProv xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//								sXMLRequest.append("<xsd1:origen xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//								sXMLRequest.append("<xsd1:saldo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
								sXMLRequest.append("<xsd1:status>" + service.getStatusServicios() + "</xsd1:status>");
//								sXMLRequest.append("<xsd1:tipo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//								sXMLRequest.append("<xsd1:tipoRecurrencia xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//								sXMLRequest.append("<xsd1:vigencia>");
//								sXMLRequest.append("<xsd1:cantidad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//								sXMLRequest.append("<xsd1:unidad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//								sXMLRequest.append("</xsd1:vigencia>");
							}
						}
						sXMLRequest.append("</xsd1:servicios>");
					}
//					sXMLRequest.append("<xsd1:sistemaTasado xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:status xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:tecnologia xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:terminal>");
//					sXMLRequest.append("<xsd1:ESN>");
//					sXMLRequest.append("<xsd1:esnDec xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:esnEncriptado xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:status xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("</xsd1:ESN>");
//					sXMLRequest.append("<xsd1:codigo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:descripcion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:fabricante xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:modelo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:precio xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:propietario xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:tecnologia xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("</xsd1:terminal>");
//					sXMLRequest.append("<xsd1:tipo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:vencimiento xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:vendedor>");
//					sXMLRequest.append("<xsd1:CURP xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:HOMOCLAVE xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:RFC xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:apellidoMaterno xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:apellidoPaterno xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:edoCivil xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:folioIdentificacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:nacimiento xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:nacionalidad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:nombre xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:ocupacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:segundoNombre xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:sexo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:tipoIdentificacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:titulo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:vigenciaIdentificacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:canal xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:distribuidor xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:id xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:login xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:puntoVenta xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("</xsd1:vendedor>");
//					sXMLRequest.append("<xsd1:vmo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
					//<!--Zero or more repetitions:-->
//					sXMLRequest.append("<xsd1:wallets>");
//					sXMLRequest.append("<xsd1:cantidad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:id xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:periodo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:unidad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:vencimiento xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:vigencia xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("</xsd1:wallets>");
					sXMLRequest.append("</xsd:lineaOrigen>");
					sXMLRequest.append("<xsd:planDestino>");
//					sXMLRequest.append("<xsd1:costoRenta xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:descripcion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
					sXMLRequest.append("<xsd1:id>"+idNvoPlan+"</xsd1:id>");					
//					sXMLRequest.append("<xsd1:idPlanProv xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:matriz xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:validoDesde xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
					sXMLRequest.append("</xsd:planDestino>");
					sXMLRequest.append("<xsd:usuario>");
//					sXMLRequest.append("<xsd1:CURP xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:HOMOCLAVE xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:RFC xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:apellidoMaterno xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:apellidoPaterno xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:edoCivil xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:folioIdentificacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:nacimiento xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:nacionalidad xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:nombre xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:ocupacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:segundoNombre xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:sexo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:tipoIdentificacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:titulo xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
//					sXMLRequest.append("<xsd1:vigenciaIdentificacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
					sXMLRequest.append("<xsd1:contrasena>"+ contrasena + "</xsd1:contrasena>");
//					sXMLRequest.append("<xsd1:descripcion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
					sXMLRequest.append("<xsd1:id>"+ idApp + "</xsd1:id>");
//					sXMLRequest.append("<xsd1:idAplicacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>");
					sXMLRequest.append("<xsd1:login>"+ login + "</xsd1:login>");
					sXMLRequest.append("<xsd1:modulo>"+ modulo + "</xsd1:modulo>");
					sXMLRequest.append("</xsd:usuario>");
					sXMLRequest.append("</ws:entCambioPlan>");
					sXMLRequest.append("</ws:cambiarPlan>");
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
				finally
		        {
		            sXMLRequest = null;
		        }
				
				return sResponse;
				
			}									
}
