/**
 * Cobro.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.iusacell.middleware.servicios.gestion;

public class Cobro  implements java.io.Serializable {
    private java.lang.Boolean aplicoCobro;

    private java.lang.String folioCobro;

    public Cobro() {
    }

    public Cobro(
           java.lang.Boolean aplicoCobro,
           java.lang.String folioCobro) {
           this.aplicoCobro = aplicoCobro;
           this.folioCobro = folioCobro;
    }


    /**
     * Gets the aplicoCobro value for this Cobro.
     * 
     * @return aplicoCobro
     */
    public java.lang.Boolean getAplicoCobro() {
        return aplicoCobro;
    }


    /**
     * Sets the aplicoCobro value for this Cobro.
     * 
     * @param aplicoCobro
     */
    public void setAplicoCobro(java.lang.Boolean aplicoCobro) {
        this.aplicoCobro = aplicoCobro;
    }


    /**
     * Gets the folioCobro value for this Cobro.
     * 
     * @return folioCobro
     */
    public java.lang.String getFolioCobro() {
        return folioCobro;
    }


    /**
     * Sets the folioCobro value for this Cobro.
     * 
     * @param folioCobro
     */
    public void setFolioCobro(java.lang.String folioCobro) {
        this.folioCobro = folioCobro;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Cobro)) return false;
        Cobro other = (Cobro) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.aplicoCobro==null && other.getAplicoCobro()==null) || 
             (this.aplicoCobro!=null &&
              this.aplicoCobro.equals(other.getAplicoCobro()))) &&
            ((this.folioCobro==null && other.getFolioCobro()==null) || 
             (this.folioCobro!=null &&
              this.folioCobro.equals(other.getFolioCobro())));
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
        if (getAplicoCobro() != null) {
            _hashCode += getAplicoCobro().hashCode();
        }
        if (getFolioCobro() != null) {
            _hashCode += getFolioCobro().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Cobro.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gestion.servicios.middleware.iusacell.com.mx", "Cobro"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aplicoCobro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "aplicoCobro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folioCobro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "folioCobro"));
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
