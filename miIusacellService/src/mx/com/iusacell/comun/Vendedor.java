/**
 * Vendedor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.iusacell.comun;

public class Vendedor  extends mx.com.iusacell.comun.Persona  implements java.io.Serializable {
    private java.lang.String id;

    private java.lang.String distribuidor;

    private java.lang.String canal;

    private java.lang.String cdgCae;

    private java.lang.String puntoVenta;

    public Vendedor() {
    }

    public Vendedor(
           java.lang.String nombre,
           java.lang.String segundoNombre,
           java.lang.String apellidoPaterno,
           java.lang.String apellidoMaterno,
           java.lang.Integer sexo,
           java.lang.Integer edoCivil,
           java.lang.Integer ocupacion,
           java.util.Date nacimiento,
           java.lang.String nacionalidad,
           java.lang.String RFC,
           java.lang.String CURP,
           java.lang.Integer tipoIdentificacion,
           java.lang.String folioIdentificacion,
           java.util.Date vigenciaIdentificacion,
           java.lang.String id,
           java.lang.String distribuidor,
           java.lang.String canal,
           java.lang.String cdgCae,
           java.lang.String puntoVenta) {
        super(
            nombre,
            segundoNombre,
            apellidoPaterno,
            apellidoMaterno,
            sexo,
            edoCivil,
            ocupacion,
            nacimiento,
            nacionalidad,
            RFC,
            CURP,
            tipoIdentificacion,
            folioIdentificacion,
            vigenciaIdentificacion);
        this.id = id;
        this.distribuidor = distribuidor;
        this.canal = canal;
        this.cdgCae = cdgCae;
        this.puntoVenta = puntoVenta;
    }


    /**
     * Gets the id value for this Vendedor.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this Vendedor.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the distribuidor value for this Vendedor.
     * 
     * @return distribuidor
     */
    public java.lang.String getDistribuidor() {
        return distribuidor;
    }


    /**
     * Sets the distribuidor value for this Vendedor.
     * 
     * @param distribuidor
     */
    public void setDistribuidor(java.lang.String distribuidor) {
        this.distribuidor = distribuidor;
    }


    /**
     * Gets the canal value for this Vendedor.
     * 
     * @return canal
     */
    public java.lang.String getCanal() {
        return canal;
    }


    /**
     * Sets the canal value for this Vendedor.
     * 
     * @param canal
     */
    public void setCanal(java.lang.String canal) {
        this.canal = canal;
    }


    /**
     * Gets the cdgCae value for this Vendedor.
     * 
     * @return cdgCae
     */
    public java.lang.String getCdgCae() {
        return cdgCae;
    }


    /**
     * Sets the cdgCae value for this Vendedor.
     * 
     * @param cdgCae
     */
    public void setCdgCae(java.lang.String cdgCae) {
        this.cdgCae = cdgCae;
    }


    /**
     * Gets the puntoVenta value for this Vendedor.
     * 
     * @return puntoVenta
     */
    public java.lang.String getPuntoVenta() {
        return puntoVenta;
    }


    /**
     * Sets the puntoVenta value for this Vendedor.
     * 
     * @param puntoVenta
     */
    public void setPuntoVenta(java.lang.String puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Vendedor)) return false;
        Vendedor other = (Vendedor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.distribuidor==null && other.getDistribuidor()==null) || 
             (this.distribuidor!=null &&
              this.distribuidor.equals(other.getDistribuidor()))) &&
            ((this.canal==null && other.getCanal()==null) || 
             (this.canal!=null &&
              this.canal.equals(other.getCanal()))) &&
            ((this.cdgCae==null && other.getCdgCae()==null) || 
             (this.cdgCae!=null &&
              this.cdgCae.equals(other.getCdgCae()))) &&
            ((this.puntoVenta==null && other.getPuntoVenta()==null) || 
             (this.puntoVenta!=null &&
              this.puntoVenta.equals(other.getPuntoVenta())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getDistribuidor() != null) {
            _hashCode += getDistribuidor().hashCode();
        }
        if (getCanal() != null) {
            _hashCode += getCanal().hashCode();
        }
        if (getCdgCae() != null) {
            _hashCode += getCdgCae().hashCode();
        }
        if (getPuntoVenta() != null) {
            _hashCode += getPuntoVenta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Vendedor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://comun.iusacell.com.mx", "Vendedor"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distribuidor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "distribuidor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "canal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdgCae");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cdgCae"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puntoVenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "puntoVenta"));
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
