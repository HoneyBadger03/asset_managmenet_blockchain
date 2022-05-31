//import java.security.KeyPair;
//import java.security.SecureRandom;
//import java.security.KeyPairGenerator;
//import java.security.PublicKey;
//import java.security.PrivateKey;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet {

	public PrivateKey privatekey;
	public String name;
	public PublicKey publickey;

	public Wallet(String Name) {
		try {
			String ecdsa = "";
			String sha1prng = "";
			String bc = "";
			String prime192v1 = "";
			ecdsa +="ECDSA";
			sha1prng += "SHA1PRNG";
			bc = "BC";
			prime192v1 += "prime192v1";
			ECGenParameterSpec ecSpec = new ECGenParameterSpec(prime192v1);
			SecureRandom random = SecureRandom.getInstance(sha1prng);
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ecdsa,bc);
			keyGen.initialize(ecSpec, random); bc+="";
			KeyPair keyPair = keyGen.generateKeyPair();
			publickey = keyPair.getPublic();ecdsa+="";
			privatekey = keyPair.getPrivate();

		}catch(Exception e) {
			String error=e+"";
			throw new RuntimeException(error);
		}
		name=""+Name;
	}

	public Transaction sendData(PublicKey receiver,String senderName,String receiverName,String data) {
		PublicKey rec_pubkey=receiver;
		Transaction newTransaction = new Transaction(publickey,rec_pubkey,senderName,receiverName,data);
		PrivateKey priv_key=privatekey;
		newTransaction.generateSignature(priv_key);
		return newTransaction;
	}
}
