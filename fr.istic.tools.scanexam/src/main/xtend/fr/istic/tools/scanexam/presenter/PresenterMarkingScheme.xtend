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
	
	/**
	 * Ajoute une nouvelle entrée à la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle ajouter l'entrée
	 * @param desc la description de l'entrée
	 * @param point le nombre de point de l'entrée
	 * @return l'ID de l'entrée
	 */
	def int addEntry(int questionId, String desc, float point) {
		//service.addEntry(questionId, desc, point)
		0
	}
	
	/**
	 * Modifie une entrée de la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle modifier l'entrée
	 * @param gradeEntryId l'ID de l'entrée à modifier
	 * @param desc la nouvelle description de l'entrée
	 * @param point le nouveau nombre de point de l'entrée
	 */
	def modifyEntry(int questionId, int gradeEntryId, String desc, float point) {
		service.modifyEntry(questionId, gradeEntryId, desc, point)			
	}
	
	/**
	 * Supprime une entrée de la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle supprimer l'entrée
	 * @param gradeEntryId l'ID de l'entrée à supprimer
	 */
	def removeEntry(int questionId, int gradeEntryId) {
		service.removeEntry(questionId, gradeEntryId)
	}
	
	
	
}