package mx.com.iusacell.mapas.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.rpc.ServiceException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AlgoritmoAes {

  private static final String ALGO = "AES/CBC/PKCS5Padding";

  public String cutKey(String keyValue) {
    if (keyValue.length() > 16) {
      keyValue = keyValue.substring(0, 16);
    }
    return keyValue;
  }

  public String encrypt(String x, byte[] KeyValue) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGO);
    IvParameterSpec ivSpec = new IvParameterSpec(KeyValue);
    SecretKeySpec keySpec = new SecretKeySpec(KeyValue, "AES");
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
    byte[] encrypted = cipher.doFinal(x.getBytes());
    String encryptedValue = new BASE64Encoder().encode(encrypted);
    return encryptedValue;
  }

  public String decrypt(String encryptedData, byte[] KeyValue) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGO);
    IvParameterSpec ivSpec = new IvParameterSpec(KeyValue);
    SecretKeySpec keySpec = new SecretKeySpec(KeyValue, "AES");
    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
    byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
    byte[] decValue = cipher.doFinal(decordedValue);
    String decryptedValue = new String(decValue);
    return decryptedValue;
  }

  public String decryptLogin(String data, String keyValue) throws ServiceException {
    if (keyValue.length() >= 25) {
      try {
        data = this.decrypt(data, keyValue.substring(9, 25).getBytes());
      } catch (Exception e) {
        throw new ServiceException(e.getMessage());
      }
    } else {
      throw new ServiceException("Cadena no valida", new ServiceException("handledException"));
    }
    return data;
  }

  public String encryptLlave(String data, byte[] keyBytes) throws Exception {
    byte[] key = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
    Cipher aesCipherForEncryption = Cipher.getInstance(ALGO);
    aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(key));
    byte[] byteDataToEncrypt = data.getBytes();
    byte[] byteCipherText = aesCipherForEncryption.doFinal(byteDataToEncrypt);
    String strCipherText = new BASE64Encoder().encode(byteCipherText);
    return strCipherText;
  }

}