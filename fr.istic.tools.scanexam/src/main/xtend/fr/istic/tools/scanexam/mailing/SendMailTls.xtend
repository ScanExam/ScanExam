package fr.istic.tools.scanexam.mailing

import com.sun.mail.util.MailConnectException
import fr.istic.tools.scanexam.config.ConfigurationManager
import fr.istic.tools.scanexam.services.Service
import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.io.PrintWriter
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
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import javax.mail.AuthenticationFailedException

/**
 * @author Thomas Guibert
 */
class SendMailTls {

	
	static Service service

	new(Service serv) {
		service = serv
	}

	def static save(File files) {
		save1(files, service.examName)
	}

	private def static save1(File files, String nom) {
		var String chemin = files.absolutePath
		var String nom1 = nom + ".txt"
		var PrintWriter writer = new PrintWriter(nom1, 'UTF-8');
		writer.println(chemin)
		writer.close()
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
	def static sendMail(String sender, String senderPassword, String recipient, String titleMail, String messageMail,
		String pieceJointe) {
		sendMail1(sender, senderPassword, recipient, titleMail, messageMail, pieceJointe, service.examName)
	}

	/**
	 * @param email une adresse email (non nulle)
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

		// Verification de la validité d'une adresse
		if (!checkEmailFormat(email))
			throw new IllegalArgumentException("email is not in a valid format")

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

	/**
	 * @param email un string
	 * @return true si <i>email</i> est une adresse email valide, false sinon
	 */
	def static boolean checkEmailFormat(String email) {
		val emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
		return email.matches(emailRegex)
	}

	def static sendMail1(String sender, String senderPassword, String recipient, String titleMail, String messageMail,
		String pieceJointe, String nameExam) {

		// Verification des parametres
		Objects.requireNonNull(sender, "Erreur : L'expediteur donner doit etre non Null");
		Objects.requireNonNull(senderPassword, "Erreur : Le mot de passe de l'expediteur donner doit etre non Null");
		Objects.requireNonNull(recipient, "Erreur : Le destinataire donner doit etre non Null");
		Objects.requireNonNull(titleMail, "Erreur : Le titre du mail ne doit pas etre Null");
		Objects.requireNonNull(messageMail, "Erreur : Le message du mail ne doit pas etre Null");
		Objects.requireNonNull(pieceJointe, "Erreur : La piece Jointe du mail ne doit pas etre Null");

		if (!checkEmailFormat(sender))
			throw new IllegalArgumentException("email is not in a valid format")

		val props = new Properties()
		val host = ConfigurationManager.instance.mailHost
		val port = ConfigurationManager.instance.mailHost

		Objects.requireNonNull(host, "Erreur : Le type d'adresse mail n'est pas présent dans le fichier configuration")
		Objects.requireNonNull(port,
			"Erreur : Le port de l'adresse mail n'est pas présent dans le fichier configuration")

		// Propriété du mail
		props.put("mail.smtp.auth", "true")
		props.put("mail.smtp.localhost", "ScanExam")
		props.put("mail.smtp.starttls.enable", "true")
		props.put("mail.smtp.host", host)
		props.put("mail.smtp.port", port)

		var String nom = null
		var String mail = ""

		try {
			// Lecture fichier liant une copie à un élève
			var File cheminInfo = new File(nameExam + ".txt")
			// var File cheminInfo = new File("nomExam.txt")
			var FileReader fx = new FileReader(cheminInfo)
			var BufferedReader f = new BufferedReader(fx)
			var File informationMail = new File(f.readLine + ".xls")

			// val files = ResourcesUtils.getInputStreamResource("mailing/anonymat_nom_mail.xls")
			var POIFSFileSystem doc = new POIFSFileSystem(informationMail)
			var HSSFWorkbook wb = new HSSFWorkbook(doc)
			var HSSFSheet sheet = wb.getSheetAt(0)

			// Lecture d'une cellule
			var int x = 0
			var HSSFRow row = sheet.getRow(x)
			var HSSFCell cell = row.getCell(0)

			var boolean trouve = false
			// Parcourt notre tableau
			while (((cell.getStringCellValue() != "") && (!trouve))) {
				// Si recipient est de la forme n° d'anonymat
				if ((com.google.common.base.Objects.equal(cell.getStringCellValue(), recipient) &&
					recipient.matches("[0-9]+"))) {
					cell = row.getCell(1)
					mail = cell.getStringCellValue()
					cell = row.getCell(2)
					nom = cell.getStringCellValue()
					trouve = true
				} else {
					// Si recipient est de la forme nom
					if (cell.stringCellValue == recipient) {
						nom = recipient;
						cell = row.getCell(1);
						mail = cell.getStringCellValue();
						trouve = true;
					} else {
						x++;
						row = sheet.getRow(x);
						cell = row.getCell(0);
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		var boolean equals = com.google.common.base.Objects.equal(mail, "")

		if (equals) {
			println("Le numero d'etudiant ou le nom ne correspond a aucune adresse mail")
		} else {
			// session de l'expediteur
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

				// Gestion de l'envoie de la piece jointe
				if (pieceJointe != "") {

					messageBodyPart = new MimeBodyPart();
					// val fileName = "attachmentName";
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

			} catch (MessagingException e) {
				e.printStackTrace
			}
		}
	}
}
