package mx.com.iusacell.services.miusacell.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

public class AlgoritmoAes
{	
	  public  String decrypt(final String encryptedData, final byte[] KeyValue) throws  IOException, InvocationTargetException,
      SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		  String decryptedValue="";
		  try
	    {
				Key key = generateKey(KeyValue);
			    Cipher c = Cipher.getInstance("AES");
			    c.init(Cipher.DECRYPT_MODE, key);
			    byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
			    byte[] decValue = c.doFinal(decordedValue);
			    decryptedValue = new String(decValue,"UTF8");
	    }
	    catch(IOException e)
	    {
	    	e.getMessage();
	    	decryptedValue="3";
	    }
		  
	    return decryptedValue;
	  }
  
	  private  Key generateKey(final byte[] keyValue) throws IOException, InvocationTargetException,
      SQLException{
	    Key key = new SecretKeySpec(keyValue, "AES");
	    return key;
	  }

	}