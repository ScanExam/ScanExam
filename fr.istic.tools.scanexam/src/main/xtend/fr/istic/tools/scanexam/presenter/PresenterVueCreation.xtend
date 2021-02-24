package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.view.ControllerVueCreation
import java.util.Objects
import fr.istic.tools.scanexam.services.Service
import fr.istic.tools.scanexam.services.ExamEditionService

/**
 * Class defining the presenter for the exam creation view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
class PresenterVueCreation {
	/**
	 * Bidirectional associations with the concrete presenters
	 * and main controller of the view
	 */
	PresenterQRCode presQRCode
	PresenterRectangle presRectangle
	PresenterMarkingScheme presMarkingScheme
	ControllerVueCreation controller;
	ExamEditionService service;
	
	
	def PresenterVueCreation(ExamEditionService service)
	{
		Objects.requireNonNull(service)
		this.service = service
	}
	
	/**
	 * @return API session 
	 */
	def getSessionAPI(){
		service
	}
	
	/**
	 * setter for the PresenterQRCode attribute
	 * @param {@link PresenterQRCode} pres instance of the presenter (not null) 
	 */
	def setPresenterQRCode(PresenterQRCode pres){
		Objects.requireNonNull(pres)
		presQRCode = pres
	}
	/**
	 * @return current {@link PresenterQRCode} 
	 */
	def getPresenterQRCode(){
		presQRCode
	}
	
	/**
	 * Setter for {@link PresenterRectangle} attribute
	 * @param {@link PresenterRectangle} pres an instance (not null)
	 */
	def setPresenterRectangle(PresenterRectangle pres){
		Objects.requireNonNull(pres)
		presRectangle = pres
	}
	/**
	 * @return current {@link PresenterRectangle} 
	 */
	def getPresenterRectangle(){
		presRectangle
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
	 * Sets a {@link ControllerVueCreation} the link with the view
	 * @param {@link ControllerVueCreation} contr an instance (not null)
	 */
	def setControllerVueCreation(ControllerVueCreation contr){
		Objects.requireNonNull(contr)
		controller = contr
	}
	/**
	 * @return current {@link ControllerVueCreation} 
	 */
	def getControllerVueCreation(){
		controller
	}
	
}