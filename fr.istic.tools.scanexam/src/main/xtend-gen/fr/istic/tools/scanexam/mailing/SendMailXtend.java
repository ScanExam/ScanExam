package fr.istic.tools.scanexam.mailing;

import fr.istic.tools.scanexam.mailing.SendMailTls;

/**
 * @author Thomas Guibert
 */
@SuppressWarnings("all")
public class SendMailXtend {
  private static String sender = "";
  
  private static String senderPassword = "";
  
  private static String recipient = "";
  
  private static String titleMail = "";
  
  private static String messageMail = "";
  
  private static String pieceJointe = "";
  
  public static void main(final String[] arg) {
    SendMailTls.sendMail1("testscanexam@gmail.com", "azerty35!", "Arthur Lalande-Marchand", SendMailXtend.titleMail, SendMailXtend.messageMail, SendMailXtend.pieceJointe, "test2_pfo");
  }
  
  /**
   * Fonction envoie du mail avec les valeur donnée par les getter
   */
  public String sendMailXtend() {
    return SendMailTls.sendMail(SendMailXtend.sender, SendMailXtend.senderPassword, SendMailXtend.recipient, SendMailXtend.titleMail, SendMailXtend.messageMail, SendMailXtend.pieceJointe);
  }
  
  /**
   * Liste des getter et setter pour modifier est acceder au parametre
   */
  public String getSender() {
    return SendMailXtend.sender;
  }
  
  public String setSender(final String send) {
    return SendMailXtend.sender = send;
  }
  
  public String getSenderPassword() {
    return SendMailXtend.senderPassword;
  }
  
  public String setSenderPassword(final String pass) {
    return SendMailXtend.senderPassword = pass;
  }
  
  public String getRecipent() {
    return SendMailXtend.recipient;
  }
  
  public String setRecipent(final String recip) {
    return SendMailXtend.recipient = recip;
  }
  
  public String getTitle() {
    return SendMailXtend.titleMail;
  }
  
  public String setTitle(final String title) {
    return SendMailXtend.titleMail = title;
  }
  
  public String getMessage() {
    return SendMailXtend.messageMail;
  }
  
  public String setMessage(final String message) {
    return SendMailXtend.messageMail = message;
  }
  
  public String getPieceJointe() {
    return SendMailXtend.pieceJointe;
  }
  
  public String setPieceJointe(final String piece) {
    return SendMailXtend.pieceJointe = piece;
  }
}
