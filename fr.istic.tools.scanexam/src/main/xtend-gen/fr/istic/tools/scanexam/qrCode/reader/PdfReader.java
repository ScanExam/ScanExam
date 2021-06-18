package fr.istic.tools.scanexam.qrCode.reader;

import fr.istic.tools.scanexam.core.StudentSheet;
import java.util.Collection;

/**
 * Lis le pdf passé au constructeur et construit le modèle
 * @author Romain et Luca
 */
@SuppressWarnings("all")
public interface PdfReader {
  /**
   * Lit le PDF spécifié
   * @return true ssi la lecture du PDF est réussie
   */
  boolean readPDf();
  
  /**
   * Renvoie la collection des copies complètes uniquement au format de l'API
   * @return la collection des copies complètes au format de l'API
   */
  Collection<StudentSheet> getCompleteStudentSheets();
  
  /**
   * Renvoie la collection des copies incomplètes uniquement au format de l'API
   * @return la collection des copies incomplètes au format de l'API
   */
  Collection<StudentSheet> getUncompleteStudentSheets();
  
  /**
   * Renvoie le nombre de total de pages du PDF de toutes les copies
   * @return le nombre de pages du PDF source
   */
  int getNbPagesPdf();
  
  /**
   * Renvoie le nombre de pages traitées par la lecture du PDF
   * @return le nombre de pages que le reader a lu du PDF source
   */
  int getNbPagesTreated();
  
  /**
   * @return true si la lecture du PDF est terminée, false sinon
   */
  boolean isFinished();
  
  /**
   * Renvoie la collection du numéro des pages du document des copies qui n'ont pas été détectées
   * comme étant marquées avec un QRCode
   * @return la collection du numéro des pages qui n'ont pas été détectées avec un QRCode
   */
  Collection<Integer> getFailedPages();
}
