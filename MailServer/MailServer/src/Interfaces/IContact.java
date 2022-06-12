package Interfaces;

import java.io.IOException;

public interface IContact {
	/*
	 * add contact after you check that the information is right and create folder to that email on the system
	 * 
	 * open the contacts file
	 * append the mail and the password 
	 * create folder with the email
	 * create contact file that contain :
	 * 					user name 
	 * 					password 
	 * 					email
	 * 					4 main folder names : draft inbox sent trash 
	 * create the 4 folders each has empty emails file
	 * stay at inbox folder  					
	 */
	public Boolean newUser();
	
	public void setrepassword(String entered);
	
	public void setemail(String entered);
	
	public void setpassword(String entered);
	
	public void setname(String entered);
	
	public String getrepassword();
	
	public String getemail();
	
	public String getpassword();
	
	public String getname();

}
