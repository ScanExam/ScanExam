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
		if (noItems) return null;
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
		currentIndex = 0;
		children.clear
	}
	
	def boolean noItems(){
		return children.isEmpty
	}
	/**
	 * Method used for highlighting
	 */
	def focusItem(QuestionItemGraduation item) {
		if (item === null) return void
		for (Node n : children) {
			var question = n as QuestionItemGraduation;
			question.focus = false
		}
		item.focus = true
	}
	
	def selectNextItem() {
		if (currentIndex + 1 < children.size) {
			currentIndex++
		} else {
			currentIndex = 0
		}
	}
	
	def selectPreviousItem() {
		if (currentIndex > 0) {
			currentIndex--
		} else {
			currentIndex = children.size - 1
		}
	}
	
	def selectItem(QuestionItemGraduation item) {
		currentIndex = children.indexOf(item);
	}
	
	
	def questionWithId(int id){
			children.findFirst[question | (question as QuestionItemGraduation).questionId == id] as QuestionItemGraduation
	}
	
	//-------------//
	
	def setupEvents(){
		
	}
}