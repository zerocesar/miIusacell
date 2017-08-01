package mx.com.iusacell.services.miusacell.util;


public abstract class  Constantes {
  public static final boolean 	PRODUCCION					= true;
  public static final boolean 	DEMO						= false;
  public static boolean 		ABONO_X_1_PESO				= false;
  public static boolean			VALIDA_VIGENCIA_TOKEN		= true;
  public static final int 		OPCION_CORREO				= 1;
  public static final int 		OPCION_CONTRASENA			= 2;
  public static final int 		OPCION_MSJ_CAMBIO_CORREO	= 1;
  public static final int 		OPCION_MSJ_CAMBIO_PASS		= 2;
  public static final int 		OPCION_MSJ_SERV_CONTRAT		= 3;
  public static final int 		OPCION_MSJ_PIN_ALTA_USUARIO	= 4;
  
  
  public static final String MSJ_CAMBIO_CORREO		= "Se ha cambiado satisfactoriamente tu correo electronico.";
  public static final String MSJ_CAMBIO_PASS		= "Se ha cambiado satisfactoriamente tu password.";
  public static final String MSJ_SERV_CONTRAT		= "Se ha contratado satisfactoriamente el servicio.";
  public static final String MSJ_PIN_ALTA_USUARIO	= "Se ha generado satisfactoriamente el codigo pin.";
  public static final String PATH_XML_QA_PRODUCCION	= "/home/jboss/miIusacell.xml";
  public static final String PATH_XML_DESARROLLO	= "/miIusacell.xml";
  public static final String FORMATO_INFO_LOGG		= "\n[ :: [INFO] :: ] ";
  public static final String FORMATO_INFO_ERROR		= "\n[ :: [E R R O R] :: ] ";
  public static final String MSJ_BD_CONSUMOS 	    = "No existe información en BD";
  
  
  public static final int ESTATUS_DISPONIBLE=0;
  public static final int ESTATUS_OCUPADO=1;
  public static final int ESTATUS_NO_UTILIZADO=2;
  
  //**********************************************************
  //Servicios internos Mi Iusacell
  public static final int SERVICIO_ALTA_USUARIO				= 10;
  public static final int SERVICIO_ALTA_TARJETA_FRECUENTE	= 11;
  public static final int SERVICIO_ALTA_SERV_ADICIONAL		= 12;
  public static final int SERVICIO_TRANS_SERV_ADICIONAL		= 13;
  public static final int SERVICIO_TRANS_PAGO_REALIZADO		= 14;
  public static final int SERVICIO_TRANS_COMPRA_TIEMPO_AIRE	= 15;
  public static final int SERVICIO_CAMBIO_PASSWORD			= 16;  
  public static final int SERVICIO_BAJA_TARJETA_FREC		= 17;
  public static final int SERVICIO_CAMBIO_CORREO			= 18;
  public static final int SERVICIO_GENERA_TOKEN_UNA_VEZ		= 19;
  public static final int SERVICIO_GENERA_TOKEN_X_USUARIO	= 20;
  public static final int SERVICIO_GENERA_PIN				= 21;  
  public static final int SERVICIO_IS_LOGIN					= 22;
  public static final int SERVICIO_TARJETAS_X_USUARIO		= 23;
  public static final int SERVICIO_SERVICIO_X_USUARIO		= 24;
  public static final int SERVICIO_SERVICIO_ADICIONAL_X_UNIDAD_NEGOCIO = 25;
  public static final int SERVICIO_ALTA_NUMERO_FRECUENTE	= 26;
  public static final int SERVICIO_BAJA_NUMERO_FRECUENTE	= 27;
  public static final int SERVICIO_CONSULTA_NUMERO_FRECUENTE_X_USU = 28;
  public static final int SERVICIO_SERVICIO_X_USUARIO_EXT	= 29;
  public static final int SERVICIO_ACTUALIZA_FOTOGRAFIA_X_USU = 30;
  public static final int SERVICIO_DN_ES_IUSACELL_UNEFON 	= 47;
  //**********************************************************
  
  //**********************************************************
  //Servicios Externos
  public static final int SERVICIO_DETALLE_CONSUMO			= 31;
  public static final int SERVICIO_EDITAR_PERFIL			= 32;
  public static final int SERVICIO_EDITAR_CUENTA			= 33;
  public static final int SERVICIO_MANDA_MAIL				= 34;
  public static final int SERVICIO_MANDA_MSN				= 35;
  public static final int SERVICIO_RESTABLECER_LLAVE		= 36;
  public static final int SERVICIO_CONSULTA_CONSUMOS		= 37;
  public static final int SERVICIO_ABONO_TIEMPO_AIRE		= 38;
  public static final int SERVICIO_CONSULTA_SALDO_DN		= 39;
  public static final int SERVICIO_ALTA_SERVICIO_EXT		= 40;
  public static final int SERVICIO_IUSACELL_DISTRIBUCION	= 41;
  public static final int SERVICIO_CONTROL_PARAM_ACTUALIZA	= 42;
  public static final int SERVICIO_CONTROL_PARAM_BAJA		= 43;
  public static final int SERVICIO_HORARIO_DISPONIBLE_X_CAE	= 44;
  public static final int SERVICIO_ALTA_CITA				= 45;
  public static final int SERVICIO_MAPAS_CITAS_CAES			= 46;
   //******************************************************
   
