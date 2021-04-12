package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.export.GradesExportImpl
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl
import fr.istic.tools.scanexam.services.ExamSingleton
import fr.istic.tools.scanexam.services.ServiceGraduation
import fr.istic.tools.scanexam.utils.Tuple3
import fr.istic.tools.scanexam.view.Adapter
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.LinkedList
import java.util.List
import java.util.Objects
import org.apache.pdfbox.pdmodel.PDDocument

/**
 * Class defining the presenter for the exam correction view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
class PresenterGraduation implements Presenter
{
	/**
	 * Bidirectional associations with the concrete presenters
	 * and main controller of the view
	 */
	PresenterQuestion presQuestion
	PresenterCopy presCopy
	PresenterGradeScale presMarkingScheme
	PresenterGraduation graduationPresenter;
	PresenterPdf presPdf 
	PresenterStudentListLoader studentListPresenter
	PresenterGraduationLoader studentSheetPresenter
	ServiceGraduation service;
	
	Adapter<PresenterGraduation> adapter;
	
	new(ServiceGraduation service)
	{
		Objects.requireNonNull(service)
		this.service = service
		
		presPdf = new PresenterPdf(service, this)
		presQuestion = new PresenterQuestion(service)
		studentListPresenter = new PresenterStudentListLoader(service)
		studentSheetPresenter = new PresenterGraduationLoader(service)
	}
	new(Adapter<PresenterGraduation> adapter,ServiceGraduation service) 
	{
		this(service);
		
		Objects.requireNonNull(adapter)
		this.adapter = adapter
		
		
		
		//Verification Switch Service
		/*if(this.service.getExamInstance().equals(null)){
			if(!EditorGraduationSwitchVerification.saveExamInstance(this.service.getExamInstance()).equals(null)){
				this.service.setExamInstance(EditorGraduationSwitchVerification.loadExamInstance)
			}
		}else{
			EditorGraduationSwitchVerification.saveExamInstance(this.service.getExamInstance())
		}*/
	}
	
	
	/**
	 * @return current {@link PresenterQuestion} 
	 */
	def getPresenterQuestion(){
		presQuestion
	}
	
	
	/**
	 * @return current {@link PresenterCopy} 
	 */
	def getPresenterCopy(){
		presCopy
	}
	

	/**
	 * @return current {@link PresenterMarkingScheme} 
	 */
	override getPresenterMarkingScheme(){
		presMarkingScheme
	}
	
	
	/**
	 * @return current {@link ControllerVueCorrection} 
	 */
	def getControllerVueCorrection(){
		graduationPresenter
	}
	
	override getPresenterPdf(){
		presPdf
	}
	

	/**
	 * @return current {@link PresenterStudentListLoader} 
	 */
	def PresenterStudentListLoader getPresenterStudentList() {
		studentListPresenter
	}
	
	/**
	 * @return current {@link PresenterStudentSheetLoader} 
	 */
	def PresenterGraduationLoader getPresenterStudentSheet() {
		studentSheetPresenter
	}
	
	
	def void openEditionTemplate(String path){
		service.openCreationTemplate(path)
	}
	
	//XXX À améliorer
	def void exportGrades() {
		(new GradesExportImpl(service)).exportGrades
	}
	
	def boolean openCorrectionPdf(File file)
	{
		val document = PDDocument.load(file);
		val stream = new ByteArrayOutputStream();
		document.save(stream);
		presPdf.create(file);
        val pdfReader = new PdfReaderWithoutQrCodeImpl(document,ExamSingleton.instance.pages.size,3);  
        pdfReader.readPDf();
        val studentSheets = pdfReader.completeStudentSheets
		return service.initializeCorrection(studentSheets) 
	}
	
	
	//--- Grade application
	
	def applyGrade(int questionId,int gradeId) {
		service.addGradeEntry(questionId,gradeId);
	}
	
	def removeGrade(int questionId,int gradeId) {
		service.removeGradeEntry(questionId,gradeId);
	}
	
	
	//---Grade entry management
	
	def List<Integer> getEntryIds(int questionId){
		var l = service.getQuestionGradeEntries(questionId);
		print("presenter size :" + l.size)
		var result = new LinkedList<Integer>();
		for (Tuple3<Integer, String, Float> t : l) {
			result.add(t._1);
		}
		result
	}
	
	def List<Integer> getSelectedEntryIds(int questionId){
		service.getQuestionSelectedGradeEntries(questionId);
	}
	
	def String getEntryText(int entryId,int questionId){
		var l = service.getQuestionGradeEntries(questionId);
		for (Tuple3<Integer, String, Float> t : l) {
			if (entryId == t._1) {
				return t._2
			}
		}
		"Entry not found"
	}
	
	def float getEntryWorth(int entryId,int questionId){
		var l = service.getQuestionGradeEntries(questionId);
		for (Tuple3<Integer, String, Float> t : l) {
			if (entryId == t._1) {
				return t._3
			}
		}
		return -1
	}
	
	/**
	 * Ajoute une nouvelle entrée à la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle ajouter l'entrée
	 * @param desc la description de l'entrée
	 * @param point le nombre de point de l'entrée
	 * @return l'ID de l'entrée
	 */
	def int addEntry(int questionId, String desc, float point) {
		service.addEntry(questionId, desc, point)
	}
	
	/**
	 * Modifie une entrée de la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle modifier l'entrée
	 * @param gradeEntryId l'ID de l'entrée à modifier
	 * @param desc la nouvelle description de l'entrée
	 * @param point le nouveau nombre de point de l'entrée
	 */
	def modifyEntry(int questionId, int gradeEntryId, String desc, float point) {
		service.modifyEntry(questionId, gradeEntryId, desc, point)			
	}
	
	/**
	 * Supprime une entrée de la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle supprimer l'entrée
	 * @param gradeEntryId l'ID de l'entrée à supprimer
	 */
	def removeEntry(int questionId, int gradeEntryId) {
		service.removeEntry(questionId, gradeEntryId)
	}
	
	
	
	
	
	
	
	
	
	
	def LinkedList<Integer> getStudentIds(){
		var list = service.studentSheets
		print(list.size)
		var result = new LinkedList<Integer>()
		for (StudentSheet s : list) {
			result.add(s.id);
		}
		result
	}
	
	
	def int getAbsolutePage(int studentId,int pageNumber){
		service.getAbsolutePageNumber(studentId,pageNumber);
	}
	
	/* --LOADING NEW TEMPLATE--  */
	
	def LinkedList<Integer> initLoading(int pageNumber){
		questions = service.getQuestionAtPage(pageNumber)//replace with method that gives a list of pages corresponding to questions at same index
		var ids = new LinkedList<Integer>();
		for (Question q : questions) {
			ids.add(q.id)
		}
		ids
	}
	
	def getTemplatePageAmount(){
		ExamSingleton.instance.pages.size
	}
	
	List<Question> questions
	
	
	/**
	 * Loads the next question into questionToLoad
	 * if there is a new question, return true,
	 * else return false
	 */
	 
	
	def double questionX(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.x
			}
		}
		result
	}
	
	def double questionY(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.y
			}
		}
		result
	}
	
	def double questionHeight(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.heigth
				print("h = " + result)
			}
		}
		result
	}
	
	def double questionWidth(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.width
				print("w = " + result)
			}
		}
		result
	}
	
	def String questionName(int id){
		var result = "";
		for (Question q : questions) {
			if (q.id == id) {
				result = q.name
			}
		}
		result
	}
	
	def float questionWorth(int id){//TODO FIX
		var result = -1f;
		for (Question q : questions) {
			if (q.id == id) {
				if (q.gradeScale !== null)
					result = q.gradeScale.maxPoint
			}
		}
		result
	}

}