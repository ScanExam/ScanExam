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
import java.util.Objects
import javax.activation.DataSource
import javax.activation.FileDataSource
import javax.activation.DataHandler

class SendMailTls {

/**
 * La fonction sendMail va chercher dans le fichier configMailFile pour trouver le port et les smtp (host) de l'adresse mail donnée puis qui ce charge d'envoier le mail
 * @param SENDER : Adresse mail de l'expediteur qui ne doit pas etre null
 * @param SENDER_PASSWORD : Mot de passe de l'expediteur qui ne doit pas etre nul
 * @param RECIPIENT : Adresse mail du destinataire qui ne doit pas etre null
 * @param TITLE_MAIL : Titre du mail qui ne doit pas etre null
 * @param MESSAGE_MAIL : Contenu du mail
 */
	static def sendMail(String SENDER, String SENDER_PASSWORD, String RECIPIENT, String TITLE_MAIL, String MESSAGE_MAIL, String PIECE_JOINTE) {
		
		//Verification des parametres
		Objects.requireNonNull(SENDER, "Erreur : L'expediteur donner doit etre non Null");
		Objects.requireNonNull(SENDER_PASSWORD, "Erreur : Le mot de passe de l'expediteur donner doit etre non Null");
		Objects.requireNonNull(RECIPIENT, "Erreur : Le destinataire donner doit etre non Null");
		Objects.requireNonNull(TITLE_MAIL, "Erreur : Le titre du mail ne doit pas etre Null");
		
	    val props = new Properties()
	    
	    //Lecture du fichier config
	    val file = new FileInputStream("src\\main\\resources\\configMailFile")
	    props.load(file)
	    file.close()
	    
	    var arobaseFind = false
	    var i = 0
	    
	    //Verification de la validiter d'une adresse
	    if(!SENDER.contains('@')){
	    	throw new Exception("L'expediteur n'a pas une adresse mail valide");}
	    	
	    if(!RECIPIENT.contains('@')){
	    	throw new Exception("Le destinataire n'a pas une adresse mail valide");}
	    	
	    
	    while(!arobaseFind){
	    	if(SENDER.charAt(i).compareTo("@") == 0){
	    		arobaseFind = true
	    	}
	    	i++
	    }
	    //println(SENDER.contains())
	    var typeMail = SENDER.substring(i, SENDER.length)
	    
	    //Création des clés qui vont permetre de recuperer le host et le port nécessaire a l'envoie du mail
	    val HOST = props.getProperty(typeMail + "Host")
	    val PORT = props.getProperty(typeMail + "Port")
	    
	    Objects.requireNonNull(HOST, "Erreur : Le type d'adresse mail n'est pas présent dans le fichier configuration")
	    Objects.requireNonNull(PORT, "Erreur : Le port de l'adresse mail n'est pas présent dans le fichier configuration")
	    
	    //Propriété du mail
	    props.put("mail.smtp.auth", "true")
	    props.put("mail.smtp.localhost", "ScanExam");
	    props.put("mail.smtp.starttls.enable", "true")
	    props.put("mail.smtp.host", HOST)
	    props.put("mail.smtp.port", PORT)
	    
	    
	    //session de l'expediteur
	    val session = Session.getInstance(props, new javax.mail.Authenticator() {
	            override protected PasswordAuthentication getPasswordAuthentication() {
	                new PasswordAuthentication(SENDER, SENDER_PASSWORD);
	            }
	          })
	          
	    try {
	    	
	    	val message = new MimeMessage(session);
	    	
	    	// Expéditeur et destinataire du message
	    	message.setFrom(new InternetAddress(SENDER))
	    	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECIPIENT))
	    	
	    	// Sujet du mail et contenu du message
	    	message.setSubject(TITLE_MAIL)
	    	message.setText(MESSAGE_MAIL)
	    	
	    	if(PIECE_JOINTE != null){
	    		var source = new FileDataSource(PIECE_JOINTE)
	    		message.setDataHandler(new DataHandler(source))
	    		message.setFileName(PIECE_JOINTE)
	    	}
	    	
	    	message.setHeader("X-Mailer", "ScanExam")
            message.setSentDate(new Date())
	        session.setDebug(true);
	    	
	    	// Envoie du mail
	    	Transport.send(message)
	    	println("Message envoyé !")
	    	
	    } catch(MessagingException e) {
	    	e.printStackTrace
	    }
	}
}