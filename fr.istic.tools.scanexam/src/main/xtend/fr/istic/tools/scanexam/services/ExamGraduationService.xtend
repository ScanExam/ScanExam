package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.Grade
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.core.templates.CorrectionTemplate
import fr.istic.tools.scanexam.core.templates.CreationTemplate
import fr.istic.tools.scanexam.io.TemplateIO
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl
import java.io.File
import java.io.FileInputStream
import java.util.ArrayList
import java.util.Collection
import org.apache.pdfbox.pdmodel.PDDocument

import static fr.istic.tools.scanexam.services.ExamSingleton.*

class ExamGraduationService extends Service
{
	int currentSheetIndex;
	 
	int currentQuestionIndex;
	
	Collection<StudentSheet> studentSheets;
	
	CreationTemplate creationTemplate;
	
	CorrectionTemplate correctionTemplate;
	
	//Set<StudentSheet> visibleSheets;
	
	override save(String path) 
	{
		// TODO (sauvegarde le XMI de correction)
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
        
        val stream =new FileInputStream(new File(path))
        val pdfReader = new PdfReaderWithoutQrCodeImpl(stream,ExamSingleton.instance.pages.size,3); // TODO
        pdfReader.readPDf();
        studentSheets = pdfReader.completeStudentSheets
      
        stream.close();
        return true
	}
	
	
	
	/**
	 * Liste des identifiants des etudiants
	 */
	def studentsList ()
	{
		var tab = new ArrayList();
		for(var i =0; i<studentSheets.size-1;i++){
			tab.add(studentSheets.get(i).id)
		}
		tab
	}
	
	def numberOfQuestions ()
	{
		var nbQuestion =0
		for (var i = 0 ; i < document.pages.size-1; i++){
			nbQuestion += creationTemplate.exam.pages.get(i).questions.size
		}
		nbQuestion
	}
	
	
	/**
	 * Ajoute d'un etudiant
	 */
	 def addStudents (int id)
	 {
	 	val newStudent = CoreFactory.eINSTANCE.createStudentSheet;
	 	newStudent.id = id
	 	//TODO
	 	//newStudent.posPage= new int [numberOfQuestions()]
	 	//newStudent.grades = new Grade [numberOfQuestions()]
	 	studentSheets.add(newStudent)
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