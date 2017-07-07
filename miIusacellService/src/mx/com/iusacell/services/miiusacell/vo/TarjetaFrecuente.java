package mx.com.iusacell.services.miiusacell.vo;



public class TarjetaFrecuente 
{ 
//  private int TarjetaID;
  private String fechaExpiracion;
  private String propietarioTarjeta;
  private int tipoTarjetaID;
  private String numeroTarjeta;
  private String ultimosDigitos;
  private String mesVencimiento;
  private String anioVencimiento;
  private String nombre;
  private String apaterno;
  private String amaterno;
  private String mensaje="";
  private int codigo;
  private String emisor;
  
  public String getEmisor() {
	return emisor;
}

public void setEmisor(final String emisor) {
	this.emisor = emisor;
}

public String getPropietarioTarjeta() {
	return propietarioTarjeta;
}

public void setPropietarioTarjeta(final String propietarioTarjeta) {
	this.propietarioTarjeta = propietarioTarjeta;
}

public String getFechaExpiracion() {
	return fechaExpiracion;
}

public void setFechaExpiracion(final String fechaExpiracion) {
	this.fechaExpiracion = fechaExpiracion;
}

public int getCodigo() {
	return codigo;
}

public void setCodigo(final int codigo) {
	this.codigo = codigo;
}

public static TarjetaFrecuente mensajeErrorBD() {
    TarjetaFrecuente t=new TarjetaFrecuente("Ocurrio un error al tratar de realizar las consultas");
    return t;
  }
  
  public String getUltimosDigitos() {
	return ultimosDigitos;
}

public void setUltimosDigitos(final String ultimosDigitos) {
	this.ultimosDigitos = ultimosDigitos;
}

public static TarjetaFrecuente mensajeUsuarioSinTarjetas() {
    TarjetaFrecuente t=new TarjetaFrecuente("El usuario no cuenta con tarjetas asociadas");
    return t;
  }
  
  public TarjetaFrecuente(final String mensaje) {
    this.mensaje=mensaje;
  }
  
  public TarjetaFrecuente() {
  }
  
  public String getMensaje() {
    return mensaje;
  }



  
  public void setMensaje(final String mensaje) {
    this.mensaje = mensaje;
  }



  public int getTipoTarjetaID() {
    return tipoTarjetaID;
  }

  
  
  public String getNombre() {
    return nombre;
  }


  
  public void setNombre(final String nombre) {
    this.nombre = nombre;
  }


  
  public String getApaterno() {
    return apaterno;
  }


  
  public void setApaterno(final String apaterno) {
    this.apaterno = apaterno;
  }


  
  public String getAmaterno() {
    return amaterno;
  }


  
  public void setAmaterno(final String amaterno) {
    this.amaterno = amaterno;
  }


  public void setTipoTarjetaID(final int tipoTarjetaID) {
    this.tipoTarjetaID = tipoTarjetaID;
  }

  public String getNumeroTarjeta() {
    return numeroTarjeta;
  }
  
  public void setNumeroTarjeta(final String numeroTarjeta) {
    this.numeroTarjeta = numeroTarjeta;
  }
  
  public String getMesVencimiento() {
    return mesVencimiento;
  }
  
  public void setMesVencimiento(final String mesVencimiento) {
    this.mesVencimiento = mesVencimiento;
  }
  
  public String getAnioVencimiento() {
    return anioVencimiento;
  }
  
  public void setAnioVencimiento(final String anioVencimiento) {
    this.anioVencimiento = anioVencimiento;
  }
  
//  public int getRegAct() {
//    return regAct;
//  }
//  
//  public void setRegAct(int regAct) {
//    this.regAct = regAct;
//  }
//  
//  public Date getFechaAlta() {
//    return fechaAlta;
//  }
//  
//  public void setFechaAlta(Date fechaAlta) {
//    this.fechaAlta = fechaAlta;
//  }
//  
//  public Date getFechaCambio() {
//    return fechaCambio;
//  }
//  
//  public void setFechaCambio(Date fechaCambio) {
//    this.fechaCambio = fechaCambio;
//  }
//  
//  public int getUsuarioId() {
//    return usuarioId;
//  }
//  
//  public void setUsuarioId(int usuarioId) {
//    this.usuarioId = usuarioId;
//  }
//  
//  public String getIpEquipo() {
//    return ipEquipo;
//  }
//  
//  public void setIpEquipo(String ipEquipo) {
//    this.ipEquipo = ipEquipo;
//  }
//  
//  public String getUsuarioBD() {
//    return usuarioBD;
//  }
//  
//  public void setUsuarioBD(String usuarioBD) {
//    this.usuarioBD = usuarioBD;
//  }
//  
//  public Date getFechaModBD() {
//    return fechaModBD;
//  }
//  
//  public void setFechaModBD(Date fechaModBD) {
//    this.fechaModBD = fechaModBD;
//  } 
//  
  
  
  
      
  
  
}
