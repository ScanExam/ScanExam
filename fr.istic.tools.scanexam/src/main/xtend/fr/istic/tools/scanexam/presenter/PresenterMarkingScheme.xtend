package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.services.ExamEditionService
import java.util.Objects

/**
 * Class to manage an exam marking scheme (french : barème)
 * @author Benjamin Danlos
 */
class PresenterMarkingScheme {
	/**
	 * Presenter for the creation view
	 */
	Presenter presenter
	ExamEditionService service
	
	/**
	 * Constructor
	 * Constructs a Presenter manipulating the grading schele with the modele
	 * @param {@link ExamEditionService} s : the Service API linked with the model
	 * @param {@link Presenter} p : a Presenter used by the view 
	 * @author Benjamin Danlos
	 */
	new(ExamEditionService s, Presenter p){
		Objects.requireNonNull(s)
		Objects.requireNonNull(p)
		presenter = p
		service = s
	}
	/**
	 * setter for the Presenter attribute
	 * @param {@link Presenter} p : instance of the presenter (not null) 
	 */
	def setPresenterVueCreation(Presenter pres){
		Objects.requireNonNull(pres)
		presenter = pres
	}
	/**
	 * @return current {@link Presenter} 
	 */
	def getPresenter(){
		presenter
	}
	
	
	
	
	
}