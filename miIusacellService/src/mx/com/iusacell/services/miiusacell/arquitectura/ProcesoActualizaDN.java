package mx.com.iusacell.services.miiusacell.arquitectura;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miusacell.call.CallServiceDetalleConsumosETAK_LEGACY;

public class ProcesoActualizaDN implements Runnable{
	private String fechaCorte;
	private String tipo;
	private String dn;
	String sResponse = new String();
	CallServiceDetalleConsumosETAK_LEGACY detalle = new CallServiceDetalleConsumosETAK_LEGACY();
	OracleProcedures oracle = new OracleProcedures();
	public String getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(String fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipo() {
		return tipo;
	}
	
	String name; // name of thread
	public Thread t;
	
	public ProcesoActualizaDN(String msg, String tipo, String dn, String fechaCorte)
	{
		name = msg;
		this.tipo = tipo;
		this.dn = dn;
		this.fechaCorte = fechaCorte;
	    t = new Thread(this, name);
	    Logger.write("   + New thread             : " + t);
	    t.start();
	}
	
	public void run(){
		Logger.write("   + Inicia Thred           : " + this.tipo);
		try{
			sResponse = detalle.detalleConsumosETAK_LEGACY(this.dn, this.tipo, this.fechaCorte);
			try{
				oracle.shedulerGeneraXML(this.dn, Integer.parseInt(this.tipo), sResponse.toString(), 2021);
			}catch (Exception e) {
				Logger.write("     No se guardar en BD    : " + e.getLocalizedMessage());
			}
		}catch (Exception e) {
			Logger.write("     No se encontraron consumos : " + e.getLocalizedMessage());
		}
	}
}
