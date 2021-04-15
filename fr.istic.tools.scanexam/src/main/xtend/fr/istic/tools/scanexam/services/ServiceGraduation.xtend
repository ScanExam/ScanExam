package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.api.DataFactory
import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.GradeEntry
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.core.templates.CorrectionTemplate
import fr.istic.tools.scanexam.core.templates.CreationTemplate
import fr.istic.tools.scanexam.io.TemplateIo
import fr.istic.tools.scanexam.utils.Tuple3
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.ArrayList
import java.util.Base64
import java.util.Collection
import java.util.List
import java.util.Objects
import org.eclipse.xtend.lib.annotations.Accessors

import static fr.istic.tools.scanexam.services.ExamSingleton.*

/**
 * Classe servant de façade aux données concernant la correction
 * @author Antoine Degas, Marius Lumbroso, Théo Giraudet, Thomas Guibert
 */
class ServiceGraduation extends Service
{

	/**
	 * Index de la page courante du modèle d'exam
	 */
	protected int pageIndex 
	
	
	/**
	 * La page actuelle de l'examen
	 */
	int currentSheetIndex;
	 
	/**
	 * Question actuelle.
	 */
	int currentQuestionIndex;
	
	int gradeEntryId;
	/**
	 * Liste des copies visible.
	 */
	@Accessors Collection<StudentSheet> studentSheets;
	
	/**
	 * Fichier du template de l'édition d'examen (Fichier de méta données sur le sujet d'examen)
	 */
	CreationTemplate creationTemplate;
	
	/**
	 * Fichier du template de correction d'examen  
	 * (Fichier de méta données sur les corrections de copies déja effectués)
	 */
	CorrectionTemplate correctionTemplate;
	

	/**
	 * Sauvegarde le fichier de correction d'examen sur le disque.
	 * @param path L'emplacement de sauvegarde du fichier.
	 * @param pdfOutputStream le contenu du fichier sous forme de Stream
	 */
	def saveCorrectionTemplate(String path,ByteArrayOutputStream pdfOutputStream) 
	{
		val encoded = Base64.getEncoder().encode(pdfOutputStream.toByteArray());
		correctionTemplate.encodedDocument = new String(encoded);
		pdfOutputStream.close();

		correctionTemplate.studentsheets.clear()
		correctionTemplate.studentsheets.addAll(studentSheets);
		
		TemplateIo.save(new File(path), correctionTemplate);
	}
	
	/**
	 * Charge un fichier de correction d'examen a partir du disque.
	 * @params path L'emplacement du fichier.
	 * @returns "true" si le fichier a bien été chargé, "false"
	 */
	def boolean openCorrectionTemplate(String xmiFile)
	{
		val correctionTemplate = TemplateIo.loadCorrectionTemplate(xmiFile) 
		
		if (correctionTemplate.present) 
        {
            this.correctionTemplate = correctionTemplate.get()
            
            ExamSingleton.instance = correctionTemplate.get().exam

            return true
        }
		return false
	}
	/**
	 * Charge un fichier d'edition d'examen a partir du disque.
	 * @params path L'emplacement du fichier.
	 * @returns "true" si le fichier a bien été chargé, "false"
	 */
	def boolean openCreationTemplate(String xmiFile) {
		val editionTemplate = TemplateIo.loadCreationTemplate(xmiFile)

		if (editionTemplate.present) {
			this.creationTemplate = editionTemplate.get()

			ExamSingleton.instance = editionTemplate.get().exam

			return true
		}
		return false
	}
	
	/**
	 * Charge le document PDF des copies manuscrites,  corrigés
	 * @params path L'emplacement du fichier.
	 * @returns "true" si le fichier a bien été chargé, "false"
	 */
	def boolean initializeCorrection(Collection<StudentSheet> studentSheets) {
		try {

			for (StudentSheet sheet : studentSheets) {
				for (var i = 0; i < ExamSingleton.templatePageAmount; i++) {
					val examPage = ExamSingleton.getPage(i);

					println("test size : " + examPage.questions.size)
					for (var j = 0; j < examPage.questions.size; j++) // TODO +1?
					{
						sheet.grades.add(CoreFactory.eINSTANCE.createGrade());
					}

				}

			}
			this.studentSheets = studentSheets
			return true
		} catch (Exception ex) {
			return false;
		}

	}
	
	//TODO si les pages sont dans le désordre ?
	def int getAbsolutePageNumber(int studentId, int offset) {
		val pageId = studentSheets.findFirst[x | x.id == studentId].posPage.get(0);
		return pageId + offset;
	}
	
	//===================================================
	//          		 StudentSheet
	//===================================================
	
