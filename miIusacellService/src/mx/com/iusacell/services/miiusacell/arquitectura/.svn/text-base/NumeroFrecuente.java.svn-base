package mx.com.iusacell.services.miiusacell.arquitectura;

//import java.util.ArrayList;
//import java.util.List;

import java.util.List;

import javax.xml.rpc.ServiceException;

import BAdministraFF.iusacell.com.mx.RespuestaFF;

import mx.com.iusacell.middleware.servicios.gestion.RespuestaServicios;
import mx.com.iusacell.services.miiusacell.vo.Addons;
import mx.com.iusacell.services.miiusacell.vo.ConsultaPrepagoVO;
//import mx.com.iusacell.services.miiusacell.vo.ContratarServiciosVO;
import mx.com.iusacell.services.miusacell.call.CallServiceAltaLegacy;
import mx.com.iusacell.services.miusacell.call.CallServiceConsultaPrepago;
import mx.com.iusacell.services.miusacell.call.CallServiceNumerosFrecuentes;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class NumeroFrecuente {

	public static boolean activarNumeroFrecuente(final String dn, final Addons servicios,final  List<String> numerosFrecuentes)
	throws ServiceException {
		String sResponse = "";
		boolean retorno = false;
		try {
			CallServiceConsultaPrepago servicePrepago = new CallServiceConsultaPrepago();
			CallServiceNumerosFrecuentes numeroFrecuentes = new CallServiceNumerosFrecuentes();
			CallServiceAltaLegacy servicioLegacy = new CallServiceAltaLegacy();
			ConsultaPrepagoVO prepago = new ConsultaPrepagoVO();
			RespuestaServicios responseServicio = null;
			RespuestaFF responseAltaNumFrec = null;

			sResponse = servicePrepago.consultaPrepago(dn);
			prepago = ParseXMLFile.ParseConsultaPrepagoMin(sResponse);

			if(prepago.getIdLinea() == null)
				throw new ServiceException("Sin información de la linea");					

			int tipoLinea = 1;
			try
			{
				tipoLinea = Integer.parseInt(prepago.getTipoLinea());
			}
			catch (NumberFormatException e) {
			}	
			responseServicio = servicioLegacy.altaServicioLegacy(prepago.getIdLinea(), servicios.getIdServicio(), 
					servicios.getOrigen(), servicios.getUnidadVigencia(), servicios.getCantidadVigencia(), 
					tipoLinea, servicios.getOperacion());						

			if(responseServicio.getRespuesta())
			{
				responseAltaNumFrec = numeroFrecuentes.altaNumerosFrecuentesLegacy(prepago.getIdLinea(), numerosFrecuentes);
				if(!responseAltaNumFrec.getRespuesta())
					throw new ServiceException(responseAltaNumFrec.getMensaje());
				else 
					retorno = true;				
			}			

		} catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return retorno;
	}

	public static int contratarServicio(final String dn, final Addons servicios) throws ServiceException {
		String sResponse = "";
		int retorno = 0;
		try {
			CallServiceConsultaPrepago servicePrepago = new CallServiceConsultaPrepago();
			CallServiceAltaLegacy servicioLegacy = new CallServiceAltaLegacy();
			ConsultaPrepagoVO prepago = new ConsultaPrepagoVO();
			RespuestaServicios responseServicio = null;			

			sResponse = servicePrepago.consultaPrepago(dn);
			prepago = ParseXMLFile.ParseConsultaPrepagoMin(sResponse);

			if(prepago.getIdLinea() == null)
				throw new ServiceException("Sin información de la linea");					

			int tipoLinea = 1;
			try
			{
				tipoLinea = Integer.parseInt(prepago.getTipoLinea());
			}
			catch (NumberFormatException e) {
			}
			responseServicio = servicioLegacy.altaServicioLegacy(prepago.getIdLinea(), servicios.getIdServicio(), 
					servicios.getOrigen(), servicios.getUnidadVigencia(), servicios.getCantidadVigencia(), 
					tipoLinea, servicios.getOperacion());						

			if(responseServicio.getRespuesta())
			{
				retorno = responseServicio.getFolioPreactivacion();		
			}			

		} catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		return retorno;
	}
}
