package mx.com.iusacell.mapas.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.mapas.consume.VerificaUsuario;
import mx.com.iusacell.mapas.consume.vo.EmpleadoVO;
import mx.com.iusacell.mapas.consume.vo.RespuestaLlaveVO;
import mx.com.iusacell.mapas.consume.vo.ValidacionTokenVO;
import mx.com.iusacell.mapas.log.MensajeLog;
import mx.com.iusacell.mapas.masivo.model.OracleProcedures;
import mx.com.iusacell.mapas.security.AlgoritmoAes;
import mx.com.iusacell.mapas.security.ValidaToken;
import mx.com.iusacell.mapas.vo.CatalogoCaeVO;
import mx.com.iusacell.mapas.vo.CatalogoCompetenciaVO;
import mx.com.iusacell.mapas.vo.CatalogoControlStreetViewVO;
import mx.com.iusacell.mapas.vo.CatalogoEmpleadoVO;
import mx.com.iusacell.mapas.vo.CatalogoGeoVersionVO;
import mx.com.iusacell.mapas.vo.CatalogoListaCaeVO;
import mx.com.iusacell.mapas.vo.CatalogoParamConfigVO;
import mx.com.iusacell.mapas.vo.CatalogoReporteCaeVO;
import mx.com.iusacell.mapas.vo.CatalogoVersionCompetencia;
import mx.com.iusacell.mapas.vo.ControlLogDSIVO;
import mx.com.iusacell.mapas.vo.ControlUsuarioVO;
import mx.com.iusacell.mapas.vo.DatosEmpleadoVO;
import mx.com.iusacell.mapas.vo.GeoVO;
import mx.com.iusacell.mapas.vo.GeografiaVO;
import mx.com.iusacell.mapas.vo.HorarioCAEVO;
import mx.com.iusacell.mapas.vo.HorarioEmpleadoVO;
import mx.com.iusacell.mapas.vo.LoginVO;
import mx.com.iusacell.mapas.vo.ReporteActivacionesV2VO;
import mx.com.iusacell.mapas.vo.ReporteEmpleadoV2VO;
import mx.com.iusacell.mapas.vo.ReporteEmpleadoVO;
import mx.com.iusacell.mapas.xstream.converter.CatchAllConverter;
import mx.com.iusacell.mapas.xstream.converter.GeoVOConverter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class ServicesImplementation implements ServicesInterface {

  private final static String usuAuth = "/esmfxtAcJam1PtmdnsCr3sP=/";

  /*********************************************** GENERICO ***********************************************/

  private void obtieneAcceso(String user, String usu, String pass, String passSet, String token, String param) throws ServiceException {
    AlgoritmoAes decode = new AlgoritmoAes();
    ValidaToken valida = new ValidaToken();
    try {
      try {
        user = decode.decryptLogin(user, usu);
      } catch (Exception e) {
        throw new ServiceException("Usuario no válido");
      }
      try {
        pass = decode.decryptLogin(pass, usu);
      } catch (Exception e) {
        throw new ServiceException("Password no válido");
      }
      if (user == null || user.trim().equals("") || !user.trim().equalsIgnoreCase(usu)) {
        throw new ServiceException("Usuario no válido");
      }
      if (pass == null || pass.trim().equals("") || !pass.trim().equalsIgnoreCase(passSet)) {
        throw new ServiceException("Password no válido");
      }
      if (token == null || token.trim().equals("")) {
        throw new ServiceException("Token no válido");
      }
      String key = decode.cutKey(usu);
      try {
        String drecriptedToken = decode.decrypt(token, key.getBytes());
        valida.validaTokenUTC(drecriptedToken, param);
      } catch (Exception e) {
        throw new ServiceException("Token no válido");
      }
    } catch (Exception e) {
      throw new ServiceException("Acceso no válido : " + e.getMessage(), new ServiceException("handledException"));
    }
  }

  private void campoFecha(String src, String name) throws ServiceException {
    if (src == null) {
      throw new ServiceException("Error en los parámetros de entrada. El campo " + name + " no está especificado");
    }
    try {
      if (!src.matches("\\d{2}-\\d{2}-\\d{4}")) {
        throw new ServiceException("El parámetro " + name + " debe de tener un formato DD-MM-YYYY");
      }
    } catch (Exception ex) {
      throw new ServiceException("El parámetro " + name + " está vacío o no es válido : " + ex.getMessage(), new ServiceException("handledException"));
    }
  }

  private String getImagebase64(String noEmpleado) {
    String retorno = "";
    VerificaUsuario verificaUserSoap = new VerificaUsuario();
    AlgoritmoAes aes = new AlgoritmoAes();
    String resObtDatosEmpleado = "";
    String imgDuro = "/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAABGAAD/4QMraHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjMtYzAxMSA2Ni4xNDU2NjEsIDIwMTIvMDIvMDYtMTQ6NTY6MjcgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDUzYgKFdpbmRvd3MpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjFBNzI4MjNDOUU2NzExRTM4Qzk1RTgxQzE2MDg1NTU4IiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjFBNzI4MjNEOUU2NzExRTM4Qzk1RTgxQzE2MDg1NTU4Ij4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6MUE3MjgyM0E5RTY3MTFFMzhDOTVFODFDMTYwODU1NTgiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6MUE3MjgyM0I5RTY3MTFFMzhDOTVFODFDMTYwODU1NTgiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz7/7gAOQWRvYmUAZMAAAAAB/9sAhAAEAwMDAwMEAwMEBgQDBAYHBQQEBQcIBgYHBgYICggJCQkJCAoKDAwMDAwKDAwNDQwMEREREREUFBQUFBQUFBQUAQQFBQgHCA8KCg8UDg4OFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wAARCABMADkDAREAAhEBAxEB/8QAbQABAAMBAQEAAAAAAAAAAAAAAAYHCAQDBQEBAAAAAAAAAAAAAAAAAAAAABAAAQMEAgECBAILAAAAAAAAAQIDBAAREgUhBhMxB1EiFAhBFWGBQiOTVLTUdTYYEQEAAAAAAAAAAAAAAAAAAAAA/9oADAMBAAIRAxEAPwDf1AoId3v3H0XQ2G0zguVtZCFLi69gpzIANluEn5GyoY5WJ9cUqxVYKim/cT2dySteu1cCPDNvG1ID0h1PAvk4hxoG5uR8g+H6aCYde+4DrMxmIx2Fh/XT1JImSG2/LDStIPKcVKdsq3A8ZxJsSQMqC2mH2JTDcmM4h6M8hLjLzagtC0LF0qSoXBBBuCKD0oFAoOTa7FjUaubtpKVrjQI7sp5LYBWUMIK1BIJAvYcXIoMUbnbzt9tZe52LnkmzXVOum6iBf0SnIkhKRZKRfhIAoOGgUGh/t23E2VqdxpX15w9c6y9EyKipH1YczQLkgIu1kAAPmUo/jQXTQKBQfB7v/pfY/wDFzf6ddBi2gUCgu37ctiw1tN7qVJWZMqOxKbUAMAiKtSFgm97kvptx8f1hoOgUCg4N5rfznS7LT+XwfmEV+J5sc8PO2pvLG6b2ve1xQYp2mr2Gl2EjV7SOuLsIq/G+w56pPqORcEEG6VDgjkcUHJQKDSXsP05/T6Z/s09KBJ3KECG2pspeaitqVyVqANnjiuyeClKFXN+At6gUCgUGbfuD0aYPZoO8aShLe2jlDtiouKkRCEqUoHgDxraSLH9k8fEKhoOvVa5/b7SFqYykIkz5DUVlThIQFvrCElRAJtc82BoNvQYUbWwo2uhI8UOI0iPHbuVYtNJCEpuokmwHqTQe9AoFBC+1+6PUeqR3/LOanbRvyIRrYiw66Xm1BKkOFOQasTz5LHg2CiLUGae5d733eZTUjcraSxGz+jisICG2Q4EBdibrVlgCc1Hn0sOKCM0Cg0l7Z+8TG9ZkQO5S4ev2EVLao8xxYjIkotisqzIQHAoBRxUMsvlSAk0FvUCgxTtO39q3SJDW03M2VGlL8j8Zx9z6dRyzH7oEIAChdKQmw/Cg+LQKBQKBQSPr/fe3dXwRp9q+1FbSUIhuHzRglS81YtOZISSfVSQFcnnk0E3/AOhu6fyGr/gyP7igqWgUCgUCgUCgUCgUCgUCgUCgUCgUCgUCgUCgUH//2Q==";
    try {
      String idAppAutentica = OracleProcedures.ObtieneParamConfig(2021).getValor();
      String llaveAppAutObtieneUsu = OracleProcedures.ObtieneParamConfig(2020).getValor();
      resObtDatosEmpleado = verificaUserSoap.obtieneFotoEmpleado(usuAuth, usuAuth, Integer.parseInt(idAppAutentica), aes.encrypt(llaveAppAutObtieneUsu, aes.cutKey(usuAuth).getBytes()), "10.203.24.211", noEmpleado, GeneraTokenUTC(noEmpleado));//request.getRemoteIp().get(MensajeLog.getThreadId())

      if (resObtDatosEmpleado.equals("") || resObtDatosEmpleado == null) {
        retorno = imgDuro;
      } else {
        retorno = resObtDatosEmpleado;
      }
    } catch (Exception ex) {
      MensajeLog.warning(ex.getLocalizedMessage());
    }
    return retorno;
  }

  private Boolean validaPermisoDSI(List<Integer> permisos) {
    Boolean retorno = false;
    try {
      String tot = OracleProcedures.ObtieneParamConfig(20013).getValor();
      String[] valores = tot.split("\\|");
      for (int i = 0; i < valores.length; i++) {
        if (permisos.contains(Integer.parseInt(valores[i]))) {
          return true;
        }
      }
    } catch (Exception e) {
      MensajeLog.exception(e);
    }
    return retorno;
  }

  private RespuestaLlaveVO ProcesaLlave(Integer noEmpleado, String llaveM) throws ServiceException {
    AlgoritmoAes aes = new AlgoritmoAes();
    VerificaUsuario AuthSOAP = new VerificaUsuario();
    RespuestaLlaveVO retorno = null;
    try {
      //      if (OracleProcedures.validaParam(20012)) {
      /* VIA DIRECTA */
      String llaveAppAutentica = OracleProcedures.ObtieneParamConfig(2018).getValor();
      String idAppAutentica = OracleProcedures.ObtieneParamConfig(2021).getValor();
      retorno = AuthSOAP.validaLlaveMaestraIusacell(usuAuth, usuAuth, Integer.parseInt(idAppAutentica), aes.encrypt(llaveAppAutentica, aes.cutKey(usuAuth).getBytes()), "10.203.24.211", noEmpleado, aes.encrypt(llaveM, aes.cutKey(llaveAppAutentica).getBytes()), GeneraTokenUTC(String.valueOf(noEmpleado)));
      //        AuthSOAP = new VerificaUsuario();
      //      } 
      //      else {
      //        //        /* POR WS */
      //        retorno = AuthSOAP.ValidaLlaveMaestra(usuAuth, usuAuth, noEmpleado, aes.encrypt(llaveM, aes.cutKey(usuAuth).getBytes()), GeneraTokenUTC(String.valueOf(noEmpleado)));
      //      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    return retorno;
  }

  private static String GeneraTokenUTC(String param) {
    TimeZone timeZone = TimeZone.getTimeZone("UTC");
    AlgoritmoAes token = new AlgoritmoAes();
    SimpleDateFormat horaToken = new SimpleDateFormat("ddMMyyyyHHmm");
    horaToken.setTimeZone(timeZone);
    Date date = new Date();
    String res = "";
    String antes = "";
    try {
      antes = horaToken.format(date) + param;
      res = token.encrypt(antes, token.cutKey(usuAuth).getBytes());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return res;
  }

  /******************************************************* METODOS **********************************************************/

  @SuppressWarnings("unchecked")
  public GeografiaVO MapaObtieneGeografia(String user, String pass, int version, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.init();
    MensajeLog.write(" + Parametros                    +");
    MensajeLog.write("   user                          : -PROTEGIDO-");
    MensajeLog.write("   pass                          : -PROTEGIDO-");
    MensajeLog.write("   tokenUTC                      : " + tokenUTC);
    MensajeLog.write("   version                       : " + version);
    OracleProcedures procs = new OracleProcedures();
    GeografiaVO retorno = new GeografiaVO();
    List<GeoVO> divisiones = null;
    CatalogoGeoVersionVO versionActual = null;
    try {
      obtieneAcceso(user, "AGhAxzwOwKEbI12XQ1KNjQ*F/", pass, "AGhAxzwOwKEbI12XQ1KNjQ*C/", tokenUTC, String.valueOf(version));
      XStream xstream = new XStream(new DomDriver());
      // Register converters for the types you are expecting
      xstream.registerConverter(new GeoVOConverter());
      // Register a catch-all converter to avoid dynamic-proxies unmarshalling
      xstream.registerConverter(new CatchAllConverter(), XStream.PRIORITY_LOW);
      xstream.alias("GeoVO", GeoVO.class);
      versionActual = procs.ObtieneVersionGeometria();
      retorno.setVersion(versionActual.getVersion());
      if (versionActual.getVersion() != version) {
        CatalogoParamConfigVO versionGuardada = OracleProcedures.ObtieneParamConfig(20000);
        if (versionActual.getVersion() == Integer.parseInt(versionGuardada.getValor())) {
          try {
            String xml = procs.obtieneClob(versionActual.getVersion());
            divisiones = (List<GeoVO>) xstream.fromXML(xml);
          } catch (Exception ex) {
            throw new ServiceException("Error al convertir el XML");
          }
        } else {
          throw new ServiceException("No hay Geografía disponible", new ServiceException("hFatalException"));
        }//ELSE VERSION GUARDADA
        retorno.setGeografia(divisiones);
      }
    } catch (Exception e) {
      MensajeLog.exception(e);
      throw new ServiceException(e.getLocalizedMessage());
    }
    MensajeLog.end(timeINI);
    return retorno;
  }

  public CatalogoListaCaeVO MapaObtieneCaes(String user, String pass, int version, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.init();
    MensajeLog.write(" + Parametros                    +");
    MensajeLog.write("   user                          : -PROTEGIDO-");
    MensajeLog.write("   pass                          : -PROTEGIDO-");
    MensajeLog.write("   tokenUTC                      : " + tokenUTC);
    MensajeLog.write("   version                       : " + version);
    OracleProcedures procs = new OracleProcedures();
    CatalogoListaCaeVO retorno = new CatalogoListaCaeVO();
    CatalogoGeoVersionVO versionActual = null;
    List<CatalogoCaeVO> caes = null;
    try {
      obtieneAcceso(user, "AGhAxzwOwKEbI12XQ1KNjQ*F/", pass, "AGhAxzwOwKEbI12XQ1KNjQ*C/", tokenUTC, String.valueOf(version));
      versionActual = procs.ObtieneVersionCae();
      retorno.setVersion(versionActual.getVersion());
      if (versionActual.getVersion() != version) {
        caes = procs.ObtieneCaes();
      }
      retorno.setCaes(caes);
    } catch (Exception e) {
      MensajeLog.exception(e);
      throw new ServiceException(e.getLocalizedMessage());
    }
    MensajeLog.end(timeINI);
    return retorno;
  }

  public List<CatalogoEmpleadoVO> MapaObtieneEmpleados(String user, String pass, int noEmpleado, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.init();
    MensajeLog.write(" + Parametros                    +");
    MensajeLog.write("   user                          : -PROTEGIDO-");
    MensajeLog.write("   pass                          : -PROTEGIDO-");
    MensajeLog.write("   tokenUTC                      : " + tokenUTC);
    MensajeLog.write("   noEmpleado                    : " + noEmpleado);
    OracleProcedures procs = new OracleProcedures();
    List<CatalogoEmpleadoVO> retorno = null;
    try {
      obtieneAcceso(user, "AGhAxzwOwKEbI12XQ1KNjQ*F/", pass, "AGhAxzwOwKEbI12XQ1KNjQ*C/", tokenUTC, String.valueOf(noEmpleado));
      retorno = procs.ObtieneEmpleados(noEmpleado);
    } catch (Exception e) {
      MensajeLog.exception(e);
      throw new ServiceException(e.getLocalizedMessage());
    }
    MensajeLog.end(timeINI);
    return retorno;
  }

  public List<HorarioEmpleadoVO> MapaObtieneAsistenciaEmpleados(String user, String pass, int idCae, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.init();
    MensajeLog.write(" + Parametros                    +");
    MensajeLog.write("   user                          : -PROTEGIDO-");
    MensajeLog.write("   pass                          : -PROTEGIDO-");
    MensajeLog.write("   tokenUTC                      : " + tokenUTC);
    MensajeLog.write("   idCae                         : " + idCae);
    OracleProcedures procs = new OracleProcedures();
    List<HorarioEmpleadoVO> retorno = null;
    try {
      obtieneAcceso(user, "AGhAxzwOwKEbI12XQ1KNjQ*F/", pass, "AGhAxzwOwKEbI12XQ1KNjQ*C/", tokenUTC, String.valueOf(idCae));
      retorno = procs.obtieneAsistenciaEmpleado(idCae);
    } catch (Exception e) {
      MensajeLog.exception(e);
      throw new ServiceException(e.getLocalizedMessage());
    }
    MensajeLog.end(timeINI);
    return retorno;
  }

  public List<HorarioCAEVO> MapaObtieneAsistenciaCae(String user, String pass, int noEmpleado, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.init();
    MensajeLog.write(" + Parametros                    +");
    MensajeLog.write("   user                          : -PROTEGIDO-");
    MensajeLog.write("   pass                          : -PROTEGIDO-");
    MensajeLog.write("   tokenUTC                      : " + tokenUTC);
    MensajeLog.write("   noEmpleado                    : " + noEmpleado);
    OracleProcedures procs = new OracleProcedures();
    List<HorarioCAEVO> retorno = null;
    try {
      obtieneAcceso(user, "AGhAxzwOwKEbI12XQ1KNjQ*F/", pass, "AGhAxzwOwKEbI12XQ1KNjQ*C/", tokenUTC, String.valueOf(noEmpleado));
      retorno = procs.ObtieneAsistenciaCae(noEmpleado);
    } catch (Exception e) {
      MensajeLog.exception(e);
      throw new ServiceException(e.getLocalizedMessage());
    }
    MensajeLog.end(timeINI);
    return retorno;
  }

  public CatalogoVersionCompetencia MapaObtieneCompetencia(String user, String pass, int version, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.init();
    MensajeLog.write(" + Parametros                    +");
    MensajeLog.write("   user                          : -PROTEGIDO-");
    MensajeLog.write("   pass                          : -PROTEGIDO-");
    MensajeLog.write("   tokenUTC                      : " + tokenUTC);
    List<CatalogoCompetenciaVO> retorno = null;
    List<CatalogoControlStreetViewVO> street = null;
    CatalogoVersionCompetencia compentencia = new CatalogoVersionCompetencia();
    String xmlCompentencia = null;
    XStream xstream;
    CatalogoParamConfigVO versionComp = null;
    try {
      obtieneAcceso(user, "AGhAxzwOwKEbI12XQ1KNjQ*F/", pass, "AGhAxzwOwKEbI12XQ1KNjQ*C/", tokenUTC, String.valueOf(version));
      OracleProcedures procs = new OracleProcedures();
      versionComp = OracleProcedures.ObtieneParamConfig(16);
      //Si la versión de la competencia es diferente entonces 
      //se devuelve la geografia
      compentencia.setVersion(Integer.parseInt(versionComp.getValor()));
      if (Integer.parseInt(versionComp.getValor()) != version) {
        retorno = procs.ObtieneGeoCompetenciaVisor();
        street = procs.obtieneStreetViewCompetencia();
        for (int i = 0; i < street.size(); i++) {
          for (int j = 0; j < retorno.size(); j++) {
            if (street.get(i).getClaveCae().equals(String.valueOf(retorno.get(j).getIdCompetencia()))) {
              retorno.get(j).setPovHeading(street.get(i).getRotacion());
              retorno.get(j).setPovLatitud(street.get(i).getLatitud());
              retorno.get(j).setPovLongitud(street.get(i).getLongitud());
              retorno.get(j).setPovPitch(street.get(i).getAngulo());
              retorno.get(j).setPovZoom(street.get(i).getZoom());
            }
          }
        }
        xstream = new XStream(new StaxDriver());
        xstream.alias("competencias", List.class);
        xstream.alias("competencia", CatalogoCompetenciaVO.class);
        xmlCompentencia = xstream.toXML(retorno);
        compentencia.setCompetencia(xmlCompentencia);
      }
    } catch (Exception e) {
      MensajeLog.exception(e);
      throw new ServiceException(e.getMessage());
    }
    MensajeLog.end(timeINI);
    return compentencia;
  }

  public List<ReporteEmpleadoV2VO> MapaObtieneActivacionesEmpleadoV2(String user, String pass, String fechaInicio, String fechaFin, int idCae, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.init();
    MensajeLog.write(" + Parametros                    +");
    MensajeLog.write("   user                          : -PROTEGIDO-");
    MensajeLog.write("   pass                          : -PROTEGIDO-");
    MensajeLog.write("   tokenUTC                      : " + tokenUTC);
    MensajeLog.write("   fechaInicio                   : " + fechaInicio);
    MensajeLog.write("   fechaFin                      : " + fechaFin);
    MensajeLog.write("   idCae                         : " + idCae);
    OracleProcedures procs = new OracleProcedures();
    List<ReporteEmpleadoV2VO> retorno = new ArrayList<ReporteEmpleadoV2VO>();
    try {
      campoFecha(fechaInicio, "fechaInicio");
      campoFecha(fechaFin, "fechaFin");
      String[] fechaInicioaux = fechaInicio.split("-");
      String[] fechaFinaux = fechaFin.split("-");
      String fechainicomp = fechaInicioaux[2] + fechaInicioaux[1] + fechaInicioaux[0];
      String fechafincomp = fechaFinaux[2] + fechaFinaux[1] + fechaFinaux[0];
      if (fechafincomp.compareTo(fechainicomp) < 0) {
        throw new ServiceException("Periodo de fechas incorrecto. Fecha " + fechaFin + " es menor a " + fechaInicio, new ServiceException("handledException"));
      }
      obtieneAcceso(user, "AGhAxzwOwKEbI12XQ1KNjQ*F/", pass, "AGhAxzwOwKEbI12XQ1KNjQ*C/", tokenUTC, String.valueOf(idCae));
      retorno = procs.obtieneActivacionesEmpleadoV2(fechaInicio, fechaFin, idCae);
    } catch (Exception e) {
      MensajeLog.exception(e);
      throw new ServiceException(e.getMessage());
    }
    MensajeLog.end(timeINI);
    return retorno;
  }

  /******************************************************* NUEVOS METODOS **********************************************************/

  public CatalogoReporteCaeVO MapaObtieneActivacionesCaesV2(String user, String pass, String fechaInicio, String fechaFin, int noEmpleado, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.init();
    MensajeLog.write(" + Parametros                    +");
    MensajeLog.write("   user                          : -PROTEGIDO-");
    MensajeLog.write("   pass                          : -PROTEGIDO-");
    MensajeLog.write("   tokenUTC                      : " + tokenUTC);
    MensajeLog.write("   fechaInicio                   : " + fechaInicio);
    MensajeLog.write("   fechaFin                      : " + fechaFin);
    MensajeLog.write("   noEmpleado                    : " + noEmpleado);
    OracleProcedures procs = new OracleProcedures();
    List<ReporteActivacionesV2VO> caes = null;
    CatalogoReporteCaeVO retorno = new CatalogoReporteCaeVO();
    CatalogoParamConfigVO param = null;
    try {
      obtieneAcceso(user, "AGhAxzwOwKEbI12XQ1KNjQ*F/", pass, "AGhAxzwOwKEbI12XQ1KNjQ*C/", tokenUTC, String.valueOf(noEmpleado));
      campoFecha(fechaInicio, "fechaInicio");
      campoFecha(fechaFin, "fechaFin");
      String[] fechaInicioaux = fechaInicio.split("-");
      String[] fechaFinaux = fechaFin.split("-");
      String fechainicomp = fechaInicioaux[2] + fechaInicioaux[1] + fechaInicioaux[0];
      String fechafincomp = fechaFinaux[2] + fechaFinaux[1] + fechaFinaux[0];

      if (fechafincomp.compareTo(fechainicomp) < 0)
        throw new ServiceException("Periodo de fechas incorrecto. Fecha final " + fechaFin + " es menor a fecha inicial " + fechaInicio);

      caes = procs.obtieneActivacionesCaeV2(fechaInicio, fechaFin, noEmpleado);
      retorno.setCaes(caes);
      param = OracleProcedures.ObtieneParamConfig(20003);
      retorno.setFechaActualizacion(param.getValor());

    } catch (Exception e) {
      MensajeLog.exception(e);
      throw new ServiceException(e.getMessage());
    }
    MensajeLog.end(timeINI);
    return retorno;
  }

  public LoginVO MapaLogin(String user, String pass, Integer noEmpleado, int plataforma, String llaveM, String fechaCliente, String ipCliente, Integer tokenBAZ, int versionAPL, Integer idSesion, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.init();
    MensajeLog.write(" + Parametros                    +");
    MensajeLog.write("   user                          : -PROTEGIDO-");
    MensajeLog.write("   pass                          : -PROTEGIDO-");
    MensajeLog.write("   tokenUTC                      : " + tokenUTC);
    MensajeLog.write("   noEmpleado                    : " + noEmpleado);
    MensajeLog.write("   plataforma                    : " + plataforma);
    MensajeLog.write("   llaveM                        : " + llaveM);
    MensajeLog.write("   fechaCliente                  : " + fechaCliente);
    MensajeLog.write("   ipCliente                     : " + ipCliente);
    MensajeLog.write("   tokenBAZ                      : " + tokenBAZ);
    MensajeLog.write("   idSesion                      : " + idSesion);
    MensajeLog.write("   versionAPL                    : " + versionAPL);
    OracleProcedures procs = new OracleProcedures();
    AlgoritmoAes aes = new AlgoritmoAes();
    Integer buscaUsrStatus = null;
    CatalogoParamConfigVO tokenPrueba = null;
    RespuestaLlaveVO respuestaProcesaLlave = null;
    ControlLogDSIVO logDsi = null;
    DatosEmpleadoVO datos = null;
    LoginVO retorno = new LoginVO();
    Boolean verif = false;
    String statusLogin = null;
    VerificaUsuario verificaUserSoap = new VerificaUsuario();
    ValidacionTokenVO respuestaVerificaUser = new ValidacionTokenVO();
    List<Integer> listaEmpleado = new ArrayList<Integer>();
    List<EmpleadoVO> resObtDatosEmpleado = new ArrayList<EmpleadoVO>();
    int respuesta = 0;
    int verifSes = 0;
    int version = 0;
    String resImg = "";

    try {

      obtieneAcceso(user, "AGhAxzwOwKEbI12XQ1KNjQ*F/", pass, "AGhAxzwOwKEbI12XQ1KNjQ*C/", tokenUTC, String.valueOf(noEmpleado));
      /******************************************************************************************************************************/
      String llaveAppAutValidAccionDatosEmp = OracleProcedures.ObtieneParamConfig(2022).getValor();
      buscaUsrStatus = procs.ValidaExUsuario(noEmpleado);
      if (buscaUsrStatus == null || buscaUsrStatus == 0) {//Si regresa 1 el empleado esta activo
        respuesta = 3; // ACCESO NO VALIDO
        retorno.setRespuesta(respuesta);
        return retorno;
      }

      //SE CREA LA SESION
      if (idSesion != null && idSesion > 0) {
        verifSes = procs.statusSesion(idSesion);
        switch (verifSes) {
          case 1: {//pendiente de activar segunda vez que se llama
            verif = true;
            break;
          }
          case 2: {
            throw new ServiceException("La sesión " + idSesion + " ya se encuentra activa ", new ServiceException("handledException"));
          }
          default: {
            logDsi = procs.registraLogginDSI(plataforma, noEmpleado, ipCliente, fechaCliente);
            if (logDsi.getMinutos() == null) {
              idSesion = logDsi.getIdStatus();
              verif = true;
            } else {
              respuesta = 5; //USUARIO BLOQUEADO
              retorno.setRespuesta(respuesta);
              retorno.setStatus(logDsi.getStatus() + " | " + (logDsi.getMinutos() == -1 ? "" : logDsi.getMinutos()));
              MensajeLog.end(timeINI);
              return retorno;
            }
          }
        }
      } else {
        logDsi = procs.registraLogginDSI(plataforma, noEmpleado, ipCliente, fechaCliente);
        if (logDsi.getMinutos() == null) {
          idSesion = logDsi.getIdStatus();
        } else {
          respuesta = 5; //USUARIO BLOQUEADO
          retorno.setRespuesta(respuesta);
          retorno.setStatus(logDsi.getStatus() + " | " + (logDsi.getMinutos() == -1 ? "" : logDsi.getMinutos()));
          MensajeLog.end(timeINI);
          return retorno;
        }
      }
      //SI EXISTE SE VALIDA LA LLAVE MAESTRA
      if (llaveM != null && !llaveM.trim().equals("")) {
        try {
          llaveM = aes.decrypt(llaveM, aes.cutKey("AGhAxzwOwKEbI12XQ1KNjQ*M/").getBytes());
        } catch (Exception e) {
          retorno.setRespuesta(8);
          retorno.setStatus(procs.actualizaIntentosDSI(noEmpleado, idSesion));
          MensajeLog.end(timeINI);
          return retorno;
        }
        if (!llaveM.matches("^[A-Za-z\\d ]{15,50}$")) {
          retorno.setRespuesta(8);
          retorno.setStatus(procs.actualizaIntentosDSI(noEmpleado, idSesion));
          MensajeLog.end(timeINI);
          return retorno;
        }
      }
      //Obtiene idAplicacion y llave (ValidacionesMarco)
      String idAppAutentica = OracleProcedures.ObtieneParamConfig(2021).getValor();
      String llaveAppAutValidAccionToken = OracleProcedures.ObtieneParamConfig(2019).getValor();
      version = Integer.parseInt(OracleProcedures.ObtieneParamConfig(14).getValor());
      /******************************************************************************************************************************/
      if ((tokenBAZ == null || String.valueOf(tokenBAZ).length() < 6) && llaveM != null && !llaveM.trim().equals("")) {

        //VALIDA SOLO POR LLAVE MAESTRA
        try {
          resImg = getImagebase64(String.valueOf(noEmpleado));
        } catch (Exception e) {
          MensajeLog.exception(e);
          throw new ServiceException(e.getLocalizedMessage());
        }
        try {
          respuestaProcesaLlave = ProcesaLlave(noEmpleado, llaveM);
        } catch (Exception e) {
          MensajeLog.exception(e);
          throw new ServiceException(e.getLocalizedMessage());
        }
        if (respuestaProcesaLlave.getAuth()) {
          ControlUsuarioVO accesos = procs.obtieneUsuario(noEmpleado, version);
          if (accesos.isExiste()) {
            if (accesos.getVersion() == version) {
              /*** DETECTA USUARIO DSI Y PLATAFORMA MOVIL ***/
              if (plataforma != 2 && validaPermisoDSI(accesos.getPermisos())) {
                retorno.setRespuesta(3);
                procs.actualizaStatusDSI(logDsi.getIdStatus(), noEmpleado, 8);
                MensajeLog.end(timeINI);
                return retorno;
              }
              /*** ACCESO CORRECTO ***/
              datos = new DatosEmpleadoVO();
              datos.setIdNivel(accesos.getIdNivel());
              datos.setIdGeografia(accesos.getIdGeo());
              datos.setPermisos(accesos.getPermisos());
              if (verif) {//--
                int verificaSes = procs.validaSesion(idSesion, noEmpleado);
                if (verificaSes == 0) {
                  // PUEDE INICIAR SESION
                  try {
                    datos.setSupervision(accesos.getSupervision());
                  } catch (Exception e) {}
                  datos.setPermisos(accesos.getPermisos());
                  retorno.setIdSesion(idSesion);
                  retorno.setDatosEmpleado(datos);
                  procs.terminaSesiones(noEmpleado, idSesion);
                } else if (verificaSes == -1) {
                  respuesta = 6; //USUARIO CON SESION ACTIVA (MENOS DE 5 MIN)
                  procs.actualizaStatusDSI(logDsi.getIdStatus(), noEmpleado, 6);
                  retorno.setRespuesta(respuesta);
                } else {
                  respuesta = 7; //USUARIO CON SESION ACTIVA (MAS DE 5 MIN)
                  for (int i = 0; i < resObtDatosEmpleado.size(); i++) {
                    datos.setNombre(resObtDatosEmpleado.get(i).getNombre());
                    datos.setPuesto(resObtDatosEmpleado.get(i).getPuesto());
                    if (resObtDatosEmpleado.get(i).getUniOrg() == null || resObtDatosEmpleado.get(i).getUniOrg().trim().equals("")) {
                      datos.setCaeAsignado("Sin CAE asignado");
                    } else {
                      datos.setCaeAsignado(resObtDatosEmpleado.get(i).getUniOrg());
                    }
                  }
                  try {
                    datos.setSupervision(accesos.getSupervision());
                  } catch (Exception e) {}
                  retorno.setIdSesion(idSesion);
                  statusLogin = String.valueOf(verificaSes);
                  //                  retorno.setStatus(statusLogin);///
                  retorno.setDatosEmpleado(datos);
                  retorno.setRespuesta(respuesta);
                }
              } else {
                // PUEDE INICIAR SESION POR PRIMERA VEZ
                listaEmpleado.add(noEmpleado);
                resObtDatosEmpleado = verificaUserSoap.ObtieneDatosEmpleadoIusacell(usuAuth, usuAuth, Integer.parseInt(idAppAutentica), aes.encrypt(llaveAppAutValidAccionDatosEmp, aes.cutKey(usuAuth).getBytes()), "10.203.24.211", listaEmpleado, GeneraTokenUTC(""));
                respuesta = 10;
                for (int i = 0; i < resObtDatosEmpleado.size(); i++) {
                  datos.setNombre(resObtDatosEmpleado.get(i).getNombre());
                  datos.setPuesto(resObtDatosEmpleado.get(i).getPuesto());
                  if (resObtDatosEmpleado.get(i).getUniOrg() == null || resObtDatosEmpleado.get(i).getUniOrg().trim().equals("")) {
                    datos.setCaeAsignado("Sin CAE asignado");
                  } else {
                    datos.setCaeAsignado(resObtDatosEmpleado.get(i).getUniOrg());
                  }
                }
                datos.setImagen64(resImg);
                retorno.setIdSesion(idSesion);
                retorno.setDatosEmpleado(datos);
                retorno.setRespuesta(respuesta);
              }
            } else {
              respuesta = 4; // VERSION INCORRECTA
              retorno.setRespuesta(respuesta);
              procs.actualizaStatusDSI(logDsi.getIdStatus(), noEmpleado, 9);
            }
          } else {
            respuesta = 3; // ACCESO NO VALIDO
            retorno.setRespuesta(respuesta);
            statusLogin = procs.actualizaIntentosDSI(noEmpleado, logDsi.getIdStatus());
          }
        } else {
          respuesta = 8; //LLAVE MAESTRA ERRONEA
          retorno.setRespuesta(respuesta);
          statusLogin = procs.actualizaIntentosDSI(noEmpleado, logDsi.getIdStatus());
        }

      } else if ((llaveM == null || llaveM.trim().equals("")) && tokenBAZ != null || String.valueOf(tokenBAZ).length() == 6) {
        //VALIDA SOLO POR TOKEN

        try {
          /****** Obtiene datos empleado iusacell *************/
          listaEmpleado.add(noEmpleado);
          resObtDatosEmpleado = verificaUserSoap.ObtieneDatosEmpleadoIusacell(usuAuth, usuAuth, Integer.parseInt(idAppAutentica), aes.encrypt(llaveAppAutValidAccionDatosEmp, aes.cutKey(usuAuth).getBytes()), "10.203.24.211", listaEmpleado, GeneraTokenUTC(""));
        } catch (Exception e) {
          MensajeLog.exception(e);
          throw new ServiceException(e.getLocalizedMessage());
        }
        try {
          resImg = getImagebase64(String.valueOf(noEmpleado));
        } catch (Exception e) {
          MensajeLog.exception(e);
          throw new ServiceException(e.getLocalizedMessage());
        }
        respuestaVerificaUser = verificaUserSoap.validaTokenIusacell(usuAuth, usuAuth, Integer.parseInt(idAppAutentica), aes.encrypt(llaveAppAutValidAccionToken, aes.cutKey(usuAuth).getBytes()), "10.203.24.211", noEmpleado, String.valueOf(tokenBAZ), GeneraTokenUTC(String.valueOf(noEmpleado)));//m
        tokenPrueba = OracleProcedures.ObtieneParamConfig(30000);

        if (respuestaVerificaUser.getAuth() || tokenBAZ == Integer.parseInt(tokenPrueba.getValor())) {
          ControlUsuarioVO accesos = procs.obtieneUsuario(noEmpleado, version);
          if (accesos.isExiste()) {
            if (accesos.getVersion() == version) {
              /*** DETECTA USUARIO DSI Y PLATAFORMA MOVIL ***/
              if (plataforma != 2 && validaPermisoDSI(accesos.getPermisos())) {
                retorno.setRespuesta(3);
                procs.actualizaStatusDSI(logDsi.getIdStatus(), noEmpleado, 8);
                MensajeLog.end(timeINI);
                return retorno;
              }
              /*** ACCESO CORRECTO ***/
              datos = new DatosEmpleadoVO();
              if (verif) {
                int verificaSes = procs.validaSesion(idSesion, noEmpleado);
                if (verificaSes == 0) {
                  for (int i = 0; i < resObtDatosEmpleado.size(); i++) {
                    if (resObtDatosEmpleado.get(i).getUniOrg() == null || resObtDatosEmpleado.get(i).getUniOrg().trim().equals("")) {
                      datos.setCaeAsignado("Sin CAE asignado");
                    } else {
                      datos.setCaeAsignado(resObtDatosEmpleado.get(i).getUniOrg());
                    }
                  }//for
                  // PUEDE INICIAR SESION
                  datos.setIdNivel(accesos.getIdNivel());
                  datos.setIdGeografia(accesos.getIdGeo());
                  try {
                    datos.setSupervision(accesos.getSupervision());
                  } catch (Exception e) {}
                  datos.setPermisos(accesos.getPermisos());
                  retorno.setIdSesion(idSesion);
                  retorno.setDatosEmpleado(datos);
                  procs.terminaSesiones(noEmpleado, idSesion);
                } else if (verificaSes == -1) {
                  respuesta = 6; //USUARIO CON SESION ACTIVA (MENOS DE 5 MIN)
                  procs.actualizaStatusDSI(idSesion, noEmpleado, 6);
                  retorno.setRespuesta(respuesta);
                } else {
                  respuesta = 7; //USUARIO CON SESION ACTIVA (MAS DE 5 MIN)
                  listaEmpleado.add(noEmpleado);
                  resObtDatosEmpleado = verificaUserSoap.ObtieneDatosEmpleadoIusacell(usuAuth, usuAuth, Integer.parseInt(idAppAutentica), aes.encrypt(llaveAppAutValidAccionDatosEmp, aes.cutKey(usuAuth).getBytes()), "10.203.24.211", listaEmpleado, GeneraTokenUTC(""));
                  for (int i = 0; i < resObtDatosEmpleado.size(); i++) {
                    if (resObtDatosEmpleado.get(i).getUniOrg() == null || resObtDatosEmpleado.get(i).getUniOrg().trim().equals("")) {
                      datos.setCaeAsignado("Sin CAE asignado");
                      retorno.setRespuesta(respuesta);//Respuesta7
                    } else {
                      datos.setCaeAsignado(resObtDatosEmpleado.get(i).getUniOrg());
                    }
                  }
                  try {
                    datos.setSupervision(accesos.getSupervision());
                  } catch (Exception e) {}
                  datos.setIdNivel(accesos.getIdNivel());
                  datos.setIdGeografia(accesos.getIdGeo());
                  datos.setPermisos(accesos.getPermisos());
                  retorno.setIdSesion(idSesion);
                  statusLogin = String.valueOf(verificaSes);
                  retorno.setDatosEmpleado(datos);
                }
              } else {
                // PUEDE INICIAR SESION POR PRIMERA VEZ
                respuesta = 10;
                for (int i = 0; i < resObtDatosEmpleado.size(); i++) {
                  datos.setNombre(resObtDatosEmpleado.get(i).getNombre());
                  datos.setPuesto(resObtDatosEmpleado.get(i).getPuesto());
                  if (resObtDatosEmpleado.get(i).getUniOrg() == null || resObtDatosEmpleado.get(i).getUniOrg().trim().equals("")) {
                    datos.setCaeAsignado("Sin CAE asignado");
                  } else {
                    datos.setCaeAsignado(resObtDatosEmpleado.get(i).getUniOrg());
                  }
                }
                datos.setIdGeografia(accesos.getIdGeo());
                datos.setIdNivel(accesos.getIdNivel());
                datos.setPermisos(accesos.getPermisos());
                datos.setSupervision(accesos.getSupervision());
                datos.setImagen64(resImg);
                retorno.setIdSesion(idSesion);
                retorno.setDatosEmpleado(datos);
                retorno.setRespuesta(respuesta);
                //                statusLogin = String.valueOf(verificaSes);
                retorno.setStatus(statusLogin);
              }
            } else {
              respuesta = 4; // VERSION INCORRECTA
              procs.actualizaStatusDSI(idSesion, noEmpleado, 9);
              retorno.setRespuesta(respuesta);
            }
          } else {
            respuesta = 3; // ACCESO NO VALIDO
            statusLogin = procs.actualizaIntentosDSI(noEmpleado, idSesion);
            retorno.setRespuesta(respuesta);
          }
        } else {
          respuesta = 1; // TOKEN ACCESOS INCORRECTO
          retorno.setRespuesta(respuesta);
          logDsi = procs.registraLogginDSI(plataforma, noEmpleado, ipCliente, fechaCliente);
          statusLogin = procs.actualizaIntentosDSI(noEmpleado, logDsi.getIdStatus());
        }
      } else {
        throw new ServiceException("DATOS INSUFICIENTES PARA INICIAR SESION", new ServiceException("handledException"));
      }
    } catch (Exception e) {
      MensajeLog.exception(e);
      throw new ServiceException(e.getLocalizedMessage());
    }
    retorno.setRespuesta(respuesta);
    retorno.setStatus(statusLogin);
    MensajeLog.end(timeINI);
    return retorno;
  }

  public int MapaTerminaSesion(String user, String pass, int noEmpleado, int idSesion, int idMotivo, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.init();
    MensajeLog.write(" + Parametros                    +");
    MensajeLog.write("   user                          : -PROTEGIDO-");
    MensajeLog.write("   pass                          : -PROTEGIDO-");
    MensajeLog.write("   tokenUTC                      : " + tokenUTC);
    MensajeLog.write("   noEmpleado                    : " + noEmpleado);
    MensajeLog.write("   idSesion                      : " + idSesion);
    MensajeLog.write("   idMotivo                      : " + idMotivo);
    OracleProcedures procs = new OracleProcedures();
    try {
      obtieneAcceso(user, "AGhAxzwOwKEbI12XQ1KNjQ*F/", pass, "AGhAxzwOwKEbI12XQ1KNjQ*C/", tokenUTC, String.valueOf(idSesion));
      int status = procs.statusSesion(idSesion);
      if (status == 1 || status == 2) {
        switch (idMotivo) {
          case 1: {
            //TERMINADA POR INACTIVIDAD
            procs.actualizaStatusDSI(idSesion, noEmpleado, 3);
            break;
          }
          case 2: {
            //TERMINADA POR PETICION DEL USUARIO TODAS LAS SESIONES ACTIVAS O PENDIENTES MENOS LAS ENVIADAS
            procs.terminaSesiones(noEmpleado, idSesion);
            break;
          }
          case 3: {
            //SESION FINALIZADA
            procs.actualizaStatusDSI(idSesion, noEmpleado, 7);
            break;
          }
          default: {
            throw new ServiceException("MOTIVO NO DEFINIDO", new ServiceException("handledException"));
          }
        }
      } else {
        throw new ServiceException("SESION YA TERMINADA", new ServiceException("handledException"));
      }
    } catch (Exception e) {
      MensajeLog.exception(e);
      throw new ServiceException(e.getMessage());
    }
    MensajeLog.end(timeINI);
    return 1;
  }

  public Boolean MapaInsertaActividadDSI(String user, String pass, String ip, int idSesion, String actividad, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.init();
    MensajeLog.write(" + Parametros                    +");
    MensajeLog.write("   user                          : -PROTEGIDO-");
    MensajeLog.write("   pass                          : -PROTEGIDO-");
    MensajeLog.write("   tokenUTC                      : " + tokenUTC);
    MensajeLog.write("   ip                            : " + ip);
    MensajeLog.write("   idSesion                      : " + idSesion);
    OracleProcedures procs = new OracleProcedures();
    Boolean retorno = false;
    try {
      obtieneAcceso(user, "AGhAxzwOwKEbI12XQ1KNjQ*F/", pass, "AGhAxzwOwKEbI12XQ1KNjQ*C/", tokenUTC, String.valueOf(idSesion));
      procs.insertaActividad(idSesion, actividad, ip);
      int status = procs.statusSesion(idSesion);
      if (status == 2) {
        retorno = true;
      }
    } catch (Exception e) {
      MensajeLog.exception(e);
      throw new ServiceException(e.getMessage());
    }
    MensajeLog.end(timeINI);
    return retorno;
  }

  public List<ReporteEmpleadoVO> MapaObtieneActivacionesEmpleado(String user, String pass, String fechaInicio, String fechaFin, int idCae, String tokenUTC) throws ServiceException {
    Long timeINI = System.currentTimeMillis();
    MensajeLog.init();
    MensajeLog.write(" + Parametros                    +");
    MensajeLog.write("   user                          : -PROTEGIDO-");
    MensajeLog.write("   pass                          : -PROTEGIDO-");
    MensajeLog.write("   tokenUTC                      : " + tokenUTC);
    MensajeLog.write("   fechaInicio                   : " + fechaInicio);
    MensajeLog.write("   fechaFin                      : " + fechaFin);
    MensajeLog.write("   idCae                         : " + idCae);
    OracleProcedures procs = new OracleProcedures();
    List<ReporteEmpleadoVO> retorno = null;
    try {
      campoFecha(fechaInicio, "fechaInicio");
      campoFecha(fechaFin, "fechaFin");
      String[] fechaInicioaux = fechaInicio.split("-");
      String[] fechaFinaux = fechaFin.split("-");
      String fechainicomp = fechaInicioaux[2] + fechaInicioaux[1] + fechaInicioaux[0];
      String fechafincomp = fechaFinaux[2] + fechaFinaux[1] + fechaFinaux[0];
      if (fechafincomp.compareTo(fechainicomp) < 0) {
        throw new ServiceException("Periodo de fechas incorrecto. Fecha " + fechaFin + " es menor a " + fechaInicio, new ServiceException("handledException"));
      }
      obtieneAcceso(user, "AGhAxzwOwKEbI12XQ1KNjQ*F/", pass, "AGhAxzwOwKEbI12XQ1KNjQ*C/", tokenUTC, String.valueOf(idCae));
      retorno = procs.obtieneActivacionesEmpleado(fechaInicio, fechaFin, idCae);
    } catch (Exception e) {
      MensajeLog.exception(e);
      throw new ServiceException(e.getMessage());
    }
    MensajeLog.end(timeINI);
    return retorno;
  }

}