	/**
	 * Défini la copie d'étudiant suivant la copie actuelle comme nouvelle copie courante
	 */
	def nextSheet() {
		if (currentSheetIndex + 1 < studentSheets.size)
			currentSheetIndex++
	}

	/**
	 * Défini la copie d'étudiant précédant la copie actuelle comme nouvelle copie courante
	 */
	def previousSheet() {
		if (currentSheetIndex > 0)
			currentSheetIndex--
	}
	
	/**
	 * Associe un nouveau identifiant d'étudiant à la copie courante
	 * @param id le nouvel identifiant d'étudiant
	 */
	def assignStudentId(String id) {
		studentSheets.get(currentSheetIndex).studentName = id
	}
	
	//===================================================
	//          			 Page
	//===================================================
	
	
	/**
	 * @return l'index de la page courante du modèle d'exam
	 */
	private def getCurrentPage()
	{
		return ExamSingleton.getPage(pageIndex);
	}
	
	/**
	 * Défini la page suivant la page actuelle comme nouvelle page courante
	 */
	def nextPage() {
		if (pageIndex + 1 < ExamSingleton.instance.pages.length)
			pageIndex++
	}

	/**
	 * Défini la page précédant la page actuelle comme nouvelle page courante
	 */
	def previousPage() {
		if (pageIndex > 0) 
			pageIndex--;
	}
	
	/**
	 * @return le nombre de pages de l'Examen
	 */
	def int getPageAmount() {
		return ExamSingleton.getTemplatePageAmount
	}
	
	//===================================================
	//          		  Question
	//===================================================
	
	/**
	 * Défini la question suivant la question actuelle comme nouvelle question courante
	 */
	def nextQuestion()
	{
		if (currentQuestionIndex + 1 < currentPage.questions.size)
			currentQuestionIndex++
	}
	
	/**
	 * Défini la question précédant la question actuelle comme nouvelle question courante
	 */
	def previousQuestion() {
		if (currentQuestionIndex > 0)
			currentQuestionIndex--
	}
	
	/**
	 * Défini pour question courante la question dont l'ID est passé en paramètre si celle-ci existe, et défini pour page courante la page où se trouve cette question.<br/>
	 * Ne fait rien si la question n'existe pas 
	 * @param id un ID de question
	 */
	def selectQuestion(int id) {
		for(page: ExamSingleton.instance.pages) {
			val question = page.questions.findFirst[question | question.id == id]
			if(question !== null) {
				pageIndex = page.id
				currentQuestionIndex = question.id
			}
		}
	}
	
	
	/**
	 * @return le nombre de questions d'une copie d'étudiant
	 */
	def numberOfQuestions() {
		var nbQuestion = 0
		for (var i = 0; i < creationTemplate.exam.pages.size(); i++)
			nbQuestion += creationTemplate.exam.pages.get(i).questions.size
		nbQuestion
	}
	
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
	{
		val DataFactory factory = new DataFactory
		val question = getQuestion(questionId)
		if(question.gradeScale === null)
			question.gradeScale = factory.createGradeScale
		val scale = question.gradeScale
		scale.steps.add(factory.createGradeEntry(gradeEntryId, desc, point))
		gradeEntryId++
	}
	
	/**
	 * Modifie une entrée de la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle modifier l'entrée
	 * @param gradeEntryId l'ID de l'entrée à modifier
	 * @param desc la nouvelle description de l'entrée
	 * @param point le nouveau nombre de point de l'entrée
	 */
	def modifyEntry(int questionId, int gradeEntryId, String desc, float point) {
		val scale = getQuestion(questionId).gradeScale
		val scaleEntry = scale.steps.findFirst[step | step.id == gradeEntryId]
		if(scaleEntry !== null) {
			scaleEntry.header = desc
			scaleEntry.step = point
		}				
	}
	
	/**
	 * Supprime une entrée de la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle supprimer l'entrée
	 * @param gradeEntryId l'ID de l'entrée à supprimer
	 */
	def removeEntry(int questionId, int gradeEntryId) {
		val scale = getQuestion(questionId).gradeScale
		val scaleEntry = scale.steps.findFirst[step | step.id == gradeEntryId]
		if(scaleEntry !== null)
			scale.steps.remove(scaleEntry)
	}
	
	/**
     * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
	 * @return une liste d'ID d'entrées pour la question de l'examen dont l'ID est <i>questionId</i>
	 */
	def List<Tuple3<Integer, String, Float>> getQuestionGradeEntries(int questionId) {
		if (getQuestion(questionId).gradeScale !== null)
			return getQuestion(questionId).gradeScale.steps.map[entry | Tuple3.of(entry.id, entry.header, entry.step)]
		return List.of
	}
	

