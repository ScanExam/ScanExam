package fr.istic.tools.scanexam.presenter

import java.util.Objects

/**
 * Class to manage the insertion of QRCodes on the exam during its creation
 * @author Benjamin Danlos
 */
class PresenterQRCode {
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