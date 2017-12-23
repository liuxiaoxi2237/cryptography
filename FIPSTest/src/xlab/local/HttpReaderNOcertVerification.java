/*
 * This app is to demonstrate client side https access without certificate verification
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
import java.net.HttpURLConnection;
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
import java.security.cert.X509Certificate;
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

public class HttpReaderNOcertVerification {

	public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException, KeyManagementException, NoSuchProviderException {

	//Initialize sslContext
	SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

	//With customized TrustManager
	sslContext.init(null,new TrustManager[] { new TrustAllX509TrustManager() },null);
	SSLSocketFactory sf = sslContext.getSocketFactory();
    
    //No certificate verification
    HttpsURLConnection.setDefaultSSLSocketFactory(sf);
    //No hostname verification during SSL handshake. SAN
    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
    {      	

		@Override
		public boolean verify(String hostname, SSLSession session) 
		{
			//Do nothing, no hostname verification
			return true;
		}

        });
 	
    //List cipherSuite supported in this environment, just a demo
	String[] cipherSuite = HttpsURLConnection.getDefaultSSLSocketFactory().getSupportedCipherSuites();	
	System.out.println("Support CipherSuite as below: ");
	for(int i=0; i < cipherSuite.length; i++)
	{
		System.out.println(cipherSuite[i]);
	}
			
	//List Hostnameverifier, just a demo
	System.out.println(HttpsURLConnection.getDefaultHostnameVerifier().getClass().getName());
	

	//By default, https conntions will use java default trust sotre to verify server certificate
	//This mean, if access web site with valid cert. No need above HttpsURLConnection configuration
	//You'd better test it with a website with self-sign(or untrust) cert !!!
    String connectionURL = "https://www.rsa.com";
    URL myURL = new URL(connectionURL);
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



    