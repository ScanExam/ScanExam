package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.export.GradesExportImpl
import fr.istic.tools.scanexam.services.ExamGraduationService
import fr.istic.tools.scanexam.view.Adapter
import java.util.LinkedList
import java.util.List
import java.util.Objects

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
		
		presPdf = new PresenterPdf(service, this)
		presQuestion = new PresenterQuestion(service)
		
		//Verification Switch Service
		if(this.service.getExamInstance().equals(null)){
			if(!EditorGraduationSwitchVerification.saveExamInstance(this.service.getExamInstance()).equals(null)){
				this.service.setExamInstance(EditorGraduationSwitchVerification.loadExamInstance)
			}
		}else{
			EditorGraduationSwitchVerification.saveExamInstance(this.service.getExamInstance())
		}
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
	

	
	
	
	def void openEditionTemplate(String path){
		service.openCreationTemplate(path)
	}
	
	//XXX À améliorer
	def void exportGrades() {
		(new GradesExportImpl(service)).exportGrades
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
	
	def getTemplatePageAmount(){
		service.templatePageAmount
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