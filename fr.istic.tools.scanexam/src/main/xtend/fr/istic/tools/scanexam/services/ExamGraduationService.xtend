package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.Grade
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.core.templates.CorrectionTemplate
import fr.istic.tools.scanexam.core.templates.CreationTemplate
import fr.istic.tools.scanexam.io.TemplateIO
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl
import fr.istic.tools.scanexam.utils.Tuple3
import java.io.File
import java.util.Collection
import java.util.List
import org.apache.pdfbox.pdmodel.PDDocument
import org.eclipse.xtend.lib.annotations.Accessors

import static fr.istic.tools.scanexam.services.ExamSingleton.*

class ExamGraduationService extends Service
{
	int currentSheetIndex;
	 
	int currentQuestionIndex;
	
	@Accessors Collection<StudentSheet> studentSheets;
	
	CreationTemplate creationTemplate;
	
	CorrectionTemplate correctionTemplate;
	
	//Set<StudentSheet> visibleSheets;
	
	override save(String path) 
	{
		// TODO (sauvegarde le XMI de correction)
	}
	
	def boolean openCorrectionTemplate(String xmiFile)
	{
		val correctionTemplate = TemplateIO.loadCorrectionTemplate(xmiFile) 
		
		if (correctionTemplate.present) 
        {
            this.correctionTemplate = correctionTemplate.get()
            
            ExamSingleton.instance = correctionTemplate.get().exam

            return true
        }
		return false
	}
	def boolean openCreationTemplate(String xmiFile) 
	{
		val editionTemplate = TemplateIO.loadCreationTemplate(xmiFile) 
		
		if (editionTemplate.present) 
        {
            this.creationTemplate = editionTemplate.get()
            
            ExamSingleton.instance = editionTemplate.get().exam

            return true
        }
		return false
	}
	
	def boolean openCorrectionPdf(String path)
	{
		
        document = PDDocument.load(new File(path))
        val pdfReader = new PdfReaderWithoutQrCodeImpl(document,ExamSingleton.instance.pages.size,3); // TODO
        pdfReader.readPDf();
        studentSheets = pdfReader.completeStudentSheets
      	
        return true
	}
	
	
	
	def numberOfQuestions ()
	{
		var nbQuestion =0
		for (var i = 0 ; i < document.pages.size-1; i++){
			nbQuestion += creationTemplate.exam.pages.get(i).questions.size
		}
		nbQuestion
	}
	
	def int getAbsolutePageNumber(int studentId,int offset)
	{
		val pageId = studentSheets.findFirst[x | x.id == studentId].posPage.get(0);
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
	
	
	/**
	 * Renomme l'étudiant
	 * @param name le nouveau nom de l'étudiant
	 */
	def renameStudent(String name) {
		studentSheets.get(currentSheetIndex).studentName = name
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
	
	
	def indexOfQuestions (int indexpage , int indexquestion){
		var indexQuestion =0
		for (var i = 0 ; i < indexpage-1 ; i++){
			indexQuestion += creationTemplate.exam.pages.get(i).questions.size
		}
		indexQuestion += indexquestion
		indexQuestion
	}
	
	/**
	 * Ajoute une entrée à la note d'une question d'une copie
	 * @param questionId l'ID de la question à laquelle ajouter l'entrée
	 * @param l'ID de l'entrée dans l'Examen
	 */
	def addGradeEntry(int questionId, int gradeEntryId) {
		val gradeEntry = getQuestion(questionId).gradeScale.steps.findFirst[entry | entry.id == gradeEntryId]
		studentSheets.get(currentSheetIndex).grades.get(questionId).entries.add(gradeEntry)
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
	def List<Integer> getQuestionSelectedGradeEntries(int questionId) {
		studentSheets.get(currentSheetIndex).grades.get(questionId).entries.map[entry | entry.id]
	}
	
	/**
     * @param l'ID de la question à laquelle récupérer la liste d'entrées
	 * @return une liste d'ID d'entrées pour la question de l'examen dont l'ID est <i>questionId</i>
	 */
	def List<Tuple3<Integer, String, Float>> getQuestionGradeEntries(int questionId) {
		getQuestion(questionId).gradeScale.steps.map[entry | Tuple3.of(entry.id, entry.header, entry.step)]
	}
	
	/**
	 * Ajoute la note a la question courante
	 */
	def setGrade (Grade note){
		studentSheets.get(currentSheetIndex).grades.set(indexOfQuestions(pageIndex,currentQuestionIndex), note);
	}
	
	
	def void create(File file) 
	{
		document = PDDocument.load(file)

		ExamSingleton.instance = CoreFactory.eINSTANCE.createExam()

		for (i : 0 ..< document.pages.size()) 
		{
			ExamSingleton.instance.pages.add(CoreFactory.eINSTANCE.createPage());
		}
	}
	
}