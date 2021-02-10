package fr.istic.tools.scanexam.presenter

import java.util.Objects

/**
 * CLass representing a presenter of question in 
 * an exam to link the view and the model
 * @author Benjamin Danlos
 */
class PresenterQuestion {
	/**
	 * Presenter for the correction view
	 */
	PresenterVueCorrection presenterCorrection
	/**
	 * setter for the PresenterVueCorrection attribute
	 * @param {@link PresenterVueCorrection} pres instance of the presenter (not null) 
	 */
	def setPresenterVueCorrection(PresenterVueCorrection pres){
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