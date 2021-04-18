package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.services.api.ServiceGraduation
import java.util.Objects

/**
 * CLass representing a presenter of question in 
 * an exam to link the view and the model
 * @author Benjamin Danlos, Matthieu Pays
 */
class PresenterQuestion {
	
	/**
	 * Association with the model via the Service API
	 */
	ServiceGraduation service
	
	/**
	 * Presenter for the correction view
	 */
	PresenterGraduation presenterCorrection
	
	/**
	 * Constructor
	 * @param {@link ExamGraduationService} (not null)
	 * Constructs a QuestionPresenter object.
	 */
	new(ServiceGraduation service){
		Objects.requireNonNull(service)
		this.service = service
	}
	
	/**
	 * setter for the PresenterVueCorrection attribute
	 * @param {@link PresenterVueCorrection} pres instance of the presenter (not null) 
	 */
	def setPresenterVueCorrection(PresenterGraduation pres){
		Objects.requireNonNull(pres)
		presenterCorrection = pres
	}
	/**
	 * @return current {@link PresenterVueCreation} 
	 */
	def getPresenterVueCorrection(){
		presenterCorrection
	}
	
	
	/**
	 * Set the grade for the current question
	 * @param grade to set up
	 */
	def setGrade(int grade){
		/*
		 * TODO service.setGrade(..new Grade(grade)..)
		 * */
	}
	
	/**
	 * @return next question
	 */
	def nextQuestion(){
		service.nextQuestion
	}
	
	/**
	 * @param question is the actual question
	 */
	def previousQuestion(){
		service.previousQuestion
	}
	
	def selectQuestion(int questionId) {
		service.selectQuestion(questionId);
	}
	
	def nextStudent(){
		service.nextSheet
	}
	
	def previousStudent(){
		service.previousSheet
	}
}