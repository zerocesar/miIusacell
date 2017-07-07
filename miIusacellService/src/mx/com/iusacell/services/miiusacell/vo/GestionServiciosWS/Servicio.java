package mx.com.iusacell.services.miiusacell.vo.GestionServiciosWS;

import java.io.Serializable;
import java.util.Calendar;

public class Servicio implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Boolean basico;
    private Boolean red;
    private String descripcion;
    private String id;
    private String costo;
    private Calendar activacion;
    private Calendar desactivacion;
    private Integer origen;
    private Vigencia[] vigencias;
    private Integer tipo;
    private Integer idHistorico;
    private String valor;
    
    
    public Boolean getBasico()
    {
        return basico;
    }
    public void setBasico(Boolean basico)
    {
        this.basico = basico;
    }
    public Boolean getRed()
    {
        return red;
    }
    public void setRed(Boolean red)
    {
        this.red = red;
    }
    public String getDescripcion()
    {
        return descripcion;
    }
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getCosto()
    {
        return costo;
    }
    public void setCosto(String costo)
    {
        this.costo = costo;
    }
    public Calendar getActivacion()
    {
        return activacion;
    }
    public void setActivacion(Calendar activacion)
    {
        this.activacion = activacion;
    }
    public Calendar getDesactivacion()
    {
        return desactivacion;
    }
    public void setDesactivacion(Calendar desactivacion)
    {
        this.desactivacion = desactivacion;
    }
    public Integer getOrigen()
    {
        return origen;
    }
    public void setOrigen(Integer origen)
    {
        this.origen = origen;
    }
    public Vigencia[] getVigencias()
    {
        return vigencias;
    }
    public void setVigencias(Vigencia[] vigencias)
    {
        this.vigencias = vigencias;
    }
    public Integer getTipo()
    {
        return tipo;
    }
    public void setTipo(Integer tipo)
    {
        this.tipo = tipo;
    }
    public Integer getIdHistorico()
    {
        return idHistorico;
    }
    public void setIdHistorico(Integer idHistorico)
    {
        this.idHistorico = idHistorico;
    }
    public String getValor()
    {
        return valor;
    }
    public void setValor(String valor)
    {
        this.valor = valor;
    }
}
