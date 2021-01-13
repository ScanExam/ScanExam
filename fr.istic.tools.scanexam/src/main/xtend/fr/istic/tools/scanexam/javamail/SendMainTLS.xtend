package fr.istic.tools.scanexam.javamail

import java.util.Properties
import javax.mail.Session
import javax.mail.PasswordAuthentication
import javax.mail.Message
import javax.mail.internet.InternetAddress
import javax.mail.Transport
import javax.mail.MessagingException
import javax.mail.internet.MimeMessage
import java.util.Date

class SendMailTLS {
	
	static def sendMail(String user, String msgText) {
		val username = "...@gmail.com"
	    val password = "..."
	    val props = new Properties()
	    
	    props.put("mail.smtp.auth", "true")
	    props.put("mail.smtp.localhost", "test");
	    props.put("mail.smtp.starttls.enable", "true")
	    props.put("mail.smtp.host", "smtp.gmail.com")
	    props.put("mail.smtp.port", "587")
	    
	    val session = Session.getInstance(props,  new javax.mail.Authenticator() {
	            override protected PasswordAuthentication getPasswordAuthentication() {
	                new PasswordAuthentication(username, password);
	            }
	          })
	          
	    try {
	    	
	    	val message = new MimeMessage(session);
	    	// Expéditeur
	    	message.setFrom(new InternetAddress(user))
	    	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user))
	    	
	    	// Sujet du mail
	    	message.setSubject("ScanExam Envoie")
	    	message.setText(msgText)
	    	
	    	message.setHeader("X-Mailer", "Java");
            message.setSentDate(new Date());
	        session.setDebug(true);
	    	
	    	// Envoie du mail
	    	Transport.send(message)
	    	println("Message envoyé !")
	    } catch(MessagingException e) {
	    	//TODO Pourquoi ?
	    	throw new RuntimeException(e);
	    }
	}
}