package vxrail.local;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.security.auth.x500.X500Principal;

import sun.net.www.http.HttpClient;

public class CATest {

	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, CertificateException, IOException {
		// TODO Auto-generated method stub
		/*
		SecureRandom rand = new SecureRandom();
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		kpg.initialize(2048, rand);
		KeyPair kp = kpg.generateKeyPair();
		PublicKey keyPublic = kp.getPublic();
		PrivateKey keyPrivate = kp.getPrivate();
		
		X500Principal x500PrincipalSubject =
				new X500Principal("CN=RootCA, DC=EMC, DC=COM,OU=VXRAIL,O=EMC,C=CN");
		BigInteger biSerialNumber = BigInteger.valueOf((System.currentTimeMillis()));
		int nYearsValid = 20;
		Date dateNotBefore = new Date(System.currentTimeMillis());
		Date dateNotAfter = new Date(System.currentTimeMillis()+ 1000L*3600*24*365*20);
		
*/
			
		SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		System.out.println("JSSE Provider is: " + sslContext.getProvider().getName());
		
		Cipher aescipher = Cipher.getInstance("AES");
		System.out.println("Crypto Provider is: " + aescipher.getProvider().getName());

		
		//
		
		KeyStore pk12store = KeyStore.getInstance("PKCS12");
		System.out.println("Keystore provider: " + pk12store.getProvider().getName());

        char[] password = "password".toCharArray();
        pk12store.load(null,password);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(pk12store);
		
	}

}
