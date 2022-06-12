package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Interfaces.IFilter;
import classes.DList;
import classes.SinglyLinkedList;
import classes.Stack;
import interfaces.ILinkedList;

public class Filter implements IFilter{
	
	private static String text;
	
	public static long compare(Object X, Object Y, int code) {
		long out = -404;
		if (code == 0) {
			try {
				out = Long.parseLong((String)X) - Long.parseLong((String)Y);
			}catch(Exception e) {
				out = ((String)X).compareTo((String)Y);
			}
		} else {
			Mail x = (Mail) X;
			Mail y = (Mail) Y;
			if (code == 1) {
				out = -1 * x.getMailName().compareTo(y.getMailName());
			} else if (code == 2) {
				out = x.getMailName().compareTo(y.getMailName());
			} else if (code == 3) {
				out = x.getSubject().compareTo(y.getSubject());
			} else if (code == 4) {
				out = -1 * x.getSubject().compareTo(y.getSubject());
			} else if (code == 5) {
				out = x.getPriority() - y.getPriority();
			} else if (code == 6) {
				out = -1 * (x.getPriority() - y.getPriority());
			}
			if (out == 0) {
				out = -1 * x.getMailName().compareTo(y.getMailName());
			}
		}
		return out;
	}

	public static int binarySearch(String [] data,String Target) {
	
		int start=0;
		int end=data.length - 1;
		Stack search=new Stack();
		search.push(end);
		search.push(start);
		while(start<=end) {
			start=(int)search.pop();
			end=(int)search.pop();
			if (start == end) {
				if(compare(Target,data[start], 0) == 0) {
					return start;
				}
			}
			int mid=start+ (end - 1)/2;
			if(compare(Target,data[mid], 0) > 0) {
				search.push(end);
				search.push(mid + 1);
			}else if(compare(Target,data[mid], 0) < 0) {
				search.push(mid - 1);
				search.push(start);
			}else {
				return mid;
			}
		}
		return -1;
	}
	
	public static ILinkedList linearSearch (File currentfolder, String target) {
		File[] folderslist = currentfolder.listFiles();
		ILinkedList foundmails = new DList();
		int sz = folderslist.length;
		for (int i = 0; i < sz; i++) {
			if (folderslist[i].isDirectory()) {
				File currentfile = new File(folderslist[i], "indexfile.txt");
				try {
					Scanner scan = new Scanner(currentfile);
					//skip time line
					scan.nextLine();
					while(scan.hasNextLine()) {
						String line = scan.nextLine();
						if (line.indexOf(target) != -1) {
							foundmails.add(folderslist[i]);
							scan.close();
							break;
						}
					}
					scan.close();
				} catch (FileNotFoundException e) {
					try {
						currentfile.createNewFile();
						File mailsfile = new File(currentfolder, "mailsfile.txt");
						FileWriter wrt = new FileWriter(currentfile, true);
						Scanner scan2 = new Scanner(mailsfile);
						while(scan2.hasNextLine()) {
							String input = scan2.nextLine();
							if (input.compareTo(folderslist[i].getName()) == 0) {
								wrt.write(input + "\n");
								String prio = scan2.nextLine();
								wrt.write(scan2.nextLine() + "\n");
								wrt.write(scan2.nextLine() + "\n");
								wrt.write(prio + "\n");
								wrt.write(scan2.nextLine() + "\n");
								wrt.write("\n\n\n");
								break;
							}
						}
						scan2.close();
						wrt.close();
						File attt = new File(folderslist[i], "Attachments");
						attt.mkdir();
						i--;
					} catch (IOException e1) {
						
					}
					
				}
			}
		}
		return foundmails;
	}
	
	

	public static String getText() {
		return text;
	}

	public static void setText(String text) {
		Filter.text = text;
	}
	
}
