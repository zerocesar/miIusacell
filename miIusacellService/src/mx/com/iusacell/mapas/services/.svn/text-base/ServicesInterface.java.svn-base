package mx.com.iusacell.mapas.services;

import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.mapas.vo.CatalogoEmpleadoVO;
import mx.com.iusacell.mapas.vo.CatalogoListaCaeVO;
import mx.com.iusacell.mapas.vo.CatalogoReporteCaeVO;
import mx.com.iusacell.mapas.vo.CatalogoVersionCompetencia;
import mx.com.iusacell.mapas.vo.GeografiaVO;
import mx.com.iusacell.mapas.vo.HorarioCAEVO;
import mx.com.iusacell.mapas.vo.HorarioEmpleadoVO;
import mx.com.iusacell.mapas.vo.LoginVO;
import mx.com.iusacell.mapas.vo.ReporteEmpleadoV2VO;
import mx.com.iusacell.mapas.vo.ReporteEmpleadoVO;

public interface ServicesInterface {

  public GeografiaVO MapaObtieneGeografia(String user, String pass, int version, String tokenUTC) throws ServiceException;

  public CatalogoListaCaeVO MapaObtieneCaes(String user, String pass, int version, String tokenUTC) throws ServiceException;

  public List<CatalogoEmpleadoVO> MapaObtieneEmpleados(String user, String pass, int noEmpleado, String tokenUTC) throws ServiceException;

  public List<HorarioCAEVO> MapaObtieneAsistenciaCae(String user, String pass, int noEmpleado, String tokenUTC) throws ServiceException;

  public List<HorarioEmpleadoVO> MapaObtieneAsistenciaEmpleados(String user, String pass, int noEmpleado, String tokenUTC) throws ServiceException;

  public CatalogoVersionCompetencia MapaObtieneCompetencia(String user, String pass, int version, String tokenUTC) throws ServiceException;

  public List<ReporteEmpleadoV2VO> MapaObtieneActivacionesEmpleadoV2(String user, String pass, String fechaInicio, String fechaFin, int idCae, String tokenUTC) throws ServiceException;

  public CatalogoReporteCaeVO MapaObtieneActivacionesCaesV2(String user, String pass, String fechaInicio, String fechaFin, int noEmpleado, String tokenUTC) throws ServiceException;

  public LoginVO MapaLogin(String user, String pass, Integer noEmpleado, int plataforma, String llaveM, String fechaCliente, String ipCliente, Integer tokenBAZ, int versionAPL, Integer idSesion, String tokenUTC) throws ServiceException;

  public int MapaTerminaSesion(String user, String pass, int noEmpleado, int idSesion, int idMotivo, String tokenUTC) throws ServiceException;

  public Boolean MapaInsertaActividadDSI(String user, String pass, String ip, int idSesion, String actividad, String tokenUTC) throws ServiceException;

  public List<ReporteEmpleadoVO> MapaObtieneActivacionesEmpleado(String user, String pass, String fechaInicio, String fechaFin, int idCae, String tokenUTC) throws ServiceException;

}