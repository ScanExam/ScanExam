package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.services.ExamGraduationService
import fr.istic.tools.scanexam.view.Adapter
import java.io.File
import java.util.Objects
import java.util.LinkedList
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.core.Question
import java.util.List

/**
 * Class defining the presenter for the exam correction view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
class GraduationPresenter implements Presenter
{
	/**
	 * Bidirectional associations with the concrete presenters
	 * and main controller of the view
	 */
	PresenterQuestion presQuestion
	PresenterCopy presCopy
	PresenterMarkingScheme presMarkingScheme
	GraduationPresenter graduationPresenter;
	PresenterPdf presPdf
	ExamGraduationService service;
	
	Adapter<GraduationPresenter> adapter;
	
	new(Adapter<GraduationPresenter> adapter,ExamGraduationService service) 
	{
		Objects.requireNonNull(service)
		this.service = service
		
		Objects.requireNonNull(adapter)
		this.adapter = adapter
		
	}
	
	/**
	 * setter for the PresenterQuestion attribute
	 * @param {@link PresenterQuestion} pres instance of the presenter (not null) 
	 */
	def setPresenterQuestion(PresenterQuestion pres){
		Objects.requireNonNull(pres)
		presQuestion = pres
	}
	/**
	 * @return current {@link PresenterQuestion} 
	 */
	def getPresenterQuestion(){
		presQuestion
	}
	
	/**
	 * Setter for {@link PresenterCopy} attribute
	 * @param {@link PresenterCopy} pres an instance (not null)
	 */
	def setPresenterCopy(PresenterCopy pres){
		Objects.requireNonNull(pres)
		presCopy = pres
	}
	/**
	 * @return current {@link PresenterCopy} 
	 */
	def getPresenterCopy(){
		presCopy
	}
	
	/**
	 * Setter for {@link PresenterMarkingScheme} attribute
	 * @param {@link PresenterMarkingScheme} pres an instance (not null)
	 */
	def setPresenterMarkingScheme(PresenterMarkingScheme pres){
		Objects.requireNonNull(pres)
		presMarkingScheme = pres
	}
	/**
	 * @return current {@link PresenterMarkingScheme} 
	 */
	def getPresenterMarkingScheme(){
		presMarkingScheme
	}
	
	/**
	 * Sets a {@link ControllerVueCorrection} the link with the view
	 * @param {@link ControllerVueCorrection} contr an instance (not null)
	 */
	def setControllerVueCorrection(GraduationPresenter contr){
		Objects.requireNonNull(contr)
		graduationPresenter = contr
	}
	/**
	 * @return current {@link ControllerVueCorrection} 
	 */
	def getControllerVueCorrection(){
		graduationPresenter
	}
	
	/**
	 * @return next question
	 */
	def getNextQuestion(int question){
		presQuestion.getNextQuestion(question)
	}
	
	/**
	 * @param question is the actual question
	 * @return previous question
	 */
	def getPreviousQuestion(int question){
		presQuestion.getPreviousQuestion(question)
	}
	
	
	def setPresenterPdf(PresenterPdf pres){
		Objects.requireNonNull(pres)
		presPdf = pres
	}
	def getPresenterPdf(){
		presPdf
	}
	
	override getCurrentPdfPage() {
		return service.getCurrentPdfPage
	}
	
	override int getCurrentPdfPageNumber(){
		service.currentPageNumber
	}
	
	def int getTotalPages(){
		service.pageNumber
	}
	
	def void nextPdfPage(){
		service.nextPage
	}
	def void previousPdfPage(){
		service.previousPage
	}
	
	def void goToPage(int page) {
		service.goToPage(page)
	}
	
	override void create(File file)
	{
		 service.create(file);
	}
	
	def void openEditionTemplate(String path){
		service.openCreationTemplate(path)
	}
	
	def void openCorrectionPdf(String path){
		service.openCorrectionPdf(path)
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

}