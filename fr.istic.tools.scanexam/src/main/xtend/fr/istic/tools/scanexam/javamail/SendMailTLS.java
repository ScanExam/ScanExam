package fr.istic.tools.scanexam.javamail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {

	public static void sendMail (String user, String messagetexte) {
	
		//Identifiant et Mot de passe de l'adresse mail destinataire
			final String username = "AdresseTestMail39@gmail.com";
	        final String password = "TestMail39";
			
			Properties props = new Properties();
			        
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	        
	        
	        Session session = Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	          });

	        try {

	            Message message = new MimeMessage(session);
	            // Expediteur 
	            message.setFrom(new InternetAddress(user)); 
	            message.setRecipients(Message.RecipientType.TO,
	            // Destination
	            InternetAddress.parse(user)); 
	            
	            //Sujet du mail envoyer
	            message.setSubject("ScanExam Envoie");
	            message.setText(messagetexte);
	            
	            //Envoye du message
	            Transport.send(message);
	            System.out.println("Message envoy�");

	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	    }

}
