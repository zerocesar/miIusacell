package mx.com.iusacell.services.miiusacell.arquitectura;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import AES.AesEncrypter;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.CallTreeVO;
import mx.com.iusacell.services.miiusacell.vo.CancelacionesPasadasVO;
import mx.com.iusacell.services.miiusacell.vo.DatosAdicionalesDispatcherClienteCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.DatosClienteCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.DatosDispatcherClienteCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.DatosUsuarioCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miiusacell.vo.GruoupIDCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.PropertyKeyCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.ResponsCreateQueueVO;
import mx.com.iusacell.services.miiusacell.vo.ResponseCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.TicketCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.TreeSubgroupCancelacionVO;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.call.CallServiceServiciosContratos;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public class CancelacionLinea {

	private final OracleProcedures oracle = new OracleProcedures();
	private final CallServiceServiciosContratos service = new CallServiceServiciosContratos();
	private String tipoCliente="";
	private String strUserId="";
	private long longUserId=0;
	private String strUsrIdCliente="";
	private String contracType="";		

	public ResponseCancelacionVO generaCancelacion(final String dn, final String dnContacto, final String strNota) throws ServiceException{		

		String idNote="";
		int treeId=0;		
		String strDivision="";				
		int intSkillId=0;
		long longCallId=0;				

		GruoupIDCancelacionVO grupoCancelacion =null;								
		List<TreeSubgroupCancelacionVO> treeDesc=null;
		DatosUsuarioCancelacionVO datosUsuario =null;
		DetalleFocalizacionVO descripcionCliente = null;		
		DatosDispatcherClienteCancelacionVO datosClienteCancelacionDispatcher = null;
		DatosAdicionalesDispatcherClienteCancelacionVO datosAdicionalesClientCancelDispatcher = null;		
		ResponsCreateQueueVO responsCreateQueue = null;
		ResponseCancelacionVO respuesta = null;
		final CallTreeVO callTree = new CallTreeVO();		
		final Date fecha = new Date();
		final DateFormat formater = new SimpleDateFormat("MM/dd/yyyy");
		final DatosClienteCancelacionVO datosCliente =new DatosClienteCancelacionVO();		
		final TicketCancelacionVO ticketReq = new TicketCancelacionVO();
		String caeDesc = "";				

		try{
			grupoCancelacion=oracle.getGroupIDCancelacion();
			//TEST
			//grupoCancelacion =new GruoupIDCancelacionVO();
			//100001799
			//
			//grupoCancelacion.setGroup_ID(100001799);
			

			validaCancelacionesPasadas(dn, String.valueOf(grupoCancelacion.getGroup_ID()));		

			descripcionCliente = getInfoClienteFocalizacion(dn);

			strDivision = oracle.getDivisionCancelacion(Integer.parseInt(dn.substring(0,3)));
			
			if (descripcionCliente.getIsPostpagoOrHibrido() != null && !descripcionCliente.getIsPostpagoOrHibrido().equals(""))
				asignaTipoCliente(descripcionCliente.getIsPostpagoOrHibrido());
			else
				throw new ServiceException("No se encontró información para el dn (" + dn + ")");
			
			asignaUserId();

			validaFechaCancelacion(dn);

			datosUsuario = oracle.getDatosUsuarioCancelacion(strUserId);

			idNote = String.valueOf(oracle.getFieldNumberCancelacion(grupoCancelacion.getGroup_ID()));				

			treeId = oracle.getMotivoCancelacion(grupoCancelacion.getGroup_ID());
			//treeId = 252152;

			treeDesc = oracle.getSubTreeCancelacion(treeId);
			
			//TEST
			/*TreeSubgroupCancelacionVO tmp1= new TreeSubgroupCancelacionVO(); 
			tmp1.setTree_ID(252156);
			tmp1.setTree_Desc("TERMINO DE PLAN");
			treeDesc.add(tmp1);
			tmp1= new TreeSubgroupCancelacionVO();
			tmp1.setTree_ID(252157);
			tmp1.setTree_Desc("FALLAS EN EL SISTEMA");
			treeDesc.add(tmp1);*/
			//*******
			
			caeDesc = oracle.getCAEUsuarioCancelacion(String.valueOf(datosUsuario.getIntIDUser()));

			datosClienteCancelacionDispatcher = getInfoClienteDispatcher(dn);
			if (datosClienteCancelacionDispatcher.getCustomerID() != null && !datosClienteCancelacionDispatcher.getCustomerID().equals("")
				&& !datosClienteCancelacionDispatcher.getNombreCliente().equals("")) 
				datosAdicionalesClientCancelDispatcher = getInfoAdicionalesDispatcher(datosClienteCancelacionDispatcher.getCustomerID());
			else
				throw new ServiceException("No se encontró información para el dn (" + dn + ")");

			final String[] descTreeArray= new String[6];
			final int[] Tree_ID_Array= new int[6];
			int j=0;
			for (final TreeSubgroupCancelacionVO tmp: treeDesc){
				if (tmp.getTree_ID()<=0){
					descTreeArray[j]="";
					Tree_ID_Array[j]=0;
				}else{
					if (j<=5){
						descTreeArray[j]=tmp.getTree_Desc();
						Tree_ID_Array[j]=tmp.getTree_ID();
					}
				}
				j++;
			}		

			//seteo de datos
			datosCliente.setIntCompany(Integer.parseInt(descripcionCliente.getIdOperador()));
			datosCliente.setIntContactType(Integer.parseInt(contracType));
			datosCliente.setIntPaymentType(Integer.parseInt(descripcionCliente.getIdFormaPago()));
			datosCliente.setLongContract(Long.valueOf(descripcionCliente.getCoID()));						
			datosCliente.setLongCustomerId(Long.parseLong(datosClienteCancelacionDispatcher.getCustomerID()));			
			datosCliente.setStrCustcode(datosClienteCancelacionDispatcher.getCustCode());									
			datosCliente.setStrCAE(caeDesc);			
			datosCliente.setStrLogin(datosUsuario.getStrLogin());
			datosCliente.setStrESN(datosClienteCancelacionDispatcher.getEsn());
			datosCliente.setStrAddress(datosAdicionalesClientCancelDispatcher.getDirFiscal());
			datosCliente.setStrCustomerName(datosClienteCancelacionDispatcher.getNombreCliente());
			datosCliente.setStrMdn(dn);// validar que sea el numero que levanta la solicitud o el número de contacto
			datosCliente.setStrNombre(datosUsuario.getStrFirstName());
			datosCliente.setStrRFC(datosAdicionalesClientCancelDispatcher.getRFC());
			datosCliente.setStrPlan(descripcionCliente.getDescripcionPlan());
			datosCliente.setStrTecnologia(descripcionCliente.getTecnologia());
			datosCliente.setStrPrgName(descripcionCliente.getTipoCliente());

			grupoCancelacion.setGroup_Category(treeId);
			callTree.setCategoryDesc(descTreeArray);			
			callTree.setCategoryId(Tree_ID_Array);

			//NoteLebel="Motivo de cancelación"
			callTree.setGroupId(String.valueOf(grupoCancelacion.getGroup_ID()));
			callTree.setNoteId(idNote);
			callTree.setNoteDesc(strNota);
			callTree.setStrCallId("0");
			callTree.setColasCAE("0");

			ticketReq.setCaptureTime(formater.format(fecha));
			ticketReq.setOpenTime(formater.format(fecha));
			ticketReq.setCategoryCreateTicket(String.valueOf(treeId));
			ticketReq.setNoteCreateTicket(strNota);
			ticketReq.setStatus("pendiente");

			//Inserta Queue
			responsCreateQueue=oracle.setSolicitudCancelacion(datosCliente, longUserId, intSkillId, longCallId, strDivision);

			if (responsCreateQueue.getPnoErrorCode() != 0){
				throw new ServiceException("Al insertar Queue ["+responsCreateQueue.getPnoErrorCode()+"] "+responsCreateQueue.getPsoErrorMsg()  );
			}		

			//Inserta solicitud de cancelacion
			respuesta=oracle.saveSolicitudCancelacion(datosCliente,callTree ,longUserId, intSkillId, longCallId, strDivision);
		}
		catch (Exception e) {
			throw new ServiceException("Error: " + e.getLocalizedMessage());
		}		

		return respuesta;
	}

	private void validaCancelacionesPasadas(String dn, String groupId) throws ServiceException{		
		final List<CancelacionesPasadasVO> respCancelacionesPasadas = oracle.getCancelacionesPasadas(dn, groupId);
		if(respCancelacionesPasadas != null && !respCancelacionesPasadas.isEmpty()){				
			for (final CancelacionesPasadasVO cancelacionPasada : respCancelacionesPasadas){
				if(!cancelacionPasada.getStatus().equalsIgnoreCase("cerrado")){
					throw new ServiceException("Tiene cancelaciones pasadas, el flujo no puede continuar" );					
				}		
			}
		}
	}

	private DetalleFocalizacionVO getInfoClienteFocalizacion(final String dn) throws ServiceException{
		final CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO infoCliente = null;
		try{
			final String sResponse = focalizacion.focalizacion(dn);
			if(sResponse != null && !sResponse.equals("")){
				infoCliente = ParseXMLFile.parseFocalizacion(sResponse);
			}	
		}catch (ServiceException e) {
			throw new ServiceException("Al consultar los servicios de focalizacion : " + e.getLocalizedMessage());		
		}

		return infoCliente;
	}

	private DatosDispatcherClienteCancelacionVO getInfoClienteDispatcher(final String dn) throws ServiceException{
		DatosDispatcherClienteCancelacionVO datosClienteCancelacionDispatcher = null;
		try{
			final String sResponse = service.getStatusLineaPospago(dn);
			datosClienteCancelacionDispatcher = ParseXMLFile.parseCustomerCancelacion(sResponse);
		}catch (ServiceException e) {
			throw new ServiceException("Al consultar los servicios de sqldispatcher : " + e.getLocalizedMessage());		
		}

		return datosClienteCancelacionDispatcher;
	}

	private DatosAdicionalesDispatcherClienteCancelacionVO getInfoAdicionalesDispatcher(final String customerId) throws ServiceException{
		DatosAdicionalesDispatcherClienteCancelacionVO datosAdicionalesCancelacionDispatcher = null;
		try{
			final String sResponse = service.getDatosCliente(customerId);			
			datosAdicionalesCancelacionDispatcher= ParseXMLFile.parseCustomerAdicionalesCancelacion(sResponse);
		}catch (ServiceException e) {
			throw new ServiceException("Al consultar los servicios de sqldispatcher : " + e.getLocalizedMessage());		
		}

		return datosAdicionalesCancelacionDispatcher;
	}

	private void asignaTipoCliente(final String tipoClienteFoca) throws ServiceException{
		if (tipoClienteFoca.equalsIgnoreCase("PREPAGO")){
			contracType="1";
			tipoCliente="LONG_USER_ID_PREPAID";
			strUsrIdCliente="STR_USER_PREPAID";
		}else if(tipoClienteFoca.equalsIgnoreCase("POSPAGO")){
			contracType="2";
			tipoCliente="LONG_USER_ID_POSTPAID";
			strUsrIdCliente="STR_USER_ID_POSTPAID";
		}else if(tipoClienteFoca.equalsIgnoreCase("HIBRIDO")){
			contracType="3";
			tipoCliente="LONG_USER_ID_HYBRID";
			strUsrIdCliente="STR_USER_ID_HYBRID";
		}else{
			throw new ServiceException("El tipo de cliente " + tipoClienteFoca + " no esta permitido");
		}
	}

	private void asignaUserId() throws ServiceException{
		int indice_List=0;
		final List<PropertyKeyCancelacionVO> listKeyCancelacion = oracle.getListPropertyKeyCancelacion();
		//listKeyCancelacion.indexOf(tipoCliente);
		try{
			for (final PropertyKeyCancelacionVO keyCancelacion : listKeyCancelacion){
				if(keyCancelacion.getPropertyKey().equalsIgnoreCase(tipoCliente)){
					final String datoDecodificadoDesencriptado = new AesEncrypter().decrypt(java.net.URLDecoder.decode(listKeyCancelacion.get(indice_List).getPropertyValue(), "UTF-8"));
					longUserId= Long.valueOf(datoDecodificadoDesencriptado);
				}else if(keyCancelacion.getPropertyKey().equalsIgnoreCase(strUsrIdCliente)){
					final String datoDecodificadoDesencriptado = new AesEncrypter().decrypt(java.net.URLDecoder.decode(listKeyCancelacion.get(indice_List).getPropertyValue(), "UTF-8"));
					strUserId= datoDecodificadoDesencriptado;
				}
				indice_List++;
			}
		}
		catch (UnsupportedEncodingException e) {
			throw new ServiceException("Al consultar lista PropertyKey " + e.getLocalizedMessage());
		}
	}

	private void validaFechaCancelacion(final String dn) throws ServiceException{

		final String timeSolicitud = oracle.getValorParametro(225);
		//final String timeSolicitud = "240";
		final String fechaCancelacion = oracle.getValidaFechaCancelacion(dn, String.valueOf(longUserId), timeSolicitud);
		if(fechaCancelacion != null && !fechaCancelacion.equals("")){
			throw new ServiceException("Tiene solicitud en proceso, el flujo no puede continuar" );
		}		
	}
}
