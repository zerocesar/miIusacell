package mx.com.iusacell.mapas.masivo.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.mapas.database.connection.OracleConnection;
import mx.com.iusacell.mapas.log.MensajeLog;
import mx.com.iusacell.mapas.vo.CatalogoCaeVO;
import mx.com.iusacell.mapas.vo.CatalogoCompetenciaVO;
import mx.com.iusacell.mapas.vo.CatalogoControlStreetViewVO;
import mx.com.iusacell.mapas.vo.CatalogoEmpleadoVO;
import mx.com.iusacell.mapas.vo.CatalogoGeoVersionVO;
import mx.com.iusacell.mapas.vo.CatalogoParamConfigVO;
import mx.com.iusacell.mapas.vo.ControlLogDSIVO;
import mx.com.iusacell.mapas.vo.ControlUsuarioVO;
import mx.com.iusacell.mapas.vo.HorarioCAEVO;
import mx.com.iusacell.mapas.vo.HorarioEmpleadoVO;
import mx.com.iusacell.mapas.vo.ReporteActivacionesV2VO;
import mx.com.iusacell.mapas.vo.ReporteEmpleadoV2VO;
import mx.com.iusacell.mapas.vo.ReporteEmpleadoVO;
import oracle.jdbc.OracleTypes;

public class OracleProcedures {

  /*********************************************** GENERICO ***********************************/

  private void collector(ResultSet rsetOBJ, CallableStatement stmsOBJ, Connection connOBJ) {
    try {
      if (rsetOBJ != null)
        rsetOBJ.close();
    } catch (SQLException e) {
      MensajeLog.exception(e);
    }
    try {
      if (stmsOBJ != null)
        stmsOBJ.close();
    } catch (SQLException e) {
      MensajeLog.exception(e);
    }
    try {
      if (connOBJ != null)
        connOBJ.close();
    } catch (SQLException e) {
      MensajeLog.exception(e);
    }
  }

  private static void collectorS(ResultSet rsetOBJ, CallableStatement stmsOBJ, Connection connOBJ) {
    try {
      if (rsetOBJ != null)
        rsetOBJ.close();
    } catch (SQLException e) {
      MensajeLog.exception(e);
    }
    try {
      if (stmsOBJ != null)
        stmsOBJ.close();
    } catch (SQLException e) {
      MensajeLog.exception(e);
    }
    try {
      if (connOBJ != null)
        connOBJ.close();
    } catch (SQLException e) {
      MensajeLog.exception(e);
    }
  }

  private static void collectorS(CallableStatement stmsOBJ, Connection connOBJ) {
    try {
      if (stmsOBJ != null)
        stmsOBJ.close();
    } catch (SQLException e) {
      MensajeLog.exception(e);
    }
    try {
      if (connOBJ != null)
        connOBJ.close();
    } catch (SQLException e) {
      MensajeLog.exception(e);
    }
  }

  private void collector(Statement stmsOBJ, Connection connOBJ) {
    try {
      if (stmsOBJ != null)
        stmsOBJ.close();
    } catch (SQLException e) {
      MensajeLog.exception(e);
    }
    try {
      if (connOBJ != null)
        connOBJ.close();
    } catch (SQLException e) {
      MensajeLog.exception(e);
    }
  }

  private String ObtieneUrl(double latitudsv, double longitudsv, String strZoom, double rotacion, double angulo) {
    String commURL = "";
    commURL = "http://maps.googleapis.com/maps/api/streetview?size=90x90&location=" + String.valueOf(latitudsv) + "," + String.valueOf(longitudsv) + "&fov=" + strZoom + "&heading=" + String.valueOf(rotacion) + "&pitch=" + String.valueOf(angulo) + "&sensor=false";
    return commURL;
  }

  private String capitalize(String line) {
    line = line.toLowerCase();
    String[] parte = line.split(" ");
    String res = "";
    int length = parte.length;
    for (int i = 0; i < length; i++) {
      res += Character.toUpperCase(parte[i].charAt(0)) + parte[i].substring(1);
      res += " ";
    }
    res = res.trim();
    return res;
  }

  private boolean esHorarioVerano(Date hoy) {
    Date inicio = getLastSunday(3, Calendar.getInstance().get(Calendar.YEAR));
    Date fin = getLastSunday(9, Calendar.getInstance().get(Calendar.YEAR));
    if (hoy.after(inicio) && hoy.before(fin)) {
      return true;
    }
    return false;
  }

  public Date getLastSunday(int month, int year) {
    Calendar cal = Calendar.getInstance();
    cal.set(year, month + 1, 1);
    int dayOftheWeek = cal.get(Calendar.DAY_OF_WEEK);
    int val = dayOftheWeek == Calendar.SUNDAY ? -7 : -(dayOftheWeek - 1);
    cal.add(Calendar.DAY_OF_MONTH, val);
    return cal.getTime();
  }

  public Date getFirstSunday(int month, int year) {
    Calendar cal = Calendar.getInstance();
    cal.set(year, month, 1);
    int dayOftheWeek = cal.get(Calendar.DAY_OF_WEEK);
    int val = dayOftheWeek == Calendar.SUNDAY ? 0 : -(dayOftheWeek - 8);
    cal.add(Calendar.DAY_OF_MONTH, val);
    return cal.getTime();
  }

