package xlab.local;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestTest {

	public static void main(String[] args) throws NoSuchAlgorithmException {

		String pldata = "This is just a message digest test data, lalalalalalalalalalalalalaal";
		//Initialize hash function
		MessageDigest md = MessageDigest.getInstance("MD5"); //MD5, SHA1, SHA2 different digest size
		System.out.println("The crypto provider for hash functino is:  "+ md.getProvider().getName());
		//implementation hash
		md.update(pldata.getBytes());  
		//print digest message
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
