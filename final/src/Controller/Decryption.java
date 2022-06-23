package Controller;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class Decryption {
	public static String decrypt(String string) {
		try {
			FileInputStream fis = new FileInputStream("D:\\privateKey.rsa");
			byte[] b = new byte[fis.available()];
			fis.read(b);
			fis.close();

			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = factory.generatePrivate(spec);

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte decryptOut[] = cipher.doFinal(Base64.getDecoder().decode(string));
			String str = new String(decryptOut);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Decrypt failed";
	}
}
