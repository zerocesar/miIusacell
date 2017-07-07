package mx.com.iusacell.services.miiusacell.vo.messageMail;

import java.io.Serializable;

public class ArchivoAdjuntoBean implements Serializable
{
    private static final long serialVersionUID = 201703230401227L;

    private String archivoBase64;
    private String extension;
    private String mimeType;
    private String nombre;
    
    public String getArchivoBase64()
    {
        return archivoBase64;
    }
    public void setArchivoBase64(String archivoBase64)
    {
        this.archivoBase64 = archivoBase64;
    }
    public String getExtension()
    {
        return extension;
    }
    public void setExtension(String extension)
    {
        this.extension = extension;
    }
    public String getMimeType()
    {
        return mimeType;
    }
    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }
    public String getNombre()
    {
        return nombre;
    }
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
}
