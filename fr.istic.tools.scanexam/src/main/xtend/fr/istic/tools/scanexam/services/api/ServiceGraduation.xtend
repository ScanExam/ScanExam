package fr.istic.tools.scanexam.services.api

import fr.istic.tools.scanexam.core.GradeEntry
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.utils.Tuple3
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.Collection
import java.util.List
import java.util.Map
import java.util.Optional
import fr.istic.tools.scanexam.core.Question
import javafx.util.Pair

interface ServiceGraduation extends Service {

	/**
	 * Sauvegarde le fichier de correction d'examen sur le disque.
	 * @param path L'emplacement de sauvegarde du fichier.
	 * @param pdfOutputStream le contenu du fichier sous forme de Stream
	 */
	def void saveCorrectionTemplate(String path, ByteArrayOutputStream pdfOutputStream)

	/**
	 * Charge un fichier de correction d'examen a partir du disque.
	 * @params path le fichier
	 * @returns Un inputStream vers le PDF si le template a bien pu être chargé, Optional.empty sinon
	 */
	def Optional<InputStream> openCorrectionTemplate(File xmiFile)

	/**
	 * Crée une nouvelle correction à partir d'une liste de StudentSheets
	 * @param studentSheets une liste de StudenSheet
	 * @param failedPages la liste des pages mal lues
	 * @param uncompleteStudentSheets les studentSheets incomplètes
	 * @returns "true" si la correction a pu être créée, "false" sinon
	 */
	def boolean initializeCorrection(Collection<StudentSheet> studentSheets, Collection<Integer> failedPages, Collection<StudentSheet> uncompleteStudentSheets)

	def int getAbsolutePageNumber(int studentId, int offset)

	// ===================================================
	// StudentSheet
	// ===================================================
	/**
	 * Définit la copie d'étudiant suivant la copie actuelle comme nouvelle copie courante
	 * Si la copie courante est la dernière, ne fait rien
	 */
	def void nextSheet()

	/**
	 * Définit la copie d'étudiant précédant la copie actuelle comme nouvelle copie courante
	 * Si la copie courante est la première, ne fait rien
	 */
	def void previousSheet()

	/**
	 * Associe un nouveau identifiant d'étudiant à la copie courante
	 * @param id le nouvel identifiant d'étudiant
	 */
	def void assignStudentName(String id)

	/**
	 * @return le nom de l'etudiant dont l'ID de la copie est id si la copie existe, Optional.empty sinon
	 * @param id l'ID de la copie
	 */
	def Optional<String> getStudentName(int id)

	/**
	 * @return la liste non modifiable de tous les StudentSheets
	 */
	def Collection<StudentSheet> getStudentSheets()
	
	/**
	 * ajoute une page en plus dans une copie
	 * @param id de la copie
	 * @param numéro de la page à ajouter
	 */
	 def void addPageInStudentSheet(int id, int page)

	/**
	 * Définit la copie courante à l'ID spécifié si cet ID est bien un ID valide. Ne fait rien sinon
	 * @param id un ID de copie d'étudiant
	 */
	def void selectSheet(int id)

	// ===================================================
	// Page
	// ===================================================
	/**
	 * @return le nombre de pages de l'Examen
	 */
	def int getPageAmount()

	/**
	 * @return les pages qui n'ont pas été détectées avec un QRCode
	 */
	def Collection<Integer> getFailedPages()

	// ===================================================
	// Question
	// ===================================================
	/**
	 * @return le nombre de questions d'une copie d'étudiant
	 */
	def int numberOfQuestions()

	// ===================================================
	// GradeEntry
	// ===================================================
	/**
	 * Ajoute une nouvelle entrée à la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle ajouter l'entrée
	 * @param desc la description de l'entrée
	 * @param point le nombre de point de l'entrée
	 * @return l'ID de l'entrée si celle-ci a pu être créée, Optional.empty sinon
	 */
	def Optional<Integer> addEntry(int questionId, String desc, float point)

	/**
	 * Modifie une entrée de la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle modifier l'entrée
	 * @param gradeEntryId l'ID de l'entrée à modifier
	 * @param desc la nouvelle description de l'entrée
	 * @param point le nouveau nombre de point de l'entrée
	 */
	def void modifyEntry(int questionId, int gradeEntryId, String desc, float point)

	/**
	 * Supprime une entrée de la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle supprimer l'entrée
	 * @param gradeEntryId l'ID de l'entrée à supprimer
	 */
	def void removeEntry(int questionId, int gradeEntryId)

	/**
	 * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
	 * @return une liste d'ID d'entrées pour la question de l'examen dont l'ID est <i>questionId</i>
	 */
	def List<Tuple3<Integer, String, Float>> getQuestionGradeEntries(int questionId)

