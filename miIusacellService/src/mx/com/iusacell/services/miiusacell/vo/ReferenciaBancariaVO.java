package mx.com.iusacell.services.miiusacell.vo;

import java.io.Serializable;

public class ReferenciaBancariaVO implements Serializable
{
    private static final long serialVersionUID = 201705261146L;
    private String convenio;
    private String referenciaBancaria;
    private String banco;
    
    public final String getConvenio()
    {
        return convenio;
    }
    public final void setConvenio(String convenio)
    {
        this.convenio = convenio;
    }
    public final String getReferenciaBancaria()
    {
        return referenciaBancaria;
    }
    public final void setReferenciaBancaria(String referenciaBancaria)
    {
        this.referenciaBancaria = referenciaBancaria;
    }
    public final String getBanco()
    {
        return banco;
    }
    public final void setBanco(String banco)
    {
        this.banco = banco;
    }
    @Override
    public String toString()
    {
        return "BANCO = " + banco + " | CONVENIO = " + convenio + " | REFENCIA = " + referenciaBancaria;
    }
}
