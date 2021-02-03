package fr.istic.tools.scanexam.presenter

import java.util.Objects

/**
 * Class to manage conversions of the view's questions 
 * delimitation of a question's zone for the model
 * @author Benjamin Danlos
 */
class PresenterRectangle {
	/**
	 * Presenter for the creation view
	 */
	PresenterVueCreation presenter
	
	/**
	 * setter for the PresenterVueCreation attribute
	 * @param {@link PresenterVueCreation} pres instance of the presenter (not null) 
	 */
	def setPresenterVueCreation(PresenterVueCreation pres){
		Objects.requireNonNull(pres)
		presenter = pres
	}
	/**
	 * @return current {@link PresenterVueCreation} 
	 */
	def getPresenterVueCreation(){
		presenter
	}
	
}