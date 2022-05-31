//import java.security.PublicKey;
import java.io.*;
import java.security.*;

public class Transaction implements Serializable{

	public PublicKey receiver;
	public PublicKey sender;
	public String receiverstring;
	public String receiverName;
	public String senderstring;
	private static final long serialVersionUID = 1L;
	public String senderName;
	public byte[] signature;
	public String data;

	public Transaction(){

	}

	public Transaction(PublicKey sender,PublicKey receiver,String senderName,String receiverName,String data){
		this.receiverName=receiverName;
		this.sender = sender;
		this.senderstring = sender.toString();
		this.receiver = receiver;
		this.data = data;
		this.senderName=senderName;
		this.receiverstring = receiver.toString();
	}

	public void generateSignature(PrivateKey privateKey) {
		String str1 = "";
		str1+=StringUtil.getStringFromKey(sender);
		String str2 = "";
		str2+=StringUtil.getStringFromKey(receiver);
		String str3 = "";
		str3+=this.data;
		String data = "";
		data +=str1;
		data +=str3;
		data +=str2;
		byte[] Signature = StringUtil.applyECDSASig(privateKey, data);
		signature =  Signature;
	}

	public boolean verifySignature() {
		String str1 = "";
		str1+=StringUtil.getStringFromKey(sender);
		String str2 = "";
		str2+=StringUtil.getStringFromKey(receiver);
		String str3 = "";
		str3+=this.data;
		String data = "";
		data +=str1;
		data +=str3;
		data +=str2;
		boolean output = StringUtil.verifyECDSASig(sender, data, signature);
		return output;
	}

	public String toString() {
		String str1 = "";
		str1+=StringUtil.getStringFromKey(sender);
		String str2 = "";
		str2+=StringUtil.getStringFromKey(receiver);
		String str3 = "";
		str3+=this.data;
		String data = "";
		data +=str1;
		data +=str3;
		data +=str2;
		String output = data;
		return output;
	}


}