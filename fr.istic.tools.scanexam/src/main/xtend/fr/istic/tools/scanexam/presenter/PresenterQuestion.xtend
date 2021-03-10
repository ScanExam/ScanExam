package fr.istic.tools.scanexam.presenter

import java.util.Objects
import fr.istic.tools.scanexam.services.ExamGraduationService

/**
 * CLass representing a presenter of question in 
 * an exam to link the view and the model
 * @author Benjamin Danlos, Matthieu Pays
 */
class PresenterQuestion {
	
	/**
	 * Association with the model via the Service API
	 */
	ExamGraduationService service
	
	/**
	 * Presenter for the correction view
	 */
	GraduationPresenter presenterCorrection
	
	/**
	 * Constructor
	 * @param {@link ExamGraduationService} (not null)
	 * Constructs a QuestionPresenter object.
	 */
	new(ExamGraduationService service){
		Objects.requireNonNull(service)
		this.service = service
	}
	
	/**
	 * setter for the PresenterVueCorrection attribute
	 * @param {@link PresenterVueCorrection} pres instance of the presenter (not null) 
	 */
	def setPresenterVueCorrection(GraduationPresenter pres){
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
	def getNextQuestion(int question){
		return 0;
	}
	
	/**
	 * @param question is the actual question
	 * @return previous question
	 */
	def getPreviousQuestion(int question){
		return 0;
	}
}