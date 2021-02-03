package fr.istic.tools.scanexam.presenter

import java.util.Objects

/**
 * Class to manage an exam marking scheme (french : barème)
 * @author Benjamin Danlos
 */
class PresenterMarkingScheme {
	/**
	 * Presenter for the creation view
	 */
	PresenterVueCreation presenterCreation
	/**
	 * Presenter for the correction view
	 */
	PresenterVueCorrection presenterCorrection
	
	/**
	 * setter for the PresenterVueCreation attribute
	 * @param {@link PresenterVueCreation} pres instance of the presenter (not null) 
	 */
	def setPresenterVueCreation(PresenterVueCreation pres){
		Objects.requireNonNull(pres)
		presenterCreation = pres
	}
	/**
	 * @return current {@link PresenterVueCreation} 
	 */
	def getPresenterVueCreation(){
		presenterCreation
	}
	
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
	
}