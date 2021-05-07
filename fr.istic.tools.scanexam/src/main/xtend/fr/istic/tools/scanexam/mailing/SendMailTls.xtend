package fr.istic.tools.scanexam.mailing

import com.sun.mail.util.MailConnectException
import fr.istic.tools.scanexam.config.ConfigurationManager
import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.io.File
import java.io.InputStream
import java.util.Date
import java.util.Objects
import java.util.Properties
import java.util.logging.Logger
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.AuthenticationFailedException
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

/**
 * @author Thomas Guibert
 */
class SendMailTls {



	/**
	 * @param email une adresse email valide (non null)
	 * @return une paire composée de l'Host et du Port SMTP pour cette adresse mail, si ceux-ci se trouvent dans le fichier mailing/configMailFile.properties
	 * @throw IllegalArgumentException si l'adresse mail n'est pas sous un format valide
	 */
	def static Pair<String, String> getSmtpInformation(String email) {
		Objects.requireNonNull(email)

		// Lecture du fichier config
		val file = ResourcesUtils.getInputStreamResource("mailing/configMailFile.properties")
		val props = new Properties()

		props.load(file)
		file.close()

		// Extraction de la chaine de caractère situer apres l'@ dans l'adresse de l'expediteur
		var typeMail = email.substring(email.indexOf('@') + 1, email.length)

		// Création des clés qui vont permetre de recuperer le host et le port nécessaire a l'envoie du mail
		val HOST = props.getProperty(typeMail + "Host")
		val PORT = props.getProperty(typeMail + "Port")

		return Pair.of(HOST, PORT)

	}


	enum LoginResult {
		SUCCESS,
		IDENTIFICATION_FAILED,
		HOST_NOT_FOUND
		
	}

	/**
	 * @param name l'adresse mail du login
	 * @param password le mot de passe du login
	 * @param host l'host SMTP
	 * @param port le port SMTP
	 * @return true si le programme a réussi à se connecter à l'adresse mail, false sinon
	 */
	def static LoginResult checkLogin(String name, String password, String host, int port) {
		Objects.requireNonNull(name)
		Objects.requireNonNull(password)
		Objects.requireNonNull(host, "Erreur : Le type d'adresse mail n'est pas présent dans le fichier configuration")
		Objects.requireNonNull(port,
			"Erreur : Le port de l'adresse mail n'est pas présent dans le fichier configuration")

		val props = new Properties()
		
		// Propriété du mail
		props.put("mail.smtp.auth", "true")
		props.put("mail.smtp.localhost", "ScanExam")
		props.put("mail.smtp.starttls.enable", "true")
		props.put("mail.smtp.host", host)
		props.put("mail.smtp.port", port)
		props.put("mail.smtps.timeout", "5000")
		props.put("mail.smtps.connectiontimeout", "5000");    

		try {
			val session = Session.getInstance(props, new Authenticator() {
				override protected PasswordAuthentication getPasswordAuthentication() {
					new PasswordAuthentication(name, password);
				}
			})
			val Transport transport = session.getTransport("smtp");
			transport.connect(host, port, name, password);
			transport.close();
			return LoginResult.SUCCESS
		} catch (AuthenticationFailedException e) {
			return LoginResult.IDENTIFICATION_FAILED 
		} catch(MailConnectException e) {
			return LoginResult.HOST_NOT_FOUND
		} catch(Exception e) {
			e.printStackTrace
			return LoginResult.IDENTIFICATION_FAILED
		}
	}
	
	
	val Session session;
	val Properties props;
	
	new() {
		props = new Properties()
		
		val sender = ConfigurationManager.instance.email
		val senderPassword = ConfigurationManager.instance.emailPassword
		val host = ConfigurationManager.instance.mailHost
		val port = ConfigurationManager.instance.mailPort
		Objects.requireNonNull(sender, "Sender's email is absent in configuration file")
		Objects.requireNonNull(sender, "Sender's email password is absent in configuration file")
		Objects.requireNonNull(host, "SMTP host of sender's email is absent in configuration file")
		Objects.requireNonNull(port, "SMTP port of sender's email is absent in configuration file")
		
		props.put("mail.smtp.auth", "true")
		props.put("mail.smtp.localhost", "ScanExam")
		props.put("mail.smtp.starttls.enable", "true")
		props.put("mail.smtp.host", host)
		props.put("mail.smtp.port", port)
		
		session = Session.getInstance(props, new Authenticator() {
			override protected PasswordAuthentication getPasswordAuthentication() {
				new PasswordAuthentication(sender, senderPassword);
			}
		})
	}


	/**
	 * La fonction sendMail va chercher dans le fichier configMailFile pour trouver le port et les smtp (host) de l'adresse mail donnée puis qui ce charge d'envoier le mail
	 * @param sender : Adresse mail de l'expediteur qui ne doit pas etre null
	 * @param senderPassword : Mot de passe de l'expediteur qui ne doit pas etre nul
	 * @param recipient : Adresse mail du destinataire qui ne doit pas etre null
	 * @param titleMail : Titre du mail qui ne doit pas etre null
	 * @param messageMail : Contenu du mail
	 * @param pieceJointe : piece jointe du mail
	 */
	def sendMail(InputStream pdfStream, String titleMail, String messageMail, String studentName, String studentAdress, File studentSheet) {

		// Verification des parametres
		Objects.requireNonNull(titleMail, "Erreur : Le titre du mail ne doit pas etre Null");
		Objects.requireNonNull(messageMail, "Erreur : Le message du mail ne doit pas etre Null");

		val sender = ConfigurationManager.instance.email
		try {
			val message = new MimeMessage(session);

			// Expéditeur et destinataire du message
			message.setFrom(new InternetAddress(sender))
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(studentAdress))

			// Sujet du mail et contenu du message
			message.setSubject(titleMail, "UTF-8")
			var messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(messageMail, "text/html; charset=UTF-8")

			var multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Gestion de l'envoie de la piece jointe
			messageBodyPart = new MimeBodyPart();
			// val fileName = "attachmentName";
			var source = new FileDataSource(studentSheet)
			messageBodyPart.setDataHandler(new DataHandler(source))
			messageBodyPart.setFileName(studentName)
			multipart.addBodyPart(messageBodyPart)

			message.setContent(multipart)

			message.setHeader("X-Mailer", "ScanExam")
			message.setSentDate(new Date())
			session.setDebug(false);

			// Envoie du mail
			Transport.send(message)
			Logger.getGlobal.info("Message envoyé !")

		} catch (MessagingException e) {
			e.printStackTrace
		}
	}
}
