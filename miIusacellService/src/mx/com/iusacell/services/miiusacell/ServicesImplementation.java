package mx.com.iusacell.services.miiusacell;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import mx.com.iusacell.comun.Linea;
import mx.com.iusacell.comun.Servicio;
import mx.com.iusacell.comun.Usuario;
import mx.com.iusacell.middleware.servicios.gestion.Cobro;
import mx.com.iusacell.services.miiusacell.arquitectura.AbonoTiempoAire;
import mx.com.iusacell.services.miiusacell.arquitectura.AbonoTiempoAireAutorizador;
import mx.com.iusacell.services.miiusacell.arquitectura.AbonoTiempoAireIn;
import mx.com.iusacell.services.miiusacell.arquitectura.ActualizaFotografiaXUsu;
import mx.com.iusacell.services.miiusacell.arquitectura.AltaCita;
import mx.com.iusacell.services.miiusacell.arquitectura.AltaNumeroFrecuente;
import mx.com.iusacell.services.miiusacell.arquitectura.AltaServicioXUsuExt;
import mx.com.iusacell.services.miiusacell.arquitectura.AltaTarjetaFrecuente;
import mx.com.iusacell.services.miiusacell.arquitectura.AltaUsuario;
import mx.com.iusacell.services.miiusacell.arquitectura.BajaNumeroFrecuente;
import mx.com.iusacell.services.miiusacell.arquitectura.BajaServicio;
import mx.com.iusacell.services.miiusacell.arquitectura.BajaTarjetaFrecuente;
import mx.com.iusacell.services.miiusacell.arquitectura.CambioPlan;
import mx.com.iusacell.services.miiusacell.arquitectura.CancelaCitaUsuario;
import mx.com.iusacell.services.miiusacell.arquitectura.CancelacionLinea;
import mx.com.iusacell.services.miiusacell.arquitectura.CompraProductosAutorizador;
import mx.com.iusacell.services.miiusacell.arquitectura.ConsultaOperador;
import mx.com.iusacell.services.miiusacell.arquitectura.ConsultaServicioXUsuExt;
import mx.com.iusacell.services.miiusacell.arquitectura.DNCambio;
import mx.com.iusacell.services.miiusacell.arquitectura.DNReactivacion;
import mx.com.iusacell.services.miiusacell.arquitectura.DNReactivacionExtendido;
import mx.com.iusacell.services.miiusacell.arquitectura.DNSugerido;
import mx.com.iusacell.services.miiusacell.arquitectura.DesgloseFactura;
import mx.com.iusacell.services.miiusacell.arquitectura.DetalleConsumos;
import mx.com.iusacell.services.miiusacell.arquitectura.DetalleConsumosTabla;
import mx.com.iusacell.services.miiusacell.arquitectura.DetalleConsumosTotales;
import mx.com.iusacell.services.miiusacell.arquitectura.DetalleSaldos;
import mx.com.iusacell.services.miiusacell.arquitectura.EditarCuenta;
import mx.com.iusacell.services.miiusacell.arquitectura.EditarPerfil;
import mx.com.iusacell.services.miiusacell.arquitectura.EliminaUsuario;
import mx.com.iusacell.services.miiusacell.arquitectura.EnviaCorreoDetalle;
import mx.com.iusacell.services.miiusacell.arquitectura.GeneraPIN;
import mx.com.iusacell.services.miiusacell.arquitectura.GetCitasPendientesXDN;
import mx.com.iusacell.services.miiusacell.arquitectura.GetHorarioDisponblesXCAE;
import mx.com.iusacell.services.miiusacell.arquitectura.GetHorariosDisponiblesCallCenter;
import mx.com.iusacell.services.miiusacell.arquitectura.GetServiciosAdicionales;
import mx.com.iusacell.services.miiusacell.arquitectura.GetTiposDeAtencion;
import mx.com.iusacell.services.miiusacell.arquitectura.Indicadores;
import mx.com.iusacell.services.miiusacell.arquitectura.IsLogin;
import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.NumeroFrecuente;
import mx.com.iusacell.services.miiusacell.arquitectura.ObtieneCaes;
import mx.com.iusacell.services.miiusacell.arquitectura.ObtieneClavesSensales;
import mx.com.iusacell.services.miiusacell.arquitectura.ObtieneEstadosMunicipiosInfo;
import mx.com.iusacell.services.miiusacell.arquitectura.ObtieneFrecuentes;
import mx.com.iusacell.services.miiusacell.arquitectura.ObtieneListaVersionesCaesS;
import mx.com.iusacell.services.miiusacell.arquitectura.ObtieneSaldoAPagar;
import mx.com.iusacell.services.miiusacell.arquitectura.ObtieneServiciosAContratar;
import mx.com.iusacell.services.miiusacell.arquitectura.ObtieneUltimasFacturas;
import mx.com.iusacell.services.miiusacell.arquitectura.OfertaComercial;
import mx.com.iusacell.services.miiusacell.arquitectura.PagarFactura;
import mx.com.iusacell.services.miiusacell.arquitectura.PagarFacturaAutorizador;
import mx.com.iusacell.services.miiusacell.arquitectura.PagarFacturain;
import mx.com.iusacell.services.miiusacell.arquitectura.ProcesoActualizaDN;
import mx.com.iusacell.services.miiusacell.arquitectura.Reprogramacion;
import mx.com.iusacell.services.miiusacell.arquitectura.RestablecerLlave;
import mx.com.iusacell.services.miiusacell.arquitectura.ServiciosUnefon;
import mx.com.iusacell.services.miiusacell.arquitectura.UnidadNegocioBusiness;
import mx.com.iusacell.services.miiusacell.arquitectura.autorizador.ReferenciaBancaria;
import mx.com.iusacell.services.miiusacell.arquitectura.autorizador.ValidatorCharges;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.masivo.security.ValidaTokens;
import mx.com.iusacell.services.miiusacell.vo.AbonoTiempoAireVO;
import mx.com.iusacell.services.miiusacell.vo.Addons;
import mx.com.iusacell.services.miiusacell.vo.AltaCitaVO;
import mx.com.iusacell.services.miiusacell.vo.AltaServicioEtakVO;
import mx.com.iusacell.services.miiusacell.vo.AltaServicioLegacyVO;
import mx.com.iusacell.services.miiusacell.vo.AltaServicioPrepagoLegacyVO;
import mx.com.iusacell.services.miiusacell.vo.BankCardAdditionalInfoVO;
import mx.com.iusacell.services.miiusacell.vo.CardVO;
import mx.com.iusacell.services.miiusacell.vo.CatalogoAbonos;
import mx.com.iusacell.services.miiusacell.vo.CatalogoCaeGeneralVO;
import mx.com.iusacell.services.miiusacell.vo.CatalogoIndicadoresVO;
import mx.com.iusacell.services.miiusacell.vo.CatalogoMovilCaeVO;
import mx.com.iusacell.services.miiusacell.vo.CatalogoTotalesVO;
import mx.com.iusacell.services.miiusacell.vo.CatalogoVO;
import mx.com.iusacell.services.miiusacell.vo.CatatoloGetVO;
import mx.com.iusacell.services.miiusacell.vo.CitasDisponiblesXHorario;
import mx.com.iusacell.services.miiusacell.vo.ClienteRegistroVO;
import mx.com.iusacell.services.miiusacell.vo.ClienteVO;
import mx.com.iusacell.services.miiusacell.vo.ConfiguracionXUsuarioMovilVO;
import mx.com.iusacell.services.miiusacell.vo.ConfiguracionXUsuarioVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaGenerica;
import mx.com.iusacell.services.miiusacell.vo.ConsultaIndicadores;
import mx.com.iusacell.services.miiusacell.vo.ConsultaOperadorExtendidoVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaOperadorVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaParametrosVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaSrScVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaWalletGeneralVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoDetalleTablaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoFechaTablaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosTablaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosTotalesResponseVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosTotalesVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosXdnVO;
import mx.com.iusacell.services.miiusacell.vo.ContratarServiciosVO;
import mx.com.iusacell.services.miiusacell.vo.DNCambioVO;
import mx.com.iusacell.services.miiusacell.vo.DNReactivacionVO;
import mx.com.iusacell.services.miiusacell.vo.DNSugeridoVO;
import mx.com.iusacell.services.miiusacell.vo.DatosFacturasVO;
import mx.com.iusacell.services.miiusacell.vo.DatosFocalizacion;
import mx.com.iusacell.services.miiusacell.vo.DatosFocalizacionExtendido;
import mx.com.iusacell.services.miiusacell.vo.DatosFocalizacionFocaVO;
import mx.com.iusacell.services.miiusacell.vo.DatosFocalizacionUsuario;
import mx.com.iusacell.services.miiusacell.vo.DatosFocalizacionUsuarioExtendido;
import mx.com.iusacell.services.miiusacell.vo.DatosFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.DatosLogin;
import mx.com.iusacell.services.miiusacell.vo.DatosServiciosAContratarVO;
import mx.com.iusacell.services.miiusacell.vo.DatosUltimasFacturasVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFacturaVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVOExtendido;
import mx.com.iusacell.services.miiusacell.vo.DetalleSaldoVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleTotalesLlamadas;
import mx.com.iusacell.services.miiusacell.vo.DetalleTotalesVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleWalletsVO;
import mx.com.iusacell.services.miiusacell.vo.DomicilioVO;
import mx.com.iusacell.services.miiusacell.vo.ErrorVO;
import mx.com.iusacell.services.miiusacell.vo.EstadoCuentaVO;
import mx.com.iusacell.services.miiusacell.vo.FacturaVirtualDetalleVO;
import mx.com.iusacell.services.miiusacell.vo.GetCitasPendientesXDNVO;
import mx.com.iusacell.services.miiusacell.vo.GetHorariosDisponiblesCallCenterVO;
import mx.com.iusacell.services.miiusacell.vo.ImagenEquipoVO;
import mx.com.iusacell.services.miiusacell.vo.Llamadas;
import mx.com.iusacell.services.miiusacell.vo.LoginVO;
import mx.com.iusacell.services.miiusacell.vo.MensajeMailVO;
import mx.com.iusacell.services.miiusacell.vo.Mensajes;
import mx.com.iusacell.services.miiusacell.vo.MinutosComunidad;
import mx.com.iusacell.services.miiusacell.vo.MinutosTodoDestino;
import mx.com.iusacell.services.miiusacell.vo.Navegacion;
import mx.com.iusacell.services.miiusacell.vo.NumerosFrecuentesVO;
import mx.com.iusacell.services.miiusacell.vo.ObtenerDescripcionPlanesVO;
import mx.com.iusacell.services.miiusacell.vo.ObtenerDescripcionPlanesVO1;
import mx.com.iusacell.services.miiusacell.vo.ObtenerDetallesServicesClassVO;
import mx.com.iusacell.services.miiusacell.vo.ObtieneEstadoMunicipioVO;
import mx.com.iusacell.services.miiusacell.vo.ObtienePoblacionesVO;
import mx.com.iusacell.services.miiusacell.vo.OfertaVO;
import mx.com.iusacell.services.miiusacell.vo.PagoFacturaResponseVO;
import mx.com.iusacell.services.miiusacell.vo.PermisosClienteVO;
import mx.com.iusacell.services.miiusacell.vo.PuntosVO;
import mx.com.iusacell.services.miiusacell.vo.Referencia;
import mx.com.iusacell.services.miiusacell.vo.ReprogramacionVO;
import mx.com.iusacell.services.miiusacell.vo.ResponseCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.ResponseCitaVO;
import mx.com.iusacell.services.miiusacell.vo.SaldosVO;
import mx.com.iusacell.services.miiusacell.vo.ServicioVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosAdicionalesVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosBundlesAdicionales;
import mx.com.iusacell.services.miiusacell.vo.ServiciosContratarVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosDisponiblesVO;
import mx.com.iusacell.services.miiusacell.vo.SuspensionReactivacionVO;
import mx.com.iusacell.services.miiusacell.vo.TarjetaVO;
import mx.com.iusacell.services.miiusacell.vo.TarjetasFrecuentesVO;
import mx.com.iusacell.services.miiusacell.vo.TipoServicioVO;
import mx.com.iusacell.services.miiusacell.vo.TiposDeAtencionVO;
import mx.com.iusacell.services.miiusacell.vo.UfmiVO;
import mx.com.iusacell.services.miiusacell.vo.WalletsDetalleVO;
import mx.com.iusacell.services.miiusacell.vo.catalogoCambioPlanVO;
import mx.com.iusacell.services.miiusacell.vo.GestionServiciosWS.RespuestaServicios;
import mx.com.iusacell.services.miiusacell.vo.autorizador.AddressVO;
import mx.com.iusacell.services.miiusacell.vo.autorizador.TransactionVO;
import mx.com.iusacell.services.miiusacell.vo.messageMail.MessageMailBean;
import mx.com.iusacell.services.miiusacell.vo.messageMail.RespuestaMensajeBean;
import mx.com.iusacell.services.miusacell.call.CallServiceConsultaPrepago;
import mx.com.iusacell.services.miusacell.call.CallServiceConsultasNum;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceGestionServicios;
import mx.com.iusacell.services.miusacell.call.CallServiceLogin;
import mx.com.iusacell.services.miusacell.call.CallServiceMandaMensaje_Mail;
import mx.com.iusacell.services.miusacell.call.CallServiceObtenerDetallesService;
import mx.com.iusacell.services.miusacell.call.CallServiceSemaphore;
import mx.com.iusacell.services.miusacell.util.AlgoritmoAes;
import mx.com.iusacell.services.miusacell.util.Constantes;
import mx.com.iusacell.services.miusacell.util.Formatter;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;
import mx.com.iusacell.services.miusacell.util.ParseXMLServices;
import mx.com.iusacell.services.miusacell.util.Utilerias;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.xfire.transport.http.XFireServletController;

import com.google.gson.Gson;
import com.iusacell.middleware.serviciosBSCS.vo.RespuestaAltaBajaServiciosVO;

public class ServicesImplementation implements ServicesInterface {		

	public String funcionEjemplo(String user, String pass, String parametros, String token) throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("funcionEjemplo");
        //E R R O R //walletsTotal Al consultar la url
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] password no válido");
		}

		String respuesta = "";
		ValidaTokens tokens =  new ValidaTokens();	

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     parametros             : " + parametros);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(parametros, token,  new MensajeLogBean());

			//Ejecutar Clase
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] funcionEjemplo :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] funcionEjemplo :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] funcionEjemplo :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("funcionEjemplo :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}


	public DatosLogin getDatosUsuario(String user, String pass, String usuario, String token) throws Throwable{		
		
		long initime = System.currentTimeMillis();
        Logger.init("getDatosUsuario");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}

		DatosLogin datosLogin = new DatosLogin();
		ValidaTokens tokens =  new ValidaTokens();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     usuario                : " + usuario);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(usuario, token, new MensajeLogBean("login"));

			datosLogin = IsLogin.getDatosLogin(usuario);	      
			
			Logger.write("   + Respuesta  +");
			Logger.write("     Nombre                 : " + datosLogin.getNombre());
			Logger.write("     ApMaterno              : " + datosLogin.getApMaterno());
			Logger.write("     ApPaterno              : " + datosLogin.getApPaterno());
			Logger.write("     Correo                 : " + datosLogin.getCorreo());
			Logger.write("     Foto                   : " + ((datosLogin.getFoto() == null) ? "" : datosLogin.getFoto().length()));
			Logger.write("     Edad                   : " + datosLogin.getEdad());
			Logger.write("     Sexo                   : " + datosLogin.getSexo());
			Logger.write("     PreguntaSecreta        : " + datosLogin.getPreguntaSecretea());
			Logger.write("     RespuestaSecreta       : " + datosLogin.getRespPreguntaSecreta());
			Logger.write("     tipoCliente            : " + datosLogin.getTipoClienteId());
			Logger.write("     fechaNacimiento        : " + datosLogin.getFechaNacimiento());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] getDatosUsuario ["+usuario+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] getDatosUsuario ["+usuario+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] getDatosUsuario ["+usuario+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("getDatosUsuario ["+usuario+"] :: " + getLocalAddress(), initime);
		}

		return datosLogin;
	}
	
	public DatosFocalizacionUsuario focalizacion(String user, String pass, String dn, String token) throws Throwable{		
		long initime = System.currentTimeMillis();
        Logger.init("focalizacion");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}

		DatosFocalizacionUsuario datos = new DatosFocalizacionUsuario();
		DatosLogin datosUsuario = new DatosLogin();
//		CallServiceObtenerDescripcionPlanes obtenPlanes = new CallServiceObtenerDescripcionPlanes();
		ObtenerDescripcionPlanesVO1 obtenerDescripcionP = new ObtenerDescripcionPlanesVO1();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		ValidaTokens tokens =  new ValidaTokens();
		OracleProcedures oracle = new OracleProcedures();
		DatosFocalizacion datosFocalizacion = new DatosFocalizacion();
		DatosFocalizacionFocaVO responseFoca = new DatosFocalizacionFocaVO();
		String[] parametrosSplit = null;
		StringBuffer sResponse = new StringBuffer();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token, new MensajeLogBean("login"));
			
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("DN no puede ir vacio");
			
			String cadenaQuitar = oracle.getValorParametro(5);
			if(cadenaQuitar != null && !cadenaQuitar.equals(""))
				parametrosSplit = cadenaQuitar.toLowerCase().trim().split(",");

			try{
				datosUsuario = IsLogin.getDatosLogin(dn);
			}catch (ServiceException e) {
				datosUsuario = new DatosLogin();
			}

			try{
				sResponse.append(focalizacion.focalizacion(dn));
				if(sResponse != null && !sResponse.equals("")){
					descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
					if(datosUsuario == null || datosUsuario.getNombre() == null || datosUsuario.getNombre().equals(""))
						responseFoca = ParseXMLFile.parseDatosFocalizacionFoca(sResponse.toString());
				}	
				sResponse.setLength(0);
			}catch (ServiceException e) {
				descripcion = new DetalleFocalizacionVO();
			}
			
			
			if(responseFoca != null){
				
				if(responseFoca.getDescripcionPlanFoca() != null && !responseFoca.getDescripcionPlanFoca().equals("")){
					if(parametrosSplit != null && parametrosSplit.length>0) {
						String datoPivote = responseFoca.getDescripcionPlanFoca();
						for(int a=0; a< parametrosSplit.length; a++) {
							datoPivote = datoPivote.toLowerCase();
							datoPivote = datoPivote.replace(parametrosSplit[a], "");
							responseFoca.setDescripcionPlanFoca(datoPivote.toUpperCase());
						}
					}
				}
				
				datos.setDatosFocalizacionUsuario(responseFoca);
			}
			datosFocalizacion.setFechaCorte(descripcion.getFechaCorte());
			datosFocalizacion.setIdOperador(descripcion.getIdOperador());
			datosFocalizacion.setIdPrepago(descripcion.getIdPrepago());
			datosFocalizacion.setIsPostpagoOrHibrido(descripcion.getIsPostpagoOrHibrido());
			datosFocalizacion.setServicio(descripcion.getServicio());
			datosFocalizacion.setSubservicio(descripcion.getSubservicio());
			datosFocalizacion.setTecnologia(descripcion.getTecnologia());
			datosFocalizacion.setFechaContratacion(descripcion.getFechaContratacion());
			datosFocalizacion.setFechaVencimiento(descripcion.getFechaVencimiento());
			datosFocalizacion.setMesesRestantes(descripcion.getMesesRestantes());
			datosFocalizacion.setPlazoForzoso(descripcion.getPlazoForzoso());
			datosFocalizacion.setRenta(descripcion.getRenta());
			datosFocalizacion.setCuenta(descripcion.getCuenta());
			datos.setDatosFocalizacion(datosFocalizacion);

			try{
				
				obtenerDescripcionP = OfertaComercial.consultaDescripcionPlanes(descripcion.getIdPlan(), descripcion.getDescripcionPlan(), descripcion.getCode(), 212121, "10.10.10.10");
				
			}catch (ServiceException e) {
				obtenerDescripcionP = new ObtenerDescripcionPlanesVO1();
			}
			
			try{
				int compania = 0;
				compania = oracle.obtieneEmpresa(dn);
				datos.setCompania(compania);
			}catch (Exception e) {
				datos.setCompania(0);
			}
			
			if(datosUsuario != null)
				datos.setDatosUsuario(datosUsuario);
			if(obtenerDescripcionP != null)
				datos.setDatosPlan(obtenerDescripcionP);
			
			Logger.write("   + Respuesta  +");
			if(datos.getDatosUsuario() != null){
				Logger.write("     Nombre                 : " + datos.getDatosUsuario().getNombre());
				Logger.write("     ApMaterno              : " + datos.getDatosUsuario().getApMaterno());
				Logger.write("     ApPaterno              : " + datos.getDatosUsuario().getApPaterno());
				Logger.write("     Correo                 : " + datos.getDatosUsuario().getCorreo());
				Logger.write("     Foto                   : " + ((datos.getDatosUsuario().getFoto() == null) ? "" : datos.getDatosUsuario().getFoto().length()));
				Logger.write("     Edad                   : " + datos.getDatosUsuario().getEdad());
				Logger.write("     Sexo                   : " + datos.getDatosUsuario().getSexo());
				Logger.write("     PreguntaSecreta        : " + datos.getDatosUsuario().getPreguntaSecretea());
				Logger.write("     RespuestaSecreta       : " + datos.getDatosUsuario().getRespPreguntaSecreta());
				Logger.write("     tipoCliente            : " + datos.getDatosUsuario().getTipoClienteId());
				Logger.write("     fechaNacimiento        : " + datos.getDatosUsuario().getFechaNacimiento());
			}

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("vacio")){
					throw new ServiceException("[WARN] focalizacion ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] focalizacion ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] focalizacion ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("focalizacion ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return datos;
	}
	
	public boolean validaLogin(String user, String pass, String usuario, String password, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("validaLogin");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}

		boolean validaLog = false;
		ValidaTokens tokens =  new ValidaTokens();

		try{

			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     usuario                : " + usuario);
			Logger.write("     password               : " + password);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(usuario, token, new MensajeLogBean("login"));
			
			validaLog = IsLogin.validaLogin(usuario, password);

			Logger.write("   + Respuesta  +");
			Logger.write("     validaLogin            : " + validaLog);
			
		}catch(ServiceException e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					Logger.warn(e.getMessage(),"validaLogin ["+usuario+"]");
				}else{
					Logger.error(e.getMessage(),"validaLogin ["+usuario+"]");	
				}
			}else{
				Logger.error(e.getMessage(),"validaLogin ["+usuario+"]");	
			}
			validaLog = false;
		}
		finally{
			Logger.end("validaLogin ["+usuario+"] :: " + getLocalAddress(), initime);
		}

		return validaLog;
	}
	
	public int login(String user, String pass, String usuario, String password, int unidadDeNegocio, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
		Logger.init("login",unidadDeNegocio, true);
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}

		int validaLog = -1;
		ValidaTokens tokens =  new ValidaTokens();
		OracleProcedures oracle = new OracleProcedures();
		String paramString = "";
		int param = 0;
		int dnInt = 0;
		String dnCorrecto = "";
		String version = "";
		String detalle = "";
		String unidad = "";
		String cia = "";
		int tipoDispositivo = 0;
		int sistemaOrigen = 0;
		String actualizaUnidadNegocio = "";
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     usuario                : " + usuario);
			Logger.write("     password               : " + password);
			Logger.write("     unidadDeNegocio        : " + unidadDeNegocio);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(usuario, token, new MensajeLogBean(""));
			
			if(usuario == null || usuario.trim().equals(""))
				throw new ServiceException("usuario no puede ir vacio");
			if(password == null || password.trim().equals(""))
				throw new ServiceException("password no puede ir vacio");
			
			paramString = oracle.getValorParametro(12);
			param = Integer.parseInt(paramString);
			actualizaUnidadNegocio = oracle.getValorParametro(224);
			
			if(usuario != null && usuario.length() >= 10){
				try{
					version = usuario.substring(10);
					dnCorrecto = usuario.substring(0,10);
					if(version.equals("")){
						throw new ServiceException("No se recibió la versión");
					}
					dnInt = Integer.parseInt(usuario.substring(10));
				}catch (NumberFormatException e) {
					dnInt = -1;
				}
			}else{
				dnInt = 1;
				param = 0;
			}
			
			if("1".equals(actualizaUnidadNegocio)){
				final UnidadNegocioBusiness validaUnidadN = new UnidadNegocioBusiness();
				validaUnidadN.validaUnidadNegocioCliente(dnCorrecto);
			}
			
			try{
				unidad = unidadDeNegocio+"";
				if(unidad.length()>=1){
					unidadDeNegocio = Integer.parseInt(unidad.substring(0,1));
				}
				if(unidad.length()>=2){
					sistemaOrigen = Integer.parseInt(unidad.substring(1,2));
				}
				if(unidad.length()>=3){
					tipoDispositivo = Integer.parseInt(unidad.substring(2,3));
				}
			}catch (Exception e) {
				tipoDispositivo = 0;
				sistemaOrigen = 0;
			}
			
			String cambio = oracle.getValorParametro(223);
			
			if (cambio.equals("1")){
			  if (unidadDeNegocio != 2)  {
                if(dnInt < param){
                    validaLog = 4;
                }else if(dnInt >= param){
                	cia = oracle.retornaCia(dnCorrecto,new MensajeLogBean(""));
                	if("4".equals(cia) || "1".equals(cia)){
                		unidadDeNegocio = Integer.parseInt(cia);
                	}
                    validaLog = oracle.consultaLogin(dnCorrecto, password, unidadDeNegocio);
                }
              } else {
               validaLog = oracle.consultaLogin(dnCorrecto, password, unidadDeNegocio);
              }
			}else{
			
			  if(dnInt < param){
				validaLog = 4;
			  }else if(dnInt >= param){
				  cia = oracle.retornaCia(dnCorrecto,new MensajeLogBean(""));
				  if("4".equals(cia) || "1".equals(cia)){
					  unidadDeNegocio = Integer.parseInt(cia);
				  }
              	  validaLog = oracle.consultaLogin(dnCorrecto, password, unidadDeNegocio);
			  }
			  
			}
			detalle = cia;	
			Logger.write("   + Respuesta              +");
			Logger.write("     validaLogin            : " + validaLog);
			
		}catch(Exception e){
			if(e.getLocalizedMessage() != null)
				detalle = e.getLocalizedMessage();
			
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("vacio")){
					throw new ServiceException("[WARN] login ["+usuario+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] login ["+usuario+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] login ["+usuario+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			try{
				oracle.loginSET(dnCorrecto, validaLog+"", detalle, tipoDispositivo, sistemaOrigen, 2, 212121);
			}catch (Exception e) {
				Logger.write("Detail al registrar el bitacora Login");
			}
			Logger.end("login ["+usuario+"] :: " + getLocalAddress(), initime);
		}

		return validaLog;
	}

	public ConsumosTablaVO detalleConsumosTabla(String user, String pass, String dn, String tipoEvento, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("detalleConsumosTabla");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleConsumosTabla consumos = new DetalleConsumosTabla();
		ConsumosTablaVO respuesta = new ConsumosTablaVO(); 
		List<ConsumoDetalleTablaVO> responseList = new ArrayList<ConsumoDetalleTablaVO>();
		ConsumoDetalleTablaVO responseSingle = new ConsumoDetalleTablaVO();
		ConsumoFechaTablaVO responseTotalSingle = new ConsumoFechaTablaVO();
		List<ConsumoFechaTablaVO> responseTotalList = new ArrayList<ConsumoFechaTablaVO>();
		ConsumosTablaVO responseTotal = new ConsumosTablaVO();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     tipoEvento             : " + tipoEvento);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = consumos.flujo(dn, tipoEvento, true);
			
			int count = 10;
			for (ConsumoFechaTablaVO consumo : respuesta.getDetalleConsumo()) {
				for (ConsumoDetalleTablaVO detalle : consumo.getDetalle()) {
					if(count>0){
						responseSingle = new ConsumoDetalleTablaVO();
						responseSingle = detalle;
						responseList = new ArrayList<ConsumoDetalleTablaVO>();
						responseList.add(responseSingle);
						responseTotalSingle = new ConsumoFechaTablaVO();
						responseTotalSingle.setFecha(consumo.getFecha());
						responseTotalSingle.setDetalle(responseList);
						responseTotalList.add(responseTotalSingle);
						count--;
					}
				}
			}
			responseTotal.setIsPostPagoHibrido(respuesta.getIsPostPagoHibrido());
			responseTotal.setDetalleConsumo(responseTotalList);
			
			Logger.write("   + Respuesta              +");
			if(respuesta.getDetalleConsumo() != null)
				Logger.write("     DetalleConsumo.size    : " + responseTotal.getDetalleConsumo().size());

		}catch(Exception e){
			throw new ServiceException("[ERR] detalleConsumosTabla ["+dn+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("detalleConsumosTabla ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return responseTotal;
	}
	
	public WalletsDetalleVO walletsDetalle(String user, String pass, String dn, String tipoEvento, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("walletsDetalle");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleConsumosTabla consumos = new DetalleConsumosTabla();
		ConsumosTablaVO respuesta = new ConsumosTablaVO(); 
		List<ConsumoDetalleTablaVO> responseList = new ArrayList<ConsumoDetalleTablaVO>();
		ConsumoDetalleTablaVO responseSingle = new ConsumoDetalleTablaVO();
		ConsumoFechaTablaVO responseTotalSingle = new ConsumoFechaTablaVO();
		List<ConsumoFechaTablaVO> responseTotalList = new ArrayList<ConsumoFechaTablaVO>();
		WalletsDetalleVO responseTotal = new WalletsDetalleVO();
		OracleProcedures oracle = new OracleProcedures();
		int count = 0;
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     tipoEvento             : " + tipoEvento);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = consumos.flujo(dn, tipoEvento, true);
							
			count = Integer.parseInt(oracle.getValorParametro(136));
			
			for (ConsumoFechaTablaVO consumo : respuesta.getDetalleConsumo()) {
				for (ConsumoDetalleTablaVO detalle : consumo.getDetalle()) {
					if(count>0){
						responseSingle = new ConsumoDetalleTablaVO();
						responseSingle = detalle;
						responseList = new ArrayList<ConsumoDetalleTablaVO>();
						responseList.add(responseSingle);
						responseTotalSingle = new ConsumoFechaTablaVO();
						responseTotalSingle.setFecha(consumo.getFecha());
						responseTotalSingle.setDetalle(responseList);
						responseTotalList.add(responseTotalSingle);
						
						count--;
					}
				}
			}
			responseTotal.setIsPostPagoHibrido(respuesta.getIsPostPagoHibrido());
			responseTotal.setDetalleConsumo(responseTotalList);
			Logger.write("   + Respuesta              +");
			Logger.write("     IsPostPagoHibrido      : " + responseTotal.getIsPostPagoHibrido());
			if(respuesta.getDetalleConsumo() != null)
				Logger.write("     DetalleConsumo.size    : " + responseTotal.getDetalleConsumo().size());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				boolean mensajeDBControlado = Utilerias.contieneMensajeError(e.getLocalizedMessage());
				if(e.getLocalizedMessage().contains("vacio") || mensajeDBControlado){
					String msg = e.getLocalizedMessage();
					if(mensajeDBControlado)
						msg = Constantes.MSJ_BD_CONSUMOS;
					throw new ServiceException("[WARN] walletsDetalle ["+dn+"]["+tipoEvento+"] :: " + msg);
				}else{
					throw new ServiceException("[ERR] walletsDetalle ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());	
				}
			}
			else{
				throw new ServiceException("[ERR] walletsDetalle ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("walletsDetalle ["+dn+"]["+tipoEvento+"] :: " + getLocalAddress(), initime);
		}

		return responseTotal;
	}
	
	public WalletsDetalleVO walletsDetalleMes(String user, String pass, String dn, String tipoEvento, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("walletsDetalleMes");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleConsumosTabla consumos = new DetalleConsumosTabla();
		ConsumosTablaVO respuesta = new ConsumosTablaVO(); 
		WalletsDetalleVO responseTotal = new WalletsDetalleVO();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     tipoEvento             : " + tipoEvento);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = consumos.flujo(dn, tipoEvento, true);
			
			responseTotal.setIsPostPagoHibrido(respuesta.getIsPostPagoHibrido());
			if(respuesta.getDetalleConsumo() != null)
				responseTotal.setDetalleConsumo(respuesta.getDetalleConsumo());
			Logger.write("   + Respuesta              +");
			Logger.write("     IsPostPagoHibrido      : " + responseTotal.getIsPostPagoHibrido());
			if(respuesta.getDetalleConsumo() != null)
				Logger.write("     DetalleConsumo.size    : " + responseTotal.getDetalleConsumo().size());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				boolean mensajeDBControlado = Utilerias.contieneMensajeError(e.getLocalizedMessage());
				if(e.getLocalizedMessage().contains("vacio") || mensajeDBControlado){
					String msg = e.getLocalizedMessage();
					if(mensajeDBControlado)
						msg = Constantes.MSJ_BD_CONSUMOS;
					throw new ServiceException("[WARN] walletsDetalleMes ["+dn+"]["+tipoEvento+"] :: " + msg);
				}else{
					throw new ServiceException("[ERR] walletsDetalleMes ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());	
				}
			}
			else{
				throw new ServiceException("[ERR] walletsDetalleMes ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("walletsDetalleMes ["+dn+"]["+tipoEvento+"] :: " + getLocalAddress(), initime);
		}

		return responseTotal;
	}
	
	public WalletsDetalleVO walletsDetallePorPeriodo(String user, String pass, String dn,  String fechaIni, String fechaFin, String tipoEvento, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("walletsDetallePorPeriodo");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleConsumosTabla consumos = new DetalleConsumosTabla();
		ConsumosTablaVO respuesta = new ConsumosTablaVO(); 
		List<ConsumoDetalleTablaVO> responseList = new ArrayList<ConsumoDetalleTablaVO>();
		ConsumoDetalleTablaVO responseSingle = new ConsumoDetalleTablaVO();
		ConsumoFechaTablaVO responseTotalSingle = new ConsumoFechaTablaVO();
		List<ConsumoFechaTablaVO> responseTotalList = new ArrayList<ConsumoFechaTablaVO>();
		WalletsDetalleVO responseTotal = new WalletsDetalleVO();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     tipoEvento             : " + tipoEvento);
			Logger.write("     fechaIni               : " + fechaIni);
			Logger.write("     fechaFin               : " + fechaFin);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = consumos.flujoXPeriodo(dn, tipoEvento,fechaIni, fechaFin, true);
			
			
			for (ConsumoFechaTablaVO consumo : respuesta.getDetalleConsumo()) {
				for (ConsumoDetalleTablaVO detalle : consumo.getDetalle()) {
						responseSingle = new ConsumoDetalleTablaVO();
						responseSingle = detalle;
						responseList = new ArrayList<ConsumoDetalleTablaVO>();
						responseList.add(responseSingle);
						responseTotalSingle = new ConsumoFechaTablaVO();
						responseTotalSingle.setFecha(consumo.getFecha());
						responseTotalSingle.setDetalle(responseList);
						responseTotalList.add(responseTotalSingle);
				}
			}
			responseTotal.setIsPostPagoHibrido(respuesta.getIsPostPagoHibrido());
			responseTotal.setDetalleConsumo(responseTotalList);
			Logger.write("   + Respuesta              +");
			Logger.write("     IsPostPagoHibrido      : " + responseTotal.getIsPostPagoHibrido());
			if(respuesta.getDetalleConsumo() != null)
				Logger.write("     DetalleConsumo.size    : " + responseTotal.getDetalleConsumo().size());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				boolean mensajeDBControlado = Utilerias.contieneMensajeError(e.getLocalizedMessage());
				if(e.getLocalizedMessage().contains("vacio") || mensajeDBControlado){
					String msg = e.getLocalizedMessage();
					if(mensajeDBControlado)
						msg = Constantes.MSJ_BD_CONSUMOS;
					throw new ServiceException("[WARN] walletsDetallePorPeriodo ["+dn+"]["+tipoEvento+"] :: " + msg);
				}else{
					throw new ServiceException("[ERR] walletsDetallePorPeriodo ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());	
				}
			}
			else{
				throw new ServiceException("[ERR] walletsDetallePorPeriodo ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("walletsDetallePorPeriodo ["+dn+"]["+tipoEvento+"] :: " + getLocalAddress(), initime);
		}

		return responseTotal;
	}
	
	public ConsumosVO detalleConsumos(String user, String pass, String dn, String tipoEvento, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("detalleConsumos");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleConsumos consumos = new DetalleConsumos();
		ConsumosVO respuesta = new ConsumosVO(); 

		try{

			Logger.write("      user                        : -PROTEGIDO-");
			Logger.write("      pass                        : -PROTEGIDO-");
			Logger.write("      dn                          : " + dn);
			Logger.write("      tipoEvento                  : " + tipoEvento);
			Logger.write("      token                       : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("      remoteAddress               : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = consumos.flujo(dn, tipoEvento);

			Logger.write("      + Respuesta  +");
			Logger.write("      TotalDatos                  : " + respuesta.getTotalDatos());
			Logger.write("      TotalMensaje                : " + respuesta.getTotalMensaje());
			Logger.write("      TotalTA                     : " + respuesta.getTotalTA());
			Logger.write("      TotalVoz                    : " + respuesta.getTotalVoz());
			if(respuesta.getDetalleConsumo() != null)
				Logger.write("      DetalleConsumo.size         : " + respuesta.getDetalleConsumo().size());
			Logger.write("      DescripcionPlan             : " + respuesta.getDescripcionPlan());

		}catch(Exception e){
			throw new ServiceException("[ERR] detalleConsumos ["+dn+"]["+tipoEvento+"]:: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("detalleConsumos ["+dn+"]["+tipoEvento+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public ConsumosTotalesVO detalleConsumosTotales(String user, String pass, String dn, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("detalleConsumosTotales");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleConsumosTotales consumos = new DetalleConsumosTotales();
		ConsumosTotalesVO response = new ConsumosTotalesVO();
		OracleProcedures oracle = new OracleProcedures();
		ObtenerDescripcionPlanesVO1 respuesta = new ObtenerDescripcionPlanesVO1();
		String[] parametrosSplit = null;
		String cadenaQuitar = oracle.getValorParametro(5);
//		CallServiceObtenerDescripcionPlanes obtenPlanes = new CallServiceObtenerDescripcionPlanes();
//		String respuestaPlanes = "";
		if(cadenaQuitar != null && !cadenaQuitar.equals(""))
			parametrosSplit = cadenaQuitar.toLowerCase().trim().split(",");

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			response = consumos.flujo(dn);
			
			if(response.getDescripcionPlan() != null && !response.getDescripcionPlan().equals("")){
				try{
//					respuestaPlanes = obtenPlanes.serviceObtenerDescripcionPlanes("", response.getDescripcionPlan(), "0", 2121, "10.10.10.10");
//					if(respuestaPlanes != null && !respuestaPlanes.equals(""))
//						respuesta = ParseXMLFile.ParseObtenerDescripcionPlanes(respuestaPlanes);
					
					respuesta = OfertaComercial.consultaDescripcionPlanes("", response.getDescripcionPlan(), "0", 212121, "10.10.10.10");
					
				}catch (ServiceException e) {
					respuesta = new ObtenerDescripcionPlanesVO1();
				}
				response.setDatosPlanes(respuesta);
			}
			
			if(response.getDetalleFocalizacion() != null && response.getDetalleFocalizacion().getDescripcionPlan() != null && !response.getDetalleFocalizacion().getDescripcionPlan().equals("")){
				if(parametrosSplit != null && parametrosSplit.length>0) {
					String datoPivote = response.getDetalleFocalizacion().getDescripcionPlan();
					for(int a=0; a< parametrosSplit.length; a++) {
						datoPivote = datoPivote.toLowerCase();
						datoPivote = datoPivote.replace(parametrosSplit[a], "");
						response.getDetalleFocalizacion().setDescripcionPlan(datoPivote.toUpperCase());
					}
				}
			}
			if(response.getDescripcionPlan() != null && !response.getDescripcionPlan().equals("")){
				if(parametrosSplit != null && parametrosSplit.length>0) {
					String datoPivote = response.getDescripcionPlan();
					for(int a=0; a< parametrosSplit.length; a++) {
						datoPivote = datoPivote.toLowerCase();
						datoPivote = datoPivote.replace(parametrosSplit[a], "");
						response.setDescripcionPlan(datoPivote.toUpperCase());
					}
				}
			}
			
			response.setTotalMensaje(response.getDetalleConsumoMensajes().size()+"");
			
			Logger.write("   + Respuesta              +");
			Logger.write("     TotalDatos             : " + response.getTotalDatos());
			Logger.write("     TotalMensaje           : " + response.getTotalMensaje());
			Logger.write("     TotalTA                : " + response.getTotalTA());
			Logger.write("     TotalVoz               : " + response.getTotalVoz());
			Logger.write("     Detalle Llamadas.size  : " + response.getDetalleConsumoLlamadas().size());
			Logger.write("     Detalle Mensajes.size  : " + response.getDetalleConsumoMensajes().size());
			Logger.write("     Detalle Nav.size       : " + response.getDetalleConsumoNavegacion().size());
			Logger.write("     DescripcionPlan        : " + response.getDescripcionPlan());
			Logger.write("     DatosFocalizacion      : + ");

		}catch(Exception e){
			throw new ServiceException("[ERR] detalleConsumosTotales ["+dn+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("detalleConsumosTotales ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return response;
	}
	
	public DetalleTotalesVO walletsTotal(String user, String pass, String dn, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("walletsTotal");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			//Logger.write("      El usuario no es valido ");
			throw new ServiceException("[WARN] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			//Logger.write("     El password no es valido ");
			throw new ServiceException("[WARN] password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleConsumosTotales consumos = new DetalleConsumosTotales();
		ConsumosTotalesResponseVO response = new ConsumosTotalesResponseVO();
		DetalleTotalesVO respuestaTotal = new DetalleTotalesVO();
		StringBuffer sResponse = new StringBuffer();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
		OracleProcedures oracle = new OracleProcedures();
		Llamadas llamadas = new Llamadas();
		Mensajes mensajes = new Mensajes();
		Navegacion navegacion = new Navegacion();
		MinutosTodoDestino minutosTodoDestino = new MinutosTodoDestino();
		MinutosComunidad minutosComunidad = new MinutosComunidad();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		List<CatatoloGetVO> listaObtieneCatalogosNav = new ArrayList<CatatoloGetVO>();
		List<CatatoloGetVO> listaObtieneCatalogosMin = new ArrayList<CatatoloGetVO>();
		List<CatatoloGetVO> listaObtieneCatalogosMen = new ArrayList<CatatoloGetVO>();
		List<TipoServicioVO> listTipoServicio = new ArrayList<TipoServicioVO>();
		ConsumosXdnVO consumosFromBD = new ConsumosXdnVO();		
		String detalleTotalesPorTablaBD = "";
		String sumaAddOns = "";
		String consultaTAVivo = "";
		boolean ejecutaTotales = true;
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("[WARN] DN no puede ir vacio");

			try{
				sumaAddOns = oracle.getValorParametro(19);
			}catch (ServiceException e) {
				sumaAddOns = "0";
			}						
			
			try{
				detalleTotalesPorTablaBD = oracle.getValorParametro(74);
			}catch (ServiceException e) {
				detalleTotalesPorTablaBD = "0";
			}
			
			try{
				consultaTAVivo = oracle.getValorParametro(71);
			}catch (ServiceException e) {
				consultaTAVivo = "0";
			}
			
			DetalleWalletsVO descripcionWallets = new DetalleWalletsVO();
			sResponse.append(focalizacion.focalizacion(dn));
			if(sResponse != null && !sResponse.equals("")){
				descripcionWallets = ParseXMLFile.parseWallets(sResponse.toString());
				descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
				sResponse.setLength(0);
			}
			
			if(detalleTotalesPorTablaBD.equals("1")){
				try{
					//1 LLAMADAS, 2 MENSAJES, 3 NAVEGACION, 4 MINUTOS_TD, 5 MINUTOS_COMUNIDAD
					consumosFromBD = new ConsumosXdnVO();
					consumosFromBD = oracle.getconsumosXdn(dn, 1);
					response.setTotalTA(consumosFromBD.getTotal());
					llamadas.setMinutosDeRegalo(consumosFromBD.getRegalo());
					llamadas.setMinutosExtras(consumosFromBD.getExtra());
					if(consumosFromBD.getIncluidos() != null)
						llamadas.setLlamadasIncluidas(consumosFromBD.getIncluidos());
					else
						llamadas.setLlamadasIncluidas("0");
					llamadas.setTotalLlamadas(consumosFromBD.getTotal());
					
					consumosFromBD = new ConsumosXdnVO();
					consumosFromBD = oracle.getconsumosXdn(dn, 2);
					response.setTotalMensaje(consumosFromBD.getTotal());
					mensajes.setMensajesDeTextoDeRegalo(consumosFromBD.getRegalo());
					mensajes.setMensajesExtras(consumosFromBD.getExtra());
					if(consumosFromBD.getIncluidos() != null)
						mensajes.setMensajesIncluidos(consumosFromBD.getIncluidos());
					else
						mensajes.setMensajesIncluidos("0");
					mensajes.setTotalMensajes(consumosFromBD.getTotal());
					
					consumosFromBD = new ConsumosXdnVO();
					consumosFromBD = oracle.getconsumosXdn(dn, 3);
					response.setTotalDatos(consumosFromBD.getTotal());
					navegacion.setNavegacionDeRegalo(consumosFromBD.getRegalo());
					navegacion.setDatosExtra(consumosFromBD.getExtra());
					if(consumosFromBD.getIncluidos() != null)
						navegacion.setDatosIncluidos(consumosFromBD.getIncluidos());
					else
						navegacion.setDatosIncluidos("0");
					navegacion.setTotalDatos(consumosFromBD.getTotal());
					
					consumosFromBD = new ConsumosXdnVO();
					consumosFromBD = oracle.getconsumosXdn(dn, 4);
					minutosTodoDestino.setMinutosTDregalo(consumosFromBD.getRegalo());
					minutosTodoDestino.setMinutosTDextra(consumosFromBD.getExtra());
					minutosTodoDestino.setMinutosTDincluido(consumosFromBD.getIncluidos());
					minutosTodoDestino.setMinutosTDconsumido(consumosFromBD.getTotal());
					
					consumosFromBD = new ConsumosXdnVO();
					consumosFromBD = oracle.getconsumosXdn(dn, 5);
					response.setTotalCom(consumosFromBD.getRegalo());
					minutosComunidad.setMinutosComunidadRegalo(consumosFromBD.getRegalo());
					minutosComunidad.setMinutosComunidadExtra(consumosFromBD.getExtra());
					minutosComunidad.setMinutosComunidadIncluido(consumosFromBD.getIncluidos());
					minutosComunidad.setMinutosComunidadConsumidos(consumosFromBD.getTotal());
					llamadas.setMinutosComunidadConsumidos(consumosFromBD.getTotal());
					
					response.setDatosPlanes(oracle.getPlanXdn(dn,1));
					
					sResponse.append(focalizacion.focalizacion(dn));
					if(sResponse != null && !sResponse.equals("")){
						descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
					}
					response.setDetalleFocalizacion(descripcion);
				}catch (Exception e) {
					response = consumos.flujoFromWallets(dn, descripcion);
					detalleTotalesPorTablaBD = "0";
				}
				ejecutaTotales = false;
			}else{
				ejecutaTotales = true;
			}
			
			if(ejecutaTotales){
				response = consumos.flujoFromWallets(dn, descripcion);
			}
			
			/*Nuevo*/
			try{
				List<ServiciosAdicionalesVO> listServiciosAdicionales = new ArrayList<ServiciosAdicionalesVO>();
				listServiciosAdicionales = GetServiciosAdicionales.flujoPorDnFromWall(response.getDatosPlanes(), response.getDetalleFocalizacion(), 0, dn);
				response.setDetalleServiciosAdicionales(listServiciosAdicionales);
			}catch (Exception e) {
				Logger.write(e.getLocalizedMessage());
			}

			double totalCostoLlamada = 0;
			double totalVoz = 0;
			try{
				if(response != null && response.getDatosPlanes() != null && response.getDatosPlanes().getMinutoadc() != null){
					totalCostoLlamada = Double.parseDouble(response.getDatosPlanes().getMinutoadc());
				}
				if(response != null && response.getTotalTA() != null){
					totalVoz = Double.parseDouble(response.getTotalTA());
				}
			}catch (NumberFormatException e) {
				totalCostoLlamada = 0;
				totalVoz = 0;
			}

			llamadas.setTotalCostoLlamadas(formatNumber.format(totalCostoLlamada * totalVoz));
			if(response.getDatosPlanes() != null && response.getDatosPlanes().getMinutos() != null)
				llamadas.setLlamadasIncluidas(response.getDatosPlanes().getMinutos());

			double totalmsjCosto = 0;
			double totalmsj = 0;
			double msjAdc = 0;
			try{
				if(response != null && response.getTotalMensaje() != null){
					totalmsj = Double.parseDouble(response.getTotalMensaje());
				}
				if(response.getDatosPlanes() != null && response.getDatosPlanes().getMegaadc() != null)
					msjAdc = Double.parseDouble(response.getDatosPlanes().getMegaadc());
				totalmsjCosto = totalmsj * msjAdc;
			}catch (NumberFormatException e) {
				totalmsjCosto = 0;
			}
			mensajes.setTotalCostoMensajes(formatNumber.format(totalmsjCosto));
			if(response.getDatosPlanes() != null){
				mensajes.setMensajesIncluidos(response.getDatosPlanes().getMensajes());
				//polo
				//respuestaTotal.setDescripcionPlanes(response.getDatosPlanes());
				respuestaTotal.setDescripcionPlanes(new ObtenerDescripcionPlanesVO());
				respuestaTotal.getDescripcionPlanes().setMegaadc(response.getDatosPlanes().getMegaadc());
				respuestaTotal.getDescripcionPlanes().setMegas(response.getDatosPlanes().getMegas());
				respuestaTotal.getDescripcionPlanes().setMensajeadc(response.getDatosPlanes().getMensajeadc());
				respuestaTotal.getDescripcionPlanes().setMensajes(response.getDatosPlanes().getMensajes());
				respuestaTotal.getDescripcionPlanes().setMinutoadc(response.getDatosPlanes().getMinutoadc());
				respuestaTotal.getDescripcionPlanes().setMinutos(response.getDatosPlanes().getMinutos());
				respuestaTotal.getDescripcionPlanes().setMinutoscomunidad(response.getDatosPlanes().getMinutoscomunidad());
				respuestaTotal.getDescripcionPlanes().setNombreCortoPlan(response.getDatosPlanes().getNombreCortoPlan());
				respuestaTotal.getDescripcionPlanes().setTiempoAire(response.getDatosPlanes().getTiempoAire());
			}

			navegacion.setTotalDatos(response.getTotalDatos());
			navegacion.setTotalCostoDatos(response.getTotalDatosCosto());
			int minutosC = 0;
			int minutosTD = 0;
			if(response.getDetalleServiciosAdicionales() != null && response.getDetalleServiciosAdicionales().size() > 0){
				int megas = 0;	
				int megasExtra = 0;
				int mensajesTotal=0;
				int mensajesExtras = 0;
				int minutos = 0; 
				int llamadasExtras = 0;
                
				
				if(response != null && response.getDatosPlanes() != null) {
					
					if(response.getDatosPlanes().getMegas() != null && !response.getDatosPlanes().getMegas().equals(""))
					{
						try{
							megas = Integer.parseInt(response.getDatosPlanes().getMegas().trim());
						}catch (Exception e) {
							megas = 0;
						}
					}
					if(response.getDatosPlanes().getMinutos() != null && !response.getDatosPlanes().getMinutos().equals(""))
					{
						try{
							minutos = Integer.parseInt(response.getDatosPlanes().getMinutos().trim());
						}catch (Exception e) {
							minutos = 0;
						}
					}
					if(response.getDatosPlanes().getMensajes() != null && !response.getDatosPlanes().getMensajes().equals(""))
					{
						try{
							mensajesTotal = Integer.parseInt(response.getDatosPlanes().getMensajes().trim());
						}catch (Exception e) {
							mensajesTotal = 0;
						}
					}

					try{
						listaObtieneCatalogosNav = oracle.catalogoGet(9);
						listaObtieneCatalogosMin = oracle.catalogoGet(10);
						listaObtieneCatalogosMen = oracle.catalogoGet(11);
						listTipoServicio = oracle.getTipoServicio(2); //Navegacion
					}catch (Exception e) {
						Logger.write("     Detail al consultar el catalogo");
					}
					
					for(int a = 0; a < response.getDetalleServiciosAdicionales().size(); a++){
						String IdServicio = response.getDetalleServiciosAdicionales().listIterator(a).next().getIdServicio();
						int status = response.getDetalleServiciosAdicionales().listIterator(a).next().getStatus();
						
						if(sumaAddOns.equals("1")){
							
							for(int c=0; c <listaObtieneCatalogosNav.size();c++){
								if( IdServicio.equals(listaObtieneCatalogosNav.get(c).getTipoRespuestaID()+"") && status == 1){
									//megas = megas + Integer.parseInt(listaObtieneCatalogosNav.get(c).getTipoRepuesta());	
									megasExtra = megasExtra + Integer.parseInt(listaObtieneCatalogosNav.get(c).getTipoRepuesta());
								}
							}
							
							for(int c=0; c <listaObtieneCatalogosMin.size();c++){
								if( IdServicio.equals(listaObtieneCatalogosMin.get(c).getTipoRespuestaID()+"") && status == 1){
									//minutos = minutos + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());	
									llamadasExtras = llamadasExtras + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
									
									for(int v=0; v < listTipoServicio.size(); v++){
										if(listTipoServicio.get(v).getServicio().equals(IdServicio)){  
											if(listTipoServicio.get(v).getDescripcion().toLowerCase().contains("comunidad")== true){
												minutosC = minutosC + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());	
											}else{//Todo Destino
												minutosTD = minutosTD + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
											}
										}
									}
									//742-743 Comunidad
//									if (IdServicio.equals("742") || IdServicio.equals("743") ) {
//										minutosC = minutosC + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
//									}
//									//775-776-777-778-779	
//									if (IdServicio.equals("775") || IdServicio.equals("776") || IdServicio.equals("777") || IdServicio.equals("778") || IdServicio.equals("779") ) {
//										minutosTD = minutosTD + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
//									}
									
								}
							}
							
							for(int c=0; c <listaObtieneCatalogosMen.size();c++){
								if( IdServicio.equals(listaObtieneCatalogosMen.get(c).getTipoRespuestaID()+"") && status == 1){
//									mensajesTotal = mensajesTotal +  Integer.parseInt(listaObtieneCatalogosMen.get(c).getTipoRepuesta());	
									mensajesExtras = mensajesExtras + Integer.parseInt(listaObtieneCatalogosMen.get(c).getTipoRepuesta());
								}
							}
						}else{
							if(IdServicio.equals("797") && status == 1){
								//megas = megas + 5;	
								megasExtra = megasExtra + 5; 
							}else if(IdServicio.equals("798") && status == 1) {
								//megas = megas + 10;       
								megasExtra = megasExtra + 10;
							}else if(IdServicio.equals("799") && status == 1) {
								//megas = megas + 50;
								megasExtra = megasExtra + 50;
							}else if(IdServicio.equals("800") && status == 1) {
								//megas = megas + 100;
								megasExtra = megasExtra + 100;
							}else if(IdServicio.equals("801") && status == 1) {
								//megas = megas + 500;
								megasExtra = megasExtra + 500;
							}else if(IdServicio.equals("729") && status == 1) {
								//megas = megas + (1 * 1024);
								megasExtra = megasExtra + (1 * 1024);
							}else if(IdServicio.equals("802") && status == 1) {
								//megas = megas + (2 * 1024);
								megasExtra = megasExtra + (2 * 1024);
							}else if(IdServicio.equals("803") && status == 1) {
								//megas = megas + (3 * 1024);
								megasExtra = megasExtra + (3 * 1024);
							}else if(IdServicio.equals("804") && status == 1) {
								//megas = megas + (5 * 1024);
								megasExtra = megasExtra + (5 * 1024);
							}else if(IdServicio.equals("805") && status == 1) {
								//megas = megas + (10 * 1024);
								megasExtra = megasExtra + (10 * 1024);
							}else if(IdServicio.equals("465") && status == 1) {
								//megas = megas + 50;
								megasExtra = megasExtra + 50;
							}else if(IdServicio.equals("539") && status == 1) {
								//megas = megas + 270;    	
								megasExtra = megasExtra + 270;	
							}
						}
					}
				}

				if(detalleTotalesPorTablaBD.equals("1")){
					
					navegacion.setDatosExtra(megasExtra+"");
					
					llamadas.setMinutosExtras(llamadasExtras+"");
					
					mensajes.setMensajesExtras(mensajesExtras+"");
					
					minutosTodoDestino.setMinutosTDextra(String.valueOf(minutosTD));
					minutosTodoDestino.setMinutosTDregalo("0");
					
					minutosComunidad.setMinutosComunidadExtra(String.valueOf(minutosC));
					minutosComunidad.setMinutosComunidadRegalo("0");
					
				}else{
					llamadas.setMinutosComunidadConsumidos(response.getTotalCom());
					llamadas.setTotalLlamadas(response.getTotalTA());
					mensajes.setTotalMensajes(response.getTotalMensaje());
					
					navegacion.setDatosIncluidos(megas+"");
					navegacion.setDatosExtra(megasExtra+"");

					llamadas.setLlamadasIncluidas(minutos+"");
					llamadas.setMinutosExtras(llamadasExtras+"");

					mensajes.setMensajesIncluidos(mensajesTotal+"");
					mensajes.setMensajesExtras(mensajesExtras+"");

					minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
					minutosTodoDestino.setMinutosTDextra(String.valueOf(minutosTD));
					minutosTodoDestino.setMinutosTDregalo("0");
					minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());

					minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
					minutosComunidad.setMinutosComunidadExtra(String.valueOf(minutosC));
					minutosComunidad.setMinutosComunidadRegalo("0");
					minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());
				}
			}else{
				llamadas.setMinutosComunidadConsumidos(response.getTotalCom());
				llamadas.setTotalLlamadas(response.getTotalTA());
				mensajes.setTotalMensajes(response.getTotalMensaje());
				
				if(response.getDatosPlanes() != null && response.getDatosPlanes().getMegas() != null){
					
					minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
					minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());
					minutosComunidad.setMinutosComunidadRegalo("0");
					
					minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
					minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());
					minutosTodoDestino.setMinutosTDregalo("0");
					
					navegacion.setDatosIncluidos(response.getDatosPlanes().getMegas());
				    mensajes.setMensajesIncluidos(response.getDatosPlanes().getMensajes());
			 	    llamadas.setLlamadasIncluidas(response.getDatosPlanes().getMinutos());
			 	    
			 	    minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
					minutosTodoDestino.setMinutosTDextra(String.valueOf(minutosTD));
					minutosTodoDestino.setMinutosTDregalo("0");
					minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());
					
					minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
					minutosComunidad.setMinutosComunidadExtra(String.valueOf(minutosC));
	                minutosComunidad.setMinutosComunidadRegalo("0");
	                minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());
				}
			} 

			if(consultaTAVivo.equals("1")){
//				sResponse.setLength(0);
//				sResponse.append(focalizacion.focalizacion(dn));
//				if(sResponse != null && !sResponse.equals("")){
//					descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
//				}
				if(descripcion.getTiempoAireComprado() != null && descripcion.getTiempoAireComprado().equals(""))
					respuestaTotal.setTiempoAireAbonado(descripcion.getTiempoAireComprado());
				else if(descripcion.getSaldoPrepago() != null && !descripcion.getSaldoPrepago().equals(""))
					respuestaTotal.setTiempoAireAbonado(descripcion.getSaldoPrepago());
				else
					respuestaTotal.setTiempoAireAbonado("");
			}else{
				if(response.getDetalleFocalizacion().getTiempoAireComprado() != null && response.getDetalleFocalizacion().getTiempoAireComprado().equals(""))
					respuestaTotal.setTiempoAireAbonado(response.getDetalleFocalizacion().getTiempoAireComprado());
				else if(response.getDetalleFocalizacion().getSaldoPrepago() != null && !response.getDetalleFocalizacion().getSaldoPrepago().equals(""))
					respuestaTotal.setTiempoAireAbonado(response.getDetalleFocalizacion().getSaldoPrepago());
				else
					respuestaTotal.setTiempoAireAbonado("");
			}

			if(descripcionWallets != null){
				llamadas.setMinutosDeRegalo(descripcionWallets.getMinutosDeRegalo());
				
				//polo
				//mensajes.setMensajesDeTextoDeRegalo(descripcionWallets.getMensajesDeTexto());
				//navegacion.setNavegacionDeRegalo(descripcionWallets.getNavegacion());
				mensajes.setMensajesDeTextoDeRegalo("0");
				navegacion.setNavegacionDeRegalo("0");
				//respuestaTotal.setTiempoAireDeRegalo("0");
				
				if(descripcionWallets.getTiempoAireDeRegalo() != null && !descripcionWallets.getTiempoAireDeRegalo().equals(""))
					  respuestaTotal.setTiempoAireDeRegalo(descripcionWallets.getTiempoAireDeRegalo());
				else
	    			  respuestaTotal.setTiempoAireDeRegalo("0");
				
				if(descripcionWallets.getTiempoAireTotal() != null && !descripcionWallets.getTiempoAireTotal().equals(""))
				  respuestaTotal.setTiempoAireTotal(descripcionWallets.getTiempoAireTotal());
				else
					respuestaTotal.setTiempoAireTotal("0");
			}
			
			//polo
//			if(descripcion != null){
//				respuestaTotal.setVigenciaSalgoPrepago(descripcion.getVigenciaSalgoPrepago());
//			}
			respuestaTotal.setLlamadas(llamadas);
			respuestaTotal.setMensajes(mensajes);
			respuestaTotal.setNavegacion(navegacion);
			respuestaTotal.setMinutosComunidad(minutosComunidad);
			respuestaTotal.setMinutosTodoDestino(minutosTodoDestino);
			
			Logger.write("   + Respuesta              +");
			Logger.write("     TotalDatos             : " + response.getTotalDatos());
			Logger.write("     TotalMensaje           : " + response.getTotalMensaje());
			Logger.write("     TotalTA                : " + response.getTotalTA());
			Logger.write("     TotalVoz               : " + response.getTotalVoz());
			Logger.write("     DescripcionPlan        : " + response.getDescripcionPlan());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				boolean mensajeDBControlado = Utilerias.contieneMensajeError(e.getLocalizedMessage());
				if(e.getLocalizedMessage().contains("vacio") || mensajeDBControlado){
					String msg = e.getLocalizedMessage();
					if(mensajeDBControlado)
						msg = Constantes.MSJ_BD_CONSUMOS;
					throw new ServiceException("[WARN] walletsTotal ["+dn+"] :: " + msg);
				}else{
					throw new ServiceException("[ERR] walletsTotal ["+dn+"] :: " + e.getLocalizedMessage());	
				}
			}
			else{
				throw new ServiceException("[ERR] walletsTotal ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("walletsTotal ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuestaTotal;
	}
	
	public DetalleTotalesLlamadas walletsTotalLlamadas(String user, String pass, String dn, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("walletsTotalLlamadas");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			//Logger.write("      El usuario no es valido ");
			throw new ServiceException("[WARN] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			//Logger.write("     El password no es valido ");
			throw new ServiceException("[WARN] password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleConsumosTotales consumos = new DetalleConsumosTotales();
		ConsumosTotalesResponseVO response = new ConsumosTotalesResponseVO();
		DetalleTotalesLlamadas respuestaTotal = new DetalleTotalesLlamadas();
		StringBuffer sResponse = new StringBuffer();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
		OracleProcedures oracle = new OracleProcedures();
		Llamadas llamadas = new Llamadas();
		MinutosTodoDestino minutosTodoDestino = new MinutosTodoDestino();
		MinutosComunidad minutosComunidad = new MinutosComunidad();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		List<CatatoloGetVO> listaObtieneCatalogosMin = new ArrayList<CatatoloGetVO>();
		List<TipoServicioVO> listTipoServicio = new ArrayList<TipoServicioVO>();		
		String sumaAddOns = "";
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("[WARN] DN no puede ir vacio");

			try{
				sumaAddOns = oracle.getValorParametro(19);
			}catch (ServiceException e) {
				sumaAddOns = "0";
			}						
			
			DetalleWalletsVO descripcionWallets = new DetalleWalletsVO();
			sResponse.append(focalizacion.focalizacion(dn));
			if(sResponse != null && !sResponse.equals("")){
				descripcionWallets = ParseXMLFile.parseWallets(sResponse.toString());
				descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
				sResponse.setLength(0);
			}
			
			response = consumos.flujoFromWalletsDividido(dn, descripcion, "1");
			
			/*Nuevo*/
			try{
				List<ServiciosAdicionalesVO> listServiciosAdicionales = new ArrayList<ServiciosAdicionalesVO>();
				listServiciosAdicionales = GetServiciosAdicionales.flujoPorDnFromWall(response.getDatosPlanes(), descripcion, 0, dn);
				response.setDetalleServiciosAdicionales(listServiciosAdicionales);
			}catch (Exception e) {
				Logger.write(e.getLocalizedMessage());
			}

			double totalCostoLlamada = 0;
			double totalVoz = 0;
			try{
				if(response != null && response.getDatosPlanes() != null && response.getDatosPlanes().getMinutoadc() != null){
					totalCostoLlamada = Double.parseDouble(response.getDatosPlanes().getMinutoadc());
				}
				if(response != null && response.getTotalTA() != null){
					totalVoz = Double.parseDouble(response.getTotalTA());
				}
			}catch (NumberFormatException e) {
				totalCostoLlamada = 0;
				totalVoz = 0;
			}

			llamadas.setTotalCostoLlamadas(formatNumber.format(totalCostoLlamada * totalVoz));
			if(response.getDatosPlanes() != null && response.getDatosPlanes().getMinutos() != null)
				llamadas.setLlamadasIncluidas(response.getDatosPlanes().getMinutos());

			int minutosC = 0;
			int minutosTD = 0;
			if(response.getDetalleServiciosAdicionales() != null && response.getDetalleServiciosAdicionales().size() > 0){
				int minutos = 0; 
				int llamadasExtras = 0;
				
				if(response != null && response.getDatosPlanes() != null) {
					if(response.getDatosPlanes().getMinutos() != null && !response.getDatosPlanes().getMinutos().equals(""))
					{
						try{
							minutos = Integer.parseInt(response.getDatosPlanes().getMinutos().trim());
						}catch (Exception e) {
							minutos = 0;
						}
					}

					try{
						listaObtieneCatalogosMin = oracle.catalogoGet(10);
						listTipoServicio = oracle.getTipoServicio(2); //Navegacion
					}catch (Exception e) {
						Logger.write("     Detail al consultar el catalogo");
					}
					
					for(int a = 0; a < response.getDetalleServiciosAdicionales().size(); a++){
						String IdServicio = response.getDetalleServiciosAdicionales().listIterator(a).next().getIdServicio();
						int status = response.getDetalleServiciosAdicionales().listIterator(a).next().getStatus();
						
						if(sumaAddOns.equals("1")){
							
							for(int c=0; c <listaObtieneCatalogosMin.size();c++){
								if( IdServicio.equals(listaObtieneCatalogosMin.get(c).getTipoRespuestaID()+"") && status == 1){
									//minutos = minutos + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());	
									llamadasExtras = llamadasExtras + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
									
									for(int v=0; v < listTipoServicio.size(); v++){
										if(listTipoServicio.get(v).getServicio().equals(IdServicio)){  
											if(listTipoServicio.get(v).getDescripcion().toLowerCase().contains("comunidad")== true){
												minutosC = minutosC + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());	
											}else{//Todo Destino
												minutosTD = minutosTD + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
											}
										}
									}
								}
							}
						}
					}
				}

					llamadas.setMinutosComunidadConsumidos(response.getTotalCom());
					llamadas.setTotalLlamadas(response.getTotalTA());
					llamadas.setLlamadasIncluidas(minutos+"");
					llamadas.setMinutosExtras(llamadasExtras+"");
					minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
					minutosTodoDestino.setMinutosTDextra(String.valueOf(minutosTD));
					minutosTodoDestino.setMinutosTDregalo("0");
					minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());
					minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
					minutosComunidad.setMinutosComunidadExtra(String.valueOf(minutosC));
					minutosComunidad.setMinutosComunidadRegalo("0");
					minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());
			}else{
				llamadas.setMinutosComunidadConsumidos(response.getTotalCom());
				llamadas.setTotalLlamadas(response.getTotalTA());
				if(response.getDatosPlanes() != null && response.getDatosPlanes().getMegas() != null){
					minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
					minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());
					minutosComunidad.setMinutosComunidadRegalo("0");
					
					minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
					minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());
					minutosTodoDestino.setMinutosTDregalo("0");
					
			 	    llamadas.setLlamadasIncluidas(response.getDatosPlanes().getMinutos());
			 	    
			 	    minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
					minutosTodoDestino.setMinutosTDextra(String.valueOf(minutosTD));
					minutosTodoDestino.setMinutosTDregalo("0");
					minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());
					
					minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
					minutosComunidad.setMinutosComunidadExtra(String.valueOf(minutosC));
	                minutosComunidad.setMinutosComunidadRegalo("0");
	                minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());
				}
			} 

			if(descripcion.getTiempoAireComprado() != null && descripcion.getTiempoAireComprado().equals(""))
				respuestaTotal.setTiempoAireAbonado(descripcion.getTiempoAireComprado());
			else if(descripcion.getSaldoPrepago() != null && !descripcion.getSaldoPrepago().equals(""))
				respuestaTotal.setTiempoAireAbonado(descripcion.getSaldoPrepago());
			else
				respuestaTotal.setTiempoAireAbonado("");

			if(descripcionWallets != null){
				llamadas.setMinutosDeRegalo(descripcionWallets.getMinutosDeRegalo());
				if(descripcionWallets.getTiempoAireDeRegalo() != null && !descripcionWallets.getTiempoAireDeRegalo().equals(""))
					  respuestaTotal.setTiempoAireDeRegalo(descripcionWallets.getTiempoAireDeRegalo());
				else
	    			  respuestaTotal.setTiempoAireDeRegalo("0");
				
				if(descripcionWallets.getTiempoAireTotal() != null && !descripcionWallets.getTiempoAireTotal().equals(""))
				  respuestaTotal.setTiempoAireTotal(descripcionWallets.getTiempoAireTotal());
				else
					respuestaTotal.setTiempoAireTotal("0");
			}
			respuestaTotal.setLlamadas(llamadas);
			respuestaTotal.setMinutosComunidad(minutosComunidad);
			respuestaTotal.setMinutosTodoDestino(minutosTodoDestino);
			
			Logger.write("   + Respuesta              +");
			Logger.write("     TotalTA                : " + response.getTotalTA());
			Logger.write("     TotalVoz               : " + response.getTotalVoz());
			Logger.write("     DescripcionPlan        : " + response.getDescripcionPlan());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				boolean mensajeDBControlado = Utilerias.contieneMensajeError(e.getLocalizedMessage());
				if(e.getLocalizedMessage().contains("vacio") || mensajeDBControlado){
					String msg = e.getLocalizedMessage();
					if(mensajeDBControlado)
						msg = Constantes.MSJ_BD_CONSUMOS;
					throw new ServiceException("[WARN] walletsTotalLlamadas ["+dn+"] :: " + msg);
				}else{
					throw new ServiceException("[ERR] walletsTotalLlamadas ["+dn+"] :: " + e.getLocalizedMessage());	
				}
			}
			else{
				throw new ServiceException("[ERR] walletsTotalLlamadas ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("walletsTotalLlamadas ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuestaTotal;
	}
	
	public Mensajes walletsTotalMensajes(String user, String pass, String dn, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("walletsTotalMensajes");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[WARN] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[WARN] password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleConsumosTotales consumos = new DetalleConsumosTotales();
		ConsumosTotalesResponseVO response = new ConsumosTotalesResponseVO();
		StringBuffer sResponse = new StringBuffer();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
		OracleProcedures oracle = new OracleProcedures();
		Mensajes mensajes = new Mensajes();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		List<CatatoloGetVO> listaObtieneCatalogosMen = new ArrayList<CatatoloGetVO>();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("[WARN] DN no puede ir vacio");

			DetalleWalletsVO descripcionWallets = new DetalleWalletsVO();
			sResponse.append(focalizacion.focalizacion(dn));
			if(sResponse != null && !sResponse.equals("")){
				descripcionWallets = ParseXMLFile.parseWallets(sResponse.toString());
				descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
				sResponse.setLength(0);
			}
			
			response = consumos.flujoFromWalletsDividido(dn, descripcion, "2");
			
			/*Nuevo*/
			try{
				List<ServiciosAdicionalesVO> listServiciosAdicionales = new ArrayList<ServiciosAdicionalesVO>();
				listServiciosAdicionales = GetServiciosAdicionales.flujoPorDnFromWall(response.getDatosPlanes(), descripcion, 0, dn);
				response.setDetalleServiciosAdicionales(listServiciosAdicionales);
			}catch (Exception e) {
				Logger.write(e.getLocalizedMessage());
			}

			double totalmsjCosto = 0;
			double totalmsj = 0;
			double msjAdc = 0;
			try{
				if(response != null && response.getTotalMensaje() != null){
					totalmsj = Double.parseDouble(response.getTotalMensaje());
				}
				if(response.getDatosPlanes() != null && response.getDatosPlanes().getMegaadc() != null)
					msjAdc = Double.parseDouble(response.getDatosPlanes().getMegaadc());
				totalmsjCosto = totalmsj * msjAdc;
			}catch (NumberFormatException e) {
				totalmsjCosto = 0;
			}
			mensajes.setTotalCostoMensajes(formatNumber.format(totalmsjCosto));
			if(response.getDatosPlanes() != null){
				mensajes.setMensajesIncluidos(response.getDatosPlanes().getMensajes());
			}

			if(response.getDetalleServiciosAdicionales() != null && response.getDetalleServiciosAdicionales().size() > 0){
				int mensajesTotal=0;
				int mensajesExtras = 0;
				
				if(response != null && response.getDatosPlanes() != null) {
					if(response.getDatosPlanes().getMensajes() != null && !response.getDatosPlanes().getMensajes().equals(""))
					{
						try{
							mensajesTotal = Integer.parseInt(response.getDatosPlanes().getMensajes().trim());
						}catch (Exception e) {
							mensajesTotal = 0;
						}
					}

					try{
						listaObtieneCatalogosMen = oracle.catalogoGet(11);  //planes o servicios adicionales que traen mensajes
					}catch (Exception e) {
						Logger.write("     Detail al consultar el catalogo");
					}
					
					for(int a = 0; a < response.getDetalleServiciosAdicionales().size(); a++){
						String IdServicio = response.getDetalleServiciosAdicionales().listIterator(a).next().getIdServicio();
						int status = response.getDetalleServiciosAdicionales().listIterator(a).next().getStatus();

						for(int c=0; c <listaObtieneCatalogosMen.size();c++){
							if( IdServicio.equals(listaObtieneCatalogosMen.get(c).getTipoRespuestaID()+"") && status == 1){	
								mensajesExtras = mensajesExtras + Integer.parseInt(listaObtieneCatalogosMen.get(c).getTipoRepuesta());
							}
						}
					}
				}

					mensajes.setTotalMensajes(response.getTotalMensaje());
					mensajes.setMensajesIncluidos(mensajesTotal+"");
					mensajes.setMensajesExtras(mensajesExtras+"");
			}else{
				mensajes.setTotalMensajes(response.getTotalMensaje());
				
				if(response.getDatosPlanes() != null && response.getDatosPlanes().getMegas() != null){
				    mensajes.setMensajesIncluidos(response.getDatosPlanes().getMensajes());
				}
			} 

			if(descripcionWallets != null){
				mensajes.setMensajesDeTextoDeRegalo("0");
			}
			
			Logger.write("   + Respuesta              +");
			Logger.write("     MsjRegalo              : " + mensajes.getMensajesDeTextoDeRegalo());
			Logger.write("     MsjExtras              : " + mensajes.getMensajesExtras());
			Logger.write("     MsjIncluidos           : " + mensajes.getMensajesIncluidos());
			Logger.write("     CostoMsjc              : " + mensajes.getTotalCostoMensajes());
			Logger.write("     TotalMsjs              : " + mensajes.getTotalMensajes());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				boolean mensajeDBControlado = Utilerias.contieneMensajeError(e.getLocalizedMessage());
				if(e.getLocalizedMessage().contains("vacio") || mensajeDBControlado){
					String msg = e.getLocalizedMessage();
					if(mensajeDBControlado)
						msg = Constantes.MSJ_BD_CONSUMOS;
					throw new ServiceException("[WARN] walletsTotalMensajes ["+dn+"] :: " + msg);
				}else{
					throw new ServiceException("[ERR] walletsTotalMensajes ["+dn+"] :: " + e.getLocalizedMessage());	
				}
			}
			else{
				throw new ServiceException("[ERR] walletsTotalMensajes ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("walletsTotalMensajes ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return mensajes;
	}
	
	public Navegacion walletsTotalNavegacion(String user, String pass, String dn, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("walletsTotalNavegacion");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[WARN] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[WARN] password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleConsumosTotales consumos = new DetalleConsumosTotales();
		ConsumosTotalesResponseVO response = new ConsumosTotalesResponseVO();
		StringBuffer sResponse = new StringBuffer();
		OracleProcedures oracle = new OracleProcedures();
		Navegacion navegacion = new Navegacion();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		List<CatatoloGetVO> listaObtieneCatalogosNav = new ArrayList<CatatoloGetVO>();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("[WARN] DN no puede ir vacio");
			
			DetalleWalletsVO descripcionWallets = new DetalleWalletsVO();
			sResponse.append(focalizacion.focalizacion(dn));
			if(sResponse != null && !sResponse.equals("")){
				descripcionWallets = ParseXMLFile.parseWallets(sResponse.toString());
				descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
				sResponse.setLength(0);
			}
			
			response = consumos.flujoFromWalletsDividido(dn, descripcion, "4");
			
			/*Nuevo*/
			try{
				List<ServiciosAdicionalesVO> listServiciosAdicionales = new ArrayList<ServiciosAdicionalesVO>();
				listServiciosAdicionales = GetServiciosAdicionales.flujoPorDnFromWall(response.getDatosPlanes(), descripcion, 0, dn);
				response.setDetalleServiciosAdicionales(listServiciosAdicionales);
			}catch (Exception e) {
				Logger.write(e.getLocalizedMessage());
			}

			int megas = 0;	
			int megasExtra = 0;
			navegacion.setTotalDatos(response.getTotalDatos());
			navegacion.setTotalCostoDatos(response.getTotalDatosCosto());
			if(response.getDetalleServiciosAdicionales() != null && response.getDetalleServiciosAdicionales().size() > 0){				
				if(response != null && response.getDatosPlanes() != null) {

					if(response.getDatosPlanes().getMegas() != null && !response.getDatosPlanes().getMegas().equals(""))
					{
						try{
							megas = Integer.parseInt(response.getDatosPlanes().getMegas().trim());
						}catch (Exception e) {
							megas = 0;
						}
					}

					try{
						listaObtieneCatalogosNav = oracle.catalogoGet(9);
					}catch (Exception e) {
						Logger.write("     Detail al consultar el catalogo");
					}

					for(int a = 0; a < response.getDetalleServiciosAdicionales().size(); a++){
						String IdServicio = response.getDetalleServiciosAdicionales().listIterator(a).next().getIdServicio();
						int status = response.getDetalleServiciosAdicionales().listIterator(a).next().getStatus();

						for(int c=0; c <listaObtieneCatalogosNav.size();c++){
							if( IdServicio.equals(listaObtieneCatalogosNav.get(c).getTipoRespuestaID()+"") && status == 1){	
								megasExtra = megasExtra + Integer.parseInt(listaObtieneCatalogosNav.get(c).getTipoRepuesta());
							}
						}
					}
				}
			
					navegacion.setDatosIncluidos(megas+"");
					navegacion.setDatosExtra(megasExtra+"");
			}else{
				if(response.getDatosPlanes() != null && response.getDatosPlanes().getMegas() != null){
					navegacion.setDatosIncluidos(response.getDatosPlanes().getMegas());
				}
			} 


			if(descripcionWallets != null){
				navegacion.setNavegacionDeRegalo("0");
			}
			
			try{				
				String compania = "";
				String activaCalculo = "0";
				try{	
					compania = oracle.getValorParametro(143);					
				}catch (Exception e) {
					compania = "PVS";					
				}
				try{						
					activaCalculo = oracle.getValorParametro(211);
				}catch (Exception e) {					
					activaCalculo = "0";
				}
				if("1".equals(activaCalculo)){
					Logger.write("   + Inicia calculo   TotalDatos  para el DN "+ dn + "  +");
					final String responseWallets = focalizacion.focalizacionConsultaWallets(dn, compania);
					final double cantidadDataNat = ParseXMLFile.parseWallets_Cantidad_Datos_Nat(responseWallets);
					final double datosIncluidos = Double.parseDouble(navegacion.getDatosIncluidos());
					Logger.write("     cantidadDataNat             : " + cantidadDataNat);
					Logger.write("     datosIncluidos              : " + datosIncluidos);
					if(0 != datosIncluidos && 0 != cantidadDataNat){
						final double totalDatos = datosIncluidos - cantidadDataNat;					
						Logger.write("     totalDatos                  : " + totalDatos);
						final Formatter format = new Formatter();
						navegacion.setTotalDatos(format.roundTwoDecimals(totalDatos)+"");
					}
				}
			}catch (Exception e) {
				Logger.write("     Exception al calcular TotalDatos : " + e.getLocalizedMessage());
			}
			
			Logger.write("   + Respuesta              +");
			Logger.write("     DatosExtra             : " + navegacion.getDatosExtra());
			Logger.write("     DatosIncluidos         : " + navegacion.getDatosIncluidos());
			Logger.write("     NavegacionDeRegalo     : " + navegacion.getNavegacionDeRegalo());
			Logger.write("     TotalCostoDatos        : " + navegacion.getTotalCostoDatos());
			Logger.write("     TotalDatos             : " + navegacion.getTotalDatos());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				boolean mensajeDBControlado = Utilerias.contieneMensajeError(e.getLocalizedMessage());
				if(e.getLocalizedMessage().contains("vacio") || mensajeDBControlado){
					String msg = e.getLocalizedMessage();
					if(mensajeDBControlado)
						msg = Constantes.MSJ_BD_CONSUMOS;
					throw new ServiceException("[WARN] walletsTotalNavegacion ["+dn+"] :: " + msg);
				}else{
					throw new ServiceException("[ERR] walletsTotalNavegacion ["+dn+"] :: " + e.getLocalizedMessage());	
				}
			}
			else{
				throw new ServiceException("[ERR] walletsTotalNavegacion ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("walletsTotalNavegacion ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return navegacion;
	}
	
	public ObtenerDescripcionPlanesVO1 walletsDescripcionPlan(String user, String pass, String dn, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("walletsDescripcionPlan");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[WARN] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[WARN] password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		ObtenerDescripcionPlanesVO1 respuestaTotal = new ObtenerDescripcionPlanesVO1();
		StringBuffer sResponse = new StringBuffer();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();		
//		CallServiceObtenerDescripcionPlanes obtenPlanes = new CallServiceObtenerDescripcionPlanes();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("[WARN] DN no puede ir vacio");
			
			sResponse.append(focalizacion.focalizacion(dn));
			if(sResponse != null && !sResponse.equals("")){
				descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
				sResponse.setLength(0);
			}
			
			try{
//				if(descripcion.getIdPlan() != null && !descripcion.getIdPlan().equals("")){
//					sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes(descripcion.getIdPlan(), "", "0", 212121, "10.10.10"));
//				}else if(descripcion.getDescripcionPlan() != null && !descripcion.getDescripcionPlan().equals("")){
//					sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes("", descripcion.getDescripcionPlan(), "0", 212121, "10.10.10"));
//				}else if(descripcion.getCode() != null && !descripcion.getCode().equals("")){
//					sResponse.append(obtenPlanes.serviceObtenerDescripcionPlanes("", "", descripcion.getCode(), 212121, "10.10.10"));
//				}
//				if(sResponse != null && !sResponse.equals("")){
//					respuestaTotal = ParseXMLFile.ParseObtenerDescripcionPlanes(sResponse.toString());
//					sResponse.setLength(0);
//				}
				
				respuestaTotal = OfertaComercial.consultaDescripcionPlanes(descripcion.getIdPlan(), descripcion.getDescripcionPlan(), descripcion.getCode(), 212121, "10.10.10.10");
				
			}catch (ServiceException e) {
				respuestaTotal = new ObtenerDescripcionPlanesVO1();
			}

			Logger.write("   + Respuesta              +");
			if(respuestaTotal != null){
				Logger.write("     IdPlan                 : " + respuestaTotal.getIdPlan());
				Logger.write("     Megaadc                : " + respuestaTotal.getMegaadc());
				Logger.write("     Megas                  : " + respuestaTotal.getMegas());
				Logger.write("     Mensajeadc             : " + respuestaTotal.getMensajeadc());
				Logger.write("     Mensajes               : " + respuestaTotal.getMensajes());
				Logger.write("     Minutoadc              : " + respuestaTotal.getMinutoadc());
				Logger.write("     Minutos                : " + respuestaTotal.getMinutos());
				Logger.write("     Minutoscomunidad       : " + respuestaTotal.getMinutoscomunidad());
				Logger.write("     NombreCortoPlan        : " + respuestaTotal.getNombreCortoPlan());
				Logger.write("     TiempoAire             : " + respuestaTotal.getTiempoAire());
			}

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("vacio")){
					throw new ServiceException("[WARN] walletsDescripcionPlan ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] walletsDescripcionPlan ["+dn+"] :: " + e.getLocalizedMessage());	
				}
			}
			else{
				throw new ServiceException("[ERR] walletsDescripcionPlan ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("walletsDescripcionPlan ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuestaTotal;
	}
	
	/********************************/
	/** **/
	
	
	public DetalleTotalesVO walletsTotalPeriodo(String user, String pass, String dn, String fechaIni, String fechaFin, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("walletsTotalPeriodo");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleConsumosTotales consumos = new DetalleConsumosTotales();
		ConsumosTotalesVO response = new ConsumosTotalesVO();
		DetalleTotalesVO respuestaTotal = new DetalleTotalesVO();
		StringBuffer sResponse = new StringBuffer();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		OracleProcedures oracle = new OracleProcedures();
		String sumaAddOns = "";
		
		List<CatatoloGetVO> listaObtieneCatalogosNav = new ArrayList<CatatoloGetVO>();
		List<CatatoloGetVO> listaObtieneCatalogosMin = new ArrayList<CatatoloGetVO>();
		List<CatatoloGetVO> listaObtieneCatalogosMen = new ArrayList<CatatoloGetVO>();
		List<TipoServicioVO> listTipoServicio = new ArrayList<TipoServicioVO>();

		Llamadas llamadas = new Llamadas();
		Mensajes mensajes = new Mensajes();
		Navegacion navegacion = new Navegacion();
		MinutosTodoDestino minutosTodoDestino = new MinutosTodoDestino();
		MinutosComunidad minutosComunidad = new MinutosComunidad();

		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     FechaInicio            : " + fechaIni);
			Logger.write("     FechaFIn               : " + fechaFin);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("DN no puede ir vacio");

			try{
				sumaAddOns = oracle.getValorParametro(19);
			}catch (ServiceException e) {
				sumaAddOns = "0";
			}
			
			sResponse.append(focalizacion.focalizacion(dn));
			if(sResponse != null && !sResponse.equals("")){
				descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
			}
			
			response = consumos.flujoRango(dn, fechaIni, fechaFin, descripcion);

			DetalleWalletsVO descripcionWallets = new DetalleWalletsVO();
			if(sResponse != null && !sResponse.equals("")){
				descripcionWallets = ParseXMLFile.parseWallets(sResponse.toString());
				sResponse.setLength(0);
			}	

			response.setTotalMensaje(response.getDetalleConsumoMensajes().size()+"");

			double totalCostoLlamada = 0;
			double totalVoz = 0;
			try{
				totalCostoLlamada = Double.parseDouble(response.getDatosPlanes().getMinutoadc());
				totalVoz = Double.parseDouble(response.getTotalTA());
			}catch (Exception e) {
				totalCostoLlamada = 0;
				totalVoz = 0;
			}

			llamadas.setTotalLlamadas(response.getTotalTA());
			llamadas.setMinutosComunidadConsumidos(response.getTotalCom());

			llamadas.setTotalCostoLlamadas(formatNumber.format(totalCostoLlamada * totalVoz));
			if(response.getDatosPlanes() != null)
				llamadas.setLlamadasIncluidas(response.getDatosPlanes().getMinutos());

			mensajes.setTotalMensajes(response.getTotalMensaje());
			double totalmsjCosto = 0;
			double totalmsj = 0;
			double msjAdc = 0;
			try{
				totalmsj = Double.parseDouble(response.getTotalMensaje());
				if(response.getDatosPlanes() != null)
					msjAdc = Double.parseDouble(response.getDatosPlanes().getMegaadc());
				totalmsjCosto = totalmsj * msjAdc;
			}catch (Exception e) {
				totalmsjCosto = 0;
			}
			mensajes.setTotalCostoMensajes(formatNumber.format(totalmsjCosto));
			if(response.getDatosPlanes() != null){
				mensajes.setMensajesIncluidos(response.getDatosPlanes().getMensajes());
				//polo
				//respuestaTotal.setDescripcionPlanes(response.getDatosPlanes());
				respuestaTotal.setDescripcionPlanes(new ObtenerDescripcionPlanesVO());
				respuestaTotal.getDescripcionPlanes().setMegaadc(response.getDatosPlanes().getMegaadc());
				respuestaTotal.getDescripcionPlanes().setMegas(response.getDatosPlanes().getMegaadc());
				respuestaTotal.getDescripcionPlanes().setMensajeadc(response.getDatosPlanes().getMegas());
				respuestaTotal.getDescripcionPlanes().setMensajes(response.getDatosPlanes().getMensajes());
				respuestaTotal.getDescripcionPlanes().setMinutoadc(response.getDatosPlanes().getMinutoadc());
				respuestaTotal.getDescripcionPlanes().setMinutos(response.getDatosPlanes().getMinutos());
				respuestaTotal.getDescripcionPlanes().setMinutoscomunidad(response.getDatosPlanes().getMinutoscomunidad());
				respuestaTotal.getDescripcionPlanes().setNombreCortoPlan(response.getDatosPlanes().getNombreCortoPlan());
				respuestaTotal.getDescripcionPlanes().setTiempoAire(response.getDatosPlanes().getTiempoAire());
			}

			navegacion.setTotalDatos(response.getTotalDatos());
			navegacion.setTotalCostoDatos(response.getTotalDatosCosto());

			int minutosC = 0;
			int minutosTD = 0;
			if(response.getDetalleServiciosAdicionales() != null && !response.getDetalleServiciosAdicionales().equals("")){
				int megas = 0;	
				int megasExtra = 0;
				int mensajesTotal=0;
				int mensajesExtras = 0;
				int minutos = 0; 
				int llamadasExtras = 0;

				if(response != null && response.getDatosPlanes() != null) {
					
					if(response.getDatosPlanes().getMegas() != null && !response.getDatosPlanes().getMegas().equals(""))
					{
						try{
							megas = Integer.parseInt(response.getDatosPlanes().getMegas().trim());
						}catch (Exception e) {
							megas = 0;
						}
					}
					if(response.getDatosPlanes().getMinutos() != null && !response.getDatosPlanes().getMinutos().equals(""))
					{
						try{
							minutos = Integer.parseInt(response.getDatosPlanes().getMinutos().trim());
						}catch (Exception e) {
							megas = 0;
						}
					}
					if(response.getDatosPlanes().getMensajes() != null && !response.getDatosPlanes().getMensajes().equals(""))
					{
						try{
							mensajesTotal = Integer.parseInt(response.getDatosPlanes().getMensajes().trim());
						}catch (Exception e) {
							mensajesTotal = 0;
						}
					}

					try{
						listaObtieneCatalogosNav = oracle.catalogoGet(9);
						listaObtieneCatalogosMin = oracle.catalogoGet(10);
						listaObtieneCatalogosMen = oracle.catalogoGet(11);
						listTipoServicio = oracle.getTipoServicio(2); //Navegacion
					}catch (Exception e) {
						Logger.write("     Detail al consultar el catalogo");
					}
					for(int a = 0; a < response.getDetalleServiciosAdicionales().size(); a++){
						String IdServicio = response.getDetalleServiciosAdicionales().listIterator(a).next().getIdServicio();
						int status = response.getDetalleServiciosAdicionales().listIterator(a).next().getStatus();

						if(sumaAddOns.equals("1")){
							
							for(int c=0; c <listaObtieneCatalogosNav.size();c++){
								if( IdServicio.equals(listaObtieneCatalogosNav.get(c).getTipoRespuestaID()+"") && status == 1){
									//megas = megas + Integer.parseInt(listaObtieneCatalogosNav.get(c).getTipoRepuesta());	
									megasExtra = megasExtra + Integer.parseInt(listaObtieneCatalogosNav.get(c).getTipoRepuesta());
								}
							}
							
							for(int c=0; c <listaObtieneCatalogosMin.size();c++){
								if( IdServicio.equals(listaObtieneCatalogosMin.get(c).getTipoRespuestaID()+"") && status == 1){
									//minutos = minutos + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());	
									llamadasExtras = llamadasExtras + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
									
									for(int v=0; v < listTipoServicio.size(); v++){
										if(listTipoServicio.get(v).getServicio().equals(IdServicio)){  
											if(listTipoServicio.get(v).getDescripcion().toLowerCase().contains("comunidad")== true){
												minutosC = minutosC + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());	
											}else{//Todo Destino
												minutosTD = minutosTD + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
											}
										}
									}
									//742-743 Comunidad
//									if (IdServicio.equals("742") || IdServicio.equals("743") ) {
//										minutosC = minutosC + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
//									}
//									//775-776-777-778-779	
//									if (IdServicio.equals("775") || IdServicio.equals("776") || IdServicio.equals("777") || IdServicio.equals("778") || IdServicio.equals("779") ) {
//										minutosTD = minutosTD + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
//									}
									
								}
							}
							
							for(int c=0; c <listaObtieneCatalogosMen.size();c++){
								if( IdServicio.equals(listaObtieneCatalogosMen.get(c).getTipoRespuestaID()+"") && status == 1){
//									mensajesTotal = mensajesTotal +  Integer.parseInt(listaObtieneCatalogosMen.get(c).getTipoRepuesta());	
									mensajesExtras = mensajesExtras + Integer.parseInt(listaObtieneCatalogosMen.get(c).getTipoRepuesta());
								}
							}
						}else{
							if(IdServicio.equals("797") && status == 1){
								//megas = megas + 5;	
								megasExtra = megasExtra + 5; 
							}else if(IdServicio.equals("798") && status == 1) {
								//megas = megas + 10;       
								megasExtra = megasExtra + 10;
							}else if(IdServicio.equals("799") && status == 1) {
								//megas = megas + 50;
								megasExtra = megasExtra + 50;
							}else if(IdServicio.equals("800") && status == 1) {
								//megas = megas + 100;
								megasExtra = megasExtra + 100;
							}else if(IdServicio.equals("801") && status == 1) {
								//megas = megas + 500;
								megasExtra = megasExtra + 500;
							}else if(IdServicio.equals("729") && status == 1) {
								//megas = megas + (1 * 1024);
								megasExtra = megasExtra + (1 * 1024);
							}else if(IdServicio.equals("802") && status == 1) {
								//megas = megas + (2 * 1024);
								megasExtra = megasExtra + (2 * 1024);
							}else if(IdServicio.equals("803") && status == 1) {
								//megas = megas + (3 * 1024);
								megasExtra = megasExtra + (3 * 1024);
							}else if(IdServicio.equals("804") && status == 1) {
								//megas = megas + (5 * 1024);
								megasExtra = megasExtra + (5 * 1024);
							}else if(IdServicio.equals("805") && status == 1) {
								//megas = megas + (10 * 1024);
								megasExtra = megasExtra + (10 * 1024);
							}else if(IdServicio.equals("465") && status == 1) {
								//megas = megas + 50;
								megasExtra = megasExtra + 50;
							}else if(IdServicio.equals("539") && status == 1) {
								//megas = megas + 270;    	
								megasExtra = megasExtra + 270;	
							}
						}
					}
				}

				llamadas.setMinutosComunidadConsumidos(response.getTotalCom());
				llamadas.setTotalLlamadas(response.getTotalTA());
				mensajes.setTotalMensajes(response.getTotalMensaje());
				
				navegacion.setDatosIncluidos(megas+"");
				navegacion.setDatosExtra(megasExtra+"");

				llamadas.setLlamadasIncluidas(minutos+"");
				llamadas.setMinutosExtras(llamadasExtras+"");

				mensajes.setMensajesIncluidos(mensajesTotal+"");
				mensajes.setMensajesExtras(mensajesExtras+"");

				minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
				minutosTodoDestino.setMinutosTDextra(String.valueOf(minutosTD));
				minutosTodoDestino.setMinutosTDregalo("0");
				minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());

				minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
				minutosComunidad.setMinutosComunidadExtra(String.valueOf(minutosC));
				minutosComunidad.setMinutosComunidadRegalo("0");
				minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());

			}else{
				llamadas.setMinutosComunidadConsumidos(response.getTotalCom());
				llamadas.setTotalLlamadas(response.getTotalTA());
				mensajes.setTotalMensajes(response.getTotalMensaje());
				
				if(response.getDatosPlanes() != null){
					minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
					minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());
					minutosComunidad.setMinutosComunidadRegalo("0");
					
					minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
					minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());
					minutosTodoDestino.setMinutosTDregalo("0");
					
					navegacion.setDatosIncluidos(response.getDatosPlanes().getMegas());
				    mensajes.setMensajesIncluidos(response.getDatosPlanes().getMensajes());
			 	    llamadas.setLlamadasIncluidas(response.getDatosPlanes().getMinutos());
			 	    
			 	    minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
					minutosTodoDestino.setMinutosTDextra(String.valueOf(minutosTD));
					minutosTodoDestino.setMinutosTDregalo("0");
					minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());
					
					minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
					minutosComunidad.setMinutosComunidadExtra(String.valueOf(minutosC));
	                minutosComunidad.setMinutosComunidadRegalo("0");
	                minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());
				}
			} 


			if(descripcion.getTiempoAireComprado() != null && descripcion.getTiempoAireComprado().equals(""))
				respuestaTotal.setTiempoAireAbonado(descripcion.getTiempoAireComprado());
			else if(descripcion.getSaldoPrepago() != null && !descripcion.getSaldoPrepago().equals(""))
				respuestaTotal.setTiempoAireAbonado(descripcion.getSaldoPrepago());
			else
				respuestaTotal.setTiempoAireAbonado("");

			if(descripcionWallets != null){
				//polo
				//llamadas.setMinutosDeRegalo(descripcionWallets.getMinutosDeRegalo());
				//mensajes.setMensajesDeTextoDeRegalo(descripcionWallets.getMensajesDeTexto());
				//navegacion.setNavegacionDeRegalo(descripcionWallets.getNavegacion());
				
				llamadas.setMinutosDeRegalo("0");
				mensajes.setMensajesDeTextoDeRegalo("0");
				navegacion.setNavegacionDeRegalo("0");
				
				respuestaTotal.setTiempoAireDeRegalo(descripcionWallets.getTiempoAireDeRegalo());
				respuestaTotal.setTiempoAireTotal(descripcionWallets.getTiempoAireTotal());
			}
			
			//polo
//			if(descripcion != null){
//				respuestaTotal.setVigenciaSalgoPrepago(descripcion.getVigenciaSalgoPrepago());
//			}
			respuestaTotal.setLlamadas(llamadas);
			respuestaTotal.setMensajes(mensajes);
			respuestaTotal.setNavegacion(navegacion);
			respuestaTotal.setMinutosComunidad(minutosComunidad);
			respuestaTotal.setMinutosTodoDestino(minutosTodoDestino);
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("   + Respuesta  +");
			Logger.write("     TotalDatos             : " + response.getTotalDatos());
			Logger.write("     TotalMensaje           : " + response.getTotalMensaje());
			Logger.write("     TotalTA                : " + response.getTotalTA());
			Logger.write("     TotalVoz               : " + response.getTotalVoz());
			Logger.write("     Detalle Llamadas.size  : " + response.getDetalleConsumoLlamadas().size());
			Logger.write("     Detalle Mensajes.size  : " + response.getDetalleConsumoMensajes().size());
			Logger.write("     Detalle Nav.size       : " + response.getDetalleConsumoNavegacion().size());
			Logger.write("     DescripcionPlan        : " + response.getDescripcionPlan());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				boolean mensajeDBControlado = Utilerias.contieneMensajeError(e.getLocalizedMessage());
				if(e.getLocalizedMessage().contains("vacio") || mensajeDBControlado){
					String msg = e.getLocalizedMessage();
					if(mensajeDBControlado)
						msg = Constantes.MSJ_BD_CONSUMOS;
					throw new ServiceException("[WARN] walletsTotalPeriodo ["+dn+"] :: " + msg);
				}else{
					throw new ServiceException("[ERR] walletsTotalPeriodo ["+dn+"] :: " + e.getLocalizedMessage());	
				}
			}
			else{
				throw new ServiceException("[ERR] walletsTotalPeriodo ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("walletsTotalPeriodo ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuestaTotal;
	}
	
	public SaldosVO detalleSaldos(String user, String pass, String dn, String tipoEvento, String token) throws Throwable{

		
		long initime = System.currentTimeMillis();
        Logger.init("detalleSaldos");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleSaldos consumos = new DetalleSaldos();
		SaldosVO respuesta = new SaldosVO(); 
		
		try{

			Logger.write("      user                        : -PROTEGIDO-");
			Logger.write("      pass                        : -PROTEGIDO-");
			Logger.write("      dn                          : " + dn);
			Logger.write("      tipoEvento                  : " + tipoEvento);
			Logger.write("      token                       : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("      remoteAddress               : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = consumos.flujo(dn, tipoEvento);

			Logger.write("     + Respuesta  +");
			Logger.write("       TotalDatos         : " + respuesta.getTotalDatos());
			Logger.write("       TotalMensaje       : " + respuesta.getTotalMensaje());
			Logger.write("       TotalTA            : " + respuesta.getTotalTA());
			Logger.write("       TotalVoz           : " + respuesta.getTotalVoz());
			if(respuesta.getDetalleFocalizacion()!=null){
				Logger.write("      DescripcionPlan             : " + respuesta.getDetalleFocalizacion().getDescripcionPlan());
				Logger.write("      IdOperador                  : " + respuesta.getDetalleFocalizacion().getIdOperador());
				Logger.write("      IsPostpagoOrHibrido         : " + respuesta.getDetalleFocalizacion().getIsPostpagoOrHibrido());
				Logger.write("      Servicio                    : " + respuesta.getDetalleFocalizacion().getServicio());
				Logger.write("      Subservicio                 : " + respuesta.getDetalleFocalizacion().getSubservicio());
				Logger.write("      Tecnologia                  : " + respuesta.getDetalleFocalizacion().getTecnologia());
				Logger.write("      tiempoAireComprado          : " + respuesta.getDetalleFocalizacion().getTiempoAireComprado());
				Logger.write("      fechaCorte                  : " + respuesta.getDetalleFocalizacion().getFechaCorte());
				if(respuesta.getDetalleConsumo() != null)
					Logger.write("      DetalleConsumos.size        : " + respuesta.getDetalleConsumo().size());
			}

		}catch(Exception e){
			throw new ServiceException("[ERR] detalleSaldos ["+dn+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("detalleSaldos ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public List<ServiciosContratarVO> consultaServicios(String user, String pass, String sNumeroDN, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("consultaServicios");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("password no valido");
		}

		List<ServiciosContratarVO> respuesta = new ArrayList<ServiciosContratarVO>();
		ValidaTokens tokens =  new ValidaTokens();

		try{
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     sNumDN                 : " + sNumeroDN);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(sNumeroDN, token, new MensajeLogBean());

			respuesta = ConsultaServicioXUsuExt.flujo(sNumeroDN);
			
			Logger.write("   + Respuesta              + ");
			if(respuesta != null)
				Logger.write("     respuesta              : " + respuesta.size());

		}catch(Exception e){
			throw new ServiceException("[ERR] consultaServicios ["+sNumeroDN+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("consultaServicios ["+sNumeroDN+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public ContratarServiciosVO contratarServicios(String user, String pass, String dn, AltaServicioEtakVO datosAltaEtak, AltaServicioLegacyVO datosAlta, AltaServicioPrepagoLegacyVO datosAltaPrepago, String token) throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("contratarServicios");

		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("       El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}
		ValidaTokens tokens =  new ValidaTokens();
		ContratarServiciosVO RespContratarServicio = new ContratarServiciosVO();
		OracleProcedures oracle = new OracleProcedures();
		String respuesta = "";
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		String sResponse = "";

		try{

			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     DN                     : " + dn);
			Logger.write("     DatosAltaETAK          : " );
			if(datosAltaEtak != null){
				Logger.write("     Costo                  : " + datosAltaEtak.getCosto());
				Logger.write("     Id                     : " + datosAltaEtak.getId());
				Logger.write("     IdHistorico            : " + datosAltaEtak.getIdHistorico());
				Logger.write("     Monto                  : " + datosAltaEtak.getMonto());
				Logger.write("     Vigencia               : " + datosAltaEtak.getVigencia());
			}
			Logger.write("     DatosAltaLEGACY        : " );
			if(datosAlta != null){
				Logger.write("     ContinenteFavortio     : " + datosAlta.getContinenteFavortio());
				Logger.write("     DnUsa                  : " + datosAlta.getDnUsa());
				Logger.write("     Servicios              : " + datosAlta.getServicios());
			}else{
				datosAlta = new AltaServicioLegacyVO();
			}
			if(datosAltaPrepago != null){
				Logger.write("     Operacion              : " + datosAltaPrepago.getOperacion());
				Logger.write("     ServicioOrigen         : " + datosAltaPrepago.getServicioOrigen());
				Logger.write("     ServiciosId            : " + datosAltaPrepago.getServiciosId());
				Logger.write("     VigenciasCantidad      : " + datosAltaPrepago.getVigenciasCantidad());
				Logger.write("     VigenciasUnidad        : " + datosAltaPrepago.getVigenciasUnidad());
			}
			Logger.write("      token                       : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("      remoteAddress               : " + getClientIpXfire());
			
			tokens.validaToken(dn, token, new MensajeLogBean());

			try{

				sResponse = focalizacion.focalizacion(dn);
				if(sResponse != null && !sResponse.equals(""))
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);

				/*  TIPO TECNOLOGIA:
				 *   1 LEGACY
				 *   2 ETAK
				 */  

				int tipoTecnologia = 0;

				if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
					tipoTecnologia = 2;  //Etak
				}else{
					tipoTecnologia = 1;  //Legacy
				}

				/*TIPO LINEA:
				 * 1	PREPAGO	
				 * 2	POSTPAGO	
				 * 3	HIBRIDO
				 */

				int tipoLinea = 0;

				if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
					if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
						tipoLinea = 2;
					}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
						tipoLinea = 3;
					}
				}else {
					tipoLinea = 1; //prepago
				}

				respuesta =	oracle.contratarServiciosSET(user, pass, dn, datosAltaEtak.getCosto(), datosAltaEtak.getId(), datosAltaEtak.getIdHistorico(), datosAltaEtak.getVigencia(), datosAltaEtak.getMonto(), datosAlta.getDnUsa(), datosAlta.getContinenteFavortio(), datosAlta.getServicios(), datosAltaPrepago.getServiciosId(), datosAltaPrepago.getServicioOrigen(), datosAltaPrepago.getVigenciasUnidad(), datosAltaPrepago.getVigenciasCantidad(), datosAltaPrepago.getOperacion(), 7, 1, token, 2021,tipoLinea, tipoTecnologia, 2);
			}catch (Exception e) {
				Logger.write("Detail en el metodo de respuesta contratarServicios");
			}
				 
			RespContratarServicio = AltaServicioXUsuExt.flujo(dn, datosAltaEtak, datosAlta, datosAltaPrepago,respuesta);

			if(RespContratarServicio != null){
				try {
					oracle.compraServicioRespuesta(respuesta, 4, RespContratarServicio.getMessageCode(),RespContratarServicio.getOperationCode() , 2121,"","");							    
				}catch (Exception e) {
                    Logger.write("Detail a ejecutar el metodo");
				}
		    }
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     MessageCode            : " + RespContratarServicio.getMessageCode());
			Logger.write("     OperationCode          : " + RespContratarServicio.getOperationCode());

		}catch(Exception e){
			throw new ServiceException("[ERR] contratarServicios ["+dn+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("contratarServicios ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return RespContratarServicio;
	}
	
	public ContratarServiciosVO bajaServicios(String user, String pass, String mdn, String idServicio, AltaServicioLegacyVO datosAlta, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("bajaServicios");

		
	    if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
	      throw new ServiceException("usuario no valido");
	    }
	    if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
	      throw new ServiceException("password no valido");
	    }
	    ValidaTokens tokens =  new ValidaTokens();
	    ContratarServiciosVO response = new ContratarServiciosVO();
	    
	    
	    try{
	    	
	    	Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     mdn                    : " + mdn);
			Logger.write("     idServicio             : " + idServicio);
			
			if(datosAlta != null){
				Logger.write("     ContinenteFavortio     : " + datosAlta.getContinenteFavortio());
				Logger.write("     DnUsa                  : " + datosAlta.getDnUsa());
				Logger.write("     Servicios              : " + datosAlta.getServicios());
			}else{
				datosAlta = new AltaServicioLegacyVO();
			}
			Logger.write("      token                       : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("      remoteAddress               : " + getClientIpXfire());
			
			tokens.validaToken(mdn, token, new MensajeLogBean());
			
			response = BajaServicio.flujo(mdn, idServicio, datosAlta);
			
			Logger.write("   + Respuesta              + ");
			if(response != null){
				Logger.write("     MessageCode            : " + response.getMessageCode());
				Logger.write("     OperationCode          : " + response.getOperationCode());
			}
	    	
	    }catch(Exception e)
	    {
			throw new ServiceException("[ERR] bajaServicios ["+mdn+"] :: " + e.getLocalizedMessage());
	    }finally
	    {
	    	Logger.end("bajaServicios ["+mdn+"] :: " + getLocalAddress(), initime);
	    }
	    
 	  return response;
  }
	
	public List<DatosServiciosAContratarVO> serviciosAContratar(String user, String pass, String dn, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("serviciosAContratar");
		
	    if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
	      throw new ServiceException("usuario no valido");
	    }
	    if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
	      throw new ServiceException("password no valido");
	    }
	    ValidaTokens tokens =  new ValidaTokens();
	    List<DatosServiciosAContratarVO> response = new ArrayList<DatosServiciosAContratarVO>();
	    
	    try{
	    	
	    	Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     coId                   : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token, new MensajeLogBean());
			
			response = ObtieneServiciosAContratar.flujo(dn);
			
			Logger.write("   + Respuesta              + ");
			if(response != null){
				Logger.write("     response.size          : " + response.size());
			}
	    	
	    }catch(Exception e)
	    {
			throw new ServiceException("[ERR] serviciosAContratar ["+dn+"] :: " + e.getLocalizedMessage());
	    }finally
	    {
	    	Logger.end("serviciosAContratar ["+dn+"] :: " + getLocalAddress(), initime);
	    }
		return response;
	}
	
	public String registroCliente(String user, String pass, ClienteVO datosCliente, int usuarioId, String token) throws Throwable{	    

		long initime = System.currentTimeMillis();
        Logger.init("registroCliente",datosCliente.getUnidadNegocioID(), false);

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("usuario no válido ");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("password no válido ");
		}

		String respuesta = "";
		ValidaTokens tokens =  new ValidaTokens();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     datosCliente           : ");
			Logger.write("     preguntaSecreta        : " + datosCliente.getPreguntaSecreta());
			Logger.write("     respuestaPreguntaSecr  : " + datosCliente.getRespPreguntaSecreta());
			Logger.write("     contrasena             : " + datosCliente.getContrasena());
			Logger.write("     correo                 : " + datosCliente.getCorreo());
			Logger.write("     dn                     : " + datosCliente.getDn());
			Logger.write("     tipoCliente            : " + datosCliente.getTipoCliente());
			Logger.write("     nombre                 : " + datosCliente.getNombre());
			Logger.write("     aPaterno               : " + datosCliente.getaPaterno());
			Logger.write("     aMaterno               : " + datosCliente.getaMaterno());
			Logger.write("     edad                   : " + datosCliente.getEdad());
			Logger.write("     sexo                   : " + datosCliente.getSexo());
			Logger.write("     nacimiento             : " + datosCliente.getNacimiento());
			Logger.write("     paisID                 : " + datosCliente.getPaisID());
			Logger.write("     unidadNegocioID        : " + datosCliente.getUnidadNegocioID());
			Logger.write("     fotografia             : " + Formatter.pintaLogCadenasLargas(datosCliente.getFotografia()));
			Logger.write("     usuarioId              : " + usuarioId);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(datosCliente.getDn(), token, new MensajeLogBean());

			respuesta = AltaUsuario.registraCliente(datosCliente, usuarioId);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("Parametros de entrada")){
					throw new ServiceException("[WARN] registroCliente ["+datosCliente.getDn()+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] registroCliente ["+datosCliente.getDn()+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] registroCliente ["+datosCliente.getDn()+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("registroCliente ["+datosCliente.getDn()+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public String registroClienteBit(String user, String pass, ClienteRegistroVO datosCliente, int usuarioId, String token) throws Throwable{	    

		long initime = System.currentTimeMillis();
        Logger.init("registroClienteBit", datosCliente.getUnidadNegocioID(), false);

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("usuario no válido ");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("password no válido ");
		}

		String respuesta = "";
		ValidaTokens tokens =  new ValidaTokens();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     datosCliente           : ");
			Logger.write("     preguntaSecreta        : " + datosCliente.getPreguntaSecreta());
			Logger.write("     respuestaPreguntaSecr  : " + datosCliente.getRespPreguntaSecreta());
			Logger.write("     contrasena             : " + datosCliente.getContrasena());
			Logger.write("     correo                 : " + datosCliente.getCorreo());
			Logger.write("     dn                     : " + datosCliente.getDn());
			Logger.write("     tipoCliente            : " + datosCliente.getTipoCliente());
			Logger.write("     nombre                 : " + datosCliente.getNombre());
			Logger.write("     aPaterno               : " + datosCliente.getaPaterno());
			Logger.write("     aMaterno               : " + datosCliente.getaMaterno());
			Logger.write("     edad                   : " + datosCliente.getEdad());
			Logger.write("     sexo                   : " + datosCliente.getSexo());
			Logger.write("     nacimiento             : " + datosCliente.getNacimiento());
			Logger.write("     paisID                 : " + datosCliente.getPaisID());
			Logger.write("     unidadNegocioID        : " + datosCliente.getUnidadNegocioID());
			Logger.write("     fotografia             : " + Formatter.pintaLogCadenasLargas(datosCliente.getFotografia()));
			Logger.write("     SistemaOrigen          : " + datosCliente.getSistemaOrigen());
			Logger.write("     Dispositivo            : " + datosCliente.getDispositivo());
			Logger.write("     usuarioId              : " + usuarioId);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(datosCliente.getDn(), token, new MensajeLogBean());

			respuesta = AltaUsuario.registraClienteBit(datosCliente, usuarioId);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("Parametros de entrada")){
					throw new ServiceException("[WARN] registroClienteBit ["+datosCliente.getDn()+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] registroClienteBit ["+datosCliente.getDn()+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] registroClienteBit ["+datosCliente.getDn()+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("registroClienteBit ["+datosCliente.getDn()+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public String registroTarjetas(String user, String pass, TarjetaVO tarjeta, int domicilioID, int usuarioId, String token) throws Throwable{	    
		
		long initime = System.currentTimeMillis();
        Logger.init("registroTarjetas");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es válido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}

		String respuesta = "";
		ValidaTokens tokens =  new ValidaTokens();	 

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     tarjeta                : ");
			Logger.write("     marcaTarjeta           : " + tarjeta.getMarcaTarjeta());
			Logger.write("     numeroTarjeta          : " + tarjeta.getNumeroTarjeta());
			Logger.write("     mesVencimiento         : " + tarjeta.getMesVencimiento());
			Logger.write("     anioVencimiento        : " + tarjeta.getAnioVencimiento());
			Logger.write("     nombre                 : " + tarjeta.getNombre());
			Logger.write("     aPaterno               : " + tarjeta.getaPaterno());
			Logger.write("     aMaterno               : " + tarjeta.getaMaterno());
			Logger.write("     cp                     : " + tarjeta.getCp());
			Logger.write("     ultimosDigitos         : " + tarjeta.getUltimosDigitos());
			Logger.write("     dn                     : " + tarjeta.getDn());
			Logger.write("     domicilioID            : " + domicilioID);			
			Logger.write("     usuarioId              : " + usuarioId);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(tarjeta.getDn(), token, new MensajeLogBean());
		
			respuesta = AltaTarjetaFrecuente.regitraTarjetaDomicilio(tarjeta, domicilioID, usuarioId);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("Parametros de entrada")
						||  e.getLocalizedMessage().contains("Haz registrado") || e.getLocalizedMessage().contains("cardBlackList") 
						|| e.getLocalizedMessage().contains("SemaphoreSaveCustomerInfo")){
					throw new ServiceException("[WARN] registroTarjetas ["+tarjeta.getDn()+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] registroTarjetas ["+tarjeta.getDn()+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] registroTarjetas ["+tarjeta.getDn()+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("registroTarjetas ["+tarjeta.getDn()+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public String registroNumFrecuentes(String user, String pass,String dn, String nombre, String telefono, int usuarioId, String token) throws Throwable{	    

		long initime = System.currentTimeMillis();
        Logger.init("registroNumFrecuentes");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("       El usuario no es válido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}

		String respuesta = "";
		ValidaTokens tokens =  new ValidaTokens();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     nombre                 : " + nombre);
			Logger.write("     telefono               : " + telefono);	
			Logger.write("     usuarioId              : " + usuarioId);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = AltaNumeroFrecuente.registraNumeroFrecuente(dn, nombre, telefono, usuarioId);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("Parametros de entrada")){
					throw new ServiceException("[WARN] registroNumFrecuentes ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] registroNumFrecuentes ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] registroNumFrecuentes ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("registroNumFrecuentes ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}	
	
	public int editarPerfil(String user, String pass, String dn, String nombre, String apPaterno, String apMaterno, String correo, String password, String fechaNacimiento, String fotografia, int usuarioId, String token) throws Throwable{	    

		long initime = System.currentTimeMillis();
        Logger.init("editarPerfil");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}

		int respuesta = -1;
		ValidaTokens tokens =  new ValidaTokens();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     nombre                 : " + nombre);
			Logger.write("     apPaterno              : " + apPaterno);
			Logger.write("     apMaterno              : " + apMaterno);
			Logger.write("     correo                 : " + correo);
			Logger.write("     password               : " + password);
			Logger.write("     fechaNacimiento        : " + fechaNacimiento);
			Logger.write("     fotografia             : " + Formatter.pintaLogCadenasLargas(fotografia));
			Logger.write("     usuarioId              : " + usuarioId);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = EditarPerfil.flujo(dn, nombre, apPaterno, apMaterno, correo, password, fechaNacimiento, fotografia, usuarioId);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("no puede ir vacio")){
					throw new ServiceException("[WARN] editarPerfil ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] editarPerfil ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] editarPerfil ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("editarPerfil ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}	
	
	public List<NumerosFrecuentesVO> obtieneNumeros(String user, String pass, String dn, String token) throws Throwable{	    

		long initime = System.currentTimeMillis();
        Logger.init("obtieneNumeros");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}

		List<NumerosFrecuentesVO> respuesta = new ArrayList<NumerosFrecuentesVO>();
		ValidaTokens tokens =  new ValidaTokens();	

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = ObtieneFrecuentes.obtieneNumeroFrecuente(dn);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta.size         : " + respuesta.size());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] obtieneNumeros ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] obtieneNumeros ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] obtieneNumeros ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("obtieneNumeros ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public List<TarjetasFrecuentesVO> obtieneTarjetas(String user, String pass, String dn, String token) throws Throwable{	    

		long initime = System.currentTimeMillis();
        Logger.init("obtieneTarjetas");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}

		List<TarjetasFrecuentesVO> respuesta = new ArrayList<TarjetasFrecuentesVO>();
		ValidaTokens tokens =  new ValidaTokens();	

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = ObtieneFrecuentes.obtieneTarjetasFrecuenteDomicilio(dn);					
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] obtieneTarjetas ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] obtieneTarjetas ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] obtieneTarjetas ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("obtieneTarjetas ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public int editarCuenta(String user, String pass, String dn, String password, String passwordAnt, String preguntaSec, String respuestaSec, int usuarioId, String token) throws Throwable{	    
		long initime = System.currentTimeMillis();
        Logger.init("editarCuenta");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es válido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}

		int respuesta = -1;
		ValidaTokens tokens =  new ValidaTokens();	

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     password               : " + password);
			Logger.write("     passwordAnt            : " + passwordAnt);
			Logger.write("     preguntaSec            : " + preguntaSec);
			Logger.write("     respuestaSec           : " + respuestaSec);
			Logger.write("     usuarioId              : " + usuarioId);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = EditarCuenta.flujo(dn, password, passwordAnt, preguntaSec, respuestaSec, usuarioId);			
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("no puede ir vacio")){
					throw new ServiceException("[WARN] editarCuenta ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] editarCuenta ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] editarCuenta ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("editarCuenta ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public int bajaNumeroFrencuente(String user, String pass, String dn, String telefono, int usuarioId, String token) throws Throwable{	    

		long initime = System.currentTimeMillis();
        Logger.init("bajaNumeroFrencuente");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}

		int respuesta = -1;
		ValidaTokens tokens =  new ValidaTokens();	  

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     telefono               : " + telefono);
			Logger.write("     usuarioId              : " + usuarioId);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = BajaNumeroFrecuente.flujo(dn, telefono, usuarioId);			
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] bajaNumeroFrencuente ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] bajaNumeroFrencuente ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] bajaNumeroFrencuente ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("bajaNumeroFrencuente ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public int bajaTarjeta(String user, String pass, String dn, int marcaTarjetaId, String numtarjeta, int usuarioID, String token) throws Throwable{	    

		long initime = System.currentTimeMillis();
        Logger.init("bajaTarjeta");


		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}

		int respuesta = -1;
		ValidaTokens tokens =  new ValidaTokens();	    
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     marcaTarjetaId         : " + marcaTarjetaId);
			Logger.write("     numtarjeta             : " + numtarjeta);
			Logger.write("     usuarioID              : " + usuarioID);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());

			respuesta = BajaTarjetaFrecuente.flujo(marcaTarjetaId, dn, numtarjeta, usuarioID);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")||e.getLocalizedMessage().contains("SemaphoreDeleteBankCardInfo")){
					throw new ServiceException("[WARN] bajaTarjeta ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] bajaTarjeta ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] bajaTarjeta ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("bajaTarjeta ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public String getValorParametro(String user, String pass,int parametro, String token) throws Throwable
	{
		long initime = System.currentTimeMillis();
        Logger.init("getValorParametro");

		if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido");
			throw new ServiceException("[ALE] password no válido");
		}	
		String valorParametro = "";		
		ValidaTokens tokens =  new ValidaTokens();	
		OracleProcedures oracleProcedures = new OracleProcedures();
		
		try {
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     parametro              : " + parametro);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(parametro+"", token, new MensajeLogBean());	
			
			valorParametro = oracleProcedures.getValorParametro(parametro);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     valorParametro         : " + valorParametro);
		}
		catch (Exception e) {
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] getValorParametro ["+valorParametro+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] getValorParametro ["+valorParametro+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] getValorParametro ["+valorParametro+"] :: " + e.getLocalizedMessage());
			}			
		} finally {
			Logger.end("getValorParametro ["+valorParametro+"] :: " + getLocalAddress(), initime);
		}

		return valorParametro;
	}
	
	public List<CatalogoVO> obtenerClavesSensales(String user, String pass, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("obtenerClavesSensales");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		List<CatalogoVO> respuesta = new ArrayList<CatalogoVO>();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken("", token, new MensajeLogBean());
			
			respuesta = ObtieneClavesSensales.flujo();
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta.size         : " + respuesta.size());

		}catch(Exception e){
			throw new ServiceException("[ERR] obtenerClavesSensales :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("obtenerClavesSensales :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public AltaCitaVO altaCita(String user, String pass, int cveHora, String fecha, String tienda, String suscriptor, String nombre, String apPaterno, String apMaterno, String correo, String comentario, int tipoAtencion, int cveGenerdor, String token) throws Throwable {

		long initime = System.currentTimeMillis();
        Logger.init("altaCita");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		AltaCitaVO altacitaVo = new AltaCitaVO();
		OracleProcedures oracle = new OracleProcedures();
		String logId = "";
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     cveHora                : "+ cveHora);
			Logger.write("     fecha                  : "+ fecha);
			Logger.write("     tienda                 : "+ tienda);
			Logger.write("     suscriptor             : "+suscriptor);
			Logger.write("     nombre                 : "+nombre);
			Logger.write("     apPaterno              : "+apPaterno);
			Logger.write("     apMaterno              : "+apMaterno);
			Logger.write("     correo                 : "+correo);
			Logger.write("     comentario             : "+comentario);
			Logger.write("     tipoAtencio            : "+tipoAtencion);
			Logger.write("     cveGenerdor            : "+cveGenerdor);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken("", token, new MensajeLogBean());
			
			try{
				logId = oracle.altaCita(user,pass, cveHora,fecha,tienda, suscriptor,nombre,apPaterno,apMaterno, correo, comentario, tipoAtencion,cveGenerdor, 1, 1,token,0,1, 1, 2);
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
			int empresa = 0;
			try{
				
				empresa = oracle.obtieneEmpresa(suscriptor);
			}catch (Exception e) {
				empresa=1;
			}
			altacitaVo = AltaCita.flujo("O7k13t9t9TbMTd3[mJN]uj39", "O7k13t9t9TbMTd3[mJN]uj39", cveHora, fecha, tienda, suscriptor, nombre, apPaterno, apMaterno, correo, comentario, tipoAtencion, cveGenerdor, empresa, logId);

			Logger.write("   + Respuesta              + ");	
			Logger.write("     exito                  : " +altacitaVo.getExito());
			Logger.write("     mensaje                : " +altacitaVo.getMensaje());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("ORA-20008")){
					throw new ServiceException("[WARN] altaCita :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] altaCita :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] altaCita :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("altaCita :: " + getLocalAddress(), initime);
		}
		return altacitaVo;
	}
	
	public AltaCitaVO altaCitas (String user, String pass, int cveHora, String fecha, String tienda, String suscriptor, String nombre, String apPaterno, String apMaterno, String correo, String comentario, int tipoAtencion, int cveGenerdor, int compania, int sistemaOrigen, int dispositivo, int empresa, String token)  throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("altaCitas");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		AltaCitaVO altacitaVo = new AltaCitaVO();
		OracleProcedures oracle = new OracleProcedures();
		
		String logId="";
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		String sResponse = "";
		
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     cveHora                : "+ cveHora);
			Logger.write("     fecha                  : "+ fecha);
			Logger.write("     tienda                 : "+ tienda);
			Logger.write("     suscriptor             : "+suscriptor);
			Logger.write("     nombre                 : "+nombre);
			Logger.write("     apPaterno              : "+apPaterno);
			Logger.write("     apMaterno              : "+apMaterno);
			Logger.write("     correo                 : "+correo);
			Logger.write("     comentario             : "+comentario);
			Logger.write("     tipoAtencio            : "+tipoAtencion);
			Logger.write("     cveGenerdor            : "+cveGenerdor);
			Logger.write("     Dispositivo            : "+dispositivo);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken("", token, new MensajeLogBean());
			
			try{
				
				sResponse = focalizacion.focalizacion(suscriptor);
				if(sResponse != null && !sResponse.equals(""))
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);
				
			int tipoTecnologia = 0;

			if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
			  tipoTecnologia = 2;  //Etak
			}else{
			  tipoTecnologia = 1;  //Legacy
			}

			int tipoLinea = 0;

			if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
			   if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
			     tipoLinea = 2;
			   }else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
			     tipoLinea = 3;
			   }
			}else {
			   tipoLinea = 1; //prepago
			}
				logId = oracle.altaCita(user,pass, cveHora,fecha,tienda, suscriptor,nombre,apPaterno,apMaterno, correo, comentario, tipoAtencion,cveGenerdor, compania, sistemaOrigen,token,0,tipoLinea, tipoTecnologia, dispositivo);
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
			
			altacitaVo = AltaCita.flujo("O7k13t9t9TbMTd3[mJN]uj39", "O7k13t9t9TbMTd3[mJN]uj39", cveHora, fecha, tienda, suscriptor, nombre, apPaterno, apMaterno, correo, comentario, tipoAtencion, cveGenerdor, empresa, logId);
			
			Logger.write("   + Respuesta              + ");	
			Logger.write("     exito                  : " +altacitaVo.getExito());
			Logger.write("     mensaje                : " +altacitaVo.getMensaje());

		}catch(Exception e){
			try{
				oracle.altaCitaResponse(logId, "","", "1", e.getMessage(), 1);
			}catch (Exception ex) {
				Logger.write("Bitacora: No se impacto la bitacora - " + ex.getLocalizedMessage());
			}
			//ASI
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("ORA-20008")){
					throw new ServiceException("[WARN] altaCitas " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] altaCitas " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] altaCitas " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("altaCitas :: " + getLocalAddress(), initime);
		}
		return altacitaVo;
	}
	
	public List<CitasDisponiblesXHorario> horarioDisponibleXcae(String user, String pass,String cae, int motivo, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("horarioDisponibleXcae");


		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}

		List<CitasDisponiblesXHorario> datos = new ArrayList<CitasDisponiblesXHorario>();
		ValidaTokens tokens =  new ValidaTokens();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     cae                    : "+ cae);
			Logger.write("     motivo                 : "+ motivo);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken("", token, new MensajeLogBean());

			datos = GetHorarioDisponblesXCAE.flujo("O7k13t9t9TbMTd3[mJN]uj39", "O7k13t9t9TbMTd3[mJN]uj39", cae, motivo , token);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     datos.size             : "+ datos.size());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("ORA-20008")){
					throw new ServiceException("[WARN] horarioDisponibleXcae ["+cae+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] horarioDisponibleXcae ["+cae+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] horarioDisponibleXcae ["+cae+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("horarioDisponibleXcae ["+cae+"] :: " + getLocalAddress(), initime);
		}

		return datos;
	}
	
	public int generaPin(String user, String pass, String dn, String pin, int idServicio, String token) throws Throwable
	{
		long initime = System.currentTimeMillis();
        Logger.init("generaPin");

		if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es válido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}			
		ValidaTokens tokens =  new ValidaTokens();
		int respuesta = 0;
		String ip="";
		try {
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : "+ dn);
			Logger.write("     pin                    : "+ pin);
			Logger.write("     idSericio              : "+ idServicio);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			ip = getLocalAddressOnly();
			respuesta = GeneraPIN.flujo(dn, pin, idServicio, ip);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              :"+ respuesta);
		}
		catch (Exception e) {
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("PIN MENSAJE")){
					throw new ServiceException("[WARN] generaPin ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] generaPin ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] generaPin ["+dn+"] :: " + e.getLocalizedMessage());
			}			
		} finally {
			Logger.end("generaPin ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return respuesta;
	}
	
	public String actualizaFotografiaXUsu(String user, String pass,String dn, String fotografia, int usuarioID,String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("actualizaFotografiaXUsu");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("[ALE] password no válido");
		}
		String datos = "";
		ValidaTokens tokens =  new ValidaTokens();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     DN                     : "+ dn);
			Logger.write("     usuarioID              : "+ usuarioID);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			datos = ActualizaFotografiaXUsu.flujo(token, dn, fotografia, usuarioID);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     datos                  :"+ datos);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] actualizaFotografiaXUsu ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] actualizaFotografiaXUsu ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] actualizaFotografiaXUsu ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("actualizaFotografiaXUsu ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return datos;
	}
	
	public AbonoTiempoAireVO abonoTiempoAire (String user,String pass, String dn, String dnParaAbono, int anioExpira, String cdgSeguridad, String concepto, Double importe, int mesExpira, String numTarjeta, String tipoTarjeta, String ip, Long secuencia, String password, int tipoPlataforma,  String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("abonoTiempoAire");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el usuario no es valido " + user.trim());
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el password no es valido " + user.trim());
			throw new ServiceException("password no valido");
		}
		
		ValidatorCharges.validaCantidadTA(importe);

		AbonoTiempoAireVO tiempoAireVO = new AbonoTiempoAireVO();
		ValidaTokens tokens =  new ValidaTokens();
		OracleProcedures oracle = new OracleProcedures();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		String sResponse = "";
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     DN                     : "+ dn);
			Logger.write("     dnParaAbono            : "+ dnParaAbono);
			Logger.write("     Anio Expira            : "+ anioExpira);
			Logger.write("     Cdg Seguridad          : xxx");
			Logger.write("     Concepto               : "+ concepto);
			Logger.write("     Importe                : "+ importe);
			Logger.write("     Mes Expira             : "+ mesExpira);
			Logger.write("     Num Tarjeta            : "+ numTarjeta);
			Logger.write("     Tipo Tarjeta           : "+ tipoTarjeta);
			Logger.write("     Ip                     : "+ ip);
			Logger.write("     Secuencia              : "+ secuencia);
			Logger.write("     Password               : "+ password);
			Logger.write("     Tipo Plataforma        : "+ tipoPlataforma);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken("", token, new MensajeLogBean());
			
			String respuesta = "";
			try{
                ip="10.189.64.45";
				sResponse = focalizacion.focalizacion(dn);
				if(sResponse != null && !sResponse.equals(""))
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);

				/*  TIPO TECNOLOGIA:
				 *   1 LEGACY
				 *   2 ETAK
				 */  

				int tipoTecnologia = 0;

				if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
					tipoTecnologia = 2;  //Etak
				}else{
					tipoTecnologia = 1;  //Legacy
				}

				/*TIPO LINEA:
				 * 1	PREPAGO	
				 * 2	POSTPAGO	
				 * 3	HIBRIDO
				 */

				int tipoLinea = 0;

				if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
					if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
						tipoLinea = 2;
					}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
						tipoLinea = 3;
					}
				}else {
					tipoLinea = 1; //prepago
				}

				respuesta = oracle.abonoTiempoAire(user, pass, dn,  dnParaAbono, anioExpira, "xxx", concepto, importe,mesExpira, numTarjeta, tipoTarjeta, ip,  secuencia, password, tipoPlataforma, 7, 1, tipoLinea, tipoTecnologia,2,token, 2021);
				
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
			AbonoTiempoAireIn abonoTA = null;
			String autorizadorDirecto = oracle.getValorParametro(208);
			if(autorizadorDirecto.equals("1")){
				abonoTA = new AbonoTiempoAireAutorizador();
			}else{
				abonoTA = new AbonoTiempoAire();
			}
			tiempoAireVO = abonoTA.flujo(token, dn, dnParaAbono, anioExpira, cdgSeguridad, concepto, importe, mesExpira, numTarjeta, tipoTarjeta, ip, secuencia, password, tipoPlataforma,respuesta);
			
			try{
				if(tiempoAireVO != null)
					oracle.abonoTAAutorizador(respuesta, tiempoAireVO.getCodigoRespuestaAbonoTA(), tiempoAireVO.getNumeroAutorizacionAbonoTA(), tiempoAireVO.getNumTransaccionCaja(), tiempoAireVO.getNumeroAutorizacionCaja(), tiempoAireVO.getIdRegistroCaja(), 2121,"","");
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("cardBlackList") || e.getLocalizedMessage().contains("TransXTarjeta")
						||e.getLocalizedMessage().contains("SemaphoreApplyCharge")||e.getLocalizedMessage().contains("[ctrl]")){
						throw new ServiceException("[WARN] abonoTiempoAire ["+dn+"] :: " + e.getLocalizedMessage());
					}else{
						throw new ServiceException("[ERR] abonoTiempoAire ["+dn+"] :: " + e.getLocalizedMessage());
					}
			}else{
				throw new ServiceException("[ERR] abonoTiempoAire ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("abonoTiempoAire ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return tiempoAireVO;
	}
	
	public int reestablecerLlave(String user, String pass, String dn, String preguntaSecreta, String resPreguntaSecreta, int idServicio, int usuarioID, String token) throws Throwable{	    

		long initime = System.currentTimeMillis();
        Logger.init("reestablecerLlave");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}

		int respuesta = -1;
		ValidaTokens tokens =  new ValidaTokens();
		

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     preguntaSecreta        : " + preguntaSecreta);
			Logger.write("     resPreguntaSecreta     : " + resPreguntaSecreta);
			Logger.write("     idServicio             : " + idServicio);
			Logger.write("     usuarioID              : " + usuarioID);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			respuesta = RestablecerLlave.flujo(dn, preguntaSecreta, resPreguntaSecreta, idServicio, usuarioID);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("Parametros de entrada")){
					throw new ServiceException("[WARN] reestablecerLlave ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] reestablecerLlave ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] reestablecerLlave ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("reestablecerLlave ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return respuesta;
	}
	
	public ReprogramacionVO reprogramacion (String user, String pass, String tecnologia, String t_msisdn, String token) throws Throwable 
	{
		long initime = System.currentTimeMillis();
        Logger.init("reprogramacion");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		ReprogramacionVO reprogramacionVo = new ReprogramacionVO();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     tecnologia             : " + tecnologia);
			Logger.write("     t_msisdn               : " + t_msisdn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(t_msisdn, token, new MensajeLogBean());
			
    		reprogramacionVo = Reprogramacion.flujo(tecnologia, t_msisdn);

			Logger.write("   + Respuesta              + ");
			Logger.write("     reprogramacionVo       : ");
			Logger.write("     codigoRespuesta        : " + reprogramacionVo.getCodigoRespuesta());
			Logger.write("     dn                     : " + reprogramacionVo.getDn());
			Logger.write("     mensaje                : " + reprogramacionVo.getMensaje());

		}catch(Exception e){
			throw new ServiceException("[ERR] Reprogramacion ["+t_msisdn+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("Reprogramacion ["+t_msisdn+"] :: " + getLocalAddress(), initime);
		}
		return reprogramacionVo;	
	}

	public DNSugeridoVO DNSugerido (String user, String pass, String dn, String cantidad, String localidad, String cveEdoCen, String cveMunCen, String cvePobCen, int compania, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("DNSugerido");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}
		ValidaTokens tokens =  new ValidaTokens();
		DNSugeridoVO sugeridoVo = new DNSugeridoVO();
		OracleProcedures oracle = new OracleProcedures();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     cantidad               : " + cantidad);
			Logger.write("     localidad              : " + localidad);
			Logger.write("     compania               : " + compania);
			Logger.write("     cveEdoCen              : " + cveEdoCen);
			Logger.write("     cveMunCen              : " + cveMunCen);
			Logger.write("     cvePobCen              : " + cvePobCen);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token, new MensajeLogBean());
			
			sugeridoVo = DNSugerido.flujo(dn, cantidad, localidad,cveEdoCen,cveMunCen,cvePobCen, compania);
			
			try{
				if(sugeridoVo != null && sugeridoVo.getSugeridoDn().size() > 0)
					for (String data : sugeridoVo.getSugeridoDn()) {
						oracle.insertaDNxReservar(data);
					}
			}catch (Exception e) {
				Logger.write("      Detail al guardar los numeros a reservar: " + e.getMessage());
			}
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     sugeridoVo             :");
			Logger.write("     descLocalidad          :" + sugeridoVo.getDescLocalidad());
			Logger.write("     localidad              :" + sugeridoVo.getLocalidad());
			Logger.write("     SugeridoDn             :" + sugeridoVo.getSugeridoDn());
			
		}catch(Exception e){
			throw new ServiceException("[ERR] DNSugerido ["+dn+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("DNSugerido ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return sugeridoVo;
	}

		public DNCambioVO DNCambio(String user, String pass, String dnActual, String dnNuevo, String cveEdoCen, String cveMunCen, String cvePobCen, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("DNCambio");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}
		ValidaTokens tokens =  new ValidaTokens();
		DNCambioVO cambioVo = new DNCambioVO();
		OracleProcedures oracle = new OracleProcedures();
		
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		String sResponse = "";

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dnActual               : " + dnActual);
			Logger.write("     dnNuevo                : " + dnNuevo);
			Logger.write("     cveEdoCen              : " + cveEdoCen);
			Logger.write("     cveMunCen              : " + cveMunCen);
			Logger.write("     cvePobCen              : " + cvePobCen);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dnActual, token, new MensajeLogBean());

			String idIdentificador="";
  			try {
  				
  				sResponse = focalizacion.focalizacion(dnActual);
  				if(sResponse != null && !sResponse.equals(""))
  					descripcion = ParseXMLFile.parseFocalizacion(sResponse);

  				/*  TIPO TECNOLOGIA:
  				 *   1 LEGACY
  				 *   2 ETAK
  				 */  

  				int tipoTecnologia = 0;

  				if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
  					tipoTecnologia = 2;  //Etak
  				}else{
  					tipoTecnologia = 1;  //Legacy
  				}

  				/*TIPO LINEA:
  				 * 1	PREPAGO	
  				 * 2	POSTPAGO	
  				 * 3	HIBRIDO
  				 */

  				int tipoLinea = 0;

  				if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
  					if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
  						tipoLinea = 2;
  					}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
  						tipoLinea = 3;
  					}
  				}else {
  					tipoLinea = 1; //prepago
  				}

  				idIdentificador = oracle.cambioDNSET(user, pass, dnActual, dnNuevo, cveEdoCen, cveMunCen, cvePobCen, 7, 1, token, 2121,tipoLinea, tipoTecnologia, 2);
  				
  			}catch (Exception e) {
               Logger.write("Detail al ejecutar el metodo :: cambioDNSET");
			}
  			
			cambioVo =  DNCambio.flujo(dnActual, dnNuevo, cveEdoCen, cveMunCen, cvePobCen,"");
			
			try{
  				oracle.dnCambioResponse(idIdentificador, cambioVo.getResponseCode(), cambioVo.getResponseMessage(), 2021,"","");
  			}catch (Exception e) {
				Logger.write("Detail al ejecutar al metodo :: dnCambioResponse");
			}
  			
			Logger.write("   + Respuesta              + ");
			Logger.write("     cambioVo ");
			Logger.write("     responseCode           :" + cambioVo.getResponseCode());
			Logger.write("     responseMessage        :" + cambioVo.getResponseCode());

		}catch(Exception e){
			throw new ServiceException("[ERR] DNCambio ["+dnActual+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("DNCambio ["+dnActual+"] :: " + getLocalAddress(), initime);
		}
		return cambioVo;
	}

	public DNReactivacionVO DNSuspensionReactivacion(String user, String pass, String dn, String reason, int tipo, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("DNSuspensionReactivacion");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("password no valido");
		}
		ValidaTokens tokens =  new ValidaTokens();
		DNReactivacionVO reactivacionVo = new DNReactivacionVO();
		OracleProcedures oracle = new OracleProcedures();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		String sResponse = "";
		
		try{
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     reason                 : " + reason);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			String idIdentificador = "";
			try {

				sResponse = focalizacion.focalizacion(dn);
				if(sResponse != null && !sResponse.equals(""))
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);

				/*  TIPO TECNOLOGIA:
				 *   1 LEGACY
				 *   2 ETAK
				 */  

				int tipoTecnologia = 0;

				if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
					tipoTecnologia = 2;  //Etak
				}else{
					tipoTecnologia = 1;  //Legacy
				}

				/*TIPO LINEA:
				 * 1	PREPAGO	
				 * 2	POSTPAGO	
				 * 3	HIBRIDO
				 */

				int tipoLinea = 0;

				if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
					if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
						tipoLinea = 2;
					}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
						tipoLinea = 3;
					}
				}else {
					tipoLinea = 1; //prepago
				}

				idIdentificador = oracle.suspensionReactivacionSET(user, pass, dn, reason, tipo, 7, 1, token, 2121,tipoLinea, tipoTecnologia, 2);
			}catch (Exception e) {
                Logger.write("Detail al consultar el metodo :: suspensionReactivacionSET");
			}
			
			reactivacionVo = DNReactivacion.flujo(dn, reason, tipo, idIdentificador);

			if(reactivacionVo != null && !reactivacionVo.equals("")){
				try{
					oracle.suspensionReactivacionesRES(idIdentificador, 4, reactivacionVo.getOperationCode(), reactivacionVo.getMessageCode(), 2121,"","");
				}catch (Exception e) {
					Logger.write("Detail al consultar el metodo :: suspensionReactivacionesRES");
				}
			}
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("   + Respuesta              + ");
			Logger.write("     reactivacionVo ");
			Logger.write("     operationCode          : " + reactivacionVo.getOperationCode());
			Logger.write("     messageCode            : " + reactivacionVo.getMessageCode());

		}catch(Exception e){
			throw new ServiceException("[ERR] DNSuspensionReactivacion ["+dn+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("DNSuspensionReactivacion ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return reactivacionVo;
	}
	
	public int eliminaUsuario(String user, String pass, String dn, int isuarioId, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("eliminaUsuario");
		
		if (user.trim().equals("") || !user.trim().equals("FGhAxzwOwKHbI65XQ1MIjQ++")) {
			Logger.write("     El usuario no es válido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("FGhAxzwOwKHbI65XQ1MIjQ++")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();	    
		int respuesta = -1;

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     isuarioId              : " + isuarioId);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean(""));

			respuesta = EliminaUsuario.flujo(dn, isuarioId);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] eliminaUsuario ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] eliminaUsuario ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] eliminaUsuario ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("eliminaUsuario ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return respuesta;
	}
	
	
	public DatosFocalizacionVO datosFocalizacion(String user, String pass, String dn, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("datosFocalizacion");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		CallServiceFocalizacion datosfocalizacion = new CallServiceFocalizacion();
		DatosFocalizacionVO response = new DatosFocalizacionVO();
		OracleProcedures oracle = new OracleProcedures();
		String[] parametrosSplit = null;
		String cadenaQuitar = oracle.getValorParametro(5);
		if(cadenaQuitar != null && !cadenaQuitar.equals(""))
			parametrosSplit = cadenaQuitar.toLowerCase().trim().split(",");
		String respuesta = "";
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean(""));
			
			respuesta = datosfocalizacion.focalizacion(dn);
			if(respuesta != null && !respuesta.equals(""))
				response = ParseXMLFile.parseDatosFocalizacion(respuesta);
			
			final String consultaOperador = oracle.getValorParametro(192);
			if(response != null && consultaOperador.equals("1")){
				final ConsultaOperador operador = new ConsultaOperador();
				try{
					response.setIdOperador(operador.getIdOperador(dn));
				}
				catch (Exception e) {
					Logger.write("   Ocurrio un error al consultar operador WSConsultasNum : " + e.getLocalizedMessage());
				}
			}

			if(response.getDescripcionPlan() != null && !response.getDescripcionPlan().equals("")){
				if(parametrosSplit != null && parametrosSplit.length>0) {
					String datoPivote = response.getDescripcionPlan();
					for(int a=0; a< parametrosSplit.length; a++) {
						datoPivote = datoPivote.toLowerCase();
						datoPivote = datoPivote.replace(parametrosSplit[a], "");
						response.setDescripcionPlan(datoPivote.toUpperCase());
					}
				}
			}
			
			Logger.write("   + Respuesta              + ");
			if(respuesta != null)
				Logger.write("     respuesta.size         : " + respuesta.length());
			
		}catch(Exception e){
			throw new ServiceException("[ERR] datosFocalizacion ["+dn+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("datosFocalizacion ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return response;
	}
	
	public ConsultaOperadorVO consultaOperador(String user, String pass, String dn, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("consultaOperador");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		CallServiceFocalizacion datosfocalizacion = new CallServiceFocalizacion();
		ConsultaOperadorVO response = new ConsultaOperadorVO();
		DetalleFocalizacionVO respuestaFoca = new DetalleFocalizacionVO();
		String sResponse = "";
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean(""));
			
			sResponse = datosfocalizacion.focalizacion(dn);
			if(sResponse != null && !sResponse.equals(""))
				respuestaFoca = ParseXMLFile.parseFocalizacion(sResponse);

			if(respuestaFoca.getIdOperador() != null && respuestaFoca.getIdOperador().equals("190"))
				respuestaFoca.setIdOperador("5");
				
			response.setIdOperador(respuestaFoca.getIdOperador());
			
			Logger.write("   + Respuesta              + ");
			if(respuestaFoca != null)
				Logger.write("     respuesta              : " + response.getIdOperador());
			
		}catch(Exception e){
			throw new ServiceException("[ERR] consultaOperador ["+dn+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("consultaOperador ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return response;
	}
	
	public MensajeMailVO enviarCorreoSMS(String user, String pass, String dn, String tipoEvento, String mail, String nombre, int idServicio, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("enviarCorreoSMS");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es válido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		MensajeMailVO respuesta = new MensajeMailVO();
		EnviaCorreoDetalle enviaDetalle = new EnviaCorreoDetalle();
		String ip="";
		String nameFrom="";
		String mailFrom="";
		OracleProcedures oracle = new OracleProcedures();
		
		try{
			ip = getLocalAddressOnly();
						
			Logger.write("     user                   : -PROTEGIDO -");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     tipoEvento             : " + tipoEvento);
			Logger.write("     nombre                 : " + nombre);
			Logger.write("     idServicio             : " + idServicio);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean(""));
	
			if (idServicio==4){
				nameFrom=oracle.getValorParametro(126);
				mailFrom=oracle.getValorParametro(127);
				
				nameFrom=nameFrom.replace("&", "&amp;");

				respuesta = enviaDetalle.flujoATTSMS(dn, tipoEvento, mail, nombre, idServicio, ip, mailFrom, nameFrom);
			}else{
				respuesta = enviaDetalle.flujo(dn, tipoEvento, mail, nombre, idServicio, ip);
			}
			
			Logger.write("   + Respuesta              + ");
			if(respuesta != null){
				Logger.write("     MailEr_ror             : " + respuesta.getMailError());
				Logger.write("     MailSended             : " + respuesta.getMailSended());
				Logger.write("     SmsEr_ror              : " + respuesta.getSmsError());
				Logger.write("     SmsSended              : " + respuesta.getSmsSended());
			}
		}catch(Exception e){
			
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] enviarCorreoSMS ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] enviarCorreoSMS ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] enviarCorreoSMS ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());	
			}
			
		}
		finally{
			Logger.end("enviarCorreoSMS ["+dn+"]["+tipoEvento+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public MensajeMailVO enviarCorreoSMSTotales(String user, String pass, String dn, String mail, String nombre, int idServicio, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("enviarCorreoSMSTotales");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es válido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		MensajeMailVO respuesta = new MensajeMailVO();
		EnviaCorreoDetalle enviaDetalle = new EnviaCorreoDetalle();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     nombre                 : " + nombre);
			Logger.write("     idServicio             : " + idServicio);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean(""));
			
			respuesta = enviaDetalle.flujoTotales(dn, mail, nombre, idServicio);
			
			Logger.write("   + Respuesta              + ");
			if(respuesta != null){
				Logger.write("     MailEr_ror             : " + respuesta.getMailError());
				Logger.write("     MailSended             : " + respuesta.getMailSended());
				Logger.write("     SmsEr_ror              : " + respuesta.getSmsError());
				Logger.write("     SmsSended              : " + respuesta.getSmsSended());
			}
		}catch(Exception e){
			throw new ServiceException("[ERR] enviarCorreoSMSTotales ["+dn+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("enviarCorreoSMSTotales ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public ObtenerDescripcionPlanesVO1 obtenerDescripcionPlanes(String user, String pass, String idPlan, String plan, int idElephan, int usuario, String ip, String token) throws Throwable {
		
		long initime = System.currentTimeMillis();
        Logger.init("obtenerDescripcionPlanes");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el usuario no es válido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
//		String respuesta="";
		ObtenerDescripcionPlanesVO1 obtenerDescripcionP = new ObtenerDescripcionPlanesVO1();
//		CallServiceObtenerDescripcionPlanes obtenPlanes = new CallServiceObtenerDescripcionPlanes();
//		OracleProcedures oracle = new OracleProcedures();
		
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     idPlan                 : " + idPlan);
			Logger.write("     plan                   : " + plan);
			Logger.write("     idElephan              : " + idElephan);
			Logger.write("     usuario                : " + usuario);
			Logger.write("     ip                     : " + ip);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(plan, token, new MensajeLogBean(""));
			
//			respuesta = obtenPlanes.serviceObtenerDescripcionPlanes(idPlan, plan, idElephan+"", usuario, ip);
//			if(respuesta != null && !respuesta.equals(""))
//				obtenerDescripcionP	= ParseXMLFile.ParseObtenerDescripcionPlanes(respuesta);
			
			obtenerDescripcionP = OfertaComercial.consultaDescripcionPlanes(idPlan, plan, idElephan+"", usuario, ip);
			
			Logger.write("   + Respuesta              + ");
			if(obtenerDescripcionP != null){
				Logger.write("     Megaadc                : " + obtenerDescripcionP.getMegaadc());
				Logger.write("     Megas                  : " + obtenerDescripcionP.getMegas());
				Logger.write("     Mensajeadc             : " + obtenerDescripcionP.getMensajeadc());
				Logger.write("     Mensajes               : " + obtenerDescripcionP.getMensajes());
				Logger.write("     Minutoadc              : " + obtenerDescripcionP.getMinutoadc());
				Logger.write("     Minutos                : " + obtenerDescripcionP.getMinutos());
				Logger.write("     Minutoscomunidad       : " + obtenerDescripcionP.getMinutoscomunidad());
				Logger.write("     NombreCortoPlan        : " + obtenerDescripcionP.getNombreCortoPlan());
				Logger.write("     TiempoAire             : " + obtenerDescripcionP.getTiempoAire());
			}
		}catch(Exception e){
			throw new ServiceException("[ERR] obtenerDescripcionPlanes :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("obtenerDescripcionPlanes :: " + getLocalAddress(), initime);
		}

		return obtenerDescripcionP;
	}
	
	public List<ServiciosDisponiblesVO> serviciosDisponibles(String user, String pass, String UnidadNegocio, String token) throws Throwable {
		long initime = System.currentTimeMillis();
		Logger.init("serviciosDisponibles", UnidadNegocio);
		
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el password no es valido ");
			throw new ServiceException("password no valido");
		}
		
		ValidaTokens tokens =  new ValidaTokens();
		List<ServiciosDisponiblesVO> respuesta = new ArrayList<ServiciosDisponiblesVO>();
		OracleProcedures oracle = new OracleProcedures();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     UnidadNegocio          : " + UnidadNegocio);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(UnidadNegocio, token, new MensajeLogBean(""));
			
			respuesta = oracle.serviciosDisponibles(UnidadNegocio);             			
			
			Logger.write("   + Respuesta              + ");
			if(respuesta != null)
				Logger.write("     respuesta.size         : " + respuesta.size());
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] serviciosDisponibles :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] serviciosDisponibles :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] serviciosDisponibles :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("serviciosDisponibles :: " + getLocalAddress(), initime);
		}
		
		return respuesta;
	}

	public LoginVO miIusacellUsuario(String user, String pass, String numeroEmpleado, int empresa, String secureToken, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("miIusacellUsuario");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("password no valido");
		}

		String sResponse = "";
		CallServiceLogin serviceLogin = new CallServiceLogin();
		LoginVO login = new LoginVO();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     numeroEmpleado         : " + numeroEmpleado);
			Logger.write("     empresa                : " + empresa);
			Logger.write("     secureToken            : " + secureToken);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			sResponse = serviceLogin.callServiceLogin(numeroEmpleado, empresa, secureToken);
			
			if(sResponse != null && !sResponse.equals(""))
				login = ParseXMLFile.ParseLoginUsuario(sResponse);
			
			Logger.write("   + Respuesta              + ");
			if(login != null){
				Logger.write("     ApellidoMat            : " + login.getApellidoMat());
				Logger.write("     ApellidoPat            : " + login.getApellidoPat());
				Logger.write("     CAEDesc                : " + login.getCAEDesc());
				Logger.write("     CaeID                  : " + login.getCaeID());
				Logger.write("     EmpresaDes             : " + login.getEmpresaDes());
				Logger.write("     Huellas                : " + Formatter.pintaLogCadenasLargas(login.getHuellas()));
				Logger.write("     Nombre                 : " + login.getNombre());
				Logger.write("     Perfiles               : " + login.getPerfiles());
				Logger.write("     PerfilPvsDesc          : " + login.getPerfilPvsDesc());
				Logger.write("     Puesto                 : " + login.getPuesto());
				Logger.write("     DadoAlta               : " + login.getDadoAlta());
				Logger.write("     Empresa                : " + login.getEmpresa());
				Logger.write("     PerfilPvsID            : " + login.getPerfilPvsID());
			}

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] miIusacellUsuario :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] miIusacellUsuario :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] miIusacellUsuario :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("miIusacellUsuario :: " + getLocalAddress(), initime);
		}
		return login;
	}
	
	public List<ServiciosAdicionalesVO> getServiciosAdicionales(String user, String pass, int version, String idPlan, String token) throws Throwable{
		
        long initime = System.currentTimeMillis();
        Logger.init("getServiciosAdicionales");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("password no valido");
		}
		
		ValidaTokens tokens = new ValidaTokens();
		
		List<ServiciosAdicionalesVO> resServAdicionales = new ArrayList<ServiciosAdicionalesVO>();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     version                : "+ version);
			Logger.write("     idPlan                 : "+ idPlan);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken("", token, new MensajeLogBean());
			
			resServAdicionales = GetServiciosAdicionales.flujo(version,idPlan);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     descripcion            : "+ resServAdicionales.size());
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().toLowerCase().contains("sin servicios disponibles")){
					throw new ServiceException("[WARN] getServiciosAdicionales :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] getServiciosAdicionales :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] getServiciosAdicionales :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("getServiciosAdicionales :: " + getLocalAddress(), initime);
		}

		return resServAdicionales;
		
	}
	
	public List<ObtieneEstadoMunicipioVO> ObtieneEstadosMunicipios(String user, String pass, String dn, String filtro, String token )  throws Throwable{
		
	    long initime = System.currentTimeMillis();
        Logger.init("ObtieneEstadosMunicipios");

		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el password no es valido ");
			throw new ServiceException("password no valido");
		}
		
		ValidaTokens tokens =  new ValidaTokens();
		List<ObtieneEstadoMunicipioVO> respuesta = new ArrayList<ObtieneEstadoMunicipioVO>();
		OracleProcedures oracle = new OracleProcedures();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     filtro                 : " + filtro);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token, new MensajeLogBean(""));
			
			if (filtro !=null && !filtro.equals("")) {
				//si filtro viene lleno invocas: FUNCION: MIIUSACELL.PAMIIPOBLACIONXEDO.FNGETMUNICIPIOXEDO ( PFCVEEDOCEN )
			    respuesta = oracle.serviciosObtieneMunicipioXEdo(filtro);
			}else{
			   //Si filtro viene vacio, invocas: FUNCION: MIIUSACELL.PAMIIPOBLACIONXEDO.FNGETESTADO	
				respuesta = ObtieneEstadosMunicipiosInfo.flujo(dn, filtro);
			}

			if(respuesta !=null && respuesta.size() > 0){
				for (int i = 0; i < respuesta.size(); i++) {
					if(respuesta.get(i) != null && respuesta.get(i).getFmunicipio() != null){
						respuesta.get(i).setFmunicipio(Utilerias.formatoCadena(respuesta.get(i).getFmunicipio()));
					}
				}
			}
			
			if(respuesta !=null)
			{
				Logger.write("   + Respuesta              + ");
				Logger.write("     descripcion            : "+ respuesta.size());
			}
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] ObtieneEstadosMunicipios ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] ObtieneEstadosMunicipios ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] ObtieneEstadosMunicipios ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			 Logger.end("ObtieneEstadosMunicipios ["+dn+"] :: " + getLocalAddress(), initime);
		}
    return respuesta;
	}
	
	public List<ObtienePoblacionesVO> ObtienePoblaciones(String user, String pass,String cveedocen, String pfcvemuncen, String token )  throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("ObtienePoblaciones");

		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el password no es valido ");
			throw new ServiceException("password no valido");
		}
		
		ValidaTokens tokens =  new ValidaTokens();
		List<ObtienePoblacionesVO> respuesta = new ArrayList<ObtienePoblacionesVO>();
		OracleProcedures oracle = new OracleProcedures();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     cveedocen              : " + cveedocen);
			Logger.write("     pfcvemuncen            : " + pfcvemuncen);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(cveedocen, token, new MensajeLogBean(""));
			
			respuesta = oracle.serviciosObtienePoblaciones(cveedocen, pfcvemuncen);
			
			if(respuesta !=null && !respuesta.equals(""))
			{
				Logger.write("   + Respuesta              + ");
				Logger.write("     descripcion            : "+ respuesta.size());
			}
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] ObtienePoblaciones :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] ObtienePoblaciones :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] ObtienePoblaciones :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("ObtienePoblaciones :: " + getLocalAddress(), initime);
		}
    return respuesta;
	}
	
	public List<TiposDeAtencionVO> getTiposDeAtencion (String user, String pass, String token) throws Throwable{
	    
		long initime = System.currentTimeMillis();
        Logger.init("getTiposDeAtencion");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
		      throw new ServiceException("usuario no valido");
	    }
		
	    if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
	      throw new ServiceException("password no valido");
	    }
	    
	    ValidaTokens tokens =  new ValidaTokens();
	    List<TiposDeAtencionVO> listTipoDeAtencion = new ArrayList<TiposDeAtencionVO>();
	    
	    try{
	    	
	    	Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken("", token, new MensajeLogBean(""));

			listTipoDeAtencion = GetTiposDeAtencion.flujo();
	    	
			if(listTipoDeAtencion != null)
			{
				Logger.write("   + Respuesta              + ");
				Logger.write("     respuesta.size         : "+ listTipoDeAtencion.size());
			}
	    }catch (Exception e) {
	    	if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("ORA-20008")){
					throw new ServiceException("[WARN] getTiposDeAtencion :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] getTiposDeAtencion :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] getTiposDeAtencion :: " + e.getLocalizedMessage());
			}
		}finally
		{
			Logger.end("getTiposDeAtencion :: " + getLocalAddress(), initime);
		}
	
		return listTipoDeAtencion;
	}
	
	public ImagenEquipoVO getImagenEquipo (String user, String pass, String idEquipo, String descripcionEquipo, String token) throws Throwable{
	    
		long initime = System.currentTimeMillis();
        Logger.init("getImagenEquipo");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
		      throw new ServiceException("usuario no valido");
	    }
		
	    if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
	      throw new ServiceException("password no valido");
	    }
	    
	    ValidaTokens tokens =  new ValidaTokens();
	    ImagenEquipoVO response = new ImagenEquipoVO();
//	    CallServiceObtenerDescripcionPlanes equipoImagen = new CallServiceObtenerDescripcionPlanes();
//	    String sResponse = "";
	    	    
	    try{
	    	
	    	Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     idEquipo               : " + idEquipo);
			Logger.write("     descripcionEquipo      : " + descripcionEquipo);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken("", token, new MensajeLogBean(""));
			
//			sResponse = equipoImagen.getImagenEquipoSO(idEquipo, descripcionEquipo);
//			if(sResponse != null && !sResponse.equals("")){
//				response = ParseXMLFile.parseImagenEquipo(sResponse);
//			}
			
			response = OfertaComercial.getImagenEquipoSO(idEquipo, descripcionEquipo);
	    	
	    }catch (Exception e) {
	    	if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("ORA-20008")){
					throw new ServiceException("[WARN] getImagenEquipo ["+idEquipo+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] getImagenEquipo ["+idEquipo+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] getImagenEquipo ["+idEquipo+"] :: " + e.getLocalizedMessage());
			}
		}finally
		{
			Logger.end("getImagenEquipo ["+idEquipo+"] :: " + getLocalAddress(), initime);
		}
	
		return response;
	}
	
	public int configuracionXUsuario(String user, String pass, String pcdn, String pcconfiguracion,int piiusaurioid, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("configuracionXUsuario");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}

		int validaLog = -1;
		ValidaTokens tokens =  new ValidaTokens();
		OracleProcedures oracle = new OracleProcedures();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     pcdn                   : " + pcdn);
			Logger.write("     pcconfiguracion        : " + pcconfiguracion);
			Logger.write("     piiusaurioid           : " + piiusaurioid);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken("", token, new MensajeLogBean(""));
			
			validaLog = oracle.configuracionXUsuario(pcdn, pcconfiguracion, piiusaurioid);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     validaLogin            : " + validaLog);
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] configuracionXUsuario ["+pcdn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] configuracionXUsuario ["+pcdn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] configuracionXUsuario ["+pcdn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("configuracionXUsuario ["+pcdn+"] :: " + getLocalAddress(), initime);
		}

		return validaLog;
	}
	
	public DatosFocalizacionUsuarioExtendido focalizacionExtendido(String user, String pass, String dn, String token) throws Throwable{		
		
		long initime = System.currentTimeMillis();
        Logger.init("focalizacionExtendido");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}

		DatosFocalizacionUsuarioExtendido datos = new DatosFocalizacionUsuarioExtendido();
		DatosLogin datosUsuario = new DatosLogin();
//		CallServiceObtenerDescripcionPlanes obtenPlanes = new CallServiceObtenerDescripcionPlanes();
		ObtenerDescripcionPlanesVO1 obtenerDescripcionP = new ObtenerDescripcionPlanesVO1();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVOExtendido descripcion = new DetalleFocalizacionVOExtendido();
		ValidaTokens tokens =  new ValidaTokens();
		OracleProcedures oracle = new OracleProcedures();
		DatosFocalizacionExtendido datosFocalizacion = new DatosFocalizacionExtendido();
		DatosFocalizacionVO responseFoca = new DatosFocalizacionVO();
		String[] parametrosSplit = null;
		String cadenaQuitar = oracle.getValorParametro(5);
		if(cadenaQuitar != null && !cadenaQuitar.equals(""))
			parametrosSplit = cadenaQuitar.toLowerCase().trim().split(",");
		
		String sResponse = "";
		

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean("login"));

			try{
				datosUsuario = IsLogin.getDatosLogin(dn);
			}catch (ServiceException e) {
				datosUsuario = new DatosLogin();
			}

			try{
				sResponse = focalizacion.focalizacion(dn);
				if(sResponse != null && !sResponse.equals("")){
					descripcion = ParseXMLFile.parseFocalizacionExtendido(sResponse);
					if(datosUsuario == null || datosUsuario.getNombre() == null || datosUsuario.getNombre().equals(""))
						responseFoca = ParseXMLFile.parseDatosFocalizacion(sResponse);
				}	
			}catch (ServiceException e) {
				descripcion = new DetalleFocalizacionVOExtendido();
			}
			
			
			if(responseFoca != null){
				
				if(responseFoca.getDescripcionPlan() != null && !responseFoca.getDescripcionPlan().equals("")){
					if(parametrosSplit != null && parametrosSplit.length>0) {
						String datoPivote = responseFoca.getDescripcionPlan();
						for(int a=0; a< parametrosSplit.length; a++) {
							datoPivote = datoPivote.toLowerCase();
							datoPivote = datoPivote.replace(parametrosSplit[a], "");
							responseFoca.setDescripcionPlan(datoPivote.toUpperCase());
						}
					}
				}
				
				datos.setDatosFocalizacionUsuario(responseFoca);
			}
			
			try{
				datos.setConfiguracion(oracle.obtieneConfiguracionXUsuario(dn));
			}catch (ServiceException e) {
				datos.setConfiguracion(new ConfiguracionXUsuarioVO());
			}
			
			datosFocalizacion.setFechaCorte(descripcion.getFechaCorte());
			datosFocalizacion.setIdOperador(descripcion.getIdOperador());
			datosFocalizacion.setIdPrepago(descripcion.getIdPrepago());
			datosFocalizacion.setIsPostpagoOrHibrido(descripcion.getIsPostpagoOrHibrido());
			datosFocalizacion.setServicio(descripcion.getServicio());
			datosFocalizacion.setSubservicio(descripcion.getSubservicio());
			datosFocalizacion.setTecnologia(descripcion.getTecnologia());
			datosFocalizacion.setIdEquipo(Utilerias.filtraCadenaIdEquipo(descripcion.getIdEquipo()));
			datos.setDatosFocalizacion(datosFocalizacion);

			try{
//				if(descripcion.getIdPlan() != null && !descripcion.getIdPlan().equals("")){
//					sResponse = obtenPlanes.serviceObtenerDescripcionPlanes(descripcion.getIdPlan(), "", "0", 212121, "10.10.10");
//				}else if(descripcion.getDescripcionPlan() != null && !descripcion.getDescripcionPlan().equals("")){
//					sResponse = obtenPlanes.serviceObtenerDescripcionPlanes("", descripcion.getDescripcionPlan(), "0", 212121, "10.10.10");
//				}else if(descripcion.getCode() != null && !descripcion.getCode().equals("")){
//					sResponse = obtenPlanes.serviceObtenerDescripcionPlanes("", "", descripcion.getCode(), 212121, "10.10.10");
//				}
//				if(sResponse != null && !sResponse.equals(""))
//					obtenerDescripcionP	= ParseXMLFile.ParseObtenerDescripcionPlanes(sResponse);
				
				obtenerDescripcionP = OfertaComercial.consultaDescripcionPlanes(descripcion.getIdPlan(), descripcion.getDescripcionPlan(), descripcion.getCode(), 212121, "10.10.10.10");
				
			}catch (ServiceException e) {
				Logger.write(e.getLocalizedMessage());
				obtenerDescripcionP = new ObtenerDescripcionPlanesVO1();
			}
			
			try{
				int compania = 0;
				compania = oracle.obtieneEmpresa(dn);
				datos.setCompania(compania);
			}catch (Exception e) {
				datos.setCompania(0);
			}
			
			if(datosUsuario != null)
				datos.setDatosUsuario(datosUsuario);
			if(obtenerDescripcionP != null)
				datos.setDatosPlan(obtenerDescripcionP);
			
			Logger.write("   + Respuesta              + ");
			if(datos.getDatosUsuario() != null){
				Logger.write("     Nombre                 : " + datos.getDatosUsuario().getNombre());
				Logger.write("     ApMaterno              : " + datos.getDatosUsuario().getApMaterno());
				Logger.write("     ApPaterno              : " + datos.getDatosUsuario().getApPaterno());
				Logger.write("     Correo                 : " + datos.getDatosUsuario().getCorreo());
				Logger.write("     Foto                   : " + ((datos.getDatosUsuario().getFoto() == null) ? "" : datos.getDatosUsuario().getFoto().length()));
				Logger.write("     Edad                   : " + datos.getDatosUsuario().getEdad());
				Logger.write("     Sexo                   : " + datos.getDatosUsuario().getSexo());
				Logger.write("     PreguntaSecreta        : " + datos.getDatosUsuario().getPreguntaSecretea());
				Logger.write("     RespuestaSecreta       : " + datos.getDatosUsuario().getRespPreguntaSecreta());
				Logger.write("     tipoCliente            : " + datos.getDatosUsuario().getTipoClienteId());
				Logger.write("     fechaNacimiento        : " + datos.getDatosUsuario().getFechaNacimiento());
			}

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] focalizacionExtendido ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] focalizacionExtendido ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] focalizacionExtendido ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("focalizacionExtendido ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return datos;
	}
	
	public List<GetCitasPendientesXDNVO> getCitasPendientesXDN(String user, String pass, String dn, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("getCitasPendientesXDN");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		
		List<GetCitasPendientesXDNVO> listgetCitasPendientesXDNVO = new ArrayList<GetCitasPendientesXDNVO>();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean(""));
			
			listgetCitasPendientesXDNVO =  GetCitasPendientesXDN.flujo(dn); 
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     getCitasPendientesXDN  : "  + listgetCitasPendientesXDNVO.size());
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("ORA-20008")){
					throw new ServiceException("[WARN] getCitasPendientesXDN ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] getCitasPendientesXDN ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] getCitasPendientesXDN ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("getCitasPendientesXDN ["+dn+"] :: " + getLocalAddress(), initime);
		}
    	return listgetCitasPendientesXDNVO;
	}
	
	public ResponseCitaVO cancelaCitaUsuario(String user, String pass, String folio, String token) throws Throwable{
		
		
		long initime = System.currentTimeMillis();
        Logger.init("cancelaCitaUsuario");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[WARN]  usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[WARN]  password no válido");
		}
		
		ValidaTokens tokens =  new ValidaTokens();
		ResponseCitaVO respuesta = new ResponseCitaVO();
		
		try {
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     folio                  : " + folio);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(folio, token, new MensajeLogBean(""));
			respuesta = CancelaCitaUsuario.flujo(folio);
			
		} catch (Exception e) {
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("ORA-20008")){
					throw new ServiceException("[WARN] cancelaCitaUsuario " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] cancelaCitaUsuario " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] cancelaCitaUsuario " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("cancelaCitaUsuario :: " + getLocalAddress(), initime);
		}
		return respuesta;
	}
	
	public List<ServiciosAdicionalesVO> getServiciosAdicionalesPorDn(String user, String pass, int version, String dn, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("getServiciosAdicionalesPorDn");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[WARN] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[WARN] password no valido");
		}
		
		ValidaTokens tokens = new ValidaTokens();
		List<ServiciosAdicionalesVO> resServAdicionales = new ArrayList<ServiciosAdicionalesVO>();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     version                : "+ version);
			Logger.write("     dn                     : "+ dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token, new MensajeLogBean());

			resServAdicionales = GetServiciosAdicionales.flujoPorDn(version,dn);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     descripcion            : "+ resServAdicionales.size());
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().toLowerCase().contains("sin servicios disponibles")){
					throw new ServiceException("[WARN] getServiciosAdicionalesPorDn ["+dn+"] " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] getServiciosAdicionalesPorDn ["+dn+"] " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] getServiciosAdicionalesPorDn ["+dn+"] " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("getServiciosAdicionalesPorDn ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return resServAdicionales;
		
	}
	
	public List<GetHorariosDisponiblesCallCenterVO> getHorariosDisponiblesCallCenter (String user,String pass, String fecha, String in3, String in4, String token) throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("getHorariosDisponiblesCallCenter");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el usuario no es valido " + user.trim());
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el password no es valido " + user.trim());
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		
		List<GetHorariosDisponiblesCallCenterVO> listHorariosDisponiblesCallCenter =  new ArrayList<GetHorariosDisponiblesCallCenterVO>();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     fecha                  : "+ fecha);
			Logger.write("     in3                    : "+ in3);
			Logger.write("     in4                    : "+ in4);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken("", token, new MensajeLogBean());
			
			listHorariosDisponiblesCallCenter = GetHorariosDisponiblesCallCenter.flujo(fecha, in3, in4);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("ORA-20008")){
					throw new ServiceException("[WARN] getHorariosDisponiblesCallCenter :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] getHorariosDisponiblesCallCenter :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] getHorariosDisponiblesCallCenter :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("getHorariosDisponiblesCallCenter :: " + getLocalAddress(), initime);
		}
		return listHorariosDisponiblesCallCenter;
	}
	
	
	public CatalogoMovilCaeVO obtieneListaVersionCaeS (String user, String pass, int canal,int idAplicacion,int version, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("obtieneListaVersionCaeS");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}
		
		ValidaTokens tokens =  new ValidaTokens();
		CatalogoMovilCaeVO respuesta = new CatalogoMovilCaeVO();
	
		try {
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     canal                  : " + canal);
			Logger.write("     idAplicacion           : " + idAplicacion);
			Logger.write("     version                : " + version);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(canal+"",token, new MensajeLogBean(""));
			
			respuesta = ObtieneListaVersionesCaesS.flujo(canal, idAplicacion, version);
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);
			
		} catch (Exception e) {
			throw new ServiceException("[ERR] obtieneListaVersionCaeS :: " + e.getLocalizedMessage());	
		}
		finally{
			Logger.end("obtieneListaVersionCaeS :: " + getLocalAddress(), initime);
		}
		return respuesta;
	}
	
	
	///////
	
	public List<CatalogoCaeGeneralVO> obtieneCae(String user, String pass, int canal,int idAplicacion, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("obtieneCae");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			
			throw new ServiceException("[ALE] password no válido");
		}
		
		ValidaTokens tokens =  new ValidaTokens();
		List<CatalogoCaeGeneralVO> respuesta = new ArrayList<CatalogoCaeGeneralVO>();
		
		
		try {
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     folio                  : " + canal);
			Logger.write("     idAplicacion           : " + idAplicacion);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(idAplicacion+"", token, new MensajeLogBean(""));
			respuesta = ObtieneCaes.flujo(canal, idAplicacion);
			
			if(respuesta != null){
				Logger.write("   + Respuesta              + ");
				Logger.write("     respuesta.size         : " + respuesta.size());
			}
		} catch (Exception e) {
			throw new ServiceException("[ERR] obtieneCae :: " + e.getLocalizedMessage());		}
		finally{
			Logger.end("obtieneCae :: " + getLocalAddress(), initime);
		}
		return respuesta;
	}
	
	public int actualizaXMLTotales(String user, String pass, String dn, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("actualizaXMLTotales");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("password no valido");
		}
		
		ValidaTokens tokens =  new ValidaTokens();
		StringBuffer sResponse = new StringBuffer();
		DetalleConsumosTotales consumos = new DetalleConsumosTotales();
		ConsumosTotalesResponseVO response = new ConsumosTotalesResponseVO();
		OracleProcedures oracle = new OracleProcedures();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
		Llamadas llamadas = new Llamadas();
		Mensajes mensajes = new Mensajes();
		Navegacion navegacion = new Navegacion();
		MinutosTodoDestino minutosTodoDestino = new MinutosTodoDestino();
		MinutosComunidad minutosComunidad = new MinutosComunidad();
		ObtenerDescripcionPlanesVO1 descripcionPlanes = new ObtenerDescripcionPlanesVO1();
		int regresa = -1;
		String actualizaTotalesTablaDirecto = "";

		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			sResponse.append(focalizacion.focalizacion(dn));
			if(sResponse != null && !sResponse.equals("")){
				descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
				sResponse.setLength(0);
			}
			
			ProcesoActualizaDN llamadasP = new ProcesoActualizaDN("Llamadas", "1", dn, descripcion.getFechaCorte());
			ProcesoActualizaDN mensajesP = new ProcesoActualizaDN("Mensajes", "2", dn, descripcion.getFechaCorte());
			ProcesoActualizaDN navegacionP = new ProcesoActualizaDN("Navegacion", "4", dn, descripcion.getFechaCorte());
			
			try{
				llamadasP.t.join();
			}catch (Exception e) {
				Logger.write(" Ocurrio un detail al Consultar llamadas: " + e.getLocalizedMessage());
				regresa = 2;
			}

			try{
				mensajesP.t.join();
			}catch (Exception e) {
				Logger.write(" Ocurrio un detail al Consultar mensajes: " + e.getLocalizedMessage());
				regresa = 2;
			}

			try{
				navegacionP.t.join();
			}catch (Exception e) {
				Logger.write(" Ocurrio un detail al Consultar navegacion: " + e.getLocalizedMessage());
				regresa = 2;
			}
			
			try{
				response = consumos.flujoFromWallets(dn, descripcion);
				Gson gson = new Gson();
				String json = gson.toJson(response);
				regresa = oracle.shedulerXMLALT(dn, 1, json, 2021);
			}catch (Exception e) {
				Logger.write(" Ocurrio un detail al guardar la cadena json: " + e.getLocalizedMessage());
				regresa = 1;
			}
			
			try{
				actualizaTotalesTablaDirecto = oracle.getValorParametro(75);
			}catch (ServiceException e) {
				actualizaTotalesTablaDirecto = "0";
			}
			
			if(actualizaTotalesTablaDirecto.equals("1")){
				try{
					double totalmsjCosto = 0;
					double totalmsj = 0;
					double msjAdc = 0;
					try{
						if(response != null && response.getTotalMensaje() != null){
							totalmsj = Double.parseDouble(response.getTotalMensaje());
						}
						if(response.getDatosPlanes() != null && response.getDatosPlanes().getMegaadc() != null)
							msjAdc = Double.parseDouble(response.getDatosPlanes().getMegaadc());
						totalmsjCosto = totalmsj * msjAdc;
					}catch (NumberFormatException e) {
						totalmsjCosto = 0;
					}
					//Llamadas
					double totalCostoLlamada = 0;
					double totalVoz = 0;
					try{
						if(response != null && response.getDatosPlanes() != null && response.getDatosPlanes().getMinutoadc() != null){
							totalCostoLlamada = Double.parseDouble(response.getDatosPlanes().getMinutoadc());
						}
						if(response != null && response.getTotalTA() != null){
							totalVoz = Double.parseDouble(response.getTotalTA());
						}
					}catch (NumberFormatException e) {
						totalCostoLlamada = 0;
						totalVoz = 0;
					}

					llamadas.setTotalLlamadas(response.getTotalTA());
					llamadas.setMinutosComunidadConsumidos(response.getTotalCom());
					llamadas.setTotalCostoLlamadas(formatNumber.format(totalCostoLlamada * totalVoz));
					llamadas.setLlamadasIncluidas(response.getDatosPlanes().getMinutos());
					llamadas.setMinutosExtras("");

					mensajes.setMensajesIncluidos(response.getDatosPlanes().getMensajes());
					mensajes.setTotalCostoMensajes(formatNumber.format(totalmsjCosto));
					mensajes.setMensajesExtras("");
					mensajes.setTotalMensajes(response.getTotalMensaje());

					navegacion.setDatosIncluidos(response.getDatosPlanes().getMegas());
					navegacion.setTotalDatos(response.getTotalDatos());
					navegacion.setTotalCostoDatos(response.getTotalDatosCosto());
					navegacion.setDatosExtra("");

					minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
					minutosTodoDestino.setMinutosTDextra("");
					minutosTodoDestino.setMinutosTDregalo("0");
					minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());

					minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
					minutosComunidad.setMinutosComunidadExtra("");
					minutosComunidad.setMinutosComunidadRegalo("0");
					minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());
					descripcionPlanes = response.getDatosPlanes();

					//Llamadas
					oracle.setConsumoXdn(dn, 1, llamadas.getMinutosDeRegalo(),
							llamadas.getMinutosExtras(), llamadas.getLlamadasIncluidas(),
							llamadas.getTotalLlamadas(), 212121);

					//Mensajes
					oracle.setConsumoXdn(dn, 2, mensajes.getMensajesDeTextoDeRegalo(),
							mensajes.getMensajesExtras(), mensajes.getMensajesIncluidos(),
							mensajes.getTotalMensajes(), 212121);

					//Navegacion
					oracle.setConsumoXdn(dn, 3, navegacion.getNavegacionDeRegalo(),
							navegacion.getDatosExtra(), navegacion.getDatosIncluidos(),
							navegacion.getTotalDatos(), 212121);

					//MinutosTD
					oracle.setConsumoXdn(dn, 4, minutosTodoDestino.getMinutosTDregalo(),
							minutosTodoDestino.getMinutosTDextra(), minutosTodoDestino.getMinutosTDincluido(),
							minutosTodoDestino.getMinutosTDconsumido(), 212121);

					//Minutos Comunidad
					oracle.setConsumoXdn(dn, 5, minutosComunidad.getMinutosComunidadRegalo(),
							minutosComunidad.getMinutosComunidadExtra(), minutosComunidad.getMinutosComunidadIncluido(),
							minutosComunidad.getMinutosComunidadConsumidos(), 212121);

					//DatosPlanes
					oracle.setDatosPlanesXdn(dn, 1, descripcionPlanes.getMegaadc(), descripcionPlanes.getMegas(),
							descripcionPlanes.getMensajeadc(), descripcionPlanes.getMensajes(), descripcionPlanes.getMinutoadc(),
							descripcionPlanes.getMinutos(), descripcionPlanes.getMinutoscomunidad(), descripcionPlanes.getNombreCortoPlan(),
							descripcionPlanes.getTiempoAire(), descripcionPlanes.getIdPlan(), 212121);

				}catch (Exception e) {
					Logger.write(" Ocurrio un detail al guardar totales en tabla: " + e.getLocalizedMessage());
					regresa = 3;
				}
			}
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] actualizaXMLTotales ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] actualizaXMLTotales ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] actualizaXMLTotales ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("actualizaXMLTotales ["+dn+"] :: " + getLocalAddress(), initime);
		}
		
		return regresa;
	}
	
	/**
	 * METODOS PARA BITACORA
	 */
	
	public DNCambioVO DNCambioRegistro(String user, String pass, String dnActual, String dnNuevo, String cveEdoCen, String cveMunCen, String cvePobCen, int compania, int sistemaOrigen, String password, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("DNCambioRegistro");

  		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
  			Logger.write("      El usuario no es valido ");
  			throw new ServiceException("usuario no valido");
  		}
  		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
  			Logger.write("      El password no es valido ");
  			throw new ServiceException("password no valido");
  		}
  		ValidaTokens tokens =  new ValidaTokens();
  		DNCambioVO cambioVo = new DNCambioVO();
  		OracleProcedures oracle = new OracleProcedures();
  		
  		
  		try{
  			
  			Logger.write("     user                   : -PROTEGIDO-");
  			Logger.write("     pass                   : -PROTEGIDO-");
  			Logger.write("     dnActual               : " + dnActual);
  			Logger.write("     dnNuevo                : " + dnNuevo);
  			Logger.write("     cveEdoCen              : " + cveEdoCen);
  			Logger.write("     cveMunCen              : " + cveMunCen);
  			Logger.write("     cvePobCen              : " + cvePobCen);
  			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
  			
  			tokens.validaToken(dnActual, token, new MensajeLogBean());
  			
  			String idIdentificador="";
  			try {
  				idIdentificador = oracle.cambioDNSET(user, pass, dnActual, dnNuevo, cveEdoCen, cveMunCen, cvePobCen, compania, sistemaOrigen, token, 2121,1, 1, 1);
  			}catch (Exception e) {
               Logger.write("Detail al ejecutar el metodo :: cambioDNSET");
			}
  			
  			int validaPwd = oracle.validarPassword(dnActual, password);
			if(validaPwd != 0){
				Logger.write("No se pudo validar el password");
				throw new ServiceException("No se pudo validar el password");
			}
  			
  			cambioVo =  DNCambio.flujo(dnActual, dnNuevo, cveEdoCen, cveMunCen, cvePobCen,idIdentificador);
  			
  			try{
  				oracle.dnCambioResponse(idIdentificador, cambioVo.getResponseCode(), cambioVo.getResponseMessage(), 2021,"","");
  			}catch (Exception e) {
				Logger.write("Detail al ejecutar al metodo :: dnCambioResponse");
			}
  			
  			Logger.write("   + Respuesta              + ");
  			Logger.write("     cambioVo ");
  			Logger.write("     responseCode           :" + cambioVo.getResponseCode());
  			Logger.write("     responseMessage        :" + cambioVo.getResponseMessage());

  		}catch(Exception e){
  			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] DNCambioRegistro ["+dnActual+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] DNCambioRegistro ["+dnActual+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] DNCambioRegistro ["+dnActual+"] :: " + e.getLocalizedMessage());
			}
  		}
  		finally{
  			Logger.end("DNCambioRegistro ["+dnActual+"] :: " + getLocalAddress(), initime);
  		}
  		return cambioVo;
  	}
	
	public DNCambioVO DNCambioBit(String user, String pass, String dnActual, String dnNuevo, String cveEdoCen, String cveMunCen, String cvePobCen, int compania, int sistemaOrigen, String password, int dispositivo,String token) throws Throwable{

  		long initime = System.currentTimeMillis();
        Logger.init("DNCambioBit");
        
  		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
  			Logger.write("      El usuario no es valido ");
  			throw new ServiceException("usuario no valido");
  		}
  		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
  			Logger.write("      El password no es valido ");
  			throw new ServiceException("password no valido");
  		}
  		ValidaTokens tokens =  new ValidaTokens();
  		DNCambioVO cambioVo = new DNCambioVO();
  		OracleProcedures oracle = new OracleProcedures();
  		
  		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
  		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
  		String sResponse = "";
  		
  		try{
  			
  			Logger.write("     user                   : -PROTEGIDO-");
  			Logger.write("     pass                   : -PROTEGIDO-");
  			Logger.write("     dnActual               : " + dnActual);
  			Logger.write("     dnNuevo                : " + dnNuevo);
  			Logger.write("     cveEdoCen              : " + cveEdoCen);
  			Logger.write("     cveMunCen              : " + cveMunCen);
  			Logger.write("     cvePobCen              : " + cvePobCen);
  			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
  			
  			tokens.validaToken(dnActual, token, new MensajeLogBean());
  			
  			String idIdentificador="";
  			try {

  				sResponse = focalizacion.focalizacion(dnActual);
  				if(sResponse != null && !sResponse.equals(""))
  					descripcion = ParseXMLFile.parseFocalizacion(sResponse);

  				int tipoTecnologia = 0;

  				if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
  					tipoTecnologia = 2;  //Etak
  				}else{
  					tipoTecnologia = 1;  //Legacy
  				}

  				int tipoLinea = 0;

  				if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
  					if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
  						tipoLinea = 2;
  					}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
  						tipoLinea = 3;
  					}
  				}else {
  					tipoLinea = 1; //prepago
  				}
  				
  				idIdentificador = oracle.cambioDNSET(user, pass, dnActual, dnNuevo, cveEdoCen, cveMunCen, cvePobCen, compania, sistemaOrigen, token, 2121,tipoLinea, tipoTecnologia, dispositivo);
  			}catch (Exception e) {
               Logger.write("Detail al identificar la linea :: cambioDNSET");
			}
  			
  			int validaPwd = oracle.validarPassword(dnActual, password);
			if(validaPwd != 0){
				throw new ServiceException("No se pudo validar el password");
			}
  			
  			cambioVo =  DNCambio.flujo(dnActual, dnNuevo, cveEdoCen, cveMunCen, cvePobCen,idIdentificador);
  			
  			try{
  				oracle.dnCambioResponse(idIdentificador, cambioVo.getResponseCode(), cambioVo.getResponseMessage(), 2021,"","");
  			}catch (Exception e) {
				Logger.write("Detail al ejecutar al metodo :: dnCambioResponse");
			}
  			
  			Logger.write("   + Respuesta              + ");
  			Logger.write("     cambioVo ");
  			Logger.write("     responseCode           :" + cambioVo.getResponseCode());
  			Logger.write("     responseMessage        :" + cambioVo.getResponseMessage());

  		}catch(Exception e){
  			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] DNCambioBit ["+dnActual+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] DNCambioBit ["+dnActual+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] DNCambioBit ["+dnActual+"] :: " + e.getLocalizedMessage());
			}
  		}
  		finally{
  			Logger.end("DNCambioBit ["+dnActual+"] :: " + getLocalAddress(), initime);
  		}
  		return cambioVo;
  	}

	public DNReactivacionVO DNSuspensionReactivacionRegistro(String user, String pass, String dn, String reason, int tipo,  int compania, int sistemaOrigen, String password, String token) throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("DNSuspensionReactivacionRegistro");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("password no valido");
		}
		ValidaTokens tokens =  new ValidaTokens();
		DNReactivacionVO reactivacionVo = new DNReactivacionVO();
		OracleProcedures oracle = new OracleProcedures();
		String idIdentificador = "";
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     reason                 : " + reason);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			try {
				idIdentificador = oracle.suspensionReactivacionSET(user, pass, dn, reason, tipo, compania, sistemaOrigen, token, 2121,1, 1, 1);
			}catch (Exception e) {
                Logger.write("Detail al consultar el metodo");
			}	
			
			int validaPwd = oracle.validarPassword(dn, password);
			if(validaPwd != 0){
				throw new ServiceException("No se pudo validar el password");
			}
			
			reactivacionVo = DNReactivacionExtendido.flujo(dn, reason, tipo,idIdentificador);
			
			if(reactivacionVo != null && !reactivacionVo.equals("")){
    			try{
					oracle.suspensionReactivacionesRES(idIdentificador, 4, reactivacionVo.getOperationCode(), reactivacionVo.getMessageCode(), 2121,"","");

				}catch (Exception e) {
					Logger.write("Detail al consultar el metodo");
				}
				
				Logger.write("   + Respuesta              + ");
				Logger.write("     reactivacionVo ");
				Logger.write("     operationCode          : " + reactivacionVo.getOperationCode());
				Logger.write("     messageCode            : " + reactivacionVo.getMessageCode());
			}

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] DNSuspensionReactivacionRegistro ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] DNSuspensionReactivacionRegistro ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] DNSuspensionReactivacionRegistro ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("DNSuspensionReactivacionRegistro ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return reactivacionVo;
	}
     
    public DNReactivacionVO DNSuspensionReactivacionBit(String user, String pass, String dn, String reason, int tipo,  int compania, int sistemaOrigen, String password, int dispositivo,String token) throws Throwable{
        long initime = System.currentTimeMillis();
        Logger.init("DNSuspensionReactivacionBit");

        if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
            Logger.write("     El usuario no es valido ");
            throw new ServiceException("usuario no valido");
        }
        if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
            Logger.write("     El password no es valido ");
            throw new ServiceException("password no valido");
        }
        ValidaTokens tokens =  new ValidaTokens();
        DNReactivacionVO reactivacionVo = new DNReactivacionVO();
        RespuestaAltaBajaServiciosVO altaBajaServiciosVO = new RespuestaAltaBajaServiciosVO();
        OracleProcedures oracle = new OracleProcedures();
        String idIdentificador = "";

        CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
        DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
        String sResponse = "";
        String validaTiempoSuspension = "";
        int tiempoSuspension = 0;

        
        try{
            
            Logger.write("     user                   : -PROTEGIDO-");
            Logger.write("     pass                   : -PROTEGIDO-");
            Logger.write("     dn                     : " + dn);
            Logger.write("     reason                 : " + reason);
            Logger.write("     dispositivo            : " + dispositivo);
            Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
            Logger.write("     remoteAddress          : " + getClientIpXfire());

            tokens.validaToken(dn, token, new MensajeLogBean());
            
            try{
                validaTiempoSuspension = oracle.getValorParametro(22);
                tiempoSuspension = Integer.parseInt(oracle.getValorParametro(23));
            }catch (ServiceException e) {
                validaTiempoSuspension = "0";
                tiempoSuspension = 5;
            }
            
            if(validaTiempoSuspension.equals("1")){
                
                List<SuspensionReactivacionVO> listSuspReac = new ArrayList<SuspensionReactivacionVO>();
                try{
                    listSuspReac = oracle.suspensionReactivacion(dn);
                }catch (Exception e) {
                    listSuspReac = new ArrayList<SuspensionReactivacionVO>();
                }
                
                if(listSuspReac != null && listSuspReac.size() > 0 && listSuspReac.get(0) != null){
                    String dato = listSuspReac.get(0).getFechaalta();   
                    Utilerias.validaTiempoDeVida(dato,tiempoSuspension);
                }
            }
            
            try {
                sResponse = focalizacion.focalizacion(dn);
                if(sResponse != null && !sResponse.equals(""))
                    descripcion = ParseXMLFile.parseFocalizacion(sResponse);

                int tipoTecnologia = 0;

                if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
                    tipoTecnologia = 2;  //Etak
                }else{
                    tipoTecnologia = 1;  //Legacy
                }

                int tipoLinea = 0;

                if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
                    if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
                        tipoLinea = 2;
                    }else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
                        tipoLinea = 3;
                    }
                }else {
                    tipoLinea = 1; //prepago
                }

                idIdentificador = oracle.suspensionReactivacionSET(user, pass, dn, reason, tipo, compania, sistemaOrigen, token, 2121,tipoLinea, tipoTecnologia, dispositivo);
            }catch (Exception e) {
                Logger.write("Detail al consultar el metodo");
            }   
            
            int validaPwd = oracle.validarPassword(dn, password);
            if(validaPwd != 0){
                throw new ServiceException("No se pudo validar el password");
            }
            
            reactivacionVo = DNReactivacionExtendido.flujoBSCS(dn, reason, tipo,idIdentificador);
            
            if(reactivacionVo != null && !reactivacionVo.getMessageCode().equalsIgnoreCase("")){
                try{
                  oracle.suspensionReactivacionesRES(idIdentificador, 4, reactivacionVo.getOperationCode(), altaBajaServiciosVO.getMsgError(), 2121,"","");

              }catch (Exception e) {
                  Logger.write("Detail al consultar el metodo");
              }
              
              if(reactivacionVo.getOperationCode().equalsIgnoreCase("1")){
                  reactivacionVo.setOperationCode("0");
              }else if(reactivacionVo.getOperationCode().equalsIgnoreCase("0") || reactivacionVo.getOperationCode().equalsIgnoreCase("")){
                  reactivacionVo.setOperationCode("1");
              }
            }
            Logger.write("   + Respuesta              + ");
            Logger.write("     reactivacionVo ");
            Logger.write("     operationCode          : " + reactivacionVo.getOperationCode());
            Logger.write("     messageCode            : " + reactivacionVo.getMessageCode());

        }catch(Exception e){
            if(e != null && e.getLocalizedMessage() != null){
                if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("Debe esperar")){
                    Logger.write("[WARN] DNSuspensionReactivacionBit ["+dn+"] :: (Exception)" + e.getLocalizedMessage());
                    String mensajeOracle = "";
                    try{
                        mensajeOracle =  e.getLocalizedMessage().replace("ORA-20000: PAMIIGETLOGIN_RL.FNGETMIITAVALIDAPASSWORD  Oops! ", "").split("\\n")[0];
                    }catch(Exception exc){
                        mensajeOracle =  e.getLocalizedMessage().replace("ORA-20000: PAMIIGETLOGIN_RL.FNGETMIITAVALIDAPASSWORD  Oops! ", "");
                    }
                    throw new ServiceException(mensajeOracle);
                }else{
                    Logger.write("[ERR] DNSuspensionReactivacionBit ["+dn+"] :: (Exception)" + e.getLocalizedMessage());
                    throw new ServiceException(e.getLocalizedMessage());
                }
            }else{
                Logger.write("[ERR] DNSuspensionReactivacionBit ["+dn+"] :: (Exception)" + e.getLocalizedMessage());
                throw new ServiceException(e.getLocalizedMessage());
            }
        }
        finally{
            Logger.end("DNSuspensionReactivacionBit ["+dn+"] :: " + getLocalAddress(), initime);
        }
        return reactivacionVo;
    }
    
 	public ContratarServiciosVO contratarServiciosRegistro(String user, String pass, String dn, AltaServicioEtakVO datosAltaEtak, AltaServicioLegacyVO datosAlta, AltaServicioPrepagoLegacyVO datosAltaPrepago,  int compania, int sistemaOrigen,String token) throws Throwable{
		
 		long initime = System.currentTimeMillis();
        Logger.init("contratarServiciosRegistro");
		
 		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("       El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}
		ValidaTokens tokens =  new ValidaTokens();
		ContratarServiciosVO RespContratarServicio = new ContratarServiciosVO();
		OracleProcedures oracle = new OracleProcedures();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     DN                     : " + dn);
			Logger.write("     DatosAltaETAK          : " );
			if(datosAltaEtak != null){
				Logger.write("     Costo                  : " + datosAltaEtak.getCosto());
				Logger.write("     Id                     : " + datosAltaEtak.getId());
				Logger.write("     IdHistorico            : " + datosAltaEtak.getIdHistorico());
				Logger.write("     Monto                  : " + datosAltaEtak.getMonto());
				Logger.write("     Vigencia               : " + datosAltaEtak.getVigencia());
			}
			Logger.write("     DatosAltaLEGACY        : " );
			if(datosAlta != null){
				Logger.write("     ContinenteFavortio     : " + datosAlta.getContinenteFavortio());
				Logger.write("     DnUsa                  : " + datosAlta.getDnUsa());
				Logger.write("     Servicios              : " + datosAlta.getServicios());
			}else{
				datosAlta = new AltaServicioLegacyVO();
			}
			if(datosAltaPrepago != null){
				Logger.write("     Operacion              : " + datosAltaPrepago.getOperacion());
				Logger.write("     ServicioOrigen         : " + datosAltaPrepago.getServicioOrigen());
				Logger.write("     ServiciosId            : " + datosAltaPrepago.getServiciosId());
				Logger.write("     VigenciasCantidad      : " + datosAltaPrepago.getVigenciasCantidad());
				Logger.write("     VigenciasUnidad        : " + datosAltaPrepago.getVigenciasUnidad());
			}
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
		
			tokens.validaToken("", token, new MensajeLogBean());

			String respuesta = "0";
			try{
				respuesta =	oracle.contratarServiciosSET(user, pass, dn, datosAltaEtak.getCosto(), datosAltaEtak.getId(), datosAltaEtak.getIdHistorico(), datosAltaEtak.getVigencia(), datosAltaEtak.getMonto(), datosAlta.getDnUsa(), datosAlta.getContinenteFavortio(), datosAlta.getServicios(), datosAltaPrepago.getServiciosId(), datosAltaPrepago.getServicioOrigen(), datosAltaPrepago.getVigenciasUnidad(), datosAltaPrepago.getVigenciasCantidad(), datosAltaPrepago.getOperacion(), compania, sistemaOrigen, token, 2021,1, 1, 1);
			}catch (Exception e) {
				Logger.write("Detail en el metodo de respuesta contratarServicios :: contratarServiciosSET");
			}
			
    	    RespContratarServicio = AltaServicioXUsuExt.flujo(dn, datosAltaEtak, datosAlta, datosAltaPrepago, respuesta);
			 
    	    if(RespContratarServicio != null && !RespContratarServicio.equals("")){
				try {
					oracle.compraServicioRespuesta(respuesta, 4, RespContratarServicio.getMessageCode(),RespContratarServicio.getOperationCode() , 2121,"","");							    
				}catch (Exception e) {
                    Logger.write("Detail a ejecutar el metodo :: compraServicioRespuesta");
				}
		    }
    	    
			Logger.write("   + Respuesta              + ");
			Logger.write("     MessageCode            : " + RespContratarServicio.getMessageCode());
			Logger.write("     OperationCode          : " + RespContratarServicio.getOperationCode());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] contratarServiciosRegistro ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] contratarServiciosRegistro ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] contratarServiciosRegistro ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("contratarServiciosRegistro ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return RespContratarServicio;
	}
 	
 	public ContratarServiciosVO contratarServiciosBit(String user, String pass, String dn, AltaServicioEtakVO datosAltaEtak, AltaServicioLegacyVO datosAlta, AltaServicioPrepagoLegacyVO datosAltaPrepago,  int compania, int sistemaOrigen, int dispositivo,String token) throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("contratarServiciosBit");
	
 		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("       El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}
		ValidaTokens tokens =  new ValidaTokens();
		ContratarServiciosVO RespContratarServicio = new ContratarServiciosVO();
		OracleProcedures oracle = new OracleProcedures();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		String sResponse = "";
		List<ServiciosAdicionalesVO> resServAdicionales = new ArrayList<ServiciosAdicionalesVO>();
		String idServicio="";
		String costoServicio="0";

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     DN                     : " + dn);
			Logger.write("     DatosAltaETAK          : " );
			if(datosAltaEtak != null){
				Logger.write("     Costo                  : " + datosAltaEtak.getCosto());
				Logger.write("     Id                     : " + datosAltaEtak.getId());
				Logger.write("     IdHistorico            : " + datosAltaEtak.getIdHistorico());
				Logger.write("     Monto                  : " + datosAltaEtak.getMonto());
				Logger.write("     Vigencia               : " + datosAltaEtak.getVigencia());
			}
			Logger.write("     DatosAltaLEGACY        : " );
			if(datosAlta != null){
				Logger.write("     ContinenteFavortio     : " + datosAlta.getContinenteFavortio());
				Logger.write("     DnUsa                  : " + datosAlta.getDnUsa());
				Logger.write("     Servicios              : " + datosAlta.getServicios());
			}else{
				datosAlta = new AltaServicioLegacyVO();
			}
			if(datosAltaPrepago != null){
				Logger.write("     Operacion              : " + datosAltaPrepago.getOperacion());
				Logger.write("     ServicioOrigen         : " + datosAltaPrepago.getServicioOrigen());
				Logger.write("     ServiciosId            : " + datosAltaPrepago.getServiciosId());
				Logger.write("     VigenciasCantidad      : " + datosAltaPrepago.getVigenciasCantidad());
				Logger.write("     VigenciasUnidad        : " + datosAltaPrepago.getVigenciasUnidad());
			}
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			

			tokens.validaToken(dn, token, new MensajeLogBean());

			String respuesta = "0";
			try{
				sResponse = focalizacion.focalizacion(dn);
				if(sResponse != null && !sResponse.equals(""))
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);

				int tipoTecnologia = 0;

				if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
					tipoTecnologia = 2;  //Etak
				}else{
					tipoTecnologia = 1;  //Legacy
				}

				int tipoLinea = 0;

				if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
					if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
						tipoLinea = 2;
					}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
						tipoLinea = 3;
					}
				}else {
					tipoLinea = 1; //prepago
				}
				
				resServAdicionales = GetServiciosAdicionales.flujoPorDn(0,dn);
				
				if(tipoTecnologia == 1){ //legacy
					if(tipoLinea == 1){ //prepago
						idServicio = datosAltaPrepago.getServiciosId();
					}else if(tipoLinea == 3)//hibrido
						idServicio = datosAlta.getServicios();
				}else if(tipoTecnologia == 2){ //Etak
					idServicio =datosAltaEtak.getId();
				}
				
				for(int a=0; a<resServAdicionales.size(); a++){
					if(idServicio.equals(resServAdicionales.get(a).getIdServicio())){
						costoServicio = resServAdicionales.get(a).getCosto();
						break;
					}
				}

				respuesta =	oracle.contratarServiciosSET(user, pass, dn, costoServicio, datosAltaEtak.getId(), datosAltaEtak.getIdHistorico(), datosAltaEtak.getVigencia(), datosAltaEtak.getMonto(), datosAlta.getDnUsa(), datosAlta.getContinenteFavortio(), datosAlta.getServicios(), datosAltaPrepago.getServiciosId(), datosAltaPrepago.getServicioOrigen(), datosAltaPrepago.getVigenciasUnidad(), datosAltaPrepago.getVigenciasCantidad(), datosAltaPrepago.getOperacion(), compania, sistemaOrigen, token, 2021,tipoLinea, tipoTecnologia, dispositivo);
			}catch (Exception e) {
				Logger.write("Detail en el metodo de respuesta contratarServicios");
			}
			
    	    RespContratarServicio = AltaServicioXUsuExt.flujo(dn, datosAltaEtak, datosAlta, datosAltaPrepago, respuesta);
			 
    	    if(RespContratarServicio != null && !RespContratarServicio.equals("")){
				try {
					oracle.compraServicioRespuesta(respuesta, 4, RespContratarServicio.getMessageCode(),RespContratarServicio.getOperationCode() , 2121,"","");							    
				}catch (Exception e) {
                    Logger.write("Detail a ejecutar el metodo :: compraServicioRespuesta");
				}
		    }
    	    
			Logger.write("   + Respuesta              + ");
			Logger.write("     MessageCode            : " + RespContratarServicio.getMessageCode());
			Logger.write("     OperationCode          : " + RespContratarServicio.getOperationCode());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] contratarServiciosBit ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] contratarServiciosBit ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] contratarServiciosBit ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("contratarServiciosBit ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return RespContratarServicio;
	}
    
 	public AbonoTiempoAireVO abonoTiempoAireRegistro(String user,String pass, String dn, String dnParaAbono, int anioExpira, String cdgSeguridad, String concepto, Double importe, int mesExpira, String numTarjeta, String tipoTarjeta, String ip, Long secuencia, String password, int tipoPlataforma, int compania, int sistemaOrigen, String token) throws Throwable{
 		
 		long initime = System.currentTimeMillis();
        Logger.init("abonoTiempoAireRegistro");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el usuario no es valido " + user.trim());
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el password no es valido " + user.trim());
			throw new ServiceException("password no valido");
		}
		
		ValidatorCharges.validaCantidadTA(importe);

		AbonoTiempoAireVO tiempoAireVO = new AbonoTiempoAireVO();
		ValidaTokens tokens =  new ValidaTokens();
		OracleProcedures oracle = new OracleProcedures();
		String respuesta = "";
		

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     DN                     : "+ dn);
			Logger.write("     dnParaAbono            : "+ dnParaAbono);
			Logger.write("     Anio Expira            : "+ anioExpira);
			Logger.write("     Cdg Seguridad          : "+ cdgSeguridad);
			Logger.write("     Concepto               : "+ concepto);
			Logger.write("     Importe                : "+ importe);
			Logger.write("     Mes Expira             : "+ mesExpira);
			Logger.write("     Num Tarjeta            : "+ numTarjeta);
			Logger.write("     Tipo Tarjeta           : "+ tipoTarjeta);
			Logger.write("     Ip                     : "+ ip);
			Logger.write("     Secuencia              : "+ secuencia);
			Logger.write("     Password               : "+ password);
			Logger.write("     Tipo Plataforma        : "+ tipoPlataforma);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken("", token, new MensajeLogBean());
			ip = "10.189.64.45";
			try{
				respuesta = oracle.abonoTiempoAire(user, pass, dn,  dnParaAbono, anioExpira, cdgSeguridad, concepto, importe,mesExpira, numTarjeta, tipoTarjeta, ip,  secuencia, password, tipoPlataforma, compania, sistemaOrigen, 1, 1, 1, token, 2021);
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
			AbonoTiempoAireIn abonoTA = null;
			String autorizadorDirecto = oracle.getValorParametro(208);
			if(autorizadorDirecto.equals("1")){
				abonoTA = new AbonoTiempoAireAutorizador();
			}else{
				abonoTA = new AbonoTiempoAire();
			}
			tiempoAireVO = abonoTA.flujo(token, dn, dnParaAbono, anioExpira, cdgSeguridad, concepto, importe, mesExpira, numTarjeta, tipoTarjeta, ip, secuencia, password, tipoPlataforma, respuesta);
			
			try{
				if(tiempoAireVO != null)
					oracle.abonoTAAutorizador(respuesta, tiempoAireVO.getCodigoRespuestaAbonoTA(), tiempoAireVO.getNumeroAutorizacionAbonoTA(), tiempoAireVO.getNumTransaccionCaja(), tiempoAireVO.getNumeroAutorizacionCaja(), tiempoAireVO.getIdRegistroCaja(), 2121,"","");
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}

		}catch(Exception e){
			try{
				oracle.abonoTAAutorizador(respuesta, "", "", "", "", 0, 2121,"1",e.getMessage());
			}catch (Exception ex) {
				Logger.write("Bitacora: No se impacto la bitacora - " + ex.getLocalizedMessage());
			}
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("cardBlackList") || e.getLocalizedMessage().contains("TransXTarjeta")
						||e.getLocalizedMessage().contains("SemaphoreApplyCharge")||e.getLocalizedMessage().contains("[ctrl]")){
					throw new ServiceException("[WARN] abonoTiempoAireRegistro ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] abonoTiempoAireRegistro ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] abonoTiempoAireRegistro ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("abonoTiempoAireRegistro ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return tiempoAireVO;
	}
 	
 	public AbonoTiempoAireVO abonoTiempoAireBit(String user,String pass, String dn, String dnParaAbono, int anioExpira, String cdgSeguridad, String concepto, Double importe, int mesExpira, String numTarjeta, String tipoTarjeta, String ip, Long secuencia, String password, int tipoPlataforma, int compania, int sistemaOrigen, int dispositivo, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("abonoTiempoAireBit");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el usuario no es valido " + user.trim());
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write(" el password no es valido " + user.trim());
			throw new ServiceException("password no valido");
		}
		
		ValidatorCharges.validaCantidadTA(importe);

		AbonoTiempoAireVO tiempoAireVO = new AbonoTiempoAireVO();
		ValidaTokens tokens =  new ValidaTokens();
		OracleProcedures oracle = new OracleProcedures();
		String respuesta = "";

		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		String sResponse = "";
				
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     DN                     : "+ dn);
			Logger.write("     dnParaAbono            : "+ dnParaAbono);
			Logger.write("     Anio Expira            : "+ anioExpira);
			Logger.write("     Cdg Seguridad          : xxx");
			Logger.write("     Concepto               : "+ concepto);
			Logger.write("     Importe                : "+ importe);
			Logger.write("     Mes Expira             : "+ mesExpira);
			Logger.write("     Num Tarjeta            : "+ numTarjeta);
			Logger.write("     Tipo Tarjeta           : "+ tipoTarjeta);
			Logger.write("     Ip                     : "+ ip);
			Logger.write("     Secuencia              : "+ secuencia);
			Logger.write("     Password               : "+ password);
			Logger.write("     Tipo Plataforma        : "+ tipoPlataforma);
			Logger.write("     compania               : "+ compania);
			Logger.write("     sistemaOrigen          : "+ sistemaOrigen);
			Logger.write("     Dispositivo            : "+ dispositivo);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());

			ip = "10.189.64.45";
			int tipoTecnologia = 0;
			int tipoLinea = 0;
			try{				
				sResponse = focalizacion.focalizacion(dn);
				if(sResponse != null && !sResponse.equals(""))
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);


				if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
					tipoTecnologia = 2;  //Etak
				}else{
					tipoTecnologia = 1;  //Legacy
				}

				if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
					if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
						tipoLinea = 2;
					}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
						tipoLinea = 3;
					}
				}else {
					tipoLinea = 1; //prepago
				}
			}catch (Exception e) {
				Logger.write("focalizacion error - " + e.getLocalizedMessage());
			}
			try{
				respuesta = oracle.abonoTiempoAire(user, pass, dn,  dnParaAbono, anioExpira, cdgSeguridad, concepto, importe,mesExpira, numTarjeta, tipoTarjeta, ip,  secuencia, password, tipoPlataforma, compania, sistemaOrigen, tipoLinea, tipoTecnologia, dispositivo, token, 2021);
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}

			AbonoTiempoAireIn abonoTA = null;
			String autorizadorDirecto = oracle.getValorParametro(208);
			if(autorizadorDirecto.equals("1")){
				abonoTA = new AbonoTiempoAireAutorizador();
			}else{
				abonoTA = new AbonoTiempoAire();
			}
			tiempoAireVO = abonoTA.flujo(token, dn, dnParaAbono, anioExpira, cdgSeguridad, concepto, importe, mesExpira, numTarjeta, tipoTarjeta, ip, secuencia, password, tipoPlataforma, respuesta);
			
			try{
				if(tiempoAireVO != null)
					oracle.abonoTAAutorizador(respuesta, tiempoAireVO.getCodigoRespuestaAbonoTA(), tiempoAireVO.getNumeroAutorizacionAbonoTA(), tiempoAireVO.getNumTransaccionCaja(), tiempoAireVO.getNumeroAutorizacionCaja(), tiempoAireVO.getIdRegistroCaja(), 2121,"","");
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}

		}catch(Exception e){
			try{
				oracle.abonoTAAutorizador(respuesta, "", "", "", "", 0, 2121,"1",e.getMessage());
			}catch (Exception ex) {
				Logger.write("Bitacora: No se impacto la bitacora - ");
			}
			
			if(e != null && e.getMessage() != null){
				if(e.getMessage().contains("ORA-20000") || e.getMessage().contains("Err [Caja]") || e.getLocalizedMessage().contains("cardBlackList") 
						|| e.getLocalizedMessage().contains("TransXTarjeta")||e.getLocalizedMessage().contains("SemaphoreApplyCharge")
						|| e.getLocalizedMessage().contains("[ctrl]")){
					throw new ServiceException("[WARN] abonoTiempoAireBit ["+dn+"] " + e.getMessage());
				}else{
					throw new ServiceException("[ERR] abonoTiempoAireBit ["+dn+"] " + e.getMessage());
				}
			}else{
				throw new ServiceException("[ERR] abonoTiempoAireBit ["+dn+"] " + e.getMessage());
			}
		}
		finally{
			Logger.end("abonoTiempoAireBit ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return tiempoAireVO;
	}
	
 	public ConsultaOperadorExtendidoVO consultaOperadorExtendido(String user, String pass, String dn, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("consultaOperadorExtendido");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		CallServiceFocalizacion datosfocalizacion = new CallServiceFocalizacion();
		ConsultaOperadorExtendidoVO response = new ConsultaOperadorExtendidoVO();
		DetalleFocalizacionVO respuestaFoca = new DetalleFocalizacionVO();
		String sResponse = "";
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean(""));
			
			sResponse = datosfocalizacion.focalizacion(dn);
			if(sResponse != null && !sResponse.equals(""))
				respuestaFoca = ParseXMLFile.parseFocalizacion(sResponse);

			String idOperador = respuestaFoca.getIdOperador();
			if(idOperador != null && idOperador.equals("190"))
				idOperador = "5";
			
			response.setIdOperador(idOperador);
			
			if(respuestaFoca != null && respuestaFoca.getServicio() != null && respuestaFoca.getSubservicio() != null && !respuestaFoca.getServicio().equals("0") && !respuestaFoca.getSubservicio().equals("0")){
				if(respuestaFoca.getServicio().equals("80") && respuestaFoca.getServicio().equals("80")){
					String status = ParseXMLFile.parseStatusFocalizacionEtak(sResponse);
					if(status != null && !status.equals("")){
						if(status.toLowerCase().equals("r1") || status.toLowerCase().equals("r2") || status.toLowerCase().equals("r0"))
							response.setStatusLinea("1");
						else
							response.setStatusLinea("2");
					}
				}else{
					String status = ParseXMLFile.parseStatusFocalizacion(sResponse);
					if(status != null && !status.equals("")){
						if(status.equals("d"))
							response.setStatusLinea("1");
						else if(status.equals("a"))
							response.setStatusLinea("2");
					}
				}
			}
			
			Logger.write("   + Respuesta              + ");
			if(respuestaFoca != null)
				Logger.write("     respuesta.idOperador   : " + response.getIdOperador());
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] consultaOperadorExtendido ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] consultaOperadorExtendido ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] consultaOperadorExtendido ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("consultaOperadorExtendido ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return response;
	}
 	
 	public ConsultaParametrosVO getConsultaParametro(String user, String pass,int parametro, String token) throws Throwable
	{
 		long initime = System.currentTimeMillis();
        Logger.init("getConsultaParametro");


		if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido");
			throw new ServiceException("usuario no valido");
		}
		if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido");
			throw new ServiceException("password no valido");
		}	
			
		ValidaTokens tokens =  new ValidaTokens();	
		OracleProcedures oracleProcedures = new OracleProcedures();
		ConsultaParametrosVO parametros = new ConsultaParametrosVO();
		
		try {
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     parametro              : " + parametro);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(parametro+"", token, new MensajeLogBean());	
			
			parametros = oracleProcedures.getConsultaParametro(parametro);

			Logger.write("   + Respuesta              + ");
			Logger.write("     Id                     : " + parametros.getId());
			Logger.write("     Descripcion            : " + parametros.getDescripcion());
			Logger.write("     Valor                  : " + parametros.getValor());
		}
		catch (Exception e) {
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] getConsultaParametro ["+parametro+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] getConsultaParametro ["+parametro+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] getConsultaParametro ["+parametro+"] :: " + e.getLocalizedMessage());
			}			
		} finally {
			Logger.end("getConsultaParametro ["+parametro+"] :: " + getLocalAddress(), initime);
		}

		return parametros;
	}
 	
 	public AltaCitaVO altaCitaBit (String user, String pass, int cveHora, String fecha, String tienda, String suscriptor, String nombre, String apPaterno, String apMaterno, String correo, String comentario, int tipoAtencion, int cveGenerdor, int compania, int sistemaOrigen,String dn, int dispositivo, String token)  throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("altaCitaBit");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		AltaCitaVO altacitaVo = new AltaCitaVO();
		OracleProcedures oracle = new OracleProcedures();
		
		String logId="";
		
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		String sResponse = "";
		
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : "+ dn);
			Logger.write("     cveHora                : "+ cveHora);
			Logger.write("     fecha                  : "+ fecha);
			Logger.write("     tienda                 : "+ tienda);
			Logger.write("     suscriptor             : "+suscriptor);
			Logger.write("     nombre                 : "+nombre);
			Logger.write("     apPaterno              : "+apPaterno);
			Logger.write("     apMaterno              : "+apMaterno);
			Logger.write("     correo                 : "+correo);
			Logger.write("     comentario             : "+comentario);
			Logger.write("     tipoAtencio            : "+tipoAtencion);
			Logger.write("     cveGenerdor            : "+cveGenerdor);
			Logger.write("     Dispositivo            : "+dispositivo);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken("", token, new MensajeLogBean());
			
			try{
				
				sResponse = focalizacion.focalizacion(dn);
				if(sResponse != null && !sResponse.equals(""))
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);
				
			int tipoTecnologia = 0;

			if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
			  tipoTecnologia = 2;  //Etak
			}else{
			  tipoTecnologia = 1;  //Legacy
			}

			int tipoLinea = 0;

			if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
			   if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
			     tipoLinea = 2;
			   }else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
			     tipoLinea = 3;
			   }
			}else {
			   tipoLinea = 1; //prepago
			}
				logId = oracle.altaCita(user,pass, cveHora,fecha,tienda, suscriptor,nombre,apPaterno,apMaterno, correo, comentario, tipoAtencion,cveGenerdor, compania, sistemaOrigen,token,0,tipoLinea, tipoTecnologia, dispositivo);
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
			
			int empresa = 0;
			try{
				
				empresa = oracle.obtieneEmpresa(suscriptor);
			}catch (Exception e) {
				empresa=1;
			}
			

			altacitaVo = AltaCita.flujo("O7k13t9t9TbMTd3[mJN]uj39", "O7k13t9t9TbMTd3[mJN]uj39", cveHora, fecha, tienda, suscriptor, nombre, apPaterno, apMaterno, correo, comentario, tipoAtencion, cveGenerdor, empresa, logId);
			
			Logger.write("   + Respuesta              + ");	
			Logger.write("     exito                  : " +altacitaVo.getExito());

		}catch(Exception e){
			try{
				oracle.altaCitaResponse(logId, "","", "1", e.getMessage(), 1);
			}catch (Exception ex) {
				Logger.write("Bitacora: No se impacto la bitacora - " + ex.getLocalizedMessage());
			}
			
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("ORA-20008")){
					throw new ServiceException("[WARN] altaCitaBit ["+dn+"] " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] altaCitaBit ["+dn+"] " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] altaCitaBit ["+dn+"] " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("altaCitaBit ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return altacitaVo;
	}
 	
 	public ReprogramacionVO reprogramacionBit (String user, String pass, String tecnologia, String t_msisdn, int compania, int sistemaOrigen, int dispositivo,String token) throws Throwable 
	{
 		long initime = System.currentTimeMillis();
        Logger.init("reprogramacionBit");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		ReprogramacionVO reprogramacionVo = new ReprogramacionVO();
		//Nuevo
		OracleProcedures oracle = new OracleProcedures();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		String sResponse="";
		String idIdentificador = "";
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     tecnologia             : " + tecnologia);
			Logger.write("     t_msisdn               : " + t_msisdn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(t_msisdn, token, new MensajeLogBean());
			
			//Nuevo
			try {
				if(t_msisdn == null || t_msisdn.trim().equals(""))
					throw new ServiceException("DN no puede ir vacio");
				
				sResponse = focalizacion.focalizacion(t_msisdn);
				if(sResponse != null && !sResponse.equals(""))
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);

				int tipoTecnologia = 0;

				if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
					tipoTecnologia = 2;  //Etak
				}else{
					tipoTecnologia = 1;  //Legacy
				}

				int tipoLinea = 0;

				if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
					if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
						tipoLinea = 2;
					}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
						tipoLinea = 3;
					}
				}else {
					tipoLinea = 1; //prepago
				}

				idIdentificador = oracle.suspensionReactivacionSET(user, pass, t_msisdn, "Reprogramacion", 5, compania, sistemaOrigen, token, 2121,tipoLinea, tipoTecnologia, dispositivo);
			}catch (Exception e) {
                Logger.write("Detail al consultar el metodo :: suspensionReactivacionSET");
			}	
			

			reprogramacionVo = Reprogramacion.flujo(tecnologia, t_msisdn);

			Logger.write("   + Respuesta              + ");
			Logger.write("     reprogramacionVo       : ");
			Logger.write("     codigoRespuesta        : " + reprogramacionVo.getCodigoRespuesta());
			Logger.write("     dn                     : " + reprogramacionVo.getDn());
			Logger.write("     mensaje                : " + reprogramacionVo.getMensaje());

		}catch(Exception e){
			throw new ServiceException("[ERR] reprogramacionBit ["+t_msisdn+"] :: " + e.getLocalizedMessage());
		}
		finally{
    			try{
					oracle.suspensionReactivacionesRES(idIdentificador, 4, reprogramacionVo.getCodigoRespuesta(), reprogramacionVo.getMensaje(), 2121,"","");
				}catch (Exception e) {
					Logger.write("Detail al consultar el metodo :: suspensionReactivacionesRES");
				}
				Logger.end("reprogramacionBit ["+t_msisdn+"] :: " + getLocalAddress(), initime);
			
		}
		return reprogramacionVo;	
	}
 	
 	/**------------------------------------------------------------------------------------------------
	   * 
	   * FUNCIONES PARA INDICADORES
	   * 
	   * ----------------------------------------------------------------------------------------------*/
 	public CatalogoIndicadoresVO indicadores(String user, String pass, ConsultaIndicadores input, String token) throws Throwable{

 		long initime = System.currentTimeMillis();
 		Logger.init("indicadores", input.getUnidadNegocio());
 	

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		CatalogoIndicadoresVO response = new CatalogoIndicadoresVO();
		
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			if(input != null){
				Logger.write("     ReporteId              : "+ input.getReporteId());
				Logger.write("     UnidadNegocio          : "+ input.getUnidadNegocio());
				Logger.write("     FechaIni               : "+ input.getFechaIni());
				Logger.write("     FechaFin               : "+ input.getFechaFin());
			}
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(input.getReporteId()+"", token, new MensajeLogBean());
			
			response = Indicadores.flujo(input);
			
			Logger.write("   + Respuesta              + ");

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] indicadores :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] indicadores :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] indicadores :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("indicadores :: " + getLocalAddress(), initime);

		}
		return response;
	}
 	
 	
 	public boolean registroApp (String user, String pass, List<ErrorVO> registroError,  String token) throws Throwable{
 		long initime = System.currentTimeMillis();
        Logger.init("registroApp");


 		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
 			throw new ServiceException("[ALE] usuario no valido");
 		}
 		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
 			throw new ServiceException("[ALE] password no válido");
 		}
 		OracleProcedures oracleProcedures = new OracleProcedures();
 		ValidaTokens tokens =  new ValidaTokens();
 		boolean respuesta = false;
 		
 		int longitud = registroError.size();
 		try{
 			Logger.write("      user                  : -PROTEGIDO-");
 			Logger.write("      pass                  : -PROTEGIDO-");
            for (int i=0; i<longitud; i++){
	 			Logger.write("     DN                     : " + registroError.get(i).getDN());
	 			Logger.write("     fechaEr_ror            : " + registroError.get(i).getFechaError());
	 			Logger.write("     compania               : " + registroError.get(i).getCompania());
	 			Logger.write("     sistemaOrigen          : " + registroError.get(i).getSistemaOrigen());
	 			Logger.write("     tipoLinea              : " + registroError.get(i).getTipoLinea());
	 			Logger.write("     tipoDispositivo        : " + registroError.get(i).getTipoDispositivo());
	 			Logger.write("     versionSistema         : " + registroError.get(i).getVersionSistema());
	 			Logger.write("     tipoEr_ror             : " + registroError.get(i).getTipoError());
	 			Logger.write("     descEr_ror             : " + registroError.get(i).getDescError());
	 			Logger.write("     metodoApp              : " + registroError.get(i).getMetodoApp());
	 			Logger.write("     metodoServicioWeb      : " + registroError.get(i).getMetodoServicioWeb());
            }
            Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
            Logger.write("     remoteAddress          : " + getClientIpXfire());

 			tokens.validaToken(registroError.get(0).getDN(), token,  new MensajeLogBean());
 			for (int j=0; j<longitud; j++){
	 			respuesta = oracleProcedures.erroresApp(registroError.get(j).getDN(), registroError.get(j).getFechaError(), registroError.get(j).getCompania(), registroError.get(j).getSistemaOrigen(), registroError.get(j).getTipoLinea(), registroError.get(j).getTipoDispositivo(), registroError.get(j).getVersionSistema(), registroError.get(j).getTipoError(), registroError.get(j).getDescError(), registroError.get(j).getMetodoApp(), registroError.get(j).getMetodoServicioWeb());
 			}
 		}catch(Exception e){
 			if(e != null && e.getLocalizedMessage() != null){
 				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] registroApp :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] registroApp :: " + e.getLocalizedMessage());
				}
 			}else{
				throw new ServiceException("[ERR] registroApp :: " + e.getLocalizedMessage());
			}
 		}
 		finally{
 			Logger.end("registroApp :: " + getLocalAddress(), initime);
 		}

 		return respuesta;
 	}
 	
 	public DatosFacturasVO getFacturas(String user, String pass, String dn, String noFacturas, String token) throws Throwable{
 		long initime = System.currentTimeMillis();
        Logger.init("getFacturas");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DatosFacturasVO response = new DatosFacturasVO();
		List<DatosUltimasFacturasVO> facturas = new ArrayList<DatosUltimasFacturasVO>();
		OracleProcedures oracle = new OracleProcedures();
		String consultaSaldoPendiente = "";
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token,  new MensajeLogBean());

			try{
				consultaSaldoPendiente = oracle.getValorParametro(117);
			}catch (ServiceException e) {
				consultaSaldoPendiente = "0";
			}
			
			try{
			facturas = ObtieneUltimasFacturas.flujo(dn,noFacturas);
			}catch (Exception e) {
				facturas = new ArrayList<DatosUltimasFacturasVO>();
			}
			
			if(consultaSaldoPendiente.equals("1")){
				try{
					response.setSaldoPendienteDePago(ObtieneSaldoAPagar.flujo(dn));
				}catch (Exception e) {
					response.setSaldoPendienteDePago("");
				}
			}
			
			if(facturas != null){
				response.setFacturas(facturas);
				Logger.write("   + Respuesta              + ");
				Logger.write("     respuesta.size         : " + facturas.size());
			}

		}catch(Exception e){
			throw new ServiceException("[ERR] getFacturas ["+dn+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("getFacturas ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return response;
 	}
 	
 	public PagoFacturaResponseVO pagarFactura(String user, String pass, String dn, CardVO tarjeta, int tipoPlataforma, int compania, int sistemaOrigen, int dispositivo, String password, String token) throws Throwable{
 		long initime = System.currentTimeMillis();
        Logger.init("pagarFactura");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		OracleProcedures oracle = new OracleProcedures();
		String sResponse = "";
		PagoFacturaResponseVO response = new PagoFacturaResponseVO();
		String numTx = "";
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     tipoPlataforma         : " + tipoPlataforma);
			Logger.write("     compania               : " + compania);
			Logger.write("     sistemaOrigen          : " + sistemaOrigen);
			Logger.write("     dispositivo            : " + dispositivo);
			if(tarjeta != null){
				Logger.write("     getAmount              : " + tarjeta.getAmount());
				Logger.write("     getCardExpiryMonth     : " + tarjeta.getCardExpiryMonth());
				Logger.write("     getCardExpiryYear      : " + tarjeta.getCardExpiryYear());
				Logger.write("     getCardNumber          : " + tarjeta.getCardNumber());
				Logger.write("     getCardSecurityCode    : " + tarjeta.getCardSecurityCode());
			}
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token,  new MensajeLogBean());
			
			int validaPwd = oracle.validarPassword(dn, password);
			if(validaPwd != 0){
				throw new ServiceException("No se pudo validar el password");
			}
			
			try{
				sResponse = focalizacion.focalizacion(dn);
				if(sResponse != null && !sResponse.equals(""))
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);
				int tipoTecnologia = 0;
				if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
					tipoTecnologia = 2;
				}else{
					tipoTecnologia = 1;
				}
				int tipoLinea = 0;
				if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
					if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
						tipoLinea = 2;
					}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
						tipoLinea = 3;
					}
				}else {
					tipoLinea = 1;
				}
				//bitacora
				try{
					numTx = oracle.pagoFacturaEntrada(user, pass, dn, tarjeta, tipoPlataforma, compania, sistemaOrigen, dispositivo, tipoLinea, tipoTecnologia, "", token);
				}catch (Exception e) {
					throw new ServiceException("Numero de transaccion invalido");
				}
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
			PagarFacturain pagarFactura = null;
			String autorizadorDirecto = oracle.getValorParametro(208);
			if(autorizadorDirecto.equals("1")){
				pagarFactura = new PagarFacturaAutorizador();
			}else{
				pagarFactura = new PagarFactura();
			}
			response = pagarFactura.flujo(numTx, user, pass, dn, tarjeta, tipoPlataforma, compania, sistemaOrigen, dispositivo, password, token);
			
			if(response != null){
				Logger.write("   + Respuesta              + ");
				Logger.write("     respuesta.FolioPago    : " + response.getFolioPago());
				Logger.write("     respuesta.NumAutBmx    : " + response.getNumAutBmx());
			}
		}catch(Exception e){
			//bitacora
			try{
				oracle.pagoFacturaSalida(numTx, "", "", e.getLocalizedMessage());
			}catch (Exception ex) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
			if(e != null && e.getLocalizedMessage() != null){
 				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("[Caja]") 
 						|| e.getLocalizedMessage().contains("cardBlackList") || e.getLocalizedMessage().contains("TransXTarjeta")
 						||e.getLocalizedMessage().contains("SemaphoreApplyCharge")||e.getLocalizedMessage().contains("[ctrl]")){
					throw new ServiceException("[WARN] pagarFactura ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] pagarFactura ["+dn+"] :: " + e.getLocalizedMessage());
				}
 			}else{
				throw new ServiceException("[ERR] pagarFactura ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			//bitacora
			try{
				oracle.pagoFacturaSalida(numTx, response.getFolioPago(), response.getNumAutBmx(), "");
			}catch (Exception e) {
				Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
			}
			Logger.end("pagarFactura ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return response;
 	}
 	
 	public EstadoCuentaVO desgloseFactura (String user, String pass, String facturaUrl, String token) throws Throwable{
 		long initime = System.currentTimeMillis();
        Logger.init("desgloseFactura");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		EstadoCuentaVO response = new EstadoCuentaVO();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     facturaUrl             : " + facturaUrl);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken("", token,  new MensajeLogBean());
			
			response = DesgloseFactura.flujo("",facturaUrl);
			
			if(response != null){
				Logger.write("   + Respuesta              + ");
				Logger.write("     Saldos                 : " + response.getSaldos().size());
				Logger.write("     Ajustes                : " + response.getAjustes().size());
				Logger.write("     ConsumosPeriodo        : " + response.getConsumosPeriodo().size());
				Logger.write("     OtrosCargos            : " + response.getOtrosCargos().size());
				Logger.write("     Rentas                 : " + response.getRentas().size());
				Logger.write("     ServiciosConPromocion  : " + response.getServiciosConPromocion().size());
				Logger.write("     ServiciosSinPromocion  : " + response.getServiciosSinPromocion().size());
			}
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("vacio")){
					throw new ServiceException("[WARN] desgloseFactura :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] desgloseFactura :: " + e.getLocalizedMessage());	
				}
			}
			else{
				throw new ServiceException("[ERR] desgloseFactura :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("desgloseFactura :: " + getLocalAddress(), initime);
		}

		return response;
 	}
 	
	/**------------------------------------------------------------------------------------------------
	   * 
	   * FUNCIONES PARA SERVICIOS
	   * 
	   * ----------------------------------------------------------------------------------------------*/

	public LinkedList<ConsultaGenerica> consultaQueryComun(String user, String pass, String query) throws ServiceException{
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1KNjQ=-")) {
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1KNjQ=-")) {
			throw new ServiceException("[ALE] password no válido");
		}
		OracleProcedures oracleProcedures = new OracleProcedures();
		LinkedList<ConsultaGenerica> contenido = null;
		contenido = oracleProcedures.getQueryComun(query);
		return contenido; 
	}

	public LinkedList<ConsultaGenerica> consultaCursorComun(String user, String pass, String query) throws ServiceException{
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1KNjQ=-")) {
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1KNjQ=-")) {
			throw new ServiceException("[ALE] password no válido");
		}
		OracleProcedures oracleProcedures = new OracleProcedures();
		LinkedList<ConsultaGenerica> contenido = null;
		contenido = oracleProcedures.getCursorComun(query);
		return contenido;
	}

	public String consultaBloqueComun(String user, String pass, String query) throws ServiceException {
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1KNjQ=-")) {
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1KNjQ=-")) {
			throw new ServiceException("[ALE] password no válido");
		}
		OracleProcedures oracleProcedures = new OracleProcedures();
		String resultado = "";
		resultado = oracleProcedures.getBloqueComun(query);
		return resultado;
	}

	public String getBitacora(String user, String pass, String filtro, String archivoLog) throws ServiceException {
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1KNjQ=-")) {
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1KNjQ=-")) {
			throw new ServiceException("[ALE] password no válido");
		}
		OracleProcedures oracleProcedures = new OracleProcedures();
		String resultado = "";
		resultado = oracleProcedures.getLogInfo(filtro, archivoLog);
		return resultado;
	}

	public String getBitacoraDir(String user, String pass) throws ServiceException {
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1KNjQ=-")) {
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1KNjQ=-")) {
			throw new ServiceException("[ALE] password no válido");
		}
		OracleProcedures oracleProcedures = new OracleProcedures();
		String resultado = "";
		resultado = oracleProcedures.getLogDir();
		return resultado;
	}
	
	//Obtiene remote Address
	public String getClientIpXfire () { 
		String ip = ""; 
		try { 
			HttpServletRequest request = XFireServletController.getRequest (); 
			ip = request.getRemoteAddr ();
		}catch (Exception e) { 
			Logger.write("unable to obtain remote ip");
		} 
		return ip; 
	}	
	
	//Obtiene local Address
	private String getLocalAddress(){
		Calendar val = Calendar.getInstance();
		String ip = "";
		try{
			HttpServletRequest request = XFireServletController.getRequest (); 
			ip = request.getLocalAddr() + ":" + request.getLocalPort() + "-[" +val.getTime()+ "]";
		}catch(Exception e){
			Logger.write("unable to obtain remote ip");
		}
		return ip;
	}
	
	//Obtiene local Address
	private String getLocalAddressOnly(){
		String ip = "";
		HttpServletRequest request = XFireServletController.getRequest (); 
		try{
			ip = request.getLocalAddr();
		}catch(Exception e){
			Logger.write("unable to obtain remote ip");
		}finally{
			request = null;
		}
		
		return ip;
	}
	
	
	public MensajeMailVO enviarCorreoSMSURL(String user, String pass, String dn, String tipoEvento, String mail, String nombre, int idServicio, String urlFactura, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        String ip = "";		
		
        Logger.init("enviarCorreoSMSURL");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es válido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}
		
		if (urlFactura == null || urlFactura.trim().equals("")) {
			Logger.write("     La urlFactura no es válido ");
			throw new ServiceException("[ALE] urlFactura no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		MensajeMailVO respuesta = new MensajeMailVO();
		EnviaCorreoDetalle enviaDetalle = new EnviaCorreoDetalle();
		
		try{
			ip = getLocalAddressOnly();
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     tipoEvento             : " + tipoEvento);
			Logger.write("     nombre                 : " + nombre);
			Logger.write("     idServicio             : " + idServicio);
			Logger.write("     UrlFactura             : " + urlFactura);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean(""));
			
			respuesta = enviaDetalle.flujo(dn, tipoEvento, mail, nombre, idServicio,urlFactura,ip);
			
			Logger.write("   + Respuesta              + ");
			if(respuesta != null){
				Logger.write("     MailEr_ror             : " + respuesta.getMailError());
				Logger.write("     MailSended             : " + respuesta.getMailSended());
				Logger.write("     SmsEr_ror              : " + respuesta.getSmsError());
				Logger.write("     SmsSended              : " + respuesta.getSmsSended());
			}
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] enviarCorreoSMSURL ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] enviarCorreoSMSURL ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] enviarCorreoSMSURL ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("enviarCorreoSMSURL :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public int setConfiguracionXUsuarioMovil(String user, String pass, String dn, String descripcion, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("setConfiguracionXUsuarioMovil");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] password no valido");
		}
    
		ValidaTokens tokens =  new ValidaTokens();
		int respuesta;
		OracleProcedures procedures = new OracleProcedures();
		
		try{

			Logger.write("      user                        : -PROTEGIDO-");
			Logger.write("      pass                        : -PROTEGIDO-");
			Logger.write("      dn                          : " + dn);
			Logger.write("      descripcion                 : " + descripcion);
			Logger.write("      token                       : " + Formatter.pintaLogCadenasLargas(token));

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			
			respuesta =  procedures.setConfiguracionXUsuarioMovil(dn, descripcion);
			
            if (respuesta == 0){
				Logger.write("     + Respuesta  +");
				Logger.write("       setConfiguracionXUsuarioMovil         :: " + respuesta);
            }
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] setConfiguracionXUsuarioMovil ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] setConfiguracionXUsuarioMovil ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] setConfiguracionXUsuarioMovil ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("setConfiguracionXUsuarioMovil :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public List<ConfiguracionXUsuarioMovilVO> getConfiguracionXUsuarioMovil(String user, String pass, String dn, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("getConfiguracionXUsuarioMovil");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[WARN] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[WARN] password no valido");
		}
		if(dn == null){
	    	throw new ServiceException("[WARN] El dn no puede ir vacio");
		}else if(dn.trim().equals("") || !Utilerias.isNumeric(dn.trim())){
			throw new ServiceException("[WARN] El dn debe ser numérico :: "+dn);
		}
			
		
        OracleProcedures procedures = new OracleProcedures();
		ValidaTokens tokens =  new ValidaTokens();
		List<ConfiguracionXUsuarioMovilVO> listConfiguracionXUsuario = new ArrayList<ConfiguracionXUsuarioMovilVO>(); 
		
		try{

			Logger.write("      user                        : -PROTEGIDO-");
			Logger.write("      pass                        : -PROTEGIDO-");
			Logger.write("      dn                          : " + dn);
			Logger.write("      token                       : " + Formatter.pintaLogCadenasLargas(token));

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			listConfiguracionXUsuario = procedures.getConfiguracionXUsuarioMovil(dn);
        
			Logger.write("     + Respuesta  +");
			Logger.write("       getConfiguracionXUsuarioMovil         :: " + listConfiguracionXUsuario.size());
        
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("Parametros de entrada")){
					throw new ServiceException("[WARN] getConfiguracionXUsuarioMovil ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] getConfiguracionXUsuarioMovil ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] getConfiguracionXUsuarioMovil ["+dn+"] :: " + e.getLocalizedMessage());
			}			
		}
		finally{
			Logger.end("getConfiguracionXUsuarioMovil :: " + getLocalAddress(), initime);
		}

		return listConfiguracionXUsuario;
	}
	
	
	public DetalleTotalesVO walletsTotalPlus(String user, String pass, String dn, String tipoEvento, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("walletsTotal");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			//Logger.write("      El usuario no es valido ");
			throw new ServiceException("[WARN] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			//Logger.write("     El password no es valido ");
			throw new ServiceException("[WARN] password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleConsumosTotales consumos = new DetalleConsumosTotales();
		ConsumosTotalesResponseVO response = new ConsumosTotalesResponseVO();
		DetalleTotalesVO respuestaTotal = new DetalleTotalesVO();
		StringBuffer sResponse = new StringBuffer();
		DecimalFormat formatNumber = new DecimalFormat("#.##");
		OracleProcedures oracle = new OracleProcedures();
		Llamadas llamadas = new Llamadas();
		Mensajes mensajes = new Mensajes();
		Navegacion navegacion = new Navegacion();
		MinutosTodoDestino minutosTodoDestino = new MinutosTodoDestino();
		MinutosComunidad minutosComunidad = new MinutosComunidad();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		List<CatatoloGetVO> listaObtieneCatalogosNav = new ArrayList<CatatoloGetVO>();
		List<CatatoloGetVO> listaObtieneCatalogosMin = new ArrayList<CatatoloGetVO>();
		List<CatatoloGetVO> listaObtieneCatalogosMen = new ArrayList<CatatoloGetVO>();
		List<TipoServicioVO> listTipoServicio = new ArrayList<TipoServicioVO>();
		ConsumosXdnVO consumosFromBD = new ConsumosXdnVO();		
		String detalleTotalesPorTablaBD = "";
		String sumaAddOns = "";
		String consultaTAVivo = "";
		boolean ejecutaTotales = true;
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean());
			
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("[WARN] DN no puede ir vacio");

			try{
				sumaAddOns = oracle.getValorParametro(19);
			}catch (ServiceException e) {
				sumaAddOns = "0";
			}						
			
			try{
				detalleTotalesPorTablaBD = oracle.getValorParametro(74);
			}catch (ServiceException e) {
				detalleTotalesPorTablaBD = "0";
			}
			
			try{
				consultaTAVivo = oracle.getValorParametro(71);
			}catch (ServiceException e) {
				consultaTAVivo = "0";
			}
			
			DetalleWalletsVO descripcionWallets = new DetalleWalletsVO();
			sResponse.append(focalizacion.focalizacion(dn));
			if(sResponse != null && !sResponse.equals("")){
				descripcionWallets = ParseXMLFile.parseWallets(sResponse.toString());
				descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
				sResponse.setLength(0);
			}
			
			if(detalleTotalesPorTablaBD.equals("1")){
				try{
					//1 LLAMADAS, 2 MENSAJES, 3 NAVEGACION, 4 MINUTOS_TD, 5 MINUTOS_COMUNIDAD
					consumosFromBD = new ConsumosXdnVO();
					consumosFromBD = oracle.getconsumosXdn(dn, 1);
					response.setTotalTA(consumosFromBD.getTotal());
					llamadas.setMinutosDeRegalo(consumosFromBD.getRegalo());
					llamadas.setMinutosExtras(consumosFromBD.getExtra());
					if(consumosFromBD.getIncluidos() != null)
						llamadas.setLlamadasIncluidas(consumosFromBD.getIncluidos());
					else
						llamadas.setLlamadasIncluidas("0");
					llamadas.setTotalLlamadas(consumosFromBD.getTotal());
					
					consumosFromBD = new ConsumosXdnVO();
					consumosFromBD = oracle.getconsumosXdn(dn, 2);
					response.setTotalMensaje(consumosFromBD.getTotal());
					mensajes.setMensajesDeTextoDeRegalo(consumosFromBD.getRegalo());
					mensajes.setMensajesExtras(consumosFromBD.getExtra());
					if(consumosFromBD.getIncluidos() != null)
						mensajes.setMensajesIncluidos(consumosFromBD.getIncluidos());
					else
						mensajes.setMensajesIncluidos("0");
					mensajes.setTotalMensajes(consumosFromBD.getTotal());
					
					consumosFromBD = new ConsumosXdnVO();
					consumosFromBD = oracle.getconsumosXdn(dn, 3);
					response.setTotalDatos(consumosFromBD.getTotal());
					navegacion.setNavegacionDeRegalo(consumosFromBD.getRegalo());
					navegacion.setDatosExtra(consumosFromBD.getExtra());
					if(consumosFromBD.getIncluidos() != null)
						navegacion.setDatosIncluidos(consumosFromBD.getIncluidos());
					else
						navegacion.setDatosIncluidos("0");
					navegacion.setTotalDatos(consumosFromBD.getTotal());
					
					consumosFromBD = new ConsumosXdnVO();
					consumosFromBD = oracle.getconsumosXdn(dn, 4);
					minutosTodoDestino.setMinutosTDregalo(consumosFromBD.getRegalo());
					minutosTodoDestino.setMinutosTDextra(consumosFromBD.getExtra());
					minutosTodoDestino.setMinutosTDincluido(consumosFromBD.getIncluidos());
					minutosTodoDestino.setMinutosTDconsumido(consumosFromBD.getTotal());
					
					consumosFromBD = new ConsumosXdnVO();
					consumosFromBD = oracle.getconsumosXdn(dn, 5);
					response.setTotalCom(consumosFromBD.getRegalo());
					minutosComunidad.setMinutosComunidadRegalo(consumosFromBD.getRegalo());
					minutosComunidad.setMinutosComunidadExtra(consumosFromBD.getExtra());
					minutosComunidad.setMinutosComunidadIncluido(consumosFromBD.getIncluidos());
					minutosComunidad.setMinutosComunidadConsumidos(consumosFromBD.getTotal());
					llamadas.setMinutosComunidadConsumidos(consumosFromBD.getTotal());
					
					response.setDatosPlanes(oracle.getPlanXdn(dn,1));
					
					sResponse.append(focalizacion.focalizacion(dn));
					if(sResponse != null && !sResponse.equals("")){
						descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
					}
					response.setDetalleFocalizacion(descripcion);
				}catch (Exception e) {
					response = consumos.flujoFromWallets(dn, descripcion);
					detalleTotalesPorTablaBD = "0";
				}
				ejecutaTotales = false;
			}else{
				ejecutaTotales = true;
			}
			
			if(ejecutaTotales){
				response = consumos.flujoFromWallets(dn, descripcion);
			}
			
			/*Nuevo*/
			try{
				List<ServiciosAdicionalesVO> listServiciosAdicionales = new ArrayList<ServiciosAdicionalesVO>();
				listServiciosAdicionales = GetServiciosAdicionales.flujoPorDnFromWall(response.getDatosPlanes(), response.getDetalleFocalizacion(), 0, dn);
				response.setDetalleServiciosAdicionales(listServiciosAdicionales);
			}catch (Exception e) {
				Logger.write(e.getLocalizedMessage());
			}

			double totalCostoLlamada = 0;
			double totalVoz = 0;
			try{
				if(response != null && response.getDatosPlanes() != null && response.getDatosPlanes().getMinutoadc() != null){
					totalCostoLlamada = Double.parseDouble(response.getDatosPlanes().getMinutoadc());
				}
				if(response != null && response.getTotalTA() != null){
					totalVoz = Double.parseDouble(response.getTotalTA());
				}
			}catch (NumberFormatException e) {
				totalCostoLlamada = 0;
				totalVoz = 0;
			}

			llamadas.setTotalCostoLlamadas(formatNumber.format(totalCostoLlamada * totalVoz));
			if(response.getDatosPlanes() != null && response.getDatosPlanes().getMinutos() != null)
				llamadas.setLlamadasIncluidas(response.getDatosPlanes().getMinutos());

			double totalmsjCosto = 0;
			double totalmsj = 0;
			double msjAdc = 0;
			try{
				if(response != null && response.getTotalMensaje() != null){
					totalmsj = Double.parseDouble(response.getTotalMensaje());
				}
				if(response.getDatosPlanes() != null && response.getDatosPlanes().getMegaadc() != null)
					msjAdc = Double.parseDouble(response.getDatosPlanes().getMegaadc());
				totalmsjCosto = totalmsj * msjAdc;
			}catch (NumberFormatException e) {
				totalmsjCosto = 0;
			}
			mensajes.setTotalCostoMensajes(formatNumber.format(totalmsjCosto));
			if(response.getDatosPlanes() != null){
				mensajes.setMensajesIncluidos(response.getDatosPlanes().getMensajes());
				//polo
				//respuestaTotal.setDescripcionPlanes(response.getDatosPlanes());
				respuestaTotal.setDescripcionPlanes(new ObtenerDescripcionPlanesVO());
				respuestaTotal.getDescripcionPlanes().setMegaadc(response.getDatosPlanes().getMegaadc());
				respuestaTotal.getDescripcionPlanes().setMegas(response.getDatosPlanes().getMegas());
				respuestaTotal.getDescripcionPlanes().setMensajeadc(response.getDatosPlanes().getMensajeadc());
				respuestaTotal.getDescripcionPlanes().setMensajes(response.getDatosPlanes().getMensajes());
				respuestaTotal.getDescripcionPlanes().setMinutoadc(response.getDatosPlanes().getMinutoadc());
				respuestaTotal.getDescripcionPlanes().setMinutos(response.getDatosPlanes().getMinutos());
				respuestaTotal.getDescripcionPlanes().setMinutoscomunidad(response.getDatosPlanes().getMinutoscomunidad());
				respuestaTotal.getDescripcionPlanes().setNombreCortoPlan(response.getDatosPlanes().getNombreCortoPlan());
				respuestaTotal.getDescripcionPlanes().setTiempoAire(response.getDatosPlanes().getTiempoAire());
			}

			navegacion.setTotalDatos(response.getTotalDatos());
			navegacion.setTotalCostoDatos(response.getTotalDatosCosto());
			int minutosC = 0;
			int minutosTD = 0;
			if(response.getDetalleServiciosAdicionales() != null && response.getDetalleServiciosAdicionales().size() > 0){
				int megas = 0;	
				int megasExtra = 0;
				int mensajesTotal=0;
				int mensajesExtras = 0;
				int minutos = 0; 
				int llamadasExtras = 0;
                
				
				if(response != null && response.getDatosPlanes() != null) {
					
					if(response.getDatosPlanes().getMegas() != null && !response.getDatosPlanes().getMegas().equals(""))
					{
						try{
							megas = Integer.parseInt(response.getDatosPlanes().getMegas().trim());
						}catch (Exception e) {
							megas = 0;
						}
					}
					if(response.getDatosPlanes().getMinutos() != null && !response.getDatosPlanes().getMinutos().equals(""))
					{
						try{
							minutos = Integer.parseInt(response.getDatosPlanes().getMinutos().trim());
						}catch (Exception e) {
							minutos = 0;
						}
					}
					if(response.getDatosPlanes().getMensajes() != null && !response.getDatosPlanes().getMensajes().equals(""))
					{
						try{
							mensajesTotal = Integer.parseInt(response.getDatosPlanes().getMensajes().trim());
						}catch (Exception e) {
							mensajesTotal = 0;
						}
					}

					try{
						listaObtieneCatalogosNav = oracle.catalogoGet(9);
						listaObtieneCatalogosMin = oracle.catalogoGet(10);
						listaObtieneCatalogosMen = oracle.catalogoGet(11);
						listTipoServicio = oracle.getTipoServicio(2); //Navegacion
					}catch (Exception e) {
						Logger.write("     Detail al consultar el catalogo");
					}
					
					for(int a = 0; a < response.getDetalleServiciosAdicionales().size(); a++){
						String IdServicio = response.getDetalleServiciosAdicionales().listIterator(a).next().getIdServicio();
						int status = response.getDetalleServiciosAdicionales().listIterator(a).next().getStatus();
						
						if(sumaAddOns.equals("1")){
							
							for(int c=0; c <listaObtieneCatalogosNav.size();c++){
								if( IdServicio.equals(listaObtieneCatalogosNav.get(c).getTipoRespuestaID()+"") && status == 1){
									//megas = megas + Integer.parseInt(listaObtieneCatalogosNav.get(c).getTipoRepuesta());	
									megasExtra = megasExtra + Integer.parseInt(listaObtieneCatalogosNav.get(c).getTipoRepuesta());
								}
							}
							
							for(int c=0; c <listaObtieneCatalogosMin.size();c++){
								if( IdServicio.equals(listaObtieneCatalogosMin.get(c).getTipoRespuestaID()+"") && status == 1){
									//minutos = minutos + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());	
									llamadasExtras = llamadasExtras + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
									
									for(int v=0; v < listTipoServicio.size(); v++){
										if(listTipoServicio.get(v).getServicio().equals(IdServicio)){  
											if(listTipoServicio.get(v).getDescripcion().toLowerCase().contains("comunidad")== true){
												minutosC = minutosC + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());	
											}else{//Todo Destino
												minutosTD = minutosTD + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
											}
										}
									}
									//742-743 Comunidad
//									if (IdServicio.equals("742") || IdServicio.equals("743") ) {
//										minutosC = minutosC + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
//									}
//									//775-776-777-778-779	
//									if (IdServicio.equals("775") || IdServicio.equals("776") || IdServicio.equals("777") || IdServicio.equals("778") || IdServicio.equals("779") ) {
//										minutosTD = minutosTD + Integer.parseInt(listaObtieneCatalogosMin.get(c).getTipoRepuesta());
//									}
									
								}
							}
							
							for(int c=0; c <listaObtieneCatalogosMen.size();c++){
								if( IdServicio.equals(listaObtieneCatalogosMen.get(c).getTipoRespuestaID()+"") && status == 1){
//									mensajesTotal = mensajesTotal +  Integer.parseInt(listaObtieneCatalogosMen.get(c).getTipoRepuesta());	
									mensajesExtras = mensajesExtras + Integer.parseInt(listaObtieneCatalogosMen.get(c).getTipoRepuesta());
								}
							}
						}else{
							if(IdServicio.equals("797") && status == 1){
								//megas = megas + 5;	
								megasExtra = megasExtra + 5; 
							}else if(IdServicio.equals("798") && status == 1) {
								//megas = megas + 10;       
								megasExtra = megasExtra + 10;
							}else if(IdServicio.equals("799") && status == 1) {
								//megas = megas + 50;
								megasExtra = megasExtra + 50;
							}else if(IdServicio.equals("800") && status == 1) {
								//megas = megas + 100;
								megasExtra = megasExtra + 100;
							}else if(IdServicio.equals("801") && status == 1) {
								//megas = megas + 500;
								megasExtra = megasExtra + 500;
							}else if(IdServicio.equals("729") && status == 1) {
								//megas = megas + (1 * 1024);
								megasExtra = megasExtra + (1 * 1024);
							}else if(IdServicio.equals("802") && status == 1) {
								//megas = megas + (2 * 1024);
								megasExtra = megasExtra + (2 * 1024);
							}else if(IdServicio.equals("803") && status == 1) {
								//megas = megas + (3 * 1024);
								megasExtra = megasExtra + (3 * 1024);
							}else if(IdServicio.equals("804") && status == 1) {
								//megas = megas + (5 * 1024);
								megasExtra = megasExtra + (5 * 1024);
							}else if(IdServicio.equals("805") && status == 1) {
								//megas = megas + (10 * 1024);
								megasExtra = megasExtra + (10 * 1024);
							}else if(IdServicio.equals("465") && status == 1) {
								//megas = megas + 50;
								megasExtra = megasExtra + 50;
							}else if(IdServicio.equals("539") && status == 1) {
								//megas = megas + 270;    	
								megasExtra = megasExtra + 270;	
							}
						}
					}
				}

				if(detalleTotalesPorTablaBD.equals("1")){
					
					navegacion.setDatosExtra(megasExtra+"");
					
					llamadas.setMinutosExtras(llamadasExtras+"");
					
					mensajes.setMensajesExtras(mensajesExtras+"");
					
					minutosTodoDestino.setMinutosTDextra(String.valueOf(minutosTD));
					minutosTodoDestino.setMinutosTDregalo("0");
					
					minutosComunidad.setMinutosComunidadExtra(String.valueOf(minutosC));
					minutosComunidad.setMinutosComunidadRegalo("0");
					
				}else{
					llamadas.setMinutosComunidadConsumidos(response.getTotalCom());
					llamadas.setTotalLlamadas(response.getTotalTA());
					mensajes.setTotalMensajes(response.getTotalMensaje());
					
					navegacion.setDatosIncluidos(megas+"");
					navegacion.setDatosExtra(megasExtra+"");

					llamadas.setLlamadasIncluidas(minutos+"");
					llamadas.setMinutosExtras(llamadasExtras+"");

					mensajes.setMensajesIncluidos(mensajesTotal+"");
					mensajes.setMensajesExtras(mensajesExtras+"");

					minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
					minutosTodoDestino.setMinutosTDextra(String.valueOf(minutosTD));
					minutosTodoDestino.setMinutosTDregalo("0");
					minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());

					minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
					minutosComunidad.setMinutosComunidadExtra(String.valueOf(minutosC));
					minutosComunidad.setMinutosComunidadRegalo("0");
					minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());
				}
			}else{
				llamadas.setMinutosComunidadConsumidos(response.getTotalCom());
				llamadas.setTotalLlamadas(response.getTotalTA());
				mensajes.setTotalMensajes(response.getTotalMensaje());
				
				if(response.getDatosPlanes() != null && response.getDatosPlanes().getMegas() != null){
					
					minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
					minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());
					minutosComunidad.setMinutosComunidadRegalo("0");
					
					minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
					minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());
					minutosTodoDestino.setMinutosTDregalo("0");
					
					navegacion.setDatosIncluidos(response.getDatosPlanes().getMegas());
				    mensajes.setMensajesIncluidos(response.getDatosPlanes().getMensajes());
			 	    llamadas.setLlamadasIncluidas(response.getDatosPlanes().getMinutos());
			 	    
			 	    minutosTodoDestino.setMinutosTDincluido(response.getDatosPlanes().getMinutos());
					minutosTodoDestino.setMinutosTDextra(String.valueOf(minutosTD));
					minutosTodoDestino.setMinutosTDregalo("0");
					minutosTodoDestino.setMinutosTDconsumido(response.getTotalTA());
					
					minutosComunidad.setMinutosComunidadIncluido(response.getDatosPlanes().getMinutoscomunidad());
					minutosComunidad.setMinutosComunidadExtra(String.valueOf(minutosC));
	                minutosComunidad.setMinutosComunidadRegalo("0");
	                minutosComunidad.setMinutosComunidadConsumidos(response.getTotalCom());
				}
			} 

			if(consultaTAVivo.equals("1")){
//				sResponse.setLength(0);
//				sResponse.append(focalizacion.focalizacion(dn));
//				if(sResponse != null && !sResponse.equals("")){
//					descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
//				}
				if(descripcion.getTiempoAireComprado() != null && descripcion.getTiempoAireComprado().equals(""))
					respuestaTotal.setTiempoAireAbonado(descripcion.getTiempoAireComprado());
				else if(descripcion.getSaldoPrepago() != null && !descripcion.getSaldoPrepago().equals(""))
					respuestaTotal.setTiempoAireAbonado(descripcion.getSaldoPrepago());
				else
					respuestaTotal.setTiempoAireAbonado("");
			}else{
				if(response.getDetalleFocalizacion().getTiempoAireComprado() != null && response.getDetalleFocalizacion().getTiempoAireComprado().equals(""))
					respuestaTotal.setTiempoAireAbonado(response.getDetalleFocalizacion().getTiempoAireComprado());
				else if(response.getDetalleFocalizacion().getSaldoPrepago() != null && !response.getDetalleFocalizacion().getSaldoPrepago().equals(""))
					respuestaTotal.setTiempoAireAbonado(response.getDetalleFocalizacion().getSaldoPrepago());
				else
					respuestaTotal.setTiempoAireAbonado("");
			}

			if(descripcionWallets != null){
				llamadas.setMinutosDeRegalo(descripcionWallets.getMinutosDeRegalo());
				
				//polo
				//mensajes.setMensajesDeTextoDeRegalo(descripcionWallets.getMensajesDeTexto());
				//navegacion.setNavegacionDeRegalo(descripcionWallets.getNavegacion());
				mensajes.setMensajesDeTextoDeRegalo("0");
				navegacion.setNavegacionDeRegalo("0");
				//respuestaTotal.setTiempoAireDeRegalo("0");
				
				if(descripcionWallets.getTiempoAireDeRegalo() != null && !descripcionWallets.getTiempoAireDeRegalo().equals(""))
					  respuestaTotal.setTiempoAireDeRegalo(descripcionWallets.getTiempoAireDeRegalo());
				else
	    			  respuestaTotal.setTiempoAireDeRegalo("0");
				
				if(descripcionWallets.getTiempoAireTotal() != null && !descripcionWallets.getTiempoAireTotal().equals(""))
				  respuestaTotal.setTiempoAireTotal(descripcionWallets.getTiempoAireTotal());
				else
					respuestaTotal.setTiempoAireTotal("0");
			}
			
			//polo
//			if(descripcion != null){
//				respuestaTotal.setVigenciaSalgoPrepago(descripcion.getVigenciaSalgoPrepago());
//			}
			respuestaTotal.setLlamadas(llamadas);
			respuestaTotal.setMensajes(mensajes);
			respuestaTotal.setNavegacion(navegacion);
			respuestaTotal.setMinutosComunidad(minutosComunidad);
			respuestaTotal.setMinutosTodoDestino(minutosTodoDestino);
			
			Logger.write("   + Respuesta              +");
			Logger.write("     TotalDatos             : " + response.getTotalDatos());
			Logger.write("     TotalMensaje           : " + response.getTotalMensaje());
			Logger.write("     TotalTA                : " + response.getTotalTA());
			Logger.write("     TotalVoz               : " + response.getTotalVoz());
			Logger.write("     DescripcionPlan        : " + response.getDescripcionPlan());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				boolean mensajeDBControlado = Utilerias.contieneMensajeError(e.getLocalizedMessage());
				if(e.getLocalizedMessage().contains("vacio") || mensajeDBControlado){
					String msg = e.getLocalizedMessage();
					if(mensajeDBControlado)
						msg = Constantes.MSJ_BD_CONSUMOS;
					throw new ServiceException("[WARN] walletsTotal ["+dn+"] :: " + msg);
				}else{
					throw new ServiceException("[ERR] walletsTotal ["+dn+"] :: " + e.getLocalizedMessage());	
				}
			}
			else{
				throw new ServiceException("[ERR] walletsTotal ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("walletsTotal ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return respuestaTotal;
	}
	
	public String reservaPIN(String user, String pass, String dn, String token) throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("reservaPIN");
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
			throw new ServiceException("[WARN] usuario no valido");
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
			throw new ServiceException("[WARN] password no valido");
        if(dn==null || dn.trim().equals(""))
            throw new ServiceException("[WARN] DN no puede ir vacio");
		
		String respuesta = "0";
		OracleProcedures oracle = new OracleProcedures();
		ValidaTokens tokens =  new ValidaTokens();
		String compania = "";
		String ip="";
		String cia="";
		try{
			tokens.validaToken(dn, token, new MensajeLogBean());
			
			if (dn.length()==11) {
				cia=dn.substring(10);
				dn=dn.substring(0,10);
			}else{
				cia = oracle.retornaCia(dn,new MensajeLogBean());
			}
			
			if(cia != null && !cia.equals("")){
				compania = cia;
			}
			ip = getLocalAddressOnly();
			respuesta = GeneraPIN.creaPIN(dn, compania, ip);
				
		}catch (Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] reservaPIN ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] reservaPIN ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] reservaPIN ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}finally{
			Logger.end("reservaPIN ["+dn+"] :: "  + getLocalAddress(), initime);
		}
		return respuesta;
	}
	
	public boolean validaPIN(String user, String pass, String dn, String pin, String token) throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("validaPIN");
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
			throw new ServiceException("[WARN] usuario no valido");
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
			throw new ServiceException("[WARN] password no valido");
        if(dn==null || dn.trim().equals(""))
            throw new ServiceException("[WARN] DN no puede ir vacio");
        if(pin==null || pin.trim().equals(""))
            throw new ServiceException("[WARN] PIN no puede ir vacio");
		boolean respuesta = false;
		ValidaTokens tokens =  new ValidaTokens();	
		
		try{
			tokens.validaToken(dn, token, new MensajeLogBean());
			respuesta = GeneraPIN.validaPIN(dn, pin);
		} catch (Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					 throw new ServiceException("[WARN] validaPIN ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					 throw new ServiceException("[ERR] validaPIN ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				 throw new ServiceException("[ERR] validaPIN ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}finally{
			Logger.end("validaPIN ["+dn+"] :: "  + getLocalAddress(), initime);
		}
		return respuesta;
	}
	
	public boolean actualizaContrasenia (String user, String pass, String dn, String pase, String token) throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("actualizaContrasenia");
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
			throw new ServiceException("[WARN] usuario no valido");
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
			throw new ServiceException("[WARN] password no valido");
        if(dn==null || dn.trim().equals(""))
            throw new ServiceException("[WARN] DN no puede ir vacio");
        if(pase==null || pase.trim().equals(""))
            throw new ServiceException("[WARN] PIN no puede ir vacio");
		boolean respuesta = false;
		OracleProcedures oracle = new OracleProcedures();
		ValidaTokens tokens =  new ValidaTokens();
		try{
			tokens.validaToken(dn, token, new MensajeLogBean());
			
			AlgoritmoAes algo=new AlgoritmoAes();
			pase = algo.decrypt(pase, "GrUPoSaLInaSsACv".getBytes());
			String valida = oracle.restablecerLlave(dn, pase, 1212);
			if(valida != null && !valida.equals("")){
				respuesta = true;
			}
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] actualizaContrasenia ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] actualizaContrasenia ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] actualizaContrasenia ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}finally{
			Logger.end("actualizaContrasenia ["+dn+"] :: "  + getLocalAddress(), initime);
		}
		return respuesta;
	}

	public ContratarServiciosVO contrataServicios(String user, String pass, String dn, AltaServicioEtakVO datosAltaEtak, AltaServicioLegacyVO datosAlta, AltaServicioPrepagoLegacyVO datosAltaPrepago, int compania, int sistemaOrigen,int dispositivo,String password, String token) throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("contrataServicios");
	
 		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("       El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}

		ValidaTokens tokens =  new ValidaTokens();
		ContratarServiciosVO RespContratarServicio = new ContratarServiciosVO();
		OracleProcedures oracle = new OracleProcedures();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		String sResponse = "";
		List<ServiciosAdicionalesVO> resServAdicionales = new ArrayList<ServiciosAdicionalesVO>();
		String idServicio="";
		String costoServicio="0";

		int validaPwd = oracle.validarPassword(dn, password);
		if(validaPwd != 0){
			throw new ServiceException("[WARN] No se pudo validar el password ["+ dn +"] :: ");
		}
		
		try{
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     DN                     : " + dn);
			Logger.write("     DatosAltaETAK          : " );
			if(datosAltaEtak != null){
				Logger.write("     Costo                  : " + datosAltaEtak.getCosto());
				Logger.write("     Id                     : " + datosAltaEtak.getId());
				Logger.write("     IdHistorico            : " + datosAltaEtak.getIdHistorico());
				Logger.write("     Monto                  : " + datosAltaEtak.getMonto());
				Logger.write("     Vigencia               : " + datosAltaEtak.getVigencia());
			}
			Logger.write("     DatosAltaLEGACY        : " );
			if(datosAlta != null){
				Logger.write("     ContinenteFavortio     : " + datosAlta.getContinenteFavortio());
				Logger.write("     DnUsa                  : " + datosAlta.getDnUsa());
				Logger.write("     Servicios              : " + datosAlta.getServicios());
			}else{
				datosAlta = new AltaServicioLegacyVO();
			}
			if(datosAltaPrepago != null){
				Logger.write("     Operacion              : " + datosAltaPrepago.getOperacion());
				Logger.write("     ServicioOrigen         : " + datosAltaPrepago.getServicioOrigen());
				Logger.write("     ServiciosId            : " + datosAltaPrepago.getServiciosId());
				Logger.write("     VigenciasCantidad      : " + datosAltaPrepago.getVigenciasCantidad());
				Logger.write("     VigenciasUnidad        : " + datosAltaPrepago.getVigenciasUnidad());
			}
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			

			tokens.validaToken(dn, token, new MensajeLogBean());

			String respuesta = "0";
			try{
				sResponse = focalizacion.focalizacion(dn);
				if(sResponse != null && !sResponse.equals(""))
					descripcion = ParseXMLFile.parseFocalizacion(sResponse);

				int tipoTecnologia = 0;

				if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
					tipoTecnologia = 2;  //Etak
				}else{
					tipoTecnologia = 1;  //Legacy
				}

				int tipoLinea = 0;

				if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
					if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
						tipoLinea = 2;
					}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
						tipoLinea = 3;
					}
				}else {
					tipoLinea = 1; //prepago
				}
				
				resServAdicionales = GetServiciosAdicionales.flujoPorDn(0,dn);
				
				if(tipoTecnologia == 1){ //legacy
					if(tipoLinea == 1){ //prepago
						idServicio = datosAltaPrepago.getServiciosId();
					}else if(tipoLinea == 3)//hibrido
						idServicio = datosAlta.getServicios();
				}else if(tipoTecnologia == 2){ //Etak
					idServicio =datosAltaEtak.getId();
				}
				
				for(int a=0; a<resServAdicionales.size(); a++){
					if(idServicio.equals(resServAdicionales.get(a).getIdServicio())){
						costoServicio = resServAdicionales.get(a).getCosto();
						break;
					}
				}

				respuesta =	oracle.contratarServiciosSET(user, pass, dn, costoServicio, datosAltaEtak.getId(), datosAltaEtak.getIdHistorico(), datosAltaEtak.getVigencia(), datosAltaEtak.getMonto(), datosAlta.getDnUsa(), datosAlta.getContinenteFavortio(), datosAlta.getServicios(), datosAltaPrepago.getServiciosId(), datosAltaPrepago.getServicioOrigen(), datosAltaPrepago.getVigenciasUnidad(), datosAltaPrepago.getVigenciasCantidad(), datosAltaPrepago.getOperacion(), compania, sistemaOrigen, token, 2021,tipoLinea, tipoTecnologia, dispositivo);
			}catch (Exception e) {
				Logger.write("Detail en el metodo de respuesta contratarServicios");
			}
			
    	    RespContratarServicio = AltaServicioXUsuExt.flujo(dn, datosAltaEtak, datosAlta, datosAltaPrepago, respuesta);
			 
    	    if(RespContratarServicio != null && !RespContratarServicio.equals("")){
				try {
					oracle.compraServicioRespuesta(respuesta, 4, RespContratarServicio.getMessageCode(),RespContratarServicio.getOperationCode() , 2121,"","");							    
				}catch (Exception e) {
                    Logger.write("Detail a ejecutar el metodo :: compraServicioRespuesta");
				}
		    }
    	    
			Logger.write("   + Respuesta              + ");
			Logger.write("     MessageCode            : " + RespContratarServicio.getMessageCode());
			Logger.write("     OperationCode          : " + RespContratarServicio.getOperationCode());

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] contrataServicios ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] contrataServicios ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] contrataServicios ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("contrataServicios ["+dn+"] :: " + getLocalAddress(), initime);
		}
		return RespContratarServicio;
	}
	
	public List<CatalogoTotalesVO> cantidadUsuarios (String user, String pass, String token) throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("cantidadUsuarios");
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ-/")) {
			throw new ServiceException("[WARN] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ-/")) {
			throw new ServiceException("[WARN] password no valido");
		}
		OracleProcedures oracle = new OracleProcedures();
		List<CatalogoTotalesVO> listaTotales = new ArrayList<CatalogoTotalesVO>(); 
		try{
		listaTotales = oracle.totalUsuarios("3");
		}catch(Exception e){
			throw new ServiceException("[WARN] cantidadUsuarios :: al consultar información");
		}finally{
			Logger.end("cantidadUsuarios :: consulta cantidad de usuarios" + getLocalAddress(), initime);
		}
		
		return listaTotales;
		
	}
	
	public DetalleFacturaVO detalleFactura (String user, String pass, String dn, String facturaUrl, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("detalleFactura");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es vlido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es vlido ");
			throw new ServiceException("[ALE] password no valido");
		}
		
		if (facturaUrl == null || facturaUrl.trim().equals("") || facturaUrl.trim().equalsIgnoreCase("null")) {
			Logger.write("     La urlFactura no es vlido ");
			throw new ServiceException("[ALE] urlFactura no valido");
		}
		try{
			OracleProcedures oracle = new OracleProcedures();
			String datosFactura = oracle.getValorParametro(115);
			String[] datosFacturaPart = datosFactura.split("\\|");
			String url = datosFacturaPart[0];
			if(url != null)
			{
				url = url.trim();
				if(!url.equals(""))
				{
					if (!facturaUrl.trim().startsWith(url)) {
						Logger.write("     La urlFactura no es valido ");
						throw new ServiceException("[ALE] urlFactura no valido");
					}
				}
			}
		}catch(Exception ex){					
			if(ex.getLocalizedMessage().contains("[ALE] urlFactura no valido"))
				throw new ServiceException(ex.getLocalizedMessage());
			else
				Logger.write("     [Exception] al validar urlFactura : " + ex.getLocalizedMessage());
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleFacturaVO respuesta = new DetalleFacturaVO();
		EnviaCorreoDetalle enviaDetalle = new EnviaCorreoDetalle();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     DN                     : " + dn);
			Logger.write("     UrlFactura             : " + facturaUrl);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean(""));
			
			respuesta = enviaDetalle.detalleFactura(dn,facturaUrl);
			
			Logger.write("   + Respuesta              + ");
			if(respuesta != null){
				Logger.write("     cuenta                      : " + respuesta.getCuenta());
				Logger.write("     factura                     : " + respuesta.getFactura());
				Logger.write("     rentas                      : " + respuesta.getRentas());
				Logger.write("     serviciosAdicionales        : " + respuesta.getServiciosAdicionales());
				Logger.write("     consumosPeriodo             : " + respuesta.getConsumosPeriodo());
				Logger.write("     subtotal                    : " + respuesta.getSubtotal());
				Logger.write("     iva                         : " + respuesta.getIva());
				Logger.write("     ajuste                      : " + respuesta.getAjuste());
				Logger.write("     total                       : " + respuesta.getTotal());
				Logger.write("     fechaLimitedePago           : " + respuesta.getFechaLimite());
				Logger.write("     porcentajeIVA               : " + respuesta.getPorcentajeIVA());

			}
		}catch(Exception e){
			throw new ServiceException("[ERR] detalleFactura ["+dn+" :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("detalleFactura :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public MensajeMailVO enviarCorreoFacturaDetalle(String user, String pass, String tipoEvento, int idServicio, String urlFactura, FacturaVirtualDetalleVO facturaVirtualDetalle, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("enviarCorreoFacturaDetalle");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es válido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es válido ");
			throw new ServiceException("[ALE] password no válido");
		}
		
		if (urlFactura == null || urlFactura.trim().equals("")) {
			Logger.write("     La urlFactura no es válido ");
			throw new ServiceException("[ALE] urlFactura no válido");
		}
		

		ValidaTokens tokens =  new ValidaTokens();
		MensajeMailVO respuesta = new MensajeMailVO();
		EnviaCorreoDetalle enviaDetalle = new EnviaCorreoDetalle();
		OracleProcedures oracle = new OracleProcedures();
		
		String dn ="";
		String nameFrom="";
		String mailFrom="";
		String nombre="";
		String ip="";
		try{
			ip = getLocalAddressOnly();
			
			dn=facturaVirtualDetalle.getDn();
			
			nombre=facturaVirtualDetalle.getNombre();
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     tipoEvento             : " + tipoEvento);
			Logger.write("     nombre                 : " + nombre);
			Logger.write("     idServicio             : " + idServicio);
			Logger.write("     UrlFactura             : " + urlFactura);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			
			tokens.validaToken(dn, token, new MensajeLogBean(""));
			
			if(tipoEvento.equals("1"))
			{ 
				
				if (idServicio==4){
					nameFrom=oracle.getValorParametro(126);
					nameFrom=nameFrom.replace("&", "&amp;");
					mailFrom=oracle.getValorParametro(127);
					respuesta = enviaDetalle.flujo_sendMailATT(tipoEvento,idServicio, urlFactura, mailFrom, nameFrom, facturaVirtualDetalle,ip);
				}else{
					respuesta = enviaDetalle.flujo_sendMail(tipoEvento,idServicio, urlFactura, mailFrom, nameFrom, facturaVirtualDetalle,ip);	
				}
				
			}
			if(tipoEvento.equals("2"))
			{
				if (idServicio==4){
					nameFrom=oracle.getValorParametro(126);
					nameFrom=nameFrom.replace("&", "&amp;");
					mailFrom=oracle.getValorParametro(127);
					respuesta = enviaDetalle.flujoFacturaVirtualATT(dn, tipoEvento, idServicio, mailFrom, nameFrom, facturaVirtualDetalle,ip);
				}else{
					respuesta = enviaDetalle.flujoFacturaVirtual(dn, tipoEvento, idServicio, mailFrom, nameFrom, facturaVirtualDetalle,ip);
				}
			}
			
			Logger.write("   + Respuesta              + ");
			if(respuesta != null){
				Logger.write("     MailEr_ror             : " + respuesta.getMailError());
				Logger.write("     MailSended             : " + respuesta.getMailSended());
				Logger.write("     SmsEr_ror              : " + respuesta.getSmsError());
				Logger.write("     SmsSended              : " + respuesta.getSmsSended());
			}
		}catch(Exception e){
			throw new ServiceException("[ERR] enviarCorreoFacturaDetalle ["+dn+"]["+tipoEvento+"] :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("enviarCorreoFacturaDetalle :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
	public RespuestaMensajeBean utilEnviarCorreo(String user, String pass, String dn, MessageMailBean datos, String token) throws Throwable
	{
	    long initime = System.currentTimeMillis();
	    Logger.init("utilEnviarCorreo");
	    
	    RespuestaMensajeBean respuesta = new RespuestaMensajeBean();
	    
	    if (user.trim().equals("") || !user.trim().equals("ENviASmSKEbI12XQ1MIjQ*/")) {
	        Logger.write("     El usuario no es valido ");
	        throw new ServiceException("[ALE] usuario no valido");
	    }
	    if (pass.trim().equals("") || !pass.trim().equals("ENviASmSKEbI12XQ1MIjQ*/")) {
	        Logger.write("     El password no es valido ");
	        throw new ServiceException("[ALE] password no valido");
	    }
	    
	    final ValidaTokens tokens =  new ValidaTokens();
	    
	    try{
	        String ip = getClientIpXfire();
	        tokens.validaToken(dn, token, new MensajeLogBean(""));
	        
	        EnviaCorreoDetalle delegate = new EnviaCorreoDetalle();
	        respuesta = delegate.utilEnviarCorreoDelegate(datos,ip);
	    }catch(Exception e){
	        throw new ServiceException("[ERR] utilEnviarCorreo ["+dn+"] :: " + e.getLocalizedMessage());
	    }
	    finally{
	        Logger.end("utilEnviarCorreo :: " + getLocalAddress(), initime);
	    }
	    
	    return respuesta;
	}
	
	public DetalleSaldoVO detalleSaldo(String user, String pass, String dn, String facturaUrl, String token) throws Throwable{

		long initime = System.currentTimeMillis();
        Logger.init("detalleSaldo");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El usuario no es valido ");
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("     El password no es valido ");
			throw new ServiceException("[ALE] password no valido");
		}
		
		if (facturaUrl == null || facturaUrl.trim().equals("") || facturaUrl.trim().equalsIgnoreCase("null")) {
			Logger.write("     La urlFactura no es vlido ");
			throw new ServiceException("[ALE] urlFactura no valido");
		}
		try{
			OracleProcedures oracle = new OracleProcedures();
			String datosFactura = oracle.getValorParametro(115);
			String[] datosFacturaPart = datosFactura.split("\\|");
			String url = datosFacturaPart[0];
			if(url != null)
			{
				url = url.trim();
				if(!url.equals(""))
				{
					if (!facturaUrl.trim().startsWith(url)) {
						Logger.write("     La urlFactura no es valido ");
						throw new ServiceException("[ALE] urlFactura no valido");
					}
				}
			}
		}catch(Exception ex){					
			if(ex.getLocalizedMessage().contains("[ALE] urlFactura no valido"))
				throw new ServiceException(ex.getLocalizedMessage());
			else
				Logger.write("     [Exception] al validar urlFactura : " + ex.getLocalizedMessage());
		}

		ValidaTokens tokens =  new ValidaTokens();
		DetalleSaldoVO respuesta = new DetalleSaldoVO();
		EnviaCorreoDetalle enviaDetalle = new EnviaCorreoDetalle();
		
		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     DN                     : " + dn);
			Logger.write("     UrlFactura             : " + facturaUrl);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());

			tokens.validaToken(dn, token, new MensajeLogBean(""));
			
			respuesta = enviaDetalle.detalleSaldo(dn, facturaUrl);
			
			Logger.write("   + Respuesta              + ");
			if(respuesta != null){
				Logger.write("     saldoAnterior                   : " + respuesta.getO1saldoAnterior());
				Logger.write("     suPagoGracias                  : " + respuesta.getO2suPagoGracias());
				Logger.write("     ajustesCuenta                  : " + respuesta.getO3ajustesCuenta());
				Logger.write("     totalFacturaActual             : " + respuesta.getO4totalFacturaActual());
				Logger.write("     ajusteRedondeo                : " + respuesta.getO5ajusteRedondeo());
				Logger.write("     totalPagar                         : " + respuesta.getO6totalPagar());

			}
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				String msg = "No hay datos Error en la consulta SQLException";
				msg = msg.toLowerCase();
				if(e.getLocalizedMessage().toLowerCase().contains(msg)){
					throw new ServiceException("[WARN] detalleSaldo ["+dn+" :: " + e.getLocalizedMessage());			
				}
				else{
					throw new ServiceException("[ERR] detalleSaldo ["+dn+" :: " + e.getLocalizedMessage());
				}
			}
			else{
				throw new ServiceException("[ERR] detalleSaldo ["+dn+" :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("detalleSaldo :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
	
    public List<CatalogoAbonos> getCatalogoAbonos(String user, String pass, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("getCatalogoAbonos");
        
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] password no válido");
		}

		ValidaTokens tokens =  new ValidaTokens();	
		OracleProcedures oracle=new OracleProcedures();
		List<CatalogoAbonos> respuesta = new ArrayList<CatalogoAbonos>();

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken("",token,new MensajeLogBean(""));
			respuesta=oracle.getCatalogoAbonos();
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta.size());

		}catch(Exception e){
			throw new ServiceException("[ERR] getCatalogoAbonos [ :: " + e.getLocalizedMessage());
		}
		finally{
			Logger.end("getCatalogoAbonos :: " + getLocalAddress(), initime);
		}

		return respuesta;
	}
    
    public ContratarServiciosVO bajaServiciosPass(String user, String pass, String mdn, String idServicio, AltaServicioLegacyVO datosAlta, String password, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("bajaServiciosPass");

		
	    if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
	      throw new ServiceException("[WARN] usuario no valido");
	    }
	    if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
	      throw new ServiceException("[WARN] password no valido");
	    }
	    ValidaTokens tokens =  new ValidaTokens();
	    ContratarServiciosVO response = new ContratarServiciosVO();
	    OracleProcedures oracle = new OracleProcedures();
	    
	    try{
	    	
	    	Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     mdn                    : " + mdn);
			Logger.write("     idServicio             : " + idServicio);
			
			if(datosAlta != null){
				Logger.write("     ContinenteFavortio     : " + datosAlta.getContinenteFavortio());
				Logger.write("     DnUsa                  : " + datosAlta.getDnUsa());
				Logger.write("     Servicios              : " + datosAlta.getServicios());
			}else{
				datosAlta = new AltaServicioLegacyVO();
			}
			Logger.write("      token                       : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("      remoteAddress               : " + getClientIpXfire());
			
			tokens.validaToken(mdn, token, new MensajeLogBean());
			
			
			int validaPwd = oracle.validarPassword(mdn, password);
			if(validaPwd != 0){
				throw new ServiceException("[WARN] Password incorrecto");
			}
			
			
			response = BajaServicio.bajaServicio(mdn, datosAlta);
			
			Logger.write("   + Respuesta              + ");
			if(response != null){
				Logger.write("     MessageCode            : " + response.getMessageCode());
				Logger.write("     OperationCode          : " + response.getOperationCode());
			}
	    	
	    }catch(Exception e)
	    {
	    	if(e != null && e.getLocalizedMessage() != null){	
		    	if(e.getLocalizedMessage().toLowerCase().contains("[ctrl]") == true){
		    		throw new ServiceException("[WARN] bajaServiciosPass ["+mdn+"] :: " + e.getLocalizedMessage());	
		    	}else if(e.getLocalizedMessage().contains("ORA-20000")){
		    		throw new ServiceException("[WARN] bajaServiciosPass ["+mdn+"] ::  " + e.getLocalizedMessage());
	    	    }else{
				    throw new ServiceException("[ERR] bajaServiciosPass ["+mdn+"] :: " + e.getLocalizedMessage());
		    	}
	    	}else{
	    		throw new ServiceException("[ERR] bajaServiciosPass ["+mdn+"] ::  " + e.getLocalizedMessage());
	    	}
	    	
	    }finally
	    {
	    	Logger.end("bajaServiciosPass ["+mdn+"] :: " + getLocalAddress(), initime);
	    }
	    
 	  return response;
  }

    public String generaEnviaPIN(String user, String pass, String dn, String token) throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("generaEnviaPIN");
		if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
			throw new ServiceException("[WARN] usuario no valido");
		if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
			throw new ServiceException("[WARN] password no valido");
		if(dn == null || dn.trim().equals(""))
			throw new ServiceException("[WARN] DN no puede ir vacio");
		
		String respuesta = "-1";		
		
		String ip="";
		
		ValidaTokens tokens =  new ValidaTokens();	
		GeneraPIN pin = new GeneraPIN();
		
		try{
			tokens.validaToken(dn, token, new MensajeLogBean());
			ip = getLocalAddressOnly();
			respuesta = pin.generaEnviaPin(dn, ip);
				
		}catch (Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[ERR] generaEnviaPIN ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] generaEnviaPIN ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] generaEnviaPIN ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}finally{
			Logger.end("generaEnviaPIN ["+dn+"] :: "  + getLocalAddress(), initime);
		}
		return respuesta;
	}
	
	public boolean getValidacionPIN(String user, String pass, String dn, String pin, String token) throws Throwable{
		long initime = System.currentTimeMillis();
        Logger.init("getValidacionPIN");
		if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
			throw new ServiceException("[WARN] usuario no valido");
		if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
			throw new ServiceException("[WARN] password no valido");
		if(dn  == null || dn.trim().equals(""))
			throw new ServiceException("[WARN] DN no puede ir vacio");
		if(pin == null || pin.trim().equals(""))
			throw new ServiceException("[WARN] PIN no puede ir vacio");
		
		boolean respuesta = false;
		ValidaTokens tokens =  new ValidaTokens();	
		GeneraPIN flujoPin = new GeneraPIN();
		try{
			tokens.validaToken(dn, token, new MensajeLogBean());
			respuesta = flujoPin.validacionPin(dn, pin);
		} catch (Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[ERR] getValidacionPIN ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] getValidacionPIN ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] getValidacionPIN ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}finally{
			Logger.end("getValidacionPIN ["+dn+"] :: "  + getLocalAddress(), initime);
		}
		return respuesta;
	}
	
	public boolean validaDNPassword(String user, String pass, String dn, String passUsuario, String token) throws Throwable
	{
		long initime = System.currentTimeMillis();
		Logger.init("validaDNPassword");
		if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
			throw new ServiceException("[WARN] usuario no valido");
		if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
			throw new ServiceException("[WARN] password no valido");
		if(dn  == null || dn.trim().equals(""))
			throw new ServiceException("[WARN] DN no puede ir vacio");
		if(passUsuario == null || passUsuario.trim().equals(""))
			throw new ServiceException("[WARN] passwordUsuario no puede ir vacio");

		int validaDatos = -1;
		boolean passCorrecto = false;
		ValidaTokens tokens =  new ValidaTokens();	
		try
		{
			tokens.validaToken(dn, token, new MensajeLogBean());
			OracleProcedures oracle = new OracleProcedures();
			try
			{
				validaDatos = oracle.validarPassword(dn, passUsuario);
			}
			catch (Exception e) {
				if(e != null && e.getLocalizedMessage() != null){
					if(e.getLocalizedMessage().contains("ORA-20000")){
						validaDatos = -1;
						Logger.write("[WARN] validaDNPassword " + e.getLocalizedMessage());
					}else{
						throw new ServiceException(e.getLocalizedMessage());
					}
				}else{
					throw new ServiceException(e.getLocalizedMessage());
				}		
			}
			if(validaDatos != 0)					
				passCorrecto = false;
			else
				passCorrecto = true;
		}
		catch (Exception e) {
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] validaDNPassword ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] validaDNPassword ["+dn+"] :: " + e.getLocalizedMessage());					
				}
			}else{
				throw new ServiceException("[ERR] validaDNPassword ["+dn+"] :: " + e.getLocalizedMessage());					
			}
		}
		finally
		{
			Logger.end("validaDNPassword ["+dn+"] :: "  + getLocalAddress(), initime);
		}

		return passCorrecto;
	}


	public List<PermisosClienteVO> getPermisosXcliente(String user, String pass, String dn, String token) throws Throwable {
		
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		StringBuffer sResponse = new StringBuffer();
		List<PermisosClienteVO> respuesta= new ArrayList<PermisosClienteVO>();
		
		long initime = System.currentTimeMillis();
        Logger.init("getPermisosXcliente");

		
	    if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
	      throw new ServiceException("[WARN] usuario no valido");
	    }
	    if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
	      throw new ServiceException("[WARN] password no valido");
	    }
	    
	    if(dn == null ||  dn.trim().equals(""))
	    	throw new ServiceException("El dn no puede ir vacio");
	    
	    ValidaTokens tokens =  new ValidaTokens();
	    OracleProcedures oracle = new OracleProcedures();
	    
	    try{
	    	
	    	Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token, new MensajeLogBean());
			
			sResponse.append(focalizacion.focalizacion(dn));
			
			if(sResponse != null && !sResponse.equals("")){
				descripcion = ParseXMLFile.parseFocalizacion(sResponse.toString());
			}
			if (descripcion.getTipoCliente() != null && !descripcion.getTipoCliente().equals("") )
			{
				respuesta=oracle.getPermisosCliente(descripcion.getTipoCliente());
			}else{
				throw new ServiceException("[CTRL] No existe tipo de cliente en focalización");
			}
				
	    	
	    }catch(Exception e)
	    {
	    	if(e != null && e.getLocalizedMessage() != null){	
		    	if(e.getLocalizedMessage().toLowerCase().contains("[ctrl]") == true){
		    		throw new ServiceException("[WARN] getPermisosXcliente ["+dn+"] :: " + e.getLocalizedMessage());	
		    	}else{
				    throw new ServiceException("[ERR] getPermisosXcliente ["+dn+"] :: " + e.getLocalizedMessage());
		    	}
	    	}else{
	    		throw new ServiceException("[ERR] getPermisosXcliente ["+dn+"] :: " + e.getLocalizedMessage());
	    	}
			
	    }finally
	    {
	    	Logger.end("getPermisosXcliente ["+dn+"] :: " + getLocalAddress(), initime);
	    }
		
		return respuesta;
	}
	public List<ConsultaWalletGeneralVO> consultaWallets(String user, String pass, String dn,String compania, String token) throws Throwable{
		
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		List<ConsultaWalletGeneralVO> listConsultaWalletsGeneral = new ArrayList<ConsultaWalletGeneralVO>();
		StringBuffer sResponse = new StringBuffer();
		OracleProcedures oracle = new OracleProcedures();
		
		
		long initime = System.currentTimeMillis();
        Logger.init("consultaWallets");

        if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[ALE] password no valido");
		}
		
		if(dn == null ||  dn.trim().equals("")){
		   	throw new ServiceException("El dn no puede ir vacio");
		}
		
		if(compania == null ||  compania.trim().equals("")){
	    	throw new ServiceException("La compania no puede ir vacia");
	    }
		
		String respuesta = "";
		ValidaTokens tokens =  new ValidaTokens();	

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     compania               : " + compania);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token,  new MensajeLogBean());
			String companiaId = compania;
			if(compania.equals("1")){
				  try{	
					compania = oracle.getValorParametro(143); //iusacell
				  }catch (Exception e) {
					compania = "APPMIUSACELL";
				  }
			}else if(compania.equals("2")){
				try{
				  compania = oracle.getValorParametro(144); //unefon
				}catch (Exception e) {
					compania = "APPMIUNEFON";	
				}
			}else{
				compania = "MIATT";
			}
			
			sResponse.append(focalizacion.focalizacionConsultaWallets(dn, compania));
			
			if(sResponse != null && !sResponse.equals("")){
				listConsultaWalletsGeneral = ParseXMLFile.parseFocalizacionConsultaWallets(sResponse.toString(), companiaId, dn);
			}
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " + respuesta);

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				
				if(e.getLocalizedMessage().toLowerCase().contains("[ctrl]") == true){
		    		throw new ServiceException("[WARN] consultaWallets ["+dn+"] :: " + e.getLocalizedMessage());	
				}if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] consultaWallets ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] consultaWallets ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] consultaWallets ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("consultaWallets :: " + getLocalAddress(), initime);
		}

		return listConsultaWalletsGeneral;
	}

	public List<ServiciosBundlesAdicionales> obtenerServiciosBundles(String user, String pass, int version, String dn, String token) throws Throwable{
		
		long initime = System.currentTimeMillis();
        Logger.init("obtenerServiciosBundles");
		
		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}		
		 List<ServiciosBundlesAdicionales> response = new ArrayList<ServiciosBundlesAdicionales>();
		 ValidaTokens tokens =  new ValidaTokens();		 
		
		try{
			String ip = getClientIpXfire();
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     version                : " + version);
			Logger.write("     dn                     : " + dn);			
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + ip);

			tokens.validaToken("", token, new MensajeLogBean(""));
						
			response = OfertaComercial.getServiciosBundles(version, dn, 401043, ip);
			
			Logger.write("   + Respuesta              + ");
			if(response != null)
			{
				Logger.write("     ServiciosBundlesAdicionales            : " + response.size() + " registro(s)");
			}
			else
				Logger.write("     ServiciosBundlesAdicionales            : 0 registro(s)");
			
		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] obtenerServiciosBundles ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] obtenerServiciosBundles ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] obtenerServiciosBundles ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("obtenerServiciosBundles ["+dn+"] :: " + getLocalAddress(), initime);
		}

		return response;
	}
	
	public boolean cambioPlan(String user, String pass, String dn, int idNuevoPlan, int costo, String login, String token) throws Throwable
	{
		OracleProcedures oracle = new OracleProcedures();
		
		long initime = System.currentTimeMillis();
		Logger.init("cambioPlan");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}
		
		int validaPwd = oracle.validarPassword(dn, login);
		if(validaPwd != 0){
			throw new ServiceException("[WARN] Password incorrecto");
		}
		
		ValidaTokens tokens =  new ValidaTokens();		
		boolean respuesta = false;
		try
		{
			String ip = getClientIpXfire();
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");			
			Logger.write("     dn                     : " + dn);		
			Logger.write("     idNuevoPlan            : " + idNuevoPlan);
			Logger.write("     costo                  : " + costo);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + ip);

			tokens.validaToken("", token, new MensajeLogBean(""));
			
			respuesta = CambioPlan.cambiarPlan(dn, idNuevoPlan, costo);
			
			
		}
		catch (Exception e) {
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("Dn no puede ir vacio") 
						|| e.getLocalizedMessage().contains("El DN no puede realizar cambio de Plan")
						|| e.getLocalizedMessage().contains("El plan debe ser distinto al plan actual")
						|| e.getLocalizedMessage().contains("Sin saldo vigente")){
					throw new ServiceException("[WARN] cambioPlan ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] cambioPlan ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] cambioPlan ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}	
		finally{
			Logger.end("cambioPlan ["+dn+"] :: " + getLocalAddress(), initime);
		}
		
		return respuesta;
	}
	
	public boolean activarNumeroFrecuente(String user, String pass, String dn, Addons servicios, List<String> numerosFrecuentes, String login,String token) throws Throwable
	{
		OracleProcedures oracle = new OracleProcedures();
		
		long initime = System.currentTimeMillis();
		Logger.init("activarNumeroFrecuente");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}
		
		if(numerosFrecuentes == null || numerosFrecuentes.size() == 0)
		{
			Logger.write("      El numerosFrecuentes no es valido ");
			throw new ServiceException("numerosFrecuentes no valido");
		}
		
		int validaPwd = oracle.validarPassword(dn, login);
		if(validaPwd != 0){
			throw new ServiceException("[WARN] Password incorrecto");
		}
		
		
		ValidaTokens tokens =  new ValidaTokens();		
		boolean respuesta = false;
		try
		{
			String ip = getClientIpXfire();
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");			
			Logger.write("     dn                     : " + dn);					
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + ip);

			tokens.validaToken("", token, new MensajeLogBean(""));						
			respuesta = NumeroFrecuente.activarNumeroFrecuente(dn, servicios, numerosFrecuentes);
			
		}
		catch (Exception e) {
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("Sin información de la linea")){
					throw new ServiceException("[WARN] activarNumeroFrecuente ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] activarNumeroFrecuente ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] activarNumeroFrecuente ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}	
		finally{
			Logger.end("activarNumeroFrecuente ["+dn+"] :: " + getLocalAddress(), initime);
		}
		
		return respuesta;
	}
	
	public List<catalogoCambioPlanVO> getCatalogoCambioPlan(String user,
			String pass, String dn, String compania, String serviceClass, String token) throws ServiceException {
		
		List<catalogoCambioPlanVO> listConsultaCatalogoCambioPlan = new ArrayList<catalogoCambioPlanVO>();
		OracleProcedures oracle = new OracleProcedures();
		
		long initime = System.currentTimeMillis();
        Logger.init("getCatalogoCambioPlan");

        if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[WARN] usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			throw new ServiceException("[WARN] password no valido");
		}
		
		if(dn == null ||  dn.trim().equals("")){
		   	throw new ServiceException("El dn no puede ir vacio");
		}
		
		
		ValidaTokens tokens =  new ValidaTokens();	

		try{
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     dn                     : " + dn);
			Logger.write("     compañia	              : " + compania);
			Logger.write("     serviceClass           : " + serviceClass);
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + getClientIpXfire());
			
			tokens.validaToken(dn, token,  new MensajeLogBean());
			listConsultaCatalogoCambioPlan=oracle.getCatalogoCambioPlan(serviceClass,"111111",compania);
			
			
			Logger.write("   + Respuesta              + ");
			Logger.write("     respuesta              : " +   listConsultaCatalogoCambioPlan.size() + " registro(s)");

		}catch(Exception e){
			if(e != null && e.getLocalizedMessage() != null){
				
				if(e.getLocalizedMessage().toLowerCase().contains("[ctrl]") == true){
		    		throw new ServiceException("[WARN] getCatalogoCambioPlan ["+dn+"] :: " + e.getLocalizedMessage());	
				}if(e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] getCatalogoCambioPlan ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] getCatalogoCambioPlan ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] getCatalogoCambioPlan ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}
		finally{
			Logger.end("getCatalogoCambioPlan :: " + getLocalAddress(), initime);
		}

		return listConsultaCatalogoCambioPlan;
	}
	
	public int contratarServiciosUnefon(String user, String pass, String dn, Addons servicios, String password, String token) throws Throwable
	{
		OracleProcedures oracle = new OracleProcedures();
		
		long initime = System.currentTimeMillis();
		Logger.init("contratarServiciosUnefon");

		if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El usuario no es valido ");
			throw new ServiceException("usuario no valido");
		}
		if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
			Logger.write("      El password no es valido ");
			throw new ServiceException("password no valido");
		}				
		
		int validaPwd = oracle.validarPassword(dn, password);
        if(validaPwd != 0){
            throw new ServiceException("[WARN] Password incorrecto");
        }
		
		ValidaTokens tokens =  new ValidaTokens();		
		int respuesta = 0;
		try
		{
			String ip = getClientIpXfire();
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");			
			Logger.write("     dn                     : " + dn);					
			Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
			Logger.write("     remoteAddress          : " + ip);

			tokens.validaToken("", token, new MensajeLogBean(""));						
			respuesta = NumeroFrecuente.contratarServicio(dn, servicios);
			
		}
		catch (Exception e) {
			if(e != null && e.getLocalizedMessage() != null){
				if(e.getLocalizedMessage().contains("Sin información de la linea")){
					throw new ServiceException("[WARN] contratarServiciosUnefon ["+dn+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] contratarServiciosUnefon ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}else{
				throw new ServiceException("[ERR] contratarServiciosUnefon ["+dn+"] :: " + e.getLocalizedMessage());
			}
		}	
		finally{
			Logger.end("contratarServiciosUnefon ["+dn+"] :: " + getLocalAddress(), initime);
		}
		
		return respuesta;
	}
	
	public int bajaServiciosUnefon(String user, String pass, String dn, Addons servicios, String password, String token) throws Throwable
    {
        OracleProcedures oracle = new OracleProcedures();
        
        long initime = System.currentTimeMillis();
        Logger.init("contratarServiciosUnefon");

        if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
            Logger.write("      El usuario no es valido ");
            throw new ServiceException("usuario no valido");
        }
        if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
            Logger.write("      El password no es valido ");
            throw new ServiceException("password no valido");
        }               
        
        int validaPwd = oracle.validarPassword(dn, password);
        if(validaPwd != 0){
            throw new ServiceException("[WARN] Password incorrecto");
        }
        
        ValidaTokens tokens =  new ValidaTokens();      
        int respuesta = 0;
        try
        {
            String ip = getClientIpXfire();
            Logger.write("     user                   : -PROTEGIDO-");
            Logger.write("     pass                   : -PROTEGIDO-");          
            Logger.write("     dn                     : " + dn);                    
            Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
            Logger.write("     remoteAddress          : " + ip);

            tokens.validaToken("", token, new MensajeLogBean(""));                      
            respuesta = ServiciosUnefon.bajaServicio(dn, servicios);
            
        }
        catch (Exception e) {
            if(e != null && e.getLocalizedMessage() != null){
                if(e.getLocalizedMessage().contains("Sin información de la linea")){
                    throw new ServiceException("[WARN] contratarServiciosUnefon ["+dn+"] :: " + e.getLocalizedMessage());
                }else{
                    throw new ServiceException("[ERR] contratarServiciosUnefon ["+dn+"] :: " + e.getLocalizedMessage());
                }
            }else{
                throw new ServiceException("[ERR] contratarServiciosUnefon ["+dn+"] :: " + e.getLocalizedMessage());
            }
        }   
        finally{
            Logger.end("contratarServiciosUnefon ["+dn+"] :: " + getLocalAddress(), initime);
        }
        
        return respuesta;
    }
	
	   public List<ObtenerDetallesServicesClassVO> obtenerDetallesServiceClass(String user, String pass, String dn,String compania, String token) throws Throwable{
			
			CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
			CallServiceObtenerDetallesService obtenerServiceClass = new CallServiceObtenerDetallesService();  
			List<ConsultaWalletGeneralVO> listConsultaWalletsGeneral = new ArrayList<ConsultaWalletGeneralVO>();
			StringBuffer sResponse = new StringBuffer();
			String Response = "";
			OracleProcedures oracle = new OracleProcedures();
			List<ObtenerDetallesServicesClassVO> listdetalleServiceClass = new ArrayList<ObtenerDetallesServicesClassVO>();
			
			
			long initime = System.currentTimeMillis();
	        Logger.init("obtenerDetallesServiceClass");

	        if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				throw new ServiceException("[ALE] usuario no valido");
			}
			if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				throw new ServiceException("[ALE] password no valido");
			}
			
			if(dn == null ||  dn.trim().equals("")){
			   	throw new ServiceException("El dn no puede ir vacio");
			}
			
			if(compania == null ||  compania.trim().equals("")){
		    	throw new ServiceException("La compania no puede ir vacia");
		    }
			
			ValidaTokens tokens =  new ValidaTokens();	

			try{
				
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");
				Logger.write("     dn                     : " + dn);
				Logger.write("     compania               : " + compania);
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
				Logger.write("     remoteAddress          : " + getClientIpXfire());
				
				tokens.validaToken(dn, token,  new MensajeLogBean());

				if(compania.equals("1")){
					  try{	
						compania = oracle.getValorParametro(143); //iusacell
					  }catch (Exception e) {
						compania = "PVS";
					  }
				}else if(compania.equals("2")){
					try{
					  compania = oracle.getValorParametro(144); //unefon
					}catch (Exception e) {
						compania = "PVS";	
					}
				}else{
					compania="";
				}
				
				sResponse.append(focalizacion.focalizacionConsultaWallets(dn, compania));
				
				if(sResponse != null && !sResponse.equals("")){
					listConsultaWalletsGeneral = ParseXMLFile.parseFocalizacionConsultaWallets(sResponse.toString(), "", dn);
				}
				
				if(listConsultaWalletsGeneral != null && listConsultaWalletsGeneral.size() > 0){
				  if(listConsultaWalletsGeneral.get(0).getServiceClass() != null && !listConsultaWalletsGeneral.get(0).getServiceClass().equals("")){
					  
					 Logger.write("Service Class " + listConsultaWalletsGeneral.get(0).getServiceClass());
					 
					 Response = obtenerServiceClass.obtenerDetallesServiceClass(listConsultaWalletsGeneral.get(0).getServiceClass());
					 
					 if(Response != null && !Response.equals("")){
						 listdetalleServiceClass = ParseXMLFile.parseDetalleServiceClass(Response);
						 
						    Logger.write("   + Respuesta              + ");
						    Logger.write("   + DetalleServicesClass   + " + listdetalleServiceClass.size());
						 
					 }
				  }else{
					  throw new ServiceException("No se encontro services class para :: " + dn);  
				  }
				}else{
				  throw new ServiceException("No se encontro datos de consulta para :: " + dn);
				}

			}catch(Exception e){
				if(e != null && e.getLocalizedMessage() != null){
					
					if(e.getLocalizedMessage().toLowerCase().contains("[ctrl]") == true){
			    		throw new ServiceException("[WARN] obtenerDetallesServiceClass ["+dn+"] :: " + e.getLocalizedMessage());	
					}if(e.getLocalizedMessage().contains("ORA-20000")){
						throw new ServiceException("[WARN] obtenerDetallesServiceClass ["+dn+"] :: " + e.getLocalizedMessage());
					}else{
						throw new ServiceException("[ERR] obtenerDetallesServiceClass ["+dn+"] :: " + e.getLocalizedMessage());
					}
				}else{
					throw new ServiceException("[ERR] obtenerDetallesServiceClass ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}
			finally{
				Logger.end("obtenerDetallesServiceClass :: " + getLocalAddress(), initime);
			}

			return listdetalleServiceClass;
		}
//regiones.properties	  


	   @Override
		public String SemaphoreSaveCustomerInfo(String user, String pass, String mobileNumber, String customerName,
				String customerLastName, String customerMaidenName,
				String bankCardNumber, String cardHolder, String expirationDate,
				String postalCode,String token) throws ServiceException {
			
			CallServiceSemaphore semaforoTDC =new CallServiceSemaphore();
			long initime = System.currentTimeMillis();
			Logger.init("SemaphoreSaveCustomerInfo");

			if (user.trim().equals("") || !user.trim().equals("SEmAphoReKEbI12XQ1MIjQ*/")) {
				Logger.write("      El usuario no es valido ");
				throw new ServiceException("usuario no valido");
			}
			if (pass.trim().equals("") || !pass.trim().equals("SEmAphoReKEbI12XQ1MIjQ*/")) {
				Logger.write("      El password no es valido ");
				throw new ServiceException("password no valido");
			}				
			
			ValidaTokens tokens =  new ValidaTokens();		
			String respuesta = "";
			try
			{
				String ip = getClientIpXfire();
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");			
				Logger.write("   + Parametros             + ");
				Logger.write("     DN                     : "+ mobileNumber );
				Logger.write("     Nombre Cliente         : "+ customerName );
				Logger.write("     Primer Apellido        : "+ customerLastName );
				Logger.write("     Apellido de Soltero    : "+ customerMaidenName );
				Logger.write("     NumTarjeta             : "+ bankCardNumber );
				Logger.write("     Titular Tarjeta         : "+ cardHolder );
				Logger.write("     Fecha que expira       : "+ expirationDate );
				Logger.write("     Codigo Postal          : "+ postalCode );
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
				Logger.write("     remoteAddress          : " + ip);

				tokens.validaToken("", token, new MensajeLogBean(""));						

				if(semaforoTDC.SemaphoreSaveCustomerInfo(mobileNumber, customerName, customerLastName,customerMaidenName, bankCardNumber,cardHolder,expirationDate,postalCode))
				{
					respuesta="Registro guardado correctamente";
				}else{
					respuesta="No se pudo guardar el registro";
				}
			
			}catch(Exception e){
				if(e != null && e.getLocalizedMessage() != null){
					if(e.getLocalizedMessage().contains("[ctrl]") || e.getLocalizedMessage().contains("ORA-20000")){
						throw new ServiceException("[WARN] SemaphoreSaveCustomerInfo ["+mobileNumber+"] :: " + e.getLocalizedMessage());
					}else{
						throw new ServiceException("[ERR] SemaphoreSaveCustomerInfo ["+mobileNumber+"] :: " + e.getLocalizedMessage());
					}
				}else{
					throw new ServiceException("[ERR] SemaphoreSaveCustomerInfo ["+mobileNumber+"] :: " + e.getLocalizedMessage());
				}
			}finally{
				Logger.end("SemaphoreSaveCustomerInfo ["+mobileNumber+"] :: " + getLocalAddress(), initime);
			}
			return respuesta.toString();
		}


		public String SemaphoreUpdateBankCardInfo(String user, String pass,
				String mobileNumber, String bankCardNumber, String cardHolder,
				String expirationDate, String postalCode, String customerName, 
				String customerLastName, String customerMaidenName, String token) throws ServiceException {
			
			CallServiceSemaphore semaforoTDC =new CallServiceSemaphore();
			
			long initime = System.currentTimeMillis();
			Logger.init("SemaphoreSaveCustomerInfo");

			if (user.trim().equals("") || !user.trim().equals("SEmAphoReKEbI12XQ1MIjQ*/")) {
				Logger.write("      El usuario no es valido ");
				throw new ServiceException("usuario no valido");
			}
			if (pass.trim().equals("") || !pass.trim().equals("SEmAphoReKEbI12XQ1MIjQ*/")) {
				Logger.write("      El password no es valido ");
				throw new ServiceException("password no valido");
			}				
			
			ValidaTokens tokens =  new ValidaTokens();		
			String respuesta = "";
			try
			{
				String ip = getClientIpXfire();
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");			
				Logger.write("   + Parametros             + ");
				Logger.write("     DN                     : "+ mobileNumber );
				Logger.write("     NumTarjeta             : "+ bankCardNumber );
				Logger.write("     Titular Tarjeta         : "+ cardHolder );
				Logger.write("     Fecha que expira       : "+ expirationDate );
				Logger.write("     Codigo Postal          : "+ postalCode );
				Logger.write("     Nombre Cliente         : "+ customerName );
				Logger.write("     Primer Apellido        : "+ customerLastName );
				Logger.write("     Apellido de Soltero    : "+ customerMaidenName );
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
				Logger.write("     remoteAddress          : " + ip);

				tokens.validaToken("", token, new MensajeLogBean(""));						

				if(semaforoTDC.SemaphoreUpdateBankCardInfo(mobileNumber, bankCardNumber,cardHolder,expirationDate, postalCode, customerName, customerLastName,customerMaidenName))
				{
					respuesta="Registro actualizado correctamente";
				}else{
					respuesta="No se pudo actualizar el registro";
				}
				
			}catch(Exception e){
				if(e != null && e.getLocalizedMessage() != null){
					if(e.getLocalizedMessage().contains("[ctrl]") || e.getLocalizedMessage().contains("ORA-20000")){
						throw new ServiceException("[WARN] SemaphoreUpdateBankCardInfo ["+mobileNumber+"] :: " + e.getLocalizedMessage());
					}else{
						throw new ServiceException("[ERR] SemaphoreUpdateBankCardInfo ["+mobileNumber+"] :: " + e.getLocalizedMessage());
					}
				}else{
					throw new ServiceException("[ERR] SemaphoreUpdateBankCardInfo ["+mobileNumber+"] :: " + e.getLocalizedMessage());
				}
			}finally{
				Logger.end("SemaphoreUpdateBankCardInfo ["+mobileNumber+"] :: " + getLocalAddress(), initime);
			}
			return respuesta.toString();
			
		}
		
		public String SemaphoreDeleteBankCardInfo(String user, String pass,
				String mobileNumber, String bankCardNumber, String token)
				throws ServiceException {
			CallServiceSemaphore semaforoTDC =new CallServiceSemaphore();
			
			long initime = System.currentTimeMillis();
			Logger.init("SemaphoreDeleteBankCardInfo");

			if (user.trim().equals("") || !user.trim().equals("SEmAphoReKEbI12XQ1MIjQ*/")) {
				Logger.write("      El usuario no es valido ");
				throw new ServiceException("usuario no valido");
			}
			if (pass.trim().equals("") || !pass.trim().equals("SEmAphoReKEbI12XQ1MIjQ*/")) {
				Logger.write("      El password no es valido ");
				throw new ServiceException("password no valido");
			}				
			
			ValidaTokens tokens =  new ValidaTokens();		
			String respuesta = "";
			try
			{
				String ip = getClientIpXfire();
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");			
				Logger.write("   + Parametros             + ");
				Logger.write("     DN                     : "+ mobileNumber );
				Logger.write("     NumTarjeta             : "+ bankCardNumber );
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
				Logger.write("     remoteAddress          : " + ip);

				tokens.validaToken("", token, new MensajeLogBean(""));						

				if(semaforoTDC.SemaphoreDeleteBankCardInfo(mobileNumber, bankCardNumber))
				{
					respuesta="Registro eliminado correctamente";
				}else{
					respuesta="No se pudo eliminar el registro";
				}
				
			}catch(Exception e){
				if(e.getLocalizedMessage().contains("[ctrl]") || e.getLocalizedMessage().contains("ORA-20000")){
					throw new ServiceException("[WARN] SemaphoreDeleteBankCardInfo ["+mobileNumber+"] :: " + e.getLocalizedMessage());
				}else{
					throw new ServiceException("[ERR] SemaphoreDeleteBankCardInfo ["+mobileNumber+"] :: " + e.getLocalizedMessage());
				}
			}finally{
				Logger.end("SemaphoreDeleteBankCardInfo ["+mobileNumber+"] :: " + getLocalAddress(), initime);
			}
			return respuesta.toString();
		}

		public MensajeMailVO enviaSMS(String user, String pass, String dn, String bodySMS,
				int unidadNegocio, String token) throws Throwable {
			
			CallServiceMandaMensaje_Mail mandaMsj = new CallServiceMandaMensaje_Mail();
			
			long initime = System.currentTimeMillis();
			Logger.init("enviaSMS",unidadNegocio, false);

			if (user.trim().equals("") || !user.trim().equals("ENviASmSKEbI12XQ1MIjQ*/")) {
				Logger.write("      El usuario no es valido ");
				throw new ServiceException("usuario no valido");
			}
			if (pass.trim().equals("") || !pass.trim().equals("ENviASmSKEbI12XQ1MIjQ*/")) {
				Logger.write("      El password no es valido ");
				throw new ServiceException("password no valido");
			}				
			
			ValidaTokens tokens =  new ValidaTokens();		
			MensajeMailVO respuesta = null;
			String sResponse="";
			try
			{
				String ip = getClientIpXfire();
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");			
				Logger.write("   + Parametros             + ");
				Logger.write("     DN                     : " + dn );
				Logger.write("     UnidadNegocio          : " + unidadNegocio);
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
				Logger.write("     remoteAddress          : " + ip);

				tokens.validaTokenSMS(dn, token, new MensajeLogBean(""));
				sResponse= mandaMsj.enviaNuevoSMS(ip, dn, bodySMS);
				if(sResponse != null && !sResponse.equals(""))
					respuesta = ParseXMLFile.ParseMensajeCorreo(sResponse);
				
			}catch(Exception e){
				throw new ServiceException("[ERR] enviaSMS ["+dn+"] :: " + e.getLocalizedMessage());
			}finally{
				Logger.end("enviaSMS ["+dn+"] :: " + getLocalAddress(), initime);
			}
			return respuesta;
		}
		
		public String getIdOperador(String user, String pass, String dn, String token) throws Throwable{
			long initime = System.currentTimeMillis();
	        Logger.init("getIdOperador");
			if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
				throw new ServiceException("[WARN] usuario no valido");
			if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
				throw new ServiceException("[WARN] password no valido");
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("[WARN] DN no puede ir vacio");
						
			String idOperador = "";
			final ConsultaOperador consulta = new ConsultaOperador();;
			
			ValidaTokens tokens =  new ValidaTokens();				
			
			try{
				tokens.validaToken(dn, token, new MensajeLogBean());
				
				idOperador = consulta.getIdOperador(dn);
				
				Logger.write("   + Retorno idOperador     : " + idOperador);
					
			}catch (Exception e){
				if(e != null && e.getLocalizedMessage() != null){
					if(e.getLocalizedMessage().contains("ORA-20000")){
						throw new ServiceException("[WARN] getIdOperador ["+dn+"] :: " + e.getLocalizedMessage());
					}else{
						throw new ServiceException("[WARN] getIdOperador ["+dn+"] :: " + e.getLocalizedMessage());
					}
				}else{
					throw new ServiceException("[ERR] getIdOperador ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}finally{
				Logger.end("getIdOperador ["+dn+"] :: "  + getLocalAddress(), initime);
			}
			return idOperador;
		}
		
		public AbonoTiempoAireVO abonoTiempoAireBitFP(String user,String pass, String dn, String dnParaAbono, int anioExpira, String cdgSeguridad, String concepto, Double importe, int mesExpira, String numTarjeta, String tipoTarjeta, String ip, Long secuencia, String password, int tipoPlataforma, int compania, int sistemaOrigen, int dispositivo, String fingerPrint, String token) throws Throwable{
			
			long initime = System.currentTimeMillis();
	        Logger.init("abonoTiempoAireBitFP");

			if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				Logger.write(" el usuario no es valido " + user.trim());
				throw new ServiceException("usuario no valido");
			}
			if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				Logger.write(" el password no es valido " + user.trim());
				throw new ServiceException("password no valido");
			}
			
			ValidatorCharges.validaCantidadTA(importe);

			AbonoTiempoAireVO tiempoAireVO = new AbonoTiempoAireVO();
			ValidaTokens tokens =  new ValidaTokens();
			OracleProcedures oracle = new OracleProcedures();
			String respuesta = "";

			CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
			DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
			String sResponse = "";
			
			try{
				
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");
				Logger.write("     DN                     : "+ dn);
				Logger.write("     dnParaAbono            : "+ dnParaAbono);
				Logger.write("     Anio Expira            : "+ anioExpira);
				Logger.write("     Cdg Seguridad          : xxx");
				Logger.write("     Concepto               : "+ concepto);
				Logger.write("     Importe                : "+ importe);
				Logger.write("     Mes Expira             : "+ mesExpira);
				Logger.write("     Num Tarjeta            : "+ numTarjeta);
				Logger.write("     Tipo Tarjeta           : "+ tipoTarjeta);
				Logger.write("     Ip                     : "+ ip);
				Logger.write("     Secuencia              : "+ secuencia);
				Logger.write("     Password               : "+ password);
				Logger.write("     Tipo Plataforma        : "+ tipoPlataforma);
				Logger.write("     Dispositivo            : "+ dispositivo);
				Logger.write("     fingerPrint            : "+ fingerPrint);
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
				Logger.write("     remoteAddress          : " + getClientIpXfire());

				tokens.validaToken(dn, token, new MensajeLogBean());
				
				ip = "10.189.64.45";
				int tipoTecnologia = 0;
				int tipoLinea = 0;
				try{					
					sResponse = focalizacion.focalizacion(dn);
					if(sResponse != null && !sResponse.equals(""))
						descripcion = ParseXMLFile.parseFocalizacion(sResponse);					

					if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
						tipoTecnologia = 2;  //Etak
					}else{
						tipoTecnologia = 1;  //Legacy
					}					

					if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
						if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
							tipoLinea = 2;
						}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
							tipoLinea = 3;
						}
					}else {
						tipoLinea = 1; //prepago
					}
				}catch (Exception e) {
					Logger.write("focalizacion error - " + e.getLocalizedMessage());
				}
				try{
					respuesta = oracle.abonoTiempoAire(user, pass, dn,  dnParaAbono, anioExpira, cdgSeguridad, concepto, importe,mesExpira, numTarjeta, tipoTarjeta, ip,  secuencia, password, tipoPlataforma, compania, sistemaOrigen, tipoLinea, tipoTecnologia, dispositivo, token, 2021);
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				AbonoTiempoAireIn abonoTA = new AbonoTiempoAire();
				tiempoAireVO = abonoTA.flujoFingerPrint(token, dn, dnParaAbono, anioExpira, cdgSeguridad, concepto, importe, mesExpira, numTarjeta, tipoTarjeta, ip, secuencia, password, tipoPlataforma, respuesta, "", fingerPrint, null, false);
				
				try{
					if(tiempoAireVO != null)
						oracle.abonoTAAutorizador(respuesta, tiempoAireVO.getCodigoRespuestaAbonoTA(), tiempoAireVO.getNumeroAutorizacionAbonoTA(), tiempoAireVO.getNumTransaccionCaja(), tiempoAireVO.getNumeroAutorizacionCaja(), tiempoAireVO.getIdRegistroCaja(), 2121,"","");
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}

			}catch(Exception e){
				try{
					oracle.abonoTAAutorizador(respuesta, "", "", "", "", 0, 2121,"1",e.getMessage());
				}catch (Exception ex) {
					Logger.write("Bitacora: No se impacto la bitacora - ");
				}
				
				if(e != null && e.getMessage() != null){
					if(e.getMessage().contains("ORA-20000") || e.getMessage().contains("Err [Caja]") || e.getLocalizedMessage().contains("cardBlackList") 
							|| e.getLocalizedMessage().contains("TransXTarjeta")||e.getLocalizedMessage().contains("SemaphoreApplyCharge")
							|| e.getLocalizedMessage().contains("[ctrl]")){
						throw new ServiceException("[WARN] abonoTiempoAireBit ["+dn+"] " + e.getMessage());
					}else{
						throw new ServiceException("[ERR] abonoTiempoAireBit ["+dn+"] " + e.getMessage());
					}
				}else{
					throw new ServiceException("[ERR] abonoTiempoAireBit ["+dn+"] " + e.getMessage());
				}
			}
			finally{
				Logger.end("abonoTiempoAireBitFP ["+dn+"] :: " + getLocalAddress(), initime);
			}
			return tiempoAireVO;
		}
		
		public PagoFacturaResponseVO pagarFacturaFP(String user, String pass, String dn, CardVO tarjeta, int tipoPlataforma, int compania, int sistemaOrigen, int dispositivo, String password, String fingerPrint, String token) throws Throwable{
	 		long initime = System.currentTimeMillis();
	        Logger.init("pagarFacturaFP");

			if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				throw new ServiceException("[ALE] usuario no valido");
			}
			if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				throw new ServiceException("[ALE] password no válido");
			}

			ValidaTokens tokens =  new ValidaTokens();
			CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
			DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
			OracleProcedures oracle = new OracleProcedures();
			String sResponse = "";
			PagoFacturaResponseVO response = new PagoFacturaResponseVO();
			String numTx = "";
			
			try{
				
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");
				Logger.write("     dn                     : " + dn);
				Logger.write("     tipoPlataforma         : " + tipoPlataforma);
				Logger.write("     compania               : " + compania);
				Logger.write("     sistemaOrigen          : " + sistemaOrigen);
				Logger.write("     dispositivo            : " + dispositivo);
				Logger.write("     fingerPrint            : " + fingerPrint);
				if(tarjeta != null){
					Logger.write("     getAmount              : " + tarjeta.getAmount());
					Logger.write("     getCardExpiryMonth     : " + tarjeta.getCardExpiryMonth());
					Logger.write("     getCardExpiryYear      : " + tarjeta.getCardExpiryYear());
					Logger.write("     getCardNumber          : " + tarjeta.getCardNumber());
					Logger.write("     getCardSecurityCode    : " + tarjeta.getCardSecurityCode());
				}
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
				Logger.write("     remoteAddress          : " + getClientIpXfire());
				
				tokens.validaToken(dn, token,  new MensajeLogBean());
				
				int validaPwd = oracle.validarPassword(dn, password);
				if(validaPwd != 0){
					throw new ServiceException("No se pudo validar el password");
				}
				
				try{
					sResponse = focalizacion.focalizacion(dn);
					if(sResponse != null && !sResponse.equals(""))
						descripcion = ParseXMLFile.parseFocalizacion(sResponse);
					int tipoTecnologia = 0;
					if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
						tipoTecnologia = 2;
					}else{
						tipoTecnologia = 1;
					}
					int tipoLinea = 0;
					if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
						if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
							tipoLinea = 2;
						}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
							tipoLinea = 3;
						}
					}else {
						tipoLinea = 1;
					}
					//bitacora
					try{
						numTx = oracle.pagoFacturaEntrada(user, pass, dn, tarjeta, tipoPlataforma, compania, sistemaOrigen, dispositivo, tipoLinea, tipoTecnologia, fingerPrint, token);
					}catch (Exception e) {
						throw new ServiceException("Numero de transaccion invalido");
					}
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				PagarFacturain pagarFactura = new PagarFactura();
				response = pagarFactura.flujoFingerPrint(numTx, user, pass, dn, tarjeta, tipoPlataforma, compania, sistemaOrigen, dispositivo, password, "", "", fingerPrint, false, null, token);
				
				if(response != null){
					Logger.write("   + Respuesta              + ");
					Logger.write("     respuesta.FolioPago    : " + response.getFolioPago());
					Logger.write("     respuesta.NumAutBmx    : " + response.getNumAutBmx());
				}
			}catch(Exception e){
				//bitacora
				try{
					oracle.pagoFacturaSalida(numTx, "", "", e.getLocalizedMessage());
				}catch (Exception ex) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				if(e != null && e.getLocalizedMessage() != null){
	 				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("[Caja]") 
	 						|| e.getLocalizedMessage().contains("cardBlackList") || e.getLocalizedMessage().contains("TransXTarjeta")
	 						||e.getLocalizedMessage().contains("SemaphoreApplyCharge")||e.getLocalizedMessage().contains("[ctrl]")){
						throw new ServiceException("[WARN] pagarFactura ["+dn+"] :: " + e.getLocalizedMessage());
					}else{
						throw new ServiceException("[ERR] pagarFactura ["+dn+"] :: " + e.getLocalizedMessage());
					}
	 			}else{
					throw new ServiceException("[ERR] pagarFactura ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}
			finally{
				//bitacora
				try{
					oracle.pagoFacturaSalida(numTx, response.getFolioPago(), response.getNumAutBmx(), "");
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				Logger.end("pagarFacturaFP ["+dn+"] :: " + getLocalAddress(), initime);
			}

			return response;
	 	}
		
		public AbonoTiempoAireVO abonoTiempoAireBitFingerP(String user,String pass, String dn, String dnParaAbono, int anioExpira, String cdgSeguridad, String concepto, Double importe, int mesExpira, String numTarjeta, String tipoTarjeta, String ip, Long secuencia, String password, int tipoPlataforma, int compania, int sistemaOrigen, int dispositivo, String email, String fingerPrint, AddressVO address, String token) throws Throwable{
			
			long initime = System.currentTimeMillis();
	        Logger.init("abonoTiempoAireBitFingerP");

			if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				Logger.write(" el usuario no es valido " + user.trim());
				throw new ServiceException("usuario no valido");
			}
			if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				Logger.write(" el password no es valido " + user.trim());
				throw new ServiceException("password no valido");
			}
			
			ValidatorCharges.validaCantidadTA(importe);

			AbonoTiempoAireVO tiempoAireVO = new AbonoTiempoAireVO();
			ValidaTokens tokens =  new ValidaTokens();
			OracleProcedures oracle = new OracleProcedures();
			String respuesta = "";

			CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
			DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
			String sResponse = "";
			
			try{
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");
				Logger.write("     DN                     : "+ dn);
				Logger.write("     dnParaAbono            : "+ dnParaAbono);
				Logger.write("     Anio Expira            : "+ anioExpira);
				Logger.write("     Cdg Seguridad          : xxx");
				Logger.write("     Concepto               : "+ concepto);
				Logger.write("     Importe                : "+ importe);
				Logger.write("     Mes Expira             : "+ mesExpira);
				Logger.write("     Num Tarjeta            : "+ numTarjeta);
				Logger.write("     Tipo Tarjeta           : "+ tipoTarjeta);
				Logger.write("     Ip                     : "+ ip);
				Logger.write("     Secuencia              : "+ secuencia);
				Logger.write("     Password               : "+ password);
				Logger.write("     Tipo Plataforma        : "+ tipoPlataforma);
				Logger.write("     compania               : "+ compania);
				Logger.write("     sistemaOrigen          : "+ sistemaOrigen);
				Logger.write("     Dispositivo            : "+ dispositivo);
				Logger.write("     email                  : "+ email);
				Logger.write("     fingerPrint            : "+ fingerPrint);
				Logger.write("     calle                  : " + address.getCalle());
				Logger.write("     numeroExterior         : " + address.getNumeroExterior());				
				Logger.write("     numeroExterior         : " + address.getNumeroInterior());				
				Logger.write("     colonia                : " + address.getColonia());
				Logger.write("     estado                 : " + address.getEstado());
				Logger.write("     ciudad                 : " + address.getCiudad());
				Logger.write("     pais                   : " + address.getPais());
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));				
				Logger.write("     remoteAddress          : " + getClientIpXfire());

				tokens.validaToken(dn, token, new MensajeLogBean());
				
				int tipoTecnologia = 0;
				int tipoLinea = 0;
				try{
					//ip = "10.189.64.45";
					sResponse = focalizacion.focalizacion(dn);
					if(sResponse != null && !sResponse.equals(""))
						descripcion = ParseXMLFile.parseFocalizacion(sResponse);					

					if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
						tipoTecnologia = 2;  //Etak
					}else{
						tipoTecnologia = 1;  //Legacy
					}					

					if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
						if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
							tipoLinea = 2;
						}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
							tipoLinea = 3;
						}
					}else {
						tipoLinea = 1; //prepago
					}
				}catch (Exception e) {
					Logger.write("focalizacion error - " + e.getLocalizedMessage());
				}
				try{
					respuesta = oracle.abonoTiempoAire(user, pass, dn,  dnParaAbono, anioExpira, cdgSeguridad, concepto, importe,mesExpira, numTarjeta, tipoTarjeta, ip,  secuencia, password, tipoPlataforma, compania, sistemaOrigen, tipoLinea, tipoTecnologia, dispositivo, token, 2021);
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				
				AbonoTiempoAireIn abonoTA = null;
				String autorizadorDirecto = oracle.getValorParametro(208);
				if(autorizadorDirecto.equals("1")){
					abonoTA = new AbonoTiempoAireAutorizador();
				}else{
					abonoTA = new AbonoTiempoAire();
				}
				tiempoAireVO = abonoTA.flujoFingerPrint(token, dn, dnParaAbono, anioExpira, cdgSeguridad, concepto, importe, mesExpira, numTarjeta, tipoTarjeta, ip, secuencia, password, tipoPlataforma, respuesta, email, fingerPrint, address, true);
				
				try{
					if(tiempoAireVO != null)
						oracle.abonoTAAutorizador(respuesta, tiempoAireVO.getCodigoRespuestaAbonoTA(), tiempoAireVO.getNumeroAutorizacionAbonoTA(), tiempoAireVO.getNumTransaccionCaja(), tiempoAireVO.getNumeroAutorizacionCaja(), tiempoAireVO.getIdRegistroCaja(), 2121,"","");
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}

			}catch(Exception e){
				try{
					oracle.abonoTAAutorizador(respuesta, "", "", "", "", 0, 2121,"1",e.getMessage());
				}catch (Exception ex) {
					Logger.write("Bitacora: No se impacto la bitacora - ");
				}
				
				if(e != null && e.getMessage() != null){
					if(e.getMessage().contains("ORA-20000") || e.getMessage().contains("Err [Caja]") || e.getLocalizedMessage().contains("cardBlackList") 
							|| e.getLocalizedMessage().contains("TransXTarjeta")||e.getLocalizedMessage().contains("SemaphoreApplyCharge")
							|| e.getLocalizedMessage().contains("[ctrl]")){
						throw new ServiceException("[WARN] abonoTiempoAireBit ["+dn+"] " + e.getMessage());
					}else{
						throw new ServiceException("[ERR] abonoTiempoAireBit ["+dn+"] " + e.getMessage());
					}
				}else{
					throw new ServiceException("[ERR] abonoTiempoAireBit ["+dn+"] " + e.getMessage());
				}
			}
			finally{
				Logger.end("abonoTiempoAireBitFingerP ["+dn+"] :: " + getLocalAddress(), initime);
			}
			return tiempoAireVO;
		}
		
		public PagoFacturaResponseVO pagarFacturaFingerP(String user, String pass, String dn, CardVO tarjeta, int tipoPlataforma, int compania, int sistemaOrigen, int dispositivo, String password, String ip, String email, String fingerPrint, AddressVO address, String token) throws Throwable{
	 		long initime = System.currentTimeMillis();
	        Logger.init("pagarFacturaFingerP");

			if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				throw new ServiceException("[ALE] usuario no valido");
			}
			if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				throw new ServiceException("[ALE] password no válido");
			}

			ValidaTokens tokens =  new ValidaTokens();
			CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
			DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
			OracleProcedures oracle = new OracleProcedures();
			String sResponse = "";
			PagoFacturaResponseVO response = new PagoFacturaResponseVO();
			String numTx = "";
			
			try{
				
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");
				Logger.write("     dn                     : " + dn);
				Logger.write("     tipoPlataforma         : " + tipoPlataforma);
				Logger.write("     compania               : " + compania);
				Logger.write("     sistemaOrigen          : " + sistemaOrigen);
				Logger.write("     dispositivo            : " + dispositivo);
				Logger.write("     ip                     : " + ip);
				Logger.write("     email                  : " + email);
				Logger.write("     fingerPrint            : " + fingerPrint);
				if(tarjeta != null){
					Logger.write("     getAmount              : " + tarjeta.getAmount());
					Logger.write("     getCardExpiryMonth     : " + tarjeta.getCardExpiryMonth());
					Logger.write("     getCardExpiryYear      : " + tarjeta.getCardExpiryYear());
					Logger.write("     getCardNumber          : " + tarjeta.getCardNumber());
					Logger.write("     getCardSecurityCode    : " + tarjeta.getCardSecurityCode());
				}
				
				Logger.write("     calle                  : " + address.getCalle());
				Logger.write("     numeroExterior         : " + address.getNumeroExterior());				
				Logger.write("     numeroExterior         : " + address.getNumeroInterior());				
				Logger.write("     colonia                : " + address.getColonia());
				Logger.write("     estado                 : " + address.getEstado());
				Logger.write("     ciudad                 : " + address.getCiudad());
				Logger.write("     pais                   : " + address.getPais());
				
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
				Logger.write("     remoteAddress          : " + getClientIpXfire());
				
				tokens.validaToken(dn, token,  new MensajeLogBean());
				
				int validaPwd = oracle.validarPassword(dn, password);
				if(validaPwd != 0){
					throw new ServiceException("No se pudo validar el password");
				}
				
				try{
					sResponse = focalizacion.focalizacion(dn);
					if(sResponse != null && !sResponse.equals(""))
						descripcion = ParseXMLFile.parseFocalizacion(sResponse);
					int tipoTecnologia = 0;
					if(descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
						tipoTecnologia = 2;
					}else{
						tipoTecnologia = 1;
					}
					int tipoLinea = 0;
					if(descripcion.getIsPostpagoOrHibrido() != null && !descripcion.getIsPostpagoOrHibrido().equals("")){
						if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("pospago")){
							tipoLinea = 2;
						}else if(descripcion.getIsPostpagoOrHibrido().toLowerCase().equals("hibrido")){
							tipoLinea = 3;
						}
					}else {
						tipoLinea = 1;
					}
					//bitacora
					try{
						numTx = oracle.pagoFacturaEntrada(user, pass, dn, tarjeta, tipoPlataforma, compania, sistemaOrigen, dispositivo, tipoLinea, tipoTecnologia, fingerPrint, token);
					}catch (Exception e) {
						throw new ServiceException("Numero de transaccion invalido");
					}
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				PagarFacturain pagarFactura = null;
				String autorizadorDirecto = oracle.getValorParametro(208);
				if(autorizadorDirecto.equals("1")){
					pagarFactura = new PagarFacturaAutorizador();
				}else{
					pagarFactura = new PagarFactura();
				}				
				response = pagarFactura.flujoFingerPrint(numTx, user, pass, dn, tarjeta, tipoPlataforma, compania, sistemaOrigen, dispositivo, password, ip, email, fingerPrint, true, address, token);
				
				if(response != null){
					Logger.write("   + Respuesta              + ");
					Logger.write("     respuesta.FolioPago    : " + response.getFolioPago());
					Logger.write("     respuesta.NumAutBmx    : " + response.getNumAutBmx());
				}
			}catch(Exception e){
				//bitacora
				try{
					oracle.pagoFacturaSalida(numTx, "", "", e.getLocalizedMessage());
				}catch (Exception ex) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				if(e != null && e.getLocalizedMessage() != null){
	 				if(e.getLocalizedMessage().contains("ORA-20000") || e.getLocalizedMessage().contains("[Caja]") 
	 						|| e.getLocalizedMessage().contains("cardBlackList") || e.getLocalizedMessage().contains("TransXTarjeta")
	 						||e.getLocalizedMessage().contains("SemaphoreApplyCharge")||e.getLocalizedMessage().contains("[ctrl]")){
						throw new ServiceException("[WARN] pagarFactura ["+dn+"] :: " + e.getLocalizedMessage());
					}else{
						throw new ServiceException("[ERR] pagarFactura ["+dn+"] :: " + e.getLocalizedMessage());
					}
	 			}else{
					throw new ServiceException("[ERR] pagarFactura ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}
			finally{
				//bitacora
				try{
					oracle.pagoFacturaSalida(numTx, response.getFolioPago(), response.getNumAutBmx(), "");
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				Logger.end("pagarFacturaFingerP ["+dn+"] :: " + getLocalAddress(), initime);
			}

			return response;
	 	}				
		
		public ConsultaSrScVO getConsultaSRSC(String user, String pass, String dn, String token) throws Throwable{
			ConsultaSrScVO respuesta = new ConsultaSrScVO();
			long initime = System.currentTimeMillis();
	        Logger.init("getConsultaSRSC");

	        if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjS-/"))
				throw new ServiceException("[WARN] usuario no valido");
			if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjS-/"))
				throw new ServiceException("[WARN] password no valido");
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("[WARN] DN no puede ir vacio");
	        
			ValidaTokens tokens =  new ValidaTokens();
			CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
			String sResponse = "";
			try {
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");
				Logger.write("     dn                     : " + dn);
				
				tokens.validaToken(dn, token,  new MensajeLogBean());

				sResponse = focalizacion.focalizacionConsultaSRSC(dn);
				if(sResponse != null && !sResponse.equals("")){
					respuesta = ParseXMLFile.parseConsultaSRSC(sResponse);
				}
					
			}catch (Exception e) {
				if(e != null && e.getLocalizedMessage() != null){
	 				if(e.getLocalizedMessage().contains("ORA-20000") ||e.getLocalizedMessage().contains("[ctrl]")){
						throw new ServiceException("[WARN] getConsultaSRSC ["+dn+"] :: " + e.getLocalizedMessage());
					}else{
						throw new ServiceException("[ERR] getConsultaSRSC ["+dn+"] :: " + e.getLocalizedMessage());
					}
	 			}else{
					throw new ServiceException("[ERR] getConsultaSRSC ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}finally{
				Logger.end("getConsultaSRSC ["+dn+"] :: " + getLocalAddress(), initime);
			}
			
			return respuesta;
		}
		
		public ResponseCancelacionVO getFolioCancelacion(String user, String pass, String dn, String dnContacto, String strNota, String token) throws Throwable{
			ResponseCancelacionVO respuesta = null;						
			
			long initime = System.currentTimeMillis();
	        Logger.init("getFolioCancelacion");

	        if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
				throw new ServiceException("[WARN] usuario no valido");
			if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
				throw new ServiceException("[WARN] password no valido");
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("[WARN] DN no puede ir vacio");
	        
			ValidaTokens tokens =  new ValidaTokens();			
			try {
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");
				Logger.write("     dn                     : " + dn);
				
				tokens.validaToken(dn, token,  new MensajeLogBean());
				final CancelacionLinea cancelacion = new CancelacionLinea();
				respuesta = cancelacion.generaCancelacion(dn, dnContacto, strNota);
				
				//respuesta = new ResponseCancelacionVO();	
				//respuesta.setNoInteractionId(1);
				//respuesta.setCallId(121);
				//respuesta.setNoErrorCode(0);
				//respuesta.setNoSolicitudId(1234);
				//respuesta.setNoErrorMsg("Exito");
				
			}catch (Exception e) {
				if(e != null && e.getLocalizedMessage() != null){
	 				if(e.getLocalizedMessage().contains("ORA-20000") ||e.getLocalizedMessage().contains("[ctrl]")){
						throw new ServiceException("[WARN] getFolioCancelacion ["+dn+"] :: " + e.getLocalizedMessage());
					}else{
						throw new ServiceException("[ERR] getFolioCancelacion ["+dn+"] :: " + e.getLocalizedMessage());
					}
	 			}else{
					throw new ServiceException("[ERR] getFolioCancelacion ["+dn+"] :: " + e.getLocalizedMessage());
				}
			}finally{
				Logger.end("getFolioCancelacion ["+dn+"] :: " + getLocalAddress(), initime);
			}
			
			return respuesta;
		}
		
		public UfmiVO getUfmi(String user, String pass, String dn, String token) throws Throwable
		{
			long initime = System.currentTimeMillis();
			Logger.init("getUfmi");

			if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
				throw new ServiceException("[WARN] usuario no valido");
			if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/"))
				throw new ServiceException("[WARN] password no valido");
			if(dn == null || dn.trim().equals(""))
				throw new ServiceException("[WARN] DN no puede ir vacio");

			ValidaTokens tokens =  new ValidaTokens();	
			UfmiVO respuesta = null;
			try {
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");
				Logger.write("     dn                     : " + dn);

				tokens.validaToken(dn, token,  new MensajeLogBean());
				final CallServiceConsultasNum wsNum = new CallServiceConsultasNum();

				final String responseWS = wsNum.getUfmiByMdn(dn);
				if(responseWS != null && !responseWS.equals("")){
					respuesta = ParseXMLServices.parseUfmiByMdn(responseWS);
				}else{
					respuesta = new UfmiVO();
				}
			}catch (Exception e) {				
				throw new ServiceException("[ERR] getUfmi ["+dn+"] :: " + e.getLocalizedMessage());				
			}finally{
				Logger.end("getUfmi ["+dn+"] :: " + getLocalAddress(), initime);
			}

			return respuesta;
		}
		
		public List<Referencia> getRefBancarias(final String user, final String pass, final String dn, final String facturaUrl, final String token) throws Throwable{

			long initime = System.currentTimeMillis();
	        Logger.init("getRefBancarias");
			
			if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				Logger.write("     El usuario no es valido ");
				throw new ServiceException("[ALE] usuario no valido");
			}
			if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				Logger.write("     El password no es valido ");
				throw new ServiceException("[ALE] password no valido");
			}
			
			if (facturaUrl == null || facturaUrl.trim().equals("") || facturaUrl.trim().equalsIgnoreCase("null")) {
				Logger.write("     La urlFactura no es vlido ");
				throw new ServiceException("[ALE] urlFactura no valido");
			}
			final OracleProcedures oracle = new OracleProcedures();
			try{				
				final String datosFactura = oracle.getValorParametro(115);
				final String[] datosFacturaPart = datosFactura.split("\\|");
				String url = datosFacturaPart[0];
				if(url != null)
				{
					url = url.trim();
					if(!url.equals(""))
					{
						if (!facturaUrl.trim().startsWith(url)) {
							Logger.write("     La urlFactura no es valido ");
							throw new ServiceException("[ALE] urlFactura no valido");
						}
					}
				}
			}catch(Exception ex){					
				if(ex.getLocalizedMessage().contains("[ALE] urlFactura no valido"))
					throw new ServiceException(ex.getLocalizedMessage());
				else
					Logger.write("     [Exception] al validar urlFactura : " + ex.getLocalizedMessage());
			}

			final ValidaTokens tokens =  new ValidaTokens();
			final List<Referencia> respuesta = new ArrayList<Referencia>();			
			
			try{
				
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");
				Logger.write("     DN                     : " + dn);
				Logger.write("     UrlFactura             : " + facturaUrl);
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
				Logger.write("     remoteAddress          : " + getClientIpXfire());

				tokens.validaToken(dn, token, new MensajeLogBean(""));
								 
				final EnviaCorreoDetalle detalle = new EnviaCorreoDetalle();
				final DetalleFacturaVO detalleFact = detalle.detalleFactura(dn,facturaUrl);
								
				if(detalleFact != null && StringUtils.isNoneEmpty(detalleFact.getCuenta())){
					final String paramBancos = oracle.getValorParametro(269);
					final String[] bancos = paramBancos.split("\\|");
					final String refer = ReferenciaBancaria.getReferencia(detalleFact.getCuenta());
					final String digitoVerif = ReferenciaBancaria.getDigitoVerificador(refer);

					if(StringUtils.isNoneEmpty(refer) && StringUtils.isNoneEmpty(digitoVerif)){
						for(final String bank : bancos){
							final Referencia referencia = new Referencia();
							final String[] ctas = bank.split("#");
							referencia.setBanco(ctas[0]);
							if(StringUtils.isNoneEmpty(ctas[0]) && "BANAMEX".equalsIgnoreCase(ctas[0].trim())){								
								try{
									final long convBanamex = Long.parseLong(ctas[1]);
									referencia.setReferencia(ReferenciaBancaria.calculaReferenciaBanamex(detalleFact.getCuenta(),convBanamex));
								}catch (NumberFormatException e) {
									Logger.write("No se pudo obtener la referencia Banamex: " + dn);
								}								
							}else{
								referencia.setReferencia(ctas[1] + refer + digitoVerif);
							}
							referencia.setCuenta(ctas[1]);
							respuesta.add(referencia);
						}
					}
					else{
						Logger.write("No se pudo obtener la referencia de la factura ingresada: " + dn);
						throw new ServiceException("No se pudo obtener la referencia de la factura ingresada");	
					}
				}else{
					Logger.write("No se pudo obtener el numero de cuenta de la factura: "  + dn);
					throw new ServiceException("No se pudo obtener el numero de cuenta de la factura");	
				}
				
												
				
			}catch(Exception e){			
					throw new ServiceException("[ERR] getRefBancarias ["+dn+" :: " + e.getLocalizedMessage());				
			}
			finally{
				Logger.end("getRefBancarias :: " + getLocalAddress(), initime);
			}

			return respuesta;
		}
		
		
		public String getOferta (String user, String pass,  String token) throws Throwable{											
			long initime = System.currentTimeMillis();
	        Logger.init("getOferta");

	        if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1Mdld*/"))
				throw new ServiceException("[WARN] usuario no valido");
			if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1Mdld*/"))
				throw new ServiceException("[WARN] password no valido");
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			final ValidaTokens tokens =  new ValidaTokens();		
			final OfertaVO respuesta = new OfertaVO();
			final OracleProcedures oracle = new OracleProcedures();
			String ofertaResp = "{}";
			try {								
				tokens.validaToken("", token,  new MensajeLogBean());				
				respuesta.setOferta(oracle.getOferta());
				ofertaResp = new Gson().toJson(respuesta);				
			}catch (Exception e) {
					throw new ServiceException("[ERR] getOferta  :: " + e.getLocalizedMessage());
			}finally{
				Logger.end("getOferta               :: " + getLocalAddress(), initime);
			}
			return ofertaResp;
		}
		
		public String getPuntos (final String user, final String pass, final int  idFamilia, final String idPlan,  final String token) throws Throwable{											
			long initime = System.currentTimeMillis();
	        Logger.init("getPuntos");

	        if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1Mdld*/"))
				throw new ServiceException("[WARN] usuario no valido");
			if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1Mdld*/"))
				throw new ServiceException("[WARN] password no valido");
			
			Logger.write("     user                   : -PROTEGIDO-");
			Logger.write("     pass                   : -PROTEGIDO-");
			Logger.write("     familia                : " + idFamilia);
			Logger.write("     idPlan                 : " + idPlan);
			
			final ValidaTokens tokens =  new ValidaTokens();					
			final OracleProcedures oracle = new OracleProcedures();
			String puntosJson = "{}";
			try {								
				tokens.validaToken("", token,  new MensajeLogBean());				
				final PuntosVO respuesta = oracle.getPuntos(idFamilia, idPlan);
				puntosJson = new Gson().toJson(respuesta);				
			}catch (Exception e) {
					throw new ServiceException("[ERR] getPuntos  :: " + e.getLocalizedMessage());
			}finally{
				Logger.end("getPuntos               :: " + getLocalAddress(), initime);
			}
			return puntosJson;
		}
		
		public List<ServicioVO> consultaCompraPaqServicios(final String user, final String pass, final String idPlan, final String operador, final String tipoOferta, final String token) throws Throwable{

			long initime = System.currentTimeMillis();
	        Logger.init("consultaCompraPaqServicios");
			
			if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				Logger.write("     El usuario no es valido ");
				throw new ServiceException("[ALE] usuario no valido");
			}
			if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				Logger.write("     El password no es valido ");
				throw new ServiceException("[ALE] password no valido");
			}						

			ValidaTokens tokens =  new ValidaTokens();			
			List<ServicioVO> response = null;
			try{
				
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");
				Logger.write("     idPlan                 : " + idPlan);
				Logger.write("     operador               : " + operador);
				Logger.write("     tipoOferta             : " + tipoOferta);
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
				Logger.write("     remoteAddress          : " + getClientIpXfire());

				tokens.validaToken("", token, new MensajeLogBean(""));		
				final CallServiceFocalizacion wsFoca = new CallServiceFocalizacion();
				final String responseWS = wsFoca.consultaCompraServicios(idPlan, operador, tipoOferta);				
				response = ParseXMLServices.parseFocaConsultaCompraServicios(responseWS);
				
			}catch(Exception e){			
					throw new ServiceException("[ERR] consultaCompraPaqServicios ["+idPlan+"] :: " + e.getLocalizedMessage());				
			}
			finally{
				Logger.end("consultaCompraPaqServicios :: " + getLocalAddress(), initime);
			}	
			
			return response;
		}
    
		public RespuestaServicios operacionServicioPaq(final String user, final String pass, final String idLinea, final String passAutorizacion, final int tipo, final Servicio[] servicios, final String token) throws Throwable{
		    
	        long initime = System.currentTimeMillis();
	        Logger.init("operacionServicioPaq (" + idLinea + ")");
	        
	        if (user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
	            Logger.write("     El usuario no es valido ");
	            throw new ServiceException("[ALE] usuario no valido");
	        }
	        if (pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
	            Logger.write("     El password no es valido ");
	            throw new ServiceException("[ALE] password no valido");
	        }                       
	    
	        ValidaTokens tokens =  new ValidaTokens();          
	        mx.com.iusacell.middleware.servicios.gestion.RespuestaServicios response = null;
	        RespuestaServicios respuesta = null;
	        
	        OracleProcedures oracle = new OracleProcedures();
	        
	        try{
	            
	            Logger.write("     user                   : -PROTEGIDO-");
	            Logger.write("     pass                   : -PROTEGIDO-");
	            Logger.write("     idLinea                : " + idLinea);
	            Logger.write("     tipo                   : " + tipo); 
	            Logger.write("     servicios              : " + (ArrayUtils.isNotEmpty(servicios)?servicios.length:"0"));
	            Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
	            Logger.write("     remoteAddress          : " + getClientIpXfire());
	    
	            tokens.validaToken("", token, new MensajeLogBean(""));     
	            
	            int validaPwd = oracle.validarPassword(idLinea, passAutorizacion);
	            if(validaPwd != 0){
	                throw new ServiceException("No se pudo validar el password");
	            }
	            
	            final CallServiceGestionServicios wsFoca = new CallServiceGestionServicios();
	            
	            CallServiceFocalizacion foca = new CallServiceFocalizacion();
	            DatosFocalizacionFocaVO responseFoca = new DatosFocalizacionFocaVO();
	            String sResponse = "";
	            boolean esPrepagoHibrido = false;
	            try{
	                sResponse = foca.focalizacion(idLinea);
	                if(sResponse != null && !sResponse.equals("")){
	                   responseFoca = ParseXMLFile.parseDatosFocalizacionFoca(sResponse.toString());
	                   if(responseFoca.getIsPostpagoOrHibridoFoca() == null){
	                       esPrepagoHibrido = true;
	                   }else if(responseFoca.getIsPostpagoOrHibridoFoca() != null && responseFoca.getIsPostpagoOrHibridoFoca().equalsIgnoreCase("HIBRIDO")){
	                	   esPrepagoHibrido = true;
	                   }else{
	                	   esPrepagoHibrido = false;
	                   }
	                }   
	            }catch (ServiceException e) {
	                esPrepagoHibrido = false;
	            }
	            
	            if(esPrepagoHibrido)
	            {
	                sResponse = "";
	                CallServiceConsultaPrepago consultaPrepago = new CallServiceConsultaPrepago();
	                sResponse = consultaPrepago.consultaPrepago(idLinea);
	                String idLineaPrepago = "";
	                if(sResponse != null && !sResponse.equals("")){
	                    idLineaPrepago = ParseXMLFile.ParseConsultaPrepago(sResponse);
	                }
	                
	                if(idLineaPrepago == null || idLineaPrepago.length() == 0){
	                    response = new mx.com.iusacell.middleware.servicios.gestion.RespuestaServicios();
	                    response.setRespuesta(false);
	                    response.setMensaje("Sin informacion de la linea");
	                }else{
	                	
//	                	[ini] SE AGREGA EN "HARDCODE" ID 30 PARA SERVICIO PRIP
	                	try{
	                		if(servicios != null){
	                			int tamanioServicios = servicios.length;
	                			for(int indice = 0; indice<tamanioServicios; indice++){
	                				int tamanioVigencia = servicios[indice].getVigencias().length;
	                				for(int indVigencia = 0; indVigencia <tamanioVigencia; indVigencia++){
	                					servicios[indice].getVigencias()[indVigencia].setCantidad(30);
	                				}
	                			}
	                		}
	                	}catch (Exception exc) {
	                		Logger.write("     (X) EXCEPTION :: al reemplazar valor 30 para servicio PRIP :: " + exc.getMessage());
						}
	                	
//	                	[fin] SE AGREGA EN "HARDCODE" ID 30 PARA SERVICIO PRIP
	                	
	                    final Linea   linea     = new Linea();
	                    linea.setId(idLineaPrepago);
	                    linea.setTipo(0);
	                    linea.setServicios(servicios);
	                    final Usuario usuario   = new Usuario();
	                    usuario.setId("VTAPTALES");
	                    usuario.setModulo(13);
	                    final Cobro   cobro     = null; 
	                    final int     operacion = 0;
	                    response = wsFoca.operacionServicio(linea, usuario, cobro, operacion);             
	                }
	            }else{
	                response = new mx.com.iusacell.middleware.servicios.gestion.RespuestaServicios();
	                response.setRespuesta(false);
	                response.setMensaje("El tipo de linea Postpago no permite esta operacion");
	            }
	            
	        }catch(Exception e){
	            String mensaje = "";
	            if(e.getMessage().contains("Oops! Datos Incorrectos, Favor de Validar. Contraseña Incorrecta")){
	                mensaje = "Contraseña incorrecta";
	            }else{
	                mensaje = "[ERR] operacionServicioPaq ["+idLinea+","+tipo+","+(ArrayUtils.isNotEmpty(servicios)?servicios.length:"0") + "] :: " + e.getLocalizedMessage();
	            }
	                throw new ServiceException(mensaje);              
	        }
	        finally{
	            Logger.end("operacionServicioPaq :: " + getLocalAddress(), initime);
	            if(ObjectUtils.notEqual(response, null)){
	                respuesta = new RespuestaServicios();
	                respuesta.setCodigo(response.getCodigo());
	                respuesta.setFolioPreactivacion(response.getFolioPreactivacion());
	                respuesta.setIdTx(response.getIdTx());
	                respuesta.setMensaje(response.getMensaje());
	                respuesta.setRespuesta(response.getRespuesta());
	            }
	        }   
	        
	        return respuesta;
	    }
		
		public AbonoTiempoAireVO buyProductsOnline(String user,String pass, TransactionVO transaction, String token) throws Throwable
		{
			long initime = System.currentTimeMillis();
	        Logger.init("buyProductsOnline");

			if (StringUtils.isEmpty(user) || !user.trim().equals("AGhAxzwOwKEbI12Q1MIjQ*/")) {
				Logger.write(" el usuario no es valido " + user.trim());
				throw new ServiceException("usuario no valido");
			}
			if (StringUtils.isEmpty(pass) || !pass.trim().equals("AGhAxzwOwKEbI12Q1MIjQ*/")) {
				Logger.write(" el password no es valido " + user.trim());
				throw new ServiceException("password no valido");
			}	
			if(transaction == null){
				Logger.write(" transaction no valida " + user.trim());
				throw new ServiceException("transactionVO no valido");
			}
			
			if(transaction.getAddress() == null){
				Logger.write(" address no valida " + user.trim());
				throw new ServiceException("address no valido");
			}

			AbonoTiempoAireVO tiempoAireVO = new AbonoTiempoAireVO();
			ValidaTokens tokens =  new ValidaTokens();
			OracleProcedures oracle = new OracleProcedures();
			String respuesta = "";			
			
			try{
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");						
				Logger.write("     Anio Expira            : "+ transaction.getAnioExpira());
				Logger.write("     Cdg Seguridad          : xxx");
				Logger.write("     Concepto               : "+ transaction.getConcepto());
				Logger.write("     Importe                : "+ transaction.getImporte());
				Logger.write("     Mes Expira             : "+ transaction.getMesExpira());
				Logger.write("     Num Tarjeta            : "+ transaction.getNumTarjeta());
				Logger.write("     Tipo Tarjeta           : "+ transaction.getTipoTarjeta());
				Logger.write("     Ip                     : "+ transaction.getIpAddress());
				Logger.write("     Pedido                 : "+ transaction.getPedido());
				Logger.write("     Producto               : "+ transaction.getProducto());				
				Logger.write("     Tipo Plataforma        : "+ transaction.getTipoPlataforma());
				Logger.write("     compania               : "+ transaction.getCompania());
				Logger.write("     sistemaOrigen          : "+ transaction.getSistemaOrigen());
				Logger.write("     Dispositivo            : "+ transaction.getDispositivo());
				Logger.write("     email                  : "+ transaction.getEmail());
				Logger.write("     fingerPrint            : "+ transaction.getFingerPrint());				
				Logger.write("     Nombres                : "+ transaction.getAddress().getNombres());
				Logger.write("     Apellidos              : "+ transaction.getAddress().getApellidos());
				Logger.write("     Calle                  : "+ transaction.getAddress().getCalle());
				Logger.write("     Colonia                : "+ transaction.getAddress().getColonia());
				Logger.write("     Municipio              : "+ transaction.getAddress().getMunicipio());
				Logger.write("     Estado                 : "+ transaction.getAddress().getEstado());
				Logger.write("     CodigoPostal           : "+ transaction.getAddress().getCodigoPostal());				
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));				
				Logger.write("     remoteAddress          : " + getClientIpXfire());

				tokens.validaToken("", token, new MensajeLogBean());
				
				int tipoTecnologia = 0;
				int tipoLinea = 0;		
				long secuencia = Calendar.getInstance().getTimeInMillis();
				try{
					respuesta = oracle.abonoTiempoAire(user, pass, transaction.getPedido(),  transaction.getProducto(), transaction.getAnioExpira(), transaction.getCdgSeguridad(), transaction.getConcepto(), 
							transaction.getImporte(),transaction.getMesExpira(), transaction.getNumTarjeta(), transaction.getTipoTarjeta(), transaction.getIpAddress(),  
							secuencia, "", transaction.getTipoPlataforma(), transaction.getCompania(), transaction.getSistemaOrigen(), tipoLinea, 
							tipoTecnologia, transaction.getDispositivo(), token, 2021);
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}
				
				final CompraProductosAutorizador abonoTA = new CompraProductosAutorizador();				
				tiempoAireVO = abonoTA.compraProducto(token, transaction, respuesta, secuencia, true);
				
				try{
					if(tiempoAireVO != null)
						oracle.abonoTAAutorizador(respuesta, tiempoAireVO.getCodigoRespuestaAbonoTA(), tiempoAireVO.getNumeroAutorizacionAbonoTA(), tiempoAireVO.getNumTransaccionCaja(), tiempoAireVO.getNumeroAutorizacionCaja(), tiempoAireVO.getIdRegistroCaja(), 2121,"","");
				}catch (Exception e) {
					Logger.write("Bitacora: No se impacto la bitacora - " + e.getLocalizedMessage());
				}

			}catch(Exception e){
				try{
					oracle.abonoTAAutorizador(respuesta, "", "", "", "", 0, 2121,"1",e.getMessage());
				}catch (Exception ex) {
					Logger.write("Bitacora: No se impacto la bitacora - ");
				}
				
				if(e != null && e.getMessage() != null){
					if(e.getMessage().contains("ORA-20000") || e.getMessage().contains("Err [Caja]") || e.getLocalizedMessage().contains("cardBlackList") 
							|| e.getLocalizedMessage().contains("TransXTarjeta")||e.getLocalizedMessage().contains("SemaphoreApplyCharge")
							|| e.getLocalizedMessage().contains("[ctrl]")){
						throw new ServiceException("[WARN] buyProductsOnline ["+transaction.getPedido()+"] " + e.getMessage());
					}else{
						throw new ServiceException("[ERR] buyProductsOnline ["+transaction.getPedido()+"] " + e.getMessage());
					}
				}else{
					throw new ServiceException("[ERR] buyProductsOnline ["+transaction.getPedido()+"] " + e.getMessage());
				}
			}
			finally{
				Logger.end("buyProductsOnline ["+transaction.getPedido()+"] :: " + getLocalAddress(), initime);
			}
			return tiempoAireVO;
		}
		public BankCardAdditionalInfoVO getAditionalCardInfo(String user, String pass,String prefix, String token) throws Throwable
		{
			long initime = System.currentTimeMillis();
	        Logger.init("getValorParametro");

			if (user == null || user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				Logger.write("      El usuario no es valido");
				throw new ServiceException("[ALE] usuario no valido");
			}
			if (pass == null || pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
				Logger.write("      El password no es valido");
				throw new ServiceException("[ALE] password no válido");
			}	
			ValidaTokens tokens =  new ValidaTokens();	
			OracleProcedures oracleProcedures = new OracleProcedures();
			BankCardAdditionalInfoVO cardInfo = new BankCardAdditionalInfoVO();
			try {
				
				
				Logger.write("     user                   : -PROTEGIDO-");
				Logger.write("     pass                   : -PROTEGIDO-");
				Logger.write("     prefix              : " + prefix);
				Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
				Logger.write("     remoteAddress          : " + getClientIpXfire());

				tokens.validaToken("", token, new MensajeLogBean());	
				
				cardInfo = oracleProcedures.getBankCardAdditionalInfoBD(prefix) ;
				
				Logger.write("   + Respuesta              + ");
				Logger.write("     ISSUER         : " + cardInfo.getIssuer());
			}
			catch (Exception e) {
				if(e != null && e.getLocalizedMessage() != null){
					if(e.getLocalizedMessage().contains("ORA-20000")){
						throw new ServiceException("[WARN] getAditionalCardInfo ["+prefix+"] :: " + e.getLocalizedMessage());
					}else{
						throw new ServiceException("[ERR] getAditionalCardInfo ["+prefix+"] :: " + e.getLocalizedMessage());
					}
				}else{
					throw new ServiceException("[ERR] getAditionalCardInfo ["+prefix+"] :: " + e.getLocalizedMessage());
				}			
			} finally {
				Logger.end("getAditionalCardInfo ["+prefix+"] :: " + getLocalAddress(), initime);
			}

			return cardInfo;
		}
		
		
    /* ****************************************************************************************
     * 
     * 
     * 
     * ************************************************************************************* */
		
    public int agregaDomicilio(String user, String pass, String dn, DomicilioVO domicilioVO, int usuarioId, String token) throws Throwable
    {
        long initime = System.currentTimeMillis();
        Logger.init("agregaDomicilio (" + dn + ")");
        
        if(user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")){
            Logger.write(" (!) El usuario no es válido ");
            throw new ServiceException("[ALE] usuario no valido");
        }
        
        if(pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")){
            Logger.write(" (!) El password no es válido ");
            throw new ServiceException("[ALE] password no válido");
        }
        
        int domicilioID = -1;
        ValidaTokens tokens =  new ValidaTokens();
        
        try
        {
            Logger.write("     user                   : -PROTEGIDO-"                              );
            Logger.write("     pass                   : -PROTEGIDO-"                              );
            Logger.write("     dn                     : " + dn                                    );
            Logger.write("     calle                  : " + domicilioVO.getCalle()                );
            Logger.write("     numeroExterior         : " + domicilioVO.getNumeroExterior()       );
            Logger.write("     numeroInterior         : " + domicilioVO.getNumeroInterior()       );
            Logger.write("     colonia                : " + domicilioVO.getColonia()              );
            Logger.write("     estado                 : " + domicilioVO.getEstado()               );
            Logger.write("     ciudad                 : " + domicilioVO.getCiudad()               );
            Logger.write("     pais                   : " + domicilioVO.getPais()                 );
            Logger.write("     usuarioId              : " + usuarioId                             );
            Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
            Logger.write("     remoteAddress          : " + getClientIpXfire()                    );
            
            tokens.validaToken(dn, token, new MensajeLogBean());
            
            domicilioID = AltaTarjetaFrecuente.registraDomicilio(domicilioVO, usuarioId);
            
            Logger.write(" (<) Respuesta              : " + domicilioID);
        }
        catch(Exception exc)
        {
            if(exc != null && exc.getLocalizedMessage() != null){
                if(exc.getLocalizedMessage().contains("ORA-20000")      || exc.getLocalizedMessage().contains("Parametros de entrada") ||
                   exc.getLocalizedMessage().contains("Has registrado") || exc.getLocalizedMessage().contains("cardBlackList") ||
                   exc.getLocalizedMessage().contains("SemaphoreSaveCustomerInfo"))
                {
                    throw new ServiceException("[WARN] agregaDomicilio [" + dn + "] :: " + exc.getLocalizedMessage());
                }else{
                    throw new ServiceException("[ERR] agregaDomicilio [" + dn + "] :: " + exc.getLocalizedMessage());
                }
            }else{
                throw new ServiceException("[ERR] agregaDomicilio [" + dn + "] :: " + exc.getLocalizedMessage());
            }
        }
        finally{
            Logger.end("agregaDomicilio (" + dn + ") :: " + getLocalAddress(), initime);
        }
        
        return domicilioID;
    }
    
    public int eliminaDomicilio(String user, String pass, String dn, DomicilioVO domicilioVO, int usuarioId, String token) throws Throwable
    {
        long initime = System.currentTimeMillis();
        Logger.init("eliminaDomicilio (" + dn + ")");
        
        if(user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")){
            Logger.write(" (!) El usuario no es válido ");
            throw new ServiceException("[ALE] usuario no valido");
        }
        
        if(pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")){
            Logger.write(" (!) El password no es válido ");
            throw new ServiceException("[ALE] password no válido");
        }
        
        ValidaTokens tokens =  new ValidaTokens();
        
        int respuestaBD = -1;
        
        try
        {
            Logger.write("     user                   : -PROTEGIDO-");
            Logger.write("     pass                   : -PROTEGIDO-");
            Logger.write("     dn                     : " + dn                                    );
            Logger.write("     domicilioID            : " + domicilioVO.getDomicilioID());
            Logger.write("     usuarioId              : " + usuarioId);
            Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
            Logger.write("     remoteAddress          : " + getClientIpXfire());
            
            tokens.validaToken(dn, token, new MensajeLogBean());
            
            respuestaBD = AltaTarjetaFrecuente.eliminaDomicilio(domicilioVO, usuarioId);
            
            Logger.write(" (<) Respuesta              : " + respuestaBD);
        }
        catch(Exception e)
        {
            if(e != null && e.getLocalizedMessage() != null)
            {
                if(e.getLocalizedMessage().contains("ORA-20000")     || e.getLocalizedMessage().contains("Parametros de entrada") ||
                   e.getLocalizedMessage().contains("Has eliminado") || e.getLocalizedMessage().contains("cardBlackList")         || 
                   e.getLocalizedMessage().contains("SemaphoreSaveCustomerInfo"))
                {
                    throw new ServiceException("[WARN] eliminaDomicilio [" + dn +"] :: " + e.getLocalizedMessage());
                }else{
                    throw new ServiceException("[ERR] eliminaDomicilio [" + dn +"] :: " + e.getLocalizedMessage());
                }
            }else{
                throw new ServiceException("[ERR] eliminaDomicilio [" + dn +"] :: " + e.getLocalizedMessage());
            }
        }
        finally
        {
            Logger.end("eliminaDomicilio  (" + dn + ") :: " + getLocalAddress(), initime);
        }
        
        return respuestaBD;
    }
    
    public int vincularDomicilioTarjeta (String user, String pass, String numeroTarjeta, String dn, int domicilioID, int usuarioId, String token) throws Throwable
    {
        long initime = System.currentTimeMillis();

        Logger.init("vincularDomicilioTarjeta (" + dn + ")");

        if(user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
            Logger.write(" (!) El usuario no es válido ");
            throw new ServiceException("[ALE] usuario no valido");
        }

        if(pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")) {
            Logger.write(" (!) El password no es válido ");
            throw new ServiceException("[ALE] password no válido");
        }

        ValidaTokens tokens =  new ValidaTokens();

        int respuestaBD = -1;

        try
        {
            Logger.write("     user                   : -PROTEGIDO-"    );
            Logger.write("     pass                   : -PROTEGIDO-"    );
            Logger.write("     dn                     : " + dn          );
            Logger.write("     domicilioID            : " + domicilioID );
            Logger.write("     usuarioId              : " + usuarioId   );
            Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
            Logger.write("     remoteAddress          : " + getClientIpXfire());

            tokens.validaToken(dn, token, new MensajeLogBean());
            
            respuestaBD = AltaTarjetaFrecuente.vincularDomicilioTarjeta(numeroTarjeta, dn, domicilioID);
            
            Logger.write("   + Respuesta              + ");
            Logger.write("     respuesta              : " + respuestaBD);
        }
        catch(Exception e)
        {
            if(e != null && e.getLocalizedMessage() != null)
            {
                if(e.getLocalizedMessage().contains("ORA-20000")     || e.getLocalizedMessage().contains("Parametros de entrada") ||
                   e.getLocalizedMessage().contains("Has eliminado") || e.getLocalizedMessage().contains("cardBlackList")         ||
                   e.getLocalizedMessage().contains("SemaphoreSaveCustomerInfo"))
                {
                    throw new ServiceException("[WARN] vincularDomicilioTarjeta [" + dn +"] :: " + e.getLocalizedMessage());
                }else{
                    throw new ServiceException("[ERR] vincularDomicilioTarjeta [" + dn +"] :: " + e.getLocalizedMessage());
                }
            }else{
                throw new ServiceException("[ERR] vincularDomicilioTarjeta [" + dn +"] :: " + e.getLocalizedMessage());
            }
        }
        finally{
            Logger.end("vincularDomicilioTarjeta  (" + dn + ") :: " + getLocalAddress(), initime);
        }

        return respuestaBD;
    }
    
    public List<DomicilioVO> getDomicilios(String user, String pass, String dn, int usuarioId, String token) throws Throwable
    {
        long initime = System.currentTimeMillis();
        Logger.init("getDomicilios  (" + dn + ")");
        
        if(user.trim().equals("") || !user.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")){
            Logger.write(" (!) El usuario no es válido ");
            throw new ServiceException("[ALE] usuario no valido");
        }
        
        if(pass.trim().equals("") || !pass.trim().equals("AGhAxzwOwKEbI12XQ1MIjQ*/")){
            Logger.write(" (!) El password no es válido ");
            throw new ServiceException("[ALE] password no válido");
        }
        
        ValidaTokens tokens =  new ValidaTokens();
        
        List<DomicilioVO> domicilios = null;
        
        try
        {
            Logger.write("     user                   : -PROTEGIDO-"    );
            Logger.write("     pass                   : -PROTEGIDO-"    );
            Logger.write("     dn                     : " + dn          );
            Logger.write("     usuarioId              : " + usuarioId   );
            Logger.write("     token                  : " + Formatter.pintaLogCadenasLargas(token));
            Logger.write("     remoteAddress          : " + getClientIpXfire());
            
            tokens.validaToken(dn, token, new MensajeLogBean());
            
            domicilios = AltaTarjetaFrecuente.consultaDomicilios(dn);
            
            Logger.write("   + Respuesta              + ");
            Logger.write("     respuesta              : " + domicilios.size());
        }
        catch(Exception e)
        {
            if(e != null && e.getLocalizedMessage() != null)
            {
                if(e.getLocalizedMessage().contains("ORA-20000")     || e.getLocalizedMessage().contains("Parametros de entrada") ||
                   e.getLocalizedMessage().contains("Has eliminado") || e.getLocalizedMessage().contains("cardBlackList")         ||
                   e.getLocalizedMessage().contains("SemaphoreSaveCustomerInfo"))
                {
                    throw new ServiceException("[WARN] getDomicilios [" + dn +"] :: " + e.getLocalizedMessage());
                }else{
                    throw new ServiceException("[ERR] getDomicilios [" + dn +"] :: " + e.getLocalizedMessage());
                }
            }else{
                throw new ServiceException("[ERR] getDomicilios [" + dn +"] :: " + e.getLocalizedMessage());
            }
        }
        finally{
            Logger.end("getDomicilios  (" + dn + ") :: " + getLocalAddress(), initime);
        }
        
        return domicilios;
    }
}