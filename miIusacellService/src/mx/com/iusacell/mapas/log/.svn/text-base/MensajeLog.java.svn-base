package mx.com.iusacell.mapas.log;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.mapas.masivo.model.OracleProcedures;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public final class MensajeLog {

  private static final SimpleDateFormat horaLog     = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS");
  private static final PatternLayout    LayoutLog   = new PatternLayout("%d{dd/MM/yyyy HH:mm:ss,SSS} %-5p [%t] %m%n");
  private static final PatternLayout    LayoutMon   = new PatternLayout("%d{dd/MM/yyyy HH:mm:ss,SSS} [%t] %m%n");
  private static final String           DatePattern = ".dd-MM-yyyy";
  private static final String           App         = "miIusacellService";
  private static final String           ServiceLog  = "MapasService.log";
  private static final String           ErrorLog    = "MapasError.log";
  private static final String           SuccessLog  = "MapasExito.log";
  private static final String           ConsoleTar  = "System.out";
  private static String                 logDir      = "";
  private static final Integer          MethodDeep  = 3;
  private static final String           ip          = "10.189.64.53";

  /***********************************************************************************/

  private static Logger                 Log         = LogManager.getLogger("serviceLog");
  private static Logger                 Slog        = LogManager.getLogger("successLog");

  /***********************************************************************************/

  private static void ruta() {
    try {
      if (logDir == null || logDir.isEmpty()) {
        logDir = System.getProperty("jboss.var.common.location.app.logs");
        if (logDir == null || logDir.isEmpty()) {
          logDir = System.getProperty("jboss.server.log.dir");
          if (logDir == null || logDir.isEmpty()) {
            logDir = System.getProperty("user.home");
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

  private static void initLog() {
    try {
      ruta();
      if (validaApp(Log) != 3 || validaApp(Slog) != 1) {
        ConsoleAppender console = new ConsoleAppender(LayoutLog, ConsoleTar);
        DailyRollingFileAppender logN = new DailyRollingFileAppender(LayoutLog, logDir + File.separator + App + File.separator + ServiceLog, DatePattern);
        DailyRollingFileAppender logE = new DailyRollingFileAppender(LayoutMon, logDir + File.separator + App + File.separator + ErrorLog, DatePattern);
        DailyRollingFileAppender logS = new DailyRollingFileAppender(LayoutMon, logDir + File.separator + App + File.separator + SuccessLog, DatePattern);
        ///
        console.setThreshold(Level.OFF);
        logN.setThreshold(Level.INFO);
        logS.setThreshold(Level.INFO);
        logE.setThreshold(Level.ERROR);
        logN.setAppend(true);
        logS.setAppend(true);
        logE.setAppend(true);
        ///
        Log.addAppender(logN);
        Log.addAppender(logE);
        Log.addAppender(console);
        Slog.addAppender(logS);
        ///
        Slog.setAdditivity(false);
      }
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());
    }

  }

  @SuppressWarnings("unchecked")
  private static int validaApp(Logger log) {
    int total = 0;
    try {
      Enumeration<Appender> tot = log.getAllAppenders();
      while (tot.nextElement() != null) {
        total++;
      }
    } catch (NoSuchElementException e) {
      //NO EXISTE OTRO ELEMENTO
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());
    }
    return total;
  }

  /***********************************************************************************/

  public static void debug(String texto) {
    initLog();
    if (Log.isDebugEnabled()) {
      Log.debug(texto);
    }
  }

  public static void init() {
    initLog();
    Log.info("<" + getThread() + "> :: >> L l a m a d a   W e b   S e r v i c e  -  " + getMethodName(MethodDeep));
  }

  public static void write(String texto) {
    initLog();
    Log.info("<" + getThread() + "> :: " + texto.replace("\n", " "));
  }

  public static void write(String texto, long tiempo_inicial) {
    initLog();
    Log.info("<" + getThread() + "> :: " + texto.replace("\n", " ") + " [" + calcula_tiempo_respuesta(tiempo_inicial) + "]");
  }

  public static void warning(String texto) {
    initLog();
    Log.warn("<" + getThread() + "> ::    " + texto.replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
  }

  public static void error(String texto) {
    Date date = new Date();
    try {
      initLog();
      Log.error("<" + getThread() + "> ::    " + texto.replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
      OracleProcedures.registraLoggin("Gestionador de Tiendas Iusacell", getMethodName(MethodDeep), "ERROR", texto, Integer.valueOf(getThread()), ip, horaLog.format(date), 0, Long.parseLong(OracleProcedures.ObtieneParamConfig(20004).getValor()));
    } catch (Exception e) {
      Log.warn("<" + getThread() + "> ::    " + e.getLocalizedMessage().replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
    } finally {
      LogManager.shutdown();
    }
  }

  public static void fatal(String texto) {
    Date date = new Date();
    try {
      initLog();
      Log.fatal("<" + getThread() + "> ::    " + texto.replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
      OracleProcedures.registraLoggin("Gestionador de Tiendas Iusacell", getMethodName(MethodDeep), "FATAL", texto, Integer.valueOf(getThread()), ip, horaLog.format(date), 0, Long.parseLong(OracleProcedures.ObtieneParamConfig(20004).getValor()));
    } catch (Exception e) {
      Log.warn("<" + getThread() + "> ::    " + e.getLocalizedMessage().replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
    } finally {
      LogManager.shutdown();
    }
  }

  public static void end(long tiempo_inicial) {
    Date date = new Date();
    try {
      initLog();
      Log.info("<" + getThread() + "> :: << L l a m a d a   W e b   S e r v i c e  -  " + getMethodName(MethodDeep) + " [" + calcula_tiempo_respuesta(tiempo_inicial) + "]");
      Slog.info("<" + getThread() + "> :: " + getMethodName(MethodDeep) + " | Successful Operation | Transaction Time : " + tiempo_total(tiempo_inicial));
      OracleProcedures.registraLoggin("Gestionador de Tiendas Iusacell", getMethodName(MethodDeep), "INFO", "Successful Operation", Integer.valueOf(getThread()), ip, horaLog.format(date), Long.parseLong(tiempo_total(tiempo_inicial)), Long.parseLong(OracleProcedures.ObtieneParamConfig(20004).getValor()));
    } catch (Exception e) {
      Log.warn("<" + getThread() + "> ::    " + e.getLocalizedMessage().replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
    } finally {
      LogManager.shutdown();
    }
  }

  public static void exception(Throwable e) {
    final Date date = new Date();
    try {
      initLog();
      if (e instanceof ServiceException) {
        if (e.getCause() != null && e.getCause().getMessage().contains("handledException")) {
          Log.warn("<" + getThread() + "> ::    " + e.getClass().getName() + " : " + e.getLocalizedMessage().replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
        } else if (e.getCause() != null && e.getCause().getMessage().contains("hFatalException")) {
          Log.fatal("<" + getThread() + "> ::    " + e.getClass().getName() + " : " + e.getLocalizedMessage().replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
          OracleProcedures.registraLoggin("Gestionador de Tiendas Iusacell", getMethodName(MethodDeep), "FATAL", e.getLocalizedMessage().replace("\n", " "), Integer.valueOf(getThread()), ip, horaLog.format(date), 0, Long.parseLong(OracleProcedures.ObtieneParamConfig(20004).getValor()));
        } else {
          Log.error("<" + getThread() + "> ::    " + e.getClass().getName() + " : " + e.getLocalizedMessage().replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
          OracleProcedures.registraLoggin("Gestionador de Tiendas Iusacell", getMethodName(MethodDeep), "ERROR", e.getLocalizedMessage().replace("\n", " "), Integer.valueOf(getThread()), ip, horaLog.format(date), 0, Long.parseLong(OracleProcedures.ObtieneParamConfig(20004).getValor()));
        }
      } else if (e instanceof SQLException) {
        if (((SQLException) e).getErrorCode() == 20000) {
          Log.warn("<" + getThread() + "> ::    " + e.getClass().getName() + " : " + e.getLocalizedMessage().replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
        } else {
          Log.error("<" + getThread() + "> ::    " + e.getClass().getName() + " : " + e.getLocalizedMessage().replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
          OracleProcedures.registraLoggin("Gestionador de Tiendas Iusacell", getMethodName(MethodDeep), "ERROR", e.getLocalizedMessage().replace("\n", " "), Integer.valueOf(getThread()), ip, horaLog.format(date), 0, Long.parseLong(OracleProcedures.ObtieneParamConfig(20004).getValor()));
        }
      } else {
        Log.error("<" + getThread() + "> ::    " + e.getClass().getName() + " : " + e.getLocalizedMessage().replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
        OracleProcedures.registraLoggin("Gestionador de Tiendas Iusacell", getMethodName(MethodDeep), "ERROR", e.getLocalizedMessage().replace("\n", " "), Integer.valueOf(getThread()), ip, horaLog.format(date), 0, Long.parseLong(OracleProcedures.ObtieneParamConfig(20004).getValor()));
      }
    } catch (Exception ex) {
      Log.warn("<" + getThread() + "> ::    " + e.getLocalizedMessage().replace("\n", " ") + " [" + getMethodName(MethodDeep) + "]");
    } finally {
      LogManager.shutdown();
    }
  }

  /**************************************************************************************************************/

  public static String calcula_tiempo_respuesta(long tiempo_inicial) {
    tiempo_inicial = System.currentTimeMillis() - tiempo_inicial;
    return String.valueOf(String.format("%02dm:%02ds:%03dms", tiempo_inicial / 60000, (tiempo_inicial % 60000) / 1000, (tiempo_inicial % 60000) % 1000));
  }

  public static String tiempo_total(long tiempo_inicial) {
    tiempo_inicial = System.currentTimeMillis() - tiempo_inicial;
    return String.valueOf(tiempo_inicial);
  }

  private static String getMethodName(final int depth) {
    final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
    return ste[depth].getMethodName();
  }

  private static String getThread() {
    return String.valueOf(String.format("%06d", Thread.currentThread().getId()));
  }

}
