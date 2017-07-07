/**
 * RegistroGrupoFaF.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.iusacell.middleware.servicios.gestion;

public class RegistroGrupoFaF  implements java.io.Serializable {
    private java.lang.String indice;

    private java.lang.String dn;

    public RegistroGrupoFaF() {
    }

    public RegistroGrupoFaF(
           java.lang.String indice,
           java.lang.String dn) {
           this.indice = indice;
           this.dn = dn;
    }


    /**
     * Gets the indice value for this RegistroGrupoFaF.
     * 
     * @return indice
     */
    public java.lang.String getIndice() {
        return indice;
    }


    /**
     * Sets the indice value for this RegistroGrupoFaF.
     * 
     * @param indice
     */
    public void setIndice(java.lang.String indice) {
        this.indice = indice;
    }


    /**
     * Gets the dn value for this RegistroGrupoFaF.
     * 
     * @return dn
     */
    public java.lang.String getDn() {
        return dn;
    }


    /**
     * Sets the dn value for this RegistroGrupoFaF.
     * 
     * @param dn
     */
    public void setDn(java.lang.String dn) {
        this.dn = dn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistroGrupoFaF)) return false;
        RegistroGrupoFaF other = (RegistroGrupoFaF) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.indice==null && other.getIndice()==null) || 
             (this.indice!=null &&
              this.indice.equals(other.getIndice()))) &&
            ((this.dn==null && other.getDn()==null) || 
             (this.dn!=null &&
              this.dn.equals(other.getDn())));
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
        if (getIndice() != null) {
            _hashCode += getIndice().hashCode();
        }
        if (getDn() != null) {
            _hashCode += getDn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistroGrupoFaF.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gestion.servicios.middleware.iusacell.com.mx", "RegistroGrupoFaF"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dn"));
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
