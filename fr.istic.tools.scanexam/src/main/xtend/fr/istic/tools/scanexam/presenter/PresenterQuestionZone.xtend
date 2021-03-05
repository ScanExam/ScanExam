package fr.istic.tools.scanexam.presenter

import java.util.Objects
import fr.istic.tools.scanexam.services.ExamEditionService
import fr.istic.tools.scanexam.core.Question
import java.util.ArrayList

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
	
	
	
	
	
	/* --LOADING NEW TEMPLATE--  */
	
	int questionToLoad = -1;
	int questionToLoadPage =  -1;
	/**
	 * Loads the next question into questionToLoad
	 * if there is a new question, return true,
	 * else return false
	 */
	def boolean loadNextQuestion(){
		//TODO get a list of questions from the model
		// the list will be a list of list of questions : List1<List2<Question>>
		//List1 represents a list of pages, and list2 is a list of all the questions of a certain page
		var pages = new ArrayList<ArrayList<Question>>()
		
		if (questionToLoad == -1) {
			questionToLoadPage = 0
			questionToLoad = 0
		} else {
			questionToLoad++
			if (questionToLoad >= pages.get(questionToLoadPage).size) {
			questionToLoadPage++;
			questionToLoad = 0
			}
		}
		
		
		
		
		
		false;
	}
	/* 
	def double currentQuestionX(){
		questionToLoad.zone.x
	}
	
	def double currentQuestionY(){
		questionToLoad.zone.y
	}
	
	def double currentQuestionHeight(){
		questionToLoad.zone.heigth
	}
	
	def double currentQuestionWidth(){
		questionToLoad.zone.width
	}
	
	def String currentQuestionName(){
		questionToLoad.name
	}
	
	def int currentQuestionId(){
		questionToLoad.id
	}
	
	def int currentQuestionPage(){
		questionToLoadPage
	}*/
	
	/* -----------------------  */
	
		
	
}