/**
 * Plan.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.iusacell.comun;

public class Plan  implements java.io.Serializable {
    private java.lang.Integer id;

    private java.lang.String descripcion;

    private java.lang.String costoRenta;

    private java.util.Calendar validoDesde;

    private java.lang.Integer matriz;

    private java.lang.String tmcode;

    private java.lang.Integer idPrepago;

    public Plan() {
    }

    public Plan(
           java.lang.Integer id,
           java.lang.String descripcion,
           java.lang.String costoRenta,
           java.util.Calendar validoDesde,
           java.lang.Integer matriz,
           java.lang.String tmcode,
           java.lang.Integer idPrepago) {
           this.id = id;
           this.descripcion = descripcion;
           this.costoRenta = costoRenta;
           this.validoDesde = validoDesde;
           this.matriz = matriz;
           this.tmcode = tmcode;
           this.idPrepago = idPrepago;
    }


    /**
     * Gets the id value for this Plan.
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }


    /**
     * Sets the id value for this Plan.
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * Gets the descripcion value for this Plan.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this Plan.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Gets the costoRenta value for this Plan.
     * 
     * @return costoRenta
     */
    public java.lang.String getCostoRenta() {
        return costoRenta;
    }


    /**
     * Sets the costoRenta value for this Plan.
     * 
     * @param costoRenta
     */
    public void setCostoRenta(java.lang.String costoRenta) {
        this.costoRenta = costoRenta;
    }


    /**
     * Gets the validoDesde value for this Plan.
     * 
     * @return validoDesde
     */
    public java.util.Calendar getValidoDesde() {
        return validoDesde;
    }


    /**
     * Sets the validoDesde value for this Plan.
     * 
     * @param validoDesde
     */
    public void setValidoDesde(java.util.Calendar validoDesde) {
        this.validoDesde = validoDesde;
    }


    /**
     * Gets the matriz value for this Plan.
     * 
     * @return matriz
     */
    public java.lang.Integer getMatriz() {
        return matriz;
    }


    /**
     * Sets the matriz value for this Plan.
     * 
     * @param matriz
     */
    public void setMatriz(java.lang.Integer matriz) {
        this.matriz = matriz;
    }


    /**
     * Gets the tmcode value for this Plan.
     * 
     * @return tmcode
     */
    public java.lang.String getTmcode() {
        return tmcode;
    }


    /**
     * Sets the tmcode value for this Plan.
     * 
     * @param tmcode
     */
    public void setTmcode(java.lang.String tmcode) {
        this.tmcode = tmcode;
    }


    /**
     * Gets the idPrepago value for this Plan.
     * 
     * @return idPrepago
     */
    public java.lang.Integer getIdPrepago() {
        return idPrepago;
    }


    /**
     * Sets the idPrepago value for this Plan.
     * 
     * @param idPrepago
     */
    public void setIdPrepago(java.lang.Integer idPrepago) {
        this.idPrepago = idPrepago;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Plan)) return false;
        Plan other = (Plan) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion()))) &&
            ((this.costoRenta==null && other.getCostoRenta()==null) || 
             (this.costoRenta!=null &&
              this.costoRenta.equals(other.getCostoRenta()))) &&
            ((this.validoDesde==null && other.getValidoDesde()==null) || 
             (this.validoDesde!=null &&
              this.validoDesde.equals(other.getValidoDesde()))) &&
            ((this.matriz==null && other.getMatriz()==null) || 
             (this.matriz!=null &&
              this.matriz.equals(other.getMatriz()))) &&
            ((this.tmcode==null && other.getTmcode()==null) || 
             (this.tmcode!=null &&
              this.tmcode.equals(other.getTmcode()))) &&
            ((this.idPrepago==null && other.getIdPrepago()==null) || 
             (this.idPrepago!=null &&
              this.idPrepago.equals(other.getIdPrepago())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        if (getCostoRenta() != null) {
            _hashCode += getCostoRenta().hashCode();
        }
        if (getValidoDesde() != null) {
            _hashCode += getValidoDesde().hashCode();
        }
        if (getMatriz() != null) {
            _hashCode += getMatriz().hashCode();
        }
        if (getTmcode() != null) {
            _hashCode += getTmcode().hashCode();
        }
        if (getIdPrepago() != null) {
            _hashCode += getIdPrepago().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Plan.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://comun.iusacell.com.mx", "Plan"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costoRenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costoRenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validoDesde");
        elemField.setXmlName(new javax.xml.namespace.QName("", "validoDesde"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matriz");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matriz"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tmcode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tmcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPrepago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idPrepago"));
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
