/*
 * This is app is use to test FIPS module provdier by Mozila NSS on client side
 * NSS module is FIPS compliance and certified on SUSE Linux Enterprise version
 * In this example, NSS will act as crypto provider for SunJSSE(Not JSSE!)  
 * By default, SunJSSE is not FIPS compliant. But we can leverage Mozila NSS module to make SunJSSE FIPS compliant. 
 *Unlike RSA BSAFE SSLJ(which implement JSSE standard, and is a completely replacement of SunJSSE), we still use SunJSSE provider instead of replace it with JsafeJCE.  
 *We only configure SunJSSE to be FIPS compliant.

 */
package xlab.local;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.Security;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class FIPSwithNSSModule {


		public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, CertificateException, IOException, KeyManagementException {

        //Dynamically load NSS module
        //NSS module can loaded dynamically(as below) or statically(java.security)
        /* the content of nss.cfg
         name = NSScrypto
		 nssLibraryDirectory = /usr/lib64
	     nssSecmodDirectory = /home/david/jssetest/nssdb
		 nssDbMode = readWrite
		 nssModule = fips
         */
        String NSS01 = "//home//david//jssetest//nss.cfg";
        Provider nssProvider = new sun.security.pkcs11.SunPKCS11(NSS01);
        Security.addProvider(nssProvider);
        //Configure SunJSSE to use NSS modules, to achieve FIPS compliant
        Provider p1 = new com.sun.net.ssl.internal.ssl.Provider(nssProvider);

        /*
         * Below operations are same as normal SunJSSE operations, no change.
         * Just need to add provider "p1" -- which is FIPS compliant now  
         */
        
        //handle keystore
		KeyStore pk12store = KeyStore.getInstance("PKCS12",p1);
		System.out.println("keystore type is: " + pk12store.getType());
		System.out.println("Keystore provider: " + pk12store.getProvider().getName());
		
		//Load keystore with password
		pk12store.load(null,"password".toCharArray());
		
		//import certificate(root cert) into keystore 		
		InputStream certis = new FileInputStream("//home//david//jssetest//thrawt.cer");
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
        //handle keystore
        
		//initialize trustmanager, there are different ways
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509",p1);
		tmf.init(pk12store);
        
        
        
        //Initalize sslcontext with provider SunJSSE NSS FIPS provider
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2",p1);
        sslContext.init(null, tmf.getTrustManagers(), null);
 		SSLSocketFactory sf = sslContext.getSocketFactory();
 		//Print JSSE provide
 		System.out.println("JSSE provider is: ");
 		System.out.println(sslContext.getProvider().getName());
		//Print CipherSuites supported in current configuration, just a demo
 		String[] cipherSuites = sf.getSupportedCipherSuites();
 		System.out.println("Support CipherSuite as below: ");
 		for(int i=0; i < cipherSuites.length; i++)
 		{
 			System.out.println(cipherSuites[i]);
 		}
 		
	    HttpsURLConnection.setDefaultSSLSocketFactory(sf);
	    
	    // Create the connection
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
