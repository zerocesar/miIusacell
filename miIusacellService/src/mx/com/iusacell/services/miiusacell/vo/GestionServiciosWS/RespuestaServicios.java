package mx.com.iusacell.services.miiusacell.vo.GestionServiciosWS;

import java.io.Serializable;

public class RespuestaServicios implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Boolean respuesta;
    private Integer folioPreactivacion;
    private Integer codigo;
    private String mensaje;
    private Integer idTx;
    
    public Boolean getRespuesta(){
        return respuesta;
    }
    public void setRespuesta(Boolean respuesta){
        this.respuesta = respuesta;
    }
    public Integer getFolioPreactivacion(){
        return folioPreactivacion;
    }
    public void setFolioPreactivacion(Integer folioPreactivacion)
    {
        this.folioPreactivacion = folioPreactivacion;
    }
    public Integer getCodigo()
    {
        return codigo;
    }
    public void setCodigo(Integer codigo)
    {
        this.codigo = codigo;
    }
    public String getMensaje()
    {
        return mensaje;
    }
    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }
    public Integer getIdTx()
    {
        return idTx;
    }
    public void setIdTx(Integer idTx)
    {
        this.idTx = idTx;
    }
}
