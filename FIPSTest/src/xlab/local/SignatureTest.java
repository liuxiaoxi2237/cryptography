package xlab.local;

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
		// Generate keypair
		SecureRandom rand = new SecureRandom();
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048, rand);
		KeyPair kp = kpg.generateKeyPair();
		PublicKey keyPublic = kp.getPublic();
		PrivateKey keyPrivate = kp.getPrivate();
		
		//sign data
		String orgdata = "hello, this is a message for signature test ";
		Signature sigSender = Signature.getInstance("SHA512withRSA");
		System.out.println("The signature crypto provider is: " + sigSender.getProvider().getName());
		sigSender.initSign(keyPrivate);
		sigSender.update(orgdata.getBytes());
		byte[] byteSigned = sigSender.sign();
		
		System.out.println("Signature as below:");
		String hexStr = "";
		for(int i =0; i<byteSigned.length;i++)
		{
			hexStr += Integer.toString((byteSigned[i] & 0xff) + 0x100, 16).substring(1);
		}
		System.out.println(hexStr);
		
		
		//signature verification
		Signature sigReciever = Signature.getInstance("SHA512withRSA");
		sigReciever.initVerify(keyPublic);
		sigReciever.update(orgdata.getBytes());
		boolean isValid = sigReciever.verify(byteSigned);
		//print the result
		System.out.println("Signature verification result is: ");		
		System.out.println(isValid);
		
	}

}