	//===========================================================
	// Manipulation d'un GradeEntry à une note d'un StudentSheet
	//===========================================================
	
	
	/**
	 * Ajoute une entrée (GradeItem) à la note d'une question d'une copie
	 * @param questionId l'ID de la question à laquelle ajouter l'entrée
	 * @param l'ID de l'entrée dans l'Examen
	 * @return boolean indique si les points on bien ete attribuer
	 */
	def boolean assignGradeEntry(int questionId, int gradeEntryId) {
		val gradeEntry = getQuestion(questionId).gradeScale.steps.findFirst[entry|entry.id == gradeEntryId]

		if (valideGradeEntry(questionId, gradeEntry)) {
			val sheet = studentSheets.get(currentSheetIndex);
			sheet.grades.get(questionId).entries.add(gradeEntry)
			return true
		} else {
			return false
		}
	}
	
	/**
	 * Retire une entrée à la note d'une question d'une copie
	 * @param questionId l'ID de la question à laquelle retirer l'entrée
	 * @param l'ID de l'entrée dans l'Examen
	 */
	def retractGradeEntry(int questionId, int gradeEntryId) {
		val entries = studentSheets.get(currentSheetIndex).grades.get(questionId).entries
		val gradeEntry = entries.findFirst[entry | entry.id == gradeEntryId]
		entries.remove(gradeEntry)
	}
	
    /**
     * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
	 * @return une liste d'ID d'entrées sélectionnées dans le StudentSheet courant pour la question dont l'ID est <i>questionId</i>
	 */
	def List<Integer> getQuestionSelectedGradeEntries(int questionId) {
		if (currentSheetIndex > studentSheets.size - 1)
			return new ArrayList<Integer>();

		val sheet = studentSheets.get(currentSheetIndex);

		if (questionId > sheet.grades.size - 1)
			return new ArrayList<Integer>();

		sheet.grades.get(questionId).entries.map[entry|entry.id]
	}

	
	//FIXME : Probleme lorsque la note maximal est modifier pour une note plus basse, risque de depacement
	/**
	 * Verification de la validiter d'une note quand on ajoute un grandEntry
	 * Elle doit respecter les condition suivant:
	 * -La note doit etre superieur a la note maximal possible
	 * -La note ne peut etre inferieur a 0
	 */
	def boolean valideGradeEntry(int questionId,GradeEntry gradeAdd){
		/*val gradeMax = getQuestion(questionId).gradeScale.maxPoint
		val gradeEntry = getQuestion(questionId).gradeScale.steps
		
		var gardeCurrent = 0.0
		for(var i = 0 ; i< gradeEntry.length-1;i++){
			gardeCurrent = gardeCurrent + gradeEntry.get(i).step
		}
		gardeCurrent+= gradeAdd.step
		
		if(gardeCurrent < gradeMax || 0 <= gardeCurrent){
			return true
		}else{
			return false
		}*/
		true //TODO FIX
	}
	
	//===================================================
	//             Informations sur la copies
	//===================================================
	
	/**
	 * @return la note maximal que peut avoir l'étudiant avec les questions auxquelles il a répondu 
	 */
	def float getCurrentMaxGrade() {
		studentSheets.get(currentSheetIndex).grades
			.indexed
			.filter[pair | !pair.value.entries.isEmpty]
			.map[pair | ExamSingleton.getQuestionFromIndex(pair.key)]
			.filter[o | !o.isEmpty]
			.map[ o | o.get.gradeScale.maxPoint]
			.reduce[acc, n | acc + n]
		
	}
	
	/**
	 * @return la note actuelle de l'étudiant courant
	 */
	def float getCurrentGrade() {
		studentSheets.get(currentSheetIndex).computeGrade
	}
	
	//===================================================
	//      Information sur la listes des étudiants
	//===================================================
	
	/**
	 * Défini le chemin d'accès vers la liste de tous les étudiants
	 * @param le chemin d'accès vers cette liste (non null)
	 */
	def setStudentListPath(String path) {
		Objects.requireNonNull(path)
		correctionTemplate.studentListPath = path
	}
	
	/**
	 * @return le chemin d'accès vers la liste de tous les étudiants. Null si ce chemin n'est pas défini
	 */
	def String getStudentListPath() {
		return correctionTemplate.studentListPath
	}
	
	/**
	 * Défini la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès
	 * @param la position initialede cette liste (non null)
	 */
	def setStudentListShift(String shift) {
		Objects.requireNonNull(shift)
		correctionTemplate.studentListShift = shift
	}
	
	/**
	 * @return la position initiale de la liste de tous les étudiants dans le fichier pointé par le chemin d'accès. 'A1' par défaut
	 */
	def String getStudentListShift() {
		return correctionTemplate.studentListShift
	}
}