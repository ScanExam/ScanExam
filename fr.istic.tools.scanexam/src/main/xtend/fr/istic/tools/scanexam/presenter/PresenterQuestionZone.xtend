package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.services.ExamEditionService
import java.util.LinkedList
import java.util.Objects
import java.util.List

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
		service.rescaleQuestion(ID,height as float,width as float)
	}
	
	/**
	 * changes the x and y coordinates of the {@link Question} identified by the id
	 * @param int id : the unique ID of question
	 * @param float x : new x position
	 * @param float y : new y position
	 * @author : Benjamin Danlos
	 */
	def void moveQuestion(int id, double x, double y){
		service.moveQuestion(id,x as float,y as float)
	}
	
	def void changeQuestionWorth(int id,float worth) {
		service.modifMaxPoint(id,worth)
	}
	
	
	
	
	/* --LOADING NEW TEMPLATE--  */
	
	def LinkedList<Integer> initLoading(int pageNumber){
		questions = service.getQuestionAtPage(pageNumber)//replace with method that gives a list of pages corresponding to questions at same index
		var ids = new LinkedList<Integer>();
		for (Question q : questions) {
			ids.add(q.id)
		}
		ids
	}
	
	List<Question> questions
	/**
	 * Loads the next question into questionToLoad
	 * if there is a new question, return true,
	 * else return false
	 */
	 
	
	def double questionX(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.x
			}
		}
		result
	}
	
	def double questionY(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.y
			}
		}
		result
	}
	
	def double questionHeight(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.heigth
				print("h = " + result)
			}
		}
		result
	}
	
	def double questionWidth(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.width
				print("w = " + result)
			}
		}
		result
	}
	
	def String questionName(int id){
		var result = "";
		for (Question q : questions) {
			if (q.id == id) {
				result = q.name
			}
		}
		result
	}
	
	

	
	def float questionWorth(int id){
		var result = 0f;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.gradeScale.maxPoint
			}
		}
		result
	}
	
	/* -----------------------  */
	
		
	
}