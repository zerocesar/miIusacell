package mx.com.iusacell.services.miusacell.util;

import org.apache.commons.codec.binary.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.xml.rpc.ServiceException;
import java.net.URL;

public abstract class B64 {

	public static String remoteImgToBase64(final String nameFile) throws ServiceException {	    
		InputStream is = null;
		ByteArrayOutputStream bos = null;
		String encodedString = "";
		try {
			bos = new ByteArrayOutputStream();
			/** remote img*/
			URL url = new URL(nameFile);
			BufferedImage img = ImageIO.read(url);	
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, "png", os);
			is = new ByteArrayInputStream(os.toByteArray());
			/** remote img*/

			byte[] buf = new byte[128];
			try {
				for (int readNum; (readNum = is.read(buf)) != -1;) {
					bos.write(buf, 0, readNum);
				}
			}
			catch (IOException e) {
				encodedString = e.getLocalizedMessage();
				bos.close();
				is.close();
				return encodedString;
			}
			byte[] bytes = bos.toByteArray();
			byte[] encoded = Base64.encodeBase64(bytes);
			encodedString = new String(encoded);
		}
		catch (Exception e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
		finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (is != null) {
					is.close();
				}	        
			}
			catch (Exception e) {
				throw new ServiceException(e.getLocalizedMessage());
			}
		}
		return encodedString;
	}		
}
