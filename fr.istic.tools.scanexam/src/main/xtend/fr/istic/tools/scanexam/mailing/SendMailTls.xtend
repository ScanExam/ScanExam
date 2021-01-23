
package fr.istic.tools.scanexam.mailing

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
import java.util.Objects
import javax.activation.DataSource
import javax.activation.FileDataSource
import javax.activation.DataHandler
import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.util.logging.Logger
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMultipart
import javax.mail.Multipart

/**
 * @author Thomas Guibert
 */
class SendMailTls {

/**
 * La fonction sendMail va chercher dans le fichier configMailFile pour trouver le port et les smtp (host) de l'adresse mail donnée puis qui ce charge d'envoier le mail
 * @param sender : Adresse mail de l'expediteur qui ne doit pas etre null
 * @param senderPassword : Mot de passe de l'expediteur qui ne doit pas etre nul
 * @param recipient : Adresse mail du destinataire qui ne doit pas etre null
 * @param titleMail : Titre du mail qui ne doit pas etre null
 * @param messageMail : Contenu du mail
 * @param pieceJointe : piece jointe du mail
 */
	static def sendMail(String sender, String senderPassword, String recipient, String titleMail, String messageMail, String pieceJointe) {
		
		//Verification des parametres
		Objects.requireNonNull(sender, "Erreur : L'expediteur donner doit etre non Null");
		Objects.requireNonNull(senderPassword, "Erreur : Le mot de passe de l'expediteur donner doit etre non Null");
		Objects.requireNonNull(recipient, "Erreur : Le destinataire donner doit etre non Null");
		Objects.requireNonNull(titleMail, "Erreur : Le titre du mail ne doit pas etre Null");
		Objects.requireNonNull(messageMail, "Erreur : Le message du mail ne doit pas etre Null");
		Objects.requireNonNull(pieceJointe, "Erreur : La piece Jointe du mail ne doit pas etre Null");
		
	    val props = new Properties()
	    
	    //Lecture du fichier config
	    val file = ResourcesUtils.getInputStreamResource("/mailing/configMailFile.properties")
	    
	    props.load(file)
	    file.close()
	    
	    
	    //Verification de la validiter d'une adresse
	    if(!sender.contains('@')){
	    	throw new Exception("L'expediteur n'a pas une adresse mail valide");}
	    if(!recipient.contains('@')){
	    	throw new Exception("Le destinataire n'a pas une adresse mail valide");}
	    	
	    //Extraction de la chaine de caractère situer apres l'@ dans l'adresse de l'expediteur
	    var typeMail = sender.substring(sender.indexOf('@')+1, sender.length)
	    
	    //Création des clés qui vont permetre de recuperer le host et le port nécessaire a l'envoie du mail
	    val HOST = props.getProperty(typeMail + "Host")
	    val PORT = props.getProperty(typeMail + "Port")
	    
	   
	    //Gestion du cas ou le type d'adresse n'est pas dans le fichier configMailFile
	    Objects.requireNonNull(HOST, "Erreur : Le type d'adresse mail n'est pas présent dans le fichier configuration")
	    Objects.requireNonNull(PORT, "Erreur : Le port de l'adresse mail n'est pas présent dans le fichier configuration")
	    
	    //Propriété du mail
	    props.put("mail.smtp.auth", "true")
	    props.put("mail.smtp.localhost", "ScanExam")
	    props.put("mail.smtp.starttls.enable", "true")
	    props.put("mail.smtp.host", HOST)
	    props.put("mail.smtp.port", PORT)
	    
	    
	    //session de l'expediteur
	    val session = Session.getInstance(props, new javax.mail.Authenticator() {
	            override protected PasswordAuthentication getPasswordAuthentication() {
	                new PasswordAuthentication(sender, senderPassword);
	            }
	          })
	          
	    try {
	    	
	    	val message = new MimeMessage(session);
	    	
	    	// Expéditeur et destinataire du message
	    	message.setFrom(new InternetAddress(sender))
	    	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient))
	    	
	    	// Sujet du mail et contenu du message
	    	message.setSubject(titleMail)
	    	var messageBodyPart = new MimeBodyPart();
	    	messageBodyPart.setText("TEST")
	    	
	    	var multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
	    
	    	//Gestion de l'envoie de la piece jointe
	    	if(pieceJointe != ""){
	    		
	    		messageBodyPart = new MimeBodyPart();
        		val fileName = "attachmentName";
	    		
	    		var source = new FileDataSource(pieceJointe)
	    		messageBodyPart.setDataHandler(new DataHandler(source))
	    		messageBodyPart.setFileName(pieceJointe)
	    		multipart.addBodyPart(messageBodyPart)
	    		
	    	}
	    	message.setContent(multipart)
	    	
	    	message.setHeader("X-Mailer", "ScanExam")
            message.setSentDate(new Date())
	        session.setDebug(true);
	    	
	    	// Envoie du mail
	    	Transport.send(message)
	    	Logger.getGlobal.info("Message envoyé !")
	    	
	    } catch(MessagingException e) {
	    	e.printStackTrace
	    }
	}
}