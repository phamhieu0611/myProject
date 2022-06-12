package Classes;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Interfaces.IMail;
import classes.LinkedBased;
import interfaces.ILinkedList;
import interfaces.IQueue;

public class Mail implements IMail{
	final static int lines = 5;
	Boolean Draft;
	int prior;
	IQueue mails;
	String subject;
	ILinkedList files;
	String body;
	String currentmail;
	
	String mailName;
	String from;
	String to;
	
	
	
	public static int getlines() {
		return lines;
	}
	
	
	
	
	
	public static Boolean checkmail(String mail) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			if(!mail.matches(regex)){
				return false;
			}
		return true;
	}
	
	public static int choosePriority(String prio) {
		if (prio.compareTo("Normal") == 0) {
			return 3;
		} else if (prio.compareTo("Very High") == 0) {
			return 1;
		} else if (prio.compareTo("High") == 0) {
			return 2;
		} else if (prio.compareTo("Low") == 0) {
			return 4;
		} else {
			return 5;
		}
	}
	
	public static String getPriority(int prio) {
		if (prio == 3) {
			return "Normal";
		} else if (prio == 1) {
			return "Very High";
		} else if (prio == 2) {
			return "High";
		} else if (prio == 4) {
			return "Low";
		} else {
			return "Very Low";
		}
	}
	
	/**
	 * 
	 */
	public IQueue checkEmailList(String toFieldInput) {
		File existmails = new File("MailServerData");
		String[] existMails = existmails.list();
		Sort.quickSort(existMails, 0);
		IQueue emailList = new LinkedBased();
		String hold = "";
		for (int i = 0; i < toFieldInput.length(); i++) {
			if (toFieldInput.charAt(i) != ' ') {
				hold += toFieldInput.charAt(i);
			}
			else {
				if (i==0) {
					JOptionPane.showMessageDialog(null,"Invalid format for list of recipients!","Error",JOptionPane.ERROR_MESSAGE);
					return null;
				}
				else {
					if (!checkmail(hold)) {
						JOptionPane.showMessageDialog(null,"Invalid Email format!","Error",JOptionPane.ERROR_MESSAGE);
						return null;
					} else {
						if (Filter.binarySearch(existMails, hold) == -1) {
							JOptionPane.showMessageDialog(null, hold + " does not exist!","Error",JOptionPane.ERROR_MESSAGE);
							return null;
						}
						if (hold.compareTo(currentmail) == 0) {
							JOptionPane.showMessageDialog(null, "Can not send email to yourself.","Error",JOptionPane.ERROR_MESSAGE);
							return null;
						}
						emailList.enqueue(hold);
						hold = "";
					}
				}
			}
		}
		if (!hold.isBlank()) {
			if (!checkmail(hold)) {
				JOptionPane.showMessageDialog(null,"Invalid Email format!","Error",JOptionPane.ERROR_MESSAGE);
				return null;
			} else {
				if (Filter.binarySearch(existMails, hold) == -1) {
					JOptionPane.showMessageDialog(null, hold + " does not exist!","Error",JOptionPane.ERROR_MESSAGE);
					return null;
				}
				if (hold.compareTo(currentmail) == 0) {
					JOptionPane.showMessageDialog(null, "Can not send email to yourself.","Error",JOptionPane.ERROR_MESSAGE);
					return null;
				}
				emailList.enqueue(hold);
				hold = "";
			}
		}
		if (emailList.size() == 0) {
			JOptionPane.showMessageDialog(null,"Recipients line is empty.","Error",JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return emailList;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static Boolean mkmail(File file) {
		file.mkdir();
		File files = new File(file, "Attachments");
		files.mkdir();
		files = new File (file, "indexfile.txt");
		try {
			files.createNewFile();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param trash
	 * @param target
	 * @param auto
	 * @return
	 */
	public static Boolean deleteFromTrash(File trash, String target, Boolean auto) {
		final long days30 = (long) 2.592e9;
		File trashfile = new File(trash, "Trashfile.txt");
		File temp2 = new File(trash, "temp2.txt");
		Long currenttime = System.currentTimeMillis();
		try {
			Scanner scan = new Scanner(trashfile);
			FileWriter writetemp = new FileWriter(temp2, true);
			while(scan.hasNext()) {
				String input = scan.nextLine();
				String[] arr = input.split(" ");
				if (auto) {
					if (currenttime - Long.parseLong(arr[0]) > days30) {
						File d = new File(trash, arr[1]);
						File m = new File(trash, "mailsfile.txt");
						Folder.deleteFolder(d);
						Folder.deleteData(m, arr[1], lines, false, null);
					} else {
						writetemp.write(input + '\n');
					}
				} else {
					if (arr[1].compareTo(target) != 0) {
						writetemp.write(input + '\n');
					}
				}
			}
			scan.close();
			writetemp.close();
			String P = trashfile.getAbsolutePath();
			trashfile.delete();
			File dump = new File(P);
			temp2.renameTo(dump);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"System Files do not exist!","Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	@Override
	public Boolean getDraft() {
		return Draft;
	}

	@Override
	public int getPriority() {
		return prior;
	}

	@Override
	public IQueue getMails() {
		return mails;
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public ILinkedList getfiles() {
		return files;
	}

	@Override
	public String getBody() {
		return body;
	}

	@Override
	public String getCurrentMail() {
		return currentmail;
	}

	@Override
	public void setDraft(Boolean Draft) {
		this.Draft = Draft;
	}

	@Override
	public void setPriority(int prior) {
		this.prior = prior;
	}

	@Override
	public void setMails(IQueue mails) {
		this.mails = mails;
	}

	@Override
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public void setfiles(ILinkedList files) {
		this.files = files;
	}

	@Override
	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public void setCurrentMail(String currentmail) {
		this.currentmail = currentmail;
	}
	
////////////////////////////////////////////////////////////////////////////////////////
	
	public String getMailName() {
		return mailName;
	}

	public void setMailName (String mailName) {
		this.mailName = mailName;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom (String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}

	public void setTo (String to) {
		this.to = to;
	}
	
}
