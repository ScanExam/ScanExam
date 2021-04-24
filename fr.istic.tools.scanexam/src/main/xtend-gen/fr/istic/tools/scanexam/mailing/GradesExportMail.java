package fr.istic.tools.scanexam.mailing;

import fr.istic.tools.scanexam.core.StudentSheet;
import java.io.File;
import java.util.Collection;

/**
 * Interface pour l'envoie des notes par mail
 * @author Degas
 */
@SuppressWarnings("all")
public interface GradesExportMail {
  /**
   * Méthode permettant d'envoyer les notes par mail
   * @param path chemin d'accès au fichier XMI permettant de faire la correspondance nom de l'élève -> Adresse mail
   */
  void exportGradesMail(final File pdf);
  
  void exportGradesMail1(final File pdf, final String mail, final String mdp, final int taille, final String nameExam, final Collection<StudentSheet> studentSheets);
}
