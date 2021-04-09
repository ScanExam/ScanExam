package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.api.DataFactory
import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.Grade
import fr.istic.tools.scanexam.core.GradeEntry
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.core.templates.CorrectionTemplate
import fr.istic.tools.scanexam.core.templates.CreationTemplate
import fr.istic.tools.scanexam.io.TemplateIo
import fr.istic.tools.scanexam.utils.Tuple3
import java.util.ArrayList
import java.util.Collection
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors

import static fr.istic.tools.scanexam.services.ExamSingleton.*

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
	 * @params path L'emplacement de sauvegarde du fichier.
	 */
	def saveCorrectionTemplate(String path) 
	{
		// TODO (sauvegarde le XMI de correction)
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
	def boolean openCreationTemplate(String xmiFile) 
	{
		val editionTemplate = TemplateIo.loadCreationTemplate(xmiFile) 
		
		if (editionTemplate.present) 
        {
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
	def boolean initializeCorrection(Collection<StudentSheet> studentSheets)
	{
		try
		{
		
        	
        	var index =0;
        	for (StudentSheet sheet : studentSheets)
        	{
        		val examPage = ExamSingleton.getPage(index);
        		
        		index ++;
        		
        		for (var i = 0;i< examPage.questions.size+1;i++)
        		{
        			sheet.grades.add(CoreFactory.eINSTANCE.createGrade());
        		}
        		
        	}
        	
        	return true
		}
		catch (Exception ex)
		{
			return false;
		}
       
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
	
	
	def numberOfQuestions ()
	{
		var nbQuestion = 0
		for (var i = 0 ; i < creationTemplate.exam.pages.size(); i++){
			nbQuestion += creationTemplate.exam.pages.get(i).questions.size
		}
		nbQuestion
	}
	
	def int getAbsolutePageNumber(int studentId,int offset)
	{
		val pageId = studentSheets.findFirst[x | x.id == studentId].posPage.get(0);
		print("\nPageid = " + (pageId + offset));
		return pageId  + offset;
	}
	
	
	
	/**
	 * Passe au prochaine etudiant dans les StudentSheet
	 */
	def nextStudent() 
	{
		if (currentSheetIndex+1 < studentSheets.size)
			currentSheetIndex++
	}
	
	/**
	 * Passe au etudiant précédent dans les StudentSheet
	 */
	def previousStudent() 
	{
		if (currentSheetIndex > 0)
			currentSheetIndex--
	}
	
	def nextPage()
	{
		if (pageIndex + 1 < ExamSingleton.instance.pages.length) 
		{
			pageIndex++
		}
	}
	def previousPage() 
	{
		 if (pageIndex > 0) 
		 {
		 	pageIndex--;
		 }
	}
	
	/**
	 * Renomme l'étudiant
	 * @param name le nouveau nom de l'étudiant
	 */
	def renameStudent(String name) {
		studentSheets.get(currentSheetIndex).studentName = name
	}
	
	
	/**
	 * Index de la page courante du modèle d'exam
	 */
	protected def getCurrentPage()
	{
		return ExamSingleton.getPage(pageIndex);
	}
	def getCurrentQuestion()
	{
		return currentPage.questions.findFirst[x | x.id == currentQuestionIndex];
	}
	
	def nextQuestion()
	{
		if (currentQuestionIndex + 1 < currentPage.questions.size)
			currentQuestionIndex++
	}
	
	def previousQuestion() 
	{
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
	
	
	def indexOfQuestions (int indexpage , int indexquestion){
		var indexQuestion =0
		for (var i = 0 ; i < indexpage-1 ; i++){
			indexQuestion += creationTemplate.exam.pages.get(i).questions.size
		}
		indexQuestion += indexquestion
		indexQuestion
	}
	
	/**
	 * Ajoute une (n as GradeItem)entrée à la note d'une question d'une copie
	 * @param questionId l'ID de la question à laquelle ajouter l'entrée
	 * @param l'ID de l'entrée dans l'Examen
	 * @return boolean indique si les points on bien ete attribuer
	 */
	def boolean addGradeEntry(int questionId, int gradeEntryId) 
	{
		val gradeEntry = getQuestion(questionId).gradeScale.steps.findFirst[entry | entry.id == gradeEntryId]
		
		if(valideGradeEntry(questionId,gradeEntry))
		{
			val sheet = studentSheets.get(currentSheetIndex);
			
			
			sheet.grades.get(questionId).entries.add(gradeEntry)
			return true
		}
		else
		{
			return false
		}
	}
	
	/**
	 * Retire une entrée à la note d'une question d'une copie
	 * @param questionId l'ID de la question à laquelle retirer l'entrée
	 * @param l'ID de l'entrée dans l'Examen
	 */
	def removeGradeEntry(int questionId, int gradeEntryId) {
		val entries = studentSheets.get(currentSheetIndex).grades.get(questionId).entries
		val gradeEntry = entries.findFirst[entry | entry.id == gradeEntryId]
		entries.remove(gradeEntry)
	}
	
    /**
     * @param l'ID de la question à laquelle récupérer la liste d'entrées
	 * @return une liste d'ID d'entrées sélectionnées dans le StudentSheet courant pour la question dont l'ID est <i>questionId</i>
	 */
	def List<Integer> getQuestionSelectedGradeEntries(int questionId) 
	{
		if (currentSheetIndex > studentSheets.size - 1)
		 {
		 	return new ArrayList<Integer>();
		 }
		 
		 val sheet = studentSheets.get(currentSheetIndex);
		 
		 if (questionId > sheet.grades.size-1)
		 {
		 	return new ArrayList<Integer>();
		 }
		 
		sheet.grades.get(questionId).entries.map[entry | entry.id]
	}
	
	/**
     * @param l'ID de la question à laquelle récupérer la liste d'entrées
	 * @return une liste d'ID d'entrées pour la question de l'examen dont l'ID est <i>questionId</i>
	 */
	def List<Tuple3<Integer, String, Float>> getQuestionGradeEntries(int questionId) {
		if (getQuestion(questionId).gradeScale !== null)//TODO F
			return getQuestion(questionId).gradeScale.steps.map[entry | Tuple3.of(entry.id, entry.header, entry.step)]
		return List.of
	}
	
	/**
	 * Ajoute la note a la question courante
	 */
	def setGrade (Grade note){
		studentSheets.get(currentSheetIndex).grades.set(indexOfQuestions(pageIndex,currentQuestionIndex), note);
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
	
	
	
	
}