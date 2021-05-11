package fr.istic.tools.scanexam.exportation;

import com.sun.mail.util.MailConnectException;
import fr.istic.tools.scanexam.config.ConfigurationManager;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * @author Thomas Guibert
 */
@SuppressWarnings("all")
public class SendMailTls {
  public enum LoginResult {
    SUCCESS,
    
    IDENTIFICATION_FAILED,
    
    HOST_NOT_FOUND;
  }
  
  /**
   * @param email une adresse email valide (non null)
   * @return une paire composée de l'Host et du Port SMTP pour cette adresse mail, si ceux-ci se trouvent dans le fichier mailing/configMailFile.properties
   * @throw IllegalArgumentException si l'adresse mail n'est pas sous un format valide
   */
  public static Pair<String, String> getSmtpInformation(final String email) {
    try {
      Objects.<String>requireNonNull(email);
      final InputStream file = ResourcesUtils.getInputStreamResource("mailing/configMailFile.properties");
      final Properties props = new Properties();
      props.load(file);
      file.close();
      int _indexOf = email.indexOf("@");
      int _plus = (_indexOf + 1);
      String typeMail = email.substring(_plus, email.length());
      final String HOST = props.getProperty((typeMail + "Host"));
      final String PORT = props.getProperty((typeMail + "Port"));
      return Pair.<String, String>of(HOST, PORT);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * @param name l'adresse mail du login
   * @param password le mot de passe du login
   * @param host l'host SMTP
   * @param port le port SMTP
   * @return true si le programme a réussi à se connecter à l'adresse mail, false sinon
   */
  public static SendMailTls.LoginResult checkLogin(final String name, final String password, final String host, final int port) {
    Objects.<String>requireNonNull(name);
    Objects.<String>requireNonNull(password);
    Objects.<String>requireNonNull(host, "Erreur : Le type d\'adresse mail n\'est pas présent dans le fichier configuration");
    Objects.<Integer>requireNonNull(Integer.valueOf(port), 
      "Erreur : Le port de l\'adresse mail n\'est pas présent dans le fichier configuration");
    final Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.localhost", "ScanExam");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", Integer.valueOf(port));
    props.put("mail.smtps.timeout", "5000");
    props.put("mail.smtps.connectiontimeout", "5000");
    try {
      final Session session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(name, password);
        }
      });
      final Transport transport = session.getTransport("smtp");
      transport.connect(host, port, name, password);
      transport.close();
      return SendMailTls.LoginResult.SUCCESS;
    } catch (final Throwable _t) {
      if (_t instanceof AuthenticationFailedException) {
        return SendMailTls.LoginResult.IDENTIFICATION_FAILED;
      } else if (_t instanceof MailConnectException) {
        return SendMailTls.LoginResult.HOST_NOT_FOUND;
      } else if (_t instanceof Exception) {
        final Exception e_2 = (Exception)_t;
        e_2.printStackTrace();
        return SendMailTls.LoginResult.IDENTIFICATION_FAILED;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  private final Session session;
  
  private final Properties props;
  
  public SendMailTls() {
    Properties _properties = new Properties();
    this.props = _properties;
    final String sender = ConfigurationManager.instance.getEmail();
    final String senderPassword = ConfigurationManager.instance.getEmailPassword();
    final String host = ConfigurationManager.instance.getMailHost();
    final int port = ConfigurationManager.instance.getMailPort();
    Objects.<String>requireNonNull(sender, "Sender\'s email is absent in configuration file");
    Objects.<String>requireNonNull(sender, "Sender\'s email password is absent in configuration file");
    Objects.<String>requireNonNull(host, "SMTP host of sender\'s email is absent in configuration file");
    Objects.<Integer>requireNonNull(Integer.valueOf(port), "SMTP port of sender\'s email is absent in configuration file");
    this.props.put("mail.smtp.auth", "true");
    this.props.put("mail.smtp.localhost", "ScanExam");
    this.props.put("mail.smtp.starttls.enable", "true");
    this.props.put("mail.smtp.host", host);
    this.props.put("mail.smtp.port", Integer.valueOf(port));
    this.session = Session.getInstance(this.props, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(sender, senderPassword);
      }
    });
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
  public void sendMail(final InputStream pdfStream, final String titleMail, final String messageMail, final String studentName, final String studentAdress, final File studentSheet) {
    Objects.<String>requireNonNull(titleMail, "Erreur : Le titre du mail ne doit pas etre Null");
    Objects.<String>requireNonNull(messageMail, "Erreur : Le message du mail ne doit pas etre Null");
    final String sender = ConfigurationManager.instance.getEmail();
    try {
      final MimeMessage message = new MimeMessage(this.session);
      InternetAddress _internetAddress = new InternetAddress(sender);
      message.setFrom(_internetAddress);
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(studentAdress));
      message.setSubject(titleMail, "UTF-8");
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent(messageMail, "text/html; charset=UTF-8");
      MimeMultipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);
      MimeBodyPart _mimeBodyPart = new MimeBodyPart();
      messageBodyPart = _mimeBodyPart;
      FileDataSource source = new FileDataSource(studentSheet);
      DataHandler _dataHandler = new DataHandler(source);
      messageBodyPart.setDataHandler(_dataHandler);
      messageBodyPart.setFileName(studentName);
      multipart.addBodyPart(messageBodyPart);
      message.setContent(multipart);
      message.setHeader("X-Mailer", "ScanExam");
      Date _date = new Date();
      message.setSentDate(_date);
      this.session.setDebug(false);
      Transport.send(message);
      Logger.getGlobal().info("Message envoyé !");
    } catch (final Throwable _t) {
      if (_t instanceof MessagingException) {
        final MessagingException e = (MessagingException)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
}
