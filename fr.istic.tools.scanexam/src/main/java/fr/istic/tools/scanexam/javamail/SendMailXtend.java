package fr.istic.tools.scanexam.javamail;

import fr.istic.tools.scanexam.javamail.SendMailTLS;

@SuppressWarnings("all")
public class SendMailXtend extends SendMailTLS {
  public static void main(final String[] arg) {
    final String user = "...@gmail.com";
    final String message = "Bonjour \r\n\t\tCeci est un mail envoyé par ScanExam pour prochainement pouvoir vous envoyer votre note directement par mail.\r\n\t\tÀ bientôt";
    SendMailTLS.sendMail(user, message);
  }
}
