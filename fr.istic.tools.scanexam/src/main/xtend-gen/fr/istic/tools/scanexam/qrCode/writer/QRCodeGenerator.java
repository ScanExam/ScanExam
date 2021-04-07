package fr.istic.tools.scanexam.qrCode.writer;

import java.io.InputStream;

@SuppressWarnings("all")
public interface QRCodeGenerator {
  /**
   * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
   * 
   * @param inputFile Chemin du sujet maitre
   * @param nbCopies Nombre de copies de l'examen souhaité
   * 
   * @return true si l'opération s'est bien déroulée
   */
  boolean createAllExamCopies(final InputStream inputFile, final int nbCopie);
}
