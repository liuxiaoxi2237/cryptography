package vxrail.local;

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



public class BFTest {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		// TODO Auto-generated method stub

		
		final String pldata = "This is just a plaintext message for Blowfish Test";
		byte[] aesma128 = new byte[]{0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03};
		
		byte[] bytesIV = new byte[] {0x00,0x01,0x02,0x03,
				0x00,0x01,0x02,0x03};
		
		
		SecretKey blowfishkey = new SecretKeySpec(aesma128, "Blowfish");
		IvParameterSpec iv = new IvParameterSpec(bytesIV);
				
		//Key AESKEY128 = new SecretKeySpec(aesma128,"AES");
		Cipher aescipher = Cipher.getInstance("Blowfish/CBC/PKCS5PADDING");
		aescipher.init(Cipher.ENCRYPT_MODE, blowfishkey,iv);
		byte[] endata = aescipher.doFinal(pldata.getBytes());
		System.out.print("-----------encryption-----------");
		String hexStr = "";
				for(int i =0; i<endata.length;i++)
				{
					hexStr += Integer.toString((endata[i] & 0xff) + 0x100, 16).substring(1);
				}
		System.out.println(hexStr);
		
		aescipher.init(Cipher.DECRYPT_MODE, blowfishkey,iv);
		byte[] dedata = aescipher.doFinal(endata);
		String ds = new String(dedata);
		System.out.println("-----------decryption---------");
		System.out.println(ds);
		

		}
				
	}


