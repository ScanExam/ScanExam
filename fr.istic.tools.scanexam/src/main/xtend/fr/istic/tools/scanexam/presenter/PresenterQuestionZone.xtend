package fr.istic.tools.scanexam.presenter

import java.util.Objects
import fr.istic.tools.scanexam.services.ExamEditionService

/**
 * Class to manage conversions of the view's questions 
 * delimitation of a question's zone for the model
 * @author Benjamin Danlos
 */
class PresenterQuestionZone {
	/**
	 * Presenter for the creation view
	 */
	EditorPresenter presenter 
	ExamEditionService service
	
	new(ExamEditionService s , EditorPresenter p) {
		Objects.requireNonNull(s)
		Objects.requireNonNull(p)
		service = s
		presenter = p
	}
	
	/**
	 * setter for the PresenterVueCreation attribute
	 * @param {@link PresenterVueCreation} pres instance of the presenter (not null) 
	 */
	def setPresenterVueCreation(EditorPresenter pres){
		Objects.requireNonNull(pres)
		presenter = pres
	}
	/**
	 * @return current {@link PresenterVueCreation} 
	 */
	def getPresenterVueCreation(){
		presenter
	}
	
	def int createQuestion(double x, double y, double height, double width){
		service.createQuestion(x as float,y as float,height as float,width as float)
	}
	
	def void removeQuestion(int ID) {
		service.removeQuestion(ID);
	}
	
	def void renameQuestion(int ID,String name) {
		service.renameQuestion(ID,name)
	}
	
	def void resizeQuestion(int ID, double height, double width) {		
		service.rescaleQuestion(ID,height as float,height as float)
	}
	
	/**
	 * changes the x and y coordinates of the {@link Question} identified by the id
	 * @param int id : the unique ID of question
	 * @param float x : new x position
	 * @param float y : new y position
	 * @author : Benjamin Danlos
	 */
	def moveQuestion(int id, float x, float y){
		service.moveQuestion(id,x,y)
	}
	
}