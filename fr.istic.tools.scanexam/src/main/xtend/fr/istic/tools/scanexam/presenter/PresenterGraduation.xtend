package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.export.GradesExportImpl
import fr.istic.tools.scanexam.mailing.StudentDataManager
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import fr.istic.tools.scanexam.utils.Tuple3
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.LinkedList
import java.util.List
import java.util.Objects
import org.apache.logging.log4j.LogManager
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
	PresenterGraduation graduationPresenter;
	PresenterPdf presPdf 
	PresenterStudentListLoader studentListPresenter
	PresenterGraduationLoader graduationLoaderPresenter
	PresenterImportExportXMI importExportPresenter
	
	ServiceGraduation service;
	

	static val logger = LogManager.logger
	
	new(ServiceGraduation service)
	{
		Objects.requireNonNull(service)
		this.service = service
		
		importExportPresenter = new PresenterImportExportXMI(service)
		graduationLoaderPresenter = new PresenterGraduationLoader(importExportPresenter,service);
		
		presPdf = new PresenterPdf(service, this)
		presQuestion = new PresenterQuestion(service)
	
		studentListPresenter = new PresenterStudentListLoader(service)
		
	
	}
	
	
	
	/**
	 * @return current {@link PresenterQuestion} 
	 */
	def getPresenterQuestion(){
		presQuestion
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
	def PresenterGraduationLoader getPresenterGraduationLoader() {
		graduationLoaderPresenter
	}
	
	def void openEditionTemplate(String path){
		service.openCreationTemplate(path)
	}
	
	//XXX À améliorer
	def void exportGrades() {
		(new GradesExportImpl(service)).exportGrades
	}
	
	def int getPageAmount() {
		return service.pageAmount
	}
	
	def boolean openCorrectionPdf(File file)
	{
		val document = PDDocument.load(file);
		val stream = new ByteArrayOutputStream();
		document.save(stream);
		presPdf.create("", file);
        val pdfReader = new PdfReaderWithoutQrCodeImpl(document, getPageAmount,3);  
        pdfReader.readPDf();
        val studentSheets = pdfReader.completeStudentSheets
		return service.initializeCorrection(studentSheets) 
	}
	
	
	//--- Grade application
	
	def applyGrade(int questionId,int gradeId) {
		service.assignGradeEntry(questionId,gradeId);
	}
	
	def removeGrade(int questionId,int gradeId) {
		service.retractGradeEntry(questionId,gradeId);
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
	
	
	/**
	 * Retourne la note globale de la copie
	 * @return Note globale de la copie
	 * //FIXME doit être lié au service
	 */
	def float getGlobalGrade() {
	    return 0.0f
	}
	    
	/**
	 * Retourne le barème total de l'examen
	 * @return Barème total de l'examen
	 * //FIXME doit être lié au service
	 */
	def float getGlobalScale() {
	    return 0.0f
	}
	
	
	
	
	/* SAVING  */
	def saveTemplate(String path){
		service.saveCorrectionTemplate(path,presPdf.pdfOutputStream)
	}
	
	
	
	/* STUDENTS */
	
	def List<String> getStudentsSuggestedNames(String start){
		StudentDataManager.allNames
			.map(l | l.filter[n | n.toLowerCase().contains(start.toLowerCase())].toList)
			.orElse(List.of())
	}
	
	def LinkedList<Integer> getStudentIds(){ //TODO Change service impl to not return null
		var list = service.studentSheets
		var result = new LinkedList<Integer>()
		if (list !== null) {
			for (StudentSheet s : list) {
				result.add(s.id);
			}
		}
		else {
			logger.warn("Service returned null studentId list")
		}
		result
	}
	
	def renameStudent(int studentId,String newname){
		service.assignStudentId(newname)
	}
	
	
	def int getAbsolutePage(int studentId,int pageNumber){
		service.getAbsolutePageNumber(studentId,pageNumber);
	}
	
	/* --LOADING TEMPLATE--  */
	
	def LinkedList<Integer> initLoading(int pageNumber){
		questions = service.getQuestionAtPage(pageNumber)//replace with method that gives a list of pages corresponding to questions at same index
		var ids = new LinkedList<Integer>();
		for (Question q : questions) {
			ids.add(q.id)
		}
		ids
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
			}
		}
		result
	}
	
	def double questionWidth(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.width
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