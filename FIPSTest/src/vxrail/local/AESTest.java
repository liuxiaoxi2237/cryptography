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
//AES Test
public class AESTest {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
		//plaintext to be encrypted		
		final String pldata = "This is just a plaintext message for Blowfish Test";
		//AES 128 manual keys, 16 bytes
		byte[] aesma128 = new byte[]{0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03};
		//AES IV used in CBC mode, 16 bytes
		//For ECB, IV is not required
		byte[] bytesIV = new byte[] {0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03};
		
		//Encryption		
		//Initialize IV, IV is required in most of the "block cipher mode of operation
		IvParameterSpec iv = new IvParameterSpec(bytesIV);
		//Initialize key. In product environment, it should be created by keygenerator				
		Key AESKEY128 = new SecretKeySpec(aesma128,"AES");
		//Set Algorithm/cipher mode/padding mode. 
		//If cipher mode not set, default cipher mode is ECB, which is forbidden to use.
		//Set CryptoProvider. If not set, then using java.security preference
		Cipher aescipher = Cipher.getInstance("AES/CFB/PKCS5PADDING","SunJCE");
		//Implement encryption. IV is required, since cipher mode is CBC
		aescipher.init(Cipher.ENCRYPT_MODE, AESKEY128,iv);
		//Print cipher text 
		byte[] endata = aescipher.doFinal(pldata.getBytes());
		System.out.println("-----------encryption-----------");
		String hexStr = "";
			for(int i =0; i<endata.length;i++)
				{
					hexStr += Integer.toString((endata[i] & 0xff) + 0x100, 16).substring(1);
				}
		System.out.println(hexStr);
		
	}
}

