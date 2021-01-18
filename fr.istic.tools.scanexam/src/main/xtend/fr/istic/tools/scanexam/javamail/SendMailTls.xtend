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
import java.io.FileInputStream

class SendMailTls {
	
	static def sendMail(String exp , String expMdp,String dest,String title, String msgText) {
		
	    val props = new Properties()
	    val file = new FileInputStream("src\\main\\xtend\\fr\\istic\\tools\\scanexam\\javamail\\configMailFile")
	    
	    props.load(file)
	    file.close()
	    
	    var register = false
	    var idtmp = 0
	    
	    while(!register){
	    	if(exp.charAt(idtmp).compareTo("@")==0){
	    		register = true
	    	}
	    	idtmp++
	    }
	    
	    var idMail = exp.substring(idtmp,exp.length)
	    
	    
	    val host = props.getProperty(idMail+"Host")
	    val port = props.getProperty(idMail+"Port")
	    
	    props.put("mail.smtp.auth", "true")
	    props.put("mail.smtp.localhost", "ScanExam");
	    props.put("mail.smtp.starttls.enable", "true")
	    props.put("mail.smtp.host", host)
	    props.put("mail.smtp.port", port)
	    
	    val session = Session.getInstance(props,  new javax.mail.Authenticator() {
	            override protected PasswordAuthentication getPasswordAuthentication() {
	                new PasswordAuthentication(exp, expMdp);
	            }
	          })
	          
	    try {
	    	
	    	val message = new MimeMessage(session);
	    	// Expéditeur
	    	message.setFrom(new InternetAddress(exp))
	    	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dest))
	    	
	    	// Sujet du mail
	    	message.setSubject(title)
	    	message.setText(msgText)
	    	
	    	message.setHeader("X-Mailer", "ScanExam");
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