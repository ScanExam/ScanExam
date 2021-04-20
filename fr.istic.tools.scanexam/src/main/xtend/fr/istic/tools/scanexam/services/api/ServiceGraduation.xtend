package fr.istic.tools.scanexam.services.api

import fr.istic.tools.scanexam.core.GradeEntry
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.utils.Tuple3
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.Collection
import java.util.List
import java.util.Optional

interface ServiceGraduation extends Service {
	

	/**
	 * Sauvegarde le fichier de correction d'examen sur le disque.
	 * @param path L'emplacement de sauvegarde du fichier.
	 * @param pdfOutputStream le contenu du fichier sous forme de Stream
	 */
	def void saveCorrectionTemplate(String path,ByteArrayOutputStream pdfOutputStream) 
	
	/**
	 * Charge un fichier de correction d'examen a partir du disque.
	 * @params path le fichier
	 * @returns Un inputStream vers le PDF si le template a bien pu être chargé, Optional.empty sinon
	 */
	def Optional<InputStream> openCorrectionTemplate(File xmiFile)

	/**
	 * Charge le document PDF des copies manuscrites,  corrigés
	 * @params path L'emplacement du fichier.
	 * @returns "true" si le fichier a bien été chargé, "false"
	 */
	def boolean initializeCorrection(Collection<StudentSheet> studentSheets)
	
	
	
	def int getAbsolutePageNumber(int studentId, int offset)
	
	//===================================================
	//          		 StudentSheet
	//===================================================
	
	/**
	 * Défini la copie d'étudiant suivant la copie actuelle comme nouvelle copie courante
	 */
	def void nextSheet()

	/**
	 * Défini la copie d'étudiant précédant la copie actuelle comme nouvelle copie courante
	 */
	def void previousSheet()
	
	/**
	 * Associe un nouveau identifiant d'étudiant à la copie courante
	 * @param id le nouvel identifiant d'étudiant
	 */
	def void assignStudentId(String id)
	
	/**
	 * @return la liste non modifiable de tous les StudentSheets
	 */
	def Collection<StudentSheet> getStudentSheets()
	
	/**
	 * Défini la copie courante à l'ID spécifié si cet ID est bien un ID valide. Ne fait rien sinon
	 * @param id un ID de copie d'étudiant
	 */
	def void selectSheet(int id)
	
	//===================================================
	//          			 Page
	//===================================================
	
	
	/**
	 * Défini la page suivant la page actuelle comme nouvelle page courante
	 */
	def void nextPage()

	/**
	 * Défini la page précédant la page actuelle comme nouvelle page courante
	 */
	def void previousPage()
	
	/**
	 * @return le nombre de pages de l'Examen
	 */
	def int getPageAmount()
	
	//===================================================
	//          		  Question
	//===================================================
	
	/**
	 * Défini la question suivant la question actuelle comme nouvelle question courante
	 */
	def void nextQuestion()
	
	/**
	 * Défini la question précédant la question actuelle comme nouvelle question courante
	 */
	def void previousQuestion()
	
	/**
	 * Défini pour question courante la question dont l'ID est passé en paramètre si celle-ci existe, et défini pour page courante la page où se trouve cette question.<br/>
	 * Ne fait rien si la question n'existe pas 
	 * @param id un ID de question
	 */
	def void selectQuestion(int id)
	
	
	/**
	 * @return le nombre de questions d'une copie d'étudiant
	 */
	def int numberOfQuestions()
	
	//===================================================
	//                   GradeEntry
	//===================================================
	
	/**
	 * Ajoute une nouvelle entrée à la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle ajouter l'entrée
	 * @param desc la description de l'entrée
	 * @param point le nombre de point de l'entrée
	 * @return l'ID de l'entrée
	 */
	def int addEntry(int questionId, String desc, float point) 

	
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
	

	//===========================================================
	// Manipulation d'un GradeEntry à une note d'un StudentSheet
	//===========================================================
	
	
	/**
	 * Ajoute une entrée (GradeItem) à la note d'une question d'une copie
	 * @param questionId l'ID de la question à laquelle ajouter l'entrée
	 * @param l'ID de l'entrée dans l'Examen
	 * @return boolean indique si les points on bien ete attribuer
	 */
	def boolean assignGradeEntry(int questionId, int gradeEntryId)
	
	/**
	 * Retire une entrée à la note d'une question d'une copie
	 * @param questionId l'ID de la question à laquelle retirer l'entrée
	 * @param l'ID de l'entrée dans l'Examen
	 */
	def void retractGradeEntry(int questionId, int gradeEntryId)
	
    /**
     * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
	 * @return une liste d'ID d'entrées sélectionnées dans le StudentSheet courant pour la question dont l'ID est <i>questionId</i>
	 */
	def List<Integer> getQuestionSelectedGradeEntries(int questionId)

	
	/**
	 * Vérification de la validité d'une note lorsque l'on ajoute un grandEntry
	 * @return vrai si le nouvelle note est valide, faux sinon
	 * Pour être valide, la nouvelle note doit respecter les conditions suivantes :
	 * <ul>
	 * <li>Être inférieure ou égale à la note maximale possible pour la question</li>
	 * <li>Ne pas être inferieure à 0</li>
	 * </ul>
	 */
	def boolean validGradeEntry(int questionId,GradeEntry gradeAdd)
	
	//===================================================
	//             Informations sur la copies
	//===================================================
	
	/**
	 * @return la note maximal que peut avoir l'étudiant avec les questions auxquelles il a répondu 
	 */
	def float getCurrentMaxGrade()
	
	/**
	 * @return la note actuelle de l'étudiant courant
	 */
	def float getCurrentGrade()
	
	//===================================================
	//      Information sur la listes des étudiants
	//===================================================
	
	/**
	 * Défini le chemin d'accès vers la liste de tous les étudiants
	 * @param le chemin d'accès vers cette liste (non null)
	 */
	def void setStudentListPath(String path)
	
	/**
	 * @return le chemin d'accès vers la liste de tous les étudiants. Null si ce chemin n'est pas défini
	 */
	def String getStudentListPath()
	
	/**
	 * Défini la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès
	 * @param la position initialede cette liste (non null)
	 */
	def void setStudentListShift(String shift)
	
	/**
	 * @return la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès. 'A1' par défaut
	 */
	def String getStudentListShift()

}