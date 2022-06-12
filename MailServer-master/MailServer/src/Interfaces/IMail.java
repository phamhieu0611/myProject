package Interfaces;

import interfaces.*;

public interface IMail {
	
	public Boolean getDraft();
	public int getPriority();
	public IQueue getMails();
	public String getSubject();
	public ILinkedList getfiles();
	public String getBody();
	public String getCurrentMail();
	
	public void setDraft(Boolean Draft);
	public void setPriority(int prior);
	public void setMails(IQueue mails);
	public void setSubject(String subject);
	public void setfiles(ILinkedList files);
	public void setBody(String body);
	public void setCurrentMail(String currentmail);
	
	public IQueue checkEmailList(String toFieldInput);
}
