package mx.com.iusacell.services.miusacell.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import org.codehaus.xfire.transport.http.XFireServletController;

import com.thoughtworks.xstream.core.util.Base64Encoder;

import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.vo.BankCardAdditionalInfoVO;
import mx.com.iusacell.services.miiusacell.vo.CatatoloGetVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoFechaTablaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoFechaVO;
import mx.com.iusacell.services.miiusacell.vo.SaldosFechaVO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class Utilerias {
	
	public static String getMarcaTarjeta(final int marcaTarjeta)
	{
		String emisorTarjeta="";
		switch (marcaTarjeta) {
		case 1:
			emisorTarjeta="MASTER CARD";
			break;
		case 2:
			emisorTarjeta="VISA";
			break;
		default:
			break;
		}
		return emisorTarjeta;
	}

	public static Integer generaPin()
	{
		SecureRandom rnd = new SecureRandom();
		rnd.setSeed(System.currentTimeMillis()); 
		return ( 100000 + rnd.nextInt(900000)); 
	}


	public static BufferedImage resizeImage(final BufferedImage originalImage, final int width,final int height, final int type){
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}
	
	public static double round(final double value, final int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	public static BufferedImage decodeToImage(final String imageString) {

		BufferedImage image = null;
		byte[] imageByte;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			imageByte = decoder.decodeBuffer(imageString);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
		} catch (Exception e) {
//			e.printStackTrace();
		    Logger.write("Utilerias.decodeToImage :: (Exception) :: " + e.getMessage());
		}
		return image;
	}

	public static String encodeToString(final BufferedImage image, final String type) {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();

			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);

			bos.close();
		} catch (IOException e) {
		    Logger.write("Utilerias.encodeToString :: (IOException) :: " + e.getMessage());
//			e.printStackTrace();
		}
		return imageString;
	}


	public static String generaPassword()
	{
		SecureRandom rnd = new SecureRandom();
		
		String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int LargoContrasena = 9;
		String contrasena="";
		int longitud = base.length();

		for(int i=0; i<LargoContrasena;i++){ //1
			int numero = (int)(rnd.nextDouble()*(longitud)); //2
			String caracter=base.substring(numero, numero+1); //3
			contrasena=contrasena+caracter; //4
		}

		return contrasena;
	}

	public static String limpiaCadena(final String sCadena)throws ServiceException
	{
		String sReturn = "";
		try
		{
			String original = "��������������u�������������������";
			String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
			String output = sCadena;

			for (int i = 0; i < original.length(); i++) 
				sReturn = output.replace(original.charAt(i), ascii.charAt(i));
		}
		catch(Exception e)
		{
			sReturn = "";
			throw new ServiceException(e.getMessage());
		}

		return sReturn;
	}

	public static boolean stringValido(final String sString)
	{
		if(sString == null || sString.equals("") || sString.equals(" ") || sString.length() == 0)
			return false;
		else
			return true;
	}

	public static String pintaLogCadenasLargas(final String dato){
		if(dato != null && dato.length() > 0){
			if(dato.length() > 15)
				return dato.substring(0,10);
			return dato;
		}
		return "";
	}
	
	public static String pintaLogRequest(final String dato){
		String pintaRequest = "0";
		if(pintaRequest.equals("0")){
			if(dato != null && dato.length() > 0){
				if(dato.length() > 1800)
					return dato.substring(0,1800);
				return dato;
			}
		}else{
			return dato;
		}
		return "";
	}

	public static List<ConsumoFechaVO> orderConsumosByDate(final int orden, final List<ConsumoFechaVO> lista, final String fechaCorte) throws ServiceException{
		ConsumoFechaVO resProv = new ConsumoFechaVO();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		Date iniDate = new Date();
		Date finDate = new Date();
		Date corteDate = new Date();

		try{
			for (int i = 0; i < lista.size() - 1; ++i) {
				for (int y = 0; y < lista.size() - 1; ++y) {
					iniDate = format.parse(lista.get(y).getFecha());
					finDate = format.parse(lista.get(y+1).getFecha());
					if (iniDate.compareTo(finDate) < 0) {
						resProv = lista.get(y);
						lista.set(y, lista.get(y+1));
						lista.set(y+1, resProv);
					}
				}
			}

			if(fechaCorte != null && !fechaCorte.equals("")){
				corteDate = format2.parse(fechaCorte);
				for(int i=0; i<lista.size(); i++){
					if(lista.get(i).getFecha() != null && !lista.get(i).getFecha().equals("")){
						iniDate = format.parse(lista.get(i).getFecha());
						if(iniDate.compareTo(corteDate) < 0){
							if(lista.size()>0){
								lista.remove(i);
								i--;
							}
						}
					}
				}
			}

			if(fechaCorte != null && !fechaCorte.equals("")){
				corteDate = format2.parse(fechaCorte);
				for(int i=0; i<lista.size(); i++){
					if(lista.get(i).getFecha() != null && !lista.get(i).getFecha().equals("")){
						iniDate = format.parse(lista.get(i).getFecha());
						if(iniDate.compareTo(corteDate) < 0){
							if(lista.size()>0){
								lista.remove(i);
								i--;
							}
						}
					}
				}
			}

		}catch (Exception e) {
			throw new ServiceException("Ocurrio un error al obtener el detalle");
		}

		return lista;
	}

	public static List<ConsumoFechaTablaVO> orderConsumosByDateTabla(final int orden, final List<ConsumoFechaTablaVO> lista, final String fechaCorte) throws ServiceException{
		ConsumoFechaTablaVO resProv = new ConsumoFechaTablaVO();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		Date iniDate = new Date();
		Date finDate = new Date();
		Date corteDate = new Date();
		SimpleDateFormat format3 = new SimpleDateFormat("hh:mm");
		Date hora1,hora2;
		
		try{
			
			//ORDENAR POR HORA
			for (int i = 0; i < lista.size() - 1; ++i) {
				for (int y = 0; y < lista.size() - 1; ++y) {
					hora1 = format3.parse(lista.get(y).getDetalle().get(0).getHora());
					hora2 = format3.parse(lista.get(y+1).getDetalle().get(0).getHora());
					if(hora1.compareTo(hora2) < 0){
						resProv = lista.get(y);
						lista.set(y, lista.get(y+1));
						lista.set(y+1, resProv);
					}
				}
			}
			
			//ORDENAR POR FECHA
			if(orden == 1){
				for (int i = 0; i < lista.size() - 1; ++i) {
					for (int y = 0; y < lista.size() - 1; ++y) {
						iniDate = format.parse(lista.get(y).getFecha());
						finDate = format.parse(lista.get(y+1).getFecha());
						if (iniDate.compareTo(finDate) < 0) {
							resProv = lista.get(y);
							lista.set(y, lista.get(y+1));
							lista.set(y+1, resProv);
						}
					}
				}
			}else{
				for (int i = 0; i < lista.size() - 1; ++i) {
					for (int y = 0; y < lista.size() - 1; ++y) {
						iniDate = format.parse(lista.get(y).getFecha());
						finDate = format.parse(lista.get(y+1).getFecha());
						if (iniDate.compareTo(finDate) >= 0) {
							resProv = lista.get(y);
							lista.set(y, lista.get(y+1));
							lista.set(y+1, resProv);
						}
					}
				}
			}

			if(fechaCorte != null && !fechaCorte.equals("")){
				corteDate = format2.parse(fechaCorte);
				for(int i=0; i<lista.size(); i++){
					if(lista.get(i).getFecha() != null && !lista.get(i).getFecha().equals("")){
						iniDate = format.parse(lista.get(i).getFecha());
						if(iniDate.compareTo(corteDate) < 0){
							if(lista.size()>0){
								lista.remove(i);
								i--;
							}
						}
					}
				}
			}

		}catch (Exception e) {
			throw new ServiceException("Ocurrio un error al obtener el detalle");
		}

		return lista;
	}

	public static List<ConsumoFechaTablaVO> orderConsumosByDateTablaxFecha(final int orden, final List<ConsumoFechaTablaVO> lista, final String fechaIni) throws ServiceException{
		ConsumoFechaTablaVO resProv = new ConsumoFechaTablaVO();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		Date iniDate = new Date();
		Date finDate = new Date();
		Date corteDate = new Date();
		SimpleDateFormat format3 = new SimpleDateFormat("hh:mm");
		Date hora1,hora2;

		try{
			//ORDENAR POR HORA
			for (int i = 0; i < lista.size() - 1; ++i) {
				for (int y = 0; y < lista.size() - 1; ++y) {
					hora1 = format3.parse(lista.get(y).getDetalle().get(0).getHora());
					hora2 = format3.parse(lista.get(y+1).getDetalle().get(0).getHora());
					if(hora1.compareTo(hora2) < 0){
						resProv = lista.get(y);
						lista.set(y, lista.get(y+1));
						lista.set(y+1, resProv);
					}
				}
			}
			
			//ORDENAR POR FECHA
			if(orden == 1){
				for (int i = 0; i < lista.size() - 1; ++i) {
					for (int y = 0; y < lista.size() - 1; ++y) {
						iniDate = format.parse(lista.get(y).getFecha());
						finDate = format.parse(lista.get(y+1).getFecha());
						if (iniDate.compareTo(finDate) < 0) {
							resProv = lista.get(y);
							lista.set(y, lista.get(y+1));
							lista.set(y+1, resProv);
						}
					}
				}
			}else{
				for (int i = 0; i < lista.size() - 1; ++i) {
					for (int y = 0; y < lista.size() - 1; ++y) {
						iniDate = format.parse(lista.get(y).getFecha());
						finDate = format.parse(lista.get(y+1).getFecha());
						if (iniDate.compareTo(finDate) >= 0) {
							resProv = lista.get(y);
							lista.set(y, lista.get(y+1));
							lista.set(y+1, resProv);
						}
					}
				}
			}
			
			if(fechaIni != null && !fechaIni.equals("")){
				corteDate = format2.parse(fechaIni);
				for(int i=0; i<lista.size(); i++){
					if(lista.get(i).getFecha() != null && !lista.get(i).getFecha().equals("")){
						iniDate = format.parse(lista.get(i).getFecha());
						if(iniDate.compareTo(corteDate) < 0){
							if(lista.size()>0){
								lista.remove(i);
								i--;
							}
						}
					}
				}
			}

		}catch (Exception e) {
			throw new ServiceException("Ocurrio un error al obtener el detalle");
		}

		return lista;
	}

	public static List<SaldosFechaVO> orderSaldosByDate(final int orden, final List<SaldosFechaVO> lista, final String fechaCorte) throws ServiceException{
		SaldosFechaVO resProv = new SaldosFechaVO();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		Date iniDate = new Date();
		Date finDate = new Date();
		Date corteDate = new Date();

		try{


			for (int i = 0; i < lista.size() - 1; ++i) {
				for (int y = 0; y < lista.size() - 1; ++y) {
					iniDate = format.parse(lista.get(y).getFecha());
					finDate = format.parse(lista.get(y+1).getFecha());
					if (iniDate.compareTo(finDate) < 0) {
						resProv = lista.get(y);
						lista.set(y, lista.get(y+1));
						lista.set(y+1, resProv);
					}
				}
			}

			if(fechaCorte != null && !fechaCorte.equals("")){
				corteDate = format2.parse(fechaCorte);
				for(int i=0; i<lista.size(); i++){
					if(lista.get(i).getFecha() != null && !lista.get(i).getFecha().equals("")){
						iniDate = format.parse(lista.get(i).getFecha());
						if(iniDate.compareTo(corteDate) < 0){
							if(lista.size()>0){
								lista.remove(i);
								i--;
							}
						}
					}
				}
			}

		}catch (Exception e) {
			throw new ServiceException("Ocurrio un error al obtener el detalle");
		}

		return lista;
	}

	public static String formatoCadena(String input){
		StringBuffer res = new StringBuffer();
		input = input.toLowerCase();
		String[] strArr = input.split(" ");
		for (String str : strArr) {
			char[] stringArray = str.trim().toCharArray();
			stringArray[0] = Character.toUpperCase(stringArray[0]);
			str = new String(stringArray);
			res.append(str).append(" ");
		}
		return res.toString();
	}

	public static String filtraCadenaIdEquipo(final String input){
		String[] data = null;
		String retorno = "";
		if(input != null && !input.equals("")){
			data = input.split(" ");
			try{
				Double.parseDouble(data[0].trim());
				retorno = data[0].trim();
			}catch (Exception e) {
				retorno = input;
			}			
		}
		return retorno;
	}
	
	public static String respCodePrepagoLegacy(final String code){

		OracleProcedures oracle =  new OracleProcedures();
		List<CatatoloGetVO> obtieneCatalogo = new ArrayList<CatatoloGetVO>();
		String response = "";
		int banderaEjecutaCondicion = 0;
		try{
			int codeInt = Integer.parseInt(code);
			obtieneCatalogo = oracle.catalogoGet(7);
			for(int i=0; i< obtieneCatalogo.size(); i++) {
				if(obtieneCatalogo.get(i).getTipoRespuestaID() == codeInt){
					response = obtieneCatalogo.get(i).getTipoRepuesta();
					break;
				}
			}
			banderaEjecutaCondicion = 0;
		}catch (Exception e) {
			banderaEjecutaCondicion = 1;
			Logger.write("Detail al consultar el metodo :: banderaEjecutaCondicion");
		}

		if(banderaEjecutaCondicion == 1){
			if(code.equals("0")){
				response = "Exito";
			}else if(code.equals("1")){
				response = "Saldo insuficiente";
			}else if(code.equals("2")){
				response = "Servicio no disponible";
			}
			else if(code.equals("3")){
				response = "N�mero de celular inv�lido";
			}
			else if(code.equals("4")){
				response = "Servicio no disponible";
			}
			else if(code.equals("5")){
				response = "Clave inv�lida";
			}
			else if(code.equals("6")){
				response = "Canal inv�lido";
			}
			else if(code.equals("10")){
				response = "Longitud del servicio inv�lido";
			}
			else if(code.equals("11")){
				response = "Servicio complementario inv�lido ";
			}
			else if(code.equals("12")){
				response = "MDN no existente";
			}
			else if(code.equals("14")){
				response = "Servicio complementario no disponible(6)";
			}
			else if(code.equals("15")){
				response = "Dispositivo ya tiene un plan contratado";
			}
			else if(code.equals("16")){
				response = "Servicio no disponible para Iusacell";
			}
			else if(code.equals("17")){
				response = "Servicio no disponible para Unefon";
			}
			else if(code.equals("18")){
				response = "Dispositivo ya tiene antiplan";
			}
			else if(code.equals("19")){
				response = "Carga Inv�lida";
			}
		}
		return response; 	
	}

	public static String getKeyFromProperties(final String fileName, final String key){
		String response = "";
		try{
			String path = System.getProperty("user.home");
			FileInputStream in = new FileInputStream(path + "/"+fileName+".properties");
			Properties props = new Properties();
			props.load(in);
			in.close();
			response = props.getProperty(key);
		}catch (Exception e) {
			response = "";
		}
		return response;
	}

	public static int setKeyToProperties(final String fileName, final String key, final String value){
		int response = 0;
		try{
			String path = System.getProperty("user.home");
			FileOutputStream out = new FileOutputStream(path + "/"+fileName+".properties");
			Properties props = new Properties();
			props.setProperty(key,value);
			props.store(out, null);
			out.close();
		}catch (Exception e) {
			response = -1;
		}
		return response;
	}
	
	public static void validaTiempoDeVida(final String time, final int elapseTime) throws ServiceException{

		try{
			//Token
			String diaT = time.substring(0,2);
			String mesT = time.substring(3,5);
			String anoT = time.substring(6,10);
			String horaT = time.substring(11,13);
			String minutoT = time.substring(14,16);

			//Sistema
			Calendar calendario = Calendar.getInstance();
			int ano=calendario.get(Calendar.YEAR);
			int mes=calendario.get(Calendar.MONTH);
			int dia = calendario.get(Calendar.DATE);
			int hora =calendario.get(Calendar.HOUR_OF_DAY);
			int minuto = calendario.get(Calendar.MINUTE);

			Calendar fechaServidor = Calendar.getInstance();
			Calendar fechaToken = Calendar.getInstance();
			fechaServidor.set(ano, mes, dia, hora, minuto);
			fechaToken.set(Integer.parseInt(anoT), Integer.parseInt(mesT)-1, Integer.parseInt(diaT), Integer.parseInt(horaT), Integer.parseInt(minutoT));

			long milis1 = fechaServidor.getTimeInMillis();
			long milis2 = fechaToken.getTimeInMillis();
			long diffMinutes = (milis1 - milis2) / (60 * 1000);

			Logger.write("Current Time: " + fechaServidor.getTime().toString());
			Logger.write("Data Time: " + fechaToken.getTime().toString());
			if(diffMinutes <= elapseTime){
				Logger.write("Debe esperar "+elapseTime+" minutos para poder ejecutar la acci�n");
				throw new ServiceException("Debe esperar "+elapseTime+" minutos para poder ejecutar la acci�n");
			}
		} catch (ServiceException e){
			Logger.write("\n     Detail  : (ServiceException) " + e.getLocalizedMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public static String isThisDateWithin2MonthsRange(final String dateToValidate, final String consultaFechaDeCorteActual) {

		if(dateToValidate != null && !dateToValidate.equals("")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			sdf.setLenient(false);
			try {

				Date date = sdf.parse(dateToValidate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				Calendar newDate = Calendar.getInstance();
				Calendar currentDate = Calendar.getInstance();
				Calendar currentDateBefore1Month = Calendar.getInstance();
				currentDateBefore1Month.add(Calendar.MONTH, -1);

				if (date.before(currentDate.getTime()) && date.after(currentDateBefore1Month.getTime())) {
					return dateToValidate;
				}
				if(consultaFechaDeCorteActual.equals("1")){
					if(cal.get(Calendar.YEAR) == currentDateBefore1Month.get(Calendar.YEAR) && cal.get(Calendar.MONTH) == currentDateBefore1Month.get(Calendar.MONTH) && cal.get(Calendar.DAY_OF_MONTH) == currentDateBefore1Month.get(Calendar.DAY_OF_MONTH)){
						newDate.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
						return sdf.format(newDate.getTime());
					}
				}
				if(date.before(currentDateBefore1Month.getTime())){
					newDate.set(currentDateBefore1Month.get(Calendar.YEAR), currentDateBefore1Month.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
				}
				
				if(date.after(currentDate.getTime())){
					newDate.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH)-1, cal.get(Calendar.DAY_OF_MONTH));
				}
				return sdf.format(newDate.getTime());
				
			} catch (Exception e) {
				return "";
			}
		}
		return "";
	}
	
//	public static boolean contieneMensajeError(final String mensajeError)
//	{
//		boolean valida = false;
//		String mensaje = (mensajeError != null) ? mensajeError.toLowerCase() : "";
//		if(mensaje.contains("ha excedido el tiempo m�ximo") 
//			|| mensaje.contains("no est� conectado") 
//			|| mensaje.contains("no hay m�s datos para leer del socket")
//			|| mensaje.contains("est� en un estado inconsistente")
//			|| mensaje.contains("tuber�a rota")
//			|| mensaje.contains("connection reset")
//			|| mensaje.contains("expir� el tiempo de conexi�n")
//			)
//		{
//			valida = true;
//		}
//		
//		return valida;
//	}
	
	public static boolean contieneMensajeError(String mensajeError)
    {
        boolean valida = false;
        mensajeError = (mensajeError != null) ? mensajeError.toLowerCase() : "";
        if(!mensajeError.equals(""))
        {
            try
            {
                final OracleProcedures oracle = new OracleProcedures();
                String mensajesPermitidos;              
                mensajesPermitidos = oracle.getValorParametro(188);
                mensajesPermitidos = mensajesPermitidos+oracle.getValorParametro(189);                
                mensajesPermitidos = (mensajesPermitidos != null) ? mensajesPermitidos.toLowerCase() : "";
                final String[] arrayMensajePerm = mensajesPermitidos.split("\\|");

                for(final String msg : arrayMensajePerm)
                {
                    if(mensajeError.contains(msg))
                    {
                        valida = true;
                        break;
                    }
                }
            }
            catch (Exception e) {
                valida = false;
            }
        }       
        return valida;
    }
	 
	public static String GetUTCdatetimeAsString(final Date localTime)
	{
		  String fecha="";
		  
		  try {
			  	 SimpleDateFormat converter = new SimpleDateFormat("ddMMyyyyHHmm");
				 converter.setTimeZone(TimeZone.getTimeZone("UTC"));
				 Logger.write("local time : " + localTime);;
				 Logger.write("time in UTC : " + converter.format(localTime));
				 fecha=converter.format(localTime);
				 
			 return fecha;
		      
		  } catch (Exception e) {
			  return "";
		  }
	}
	
	public static String fechaActual()
	{
		String fechaActual = "";
		Calendar c = new GregorianCalendar();
		
		int dia = c.get(Calendar.DAY_OF_MONTH);
		int mes = c.get(Calendar.MONTH) + 1;
		int anio = c.get(Calendar.YEAR);
		String fechaDia = Integer.toString(dia);
		String fechaMes = Integer.toString(mes);
		if(dia < 10)
		{
			fechaDia = "0" + dia;
		}
		if(mes < 10)
		{
			fechaMes = "0" + mes;
		}
		
		fechaActual = fechaDia + "/" + fechaMes + "/" + anio + "";
		
		return fechaActual;
	}
	
	public static boolean isNumeric(String cadena){
		try {
			cadena=cadena.trim().replace(" ", "");
			Long.parseLong(cadena);
			return true;
		} catch (NumberFormatException  nfe){
			return false;
		}
	}
	
	public static String expoFormat(final String valor){
	      
		BigDecimal decimalDatos = new BigDecimal(valor);
		double decimalDatosdou = decimalDatos.doubleValue();
		DecimalFormat n = new DecimalFormat("######.00");
		String s = n.format(decimalDatosdou);
	
		return s;
	}
	
	public static String imagenBase64(final String nombreImagen){
		HttpServletRequest request = XFireServletController.getRequest ();
		FileInputStream datos = null;
		BufferedImage img = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Base64Encoder encoder = new Base64Encoder();
		String encodedString = "";
		
		try {
			datos =   new FileInputStream(request.getSession().getServletContext().getRealPath("/") + "static"+ File.separator + "images"+ File.separator + nombreImagen);
			Logger.write("\n  Ruta imagen " + request.getSession().getServletContext().getRealPath("/") + "static"+ File.separator + "images"+ File.separator + nombreImagen );
			img = ImageIO.read(datos);
			ImageIO.write(img, "jpg", os);
			byte[] imageBytes = os.toByteArray();
			encodedString = encoder.encode(imageBytes);
			
		} catch (Exception e) {
		   encodedString = "";
		}finally{
			request = null;
			os = null;
			img = null;
			encoder = null;
		}
		return encodedString;
	}
	
	public static boolean isNumber(String cadena){
		try {
			cadena=cadena.trim().replace(" ", "");
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException  nfe){
			return false;
		}
	}
	
	public BankCardAdditionalInfoVO getBankCardAdditionalInfoFile(final String prefix)throws ServiceException {
    	Logger.write(" >>> C o n s u l t a  a  A r c h i v o  B i n e s");
    	BankCardAdditionalInfoVO respuesta=new BankCardAdditionalInfoVO();
    	String respuestaBines="";
		respuestaBines = ResourceBundle.getBundle("bines").getString(prefix);
    	if (!respuestaBines.equals(""))
    	{
    		String[] datosAdicionalesTarjeta = respuestaBines.split("\\|");
    		respuesta.setAppliesPromotion(datosAdicionalesTarjeta[4]);
    		respuesta.setCardType(datosAdicionalesTarjeta[0]);
    		respuesta.setCardTypeDescription(datosAdicionalesTarjeta[2]);
    		respuesta.setIssuer(datosAdicionalesTarjeta[3]);
    		respuesta.setProductName(datosAdicionalesTarjeta[1]);
    	}
    	Logger.write(" <<< F i n a l i z o  C o r r e c t a m e n t e ");
    	return respuesta;
    }
	
	public static String valorMegaBytes(final String valor){
		BigDecimal trans = null;
		String respuesta="";
		try {
   			   Double bytes = Double.valueOf(valor);
			   Double megabytes = bytes/1024;
			   trans = new BigDecimal(megabytes);
			   respuesta = Utilerias.expoFormat(trans.toString());
			   respuesta = respuesta.replace(",", ".");
		} catch (Exception e) {
			Logger.write(" No se pudo obtener los bytes :: " + e.getLocalizedMessage());  
		}
		return respuesta;
	}
	
	public static String getStringUTF8(final String cadena)
	{
		String cadenaUTF8;
		if(cadena == null || cadena.trim().equals(""))
			return cadena;
		
		try {
			cadenaUTF8 = new String(cadena.getBytes(), "UTF-8");			
		} catch (UnsupportedEncodingException e) {
			cadenaUTF8 = cadena;
		} catch (Exception e) {
			cadenaUTF8 = cadena;
		}
		return cadenaUTF8;
	}
	
	public static String retornacompania(final int idServicio){
		OracleProcedures oracle = new OracleProcedures();
		String compania="";
		Logger.write(" >>> C o n s u l t a  C o m p a � i a ");
		try {
			compania=oracle.retornaDescripcionCompania(idServicio+"", new MensajeLogBean());
			if (compania.equals("0")){
				Logger.write("[ctrl]  :: No se encontr� descripcion de la UnidadNegocio :"+idServicio);
				compania="";
			}else {
				compania=compania.replace("&", "&amp;");
			}
		} catch (ServiceException e) {
			Logger.write("[ctrl]  :: No se encontr� descripcion de la UnidadNegocio :"+idServicio);
			
			switch( idServicio ){
				case 1:compania="Mi Iusacell";
					break;
				case 2:compania="Mi Unefon";
					break;
				case 4:compania="Mi AT&amp;T";
					break;
				default: compania="";
					break;
			}
		}
		Logger.write("compania:: "+compania);
		Logger.write(" <<< F i n a l i z o  C o r r e c t a m e n t e");
		return compania;
	}	
	
	public static String[] retornaRemitente(final int idServicio){
		String[]remitente=new String[2];
		
		remitente[0]="";
		remitente[1]="";
		Logger.write(" >>> C o n s u l t a  R e m i t e n t e ");
		
		switch (idServicio) {
			case 1:
				remitente[0]="portal.iusacell";
				remitente[1]="notificacionesautomaticasiusacell@it.iusacell.com.mx";
				break;
			default:
				remitente[0]="portal.iusacell";
				remitente[1]="notificacionesautomaticasiusacell@it.iusacell.com.mx";
				break;
		}
		
		Logger.write("IdServicio:"+ idServicio);
		Logger.write("NameFrom:"+ remitente[0]);
		Logger.write("MailFrom:"+ remitente[1]);
		
		Logger.write(" <<< F i n a l i z o  C o r r e c t a m e n t e");
		return remitente;
	}
	public static String imprimeVectorInt(int[] categoryId){
		String cadena="[";
		if (categoryId!=null){
			
			for (int i=0; i<categoryId.length;i++){
				if (i>0){
					cadena+=",";
				}
				cadena+=categoryId[i];
			}
		}
		cadena+="]";
		return	cadena;
	}
	public static String imprimeVectorString(String[] categoryDesc){
		String cadena="[";
		if (categoryDesc!=null){
			
			for (int i=0; i<categoryDesc.length;i++){
				if (i>0){
					cadena+=",";
				}
				cadena+=categoryDesc[i];
			}
		}
		cadena+="]";
		return	cadena;
	}
	public static String imprimeVectorString(int[] categoryDesc){
		String cadena="[";
		if (categoryDesc!=null){
			
			for (int i=0; i<categoryDesc.length;i++){
				if (i>0){
					cadena+=",";
				}
				cadena+=categoryDesc[i];
			}
		}
		cadena+="]";
		return	cadena;
	}
	public static synchronized int diferenciasDeFechas(String fechaInicial) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		double dias=0;
        try {
        	Date fechaInicio = df.parse(fechaInicial);
        	long fechaInicialMs = fechaInicio.getTime();
            Date fechaFin = new Date();
        	long fechaFinalMs = fechaFin.getTime();
        	long diferencia = fechaFinalMs - fechaInicialMs;
        	dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        } catch (ParseException ex) {
        	
        }
        return ((int) dias);
    }
	public static synchronized double difDeFecServiciosFoca(String fechaFinal) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		fechaFinal = fechaFinal.replace("T"," ");
		//String dat="";
		double dias =0;
		double horas =0;
		double  minutos =0;
		double  segundos =0;
        try {
        	Date fechaInicio = new Date();
        	long fechaInicialMs = fechaInicio.getTime();
            Date fechaFin =  df.parse(fechaFinal);
        	long fechaFinalMs = fechaFin.getTime();
        	long diferencia = fechaFinalMs - fechaInicialMs;
        	//Logger.write("Fecha Inicio:"+ fechaInicio);
        	//Logger.write("Fecha fin:"+ fechaFin);
        	//Logger.write("Diferencia: "+ diferencia);
        	segundos = diferencia / 1000 ;
        	//Logger.write("segundos :"+ segundos);
        	minutos = segundos / 60 ;
        	//Logger.write("minutos :"+ minutos);
        	horas = minutos / 60 ;
        	//Logger.write("horas :"+ horas);
        	dias = horas / 24 ;
        	//Logger.write("dias :"+ dias);
        	//dat = String.valueOf((int)dias) + ":"+String.valueOf((int)(horas % 24))+ ":"+String.valueOf((int)(minutos % 60)) + ":"+String.valueOf((int)(segundos % 60));
        	//Logger.write("tiempo "+ dat);
        } catch (ParseException ex) {
        	
        }
        return (dias);
    }
}
