/*
 * Created on 29/08/2007
 *
 */
package mx.com.iusacell.services.miiusacell.masivo.security;

import java.security.spec.AlgorithmParameterSpec;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public final class DESAlgorithm {

	private static final String algorithm = "DES/CBC/PKCS5Padding";
	private static final String algorithmKey = "DES";
	private static final AlgorithmParameterSpec algorithmParameterSpec;
	private static final String key;
	private static final SecretKey secretKey;
	private static final BASE64Encoder bASE64Encoder;
	private static final BASE64Decoder bASE64Decoder;
	private static DESAlgorithm dESAlgorithm;
	
	static {
		try {
			key = "facturas";
			secretKey = new SecretKeySpec(DESAlgorithm.key.getBytes("UTF-8"),DESAlgorithm.algorithmKey);
			algorithmParameterSpec = new IvParameterSpec(new byte[] {(byte)0x7B,(byte)0x1D,(byte)0x2,(byte)0x9E,(byte)0xF9,(byte)0xF,(byte)0x50,(byte)0x5C});
			bASE64Encoder = new BASE64Encoder();
			bASE64Decoder = new BASE64Decoder();
		} catch (Exception exp) {
			throw new ExceptionInInitializerError("DESAlgorithm: No se logro inicializar: "+exp);
		}
	}
	
	/**
	 * 
	 */
	private DESAlgorithm() throws Exception {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static synchronized DESAlgorithm getInstance() throws Exception {
		if(dESAlgorithm==null) {
			dESAlgorithm = new DESAlgorithm();
		}
		return dESAlgorithm;
	}
	
	private static Cipher createEncrypter() throws Exception {
		Cipher encrypter = null;
		try {
			encrypter =  Cipher.getInstance(algorithm);
			encrypter.init(Cipher.ENCRYPT_MODE,DESAlgorithm.secretKey,algorithmParameterSpec);
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("DESAlgorithm:createEncrypter() Error durante la creación de un encryter: " + e,e);
		} 
		return encrypter;
	}
	
	private static Cipher createDecrypter() throws Exception {
		Cipher decrypter = null;
		try {
			decrypter =  Cipher.getInstance(algorithm);
			decrypter.init(Cipher.DECRYPT_MODE,DESAlgorithm.secretKey,algorithmParameterSpec);
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("DESAlgorithm:createDecrypter() Error durante la creación de un decrypter: " + e,e);
		} 
		return decrypter;
	}
	
	public byte[] encrypt(final String originalData) throws Exception {
		Cipher encrypter = null;
		byte[] encryptedData = null;
		try {
			encrypter = createEncrypter();
			encryptedData = encrypter.doFinal(originalData.getBytes("UTF-8"));
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("DESAlgorithm:encrypt("+originalData+") Error durante el proceso de cifrado: " + e,e);
		}
		return encryptedData;
	}
	
	public String encrypt64(final String originalData) throws Exception {
		String encryptedData = null;
		try {
			encryptedData = encode64(encrypt(originalData));
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("DESAlgorithm:encrypt64("+originalData+") Error durante el proceso de cifrado64: " + e,e);
		}
		return encryptedData;
	}
	
	public String dencrypt(final byte[] encryptedData ) throws Exception {
		Cipher decrypter = null;
		String originalData = null;
		try {
			decrypter = createDecrypter();
			originalData = new String(decrypter.doFinal(encryptedData));
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("DESAlgorithm:dencrypt("+encryptedData+") Error durante el proceso de decifrado: " + e,e);
		}
		return originalData;
	}
	
	public String dencrypt64(final String encryptedData ) throws Exception {
		String originalData = null;
		try {
			originalData = dencrypt(decode64(encryptedData));
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("DESAlgorithm:dencrypt64("+encryptedData+") Error durante el proceso de decifrado64: " + e,e);
		}
		return originalData;
	}
	
	public String encode64(final byte []decodedData) throws Exception {
		String encodedData = null;
		try {
			encodedData = removeWhiteSpaces(bASE64Encoder.encode(decodedData));
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("DESAlgorithm:encode64("+decodedData+") Error durante la codificacion: " + e,e);
		}
		return encodedData;
	}

	public byte[] decode64(final String encodedData) throws Exception {
		byte[] decodedData = null;
		try {
			decodedData = bASE64Decoder.decodeBuffer(encodedData);
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("DESAlgorithm:decode64("+encodedData+") Error durante la decodificacion: " + e,e);
		}
		return decodedData;
	}

	/**
	 * @param encode
	 * @return
	 */
	private static String removeWhiteSpaces(final String encode) {
		StringBuffer url = new StringBuffer();
		StringTokenizer tokens = new StringTokenizer(encode);
		while (tokens.hasMoreTokens()) {
			url.append(tokens.nextToken());
		}
		return url.toString();
	}
}