package xlab.local;

import java.security.Provider;
import java.security.Security;
import java.security.Provider.Service;
import java.util.Iterator;
import java.util.Set;

public class ListProvider {

	public static void main(String[] args) {
		//Get and print all crypto providers in current environment, just a demo
		Provider[] providers =  Security.getProviders();
		Provider provider;
		System.out.println("Supported Crypto Provider in current environment:");
		System.out.println("Total number of crypto provider:   "+ providers.length);
		for(int n=0;n<providers.length; n++)
		{
			provider = providers[n];
			System.out.println(provider.getName() + "  " + provider.getVersion() + "  " + 
			provider.getInfo());
		}
		
		//Initialize with defined crypto provider "SunJCE".
		//If no provider defined, using preference defined in java.security 
		Provider p1 = Security.getProvider("SunJCE");
		
		//Print algorithm supported by the provider, this is just for demo
		Set<Service> setServices = p1.getServices();
		Iterator<Service> itServices = setServices.iterator();
		Service service;
		System.out.println();
		System.out.println("Algorithm supported by SunJCE:");
		System.out.println("----------------algorithm----------");
		while(itServices.hasNext())
		{ 
			service = itServices.next();
			System.out.println("  " + service.getAlgorithm() + "//" + service.getType());
		}
		System.out.println("----------------algorithm----------");
		

	}

}
