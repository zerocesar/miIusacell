/**
 * RespuestaFF.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BAdministraFF.iusacell.com.mx;

public class RespuestaFF  implements java.io.Serializable {
    private java.lang.Integer idTx;

    private java.lang.Boolean respuesta;

    private java.lang.Integer codigo;

    private java.lang.String mensaje;

    private java.lang.String altas;

    private java.lang.String bajas;

    public RespuestaFF() {
    }

    public RespuestaFF(
           java.lang.Integer idTx,
           java.lang.Boolean respuesta,
           java.lang.Integer codigo,
           java.lang.String mensaje,
           java.lang.String altas,
           java.lang.String bajas) {
           this.idTx = idTx;
           this.respuesta = respuesta;
           this.codigo = codigo;
           this.mensaje = mensaje;
           this.altas = altas;
           this.bajas = bajas;
    }


    /**
     * Gets the idTx value for this RespuestaFF.
     * 
     * @return idTx
     */
    public java.lang.Integer getIdTx() {
        return idTx;
    }


    /**
     * Sets the idTx value for this RespuestaFF.
     * 
     * @param idTx
     */
    public void setIdTx(java.lang.Integer idTx) {
        this.idTx = idTx;
    }


    /**
     * Gets the respuesta value for this RespuestaFF.
     * 
     * @return respuesta
     */
    public java.lang.Boolean getRespuesta() {
        return respuesta;
    }


    /**
     * Sets the respuesta value for this RespuestaFF.
     * 
     * @param respuesta
     */
    public void setRespuesta(java.lang.Boolean respuesta) {
        this.respuesta = respuesta;
    }


    /**
     * Gets the codigo value for this RespuestaFF.
     * 
     * @return codigo
     */
    public java.lang.Integer getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this RespuestaFF.
     * 
     * @param codigo
     */
    public void setCodigo(java.lang.Integer codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the mensaje value for this RespuestaFF.
     * 
     * @return mensaje
     */
    public java.lang.String getMensaje() {
        return mensaje;
    }


    /**
     * Sets the mensaje value for this RespuestaFF.
     * 
     * @param mensaje
     */
    public void setMensaje(java.lang.String mensaje) {
        this.mensaje = mensaje;
    }


    /**
     * Gets the altas value for this RespuestaFF.
     * 
     * @return altas
     */
    public java.lang.String getAltas() {
        return altas;
    }


    /**
     * Sets the altas value for this RespuestaFF.
     * 
     * @param altas
     */
    public void setAltas(java.lang.String altas) {
        this.altas = altas;
    }


    /**
     * Gets the bajas value for this RespuestaFF.
     * 
     * @return bajas
     */
    public java.lang.String getBajas() {
        return bajas;
    }


    /**
     * Sets the bajas value for this RespuestaFF.
     * 
     * @param bajas
     */
    public void setBajas(java.lang.String bajas) {
        this.bajas = bajas;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaFF)) return false;
        RespuestaFF other = (RespuestaFF) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idTx==null && other.getIdTx()==null) || 
             (this.idTx!=null &&
              this.idTx.equals(other.getIdTx()))) &&
            ((this.respuesta==null && other.getRespuesta()==null) || 
             (this.respuesta!=null &&
              this.respuesta.equals(other.getRespuesta()))) &&
            ((this.codigo==null && other.getCodigo()==null) || 
             (this.codigo!=null &&
              this.codigo.equals(other.getCodigo()))) &&
            ((this.mensaje==null && other.getMensaje()==null) || 
             (this.mensaje!=null &&
              this.mensaje.equals(other.getMensaje()))) &&
            ((this.altas==null && other.getAltas()==null) || 
             (this.altas!=null &&
              this.altas.equals(other.getAltas()))) &&
            ((this.bajas==null && other.getBajas()==null) || 
             (this.bajas!=null &&
              this.bajas.equals(other.getBajas())));
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
        if (getIdTx() != null) {
            _hashCode += getIdTx().hashCode();
        }
        if (getRespuesta() != null) {
            _hashCode += getRespuesta().hashCode();
        }
        if (getCodigo() != null) {
            _hashCode += getCodigo().hashCode();
        }
        if (getMensaje() != null) {
            _hashCode += getMensaje().hashCode();
        }
        if (getAltas() != null) {
            _hashCode += getAltas().hashCode();
        }
        if (getBajas() != null) {
            _hashCode += getBajas().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespuestaFF.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mx.com.iusacell.BAdministraFF", "RespuestaFF"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTx");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTx"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("respuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "respuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
        elemField.setFieldName("altas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bajas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bajas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
