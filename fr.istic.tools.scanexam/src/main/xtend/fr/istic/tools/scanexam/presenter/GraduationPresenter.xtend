package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.services.ExamGraduationService
import fr.istic.tools.scanexam.view.Adapter
import java.io.File
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
		
	}


	/**
	 * @return API session 
	 */
	def getServiceAPI(){
		service
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
	
	override void create(File file)
	{
		 service.create(file);
	}
	
	
}