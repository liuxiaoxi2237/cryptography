package xlab.local;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;



public class HttpReaderVxRail {

	public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException, KeyManagementException, NoSuchProviderException {
		// TODO Auto-generated method stub
		//this is for no-fips operation, create keystore
		KeyStore pk12store = KeyStore.getInstance("PKCS12");
		
		System.out.println("keystore type is: " + pk12store.getType());
		//pk12store.load(null, null);
		
		
		//print keystore content
		System.out.println("Keystore provider: " + pk12store.getProvider().getName());
		//load keystore from file
		FileInputStream fis = new FileInputStream("c:\\temp\\server.pfx");
		pk12store.load(fis,"testpassword".toCharArray());
		
		//read keystore content 
		Enumeration<String> alias = pk12store.aliases();
		while(alias.hasMoreElements()){
	            String certname = alias.nextElement();
	            System.out.println("Alias name: " + certname);
	        }
		
		

	TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
	tmf.init(pk12store);
	
	SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
	System.out.println("JSSE Provider is: " + sslContext.getProvider().getName());
	
	sslContext.init(null, tmf.getTrustManagers(), null);
	SSLSocketFactory sf = sslContext.getSocketFactory();
    
    //set https porperty
    HttpsURLConnection.setDefaultSSLSocketFactory(sf);

    // Create the connection.
    String connectionURL = "https://05aa-app02-vxm.vxrail.vce.com";
    URL myURL = new URL(connectionURL);
    HttpsURLConnection myConnection = (HttpsURLConnection) myURL.openConnection();
    
  
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
    