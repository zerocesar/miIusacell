package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;

import mx.com.iusacell.services.miiusacell.vo.ConsumoFechaVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosTotalesVO;
import mx.com.iusacell.services.miiusacell.vo.ConsumosTotalesVOThread;
import mx.com.iusacell.services.miiusacell.vo.SaldosFechaVO;

public class ProcesoRangos implements Runnable{

	private String fechaIni;
	private String fechaFin;
	private String tipo;
	private String dn;
	private ConsumosTotalesVOThread response = null;
	ConsumosTotalesVO respuesta = new ConsumosTotalesVO();
	DetalleConsumosTotales consumos = new DetalleConsumosTotales();
	public String getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public ConsumosTotalesVOThread getResponse() {
		return response;
	}
	public void setResponse(ConsumosTotalesVOThread response) {
		this.response = response;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipo() {
		return tipo;
	}
	
	
	String name; // name of thread
	Thread t;
	
	public ProcesoRangos(String msg, String tipo, String dn, String fechaIni, String fechaFin)
	{
		name = msg;
		this.setTipo(tipo);
		this.dn = dn;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
	    t = new Thread(this, name);
	    Logger.write("   + New thread             : " + t);
	    t.start();
	}
	
	public void run(){
		long timeIni = 0;
		Logger.write("   + Inicia Thred           : " + this.tipo);
		try{
			timeIni = System.currentTimeMillis();
			this.respuesta = new ConsumosTotalesVO();
			this.respuesta = consumos.flujoTotalRango(this.dn, this.tipo, this.fechaIni, this.fechaFin);
		}catch (Exception e) {
			Logger.write("     No se encontraron consumos : " + e.getLocalizedMessage());
		}
		
		this.response = new ConsumosTotalesVOThread();
		
		if(respuesta != null){
			this.response.setTotalDatos(this.respuesta.getTotalDatos());
			this.response.setTotalMensaje(this.respuesta.getTotalMensaje());
			this.response.setTotalTA(this.respuesta.getTotalTA());
			this.response.setTotalVoz(this.respuesta.getTotalVoz());
			this.response.setDescripcionPlan(this.respuesta.getDescripcionPlan());
		}
		
		if(this.respuesta.getDetalleFocalizacion() != null){
			this.response.setDetalleFocalizacion(this.respuesta.getDetalleFocalizacion());
		}
		
		if(this.tipo.equals("1")){
			if(this.respuesta != null && this.respuesta.getDetalleConsumoLlamadas() != null)
				this.response.setDetalleConsumo(this.respuesta.getDetalleConsumoLlamadas());
			else
				this.response.setDetalleConsumo(new ArrayList<ConsumoFechaVO>());

			if(this.respuesta != null && this.respuesta.getDetalleSaldosLlamadas() != null)
				this.response.setDetalleSaldos(this.respuesta.getDetalleSaldosLlamadas());
			else
				this.response.setDetalleSaldos(new ArrayList<SaldosFechaVO>());
		}
		if(this.tipo.equals("2")){
			if(this.respuesta != null && this.respuesta.getDetalleConsumoMensajes() != null)
				this.response.setDetalleConsumo(this.respuesta.getDetalleConsumoMensajes());
			else
				this.response.setDetalleConsumo(new ArrayList<ConsumoFechaVO>());

			if(this.respuesta != null && this.respuesta.getDetalleSaldosMensajes() != null)
				this.response.setDetalleSaldos(this.respuesta.getDetalleSaldosMensajes());
			else
				this.response.setDetalleSaldos(new ArrayList<SaldosFechaVO>());
		}
		if(this.tipo.equals("4")){
			if(this.respuesta != null && this.respuesta.getDetalleConsumoNavegacion() != null)
				this.response.setDetalleConsumo(this.respuesta.getDetalleConsumoNavegacion());
			else
				this.response.setDetalleConsumo(new ArrayList<ConsumoFechaVO>());

			if(this.respuesta != null && this.respuesta.getDetalleSaldosNavegacion() != null)
				this.response.setDetalleSaldos(this.respuesta.getDetalleSaldosNavegacion());
			else
				this.response.setDetalleSaldos(new ArrayList<SaldosFechaVO>());
		}
		Logger.write("     Tiempo ejecucion Thred : " + this.tipo + "  :  " + Util.tipoRespuesta(timeIni));
	}
}
