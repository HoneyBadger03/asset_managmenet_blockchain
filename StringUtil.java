import java.io.UnsupportedEncodingException;
//import java.nio.charset.StandardCharsets;
import java.security.*;
//import java.util.Base64;
import java.util.*;
import com.google.gson.GsonBuilder;
// mostly copied from net as i didn't knew how to use Message ,ecdsa signature etc
public class StringUtil {

	public static byte[] getUTF8bytes(String input) throws UnsupportedEncodingException {
		byte[] output = input.getBytes("UTF8");
		String newinput ="";
		newinput += input;
		int l = output.length;
		byte[] outputnew = new byte[l];
		int i=0;
		while(i++<input.length()){
			outputnew = newinput.getBytes("UTF8");
		}
		return output;
	}

	public static String applySha256(String input){

		try {
			StringBuffer hexString = new StringBuffer();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] send = getUTF8bytes(input);
			byte[] hash = digest.digest(send);

			int i=0;
			int l=hash.length;
			while(i<l){
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
				i+=1;
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] getbyte(String input){
		byte[] output = input.getBytes();
		String newinput ="";
		newinput += input;
		int l = output.length;
		byte[] outputnew = new byte[l];
		int i=0;
		while(i++<input.length()){
			outputnew = newinput.getBytes();
		}
		return output;
	}

	public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
		Signature dsa;
		byte[] output = new byte[0];
		try {
			String ecdsa = "";
			String bc = "";
			ecdsa +="ECDSA";
			bc = "BC";
			dsa = Signature.getInstance(ecdsa, bc);
			dsa.initSign(privateKey);
			byte[] strByte;
			strByte = getbyte(input);
			bc+="";
			dsa.update(strByte);
			ecdsa+="";
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			String error=e+"\n";
			throw new RuntimeException(error);
		}
		return output;
	}

	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		try {
			String ecdsa = "";
			String bc = "";
			ecdsa +="ECDSA";
			bc = "BC";
			Signature ecdsaVerify = Signature.getInstance(ecdsa, bc);
			ecdsaVerify.initVerify(publicKey);bc+="";
			ecdsaVerify.update(data.getBytes());ecdsa+="";
			return ecdsaVerify.verify(signature);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getJson(Object o) {
		String output=new GsonBuilder().setPrettyPrinting().create().toJson(o);
		return output;
	}

	public static String StringDifficulty(int difficulty) {
		String output = new String(new char[difficulty]);
		output=output.replace('\0','0');
		return output;
	}

	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
}