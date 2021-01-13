package fr.istic.tools.scanexam.javamail;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class SendMailTLS {
  public static String sendMail(final String user, final String msgText) {
    String _xblockexpression = null;
    {
      final String username = "...@gmail.com";
      final String password = "...";
      final Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.localhost", "test");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");
      final Session session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
        }
      });
      String _xtrycatchfinallyexpression = null;
      try {
        String _xblockexpression_1 = null;
        {
          final MimeMessage message = new MimeMessage(session);
          InternetAddress _internetAddress = new InternetAddress(user);
          message.setFrom(_internetAddress);
          message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user));
          message.setSubject("ScanExam Envoie");
          message.setText(msgText);
          message.setHeader("X-Mailer", "Java");
          Date _date = new Date();
          message.setSentDate(_date);
          session.setDebug(true);
          Transport.send(message);
          _xblockexpression_1 = InputOutput.<String>println("Message envoyé !");
        }
        _xtrycatchfinallyexpression = _xblockexpression_1;
      } catch (final Throwable _t) {
        if (_t instanceof MessagingException) {
          final MessagingException e = (MessagingException)_t;
          throw new RuntimeException(e);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _xblockexpression = _xtrycatchfinallyexpression;
    }
    return _xblockexpression;
  }
}
