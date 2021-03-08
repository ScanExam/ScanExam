package fr.istic.tools.scanexam.presenter

import java.util.Objects
import fr.istic.tools.scanexam.services.ExamEditionService
import fr.istic.tools.scanexam.core.Question
import java.util.ArrayList
import java.util.LinkedList

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
	def moveQuestion(int id, double x, double y){
		service.moveQuestion(id,x as float,y as float)
	}
	
	
	
	
	
	/* --LOADING NEW TEMPLATE--  */
	
	def void initLoading(){
		pageNumbers = new LinkedList<Integer>() // replace method that give a list of questions
		questions = new LinkedList<Question>() //replace with method that gives a list of pages corresponding to questions at same index
		questionToLoadIndex = -1;
	}
	
	LinkedList<Integer> pageNumbers
	LinkedList<Question> questions
	int questionToLoadIndex;
	/**
	 * Loads the next question into questionToLoad
	 * if there is a new question, return true,
	 * else return false
	 */
	 
	def boolean loadNextQuestion(){
		//TODO get a list of questions from the model
		// questions will be the list of questions
		// pageNumbers is a list representing the page number of the questikns at the corresponding index
		// 
		questionToLoadIndex++;
		if (questionToLoadIndex >= questions.size) {
			return false;
		}
		return true
	}
	
	def double currentQuestionX(){
		questions.get(questionToLoadIndex).zone.x
	}
	
	def double currentQuestionY(){
		questions.get(questionToLoadIndex).zone.y
	}
	
	def double currentQuestionHeight(){
		questions.get(questionToLoadIndex).zone.heigth
	}
	
	def double currentQuestionWidth(){
		questions.get(questionToLoadIndex).zone.width
	}
	
	def String currentQuestionName(){
		questions.get(questionToLoadIndex).name
	}
	
	def int currentQuestionId(){
		questions.get(questionToLoadIndex).id
	}
	
	def int currentQuestionPage(){
		pageNumbers.get(questionToLoadIndex)
	}
	
	/* -----------------------  */
	
		
	
}