  public static final int ACTUALIZA_DN						= 999;
  
  public static final int SERVICIO_CT_MARCA_TARJETA=1;
  public static final int SERVICIO_CT_FUNCION_USUARIO_HUMANO=2;
  public static final int SERVICIO_CT_TIPO_USUARIO=3;
  public static final int SERVICIO_CT_TIPO_CLIENTE=4;
  public static final int SERVICIO_CT_TIPO_CUENTA=5;
  public static final int SERVICIO_CT_PAIS=6;
  public static final int SERVICIO_CT_UNIDAD_NEGOCIO=7;
  public static final int SERVICIO_CT_PERIODICIDAD_SERVICIO=8;
  public static final int SERVICIO_CT_TIPO_TRANSACCION=9;
  
  //Header AltaServicios
  public static final int ALTA_HEADER_PAIS_ID=1;
  public static final int ALTA_HEADER_CANAL_ID=1;
  public static final int ALTA_HEADER_SUCURSAL_ID=1;
  public static final int ALTA_HEADER_SISTEMA_ID=1;
  public static final int ALTA_HEADER_SERVICIO_ID=1;
  public static final int ALTA_TRANSACCION_ID=1;
  public static final int ALTA_USERSAEO_ID=1;
  public static final String ALTA_ORIGEN="E2E";
  
  
  //parametros header  
  public static final int HEADER_CANAL_ID=1;
  public static final int HEADER_PAIS_ID=1;
  public static final int HEADER_SERVICIO_ID_TIEMPO_AIRE=57;
  public static final int HEADER_SERVICIO_ID_CONSUMO_DETALLE=56;
  public static final int HEADER_SERVICIO_ID_ALTA_SERVICIO=61;
  public static final int HEADER_SERVICIO_ID_OBTIENE_SERVICIOS=73;
  public static final int HEADER_SISTEMA_ID=23;
  public static final String HEADER_SUCURSAL_ID="V10018";
  
  public static final String ABONO_SUCURSAL="00018";
  public static final String ABONO_NEGOCIO="022";
  public static final String ABONO_CONTRASENIA="null";
  
  //parametros acceso
  public static final String ACCESO_TRANSACCION_ID="1";
  public static final String ACCESO_USERSAEO_ID="1";
  public static final String ACCESO_ORIGEN="E2E";
  
  //Focalizacion
  public static final int FOCALIZACION_UNEFON=5;
  public static final int FOCALIZACION_IUSACELL=7;
  
  // Tarjetas 
  public static final String TARJETAS_TIPO_DEBITO="DEBITO";
  public static final String TARJETAS_TIPO_CREDITO="CREDITO";
  
  //llave descencriptar aes128
  public static final String LLAVE_DESENCRIPTAR_AES="GrUPoSaLInaSsACv";
  
  public static final int DOMICILIO_REGISTRO = 1;
  public static final int DOMICILIO_ACTUALIZA = 3;
  public static final int DOMICILIO_ELIMINA = 4;
  
  public static final String PARAMETRO_DISTRIBUIDOR_01="AGhAxzwOwKEbI12XQ1KNjQ=-";
  public static final String PARAMETRO_DISTRIBUIDOR_02="AGhAxzwOwKEbI12XQ1KNjQ=-";
  public static final String PARAMETRO_DISTRIBUIDOR_03="0";
  public static final String PARAMETRO_DISTRIBUIDOR_04="0";
  
  public static boolean validaFocalizacion		= false;
  
  public static final int PLATAFORMA_ETALK		= 1;
  public static final int PLATAFORMA_LEGACY		= 2;
  
  public static final int KEY_TECNOLOGIA_GSM 	= 2;
  public static final int KEY_TECNOLOGIA_CDMA 	= 1;
  
  public static final int KEY_OPERACION_EXITOSA			 	= 0;
  public static final int KEY_EXCEPCION_PARAM_INCOMPLETO 	= 1;
  public static final int KEY_EXCEPCION_ERROR_CONTROLADO 	= 2;
  public static final int KEY_EXCEPCION_ERROR_SISTEMA 		= 3;
  
}
