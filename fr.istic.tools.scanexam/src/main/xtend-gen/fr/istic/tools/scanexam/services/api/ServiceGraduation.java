package fr.istic.tools.scanexam.services.api;

import fr.istic.tools.scanexam.core.GradeEntry;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.services.api.Service;
import fr.istic.tools.scanexam.utils.Tuple3;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("all")
public interface ServiceGraduation extends Service {
  /**
   * Sauvegarde le fichier de correction d'examen sur le disque.
   * @param path L'emplacement de sauvegarde du fichier.
   * @param pdfOutputStream le contenu du fichier sous forme de Stream
   */
  void saveCorrectionTemplate(final String path, final ByteArrayOutputStream pdfOutputStream);
  
  /**
   * Charge un fichier de correction d'examen a partir du disque.
   * @params path le fichier
   * @returns Un inputStream vers le PDF si le template a bien pu être chargé, Optional.empty sinon
   */
  Optional<InputStream> openCorrectionTemplate(final File xmiFile);
  
  /**
   * Charge le document PDF des copies manuscrites,  corrigés
   * @params path L'emplacement du fichier.
   * @returns "true" si le fichier a bien été chargé, "false"
   */
  boolean initializeCorrection(final Collection<StudentSheet> studentSheets);
  
  int getAbsolutePageNumber(final int studentId, final int offset);
  
  /**
   * Défini la copie d'étudiant suivant la copie actuelle comme nouvelle copie courante
   */
  void nextSheet();
  
  /**
   * Défini la copie d'étudiant précédant la copie actuelle comme nouvelle copie courante
   */
  void previousSheet();
  
  /**
   * Associe un nouveau identifiant d'étudiant à la copie courante
   * @param id le nouvel identifiant d'étudiant
   */
  void assignStudentId(final String id);
  
  /**
   * @return le nom de l'etudiant avec ID
   */
  String getStudentName(final int id);
  
  /**
   * @return la liste non modifiable de tous les StudentSheets
   */
  Collection<StudentSheet> getStudentSheets();
  
  /**
   * Défini la copie courante à l'ID spécifié si cet ID est bien un ID valide. Ne fait rien sinon
   * @param id un ID de copie d'étudiant
   */
  void selectSheet(final int id);
  
  /**
   * Défini la page suivant la page actuelle comme nouvelle page courante
   */
  void nextPage();
  
  /**
   * Défini la page précédant la page actuelle comme nouvelle page courante
   */
  void previousPage();
  
  /**
   * @return le nombre de pages de l'Examen
   */
  int getPageAmount();
  
  /**
   * Défini la question suivant la question actuelle comme nouvelle question courante
   */
  void nextQuestion();
  
  /**
   * Défini la question précédant la question actuelle comme nouvelle question courante
   */
  void previousQuestion();
  
  /**
   * Défini pour question courante la question dont l'ID est passé en paramètre si celle-ci existe, et défini pour page courante la page où se trouve cette question.<br/>
   * Ne fait rien si la question n'existe pas
   * @param id un ID de question
   */
  void selectQuestion(final int id);
  
  /**
   * @return le nombre de questions d'une copie d'étudiant
   */
  int numberOfQuestions();
  
  /**
   * Ajoute une nouvelle entrée à la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle ajouter l'entrée
   * @param desc la description de l'entrée
   * @param point le nombre de point de l'entrée
   * @return l'ID de l'entrée
   */
  int addEntry(final int questionId, final String desc, final float point);
  
  /**
   * Modifie une entrée de la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle modifier l'entrée
   * @param gradeEntryId l'ID de l'entrée à modifier
   * @param desc la nouvelle description de l'entrée
   * @param point le nouveau nombre de point de l'entrée
   */
  void modifyEntry(final int questionId, final int gradeEntryId, final String desc, final float point);
  
  /**
   * Supprime une entrée de la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle supprimer l'entrée
   * @param gradeEntryId l'ID de l'entrée à supprimer
   */
  void removeEntry(final int questionId, final int gradeEntryId);
  
  /**
   * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
   * @return une liste d'ID d'entrées pour la question de l'examen dont l'ID est <i>questionId</i>
   */
  List<Tuple3<Integer, String, Float>> getQuestionGradeEntries(final int questionId);
  
  /**
   * Ajoute une entrée (GradeItem) à la note d'une question d'une copie
   * @param questionId l'ID de la question à laquelle ajouter l'entrée
   * @param l'ID de l'entrée dans l'Examen
   * @return boolean indique si les points on bien ete attribuer
   */
  boolean assignGradeEntry(final int questionId, final int gradeEntryId);
  
  /**
   * Retire une entrée à la note d'une question d'une copie
   * @param questionId l'ID de la question à laquelle retirer l'entrée
   * @param l'ID de l'entrée dans l'Examen
   */
  void retractGradeEntry(final int questionId, final int gradeEntryId);
  
  /**
   * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
   * @return une liste d'ID d'entrées sélectionnées dans le StudentSheet courant pour la question dont l'ID est <i>questionId</i>
   */
  List<Integer> getQuestionSelectedGradeEntries(final int questionId);
  
  /**
   * Vérification de la validité d'une note lorsque l'on ajoute un grandEntry
   * @return vrai si le nouvelle note est valide, faux sinon
   * Pour être valide, la nouvelle note doit respecter les conditions suivantes :
   * <ul>
   * <li>Être inférieure ou égale à la note maximale possible pour la question</li>
   * <li>Ne pas être inferieure à 0</li>
   * </ul>
   */
  boolean validGradeEntry(final int questionId, final GradeEntry gradeAdd);
  
  /**
   * @return la note maximal que peut avoir l'étudiant avec les questions auxquelles il a répondu
   */
  float getCurrentMaxGrade();
  
  /**
   * Retourne la note actuelle de l'étudiant courant
   * @return la note actuelle de l'étudiant courant
   */
  float getCurrentGrade();
  
  /**
   * Retourne le barème total de l'examen
   * @return le barème total de l'examen
   */
  float getGlobalScale();
  
  /**
   * Défini le chemin d'accès vers la liste de tous les étudiants
   * @param le chemin d'accès vers cette liste (non null)
   */
  void setStudentListPath(final String path);
  
  /**
   * @return le chemin d'accès vers la liste de tous les étudiants. Null si ce chemin n'est pas défini
   */
  String getStudentListPath();
  
  /**
   * Défini la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès
   * @param la position initialede cette liste (non null)
   */
  void setStudentListShift(final String shift);
  
  /**
   * @return la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès. 'A1' par défaut
   */
  String getStudentListShift();
}
