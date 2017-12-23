/*
 * This app need JDK environment which support FIPS crypto provider, such as RSA BSAFE
 */
package xlab.local;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class HttpReaderFIPSBSAFE {

	public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException, KeyManagementException, NoSuchProviderException {

		//Initialize keystore with crypto provider explicitly(define crypto provider) or implicitly(by java.security preference)
		//For JsafeJCE FIPS mode, keystore encryption algorithm is PBES2. While in non-FIPS mode ,encryption algorithm is 3DES(PKCS12PBEWithSHA1And3keyDESede)
		//JKS or PKCS#12(3DES) are not supported in Bsafe FIPS mode.  PKCS#12(PBES2) keystore must be used
		//"JsafeJCE" is the crypto provider of RSA BSAFE
		KeyStore pk12store = KeyStore.getInstance("PKCS12","JsafeJCE");
		System.out.println("keystore type is: " + pk12store.getType());
		System.out.println("Keystore provider: " + pk12store.getProvider().getName());
		
		//Load keystore with password
		pk12store.load(null,"password".toCharArray());
		
		//import certificate(root cert) into keystore 		
		InputStream certis = new FileInputStream("c:\\temp\\thrawt.cer");
		BufferedInputStream bis = new BufferedInputStream(certis);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		while (bis.available() > 0) {
		    Certificate cert = cf.generateCertificate(bis);
		    //import cert with alias name
		    pk12store.setCertificateEntry("emcroot"+bis.available(), cert);
		}

		//List keystore content to verify 
		Enumeration<String> alias = pk12store.aliases();
		while(alias.hasMoreElements()){
	            String certname = alias.nextElement();
	            System.out.println("Alias name: " + certname);
	        }
	
		//write keystore into a file  
		FileOutputStream fos = new FileOutputStream("c:\\temp\\fipsstore.p12");
		pk12store.store(fos, "password".toCharArray());

		//initialize trustmanager, there are different ways
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		tmf.init(pk12store);
	
		//Initialize sslcontext for client side
		SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		System.out.println("JSSE Provider is: " + sslContext.getProvider().getName());
		sslContext.init(null, tmf.getTrustManagers(), null);
	    SSLSocketFactory sf = sslContext.getSocketFactory();

	    HttpsURLConnection.setDefaultSSLSocketFactory(sf);
    
	    // Create the connection
	    String connectionURL = "https://www.rsa.com";
	    URL myURL = new URL(connectionURL);
	    //URLConnection myConnection = myURL.openConnection();
	    HttpsURLConnection myConnection = 
    		(HttpsURLConnection) myURL.openConnection();
    
    
	    // Get the data from the server, and print it out.
	    InputStream input = myConnection.getInputStream();
	    String result = getStringFromInputStream(input);
	    System.out.println(result);
	
	}
	private static String getStringFromInputStream(InputStream is) {

	BufferedReader br = null;
	StringBuilder sb = new StringBuilder();

	String line;
	try {

		br = new BufferedReader(new InputStreamReader(is));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	return sb.toString();

}
}
    