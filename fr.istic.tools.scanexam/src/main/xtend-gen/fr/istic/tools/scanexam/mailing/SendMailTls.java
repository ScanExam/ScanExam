package fr.istic.tools.scanexam.mailing;

import com.sun.mail.util.MailConnectException;
import fr.istic.tools.scanexam.config.ConfigurationManager;
import fr.istic.tools.scanexam.services.api.Service;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
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
  
  private static Service service;
  
  public SendMailTls(final Service serv) {
    SendMailTls.service = serv;
  }
  
  public static void save(final File files) {
    SendMailTls.save1(files, SendMailTls.service.getExamName());
  }
  
  private static void save1(final File files, final String nom) {
    try {
      String chemin = files.getAbsolutePath();
      String nom1 = (nom + ".txt");
      PrintWriter writer = new PrintWriter(nom1, "UTF-8");
      writer.println(chemin);
      writer.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
  public static String sendMail(final String sender, final String senderPassword, final String recipient, final String titleMail, final String messageMail, final String pieceJointe) {
    return SendMailTls.sendMail1(sender, senderPassword, recipient, titleMail, messageMail, pieceJointe, SendMailTls.service.getExamName());
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
  
  public static String sendMail1(final String sender, final String senderPassword, final String recipient, final String titleMail, final String messageMail, final String pieceJointe, final String nameExam) {
    String _xblockexpression = null;
    {
      Objects.<String>requireNonNull(sender, "Erreur : L\'expediteur donner doit etre non Null");
      Objects.<String>requireNonNull(senderPassword, "Erreur : Le mot de passe de l\'expediteur donner doit etre non Null");
      Objects.<String>requireNonNull(recipient, "Erreur : Le destinataire donner doit etre non Null");
      Objects.<String>requireNonNull(titleMail, "Erreur : Le titre du mail ne doit pas etre Null");
      Objects.<String>requireNonNull(messageMail, "Erreur : Le message du mail ne doit pas etre Null");
      Objects.<String>requireNonNull(pieceJointe, "Erreur : La piece Jointe du mail ne doit pas etre Null");
      final Properties props = new Properties();
      final String host = ConfigurationManager.instance.getMailHost();
      final String port = ConfigurationManager.instance.getMailHost();
      Objects.<String>requireNonNull(host, "Erreur : Le type d\'adresse mail n\'est pas présent dans le fichier configuration");
      Objects.<String>requireNonNull(port, 
        "Erreur : Le port de l\'adresse mail n\'est pas présent dans le fichier configuration");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.localhost", "ScanExam");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", port);
      String nom = null;
      String mail = "";
      try {
        File cheminInfo = new File((nameExam + ".txt"));
        FileReader fx = new FileReader(cheminInfo);
        BufferedReader f = new BufferedReader(fx);
        String _readLine = f.readLine();
        String _plus = (_readLine + ".xls");
        File informationMail = new File(_plus);
        POIFSFileSystem doc = new POIFSFileSystem(informationMail);
        HSSFWorkbook wb = new HSSFWorkbook(doc);
        HSSFSheet sheet = wb.getSheetAt(0);
        int x = 0;
        HSSFRow row = sheet.getRow(x);
        HSSFCell cell = row.getCell(0);
        boolean trouve = false;
        while (((!com.google.common.base.Objects.equal(cell.getStringCellValue(), "")) && (!trouve))) {
          if ((com.google.common.base.Objects.equal(cell.getStringCellValue(), recipient) && 
            recipient.matches("[0-9]+"))) {
            cell = row.getCell(1);
            mail = cell.getStringCellValue();
            cell = row.getCell(2);
            nom = cell.getStringCellValue();
            trouve = true;
          } else {
            String _stringCellValue = cell.getStringCellValue();
            boolean _equals = com.google.common.base.Objects.equal(_stringCellValue, recipient);
            if (_equals) {
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
      } catch (final Throwable _t) {
        if (_t instanceof FileNotFoundException) {
          final FileNotFoundException e = (FileNotFoundException)_t;
          e.printStackTrace();
        } else if (_t instanceof IOException) {
          final IOException e_1 = (IOException)_t;
          e_1.printStackTrace();
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      boolean equals = com.google.common.base.Objects.equal(mail, "");
      String _xifexpression = null;
      if (equals) {
        _xifexpression = InputOutput.<String>println("Le numero d\'etudiant ou le nom ne correspond a aucune adresse mail");
      } else {
        final Session session = Session.getInstance(props, new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(sender, senderPassword);
          }
        });
        try {
          final MimeMessage message = new MimeMessage(session);
          InternetAddress _internetAddress = new InternetAddress(sender);
          message.setFrom(_internetAddress);
          message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
          message.setSubject(titleMail);
          MimeBodyPart messageBodyPart = new MimeBodyPart();
          messageBodyPart.setText((messageMail + nom));
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
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
