package fr.istic.tools.scanexam.qrCode.reader;

import fr.istic.tools.scanexam.core.StudentSheet;
import java.util.Collection;

@SuppressWarnings("all")
public interface PdfReader {
  /**
   * Lit le PDF spécifié
   */
  boolean readPDf();
  
  /**
   * Renvoie la collection des copies complètes uniquement au format de l'API
   * //TODO voir ce qu'on fait avec les copies pas complètes
   * @return la collection des copies complètes au format de l'API
   */
  Collection<StudentSheet> getStudentSheets();
  
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
}
