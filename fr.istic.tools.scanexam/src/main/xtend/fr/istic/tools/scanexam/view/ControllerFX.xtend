package fr.istic.tools.scanexam.view

import java.util.Objects

/**
 * Class used by the JavaFX library as a controller for the view. 
 * @author Benjamin Danlos
 */
class ControllerFX {
	
	/**
	 * High level Controllers to access the Presenters
	 */
	ControllerVueCreation controllerCreation
	ControllerVueCorrection controllerCorrection
	
	/**
	 * setter for the ControllerVueCreation attribute
	 * @param {@link ControllerVueCreation} controller instance of ControllerVueCreation (not null) 
	 */
	def setControllerVueCreation(ControllerVueCreation controller){
		Objects.requireNonNull(controller)
		controllerCreation = controller
	}
	/**
	 * @return current {@link ControllerVueCreation} 
	 */
	def getControllerVueCreation(){
		controllerCreation
	}
	
	/**
	 * setter for the ControllerVueCorrection attribute
	 * @param {@link ControllerVueCorrection} controller instance of ControllerVueCorrection (not null) 
	 */
	def setControllerVueCorrection(ControllerVueCorrection controller){
		Objects.requireNonNull(controller)
		controllerCorrection = controller
	}
	/**
	 * @return current {@link ControllerVueCorrection} 
	 */
	def getControllerVueCorrection(){
		controllerCorrection
	}
}