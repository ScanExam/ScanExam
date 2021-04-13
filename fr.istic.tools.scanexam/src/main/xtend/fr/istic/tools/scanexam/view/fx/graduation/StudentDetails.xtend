package fr.istic.tools.scanexam.view.fx.graduation

import fr.istic.tools.scanexam.view.fx.utils.RenameField
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import java.util.LinkedList
import fr.istic.tools.scanexam.view.fx.utils.RenameFieldSuggests

class StudentDetails extends VBox {
	new(ControllerFxGraduation controller){
		this.controller = controller
		var nameRow = new Label("Student name :")
		var idRow = new Label("Student Id :")
		
		grid = new GridPane();
		grid.add(nameRow,0,0);
		grid.add(idRow,0,1);
		
		
		name = new RenameFieldSuggests
		
		idLabel = new Label();
		grid.add(name,1,0);
		grid.add(idLabel,1,1);
		
		
		this.children.add(grid)
		setupEvents
	}
	
	LinkedList<String> nameList
	
	GridPane grid;
	RenameFieldSuggests name;
	Label idLabel;
	ControllerFxGraduation controller;
	
	StudentItemGraduation currentItem;
	
	def display(StudentItemGraduation item) {
		this.visible = true
		currentItem = item
		setName
		setId
	}
	
	def clearDisplay(){
		this.visible = false;
	}
	
	private def setName(){
		name.text = currentItem.studentName
	}
	private def setId(){
		idLabel.text = currentItem.studentId + "";
	}
	
	
	def commitRename() {
		println("Renaming to" + name.text)
		currentItem.studentName = name.text
		controller.studentList.updateInModel(currentItem)
	}
	
	def findSuggestions(String start){
		println("Changing")
		var l = controller.adapter.presenter.getStudentsSuggestedNames(start);
		name.showSuggestion(l);
	}
	
	def setupEvents(){
		name.textProperty.addListener([obs,oldVal,newVal | commitRename])
		name.fieldTextProperty.addListener([obs,oldVal,newVal | findSuggestions(newVal)])
		
	}
	
}