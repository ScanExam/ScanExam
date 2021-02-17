package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.presenter.PresenterVueCorrection
import java.util.Objects
import fr.istic.tools.scanexam.core.Question
import java.util.LinkedList

/**
 * General controller for the Correction view
 * @author Benjamin Danlos
 */
class ControllerVueCorrection {
	/**
	 * Presenter to send the requests to to interact with the model
	 */
	PresenterVueCorrection presenter
	/**
	 * Controller used 
	 */
	ControllerI controller
    /**
	 * setter for the PresenterVueCorrection attribute
	 * @param {@link PresenterVueCorrection} pres instance of the presenter (not null) 
	 */
    def setPresenterVueCorrection(PresenterVueCorrection pres){
    	Objects.requireNonNull(pres)
    	presenter = pres
    }
    /**
	 * @return current {@link PresenterVueCorrection} 
	 */
    def getPresenterVueCorrection(){
    	presenter
    }
    
    /**
	 * setter for the Controller attribute
	 * @param {@link ControllerFX} pres instance of the Java FX Controller (not null) 
	 */
    def setController(ControllerI contr){
    	Objects.requireNonNull(contr)
    	controller=contr
    }
    /**
	 * @return current controller {@link ControllerFX} 
	 */
    def getController(){
    	controller
    }
    
    
  	
    
    
    def LinkedList<String> questionNames(){/*
    	var questions = presenter.presenterQuestion.getAllQuestions();
    	var names = new LinkedList<String>();
    	for (Question q : questions) {
    		names.add(q.name)
    	}
    	controller.initQuestionNames(names);*/
  
   	}
    /**
	 * @return next question
	 */
    def void nextQuestion(){
    	/*
    	var question = presenter.presenterQuestion.getNextQuestion();
    	controller.showQuestion(question)
    	* 
    	*/
    	
    }
    /**
     * @param question is the actual question
	 * @return previous question
	 */
    def void previousQuestion(){
    	/* 
    	var question = presenter.presenterQuestion.getPreviousQuestion();
    	controller.showQuestion(question)
    	* */
    }

    def void thisQuestion(int index) {
    	/* 
    	var question = presenter.presenterQuestion.getQuestion(index);
    	controller.showQuestion(question)
    	*/
    }
    
    }