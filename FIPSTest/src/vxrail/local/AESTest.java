package vxrail.local;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.Provider.Service;
import java.util.Iterator;
import java.util.Set;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;




public class AESTest {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
		// TODO Auto-generated method stub

		
		final String pldata = "This is just a plaintext message for Blowfish Test";
		byte[] aesma128 = new byte[]{0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03};
		
		byte[] bytesIV = new byte[] {0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03};
		
		
		Provider[] providers =  Security.getProviders();
		System.out.println(providers.length);
		Provider provider;
		for(int n=0;n<providers.length; n++)
		{
			provider = providers[n];
			System.out.println(provider.getName() + " -----   " + provider.getVersion() + " " + 
			provider.getInfo());
		}
		
		Provider p1 = Security.getProvider("SunJCE");
		
		Set<Service> setServices = p1.getServices();
		Iterator<Service> itServices = setServices.iterator();
		Service service;
		System.out.println("----------------algorithm----------");
		while(itServices.hasNext())
		{ 
			service = itServices.next();
			System.out.println("  " + service.getAlgorithm() + "//" + service.getType());
		}
		System.out.println("----------------algorithm----------");
		
		
		IvParameterSpec iv = new IvParameterSpec(bytesIV);
		
		
		
		
				
		Key AESKEY128 = new SecretKeySpec(aesma128,"AES");
		Cipher aescipher = Cipher.getInstance("AES/CFB/PKCS5PADDING","JsafeJCE");
				aescipher.init(Cipher.ENCRYPT_MODE, AESKEY128,iv);
		byte[] endata = aescipher.doFinal(pldata.getBytes());
		System.out.print("-----------encryption-----------");
		String hexStr = "";
			for(int i =0; i<endata.length;i++)
				{
					hexStr += Integer.toString((endata[i] & 0xff) + 0x100, 16).substring(1);
				}
		System.out.println(hexStr);
		
		aescipher.init(Cipher.DECRYPT_MODE, AESKEY128,iv);
		byte[] dedata = aescipher.doFinal(endata);
		String ds = new String(dedata);
		System.out.println("-----------decryption---------");
		System.out.println(ds);
		
		System.out.println("The provider name is: " + aescipher.getProvider().getName());
		
		
	
	///Message Digest Test
	           
	MessageDigest md = MessageDigest.getInstance("MD5", "JsafeJCE");
	md.update(pldata.getBytes());            
	byte[] digest = md.digest();     
	System.out.println("-----------Digest Begin -----------");
	String hexdigestStr = "";
		for(int i =0; i<digest.length;i++)
			{
				hexdigestStr += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
			}
		System.out.println(hexdigestStr);
		System.out.println("-----------Digest Finished -----------");
	}
}

