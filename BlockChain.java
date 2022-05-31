//import com.google.gson.ExclusionStrategy;
//import com.google.gson.FieldAttributes;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

//import java.io.File;
import java.io.*;
import java.security.*;
import java.util.*;

//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.io.FileWriter;
//import java.io.Serializable;

public class BlockChain implements Serializable{
	private static final long serialVersionUID = 1L;
	public ArrayList<Block> Chain = new ArrayList<Block>();
	public ArrayList<Transaction> undoneTransaction = new ArrayList<Transaction>();
	int difficulty = 2;

	public BlockChain() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		generateBlock();
	}
	public boolean createBlock() {
		int flag=1;
		if(verifyTransaction(undoneTransaction)) {
			String previousHash = Chain.get(Chain.size()-1).hash;
			flag=0;
			Block newBlock = new Block(previousHash,undoneTransaction);
			newBlock.mineBlock(difficulty);flag++;
			Chain.add(newBlock);
			newBlock.printBlock();
			undoneTransaction.clear();
			flag=0;
			if(flag==0)
				return true;
		}
		return false;
	}

	public void generateBlock() {
		Wallet Asset_info = new Wallet("New Asset Fund");
		int index=0;
		Transaction Asset_transaction = new Transaction(Asset_info.publickey,Asset_info.publickey,Asset_info.name,Asset_info.name,"Hello I am Asset_info block");
		undoneTransaction.add(Asset_transaction);index=1;
		Block Asset_info_block = new Block("0",undoneTransaction);
		Asset_info_block.mineBlock(difficulty);
		index=0;
		undoneTransaction.remove(index);
		Chain.add(Asset_info_block);
	}
	
    public boolean verifyTransaction(ArrayList<Transaction> list) {
		for(int i=0;i<=list.size()-1;i++)
		{
    		if(!list.get(i).verifySignature())
    			return false;
    	}
    	return true;
    }
    


	/*public static void writeJSON(BlockChain obj, String Filename)
	{
		try(Writer writer = new FileWriter(Filename)){
			Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
				@Override
				public boolean shouldSkipField(FieldAttributes arg0) {
					return false;
				}

				@Override
				public boolean shouldSkipClass(Class<?> arg0)
				{
					return arg0 == PublicKey.class || arg0 == PrivateKey.class || arg0 == byte[].class;
				}
			}).setPrettyPrinting().create();
			gson.toJson(obj,writer);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}*/
    
    public String viewUser(PublicKey publickey,String name) {
    	String str="";
    	//str+="User: \n"+name+"\n"+"My public key is \n"+publickey+"\n";
    	try {
			BufferedReader br = new BufferedReader(new FileReader("data.txt"));
			String st=br.readLine();
			while(st!=null) {
				String[] words = st.split(" ");
				if (words[1].equals(name)) {
					str+=st+"\n";
					while(((st=br.readLine())!=null)&&!((st.split(" ")[0].equals("User:"))))
					{
						str+=st+"\n";
					}
				}
				else
				{
					while(((st=br.readLine())!=null)&&!((st.split(" ")[0].equals("User:"))))
					{
						;
					}
				}
			}
		}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return str;
    }
}