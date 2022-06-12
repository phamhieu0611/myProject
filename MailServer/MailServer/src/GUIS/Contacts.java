package GUIS;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import Classes.Contact;
import Classes.Folder;
import Classes.Mail;
import Classes.Sort;
import classes.DList;

import javax.swing.JLabel;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.Choice;



public class Contacts extends JFrame {

	private JPanel contentPane;
	private JButton btnAddContact;
	private JTextField name;
	private static DefaultListModel DLM=new DefaultListModel();
	private static DefaultListModel DLM2=new DefaultListModel();
	private static DefaultListModel DLM3=new DefaultListModel();
	private static JList Emails=new JList();
	private static JList Names=new JList();
	private static String accountName;
	private JTextField email;
	private JTextField extraEmail;
	private JLabel showcontact;
	private JLabel showmail;
	private JTextField searchContact;
	private Choice sort;
	private JButton btnSearch;
	private ItemListener changeSort;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Contacts frame = new Contacts();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	accountName=args[0];
	DLM.clear();
	DLM2.clear();
	File myobj =new File("MailServerData/"+accountName+"/contacts");
	String[] names=myobj.list();
	
	for(String name : names) {
		String filename = name.replaceAll(".txt","");
		DLM.addElement(filename);	
		}
	Names.setModel(DLM);
	}

	/**
	 * Create the frame.
	 */
	
	
	
	class JTextFieldLimit extends PlainDocument {
		  private int limit;
		  JTextFieldLimit(int limit) {
		    super();
		    this.limit = limit;
		  }

		  JTextFieldLimit(int limit, boolean upper) {
		    super();
		    this.limit = limit;
		  }

		  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		    if (str == null)
		      return;

		    if ((getLength() + str.length()) <= limit) {
		      super.insertString(offset, str, attr);
		    }
		  }
		}
	
	
	void refreshList() {
		DLM.clear();
		DLM2.clear();
		File myobj =new File("MailServerData/"+accountName+"/contacts");
		String[] names=myobj.list();
		
		for(String name : names) {
			String filename = name.replaceAll(".txt","");
			DLM.addElement(filename);	
			}
			showcontact.setText("");
			showmail.setText("");
			sort.select("Alphabetical Order");
			searchContact.setText("");
	}
	public Contacts() {
		setTitle("My Contacts");
		
		


		
	    ListSelectionListener listSelectionListener1 = new ListSelectionListener() {
		       
			@Override
			public void valueChanged(ListSelectionEvent e) {
			if(Emails.getSelectedValue()!=null) {	
				showmail.setText((String)Emails.getSelectedValue());
			}}
	    };
			Emails.addListSelectionListener(listSelectionListener1);
		
		
	    ListSelectionListener listSelectionListener = new ListSelectionListener() {
	       
			@Override
			public void valueChanged(ListSelectionEvent e) {
			if(Names.getSelectedValue()!=null) {	
				try {
					showcontact.setText("");
					String[] con =((String) Names.getSelectedValue()).split("\\s+");	
					for(int i=0;i<con.length-1;i++) {
						showcontact.setText(showcontact.getText()+" "+con[i]);
					}
					DLM2.clear();	
					String currSelection=(String)Names.getSelectedValue();
					File myobj =new File("MailServerData/"+accountName+"/Contacts/"+currSelection+".txt");
					Scanner myreader;
					myreader = new Scanner(myobj);
					while(myreader.hasNextLine()) {
						String data=myreader.nextLine();
						DLM2.addElement(data);		
					}
					Emails.setModel(DLM2);
					myreader.close();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null,"Error.","Error",JOptionPane.ERROR_MESSAGE);
				}

			}
			
			}};
			Names.addListSelectionListener(listSelectionListener);
		
		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 856, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton RemoveContact = new JButton("Remove Email");
		RemoveContact.setForeground(Color.WHITE);
		RemoveContact.setBackground(SystemColor.textHighlight);
		RemoveContact.setFont(new Font("Tahoma", Font.PLAIN, 18));
		RemoveContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(Emails.getSelectedValue()==null)) {
				int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete "+(String) Emails.getSelectedValue()+ " Contact","Confirmation",JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION) {
					File f = new File("MailServerData/"+accountName+"/Contacts/"+(String)Names.getSelectedValue()+".txt");
					Folder.deleteData(f,(String)Emails.getSelectedValue(),0,false,null);
					DLM2.removeElement((String) Emails.getSelectedValue());
					showmail.setText("");
					JOptionPane.showMessageDialog(null,"Email Deleted Successfully !");
				}
				}
				else {
					if(Names.getSelectedValue()==null) {
					JOptionPane.showMessageDialog(null,"Please Select a First !");
					}else {
						JOptionPane.showMessageDialog(null,"Please Select an E-mail !");
					}
				}
				
			}
		});
		
		JLabel lblUserName_1_1 = new JLabel("E-Mails");
		lblUserName_1_1.setForeground(Color.WHITE);
		lblUserName_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUserName_1_1.setBounds(212, 91, 147, 38);
		contentPane.add(lblUserName_1_1);
		
		JLabel lblContacts_1 = new JLabel("Contacts");
		lblContacts_1.setForeground(Color.WHITE);
		lblContacts_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblContacts_1.setBounds(10, 91, 147, 38);
		contentPane.add(lblContacts_1);
		
		JButton btnRefreshList = new JButton("Refresh");
		btnRefreshList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			refreshList();
			}
		});
		btnRefreshList.setForeground(Color.WHITE);
		btnRefreshList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRefreshList.setBackground(SystemColor.textHighlight);
		btnRefreshList.setBounds(721, 15, 102, 38);
		contentPane.add(btnRefreshList);
		
		JLabel lblExtraEmailName = new JLabel("Extra Email Name");
		lblExtraEmailName.setForeground(Color.WHITE);
		lblExtraEmailName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblExtraEmailName.setBounds(404, 349, 147, 43);
		contentPane.add(lblExtraEmailName);
		
		JLabel lblCurrentSelectedContact_1_1_1 = new JLabel("To");
		lblCurrentSelectedContact_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentSelectedContact_1_1_1.setForeground(Color.YELLOW);
		lblCurrentSelectedContact_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCurrentSelectedContact_1_1_1.setBounds(575, 274, 35, 43);
		contentPane.add(lblCurrentSelectedContact_1_1_1);
		
		JLabel lblCurrentSelectedContact_1_1 = new JLabel("To");
		lblCurrentSelectedContact_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentSelectedContact_1_1.setForeground(Color.YELLOW);
		lblCurrentSelectedContact_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCurrentSelectedContact_1_1.setBounds(575, 424, 35, 43);
		contentPane.add(lblCurrentSelectedContact_1_1);
		
		showmail = new JLabel("");
		showmail.setForeground(Color.YELLOW);
		showmail.setFont(new Font("Tahoma", Font.BOLD, 20));
		showmail.setBounds(612, 424, 230, 43);
		contentPane.add(showmail);
		
		showcontact = new JLabel("");
		showcontact.setForeground(Color.YELLOW);
		showcontact.setFont(new Font("Tahoma", Font.BOLD, 20));
		showcontact.setBounds(620, 274, 222, 43);
		contentPane.add(showcontact);
		
		JButton btnRemoveContact = new JButton("Remove Contact");
		btnRemoveContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(Names.getSelectedValue()==null)) {
				String[] con =((String) Names.getSelectedValue()).split("\\s+");
				int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete "+con[0]+ " Contact","Confirmation",JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION) {
					
					File f = new File("MailServerData/"+accountName+"/Contacts/"+(String)Names.getSelectedValue()+".txt");
					f.delete();
					DLM.removeElement((String)Names.getSelectedValue());
					DLM2.clear();
					showcontact.setText("");
					JOptionPane.showMessageDialog(null,"Contact Deleted Succesfully !");
					
				}
				}
				else {
					JOptionPane.showMessageDialog(null,"Please Select a Contact !");
				}
				changeSort.itemStateChanged(null);
			}
		});
		btnRemoveContact.setForeground(Color.WHITE);
		btnRemoveContact.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRemoveContact.setBackground(SystemColor.textHighlight);
		btnRemoveContact.setBounds(10, 424, 171, 44);
		contentPane.add(btnRemoveContact);
		Names.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		Names.setBounds(10, 130, 171, 284);
		contentPane.add(Names);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEmail.setBounds(648, 89 ,147, 43);
		contentPane.add(lblEmail);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setForeground(Color.WHITE);
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUserName.setBounds(416, 91,147, 38);
		contentPane.add(lblUserName);
		
		extraEmail = new JTextField();
		extraEmail.setColumns(10);
		extraEmail.setBounds(561, 354, 251, 38);
		contentPane.add(extraEmail);
		
		JButton btnAddExtraEamil = new JButton("Add Extra Email");
		btnAddExtraEamil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String extramail=extraEmail.getText();
			if(!(extramail.isBlank())) {
				if(Mail.checkmail(extramail)) {
					if(Contact.existEmail(extramail)) {
						if(!(Contact.existExtraEmail(accountName,(String)Names.getSelectedValue(),extramail))) {
							try {
								DLM2.addElement(extramail);
								File f=new File("MailServerData/"+accountName+"/Contacts/"+(String)Names.getSelectedValue()+".txt");
								FileWriter wrt = new FileWriter(f, true);
								wrt.write(extramail+"\n");
								wrt.close();
								JOptionPane.showMessageDialog(null,"Email added successfully.");
								extraEmail.setText(null);
								
							} catch (FileNotFoundException e1) {
								
							} catch (IOException e1) {
								
							}
							
						}else {
							 JOptionPane.showMessageDialog(null,"Email already exists.","Error",JOptionPane.ERROR_MESSAGE);
						}	
					}else {
						JOptionPane.showMessageDialog(null,"This email does not exist on the mail server.","Error",JOptionPane.ERROR_MESSAGE);
					}
					}else {
						JOptionPane.showMessageDialog(null,"Invalid email format.","Error",JOptionPane.ERROR_MESSAGE);
					}
				
			}else {
				JOptionPane.showMessageDialog(null,"Please fill Extra Email field.","Error",JOptionPane.ERROR_MESSAGE);
			}
			
			}
			
		});
		btnAddExtraEamil.setForeground(Color.WHITE);
		btnAddExtraEamil.setBackground(SystemColor.textHighlight);
		btnAddExtraEamil.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAddExtraEamil.setBounds(399, 272, 166, 48);
		contentPane.add(btnAddExtraEamil);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(648, 130, 194, 38);
		contentPane.add(email);
		
		
		Emails.setBounds(212, 130, 166, 284);
		contentPane.add(Emails);
		
		JButton SendMessage = new JButton("Send Message");
		SendMessage.setForeground(Color.WHITE);
		SendMessage.setBackground(SystemColor.textHighlight);
		SendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] currentEmail = new String[2];
				currentEmail[0] = accountName;
				currentEmail[1]=(String) Emails.getSelectedValue();
				ComposeMessage.main(currentEmail);
				
			}
		});
		SendMessage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		SendMessage.setBounds(400, 424, 166, 44);
		contentPane.add(SendMessage);
		
		name = new JTextField(15);
		name.setDocument(new JTextFieldLimit(20));
		name.setBounds(416, 130, 194, 38);
		contentPane.add(name);
		name.setColumns(10);
		RemoveContact.setBounds(212, 424, 166, 44);
		contentPane.add(RemoveContact);
		
		btnAddContact = new JButton("Add Contact");
		btnAddContact.setForeground(Color.WHITE);
		btnAddContact.setBackground(SystemColor.textHighlight);
		btnAddContact.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date date=new Date();
				long curr=date.getTime();
				String newName=name.getText()+"                                    "+String.valueOf(curr);
				String newEmail=email.getText();
				if(!(name.getText().isBlank()) && !(email.getText().isBlank())) {
				//if(Pattern.matches("^[a-zA-Z ]+$",name.getText())) {
					if(!(name.getText().contains(".txt"))) {
					if(Mail.checkmail(newEmail)) {
					if(Contact.existEmail(newEmail)) {
						if(Contact.addContact(accountName,newName,newEmail)) {
							
						//	newName=Contact.toDate(newName);
							DLM.addElement(newName);
							Names.setModel(DLM);
							JOptionPane.showMessageDialog(null,"Contact added successfully.");
							name.setText("");
							email.setText("");
				}else {
					JOptionPane.showMessageDialog(null,"Error ddding contact","Error",JOptionPane.ERROR_MESSAGE);
				}
				}
				else {
					JOptionPane.showMessageDialog(null,"This email does not exist on the mail server.","Error",JOptionPane.ERROR_MESSAGE); 	
				}
				}
				else {
					 JOptionPane.showMessageDialog(null,"Invalid email format.","Error",JOptionPane.ERROR_MESSAGE);
				}
			}else {
				 JOptionPane.showMessageDialog(null,"\".txt\" is probhibted in contact names.","Error",JOptionPane.ERROR_MESSAGE);
			
			}}
			else {
				 JOptionPane.showMessageDialog(null,"Please fill both Username and Email fields.","Error",JOptionPane.ERROR_MESSAGE);
			}
				changeSort.itemStateChanged(null);
			}
		});
		btnAddContact.setBounds(532, 195, 194, 44);
		contentPane.add(btnAddContact);
		
		JLabel lblSearch = new JLabel("Search:");
		lblSearch.setForeground(Color.WHITE);
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSearch.setBounds(13, 10, 171, 43);
		contentPane.add(lblSearch);
				
	searchContact = new JTextField();
	searchContact.setColumns(10);
	searchContact.setBounds(98, 20, 239, 32);
	contentPane.add(searchContact);
					
	btnSearch = new JButton("Search");
	btnSearch.setForeground(Color.WHITE);
	btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnSearch.setBackground(SystemColor.textHighlight);
	btnSearch.setBounds(335, 21, 92, 32);
	btnSearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			boolean found=false;
			if(!(searchContact.getText().isBlank())) {
			String search = searchContact.getText();
			search.replaceAll(" ","");
			File myobj =new File("MailServerData/"+accountName+"/contacts");
			String[] contacts = myobj.list();
			if (contacts.length!=0) {
				DLM.clear();
				DLM2.clear();
				for (int i = 0 ; i < contacts.length ; i ++) {
					contacts[i]=contacts[i].replaceAll(".txt","");
					String name = contacts[i];
					String[] con =(name).split("\\s+");
					name="";
					for(int j =0 ; j < con.length-1;j++){
						name+=con[j];
					}
					if (name.contains(search)==true) {
						found=true;
						DLM.addElement(contacts[i]);
					}
				}
				if(!found) {
					refreshList();
					JOptionPane.showMessageDialog(null,"No result Found !");
				}
				
			}

			changeSort.itemStateChanged(null);
			}else {
				refreshList();
				JOptionPane.showMessageDialog(null,"Please Fill Serach Field");
				
			}
			}
	});
	
	contentPane.add(btnSearch);
								
	JLabel lblSort = new JLabel(" Sort:");
	lblSort.setForeground(Color.WHITE);
	lblSort.setFont(new Font("Tahoma", Font.BOLD, 20));
	lblSort.setBounds(10, 52, 85, 43);
	contentPane.add(lblSort);
										
	sort = new Choice();
	sort.setBounds(98, 67, 194, 20);
	sort.add("Alphabetical Order");
	sort.add("Reverse Alphabetical Order");
	changeSort = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			String[] contacts = new String[DLM.getSize()];
			for (int i = 0 ; i < DLM.getSize() ; i++) {
				contacts[i] = (String)DLM.get(i);
			}
			if (contacts.length!=0) {

				Sort.quickSort(contacts, 0);
				DLM.clear();
				if (sort.getSelectedItem().compareTo("Alphabetical Order")==0) {
					for (String name: contacts) {
						DLM.addElement(name);
					}
				}
				else {
					for (String name: contacts) {
						DLM.add(0, name);
					}
				}
			}
			
				
		}};
		sort.addItemListener(changeSort);
		
	contentPane.add(sort);
										
	
	JLabel LblImage = new JLabel("");
	LblImage.setForeground(Color.WHITE);
	LblImage.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images.jpg")));
	LblImage.setBounds(0, 0, 852, 502);
	contentPane.add(LblImage);
										
	contentPane.add(LblImage);
											
	contentPane.add(LblImage);
	
	
	
	
	}
}
