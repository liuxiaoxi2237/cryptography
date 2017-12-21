package vxrail.local;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.Iterator;
import java.util.Set;

import javax.net.ssl.SSLSocketFactory;

import com.sun.net.ssl.SSLContext;



public class FIPSNSSNormalMode {

        public static void main(String[] args) throws NoSuchAlgorithmException {


        String NSS01 = "/home/david/jssetest/nss.cfg";
        Provider p = new sun.security.pkcs11.SunPKCS11(NSS01);
        KeyPair 
        
        
        
        //Security.getProvider("SunJSSE");
        
        Provider p1 = new com.sun.net.ssl.internal.ssl.Provider(p);
        
        
		SSLContext context = SSLContext.getInstance("TLSv1.2", p1);
        
		SSLSocketFactory sf = context.getSocketFactory();
		String[] cipherSuites = sf.getSupportedCipherSuites();
		System.out.println(cipherSuites);
        		
        
        //Security.addProvider(p1);
        Set<Service> setServices = p1.getServices();
        Iterator<Service> itServices = setServices.iterator();
        Service service;
        while(itServices.hasNext())
        {
        	service = itServices.next();
        	System.out.println(" " + service.getAlgorithm() + " " + service.getType());
        }
        
        

        Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            final String name = providers[i].getName();
          System.out.println(name);






        }
}

}
