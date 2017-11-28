package mx.com.iusacell.services.miiusacell.masivo.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.arquitectura.Logger;
import mx.com.iusacell.services.miiusacell.arquitectura.MensajeLogBean;
import mx.com.iusacell.services.miiusacell.arquitectura.Util;
import mx.com.iusacell.services.miiusacell.database.connection.OracleConnection;
import mx.com.iusacell.services.miiusacell.vo.BankCardAdditionalInfoVO;
import mx.com.iusacell.services.miiusacell.vo.CallTreeVO;
import mx.com.iusacell.services.miiusacell.vo.CancelacionesPasadasVO;
import mx.com.iusacell.services.miiusacell.vo.CardVO;
import mx.com.iusacell.services.miiusacell.vo.CatalogoAbonos;
import mx.com.iusacell.services.miiusacell.vo.CatalogoIndicadoresVO;
import mx.com.iusacell.services.miiusacell.vo.CatalogoTotalesVO;
import mx.com.iusacell.services.miiusacell.vo.CatatoloGetVO;
import mx.com.iusacell.services.miiusacell.vo.ConfiguracionXUsuarioMovilVO;
import mx.com.iusacell.services.miiusacell.vo.ConfiguracionXUsuarioVO;
import mx.com.iusacell.services.miiusacell.vo.ConsultaGenerica;
import mx.com.iusacell.services.miiusacell.vo.ConsultaParametrosVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoDetalleTablaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoDetalleVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoFechaTablaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumoFechaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosXdnVO;
import mx.com.iusacell.services.miiusacell.vo.CuentaCreditoVO;
import mx.com.iusacell.services.miiusacell.vo.DatosClienteCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.DatosLogin;
import mx.com.iusacell.services.miiusacell.vo.DatosUsuarioCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.GruoupIDCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.ImagenEquipoVO;
import mx.com.iusacell.services.miiusacell.vo.IndicadoresVO;
import mx.com.iusacell.services.miiusacell.vo.MovimientosTA;
import mx.com.iusacell.services.miiusacell.vo.NumerosFrecuentesVO;
import mx.com.iusacell.services.miiusacell.vo.ObtenerDescripcionPlanesVO1;
import mx.com.iusacell.services.miiusacell.vo.ObtieneEstadoMunicipioVO;
import mx.com.iusacell.services.miiusacell.vo.ObtienePoblacionesVO;
import mx.com.iusacell.services.miiusacell.vo.PagoVO;
import mx.com.iusacell.services.miiusacell.vo.PermisosClienteVO;
import mx.com.iusacell.services.miiusacell.vo.PlanVO;
import mx.com.iusacell.services.miiusacell.vo.PropertyKeyCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.PuntosVO;
import mx.com.iusacell.services.miiusacell.vo.ResponsCreateQueueVO;
import mx.com.iusacell.services.miiusacell.vo.ResponseCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.SNCODEInsuranceVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosAdicionalesVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosBundlesAdicionales;
import mx.com.iusacell.services.miiusacell.vo.ServiciosContratarVO;
import mx.com.iusacell.services.miiusacell.vo.ServiciosDisponiblesVO;
import mx.com.iusacell.services.miiusacell.vo.SuspensionReactivacionVO;
import mx.com.iusacell.services.miiusacell.vo.TarjetaVO;
import mx.com.iusacell.services.miiusacell.vo.TarjetasFrecuentesVO;
import mx.com.iusacell.services.miiusacell.vo.TipoServicioVO;
import mx.com.iusacell.services.miiusacell.vo.TreeSubgroupCancelacionVO;
import mx.com.iusacell.services.miiusacell.vo.ValidarErrorTarjetaVO;
import mx.com.iusacell.services.miiusacell.vo.catalogoCambioPlanVO;
import mx.com.iusacell.services.miusacell.util.Formatter;
import mx.com.iusacell.services.miusacell.util.Utilerias;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.FileCopyUtils;

public class OracleProcedures {

    private static Connection conecta  = null;
    Formatter formatter = new Formatter();
    int intentos = 5;
    
    
    public ConsultaParametrosVO getConsultaParametro(final int IDParametro) throws ServiceException {
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        String retorno =  "";
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        ResultSet rsetOBJ = null;
        String commORA = "";
        String commPRN = "";
        long timeIni = 0;
        ConsultaParametrosVO parametros = new ConsultaParametrosVO();
        
        try {
            commORA = "{ ? = call MIIUSACELL.PAMIIGET_RL.FNGETMIITACONSULTAPARAM(?,?,?,?) }";
            commPRN = "{ ? = call MIIUSACELL.PAMIIGET_RL.FNGETMIITACONSULTAPARAM(" + IDParametro + ") }";

            Logger.write("     Operacion              : getConsultaParametro");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);

            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            Logger.write("     Conexion BD Abierta    : true");
            stmsOBJ = connOBJ.prepareCall(commORA);
            stmsOBJ.registerOutParameter(1, OracleTypes.INTEGER);
            stmsOBJ.setInt(2, IDParametro);
            
            stmsOBJ.registerOutParameter(3, OracleTypes.INTEGER);
            stmsOBJ.registerOutParameter(4, OracleTypes.VARCHAR);
            stmsOBJ.registerOutParameter(5, OracleTypes.VARCHAR);
            
            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            parametros.setId(String.valueOf(stmsOBJ.getInt(3)));
            parametros.setDescripcion(stmsOBJ.getString(4));
            parametros.setValor(stmsOBJ.getString(5));

            Logger.write("   + Respuesta              +");
            Logger.write("     retorno                : " + retorno);
        }
        catch (Exception e) {      
            throw new ServiceException(e.getMessage());
        }
        finally {

            if(rsetOBJ != null){
                try {
                    rsetOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return parametros;
    }
    /**
     * Catalogos Fin
     */
    
    
    public String getValorParametro(final int IDParametro) throws ServiceException {
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        String retorno =  "";
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        ResultSet rsetOBJ = null;
        String commORA = "";
        String commPRN = "";
        long timeIni = 0;
        try {
            commORA = "{ ? = call MIIUSACELL.PAMIIGET_RL.FNGETMIITACONSULTAPARAM(?,?,?,?) }";
            commPRN = "{ ? = call MIIUSACELL.PAMIIGET_RL.FNGETMIITACONSULTAPARAM(" + IDParametro + ") }";

            Logger.write("     Operacion              : getValorParametro");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);

            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            Logger.write("     Conexion BD Abierta    : true");
            stmsOBJ = connOBJ.prepareCall(commORA);
            stmsOBJ.registerOutParameter(1, OracleTypes.INTEGER);
            stmsOBJ.setInt(2, IDParametro);
            
            stmsOBJ.registerOutParameter(3, OracleTypes.INTEGER);
            stmsOBJ.registerOutParameter(4, OracleTypes.VARCHAR);
            stmsOBJ.registerOutParameter(5, OracleTypes.VARCHAR);
            
            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();
            retorno = stmsOBJ.getString(5);
            
            Logger.write("   + Respuesta              +");
            Logger.write("     retorno                : " + retorno);
        }
        catch (Exception e) {      
            throw new ServiceException(e.getMessage());
        }
        finally {

            if(rsetOBJ != null){
                try {
                    rsetOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return retorno;
    } 


    public int altaCliente(final String preguntaSecreta, final String respPreguntaSecreta, final String password, final String correo, final String dn,
            final int tipoCliente, final String nombre, final String aPaterno, final String aMaterno, final int edad, final String sexo,
            final String nacimiento, final int paisID, final int unidadNegocioID, final String fotografia, final int usuarioId,final int pficvetipolinea , final int picveaplicacionid, final int pficvetipotecnologia, final int pficvetipodispositivo) 
    throws  ServiceException{
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        int retorno=0;
        long timeIni = 0;
        int i = 1;

        try{            
            commSQL = "{ ? = call  MIIUSACELL.PAMIISETLOGIN.FNINSMIICTUSUARIOALTA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIISETLOGIN.FNINSMIICTUSUARIOALTA("+
                password+","+
                correo+","+
                preguntaSecreta+","+
                respPreguntaSecreta+","+
                dn+","+
                tipoCliente+","+
                nombre+","+
                aPaterno+","+
                aMaterno+","+
                edad+","+
                sexo+","+
                nacimiento+","+
                paisID+","+
                unidadNegocioID+","+
                Utilerias.pintaLogCadenasLargas(fotografia)+","+
                usuarioId+","+ pficvetipolinea +","+picveaplicacionid+","+ pficvetipotecnologia +","+ pficvetipodispositivo +")}";
            
            Logger.write("     Operacion              : altaCliente");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());

            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}    

            if(stmsOBJ != null){
                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);             
                stmsOBJ.setString(i++, password);
                stmsOBJ.setString(i++, correo);
                stmsOBJ.setString(i++, preguntaSecreta);
                stmsOBJ.setString(i++, respPreguntaSecreta);
                stmsOBJ.setString(i++,dn);
                stmsOBJ.setInt(i++, tipoCliente);                                               
                stmsOBJ.setString(i++, nombre );
                stmsOBJ.setString(i++, aPaterno );
                stmsOBJ.setString(i++, aMaterno );
                stmsOBJ.setInt(i++, edad);
                stmsOBJ.setString(i++, sexo);
                stmsOBJ.setString(i++, nacimiento);
                stmsOBJ.setInt(i++, paisID);
                stmsOBJ.setInt(i++, unidadNegocioID);
                stmsOBJ.setString(i++,fotografia );
                stmsOBJ.setInt(i++, usuarioId);
                stmsOBJ.setInt(i++, pficvetipolinea);
                stmsOBJ.setInt(i++, picveaplicacionid);
                stmsOBJ.setInt(i++, pficvetipotecnologia);
                stmsOBJ.setInt(i++, pficvetipodispositivo);
            
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                retorno = stmsOBJ.getInt(1);        
                
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }           
        }catch(Exception e)
        {
            throw new ServiceException(e.getLocalizedMessage());
        }       
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return retorno;
    }

    public int guardaPin(final String dn, final Integer pinCreado, final MensajeLogBean mensajeLog) throws ServiceException, IOException, SQLException {
            
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        Integer codigo = 0;
        String mensaje = "";
        long timeIni = 0;
        int i=1;
        try
        {
            
            commSQL = "{ ? = call  MIIUSACELL.PAMIISETLOGIN.FNINSMIITACODIGOPIN(?,?,?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIISETLOGIN.FNINSMIITACODIGOPIN("+dn+","+pinCreado+",112)}";
            
            Logger.write("     Operacion              : guardaPin");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
            connOBJ = OracleConnection.getConnection(mensajeLog);
            if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

            if(stmsOBJ != null){
                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, pinCreado.toString());
                stmsOBJ.setInt(i++, 112);//idUsuario
                

                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();

                codigo = (Integer) stmsOBJ.getObject(1);
                
                Logger.write("   + Respuesta              +");
                Logger.write("     codigo                 : " + codigo);
                Logger.write("     mensaje                : " + mensaje);
            }
        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return  codigo;
    }

    public int altaTarjeta(final int marcaTarjeta,
            final String numeroTarjeta,  
            final String mesVencimiento, 
            final String anioVencimiento, 
            final String nombre, 
            final String aPaterno, 
            final String aMaterno, 
            final String cp, 
            final String ultimosDigitos,  
            final String dn,
            final int usuarioId) 
    throws ServiceException 
    {       
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";        
        int retorno=0;
        long timeIni = 0;
        int i = 1;

        try
        {           
            commSQL = "{ ? = call  MIIUSACELL.PAMIISET1.FNINSMIITATARJETACREDALTA (?,?,?,?,?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIISET1.FNINSMIITATARJETACREDALTA (" +
            + marcaTarjeta + ","
            +numeroTarjeta + ","
            +ultimosDigitos+ ","
            +mesVencimiento+ ","
            +anioVencimiento+ ","
            +nombre+ ","
            +aPaterno+ ","
            +aMaterno+ ","
            +cp+ ","
            +dn+ ","
            +usuarioId+ ","
            + ")}";
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());

            Logger.write("     Operacion              : altaTarjeta");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            Logger.write("     Conexion BD Abierta    : true");
            if(stmsOBJ != null){
                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER );
                stmsOBJ.setInt(i++, marcaTarjeta);
                stmsOBJ.setString(i++, numeroTarjeta);
                stmsOBJ.setString(i++, ultimosDigitos);
                stmsOBJ.setString(i++, mesVencimiento);
                stmsOBJ.setString(i++, anioVencimiento);
                stmsOBJ.setString(i++, nombre);
                stmsOBJ.setString(i++, aPaterno);
                stmsOBJ.setString(i++, aMaterno);
                stmsOBJ.setString(i++, cp);
                stmsOBJ.setString(i++, dn);     
                stmsOBJ.setInt(i++, usuarioId);

                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();

                retorno = stmsOBJ.getInt(1);                

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);                               
            }

        }
        catch(Exception e)
        {
            throw new ServiceException(e.getMessage());         
        }  
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return retorno;
    }

    public Integer cambioPass(final String dn, final String pass, final String passAnt, final String ip,final MensajeLogBean mensajeLog) throws  ServiceException
    {
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        int status=0;
        long timeIni = 0;

        try{

            commSQL = "{call  MIIUSACELL.PAMIIUPDATELOGIN.SPUPDMIITALOGGENCAMCONPAS(?,?,?,?,?,?,?,?)}";
            commPRN = "{call  MIIUSACELL.PAMIIUPDATELOGIN.SPUPDMIITALOGGENCAMCONPAS("+dn+","+pass+","+passAnt+","+1+","+ip+","+2+")}";
            connOBJ = OracleConnection.getConnection(mensajeLog);

            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

            if(stmsOBJ != null){

                stmsOBJ.setString(1, dn);
                stmsOBJ.setString(2, pass);
                stmsOBJ.setString(3, passAnt);


                stmsOBJ.setInt(4, 1);
                stmsOBJ.setString(5, ip);
                stmsOBJ.setInt(6, 2);

                stmsOBJ.registerOutParameter(7, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(8, OracleTypes.VARCHAR);

                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();

                String rsMen = (String)stmsOBJ.getObject(8);

                status = Integer.parseInt(rsMen);
            }

        }catch(SQLException e)
        {return e.getErrorCode();}
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return status;
    }

    public int bajaTarjeta( final int marcaTarjetaId, final String dn, final String numtarjeta, final int usuarioID) throws ServiceException
    {
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        String commSQL = "";
        String commPRN = "";
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        int codigo=0;
        int i=1;
        long timeIni = 0;
        try{
            
            commSQL = "{ ? = call MIIUSACELL.PAMIIUPDATE.FNUPDMIITATARJETACREDBAJA(?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIIUPDATE.FNUPDMIITATARJETACREDBAJA("+marcaTarjetaId+","+dn+","+numtarjeta+","+usuarioID+")}";
            
            Logger.write("     Operacion              : bajaTarjeta");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

            if(stmsOBJ != null){
                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER );
                stmsOBJ.setInt(i++, marcaTarjetaId);
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, numtarjeta);
                stmsOBJ.setInt(i++, usuarioID);

                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();

                codigo = (Integer)stmsOBJ.getObject(1);
                Logger.write("   + Respuesta              +");
                Logger.write("     codigo                 : " + codigo);
            }
        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return codigo;
    }

    public Integer cambioCorreo(final String dn, final String correo, final String correoAnt, final String ip,final MensajeLogBean mensajeLog) throws ServiceException{

        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        int status=0;
        long timeIni = 0;

        try{

            commSQL = "{call  MIIUSACELL.PAMIIUPDATELOGIN.SPUPDMIITALOGGENCAMCONPAS(?,?,?,?,?,?,?,?)}";
            commPRN = "{call  MIIUSACELL.PAMIIUPDATELOGIN.SPUPDMIITALOGGENCAMCONPAS("+dn+","+correo+","+correoAnt+","+1+","+ip+","+1+")}";
            connOBJ = OracleConnection.getConnection(mensajeLog);

            if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}

            if(stmsOBJ != null){
                stmsOBJ.setString(1, dn);
                stmsOBJ.setString(2, correo);
                stmsOBJ.setString(3, correoAnt);

                stmsOBJ.setInt(4, 1);
                stmsOBJ.setString(5, ip);
                stmsOBJ.setInt(6, 1);

                stmsOBJ.registerOutParameter(7, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(8, OracleTypes.VARCHAR);

                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();

                String rsMen = (String)stmsOBJ.getObject(8);

                status = Integer.parseInt(rsMen);
            }

        }catch(SQLException e){
            return status;
        }
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return status;
    }

    public DatosLogin login(final String dn) throws ServiceException
    {       
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";    
        String commPRN = "";        
        DatosLogin retorno = null;
        ResultSet rs=null;
        long timeIni = 0;
        try
        {
            commSQL = "{ ? = call  MIIUSACELL.PAMIIGETLOGIN_RL.FNGETMIITALOGINGENERICO(?,?,?,?,?,?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIIGETLOGIN_RL.FNGETMIITALOGINGENERICO("+dn+")}";

            Logger.write("     Operacion              : login");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            connOBJ = OracleConnection.getConnection(new MensajeLogBean("login"));
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

            if(stmsOBJ != null)
            {
                stmsOBJ.registerOutParameter(1, OracleTypes.INTEGER);
                stmsOBJ.setString(2, dn);                                           
                stmsOBJ.registerOutParameter( 3, OracleTypes.VARCHAR );
                stmsOBJ.registerOutParameter( 4, OracleTypes.VARCHAR );
                stmsOBJ.registerOutParameter( 5, OracleTypes.VARCHAR );
                stmsOBJ.registerOutParameter( 6, OracleTypes.INTEGER );
                stmsOBJ.registerOutParameter( 7, OracleTypes.VARCHAR );
                stmsOBJ.registerOutParameter( 8, OracleTypes.VARCHAR );
                stmsOBJ.registerOutParameter( 9, OracleTypes.VARCHAR );
                stmsOBJ.registerOutParameter(10, OracleTypes.VARCHAR );
                stmsOBJ.registerOutParameter(11, OracleTypes.CLOB    );
                stmsOBJ.registerOutParameter(12, OracleTypes.VARCHAR );
                stmsOBJ.registerOutParameter(13, OracleTypes.INTEGER );
                
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();                                                      
                Logger.write("   + Respuesta              +");  

                retorno = new DatosLogin();               

                retorno.setNombre(stmsOBJ.getString(3));
                retorno.setApPaterno(stmsOBJ.getString(4));
                retorno.setApMaterno(stmsOBJ.getString(5)==null?" ":stmsOBJ.getString(5));
                retorno.setEdad(stmsOBJ.getInt(6));
                retorno.setSexo(stmsOBJ.getString(7));
                retorno.setCorreo(stmsOBJ.getString(8));
                retorno.setPreguntaSecretea(stmsOBJ.getString(9));
                retorno.setRespPreguntaSecreta(stmsOBJ.getString(10));
                retorno.setFoto(stmsOBJ.getString(11));
                retorno.setFechaNacimiento(stmsOBJ.getString(12));
                retorno.setTipoClienteId(stmsOBJ.getInt(13));
            }
        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if(rs != null){
                try {
                    rs.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return retorno;
    }
    
    public boolean validaLogin(final String dn, final String pass) throws ServiceException
    {       
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";    
        String commPRN = "";        
        boolean retorno = false;
        long timeIni = 0;
        int respuesta = -1;
        try{            
            
            commSQL = "{ ? = call  MIIUSACELL.PAMIIGETLOGIN_RL.FNGETMIITALOGGEN(?,?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIIGETLOGIN_RL.FNGETMIITALOGGEN("+dn+","+pass+")}";

            Logger.write("     Operacion              : login");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            connOBJ = OracleConnection.getConnection(new MensajeLogBean("login"));
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

            if(stmsOBJ != null){
                stmsOBJ.registerOutParameter(1, OracleTypes.INTEGER);
                stmsOBJ.setString(2, dn);
                stmsOBJ.setString(3, pass);                                             

                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();                                                      
                Logger.write("   + Respuesta              +");
                if(stmsOBJ != null){
                    respuesta = stmsOBJ.getInt(1);
                    if(respuesta == 0)
                        retorno = true;
                    else
                        retorno = false;
                }                           
            }
        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return retorno;
    }
    
    public int consultaLogin(final String dn, final String pass, final int unidadDeNegocio) throws ServiceException
    {       
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";    
        String commPRN = "";        
        int retorno = -1;
        int i = 1;
        long timeIni = 0;
        try{            
            commSQL = "{ ? = call  MIIUSACELL.PAMIIGETLOGIN_RL.FNGETMIITALOGGENCIA(?,?,?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIIGETLOGIN_RL.FNGETMIITALOGGENCIA("+dn+","+pass+","+unidadDeNegocio+")}";

            Logger.write("     Operacion              : login");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            connOBJ = OracleConnection.getConnection(new MensajeLogBean(""));
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

            if(stmsOBJ != null){
                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, pass);       
                stmsOBJ.setInt(i++, unidadDeNegocio);

                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();                                                      
                Logger.write("   + Respuesta              +");
                if(stmsOBJ != null){
                    retorno = stmsOBJ.getInt(1);
                }                           
            }
        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return retorno;
    }

    public int altaNumeroFrecuente(final String dn, final String nombre, final String telefono, final int usuarioId, final int idOperador) throws ServiceException
    {       
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        int retorno=0;      
        long timeIni = 0;
        int i = 1;
        try
        {           
            commSQL = "{ ? = call  MIIUSACELL.PAMIISET1.FNINSMIITANUMFRECUENTEALTA(?,?,?,?,?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIISET1.FNINSMIITANUMFRECUENTEALTA("
            + dn + ","
            + nombre + ","
            + telefono + ","
            + usuarioId + ","
            + idOperador
            + ")";
            
            Logger.write("     Operacion              : altaNumeroFrecuente");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

            if(stmsOBJ != null){

                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER );
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, nombre);
                stmsOBJ.setString(i++, telefono);
                stmsOBJ.setInt(i++, usuarioId);
                stmsOBJ.setInt(i++, idOperador);

                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                
                retorno = stmsOBJ.getInt(1);    
                
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);               
            }
        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return retorno;
    }
    
    public List<NumerosFrecuentesVO> obtieneNumeroFrecuente(final String dn) throws ServiceException
    {       
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        List<NumerosFrecuentesVO> numerosList = new ArrayList<NumerosFrecuentesVO>();
        NumerosFrecuentesVO numerosSingle = new NumerosFrecuentesVO();
        ResultSet rs=null;
        long timeIni = 0;
        int i = 1;
        try
        {           
            commSQL = "{ ? = call  MIIUSACELL.PAMIIGET_RL.FNGETMIITANUMFRECUENTEXUSUA(?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIIGET_RL.FNGETMIITANUMFRECUENTEXUSUA("
            + dn + ")";
            
            Logger.write("     Operacion              : obtieneNumeroFrecuente");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

            if(stmsOBJ != null){

                stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR );
                stmsOBJ.setString(i++, dn);

                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                
                rs=(ResultSet)stmsOBJ.getObject(1);

                while (rs.next()) 
                {
                    numerosSingle = new NumerosFrecuentesVO();
                    numerosSingle.setNombre(rs.getString("FCNOMBRE"));
                    numerosSingle.setTelefono(rs.getString("FCTELEFONO"));
                    numerosList.add(numerosSingle);
                }
                
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno.size           : " + numerosList.size());                
            }
        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if (rs != null) try {
                rs.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return numerosList;
    }
    
    public List<TarjetasFrecuentesVO> obtieneTarjetasFrecuente(final String dn) throws ServiceException
    {       
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        List<TarjetasFrecuentesVO> tarjetasList = new ArrayList<TarjetasFrecuentesVO>();
        TarjetasFrecuentesVO tarjetasSingle = new TarjetasFrecuentesVO();
        ResultSet rs=null;
        long timeIni = 0;
        int i = 1;
        try
        {           
            commSQL = "{ ? = call  MIIUSACELL.PAMIIGET.FNGETMIITATARJETACREDXUSU(?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIIGET.FNGETMIITATARJETACREDXUSU("
            + dn + ")";
            
            Logger.write("     Operacion              : obtieneNumeroFrecuente");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

            if(stmsOBJ != null){

                stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR );
                stmsOBJ.setString(i++, dn);

                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                
                rs=(ResultSet)stmsOBJ.getObject(1);

                while (rs.next()) 
                {
                    tarjetasSingle = new TarjetasFrecuentesVO();
                    tarjetasSingle.setNumeroTarjeta(rs.getString("FCNUMEROTARJETA"));
                    tarjetasSingle.setMesVencimiento(rs.getString("FCMESVENCIMIENTO"));
                    tarjetasSingle.setAnioVencimiento(rs.getString("FCANIOVENCIMIENTO"));
                    tarjetasSingle.setNombre(rs.getString("FCNOMBRE"));
                    tarjetasSingle.setApPaterno(rs.getString("FCAPATERNO"));
                    tarjetasSingle.setApMaterno(rs.getString("FCAMATERNO"));
                    tarjetasSingle.setCp(rs.getString("FCCP"));
                    tarjetasSingle.setUltimosDigitos(rs.getString("FCULTIMOSDIGITOS"));
                    tarjetasSingle.setMarcaTarjetaId(rs.getInt("FICVEMARCATARJETAID"));
                    tarjetasList.add(tarjetasSingle);
                }
                
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno.size           : " + tarjetasList.size());               
            }
        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if (rs != null) try {
                rs.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return tarjetasList;
    }       

    public int bajaNumeroFrecuente(final String dn, final String telefono, final int usuarioId) throws ServiceException
    {
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        int codigo=-1;
        long timeIni = 0;
        int i=1;
        try
        {
            commSQL = "{ ?=call MIIUSACELL.PAMIIUPDATE.FNUPDMIITANUMFRECBAJA(?,?,?)}";
            commPRN = "{ ?=call MIIUSACELL.PAMIIUPDATE.FNUPDMIITANUMFRECBAJA("+dn+","+telefono+","+usuarioId+")}";
            
            Logger.write("     Operacion              : bajaNumeroFrecuente");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

            if(stmsOBJ != null){

                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER );
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, telefono);
                stmsOBJ.setInt(i++, usuarioId);

                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();

                codigo = (Integer)stmsOBJ.getObject(1);
                
                Logger.write("   + Respuesta              +");
                Logger.write("     codigo                 : " + codigo);                
            }
        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }   
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return codigo;
    }

    public int actualizaFotografiaXUsu(final String token, final String dn, final String fotografia, final int usuarioID) throws  ServiceException
    {
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        int codigo =0;
        long timeIni = 0;
        int i = 1;
        try{
            commSQL = "{ ? = call  MIIUSACELL.PAMIIUPDATE.FNUPDMIITAFOTOACTUALXUSUA(?,?,?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIIUPDATE.FNUPDMIITAFOTOACTUALXUSUA("
            + dn + ","
            + Formatter.pintaLogCadenasLargas(fotografia) + ","
            + usuarioID + ","
            + ")";
            
            Logger.write("     Operacion              : actualizaFotografiaXUsu");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

            if(stmsOBJ != null){
                
                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER );
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, fotografia);
                stmsOBJ.setInt(i++, usuarioID);

                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();

                codigo = stmsOBJ.getInt(1);
                
                Logger.write("   + Respuesta              +");
                Logger.write(" BD  :: codigo  :  "+codigo);

            }
        }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getLocalizedMessage());
        }
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }       
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return codigo;
    }

    public int editarPerfil(final String dn, final String nombre, final String apPaterno, final String apMaterno, final String correo, final String password, final String fechaNacimiento, final String fotografia, final int usuarioId) throws ServiceException 
    {
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        int codigo=-1;
        long timeIni = 0;
        int i=1;
        try
        {
            commSQL = "{?=call  MIIUSACELL.PAMIIUPDATELOGIN.FNUPDEDITARPERFILXUSUA(?,?,?,?,?,?,?,?,?)}";
            commPRN = "{?=call  MIIUSACELL.PAMIIUPDATELOGIN.FNUPDEDITARPERFILXUSUA("
                    +dn+","
                    +nombre+","
                    +apPaterno+","
                    +apMaterno+","
                    +correo+","
                    +password+","
                    +fechaNacimiento+","
                    +Formatter.pintaLogCadenasLargas(fotografia)+","
                    +usuarioId+")}";
            
            Logger.write("     Operacion              : editarPerfil");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER );
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, nombre);
                stmsOBJ.setString(i++, apPaterno);
                stmsOBJ.setString(i++, apMaterno);
                stmsOBJ.setString(i++, correo);
                stmsOBJ.setString(i++, password);
                stmsOBJ.setString(i++, fechaNacimiento);
                stmsOBJ.setString(i++, fotografia);
                stmsOBJ.setInt(i++, usuarioId);
                
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();

                codigo =(Integer) stmsOBJ.getObject(1);
                
                Logger.write("   + Respuesta              +");
                Logger.write("     codigo                 : " + codigo);                
            }

        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }           
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return codigo;
    }

    public int editarCuenta(final String dn, final String password, final String passwordAnt, final String preguntaSec, final String respuestaSec, final int usuarioId) throws ServiceException 
    {
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        int codigo=0;
        long timeIni = 0;
        int i=1;
        try
        {
            commSQL = "{? = call  MIIUSACELL.PAMIIUPDATELOGIN.FNUPDEDITARCUENTAXUSUA(?,?,?,?,?,?)}";
            commPRN = "{? = call  MIIUSACELL.PAMIIUPDATELOGIN.FNUPDEDITARCUENTAXUSUA("
                    +dn+","
                    +password+","
                    +passwordAnt+","
                    +preguntaSec+","
                    +respuestaSec+","
                    +usuarioId+")}";
            
            Logger.write("     Operacion              : editarCuenta");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

            if(stmsOBJ != null){
                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER );
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, password);
                stmsOBJ.setString(i++, passwordAnt);
                stmsOBJ.setString(i++, preguntaSec);
                stmsOBJ.setString(i++, respuestaSec);
                stmsOBJ.setInt(i++, usuarioId);

                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();

                codigo =(Integer)stmsOBJ.getObject(1);

                Logger.write("   + Respuesta              +");
                Logger.write("     codigo                 : " + codigo);
            }
        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }           
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return codigo;
    }

    public String restablecerLlave(final String dn, final String nuevoPassword, final int usuarioID) throws  ServiceException
    {
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        ResultSet rsetOBJ = null;
        String commSQL = "";
        String commPRN = "";
        String mail = "";
        long timeIni = 0;
        int i=1;

        try{
            commSQL = "{ ? = call  MIIUSACELL.PAMIIUPDATELOGIN.FNUPDMIITARECUPASSWORDXUSUA(?,?,?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIIUPDATELOGIN.FNUPDMIITARECUPASSWORDXUSUA("
                    +dn+","
                    +nuevoPassword+","
                    +usuarioID+")}";
            
            Logger.write("     Operacion              : restablecerLlave");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

            if(stmsOBJ != null){

                stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, nuevoPassword);
                stmsOBJ.setInt(i++, usuarioID);             

                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();

                rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                while(rsetOBJ.next()){
                    mail = (String)rsetOBJ.getString(1);
                }
                
                Logger.write("   + Respuesta              +");
                Logger.write("     mail                   : " + mail);              
            }
        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if (rsetOBJ != null) try {
                rsetOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }           
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return mail;
    }

    public int validarPassword(final String dn, final String password) throws ServiceException
    {
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        long timeIni = 0;
        int i=1;
        int resultado = -1;
        try{

            commSQL = "{ ? = call MIIUSACELL.PAMIIGETLOGIN_RL.FNGETMIITAVALIDAPASSWORD(?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIIGETLOGIN_RL.FNGETMIITAVALIDAPASSWORD("+dn+","+password+")}";

            Logger.write("     Operacion              : validarPassword");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

            if(stmsOBJ != null)
            {
                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, password);
                
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();

                resultado = (Integer)stmsOBJ.getObject(1);
                
                Logger.write("   + Respuesta              +");
                Logger.write("     resultado              : " + resultado);
            }
        }catch(Exception e){    
            throw new ServiceException(e.getMessage());
        }
        finally {
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }           
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return resultado;
    }
 
    public String validarDnEnPin(final String dn, final MensajeLogBean mensajeLog) throws ServiceException {
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        String commSQL = "";
        String commPRN = "";
        String retorno = "";
        long timeIni = 0;

        try {
        commSQL = "{ ? = call  MIIUSACELL.PAMIIGETLOGIN_RL.FNGETMIIREVISAEXISTEPIN(?,?)}";
        commPRN = "{ ? = call  MIIUSACELL.PAMIIGETLOGIN_RL.FNGETMIIREVISAEXISTEPIN("+dn+",out)}";
        
        Logger.write("     Operacion              : validarDnEnPin");
        Logger.write("   + Parametros             +");
        Logger.write("     commPRN                : " + commPRN);
        
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

            if(stmsOBJ != null){
                stmsOBJ.registerOutParameter(1, OracleTypes.INTEGER);
                stmsOBJ.setString(2, dn);
                stmsOBJ.registerOutParameter(3, OracleTypes.VARCHAR);
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                
                retorno = null != stmsOBJ.getString(3) && stmsOBJ.getString(3).equalsIgnoreCase("1")?dn:"";
            }
            
            Logger.write("   + Respuesta              +");
            Logger.write("     respuesta.consulta.fn  : " + stmsOBJ.getString(3));
            
        }catch(Exception e){    
            if(e.getLocalizedMessage().contains("ORA-01422")){
                retorno = dn;
            }else if (e.getLocalizedMessage().contains("ORA-01403")){
            	Logger.write("     Detail  : ORA-01403 No existe información ");
                retorno = "";
            }else throw new ServiceException(e.getMessage());
        }
        finally {
            Logger.write("     dn                     : " + retorno);
            
            if (stmsOBJ != null) try {
                stmsOBJ.close();
            }
            catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
                try {
                    connOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
            }
            Logger.write("     Conexion BD Abierta    : false");
        }           
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return retorno;
    }
     
     public List<Object> getPinPorUsuario(final String dn) throws ServiceException 
     {
         Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL="";
            String commPRN = "";
            ResultSet rs = null;
            long timeIni = 0;
            List<Object> datos=new ArrayList<Object>();
            try {
              
                commSQL = "{ ? = call MIIUSACELL.PAMIIGETLOGIN_RL.FNGETMIITACODIGOPIN(?,?,?,?)}";
                commPRN = "{ ? = call MIIUSACELL.PAMIIGETLOGIN_RL.FNGETMIITACODIGOPIN("+dn+")}";
                
                Logger.write("     Operacion              : getPinPorUsuario");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
              
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(1, OracleTypes.INTEGER);
                    stmsOBJ.setString(2,dn );      
                    stmsOBJ.registerOutParameter(3, OracleTypes.VARCHAR);
                    stmsOBJ.registerOutParameter(4, OracleTypes.INTEGER);
                    stmsOBJ.registerOutParameter(5, OracleTypes.TIMESTAMP);

                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();
                    //rs=(ResultSet)stmsOBJ.getObject(1);
                }
                //if(rs != null){
                        datos.add(stmsOBJ.getString(3));
                        datos.add(stmsOBJ.getTimestamp(5));
                        datos.add(stmsOBJ.getInt(4));
                //}else{datos.add("");}
            }catch(Exception e){    
                if (e.getLocalizedMessage().contains("ORA-01403")){
                    Logger.write("     Detail  : ORA-01403 No existe información ");
                }else throw new ServiceException(e.getLocalizedMessage());
            }
            finally {
                if (rs != null) try {
                    rs.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return datos;
        }
     
     public boolean cambiarPinEstatusNoUtilizado(final String dn) throws ServiceException
     {
         Connection connOBJ = null;
         CallableStatement stmsOBJ = null;
         String commSQL = "";
         String commPRN = "";
         int codigo=-1;
         long timeIni = 0;
         int i=1;
         try {

             commSQL = "{ ? = call MIIUSACELL.PAMIIUPDATELOGIN.FNUPDMIITAPINESTATNOUTILIZADO2(?,?)}";
             commPRN = "{ ? = call MIIUSACELL.PAMIIUPDATELOGIN.FNUPDMIITAPINESTATNOUTILIZADO2("+dn+",112)}";

             Logger.write("     Operacion              : cambiarPinEstatusNoUtilizado");
             Logger.write("   + Parametros             +");
             Logger.write("     commPRN                : " + commPRN);

             connOBJ = OracleConnection.getConnection(new MensajeLogBean());
             if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

             if(stmsOBJ != null){
                 stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                 stmsOBJ.setString(i++, dn);
                 stmsOBJ.setInt(i++, 112);//idUsuario                

                 timeIni = System.currentTimeMillis();
                 Logger.write("     Ejecutando consulta    :");
                 stmsOBJ.execute();

                 codigo = (Integer)stmsOBJ.getObject(1);

                 Logger.write("   + Respuesta              +");
                 Logger.write("     codigo                 : " + codigo);
                 if(codigo==0)
                 {
                     Logger.write("     respuesta              : true");
                     return true;
                 }
                 else{
                     Logger.write("     respuesta              : false");
                     return false;
                 }
             }
         } catch (SQLException e) {
             throw new ServiceException(e.getMessage());
         }
         finally {
             if (stmsOBJ != null) try {
                 stmsOBJ.close();
             }
             catch (SQLException e) {
                 Logger.write("     Detail  : (SQLException) " + e.getMessage());
             }
             if (connOBJ != null){
                 try {
                     connOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
             }
             Logger.write("     Conexion BD Abierta    : false");
         }      
         Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
         return false;
     }

     public int ValidaDatos(final String dn, final String preguntaSecreta, final String resPreguntaSecreta, final int usuarioID) throws  ServiceException
     {
         Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
         Connection connOBJ = null;
         CallableStatement stmsOBJ = null;
         String commSQL = "";
         String commPRN = "";
         int respuesta = -1;
         long timeIni = 0;
         int i=1;

         try{
             commSQL = "{ ? = call  MIIUSACELL.PAMIIUPDATELOGIN.FNGETMIITVALDATOS (?,?,?,?)}";
             commPRN = "{ ? = call  MIIUSACELL.PAMIIUPDATELOGIN.FNGETMIITVALDATOS ("
                 +dn+","
                 +preguntaSecreta+","
                 +resPreguntaSecreta+","
                 +usuarioID+")}";

             Logger.write("     Operacion              : validaDatos");
             Logger.write("   + Parametros             +");
             Logger.write("     commPRN                : " + commPRN);

             connOBJ = OracleConnection.getConnection(new MensajeLogBean());
             Logger.write("     Conexion BD Abiertaa   : true");
             if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

             if(stmsOBJ != null){

                 stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                 stmsOBJ.setString(i++, dn);
                 stmsOBJ.setString(i++, preguntaSecreta);
                 stmsOBJ.setString(i++, resPreguntaSecreta);
                 stmsOBJ.setInt(i++, usuarioID);                

                 Logger.write("     Ejecutando consulta    :");
                 timeIni = System.currentTimeMillis();
                 stmsOBJ.execute();

                 respuesta = stmsOBJ.getInt(1); 

                 Logger.write("   + Respuesta              +");
                 Logger.write("     RespConfirma           : " + respuesta);                
             }
         }catch(Exception e){   
             throw new ServiceException(e.getMessage());
         }
         finally {
             if (stmsOBJ != null) try {
                 stmsOBJ.close();
             }
             catch (SQLException e) {
                 Logger.write("     Detail  : (SQLException) " + e.getMessage());
             }
             if (connOBJ != null){
                 try {
                     connOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
             }
             Logger.write("     Conexion BD Abierta    : false");
         }          
         Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
         return respuesta;
     }
     
     public int eliminarUsuario(final String dn, final int isuarioId) throws  ServiceException{
         Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
         Connection connOBJ = null;
         CallableStatement stmsOBJ = null;
         String commSQL = "";
         String commPRN = "";
         int retorno=0;
         long timeIni = 0;
         int i = 1;

         try{           
             commSQL = "{ ? = call  MIIUSACELL.PAMIIUPDATE.FNDELMIINUMERO(?,?)}";
             commPRN = "{ ? = call  MIIUSACELL.PAMIIUPDATE.FNDELMIINUMERO("+dn+","+isuarioId+")}";


             Logger.write("     Operacion              : eliminaUsuario");
             Logger.write("   + Parametros             +");
             Logger.write("     commPRN                : " + commPRN);

             connOBJ = OracleConnection.getConnection(new MensajeLogBean());

             Logger.write("     Conexion BD Abierta    : true");
             if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   

             if(stmsOBJ != null){
                 stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);                
                 stmsOBJ.setString(i++, dn);
                 stmsOBJ.setInt(i++, isuarioId);


                 Logger.write("     Ejecutando consulta    :");
                 timeIni = System.currentTimeMillis();
                 stmsOBJ.execute();
                 retorno = stmsOBJ.getInt(1);       

                 Logger.write("   + Respuesta              +");
                 Logger.write("     retorno                : " + retorno);
             }          
         }catch(Exception e)
         {
             throw new ServiceException(e.getLocalizedMessage());
         }      
         finally {
             if (stmsOBJ != null) try {
                 stmsOBJ.close();
             }
             catch (SQLException e) {
                 Logger.write("     Detail  : (SQLException) " + e.getMessage());
             }
             if (connOBJ != null){
                 try {
                     connOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
             }
             Logger.write("     Conexion BD Abierta    : false");
         }      
         Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
         return retorno;
     }
     
     public int obtieneEmpresa(final String dn) throws  ServiceException{
         Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
         Connection connOBJ = null;
         CallableStatement stmsOBJ = null;
         ResultSet rs = null;
         String commSQL = "";
         String commPRN = "";
         int retorno=0;
         long timeIni = 0;
         int i = 1;

         try{           
             commSQL = "{ ? = call  MIIUSACELL.PAMIISELTRANGET.FNSELMIICUENTACREDITO(?,?)}";
             commPRN = "{ ? = call  MIIUSACELL.PAMIISELTRANGET.FNSELMIICUENTACREDITO(0,"+dn+")}";


             Logger.write("     Operacion              : obtieneEmpresa");
             Logger.write("   + Parametros             +");
             Logger.write("     commPRN                : " + commPRN);

             connOBJ = OracleConnection.getConnection(new MensajeLogBean());

             Logger.write("     Conexion BD Abierta    : true");
             if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   

             if(stmsOBJ != null){
                 stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                 stmsOBJ.setInt(i++, OracleTypes.NULL);
                 stmsOBJ.setString(i++, dn);

                 Logger.write("     Ejecutando consulta    :");
                 timeIni = System.currentTimeMillis();
                 stmsOBJ.execute();
                 rs=(ResultSet)stmsOBJ.getObject(1);

                 while (rs.next()) 
                 {
                     retorno = rs.getInt("FICVEUNIDADNEGOCIOID");
                 }  

                 Logger.write("   + Respuesta              +");
                 Logger.write("     retorno                : " + retorno);
             }          
         }catch(Exception e)
         {
             throw new ServiceException(e.getLocalizedMessage());
         }      
         finally {
             if (stmsOBJ != null) try {
                 stmsOBJ.close();
             }
             catch (SQLException e) {
                 Logger.write("     Detail  : (SQLException) " + e.getMessage());
             }
             if (connOBJ != null){
                 try {
                     connOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
             }
             Logger.write("     Conexion BD Abierta    : false");
         }      
         Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
         return retorno;
     }
     
     public List<ServiciosDisponiblesVO> serviciosDisponibles( final String UnidadNegocio) throws ServiceException {
         Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
         Connection connOBJ = null;
         CallableStatement stmsOBJ = null;
         String commSQL = "";
         String commPRN = "";
         ResultSet rsetOBJ = null;

         List<ServiciosDisponiblesVO> serviciosDisponiblesList = new ArrayList<ServiciosDisponiblesVO>();
         long timeIni = 0;
         int i = 1;

         try{
             commSQL = "{ ? = call MIIUSACELL.PAMIIGET.FNGETCTMIICONTRATASERVICIO(?)}";
             commPRN = "{ ? = call MIIUSACELL.PAMIIGET.FNGETCTMIICONTRATASERVICIO ("
                 +UnidadNegocio+")}";

             Logger.write("     Operacion              : serviciosDisponibles");
             Logger.write("   + Parametros             +");
             Logger.write("     commPRN                : " + commPRN);

             connOBJ = OracleConnection.getConnection(new MensajeLogBean());

             Logger.write("     Conexion BD Abierta    : true");
             if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   

             if(stmsOBJ != null){
                 stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                 stmsOBJ.setString(i++, UnidadNegocio);

                 Logger.write("     Ejecutando consulta    :");
                 timeIni = System.currentTimeMillis();
                 stmsOBJ.execute();

                 rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                 while(rsetOBJ.next()){
                     ServiciosDisponiblesVO serviciosDisponibles = new ServiciosDisponiblesVO();

                     serviciosDisponibles.setContratarServicioID(rsetOBJ.getString("FCCONTRATASERVICIOID"));
                     serviciosDisponibles.setContratarServicioDesc(rsetOBJ.getString("FCCONTRATASERVICIODESC"));

                     serviciosDisponiblesList.add(serviciosDisponibles);
                 }
                 Logger.write("   + Respuesta              +");
             }
         }catch(SQLException e){
             throw new ServiceException(e.getLocalizedMessage());
         }finally{
             if (stmsOBJ != null) try {
                 stmsOBJ.close();
             }
             catch (SQLException e) {
                 Logger.write("     Detail  : (SQLException) " + e.getMessage());
             }
             if (connOBJ != null){
                 try {
                     connOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
             }
             Logger.write("     Conexion BD Abierta    : false");    
         }
         Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
         return serviciosDisponiblesList;

     }
     
     public List<ObtieneEstadoMunicipioVO> serviciosObtieneMunicipioXEdo( final String pfcveedocen) throws ServiceException {

            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             List<ObtieneEstadoMunicipioVO> MunicipioXEdoList = new ArrayList<ObtieneEstadoMunicipioVO>();
             
             long timeIni = 0;
             int i = 1; 
             
          try{
             commSQL = "{ ? = call MIIUSACELL.PAMIIPOBLACIONXEDO.FNGETMUNICIPIOXEDO(?)}";
             
             commPRN = "{ ? = call MIIUSACELL.PAMIIPOBLACIONXEDO.FNGETMUNICIPIOXEDO("
                        +pfcveedocen+")}";
             
             Logger.write("     Operacion              : serviciosObtieneMunicipioXEdo");
             Logger.write("   + Parametros             +");
             Logger.write("     commPRN                : " + commPRN);
             
             
             connOBJ = OracleConnection.getConnection(new MensajeLogBean());

             Logger.write("     Conexion BD Abierta    : true");
             if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
             
             if(stmsOBJ != null){
                 stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                 stmsOBJ.setString(i++, pfcveedocen);
                 
                 Logger.write("     Ejecutando consulta    :");
                 timeIni = System.currentTimeMillis();
                 stmsOBJ.execute();

                 rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                 
                 while(rsetOBJ.next()){
                     ObtieneEstadoMunicipioVO obtieneEstadosMunicipioVo = new ObtieneEstadoMunicipioVO();
                     
                     obtieneEstadosMunicipioVo.setFcveedocen(rsetOBJ.getString("FCCVEEDOCEN"));
                     obtieneEstadosMunicipioVo.setFcvemuncen(rsetOBJ.getString("FCCVEMUNCEN"));
                     obtieneEstadosMunicipioVo.setFmunicipio(rsetOBJ.getString("FCMUNICIPIO"));
                     
                     MunicipioXEdoList.add(obtieneEstadosMunicipioVo);
                 }
             }
          }catch(SQLException e){
             Logger.write("     Detail    : (Exception) " + e.getMessage());     

         }finally{
            if (rsetOBJ != null) try {
                 rsetOBJ.close();
             }
             catch (SQLException e) {
                 Logger.write("     Detail  : (SQLException) " + e.getMessage());
             }
             if (stmsOBJ != null) try {
                 stmsOBJ.close();
             }
             catch (SQLException e) {
                 Logger.write("     Detail  : (SQLException) " + e.getMessage());
             }
             if (connOBJ != null){
                 try {
                     connOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
             }
             Logger.write("     Conexion BD Abierta    : false");    
         }
         Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
         return MunicipioXEdoList;
        }
        
        public List<ObtieneEstadoMunicipioVO> serviciosObtieneEdo() throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             List<ObtieneEstadoMunicipioVO> EdoList = new ArrayList<ObtieneEstadoMunicipioVO>();
             
             long timeIni = 0;
             int i = 1; 
             
          try{
             commSQL = "{ ? = call MIIUSACELL.PAMIIPOBLACIONXEDO.FNGETESTADO()}";
             commPRN = "{ ? = call MIIUSACELL.PAMIIPOBLACIONXEDO.FNGETESTADO()}";
                        
             Logger.write("     Operacion              : serviciosObtieneEdo");
             Logger.write("   + Parametros             +");
             Logger.write("     commPRN                : " + commPRN);
             
             connOBJ = OracleConnection.getConnection(new MensajeLogBean());

             Logger.write("     Conexion BD Abierta    : true");
             if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
             
             if(stmsOBJ != null){
                 stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                 
                 Logger.write("     Ejecutando consulta    :");
                 timeIni = System.currentTimeMillis();
                 stmsOBJ.execute();

                 rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                 
                 while(rsetOBJ.next()){
                     ObtieneEstadoMunicipioVO obtieneEstadosMunicipioVo = new ObtieneEstadoMunicipioVO();
                     
                     obtieneEstadosMunicipioVo.setFcveedocen(rsetOBJ.getString("FCCVEEDOCEN"));
                     obtieneEstadosMunicipioVo.setFcvemuncen(rsetOBJ.getString("FCCLAVEEDO"));
                     obtieneEstadosMunicipioVo.setFmunicipio(rsetOBJ.getString("FCDESCRIPCION"));

                     EdoList.add(obtieneEstadosMunicipioVo);
                 }
             }
             
          }catch(SQLException e){
             Logger.write("     Detail    : (Exception) " + e.getMessage());     

         }finally{
             if (rsetOBJ != null) try {
                 rsetOBJ.close();
             }
             catch (SQLException e) {
                 Logger.write("     Detail  : (SQLException) " + e.getMessage());
             }
             if (stmsOBJ != null) try {
                 stmsOBJ.close();
             }
             catch (SQLException e) {
                 Logger.write("     Detail  : (SQLException) " + e.getMessage());
             }
             if (connOBJ != null){
                 try {
                     connOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
             }
             Logger.write("     Conexion BD Abierta    : false");    
         }
         Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
         return EdoList;
        }
        
        public List<ObtienePoblacionesVO> serviciosObtienePoblaciones(final String cveedocen, final String pfcvemuncen) throws ServiceException {
            
             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             List<ObtienePoblacionesVO> poblacionesList = new ArrayList<ObtienePoblacionesVO>();
             
             long timeIni = 0;
             int i = 1; 
            
             try{
                 
                 commSQL = "{ ? = call MIIUSACELL.PAMIIPOBLACIONXEDO.FNGETPOBLACIONXMUNXEDO (?,?)}";
                 commPRN = "{ ? = call MIIUSACELL.PAMIIPOBLACIONXEDO.FNGETPOBLACIONXMUNXEDO ("
                       +cveedocen+","+pfcvemuncen+")}";
             
             Logger.write("     Operacion              : serviciosObtieneEdo");
             Logger.write("   + Parametros             +");
             Logger.write("     commPRN                : " + commPRN);
             
             
             connOBJ = OracleConnection.getConnection(new MensajeLogBean());
             
             Logger.write("     Conexion BD Abierta    : true");
             if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
                 
             if(stmsOBJ != null){
                 stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                 stmsOBJ.setString(i++, cveedocen);
                 stmsOBJ.setString(i++, pfcvemuncen);
                 
                 Logger.write("     Ejecutando consulta    :");
                 timeIni = System.currentTimeMillis();
                 stmsOBJ.execute();

                 rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                 
                 while(rsetOBJ.next()){
                     ObtienePoblacionesVO obtienePoblaciones = new ObtienePoblacionesVO();
                     
                    obtienePoblaciones.setFcveedocen(rsetOBJ.getString("FCCVEEDOCEN"));
                    obtienePoblaciones.setFcvemuncen(rsetOBJ.getString("FCCVEMUNCEN"));
                    obtienePoblaciones.setFcvepobcen(rsetOBJ.getString("FCCVEPOBCEN"));
                    obtienePoblaciones.setFclaveedo(rsetOBJ.getString("FCCLAVEEDO"));
                    obtienePoblaciones.setEstado(rsetOBJ.getString("ESTADO"));
                    obtienePoblaciones.setFmunicipio(rsetOBJ.getString("FCMUNICIPIO"));
                    obtienePoblaciones.setPoblacion(rsetOBJ.getString("POBLACION"));
                    obtienePoblaciones.setFld(rsetOBJ.getString("FCLD"));
                    obtienePoblaciones.setFpcs(rsetOBJ.getString("FCPCS"));
                    obtienePoblaciones.setFiva(rsetOBJ.getString("FNIVA"));
                    obtienePoblaciones.setFsistemas(rsetOBJ.getString("FISISTEMAS"));
                    obtienePoblaciones.setFidregion(rsetOBJ.getString("FNIDREGION"));
                    obtienePoblaciones.setForden(rsetOBJ.getString("FIORDEN"));
                    obtienePoblaciones.setFiddivision(rsetOBJ.getString("FIDDIVISION"));
                     
                    poblacionesList.add(obtienePoblaciones);
                     
                 }
             }
             
              }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
                 }finally{
                     if (rsetOBJ != null) try {
                         rsetOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                     if (stmsOBJ != null) try {
                         stmsOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                     if (connOBJ != null){
                         try {
                             connOBJ.close();
                         }
                         catch (SQLException e) {
                             Logger.write("     Detail  : (SQLException) " + e.getMessage());
                         }
                     }
                     Logger.write("     Conexion BD Abierta    : false");    
                 }
                 Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
                 return poblacionesList;
            }
        
        public int configuracionXUsuario(final String pcdn, final String pcconfiguracion, final int piiusuarioid) throws  ServiceException{
             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             int retorno=0;
             long timeIni = 0;
             int i = 1;

             try{           
                 commSQL = "{? = call  MIIUSACELL.PAMIISET1.FNINSMIICONFIGURACIONXUSUARIO(?,?,?)}";
                 commPRN = "{? = call  MIIUSACELL.PAMIISET1.FNINSMIICONFIGURACIONXUSUARIO("+pcdn+","+pcconfiguracion+","+piiusuarioid+")}";
                 
                 Logger.write("     Operacion              : configuracionXUsuario");
                 Logger.write("   + Parametros             +");
                 Logger.write("     commPRN                : " + commPRN);

                 connOBJ = OracleConnection.getConnection(new MensajeLogBean());

                 Logger.write("     Conexion BD Abierta    : true");
                 if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   

                 if(stmsOBJ != null){
                     stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                     stmsOBJ.setString(i++, pcdn);
                     stmsOBJ.setString(i++, pcconfiguracion);
                     stmsOBJ.setInt(i++, piiusuarioid);
                     
                     
                     Logger.write("     Ejecutando consulta    :");
                     timeIni = System.currentTimeMillis();
                     stmsOBJ.execute();
                     
                     retorno = stmsOBJ.getInt(1);

                     Logger.write("   + Respuesta              +");
                     Logger.write("     retorno                : " + retorno);
                 }          
             }catch(Exception e)
             {
                 throw new ServiceException(e.getLocalizedMessage());
             }      
             finally {
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");
             }      
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return retorno;
         }
        
        public ConfiguracionXUsuarioVO obtieneConfiguracionXUsuario(final String fcdn) throws ServiceException {
            
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ConfiguracionXUsuarioVO configuracionUsuario = new ConfiguracionXUsuarioVO();
            
             long timeIni = 0;
            
             try{
                 
                 commSQL = "{ ? = call MIIUSACELL.PAMIIGET_RL.FNGETMIICONFIGURACIONXUSUARIO (?,?,?)}";
                 commPRN = "{ ? = call MIIUSACELL.PAMIIGET_RL.FNGETMIICONFIGURACIONXUSUARIO ("+fcdn+")}";
                 
                 Logger.write("     Operacion              : obtieneConfiguracionXUsuario");
                 Logger.write("   + Parametros             +");
                 Logger.write("     commPRN                : " + commPRN);
                 
                 connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                 
                 Logger.write("     Conexion BD Abierta    : true");
                 if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
                 
                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(1, OracleTypes.INTEGER);
                    stmsOBJ.setString(2, fcdn);
                    stmsOBJ.registerOutParameter(3, OracleTypes.VARCHAR);
                    stmsOBJ.registerOutParameter(4, OracleTypes.VARCHAR);
                    
                     Logger.write("     Ejecutando consulta    :");
                     timeIni = System.currentTimeMillis();
                     stmsOBJ.execute();

                     configuracionUsuario.setFcdn(stmsOBJ.getString(3));
                     configuracionUsuario.setFcconfiguracion(stmsOBJ.getString(4));
                }
              }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
                 }finally{
                     if (stmsOBJ != null) try {
                         stmsOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                     if (connOBJ != null){
                         try {
                             connOBJ.close();
                         }
                         catch (SQLException e) {
                             Logger.write("     Detail  : (SQLException) " + e.getMessage());
                         }
                     }
                     Logger.write("     Conexion BD Abierta    : false");    
                 }
                 Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return configuracionUsuario;
        }
        
        public int cambioDNBD(final String dnactual,final String dncambia, final int usuarioId ) throws ServiceException {

            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response=-1;

            long timeIni = 0;
            int i = 1; 

            try{

                commSQL = "{ ? = call MIIUSACELL.PAMIIUPDATELOGIN.FNINSCAMBIODN (?,?,?)}";
                commPRN = "{ ? = call MIIUSACELL.PAMIIUPDATELOGIN.FNINSCAMBIODN ("
                    +dnactual+","+dncambia+","+usuarioId+")}";

                Logger.write("     Operacion              : cambioDN");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());

                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, dnactual);
                    stmsOBJ.setString(i++, dncambia);
                    stmsOBJ.setInt(i++, usuarioId);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    response = stmsOBJ.getInt(1);
                }
            }catch(SQLException e){
                Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));

            return response;
        }
        
        public int insertaDNxReservar(final String dnReserva) throws ServiceException
        {       
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";    
            String commPRN = "";        
            int retorno = -1;
            
            long timeIni = 0;
            int i = 1;
            
            try{    
                commSQL = "{ ? = call  MIIUSACELL.PAMIISET1.FNSETRESERVADN(?)}";
                commPRN = "{ ? = call  MIIUSACELL.PAMIISET1.FNSETRESERVADN("+dnReserva+")}";
                
                Logger.write("     Operacion              : insertaDNxReservar");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean(""));
                Logger.write("     Conexion BD Abierta    : true");
                
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, dnReserva);                                          
                    
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();                                                      
                    
                    retorno = stmsOBJ.getInt(1);
                    Logger.write("   + Respuesta              +" + retorno);    


                    }   
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }
        
        //actualiza DN
        public int actualizaDN(final String dn) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response=-1;

            long timeIni = 0;
            int i = 1; 

            try{

                commSQL = "{ ? = call MIIUSACELL.PAMIIUPDATE.FNUPDRESERVADN (?)}";
                commPRN = "{ ? = call MIIUSACELL.PAMIIUPDATE.FNUPDRESERVADN ("
                    +dn+")}";

                Logger.write("     Operacion              : cambioDN");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());

                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, dn);
                    

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    response = stmsOBJ.getInt(1);
                }
            }catch(SQLException e){
                Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));

            return response;
        }
        
        public List<ServiciosContratarVO> servicioLegacy(final String dn) throws ServiceException
        {       
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";    
            String commPRN = "";        
            List<ServiciosContratarVO> response = new ArrayList<ServiciosContratarVO>();
            ServiciosContratarVO responseSingle = new ServiciosContratarVO();
            
            ResultSet rs=null;
            int i = 1;
            long timeIni = 0;
            try{            
                commSQL = "{ ? = call  MIIUSACELL.PAMIIREPORTERIA.FNGETSERVICIOLEGACY(?,?,?)}";
                commPRN = "{ ? = call  MIIUSACELL.PAMIIREPORTERIA.FNGETSERVICIOLEGACY("+dn+")}";

                Logger.write("     Operacion              : servicioLegacy");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setString(i++, dn);
                    stmsOBJ.setNull(i++, OracleTypes.NULL);
                    stmsOBJ.setNull(i++, OracleTypes.NULL);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();                                                      
                    Logger.write("   + Respuesta              +");  

                    rs = (ResultSet)stmsOBJ.getObject(1);
                    i = 1;
                    while (rs.next()) 
                    {
                        responseSingle = new ServiciosContratarVO();
                        responseSingle.setIdServicio(rs.getString("FCSERVICIOSID"));
                        responseSingle.setDescripcionServicio(rs.getString("FCSERVICIOS"));
                        responseSingle.setStatusServicios("1");
                        response.add(responseSingle);
                    }   
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if(rs != null){
                    try {
                        rs.close();
                    }
                    catch (SQLException e) {
                        Logger.write("    Detail : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return response;
        }
        
        public String getDetalleConsumoPorDn(final String dn, final int id) throws ServiceException
        {       
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";    
            String commPRN = "";        
            String detalle = "";
            
            int i = 1;
            long timeIni = 0;
            try{            
                commSQL = "{ ? = call  MIIUSACELL.PAMIIGET.FNMIIXMLXDNGET(?,?)}";
                commPRN = "{ ? = call  MIIUSACELL.PAMIIGET.FNMIIXMLXDNGET("+dn+","+id+")}";

                Logger.write("     Operacion              : getDetalleConsumoPorDn");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CLOB);
                    stmsOBJ.setString(i++, dn);
                    stmsOBJ.setInt(i++, id);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();                                                      
                    Logger.write("   + Respuesta              +");  

                    if(stmsOBJ != null){
                        detalle  = stmsOBJ.getString(1);
                    }
                    Logger.write("   + XMLResponse              +" + detalle);  
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return detalle;
        }
        
        public String getDetalleConsumoPorDnAlt(final String dn, final int id) throws ServiceException
        {       
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";    
            String commPRN = "";        
            String detalle = "";
            
            int i = 1;
            long timeIni = 0;
            try{            
                commSQL = "{ ? = call  MIIUSACELL.PAMIIGET.FNMIIXMLXDNALTGET(?,?)}";
                commPRN = "{ ? = call  MIIUSACELL.PAMIIGET.FNMIIXMLXDNALTGET("+dn+","+id+")}";

                Logger.write("     Operacion              : getDetalleConsumoPorDn");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CLOB);
                    stmsOBJ.setString(i++, dn);
                    stmsOBJ.setInt(i++, id);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();                                                      
                    Logger.write("   + Respuesta              +");  

                    if(stmsOBJ != null){
                        detalle  = stmsOBJ.getString(1);
                    }
                    Logger.write("   + XMLResponse              +" + detalle);  
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return detalle;
        }
        
        public int shedulerXMLALT(final String dn, final int identificador, final String xml, final int usuario) throws Exception {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            int retorno = -1;
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;

            try{
                commSQL = "{ ? =  call MIIUSACELL.PAMIISET1.FNINSXMLXDNALT(?,?,?,?)}";
                commPRN = "{ ? =  call MIIUSACELL.PAMIISET1.FNINSXMLXDNALT("+dn+","+identificador+","+Utilerias.pintaLogCadenasLargas(xml)+","+usuario+")}";

                Logger.write("     Operacion              : shedulerXMLALT");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");

                if(connOBJ != null) 
                    stmsOBJ = connOBJ.prepareCall(commSQL);

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, dn);
                    stmsOBJ.setInt(i++, identificador);
                    stmsOBJ.setString(i++, xml);
                    stmsOBJ.setInt(i++, usuario);
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();
                    retorno = stmsOBJ.getInt(1); 

                    Logger.write("   + Respuesta              +");
                    Logger.write("     retorno                : " + retorno);
                }
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }

                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;       
        }
        
        public int shedulerGeneraXML(final String dn, final int identificador, final String xml, final int usuario) throws Exception {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            int retorno = -1;
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;

            try{
                commSQL = "{ ? =  call MIIUSACELL.PAMIISET1.FNINSXMLXDN(?,?,?,?)}";
                commPRN = "{ ? =  call MIIUSACELL.PAMIISET1.FNINSXMLXDN("+dn+","+identificador+","+Utilerias.pintaLogCadenasLargas(xml)+","+usuario+")}";

                Logger.write("     Operacion              : shedulerGeneraXML");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");

                if(connOBJ != null) 
                    stmsOBJ = connOBJ.prepareCall(commSQL);

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, dn);
                    stmsOBJ.setInt(i++, identificador);
                    stmsOBJ.setString(i++, xml);
                    stmsOBJ.setInt(i++, usuario);
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();
                    retorno = stmsOBJ.getInt(1); 

                    Logger.write("   + Respuesta              +");
                    Logger.write("     retorno                : " + retorno);
                }
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }

                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;       
        }
        
        public ConsumosXdnVO  getconsumosXdn(final String dn, final int identificacion) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            ConsumosXdnVO consumoxdn = new ConsumosXdnVO(); 
            try {
                commORA = "{ ? = call MIIUSACELL.PAMIIGET_RL.FNMIICONSUMOSXDNGET(?,?,?,?,?,?)}";
                commPRN = "{ ? = call MIIUSACELL.PAMIIGET_RL.FNMIICONSUMOSXDNGET(" + dn + ","+identificacion+")}";

                Logger.write("     Operacion              : getconsumosXdn");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                stmsOBJ.registerOutParameter(1, OracleTypes.INTEGER);
                stmsOBJ.setString(2, dn);
                stmsOBJ.setInt(3, identificacion);
                stmsOBJ.registerOutParameter(4, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(5, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(6, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(7, OracleTypes.VARCHAR);
                
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                
                try
                {
                    consumoxdn = new ConsumosXdnVO();
                    consumoxdn.setRegalo(stmsOBJ.getString(4));
                    consumoxdn.setExtra(stmsOBJ.getString(5));
                    consumoxdn.setIncluidos(stmsOBJ.getString(6));
                    consumoxdn.setTotal(stmsOBJ.getString(7));
                }
                catch (Exception e)
                {
                    throw new ServiceException("Sin Datos");
                }

                Logger.write("   + Respuesta              +");
                Logger.write("     Extra                  : " + consumoxdn.getExtra());
                Logger.write("     Incluidos              : " + consumoxdn.getIncluidos());
                Logger.write("     Regalo                 : " + consumoxdn.getRegalo());
                Logger.write("     Total                  : " + consumoxdn.getTotal());
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {
                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoxdn;
        }
        
        public ObtenerDescripcionPlanesVO1  getPlanXdn(final String dn, final int identificador) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            ObtenerDescripcionPlanesVO1 descripcionPlanes = new ObtenerDescripcionPlanesVO1();
            
            try {
                commORA = "{ ? = call MIIUSACELL.PAMIIGET_RL.FNMIIPLANXDN(?,?,?,?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call MIIUSACELL.PAMIIGET_RL.FNMIIPLANXDN(" + dn + ","+identificador+")}";

                Logger.write("     Operacion              : getPlanXdn");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                stmsOBJ.registerOutParameter(1, OracleTypes.INTEGER);
                stmsOBJ.setString(2, dn);
                stmsOBJ.setInt(3, identificador);
                stmsOBJ.registerOutParameter(4, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(5, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(6, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(7, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(8, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(9, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(10, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(11, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(12, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(13, OracleTypes.VARCHAR);
                
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                
                try
                {
                    descripcionPlanes = new ObtenerDescripcionPlanesVO1();
                    descripcionPlanes.setMegaadc(stmsOBJ.getString(4));
                    descripcionPlanes.setMegas(stmsOBJ.getString(5));
                    descripcionPlanes.setMensajeadc(stmsOBJ.getString(6));
                    descripcionPlanes.setMensajes(stmsOBJ.getString(7));
                    descripcionPlanes.setMinutoadc(stmsOBJ.getString(8));
                    descripcionPlanes.setMinutos(stmsOBJ.getString(9));
                    descripcionPlanes.setMinutoscomunidad(stmsOBJ.getString(10));
                    descripcionPlanes.setNombreCortoPlan(stmsOBJ.getString(11));
                    descripcionPlanes.setTiempoAire(stmsOBJ.getString(12));
                    descripcionPlanes.setIdPlan(stmsOBJ.getString(13));
                }
                catch (SQLException e)
                {
                    throw new ServiceException("Datos no encontrados");
                }

                Logger.write("   + Respuesta              +");
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {
                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return descripcionPlanes;
        }
        
        public int setConsumoXdn(final String dn, final int identificador, final String regalo, final String extra, final String incluidos, final String total, final int usuario) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            int retorno = -1;
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            
            try{
                commSQL = "{ ? =  call MIIUSACELL.PAMIISET1.FNINSCONSUMOSXDN(?,?,?,?,?,?,?)}";
                commPRN = "{ ? =  call MIIUSACELL.PAMIISET1.FNINSCONSUMOSXDN("+dn+","+identificador+","+regalo+","+extra+","+incluidos+","+total+","+usuario+")}";

                Logger.write("     Operacion              : setConsumoXdn");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                
                if(connOBJ != null) 
                    stmsOBJ = connOBJ.prepareCall(commSQL);

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, dn);
                    stmsOBJ.setInt(i++, identificador);
                    stmsOBJ.setString(i++, regalo);
                    stmsOBJ.setString(i++, extra);
                    stmsOBJ.setString(i++, incluidos);
                    stmsOBJ.setString(i++, total);
                    stmsOBJ.setInt(i++, usuario);
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();
                    retorno = stmsOBJ.getInt(1); 

                    Logger.write("   + Respuesta              +");
                    Logger.write("     retorno                : " + retorno);
                }
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno; 
        }
        
        public int setDatosPlanesXdn(final String dn, final int identificador, final String  megaadc, final String megas, final String mensajeadc,
                final String mensaje, final String minutoadc, final String minuto, final String minutoComunidad,
                final String nombrePlanCorto, final String tiempoAire, final String idPlan, final int usuario) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            int retorno = -1;
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            
            try{
                commSQL = "{ ? =  call MIIUSACELL.PAMIISET1.FNINSPLANXDN(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? =  call MIIUSACELL.PAMIISET1.FNINSPLANXDN("+dn+","+identificador+
                ","+megaadc+","+megas+","+mensajeadc+","+mensaje+","+minutoadc+
                ","+minuto+","+minutoComunidad+","+nombrePlanCorto+","+tiempoAire+","+idPlan+","+usuario+")}";

                Logger.write("     Operacion              : setDatosPlanesXdn");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                
                if(connOBJ != null) 
                    stmsOBJ = connOBJ.prepareCall(commSQL);

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, dn);
                    stmsOBJ.setInt(i++, identificador);
                    stmsOBJ.setString(i++, megaadc);
                    stmsOBJ.setString(i++, megas);
                    stmsOBJ.setString(i++, mensajeadc);
                    stmsOBJ.setString(i++, mensaje);
                    stmsOBJ.setString(i++, minutoadc);
                    stmsOBJ.setString(i++, minuto);
                    stmsOBJ.setString(i++, minutoComunidad);
                    stmsOBJ.setString(i++, nombrePlanCorto);
                    stmsOBJ.setString(i++, tiempoAire);
                    stmsOBJ.setString(i++, idPlan);
                    stmsOBJ.setInt(i++, usuario);
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();
                    retorno = stmsOBJ.getInt(1); 

                    Logger.write("   + Respuesta              +");
                    Logger.write("     retorno                : " + retorno);
                }
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno; 
        }
        
        /**
         * Consumos Ini
         */
        
        public List<ConsumoFechaVO> getLlamadas(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s C o n s u m o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            ConsumoDetalleVO consumo = new ConsumoDetalleVO();
            List<ConsumoDetalleVO> consumoList = new ArrayList<ConsumoDetalleVO>();
            ConsumoFechaVO consumoFecha = new ConsumoFechaVO();
            List<ConsumoFechaVO> consumoFechaList = new ArrayList<ConsumoFechaVO>();
            
            try{
                 int varTimeOut = 20; 
                 try{
                   varTimeOut = Integer.parseInt(getValorParametro(118));
                 }catch (Exception e) {
                    varTimeOut = 20;
                 }
                
                commSQL = "{ ? =  call CLIENREPUNI.PQBICCONSUMOS.FNGETDETALLEVOZBIC(?,?,?)}";
                commPRN = "{ ? =  call CLIENREPUNI.PQBICCONSUMOS.FNGETDETALLEVOZBIC("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                
                
                for(int cont = 0; cont < intentos; cont++)
                {
                    try
                    {
                        connOBJ = OracleConnection.getConnectionConsumos(new MensajeLogBean());
                        Logger.write("     Conexion BD Abierta    : true");

                        if(connOBJ != null) 
                            stmsOBJ = connOBJ.prepareCall(commSQL);

                        stmsOBJ.setQueryTimeout(varTimeOut);

                        Logger.write("     Conexion BD Abierta    : true");
                        i = 1;
                        stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                        stmsOBJ.setString(i++, dn);
                        stmsOBJ.setString(i++, fechaIni);
                        stmsOBJ.setString(i++, fechaFin);



                        Logger.write("     Ejecutando consulta    intento[" + (cont+1) +"] :");
                        timeIni = System.currentTimeMillis();
                        stmsOBJ.execute();  
                        rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                        break;
                    }
                    catch (Exception e) 
                    {
                        boolean reintentar = Utilerias.contieneMensajeError(e.getLocalizedMessage());                                               
                        if(cont < (intentos-1) && reintentar)                           
                        {
                            Logger.write("     [WARN]  : (SQLException) " + e.getLocalizedMessage());
                            if(connOBJ != null) try {
                                connOBJ.close();    
                                Logger.write("     Conexion BD Abierta    : false");
                            }catch (Exception e1) {
                                Logger.write("     Detail  : (SQLException) " + e1.getMessage());   
                            }
                            continue;
                        }
                        else
                        {
                            throw new ServiceException(e.getLocalizedMessage());
                        }
                    }                   
                }
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleVO();
                    consumoList = new ArrayList<ConsumoDetalleVO>();
                    consumoFecha = new ConsumoFechaVO();
                    consumo.setCdescWallet(rsetOBJ.getString("C_DESC_WALLET"));
                    consumo.setCiudadDestino(rsetOBJ.getString("DESTINO"));
                    consumo.setDescTipoTrafico(rsetOBJ.getString("DESC_TIPO_TRAFICO"));
                    consumo.setDurMinRedn(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setHoraEvento(rsetOBJ.getString("HORA_EVENTO"));
                    consumo.setMontoFree(rsetOBJ.getString("MONTOFREE"));
                    consumo.setMontoReal(rsetOBJ.getString("MONTOREAL"));
                    consumo.setNumDestino(rsetOBJ.getString("NUM_DESTINO"));
                    consumo.setNumOrigen(rsetOBJ.getString("NUM_ORIGEN"));
                    consumo.setOperadorDestino(rsetOBJ.getString("OPERADOR_DESTINO"));
                    consumo.setOperadorOrigen(rsetOBJ.getString("OPERADOR_ORIGEN"));
                    consumo.setSaldoAntes(rsetOBJ.getString("N_SALDO_ANTES"));
                    consumo.setSaldoDespues(rsetOBJ.getString("N_SALDO_DESPUES"));
                    consumo.setTotalNavegacion("");
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    consumoFechaList.add(consumoFecha);
                }
                

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        public List<ConsumoFechaVO> getMensajes(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s C o n s u m o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            ConsumoDetalleVO consumo = new ConsumoDetalleVO();
            List<ConsumoDetalleVO> consumoList = new ArrayList<ConsumoDetalleVO>();
            ConsumoFechaVO consumoFecha = new ConsumoFechaVO();
            List<ConsumoFechaVO> consumoFechaList = new ArrayList<ConsumoFechaVO>();
            
            try{
                
                int varTimeOut = 20; 

                try{
                   varTimeOut = Integer.parseInt(getValorParametro(118));
                }catch (Exception e) {
                    varTimeOut = 20;
                }
                
                commSQL = "{ ? =  call CLIENREPUNI.PQBICCONSUMOS.FNGETDETALLESMSBIC(?,?,?)}";
                commPRN = "{ ? =  call CLIENREPUNI.PQBICCONSUMOS.FNGETDETALLESMSBIC("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                
                for(int cont = 0; cont < intentos; cont++)
                {
                    try
                    {
                        connOBJ = OracleConnection.getConnectionConsumos(new MensajeLogBean());
                        Logger.write("     Conexion BD Abierta    : true");

                        if(connOBJ != null) 
                            stmsOBJ = connOBJ.prepareCall(commSQL);

                        stmsOBJ.setQueryTimeout(varTimeOut);

                        Logger.write("     Conexion BD Abierta    : true");
                        i = 1;
                        stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                        stmsOBJ.setString(i++, dn);
                        stmsOBJ.setString(i++, fechaIni);
                        stmsOBJ.setString(i++, fechaFin);



                        Logger.write("     Ejecutando consulta    intento[" + (cont+1) +"] :");
                        timeIni = System.currentTimeMillis();
                        stmsOBJ.execute();  
                        rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                        break;
                    }
                    catch (Exception e) 
                    {
                        boolean reintentar = Utilerias.contieneMensajeError(e.getLocalizedMessage());                       
                        if(cont < (intentos-1) && reintentar)
                        {
                            Logger.write("     [WARN]  : (SQLException) " + e.getLocalizedMessage());
                            if(connOBJ != null) try {
                                connOBJ.close();    
                                Logger.write("     Conexion BD Abierta    : false");
                            }catch (Exception e1) {
                                Logger.write("     Detail  : (SQLException) " + e1.getMessage());   
                            }
                            continue;
                        }
                        else
                        {
                            throw new ServiceException(e.getLocalizedMessage());
                        }
                    }                   
                }
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleVO();
                    consumoList = new ArrayList<ConsumoDetalleVO>();
                    consumoFecha = new ConsumoFechaVO();
                    consumo.setCdescWallet(rsetOBJ.getString("C_DESC_WALLET"));
                    consumo.setCiudadDestino(rsetOBJ.getString("DESTINO"));
                    consumo.setDescTipoTrafico(rsetOBJ.getString("DESC_TIPO_TRAFICO"));
                    consumo.setDurMinRedn(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setHoraEvento(rsetOBJ.getString("HORA_EVENTO"));
                    consumo.setMontoFree(rsetOBJ.getString("MONTOFREE"));
                    consumo.setMontoReal(rsetOBJ.getString("MONTOREAL"));
                    consumo.setNumDestino(rsetOBJ.getString("NUM_DESTINO"));
                    consumo.setNumOrigen(rsetOBJ.getString("NUM_ORIGEN"));
                    consumo.setOperadorDestino(rsetOBJ.getString("OPERADOR_DESTINO"));
                    consumo.setOperadorOrigen(rsetOBJ.getString("OPERADOR_ORIGEN"));
                    consumo.setSaldoAntes(rsetOBJ.getString("N_SALDO_ANTES"));
                    consumo.setSaldoDespues(rsetOBJ.getString("N_SALDO_DESPUES"));
                    consumo.setTotalNavegacion("");
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    consumoFechaList.add(consumoFecha);
                }
                

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        public List<ConsumoFechaVO> getDatos(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s C o n s u m o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            ConsumoDetalleVO consumo = new ConsumoDetalleVO();
            List<ConsumoDetalleVO> consumoList = new ArrayList<ConsumoDetalleVO>();
            ConsumoFechaVO consumoFecha = new ConsumoFechaVO();
            List<ConsumoFechaVO> consumoFechaList = new ArrayList<ConsumoFechaVO>();
            
            try{
                
                int varTimeOut = 20; 

                try{
                   varTimeOut = Integer.parseInt(getValorParametro(118));
                }catch (Exception e) {
                    varTimeOut = 20;
                }
                
                commSQL = "{ ? =  call CLIENREPUNI.PQBICCONSUMOS.FNGETDETALLEDATOSBIC(?,?,?)}";
                commPRN = "{ ? =  call CLIENREPUNI.PQBICCONSUMOS.FNGETDETALLEDATOSBIC("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                
                for(int cont = 0; cont < intentos; cont++)
                {
                    try
                    {
                        connOBJ = OracleConnection.getConnectionConsumos(new MensajeLogBean());
                        Logger.write("     Conexion BD Abierta    : true");

                        if(connOBJ != null) 
                            stmsOBJ = connOBJ.prepareCall(commSQL);

                        stmsOBJ.setQueryTimeout(varTimeOut);

                        Logger.write("     Conexion BD Abierta    : true");
                        i = 1;
                        stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                        stmsOBJ.setString(i++, dn);
                        stmsOBJ.setString(i++, fechaIni);
                        stmsOBJ.setString(i++, fechaFin);



                        Logger.write("     Ejecutando consulta    intento[" + (cont+1) +"] :");
                        timeIni = System.currentTimeMillis();
                        stmsOBJ.execute();  
                        rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                        break;
                    }
                    catch (Exception e) 
                    {
                        boolean reintentar = Utilerias.contieneMensajeError(e.getLocalizedMessage());                       
                        if(cont < (intentos-1) && reintentar)
                        {
                            Logger.write("     [WARN]  : (SQLException) " + e.getLocalizedMessage());
                            if(connOBJ != null) try {
                                connOBJ.close();    
                                Logger.write("     Conexion BD Abierta    : false");
                            }catch (Exception e1) {
                                Logger.write("     Detail  : (SQLException) " + e1.getMessage());   
                            }
                            continue;
                        }
                        else
                        {
                            throw new ServiceException(e.getLocalizedMessage());
                        }
                    }                   
                }
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleVO();
                    consumoList = new ArrayList<ConsumoDetalleVO>();
                    consumoFecha = new ConsumoFechaVO();
                    consumo.setCdescWallet(rsetOBJ.getString("C_DESC_WALLET"));
                    consumo.setCiudadDestino(rsetOBJ.getString("DESTINO"));
                    consumo.setDescTipoTrafico(rsetOBJ.getString("DESC_TIPO_TRAFICO"));
                    consumo.setDurMinRedn(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setHoraEvento(rsetOBJ.getString("HORA_EVENTO"));
                    consumo.setMontoFree(rsetOBJ.getString("MONTOFREE"));
                    consumo.setMontoReal(rsetOBJ.getString("MONTOREAL"));
                    consumo.setNumDestino(rsetOBJ.getString("NUM_DESTINO"));
                    consumo.setNumOrigen(rsetOBJ.getString("NUM_ORIGEN"));
                    consumo.setOperadorDestino(rsetOBJ.getString("OPERADOR_DESTINO"));
                    consumo.setOperadorOrigen(rsetOBJ.getString("OPERADOR_ORIGEN"));
                    consumo.setSaldoAntes(rsetOBJ.getString("N_SALDO_ANTES"));
                    consumo.setSaldoDespues(rsetOBJ.getString("N_SALDO_DESPUES"));
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    consumoFechaList.add(consumoFecha);
                }
                

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        public List<ConsumoFechaTablaVO> getDetalles(final String dn, final String fechaIni, final String fechaFin, final String tipoEvento) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s C o n s u m o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            String numero = "";
            String telefono = "";
            String textoFormateado = "";
            String operador = "";
            ConsumoDetalleTablaVO consumo = new ConsumoDetalleTablaVO();
            List<ConsumoDetalleTablaVO> consumoList = new ArrayList<ConsumoDetalleTablaVO>();
            ConsumoFechaTablaVO consumoFecha = new ConsumoFechaTablaVO();
            List<ConsumoFechaTablaVO> consumoFechaList = new ArrayList<ConsumoFechaTablaVO>();
            SimpleDateFormat dateFormat = null;
            Date dateToHrs = new Date();
            try{

                int varTimeOut = 20; 

                try{
                    varTimeOut = Integer.parseInt(getValorParametro(118));
                }catch (Exception e) {
                    varTimeOut = 20;
                }

                dateFormat = new SimpleDateFormat(getValorParametro(101));

                commSQL = "{ ? =  call CLIENREPUNI.PQBICCONSUMOS.FNGETDETALLEGENERALBIC(?,?,?,?)}";
                commPRN = "{ ? =  call CLIENREPUNI.PQBICCONSUMOS.FNGETDETALLEGENERALBIC("+dn+","+fechaIni+
                ","+fechaFin+","+tipoEvento+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                for(int cont = 0; cont < intentos; cont++)
                {
                    try
                    {
                        connOBJ = OracleConnection.getConnectionConsumos(new MensajeLogBean());
                        Logger.write("     Conexion BD Abierta    : true");

                        if(connOBJ != null) 
                            stmsOBJ = connOBJ.prepareCall(commSQL);

                        stmsOBJ.setQueryTimeout(varTimeOut);
                        //String nuevoDN = "\'" + dn + "\'";
                        Logger.write("     Conexion BD Abierta    : true");
                        i = 1;
                        stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                        stmsOBJ.setString(i++, dn);
                        stmsOBJ.setString(i++, fechaIni);
                        stmsOBJ.setString(i++, fechaFin);
                        if(tipoEvento.equals("5") || tipoEvento.equals("6")){
                            stmsOBJ.setString(i++, "1");
                        }else{
                            stmsOBJ.setString(i++, tipoEvento); 
                        }

                        Logger.write("     Ejecutando consulta    intento[" + (cont+1) +"] :");
                        timeIni = System.currentTimeMillis();
                        stmsOBJ.execute();
                        rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                    }
                    catch (Exception e) 
                    {
                        boolean reintentar = Utilerias.contieneMensajeError(e.getLocalizedMessage());                       
                        if(cont < (intentos-1) && reintentar)
                        {
                            Logger.write("     [WARN]  : (SQLException) " + e.getLocalizedMessage());
                            if(connOBJ != null) try {
                                connOBJ.close();    
                                Logger.write("     Conexion BD Abierta    : false");
                            }catch (Exception e1) {
                                Logger.write("     Detail  : (SQLException) " + e1.getMessage());   
                            }
                            continue;
                        }
                        else
                        {
                            throw new ServiceException(e.getLocalizedMessage());
                        }
                    }                   
                }
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleTablaVO();
                    consumoList = new ArrayList<ConsumoDetalleTablaVO>();
                    consumoFecha = new ConsumoFechaTablaVO();
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    try{
                        if(rsetOBJ.getString("OPERADOR_ORIGEN") != null){
                            operador = rsetOBJ.getString("OPERADOR_ORIGEN");
                        }
                    }catch (Exception e)
                    {operador = "";}
                    textoFormateado = rsetOBJ.getString("OPERADOR_DESTINO");
                    if(textoFormateado != null && textoFormateado.toLowerCase().trim().equals("no identificado"))
                        consumo.setCompania("ND");
                    else
                        consumo.setCompania(Utilerias.formatoCadena(textoFormateado));
                    consumo.setCosto("0");
                    consumo.setDestino(Utilerias.formatoCadena(rsetOBJ.getString("DESTINO")));
                    if(rsetOBJ.getString("HORA_EVENTO") != null && !rsetOBJ.getString("HORA_EVENTO").trim().equals("")){
                        dateToHrs = dateFormat.parse(rsetOBJ.getString("HORA_EVENTO"));
                        consumo.setHora(new SimpleDateFormat("HH:mm").format(dateToHrs));
                    }
                    consumo.setIdColumna(1);
                    
                    numero = rsetOBJ.getString("NUM_DESTINO");
                    if(numero != null && numero.length() >= 10){
                        telefono = numero.substring(numero.length()-10,numero.length());
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }else{
                        telefono = numero;
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }
                    consumo.setNumero(telefono);
                    consumo.setTiempo(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setMbConsumidos(rsetOBJ.getString("DUR_MIN_REDN"));
                    
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    
                    if(tipoEvento.equals("5") || tipoEvento.equals("6")){
                        if(tipoEvento.equals("5") &&  (consumo.getCompania().trim().equals("Iusacell") || consumo.getCompania().trim().equals("Unefon"))){
                            consumoFechaList.add(consumoFecha);
                        }
                        else if(tipoEvento.equals("6") &&  (!consumo.getCompania().trim().equals("Iusacell") && !consumo.getCompania().trim().equals("Unefon"))){
                            consumoFechaList.add(consumoFecha);
                        }
                    }else{
                        if(tipoEvento.equals("4") && operador.trim().toLowerCase().equals("no identificado")){
                            consumoFecha = new ConsumoFechaTablaVO();
                        }else{
                            consumoFechaList.add(consumoFecha);
                        }
                    }
                }
                

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        /**
         * Consumos Fin
         */
        
        
        /**
         * Consumos Ini Local
         */
        
        public List<ConsumoFechaVO> getLlamadasLocal(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            ConsumoDetalleVO consumo = new ConsumoDetalleVO();
            List<ConsumoDetalleVO> consumoList = new ArrayList<ConsumoDetalleVO>();
            ConsumoFechaVO consumoFecha = new ConsumoFechaVO();
            List<ConsumoFechaVO> consumoFechaList = new ArrayList<ConsumoFechaVO>();
            
            try{
                commSQL = "{ ? =  call MIIUSACELL.PQBICCONSUMOS.FNGETDETALLEVOZBIC(?,?,?)}";
                commPRN = "{ ? =  call MIIUSACELL.PQBICCONSUMOS.FNGETDETALLEVOZBIC("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                
                if(connOBJ != null) 
                stmsOBJ = connOBJ.prepareCall(commSQL);

                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, fechaIni);
                stmsOBJ.setString(i++, fechaFin);
                
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleVO();
                    consumoList = new ArrayList<ConsumoDetalleVO>();
                    consumoFecha = new ConsumoFechaVO();
                    consumo.setCdescWallet(rsetOBJ.getString("C_DESC_WALLET"));
                    consumo.setCiudadDestino(rsetOBJ.getString("DESTINO"));
                    consumo.setDescTipoTrafico(rsetOBJ.getString("DESC_TIPO_TRAFICO"));
                    consumo.setDurMinRedn(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setHoraEvento(rsetOBJ.getString("HORA_EVENTO"));
                    consumo.setMontoFree(rsetOBJ.getString("MONTOFREE"));
                    consumo.setMontoReal(rsetOBJ.getString("MONTOREAL"));
                    consumo.setNumDestino(rsetOBJ.getString("NUM_DESTINO"));
                    consumo.setNumOrigen(rsetOBJ.getString("NUM_ORIGEN"));
                    consumo.setOperadorDestino(rsetOBJ.getString("OPERADOR_DESTINO"));
                    consumo.setOperadorOrigen(rsetOBJ.getString("OPERADOR_ORIGEN"));
                    consumo.setSaldoAntes(rsetOBJ.getString("N_SALDO_ANTES"));
                    consumo.setSaldoDespues(rsetOBJ.getString("N_SALDO_DESPUES"));
                    consumo.setTotalNavegacion("");
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    consumoFechaList.add(consumoFecha);
                }
                

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        public List<ConsumoFechaVO> getMensajesLocal(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            ConsumoDetalleVO consumo = new ConsumoDetalleVO();
            List<ConsumoDetalleVO> consumoList = new ArrayList<ConsumoDetalleVO>();
            ConsumoFechaVO consumoFecha = new ConsumoFechaVO();
            List<ConsumoFechaVO> consumoFechaList = new ArrayList<ConsumoFechaVO>();
            
            try{
                commSQL = "{ ? =  call MIIUSACELL.PQBICCONSUMOS.FNGETDETALLESMSBIC(?,?,?)}";
                commPRN = "{ ? =  call MIIUSACELL.PQBICCONSUMOS.FNGETDETALLESMSBIC("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                
                if(connOBJ != null) 
                stmsOBJ = connOBJ.prepareCall(commSQL);

                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, fechaIni);
                stmsOBJ.setString(i++, fechaFin);
                
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleVO();
                    consumoList = new ArrayList<ConsumoDetalleVO>();
                    consumoFecha = new ConsumoFechaVO();
                    consumo.setCdescWallet(rsetOBJ.getString("C_DESC_WALLET"));
                    consumo.setCiudadDestino(rsetOBJ.getString("DESTINO"));
                    consumo.setDescTipoTrafico(rsetOBJ.getString("DESC_TIPO_TRAFICO"));
                    consumo.setDurMinRedn(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setHoraEvento(rsetOBJ.getString("HORA_EVENTO"));
                    consumo.setMontoFree(rsetOBJ.getString("MONTOFREE"));
                    consumo.setMontoReal(rsetOBJ.getString("MONTOREAL"));
                    consumo.setNumDestino(rsetOBJ.getString("NUM_DESTINO"));
                    consumo.setNumOrigen(rsetOBJ.getString("NUM_ORIGEN"));
                    consumo.setOperadorDestino(rsetOBJ.getString("OPERADOR_DESTINO"));
                    consumo.setOperadorOrigen(rsetOBJ.getString("OPERADOR_ORIGEN"));
                    consumo.setSaldoAntes(rsetOBJ.getString("N_SALDO_ANTES"));
                    consumo.setSaldoDespues(rsetOBJ.getString("N_SALDO_DESPUES"));
                    consumo.setTotalNavegacion("");
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    consumoFechaList.add(consumoFecha);
                }
                

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        public List<ConsumoFechaVO> getDatosLocal(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            ConsumoDetalleVO consumo = new ConsumoDetalleVO();
            List<ConsumoDetalleVO> consumoList = new ArrayList<ConsumoDetalleVO>();
            ConsumoFechaVO consumoFecha = new ConsumoFechaVO();
            List<ConsumoFechaVO> consumoFechaList = new ArrayList<ConsumoFechaVO>();
            
            try{
                commSQL = "{ ? =  call MIIUSACELL.PQBICCONSUMOS.FNGETDETALLEDATOSBIC(?,?,?)}";
                commPRN = "{ ? =  call MIIUSACELL.PQBICCONSUMOS.FNGETDETALLEDATOSBIC("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                
                if(connOBJ != null) 
                stmsOBJ = connOBJ.prepareCall(commSQL);

                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, fechaIni);
                stmsOBJ.setString(i++, fechaFin);
                
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleVO();
                    consumoList = new ArrayList<ConsumoDetalleVO>();
                    consumoFecha = new ConsumoFechaVO();
                    consumo.setCdescWallet(rsetOBJ.getString("C_DESC_WALLET"));
                    consumo.setCiudadDestino(rsetOBJ.getString("DESTINO"));
                    consumo.setDescTipoTrafico(rsetOBJ.getString("DESC_TIPO_TRAFICO"));
                    consumo.setDurMinRedn(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setHoraEvento(rsetOBJ.getString("HORA_EVENTO"));
                    consumo.setMontoFree(rsetOBJ.getString("MONTOFREE"));
                    consumo.setMontoReal(rsetOBJ.getString("MONTOREAL"));
                    consumo.setNumDestino(rsetOBJ.getString("NUM_DESTINO"));
                    consumo.setNumOrigen(rsetOBJ.getString("NUM_ORIGEN"));
                    consumo.setOperadorDestino(rsetOBJ.getString("OPERADOR_DESTINO"));
                    consumo.setOperadorOrigen(rsetOBJ.getString("OPERADOR_ORIGEN"));
                    consumo.setSaldoAntes(rsetOBJ.getString("N_SALDO_ANTES"));
                    consumo.setSaldoDespues(rsetOBJ.getString("N_SALDO_DESPUES"));
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    consumoFechaList.add(consumoFecha);
                }
                

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        public List<ConsumoFechaTablaVO> getDetallesLocal(final String dn, final String fechaIni, final String fechaFin, final String tipoEvento) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            String numero = "";
            String telefono = "";
            String textoFormateado = "";
            String operador = "";
            ConsumoDetalleTablaVO consumo = new ConsumoDetalleTablaVO();
            List<ConsumoDetalleTablaVO> consumoList = new ArrayList<ConsumoDetalleTablaVO>();
            ConsumoFechaTablaVO consumoFecha = new ConsumoFechaTablaVO();
            List<ConsumoFechaTablaVO> consumoFechaList = new ArrayList<ConsumoFechaTablaVO>();
            SimpleDateFormat dateFormat = null;
            Date dateToHrs = new Date();
            try{
                dateFormat = new SimpleDateFormat(getValorParametro(101));
                
                commSQL = "{ ? =  call MIIUSACELL.PQBICCONSUMOS.FNGETDETALLEGENERALBIC(?,?,?,?)}";
                commPRN = "{ ? =  call MIIUSACELL.PQBICCONSUMOS.FNGETDETALLEGENERALBIC("+dn+","+fechaIni+
                ","+fechaFin+","+tipoEvento+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                
                if(connOBJ != null) 
                stmsOBJ = connOBJ.prepareCall(commSQL);

                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, fechaIni);
                stmsOBJ.setString(i++, fechaFin);
                if(tipoEvento.equals("5") || tipoEvento.equals("6")){
                    stmsOBJ.setString(i++, "1");
                }else{
                    stmsOBJ.setString(i++, tipoEvento); 
                }
                
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleTablaVO();
                    consumoList = new ArrayList<ConsumoDetalleTablaVO>();
                    consumoFecha = new ConsumoFechaTablaVO();
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    try{
                        if(rsetOBJ.getString("OPERADOR_ORIGEN") != null){
                            operador = rsetOBJ.getString("OPERADOR_ORIGEN");
                        }
                    }catch (Exception e)
                    {operador = "";}
                    textoFormateado = rsetOBJ.getString("OPERADOR_DESTINO");
                    if(textoFormateado != null && textoFormateado.toLowerCase().trim().equals("no identificado"))
                        consumo.setCompania("ND");
                    else
                        consumo.setCompania(Utilerias.formatoCadena(textoFormateado));
                    consumo.setCosto("0");
                    consumo.setDestino(Utilerias.formatoCadena(rsetOBJ.getString("DESTINO")));
                    if(rsetOBJ.getString("HORA_EVENTO") != null && !rsetOBJ.getString("HORA_EVENTO").trim().equals("")){
                        dateToHrs = dateFormat.parse(rsetOBJ.getString("HORA_EVENTO"));
                        consumo.setHora(new SimpleDateFormat("HH:mm").format(dateToHrs));
                    }
                    consumo.setIdColumna(1);
                    
                    numero = rsetOBJ.getString("NUM_DESTINO");
                    if(numero != null && numero.length() >= 10){
                        telefono = numero.substring(numero.length()-10,numero.length());
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }else{
                        telefono = numero;
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }
                    consumo.setNumero(telefono);
                    consumo.setTiempo(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setMbConsumidos(rsetOBJ.getString("DUR_MIN_REDN"));
                    
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    
                    if(tipoEvento.equals("5") || tipoEvento.equals("6")){
                        if(tipoEvento.equals("5") &&  (consumo.getCompania().trim().equals("Iusacell") || consumo.getCompania().trim().equals("Unefon"))){
                            consumoFechaList.add(consumoFecha);
                        }
                        else if(tipoEvento.equals("6") &&  (!consumo.getCompania().trim().equals("Iusacell") && !consumo.getCompania().trim().equals("Unefon"))){
                            consumoFechaList.add(consumoFecha);
                        }
                    }else{
                        if(tipoEvento.equals("4") && operador.trim().toLowerCase().equals("no identificado")){
                            consumoFecha = new ConsumoFechaTablaVO();
                        }else{
                            consumoFechaList.add(consumoFecha);
                        }
                    }
                }
                

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        /**
         * Consumos Fin Local
         */
        
        /**
         * Bitacora Ini 
         */
        // Oracle
        public int loginSET(final String pfcdn, final String pfcrespuesta, final String pfcdetalle, final int pficvetipodispositivo, final int pfisistemaorigen, final int pfiidentificador, final int pfiusuarioid) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;

            long timeIni = 0;
            int i = 1; 

            try{
                commSQL = "{ ? = call MIIUSACELL.PAMIIGETLOGIN_RL.FNSETMIILOG (?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call MIIUSACELL.PAMIIGETLOGIN_RL.FNSETMIILOG ("
                    + pfcdn+","+pfcrespuesta+","+pfcdetalle+","+pficvetipodispositivo+","+pfisistemaorigen+","+pfiidentificador+","+pfiusuarioid+")}"; 

                Logger.write("     Operacion              : loginSET");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     pfcdn                  : " + pfcdn);
                Logger.write("     pfcrespuesta           : " + pfcrespuesta);
                Logger.write("     pfcdetalle             : " + pfcdetalle);
                Logger.write("     pficvetipodispositivo  : " + pficvetipodispositivo);
                Logger.write("     pfisistemaorigen       : " + pfisistemaorigen);
                Logger.write("     pfiidentificador       : " + pfiidentificador);
                Logger.write("     pfiusuarioid           : " + pfiusuarioid);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());

                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, pfcdn);
                    stmsOBJ.setString(i++, pfcrespuesta);
                    stmsOBJ.setString(i++, pfcdetalle);
                    stmsOBJ.setInt(i++, pficvetipodispositivo);
                    stmsOBJ.setInt(i++, pfisistemaorigen);
                    stmsOBJ.setInt(i++, pfiidentificador);
                    stmsOBJ.setInt(i++, pfiusuarioid);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    response = stmsOBJ.getInt(1);

                    Logger.write("     Respuesta              : " + response);
                }
            }catch(SQLException e){
                Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));

            return response;
        }
        
        public String contratarServiciosSET(final String pfcuser, final String pfcpass, final String pfcdn, final String pfccosto, final String pfcid, final int pfiidhistorico, final int pfivigencia, final String pfcmonto, final String pfcdnusa, 
                final String pfccontinentefavortio, final String pfcservicios, final String pfcserviciosid, final String pfcservicioorigen, final String pfcvigenciasunidad, final String pfcvigenciascantidad,
                final String pfcoperacion, final int pficompania, final int pfisistemaorigen, final String pfctoken, final int pfiusuarioid, final int pficvetipolinea , final int pficvetipotecnologia, final int pficvetipodispositivo) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            String response= "";

            long timeIni = 0;
            int i = 1; 

            try{
                commSQL = "{ ? = call MIIUSACELL.PAMIICONTRATASERVICIOSET.FNINSCONTRATASERVICIO (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call MIIUSACELL.PAMIICONTRATASERVICIOSET.FNINSCONTRATASERVICIO ("
                    + pfcuser +","+pfcpass+","+pfcdn+","+pfccosto+","+pfcid+","+pfiidhistorico+","+pfivigencia+","+pfcmonto+","+pfcdnusa+"," 
                    + pfccontinentefavortio+","+pfcservicios+","+pfcserviciosid+","+pfcservicioorigen+","+pfcvigenciasunidad+","+pfcvigenciascantidad+","
                    + pfcoperacion+","+pficompania+","+pfisistemaorigen+","+ pfctoken+"," + pfiusuarioid +","+ pficvetipolinea +","+ pficvetipotecnologia +","+ pficvetipodispositivo +")}";

                Logger.write("     Operacion              : contratarServiciosSET");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     pfcdn                   : " + pfcdn);
                Logger.write("     pfccosto                : " + pfccosto);
                Logger.write("     pfccosto                : " + pfccosto);
                Logger.write("     pfiidhistorico          : " + pfiidhistorico);
                Logger.write("     pfivigencia             : " + pfivigencia);
                Logger.write("     pfcmonto                : " + pfcmonto);
                Logger.write("     pfcdnusa                : " + pfcdnusa);
                Logger.write("     pfccontinentefavortio   : " + pfccontinentefavortio);
                Logger.write("     pfcservicios            : " + pfcservicios);
                Logger.write("     pfcserviciosid          : " + pfcserviciosid);
                Logger.write("     pfcservicioorigen       : " + pfcservicioorigen);
                Logger.write("     pfcvigenciasunidad      : " + pfcvigenciasunidad);
                Logger.write("     pfcvigenciascantidad    : " + pfcvigenciascantidad);
                Logger.write("     pfcoperacion            : " + pfcoperacion);
                Logger.write("     pficompania             : " + pficompania);
                Logger.write("     pfisistemaorigen        : " + pfisistemaorigen);
                Logger.write("     pfiusuarioid            : " + pfiusuarioid);
                Logger.write("     pficvetipolinea         : " + pficvetipolinea);
                Logger.write("     pficvetipotecnologia    : " + pficvetipotecnologia);
                Logger.write("     pficvetipodispositivo   : " + pficvetipodispositivo);
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());

                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.VARCHAR);
                    stmsOBJ.setString(i++, pfcuser);
                    stmsOBJ.setString(i++, pfcpass);
                    stmsOBJ.setString(i++, pfcdn);
                    stmsOBJ.setString(i++, pfccosto);
                    stmsOBJ.setString(i++, pfcid);
                    stmsOBJ.setInt(i++, pfiidhistorico);
                    stmsOBJ.setInt(i++, pfivigencia);
                    stmsOBJ.setString(i++, pfcmonto);
                    stmsOBJ.setString(i++, pfcdnusa);
                    stmsOBJ.setString(i++, pfccontinentefavortio);
                    stmsOBJ.setString(i++, pfcservicios);
                    stmsOBJ.setString(i++, pfcserviciosid);
                    stmsOBJ.setString(i++, pfcservicioorigen);
                    stmsOBJ.setString(i++, pfcvigenciasunidad);
                    stmsOBJ.setString(i++, pfcvigenciascantidad);
                    stmsOBJ.setString(i++, pfcoperacion);
                    stmsOBJ.setInt(i++, pficompania);
                    stmsOBJ.setInt(i++, pfisistemaorigen);
                    stmsOBJ.setString(i++, pfctoken);
                    stmsOBJ.setInt(i++, pfiusuarioid);
                    stmsOBJ.setInt(i++, pficvetipolinea);
                    stmsOBJ.setInt(i++, pficvetipotecnologia);
                    stmsOBJ.setInt(i++, pficvetipodispositivo);
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    response = stmsOBJ.getString(1);
                    
                    Logger.write("   + Respuesta              + " + response);
                }
            }catch(SQLException e){
                Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));

            return response;
        }
        
        public int compraServicioEtakSET(final String pfilogid, final String pfcdn, final int pfiidoperador, final String pfccosto, final String pfcid, final int pfiidhistorico, final int pfivigencia, final String pfcmonto, final int pfiusuarioid) throws ServiceException {
                Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
                Connection connOBJ = null;
                CallableStatement stmsOBJ = null;
                String commSQL = "";
                String commPRN = "";
                int response= -1;
                
                long timeIni = 0;
                int i = 1; 
                
                try{
                commSQL = "{ ? = call MIIUSACELL.PAMIICONTRATASERVICIOSET.FNINSCOMPRASERVICIOETAK (?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call MIIUSACELL.PAMIICONTRATASERVICIOSET.FNINSCOMPRASERVICIOETAK ("
                +pfilogid+","+pfcdn+","+pfiidoperador+","+pfccosto+","+pfcid+","+pfiidhistorico+","+pfivigencia+","+pfcmonto+","+pfiusuarioid+")}"; 
                
                Logger.write("     Operacion              : compraServicioEtakSET");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     pfilogid               : " + pfilogid);
                Logger.write("     pfcdn                  : " + pfcdn);
                Logger.write("     pfiidoperador          : " + pfiidoperador);
                Logger.write("     pfccosto               : " + pfccosto);
                Logger.write("     pfcid                  : " + pfcid);
                Logger.write("     pfiidhistorico         : " + pfiidhistorico);
                Logger.write("     pfivigencia            : " + pfivigencia);
                Logger.write("     pfcmonto               : " + pfcmonto);
                Logger.write("     pfiusuarioid           : " + pfiusuarioid);
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
                
                if(stmsOBJ != null){
                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                stmsOBJ.setString(i++, pfilogid);
                stmsOBJ.setString(i++, pfcdn);
                stmsOBJ.setInt(i++, pfiidoperador);
                stmsOBJ.setString(i++, pfccosto);
                stmsOBJ.setString(i++, pfcid);
                stmsOBJ.setInt(i++, pfiidhistorico);
                stmsOBJ.setInt(i++, pfivigencia);
                stmsOBJ.setString(i++, pfcmonto);
                stmsOBJ.setInt(i++, pfiusuarioid);
                
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();

                response = stmsOBJ.getInt(1);
                
                }
                }catch(SQLException e){
                Logger.write("     Detail    : (Exception) " + e.getMessage());     
                }finally{
                if (stmsOBJ != null) try {
                stmsOBJ.close();
                }
                catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                try {
                connOBJ.close();
                }
                catch (SQLException e) {
                Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                }
                Logger.write("     Conexion BD Abierta    : false");     
                }
                Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
                
                return response;
        }
        
        public int compraServicioLEGACYSET(final String pfilogid, final String pfccoid, final String pfcdnusa, final String pfccontinentefavorito, final String pfcservicios, final String pfctmcode, final String pfctype, final String pfcusermod, final int pfiusuarioid) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICONTRATASERVICIOSET.FNINSALTABAJASERVICIOSLEGACY (?,?,?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICONTRATASERVICIOSET.FNINSALTABAJASERVICIOSLEGACY ("
                    + pfilogid+","+pfccoid+","+pfcdnusa+","+pfccontinentefavorito+","+pfcservicios+","+pfctmcode+","+pfctype+","+pfcusermod+","+pfiusuarioid+")}"; 
            
            Logger.write("     Operacion               : compraServicioLEGACYSET");
            Logger.write("   + Parametros              +");
            Logger.write("     commPRN                 : " + commPRN);
            Logger.write("     pfilogid                : " + pfilogid);
            Logger.write("     pfccoid                 : " + pfccoid);
            Logger.write("     pfcdnusa                : " + pfcdnusa);
            Logger.write("     pfccontinentefavorito   : " + pfccontinentefavorito);
            Logger.write("     pfcservicios            : " + pfcservicios);
            Logger.write("     pfctmcode               : " + pfctmcode);
            Logger.write("     pfctype                 : " + pfctype);
            Logger.write("     pfcusermod              : " + pfcusermod);
            Logger.write("     pfiusuarioid            : " + pfiusuarioid);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfccoid);
            stmsOBJ.setString(i++, pfcdnusa);
            stmsOBJ.setString(i++, pfccontinentefavorito);
            stmsOBJ.setString(i++, pfcservicios);
            stmsOBJ.setString(i++, pfctmcode);
            stmsOBJ.setString(i++, pfctype);
            stmsOBJ.setString(i++, pfcusermod);
            stmsOBJ.setInt(i++, pfiusuarioid);
            
            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }
        
        public int altaLEGACYSET(final String pfilogid, final String pfclineaid, final String pfcserviciosid, final String pfcservicioorigen, final String pfcvigenciasunidad, final String pfcvigenciascantidad, final String pfcoperacion, final int pfiusuarioid) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICONTRATASERVICIOSET.FNINSALTALEGACY (?,?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICONTRATASERVICIOSET.FNINSALTALEGACY ("
                + pfilogid+","+pfclineaid+","+pfcserviciosid+","+pfcservicioorigen+","+pfcvigenciasunidad+","+pfcvigenciascantidad+","+pfcoperacion+","+pfiusuarioid+")}"; 
            
            Logger.write("     Operacion               : compraServicioLEGACYSET");
            Logger.write("   + Parametros              +");
            Logger.write("     commPRN                 : " + commPRN);
            Logger.write("     pfilogid                : " + pfilogid);
            Logger.write("     pfclineaid              : " + pfclineaid);
            Logger.write("     pfcserviciosid          : " + pfcserviciosid);
            Logger.write("     pfcservicioorigen       : " + pfcservicioorigen);
            Logger.write("     pfcvigenciasunidad      : " + pfcvigenciasunidad);
            Logger.write("     pfcvigenciascantidad    : " + pfcvigenciascantidad);
            Logger.write("     pfcoperacion            : " + pfcoperacion);
            Logger.write("     pfiusuarioid            : " + pfiusuarioid);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfclineaid);
            stmsOBJ.setString(i++, pfcserviciosid);
            stmsOBJ.setString(i++, pfcservicioorigen);
            stmsOBJ.setString(i++, pfcvigenciasunidad);
            stmsOBJ.setString(i++, pfcvigenciascantidad);
            stmsOBJ.setString(i++, pfcoperacion);
            stmsOBJ.setInt(i++, pfiusuarioid);
            
            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }
        
      public int compraServicioRespuesta(final String pfilogid, final int pfiresid, final String pfcmessagecode, final String pfcoperationcode, final int pfiusuarioid, final String pfccdgerror, final String pfcmsjerror) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICONTRATASERVICIOSET.FNINSCOMPRASERVICIORESPUESTA (?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICONTRATASERVICIOSET.FNINSCOMPRASERVICIORESPUESTA ("
                + pfilogid+","+pfiresid+","+pfcmessagecode+","+pfcoperationcode+","+pfiusuarioid+","+pfccdgerror+","+pfcmsjerror+")}"; 
            
            Logger.write("     Operacion            : compraServicioRespuesta");
            Logger.write("   + Parametros           +");
            Logger.write("     commPRN              : " + commPRN);
            Logger.write("     pfilogid             : " + pfilogid);
            Logger.write("     pfiresid             : " + pfiresid);
            Logger.write("     pfcmessagecode       : " + pfcmessagecode);
            Logger.write("     pfcoperationcode     : " + pfcoperationcode);
            Logger.write("     pfiusuarioid         : " + pfiusuarioid);
            Logger.write("     pfccdger_ror         : " + pfccdgerror);
            Logger.write("     pfcmsjer_ror         : " + pfcmsjerror);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setInt(i++, pfiresid);
            stmsOBJ.setString(i++, pfcmessagecode);
            stmsOBJ.setString(i++, pfcoperationcode);
            stmsOBJ.setInt(i++, pfiusuarioid);
            stmsOBJ.setString(i++, pfccdgerror);
            stmsOBJ.setString(i++, pfcmsjerror);
            
            
            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }         
      
      
      public String suspensionReactivacionSET(final String pfcuser, final String pfcpass, final String pfcdn, final String pfcreason, final int pfitipo, final int pficompania, final int pfisistemaorigen, final String pfctoken, final int pfiusuarioid, final int pficvetipolinea , final int pficvetipotecnologia, final int pficvetipodispositivo) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            String response= "";
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIISUSPENSIONREACTIVACIONSET.FNINSSUSPENSIONREACTIVACION (?,?,?,?,?,?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIISUSPENSIONREACTIVACIONSET.FNINSSUSPENSIONREACTIVACION ("
                +pfcuser+","+pfcpass+","+pfcdn+","+pfcreason+","+pfitipo+","+pficompania+","+pfisistemaorigen+","+pfctoken+","+pfiusuarioid+","+ pficvetipolinea +","+ pficvetipotecnologia +","+ pficvetipodispositivo +")}"; 
            
            Logger.write("     Operacion            : suspensionReactivacionSET");
            Logger.write("   + Parametros           +");
            Logger.write("     commPRN              : " + commPRN);
            Logger.write("     pfcuser             : " + pfcuser);
            Logger.write("     pfcpass             : " + pfcpass);
            Logger.write("     pfcdn               : " + pfcdn);
            Logger.write("     pfcreason           : " + pfcreason);
            Logger.write("     pfitipo             : " + pfitipo);
            Logger.write("     pficompania         : " + pficompania);
            Logger.write("     pfisistemaorigen    : " + pfisistemaorigen);
            Logger.write("     pfctoken            : " + pfctoken);
            Logger.write("     pfiusuarioid        : " + pfiusuarioid);
            Logger.write("     tipo linea          : " + pficvetipolinea);
            Logger.write("     tipo tecnologia     : " + pficvetipotecnologia);
            Logger.write("     tipo dispositivo    : " + pficvetipodispositivo);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.VARCHAR);
            stmsOBJ.setString(i++, pfcuser);
            stmsOBJ.setString(i++, pfcpass);
            stmsOBJ.setString(i++, pfcdn);
            stmsOBJ.setString(i++, pfcreason);
            stmsOBJ.setInt(i++, pfitipo);
            stmsOBJ.setInt(i++, pficompania);
            stmsOBJ.setInt(i++, pfisistemaorigen);
            stmsOBJ.setString(i++, pfctoken);
            stmsOBJ.setInt(i++, pfiusuarioid);
            stmsOBJ.setInt(i++, pficvetipolinea);
            stmsOBJ.setInt(i++, pficvetipotecnologia);
            stmsOBJ.setInt(i++, pficvetipodispositivo);
            

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getString(1);
            
            Logger.write("   + Respuesta              +" + response );
                
            
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }         

      
      public int suspensionSET(final String pfilogid, final String pfcdn, final String pfcreason, final int pfiusuarioid) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIISUSPENSIONREACTIVACIONSET.FNINSDNSUSPENSION (?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIISUSPENSIONREACTIVACIONSET.FNINSDNSUSPENSION ("
                + pfilogid+"," + pfcdn+"," + pfcreason+"," + pfiusuarioid+")}"; 
            
            Logger.write("     Operacion            : suspensionSET");
            Logger.write("   + Parametros           +");
            Logger.write("     commPRN              : " + commPRN);
            Logger.write("     pfcuser             : " + pfilogid);
            Logger.write("     pfcdn               : " + pfcdn);
            Logger.write("     pfcreason           : " + pfcreason);
            Logger.write("     pfiusuarioid        : " + pfiusuarioid);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfcdn);
            stmsOBJ.setString(i++, pfcreason);
            stmsOBJ.setInt(i++, pfiusuarioid);

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }
      
      public int reactivacionSET(final String pfilogid, final String pfcdn, final String pfcreason, final int pfiusuarioid) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIISUSPENSIONREACTIVACIONSET.FNINSDNREACTIVACION (?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIISUSPENSIONREACTIVACIONSET.FNINSDNREACTIVACION ("
                + pfilogid+"," + pfcdn+"," + pfcreason+"," + pfiusuarioid+")}"; 
            Logger.write("     Operacion              : reactivacionSET");
            Logger.write("   + Parametros             + ");
            Logger.write("     commPRN                : " + commPRN);
            Logger.write("     pfilogid               : " + pfilogid);
            Logger.write("     pfcdn                  : " + pfcdn);
            Logger.write("     pfcreason              : " + pfcreason);
            Logger.write("     pfiusuarioid           : " + pfiusuarioid);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfcdn);
            stmsOBJ.setString(i++, pfcreason);
            stmsOBJ.setInt(i++, pfiusuarioid);

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }         
      
      
      public int cambiaStatusSET(final String pfilogid, final String pfccontractid, final String pfcdesactivaciontype, final String pfcmdn, final String pfcreasonid, final String pfcdatos, final int pfiusuarioid) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIISUSPENSIONREACTIVACIONSET.FNINSCAMBIOSTATUS (?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIISUSPENSIONREACTIVACIONSET.FNINSCAMBIOSTATUS ("
                        +pfilogid+","+pfccontractid+","+ pfcdesactivaciontype+","+ pfcmdn+","+pfcreasonid+","+pfcdatos+","+pfiusuarioid+")}"; 
            Logger.write("     user                   : -PROTEGIDO-");
            Logger.write("     Operacion              : reactivacionSET");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            Logger.write("     pfilogid               : " + pfilogid);
            Logger.write("     pfccontractid          : " + pfccontractid);
            Logger.write("     pfcdesactivaciontype   : " + pfcdesactivaciontype);
            Logger.write("     pfcmdn                 : " + pfcmdn);
            Logger.write("     pfcreasonid            : " + pfcreasonid);
            Logger.write("     pfcdatos               : " + pfcdatos);
            Logger.write("     pfiusuarioid           : " + pfiusuarioid);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfccontractid);
            stmsOBJ.setString(i++, pfcdesactivaciontype);
            stmsOBJ.setString(i++, pfcmdn);
            stmsOBJ.setString(i++, pfcreasonid);
            stmsOBJ.setString(i++, pfcdatos);
            stmsOBJ.setInt(i++, pfiusuarioid);

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }
      
      public int suspensionReactivacionesRES(final String pfilogid, final int pfiresid, final String  poperationcode, final String  pmessagecode,final int  pfiusuarioid,final String pfccdgerror,final String pfcmsjerror) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIISUSPENSIONREACTIVACIONSET.FNINSSUSPENSIONREACTIVACIONRES (?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIISUSPENSIONREACTIVACIONSET.FNINSSUSPENSIONREACTIVACIONRES ("
                + pfilogid+","+pfiresid+","+poperationcode+","+pmessagecode+","+pfiusuarioid+","+pfccdgerror+","+pfcmsjerror+")}"; 
            Logger.write("     user                   : -PROTEGIDO-");
            Logger.write("     Operacion              : reactivacionSET");
            Logger.write("   + Parametros             + ");
            Logger.write("     commPRN                : " + commPRN);
            Logger.write("     pfilogid               : " + pfilogid);
            Logger.write("     pfiresid               : " + pfiresid);
            Logger.write("     poperationcode         : " + poperationcode);
            Logger.write("     pmessagecode           : " + pmessagecode);
            Logger.write("     pfiusuarioid           : " + pfiusuarioid);
            Logger.write("     pfccdger_ror           : " + pfccdgerror);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setInt(i++, pfiresid);
            stmsOBJ.setString(i++, poperationcode);
            stmsOBJ.setString(i++, pmessagecode);
            stmsOBJ.setInt(i++, pfiusuarioid);
            stmsOBJ.setString(i++, pfccdgerror);
            stmsOBJ.setString(i++, pfcmsjerror);

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }
      
      // cambioDN
      
      public String cambioDNSET(final String pfcuser, final String pfcpass, final String pfcdnactual, final String pfcdnnuevo, final String pfccveedocen, final String pfccvemuncen, final String pfccvepobcen,final int pficompania, final int pfisistemaorigen, final String pfctoken, final int pfiusuarioid, int pficvetipolinea , int pficvetipotecnologia, int pficvetipodispositivo) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            String response= "";
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSCAMBIODN (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSCAMBIODN ("
                       +pfcuser+","+pfcpass+","+pfcdnactual+","+pfcdnnuevo+","+pfccveedocen+","+pfccvemuncen+","+pfccvepobcen+","+pficompania+","+pfisistemaorigen+","+pfctoken+","+pfiusuarioid+","+ pficvetipolinea +","+ pficvetipotecnologia +","+ pficvetipodispositivo +")}"; 
            
            Logger.write("     Operacion              : cambioDNSET");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            Logger.write("     pfcuser                : " + pfcuser);
            Logger.write("     pfcpass                : " + pfcpass);
            Logger.write("     pfcdnactual            : " + pfcdnactual);
            Logger.write("     pfcdnnuevo             : " + pfcdnnuevo);
            Logger.write("     pfccveedocen           : " + pfccveedocen);
            Logger.write("     pfccvemuncen           : " + pfccvemuncen);
            Logger.write("     pfccvepobcen           : " + pfccvepobcen);
            Logger.write("     pficompania            : " + pficompania);
            Logger.write("     pfisistemaorigen       : " + pfisistemaorigen);
            Logger.write("     pfctoken               : " + pfctoken);
            Logger.write("     pfiusuarioid           : " + pfiusuarioid);
            Logger.write("     pficvetipolinea        : " + pficvetipolinea);
            Logger.write("     pficvetipotecnologia   : " + pficvetipotecnologia);
            Logger.write("     pficvetipodispositivo  : " + pficvetipodispositivo);
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.VARCHAR);
            stmsOBJ.setString(i++, pfcuser);
            stmsOBJ.setString(i++, pfcpass);
            stmsOBJ.setString(i++, pfcdnactual);
            stmsOBJ.setString(i++, pfcdnnuevo);
            stmsOBJ.setString(i++, pfccveedocen);
            stmsOBJ.setString(i++, pfccvemuncen);
            stmsOBJ.setString(i++, pfccvepobcen);
            stmsOBJ.setInt(i++, pficompania);
            stmsOBJ.setInt(i++, pfisistemaorigen);
            stmsOBJ.setString(i++, pfctoken);
            stmsOBJ.setInt(i++, pfiusuarioid);
            stmsOBJ.setInt(i++, pficvetipolinea);
            stmsOBJ.setInt(i++, pficvetipotecnologia);
            stmsOBJ.setInt(i++, pficvetipodispositivo);

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getString(1);
            
            Logger.write("   + Respuesta              + " + response);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }
      
      
      public int cambioDNrequest(final String pfilogid, final String pfcdnactual, final String pfcdnnuevo , final int pfiusuarioid) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSDNCAMBIOREQUEST (?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSDNCAMBIOREQUEST ("
                       +pfilogid+","+pfcdnactual+","+pfcdnnuevo+","+pfiusuarioid+")}"; 
            
            Logger.write("     Operacion           : cambioDNrequest");
            Logger.write("   + Parametros          +");
            Logger.write("     commPRN             : " + commPRN);
            Logger.write("     pfilogid            : " + pfilogid);
            Logger.write("     pfcdnactual         : " + pfcdnactual);
            Logger.write("     pfcdnnuevo          : " + pfcdnnuevo);
            Logger.write("     pfiusuarioid        : " + pfiusuarioid);
                
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfcdnactual);
            stmsOBJ.setString(i++, pfcdnnuevo);
            stmsOBJ.setInt(i++, pfiusuarioid);

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail   : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }

      
      public int cambioDNresponse(final String pfilogid, final String pfcresponsecode, final String pfcresponsemessage, final int pfiusuarioid,final String pfccdgerror, final String pfcmsjerror) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSDNCAMBIORESPONSE (?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSDNCAMBIORESPONSE ("
                       +pfilogid+","+pfcresponsecode+","+pfcresponsemessage+","+pfiusuarioid+","+pfccdgerror+","+pfcmsjerror+")}"; 
            
            Logger.write("     Operacion           : cambioDNresponse");
            Logger.write("   + Parametros          +");
            Logger.write("     commPRN             : " + commPRN);
            Logger.write("     pfilogid            : " + pfilogid);
            Logger.write("     pfcdnactual         : " + pfcresponsecode);
            Logger.write("     pfcdnnuevo          : " + pfcresponsemessage);
            Logger.write("     pfiusuarioid        : " + pfiusuarioid);
            Logger.write("     pfccdger_ror        : " + pfccdgerror);
            Logger.write("     pfcmsjer_ror        : " + pfcmsjerror);

            
            
                
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfcresponsecode);
            stmsOBJ.setString(i++, pfcresponsemessage);
            stmsOBJ.setInt(i++, pfiusuarioid);
            stmsOBJ.setString(i++, pfccdgerror);
            stmsOBJ.setString(i++, pfcmsjerror);
            

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }
      
      
      public int doChangeMdnRequest(final String pfilogid, final String pfcmdnori, final String pfcmdndes, final String pfcidregion, final String pfccveedocen, final String pfccvemunicipiocen, final String pfccvepobcen, final String pfcidsistemafact,
              final String pfcidtipopago, final String pfcidmodalidad, final String pfcidoperador, final String pfccveservicio, final String pfccvesubservicio, final String pfctecnologia, final String pfcusuario,final int pfiusuarioid) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSDOCHANGEMDNREQUEST (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSDOCHANGEMDNREQUEST ("
                       +pfilogid+","+pfcmdnori+","+pfcmdndes+","+pfcidregion+","+pfccveedocen+","+pfccvemunicipiocen+","+pfccvepobcen+","+pfcidsistemafact+","+pfcidtipopago+","+pfcidmodalidad+","+pfcidoperador+","+pfccveservicio+","+pfccvesubservicio+","+pfctecnologia+","+pfcusuario+","+pfiusuarioid+")}"; 
            
            Logger.write("     Operacion            : doChangeMdnRequest");
            Logger.write("   + Parametros           +");
            Logger.write("     commPRN              : " + commPRN);
            Logger.write("     pfilogid             : " + pfilogid);
            Logger.write("     pfcmdnori            : " + pfcmdnori);
            Logger.write("     pfcmdndes            : " + pfcmdndes);
            Logger.write("     pfcidregion          : " + pfcidregion);
            Logger.write("     pfccveedocen         : " + pfccveedocen);
            Logger.write("     pfccvemunicipiocen   : " + pfccvemunicipiocen);
            Logger.write("     pfccvepobcen         : " + pfccvepobcen);
            Logger.write("     pfcidsistemafact     : " + pfcidsistemafact);
            Logger.write("     pfcidtipopago        : " + pfcidtipopago);
            Logger.write("     pfcidmodalidad       : " + pfcidmodalidad);
            Logger.write("     pfcidoperador        : " + pfcidoperador);
            Logger.write("     pfccveservicio       : " + pfccveservicio);
            Logger.write("     pfccvesubservicio    : " + pfccvesubservicio);
            Logger.write("     pfctecnologia        : " + pfctecnologia);
            Logger.write("     pfcusuario           : " + pfcusuario);
            Logger.write("     pfiusuarioid         : " + pfiusuarioid);
                
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfcmdnori);
            stmsOBJ.setString(i++, pfcmdndes);
            stmsOBJ.setString(i++, pfcidregion);
            stmsOBJ.setString(i++, pfccveedocen);
            stmsOBJ.setString(i++, pfccvemunicipiocen);
            stmsOBJ.setString(i++, pfccvepobcen);
            stmsOBJ.setString(i++, pfcidsistemafact);
            stmsOBJ.setString(i++, pfcidtipopago);
            stmsOBJ.setString(i++, pfcidmodalidad);
            stmsOBJ.setString(i++, pfcidoperador);
            stmsOBJ.setString(i++, pfccveservicio);
            stmsOBJ.setString(i++, pfccvesubservicio);
            stmsOBJ.setString(i++, pfctecnologia);
            stmsOBJ.setString(i++, pfcusuario);
            stmsOBJ.setInt(i++, pfiusuarioid);
            
            

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }
      
      public int doChangeMdnResponse(final String pfilogid,  final String pfclongidtransaccion,  final String pfcusuario, final int pfiusuarioid,final String pfccdgerror, final String pfcmsjerror) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSDOCHANGEMDNRESPONSE (?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSDOCHANGEMDNRESPONSE ("
                       +pfilogid+","+pfclongidtransaccion+","+pfcusuario+","+pfiusuarioid+","+pfccdgerror+","+pfcmsjerror+")}"; 
            
            Logger.write("     Operacion             : doChangeMdnResponse");
            Logger.write("   + Parametros            +");
            Logger.write("     commPRN               : " + commPRN);
            Logger.write("     pfilogid              : " + pfilogid);
            Logger.write("     pfclongidtransaccion  : " + pfclongidtransaccion);
            Logger.write("     pfcusuario            : " + pfcusuario);
            Logger.write("     pfiusuarioid          : " + pfiusuarioid);
            Logger.write("     pfccdger_ror        : " + pfccdgerror);
            Logger.write("     pfcmsjer_ror        : " + pfcmsjerror);
                
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfclongidtransaccion);
            stmsOBJ.setString(i++, pfcusuario);
            stmsOBJ.setInt(i++, pfiusuarioid);
            stmsOBJ.setString(i++, pfccdgerror);
            stmsOBJ.setString(i++, pfcmsjerror);

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }

      public int doCommitReserve(final String pfilogid, final String pfcidtransaccion, final String pfcusuario, final int pfiusuarioid) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSCOMMITRESERVEREQUEST (?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSCOMMITRESERVEREQUEST ("
                       +pfilogid+","+pfcidtransaccion+","+pfcusuario+","+pfiusuarioid+")}"; 
            
            Logger.write("     Operacion             : doCommitReserve");
            Logger.write("   + Parametros            +");
            Logger.write("     commPRN               : " + commPRN);
            Logger.write("     pfilogid              : " + pfilogid);
            Logger.write("     pfcidtransaccion      : " + pfcidtransaccion);
            Logger.write("     pfcusuario            : " + pfcusuario);
            Logger.write("     pfiusuarioid          : " + pfiusuarioid);
                
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfcidtransaccion);
            stmsOBJ.setString(i++, pfcusuario);
            stmsOBJ.setInt(i++, pfiusuarioid);

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }   
      
      
      public int doCommitResponse(final String pfilogid, final String pfccommitreserve, final String pfcidtransaccion, final String pfcusuario, final int pfiusuarioid,final String pfccdgerror, final String pfcmsjerror) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSCOMMITRESERVERESPONSE (?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSCOMMITRESERVERESPONSE ("
                       +pfilogid+","+pfccommitreserve+","+pfcidtransaccion+","+pfcusuario+","+pfiusuarioid+","+pfccdgerror+","+pfcmsjerror+")}"; 
            
            Logger.write("     Operacion             : doCommitResponse");
            Logger.write("   + Parametros            +");
            Logger.write("     commPRN               : " + commPRN);
            Logger.write("     pfilogid              : " + pfilogid);
            Logger.write("     pfccommitreserve      : " + pfccommitreserve);
            Logger.write("     pfcidtransaccion      : " + pfcidtransaccion);
            Logger.write("     pfcusuario            : " + pfcusuario);
            Logger.write("     pfiusuarioid          : " + pfiusuarioid);
            Logger.write("     pfccdger_ror        : " + pfccdgerror);
            Logger.write("     pfcmsjer_ror        : " + pfcmsjerror);
                
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfccommitreserve);
            stmsOBJ.setString(i++, pfcidtransaccion);
            stmsOBJ.setString(i++, pfcusuario);
            stmsOBJ.setInt(i++, pfiusuarioid);
            stmsOBJ.setString(i++, pfccdgerror);
            stmsOBJ.setString(i++, pfcmsjerror);

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }   

      public int doAvailableRequest(final String pfilogid, final String pfcmdn, final String pfcusuario, final int pfiusuarioid) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSDOAVAILABLEREQUEST (?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSDOAVAILABLEREQUEST ("
                          +pfilogid+","+pfcmdn+","+pfcusuario+","+pfiusuarioid+")}"; 
            
            Logger.write("     Operacion             : doAvailableRequest");
            Logger.write("   + Parametros            +");
            Logger.write("     commPRN               : " + commPRN);
            Logger.write("     pfilogid              : " + pfilogid);
            Logger.write("     pfcmdn                : " + pfcmdn);
            Logger.write("     pfcusuario            : " + pfcusuario);
            Logger.write("     pfiusuarioid          : " + pfiusuarioid);
                
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfcmdn);
            stmsOBJ.setString(i++, pfcusuario);
            stmsOBJ.setInt(i++, pfiusuarioid);

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }   
      
      public int doAvailableResponse(final String pfilogid, final String pfavailablereturn, final int pfiusuarioid,final String pfccdgerror, final String pfcmsjerror) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSDOAVAILABLERESPONSE (?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSDOAVAILABLERESPONSE ("
                          +pfilogid+","+pfavailablereturn+","+pfiusuarioid+","+pfccdgerror+","+pfcmsjerror+")}"; 
            
            Logger.write("     Operacion             : doAvailableResponse");
            Logger.write("   + Parametros            +");
            Logger.write("     commPRN               : " + commPRN);
            Logger.write("     pfilogid              : " + pfilogid);
            Logger.write("     pfavailablereturn     : " + pfavailablereturn);
            Logger.write("     pfiusuarioid          : " + pfiusuarioid);
            Logger.write("     pfccdger_ror        : " + pfccdgerror);
            Logger.write("     pfcmsjer_ror        : " + pfcmsjerror);
                
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfavailablereturn);
            stmsOBJ.setInt(i++, pfiusuarioid);
            stmsOBJ.setString(i++, pfccdgerror);
            stmsOBJ.setString(i++, pfcmsjerror);

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }
      
      public int cambioMdnRequest(final String pfilogid, final String pfcdnnuevo, final String pfcnumerosresponse, final String pfcidlinea, final String pfccatciudad, final String pfcmotcambio, final int  pfiusuarioid) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSCAMBIOMDNREQUEST (?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSCAMBIOMDNREQUEST ("
                       +pfilogid+","+pfcdnnuevo+","+pfcnumerosresponse+","+pfcidlinea+","+pfccatciudad+","+pfcmotcambio+","+pfiusuarioid+")}"; 
            
            Logger.write("     Operacion             : cambioMdnRequest");
            Logger.write("   + Parametros            +");
            Logger.write("     commPRN               : " + commPRN);
            Logger.write("     pfilogid              : " + pfilogid);  
            Logger.write("     pfcdnnuevo            : " + pfcdnnuevo);
            Logger.write("     pfcnumerosresponse    : " + pfcnumerosresponse);
            Logger.write("     pfcidlinea            : " + pfcidlinea);
            Logger.write("     pfccatciudad          : " + pfccatciudad);
            Logger.write("     pfcmotcambio          : " + pfcmotcambio);
            Logger.write("     pfiusuarioid          : " + pfiusuarioid);

            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfcdnnuevo);
            stmsOBJ.setString(i++, pfcnumerosresponse);
            stmsOBJ.setString(i++, pfcidlinea);
            stmsOBJ.setString(i++, pfccatciudad);
            stmsOBJ.setString(i++, pfcmotcambio);
            stmsOBJ.setInt(i++, pfiusuarioid);
            
            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }   
      
      
      public int programacionRequest(final String pfilogid, final String pfcuser, final String pfcpass, final String pfctecnologia,final String pfcidoperador,final String pfctmsisdn,final String pfctimsi,final int pfiusuarioid) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSREPROGRAMACIONREQUEST (?,?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSREPROGRAMACIONREQUEST ("
                        +pfilogid+","+pfcuser+","+pfcpass+","+pfctecnologia+","+pfcidoperador+","+pfctmsisdn+","+pfctimsi+","+pfiusuarioid+")}"; 
            
            Logger.write("     Operacion             : programacionRequest");
            Logger.write("   + Parametros            +");
            Logger.write("     commPRN               : " + commPRN);
            Logger.write("     pfilogid              : " + pfilogid);
            Logger.write("     pfcuser               : " + pfcuser);
            Logger.write("     pfcpass               : " + pfcpass);
            Logger.write("     pfctecnologia         : " + pfctecnologia);
            Logger.write("     pfcidoperador         : " + pfcidoperador);
            Logger.write("     pfctmsisdn            : " + pfctmsisdn);
            Logger.write("     pfctimsi              : " + pfctimsi);
            Logger.write("     pfiusuarioid          : " + pfiusuarioid);

            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfcuser);
            stmsOBJ.setString(i++, pfcpass);
            stmsOBJ.setString(i++, pfctecnologia);
            stmsOBJ.setString(i++, pfcidoperador);
            stmsOBJ.setString(i++, pfctmsisdn);
            stmsOBJ.setString(i++, pfctimsi);
            stmsOBJ.setInt(i++, pfiusuarioid);
            
            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }   
      
      public int reProgramacionResponse(final String pfilogid, final String pfccodigorespuesta, final String pfcdn, final String pfcmensaje, final int pfiusuarioid,final String pfccdgerror, final String pfcmsjerror) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSREPROGRAMACIONRESPONSE (?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSREPROGRAMACIONRESPONSE ("
                        +pfilogid+","+pfccodigorespuesta+","+pfcdn+","+pfcmensaje+","+pfiusuarioid+","+pfccdgerror+","+pfcmsjerror+")}"; 
            
            Logger.write("     Operacion             : reProgramacionResponse");
            Logger.write("   + Parametros            +");
            Logger.write("     commPRN               : " + commPRN);
            Logger.write("     pfilogid              : " + pfilogid);
            Logger.write("     pfccodigorespuesta    : " + pfccodigorespuesta);
            Logger.write("     pfcdn                 : " + pfcdn);
            Logger.write("     pfcmensaje            : " + pfcmensaje);
            Logger.write("     pfiusuarioid          : " + pfiusuarioid);
            Logger.write("     pfccdger_ror        : " + pfccdgerror);
            Logger.write("     pfcmsjer_ror        : " + pfcmsjerror);

            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfccodigorespuesta);
            stmsOBJ.setString(i++, pfcdn);
            stmsOBJ.setString(i++, pfcmensaje);
            stmsOBJ.setInt(i++, pfiusuarioid);
            stmsOBJ.setString(i++, pfccdgerror);
            stmsOBJ.setString(i++, pfcmsjerror);
            
            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }   
      
      
      public int dnCambioResponse(final String pfilogid, final String pfcresponsecode, final String pfcresponsemessage, final int pfiusuarioid,final String pfccdgerror, final String pfcmsjerror) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            int response= -1;
            
            long timeIni = 0;
            int i = 1; 
            
            try{
            commSQL = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSCAMBIODNRESPONSE (?,?,?,?,?,?)}";
            commPRN = "{ ? = call MIIUSACELL.PAMIICAMBIODNSET.FNINSCAMBIODNRESPONSE ("
                       +pfilogid+","+pfcresponsecode+","+pfcresponsemessage+","+pfiusuarioid+","+pfccdgerror+","+pfcmsjerror+")}"; 
            
            Logger.write("     Operacion           : dnCambioResponse");
            Logger.write("   + Parametros          +");
            Logger.write("     commPRN             : " + commPRN);
            Logger.write("     pfilogid            : " + pfilogid);
            Logger.write("     pfcdnactual         : " + pfcresponsecode);
            Logger.write("     pfcdnnuevo          : " + pfcresponsemessage);
            Logger.write("     pfiusuarioid        : " + pfiusuarioid);
            Logger.write("     pfccdger_ror         : " + pfccdgerror);
            Logger.write("     pfcmsjer_ror         : " + pfcmsjerror);
                
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Conexion BD Abierta    : true");
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            
            if(stmsOBJ != null){
            stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
            stmsOBJ.setString(i++, pfilogid);
            stmsOBJ.setString(i++, pfcresponsecode);
            stmsOBJ.setString(i++, pfcresponsemessage);
            stmsOBJ.setInt(i++, pfiusuarioid);
            stmsOBJ.setString(i++, pfccdgerror);
            stmsOBJ.setString(i++, pfcmsjerror);

            Logger.write("     Ejecutando consulta    :");
            timeIni = System.currentTimeMillis();
            stmsOBJ.execute();

            response = stmsOBJ.getInt(1);
            
            }
            }catch(SQLException e){
            Logger.write("     Detail    : (Exception) " + e.getMessage());     
            }finally{
            if (stmsOBJ != null) try {
            stmsOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            if (connOBJ != null){
            try {
            connOBJ.close();
            }
            catch (SQLException e) {
            Logger.write("     Detail  : (SQLException) " + e.getMessage());
            }
            }
            Logger.write("     Conexion BD Abierta    : false");     
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            
            return response;
      }
        
      //AbonoTA
      public String abonoTiempoAire(final String user, final String pass, final String dn, final String dnParaAbono, final int anioExpira, final String cdgSeguridad, final String concepto, final Double importe,final int mesExpira, final String numTarjeta, final String tipoTarjeta, final String ip, final long secuencia, final String password, final int tipoPlataforma, final int compania, final int sistemaOrigen, final int pficvetipolinea , final int pficvetipotecnologia, final int pficvetipodispositivo, final String token, final int usuarioId) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String rsetOBJ = "";
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;

            try{
                commSQL = "{ ? = call   MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNBBINSLOGABONOTIEMPOAIRE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call   MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNBBINSLOGABONOTIEMPOAIRE("
                    +user+","+pass+","+dn+","+dnParaAbono+","+","+anioExpira+","+concepto+","+importe+","+mesExpira+","+numTarjeta+","
                    +tipoTarjeta+","+ip+","+secuencia+","+password+","+tipoPlataforma+","+compania+","+sistemaOrigen+","+token+","+usuarioId+","+pficvetipolinea+","+pficvetipotecnologia+","+pficvetipodispositivo+
                    ")}";

                Logger.write("     Operacion              : abonoTiempoAire");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     dn                     : " + dn);
                Logger.write("     dnParaAbono            : " + dnParaAbono);
                Logger.write("     anioExpira             : " + anioExpira);
                Logger.write("     concepto               : " + concepto);
                Logger.write("     importe                : " + importe);
                Logger.write("     mesExpira              : " + mesExpira);
                Logger.write("     numTargeta             : " + numTarjeta);
                Logger.write("     tipoTarjeta            : " + tipoTarjeta);
                Logger.write("     ip                     : " + ip);
                Logger.write("     secuencia              : " + secuencia);
                Logger.write("     password               : " + password);
                Logger.write("     tipoPlataforma         : " + tipoPlataforma);
                Logger.write("     compania               : " + compania);
                Logger.write("     sistemaOrigen          : " + sistemaOrigen);
                Logger.write("     pficvetipolinea        : " + pficvetipolinea);
                Logger.write("     pficvetipotecnologia   : " + pficvetipotecnologia);
                Logger.write("     pficvetipodispositivo  : " + pficvetipodispositivo);
                Logger.write("     usuarioId              : " + usuarioId);


                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++,user);
                    stmsOBJ.setString(i++,pass);
                    stmsOBJ.setString(i++, dn);
                    stmsOBJ.setString(i++, dnParaAbono);
                    stmsOBJ.setInt(i++, anioExpira);
                    stmsOBJ.setString(i++, concepto);
                    stmsOBJ.setDouble(i++, importe);
                    stmsOBJ.setInt(i++, mesExpira);
                    stmsOBJ.setString(i++, numTarjeta);
                    stmsOBJ.setString(i++, tipoTarjeta);
                    stmsOBJ.setString(i++, ip);
                    stmsOBJ.setLong(i++, secuencia);
                    stmsOBJ.setString(i++, password);
                    stmsOBJ.setInt(i++, tipoPlataforma);
                    stmsOBJ.setInt(i++, compania);
                    stmsOBJ.setInt(i++, sistemaOrigen);
                    stmsOBJ.setString(i++, token);
                    stmsOBJ.setInt(i++, usuarioId);
                    stmsOBJ.setInt(i++, pficvetipolinea);
                    stmsOBJ.setInt(i++, pficvetipotecnologia);
                    stmsOBJ.setInt(i++, pficvetipodispositivo);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rsetOBJ = stmsOBJ.getString(1);

                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
        public String abonoTiempoAireCaja(final String logId, final String dnParaAbono, final String user, final String contrasena, final String anioExpira, final String cdgSeguridad, final String concepto, final double importe,final int mesExpira, final String numTarjeta, final String tipoTarjeta, final String ip, final long secuencia, final String password, final int tipoPlataforma, final String scdgcsi,final String importeRenta,final String importeDeposito,final String numTransaccion, final String region, final String sistema, final String tipoMovCaja, final String tipoOperacion, final String tipoTransac, final String usuario, final String vendedor,final String bancoId,final String campo55, final String idBenefch, final String idctarjet, final String mesesSinIntereses,final String nomTitularCh, final String refBancaria, final String numAutor, final String tipoMovTarjeta,final String tipoPago,final String track2, final String isProducto, final String cantidad, final String descuento, final String numSerie, final String iccId, final String numCheque, final String numCtaCheque, final int usuarioId) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String rsetOBJ = "";
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;

            try{
                commSQL = "{ ? = call   MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGABONOTIEMPOAIRECAJA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call   MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGABONOTIEMPOAIRECAJA("
                    +logId+","+dnParaAbono+","+user+","+","+contrasena+","+anioExpira+","+concepto+","+importe+","+mesExpira+","+numTarjeta+","
                    +tipoTarjeta+","+ip+","+secuencia+","+password+","+tipoPlataforma+","+scdgcsi+","+importeRenta+","+importeDeposito+","+","
                    +numTransaccion+","+region+","+sistema+","+tipoMovCaja+","+tipoOperacion+","+tipoTransac+","+usuario+","+vendedor+","+","
                    +bancoId+","+campo55+","+idBenefch+","+idctarjet+","+mesesSinIntereses+","+nomTitularCh+","+refBancaria+","+numAutor+","+","
                    +tipoMovTarjeta+","+tipoPago+","+track2+","+isProducto+","+cantidad+","+descuento+","+numSerie+","+iccId+","+","
                    +numCheque+","+numCtaCheque+","+usuarioId+","+
                    ")}";

                Logger.write("     Operacion              : abonoTiempoAireCaja");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     logId                  : " + logId);
                Logger.write("     dnParaAbono            : " + dnParaAbono);
                Logger.write("     user                   : " + user);
                Logger.write("     contrasena             : " + contrasena);
                Logger.write("     anioExpira             : " + anioExpira);
                Logger.write("     concepto               : " + concepto);
                Logger.write("     importe                : " + importe);
                Logger.write("     mesExpira              : " + mesExpira);
                Logger.write("     numTarjeta             : " + numTarjeta);
                Logger.write("     tipoTarjeta            : " + tipoTarjeta);
                Logger.write("     ip                     : " + ip);
                Logger.write("     secuencia              : " + secuencia);
                Logger.write("     password               : " + password);
                Logger.write("     tipoPlataforma         : " + tipoPlataforma);
                Logger.write("     scdgcsi                : " + scdgcsi);
                Logger.write("     importeRenta           : " + importeRenta);
                Logger.write("     importeDeposito        : " + importeDeposito);
                Logger.write("     numTransaccion         : " + numTransaccion);
                Logger.write("     region                 : " + region);
                Logger.write("     sistema                : " + sistema);
                Logger.write("     tipoMovCaja            : " + tipoMovCaja);
                Logger.write("     tipoOperacion          : " + tipoOperacion);
                Logger.write("     tipoTransac            : " + tipoTransac);
                Logger.write("     usuario                : " + usuario);
                Logger.write("     vendedor               : " + vendedor);
                Logger.write("     bancoId                : " + bancoId);
                Logger.write("     campo55                : " + campo55);
                Logger.write("     fingerPrint              : " + idBenefch);
                Logger.write("     idctarjet              : " + idctarjet);
                Logger.write("     mesesSinIntereses      : " + mesesSinIntereses);
                Logger.write("     nomTitularCh           : " + nomTitularCh);
                Logger.write("     refBancaria            : " + refBancaria);
                Logger.write("     numAutor               : " + numAutor);
                Logger.write("     numCheque              : " + numCheque);
                Logger.write("     numCtaCheque           : " + numCtaCheque);
                Logger.write("     usuarioId              : " + usuarioId);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++,logId); 
                    stmsOBJ.setString(i++,dnParaAbono);
                    stmsOBJ.setString(i++,user);
                    stmsOBJ.setString(i++,contrasena); 
                    stmsOBJ.setString(i++,anioExpira); 
                    stmsOBJ.setString(i++,concepto);
                    stmsOBJ.setDouble(i++,  importe);
                    stmsOBJ.setInt(i++,  mesExpira);
                    stmsOBJ.setString(i++,numTarjeta); 
                    stmsOBJ.setString(i++,tipoTarjeta); 
                    stmsOBJ.setString(i++,ip);
                    stmsOBJ.setDouble(i++,  secuencia); 
                    stmsOBJ.setString(i++,password);
                    stmsOBJ.setInt(i++,  tipoPlataforma); 
                    stmsOBJ.setString(i++,scdgcsi);
                    stmsOBJ.setString(i++,importeRenta);
                    stmsOBJ.setString(i++,importeDeposito);
                    stmsOBJ.setString(i++,numTransaccion);
                    stmsOBJ.setString(i++,region);
                    stmsOBJ.setString(i++,sistema);
                    stmsOBJ.setString(i++,tipoMovCaja); 
                    stmsOBJ.setString(i++,tipoOperacion); 
                    stmsOBJ.setString(i++,tipoTransac);
                    stmsOBJ.setString(i++,usuario);
                    stmsOBJ.setString(i++,vendedor);
                    stmsOBJ.setString(i++,bancoId);
                    stmsOBJ.setString(i++,campo55); 
                    stmsOBJ.setString(i++,idBenefch); 
                    stmsOBJ.setString(i++,idctarjet);
                    stmsOBJ.setString(i++,mesesSinIntereses);
                    stmsOBJ.setString(i++,nomTitularCh);
                    stmsOBJ.setString(i++,refBancaria);
                    stmsOBJ.setString(i++,numAutor);
                    stmsOBJ.setString(i++,tipoMovTarjeta);
                    stmsOBJ.setString(i++,tipoPago);
                    stmsOBJ.setString(i++,track2);
                    stmsOBJ.setString(i++,isProducto); 
                    stmsOBJ.setString(i++,cantidad);
                    stmsOBJ.setString(i++,descuento); 
                    stmsOBJ.setString(i++,numSerie);
                    stmsOBJ.setString(i++,iccId);
                    stmsOBJ.setString(i++,numCheque); 
                    stmsOBJ.setString(i++,numCtaCheque); 
                    stmsOBJ.setInt(i++,  usuarioId);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rsetOBJ = stmsOBJ.getString(1);


                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
        
        
        public int cajaRespuesta(final String logId, final int numResCaja, final String errorCode, final int idRegistro, final String numAutorizacion, final String numTarjetaint,final int usuarioId, final String cdgError, final String msjError) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            int rsetOBJ = -1;
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;

            try{
                commSQL = "{ ? = call   MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGCAJARESPUESTA(?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call   MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGCAJARESPUESTA("
                    +logId+","+numResCaja+","+errorCode+","+","+idRegistro+","+numAutorizacion+","+numTarjetaint+","+usuarioId+","+cdgError+","+msjError+","+
                    ")}";

                Logger.write("     Operacion              : cajaRespuesta");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     logId                  : " + logId);
                Logger.write("     numResCaja             : " + numResCaja);
                Logger.write("     er_rorCode             : " + errorCode);
                Logger.write("     idRegistro             : " + idRegistro);
                Logger.write("     numAutorizacion        : " + numAutorizacion);
                Logger.write("     numTarjetaint          : " + numTarjetaint);
                Logger.write("     usuarioId              : " + usuarioId);
                Logger.write("     cdgEr_ror              : " + cdgError);
                Logger.write("     msjEr_ror              : " + msjError);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++,logId); 
                    stmsOBJ.setInt(i++,numResCaja);
                    stmsOBJ.setString(i++,errorCode);
                    stmsOBJ.setInt(i++,idRegistro); 
                    stmsOBJ.setString(i++,numAutorizacion);
                    stmsOBJ.setString(i++,numTarjetaint); 
                    stmsOBJ.setInt(i++,usuarioId);
                    stmsOBJ.setString(i++,cdgError);
                    stmsOBJ.setString(i++,msjError);
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rsetOBJ = stmsOBJ.getInt(1);


                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
        
        
        public int abonoTAUnidad(final String logId, final int idUnidad, final String dn, final double importe, final long secuencia,final int usuarioId) throws  ServiceException
        {               
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            int rsetOBJ = -1;
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;

            try{
                commSQL = "{ ? = call    MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGABONOTAUNIDAD(?,?,?,?,?,?)}";
                commPRN = "{ ? = call    MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGABONOTAUNIDAD("
                    +logId+","+idUnidad+","+dn+","+","+importe+","+secuencia+","+usuarioId+","+
                    ")}";

                Logger.write("     Operacion              : abonoTAUnidad");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     logId                  : " + logId);
                Logger.write("     idUnidad               : " + idUnidad);
                Logger.write("     dn                     : " + dn);
                Logger.write("     importe                : " + importe);
                Logger.write("     secuencia              : " + secuencia);
                Logger.write("     usuarioId              : " + usuarioId);


                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++,logId); 
                    stmsOBJ.setInt(i++,idUnidad);
                    stmsOBJ.setString(i++,dn);
                    stmsOBJ.setDouble(i++,importe); 
                    stmsOBJ.setLong(i++,secuencia);
                    stmsOBJ.setInt(i++,usuarioId); 


                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rsetOBJ = stmsOBJ.getInt(1);


                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
        
        
        public int abonoTAUnidadRespuesta(final String logId, final int idUnidad, final int numRespuesta, final String cdgResAbonoTA, final String numResAbonoTA,final String numTransaCaja, 
                final String numAutorICaja, final int idRegistroCaja ,final int usuarioId,final String cdgError, final String msjError) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            int rsetOBJ = -1;
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;

            try{
                commSQL = "{ ? = call    MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGABONOTAUNIDADRESPUESTA(?,?,?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call    MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGABONOTAUNIDADRESPUESTA("
                    +logId+","+idUnidad+","+","+numRespuesta+","+cdgResAbonoTA+","+numResAbonoTA+","+numTransaCaja+","+
                    numAutorICaja+","+  idRegistroCaja+","+ usuarioId+","+cdgError+","+msjError+","+
                    ")}";

                Logger.write("     Operacion              : abonoTAUnidadRespuesta");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     logId                  : " + logId);
                Logger.write("     idUnidad               : " + idUnidad);
                Logger.write("     numRespuesta           : " + numRespuesta);
                Logger.write("     cdgResAbonoTA          : " + cdgResAbonoTA);
                Logger.write("     numResAbonoTA          : " + numResAbonoTA);
                Logger.write("     numTransaCaja          : " + numTransaCaja);
                Logger.write("     numAutorICaja          : " + numAutorICaja);
                Logger.write("     idRegistroCaja         : " + idRegistroCaja);
                Logger.write("     usuarioId              : " + usuarioId);
                Logger.write("     cdgEr_ror               : " + cdgError);
                Logger.write("     msjEr_ror               : " + msjError);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++,logId); 
                    stmsOBJ.setInt(i++,idUnidad);
                    stmsOBJ.setInt(i++,numRespuesta);
                    stmsOBJ.setString(i++,cdgResAbonoTA); 
                    stmsOBJ.setString(i++,numResAbonoTA);
                    stmsOBJ.setString(i++,numTransaCaja);
                    stmsOBJ.setString(i++,numAutorICaja);
                    stmsOBJ.setInt(i++,idRegistroCaja);
                    stmsOBJ.setInt(i++,usuarioId); 
                    stmsOBJ.setString(i++,cdgError); 
                    stmsOBJ.setString(i++,msjError); 

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();  

                    rsetOBJ = stmsOBJ.getInt(1);


                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }

        public int reversaCaja(final String logId, final int in03, final String ip,final int usuarioId,final String cdgError, final String msjError) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            int rsetOBJ = -1;
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            try{
                
                commSQL = "{ ? = call    MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGREVERSACAJA(?,?,?,?,?,?)}";
                commPRN = "{ ? = call    MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGREVERSACAJA("
                    +logId+","+in03+","+","+ip+","+ usuarioId+","+
                    ")}";

                Logger.write("     Operacion              : reversaCaja");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     logId                  : " + logId);
                Logger.write("     in03                   : " + in03);
                Logger.write("     ip                     : " + ip);
                Logger.write("     usuarioId              : " + usuarioId);
                Logger.write("     cdgEr_ror               : " + cdgError);
                Logger.write("     msjEr_ror               : " + msjError);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++,logId); 
                    stmsOBJ.setInt(i++,in03);
                    stmsOBJ.setString(i++,ip);
                    stmsOBJ.setInt(i++,usuarioId); 
                    stmsOBJ.setString(i++,cdgError); 
                    stmsOBJ.setString(i++,msjError); 

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();  

                    rsetOBJ = stmsOBJ.getInt(1);


                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
        
        public int cajaConfirmaPago(final String logId, final int in03, final String ip,final String autorizacionTA, final long secuenciaTA, final int usuarioId) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            int rsetOBJ = -1;
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            try{
                
                commSQL = "{ ? = call    MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGCAJACONFIRMAPAGO(?,?,?,?,?,?)}";
                commPRN = "{ ? = call    MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGCAJACONFIRMAPAGO("
                    +logId+","+in03+","+","+ip+","+autorizacionTA+","+secuenciaTA+","+usuarioId+","+
                    ")}";

                Logger.write("     Operacion              : cajaConfirmaPago");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     logId                  : " + logId);
                Logger.write("     in03                   : " + in03);
                Logger.write("     ip                     : " + ip);
                Logger.write("     autorizacionTA         : " + autorizacionTA);
                Logger.write("     secuenciaTA            : " + secuenciaTA);
                Logger.write("     usuarioId              : " + usuarioId);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++,logId); 
                    stmsOBJ.setInt(i++,in03);
                    stmsOBJ.setString(i++,ip);
                    stmsOBJ.setString(i++,autorizacionTA);
                    stmsOBJ.setLong(i++,secuenciaTA);
                    stmsOBJ.setInt(i++,usuarioId); 

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();  

                    rsetOBJ = stmsOBJ.getInt(1);


                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
        
        public int cajaConfirmaPagoRes(final String logId, final String numTransaccion, final int usuarioId, final String cdgError, final String msjError) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            int rsetOBJ = -1;
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            try{
                
                commSQL = "{ ? = call    MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGCAJACONFIRMAPAGORES(?,?,?,?,?)}";
                commPRN = "{ ? = call    MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGCAJACONFIRMAPAGORES("
                    +logId+","+numTransaccion+","+usuarioId+","+cdgError+","+   msjError+","+
                    ")}";

                Logger.write("     Operacion              : cajaConfirmaPagoRes");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     logId                  : " + logId);
                Logger.write("     numTransaccion         : " + numTransaccion);
                Logger.write("     usuarioId              : " + usuarioId);
                Logger.write("     cdgEr_ror              : " + cdgError);
                Logger.write("     msjEr_ror              : " + msjError);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++,logId); 
                    stmsOBJ.setString(i++,numTransaccion);
                    stmsOBJ.setInt(i++,usuarioId); 
                    stmsOBJ.setString(i++,cdgError); 
                    stmsOBJ.setString(i++,msjError); 

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();  

                    rsetOBJ = stmsOBJ.getInt(1);


                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
        
        
        public int abonoTAAutorizador(final String logId, final String codigoRespuestaAbonoTA, final String numeroAutorizacionAbonoTA, final String numTransaccionCaja,final String numeroAutorizacionCaja, final int idRegistroCaja, final int usuarioId,final String cdgError, final String msjError) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            int rsetOBJ = -1;
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            try{
                
                commSQL = "{ ? = call    MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGABONOTAAUTORIZADOR(?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call    MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNINSLOGABONOTAAUTORIZADOR("
                    +logId+","+codigoRespuestaAbonoTA+","+numeroAutorizacionAbonoTA+","+numTransaccionCaja+","+numeroAutorizacionCaja+","+idRegistroCaja+","+usuarioId+","+cdgError+","+msjError+","+
                    ")}";
                Logger.write("     user                   : -PROTEGIDO-");
                Logger.write("     Operacion              : abonoTAAutorizador");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     logId                  : " + logId);
                Logger.write("     codigoRespAbonoTA      : " + codigoRespuestaAbonoTA);
                Logger.write("     numeroAutorAbonoTA     : " + numeroAutorizacionAbonoTA);
                Logger.write("     numTransaccionCaja     : " + numTransaccionCaja);
                Logger.write("     numeroAutorizacionCaja : " + numeroAutorizacionCaja);
                Logger.write("     idRegistroCaja         : " + idRegistroCaja);
                Logger.write("     usuarioId              : " + usuarioId);
                Logger.write("     cdgEr_ror              : " + cdgError);
                Logger.write("     msjEr_ror              : " + msjError);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++,logId); 
                    stmsOBJ.setString(i++,codigoRespuestaAbonoTA);
                    stmsOBJ.setString(i++,numeroAutorizacionAbonoTA);
                    stmsOBJ.setString(i++,numTransaccionCaja);
                    stmsOBJ.setString(i++,numeroAutorizacionCaja);
                    stmsOBJ.setInt(i++,idRegistroCaja);
                    stmsOBJ.setInt(i++,usuarioId); 
                    stmsOBJ.setString(i++,cdgError); 
                    stmsOBJ.setString(i++,msjError); 

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();  

                    rsetOBJ = stmsOBJ.getInt(1);


                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
        
        public String altaCita(final String user, final String pass,final int cveHora, final String fecha,final String tienda, final String suscriptor, final String nombre, final String apPaterno, 
                final String apMaterno, final String correo, final String comentario, final int tipoAtencion, final int cveGenerdor, final int compania, final int sistemaOrigen, final String token, final int usuarioId,final int pficvetipolinea , final int pficvetipotecnologia, final int pficvetipodispositivo) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            String retorno =  "";
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i = 1;
            try {
                commORA = "{ ? = call MIIUSACELL.PAMIIALTACITASET.FNINSLOGALTACITA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
                commPRN = "{ ? = MIIUSACELL.PAMIIALTACITASET.FNINSLOGALTACITA(" + user+","+ pass+","+ cveHora +","+ fecha+","+ tienda +","+ suscriptor +","+ nombre +","+  apPaterno +","+ apMaterno +","+ correo +","+ comentario +","+ tipoAtencion +","+ cveGenerdor +","+ token+","+ usuarioId +","+ pficvetipolinea +","+ pficvetipotecnologia +","+ pficvetipodispositivo +","+ 
                ") }";

                Logger.write("     Operacion              : altaCita");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     user                   : " + user);
                Logger.write("     pass                   : " + pass);
                Logger.write("     cveHora                : " + cveHora);
                Logger.write("     fecha                  : " + fecha);
                Logger.write("     tienda                 : " + tienda);
                Logger.write("     suscriptor             : " + suscriptor);
                Logger.write("     nombre                 : " + nombre);
                Logger.write("     apPaterno              : " + apPaterno);
                Logger.write("     apMaterno              : " + apMaterno);
                Logger.write("     correo                 : " + correo);
                Logger.write("     comentario             : " + comentario);
                Logger.write("     tipoAtencion           : " + tipoAtencion);
                Logger.write("     cveGenerdor            : " + cveGenerdor);
                Logger.write("     token                  : " + token);
                Logger.write("     compania               : " + compania);
                Logger.write("     sistemaOrigen          : " + sistemaOrigen);
                Logger.write("     usuarioId              : " + usuarioId);
                Logger.write("     Tipo Linea             : " + pficvetipolinea);
                Logger.write("     Tipo Tecnologia        : " + pficvetipotecnologia);
                Logger.write("     Tipo Dispositivo       : " + pficvetipodispositivo);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                stmsOBJ.registerOutParameter(i++, OracleTypes.VARCHAR);
                stmsOBJ.setString(i++, user);
                stmsOBJ.setString(i++, pass);
                stmsOBJ.setInt(i++, cveHora);
                stmsOBJ.setString(i++, fecha);
                stmsOBJ.setString(i++, tienda);
                stmsOBJ.setString(i++, suscriptor);
                stmsOBJ.setString(i++, nombre);
                stmsOBJ.setString(i++, apPaterno);
                stmsOBJ.setString(i++, apMaterno);
                stmsOBJ.setString(i++, correo);
                stmsOBJ.setString(i++, comentario);
                stmsOBJ.setInt(i++, tipoAtencion);
                stmsOBJ.setInt(i++, cveGenerdor);
                stmsOBJ.setString(i++, token);
                stmsOBJ.setInt(i++, compania);
                stmsOBJ.setInt(i++, sistemaOrigen);
                stmsOBJ.setInt(i++, usuarioId);
                stmsOBJ.setInt(i++, pficvetipolinea);
                stmsOBJ.setInt(i++, pficvetipotecnologia);
                stmsOBJ.setInt(i++, pficvetipodispositivo);
                
                Logger.write("     Ejecutando consulta    :");
                
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                
                retorno = stmsOBJ.getString(1);
                    

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        } 
      
        
        public int callServiceAltaCita(final String logId,final String user, final String pass, final int cveHora, final String fecha,final String tienda, final String suscriptor,final String nombre, final String apPaterno,final String apMaterno,final String correo,final String comentario,  final int tipoAtencion, final int cveGenerdor, final String tokenSeguridad, final int usuarioId) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            int rsetOBJ = -1;
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            try{
                
                commSQL = "{ ? = call    MIIUSACELL.PAMIIALTACITASET.FNINSLOGCALLSERVICEALTACITA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call    MIIUSACELL.PAMIIALTACITASET.FNINSLOGCALLSERVICEALTACITA("
                    +logId+","+user+","+pass+","+cveHora+","+fecha+","+tienda+","+suscriptor+","+nombre+","+apPaterno+","+apMaterno+","+correo+","+comentario+
                    ","+tipoAtencion+","+cveGenerdor+","+tokenSeguridad+","+usuarioId+
                    ")}";
                Logger.write("     Operacion              : callServiceAltaCita");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     logId                  : " + logId);
                Logger.write("     user                   : " + user);
                Logger.write("     pass                   : " + pass);
                Logger.write("     cveHora                : " + cveHora);
                Logger.write("     fecha                  : " + fecha);
                Logger.write("     tienda                 : " + tienda);
                Logger.write("     suscriptor             : " + suscriptor);
                Logger.write("     nombre                 : " + nombre );
                Logger.write("     apPaterno              : " + apPaterno);
                Logger.write("     apMaterno              : " + apMaterno);
                Logger.write("     correo                 : " + correo);
                Logger.write("     comentario             : " + comentario);
                Logger.write("     tipoAtencion           : " + tipoAtencion);
                Logger.write("     cveGenerdor            : " + cveGenerdor);
                Logger.write("     tokenSeguridad         : " + tokenSeguridad);
                Logger.write("     usuarioId              : " + usuarioId);
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++,logId); 
                    stmsOBJ.setString(i++,user);
                    stmsOBJ.setString(i++,pass);
                    stmsOBJ.setInt(i++,cveHora);
                    stmsOBJ.setString(i++,fecha);
                    stmsOBJ.setString(i++,tienda);
                    stmsOBJ.setString(i++,suscriptor); 
                    stmsOBJ.setString(i++,nombre); 
                    stmsOBJ.setString(i++,apPaterno);
                    stmsOBJ.setString(i++,apMaterno);
                    stmsOBJ.setString(i++,correo);
                    stmsOBJ.setString(i++,comentario);
                    stmsOBJ.setInt(i++,tipoAtencion);
                    stmsOBJ.setInt(i++,cveGenerdor);
                    stmsOBJ.setString(i++,tokenSeguridad);
                    stmsOBJ.setInt(i++,usuarioId);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();  

                    rsetOBJ = stmsOBJ.getInt(1);


                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
        public int altaCitaResponse(final String logId,final String exito, final String mensaje, final String cdgError, final String msjError, final int usuarioId) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            int rsetOBJ = -1;
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            try{
                
                commSQL = "{ ? = call    MIIUSACELL.PAMIIALTACITASET.FNINSLOGALTACITARESPONSE(?,?,?,?,?,?)}";
                commPRN = "{ ? = call    MIIUSACELL.PAMIIALTACITASET.FNINSLOGALTACITARESPONSE("
                    +logId+","+exito+","+mensaje+","+cdgError+","+msjError+","+usuarioId+
                    ")}";
                Logger.write("     Operacion              : altaCitaResponse");
                Logger.write("   + Parametros             + ");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     logId                  : " + logId);
                Logger.write("     exito                  : " + exito);
                Logger.write("     mensaje                : " + mensaje);
                Logger.write("     cdgEr_ror              : " + cdgError);
                Logger.write("     usuarioId              : " + usuarioId);
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++,logId); 
                    stmsOBJ.setString(i++,exito);
                    stmsOBJ.setString(i++,mensaje);
                    stmsOBJ.setString(i++,cdgError);
                    stmsOBJ.setString(i++,msjError);
                    stmsOBJ.setInt(i++,usuarioId);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();  

                    rsetOBJ = stmsOBJ.getInt(1);

                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
        
        public String pagoFacturaEntrada(final String user, final String pass, final String dn, final CardVO tarjeta, 
                final int tipoPlataforma, final int compania, final int sistemaOrigen, final int dispositivo, 
                final int tipoLinea, final int tipoTecnologia, final String fingerPrint, final String token) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String rsetOBJ = "";
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            try{
                
                commSQL = "{ ? = call MIIUSACELL.PAMIIFACTURACIONSET.FNINSFACTURACION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call MIIUSACELL.PAMIIFACTURACIONSET.FNINSFACTURACION("
                    +dn+","+user+","+pass+","+tarjeta.getAmount()+","+tarjeta.getCardExpiryMonth()+","+tarjeta.getCardExpiryYear()+","
                    +tarjeta.getCardNumber()+",***,"+tipoPlataforma+","+compania+","+sistemaOrigen+","+dispositivo+","+tipoLinea+","
                    +tipoTecnologia+","+token+",2121,"+ fingerPrint +
                    ")}";
                Logger.write("     Operacion              : pagoFacturaEntrada");
                Logger.write("   + Parametros             + ");
                Logger.write("     commPRN                : " + commPRN);
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, dn);
                    stmsOBJ.setString(i++, user);
                    stmsOBJ.setString(i++, pass);
                    stmsOBJ.setString(i++, tarjeta.getAmount());
                    stmsOBJ.setString(i++, tarjeta.getCardExpiryMonth());
                    stmsOBJ.setString(i++, tarjeta.getCardExpiryYear());
                    stmsOBJ.setString(i++, tarjeta.getCardNumber());
                    stmsOBJ.setInt(i++, tipoPlataforma);
                    stmsOBJ.setInt(i++, compania);
                    stmsOBJ.setInt(i++, sistemaOrigen);
                    stmsOBJ.setInt(i++, dispositivo);
                    stmsOBJ.setInt(i++, tipoLinea);
                    stmsOBJ.setInt(i++, tipoTecnologia);
                    stmsOBJ.setString(i++, token);
                    stmsOBJ.setInt(i++, 212121);
                    stmsOBJ.setString(i++, fingerPrint);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();  

                    rsetOBJ = stmsOBJ.getString(1);

                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
        
        public int pagoFacturaLlamadaMetodo(final String logId, final CardVO tarjeta, final PagoVO pago) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            int rsetOBJ = -1;
            String commSQL = "";
            String commPRN = "";
            String fecha = "";
            long timeIni = 0;
            int i=1;
            try{
                
                if(pago != null && pago.getFecha() != null){
                    Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(pago.getFecha());
                    fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
                }
                
                commSQL = "{ ? = call    MIIUSACELL.PAMIIFACTURACIONSET.FNINSMETODOFACTURACION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call    MIIUSACELL.PAMIIFACTURACIONSET.FNINSMETODOFACTURACION("
                    +logId+","+pago.getNumReferencia()+","+pago.getUsuarioAdn()+","+pago.getTienda()+","+pago.getMonto()+","+pago.getOrigen()+","
                    +pago.getFolioElk()+","+pago.getCanal()+","+pago.getNumTransaccion()+","+pago.getFormaPago()+","+pago.getCuenta()
                    +","+fecha+","+tarjeta.getCardExpiryMonth()+","+tarjeta.getCardExpiryYear()+","+tarjeta.getCardNumber()
                    +",***,2121)}";
                Logger.write("     Operacion              : pagoFacturaLlamadaMetodo");
                Logger.write("   + Parametros             + ");
                Logger.write("     commPRN                : " + commPRN);
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, logId);
                    stmsOBJ.setString(i++, pago.getNumReferencia());
                    stmsOBJ.setString(i++, pago.getUsuarioAdn());
                    stmsOBJ.setString(i++, pago.getTienda());
                    stmsOBJ.setString(i++, pago.getMonto());
                    stmsOBJ.setString(i++, pago.getOrigen());
                    stmsOBJ.setString(i++, pago.getFolioElk());
                    stmsOBJ.setString(i++, pago.getCanal());
                    stmsOBJ.setString(i++, pago.getNumTransaccion());
                    stmsOBJ.setString(i++, pago.getFormaPago());
                    stmsOBJ.setString(i++, pago.getCuenta());
                    stmsOBJ.setString(i++, fecha);
                    stmsOBJ.setString(i++, tarjeta.getCardExpiryMonth());
                    stmsOBJ.setString(i++, tarjeta.getCardExpiryYear());
                    stmsOBJ.setString(i++, tarjeta.getCardNumber());
                    stmsOBJ.setInt(i++, 212121);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();  

                    rsetOBJ = stmsOBJ.getInt(1);

                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
        
        public int pagoFacturaSalida(final String numTx, final String folioPago, final String numAutBmx, final String exception) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            int rsetOBJ = -1;
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            try{
                
                commSQL = "{ ? = call    MIIUSACELL.PAMIIFACTURACIONSET.FNINSSALIDAFACTURACION(?,?,?,?,?)}";
                commPRN = "{ ? = call    MIIUSACELL.PAMIIFACTURACIONSET.FNINSSALIDAFACTURACION("
                    +numTx+","+folioPago+","+numAutBmx+","+exception+",2121)}";
                Logger.write("     Operacion              : pagoFacturaSalida");
                Logger.write("   + Parametros             + ");
                Logger.write("     commPRN                : " + commPRN);
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, numTx);
                    stmsOBJ.setString(i++, folioPago);
                    stmsOBJ.setString(i++, numAutBmx);
                    stmsOBJ.setString(i++, exception);
                    stmsOBJ.setInt(i++, 212121);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();  

                    rsetOBJ = stmsOBJ.getInt(1);

                    Logger.write("   + Respuesta              +");
                    Logger.write("     Respuesta              : " + rsetOBJ);               
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return rsetOBJ;
        }
      
      
        /**
         * Bitacora Fin
         */
        
        /**
         * Catalogos
         */
        public List<CatatoloGetVO> catalogoGet(final int catalogoId) throws ServiceException
        {       
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            List<CatatoloGetVO> listaObtieneCatalogos = new ArrayList<CatatoloGetVO>();
            CatatoloGetVO obtieneCatalogo = new CatatoloGetVO();
            ResultSet rs=null;
            long timeIni = 0;
            int i = 1;
            try
            {           
                commSQL = "{ ? = call  MIIUSACELL.PAMIIGET.FNMIICATALOGOGET(?)}";
                commPRN = "{ ? = call  MIIUSACELL.PAMIIGET.FNMIICATALOGOGET("
                + catalogoId + ")";
                
                Logger.write("     Operacion              : catalogoGet");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                Logger.write("     catalogoId             : " + catalogoId);
                
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR );
                    stmsOBJ.setInt(i++, catalogoId);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();
                    
                    rs=(ResultSet)stmsOBJ.getObject(1);

                    while (rs.next()) 
                    {
                        obtieneCatalogo = new CatatoloGetVO();
                        obtieneCatalogo.setTipoRespuestaID(rs.getInt(1));
                        obtieneCatalogo.setTipoRepuesta(rs.getString(2));
                        listaObtieneCatalogos.add(obtieneCatalogo);
                    }
                    
                    Logger.write("   + Respuesta              +");
                    Logger.write("     retorno.size           : " + listaObtieneCatalogos.size());              
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (rs != null) try {
                    rs.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return listaObtieneCatalogos;
        }
        
        public ValidarErrorTarjetaVO validarTarjeta(final int codigo) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            ValidarErrorTarjetaVO validarErr = new ValidarErrorTarjetaVO(); 
            
            try {
                commORA = "{ ? = call MIIUSACELL.PAMIIGET_RL.FNMIIVALIDAERRTARJETAGET(?,?,?,?) }";
                commPRN = "{ ? = call MIIUSACELL.PAMIIGET_RL.FNMIIVALIDAERRTARJETAGET(" + codigo + ") }";

                Logger.write("     Operacion              : validarTarjeta");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                stmsOBJ.registerOutParameter(1, OracleTypes.INTEGER);
                stmsOBJ.setInt(2, codigo);
                stmsOBJ.registerOutParameter(3, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(4, OracleTypes.VARCHAR);
                stmsOBJ.registerOutParameter(5, OracleTypes.VARCHAR);
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                
                validarErr.setDescIngles(stmsOBJ.getString(4));
                validarErr.setDescEspaniol(stmsOBJ.getString(5));

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + validarErr.getDescEspaniol());
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return validarErr;
        }
        
        
        /**
         * Gets Bitacoras
         */
        public List<SuspensionReactivacionVO> suspensionReactivacion(final String dn) throws ServiceException
        {       
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            
            List<SuspensionReactivacionVO> listSuspReac = new ArrayList<SuspensionReactivacionVO>(); 
            SuspensionReactivacionVO suspReac = new SuspensionReactivacionVO();
            
            String commSQL = "";    
            String commPRN = "";        
            ResultSet rs=null;
            int i = 1;
            long timeIni = 0;
            
            try{
                commSQL = "{ ? = call  MIIUSACELL.PAMIILOGGET.FNSUSPENSIONREACTIVACIONXDN(?,?,?,?)}";
                commPRN = "{ ? = call  MIIUSACELL.PAMIILOGGET.FNSUSPENSIONREACTIVACIONXDN("+dn+")}";
                
                Logger.write("     Operacion        : suspensionReactivacion");
                Logger.write("   + Parametros       +");
                Logger.write("     commPRN          : " + commPRN);
                Logger.write("     dn               : " + dn);
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}
                
                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setNull(i++, OracleTypes.NULL);
                    stmsOBJ.setNull(i++, OracleTypes.NULL);
                    stmsOBJ.setNull(i++, OracleTypes.NULL);
                    stmsOBJ.setString(i++, dn);
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();                                                      
                    Logger.write("   + Respuesta              +");
                    
                    rs = (ResultSet) stmsOBJ.getObject(1);
                    i = 1;
                    
                    while(rs.next()){
                        suspReac = new SuspensionReactivacionVO();
                        
                        suspReac.setFechaalta(rs.getString("FDFECHAALTA"));
                        suspReac.setDn(rs.getString("FCDN"));
                        suspReac.setLogid(rs.getString(3)); //FILOGID
                        suspReac.setUser(rs.getString("FCUSER"));
                        suspReac.setPass(rs.getString("FCPASS"));
                        suspReac.setReason(rs.getString("FCREASON"));
                        suspReac.setTipo(rs.getInt("TIPO"));
                        suspReac.setCompania(rs.getInt("FICOMPANIA"));
                        suspReac.setSistemaorigen(rs.getInt("FISISTEMAORIGEN"));
                        suspReac.setToken(rs.getString("FCTOKEN"));
                        suspReac.setRespuesta(rs.getString("RESPUESTA"));
                        suspReac.setMessagecode(rs.getString("FCMESSAGECODE"));
                        suspReac.setCdgerror(rs.getString("FCCDGERROR"));
                        suspReac.setMsjerror(rs.getString("FCMSJERROR"));
                        
                        listSuspReac.add(suspReac);
                    }
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if(rs != null){
                    try {
                        rs.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
          return listSuspReac;
        }
        /**
         * getBitacoras Fin
         */
        
        /**
         * Indicadores Ini
         */
        public CatalogoIndicadoresVO obtieneIndicadores(final int reporteId, final String unidadNegocio, 
                final int plataforma, final String fechaIni, final String fechaFin, final int tipolinea) throws ServiceException
        {       
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            ResultSet rs=null;
            String titulo = "";
            CatalogoIndicadoresVO response = new CatalogoIndicadoresVO();
            IndicadoresVO detalleSingle = new IndicadoresVO();
            List<IndicadoresVO> detalleList = new ArrayList<IndicadoresVO>();
            Date datecon = null;
            SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");

            
            
            long timeIni = 0;
            int i = 1;
            try
            {           
                commSQL = "{ ? = call  MIIUSACELL.PAMIIREPORTERIA.FNGETDATOSREPORTE(?,?,?,?,?,?)}";
                commPRN = "{ ? = call  MIIUSACELL.PAMIIREPORTERIA.FNGETDATOSREPORTE("
                + reporteId + "," 
                + unidadNegocio + ","
                + fechaIni + ","
                + fechaFin + ","
                + plataforma + ","
                + tipolinea + ")";
                
                Logger.write("     Operacion              : catalogoGet");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setInt(i++, reporteId);
                    if(unidadNegocio != null && unidadNegocio.equals(""))
                        stmsOBJ.setNull(i++, OracleTypes.NULL);
                    else
                        stmsOBJ.setString(i++, unidadNegocio);
                    
                    if(fechaIni != null && fechaIni.equals(""))
                        stmsOBJ.setNull(i++, OracleTypes.NULL);
                    else
                        stmsOBJ.setString(i++, fechaIni);
                    
                    if(fechaFin != null && fechaFin.equals(""))
                        stmsOBJ.setNull(i++, OracleTypes.NULL);
                    else
                        stmsOBJ.setString(i++, fechaFin);
                    
                    stmsOBJ.setInt(i++, plataforma);
                    stmsOBJ.setInt(i++, tipolinea);
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();
                    
                    rs=(ResultSet)stmsOBJ.getObject(1);
                    
                    
                    int contador = 1;
                    
                    while(rs.next())
                    {
                        detalleSingle = new IndicadoresVO();
                        
                        if (contador ==1){
                            titulo = rs.getString(1);
                        }else{

                            detalleSingle.setCantidad(rs.getString(1));  
                            detalleSingle.setCategoria(rs.getString(2));
                            detalleSingle.setCompania(rs.getString(3));
                            detalleSingle.setDinero(rs.getString(4));

                            if(rs.getString(5) != null && !rs.getString(5).equals("")){
                                datecon = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH).parse(rs.getString(5));
                                detalleSingle.setFecha(sdf.format(datecon));
                            }else{
                                detalleSingle.setFecha("");
                            }
                            detalleSingle.setHora(rs.getString(6));
                            detalleSingle.setPlataforma(rs.getString(7));
                            detalleSingle.setTipoLinea(rs.getString(8));

                            detalleList.add(detalleSingle);
                        }
                        contador++;
                    }

                }
                
                
                response.setDatos(detalleList);
                response.setReporteID(reporteId);
                response.setTitulo(titulo);
                Logger.write("   + Respuesta              +");
                Logger.write("   + Size                   : " + response.getDatos().size());
                
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (rs != null) try {
                    rs.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.write("     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return response;
        }
        /**
         * Indicadores Fin
         */
        

        public boolean erroresApp (final String DN, final String fechaError, final int compania, final int sistemaOrigen, final int tipoLinea, final int tipoDispositivo, final String versionSistema, final int tipoError, final String descError, final String metodoApp, final String metodoServicioWeb) throws  ServiceException
        {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            boolean respuesta = false;
            int resultado =  -1;
            String commSQL = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            try{
                
                commSQL = "{ ? = call    MIIUSACELL.PAMIISET1.FNINSERRORAPLICACION(?,?,?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call    MIIUSACELL.PAMIISET1.FNINSERRORAPLICACION("
                    +DN+","+fechaError+","+compania+","+sistemaOrigen+","+tipoLinea+","+tipoDispositivo+","+versionSistema+","+tipoError+","+descError+","+metodoApp+","+metodoServicioWeb+")}";
                        
                Logger.write("     Operacion                 : er_roresApp");
                Logger.write("   + Parametros                +");
                Logger.write("     commPRN                   : " + commPRN);
                Logger.write("     DN                        : " + DN);
                Logger.write("     fechaEr_ror                : " + fechaError);
                Logger.write("     compania                  : " + compania);
                Logger.write("     sistemaOrigen             : " + sistemaOrigen);
                Logger.write("     tipoLinea                 : " + tipoLinea);
                Logger.write("     tipoDispositivo           : " + tipoDispositivo);
                Logger.write("     versionSistema            : " + versionSistema);
                Logger.write("     tipoEr_ror                 : " + tipoError);
                Logger.write("     descEr_ror                 : " + descError);
                Logger.write("     metodoApp                 : " + metodoApp);
                Logger.write("     metodoServicioWeb         : " + metodoServicioWeb);
                
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++,DN); 
                    stmsOBJ.setString(i++,fechaError);
                    stmsOBJ.setInt(i++,compania);
                    stmsOBJ.setInt(i++,sistemaOrigen);
                    stmsOBJ.setInt(i++,tipoLinea);
                    stmsOBJ.setInt(i++,tipoDispositivo);
                    stmsOBJ.setString(i++,versionSistema);
                    stmsOBJ.setInt(i++,tipoError);
                    stmsOBJ.setString(i++,descError);
                    stmsOBJ.setString(i++,metodoApp);
                    stmsOBJ.setString(i++,metodoServicioWeb);
                                        
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();  

                    if(stmsOBJ != null){
                        resultado = stmsOBJ.getInt(1);
                        if(resultado == 0)
                            respuesta = true;
                        else
                            respuesta = false;
                    }   
                    
                    Logger.write("   + Respuesta              +");
                    Logger.write("     Datos                  : " + respuesta);             
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        
        
     /**
         * Funciones de servicio
         */
        public String getBloqueComun(final String query) throws ServiceException {
            String sqlEnv = null;
            String checkBoxvalue = "false";
            String dbmsOutputon = "";
            CallableStatement cstmt = null;
            ResultSet rs = null;

            String driverBD = null;
            String ulrJDBC = null;
            String userBD = null;
            String passBD = null;
            try {
                driverBD = "oracle.jdbc.OracleDriver";

                String[] parametrosConexion = formatter.getParametrosConexion();
                ulrJDBC = parametrosConexion[0];
                userBD = parametrosConexion[1];
                passBD = parametrosConexion[2];

                Class.forName (driverBD).newInstance ();
                conecta = DriverManager.getConnection (ulrJDBC, userBD, passBD);
            } catch (Exception e) {
                dbmsOutputon = e.getLocalizedMessage();
            }

            try {

                sqlEnv = query.replace("\r\n", " ");

                if (checkBoxvalue.equals("true")) {
                    sqlEnv = "declare salida varchar2(32000); linea varchar2(2500);  status integer :=0; BEGIN DBMS_OUTPUT.enable; "
                        + sqlEnv
                        + " while( status = 0 ) loop dbms_output.get_line( linea,status ); "
                        + "salida := salida || linea ||chr(10)|| chr(13); end loop; "
                        + "? := salida; end;";
                } // if recuperar salida de dbms_output.
                cstmt = conecta.prepareCall(sqlEnv);

                if (checkBoxvalue.equals("true")) {
                    cstmt.registerOutParameter(1, OracleTypes.VARCHAR);
                }
                cstmt.execute();

                if (checkBoxvalue.equals("true")) {
                    dbmsOutputon = cstmt.getString(1);
                }

            } catch (Exception e) {
                dbmsOutputon = e.getLocalizedMessage();

            } finally {
                collector(rs, cstmt, conecta);

            }

            return dbmsOutputon;
        }
        
        public LinkedList<ConsultaGenerica> getCursorComun(final String query) throws ServiceException {

            //Creamos la conexion

            String driverBD = null;
            String ulrJDBC = null;
            String userBD = null;
            String passBD = null;
            List<String> lstgenerica = null;
            LinkedList<ConsultaGenerica> contenido = null;
            try {
                driverBD = "oracle.jdbc.OracleDriver";

                String[] parametrosConexion = formatter.getParametrosConexion();
                ulrJDBC = parametrosConexion[0];
                userBD = parametrosConexion[1];
                passBD = parametrosConexion[2];

                Class.forName (driverBD).newInstance ();
                conecta = DriverManager.getConnection (ulrJDBC, userBD, passBD);
            } catch (Exception e) {
                contenido = new LinkedList<ConsultaGenerica>();
                ConsultaGenerica gBean = null;
                lstgenerica = new ArrayList<String>();
                gBean = new ConsultaGenerica();
                lstgenerica.add(e.getLocalizedMessage());
                gBean.setContenido(lstgenerica);
                contenido.add(gBean);
            }

            //M?todo para realizar cualquier query INSERT, UPDATE, DELETE, SELECT, ETC
            CallableStatement statements = null;
            ResultSet resultsets = null;
            ResultSetMetaData metaData = null;
            int cuentaColumnas = 0;
            String cadgenerica[];


            try {
                String          functionSQL = "";
                functionSQL = "{ ? = call " + query + "}";
                statements = conecta.prepareCall (functionSQL);
                statements.registerOutParameter (1, OracleTypes.CURSOR);
                statements.execute ();
                resultsets = (ResultSet) statements.getObject (1);

                metaData = resultsets.getMetaData();
                //System.out.println(metaData.toString());
                cuentaColumnas = metaData.getColumnCount();
                cadgenerica = new String[cuentaColumnas + 1];

                contenido = new LinkedList<ConsultaGenerica>();
                ConsultaGenerica gBean = null;
                while (resultsets.next()) {
                    lstgenerica = new ArrayList<String>();
                    gBean = new ConsultaGenerica();
                    for (int i = 1; i <= cuentaColumnas; i++) {
                        cadgenerica[i] = resultsets.getString(i);
                        lstgenerica.add(cadgenerica[i]);
                    }
                    gBean.setContenido(lstgenerica);
                    contenido.add(gBean);
                }
                resultsets.close ();
                conecta.close ();
            } catch (Exception e) {
                contenido = new LinkedList<ConsultaGenerica>();
                ConsultaGenerica gBean = null;
                lstgenerica = new ArrayList<String>();
                gBean = new ConsultaGenerica();
                lstgenerica.add(e.getLocalizedMessage());
                gBean.setContenido(lstgenerica);
                contenido.add(gBean);
            } finally {
                collector(resultsets, statements, conecta); 
            }
            return contenido;
        }

        @SuppressWarnings("unused")
        public LinkedList<ConsultaGenerica> getQueryComun(String query) throws ServiceException {
            String driverBD = null;
            String ulrJDBC = null;
            String userBD = null;
            String passBD = null; 
            List<String> lstgenerica = null;
            LinkedList<ConsultaGenerica> contenido = null;  
            try {
                driverBD = "oracle.jdbc.OracleDriver";

                String[] parametrosConexion = formatter.getParametrosConexion();
                ulrJDBC = parametrosConexion[0];
                userBD = parametrosConexion[1];
                passBD = parametrosConexion[2];

                Class.forName (driverBD).newInstance ();
                conecta = DriverManager.getConnection (ulrJDBC, userBD, passBD);
            } catch (Exception e) {
                contenido = new LinkedList<ConsultaGenerica>();
                ConsultaGenerica gBean = null;
                lstgenerica = new ArrayList<String>();
                gBean = new ConsultaGenerica();
                lstgenerica.add(e.getLocalizedMessage());
                gBean.setContenido(lstgenerica);
                contenido.add(gBean);
            }
            //M?todo para realizar cualquier query INSERT, UPDATE, DELETE, SELECT, ETC
            Statement statements = null;
            ResultSet resultsets = null;

            ResultSetMetaData metaData = null;
            int cuentaColumnas = 0;
            String cadgenerica[];
            ArrayList<String> resultados =  new ArrayList<String>();

            try {
                query = query.replace ("\n", " ");
                query = query.replace ("\r", " ");
                query = query.toUpperCase().trim();
                statements = conecta.createStatement ();
                resultsets = statements.executeQuery (query);
                metaData = resultsets.getMetaData();
                cuentaColumnas = metaData.getColumnCount();
                cadgenerica = new String[cuentaColumnas + 1];

                contenido = new LinkedList<ConsultaGenerica>();
                ConsultaGenerica gBean = null;
                while (resultsets.next()) {
                    lstgenerica = new ArrayList<String>();
                    gBean = new ConsultaGenerica();
                    for (int i = 1; i <= cuentaColumnas; i++) {
                        cadgenerica[i] = resultsets.getString(i);
                        lstgenerica.add(cadgenerica[i]);
                    }
                    gBean.setContenido(lstgenerica);
                    contenido.add(gBean);
                }

            } catch (Exception e) {
                contenido = new LinkedList<ConsultaGenerica>();
                ConsultaGenerica gBean = null;
                lstgenerica = new ArrayList<String>();
                gBean = new ConsultaGenerica();
                lstgenerica.add(e.getLocalizedMessage());
                gBean.setContenido(lstgenerica);
                contenido.add(gBean);
            } finally {
                collector(resultsets, statements, conecta); 
            }

            return contenido;
        }

        public String getLogInfo(final String filtro, String archivoLog){
            String respuesta = "";
            String busqueda = filtro == null ? "" : filtro; 
            try {
                StringBuilder  stringBuilder = new StringBuilder();
                File logDir = new File(System.getProperty("jboss.server.log.dir") + "/" + archivoLog);
                File logTemp = null;
                BufferedReader reader = null;
                if(logDir.exists() && logDir.isFile())
                {
                    try {
                        String destino = System.getProperty("jboss.server.log.dir") + "logProvi.txt";
                        String origen = System.getProperty("jboss.server.log.dir") + archivoLog;
                        String yes = "awk " + filtro + origen + " > " + destino;
                        Runtime.getRuntime().exec(yes);
                        archivoLog = "logProvi.txt";
                        logDir = new File(System.getProperty("jboss.server.log.dir") + "/" + archivoLog);
                        
                        logTemp = File.createTempFile("logProv", ".txt");
                        FileCopyUtils.copy(logDir, logTemp);
                        reader = new BufferedReader( new FileReader (logTemp.getAbsolutePath()));
                        String line = "";

                        while((line = reader.readLine()) != null){
                            if(line.contains(busqueda)){
                                stringBuilder.append(line);
                                stringBuilder.append(System.getProperty("line.separator"));
                            }
                        }
                        reader.close();
                        if(logTemp.exists()){
                            reader.close();
                            logTemp.delete();
                        }
                        respuesta = stringBuilder.toString().isEmpty() ? "Sin coincidencias" : stringBuilder.toString();
                    }
                    catch (IOException e) {
                        respuesta = "[Detail]: " + e.getMessage();
                    }
                    finally{
                        if(reader != null)
                            reader.close();
                        if(logTemp != null && logTemp.exists())
                            logTemp.delete();
                    }
                }
                else{
                    respuesta = "[Detail]: Archivo invalido";
                }
            }catch (Exception e) {
                respuesta = "[Detail]: " + e.getMessage();
            }
            return respuesta;
        }

        public String getLogDir(){
            String respuesta = "";
            try {
                StringBuilder  stringBuilder = new StringBuilder();
                File logDirParent = new File(System.getProperty("jboss.server.log.dir"));
                String[] dirs = logDirParent.list();

                for(int i=0; i<dirs.length;i++){
                    stringBuilder.append(dirs[i]);
                    stringBuilder.append(System.getProperty("line.separator"));
                }
                
                respuesta = (stringBuilder.toString().equalsIgnoreCase("") ? "Sin Archivos" : stringBuilder.toString());
            }catch (Exception e) {
                respuesta = "[Detail]: " + e.getMessage();
            }
            return respuesta;
        }
        
        /** funciones genericas **/ 
        private void collector(final ResultSet rsetOBJ, final Statement stmsOBJ, final Connection connOBJ) {
            try {
                if (rsetOBJ != null) rsetOBJ.close();
                if (stmsOBJ != null) stmsOBJ.close();
                if (connOBJ != null && !connOBJ.isClosed()) connOBJ.close();
            }
            catch (SQLException e) {
                //              log.info(" [ERR] :: (-) commERR :: " + e.getMessage());
            }
        }
        
        public List<TipoServicioVO>  getTipoServicio(final int tipo) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i = 1;
            TipoServicioVO tiposervicio = new TipoServicioVO(); 
            List<TipoServicioVO> listTipoServicio = new ArrayList<TipoServicioVO>();
            try {
                commORA = "{ ? = call MIIUSACELL.PAMIIEXECATGET.FNTIPOSERVICIOGET(?,?)}";
                commPRN = "{ ? = call MIIUSACELL.PAMIIEXECATGET.FNTIPOSERVICIOGET(" + tipo + ")}";

                Logger.write("     Operacion              : getTipoServicio");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                stmsOBJ.setInt(i++, tipo);
                stmsOBJ.setNull(i++, OracleTypes.NULL);
                
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                while(rsetOBJ.next()){
                    tiposervicio = new TipoServicioVO();
                    tiposervicio.setServicio(rsetOBJ.getString(2));
                    tiposervicio.setDescripcion(rsetOBJ.getString(4));
                    listTipoServicio.add(tiposervicio);
                }

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + listTipoServicio.size());
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return listTipoServicio;
        }   
        
        public int  setConfiguracionXUsuarioMovil(final String dn, final String descripcion) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i = 1;
            int retorno = -1;
            
            try {
                commORA = "{ ? = call MIIUSACELL.PAMIISET2.FNINSMIICONFIGURAXUSRMOVIL(?,?,?,?)}";
                commPRN = "{ ? = call MIIUSACELL.PAMIISET2.FNINSMIICONFIGURAXUSRMOVIL(" + dn + ","+ descripcion +",2021,0)}";

                Logger.write("     Operacion              : setConfiguracionXUsuarioMovil");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setString(i++, descripcion);
                stmsOBJ.setInt(i++, 212121);
                stmsOBJ.setInt(i++, 0);
                
                Logger.write("     Ejecutando consulta     :");
                
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                
                retorno = stmsOBJ.getInt(1);    
                
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"  setConfiguracionXUsuarioMovil ::  Tiempo ejecucion       :: " + Util.tipoRespuesta(timeIni));
            return retorno;
        }   
        
        public  List<ConfiguracionXUsuarioMovilVO>  getConfiguracionXUsuarioMovil(final String dn) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i = 1;
            ConfiguracionXUsuarioMovilVO configuracionXUsuario =  new ConfiguracionXUsuarioMovilVO();
            List<ConfiguracionXUsuarioMovilVO> listConfiguracionXUsuarioMovil = new ArrayList<ConfiguracionXUsuarioMovilVO>();
            
            
            try {
                commORA = "{ ? = call MIIUSACELL.PAMIIGET2.FNGETMIICONFIGURAXUSRMOVIL(?)}";
                commPRN = "{ ? = call MIIUSACELL.PAMIIGET2.FNGETMIICONFIGURAXUSRMOVIL("+ dn +")}";

                Logger.write("     Operacion              : getConfiguracionXUsuarioMovil");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                stmsOBJ.setString(i++, dn);
                
                Logger.write("     Ejecutando consulta     :");
                
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                
                rsetOBJ=(ResultSet)stmsOBJ.getObject(1);

                while (rsetOBJ.next()) 
                {
                    configuracionXUsuario =  new ConfiguracionXUsuarioMovilVO();
                    
                    configuracionXUsuario.setDn(rsetOBJ.getString("FCDN"));
                    configuracionXUsuario.setDescripcion(rsetOBJ.getString("FCDESCRIPCION"));
                
                    listConfiguracionXUsuarioMovil.add(configuracionXUsuario);
                }
                
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + listConfiguracionXUsuarioMovil.size());
                
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"  getConfiguracionXUsuarioMovil ::  Tiempo ejecucion       :: " + Util.tipoRespuesta(timeIni));
            return listConfiguracionXUsuarioMovil;
        }       
        
        
        public List<ConsumoFechaTablaVO> getDetallesDivisionLlamadas(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> getDetallesDivision :: C o n s u l t a  a  B a s e  d e  D a t o s C o n s u m o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            String numero = "";
            String telefono = "";
            String textoFormateado = "";
            String operador = "";
            ConsumoDetalleTablaVO consumo = new ConsumoDetalleTablaVO();
            List<ConsumoDetalleTablaVO> consumoList = new ArrayList<ConsumoDetalleTablaVO>();
            ConsumoFechaTablaVO consumoFecha = new ConsumoFechaTablaVO();
            List<ConsumoFechaTablaVO> consumoFechaList = new ArrayList<ConsumoFechaTablaVO>();
            SimpleDateFormat dateFormat = null;
            Date dateToHrs = new Date();
            try{
                
                int varTimeOut = 20; 

                try{
                   varTimeOut = Integer.parseInt(getValorParametro(118));
                }catch (Exception e) {
                    varTimeOut = 20;
                }
                
                dateFormat = new SimpleDateFormat(getValorParametro(101));
                
                commSQL = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSDET.FNGETDETALLEGENERAL(?,?,?)}";
                commPRN = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSDET.FNGETDETALLEGENERAL("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                
                for(int cont = 0; cont < intentos; cont++)
                {
                    try
                    {
                        connOBJ = OracleConnection.getConnectionConsumos(new MensajeLogBean());
                        Logger.write("     Conexion BD Abierta    : true");

                        if(connOBJ != null) 
                            stmsOBJ = connOBJ.prepareCall(commSQL);

                        stmsOBJ.setQueryTimeout(varTimeOut);

                        Logger.write("     Conexion BD Abierta    : true");
                        i = 1;
                        stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                        stmsOBJ.setString(i++, dn);
                        stmsOBJ.setString(i++, fechaIni);
                        stmsOBJ.setString(i++, fechaFin);



                        Logger.write("     Ejecutando consulta    intento[" + (cont+1) +"] :");
                        timeIni = System.currentTimeMillis();
                        stmsOBJ.execute();  
                        rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                        break;
                    }
                    catch (Exception e) 
                    {
                        boolean reintentar = Utilerias.contieneMensajeError(e.getLocalizedMessage());                       
                        if(cont < (intentos-1) && reintentar)
                        {
                            Logger.write("     [WARN]  : (SQLException) " + e.getLocalizedMessage());
                            if(connOBJ != null) try {
                                connOBJ.close();    
                                Logger.write("     Conexion BD Abierta    : false");
                            }catch (Exception e1) {
                                Logger.write("     Detail  : (SQLException) " + e1.getMessage());   
                            }
                            continue;
                        }
                        else
                        {
                            throw new ServiceException(e.getLocalizedMessage());
                        }
                    }                   
                }
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleTablaVO();
                    consumoList = new ArrayList<ConsumoDetalleTablaVO>();
                    consumoFecha = new ConsumoFechaTablaVO();
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    try{
                        if(rsetOBJ.getString("OPERADOR_ORIGEN") != null){
                            operador = rsetOBJ.getString("OPERADOR_ORIGEN");
                        }
                    }catch (Exception e)
                    {operador = "";}
                    textoFormateado = rsetOBJ.getString("OPERADOR_DESTINO");
                    if(textoFormateado != null && textoFormateado.toLowerCase().trim().equals("no identificado"))
                        consumo.setCompania("ND");
                    else
                        consumo.setCompania(Utilerias.formatoCadena(textoFormateado));
                    consumo.setCosto("0");
                    consumo.setDestino(Utilerias.formatoCadena(rsetOBJ.getString("DESTINO")));
                    if(rsetOBJ.getString("HORA_EVENTO") != null && !rsetOBJ.getString("HORA_EVENTO").trim().equals("")){
                        dateToHrs = dateFormat.parse(rsetOBJ.getString("HORA_EVENTO"));
                        consumo.setHora(new SimpleDateFormat("HH:mm").format(dateToHrs));
                    }
                    consumo.setIdColumna(1);
                    
                    numero = rsetOBJ.getString("NUM_DESTINO");
                    if(numero != null && numero.length() >= 10){
                        telefono = numero.substring(numero.length()-10,numero.length());
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }else{
                        telefono = numero;
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }
                    consumo.setNumero(telefono);
                    consumo.setTiempo(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setMbConsumidos(rsetOBJ.getString("DUR_MIN_REDN"));
                    
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    String tipoEvento = "1";
                    if(tipoEvento.equals("5") || tipoEvento.equals("6")){
                        if(tipoEvento.equals("5") &&  (consumo.getCompania().trim().equals("Iusacell") || consumo.getCompania().trim().equals("Unefon"))){
                            consumoFechaList.add(consumoFecha);
                        }
                        else if(tipoEvento.equals("6") &&  (!consumo.getCompania().trim().equals("Iusacell") && !consumo.getCompania().trim().equals("Unefon"))){
                            consumoFechaList.add(consumoFecha);
                        }
                    }else{
                        if(tipoEvento.equals("4") && operador.trim().toLowerCase().equals("no identificado")){
                            consumoFecha = new ConsumoFechaTablaVO();
                        }else{
                            consumoFechaList.add(consumoFecha);
                        }
                    }
                }
                

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        public List<ConsumoFechaTablaVO> getDetallesDivisionSMS(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> getDetallesDivision :: C o n s u l t a  a  B a s e  d e  D a t o s C o n s u m o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            String numero = "";
            String telefono = "";
            String textoFormateado = "";
            String operador = "";
            ConsumoDetalleTablaVO consumo = new ConsumoDetalleTablaVO();
            List<ConsumoDetalleTablaVO> consumoList = new ArrayList<ConsumoDetalleTablaVO>();
            ConsumoFechaTablaVO consumoFecha = new ConsumoFechaTablaVO();
            List<ConsumoFechaTablaVO> consumoFechaList = new ArrayList<ConsumoFechaTablaVO>();
            SimpleDateFormat dateFormat = null;
            Date dateToHrs = new Date();
            try{
                
                int varTimeOut = 20; 

                try{
                   varTimeOut = Integer.parseInt(getValorParametro(118));
                }catch (Exception e) {
                    varTimeOut = 20;
                }
                
                dateFormat = new SimpleDateFormat(getValorParametro(101));
                
                commSQL = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSDET.FNGETDETALLEGRALSMSBIC(?,?,?)}";
                commPRN = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSDET.FNGETDETALLEGRALSMSBIC("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                
                for(int cont = 0; cont < intentos; cont++)
                {
                    try
                    {
                        connOBJ = OracleConnection.getConnectionConsumos(new MensajeLogBean());
                        Logger.write("     Conexion BD Abierta    : true");

                        if(connOBJ != null) 
                            stmsOBJ = connOBJ.prepareCall(commSQL);

                        stmsOBJ.setQueryTimeout(varTimeOut);

                        Logger.write("     Conexion BD Abierta    : true");
                        i = 1;
                        stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                        stmsOBJ.setString(i++, dn);
                        stmsOBJ.setString(i++, fechaIni);
                        stmsOBJ.setString(i++, fechaFin);



                        Logger.write("     Ejecutando consulta    intento[" + (cont+1) +"] :");
                        timeIni = System.currentTimeMillis();
                        stmsOBJ.execute();  
                        rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                        break;
                    }
                    catch (Exception e) 
                    {
                        boolean reintentar = Utilerias.contieneMensajeError(e.getLocalizedMessage());                       
                        if(cont < (intentos-1) && reintentar)
                        {
                            Logger.write("     [WARN]  : (SQLException) " + e.getLocalizedMessage());
                            if(connOBJ != null) try {
                                connOBJ.close();    
                                Logger.write("     Conexion BD Abierta    : false");
                            }catch (Exception e1) {
                                Logger.write("     Detail  : (SQLException) " + e1.getMessage());   
                            }
                            continue;
                        }
                        else
                        {
                            throw new ServiceException(e.getLocalizedMessage());
                        }
                    }                   
                }
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleTablaVO();
                    consumoList = new ArrayList<ConsumoDetalleTablaVO>();
                    consumoFecha = new ConsumoFechaTablaVO();
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    try{
                        if(rsetOBJ.getString("OPERADOR_ORIGEN") != null){
                            operador = rsetOBJ.getString("OPERADOR_ORIGEN");
                        }
                    }catch (Exception e)
                    {operador = "";}
                    textoFormateado = rsetOBJ.getString("OPERADOR_DESTINO");
                    if(textoFormateado != null && textoFormateado.toLowerCase().trim().equals("no identificado"))
                        consumo.setCompania("ND");
                    else
                        consumo.setCompania(Utilerias.formatoCadena(textoFormateado));
                    consumo.setCosto("0");
                    consumo.setDestino(Utilerias.formatoCadena(rsetOBJ.getString("DESTINO")));
                    if(rsetOBJ.getString("HORA_EVENTO") != null && !rsetOBJ.getString("HORA_EVENTO").trim().equals("")){
                        dateToHrs = dateFormat.parse(rsetOBJ.getString("HORA_EVENTO"));
                        consumo.setHora(new SimpleDateFormat("HH:mm").format(dateToHrs));
                    }
                    consumo.setIdColumna(1);
                    
                    numero = rsetOBJ.getString("NUM_DESTINO");
                    if(numero != null && numero.length() >= 10){
                        telefono = numero.substring(numero.length()-10,numero.length());
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }else{
                        telefono = numero;
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }
                    consumo.setNumero(telefono);
                    consumo.setTiempo(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setMbConsumidos(rsetOBJ.getString("DUR_MIN_REDN"));
                    
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    
                    String tipoEvento = "2";
                    if(tipoEvento.equals("5") || tipoEvento.equals("6")){
                        if(tipoEvento.equals("5") &&  (consumo.getCompania().trim().equals("Iusacell") || consumo.getCompania().trim().equals("Unefon"))){
                            consumoFechaList.add(consumoFecha);
                        }
                        else if(tipoEvento.equals("6") &&  (!consumo.getCompania().trim().equals("Iusacell") && !consumo.getCompania().trim().equals("Unefon"))){
                            consumoFechaList.add(consumoFecha);
                        }
                    }else{
                        if(tipoEvento.equals("4") && operador.trim().toLowerCase().equals("no identificado")){
                            consumoFecha = new ConsumoFechaTablaVO();
                        }else{
                            consumoFechaList.add(consumoFecha);
                        }
                    }
                }
                

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        public List<ConsumoFechaTablaVO> getDetallesDivisionDatos(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> getDetallesDivision :: C o n s u l t a  a  B a s e  d e  D a t o s C o n s u m o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            String numero = "";
            String telefono = "";
            String textoFormateado = "";
            String operador = "";
            ConsumoDetalleTablaVO consumo = new ConsumoDetalleTablaVO();
            List<ConsumoDetalleTablaVO> consumoList = new ArrayList<ConsumoDetalleTablaVO>();
            ConsumoFechaTablaVO consumoFecha = new ConsumoFechaTablaVO();
            List<ConsumoFechaTablaVO> consumoFechaList = new ArrayList<ConsumoFechaTablaVO>();
            SimpleDateFormat dateFormat = null;
            Date dateToHrs = new Date();
            try{
                
                int varTimeOut = 20; 

                try{
                   varTimeOut = Integer.parseInt(getValorParametro(118));
                }catch (Exception e) {
                    varTimeOut = 20;
                }
                
                dateFormat = new SimpleDateFormat(getValorParametro(101));
                
                commSQL = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSDET.FNGETDETALLEGRALDATOBIC(?,?,?)}";
                commPRN = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSDET.FNGETDETALLEGRALDATOBIC("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                
                for(int cont = 0; cont < intentos; cont++)
                {
                    try
                    {
                        connOBJ = OracleConnection.getConnectionConsumos(new MensajeLogBean());
                        Logger.write("     Conexion BD Abierta    : true");

                        if(connOBJ != null) 
                            stmsOBJ = connOBJ.prepareCall(commSQL);

                        stmsOBJ.setQueryTimeout(varTimeOut);

                        Logger.write("     Conexion BD Abierta    : true");
                        i = 1;
                        stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                        stmsOBJ.setString(i++, dn);
                        stmsOBJ.setString(i++, fechaIni);
                        stmsOBJ.setString(i++, fechaFin);



                        Logger.write("     Ejecutando consulta    intento[" + (cont+1) +"] :");
                        timeIni = System.currentTimeMillis();
                        stmsOBJ.execute();  
                        rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                        break;
                    }
                    catch (Exception e) 
                    {
                        boolean reintentar = Utilerias.contieneMensajeError(e.getLocalizedMessage());                       
                        if(cont < (intentos-1) && reintentar)
                        {
                            Logger.write("     [WARN]  : (SQLException) " + e.getLocalizedMessage());
                            if(connOBJ != null) try {
                                connOBJ.close();    
                                Logger.write("     Conexion BD Abierta    : false");
                            }catch (Exception e1) {
                                Logger.write("     Detail  : (SQLException) " + e1.getMessage());   
                            }
                            continue;
                        }
                        else
                        {
                            throw new ServiceException(e.getLocalizedMessage());
                        }
                    }                   
                }
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleTablaVO();
                    consumoList = new ArrayList<ConsumoDetalleTablaVO>();
                    consumoFecha = new ConsumoFechaTablaVO();
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    try{
                        if(rsetOBJ.getString("OPERADOR_ORIGEN") != null){
                            operador = rsetOBJ.getString("OPERADOR_ORIGEN");
                        }
                    }catch (Exception e)
                    {operador = "";}
                    textoFormateado = rsetOBJ.getString("OPERADOR_DESTINO");
                    if(textoFormateado != null && textoFormateado.toLowerCase().trim().equals("no identificado"))
                        consumo.setCompania("ND");
                    else
                        consumo.setCompania(Utilerias.formatoCadena(textoFormateado));
                    consumo.setCosto("0");
                    consumo.setDestino(Utilerias.formatoCadena(rsetOBJ.getString("DESTINO")));
                    if(rsetOBJ.getString("HORA_EVENTO") != null && !rsetOBJ.getString("HORA_EVENTO").trim().equals("")){
                        dateToHrs = dateFormat.parse(rsetOBJ.getString("HORA_EVENTO"));
                        consumo.setHora(new SimpleDateFormat("HH:mm").format(dateToHrs));
                    }
                    consumo.setIdColumna(1);
                    
                    numero = rsetOBJ.getString("NUM_DESTINO");
                    if(numero != null && numero.length() >= 10){
                        telefono = numero.substring(numero.length()-10,numero.length());
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }else{
                        telefono = numero;
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }
                    consumo.setNumero(telefono);
                    consumo.setTiempo(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setMbConsumidos(rsetOBJ.getString("DUR_MIN_REDN"));
                    
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    String tipoEvento = "4";
                    if(tipoEvento.equals("5") || tipoEvento.equals("6")){
                        if(tipoEvento.equals("5") &&  (consumo.getCompania().trim().equals("Iusacell") || consumo.getCompania().trim().equals("Unefon"))){
                            consumoFechaList.add(consumoFecha);
                        }
                        else if(tipoEvento.equals("6") &&  (!consumo.getCompania().trim().equals("Iusacell") && !consumo.getCompania().trim().equals("Unefon"))){
                            consumoFechaList.add(consumoFecha);
                        }
                    }else{
                        if(tipoEvento.equals("4") && operador.trim().toLowerCase().equals("no identificado")){
                            consumoFecha = new ConsumoFechaTablaVO();
                        }else{
                            consumoFechaList.add(consumoFecha);
                        }
                    }
                }
                

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        
        public List<ConsumoFechaTablaVO> getDetallesDivisionComunidad(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> getDetallesDivision :: C o n s u l t a  a  B a s e  d e  D a t o s C o n s u m o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            String numero = "";
            String telefono = "";
            String textoFormateado = "";
            String operador = "";
            ConsumoDetalleTablaVO consumo = new ConsumoDetalleTablaVO();
            List<ConsumoDetalleTablaVO> consumoList = new ArrayList<ConsumoDetalleTablaVO>();
            ConsumoFechaTablaVO consumoFecha = new ConsumoFechaTablaVO();
            List<ConsumoFechaTablaVO> consumoFechaList = new ArrayList<ConsumoFechaTablaVO>();
            SimpleDateFormat dateFormat = null;
            Date dateToHrs = new Date();
            try{
                
                int varTimeOut = 20; 

                try{
                   varTimeOut = Integer.parseInt(getValorParametro(118));
                }catch (Exception e) {
                    varTimeOut = 20;
                }
                
                dateFormat = new SimpleDateFormat(getValorParametro(101));
                
                commSQL = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSDET.FNGETDETALLEGENERALIUSA(?,?,?)}";
                commPRN = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSDET.FNGETDETALLEGENERALIUSA("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                
                for(int cont = 0; cont < intentos; cont++)
                {
                    try
                    {
                        connOBJ = OracleConnection.getConnectionConsumos(new MensajeLogBean());
                        Logger.write("     Conexion BD Abierta    : true");

                        if(connOBJ != null) 
                            stmsOBJ = connOBJ.prepareCall(commSQL);

                        stmsOBJ.setQueryTimeout(varTimeOut);

                        Logger.write("     Conexion BD Abierta    : true");
                        i = 1;
                        stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                        stmsOBJ.setString(i++, dn);
                        stmsOBJ.setString(i++, fechaIni);
                        stmsOBJ.setString(i++, fechaFin);

                        Logger.write("     Ejecutando consulta    intento[" + (cont+1) +"] :");
                        timeIni = System.currentTimeMillis();
                        stmsOBJ.execute();  
                        rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                        break;
                    }
                    catch (Exception e) 
                    {
                        boolean reintentar = Utilerias.contieneMensajeError(e.getLocalizedMessage());                       
                        if(cont < (intentos-1) && reintentar)
                        {
                            Logger.write("     [WARN]  : (SQLException) " + e.getLocalizedMessage());
                            if(connOBJ != null) try {
                                connOBJ.close();    
                                Logger.write("     Conexion BD Abierta    : false");
                            }catch (Exception e1) {
                                Logger.write("     Detail  : (SQLException) " + e1.getMessage());   
                            }
                            continue;
                        }
                        else
                        {
                            throw new ServiceException(e.getLocalizedMessage());
                        }
                    }                   
                }   
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleTablaVO();
                    consumoList = new ArrayList<ConsumoDetalleTablaVO>();
                    consumoFecha = new ConsumoFechaTablaVO();
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    try{
                        if(rsetOBJ.getString("OPERADOR_ORIGEN") != null){
                            operador = rsetOBJ.getString("OPERADOR_ORIGEN");
                        }
                    }catch (Exception e)
                    {operador = "";}
                    Logger.write("     operador               " + operador);
                    textoFormateado = rsetOBJ.getString("OPERADOR_DESTINO");
                    if(textoFormateado != null && textoFormateado.toLowerCase().trim().equals("no identificado"))
                        consumo.setCompania("ND");
                    else
                        consumo.setCompania(Utilerias.formatoCadena(textoFormateado));
                    consumo.setCosto("0");
                    consumo.setDestino(Utilerias.formatoCadena(rsetOBJ.getString("DESTINO")));
                    if(rsetOBJ.getString("HORA_EVENTO") != null && !rsetOBJ.getString("HORA_EVENTO").trim().equals("")){
                        dateToHrs = dateFormat.parse(rsetOBJ.getString("HORA_EVENTO"));
                        consumo.setHora(new SimpleDateFormat("HH:mm").format(dateToHrs));
                    }
                    consumo.setIdColumna(1);
                    
                    numero = rsetOBJ.getString("NUM_DESTINO");
                    if(numero != null && numero.length() >= 10){
                        telefono = numero.substring(numero.length()-10,numero.length());
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }else{
                        telefono = numero;
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }
                    consumo.setNumero(telefono);
                    consumo.setTiempo(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setMbConsumidos(rsetOBJ.getString("DUR_MIN_REDN"));
                    
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    
                    consumoFechaList.add(consumoFecha);
                }

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        
        public List<ConsumoFechaTablaVO> getDetallesDivisionOtrasCompanias(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> getDetallesDivision :: C o n s u l t a  a  B a s e  d e  D a t o s C o n s u m o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            String numero = "";
            String telefono = "";
            String textoFormateado = "";
            String operador = "";
            ConsumoDetalleTablaVO consumo = new ConsumoDetalleTablaVO();
            List<ConsumoDetalleTablaVO> consumoList = new ArrayList<ConsumoDetalleTablaVO>();
            ConsumoFechaTablaVO consumoFecha = new ConsumoFechaTablaVO();
            List<ConsumoFechaTablaVO> consumoFechaList = new ArrayList<ConsumoFechaTablaVO>();
            SimpleDateFormat dateFormat = null;
            Date dateToHrs = new Date();
            try{
                
                int varTimeOut = 20; 

                try{
                   varTimeOut = Integer.parseInt(getValorParametro(118));
                }catch (Exception e) {
                    varTimeOut = 20;
                }
                
                dateFormat = new SimpleDateFormat(getValorParametro(101));
                
                commSQL = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSDET.FNGETDETALLEGENERALTDESTINO(?,?,?)}";
                commPRN = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSDET.FNGETDETALLEGENERALTDESTINO("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                
                for(int cont = 0; cont < intentos; cont++)
                {
                    try
                    {
                        connOBJ = OracleConnection.getConnectionConsumos(new MensajeLogBean());
                        Logger.write("     Conexion BD Abierta    : true");

                        if(connOBJ != null) 
                            stmsOBJ = connOBJ.prepareCall(commSQL);

                        stmsOBJ.setQueryTimeout(varTimeOut);

                        Logger.write("     Conexion BD Abierta    : true");
                        i = 1;
                        stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                        stmsOBJ.setString(i++, dn);
                        stmsOBJ.setString(i++, fechaIni);
                        stmsOBJ.setString(i++, fechaFin);   



                        Logger.write("     Ejecutando consulta    intento[" + (cont+1) +"] :");
                        timeIni = System.currentTimeMillis();
                        stmsOBJ.execute();  
                        rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                        break;
                    }
                    catch (Exception e) 
                    {
                        boolean reintentar = Utilerias.contieneMensajeError(e.getLocalizedMessage());                       
                        if(cont < (intentos-1) && reintentar)
                        {
                            Logger.write("     [WARN]  : (SQLException) " + e.getLocalizedMessage());
                            if(connOBJ != null) try {
                                connOBJ.close();    
                                Logger.write("     Conexion BD Abierta    : false");
                            }catch (Exception e1) {
                                Logger.write("     Detail  : (SQLException) " + e1.getMessage());   
                            }
                            continue;
                        }
                        else
                        {
                            throw new ServiceException(e.getLocalizedMessage());
                        }
                    }                   
                }   
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleTablaVO();
                    consumoList = new ArrayList<ConsumoDetalleTablaVO>();
                    consumoFecha = new ConsumoFechaTablaVO();
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    try{
                        if(rsetOBJ.getString("OPERADOR_ORIGEN") != null){
                            operador = rsetOBJ.getString("OPERADOR_ORIGEN");
                        }
                    }catch (Exception e)
                    {operador = "";}
                    Logger.write("     operador               " + operador);
                    textoFormateado = rsetOBJ.getString("OPERADOR_DESTINO");
                    if(textoFormateado != null && textoFormateado.toLowerCase().trim().equals("no identificado"))
                        consumo.setCompania("ND");
                    else
                        consumo.setCompania(Utilerias.formatoCadena(textoFormateado));
                    consumo.setCosto("0");
                    consumo.setDestino(Utilerias.formatoCadena(rsetOBJ.getString("DESTINO")));
                    if(rsetOBJ.getString("HORA_EVENTO") != null && !rsetOBJ.getString("HORA_EVENTO").trim().equals("")){
                        dateToHrs = dateFormat.parse(rsetOBJ.getString("HORA_EVENTO"));
                        consumo.setHora(new SimpleDateFormat("HH:mm").format(dateToHrs));
                    }
                    consumo.setIdColumna(1);
                    
                    numero = rsetOBJ.getString("NUM_DESTINO");
                    if(numero != null && numero.length() >= 10){
                        telefono = numero.substring(numero.length()-10,numero.length());
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }else{
                        telefono = numero;
                        try{
                            telefono = Integer.toString(Integer.parseInt(telefono));
                        }catch (NumberFormatException e) {
                        }
                    }
                    consumo.setNumero(telefono);
                    consumo.setTiempo(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setMbConsumidos(rsetOBJ.getString("DUR_MIN_REDN"));
                    
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    
                    consumoFechaList.add(consumoFecha);
                }
                

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        public List<ConsumoFechaVO> getLlamadasPlus(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s C o n s u m o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            ConsumoDetalleVO consumo = new ConsumoDetalleVO();
            List<ConsumoDetalleVO> consumoList = new ArrayList<ConsumoDetalleVO>();
            ConsumoFechaVO consumoFecha = new ConsumoFechaVO();
            List<ConsumoFechaVO> consumoFechaList = new ArrayList<ConsumoFechaVO>();
            
            try{
                                
                commSQL = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSTOTAL.FNGETDETALLEVOZBIC(?,?,?)}";
                commPRN = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSTOTAL.FNGETDETALLEVOZBIC("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                
                for(int cont = 0; cont < intentos; cont++)
                {
                    try
                    {
                        connOBJ = OracleConnection.getConnectionConsumos(new MensajeLogBean());
                        Logger.write("     Conexion BD Abierta    : true");

                        if(connOBJ != null) 
                            stmsOBJ = connOBJ.prepareCall(commSQL);

                        Logger.write("     Conexion BD Abierta    : true");
                        i = 1;
                        stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                        stmsOBJ.setString(i++, dn);
                        stmsOBJ.setString(i++, fechaIni);
                        stmsOBJ.setString(i++, fechaFin);

                        Logger.write("     Ejecutando consulta    intento[" + (cont+1) +"] :");
                        timeIni = System.currentTimeMillis();                           
                        stmsOBJ.execute();  
                        rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                        break;
                    }
                    catch (Exception e) 
                    {
                        boolean reintentar = Utilerias.contieneMensajeError(e.getLocalizedMessage());                       
                        if(cont < (intentos-1) && reintentar)
                        {
                            Logger.write("     [WARN]  : (SQLException) " + e.getLocalizedMessage());
                            if(connOBJ != null) try {
                                connOBJ.close();    
                                Logger.write("     Conexion BD Abierta    : false");
                            }catch (Exception e1) {
                                Logger.write("     Detail  : (SQLException) " + e1.getMessage());   
                            }
                            continue;
                        }
                        else
                        {
                            throw new ServiceException(e.getLocalizedMessage());
                        }
                    }                   
                }
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleVO();
                    consumoList = new ArrayList<ConsumoDetalleVO>();
                    consumoFecha = new ConsumoFechaVO();
                    consumo.setCdescWallet(rsetOBJ.getString("C_DESC_WALLET"));
                    consumo.setCiudadDestino(rsetOBJ.getString("DESTINO"));
                    consumo.setDescTipoTrafico(rsetOBJ.getString("DESC_TIPO_TRAFICO"));
                    consumo.setDurMinRedn(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setHoraEvento(rsetOBJ.getString("HORA_EVENTO"));
                    consumo.setMontoFree(rsetOBJ.getString("MONTOFREE"));
                    consumo.setMontoReal(rsetOBJ.getString("MONTOREAL"));
                    consumo.setNumDestino(rsetOBJ.getString("NUM_DESTINO"));
                    consumo.setNumOrigen(rsetOBJ.getString("NUM_ORIGEN"));
                    consumo.setOperadorDestino(rsetOBJ.getString("OPERADOR_DESTINO"));
                    consumo.setOperadorOrigen(rsetOBJ.getString("OPERADOR_ORIGEN"));
                    consumo.setSaldoAntes(rsetOBJ.getString("N_SALDO_ANTES"));
                    consumo.setSaldoDespues(rsetOBJ.getString("N_SALDO_DESPUES"));
                    consumo.setTotalNavegacion("");
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    consumoFechaList.add(consumoFecha);
                }
                if(connOBJ != null) {
                    Logger.write("cierra conexion CLIENREPUNI.PQBICCONSUMOSTOTAL.FNGETDETALLEVOZBIC");
                    stmsOBJ.close();
                    stmsOBJ = null;
                    rsetOBJ.close();
                    rsetOBJ = null;
                    connOBJ.close();
                    connOBJ = null;
                }

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        public List<ConsumoFechaVO> getMensajesPlus(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s C o n s u m o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            ConsumoDetalleVO consumo = new ConsumoDetalleVO();
            List<ConsumoDetalleVO> consumoList = new ArrayList<ConsumoDetalleVO>();
            ConsumoFechaVO consumoFecha = new ConsumoFechaVO();
            List<ConsumoFechaVO> consumoFechaList = new ArrayList<ConsumoFechaVO>();
            
            try{
                                
                commSQL = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSTOTAL.FNGETDETALLESMSBIC(?,?,?)}";
                commPRN = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSTOTAL.FNGETDETALLESMSBIC("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                
                for(int cont = 0; cont < intentos; cont++)
                {
                    try
                    {
                        connOBJ = OracleConnection.getConnectionConsumos(new MensajeLogBean());
                        Logger.write("     Conexion BD Abierta    : true");

                        if(connOBJ != null) 
                            stmsOBJ = connOBJ.prepareCall(commSQL);
            

                        Logger.write("     Conexion BD Abierta    : true");
                        i = 1;
                        stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                        stmsOBJ.setString(i++, dn);
                        stmsOBJ.setString(i++, fechaIni);
                        stmsOBJ.setString(i++, fechaFin);



                        Logger.write("     Ejecutando consulta    intento[" + (cont+1) +"] :");
                        timeIni = System.currentTimeMillis();
                        stmsOBJ.execute();  
                        rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                        break;
                    }
                    catch (Exception e) 
                    {
                        boolean reintentar = Utilerias.contieneMensajeError(e.getLocalizedMessage());                       
                        if(cont < (intentos-1) && reintentar)
                        {
                            Logger.write("     [WARN]  : (SQLException) " + e.getLocalizedMessage());
                            if(connOBJ != null) try {
                                connOBJ.close();    
                                Logger.write("     Conexion BD Abierta    : false");
                            }catch (Exception e1) {
                                Logger.write("     Detail  : (SQLException) " + e1.getMessage());   
                            }
                            continue;
                        }
                        else
                        {
                            throw new ServiceException(e.getLocalizedMessage());
                        }
                    }                   
                }
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleVO();
                    consumoList = new ArrayList<ConsumoDetalleVO>();
                    consumoFecha = new ConsumoFechaVO();
                    consumo.setCdescWallet(rsetOBJ.getString("C_DESC_WALLET"));
                    consumo.setCiudadDestino(rsetOBJ.getString("DESTINO"));
                    consumo.setDescTipoTrafico(rsetOBJ.getString("DESC_TIPO_TRAFICO"));
                    consumo.setDurMinRedn(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setHoraEvento(rsetOBJ.getString("HORA_EVENTO"));
                    consumo.setMontoFree(rsetOBJ.getString("MONTOFREE"));
                    consumo.setMontoReal(rsetOBJ.getString("MONTOREAL"));
                    consumo.setNumDestino(rsetOBJ.getString("NUM_DESTINO"));
                    consumo.setNumOrigen(rsetOBJ.getString("NUM_ORIGEN"));
                    consumo.setOperadorDestino(rsetOBJ.getString("OPERADOR_DESTINO"));
                    consumo.setOperadorOrigen(rsetOBJ.getString("OPERADOR_ORIGEN"));
                    consumo.setSaldoAntes(rsetOBJ.getString("N_SALDO_ANTES"));
                    consumo.setSaldoDespues(rsetOBJ.getString("N_SALDO_DESPUES"));
                    consumo.setTotalNavegacion("");
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    consumoFechaList.add(consumoFecha);
                }

                if(connOBJ != null) {
                    Logger.write("cierra conexion CLIENREPUNI.PQBICCONSUMOSTOTAL.FNGETDETALLESMSBIC");
                    stmsOBJ.close();
                    stmsOBJ = null;
                    rsetOBJ.close();
                    rsetOBJ = null;
                    connOBJ.close();
                    connOBJ = null;
                }

                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        public List<ConsumoFechaVO> getDatosPlus(final String dn, final String fechaIni, final String fechaFin) throws Exception{
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s C o n s u m o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commSQL="";
            String commPRN="";
            long timeIni = 0;
            int i = 1;
            String fecha = "";
            ConsumoDetalleVO consumo = new ConsumoDetalleVO();
            List<ConsumoDetalleVO> consumoList = new ArrayList<ConsumoDetalleVO>();
            ConsumoFechaVO consumoFecha = new ConsumoFechaVO();
            List<ConsumoFechaVO> consumoFechaList = new ArrayList<ConsumoFechaVO>();
            
            try{
                
                                
                commSQL = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSTOTAL.FNGETDETALLEDATOSBIC(?,?,?)}";
                commPRN = "{ ? =  call CLIENREPUNI.PQBICCONSUMOSTOTAL.FNGETDETALLEDATOSBIC("+dn+","+fechaIni+
                ","+fechaFin+")}";

                Logger.write("     Operacion              : getLlamadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                
                for(int cont = 0; cont < intentos; cont++)
                {
                    try
                    {
                        connOBJ = OracleConnection.getConnectionConsumos(new MensajeLogBean());
                        Logger.write("     Conexion BD Abierta    : true");

                        if(connOBJ != null) 
                            stmsOBJ = connOBJ.prepareCall(commSQL);

                        Logger.write("     Conexion BD Abierta    : true");
                        i = 1;
                        stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                        stmsOBJ.setString(i++, dn);
                        stmsOBJ.setString(i++, fechaIni);
                        stmsOBJ.setString(i++, fechaFin);



                        Logger.write("     Ejecutando consulta    intento[" + (cont+1) +"] :");
                        timeIni = System.currentTimeMillis();
                        stmsOBJ.execute();  
                        rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                        break;
                    }
                    catch (Exception e) 
                    {
                        boolean reintentar = Utilerias.contieneMensajeError(e.getLocalizedMessage());                       
                        if(cont < (intentos-1) && reintentar)
                        {
                            Logger.write("     [WARN]  : (SQLException) " + e.getLocalizedMessage());
                            if(connOBJ != null) try {
                                connOBJ.close();    
                                Logger.write("     Conexion BD Abierta    : false");
                            }catch (Exception e1) {
                                Logger.write("     Detail  : (SQLException) " + e1.getMessage());   
                            }
                            continue;
                        }
                        else
                        {
                            throw new ServiceException(e.getLocalizedMessage());
                        }
                    }                   
                }   
                while(rsetOBJ.next()){
                    consumo = new ConsumoDetalleVO();
                    consumoList = new ArrayList<ConsumoDetalleVO>();
                    consumoFecha = new ConsumoFechaVO();
                    consumo.setCdescWallet(rsetOBJ.getString("C_DESC_WALLET"));
                    consumo.setCiudadDestino(rsetOBJ.getString("DESTINO"));
                    consumo.setDescTipoTrafico(rsetOBJ.getString("DESC_TIPO_TRAFICO"));
                    consumo.setDurMinRedn(rsetOBJ.getString("DUR_MIN_REDN"));
                    consumo.setHoraEvento(rsetOBJ.getString("HORA_EVENTO"));
                    consumo.setMontoFree(rsetOBJ.getString("MONTOFREE"));
                    consumo.setMontoReal(rsetOBJ.getString("MONTOREAL"));
                    consumo.setNumDestino(rsetOBJ.getString("NUM_DESTINO"));
                    consumo.setNumOrigen(rsetOBJ.getString("NUM_ORIGEN"));
                    consumo.setOperadorDestino(rsetOBJ.getString("OPERADOR_DESTINO"));
                    consumo.setOperadorOrigen(rsetOBJ.getString("OPERADOR_ORIGEN"));
                    consumo.setSaldoAntes(rsetOBJ.getString("N_SALDO_ANTES"));
                    consumo.setSaldoDespues(rsetOBJ.getString("N_SALDO_DESPUES"));
                    fecha = rsetOBJ.getString("FECHA_EVENTO");
                    consumoList.add(consumo);
                    consumoFecha.setDetalle(consumoList);
                    consumoFecha.setFecha(fecha);
                    consumoFechaList.add(consumoFecha);
                }
                
                if(connOBJ != null) {
                    Logger.write("cierra conexion CLIENREPUNI.PQBICCONSUMOSTOTAL.FNGETDETALLEDATOSBIC");
                    stmsOBJ.close();
                    stmsOBJ = null;
                    rsetOBJ.close();
                    rsetOBJ = null;
                    connOBJ.close();
                    connOBJ = null;
                }
                
                Logger.write("   + Respuesta              +");
            }catch (Exception e) {
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally{
                if(stmsOBJ != null) try {
                    stmsOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(rsetOBJ != null) try {
                    rsetOBJ.close();
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if(connOBJ != null) try {
                    connOBJ.close();    
                }catch (Exception e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());    
                }
                Logger.write("     Conexion BD Abierta    : false");
            }
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return consumoFechaList; 
        }
        
        public int insertaPIN(final String dn, final String pinCreado, final MensajeLogBean mensajeLog) throws ServiceException, IOException, SQLException {
            
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            Integer codigo = 0;
            String mensaje = "";
            long timeIni = 0;
            int i=1;
            try
            {
                
                commSQL = "{ ? = call  MIIUSACELL.PAMIIUPDATELOGIN.FNMIIPASSPIN(?,?)}";
                commPRN = "{ ? = call  MIIUSACELL.PAMIIUPDATELOGIN.FNMIIPASSPIN("+dn+","+pinCreado+")";
                
                Logger.write("     Operacion              : insertaPIN");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                
                connOBJ = OracleConnection.getConnection(mensajeLog);
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, dn);
                    stmsOBJ.setString(i++, pinCreado);
    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    codigo = (Integer) stmsOBJ.getObject(1);
                    
                    Logger.write("   + Respuesta              +");
                    Logger.write("     codigo                 : " + codigo);
                    Logger.write("     mensaje                : " + mensaje);
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return  codigo;
        }

        public String retornaPin(final String dn, final MensajeLogBean mensajeLog) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            ResultSet rs = null;
            String retorno = "0";
            long timeIni = 0;
            int i = 1;

            try {
            commSQL = "{ ? = call  MIIUSACELL.PAMIIGETLOGIN.FNGETMIITACODIGOPIN(?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIIGETLOGIN.FNGETMIITACODIGOPIN("+dn+")}";
            
            Logger.write("     Operacion              : retornaPIN");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setString(i++, dn);
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rs = (ResultSet)stmsOBJ.getObject(1);
                    while(rs.next()){
                        retorno = rs.getString("FCCODIGOPIN");
                    }

                    Logger.write("   + Respuesta              +");
                    Logger.write("     dn                     : " + dn);
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (rs != null) try {
                    rs.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }
        
        public String retornaCia (final String dn, final MensajeLogBean mensajeLog) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            ResultSet rs = null;
            String retorno = "0";
            long timeIni = 0;
            int i = 1;

            try {
            commSQL = "{ ? = call  MIIUSACELL.PAMIISELTRANGET.FNSELMIICLIENTE(?,?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIISELTRANGET.FNSELMIICLIENTE(null,"+dn+")}";
            
            Logger.write("     Operacion              : retornaPIN");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setString(i++, null);
                    stmsOBJ.setString(i++, dn);
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rs = (ResultSet)stmsOBJ.getObject(1);
                    while(rs.next()){
                        retorno = rs.getString("FICVEUNIDADNEGOCIOID");
                    }

                    Logger.write("   + Respuesta              +");
                    Logger.write("     dn                     : " + dn);
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (rs != null) try {
                    rs.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }
        
        public String retornaDescripcionCompania (final String idServicio, final MensajeLogBean mensajeLog) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            ResultSet rs = null;
            String retorno = "0";
            long timeIni = 0;
            int i = 1;

            try {
            commSQL = "{ ? = call  MIIUSACELL.PAMIIEXECATGET.FNMIUNIDADNEGOCIOGET(?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIIEXECATGET.FNMIUNIDADNEGOCIOGET("+idServicio+")}";
            
            Logger.write("     Operacion              : retornaDescripcionCompania");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setString(i++, idServicio);
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rs = (ResultSet)stmsOBJ.getObject(1);
                    while(rs.next()){
                        retorno = rs.getString("UNINEGOCIO");
                    }

                    Logger.write("   + Respuesta              +");
                    Logger.write("     Descripcion Compania  : " + retorno);
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (rs != null) try {
                    rs.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }
        
        
        public List<CatalogoTotalesVO> totalUsuarios(final String tipo) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            ResultSet rs = null;
            long timeIni = 0;
            int i = 1;
            List<CatalogoTotalesVO> listaTotales = new ArrayList<CatalogoTotalesVO>(); 
            CatalogoTotalesVO totales = new CatalogoTotalesVO();
            try {
            commSQL = "{ ? = call  MIIUSACELL.PAMIIREPORTERIA.FNINDICADORESXDN(?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIIREPORTERIA.FNINDICADORESXDN(3,null,null,null,null,null,null)}";
            
            Logger.write("     Operacion              : totalUsuarios");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setInt(i++, 3);
                    stmsOBJ.setString(i++, null);
                    stmsOBJ.setString(i++, null);
                    stmsOBJ.setString(i++, null);
                    stmsOBJ.setString(i++, null);
                    stmsOBJ.setString(i++, null);
                    stmsOBJ.setString(i++, null);
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rs = (ResultSet)stmsOBJ.getObject(1);
                    while(rs.next()){
                        totales = new CatalogoTotalesVO();
                        long valor = Long.parseLong(rs.getString(1));
                        totales.setCantidad(valor);
                        totales.setDescripcion(rs.getString(2));
                        listaTotales.add(totales);
                    }

                    Logger.write("   + Respuesta              +");
                    Logger.write("     carga totales exitosa");
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (rs != null) try {
                    rs.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return listaTotales;
        }
        
        
        public List<MovimientosTA> getOperacionesTAporDN(final String logId, final String fechaInicio, final String fechaFin, final String dn) throws ServiceException
        {       
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            List<MovimientosTA> movimientosTA = new ArrayList<MovimientosTA>();
            MovimientosTA registroTA = new MovimientosTA();
            ResultSet rs=null;
            long timeIni = 0;
            int i = 1;
            try
            {           
                commSQL = "{ ? = call  MIIUSACELL.PAMIILOGGET.FNABONOTIEMPOAIREXDN(?,?,?,?)}";
                commPRN = "{ ? = call  MIIUSACELL.PAMIILOGGET.FNABONOTIEMPOAIREXDN("
                + logId + ","
                + fechaInicio + ","
                + fechaFin + ","
                + dn + ","
                + ")";
                
                Logger.write("     Operacion              : getOperacionesTAporDN");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR );
                    stmsOBJ.setString(i++, logId);
                    stmsOBJ.setString(i++, fechaInicio);
                    stmsOBJ.setString(i++, fechaFin);
                    stmsOBJ.setString(i++, dn);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();
                    
                    rs=(ResultSet)stmsOBJ.getObject(1);

                    while (rs.next()) 
                    {
                        registroTA = new MovimientosTA();
                        registroTA.setFecha(rs.getString("FDFECHAALTA"));
                        registroTA.setLogID(rs.getString("FILOGID"));
                        registroTA.setDn(rs.getString("FCDN"));
                        registroTA.setDnAbono(rs.getString("FCDNPARAABONO"));
                        registroTA.setConcepto(rs.getString("FCCONCEPTO"));
                        registroTA.setImporte(rs.getString("FNIMPORTE"));
                        registroTA.setCodigoRespuestaAbono(rs.getString("FCCODIGORESPUESTAABONOTA"));
                        registroTA.setNumeroAutorizacionAbono(rs.getString("FCNUMEROAUTORIZACIONABONOTA"));
                        registroTA.setNumeroAutorizacionCargo(rs.getString("FCNUMEROAUTORIZACIONCAJA"));
                        movimientosTA.add(registroTA);
                    }
                    
                    Logger.write("   + Respuesta              +");
                    Logger.write("     retorno.size           : " + movimientosTA.size());              
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (rs != null) try {
                    rs.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return movimientosTA;
        }

        public List<CatalogoAbonos> getCatalogoAbonos() throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             List<CatalogoAbonos> catalogoAbonos = new ArrayList<CatalogoAbonos>();
             
             long timeIni = 0;
             int i = 1; 
             
        try{
         commSQL = "{ ? = call MIIUSACELL.PAMIIGET2.FNGETMIICATABONO()}";
         commPRN = "{ ? = call MIIUSACELL.PAMIIGET2.FNGETMIICATABONO()}";
                        
             Logger.write("     Operacion              : getCatalogoAbonos");
             Logger.write("   + Parametros             +");
             Logger.write("     commPRN                : " + commPRN);
             
             connOBJ = OracleConnection.getConnection(new MensajeLogBean());

             Logger.write("     Conexion BD Abierta    : true");
             if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
             
             if(stmsOBJ != null){
                 stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                 
                 Logger.write("     Ejecutando consulta    :");
                 timeIni = System.currentTimeMillis();
                 stmsOBJ.execute();

                 rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                 
                 while(rsetOBJ.next()){
                     CatalogoAbonos catalogo = new CatalogoAbonos();
                     catalogo.setCantidad(rsetOBJ.getString(1));
                     catalogo.setDescripcion(rsetOBJ.getString(2));
                     catalogoAbonos.add(catalogo);
                 }
             }
             
        }catch(SQLException e){
             Logger.write("     Detail    : (Exception) " + e.getMessage());     

         }finally{
             if (rsetOBJ != null) try {
                 rsetOBJ.close();
             }
             catch (SQLException e) {
                 Logger.write("     Detail  : (SQLException) " + e.getMessage());
             }
             if (stmsOBJ != null) try {
                 stmsOBJ.close();
             }
             catch (SQLException e) {
                 Logger.write("     Detail  : (SQLException) " + e.getMessage());
             }
             if (connOBJ != null){
                 try {
                     connOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
             }
             Logger.write("     Conexion BD Abierta    : false");    
         }
         Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
         return catalogoAbonos;
        }

        public String generaPinBD(final String dn, final int usuario) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            String retorno =  "";
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i = 1;
            try {
                commORA = "{ ? = call MIIUSACELL.PAMIISET2.FNGENMIICODIGOPIN(?,?) }";
                commPRN = "{ ? = call MIIUSACELL.PAMIISET2.FNGENMIICODIGOPIN(" + dn + ","+ usuario +") }";

                Logger.write("     Operacion              : generaPinBD");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                stmsOBJ.registerOutParameter(i++, OracleTypes.VARCHAR);
                stmsOBJ.setString(i++, dn);
                stmsOBJ.setInt(i++, usuario);
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                retorno = stmsOBJ.getString(1);             

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        } 
        
        public String validaPinBD(final String dn) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            String retorno =  "";
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i = 1;
            try {
                commORA = "{ ? = call MIIUSACELL.PAMIIGET2.FNGETMIITACODIGOPIN(?) }";
                commPRN = "{ ? = call MIIUSACELL.PAMIIGET2.FNGETMIITACODIGOPIN(" + dn + ") }";

                Logger.write("     Operacion              : validaPinBD");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                stmsOBJ.setString(i++, dn);
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                while(rsetOBJ.next()){
                    retorno = rsetOBJ.getString(1);
                }

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }   

        public String getMensajeServicio(final String idServicio, final MensajeLogBean mensajeLog) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            ResultSet rs = null;
            String retorno = "";
            long timeIni = 0;
            int i = 1;

            try {
            commSQL = "{ ? = call  SCCPPIUSA.PACPPCATALOGOGET1.FNMNSRETSRVGET(?)}";
            commPRN = "{ ? = call  SCCPPIUSA.PACPPCATALOGOGET1.FNMNSRETSRVGET("+idServicio+")}";
            
            Logger.write("     Operacion              : getMensajeServicio");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
                connOBJ = OracleConnection.conexionDBOferta(new MensajeLogBean());
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setString(i++, idServicio);
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rs = (ResultSet)stmsOBJ.getObject(1);
                    while(rs.next()){
                        retorno = rs.getString("FCMENSAJERET");
                    }

                    Logger.write("   + Respuesta              +");
                    Logger.write("     mensajeServicio    : " + retorno);
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (rs != null) try {
                    rs.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }           
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }

        public List<PermisosClienteVO> getPermisosCliente(final String tipoCliente) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             List<PermisosClienteVO> permisosCliente = new ArrayList<PermisosClienteVO>();
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                 commSQL = "{ ? = call SCCPPIUSA.PACPPCATALOGOGET1.FNPRMCLIDESCGET(?)}";
                 commPRN = "{ ? = call SCCPPIUSA.PACPPCATALOGOGET1.FNPRMCLIDESCGET(" + tipoCliente + ") }";
                                
                     Logger.write("     Operacion              : getPermisosCliente");
                     Logger.write("   + Parametros             +");
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.conexionDBOferta(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                         stmsOBJ.setString(i++, tipoCliente);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
        
                         rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                         
                         while(rsetOBJ.next()){
                             PermisosClienteVO cliente = new PermisosClienteVO();
                             cliente.setServicioID(rsetOBJ.getString(1));
                             cliente.setValor(rsetOBJ.getString(2));
                             permisosCliente.add(cliente);
                         }
                     }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return permisosCliente;
        }
        
        public ObtenerDescripcionPlanesVO1 getObtenerDescripcionPlanes(final String idPlan, final String plan, final String idElephan, final int usuarioId, final String ip ) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             ObtenerDescripcionPlanesVO1 descripcionPlanes = new ObtenerDescripcionPlanesVO1();
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                 commSQL = "{ ? = call SCCPPIUSA.PACPPPRESUPUESTOGET.FNCPPCAREQP(?,?,?,?,?)}";
                 commPRN = "{ ? = call SCCPPIUSA.PACPPPRESUPUESTOGET.FNCPPCAREQP('" + 
                idPlan + "','" +
                plan + "','" +
                idElephan + "'," +
                usuarioId + ",'" +
                ip + "'" +
                ") }";
                                
                     Logger.write("     Operacion              : getObtenerDescripcionPlanes");
                     Logger.write("   + Parametros             +");
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.conexionDBOferta(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                         stmsOBJ.setString(i++, idPlan);
                         stmsOBJ.setString(i++, plan);
                         stmsOBJ.setString(i++, idElephan);
                         stmsOBJ.setInt(i++, usuarioId);
                         stmsOBJ.setString(i++, ip);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
        
                         rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                         
                         while(rsetOBJ.next()){ 
                             descripcionPlanes = new ObtenerDescripcionPlanesVO1();
                             descripcionPlanes.setMinutos(rsetOBJ.getString("MINUTOS"));
                             descripcionPlanes.setTiempoAire(rsetOBJ.getString("TIEMPOAIRE"));
                             descripcionPlanes.setMinutoscomunidad(rsetOBJ.getString("MINUTOSCOMUNIDAD"));
                             descripcionPlanes.setMensajes(rsetOBJ.getString("MENSAJES"));
                             descripcionPlanes.setMegas(rsetOBJ.getString("MEGAS"));
                             descripcionPlanes.setMinutoadc(rsetOBJ.getString("MINUTOADC"));
                             descripcionPlanes.setMensajeadc(rsetOBJ.getString("MENSAJEADC"));
                             descripcionPlanes.setMegaadc(rsetOBJ.getString("MEGAADC"));
                             descripcionPlanes.setNombreCortoPlan(Utilerias.getStringUTF8(rsetOBJ.getString("NOMBRECORTO")));
                             descripcionPlanes.setIdPlan(rsetOBJ.getString("IDPLAN"));
                         }
                     }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return descripcionPlanes;
        }
        
        public List<ServiciosAdicionalesVO> getServiciosAdicionales(final int version,final String  idPlan, final String idElephant, final int usuario, final String ip) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             List<ServiciosAdicionalesVO> serviciosAdicionales = new ArrayList<ServiciosAdicionalesVO>();
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                 commSQL = "{ ? = call SCCPPIUSA.PACPPPRESUPUESTOGET.FNCPPSERVOCP(?,?,?,?,?)}";
                 commPRN = "{ ? = call SCCPPIUSA.PACPPPRESUPUESTOGET.FNCPPSERVOCP(" + 
                 version + ",'" +
                 idPlan+ "','" +
                 idElephant + "'," +
                 usuario + ",'" +
                 ip + "'" +
                 ") }";
                                
                     Logger.write("     Operacion              : getServiciosAdicionales");
                     Logger.write("   + Parametros             +");
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.conexionDBOferta(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                         stmsOBJ.setInt(i++, version);
                         stmsOBJ.setString(i++, idPlan);
                         stmsOBJ.setString(i++, idElephant);
                         stmsOBJ.setInt(i++, usuario);
                         stmsOBJ.setString(i++, ip);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
        
                         rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                         String idServicioMod = "";
                         String concatenaId = "";
                         
                         while(rsetOBJ.next()){
                             ServiciosAdicionalesVO cliente = new ServiciosAdicionalesVO();
                             cliente.setCosto(rsetOBJ.getString("COSTO"));
                             cliente.setDescripcion(rsetOBJ.getString("DESLARGA"));                          
                             cliente.setImagenB64(rsetOBJ.getString("IMAGEN"));
                             cliente.setServicio(rsetOBJ.getString("SERVICIO"));
                             cliente.setVersion(rsetOBJ.getString("VERSION"));                           
                             cliente.setStatus(0);                          
                             
                             idServicioMod = rsetOBJ.getString("SERVICIOID");
                             concatenaId = "";
                             
                             if(concatenaId.equals("1")){
                                 if(idServicioMod != null && idServicioMod.toLowerCase().contains("vp")){
                                     idServicioMod = idServicioMod + "00000000";
                                 }
                                 if(idServicioMod != null && idServicioMod.toLowerCase().contains("justi")){
                                     idServicioMod = idServicioMod + "00000000";
                                 }
                             }
                             cliente.setIdServicio(idServicioMod);
                             serviciosAdicionales.add(cliente);
                         }
                     }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return serviciosAdicionales;
        }   
        
        public ImagenEquipoVO getImagenEquipoSO(final String idEquipo, final String equipoDesc, final int usuarioId, final String ip ) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             ImagenEquipoVO descripcionPlanes = new ImagenEquipoVO();
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                 commSQL = "{ ? = call SCCPPIUSA.PACPPPRESUPUESTOGET.FNCPPIMGEQPSIO (?,?,?,?)}";
                 commPRN = "{ ? = call SCCPPIUSA.PACPPPRESUPUESTOGET.FNCPPIMGEQPSIO ('" + 
                 idEquipo + "','" +
                 equipoDesc + "','" +               
                 usuarioId + ",'" +
                 ip + "'" +
                 ") }";
                                
                     Logger.write("     Operacion              : getImagenEquipoSO");
                     Logger.write("   + Parametros             +");
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.conexionDBOferta(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                         stmsOBJ.setString(i++, idEquipo);
                         stmsOBJ.setString(i++, equipoDesc);                         
                         stmsOBJ.setInt(i++, usuarioId);
                         stmsOBJ.setString(i++, ip);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
        
                         rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                         
                         while(rsetOBJ.next()){ 
                             descripcionPlanes = new ImagenEquipoVO();
                             descripcionPlanes.setEquipo(rsetOBJ.getString("EQUIPO"));
                             descripcionPlanes.setImagenEquipoB64(rsetOBJ.getString("IMGEQP"));
                             descripcionPlanes.setSistemaOper(rsetOBJ.getString("SOPERATIVO"));
                             descripcionPlanes.setImagenSOB64(rsetOBJ.getString("IMGSIO"));                          
                         }
                     }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return descripcionPlanes;
        }
        
        public List<ServiciosBundlesAdicionales> getServiciosBundlesXTipoCte(final String idPlan, final int usuarioId, final String ip, final int tipoCliente ) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             List<ServiciosBundlesAdicionales> serviciosAdicionales = new ArrayList<ServiciosBundlesAdicionales>();
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                 commSQL = "{ ? = call SCCPPIUSA.PACPPGETAPLICATIVO.FNCPPSERVOCPCLI(?,?,?,?)}";
                 commPRN = "{ ? = call SCCPPIUSA.PACPPGETAPLICATIVO.FNCPPSERVOCPCLI('" + 
                 idPlan + "'," +                
                 usuarioId + ",'" +
                 ip + "'," +
                 tipoCliente + 
                 ") }";
                                
                     Logger.write("     Operacion              : getServiciosBundlesXTipoCte");
                     Logger.write("   + Parametros             +");
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.conexionDBOferta(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);                      
                         stmsOBJ.setString(i++, idPlan);                         
                         stmsOBJ.setInt(i++, usuarioId);
                         stmsOBJ.setString(i++, ip);
                         stmsOBJ.setInt(i++, tipoCliente);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
        
                         rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                         String idServicioMod = "";
                         String concatenaId = "";
                         
                         while(rsetOBJ.next()){
                             ServiciosBundlesAdicionales cliente = new ServiciosBundlesAdicionales();
                             cliente.setCosto(rsetOBJ.getString("COSTO"));
                             cliente.setDescripcion(rsetOBJ.getString("DESLARGA"));                          
                             cliente.setImagenB64(rsetOBJ.getString("IMAGEN"));
                             cliente.setServicio(rsetOBJ.getString("SERVICIO"));
                             cliente.setVersion(rsetOBJ.getString("VERSION"));      
                             cliente.setTipo(rsetOBJ.getInt("TIPO"));
                             cliente.setMinutos(rsetOBJ.getInt("MINUTOS"));
                             cliente.setMensajes(rsetOBJ.getInt("MENSAJES"));
                             cliente.setNavegacion(rsetOBJ.getInt("NAVEGACION"));
                             cliente.setVigencia(rsetOBJ.getInt("VIGENCIA"));
                             cliente.setCostoBundle(rsetOBJ.getInt("FICOSTO"));
                             cliente.setStatus(0);                          
                             
                             idServicioMod = rsetOBJ.getString("SERVICIOID");
                             concatenaId = "";
                             
                             if(concatenaId.equals("1")){
                                 if(idServicioMod != null && idServicioMod.toLowerCase().contains("vp")){
                                     idServicioMod = idServicioMod + "00000000";
                                 }
                                 if(idServicioMod != null && idServicioMod.toLowerCase().contains("justi")){
                                     idServicioMod = idServicioMod + "00000000";
                                 }
                             }
                             cliente.setIdServicio(idServicioMod);
                             serviciosAdicionales.add(cliente);
                         }
                     }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return serviciosAdicionales;
        }   
        
        public List<ServiciosBundlesAdicionales> getServiciosBundles(final String idPlan, final int usuarioId, final String ip ) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             List<ServiciosBundlesAdicionales> serviciosAdicionales = new ArrayList<ServiciosBundlesAdicionales>();
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                 commSQL = "{ ? = call SCCPPIUSA.PACPPGETAPLICATIVO.FNCPPSERVICIOOPC(?,?,?)}";
                 commPRN = "{ ? = call SCCPPIUSA.PACPPGETAPLICATIVO.FNCPPSERVICIOOPC ('" + 
                 idPlan + "'," +                
                 usuarioId + ",'" +
                 ip + "'" +
                 ") }";
                                
                     Logger.write("     Operacion              : getServiciosBundles");
                     Logger.write("   + Parametros             +");
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.conexionDBOferta(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);                      
                         stmsOBJ.setString(i++, idPlan);                         
                         stmsOBJ.setInt(i++, usuarioId);
                         stmsOBJ.setString(i++, ip);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
        
                         rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                         String idServicioMod = "";
                         String concatenaId = "";
                         
                         while(rsetOBJ.next()){
                             ServiciosBundlesAdicionales cliente = new ServiciosBundlesAdicionales();
                             cliente.setCosto(rsetOBJ.getString("COSTO"));
                             cliente.setDescripcion(rsetOBJ.getString("DESLARGA"));                          
                             cliente.setImagenB64(rsetOBJ.getString("IMAGEN"));
                             cliente.setServicio(rsetOBJ.getString("SERVICIO"));
                             cliente.setVersion(rsetOBJ.getString("VERSION"));      
                             cliente.setTipo(rsetOBJ.getInt("TIPO"));
                             cliente.setMinutos(rsetOBJ.getInt("MINUTOS"));
                             cliente.setMensajes(rsetOBJ.getInt("MENSAJES"));
                             cliente.setNavegacion(rsetOBJ.getInt("NAVEGACION"));
                             cliente.setVigencia(rsetOBJ.getInt("VIGENCIA"));
                             cliente.setCostoBundle(rsetOBJ.getInt("FICOSTO"));
                             cliente.setStatus(0);                          
                             
                             idServicioMod = rsetOBJ.getString("SERVICIOID");
                             concatenaId = "";
                             
                             if(concatenaId.equals("1")){
                                 if(idServicioMod != null && idServicioMod.toLowerCase().contains("vp")){
                                     idServicioMod = idServicioMod + "00000000";
                                 }
                                 if(idServicioMod != null && idServicioMod.toLowerCase().contains("justi")){
                                     idServicioMod = idServicioMod + "00000000";
                                 }
                             }
                             cliente.setIdServicio(idServicioMod);
                             serviciosAdicionales.add(cliente);
                         }
                     }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return serviciosAdicionales;
        }   
        
        public String getDescPlanXServiceClass(final String idPlan, final int serviceClass, final int usuarioId, final String ip ) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             String descripcionPlan = "";
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                 commSQL = "{ ? = call SCCPPIUSA.PACPPGETAPLICATIVO.FNCPPSERVICECLASS(?,?,?,?)}";
                 commPRN = "{ ? = call SCCPPIUSA.PACPPGETAPLICATIVO.FNCPPSERVICECLASS (" +
                 serviceClass + ",'" +
                 idPlan + "'," +                
                 usuarioId + ",'" +
                 ip + "'" +
                 ") }";
                                
                     Logger.write("     Operacion              : getDescPlanXServiceClass");
                     Logger.write("   + Parametros             +");
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.conexionDBOferta(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                         stmsOBJ.setInt(i++, serviceClass);
                         stmsOBJ.setString(i++, idPlan);                         
                         stmsOBJ.setInt(i++, usuarioId);
                         stmsOBJ.setString(i++, ip);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
        
                         rsetOBJ = (ResultSet)stmsOBJ.getObject(1);                      
                         
                         while(rsetOBJ.next()){
                             descripcionPlan = Utilerias.getStringUTF8(rsetOBJ.getString("NOMBRECORTO"));
                         }
                     }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return descripcionPlan;
        }
        
        public List<catalogoCambioPlanVO> getCatalogoCambioPlan(final String serviceClass,final String usuarioId,final String compania) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             List<catalogoCambioPlanVO> catalogoCambioPlan = new ArrayList<catalogoCambioPlanVO>();
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                 commSQL = "{ ? = call SCCPPIUSA.PACPPGETAPLICATIVO.FNCPPCAMBIAPLANSC(?,?,?)}";
                 commPRN = "{ ? = call SCCPPIUSA.PACPPGETAPLICATIVO.FNCPPCAMBIAPLANSC("+
                 serviceClass + "," +
                 usuarioId + "," + compania + ")}";
                                
                     Logger.write("     Operacion              : getCatalogoCambioPlan");
                     Logger.write("   + Parametros             +");
                     Logger.write("     serviceClass           : "+serviceClass);
                     Logger.write("     usuarioId             : "+usuarioId);
                     Logger.write("     compania               : "+compania);
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.conexionDBOferta(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                         stmsOBJ.setInt(i++, Integer.parseInt(serviceClass));
                         stmsOBJ.setInt(i++, Integer.parseInt(usuarioId));
                         stmsOBJ.setInt(i++, Integer.parseInt(compania));
                         
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
        
                         rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                         
                         while(rsetOBJ.next()){
                             catalogoCambioPlanVO cambioPlan = new catalogoCambioPlanVO();
                             cambioPlan.setServiceClass(rsetOBJ.getString(1));
                             cambioPlan.setNombrePlan(rsetOBJ.getString(2));
                             cambioPlan.setIdPlan(rsetOBJ.getString(3));
                             cambioPlan.setCosto(rsetOBJ.getString(4));
                             cambioPlan.setDescripcion(rsetOBJ.getString(5));
                             catalogoCambioPlan.add(cambioPlan);
                         }
                     }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return catalogoCambioPlan;
        }

        public int bitacoraCobro(final String dn, final String monto, final String ajuste, final String requestCobro,final String usuarioId ) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             int cpId = -1;
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                   commSQL = "{ ? = call MIIUSACELL.PAMIIBITCAMPLNSET.FNINSLCPCOBRO(?,?,?,?,?)}";
                   commPRN = "{ ? = call MIIUSACELL.PAMIIBITCAMPLNSET.FNINSLCPCOBRO("+
                    dn + "," +
                    monto + ",'" +
                    ajuste + "','" +
                    requestCobro+"',"+
                    usuarioId
                    +")}";
                           
                     Logger.write("     Operacion              : bitacoraCobro");
                     Logger.write("   + Parametros             +");
                     Logger.write("     commPRN       : " + commPRN);
                     
                     connOBJ = OracleConnection.getConnection(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                         stmsOBJ.setString(i++, dn);
                         stmsOBJ.setString(i++, monto);                      
                         stmsOBJ.setString(i++, ajuste);
                         stmsOBJ.setString(i++, requestCobro);
                         stmsOBJ.setString(i++, usuarioId);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
                         cpId = stmsOBJ.getInt(1);
                        Logger.write("   + Respuesta              +");
                        Logger.write("     id                  : " + cpId); 
                         
                     }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return cpId;
        }
        
        public int bitacoraCambioPlan(final int cpId, final String dnCp, final String plazoCp, final String planCp,final String idPlanNuevoCp,final String requestCp,final String usuarioId ) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             int retorno = -1;
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                   commSQL = "{ ? = call MIIUSACELL.PAMIIBITCAMPLNSET.FNINSLCPCAMBIOPLAN(?,?,?,?,?,?,?)}";
                   commPRN = "{ ? = call MIIUSACELL.PAMIIBITCAMPLNSET.FNINSLCPCAMBIOPLAN("+
                   cpId + "," +
                   dnCp + "," +
                   plazoCp + "," +
                   planCp+","+
                   idPlanNuevoCp+","+
                   requestCp+","+
                   usuarioId+" )}";
                           
                     Logger.write("     Operacion              : bitacoraCambioPlan");
                     Logger.write("   + Parametros             +"+cpId);
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.getConnection(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                         stmsOBJ.setInt(i++, cpId);
                         stmsOBJ.setString(i++, dnCp);                       
                         stmsOBJ.setString(i++, plazoCp);
                         stmsOBJ.setString(i++, planCp);
                         stmsOBJ.setString(i++, idPlanNuevoCp);
                         stmsOBJ.setString(i++, requestCp);
                         stmsOBJ.setString(i++, usuarioId);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
                         retorno = stmsOBJ.getInt(1);
                         Logger.write("   + Respuesta              +");
                         Logger.write("     retorno                  : " + retorno);    
                         
                     }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return retorno;
        }
        
        
        public int bitacoraReversoCobro(final int cpId, final String dnCp, final String montoRev, final String cobroRev,final String requestCobRev,final String usuarioId ) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             int retorno = -1;
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                   commSQL = "{ ? = call MIIUSACELL.PAMIIBITCAMPLNSET.FNINSLCPREVERSOCOBRO(?,?,?,?,?,?)}";
                   commPRN = "{ ? = call MIIUSACELL.PAMIIBITCAMPLNSET.FNINSLCPREVERSOCOBRO("+
                   cpId + "," +
                   dnCp + "," +
                   montoRev + "," +
                   cobroRev+","+
                   requestCobRev+","+
                   usuarioId+" )}";
                           
                     Logger.write("     Operacion              : bitacoraReversoCobro");
                     Logger.write("   + Parametros             +");
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.getConnection(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                         stmsOBJ.setInt(i++, cpId);
                         stmsOBJ.setString(i++, dnCp);                       
                         stmsOBJ.setString(i++, montoRev);
                         stmsOBJ.setString(i++, cobroRev);
                         stmsOBJ.setString(i++, requestCobRev);
                         stmsOBJ.setString(i++, usuarioId);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
                         retorno = stmsOBJ.getInt(1);   
                         
                         Logger.write("   + Respuesta              +");
                         Logger.write("     retorno                  : " + retorno);
                     }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return retorno;
        }
        
        public int bitacoraCobroRespuesta(final int cpId, final String codigoRespCobro, final String mensajeCobro, final String idCobroResp,final String responseCobro,final String usuarioId ) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             int retorno = -1;
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                   commSQL = "{ ? = call MIIUSACELL.PAMIIBITCAMPLNSET.FNUPDLCPCOBRO(?,?,?,?,?,?)}";
                   commPRN = "{ ? = call MIIUSACELL.PAMIIBITCAMPLNSET.FNUPDLCPCOBRO("+
                   cpId + "," +
                   codigoRespCobro + "," +
                   mensajeCobro + "," +
                   idCobroResp+","+
                   responseCobro+","+
                   usuarioId+" )}";
                           
                     Logger.write("     Operacion              : bitacoraCobroRespuesta");
                     Logger.write("   + Parametros             +");
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.getConnection(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                         stmsOBJ.setInt(i++, cpId);
                         stmsOBJ.setString(i++, codigoRespCobro);                        
                         stmsOBJ.setString(i++, mensajeCobro);
                         stmsOBJ.setString(i++, idCobroResp);
                         stmsOBJ.setString(i++, responseCobro);
                         stmsOBJ.setString(i++, usuarioId);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
                         retorno = stmsOBJ.getInt(1);
                         
                         Logger.write("   + Respuesta              +");
                         Logger.write("     retorno                  : " + retorno);
                     }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return retorno;
        }
        
        public int bitacoraCambioPlanRespuesta(final int cpId, final String codigoRespCp, final String mensajeRespCp, final String exitoRespCp,final String responseCp,final String usuarioId ) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             int retorno = -1;
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                   commSQL = "{ ? = call MIIUSACELL.PAMIIBITCAMPLNSET.FNUPDLCPCAMBIOPLAN(?,?,?,?,?,?)}";
                   commPRN = "{ ? = call MIIUSACELL.PAMIIBITCAMPLNSET.FNUPDLCPCAMBIOPLAN("+
                   cpId + "," +
                   codigoRespCp + "," +
                   mensajeRespCp + "," +
                   exitoRespCp+","+
                   responseCp+","+
                   usuarioId+" )}";
                           
                     Logger.write("     Operacion              : bitacoraCambioPlanRespuesta");
                     Logger.write("   + Parametros             +");
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.getConnection(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                         stmsOBJ.setInt(i++, cpId);
                         stmsOBJ.setString(i++, codigoRespCp);                       
                         stmsOBJ.setString(i++, mensajeRespCp);
                         stmsOBJ.setString(i++, exitoRespCp);
                         stmsOBJ.setString(i++, responseCp);
                         stmsOBJ.setString(i++, usuarioId);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
                         retorno = stmsOBJ.getInt(1);
                         
                         Logger.write("   + Respuesta              +");
                         Logger.write("     retorno                  : " + retorno);
                    }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return retorno;
        }
        
        public int bitacoraReversoCobroRespuesta(final int cpId, final String codigoRespCobroRev, final String mensajeRespCobroRev, final String idCobroRespCobroRev,final String responseCobroRev,final String usuarioId ) throws ServiceException {

             Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
             Connection connOBJ = null;
             CallableStatement stmsOBJ = null;
             String commSQL = "";
             String commPRN = "";
             ResultSet rsetOBJ = null;
             
             int retorno = -1;
             
             long timeIni = 0;
             int i = 1; 
             
               try{
                   commSQL = "{ ? = call MIIUSACELL.PAMIIBITCAMPLNSET.FNUPDLCPREVERSOCOBRO(?,?,?,?,?,?)}";
                   commPRN = "{ ? = call MIIUSACELL.PAMIIBITCAMPLNSET.FNUPDLCPREVERSOCOBRO("+
                   cpId + "," +
                   codigoRespCobroRev + "," +
                   mensajeRespCobroRev + "," +
                   idCobroRespCobroRev+","+
                   responseCobroRev+","+
                   usuarioId+" )}";
                           
                     Logger.write("     Operacion              : bitacoraReversoCobroRespuesta");
                     Logger.write("   + Parametros             +");
                     Logger.write("     commPRN                : " + commPRN);
                     
                     connOBJ = OracleConnection.getConnection(new MensajeLogBean());
        
                     Logger.write("     Conexion BD Abierta    : true");
                     if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}   
                     
                     if(stmsOBJ != null){
                         stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                         stmsOBJ.setInt(i++, cpId);
                         stmsOBJ.setString(i++, codigoRespCobroRev);                         
                         stmsOBJ.setString(i++, mensajeRespCobroRev);
                         stmsOBJ.setString(i++, idCobroRespCobroRev);
                         stmsOBJ.setString(i++, responseCobroRev);
                         stmsOBJ.setString(i++, usuarioId);
                         
                         Logger.write("     Ejecutando consulta    :");
                         timeIni = System.currentTimeMillis();
                         stmsOBJ.execute();
                         retorno = stmsOBJ.getInt(1);
                         
                         Logger.write("   + Respuesta              +");
                         Logger.write("     retorno                  : " + retorno);
                         
                    }
                     
               }catch(SQLException e){
                     Logger.write("     Detail    : (Exception) " + e.getMessage());     
    
             }finally{
                 if (rsetOBJ != null) try {
                     rsetOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (stmsOBJ != null) try {
                     stmsOBJ.close();
                 }
                 catch (SQLException e) {
                     Logger.write("     Detail  : (SQLException) " + e.getMessage());
                 }
                 if (connOBJ != null){
                     try {
                         connOBJ.close();
                     }
                     catch (SQLException e) {
                         Logger.write("     Detail  : (SQLException) " + e.getMessage());
                     }
                 }
                 Logger.write("     Conexion BD Abierta    : false");    
             }
             Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
             return retorno;
        }       
       
        public int getTransXTarjeta(final String numTarjeta) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            int retorno =  -1;
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i = 1;
            try {
                commORA = "{ ? = call MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNVALIDATRANSACCION(?) }";
                commPRN = "{ ? = call MIIUSACELL.PAMIIABONOTIEMPOAIRESET.FNVALIDATRANSACCION(" + numTarjeta + ") }";

                Logger.write("     Operacion              : getTransXTarjeta");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                stmsOBJ.setString(i++, numTarjeta);
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                retorno = stmsOBJ.getInt(1);
                
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }   

        public BankCardAdditionalInfoVO getBankCardAdditionalInfoBD(final String prefix)throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i = 1;
            BankCardAdditionalInfoVO respuesta=new BankCardAdditionalInfoVO();
            try
            {
                commORA = "{ ? = call PORTAL.PAAPPMIIUSACELL.FNSELAIRE_BINES(?) }";
                commPRN = "{ ? = call PORTAL.PAAPPMIIUSACELL.FNSELAIRE_BINES(" + prefix + ") }";

                Logger.write("     Operacion              : BankCardAdditionalInfo");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBPORTAL(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){

                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setString(i++, prefix);
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                    while(rsetOBJ.next()){
                        respuesta.setCardType((String)rsetOBJ.getString(1));
                        respuesta.setProductName((String)rsetOBJ.getString(2));
                        respuesta.setCardTypeDescription((String)rsetOBJ.getString(3));
                        respuesta.setIssuer((String)rsetOBJ.getString(4));
                        respuesta.setAppliesPromotion((String)rsetOBJ.getString(5));
                    }           
                }
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }

        public String setCardBlackList(final String numTarjeta, final String descOperacion, final String respuesta,final String idUsuario) throws ServiceException, IOException, SQLException {

            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            String idOperacion = "";
            long timeIni = 0;
            int i=1;
            try
            {

                commSQL = "{ ? = call  MIIUSACELL.PAMIISET2.FNINSLOGCARDBLACKLIST(?,?,?,?)}";
                commPRN = "{ ? = call  MIIUSACELL.PAMIISET2.FNINSLOGCARDBLACKLIST("+numTarjeta+","+descOperacion+","+respuesta+","+idUsuario+")}";

                Logger.write("     Operacion              : setCardBlackList");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.getConnection(new MensajeLogBean());
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, numTarjeta);
                    stmsOBJ.setString(i++, descOperacion);
                    stmsOBJ.setString(i++, respuesta);
                    stmsOBJ.setString(i++, idUsuario);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    idOperacion = stmsOBJ.getString(1);

                    Logger.write("   + Respuesta              +");
                    Logger.write("     retorno               : " + idOperacion);
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return  idOperacion;
        }
        
        public String getIssuer(final String IssuerBin) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            String retorno =  "";
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            long timeIni = 0;           
            try {
                commORA = "SELECT ISSUER FROM PORTAL.AIRE_BINES WHERE TRIM(PREFIX) = '" + IssuerBin + "'";

                Logger.write("     Operacion              : GetParametro");
                Logger.write("   + Parametros             +");
                Logger.write("     commORA                : " + commORA);

                connOBJ = OracleConnection.conexionDBPORTAL(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                rsetOBJ = (ResultSet)stmsOBJ.executeQuery();
                while(rsetOBJ.next()){
                    retorno = rsetOBJ.getString(1);
                }

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commORA,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }
        
        public int getCardType(final String cardTypeBin) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            int retorno =  0;
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            long timeIni = 0;           
            try {
                 commORA = "SELECT CARD_TYPE FROM PORTAL.AIRE_BINES WHERE TRIM(PREFIX) = '" + cardTypeBin + "'" ;

                Logger.write("     Operacion              : GetParametro");
                Logger.write("   + Parametros             +");
                Logger.write("     commORA                : " + commORA);

                connOBJ = OracleConnection.conexionDBPORTAL(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                rsetOBJ = (ResultSet)stmsOBJ.executeQuery();
                while(rsetOBJ.next()){
                    retorno = rsetOBJ.getInt(1);
                }

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commORA,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }
                
        public int getChargeId() throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            int retorno =  0;
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            long timeIni = 0;           
            try {
                commORA = "SELECT PORTAL.SEQ_TRANSACCIONES_BANCARIAS.NEXTVAL AS CHARGEID FROM DUAL";

                Logger.write("     Operacion              : GetParametro");
                Logger.write("   + Parametros             +");
                Logger.write("     commORA                : " + commORA);

                connOBJ = OracleConnection.conexionDBPORTAL(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                
                rsetOBJ = (ResultSet)stmsOBJ.executeQuery();
                while(rsetOBJ.next()){
                    retorno = rsetOBJ.getInt(1);
                }

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commORA,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }
                
        public String getBankID(final String BankID) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            String retorno =  "";
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            long timeIni = 0;           
            try {
                commORA = "SELECT BANK_ID FROM PORTAL.AIRE_BINES WHERE TRIM(PREFIX) = '" + BankID + "'"  ;

                Logger.write("     Operacion              : GetParametro");
                Logger.write("   + Parametros             +");
                Logger.write("     commORA                : " + commORA);

                connOBJ = OracleConnection.conexionDBPORTAL(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                rsetOBJ = (ResultSet)stmsOBJ.executeQuery();
                while(rsetOBJ.next()){
                    retorno = rsetOBJ.getString(1);
                }

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commORA,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }
        
        public CuentaCreditoVO getUnidadNegocioCliente(final String dn) throws ServiceException
        {       
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";    
            String commPRN = "";        
            final CuentaCreditoVO retorno=new CuentaCreditoVO();
            ResultSet rs=null;
            int i = 1;
            long timeIni = 0;
            try{            
                commSQL = "{ ? = call  MIIUSACELL.PAMIISELTRANGET.FNSELMIICUENTACREDITO (?,?)}";
                commPRN = "{ ? = call  MIIUSACELL.PAMIISELTRANGET.FNSELMIICUENTACREDITO (NULL,"+dn+")}";

                Logger.write("     Operacion              : FNSELMIICUENTACREDITO ");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                connOBJ = OracleConnection.getConnection(new MensajeLogBean(""));
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setNull(i++, OracleTypes.NULL); 
                    stmsOBJ.setString(i++, dn);                                         

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();                                                      
                    Logger.write("   + Respuesta              +");  
                                
                    rs = (ResultSet)stmsOBJ.getObject(1);
                    i = 1;
                    while (rs.next()) 
                    {
                        retorno.setCuentaCreditoId(rs.getString("FICVECUENTACREDITOID"));
                        retorno.setPaisId(rs.getString("FICVEPAISID"));
                        retorno.setUnidadNegocioId(rs.getString("FICVEUNIDADNEGOCIOID"));
                        retorno.setTipoCuentaId(rs.getString("FICVETIPOCUENTAID"));
                        retorno.setClienteId(rs.getString("FICVECLIENTEID"));
                        retorno.setUsuarioId(rs.getString("FIUSUARIOID"));                      
                    }       
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if(rs != null){
                    try {
                        rs.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }
        
        public int setUnidadNegocioCliente(final CuentaCreditoVO datos) throws ServiceException, IOException, SQLException {
            
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            String commSQL = "";
            String commPRN = "";
            Integer codigo = 0;         
            long timeIni = 0;
            int i=1;
            try
            {
                
                commSQL = "{ ? = call  MIIUSACELL.PAMIIEXETRANSET.FNEXEMIICUENTACREDITO(?,?,?,?,?,?,?)}";
                commPRN = "{ ? = call  MIIUSACELL.PAMIIEXETRANSET.FNEXEMIICUENTACREDITO("
                           +datos.getCuentaCreditoId() +
                        ","+datos.getPaisId()+
                        ","+datos.getUnidadNegocioId()+
                        ","+datos.getTipoCuentaId()+
                        ","+datos.getClienteId()+
                        ","+datos.getUsuarioId()+
                        ",1)}";
                
                Logger.write("     Operacion              : FNEXEMIICUENTACREDITO");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);
                
                connOBJ = OracleConnection.getConnection(new MensajeLogBean(""));
                if(connOBJ != null){ stmsOBJ=connOBJ.prepareCall(commSQL);}        

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.setString(i++, datos.getCuentaCreditoId());
                    stmsOBJ.setString(i++, datos.getPaisId());                  
                    stmsOBJ.setString(i++, datos.getUnidadNegocioId());
                    stmsOBJ.setString(i++, datos.getTipoCuentaId());
                    stmsOBJ.setString(i++, datos.getClienteId());
                    stmsOBJ.setString(i++, datos.getUsuarioId());
                    stmsOBJ.setInt(i++, 1);
                    

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    codigo = (Integer) stmsOBJ.getObject(1);
                    
                    Logger.write("   + Respuesta              +");
                    Logger.write("     codigo                 : " + codigo);                    
                }
            }catch(Exception e){    
                throw new ServiceException(e.getMessage());
            }
            finally {
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return  codigo;
        }
        
        public GruoupIDCancelacionVO getGroupIDCancelacion()throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            final GruoupIDCancelacionVO respuesta= new GruoupIDCancelacionVO();
            try
            {
                commORA = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_GRUPO_CANCELA_FN}";
                commPRN = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_GRUPO_CANCELA_FN}";

                Logger.write("     Operacion              : getGroupIDCancelacion");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBBUCADM();
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                    while(rsetOBJ.next()){
                        respuesta.setGroup_ID(rsetOBJ.getInt("GROUP_ID"));
                        respuesta.setGroup_Desc(rsetOBJ.getString("GROUP_DESC"));
                        respuesta.setDivisional(rsetOBJ.getInt("DIVISIONAL"));
                        respuesta.setGroup_Category(rsetOBJ.getInt("GROUP_CATEGORY"));
                        respuesta.setStatus_Active(rsetOBJ.getInt("STATUS_ACTIVE"));
                        respuesta.setTransaction_ID(rsetOBJ.getInt("TRANSITION_ID"));
                        respuesta.setUserID_Owner(rsetOBJ.getInt("USERID_OWNER"));
                        respuesta.setNote_Activate(rsetOBJ.getInt("NOTE_ACTIVATE"));
                        respuesta.setCatalogue_Activate(rsetOBJ.getInt("CATALOGUE_ACTIVATE"));
                        respuesta.setMecanica_Asignacion(rsetOBJ.getInt("MECANICA_ASIGNACION"));
                        respuesta.setCanal_Atencion(rsetOBJ.getInt("CANAL_ATENCION"));
                    }           
                }
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + respuesta.getGroup_ID());
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        
        public List<CancelacionesPasadasVO> getCancelacionesPasadas(final String dn, final String groupId)throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            CancelacionesPasadasVO respuestaVO= new CancelacionesPasadasVO();
            List<CancelacionesPasadasVO> respuesta = new ArrayList<CancelacionesPasadasVO>();
            
            try
            {
                commORA = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_VALCANCEL_PAS_FN(?,?)}";
                commPRN = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_VALCANCEL_PAS_FN("+ dn +"," + groupId + ") }";;

                Logger.write("     Operacion              : getCancelacionesPasadas");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBBUCADM();
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){
                    
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setLong(i++, Long.parseLong(dn));
                    stmsOBJ.setInt(i++, Integer.parseInt(groupId)); 

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                    while(rsetOBJ.next()){
                        respuestaVO.setTicketNumber(rsetOBJ.getInt("TicketNumber"));
                        respuestaVO.setGroupname(rsetOBJ.getString("Groupname"));
                        respuestaVO.setGroupId(rsetOBJ.getInt("GroupId"));
                        respuestaVO.setLevelValue(rsetOBJ.getString("levelValue1"));
                        respuestaVO.setOpenTime(rsetOBJ.getDate("openTime"));
                        respuestaVO.setNoteDesc(rsetOBJ.getString("noteDesc"));
                        respuestaVO.setStatus(rsetOBJ.getString("status"));
                        respuesta.add(respuestaVO);
                    }           
                }
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + respuesta.size()+" registros");
                
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        
        public int getMotivoCancelacion(final int groupId)throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            int respuesta=0;
            try
            {
                commORA = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_MOTIVO_CANCELA_FN(?)}";
                commPRN = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_MOTIVO_CANCELA_FN("+ groupId + ") }";

                Logger.write("     Operacion              : getMotivoCancelacion");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBBUCADM();
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){
                    
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setInt(i++, groupId);   

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                    while(rsetOBJ.next()){
                        respuesta= rsetOBJ.getInt("TREE_ROOT_ID");
                    }           
                }
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + respuesta );
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        
        
        
        public List<TreeSubgroupCancelacionVO> getSubTreeCancelacion(final int treeId)throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            TreeSubgroupCancelacionVO respuestaVO = new TreeSubgroupCancelacionVO();
            List<TreeSubgroupCancelacionVO> respuesta = new ArrayList<TreeSubgroupCancelacionVO>();
            
            try
            {
                commORA = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_SUBGROUP_FN(?)}";
                commPRN = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_SUBGROUP_FN("+ treeId + ") }";

                Logger.write("     Operacion              : getSubTreeCancelacion");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBBUCADM();
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){
                    
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setInt(i++, treeId);    

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                    while(rsetOBJ.next()){
                        respuestaVO.setTree_ID(rsetOBJ.getInt("TREE_ID"));
                        respuestaVO.setTree_Desc(rsetOBJ.getString("TREE_DESC"));
                        respuesta.add(respuestaVO);
                        respuestaVO = new TreeSubgroupCancelacionVO();
                    }           
                }
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + respuesta.size());
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        
        public int getFieldNumberCancelacion(final int groupId)throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            int respuesta=0;
            try
            {
                commORA = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_FIELD_NUMBER_FN(?)}";
                commPRN = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_FIELD_NUMBER_FN("+ groupId + ") }";;

                Logger.write("     Operacion              : getFieldNumberCancelacion");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBBUCADM();
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){
                    
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setInt(i++, groupId);   

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                    while(rsetOBJ.next()){
                        respuesta= rsetOBJ.getInt("FIELD_NUMBER");
                    }           
                }
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + respuesta);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        
        public String getDivisionCancelacion(final int nir)throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            String respuesta="";
            try
            {
                commORA = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_DIVISION_FN(?)}";
                commPRN = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_DIVISION_FN("+ nir + ") }";;

                Logger.write("     Operacion              : getDivisionCancelacion");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBBUCADM();
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){
                    
                    stmsOBJ.registerOutParameter(i++, OracleTypes.VARCHAR);
                    stmsOBJ.setInt(i++, nir);   

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();
                    respuesta= (String)stmsOBJ.getObject(1);
                }
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + respuesta);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        
        public List<PropertyKeyCancelacionVO> getListPropertyKeyCancelacion()throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            PropertyKeyCancelacionVO res;
            final List<PropertyKeyCancelacionVO> respuesta= new ArrayList<PropertyKeyCancelacionVO>();
            try
            {
                commORA = "{ ? = call  BUCADMIN.BUC_CANCELA_PORTAL_MIATT_PK.BUC_PROPERTY_KEY_FN}";
                commPRN = "{ ? = call  BUCADMIN.BUC_CANCELA_PORTAL_MIATT_PK.BUC_PROPERTY_KEY_FN";

                Logger.write("     Operacion              : getListPropertyKeyCancelacion");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBPORTAL(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                    while(rsetOBJ.next()){
                        res = new PropertyKeyCancelacionVO();
                        res.setPropertyKey(rsetOBJ.getString("PROPERTY_KEY"));
                        res.setPropertyValue(rsetOBJ.getString("PROPERTY_VALUE"));
                        respuesta.add(res);
                    }           
                }
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + respuesta.size());
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        
        public DatosUsuarioCancelacionVO getDatosUsuarioCancelacion(final String login)throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            final DatosUsuarioCancelacionVO respuesta= new DatosUsuarioCancelacionVO();
            try
            {
                commORA = "{ ? = call  BUCADMIN.BUC_CANCELA_PORTAL_MIATT_PK.BUC_USR_LOGIN_FN(?)}";
                commPRN = "{ ? = call  BUCADMIN.BUC_CANCELA_PORTAL_MIATT_PK.BUC_USR_LOGIN_FN("+ login + ") }";

                Logger.write("     Operacion              : getDatosUsuarioCancelacion");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBPORTAL(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){
                    
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setString(i++, login);
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                    while(rsetOBJ.next()){
                        respuesta.setIntIDUser(rsetOBJ.getInt("INTIDUSER"));
                        respuesta.setStrLogin(rsetOBJ.getString("STRLOGIN"));
                        respuesta.setStrFirstName(rsetOBJ.getString("STRFIRSTNAME"));
                        respuesta.setStrLastName(rsetOBJ.getString("STRLASTNAME"));
                    }           
                }
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + respuesta.getIntIDUser());
                Logger.write("                            : " + respuesta.getStrLogin());
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        
        public String getValidaFechaCancelacion(final String dn, final String user_id, final String timeSolicitud )throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            String respuesta="";
            try
            {
                commORA = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_VALIDA_FECHA_FN(?,?,?)}";
                commPRN = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_VALIDA_FECHA_FN("+ dn +"," + user_id + "," +timeSolicitud + ") }";
                //commPRN = "{ ? = call  BUCADMIN.BUC_CANCELA_MIATT_PK.BUC_VALIDA_FECHA_FN("+ dn +"," + user_id + ") }";

                Logger.write("     Operacion              : getValidaFechaCancelacion");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBBUCADM();
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){
                    
                    stmsOBJ.registerOutParameter(i++, OracleTypes.VARCHAR);
                    stmsOBJ.setString(i++, dn); 
                    stmsOBJ.setLong(i++, Long.parseLong(user_id));
                    stmsOBJ.setInt(i++, Integer.parseInt(timeSolicitud));

                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();

                    respuesta = (String) stmsOBJ.getObject(1);
                            
                }
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + respuesta);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        
        public String getCAEUsuarioCancelacion(final String idUser )throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            String respuesta = "";
            try
            {
                commORA = "{ ? = call  BUCADMIN.BUC_CANCELA_PORTAL_MIATT_PK.BUC_CAE_FN(?)}";
                commPRN = "{ ? = call  BUCADMIN.BUC_CANCELA_PORTAL_MIATT_PK.BUC_CAE_FN("+ idUser + ") }";

                Logger.write("     Operacion              : getCAEUsuarioCancelacion");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBPORTAL(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){
                    
                    stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
                    stmsOBJ.setString(i++, idUser);
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();
                    
                    rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
                    while(rsetOBJ.next()){
                        respuesta= rsetOBJ.getString("DESC_CSI");
                    }
                    
                }
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + respuesta);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        
        public ResponsCreateQueueVO setSolicitudCancelacion(DatosClienteCancelacionVO datosCliente, long longUserId, int intSkillId, long longCallId, String strDivision)throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            ResponsCreateQueueVO respuesta= new ResponsCreateQueueVO();
            try
            {
                commORA = "{ call  BUCADMIN.BUC_PACK_CODIFICADOR.BUC_PROC_CREATE_QUEUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ call  BUCADMIN.BUC_PACK_CODIFICADOR.BUC_PROC_CREATE_QUEUE("+
                                       datosCliente.getLongContract() // longContract 
                                +"," + datosCliente.getStrMdn() // strMdn de contacto
                                +"," + datosCliente.getStrCustcode() // Cuenta del Cliente 1.7559
                                +"," + longUserId   
                                +"," + datosCliente.getIntContactType() // intContactType (prepago, pospago, hibrido) y realiza validaciones de acuerdo a esto
                                +"," + intSkillId
                                +"," + longCallId
                                +"," + datosCliente.getIntPaymentType() // intPaymentType
                                +"," + datosCliente.getIntCompany() // intCompany
                                +"," + datosCliente.getStrESN() // strESN
                                +"," + datosCliente.getLongCustomerId() // longCustomerId
                                +"," + datosCliente.getStrCustomerName() // strCustomerName
                                +"," + datosCliente.getStrPrgName()  // strPrgName
                                +"," + datosCliente.getStrPlan() // strPlan
                                +"," + datosCliente.getStrSegment() // strSegment
                                +"," + datosCliente.getStrRFC() // strRFC
                                +"," + datosCliente.getStrAddress() // strAddress
                                +"," + strDivision
                                +"," + datosCliente.getStrCAE() // strCAE
                                +"," + datosCliente.getStrLogin()  //dn
                                +"," + datosCliente.getStrNombre() // strNombre 
                                + ") }";

                Logger.write("     Operacion              : setSolicitudCancelacion");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBBUCADM();
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){
                    stmsOBJ.setLong(i++, datosCliente.getLongContract());   
                    stmsOBJ.setString(i++, datosCliente.getStrMdn());
                    stmsOBJ.setString(i++, datosCliente.getStrCustcode());
                    stmsOBJ.setLong(i++, longUserId);
                    stmsOBJ.setInt(i++, datosCliente.getIntContactType());
                    stmsOBJ.setInt(i++, intSkillId);
                    stmsOBJ.setLong(i++, longCallId);
                    stmsOBJ.setInt(i++, datosCliente.getIntPaymentType());
                    stmsOBJ.setInt(i++, datosCliente.getIntCompany());
                    stmsOBJ.setString(i++, datosCliente.getStrESN());
                    stmsOBJ.setLong(i++, datosCliente.getLongCustomerId());
                    stmsOBJ.setString(i++, datosCliente.getStrCustomerName());
                    stmsOBJ.setString(i++, datosCliente.getStrPrgName());
                    stmsOBJ.setString(i++, datosCliente.getStrPlan());
                    stmsOBJ.setString(i++, datosCliente.getStrSegment());
                    stmsOBJ.setString(i++, datosCliente.getStrRFC());
                    stmsOBJ.setString(i++, datosCliente.getStrAddress());
                    stmsOBJ.setString(i++, strDivision);
                    stmsOBJ.setString(i++, datosCliente.getStrCAE());
                    stmsOBJ.setString(i++, datosCliente.getStrLogin());
                    stmsOBJ.setString(i++, datosCliente.getStrNombre());
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.registerOutParameter(i++, OracleTypes.VARCHAR);
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();
                    
                    respuesta.setPnoErrorCode(stmsOBJ.getInt(22));
                    respuesta.setPsoErrorMsg(stmsOBJ.getString(23));
                }
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + respuesta.getPnoErrorCode());
                Logger.write("                            : " + respuesta.getPsoErrorMsg());
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        
        public ResponseCancelacionVO saveSolicitudCancelacion(DatosClienteCancelacionVO datosCliente, CallTreeVO callTree , long longUserId, int intSkillId, long longCallId, String strDivision)throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            Connection connOBJ = null;
            Connection connOBJOra= null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            String commPRN = "";
            long timeIni = 0;
            int i=1;
            ResponseCancelacionVO respuesta= new ResponseCancelacionVO();
            try
            {
                commORA = "{ call  BUCADMIN.BUC_PACK_CODIFICADOR.BUC_PROC_CREATE_CONTACT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                commPRN = "{ call  BUCADMIN.BUC_PACK_CODIFICADOR.BUC_PROC_CREATE_CONTACT("+
                                       callTree.getStrCallId()
                                +"," + datosCliente.getIntPaymentType() // intPaymentType
                                +"," + datosCliente.getIntCompany()
                                +"," + datosCliente.getStrMdn() // strMdn de contacto
                                +"," + datosCliente.getStrESN() // strESN
                                +"," + datosCliente.getLongContract() // longContract 
                                +"," + datosCliente.getStrCustcode() // Cuenta del Cliente 1.7559
                                +"," + datosCliente.getLongCustomerId() // longCustomerId
                                +"," + datosCliente.getStrCustomerName() // strCustomerName
                                +"," + datosCliente.getStrPrgName()  // strPrgName
                                +"," + datosCliente.getStrPlan() // strPlan
                                +"," + datosCliente.getStrSegment() // strSegment
                                +"," + datosCliente.getStrRFC() // strRFC
                                +"," + datosCliente.getStrAddress() // strAddress
                                +"," + longUserId   
                                +"," + datosCliente.getStrLogin()  //dn
                                +"," + datosCliente.getStrNombre() // strNombre 
                                +"," + strDivision
                                +"," + datosCliente.getStrCAE() // strCAE
                                +"," + intSkillId
                                +"," + datosCliente.getStrTecnologia()
                                +"," + callTree.getGroupId()
                                +"," + Utilerias.imprimeVectorString(callTree.getCategoryDesc())
                                +"," + Utilerias.imprimeVectorInt(callTree.getCategoryId())
                                +"," + "0"//NoIncident
                                +"," + callTree.getNoteDesc()
                                +"," + callTree.getNoteId()
                                + ") }";

                Logger.write("     Operacion              : saveSolicitudCancelacion");
                Logger.write("   + Parametros             +");
                Logger.write("     commPRN                : " + commPRN);

                connOBJ = OracleConnection.conexionDBBUCADM();
                
                
                Logger.write("     Conexion BD Abierta    : true");
                if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commORA);}

                if(stmsOBJ != null){
                    stmsOBJ.setLong(i++, Long.parseLong(callTree.getStrCallId()));
                    stmsOBJ.setLong(i++, datosCliente.getIntPaymentType()); // intPaymentType
                    stmsOBJ.setLong(i++, datosCliente.getIntCompany());
                    stmsOBJ.setLong(i++, Long.parseLong(datosCliente.getStrMdn())); // strMdn de contacto
                    stmsOBJ.setString(i++, datosCliente.getStrESN()); // strESN
                    stmsOBJ.setLong(i++, datosCliente.getLongContract()); // longContract 
                    stmsOBJ.setString(i++,datosCliente.getStrCustcode()); // Cuenta del Cliente 1.7559
                    stmsOBJ.setLong(i++, datosCliente.getLongCustomerId()); // longCustomerId
                    stmsOBJ.setString(i++,datosCliente.getStrCustomerName()); // strCustomerName
                    stmsOBJ.setString(i++,datosCliente.getStrPrgName());  // strPrgName
                    stmsOBJ.setString(i++,datosCliente.getStrPlan()); // strPlan
                    stmsOBJ.setString(i++,datosCliente.getStrSegment()); // strSegment
                    stmsOBJ.setString(i++,datosCliente.getStrRFC()); // strRFC
                    stmsOBJ.setString(i++,datosCliente.getStrAddress()); // strAddress
                    stmsOBJ.setLong(i++,longUserId);   
                    stmsOBJ.setString(i++,datosCliente.getStrLogin());  //dn
                    stmsOBJ.setString(i++,datosCliente.getStrNombre()); // strNombre 
                    stmsOBJ.setString(i++,strDivision);
                    stmsOBJ.setString(i++,datosCliente.getStrCAE()); // strCAE
                    stmsOBJ.setLong(i++,intSkillId);
                    stmsOBJ.setLong(i++,Long.parseLong(datosCliente.getStrTecnologia()));
                    stmsOBJ.setLong(i++,Long.parseLong(callTree.getGroupId()));
                    
                    connOBJOra=OracleConnection.getOracleConnectionBUCADM();
                    ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor("BUCADMIN.BUC_TYPE_LEVEL", connOBJOra);
                    String[] content;
                    if (callTree.getCategoryDesc() == null || callTree.getCategoryDesc().length==0){//TEST
                        content = new String[]{"SOLICITUD DE CANCELACION","","","","",""};
                    }else{
                        content = callTree.getCategoryDesc();
                    }
                    Logger.write("Vector BUC_TYPE_LEVEL");
                    Logger.write(Utilerias.imprimeVectorString(content));
                    
                    Array sqlArray = new ARRAY(arrayDescriptor, connOBJOra, content);
                    
                    stmsOBJ.setArray(i++,sqlArray);
                    int[] contentInt;
                    if(callTree.getCategoryId() == null  || callTree.getCategoryId().length==0){//TEST
                        contentInt= new int[]{0,0,0,0,0,0,0,0,0,0};
                    }else{
                        contentInt=callTree.getCategoryId();
                    }
                    Logger.write("Vector BUC_TYPE_TREE_ID");
                    Logger.write(Utilerias.imprimeVectorString(contentInt));
                    
                    ArrayDescriptor arrayDescriptorInt = ArrayDescriptor.createDescriptor("BUCADMIN.BUC_TYPE_TREE_ID", connOBJOra);
                    sqlArray = new ARRAY(arrayDescriptorInt, connOBJOra, contentInt);
                    stmsOBJ.setArray(i++,sqlArray);//callTree.getCategoryId());
                    
                    stmsOBJ.setLong(i++,0);//NoIncident
                    
                    
                    String[] contentDos={callTree.getNoteDesc()};
                    arrayDescriptor = ArrayDescriptor.createDescriptor("BUCADMIN.BUC_TYPE_NOTE_DESC", connOBJOra);
                    sqlArray = new ARRAY(arrayDescriptor, connOBJOra, contentDos);
                    
                    stmsOBJ.setArray(i++,sqlArray);//callTree.getNoteDesc());
                    
                    int[] contentIntDos = {Integer.parseInt(callTree.getNoteId())};
                    arrayDescriptor = ArrayDescriptor.createDescriptor("BUCADMIN.BUC_TYPE_NOTE_TYPE_ID", connOBJOra);
                    sqlArray = new ARRAY(arrayDescriptor, connOBJOra, contentIntDos);
                    
                    stmsOBJ.setArray(i++,sqlArray);//callTree.getNoteId());
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.registerOutParameter(i++, OracleTypes.INTEGER);
                    stmsOBJ.registerOutParameter(i++, OracleTypes.VARCHAR);
                    
                    
                    
                    Logger.write("     Ejecutando consulta    :");
                    timeIni = System.currentTimeMillis();
                    stmsOBJ.execute();
                    
                    respuesta.setCallId(stmsOBJ.getInt(28));
                    respuesta.setNoInteractionId(stmsOBJ.getInt(29));
                    respuesta.setNoSolicitudId(stmsOBJ.getInt(30));
                    respuesta.setNoErrorCode(stmsOBJ.getInt(31));
                    respuesta.setNoErrorMsg(stmsOBJ.getString(32));                 
                }
                Logger.write("   + Respuesta              +");
                Logger.write("     ErrorCode              : " + respuesta.getNoErrorCode());
                Logger.write("     ErrorMsg               : " + respuesta.getNoErrorMsg());
                Logger.write("     SolicitudId            : " + respuesta.getNoSolicitudId());
            }
            catch (Exception e) {      
                throw new ServiceException(e.getLocalizedMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return respuesta;
        }
        public String getPrecioMercadoInsurance(final String cdgProducto) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            String retorno =  "";
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            long timeIni = 0;           
            try {
                commORA = "SELECT FI_MERCADO FROM NPDV.TAPVS_MERCADO_VAL WHERE FC_CGDPRODUCTO = '" + cdgProducto + "'";

                Logger.write("     Operacion              : getPrecioMercadoInsurance");
                Logger.write("   + Parametros             +");
                Logger.write("     commORA                : " + commORA);

                connOBJ = OracleConnection.conexionDBPORTAL(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                rsetOBJ = (ResultSet)stmsOBJ.executeQuery();
                while(rsetOBJ.next()){
                    retorno = rsetOBJ.getString(1);
                }

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commORA,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        }
        public List<SNCODEInsuranceVO> getSNCODEInsurance(final String precioMercadoInsurance) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            String retorno =  "";
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            long timeIni = 0;
            List<SNCODEInsuranceVO> sncodesInsurance = new ArrayList<SNCODEInsuranceVO>();
            String[] psncodesSplit = {""} ;
            String cadenaSplit ="";
            try {
                SNCODEInsuranceVO sncodeTmp= null;
                cadenaSplit = getValorParametro(226);
                
                if (!cadenaSplit.equals("")){
                    psncodesSplit = cadenaSplit.split(",");
                }
                for (String tmpSncodeBD : psncodesSplit){
                    sncodeTmp= new SNCODEInsuranceVO();
                    sncodeTmp.setSncode(tmpSncodeBD);
                    sncodeTmp.setFlagActivo(0);
                    sncodesInsurance.add(sncodeTmp);
                }

                commORA = "SELECT * FROM NPDV.TAPVS_SEGPROTEC WHERE  FI_RANGO_INICIO < " + precioMercadoInsurance +" AND " +
                                    "FI_RANGO_FIN >=(SELECT MAX (FI_RANGO_FIN) FROM NPDV.TAPVS_SEGPROTEC WHERE  FI_RANGO_INICIO<" + precioMercadoInsurance +")";

                Logger.write("     Operacion              : getSNCODEInsurance");
                Logger.write("   + Parametros             +");
                Logger.write("     commORA                : " + commORA);
                Logger.write("     sncodesSplit           : " + cadenaSplit);

                connOBJ = OracleConnection.conexionDBPORTAL(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                rsetOBJ = (ResultSet)stmsOBJ.executeQuery();
                while(rsetOBJ.next()){
                    retorno = rsetOBJ.getString(1);
                }

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
                int i=0;
                for(SNCODEInsuranceVO tmpSncode : sncodesInsurance){
                    if (tmpSncode.getSncode().equalsIgnoreCase(retorno)){
                        sncodesInsurance.get(i).setFlagActivo(1);
                    }
                    i++;
                }
            }
            catch (Exception e) {      
                Logger.write("     Detail  : (Exception) " + e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commORA,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return sncodesInsurance;
        }
        public String getCDG_ProductoInsurance(final String ESN) throws ServiceException {
            Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
            String retorno =  "";
            Connection connOBJ = null;
            CallableStatement stmsOBJ = null;
            ResultSet rsetOBJ = null;
            String commORA = "";
            long timeIni = 0;           
            try {
                commORA = "SELECT CDG_PRODUCTO FROM NPDV.SERIES_DIST WHERE ESN='" + ESN +"'";

                Logger.write("     Operacion              : getCDG_ProductoInsurance");
                Logger.write("   + Parametros             +");
                Logger.write("     commORA                : " + commORA);

                connOBJ = OracleConnection.conexionDBPORTAL(new MensajeLogBean());
                Logger.write("     Conexion BD Abierta    : true");
                stmsOBJ = connOBJ.prepareCall(commORA);
                Logger.write("     Ejecutando consulta    :");
                timeIni = System.currentTimeMillis();
                rsetOBJ = (ResultSet)stmsOBJ.executeQuery();
                while(rsetOBJ.next()){
                    retorno = rsetOBJ.getString(1);
                }

                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }
            catch (Exception e) {      
                throw new ServiceException(e.getMessage());
            }
            finally {

                if(rsetOBJ != null){
                    try {
                        rsetOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                if (stmsOBJ != null) try {
                    stmsOBJ.close();
                }
                catch (SQLException e) {
                    Logger.write("     Detail  : (SQLException) " + e.getMessage());
                }
                if (connOBJ != null){
                    try {
                        connOBJ.close();
                    }
                    catch (SQLException e) {
                        Logger.write("     Detail  : (SQLException) " + e.getMessage());
                    }
                }
                Logger.write("     Conexion BD Abierta    : false");
            }       
            Logger.timeTx(commORA,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
            return retorno;
        } 
        
         public List<PlanVO> getOferta () throws ServiceException {

		     Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s - getOferta");
			 Connection connOBJ = null;
			 CallableStatement stmsOBJ = null;
			 String commSQL = "";
			 String commPRN = "";
			 ResultSet rsetOBJ = null;
			 
			 List<PlanVO> catalogoOfertaList = new ArrayList<PlanVO>();
			 
			 long timeIni = 0;
			 int i = 1; 
			 
		       try{
		      	 commSQL = "{ ? = call SCCPPIUSA.PACPPPRESUPUESTOGETPROMO.FNCPPCAREQPPROMOTOTAL()}";
		      	 commPRN = "{ ? = call SCCPPIUSA.PACPPPRESUPUESTOGETPROMO.FNCPPCAREQPPROMOTOTAL()";
						        
					 Logger.write("     Operacion              : getOferta");
					 Logger.write("   + Parametros             +");
					 Logger.write("     commPRN                : " + commPRN);
					 
					 connOBJ = OracleConnection.conexionDBOferta(new MensajeLogBean());
		
					 Logger.write("     Conexion BD Abierta    : true");
					 if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}	  
					 
					 if(stmsOBJ != null){
						 stmsOBJ.registerOutParameter(i++, OracleTypes.CURSOR);
						 Logger.write("     Ejecutando consulta  - getOferta  :");
						 timeIni = System.currentTimeMillis();
						 stmsOBJ.execute();
						 rsetOBJ = (ResultSet)stmsOBJ.getObject(1);
						 
						 while(rsetOBJ.next()){
							 final PlanVO catalogoOferta= new PlanVO();
							 catalogoOferta.setMinutos(rsetOBJ.getString("MINUTOS"));
							 catalogoOferta.setMinutoscomunidad (rsetOBJ.getString("MINUTOSCOMUNIDAD"));
							 catalogoOferta.setMensajes(rsetOBJ.getString("MENSAJES"));
							 catalogoOferta.setMegas(rsetOBJ.getString("MEGAS"));
							 catalogoOferta.setMinutoadc(rsetOBJ.getString("MINUTOADC"));
							 catalogoOferta.setMensajeadc(rsetOBJ.getString("MENSAJEADC"));
							 catalogoOferta.setMegaadc(rsetOBJ.getString("MEGAADC"));
							 catalogoOferta.setNombreCortoPlan(rsetOBJ.getString("NOMBRECORTO"));
							 catalogoOferta.setTiempoAire(rsetOBJ.getString("TIEMPOAIRE"));
							 catalogoOferta.setMegaPromo(rsetOBJ.getString("MEGASPROMO"));
							 catalogoOferta.setRenta(rsetOBJ.getString("RENTA"));
							 catalogoOferta.setMinotscia(rsetOBJ.getString("MINOTSCIA"));
							 catalogoOferta.setIdFamilia(rsetOBJ.getInt("FISUBFAMILIAID"));
							 catalogoOferta.setIdPlan(rsetOBJ.getString("IDPLAN"));
							 catalogoOfertaList.add(catalogoOferta);			
						 }
					 }
					 
		       }catch(SQLException e){
					 Logger.write("     Detail    : (Exception) " + e.getMessage());     
	
			 }finally{
				 if (rsetOBJ != null) try {
					 rsetOBJ.close();
				 }
				 catch (SQLException e) {
					 Logger.write("     Detail  : (SQLException) " + e.getMessage());
				 }
				 if (stmsOBJ != null) try {
					 stmsOBJ.close();
				 }
				 catch (SQLException e) {
					 Logger.write("     Detail  : (SQLException) " + e.getMessage());
				 }
				 if (connOBJ != null){
					 try {
						 connOBJ.close();
					 }
					 catch (SQLException e) {
						 Logger.write("     Detail  : (SQLException) " + e.getMessage());
					 }
				 }
				 Logger.write("     Conexion BD Abierta    : false");	 
			 }
			 Logger.timeTx(commPRN,"     Tiempo ejecucion   - getOferta    : " + Util.tipoRespuesta(timeIni));
			 return catalogoOfertaList;
		}
        
        public PuntosVO getPuntos (final int idFamilia, final String idPlan) throws ServiceException {

		     Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s - getOferta");
			 Connection connOBJ = null;
			 CallableStatement stmsOBJ = null;
			 String commSQL = "";
			 String commPRN = "";
			 ResultSet rsetOBJ = null;
			 
			 PuntosVO puntos = new PuntosVO();
			 
			 long timeIni = 0;
			 int i = 1; 
			 
		       try{
		      	 commSQL = "{ call SCCPPIUSA.PACPPPRESUPUESTOGETPROMO.PRCPPPUNTOSMIATTCOL (?,?,?,?,?,?,?,?)}";
		      	 commPRN = "{ call SCCPPIUSA.PACPPPRESUPUESTOGETPROMO.PRCPPPUNTOSMIATTCOL()";
						        
					 Logger.write("     Operacion              : getPuntos");
					 Logger.write("   + Parametros             +");
					 Logger.write("     commPRN                : " + commPRN);
					 
					 connOBJ = OracleConnection.conexionDBOferta(new MensajeLogBean());
		
					 Logger.write("     Conexion BD Abierta    : true");
					 if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}	  
					 
					 if(stmsOBJ != null){						 
						 stmsOBJ.setInt(i++, idFamilia);
						 stmsOBJ.setString(i++, idPlan);
						 stmsOBJ.registerOutParameter(i++, OracleTypes.VARCHAR);
						 stmsOBJ.registerOutParameter(i++, OracleTypes.VARCHAR);
						 stmsOBJ.registerOutParameter(i++, OracleTypes.NUMBER);
						 stmsOBJ.registerOutParameter(i++, OracleTypes.NUMBER);
						 stmsOBJ.registerOutParameter(i++, OracleTypes.NUMBER);
						 stmsOBJ.registerOutParameter(i++, OracleTypes.NUMBER);
						 
						 Logger.write("     Ejecutando consulta  - getPuntos  :");
						 timeIni = System.currentTimeMillis();
						 stmsOBJ.execute();
						
						 puntos.setNombreFamilia(stmsOBJ.getString(3));
						 puntos.setNombrePlan(stmsOBJ.getString(4));
						 puntos.setPlanSIva(stmsOBJ.getDouble(5));
						 puntos.setPuntos(stmsOBJ.getDouble(6));
						 puntos.setPremioPor(stmsOBJ.getDouble(7));
						 puntos.setPuntosATM(stmsOBJ.getDouble(8));						 						
					 }
					 
		       }catch(SQLException e){
					 Logger.write("     Detail    : (Exception) " + e.getMessage());     
	
			 }finally{
				 if (rsetOBJ != null) try {
					 rsetOBJ.close();
				 }
				 catch (SQLException e) {
					 Logger.write("     Detail  : (SQLException) " + e.getMessage());
				 }
				 if (stmsOBJ != null) try {
					 stmsOBJ.close();
				 }
				 catch (SQLException e) {
					 Logger.write("     Detail  : (SQLException) " + e.getMessage());
				 }
				 if (connOBJ != null){
					 try {
						 connOBJ.close();
					 }
					 catch (SQLException e) {
						 Logger.write("     Detail  : (SQLException) " + e.getMessage());
					 }
				 }
				 Logger.write("     Conexion BD Abierta    : false");	 
			 }
			 Logger.timeTx(commPRN,"     Tiempo ejecucion   - getPuntos    : " + Util.tipoRespuesta(timeIni));
			 return puntos;
		}
        
    public int vincularDomicilioTarjeta(final String tarjeta, final String dn, final int domicilioID)throws ServiceException
    {
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection        conexionBD  = null;
        CallableStatement sentenciaBD = null;
        
        String commSQL = "";
        String commPRN = "";
        
        int  respuestaBD = -1;
        int  paramIndex  =  1;
        long timeIni     =  0;
        
        try
        {
            commSQL = "{ ? = call  MIIUSACELL.PAMIISET2.FNUPDMIITATARJETACREDDOM(?,?,?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIISET2.FNUPDMIITATARJETACREDDOM("
                + tarjeta     + ","
                + dn          + ","
                + domicilioID + ")}";
            
            conexionBD = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Operacion              : VINCULA_TARJETA_DOMICILIO (" + dn + ")");
            Logger.write("   + Parametros             + ");
            Logger.write("     commPRN                : " + commPRN);
            if(conexionBD != null){sentenciaBD=conexionBD.prepareCall(commSQL);}
            Logger.write("     Conexion BD Abierta    : true");
            
            if(sentenciaBD != null)
            {
                sentenciaBD.registerOutParameter(paramIndex++, OracleTypes.INTEGER );
                sentenciaBD.setString(paramIndex++, tarjeta     );
                sentenciaBD.setString(paramIndex++, dn          );
                sentenciaBD.setInt(   paramIndex++, domicilioID );
                
                Logger.write(" (+) Ejecutando consulta    : ");
                timeIni = System.currentTimeMillis();
                sentenciaBD.execute();
                Logger.write(" (-) Ejecutando consulta    : " + Util.tipoRespuesta(timeIni));
                
                respuestaBD = sentenciaBD.getInt(1);
                
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + respuestaBD);
            }
        }
        catch(Exception e)
        {
            throw new ServiceException(procesaMensajeSQLException(e.getMessage()));
        }
        finally
        {
            if (sentenciaBD != null){
                try { sentenciaBD.close(); }
                catch (SQLException e) { Logger.write("     Detail  : (SQLException) " + e.getMessage()); }
            }
            
            if (conexionBD != null){
                try { conexionBD.close(); }
                catch (SQLException e) { Logger.write("     Detail  : (SQLException) " + e.getMessage()); }
            }
            
            Logger.write("     Conexion BD Abierta    : false");
        }
        
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return respuestaBD;
    }
    
    public int altaTarjetaDomicilio(final TarjetaVO datosTarjeta, final int domicilioId, final int usuarioId) throws ServiceException
    {
        Logger.write(" >>> C o n s u l t a  a  B a s e  d e  D a t o s");
        Connection connOBJ = null;
        CallableStatement stmsOBJ = null;
        
        String commSQL = "";
        String commPRN = "";
        int retorno=0;
        long timeIni = 0;
        int paramIndex = 1;
        
        try
        {
            commSQL = "{ ? = call  MIIUSACELL.PAMIISET2.FNINSMIITATARJETACREDALTADOM (?,?,?,?,?,?,?,?,?,?,?,?)}";
            commPRN = "{ ? = call  MIIUSACELL.PAMIISET2.FNINSMIITATARJETACREDALTADOM ("+
                datosTarjeta.getMarcaTarjeta()    + "," +
                domicilioId                       + "," +
                datosTarjeta.getNumeroTarjeta()   + "," +
                datosTarjeta.getUltimosDigitos()  + "," +
                datosTarjeta.getMesVencimiento()  + "," +
                datosTarjeta.getAnioVencimiento() + "," +
                datosTarjeta.getNombre()          + "," +
                datosTarjeta.getaPaterno()        + "," +
                datosTarjeta.getaMaterno()        + "," +
                datosTarjeta.getCp()              + "," +
                datosTarjeta.getDn()              + "," +
                usuarioId                         + ")}";
            
            connOBJ = OracleConnection.getConnection(new MensajeLogBean());
            
            Logger.write("     Operacion              : altaTarjeta");
            Logger.write("   + Parametros             +");
            Logger.write("     commPRN                : " + commPRN);
            
            if(connOBJ != null){stmsOBJ=connOBJ.prepareCall(commSQL);}
            Logger.write("     Conexion BD Abierta    : true");
            if(stmsOBJ != null)
            {
                stmsOBJ.registerOutParameter(paramIndex++, OracleTypes.INTEGER    );
                stmsOBJ.setInt   (paramIndex++, datosTarjeta.getMarcaTarjeta()    );
                stmsOBJ.setInt   (paramIndex++, domicilioId                       );
                stmsOBJ.setString(paramIndex++, datosTarjeta.getNumeroTarjeta()   );
                stmsOBJ.setString(paramIndex++, datosTarjeta.getUltimosDigitos()  );
                stmsOBJ.setString(paramIndex++, datosTarjeta.getMesVencimiento()  );
                stmsOBJ.setString(paramIndex++, datosTarjeta.getAnioVencimiento() );
                stmsOBJ.setString(paramIndex++, datosTarjeta.getNombre()          );
                stmsOBJ.setString(paramIndex++, datosTarjeta.getaPaterno()        );
                stmsOBJ.setString(paramIndex++, datosTarjeta.getaMaterno()        );
                stmsOBJ.setString(paramIndex++, datosTarjeta.getCp()              );
                stmsOBJ.setString(paramIndex++, datosTarjeta.getDn()              );
                stmsOBJ.setInt   (paramIndex++, usuarioId                         );
                
                Logger.write(" (+) Ejecutando consulta    : ");
                timeIni = System.currentTimeMillis();
                stmsOBJ.execute();
                Logger.write(" (-) Ejecutando consulta    : " + Util.tipoRespuesta(timeIni));
                
                retorno = stmsOBJ.getInt(1);
                
                Logger.write("   + Respuesta              +");
                Logger.write("     retorno                : " + retorno);
            }
        }
        catch(Exception e)
        {
            throw new ServiceException(procesaMensajeSQLException(e.getMessage()));
        }
        finally
        {
            if (stmsOBJ != null){
                try { stmsOBJ.close(); }
                catch (SQLException e) { Logger.write("     Detail  : (SQLException) " + e.getMessage()); }
                
            } 
            
            if (connOBJ != null){
                try { connOBJ.close(); }
                catch (SQLException e) { Logger.write("     Detail  : (SQLException) " + e.getMessage()); }
            }
            
            Logger.write("     Conexion BD Abierta    : false");
        }
        
        Logger.timeTx(commPRN,"     Tiempo ejecucion       : " + Util.tipoRespuesta(timeIni));
        return retorno;
    }
    
    private String procesaMensajeSQLException(final String mensaje){
        String respuesta = mensaje;
        if(StringUtils.contains(mensaje, "[ctrl]")){
            String[] secciones = mensaje.split("ctrl]");
            secciones = secciones[1].split("\\n");
            respuesta = secciones[0].trim();
        }
        return respuesta;
    }
}