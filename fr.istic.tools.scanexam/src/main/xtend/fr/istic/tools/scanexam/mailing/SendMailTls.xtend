package fr.istic.tools.scanexam.mailing


import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.util.Date
import java.util.Objects
import java.util.Properties
import java.util.logging.Logger
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFCell
import java.io.FileNotFoundException
import java.io.IOException
import java.io.File
import java.io.BufferedReader
import java.io.FileReader

import fr.istic.tools.scanexam.services.Service
import java.io.PrintWriter
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
 
 	static Service service

	new(Service serv) {
		service = serv
	}
 	
 	def static save(File files){
		var String chemin = files.absolutePath
		var String nom = "nomExam" + ".txt" 		
		//var String nom1 = service.examName + ".txt"
		var PrintWriter writer = new PrintWriter(nom,'UTF-8');
		writer.println(chemin)
		writer.close()
 	}
 
	def static sendMail(String sender, String senderPassword, String recipient, String titleMail, String messageMail, String pieceJointe) {
		
		//Verification des parametres
		Objects.requireNonNull(sender, "Erreur : L'expediteur donner doit etre non Null");
		Objects.requireNonNull(senderPassword, "Erreur : Le mot de passe de l'expediteur donner doit etre non Null");
		Objects.requireNonNull(recipient, "Erreur : Le destinataire donner doit etre non Null");
		Objects.requireNonNull(titleMail, "Erreur : Le titre du mail ne doit pas etre Null");
		Objects.requireNonNull(messageMail, "Erreur : Le message du mail ne doit pas etre Null");
		Objects.requireNonNull(pieceJointe, "Erreur : La piece Jointe du mail ne doit pas etre Null");
		
	    val props = new Properties()
	     
	    //Lecture du fichier config
	    val file = ResourcesUtils.getInputStreamResource("mailing/configMailFile.properties")
	    
	    props.load(file)
	    file.close()
	    
	    
	    //Verification de la validiter d'une adresse
	    if(!sender.contains('@')){
	    	throw new Exception("L'expediteur n'a pas une adresse mail valide");}
	   /*if(!recipient.contains('@')){
	    	throw new Exception("Le destinataire n'a pas une adresse mail valide");}*/
	    	
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
	    
	    var String nom = null
        var String mail = ""
	    
	    try{
	    //Lecture fichier liant une copie à un élève
	    //var File cheminInfo = new File(service.examName + ".txt")
	    var File cheminInfo = new File("nomExam.txt")
	    var FileReader fx = new FileReader(cheminInfo)
	    var BufferedReader f = new BufferedReader(fx)
	    var File informationMail = new File(f.readLine + ".xls")
	    
	   // val files = ResourcesUtils.getInputStreamResource("mailing/anonymat_nom_mail.xls")
	    
        var POIFSFileSystem doc = new POIFSFileSystem(informationMail)
        var HSSFWorkbook wb = new HSSFWorkbook(doc)
        var HSSFSheet sheet = wb.getSheetAt(0)
        
        //Lecture d'une cellule
        var int x = 0        
        var HSSFRow row = sheet.getRow(x)
        var HSSFCell cell = row.getCell(0)
        
        var boolean trouve = false
        
        //Parcourt notre tableau
        while (((cell.getStringCellValue() != "") && (!trouve))) {
          //Si recipient est de la forme n° d'anonymat
          if ((com.google.common.base.Objects.equal(cell.getStringCellValue(), recipient) && recipient.matches("[0-9]+"))) {
            cell = row.getCell(1)
            mail = cell.getStringCellValue()
            cell = row.getCell(2)
            nom = cell.getStringCellValue()
            trouve = true
          } 
          else {
          	//Si recipient est de la forme nom
            var boolean equals = com.google.common.base.Objects.equal(cell, recipient)
            if (equals) {
              nom = recipient;
              cell = row.getCell(1);
              mail = cell.getStringCellValue();
              trouve = true;
            } 
            else {
              x++;
              row = sheet.getRow(x);
              cell = row.getCell(0);
            }
          }
        }
	    	
	    }catch (FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
		e.printStackTrace();
		}
		
		var boolean equals = com.google.common.base.Objects.equal(mail, "")
		
        if (equals) {
          println("Le numero d'etudiant ou le nom ne correspond a aucune adresse mail")
        } else {	   	    
	    //session de l'expediteur
	    val session = Session.getInstance(props, new Authenticator() {
	            override protected PasswordAuthentication getPasswordAuthentication() {
	                new PasswordAuthentication(sender, senderPassword);
	            }
	          })
	          
	    try {
	    	
	    	val message = new MimeMessage(session);
	    	
	    	// Expéditeur et destinataire du message
	    	message.setFrom(new InternetAddress(sender))
	    	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail))
	    	
	    	// Sujet du mail et contenu du message
	    	message.setSubject(titleMail)
	    	var messageBodyPart = new MimeBodyPart();
	    	messageBodyPart.setText(messageMail + nom)
	    	
	    	var multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
	    
	    	//Gestion de l'envoie de la piece jointe
	    	if(pieceJointe != ""){
	    		
	    		messageBodyPart = new MimeBodyPart();
        		//val fileName = "attachmentName";
	    		
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
}