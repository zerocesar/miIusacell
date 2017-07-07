/**
 * Usuario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.iusacell.comun;

public class Usuario  extends mx.com.iusacell.comun.Persona  implements java.io.Serializable {
    private java.lang.String id;

    private java.lang.Integer modulo;

    private java.lang.String contrasena;

    private java.lang.String descripcion;

    public Usuario() {
    }

    public Usuario(
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
           java.lang.Integer modulo,
           java.lang.String contrasena,
           java.lang.String descripcion) {
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
        this.modulo = modulo;
        this.contrasena = contrasena;
        this.descripcion = descripcion;
    }


    /**
     * Gets the id value for this Usuario.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this Usuario.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the modulo value for this Usuario.
     * 
     * @return modulo
     */
    public java.lang.Integer getModulo() {
        return modulo;
    }


    /**
     * Sets the modulo value for this Usuario.
     * 
     * @param modulo
     */
    public void setModulo(java.lang.Integer modulo) {
        this.modulo = modulo;
    }


    /**
     * Gets the contrasena value for this Usuario.
     * 
     * @return contrasena
     */
    public java.lang.String getContrasena() {
        return contrasena;
    }


    /**
     * Sets the contrasena value for this Usuario.
     * 
     * @param contrasena
     */
    public void setContrasena(java.lang.String contrasena) {
        this.contrasena = contrasena;
    }


    /**
     * Gets the descripcion value for this Usuario.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this Usuario.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Usuario)) return false;
        Usuario other = (Usuario) obj;
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
            ((this.modulo==null && other.getModulo()==null) || 
             (this.modulo!=null &&
              this.modulo.equals(other.getModulo()))) &&
            ((this.contrasena==null && other.getContrasena()==null) || 
             (this.contrasena!=null &&
              this.contrasena.equals(other.getContrasena()))) &&
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion())));
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
        if (getModulo() != null) {
            _hashCode += getModulo().hashCode();
        }
        if (getContrasena() != null) {
            _hashCode += getContrasena().hashCode();
        }
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Usuario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://comun.iusacell.com.mx", "Usuario"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modulo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modulo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contrasena");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contrasena"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
