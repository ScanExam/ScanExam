package fr.istic.tools.scanexam.view.fx.corrector

import javafx.scene.layout.VBox
import javafx.scene.Node

class StudentListCorrector extends VBox {
	new(ControllerFXCorrector controller){
		this.controller = controller
		currentIndex = 0;
		this.spacing = 5;
		setupEvents
	}
	
	
	ControllerFXCorrector controller;
	
	int currentIndex;
	//---GETTERS/SETTERS---//
	def getController(){
		controller
	}
	def getCurrentIndex(){
		currentIndex
	}
	def  getCurrentItem(){
		children.get(currentIndex) as StudentItemCorrector 
	}
	
	//---------------------//
	
	
	//---METHODS---//

	def addItem(StudentItemCorrector item) {
		item.list = this
		children.add(item)
	}
	
	def removeItem(StudentItemCorrector item) {
		children.remove(item)
	}
	
	def clearItems(){
		children.clear
	}
	
	/**
	 * Method used for highlighting
	 */
	 def focusItem(StudentItemCorrector item) {
	 	for (Node n : children) {
			var question = n as StudentItemCorrector;
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
	
	def selectItem(StudentItemCorrector item) {
		currentIndex = children.indexOf(item);
	}
	
	//-------------//
	
	def setupEvents(){
		
	}
}