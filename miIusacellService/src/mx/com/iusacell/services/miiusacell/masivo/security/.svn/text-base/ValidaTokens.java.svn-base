package mx.com.iusacell.services.miiusacell.masivo.security;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;

public class ValidaTokens {
	public void validaToken(final String cadenaValidar, final String token, final MensajeLogBean mensajeLog) throws ServiceException{
		String cadenaSistema = "";
		String cadenaToken = "";
		OracleProcedures oracle = new OracleProcedures();

		String validaToken = "";
		try{
			validaToken = oracle.getValorParametro(1);
		}catch(Exception ex){
			validaToken = "0";
		}
		Logger.write("   + ValidaToken            : " + validaToken);

		if(validaToken.equals("1")){
			try{
				//Token
				String diaT = token.substring(0, 2);
				String mesT = token.substring(2, 4);
				String anoT = token.substring(4, 8);
				String horaT = token.substring(8, 10);
				String minutoT = token.substring(10, 12);
				String tokenResta = token.substring(12);

				//Sistema
				Calendar calendario = Calendar.getInstance();
				int ano=calendario.get(Calendar.YEAR);
				int mes=calendario.get(Calendar.MONTH) + 1;
				int dia = calendario.get(Calendar.DATE);
				int hora =calendario.get(Calendar.HOUR_OF_DAY);
				int minuto = calendario.get(Calendar.MINUTE);

				String anoS = Integer.toString(ano);
				String mesS = Integer.toString(mes).length() == 1 ? "0"+Integer.toString(mes) : Integer.toString(mes);
				String diaS = Integer.toString(dia).length() == 1 ? "0"+Integer.toString(dia) : Integer.toString(dia);

				cadenaSistema = diaS + mesS + anoS + cadenaValidar;
				cadenaToken = diaT + mesT + anoT + tokenResta;

				Calendar fechaServidor = Calendar.getInstance();
				Calendar fechaToken = Calendar.getInstance();
				fechaServidor.set(ano, mes, dia, hora, minuto);
				fechaToken.set(Integer.parseInt(anoT), Integer.parseInt(mesT), Integer.parseInt(diaT), Integer.parseInt(horaT), Integer.parseInt(minutoT));

				long milis1 = fechaServidor.getTimeInMillis();
				long milis2 = fechaToken.getTimeInMillis();
				long diffMinutes = (milis2 - milis1) / (60 * 1000);

				if(diffMinutes > 5 || diffMinutes < -5 || !cadenaSistema.equals(cadenaToken)){
					throw new ServiceException("Warning :: Token Incorrecto");
				}
			} catch (ServiceException e){
				throw new ServiceException("validaToken :: " + e.getLocalizedMessage());
			}
		}
	}
	
	public void validaTokenSMS(final String cadenaValidar, final String token, final MensajeLogBean mensajeLog) throws ServiceException, ParseException{
		
		if (token == null || token.isEmpty() || token.length() < 12 || 12 + cadenaValidar.length() != token.length()) {
		      throw new ServiceException("Token incorrecto; vacío o de tamaño inválido");
		    }
		
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
	    Calendar tokenDate = Calendar.getInstance(timeZone);
	    Calendar minDate = Calendar.getInstance(timeZone);
	    Calendar maxDate = Calendar.getInstance(timeZone);
	    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    formatoDelTexto.setTimeZone(timeZone);
	    Date date = null;
	    
		OracleProcedures oracle = new OracleProcedures();

		String validaToken = "";
		try{
			validaToken = oracle.getValorParametro(179);
		}catch(Exception ex){
			validaToken = "0";
		}
		Logger.write("   + ValidaToken            : " + validaToken);

		if(validaToken.equals("1")){
			try{
				String diaT = token.substring(0, 2);
			      String mesT = token.substring(2, 4);
			      String anoT = token.substring(4, 8);
			      String horaT = token.substring(8, 10);
			      String minutoT = token.substring(10, 12);
			      String tokenResta = token.substring(12);
			      date = formatoDelTexto.parse(anoT + "-" + mesT + "-" + diaT + " " + horaT + ":" + minutoT); 
			      tokenDate.setTime(date);
			      minDate.add(Calendar.MINUTE, -5);
			      maxDate.add(Calendar.MINUTE, 5);
			      if (!cadenaValidar.equals(tokenResta) || tokenDate.before(minDate) || tokenDate.after(maxDate)) {
			        throw new ServiceException("Token incorrecto");
			      }
			} catch (ServiceException e){
				throw new ServiceException("validaToken :: " + e.getLocalizedMessage());
			}
		}
	}
	
}
