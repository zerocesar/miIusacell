package mx.com.iusacell.mapas.vo;

public class ReporteActivacionesV2VO {

  //Nuevo campo
  private int    idCae;

  //Para los valoes actuales
  private int    cantidadNuevoActual;
  private double montoNuevoActual;

  private int    cantidadPropioActual;
  private double montoPropioActual;

  private int    cantidadTotalActual;
  private double montoTotalActual;

  //Para el presupuesto  
  private int    cantidadNuevoPlan;
  private double montoNuevoPlan;

  private int    cantidadPropioPlan;
  private double montoPropioPlan;

  private int    cantidadTotalPlan;
  private double montoTotalPlan;

  //Diferencias
  private double diferenciaMontoPropio;
  private int    diferenciaCantidadPropio;
  private double diferenciaPorcentajePropio;

  private double diferenciaMontoNuevo;
  private int    diferenciaCantidadNuevo;
  private double diferenciaPorcentajeNuevo;

  private double diferenciaMontoTotal;
  private int    diferenciaCantidadTotal;
  private double diferenciaPorcentajeTotal;
  
  public void setIdCae(int idCae) {
    this.idCae = idCae;
  }
  public int getIdCae() {
    return idCae;
  }
  public void setCantidadNuevoActual(int cantidadNuevoActual) {
    this.cantidadNuevoActual = cantidadNuevoActual;
  }
  public int getCantidadNuevoActual() {
    return cantidadNuevoActual;
  }
  public void setMontoNuevoActual(double montoNuevoActual) {
    this.montoNuevoActual = montoNuevoActual;
  }
  public double getMontoNuevoActual() {
    return montoNuevoActual;
  }
  public void setCantidadPropioActual(int cantidadPropioActual) {
    this.cantidadPropioActual = cantidadPropioActual;
  }
  public int getCantidadPropioActual() {
    return cantidadPropioActual;
  }
  public void setMontoPropioActual(double montoPropioActual) {
    this.montoPropioActual = montoPropioActual;
  }
  public double getMontoPropioActual() {
    return montoPropioActual;
  }
  public void setCantidadTotalActual(int cantidadTotalActual) {
    this.cantidadTotalActual = cantidadTotalActual;
  }
  public int getCantidadTotalActual() {
    return cantidadTotalActual;
  }
  public void setMontoTotalActual(double montoTotalActual) {
    this.montoTotalActual = montoTotalActual;
  }
  public double getMontoTotalActual() {
    return montoTotalActual;
  }
  public void setCantidadNuevoPlan(int cantidadNuevoPlan) {
    this.cantidadNuevoPlan = cantidadNuevoPlan;
  }
  public int getCantidadNuevoPlan() {
    return cantidadNuevoPlan;
  }
  public void setMontoNuevoPlan(double montoNuevoPlan) {
    this.montoNuevoPlan = montoNuevoPlan;
  }
  public double getMontoNuevoPlan() {
    return montoNuevoPlan;
  }
  public void setCantidadPropioPlan(int cantidadPropioPlan) {
    this.cantidadPropioPlan = cantidadPropioPlan;
  }
  public int getCantidadPropioPlan() {
    return cantidadPropioPlan;
  }
  public void setMontoPropioPlan(double montoPropioPlan) {
    this.montoPropioPlan = montoPropioPlan;
  }
  public double getMontoPropioPlan() {
    return montoPropioPlan;
  }
  public void setCantidadTotalPlan(int cantidadTotalPlan) {
    this.cantidadTotalPlan = cantidadTotalPlan;
  }
  public int getCantidadTotalPlan() {
    return cantidadTotalPlan;
  }
  public void setMontoTotalPlan(double montoTotalPlan) {
    this.montoTotalPlan = montoTotalPlan;
  }
  public double getMontoTotalPlan() {
    return montoTotalPlan;
  }
  public void setDiferenciaCantidadPropio(int diferenciaCantidadPropio) {
    this.diferenciaCantidadPropio = diferenciaCantidadPropio;
  }
  public int getDiferenciaCantidadPropio() {
    return diferenciaCantidadPropio;
  }
  public void setDiferenciaMontoPropio(double diferenciaMontoPropio) {
    this.diferenciaMontoPropio = diferenciaMontoPropio;
  }
  public double getDiferenciaMontoPropio() {
    return diferenciaMontoPropio;
  }
  public void setDiferenciaPorcentajePropio(double diferenciaPorcentajePropio) {
    this.diferenciaPorcentajePropio = diferenciaPorcentajePropio;
  }
  public double getDiferenciaPorcentajePropio() {
    return diferenciaPorcentajePropio;
  }
  public void setDiferenciaMontoNuevo(double diferenciaMontoNuevo) {
    this.diferenciaMontoNuevo = diferenciaMontoNuevo;
  }
  public double getDiferenciaMontoNuevo() {
    return diferenciaMontoNuevo;
  }
  public void setDiferenciaCantidadNuevo(int diferenciaCantidadNuevo) {
    this.diferenciaCantidadNuevo = diferenciaCantidadNuevo;
  }
  public int getDiferenciaCantidadNuevo() {
    return diferenciaCantidadNuevo;
  }
  public void setDiferenciaPorcentajeNuevo(double diferenciaPorcentajeNuevo) {
    this.diferenciaPorcentajeNuevo = diferenciaPorcentajeNuevo;
  }
  public double getDiferenciaPorcentajeNuevo() {
    return diferenciaPorcentajeNuevo;
  }
  public void setDiferenciaMontoTotal(double diferenciaMontoTotal) {
    this.diferenciaMontoTotal = diferenciaMontoTotal;
  }
  public double getDiferenciaMontoTotal() {
    return diferenciaMontoTotal;
  }
  public void setDiferenciaCantidadTotal(int diferenciaCantidadTotal) {
    this.diferenciaCantidadTotal = diferenciaCantidadTotal;
  }
  public int getDiferenciaCantidadTotal() {
    return diferenciaCantidadTotal;
  }
  public void setDiferenciaPorcentajeTotal(double diferenciaPorcentajeTotal) {
    this.diferenciaPorcentajeTotal = diferenciaPorcentajeTotal;
  }
  public double getDiferenciaPorcentajeTotal() {
    return diferenciaPorcentajeTotal;
  }


}