package vxrail.local;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

import javax.crypto.KeyGenerator;

public class SignatureTest {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		SecureRandom rand = new SecureRandom();
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048, rand);
		KeyPair kp = kpg.generateKeyPair();
		PublicKey keyPublic = kp.getPublic();
		PrivateKey keyPrivate = kp.getPrivate();
		
		//sign data
		String orgdata = "hello, this a message for signature ";
		Signature sigSender = Signature.getInstance("SHA512withRSA");
		sigSender.initSign(keyPrivate);
		sigSender.update(orgdata.getBytes("UTF-8"));
		byte[] byteSigned = sigSender.sign();
		
		String hexStr = "";
		for(int i =0; i<byteSigned.length;i++)
		{
			hexStr += Integer.toString((byteSigned[i] & 0xff) + 0x100, 16).substring(1);
		}
		System.out.println(hexStr);
		
		System.out.println(sigSender.getProvider().getName());
		
	}

}
