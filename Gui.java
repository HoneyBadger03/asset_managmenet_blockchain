//import java.awt.EventQueue;
//import java.awt.Font;
//import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.security.PublicKey;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Gui implements ActionListener{

	private JFrame frame;
	BlockChain bc = new BlockChain();
	private int count=0;
	ArrayList <Wallet> Users=new ArrayList<Wallet>();
	JTextArea textArea = new JTextArea();
	JButton submit = new JButton("Submit");
	JButton report = new JButton("View Reports");
	JComboBox Sender_combo = new JComboBox();
	DefaultComboBoxModel Combo_BoxModel_sender = new DefaultComboBoxModel();
	JComboBox Reciever_combo = new JComboBox();
	DefaultComboBoxModel Combo_BoxModel_reciever = new DefaultComboBoxModel();
	JComboBox User_data_combo = new JComboBox();
	DefaultComboBoxModel Combo_BoxMode_Check = new DefaultComboBoxModel();
	JLabel lblNewLabel_6 = new JLabel("New User");
	JLabel lblNewLabel_5 = new JLabel("Are you a new User?");
	JTextField username = new JTextField();
	JButton create = new JButton("Create");





	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Gui();
			}
		});
	}


	public Gui() {
		initialize_GUI();
	}

	@SuppressWarnings("unchecked")

	public int get_users(String[] ans)
	{
		int i=0;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("users.txt"));
			String st;
			while ((st = br.readLine()) != null)
			{
				ans[i]=st;
				i++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return i;
	}
	public void initialize_GUI() {

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 165, 0));
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		int offset=0;
		JLabel Label_4 = new JLabel("From :");
		Label_4.setFont(new Font("Comic Sans", Font.BOLD, 24));
		Label_4.setBounds(106, 110+offset, 80, 21);
		frame.getContentPane().add(Label_4);

		Sender_combo.setBounds(200, 110+offset, 80, 30);
		frame.getContentPane().add(Sender_combo);

		JLabel Label_2 = new JLabel("To :");
		Label_2.setHorizontalAlignment(SwingConstants.CENTER);offset=10;
		Label_2.setFont(new Font("Comic Sans", Font.BOLD, 24));
		Label_2.setBounds(371, 100+offset, 80, 21);
		frame.getContentPane().add(Label_2);
		offset=0;

		Reciever_combo.setBounds(453, 110+offset, 80, 30);
		frame.getContentPane().add(Reciever_combo);

		JLabel Label1 = new JLabel("Asset Management Blockchain");
		Label1.setFont(new Font("Comic Sans", Font.BOLD, 24));
		Label1.setHorizontalAlignment(SwingConstants.CENTER);offset=0;
		Label1.setBounds(175, 41+offset, 406, 31);
		frame.getContentPane().add(Label1);

		textArea.setBounds(175, 175+offset, 300, 200);
		frame.getContentPane().add(textArea);

		submit.addActionListener(this);
		submit.setBounds(375, 410+offset, 89, 31);
		frame.getContentPane().add(submit);


		JLabel lblNewLabel_1 = new JLabel("Asset Information :");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);offset=0;
		lblNewLabel_1.setFont(new Font("Comic Sans", Font.BOLD, 16));
		lblNewLabel_1.setBounds(20, 220+offset, 150, 45);
		frame.getContentPane().add(lblNewLabel_1);

		report.setBounds(180, 450+offset, 150, 40);
		frame.getContentPane().add(report);
		report.addActionListener(this);

		lblNewLabel_5.setFont(new Font("Comic Sans", Font.BOLD, 20));
		offset=10;
		lblNewLabel_5.setBounds(550, 165+offset, 300, 30);
		frame.getContentPane().add(lblNewLabel_5);

		frame.getContentPane().add(username);
		username.setColumns(15);offset=0;
		username.setBounds(600, 220+offset, 100, 35);


		create.setBounds(600, 270, 100, 30);
		frame.getContentPane().add(create);

		lblNewLabel_6.setBounds(281, 356+offset, 161, 14);
		lblNewLabel_6.setFont(new Font("Serif", Font.BOLD, 16));


		frame.getContentPane().add(lblNewLabel_6);

		User_data_combo.setBounds(180, 400+offset, 110, 40);
		frame.getContentPane().add(User_data_combo);

		create.addActionListener(this);
		String user_list[]=new String[100];
		int size=get_users(user_list);
		for(int i=0;i<=size-1;i++)
		{
			Wallet w = new Wallet(user_list[i]);
			Users.add(w);
			Combo_BoxModel_sender.addElement(user_list[i]);
			Combo_BoxModel_reciever.addElement(user_list[i]);
			Combo_BoxMode_Check.addElement(user_list[i]);
		}
		Sender_combo.setModel(Combo_BoxModel_sender);
		Reciever_combo.setModel(Combo_BoxModel_reciever);
		User_data_combo.setModel(Combo_BoxMode_Check);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==report)
		{
			int i=0;
			for(Wallet user : Users)
			{
				//this check for users and return the report of the user selected
				if(user.name.equals((String) User_data_combo.getSelectedItem()))
				{
					String pr=bc.viewUser(user.publickey,user.name);
					JOptionPane.showMessageDialog(frame,pr);
				}
				else
				{
					i++;
					continue;
				}
			}
			//System.out.println(i);
		}
		if(e.getSource()==submit)
		{
			PublicKey r=Users.get(0).publickey;
			Wallet s=Users.get(0);
			//submits the block and makes the necessary changes
			for(int i=0;i<=Users.size()-1;i++)
			{
				String str=(String) Reciever_combo.getSelectedItem();
				if(Users.get(i).name.equals(str))
				{
					r=Users.get(i).publickey;
				}
				String str2=(String) Sender_combo.getSelectedItem();
				if(Users.get(i).name.equals(str))
				{
					s=Users.get(i);
				}
			}
			Transaction t = s.sendData(r,(String) Sender_combo.getSelectedItem(),(String) Reciever_combo.getSelectedItem(),textArea.getText());
			textArea.setText("");
			bc.undoneTransaction.add(t);
			bc.createBlock();
			//bc.writeJSON(bc,"hello.json");
			JOptionPane.showMessageDialog(frame,"Report sent!");
		}
		if(e.getSource()==create)
		{
			Wallet w=new Wallet(username.getText());
			Users.add(w);
			try {
				FileWriter fileWritter = new FileWriter("users.txt", true);
				BufferedWriter bw = new BufferedWriter(fileWritter);
				bw.write(username.getText()+"\n");
				bw.close();
			}
			catch(Exception E)
			{
				E.printStackTrace();
			}
			Combo_BoxModel_sender.addElement((String)username.getText());
			Sender_combo.setModel(Combo_BoxModel_sender);
			Combo_BoxModel_sender.setSelectedItem(username.getText());
			Combo_BoxModel_reciever.addElement((String)username.getText());
			Reciever_combo.setModel(Combo_BoxModel_reciever);
			Combo_BoxModel_reciever.setSelectedItem(username.getText());
			Combo_BoxMode_Check.addElement((String)username.getText());
			User_data_combo.setModel(Combo_BoxMode_Check);
			Combo_BoxMode_Check.setSelectedItem(username.getText());
			username.setText("");
			JOptionPane.showMessageDialog(frame,"User created!");
		}
	}
}