	// ===========================================================
	// Manipulation d'un GradeEntry à une note d'un StudentSheet
	// ===========================================================
	/**
	 * Ajoute une entrée (GradeItem) à la note d'une question d'une copie si la valeur de l'entrée ne fait pas dépasser la note du barême de la question
	 * @param questionId l'ID de la question à laquelle ajouter l'entrée
	 * @param l'ID de l'entrée dans l'Examen
	 * @return boolean indique si les points ont bien été attribués
	 */
	def boolean assignGradeEntry(int questionId, int gradeEntryId)

	/**
	 * Retire une entrée à la note d'une question d'une copie
	 * @param questionId l'ID de la question à laquelle retirer l'entrée
	 * @param l'ID de l'entrée dans l'Examen
	 */
	def boolean retractGradeEntry(int questionId, int gradeEntryId)

	/**
	 * Retourne la position de la zone du qr code
	 */
	def Optional<Pair<Float, Float>> getQrCodePosition()

	/**
	 * @param l'ID de la Question
	 * @return la Question du modèle correspondant à l'ID spécifié
	 */
	def Question getQuestion(int id)

	/**
	 * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
	 * @return une liste d'ID d'entrées sélectionnées dans le StudentSheet courant pour la question dont l'ID est <i>questionId</i>
	 */
	def List<Integer> getQuestionSelectedGradeEntries(int questionId)

	def double getQuestionSelectedGradeEntriesTotalWorth(int questionId)

	/**
	 * Vérification de la validité d'une note lorsque l'on ajoute un grandEntry
	 * @return vrai si le nouvelle note est valide, faux sinon
	 * Pour être valide, la nouvelle note doit respecter les conditions suivantes :
	 * <ul>
	 * <li>Être inférieure ou égale à la note maximale possible pour la question</li>
	 * <li>Ne pas être inferieure à 0</li>
	 * </ul>
	 */
	def boolean validGradeEntry(int questionId, GradeEntry gradeAdd, boolean removal)

	// ===================================================
	// Informations sur la copie
	// ===================================================
	/**
	 * @return la note maximal que peut avoir l'étudiant avec les questions auxquelles il a répondu 
	 */
	def float getCurrentMaxGrade()

	/**
	 * Retourne la note actuelle de l'étudiant courant
	 * @return la note actuelle de l'étudiant courant
	 */
	def float getCurrentGrade()

	/**
	 * Retourne le barème total de l'examen
	 * @return le barème total de l'examen
	 */
	def float getGlobalScale()

	// ===================================================
	// Information sur la listes des étudiants
	// ===================================================
	/**
	 * Définit la liste des informations des étudiants (non null)
	 * @param informations une Map Nom de l'étudiant -> adresse mail de l'étudiant
	 */
	def void setStudentInfos(Map<String, String> informations)

	/**
	 * @return l'ensemble de tous les noms des étudiants chargés
	 */
	def Collection<String> getStudentNames()

	/**
	 * @return une Map contenant les informations des étudiants : Nom de l'étudiant -> adresse mail de l'étudiant
	 */
	def Map<String, String> getStudentInfos()

	// ===================================================
	// Annotations
	// ===================================================
	/**
	 * Ajoute une annotation sur la page donee et l'etudiant donne au modele, lui genere une id et la retourne
	 * @param The details of the new Annotation
	 * @return l'id de l'annotation cree
	 */
	def int addNewAnnotation(double x, double y, double width, double height, double pointerX, double pointerY,
		String text, int questionId, int studentId)

	def void updateAnnotation(double x, double y, double width, double height, double pointerX, double pointerY,
		String text, int annotationId, int questionId, int studentId)

	/**
	 * @param pageNumber : Le numero de la page du template.
	 * 		studentId : l'id de l'etudiant pour lequel on veut obtenir les ids
	 * @returns une liste des id des annotations
	 */
	def List<Integer> getAnnotationIds(int questionId, int studentId)

	/**
	 * Retourne le texte pour l'annotations avec l'id donne
	 */
	def String getAnnotationText(int annotationId, int questionId, int studentId)

	/**
	 * Retourne la postion X pour l'annotations avec l'id donne
	 */
	def double getAnnotationX(int annotationId, int questionId, int studentId)

	/**
	 * Retourne le  postion Y pour l'annotations avec l'id donne
	 */
	def double getAnnotationY(int annotationId, int questionId, int studentId)

	/**
	 * Retourne la hauteur pour l'annotations avec l'id donne
	 */
	def double getAnnotationHeight(int annotationId, int questionId, int studentId)

	/**
	 * Retourne la largeur pour l'annotations avec l'id donne
	 */
	def double getAnnotationWidth(int annotationId, int questionId, int studentId)

	/**
	 * Retourne la postion X pour le pointer de l'annotations avec l'id donne
	 */
	def double getAnnotationPointerX(int annotationId, int questionId, int studentId)

	/**
	 * Retourne la postion Y pour le pointer de l'annotations avec l'id donne
	 */
	def double getAnnotationPointerY(int annotationId, int questionId, int studentId)

	/**
	 * Retire une annotation du modele
	 */
	def void removeAnnotation(int annotationId, int questionId, int studentId)

}
