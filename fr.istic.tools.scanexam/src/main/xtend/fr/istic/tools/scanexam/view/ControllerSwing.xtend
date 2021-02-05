package fr.istic.tools.scanexam.view

import java.util.Objects

/**
 * Class representing a controller to be used with the Swing view. 
 */
class ControllerSwing {
	
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