/**
 * GestionServiciosWS_IGestionServiciosHttpServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.iusacell.middleware.servicios.gestion.Binding3;

public class GestionServiciosWS_IGestionServiciosHttpServiceLocator extends org.apache.axis.client.Service implements mx.com.iusacell.middleware.servicios.gestion.Binding3.GestionServiciosWS_IGestionServiciosHttpService {

    public GestionServiciosWS_IGestionServiciosHttpServiceLocator() {
    }


    public GestionServiciosWS_IGestionServiciosHttpServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GestionServiciosWS_IGestionServiciosHttpServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GestionServiciosWS_IGestionServiciosHttpPort
    private java.lang.String GestionServiciosWS_IGestionServiciosHttpPort_address = "http://192.168.190.96:81/SServiciosWeb/sca/GestionServiciosWS";

    public java.lang.String getGestionServiciosWS_IGestionServiciosHttpPortAddress() {
        return GestionServiciosWS_IGestionServiciosHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GestionServiciosWS_IGestionServiciosHttpPortWSDDServiceName = "GestionServiciosWS_IGestionServiciosHttpPort";

    public java.lang.String getGestionServiciosWS_IGestionServiciosHttpPortWSDDServiceName() {
        return GestionServiciosWS_IGestionServiciosHttpPortWSDDServiceName;
    }

    public void setGestionServiciosWS_IGestionServiciosHttpPortWSDDServiceName(java.lang.String name) {
        GestionServiciosWS_IGestionServiciosHttpPortWSDDServiceName = name;
    }

    public mx.com.iusacell.middleware.servicios.gestion.IGestionServicios getGestionServiciosWS_IGestionServiciosHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GestionServiciosWS_IGestionServiciosHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGestionServiciosWS_IGestionServiciosHttpPort(endpoint);
    }

    public mx.com.iusacell.middleware.servicios.gestion.IGestionServicios getGestionServiciosWS_IGestionServiciosHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            mx.com.iusacell.middleware.servicios.gestion.Binding3.GestionServiciosWS_IGestionServiciosHttpBindingStub _stub = new mx.com.iusacell.middleware.servicios.gestion.Binding3.GestionServiciosWS_IGestionServiciosHttpBindingStub(portAddress, this);
            _stub.setPortName(getGestionServiciosWS_IGestionServiciosHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGestionServiciosWS_IGestionServiciosHttpPortEndpointAddress(java.lang.String address) {
        GestionServiciosWS_IGestionServiciosHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (mx.com.iusacell.middleware.servicios.gestion.IGestionServicios.class.isAssignableFrom(serviceEndpointInterface)) {
                mx.com.iusacell.middleware.servicios.gestion.Binding3.GestionServiciosWS_IGestionServiciosHttpBindingStub _stub = new mx.com.iusacell.middleware.servicios.gestion.Binding3.GestionServiciosWS_IGestionServiciosHttpBindingStub(new java.net.URL(GestionServiciosWS_IGestionServiciosHttpPort_address), this);
                _stub.setPortName(getGestionServiciosWS_IGestionServiciosHttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("GestionServiciosWS_IGestionServiciosHttpPort".equals(inputPortName)) {
            return getGestionServiciosWS_IGestionServiciosHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://gestion.servicios.middleware.iusacell.com.mx/Binding3", "GestionServiciosWS_IGestionServiciosHttpService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://gestion.servicios.middleware.iusacell.com.mx/Binding3", "GestionServiciosWS_IGestionServiciosHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GestionServiciosWS_IGestionServiciosHttpPort".equals(portName)) {
            setGestionServiciosWS_IGestionServiciosHttpPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
