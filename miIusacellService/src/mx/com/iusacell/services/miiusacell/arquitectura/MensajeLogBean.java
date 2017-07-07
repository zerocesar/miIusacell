package mx.com.iusacell.services.miiusacell.arquitectura;


public class MensajeLogBean
{
	private StringBuilder mensaje;
	private String        empleado;
	private String        tiempoEjecucion;
	private long          tiempoInicio;
	
	public MensajeLogBean()
	{
		empleado     = "BUYBACK";
		tiempoInicio = System.currentTimeMillis();
		mensaje      = new StringBuilder();
	}
	
	public MensajeLogBean(final String numerEmpleado)
	{
		empleado     = numerEmpleado;
		tiempoInicio = System.currentTimeMillis();
		mensaje      = new StringBuilder();
	}
	
	public void agrega(final String cadena)
	{
		mensaje.append(cadena);
	}
	
	public String pintaMensaje(final String action_metodo)
	{
		tiempoEjecucion = Util.tipoRespuesta(tiempoInicio);
		mensaje.append("\n ");
		mensaje.insert(0, "==== [" + empleado + "] :: "+ Util.tipoRespuesta(tiempoInicio) +" :: " + action_metodo);
		return mensaje.toString();
	}

	/*---------------------------------------------------------------
 	 *              G e t t e r s   &   s e t t e r s
 	-------------------------------------------------------------- */
	
	public StringBuilder getMensaje() {
		return mensaje;
	}

	public void setMensaje(StringBuilder mensaje) {
		this.mensaje = mensaje;
	}

	public long getTiempoInicio() {
		return tiempoInicio;
	}

	public void setTiempoInicio(long tiempoInicio) {
		this.tiempoInicio = tiempoInicio;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public String getTiempoEjecucion() {
		return tiempoEjecucion;
	}

	public void setTiempoEjecucion(String tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}
}