  public static boolean validaParam(int param) {
    boolean retorno = false;
    CatalogoParamConfigVO status = new CatalogoParamConfigVO();
    try {
      status = OracleProcedures.ObtieneParamConfig(param);
      if (!status.getValor().trim().equals("1")) {
        retorno = true;
      }
    } catch (Exception e) {
      MensajeLog.warning("   " + e.getClass().getName() + " : " + e.getLocalizedMessage());
    }
    return retorno;
  }

  /*********************************************** FUNCTIONS ***********************************/

  public static CatalogoParamConfigVO ObtieneParamConfig(int idParamConfig) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: ObtieneParamConfig");
    CatalogoParamConfigVO retorno = new CatalogoParamConfigVO();
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOVISORPARAMCONFIG.FNGEOGEPARAMCONFIG(?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOVISORPARAMCONFIG.FNGEOGEPARAMCONFIG(" + idParamConfig + ") }";
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> idParamConfig   :: " + idParamConfig);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.setInt(i++, idParamConfig);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      while (rsetOBJ.next()) {
        retorno.setId(rsetOBJ.getInt(1));
        retorno.setDescripcion(rsetOBJ.getString(2));
        retorno.setValor(rsetOBJ.getString(3));
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collectorS(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: " + retorno.getId() + " - " + retorno.getDescripcion() + ", Consultado en", timeINI);
    return retorno;
  }

  public static void registraLoggin(String aplicacion, String metodo, String nivel, String mensaje, int usuario, String ip, String fecha, long ejecucion, long max) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: registraLoggin");
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOINSMONITOREO(?,?,?,?,?,?,?,?,?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOINSMONITOREO(" + aplicacion + "," + metodo + "," + nivel + "," + mensaje + "," + usuario + "," + ip + "," + fecha + "," + ejecucion + "," + max + ") }";
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> aplicacion      :: " + aplicacion);
      MensajeLog.write("   >>> metodo          :: " + metodo);
      MensajeLog.write("   >>> nivel           :: " + nivel);
      MensajeLog.write("   >>> mensaje         :: " + mensaje);
      MensajeLog.write("   >>> usuario         :: " + usuario);
      MensajeLog.write("   >>> ip              :: " + ip);
      MensajeLog.write("   >>> fecha           :: " + fecha);
      MensajeLog.write("   >>> ejecucion       :: " + ejecucion);
      MensajeLog.write("   >>> max             :: " + max);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
      stmsOBJ.setString(i++, aplicacion);
      stmsOBJ.setString(i++, metodo);
      stmsOBJ.setString(i++, nivel);
      stmsOBJ.setString(i++, mensaje);
      stmsOBJ.setInt(i++, usuario);
      stmsOBJ.setString(i++, ip);
      stmsOBJ.setString(i++, fecha);
      stmsOBJ.setLong(i++, ejecucion);
      stmsOBJ.setLong(i++, max);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      if (stmsOBJ.getInt(1) != 0) {
        throw new ServiceException("Inserción no exitosa en la DB: " + stmsOBJ.getInt(1), new ServiceException("handledException"));
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collectorS(stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Insertado en", timeINI);
  }

  /**********************************************************************************************/

  public CatalogoGeoVersionVO ObtieneVersionGeometria() throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: ObtieneVersionGeometria");
    CatalogoGeoVersionVO retorno = new CatalogoGeoVersionVO();
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOGEOMETRIA.FNGETGEOMETRIAVERCURRENT() }";
      commPRN = "{ ? = call IUSAGEO.PAGEOGEOMETRIA.FNGETGEOMETRIAVERCURRENT() }";
      MensajeLog.write("   FN                  :: " + commPRN);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      retorno.setVersion(0);
      while (rsetOBJ.next()) {
        retorno.setVersion(rsetOBJ.getInt(1));
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Versión " + retorno.getVersion() + ", Consultado en", timeINI);
    return retorno;
  }

  public ControlUsuarioVO obtieneUsuario(int idEmpleado, int version) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: obtieneUsuario");
    ControlUsuarioVO retorno = new ControlUsuarioVO();
    List<Integer> permisos = null;
    List<Integer> supervision = null;
    retorno.setExiste(false);
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOGESTIONADOR.FNGETUSUARIOREG(?,?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOGESTIONADOR.FNGETUSUARIOREG(" + idEmpleado + "," + version + ") }";
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> idEmpleado      :: " + idEmpleado);
      MensajeLog.write("   >>> version         :: " + version);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.setInt(i++, idEmpleado);
      stmsOBJ.setInt(i++, version);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      while (rsetOBJ.next()) {
        retorno.setVersion(rsetOBJ.getInt("VERSION"));
        if (retorno.getVersion() == version) {
          try {
            String[] niveles = rsetOBJ.getString("NIVEL").split("\\|");
            try {
              retorno.setIdNivel(Integer.parseInt(niveles[0]));
            } catch (Exception e) {}
            try {
              retorno.setIdGeo(Integer.parseInt(niveles[1]));
            } catch (Exception e) {}
          } catch (Exception e) {}
          if (rsetOBJ.getString("ACCESOS") != null && !rsetOBJ.getString("ACCESOS").trim().equals("")) {
            permisos = new ArrayList<Integer>();
            String[] perm = rsetOBJ.getString("ACCESOS").split("\\|");
            for (int j = 0; j < perm.length; j++) {
              permisos.add(Integer.parseInt(perm[j]));
            }
          }
          if (rsetOBJ.getString("SUPERVISION") != null && !rsetOBJ.getString("SUPERVISION").trim().equals("")) {
            supervision = new ArrayList<Integer>();
            String[] superv = rsetOBJ.getString("SUPERVISION").split("\\|");
            for (int j = 0; j < superv.length; j++) {
              supervision.add(Integer.parseInt(superv[j]));
            }
          }
          retorno.setPermisos(permisos);
          retorno.setSupervision(supervision);
        }//IF VERSION
        retorno.setExiste(true);
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Consultado en", timeINI);
    return retorno;
  }

