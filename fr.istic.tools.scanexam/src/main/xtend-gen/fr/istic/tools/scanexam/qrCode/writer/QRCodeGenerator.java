package fr.istic.tools.scanexam.qrCode.writer;

import fr.istic.tools.scanexam.core.QrCodeZone;
import java.io.File;
import java.io.InputStream;

@SuppressWarnings("all")
public interface QRCodeGenerator {
  /**
   * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
   * 
   * @param inputFile Chemin du sujet maitre
   * @param outputPath chemin de sortie
   * @param qrCodeZone zone sur le document où insérer le qrcode
   * @param idExam l'id de l'examen
   * @param nbCopies Nombre de copies de l'examen souhaité
   */
  void createAllExamCopies(final InputStream inputFile, final File outputPath, final QrCodeZone qrCodeZone, final String idExam, final int nbCopie);
  
  boolean isFinished();
  
  int getNbTreated();
  
  int getNumberPagesAllSheets();
}
