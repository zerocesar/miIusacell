/**
 * RespuestaServicios.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.iusacell.middleware.servicios.gestion;

public class RespuestaServicios  implements java.io.Serializable {
    private java.lang.Boolean respuesta;

    private java.lang.Integer folioPreactivacion;

    private java.lang.Integer codigo;

    private java.lang.String mensaje;

    private java.lang.Integer idTx;

    public RespuestaServicios() {
    }

    public RespuestaServicios(
           java.lang.Boolean respuesta,
           java.lang.Integer folioPreactivacion,
           java.lang.Integer codigo,
           java.lang.String mensaje,
           java.lang.Integer idTx) {
           this.respuesta = respuesta;
           this.folioPreactivacion = folioPreactivacion;
           this.codigo = codigo;
           this.mensaje = mensaje;
           this.idTx = idTx;
    }


    /**
     * Gets the respuesta value for this RespuestaServicios.
     * 
     * @return respuesta
     */
    public java.lang.Boolean getRespuesta() {
        return respuesta;
    }


    /**
     * Sets the respuesta value for this RespuestaServicios.
     * 
     * @param respuesta
     */
    public void setRespuesta(java.lang.Boolean respuesta) {
        this.respuesta = respuesta;
    }


    /**
     * Gets the folioPreactivacion value for this RespuestaServicios.
     * 
     * @return folioPreactivacion
     */
    public java.lang.Integer getFolioPreactivacion() {
        return folioPreactivacion;
    }


    /**
     * Sets the folioPreactivacion value for this RespuestaServicios.
     * 
     * @param folioPreactivacion
     */
    public void setFolioPreactivacion(java.lang.Integer folioPreactivacion) {
        this.folioPreactivacion = folioPreactivacion;
    }


    /**
     * Gets the codigo value for this RespuestaServicios.
     * 
     * @return codigo
     */
    public java.lang.Integer getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this RespuestaServicios.
     * 
     * @param codigo
     */
    public void setCodigo(java.lang.Integer codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the mensaje value for this RespuestaServicios.
     * 
     * @return mensaje
     */
    public java.lang.String getMensaje() {
        return mensaje;
    }


    /**
     * Sets the mensaje value for this RespuestaServicios.
     * 
     * @param mensaje
     */
    public void setMensaje(java.lang.String mensaje) {
        this.mensaje = mensaje;
    }


    /**
     * Gets the idTx value for this RespuestaServicios.
     * 
     * @return idTx
     */
    public java.lang.Integer getIdTx() {
        return idTx;
    }


    /**
     * Sets the idTx value for this RespuestaServicios.
     * 
     * @param idTx
     */
    public void setIdTx(java.lang.Integer idTx) {
        this.idTx = idTx;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaServicios)) return false;
        RespuestaServicios other = (RespuestaServicios) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.respuesta==null && other.getRespuesta()==null) || 
             (this.respuesta!=null &&
              this.respuesta.equals(other.getRespuesta()))) &&
            ((this.folioPreactivacion==null && other.getFolioPreactivacion()==null) || 
             (this.folioPreactivacion!=null &&
              this.folioPreactivacion.equals(other.getFolioPreactivacion()))) &&
            ((this.codigo==null && other.getCodigo()==null) || 
             (this.codigo!=null &&
              this.codigo.equals(other.getCodigo()))) &&
            ((this.mensaje==null && other.getMensaje()==null) || 
             (this.mensaje!=null &&
              this.mensaje.equals(other.getMensaje()))) &&
            ((this.idTx==null && other.getIdTx()==null) || 
             (this.idTx!=null &&
              this.idTx.equals(other.getIdTx())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getRespuesta() != null) {
            _hashCode += getRespuesta().hashCode();
        }
        if (getFolioPreactivacion() != null) {
            _hashCode += getFolioPreactivacion().hashCode();
        }
        if (getCodigo() != null) {
            _hashCode += getCodigo().hashCode();
        }
        if (getMensaje() != null) {
            _hashCode += getMensaje().hashCode();
        }
        if (getIdTx() != null) {
            _hashCode += getIdTx().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespuestaServicios.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gestion.servicios.middleware.iusacell.com.mx", "RespuestaServicios"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("respuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "respuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folioPreactivacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "folioPreactivacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensaje");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mensaje"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTx");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTx"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
