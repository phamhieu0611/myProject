package Classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Interfaces.IFolder;

public class Folder implements IFolder {
	File fldr;
	
	public Folder(String path) {
		fldr = new File(path);
	}
	
	public File get() {
		return fldr;
	}
	
	public static Boolean deleteData (File src, String deleteitem, int lines,Boolean destBool, File dest) {
		File temp = new File(src.getParentFile(), "temp.txt");
		try {
			Scanner scan = new Scanner(src);
			FileWriter writetemp = new FileWriter(temp, true);
			FileWriter writedest = null;
			if (destBool) {
				writedest = new FileWriter(dest, true);
			}
			while(scan.hasNext()) {
				String input = scan.nextLine();
				if(input.compareTo(deleteitem) == 0) {
					if (destBool) {
						writedest.write(input + '\n');
						for (int j = 1; j < lines; j++) {
							writedest.write(scan.nextLine() + '\n');
						}
					} else {
						for (int j = 1; j < lines; j++) {
							scan.nextLine();
						}
					}
				} else {
					writetemp.write(input + '\n');
				}
			}
			scan.close();
			writetemp.close();
			if (destBool) {
				writedest.close();				
			}
			String P = src.getAbsolutePath();
			src.delete();
			File dump = new File(P);
			temp.renameTo(dump);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"System Files do not exist!","Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public static Boolean copyFolder(File src, File dest) {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String[] files = src.list();
			for (String filename : files) {
				File srcFile = new File(src, filename);
                File destFile = new File(dest, filename);
                copyFolder(srcFile, destFile);
			}
		} else {
			try {
				Files.copy(src.toPath(), dest.toPath());
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	public static Boolean deleteFolder(File src) {
		if (src.isDirectory()) {
			String[] files = src.list();
			if (files.length == 0) {
				src.delete();
			} else {
				for (String filename : files) {
					File newfile = new File(src, filename);
					deleteFolder(newfile);
					if (src.exists()) {
						src.delete();
					}
				}
			}
		} else {
			src.delete();
		}
		return true;
	}
}
