package fr.istic.tools.scanexam.qrCode;

/**
 * Classe décrivant les différents types de qr codes
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class QrCodeType {
  /**
   * Qr code contenant l'identifiant de la copie et le numéro de la page
   */
  public static final int SHEET_PAGE = 0;
  
  /**
   * Qr code contenant le numéro de la page uniquement
   */
  public static final int PAGE = 1;
  
  /**
   * Qr code contenant l'identifiant de l'étudiant
   */
  public static final int STUDENT = 2;
}
