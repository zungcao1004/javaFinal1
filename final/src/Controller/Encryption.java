package Controller;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class Encryption {
	public static String encrypt(String msg) {
		String s1="";
		try {
			FileInputStream fis = new FileInputStream("D:\\publicKey.rsa");
			byte[] b = new byte[fis.available()];
			fis.read(b);
			fis.close();
			
			X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = factory.generatePublic(spec);
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte encryptOut[] = cipher.doFinal(msg.getBytes());
			s1= Base64.getEncoder().encodeToString(encryptOut);
			return s1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s1;
	}
}
