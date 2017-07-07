/**
 * Esn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.iusacell.comun;

public class Esn  implements java.io.Serializable {
    private java.lang.String esnDec;

    private java.lang.String esnHex;

    private java.lang.String status;

    public Esn() {
    }

    public Esn(
           java.lang.String esnDec,
           java.lang.String esnHex,
           java.lang.String status) {
           this.esnDec = esnDec;
           this.esnHex = esnHex;
           this.status = status;
    }


    /**
     * Gets the esnDec value for this Esn.
     * 
     * @return esnDec
     */
    public java.lang.String getEsnDec() {
        return esnDec;
    }


    /**
     * Sets the esnDec value for this Esn.
     * 
     * @param esnDec
     */
    public void setEsnDec(java.lang.String esnDec) {
        this.esnDec = esnDec;
    }


    /**
     * Gets the esnHex value for this Esn.
     * 
     * @return esnHex
     */
    public java.lang.String getEsnHex() {
        return esnHex;
    }


    /**
     * Sets the esnHex value for this Esn.
     * 
     * @param esnHex
     */
    public void setEsnHex(java.lang.String esnHex) {
        this.esnHex = esnHex;
    }


    /**
     * Gets the status value for this Esn.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Esn.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Esn)) return false;
        Esn other = (Esn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.esnDec==null && other.getEsnDec()==null) || 
             (this.esnDec!=null &&
              this.esnDec.equals(other.getEsnDec()))) &&
            ((this.esnHex==null && other.getEsnHex()==null) || 
             (this.esnHex!=null &&
              this.esnHex.equals(other.getEsnHex()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getEsnDec() != null) {
            _hashCode += getEsnDec().hashCode();
        }
        if (getEsnHex() != null) {
            _hashCode += getEsnHex().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Esn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://comun.iusacell.com.mx", "Esn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esnDec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esnDec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esnHex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esnHex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
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
