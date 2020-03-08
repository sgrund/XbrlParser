/*
 * created by github.com/marcioAlexandre
 * 6 Jun 2019
 *
 */

package com.xbrlframework.file;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
	
	// https://www.tutorialspoint.com/java/java_sending_email.htm
	// https://stackoverflow.com/questions/10509699/must-issue-a-starttls-command-first
	
	private String getName(String filename) {
		String name = "";
		if (filename.indexOf("/") > -1) {
			String[] aux = filename.split("/");
			String temp = aux[aux.length-1];
			aux = temp.split("\\.");
			name = aux[0];			
		}else {
			String[] aux = filename.split("\\.");
			name = aux[0];
		}
		return name;
	}
	
	protected void toGmail(String emailto, String filename, String xbrljson) throws AddressException {
		 	String to = emailto;
		    String subject = "[XBRL-JSON Converter] "+this.getName(filename)+".xml in XBRL-JSON";
		    String msg = "Dear,\n"
		    		+"Firstly, thank you for using XBRL-JSON Converter tool. "
		    		+"Your file ("+filename+") has been converted to JSON format successfully (it's available below). \n"
		    		+"More details about this tool: https://marcioalexandre.wordpress.com/projects/xbrl-parser. \n"
		    		+"Any doubt, error or feedback: https://marcioalexandre.wordpress.com/contact. \n" 
		    		+"Best, \n \n"
		    		+ this.getName(filename)+".json: \n";
		    final String from ="javaapibuilder@gmail.com";
		    final  String password = "Ma@12@1983";


		    Properties props = new Properties();  
		    props.setProperty("mail.transport.protocol", "smtp");     
		    props.setProperty("mail.host", "smtp.gmail.com");  
		    
		    props.put("mail.smtp.auth", "true");  
		    props.put("mail.smtp.port", "465");  
		    props.put("mail.debug", "true");  
		    props.put("mail.smtp.socketFactory.port", "465");  
		    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
		    props.put("mail.smtp.socketFactory.fallback", "false");
		    props.put("mail.smtp.starttls.enable", "true");
		    
		    Session session = Session.getDefaultInstance(props,  
		    new javax.mail.Authenticator() {
		       protected PasswordAuthentication getPasswordAuthentication() {  
		       return new PasswordAuthentication(from,password);  
		   }  
		   });  
		    
		   try {
			   //session.setDebug(true);  
			   Transport transport = session.getTransport();  
			   InternetAddress addressFrom = new InternetAddress(from, "Marcio Alexandre");
			   
			   InternetAddress addressTo = new InternetAddress(emailto);
	           addressTo.validate();
	
			   MimeMessage message = new MimeMessage(session);  
			   message.setSender(addressFrom);  
			   message.setSubject(subject, "UTF-8");  
			   message.setContent(msg+xbrljson, "text/plain");  
			   message.setHeader("Content-Type", "text/plain; charset=\"utf-8\"");
			   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));  
	
			   transport.connect();  
			   Transport.send(message);  
			   transport.close();
		   }catch(Exception e) {
			   e.printStackTrace();
		   }
		   
	} 

}
