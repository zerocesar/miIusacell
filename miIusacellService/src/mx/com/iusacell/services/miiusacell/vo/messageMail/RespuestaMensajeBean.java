package mx.com.iusacell.services.miiusacell.vo.messageMail;

import java.io.Serializable;

public class RespuestaMensajeBean implements Serializable
{
    private static final long serialVersionUID = 201703230401227L;
    private Boolean enviado;
    private String mensaje;
    
    public Boolean getEnviado()
    {
        return enviado;
    }
    public void setEnviado(Boolean enviado)
    {
        this.enviado = enviado;
    }
    public String getMensaje()
    {
        return mensaje;
    }
    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }
}
