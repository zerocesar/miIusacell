/**
 * IGestionServicios.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.iusacell.middleware.servicios.gestion;

public interface IGestionServicios extends java.rmi.Remote {
    public mx.com.iusacell.middleware.servicios.gestion.RespuestaServicios operacionServicio(mx.com.iusacell.comun.Linea linea, mx.com.iusacell.comun.Usuario usuario, mx.com.iusacell.middleware.servicios.gestion.Cobro cobro, int operacion) throws java.rmi.RemoteException, mx.com.iusacell.comun.Falta;
}
