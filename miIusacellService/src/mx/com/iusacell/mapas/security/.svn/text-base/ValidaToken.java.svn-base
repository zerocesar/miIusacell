package mx.com.iusacell.mapas.security;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.rpc.ServiceException;

public class ValidaToken {

  public void validaTokenUTC(String token, String param) throws ServiceException {
    if (token == null || token.isEmpty() || token.length() < 12 || 12 + param.length() != token.length()) {
      throw new ServiceException("Token vacío o de tamaño incorrecto", new ServiceException("handledException"));
    }
    TimeZone timeZone = TimeZone.getTimeZone("UTC");
    Calendar tokenDate = Calendar.getInstance(timeZone);
    Calendar minDate = Calendar.getInstance(timeZone);
    Calendar maxDate = Calendar.getInstance(timeZone);
    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    formatoDelTexto.setTimeZone(timeZone);
    Date date = null;
    try {
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
      if (!param.equals(tokenResta) || tokenDate.before(minDate) || tokenDate.after(maxDate)) {
        throw new ServiceException("Token incorrecto", new ServiceException("handledException"));
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
  }

}
