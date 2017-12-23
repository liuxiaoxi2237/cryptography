package xlab.local;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.Provider.Service;
import java.util.Iterator;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



public class BlowfishTest {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		//This sample is similar to  AESTest
		final String pldata = "This is just a plaintext message for Blowfish Test";
		byte[] aesma128 = new byte[]{0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03};
		//Blowfish IV in CBC is 8 bytes instead of 16 bytes for AES
		byte[] bytesIV = new byte[] {0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03};
		
		
		SecretKey blowfishkey = new SecretKeySpec(aesma128, "Blowfish");
		IvParameterSpec iv = new IvParameterSpec(bytesIV);
				
		//encryption
		Cipher aescipher = Cipher.getInstance("Blowfish/CBC/PKCS5PADDING");
		aescipher.init(Cipher.ENCRYPT_MODE, blowfishkey,iv);
		byte[] endata = aescipher.doFinal(pldata.getBytes());
		System.out.println("-----------encryption-----------");
		String hexStr = "";
				for(int i =0; i<endata.length;i++)
				{
					hexStr += Integer.toString((endata[i] & 0xff) + 0x100, 16).substring(1);
				}
		System.out.println(hexStr);
		
		//decryption
		aescipher.init(Cipher.DECRYPT_MODE, blowfishkey,iv);
		byte[] dedata = aescipher.doFinal(endata);
		String ds = new String(dedata);
		System.out.println("-----------decryption---------");
		System.out.println(ds);
		

		}
				
	}


