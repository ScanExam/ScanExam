package fr.istic.tools.scanexam.view.fx.graduation

import javafx.scene.layout.VBox
import javafx.scene.Node

class QuestionListGraduation extends VBox {
	new(ControllerFxGraduation controller){
		this.controller = controller
		currentIndex = 0;
		this.spacing = 5;
		setupEvents
	}
	
	
	ControllerFxGraduation controller;
	int currentIndex;
	//---GETTERS/SETTERS---//
	def getController(){
		controller
	}
	def getCurrentIndex(){
		currentIndex
	}
	def  getCurrentItem(){
		children.get(currentIndex) as QuestionItemGraduation
	}
	//---------------------//
	
	
	//---METHODS---//

	def addItem(QuestionItemGraduation item) {
		item.list = this;
		children.add(item)
	}
	
	def removeItem(QuestionItemGraduation item) {
		children.remove(item)
	}
	
	def clearItems(){
		children.clear
	}
	
	/**
	 * Method used for highlighting
	 */
	def focusItem(QuestionItemGraduation item) {
		for (Node n : children) {
			var question = n as QuestionItemGraduation;
			question.focus = false
		}
		item.focus = true
	}
	
	def selectNextItem(){
		currentIndex++;
		currentIndex = Math.min(currentIndex,children.size-1)
	}
	
	def selectPreviousItem(){
		currentIndex--;
		currentIndex = Math.max(currentIndex,0)
	}
	
	def selectItem(QuestionItemGraduation item) {
		currentIndex = children.indexOf(item);
	}
	
	//-------------//
	
	def setupEvents(){
		
	}
}