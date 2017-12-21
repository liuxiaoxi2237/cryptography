package vxrail.local;

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
		// TODO Auto-generated method stub
	

/*	
	SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
	System.out.println("JSSE Provider is: " + sslContext.getProvider().getName());
	
	//sslContext.init(null, tmf.getTrustManagers(), null);
	sslContext.init(null,new TrustManager[] { new TrustAllX509TrustManager() },null);

	
    SSLSocketFactory sf = sslContext.getSocketFactory();

        
    // Create the connection.

    HttpsURLConnection.setDefaultSSLSocketFactory(sf);
    
*/
		
/*    
    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
    {      	

		@Override
		public boolean verify(String hostname, SSLSession session) 
		{
			// TODO Auto-generated method stub
			return true;
		}

        });
 */
    String connectionURL = "https://www.rsa.com";
    URL myURL = new URL(connectionURL);
    //URLConnection myConnection = myURL.openConnection();
    HttpURLConnection myConnection = 
    		(HttpURLConnection) myURL.openConnection();
    

   /* 
    if (myConnection instanceof HttpsURLConnection) {
        ((HttpsURLConnection) myConnection).setSSLSocketFactory(sf);
        //disable certificate verification
 
    }
    */
    
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



    