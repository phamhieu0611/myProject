package GUIS;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Classes.App;
import Classes.Contact;
import Classes.Filter;
import Classes.Folder;
import Classes.Mail;
import Classes.Sort;
import Interfaces.IFilter;
import Interfaces.IFolder;
import Interfaces.ISort;
import classes.DList;
import classes.SinglyLinkedList;
import interfaces.ILinkedList;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.ScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Choice;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;

import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainHub extends JFrame {

	private JPanel contentPane;
	private JButton composeBtn;
	private JButton nextBtn;
	private JButton prevBtn;
	private JLabel pageLbl;
	
	private JLabel lblSearch;
	private JTextField searchField;
	private Choice sort;
	private JButton refreshBtn;
	private JButton contactBtn;
	private JLabel Username;
	private JLabel lblSelectFolder;
	private JTextField addFolderTxt;
	private Choice folderSelect;
	private JLabel lblFolderName;
	private JButton btnAddFolder;
	private JButton btnDeleteSelectedFolder;
	private JLabel lblName;
	private JLabel bgImage;
	private JLabel lblSort;
	private JButton btnSearch;
	private JButton btnRenameFolder;
	private JTable table;
	private DefaultTableModel modelShowEmail;
	private JButton btnMoveSelectedEmails;
	private JButton btnDeleteSelectedEmails;
	private JButton btnRestoreSelectedEmails;
	private static ILinkedList selectedEmails = new SinglyLinkedList();
	private String sortString = "Newest first";
	private App mainApp = new App();
	private Mail[] pageArray;
	public static int currentPage=1;
	private Folder currentFolder;
	private Sort sortType = new Sort();
	private Filter filterKey;
	private JButton btnNewButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainHub frame = new MainHub();
					frame.setVisible(true);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	}

	/**
	 * Create the frame.
	 */
	public MainHub() {
		setTitle("Login-" + mainApp.currentUser.getname());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1025, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnNewButton = new JButton("Sign out");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home.main(null);
				setVisible(false);
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setBounds(883, 11, 113, 41);
		contentPane.add(btnNewButton);
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(274, 194, 708, 264);
		contentPane.add(scrollPane);
		//First load
		sortType.sortCode = 1;
		currentFolder = new Folder("MailServerData/" + mainApp.currentUser.getemail() + "/Mail Folders/" + mainApp.loadedFolder);
		filterKey.setText("");
		mainApp.setViewingOptions(currentFolder, filterKey, sortType);
		//mainApp.loadEmails();
		
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modelShowEmail = new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
				},
				new String[] {
					"Select", "Priority", "Subject", "From", "To", "Date"
				}
			) {
				Class[] columnTypes = new Class[] {
					Boolean.class, String.class, String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
						true, false, false, false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
			
		};
		table.setModel(modelShowEmail);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(130);
		table.getColumnModel().getColumn(3).setPreferredWidth(140);
		table.getColumnModel().getColumn(4).setPreferredWidth(140);
		table.getColumnModel().getColumn(5).setPreferredWidth(140);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		//Open double clicked email
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		           int index = table.getSelectedRow();
		           if(pageArray[index]!=null) {
		           	File email = new File("MailServerData/" + mainApp.currentUser.getemail() + "/Mail Folders/"+ mainApp.loadedFolder,pageArray[index].getMailName());
		           	File contents = new File(email,"indexfile.txt");
		           	File attachments = new File(email,"Attachments");
		           	Scanner myreader;
		   		
		   			try {	
		   				myreader = new Scanner(contents);
		   				String body = "";
		   				// Read email message
		   				String data=myreader.nextLine();
		   				String[] composeData = new String[7];
		   				composeData[0] = mainApp.currentUser.getemail();
		   				//from
		   				composeData[2] = myreader.nextLine();
		   				//to
		   				composeData[1] = myreader.nextLine();
		   				//priority
		   				composeData[3] = myreader.nextLine();
		   				//subject
		   				composeData[4] = myreader.nextLine();
		   				//skip attachments line
		   				myreader.nextLine();
		   				//body
		   				String add = "";
		   				while(myreader.hasNextLine()) {
		   					 
		   					body += myreader.nextLine();
		   					body += "\n";
		   					
		   				}
		   				composeData[5] = body;
		   				composeData[6] = attachments.getAbsolutePath();
		   				myreader.close();
		   				ComposeMessage.main(composeData);
		   		}		
		   		catch (FileNotFoundException eeee) {
		   			JOptionPane.showMessageDialog(null,"Error.","Error",JOptionPane.ERROR_MESSAGE);
		   		}
		        }
		    }
		    }
		});

		scrollPane.setViewportView(table);
		
		pageArray = (Mail[]) mainApp.listEmails(1);
		
		for (int i = 0 ; i < mainApp.mailsPerPage ; i++) {
			for (int j = 1; j < 6 ; j++) {
				if (pageArray[i]!=null) {
					String add = "";
					if (j==1) {
						add = Mail.getPriority(pageArray[i].getPriority());

					}
					else if (j==2) {
						add = pageArray[i].getSubject();

					}
					else if (j==3) {
						add = pageArray[i].getFrom();

					}
					else if (j==4) {
						add = pageArray[i].getTo() ;

					}
					else {
						long temp = Long.parseLong(pageArray[i].getMailName());
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
						Date resultdate = new Date(temp);
						add = sdf.format(resultdate);
					}
					modelShowEmail.setValueAt(add, i, j);
				}
			}
		}
		
		
		
		lblFolderName = new JLabel("Folder Name");
		lblFolderName.setHorizontalAlignment(SwingConstants.LEFT);
		lblFolderName.setForeground(Color.WHITE);
		lblFolderName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFolderName.setBackground(Color.WHITE);
		lblFolderName.setBounds(12, 383, 83, 31);
		contentPane.add(lblFolderName);
		
		addFolderTxt = new JTextField();
		addFolderTxt.setColumns(10);
		addFolderTxt.setBounds(104, 388, 145, 23);
		contentPane.add(addFolderTxt);
		
		lblSelectFolder = new JLabel("Folder");
		lblSelectFolder.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectFolder.setForeground(Color.WHITE);
		lblSelectFolder.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSelectFolder.setBackground(Color.WHITE);
		lblSelectFolder.setBounds(12, 240, 70, 23);
		contentPane.add(lblSelectFolder);
		
		folderSelect = new Choice();
		folderSelect.setBounds(90, 245, 145, 18);
		contentPane.add(folderSelect);
		
		for (int i = 1; i <= mainApp.folderList.size() ; i++) {
			folderSelect.add((String)mainApp.folderList.get(i));
		}
		folderSelect.select(mainApp.loadedFolder);
		ItemListener updateTable = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				// clear table
				for (int i = 0 ; i < table.getRowCount() ; i++) {
					for (int j = 0 ; j < table.getColumnCount() ; j++) {
						modelShowEmail.setValueAt(null, i, j);
					}
				}
				// Reset page if selection changed
				if (mainApp.loadedFolder.compareTo(folderSelect.getSelectedItem())!=0) {
					currentPage = 1;
					pageLbl.setText("Current Page: " + currentPage);
				}
				
				mainApp.folderIndex = new DList();
				mainApp.loadedFolder = folderSelect.getSelectedItem();
				currentFolder = new Folder("MailServerData/" + mainApp.currentUser.getemail() + "/Mail Folders/" + mainApp.loadedFolder);
				sortString = sort.getSelectedItem();
				sortType.sortCode = Sort.chooseSortCode(sortString);
				mainApp.setViewingOptions(currentFolder, filterKey, sortType);
				
				//Show restore button if loaded folder is Trash folder and hide it otherwise
				if (mainApp.loadedFolder.compareTo("Trash")==0) {
					btnRestoreSelectedEmails.setVisible(true);
				}
				else {
					btnRestoreSelectedEmails.setVisible(false);
				}
				
				if (mainApp.loadedFolder.compareTo("Draft") != 0) {
					btnMoveSelectedEmails.setVisible(true);
				}
				else {
					btnMoveSelectedEmails.setVisible(false);
				}
				
				
				//mainApp.loadEmails();
				pageArray = (Mail[]) mainApp.listEmails(currentPage);
				
				for (int i = 0 ; i < mainApp.mailsPerPage ; i++) {
					for (int j = 1; j < 6 ; j++) {
						if (pageArray[i]!=null) {
							String add = "";
							if (j==1) {
								add = Mail.getPriority(pageArray[i].getPriority());

							}
							else if (j==2) {
								add = pageArray[i].getSubject();

							}
							else if (j==3) {
								add = pageArray[i].getFrom();

							}
							else if (j==4) {
								add = pageArray[i].getTo() ;

							}
							else {
								long temp = Long.parseLong(pageArray[i].getMailName());
								SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
								Date resultdate = new Date(temp);
								add = sdf.format(resultdate);
							}
							modelShowEmail.setValueAt(add, i, j);
						}
					}
				}
				
					
			}};
			folderSelect.addItemListener(updateTable);
		
		Username = new JLabel("Username");
		Username.setHorizontalAlignment(SwingConstants.LEFT);
		Username.setForeground(Color.WHITE);
		Username.setFont(new Font("Tahoma", Font.BOLD, 20));
		Username.setBackground(Color.WHITE);
		Username.setBounds(12, 16, 121, 23);
		contentPane.add(Username);
		
		
		
		composeBtn = new JButton("New Message");
		composeBtn.setForeground(Color.WHITE);
		composeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] data = new String[2];
				data[0] = mainApp.currentUser.getemail();
				data[1] = null;
				ComposeMessage.main(data);
				
			}
		});
		composeBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		composeBtn.setBackground(SystemColor.textHighlight);
		composeBtn.setBounds(53, 143, 144, 42);
		contentPane.add(composeBtn);
		
		nextBtn = new JButton("-->");
		nextBtn.setForeground(Color.WHITE);
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentPage < mainApp.pagesPerFolder) {
					currentPage ++;
					pageLbl.setText("Current Page: " + currentPage);
					updateTable.itemStateChanged(null);
				}
			}
		});
		nextBtn.setBackground(SystemColor.textHighlight);
		nextBtn.setBounds(903, 523, 62, 23);
		contentPane.add(nextBtn);
		
		prevBtn = new JButton("<--");
		prevBtn.setForeground(Color.WHITE);
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentPage>1) {
					currentPage--;
					pageLbl.setText("Current Page: " + currentPage);
					updateTable.itemStateChanged(null);
				}
				
			}
		});
		prevBtn.setBackground(SystemColor.textHighlight);
		prevBtn.setBounds(838, 523, 62, 23);
		contentPane.add(prevBtn);
		
		pageLbl = new JLabel("Current Page: " + currentPage);
		pageLbl.setForeground(Color.WHITE);
		pageLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		pageLbl.setBackground(Color.WHITE);
		pageLbl.setBounds(828, 490, 154, 23);
		contentPane.add(pageLbl);
		
		lblSearch = new JLabel("Search:");
		lblSearch.setHorizontalAlignment(SwingConstants.LEFT);
		lblSearch.setForeground(Color.WHITE);
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSearch.setBackground(Color.WHITE);
		lblSearch.setBounds(292, 47, 70, 23);
		contentPane.add(lblSearch);
		
		searchField = new JTextField();
		searchField.setBounds(372, 40, 334, 42);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		lblSort = new JLabel("Sort:");
		lblSort.setHorizontalAlignment(SwingConstants.CENTER);
		lblSort.setForeground(Color.WHITE);
		lblSort.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSort.setBackground(Color.WHITE);
		lblSort.setBounds(360, 125, 70, 23);
		contentPane.add(lblSort);
		
		sort = new Choice();
		sort.add("Newest first");
		sort.add("Oldest first");
		sort.add("Alphabetical Order");
		sort.add("Reverse Alphabetical Order");
		sort.add("Highest Priority first");
		sort.add("Lowest Priority first");
		sort.setBounds(436, 130, 267, 40);
		ItemListener updateSort = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//Sort 
				sortString = sort.getSelectedItem();
				//Method convert sortString to integer
				sortType.sortCode = Sort.chooseSortCode(sortString);
				mainApp.setViewingOptions(currentFolder, filterKey, sortType);
				updateTable.itemStateChanged(null);
					
			}};
			sort.addItemListener(updateSort);
		contentPane.add(sort);
		
		refreshBtn = new JButton("");
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Search
				String searchText = searchField.getText();
				filterKey.setText(searchText);
				//Sort 
				sortString = sort.getSelectedItem();
				//Method convert sortString to integer
				sortType.sortCode = Sort.chooseSortCode(sortString);
				mainApp.setViewingOptions(currentFolder, filterKey, sortType);
				updateTable.itemStateChanged(null);
			}
		});
		refreshBtn.setIcon(new ImageIcon(getClass().getClassLoader().getResource("re.png")));
		refreshBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		refreshBtn.setBackground(Color.WHITE);
		refreshBtn.setBounds(918, 125, 54, 40);
		contentPane.add(refreshBtn);
		
		contactBtn = new JButton("Contacts");
		contactBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] data = new String[1];
				data[0] = mainApp.currentUser.getemail();
				Contacts.main(data);
				
			}
		});
		contactBtn.setForeground(Color.WHITE);
		contactBtn.setBounds(883, 62, 113, 42);
		contentPane.add(contactBtn);
		contactBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		contactBtn.setBackground(SystemColor.textHighlight);
		
		lblName = new JLabel(mainApp.currentUser.getname());
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Agency FB", Font.BOLD, 28));
		lblName.setForeground(new Color(240, 255, 255));
		lblName.setBounds(22, 40, 227, 49);
		contentPane.add(lblName);
		
		
		btnAddFolder = new JButton("Add Folder");
		btnAddFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean acceptableFolder = true;
				String folderName = addFolderTxt.getText();
				int folderNum = folderSelect.countItems();
				if(folderName.isBlank()) {
					JOptionPane.showMessageDialog(null,"The folder name field is blank.","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				for (int i = 0 ; i < folderNum ; i++) {
					String folder = folderSelect.getItem(i);
					if (folderName.compareTo(folder)==0) {
						JOptionPane.showMessageDialog(null,"The specified folder name already exists.","Error",JOptionPane.ERROR_MESSAGE);
						acceptableFolder = false;
						break;
					}
				}
				if (acceptableFolder == true) {
					int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to create the selected folder\""+folderName+"\"?","Confirmation",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION) {
						File newFolder = new File("MailServerData/" + mainApp.currentUser.getemail() + "/Mail Folders" , folderName);
						newFolder.mkdir();
						File mailsFile = new File(newFolder, "mailsfile.txt");
						try {
							mailsFile.createNewFile();
						} catch (IOException ex) {
							
						}
						folderSelect.add(folderName);
						folderSelect.select(folderName);
						addFolderTxt.setText(null);
						updateTable.itemStateChanged(null);
					}
				}
			}
				
		});
		btnAddFolder.setForeground(Color.WHITE);
		btnAddFolder.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnAddFolder.setBackground(SystemColor.textHighlight);
		btnAddFolder.setBounds(142, 329, 122, 29);
		contentPane.add(btnAddFolder);
		
		btnDeleteSelectedFolder = new JButton("Delete Folder");
		btnDeleteSelectedFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedFolder = folderSelect.getSelectedItem();
				if (selectedFolder.compareTo("Inbox")==0 || selectedFolder.compareTo("Draft")==0  || selectedFolder.compareTo("Sent")==0 || selectedFolder.compareTo("Trash")==0) {
					JOptionPane.showMessageDialog(null,"Cannot delete the essential folder \""+selectedFolder+"\".","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete the selected folder\""+selectedFolder+"\"?","Confirmation",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION) {
						folderSelect.remove(selectedFolder);
						File deleted = new File ("MailServerData/" + mainApp.currentUser.getemail() + "/Mail Folders/" + selectedFolder);
						Folder.deleteFolder(deleted);
						folderSelect.select("Inbox");
						updateTable.itemStateChanged(null);
					}
					
				}
			}
		});
		btnDeleteSelectedFolder.setForeground(Color.WHITE);
		btnDeleteSelectedFolder.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnDeleteSelectedFolder.setBackground(SystemColor.textHighlight);
		btnDeleteSelectedFolder.setBounds(73, 285, 116, 23);
		contentPane.add(btnDeleteSelectedFolder);
		
		btnRenameFolder = new JButton("Rename Folder");
		btnRenameFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean acceptableFolder = true;
				String folderName = addFolderTxt.getText();
				String selectedFolder = folderSelect.getSelectedItem();
				if(folderName.isBlank()) {
					JOptionPane.showMessageDialog(null,"The folder name field is blank.","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (selectedFolder.compareTo("Inbox")==0 || selectedFolder.compareTo("Draft")==0  || selectedFolder.compareTo("Sent")==0 || selectedFolder.compareTo("Trash")==0) {
					JOptionPane.showMessageDialog(null,"Cannot rename the essential folder \""+selectedFolder+"\".","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					int folderNum = folderSelect.countItems();
					for (int i = 0 ; i < folderNum ; i++) {
						String folder = folderSelect.getItem(i);
						if (folderName.compareTo(folder)==0) {
							JOptionPane.showMessageDialog(null,"The specified folder name already exists.","Error",JOptionPane.ERROR_MESSAGE);
							acceptableFolder = false;
							break;
						}
					}
					if (acceptableFolder == true) {
						int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to rename the selected folder\""+selectedFolder+"\" to\""+folderName+"\"?","Confirmation",JOptionPane.YES_NO_OPTION);
								if(dialogResult == JOptionPane.YES_OPTION) {
									int index = folderSelect.getSelectedIndex();
									folderSelect.remove(index);
									folderSelect.insert(folderName, index);
									File old = new File ("MailServerData/" + mainApp.currentUser.getemail() + "/Mail Folders/" + selectedFolder);
									File renamed = new File ("MailServerData/" + mainApp.currentUser.getemail() + "/Mail Folders/" + folderName);
									old.renameTo(renamed);
								}
								addFolderTxt.setText(null);
								folderSelect.select(folderName);
								updateTable.itemStateChanged(null);
					}
				}
			}
				

		});
		btnRenameFolder.setForeground(Color.WHITE);
		btnRenameFolder.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnRenameFolder.setBackground(SystemColor.textHighlight);
		btnRenameFolder.setBounds(14, 329, 121, 29);
		contentPane.add(btnRenameFolder);
		
		btnMoveSelectedEmails = new JButton("Move Emails");
		btnMoveSelectedEmails.setForeground(Color.WHITE);
		btnMoveSelectedEmails.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnMoveSelectedEmails.setBackground(SystemColor.textHighlight);
		btnMoveSelectedEmails.setBounds(288, 494, 131, 42);
		btnMoveSelectedEmails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				selectedEmails.clear();
				for (int i = 0 ; i < mainApp.mailsPerPage ; i++) {
					Object tmp = modelShowEmail.getValueAt(i, 0);
					if (tmp != null && (boolean)tmp) {
						try {
							boolean select = (boolean)tmp;
							File selectedFile = new File ("MailServerData/" + mainApp.currentUser.getemail() + "/Mail Folders/"+ mainApp.loadedFolder +"/" + pageArray[i].getMailName() );
							selectedEmails.add(selectedFile);
						}
						catch (Exception eeeeee) {
							JOptionPane.showMessageDialog(null,"Can not choose an empty row.","Error",JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
				if(!selectedEmails.isEmpty()) {
					int acceptableMoves = folderSelect.countItems()-2;
					String[] folders = new String[acceptableMoves];
					for (int i = 0, j = 0 ; i < acceptableMoves && j < folderSelect.countItems() ; j++) {
						String name = (String)folderSelect.getItem(j);
						if (name.compareTo(mainApp.loadedFolder)!=0 && name.compareTo("Draft") != 0) {
							folders[i] = name;
							i++;
						}
					}
					String dest = (String)JOptionPane.showInputDialog(null,"Move the selected email(s) to", "Select Folder",JOptionPane.INFORMATION_MESSAGE,null, folders, folders[0]);

					
					if ((dest != null) && (dest.length() > 0)) {
					    Folder destinationFolder = new Folder("MailServerData/" + mainApp.currentUser.getemail() + "/Mail Folders/"+ dest);

					    mainApp.moveEmails(selectedEmails, destinationFolder);
					}
					updateTable.itemStateChanged(null);
					
				}
				else {
					JOptionPane.showMessageDialog(null,"No emails selected.","Error",JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		contentPane.add(btnMoveSelectedEmails);
		
		btnDeleteSelectedEmails = new JButton("Delete Emails");
		btnDeleteSelectedEmails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					selectedEmails.clear();
					for (int i = 0 ; i < mainApp.mailsPerPage ; i++) {
						Object tmp = modelShowEmail.getValueAt(i, 0);
						if (tmp != null && (boolean)tmp) {
							try {
								boolean select = (boolean)tmp;
								File selectedFile = new File ("MailServerData/" + mainApp.currentUser.getemail() + "/Mail Folders/"+ mainApp.loadedFolder +"/" + pageArray[i].getMailName() );
								selectedEmails.add(selectedFile);
							}
							catch (Exception eeeee) {
								JOptionPane.showMessageDialog(null,"Can not choose an empty row.","Error",JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
					}
					if(!selectedEmails.isEmpty()) {
						int dialogResult;
						if (mainApp.loadedFolder.compareTo("Trash")!=0){
							dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete the selected email(s)?","Confirmation",JOptionPane.YES_NO_OPTION);
							
						}
						else {
							dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to permanently delete the selected email(s)?","Confirmation",JOptionPane.YES_NO_OPTION);
						}
						if(dialogResult == JOptionPane.YES_OPTION) {

							mainApp.deleteEmails(selectedEmails);
							updateTable.itemStateChanged(null);
						}
					}
					else {
						JOptionPane.showMessageDialog(null,"No emails selected.","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			
		});
		btnDeleteSelectedEmails.setForeground(Color.WHITE);
		btnDeleteSelectedEmails.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnDeleteSelectedEmails.setBackground(SystemColor.textHighlight);
		btnDeleteSelectedEmails.setBounds(642, 494, 131, 42);
		contentPane.add(btnDeleteSelectedEmails);
		
		btnRestoreSelectedEmails = new JButton("Restore Emails");
		btnRestoreSelectedEmails.setVisible(false);
		btnRestoreSelectedEmails.setForeground(Color.WHITE);
		btnRestoreSelectedEmails.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnRestoreSelectedEmails.setBackground(SystemColor.textHighlight);
		btnRestoreSelectedEmails.setBounds(463, 494, 131, 42);
		btnRestoreSelectedEmails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				selectedEmails.clear();
				for (int i = 0 ; i < mainApp.mailsPerPage ; i++) {
					Object tmp = modelShowEmail.getValueAt(i, 0);
					if (tmp != null && (boolean)tmp) {
						try {
							boolean select = (boolean)tmp;
							File selectedFile = new File ("MailServerData/" + mainApp.currentUser.getemail() + "/Mail Folders/"+ mainApp.loadedFolder +"/" + pageArray[i].getMailName() );
							selectedEmails.add(selectedFile);
						}
						catch (Exception eeeee) {
							JOptionPane.showMessageDialog(null,"Can not choose an empty row.","Error",JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
				if(!selectedEmails.isEmpty()) {
					int dialogResult;
					dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to restore the selected email(s)?","Confirmation",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION) {
						mainApp.restoreEmails(selectedEmails);
						updateTable.itemStateChanged(null);
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"No emails selected.","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
				
		});
		contentPane.add(btnRestoreSelectedEmails);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchText = searchField.getText();
				filterKey.setText(searchText);
				mainApp.setViewingOptions(currentFolder, filterKey, sortType);
				updateTable.itemStateChanged(null);
			}
		});
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnSearch.setBackground(SystemColor.textHighlight);
		btnSearch.setBounds(705, 40, 83, 42);
		contentPane.add(btnSearch);
		
		bgImage = new JLabel("");
		bgImage.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img-email-worthopening.png")));
		bgImage.setBounds(0, -8, 1043, 601);
		contentPane.add(bgImage);
	}
}
