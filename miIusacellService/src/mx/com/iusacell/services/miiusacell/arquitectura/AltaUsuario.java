package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.ClienteRegistroVO;
import mx.com.iusacell.services.miiusacell.vo.ClienteVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleFocalizacionVO;
import mx.com.iusacell.services.miusacell.call.CallServiceFocalizacion;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class AltaUsuario {

	public static String registraClienteBit(final ClienteRegistroVO datosCliente, final int usuarioId) throws ServiceException
	{
		String msg = "";
		String date[] = null;
		String dateParse[] = null;
		String finalDate = "";
		String validaFocalizacion = "";
		String sResponse = "";
		OracleProcedures oracle = new OracleProcedures();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		int tipoLinea = 0;
		int tipoTecnologia = 0;
		try
		{
			validaDatosClienteBit(datosCliente);

			try{
				validaFocalizacion = oracle.getValorParametro(15);
			}catch (ServiceException e) {
				validaFocalizacion = "0";
			}
			
			if(validaFocalizacion.equals("1")){
				
				try{
					sResponse = focalizacion.focalizacion(datosCliente.getDn());
					if(sResponse != null && !sResponse.equals("")){
						descripcion = ParseXMLFile.parseFocalizacion(sResponse);
					}	
				}catch (ServiceException e) {
					descripcion = new DetalleFocalizacionVO();
				}
				
				if(descripcion.getServicio() == null){
					throw new ServiceException("El numero no existe en Iusacell-Unefon");
				}
				
				if(descripcion.getServicio() != null && descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
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
			}
			
			if(datosCliente.getNacimiento() != null && !datosCliente.getNacimiento().equals("")){
				if(datosCliente.getNacimiento().indexOf("T") >= 0 && datosCliente.getNacimiento().indexOf("-") >= 0){
					date = datosCliente.getNacimiento().split("T");
					if(date.length >=0){
						dateParse = date[0].split("-");
						if(dateParse.length >= 3){
							finalDate = dateParse[2] + "-" + dateParse[1] + "-" + dateParse[0];
						}else{
							throw new ServiceException("Formato de fecha invalido");
						}
					}else{
						throw new ServiceException("Formato de fecha invalido");
					}
				}else{
					throw new ServiceException("Formato de fecha invalido");
				}
			}else{
				if(datosCliente.getNacimiento() != null)
					throw new ServiceException("Fecha invalida:" + datosCliente.getNacimiento());
				else
					throw new ServiceException("Fecha invalida");
			}
			
			int resp = oracle.altaCliente(datosCliente.getPreguntaSecreta(), datosCliente.getRespPreguntaSecreta(), datosCliente.getContrasena(), datosCliente.getCorreo(), datosCliente.getDn(),
					datosCliente.getTipoCliente(), datosCliente.getNombre(), datosCliente.getaPaterno(), datosCliente.getaMaterno(), datosCliente.getEdad(), datosCliente.getSexo(),
					finalDate, datosCliente.getPaisID(), datosCliente.getUnidadNegocioID(), datosCliente.getFotografia(), usuarioId,tipoLinea, datosCliente.getSistemaOrigen(),tipoTecnologia, datosCliente.getDispositivo());

			if(resp == 0)
				msg = "cliente registrado correctamente";
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}

		return msg;
	}
	
	public static String registraCliente(final ClienteVO datosCliente, final int usuarioId) throws ServiceException
	{
		String msg = "";
		String date[] = null;
		String dateParse[] = null;
		String finalDate = "";
		String validaFocalizacion = "";
		String sResponse = "";
		OracleProcedures oracle = new OracleProcedures();
		CallServiceFocalizacion focalizacion = new CallServiceFocalizacion();
		DetalleFocalizacionVO descripcion = new DetalleFocalizacionVO();
		int tipoLinea = 0;
		int tipoTecnologia = 0;
		try
		{
			validaDatosCliente(datosCliente);

			try{
				validaFocalizacion = oracle.getValorParametro(15);
			}catch (ServiceException e) {
				validaFocalizacion = "0";
			}
			
			if(validaFocalizacion.equals("1")){
				
				try{
					sResponse = focalizacion.focalizacion(datosCliente.getDn());
					if(sResponse != null && !sResponse.equals("")){
						descripcion = ParseXMLFile.parseFocalizacion(sResponse);
					}	
				}catch (ServiceException e) {
					descripcion = new DetalleFocalizacionVO();
				}
				
				if(descripcion.getServicio() == null){
					throw new ServiceException("El numero no existe en Iusacell-Unefon");
				}
				
				if(descripcion.getServicio() != null && descripcion.getServicio().equals("80") && descripcion.getSubservicio().equals("80")){
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
			}
			
			if(datosCliente.getNacimiento() != null && !datosCliente.getNacimiento().equals("")){
				if(datosCliente.getNacimiento().indexOf("T") >= 0 && datosCliente.getNacimiento().indexOf("-") >= 0){
					date = datosCliente.getNacimiento().split("T");
					if(date.length >=0){
						dateParse = date[0].split("-");
						if(dateParse.length >= 3){
							finalDate = dateParse[2] + "-" + dateParse[1] + "-" + dateParse[0];
						}else{
							throw new ServiceException("Formato de fecha invalido");
						}
					}else{
						throw new ServiceException("Formato de fecha invalido");
					}
				}else{
					throw new ServiceException("Formato de fecha invalido");
				}
			}else{
				if(datosCliente.getNacimiento() != null)
					throw new ServiceException("Fecha invalida:" + datosCliente.getNacimiento());
				else
					throw new ServiceException("Fecha invalida");
			}
			
			int resp = oracle.altaCliente(datosCliente.getPreguntaSecreta(), datosCliente.getRespPreguntaSecreta(), datosCliente.getContrasena(), datosCliente.getCorreo(), datosCliente.getDn(),
					datosCliente.getTipoCliente(), datosCliente.getNombre(), datosCliente.getaPaterno(), datosCliente.getaMaterno(), datosCliente.getEdad(), datosCliente.getSexo(),
					finalDate, datosCliente.getPaisID(), datosCliente.getUnidadNegocioID(), datosCliente.getFotografia(), usuarioId,tipoLinea, 1,tipoTecnologia, 1);

			if(resp == 0)
				msg = "cliente registrado correctamente";
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}

		return msg;
	}
	
	private static void validaDatosCliente(final ClienteVO datosCliente) throws ServiceException
	{		
		if(datosCliente.getPreguntaSecreta() == null || datosCliente.getPreguntaSecreta().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  La pregunta secreta no puede ir vacio");
		}
		if(datosCliente.getContrasena() == null || datosCliente.getContrasena().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  La contrasena no puede ir vacio");
		}
		if(datosCliente.getCorreo() == null || datosCliente.getCorreo().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  El correo no puede ir vacio");
		}
		if(datosCliente.getDn() == null || datosCliente.getDn().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  El Dn no puede ir vacio");
		}		
		if(datosCliente.getNombre() == null || datosCliente.getNombre().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  El nombre no puede ir vacio");
		}
		if(datosCliente.getaPaterno() == null || datosCliente.getaPaterno().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  El apellido paterno no puede ir vacio");
		}
		if(datosCliente.getaMaterno() == null || datosCliente.getaMaterno().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  El apellido materno no puede ir vacio");
		}
		if(datosCliente.getSexo() == null || datosCliente.getSexo().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  El sexo no puede ir vacio");
		}
		if(datosCliente.getNacimiento() == null){
			throw new ServiceException("Parametros de entrada incorrectos  ::  La fecha Nacimiento no puede ir vacio");
		}
	}
	
	private static void validaDatosClienteBit(final ClienteRegistroVO datosCliente) throws ServiceException
	{		
		if(datosCliente.getPreguntaSecreta() == null || datosCliente.getPreguntaSecreta().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  La pregunta secreta no puede ir vacio");
		}
		if(datosCliente.getContrasena() == null || datosCliente.getContrasena().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  La contrasena no puede ir vacio");
		}
		if(datosCliente.getCorreo() == null || datosCliente.getCorreo().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  El correo no puede ir vacio");
		}
		if(datosCliente.getDn() == null || datosCliente.getDn().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  El Dn no puede ir vacio");
		}		
		if(datosCliente.getNombre() == null || datosCliente.getNombre().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  El nombre no puede ir vacio");
		}
		if(datosCliente.getaPaterno() == null || datosCliente.getaPaterno().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  El apellido paterno no puede ir vacio");
		}
		if(datosCliente.getaMaterno() == null || datosCliente.getaMaterno().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  El apellido materno no puede ir vacio");
		}
		if(datosCliente.getSexo() == null || datosCliente.getSexo().trim().equals("")){
			throw new ServiceException("Parametros de entrada incorrectos  ::  El sexo no puede ir vacio");
		}
		if(datosCliente.getNacimiento() == null){
			throw new ServiceException("Parametros de entrada incorrectos  ::  La fecha Nacimiento no puede ir vacio");
		}
	}
}
