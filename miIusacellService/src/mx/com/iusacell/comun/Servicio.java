/**
 * Servicio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.iusacell.comun;

public class Servicio  implements java.io.Serializable {
    private java.lang.Boolean basico;

    private mx.com.iusacell.comun.ServicioStatus status;

    private java.lang.Boolean red;

    private java.lang.String descripcion;

    private mx.com.iusacell.comun.CuentaContable cuentaContable;

    private java.lang.String id;

    private java.lang.String costo;

    private java.util.Calendar activacion;

    private java.util.Calendar desactivacion;

    private java.lang.Integer origen;

    private mx.com.iusacell.comun.Vigencia[] vigencias;

    private java.lang.Integer tipo;

    private java.lang.Integer idHistorico;

    private java.lang.String valor;

    public Servicio() {
    }

    public Servicio(
           java.lang.Boolean basico,
           mx.com.iusacell.comun.ServicioStatus status,
           java.lang.Boolean red,
           java.lang.String descripcion,
           mx.com.iusacell.comun.CuentaContable cuentaContable,
           java.lang.String id,
           java.lang.String costo,
           java.util.Calendar activacion,
           java.util.Calendar desactivacion,
           java.lang.Integer origen,
           mx.com.iusacell.comun.Vigencia[] vigencias,
           java.lang.Integer tipo,
           java.lang.Integer idHistorico,
           java.lang.String valor) {
           this.basico = basico;
           this.status = status;
           this.red = red;
           this.descripcion = descripcion;
           this.cuentaContable = cuentaContable;
           this.id = id;
           this.costo = costo;
           this.activacion = activacion;
           this.desactivacion = desactivacion;
           this.origen = origen;
           this.vigencias = vigencias;
           this.tipo = tipo;
           this.idHistorico = idHistorico;
           this.valor = valor;
    }


    /**
     * Gets the basico value for this Servicio.
     * 
     * @return basico
     */
    public java.lang.Boolean getBasico() {
        return basico;
    }


    /**
     * Sets the basico value for this Servicio.
     * 
     * @param basico
     */
    public void setBasico(java.lang.Boolean basico) {
        this.basico = basico;
    }


    /**
     * Gets the status value for this Servicio.
     * 
     * @return status
     */
    public mx.com.iusacell.comun.ServicioStatus getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Servicio.
     * 
     * @param status
     */
    public void setStatus(mx.com.iusacell.comun.ServicioStatus status) {
        this.status = status;
    }


    /**
     * Gets the red value for this Servicio.
     * 
     * @return red
     */
    public java.lang.Boolean getRed() {
        return red;
    }


    /**
     * Sets the red value for this Servicio.
     * 
     * @param red
     */
    public void setRed(java.lang.Boolean red) {
        this.red = red;
    }


    /**
     * Gets the descripcion value for this Servicio.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this Servicio.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Gets the cuentaContable value for this Servicio.
     * 
     * @return cuentaContable
     */
    public mx.com.iusacell.comun.CuentaContable getCuentaContable() {
        return cuentaContable;
    }


    /**
     * Sets the cuentaContable value for this Servicio.
     * 
     * @param cuentaContable
     */
    public void setCuentaContable(mx.com.iusacell.comun.CuentaContable cuentaContable) {
        this.cuentaContable = cuentaContable;
    }


    /**
     * Gets the id value for this Servicio.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this Servicio.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the costo value for this Servicio.
     * 
     * @return costo
     */
    public java.lang.String getCosto() {
        return costo;
    }


    /**
     * Sets the costo value for this Servicio.
     * 
     * @param costo
     */
    public void setCosto(java.lang.String costo) {
        this.costo = costo;
    }


    /**
     * Gets the activacion value for this Servicio.
     * 
     * @return activacion
     */
    public java.util.Calendar getActivacion() {
        return activacion;
    }


    /**
     * Sets the activacion value for this Servicio.
     * 
     * @param activacion
     */
    public void setActivacion(java.util.Calendar activacion) {
        this.activacion = activacion;
    }


    /**
     * Gets the desactivacion value for this Servicio.
     * 
     * @return desactivacion
     */
    public java.util.Calendar getDesactivacion() {
        return desactivacion;
    }


    /**
     * Sets the desactivacion value for this Servicio.
     * 
     * @param desactivacion
     */
    public void setDesactivacion(java.util.Calendar desactivacion) {
        this.desactivacion = desactivacion;
    }


    /**
     * Gets the origen value for this Servicio.
     * 
     * @return origen
     */
    public java.lang.Integer getOrigen() {
        return origen;
    }


    /**
     * Sets the origen value for this Servicio.
     * 
     * @param origen
     */
    public void setOrigen(java.lang.Integer origen) {
        this.origen = origen;
    }


    /**
     * Gets the vigencias value for this Servicio.
     * 
     * @return vigencias
     */
    public mx.com.iusacell.comun.Vigencia[] getVigencias() {
        return vigencias;
    }


    /**
     * Sets the vigencias value for this Servicio.
     * 
     * @param vigencias
     */
    public void setVigencias(mx.com.iusacell.comun.Vigencia[] vigencias) {
        this.vigencias = vigencias;
    }

    public mx.com.iusacell.comun.Vigencia getVigencias(int i) {
        return this.vigencias[i];
    }

    public void setVigencias(int i, mx.com.iusacell.comun.Vigencia _value) {
        this.vigencias[i] = _value;
    }


    /**
     * Gets the tipo value for this Servicio.
     * 
     * @return tipo
     */
    public java.lang.Integer getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this Servicio.
     * 
     * @param tipo
     */
    public void setTipo(java.lang.Integer tipo) {
        this.tipo = tipo;
    }


    /**
     * Gets the idHistorico value for this Servicio.
     * 
     * @return idHistorico
     */
    public java.lang.Integer getIdHistorico() {
        return idHistorico;
    }


    /**
     * Sets the idHistorico value for this Servicio.
     * 
     * @param idHistorico
     */
    public void setIdHistorico(java.lang.Integer idHistorico) {
        this.idHistorico = idHistorico;
    }


    /**
     * Gets the valor value for this Servicio.
     * 
     * @return valor
     */
    public java.lang.String getValor() {
        return valor;
    }


    /**
     * Sets the valor value for this Servicio.
     * 
     * @param valor
     */
    public void setValor(java.lang.String valor) {
        this.valor = valor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Servicio)) return false;
        Servicio other = (Servicio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.basico==null && other.getBasico()==null) || 
             (this.basico!=null &&
              this.basico.equals(other.getBasico()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.red==null && other.getRed()==null) || 
             (this.red!=null &&
              this.red.equals(other.getRed()))) &&
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion()))) &&
            ((this.cuentaContable==null && other.getCuentaContable()==null) || 
             (this.cuentaContable!=null &&
              this.cuentaContable.equals(other.getCuentaContable()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.costo==null && other.getCosto()==null) || 
             (this.costo!=null &&
              this.costo.equals(other.getCosto()))) &&
            ((this.activacion==null && other.getActivacion()==null) || 
             (this.activacion!=null &&
              this.activacion.equals(other.getActivacion()))) &&
            ((this.desactivacion==null && other.getDesactivacion()==null) || 
             (this.desactivacion!=null &&
              this.desactivacion.equals(other.getDesactivacion()))) &&
            ((this.origen==null && other.getOrigen()==null) || 
             (this.origen!=null &&
              this.origen.equals(other.getOrigen()))) &&
            ((this.vigencias==null && other.getVigencias()==null) || 
             (this.vigencias!=null &&
              java.util.Arrays.equals(this.vigencias, other.getVigencias()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo()))) &&
            ((this.idHistorico==null && other.getIdHistorico()==null) || 
             (this.idHistorico!=null &&
              this.idHistorico.equals(other.getIdHistorico()))) &&
            ((this.valor==null && other.getValor()==null) || 
             (this.valor!=null &&
              this.valor.equals(other.getValor())));
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
        if (getBasico() != null) {
            _hashCode += getBasico().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getRed() != null) {
            _hashCode += getRed().hashCode();
        }
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        if (getCuentaContable() != null) {
            _hashCode += getCuentaContable().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getCosto() != null) {
            _hashCode += getCosto().hashCode();
        }
        if (getActivacion() != null) {
            _hashCode += getActivacion().hashCode();
        }
        if (getDesactivacion() != null) {
            _hashCode += getDesactivacion().hashCode();
        }
        if (getOrigen() != null) {
            _hashCode += getOrigen().hashCode();
        }
        if (getVigencias() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVigencias());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVigencias(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        if (getIdHistorico() != null) {
            _hashCode += getIdHistorico().hashCode();
        }
        if (getValor() != null) {
            _hashCode += getValor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Servicio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://comun.iusacell.com.mx", "Servicio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("basico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "basico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://comun.iusacell.com.mx", ">Servicio>status"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("red");
        elemField.setXmlName(new javax.xml.namespace.QName("", "red"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
        elemField.setFieldName("cuentaContable");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuentaContable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://comun.iusacell.com.mx", "CuentaContable"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desactivacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desactivacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("origen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "origen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vigencias");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vigencias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://comun.iusacell.com.mx", "Vigencia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idHistorico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idHistorico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valor"));
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
