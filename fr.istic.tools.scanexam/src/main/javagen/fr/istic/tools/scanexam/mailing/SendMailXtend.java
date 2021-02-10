package fr.istic.tools.scanexam.mailing;

import fr.istic.tools.scanexam.mailing.SendMailTls;

/**
 * @author Thomas Guibert
 */
@SuppressWarnings("all")
public class SendMailXtend {
  private static String sender = "A completer";
  
  private static String senderPassword = "A completer";
  
  private static String recipient = "A completer";
  
  private static String titleMail = "A completer";
  
  private static String messageMail = "A completer";
  
  private static String pieceJointe = "A completer";
  
  public static void main(final String[] arg) {
    SendMailTls.sendMail(SendMailXtend.getSender(), SendMailXtend.getSenderPassword(), SendMailXtend.getRecipent(), SendMailXtend.getTitle(), SendMailXtend.getMessage(), SendMailXtend.getPieceJointe());
  }
  
  /**
   * Fonction envoie du mail avec les valeur donnée par les getter
   */
  public static void sendMailXtend() {
    SendMailTls.sendMail(SendMailXtend.getSender(), SendMailXtend.getSenderPassword(), SendMailXtend.getRecipent(), SendMailXtend.getTitle(), SendMailXtend.getMessage(), SendMailXtend.getPieceJointe());
  }
  
  /**
   * Liste des getter et setter pour modifier est acceder au parametre
   */
  public static String getSender() {
    return SendMailXtend.sender;
  }
  
  public static String setSender(final String send) {
    return SendMailXtend.sender = send;
  }
  
  public static String getSenderPassword() {
    return SendMailXtend.senderPassword;
  }
  
  public static String setSenderPassword(final String pass) {
    return SendMailXtend.senderPassword = pass;
  }
  
  public static String getRecipent() {
    return SendMailXtend.recipient;
  }
  
  public static String setRecipent(final String recip) {
    return SendMailXtend.recipient = recip;
  }
  
  public static String getTitle() {
    return SendMailXtend.titleMail;
  }
  
  public static String setTitle(final String title) {
    return SendMailXtend.titleMail = title;
  }
  
  public static String getMessage() {
    return SendMailXtend.messageMail;
  }
  
  public static String setMessage(final String message) {
    return SendMailXtend.messageMail = message;
  }
  
  public static String getPieceJointe() {
    return SendMailXtend.pieceJointe;
  }
  
  public static String setPieceJointe(final String piece) {
    return SendMailXtend.pieceJointe = piece;
  }
}
