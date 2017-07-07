package mx.com.iusacell.services.miusacell.util;

import java.io.UnsupportedEncodingException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
//import javax.xml.bind.DatatypeConverter;


public class AesTA {

	private final int keySize;
	private final int iterationCount;
	private final Cipher cipher;

	private static final String IV =   "AA00F0000C00000A000000A8090000D0";
	private static final String SALT = "AA00F0000C00000A000000A8090000D0";
	private static final int KEY_SIZE = 128;
	private static final int ITERATION_COUNT = 10000;

	public AesTA() throws Exception {
		this.keySize = KEY_SIZE;
		this.iterationCount = ITERATION_COUNT;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (Exception e) {
			// System.out.println(" Exception - AesUtilHelper : " +
			// e.getLocalizedMessage());
			throw e;
		}
	}

	public String encrypt(String passPhrase, String textToEncrypt)
			throws Exception {
		try {
			SecretKey key = generateKey(SALT, passPhrase);
			byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, IV,
					textToEncrypt.getBytes("UTF-8"));
			return base64(encrypted);
		} catch (UnsupportedEncodingException e) {
			// System.out.println(" UnsupportedEncodingException - encrypt : " +
			// e.getLocalizedMessage());
			throw new Exception("Ocurrio un error al encriptar la cadena : "
					+ e);
		} catch (Exception e) {
			// System.out.println(" Exception - encrypt : " +
			// e.getLocalizedMessage());
			throw new Exception("Ocurrio un error al encriptar la cadena : "
					+ e.getMessage());
		}
	}

	public String decrypt(String passPhrase, String cipherToDecrypt)
			throws Exception {
		try {
			SecretKey key = generateKey(SALT, passPhrase);
			byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, IV,
					base64(cipherToDecrypt));
			return new String(decrypted, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println(" UnsupportedEncodingException - decrypt : "
					+ e.getLocalizedMessage());
			throw new Exception("Ocurrio un error al desencriptar la cadena : "
					+ e);
		} catch (Exception e) {
			// System.out.println(" Exception - decrypt : " +
			// e.getLocalizedMessage());
			throw new Exception("Ocurrio un error al desencriptar la cadena : "
					+ e.getMessage());
		}
	}

	private byte[] doFinal(int encryptMode, SecretKey key, String iv,
			byte[] bytes) throws Exception {
		try {
			cipher.init(encryptMode, key, new IvParameterSpec(hex(iv)));
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			// System.out.println(" Exception - doFinal : " +
			// e.getLocalizedMessage());
			throw e;
		}
	}

	private SecretKey generateKey(String salt, String passphrase) {
		try {
			SecretKeyFactory factory = SecretKeyFactory
					.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex(salt),
					iterationCount, keySize);
			SecretKey key = new SecretKeySpec(factory.generateSecret(spec)
					.getEncoded(), "AES");
			return key;
		} catch (Exception e) {
			// System.out.println(" Exception - generateKey : " +
			// e.getLocalizedMessage());
			return null;
		}
	}

	public static String base64(byte[] bytes) {
		return DatatypeConverter.printBase64Binary(bytes);
//		return new BASE64Encoder().encode(bytes);
	}

	public static byte[] base64(String str) {
//		try {
//			return  new BASE64Decoder().decodeBuffer(str);
//		} catch (IOException e) {
//			return null;
//		}
		return DatatypeConverter.parseBase64Binary(str);
	}

	public static String hex(byte[] bytes) {
//		return BaseEncoding.base16().encode(bytes);
		return DatatypeConverter.printHexBinary(bytes);
	}

	public static byte[] hex(String str) {
		
		return DatatypeConverter.parseHexBinary(str);		
//		return BaseEncoding.base16().decode(str);
	}
}