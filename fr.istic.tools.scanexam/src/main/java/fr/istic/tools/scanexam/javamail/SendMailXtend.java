package fr.istic.tools.scanexam.javamail;

import fr.istic.tools.scanexam.javamail.SendMailTLS;

@SuppressWarnings("all")
public class SendMailXtend extends SendMailTLS {
  public static void main(final String[] arg) {
    final String user = "...@gmail.com";
    final String message = "Bonjour \r\n\t\t\n\n Ceci est un mail envoyer par ScanExam pour prochainement pouvoir vous envoyer votre note directement par mail.\r\n\t\t\n\n A bientôt";
    SendMailTLS.sendMail(user, message);
  }
}
