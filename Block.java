//import java.io.File;
//import java.io.FileWriter;
//import java.io.Serializable;
import java.io.*;
//import java.util.ArrayList;
import java.util.*;

public class Block implements Serializable  {

	private static final long serialVersionUID = 1L;
	String hash,previousHash,merkelroot;
	Long nonce = (long) 0;
	ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	Long timestamp;

	public Block(String previousHash, ArrayList<Transaction> list) {
		this.timestamp = System.currentTimeMillis();
		this.transactionList = new ArrayList<Transaction>(list);
		this.previousHash = previousHash;
		this.merkelroot = totalTransaction();
		this.hash = calcHash();
	}

	public String totalTransaction() {      //like a merkel root
		String data = "";
		for(int i=0;i<=transactionList.size()-1;i++)
		{
			data += transactionList.get(i).toString();
		}
		return data;
	}

	public String calcHash() {
		String hash;
		hash = StringUtil.applySha256(
				previousHash +
						Long.toString(timestamp) +
						Long.toString(nonce) +
						merkelroot
		);
		System.out.println("going for DES calculation");
		System.out.println("hash is : "+hash);
		String a = hash.substring(0, 16);
		String b = hash.substring(16, 32);
		String c = hash.substring(32, 48);
		String d = hash.substring(48, 64);
		String A = dataEncryptionStandard.getDES(a);
		String B = dataEncryptionStandard.getDES(b);
		String C = dataEncryptionStandard.getDES(c);
		String D = dataEncryptionStandard.getDES(d);
		String HASH = A + B + C + D;
		System.out.println("DES HASH is : "+HASH);
		return HASH;
//		return hash;
	}


	public String toString() {
		return StringUtil.getJson(this);
	}

	public void printBlock() {

		System.out.println("			|\n"+"			|\n"+"			|\n");
		System.out.println("*******************************************************");
		System.out.println("timestamp: "+timestamp+"\n"+"hash: "+ hash+"\n"+"prevHash: "+ previousHash+"\n"+"nonce: "+ nonce);
		int i=transactionList.size()-1;
		try {
			File datah = new File("data.txt");
			datah.createNewFile();
			FileWriter fw = new FileWriter(datah.getName(),true);
			String str="";
			str+="User: "+transactionList.get(i).senderName+"\n"+"Sent data: "+transactionList.get(i).data+ "\n to " + transactionList.get(i).receiverName+"\n"+transactionList.get(i).receiver;
			str+="User: "+transactionList.get(i).receiverName+"\n"+"Received data: "+transactionList.get(i).data + "\n from " + transactionList.get(i).senderName+"\n"+transactionList.get(i).sender;
			fw.write(str);
			fw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		for(Transaction trans: transactionList) {
			System.out.println("\n");
			System.out.println("Sender name: \n"+trans.senderName+"\nSender publickey\n"+trans.sender+"\nReceiver name:\n"+trans.receiverName+"\nReceiver publickey\n"+trans.data);
			System.out.println("----------------------------");
		}
		System.out.println("*********************************************************");
		//System.out.println("\n\n");
	}

	public boolean mineBlock(int difficulty) {
		String target = StringUtil.StringDifficulty(difficulty);
		nonce = (long) 0;
		while(!target.equals(hash.substring(0, difficulty))) {
			nonce++;
			hash = calcHash();
		}
		return true;
	}

}
