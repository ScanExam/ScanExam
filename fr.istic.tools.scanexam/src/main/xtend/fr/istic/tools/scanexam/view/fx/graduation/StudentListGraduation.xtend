package fr.istic.tools.scanexam.view.fx.graduation

import javafx.scene.layout.VBox
import javafx.scene.Node
import org.apache.logging.log4j.LogManager

class StudentListGraduation extends VBox {
	static val logger = LogManager.logger
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
		if (noItems) return null
		children.get(currentIndex) as StudentItemGraduation 
	}	
	//---------------------//
	
	
	//---METHODS---//
	def boolean noItems(){
		return children.isEmpty
	}

	def addItem(StudentItemGraduation item) {
		item.list = this
		children.add(item)
	}
	
	def removeItem(StudentItemGraduation item) {
		children.remove(item)
	}
	
	def clearItems(){
		currentIndex = 0;
		children.clear
	}
	
	def void updateInModel(StudentItemGraduation item) {
		logger.info("Updating " + item.studentId + " to model")
		controller.renameStudent(item.studentId,item.studentName)
	}
	
	/**
	 * Method used for highlighting
	 */
	 def focusItem(StudentItemGraduation item) {
	 	if (item === null) return void
	 	for (Node n : children) {
			var question = n as StudentItemGraduation;
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
	
	def selectItem(StudentItemGraduation item) {
		currentIndex = children.indexOf(item);
	}
	
	//-------------//
	
	def setupEvents(){
		
	}
}