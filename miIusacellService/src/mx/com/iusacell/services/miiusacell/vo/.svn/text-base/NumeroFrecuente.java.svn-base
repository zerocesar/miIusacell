package mx.com.iusacell.services.miiusacell.vo;

public class NumeroFrecuente {
	
	private String dn;
	private String nombre;
	private String telefono;
	private String mensaje="";
	private int codigo;
	  
	
	
	  public String getDn() {
		return dn;
	}

	public void setDn(final String dn) {
		this.dn = dn;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(final int codigo) {
		this.codigo = codigo;
	}

	public void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
	}

	public NumeroFrecuente(final String mensaje) {
		  this.mensaje = mensaje;
	  } 
	  
	  public NumeroFrecuente() {
	  }
	  
	  public static NumeroFrecuente mensajeErrorBD() {
		  NumeroFrecuente t=new NumeroFrecuente("Ocurrio un error al tratar de realizar la consulta");
		    return t;
		  }
	  
	  public static NumeroFrecuente mensajeUsuarioSinNumerosFrecuentes() {
		  NumeroFrecuente t=new NumeroFrecuente("El usuario no cuenta con numeros frecuentes asociados");
		    return t;
		  }
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	public String getMensaje() {
		return mensaje;
	}
	
	
	

}
