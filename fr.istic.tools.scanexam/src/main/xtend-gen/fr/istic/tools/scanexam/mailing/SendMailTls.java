package fr.istic.tools.scanexam.mailing;

import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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

/**
 * @author Thomas Guibert
 */
@SuppressWarnings("all")
public class SendMailTls {
  /**
   * La fonction sendMail va chercher dans le fichier configMailFile pour trouver le port et les smtp (host) de l'adresse mail donnée puis qui ce charge d'envoier le mail
   * @param sender : Adresse mail de l'expediteur qui ne doit pas etre null
   * @param senderPassword : Mot de passe de l'expediteur qui ne doit pas etre nul
   * @param recipient : Adresse mail du destinataire qui ne doit pas etre null
   * @param titleMail : Titre du mail qui ne doit pas etre null
   * @param messageMail : Contenu du mail
   * @param pieceJointe : piece jointe du mail
   */
  public static void sendMail(final String sender, final String senderPassword, final String recipient, final String titleMail, final String messageMail, final String pieceJointe) {
    try {
      Objects.<String>requireNonNull(sender, "Erreur : L\'expediteur donner doit etre non Null");
      Objects.<String>requireNonNull(senderPassword, "Erreur : Le mot de passe de l\'expediteur donner doit etre non Null");
      Objects.<String>requireNonNull(recipient, "Erreur : Le destinataire donner doit etre non Null");
      Objects.<String>requireNonNull(titleMail, "Erreur : Le titre du mail ne doit pas etre Null");
      Objects.<String>requireNonNull(messageMail, "Erreur : Le message du mail ne doit pas etre Null");
      Objects.<String>requireNonNull(pieceJointe, "Erreur : La piece Jointe du mail ne doit pas etre Null");
      final Properties props = new Properties();
      final InputStream file = ResourcesUtils.getInputStreamResource("mailing/configMailFile.properties");
      props.load(file);
      file.close();
      boolean _contains = sender.contains("@");
      boolean _not = (!_contains);
      if (_not) {
        throw new Exception("L\'expediteur n\'a pas une adresse mail valide");
      }
      boolean _contains_1 = recipient.contains("@");
      boolean _not_1 = (!_contains_1);
      if (_not_1) {
        throw new Exception("Le destinataire n\'a pas une adresse mail valide");
      }
      int _indexOf = sender.indexOf("@");
      int _plus = (_indexOf + 1);
      String typeMail = sender.substring(_plus, sender.length());
      final String HOST = props.getProperty((typeMail + "Host"));
      final String PORT = props.getProperty((typeMail + "Port"));
      Objects.<String>requireNonNull(HOST, "Erreur : Le type d\'adresse mail n\'est pas présent dans le fichier configuration");
      Objects.<String>requireNonNull(PORT, "Erreur : Le port de l\'adresse mail n\'est pas présent dans le fichier configuration");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.localhost", "ScanExam");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", HOST);
      props.put("mail.smtp.port", PORT);
      final Session session = Session.getInstance(props, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(sender, senderPassword);
        }
      });
      try {
        final MimeMessage message = new MimeMessage(session);
        InternetAddress _internetAddress = new InternetAddress(sender);
        message.setFrom(_internetAddress);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(titleMail);
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(messageMail);
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        boolean _notEquals = (!com.google.common.base.Objects.equal(pieceJointe, ""));
        if (_notEquals) {
          MimeBodyPart _mimeBodyPart = new MimeBodyPart();
          messageBodyPart = _mimeBodyPart;
          FileDataSource source = new FileDataSource(pieceJointe);
          DataHandler _dataHandler = new DataHandler(source);
          messageBodyPart.setDataHandler(_dataHandler);
          messageBodyPart.setFileName(pieceJointe);
          multipart.addBodyPart(messageBodyPart);
        }
        message.setContent(multipart);
        message.setHeader("X-Mailer", "ScanExam");
        Date _date = new Date();
        message.setSentDate(_date);
        session.setDebug(true);
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
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