  public CatalogoGeoVersionVO ObtieneVersionCae() throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: ObtieneVersionCae");
    CatalogoGeoVersionVO retorno = new CatalogoGeoVersionVO();
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOCATALOGOGET.FNGETCAEVERCURRENT() }";
      commPRN = "{ ? = call IUSAGEO.PAGEOCATALOGOGET.FNGETCAEVERCURRENT() }";
      MensajeLog.write("   FN                  :: " + commPRN);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.execute();
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      MensajeLog.write("   <<<                 :: FN", timeINI);
      retorno.setVersion(0);
      while (rsetOBJ.next()) {
        retorno.setVersion(rsetOBJ.getInt(1));
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Consultado en", timeINI);
    return retorno;
  }

  public List<CatalogoCaeVO> ObtieneCaes() throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: ObtieneCaes");
    List<CatalogoCaeVO> retorno = new ArrayList<CatalogoCaeVO>();
    String commORA = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    Boolean filtra = false;
    int i = 1;
    try {
      filtra = validaParam(10002);
      commORA = "{ ? = call IUSAGEO.PAGEOGESTIONADOR.FNGEOGETCAEIPAD() }";
      connOBJ = OracleConnection.getConnection(1);
      MensajeLog.write("   FN                  :: " + commORA);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      while (rsetOBJ.next()) {
        CatalogoCaeVO cat = new CatalogoCaeVO();
        cat.setIdCae(rsetOBJ.getInt("IDCAE"));
        cat.setIdRegion(rsetOBJ.getInt("IDREGION"));
        cat.setClave(rsetOBJ.getString("CLAVE"));
        cat.setDescripcion(rsetOBJ.getString("DESCRIPCION"));
        cat.setDireccion(rsetOBJ.getString("DOMICILIO"));
        cat.setLongitud(rsetOBJ.getDouble("LONGITUD"));
        cat.setLatitud(rsetOBJ.getDouble("LATITUD"));
        if (rsetOBJ.getString("TELEFONO") == null || rsetOBJ.getString("TELEFONO").trim().equals("")) {
          cat.setTelefono("No disponible");
        } else {
          cat.setTelefono(rsetOBJ.getString("TELEFONO"));
        }
        cat.setPovLatitud(rsetOBJ.getDouble("POVLATITUD"));
        cat.setPovLongitud(rsetOBJ.getDouble("POVLONGITUD"));
        cat.setPovZoom(rsetOBJ.getDouble("POVZOOM"));
        cat.setPovHeading(rsetOBJ.getDouble("POVHEADING"));
        cat.setPovPitch(rsetOBJ.getDouble("POVPITCH"));
        if (filtra) {
          /* VERSION : OMITIR COORPORATIVO - PRODUCCION */
          if (rsetOBJ.getString("CLAVE").matches("[A-Z]{1}[\\d]{1}-[\\d]{4}")) {
            retorno.add(cat);
          }
        } else {
          /* VERSION : CON COORPORATIVO - DESARROLLO */
          retorno.add(cat);
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: " + retorno.size() + " Elementos consultado en", timeINI);
    return retorno;
  }

  public List<HorarioEmpleadoVO> obtieneAsistenciaEmpleado(int idCae) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: obtieneAsistenciaEmpleado");
    List<HorarioEmpleadoVO> retorno = new ArrayList<HorarioEmpleadoVO>();
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    try {
      int i = 1;
      commORA = "{ ? = call IUSAGEO.PAGEOGESTIONADOR.FNGEOGETASISTENCIAEMP(?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOGESTIONADOR.FNGEOGETASISTENCIAEMP(" + idCae + ") }";
      connOBJ = OracleConnection.getConnection(1);
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> idCae           :: " + idCae);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.setInt(i++, idCae);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      while (rsetOBJ.next()) {
        HorarioEmpleadoVO cat = new HorarioEmpleadoVO();
        cat.setIdEmpleado(rsetOBJ.getInt("IDEMPLEADO"));
        cat.setRegistroEntrada(rsetOBJ.getString("REGISTROENTRADA"));
        cat.setRegistroSalida(rsetOBJ.getString("REGISTROSALIDA"));
        retorno.add(cat);
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: " + retorno.size() + " Elementos consultado en", timeINI);
    return retorno;
  }

  public String obtieneClob(int version) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: obtieneClob");
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    //Clob clob = null;
    String respuesta;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOGEOMETRIANUEVO.FNGEOGETCADENAGEMETRIA(?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOGEOMETRIANUEVO.FNGEOGETCADENAGEMETRIA(" + version + ") }";
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> version         :: " + version);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CLOB);
      stmsOBJ.setInt(i++, version);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      respuesta = stmsOBJ.getString(1);
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Procesado en", timeINI);
    return respuesta;
  }

  public List<CatalogoCompetenciaVO> ObtieneGeoCompetenciaVisor() throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: ObtieneGeoCompetenciaVisor");
    List<CatalogoCompetenciaVO> retorno = new ArrayList<CatalogoCompetenciaVO>();
    String commORA = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOCOMPETENCIA.FNGEOGETCOMP() }";
      MensajeLog.write("   FN                  :: " + commORA);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.execute();
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      MensajeLog.write("   <<<                 :: FN", timeINI);
      while (rsetOBJ.next()) {
        CatalogoCompetenciaVO cat = new CatalogoCompetenciaVO();
        cat.setIdCompetencia(rsetOBJ.getInt(1));
        cat.setDescCompetencia(rsetOBJ.getString(2));
        cat.setIdCanal(rsetOBJ.getInt(3));
        //cat.setDescCanal(rsetOBJ.getString(4));
        cat.setLongitud(rsetOBJ.getDouble(5));
        cat.setLatitud(rsetOBJ.getDouble(6));
        cat.setDireccion(rsetOBJ.getString(7));
        if (rsetOBJ.getString(8) == null || rsetOBJ.getString(8).equals("")) {
          cat.setTelefono("No disponible");
        } else {
          cat.setTelefono(rsetOBJ.getString(8));
        }
        //cat.setIdDivision(rsetOBJ.getInt(9));
        cat.setIdPlaza(rsetOBJ.getInt(10));
        cat.setIdRegion(rsetOBJ.getInt(11));
        retorno.add(cat);
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: " + retorno.size() + " Elementos consultados en", timeINI);
    return retorno;
  }

  public List<CatalogoControlStreetViewVO> obtieneStreetViewCompetencia() throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: obtieneStreetViewCompetencia");
    List<CatalogoControlStreetViewVO> retorno = new ArrayList<CatalogoControlStreetViewVO>();
    String commORA = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOCOMPETENCIA.FNGEOGETSTREETVIEWCOMP() }";
      MensajeLog.write("   FN                  :: " + commORA);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      while (rsetOBJ.next()) {
        CatalogoControlStreetViewVO cat = new CatalogoControlStreetViewVO();
        cat.setClaveCae(String.valueOf(rsetOBJ.getInt(1)));
        cat.setRotacion(rsetOBJ.getDouble(3));
        cat.setAngulo(rsetOBJ.getDouble(4));
        cat.setZoom(rsetOBJ.getDouble(5));
        cat.setLatitud(rsetOBJ.getDouble(6));
        cat.setLongitud(rsetOBJ.getDouble(7));
        if (rsetOBJ.getString(2) == null || rsetOBJ.getString(2).equals("")) {
          cat.setUrl(ObtieneUrl(cat.getLatitud(), cat.getLongitud(), String.valueOf(cat.getZoom()), cat.getRotacion(), cat.getAngulo()));
        } else {
          cat.setUrl(rsetOBJ.getString(2));
        }
        retorno.add(cat);
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: " + retorno.size() + " Elementos consultados en", timeINI);
    return retorno;
  }

  public List<ReporteEmpleadoV2VO> obtieneActivacionesEmpleadoV2(String fechaInicio, String fechaFin, int idCae) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: obtieneActivacionesEmpleadoV2");
    List<ReporteEmpleadoV2VO> retorno = new ArrayList<ReporteEmpleadoV2VO>();
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    try {
      int i = 1;
      commORA = "{ ? = call SCSIRIUSA.PASIRRPTVENTAS.FNSIRRPTVTACAEUSRV1(?,?,?) }";
      commPRN = "{ ? = call SCSIRIUSA.PASIRRPTVENTAS.FNSIRRPTVTACAEUSRV1((" + fechaInicio + "," + fechaFin + "," + idCae + ") }";
      connOBJ = OracleConnection.getConnection(1);
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> fechaInicio     :: " + fechaInicio);
      MensajeLog.write("   >>> fechaFin        :: " + fechaFin);
      MensajeLog.write("   >>> idCae           :: " + idCae);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.setInt(i++, idCae);
      stmsOBJ.setString(i++, fechaInicio);
      stmsOBJ.setString(i++, fechaFin);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      while (rsetOBJ.next()) {
        ReporteEmpleadoV2VO cat = new ReporteEmpleadoV2VO();
        cat.setNoEmpleado(rsetOBJ.getInt("EMPLEADOID"));

        cat.setActivacionesNuevo(rsetOBJ.getInt("CANTIDAD_NUEVO"));
        cat.setActivacionesPropio(rsetOBJ.getInt("CANTIDAD_PROPIO"));
        cat.setMontoNuevo(rsetOBJ.getDouble("MONTO_NUEVO"));
        cat.setMontoPropio(rsetOBJ.getDouble("MONTO_PROPIO"));

        cat.setActivacionesTotal(rsetOBJ.getInt("CANTIDAD_TOTAL"));
        cat.setMontoTotal(rsetOBJ.getDouble("MONTO_TOTAL"));

        retorno.add(cat);
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: " + retorno.size() + " Elementos consultado en", timeINI);
    return retorno;
  }

  public List<ReporteActivacionesV2VO> obtieneActivacionesCaeV2(String fechaInicio, String fechaFin, int idEmpleado) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: obtieneActivacionesCaeV2");
    List<ReporteActivacionesV2VO> retorno = new ArrayList<ReporteActivacionesV2VO>();
    String commORA = "";
    String commPRN = "";
    String auxCae = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    try {
      int i = 1;
      commORA = "{ ? = call SCSIRIUSA.PASIRRPTVENTAS.FNSIRRPTVTACAEV1(?,?,?) }";
      commPRN = "{ ? = call SCSIRIUSA.PASIRRPTVENTAS.FNSIRRPTVTACAEV1(" + idEmpleado + "," + fechaFin + "," + fechaInicio + ") }";
      connOBJ = OracleConnection.getConnection(1);
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> fechaInicio     :: " + fechaInicio);
      MensajeLog.write("   >>> fechaFin        :: " + fechaFin);
      MensajeLog.write("   >>> caes            :: " + auxCae);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.setInt(i++, idEmpleado);
      stmsOBJ.setString(i++, fechaInicio);
      stmsOBJ.setString(i++, fechaFin);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      while (rsetOBJ.next()) {
        ReporteActivacionesV2VO cat = new ReporteActivacionesV2VO();
        cat.setIdCae(rsetOBJ.getInt("IDCAE"));

        cat.setCantidadNuevoActual(rsetOBJ.getInt("CANTIDAD_NUEVO_ACTUAL"));
        cat.setCantidadNuevoPlan(rsetOBJ.getInt("CANTIDAD_NUEVO_PLAN"));
        cat.setDiferenciaCantidadNuevo(rsetOBJ.getInt("DIFERENCIA_CANTIDAD_NUEVO"));

        cat.setMontoNuevoActual(rsetOBJ.getDouble("MONTO_NUEVO_ACTUAL"));
        cat.setMontoNuevoPlan(rsetOBJ.getDouble("MONTO_NUEVO_PLAN"));
        cat.setDiferenciaMontoNuevo(rsetOBJ.getDouble("DIFERENCIA_MONTO_NUEVO"));
        cat.setDiferenciaPorcentajeNuevo(rsetOBJ.getDouble("DIFERENCIA_PORCENTAJE_NUEVO"));

        cat.setCantidadPropioActual(rsetOBJ.getInt("CANTIDAD_PROPIO_ACTUAL"));
        cat.setCantidadPropioPlan(rsetOBJ.getInt("CANTIDAD_PROPIO_PLAN"));
        cat.setDiferenciaCantidadPropio(rsetOBJ.getInt("DIFERENCIA_CANTIDAD_PROPIO"));

        cat.setMontoPropioActual(rsetOBJ.getDouble("MONTO_PROPIO_ACTUAL"));
        cat.setMontoPropioPlan(rsetOBJ.getDouble("MONTO_PROPIO_PLAN"));
        cat.setDiferenciaMontoPropio(rsetOBJ.getDouble("DIFERENCIA_MONTO_PROPIO"));
        cat.setDiferenciaPorcentajePropio(rsetOBJ.getDouble("DIFERENCIA_PORCENTAJE_PROPIO"));

        cat.setCantidadTotalPlan(rsetOBJ.getInt("CANTIDAD_TOTAL_PLAN"));
        cat.setCantidadTotalActual(rsetOBJ.getInt("CANTIDAD_TOTAL_ACTUAL"));
        cat.setDiferenciaCantidadTotal(rsetOBJ.getInt("DIFERENCIA_CANTIDAD_TOTAL"));

        cat.setMontoTotalActual(rsetOBJ.getDouble("MONTO_TOTAL_ACTUAL"));
        cat.setMontoTotalPlan(rsetOBJ.getDouble("MONTO_TOTAL_PLAN"));
        cat.setDiferenciaMontoTotal(rsetOBJ.getDouble("DIFERENCIA_MONTO_TOTAL"));
        cat.setDiferenciaPorcentajeTotal(rsetOBJ.getDouble("DIFERENCIA_PORCENTAJE_TOTAL"));
        retorno.add(cat);
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: " + retorno.size() + " Elementos consultado en", timeINI);
    return retorno;
  }

  public ControlLogDSIVO registraLogginDSI(int plataforma, int noEmpleado, String ip, String fecha) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: registraLogginDSI");
    ControlLogDSIVO retorno = new ControlLogDSIVO();
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOINSLOGDSI(?,?,?,?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOINSLOGDSI(" + plataforma + "," + noEmpleado + "," + ip + "," + fecha + ") }";
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> plataforma      :: " + plataforma);
      MensajeLog.write("   >>> noEmpleado      :: " + noEmpleado);
      MensajeLog.write("   >>> ip              :: " + ip);
      MensajeLog.write("   >>> fecha           :: " + fecha);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.setInt(i++, plataforma);
      stmsOBJ.setInt(i++, noEmpleado);
      stmsOBJ.setString(i++, ip);
      stmsOBJ.setString(i++, fecha);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      while (rsetOBJ.next()) {
        try {
          retorno.setIdStatus(rsetOBJ.getInt("IDSTATUS"));
        } catch (Exception e) {}
        try {
          retorno.setMinutos(rsetOBJ.getInt("MINUTOS"));
          retorno.setStatus(rsetOBJ.getString("STATUS"));
        } catch (Exception e) {}
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Insertado en", timeINI);
    return retorno;
  }

  public int validaSesion(int idSesion, int noEmpleado) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: validaSesion");
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    int i = 1;
    int retorno = -1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOGETVALIDASESION(?,?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOGETVALIDASESION(" + idSesion + "," + noEmpleado + ") }";
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> idSesion        :: " + idSesion);
      MensajeLog.write("   >>> noEmpleado      :: " + noEmpleado);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
      stmsOBJ.setInt(i++, idSesion);
      stmsOBJ.setInt(i++, noEmpleado);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      retorno = stmsOBJ.getInt(1);
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Consultado en", timeINI);
    return retorno;
  }

  public void actualizaStatusDSI(int sesion, int noEmpleado, int idStatus) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: actualizaStatusDSI");
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOUDPSESIONSTATUS(?,?,?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOUDPSESIONSTATUS(" + sesion + "," + noEmpleado + "," + idStatus + ")";
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> sesion          :: " + sesion);
      MensajeLog.write("   >>> noEmpleado      :: " + noEmpleado);
      MensajeLog.write("   >>> idStatus        :: " + idStatus);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
      stmsOBJ.setInt(i++, sesion);
      stmsOBJ.setInt(i++, noEmpleado);
      stmsOBJ.setInt(i++, idStatus);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      if (stmsOBJ.getInt(1) != 0) {
        throw new ServiceException("Inserción no exitosa en la DB: " + stmsOBJ.getInt(1), new ServiceException("handledException"));
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Insertado en", timeINI);
  }

  public String actualizaIntentosDSI(int noEmpleado, Integer sesion) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: actualizaIntentosDSI");
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    String retorno = "";
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOUPDINTENTOSEROR(?,?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOUPDINTENTOSEROR(" + noEmpleado + "," + sesion + ")";
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> noEmpleado      :: " + noEmpleado);
      MensajeLog.write("   >>> sesion          :: " + sesion);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.VARCHAR);
      stmsOBJ.setInt(i++, noEmpleado);
      stmsOBJ.setInt(i++, sesion);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      retorno = stmsOBJ.getString(1);
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Insertado en", timeINI);
    return retorno;
  }

  public int statusSesion(int idSesion) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: statusSesion");
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    int i = 1;
    int retorno = 0;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOGETSTATUSSESION(?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOGETSTATUSSESION(" + idSesion + ") }";
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> idSesion        :: " + idSesion);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
      stmsOBJ.setInt(i++, idSesion);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      retorno = stmsOBJ.getInt(1);
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Consultado en", timeINI);
    return retorno;
  }

  public void insertaActividad(int idSesion, String actividades, String ip) throws SQLException, ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: insertaActividad");
    String commORA = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOINSREGACTIVIDAD(?,?,?) }";
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
      stmsOBJ.setInt(i++, idSesion);
      stmsOBJ.setString(i++, actividades);
      stmsOBJ.setString(i++, ip);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      if (stmsOBJ.getInt(1) != 0) {
        throw new ServiceException("ERROR AL TERMINAR LA SESION, CODIGO: " + stmsOBJ.getInt(1), new ServiceException("handledException"));
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Insertado en", timeINI);
  }

  public void terminaSesiones(int noEmpleado, int idSesion) throws SQLException, ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: terminaSesiones");
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOUDPCIERRASESIONES(?,?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOMONITOREODSI.FNGEOUDPCIERRASESIONES(" + idSesion + "," + noEmpleado + ") }";
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> idSesion        :: " + idSesion);
      MensajeLog.write("   >>> noEmpleado      :: " + noEmpleado);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
      stmsOBJ.setInt(i++, idSesion);
      stmsOBJ.setInt(i++, noEmpleado);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      if (stmsOBJ.getInt(1) != 0) {
        throw new ServiceException("ERROR AL TERMINAR LA SESION, CODIGO: " + stmsOBJ.getInt(1), new ServiceException("handledException"));
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(stmsOBJ, connOBJ);
    }
  }

  public List<CatalogoEmpleadoVO> ObtieneEmpleados(int noEmpleado) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   * L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: ObtieneEmpleados");
    CatalogoParamConfigVO param = new CatalogoParamConfigVO();
    List<CatalogoEmpleadoVO> retorno = new ArrayList<CatalogoEmpleadoVO>();
    HashMap<Integer, String> mapaFiltro = new HashMap<Integer, String>();
    Boolean filtra = false;
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    int i = 1;
    try {
      param = ObtieneParamConfig(10001);
      filtra = validaParam(10002);
      String[] valCae = param.getValor().split(";");
      for (int j = 0; j < valCae.length; j++) {
        String[] clave = valCae[j].split("\\|");
        mapaFiltro.put(Integer.parseInt(clave[1]), clave[0]);
      }
      /******************************************************************************************/
      commORA = "{ ? = call IUSAGEO.PAGEOGESTIONADOR.FNGEOGETEMPLEADOCAE2(?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOGESTIONADOR.FNGEOGETEMPLEADOCAE2(" + noEmpleado + ") }";
      connOBJ = OracleConnection.getConnection(1);
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> noEmpleado      :: " + noEmpleado);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.setInt(i++, noEmpleado);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      while (rsetOBJ.next()) {
        CatalogoEmpleadoVO catalogoEmpleado = new CatalogoEmpleadoVO();
        catalogoEmpleado.setIdCae(rsetOBJ.getInt("IDCAE"));
        catalogoEmpleado.setIdEmpleado(rsetOBJ.getInt("IDEMPLEADO"));
        catalogoEmpleado.setNombre(rsetOBJ.getString("NOMBRE"));
        String area = rsetOBJ.getString("AREA");
        String posicion = rsetOBJ.getString("POSICION");
        if (area.equals("V")) {
          catalogoEmpleado.setArea("Ventas");
        } else if (area.equals("S")) {
          catalogoEmpleado.setArea("Servicios");
        } else {
          String[] posArray = posicion.split(" ");
          if (posArray.length >= 3) {
            String pos = posArray[0] + " " + posArray[1] + " " + posArray[2];
            pos = pos.replace(" DE ", " ");
            catalogoEmpleado.setArea(capitalize(pos));
          } else {
            catalogoEmpleado.setArea(capitalize(posicion));
          }
        }
        catalogoEmpleado.setHoraEntrada(rsetOBJ.getString("HORAENTRADA"));
        catalogoEmpleado.setHoraSalida(rsetOBJ.getString("HORASALIDA"));
        if (filtra) {
          if (!mapaFiltro.containsKey(catalogoEmpleado.getIdCae())) {
            retorno.add(catalogoEmpleado);
          }
        } else {
          retorno.add(catalogoEmpleado);
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: " + retorno.size() + " Elementos consultado en", timeINI);
    return retorno;
  }

  public List<ReporteEmpleadoVO> obtieneActivacionesEmpleado(String fechaInicio, String fechaFin, int idCae) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: obtieneActivacionesEmpleado");
    List<ReporteEmpleadoVO> retorno = new ArrayList<ReporteEmpleadoVO>();
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    try {
      int i = 1;
      commORA = "{ ? = call SCSIRIUSA.PASIRRPTVENTAS.FNSIRRPTVTACAEUSRV1(?,?,?) }";
      commPRN = "{ ? = call SCSIRIUSA.PASIRRPTVENTAS.FNSIRRPTVTACAEUSRV1((" + fechaInicio + "," + fechaFin + "," + idCae + ") }";
      connOBJ = OracleConnection.getConnection(1);
      MensajeLog.write("   FN                  :: " + commPRN);
      MensajeLog.write("   >>> fechaInicio     :: " + fechaInicio);
      MensajeLog.write("   >>> fechaFin        :: " + fechaFin);
      MensajeLog.write("   >>> idCae           :: " + idCae);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.setInt(i++, idCae);
      stmsOBJ.setString(i++, fechaInicio);
      stmsOBJ.setString(i++, fechaFin);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      while (rsetOBJ.next()) {
        ReporteEmpleadoVO cat = new ReporteEmpleadoVO();
        cat.setNoEmpleado(rsetOBJ.getInt("EMPLEADOID"));
        cat.setActivacionesNuevo(rsetOBJ.getInt("CANTIDAD_NUEVO"));
        cat.setActivacionesPropio(rsetOBJ.getInt("CANTIDAD_PROPIO"));
        cat.setMontoNuevo(rsetOBJ.getDouble("MONTO_NUEVO"));
        cat.setMontoPropio(rsetOBJ.getDouble("MONTO_PROPIO"));
        cat.setActivacionesTotal(rsetOBJ.getInt("CANTIDAD_TOTAL"));
        cat.setMontoTotal(rsetOBJ.getDouble("MONTO_TOTAL"));
        cat.setNombre(rsetOBJ.getString("NOMBRE_EMPLEADO"));
        retorno.add(cat);
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: " + retorno.size() + " Elementos consultado en", timeINI);
    return retorno;
  }

  public List<HorarioCAEVO> ObtieneAsistenciaCae(int idEmpleado) throws ServiceException, SQLException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: ObtieneAsistenciaCae");
    List<HorarioCAEVO> retorno = new ArrayList<HorarioCAEVO>();
    List<Integer> norOeste = Arrays.asList(new Integer[] { 2 });
    List<Integer> pacifico = Arrays.asList(new Integer[] { 3, 8, 18, 25 });
    List<Integer> pacificoSHV = Arrays.asList(new Integer[] { 26 });
    String commORA = "";
    String commPRN = "";
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOGESTIONADOR.FNGEOCAEGETHORIOCAE2(?) }";
      connOBJ = OracleConnection.getConnection(1);
      Long timeFor = System.currentTimeMillis();
      commPRN = "{ ? = call IUSAGEO.PAGEOGESTIONADOR.FNGEOCAEGETHORIOCAE2(" + idEmpleado + ") }";
      MensajeLog.write("   FN                  :: " + commPRN);
      int i = 1;
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.setInt(i++, idEmpleado);
      stmsOBJ.execute();
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      MensajeLog.write("   <<<                 :: FN", timeFor);
      while (rsetOBJ.next()) {
        HorarioCAEVO cat = new HorarioCAEVO();

        String horaCierre = rsetOBJ.getString("HORACIERRE");
        //        String cierreOficial = rsetOBJ.getString("CIERREOFICIAL");
        Integer idEstado = rsetOBJ.getInt("IDEDO");
        cat.setIdCae(rsetOBJ.getInt("IDCAE"));
        cat.setHoraApertura(rsetOBJ.getString("HORAAPERTURA"));
        cat.setAperturaOficial(rsetOBJ.getString("APERTURAOFICIAL"));
        cat.setEmpleadosTotal(rsetOBJ.getInt("EMPLEADOSTOTAL"));
        cat.setVentasTotal(rsetOBJ.getInt("VENTASTOTAL"));
        cat.setServiciosTotal(rsetOBJ.getInt("SERVICIOSTOTAL"));
        cat.setEmpleadosAsistencia(rsetOBJ.getInt("EMPLEADOSASISTENCIA"));
        cat.setVentasAsistencia(rsetOBJ.getInt("VENTASASISTENCIA"));
        cat.setServiciosAsistencia(rsetOBJ.getInt("SERVICIOSASISTENCIA"));
        cat.setEmpleadosEnHorario(rsetOBJ.getInt("EMPLEADOSENHORARIO"));
        cat.setVentasEnHorario(rsetOBJ.getInt("VENTASENHORARIO"));
        cat.setServiciosEnHorario(rsetOBJ.getInt("SERVICIOSENHORARIO"));
        if (cat.getAperturaOficial() == null || !cat.getAperturaOficial().matches("[0-2]{1}[\\d]{1}:[0-5]{1}[\\d]{1}")) {
          //SIN HORA DE APERTURA OFICIAL
          cat.setStatusCAE(0); // CERRADO
        } else if (horaCierre != null && horaCierre.matches("[0-2]{1}[\\d]{1}:[0-5]{1}[\\d]{1}")) {
          cat.setStatusCAE(4); // FIN DE JORNADA LABORAL
        } else {
          // EVALUA HORAS
          SimpleDateFormat formatoHoy = new SimpleDateFormat("yyyy-MM-dd");
          SimpleDateFormat conversion = new SimpleDateFormat("yyyy-MM-dd HH:mm");
          Calendar hoy = Calendar.getInstance();
          Date oficial = conversion.parse(formatoHoy.format(hoy.getTime()) + " " + cat.getAperturaOficial());
          if (norOeste.contains(idEstado)) {
            hoy.add(Calendar.HOUR, -2);
          } else if (pacifico.contains(idEstado)) {
            hoy.add(Calendar.HOUR, -1);
          } else if (pacificoSHV.contains(idEstado)) {
            if (esHorarioVerano(hoy.getTime())) {
              hoy.add(Calendar.HOUR, -2);
            } else {
              hoy.add(Calendar.HOUR, -1);
            }
          }
          if (cat.getHoraApertura() == null || !cat.getHoraApertura().matches("[0-2]{1}[\\d]{1}:[0-5]{1}[\\d]{1}")) {
            //si viene nulo, se compara la hora actual con la hora de apertura oficial
            if (hoy.getTime().equals(oficial))//SON IGUALES
            {
              cat.setStatusCAE(2); // ABIERTO EN TIEMPO
            } else if (hoy.getTime().before(oficial)) {
              cat.setStatusCAE(3); // POR ABRIR
            } else {
              cat.setStatusCAE(0); // CERRADO
            }
          } else {
            //si NO viene nulo, se compará la HORA DE APERTURA con la hora de apertura oficial
            Date apertura = conversion.parse(formatoHoy.format(hoy.getTime()) + " " + cat.getHoraApertura());
            if (apertura.equals(oficial))//SON IGUALES
            {
              cat.setStatusCAE(2); // ABIERTO EN TIEMPO
            } else if (apertura.before(oficial)) {
              cat.setStatusCAE(2); //ABIERTO EN TIEMPO
            } else {
              cat.setStatusCAE(1); //ABIERTO CON RETARDO
            }
          }
        }
        if (!retorno.contains(cat)) {
          retorno.add(cat);
        }
      }
      stmsOBJ.close();
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Consultado en", timeINI);
    return retorno;
  }

  public Integer ValidaExUsuario(int numEmpleado) throws ServiceException {
    MensajeLog.write("   *** L l a m a d a  a  B a s e  d e  D a t o s ");
    MensajeLog.write("   Operacion           :: ValidaExUsuario");
    MensajeLog.write("   >>> numEmpleado     :: " + numEmpleado);
    Integer retorno = null;
    String commORA = "";
    String commPRN = "";
    Long timeINI = System.currentTimeMillis();
    Connection connOBJ = null;
    CallableStatement stmsOBJ = null;
    ResultSet rsetOBJ = null;
    int i = 1;
    try {
      commORA = "{ ? = call IUSAGEO.PAGEOPERFILACCESO.FNGETUSUARIODSI(?) }";
      commPRN = "{ ? = call IUSAGEO.PAGEOPERFILACCESO.FNGETUSUARIODSI(" + numEmpleado + ") }";
      MensajeLog.write("   FN                  :: " + commPRN);
      connOBJ = OracleConnection.getConnection(1);
      stmsOBJ = connOBJ.prepareCall(commORA);
      stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
      stmsOBJ.setInt(i++, numEmpleado);
      stmsOBJ.execute();
      MensajeLog.write("   <<<                 :: FN", timeINI);
      rsetOBJ = (ResultSet) stmsOBJ.getObject(1);
      while (rsetOBJ.next()) {
        try {
          retorno = rsetOBJ.getInt("STATUS");
        } catch (Exception e) {}
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    } finally {
      collector(rsetOBJ, stmsOBJ, connOBJ);
    }
    MensajeLog.write("   <<<                 :: Elemento consultado", timeINI);
    return retorno;
  }

}