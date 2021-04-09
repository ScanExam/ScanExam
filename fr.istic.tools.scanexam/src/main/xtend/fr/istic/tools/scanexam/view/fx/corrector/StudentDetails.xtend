package fr.istic.tools.scanexam.view.fx.corrector

import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox

class StudentDetails extends VBox {
	new(){
		var nameRow = new Label("Student name :")
		var idRow = new Label("Student Id :")
		grid = new GridPane();
		grid.add(nameRow,0,0);
		grid.add(idRow,0,1);
		
		nameLabel = new Label();
		idLabel = new Label();
		grid.add(nameLabel,1,0);
		grid.add(idLabel,1,1);
		
		this.children.add(grid)
	}
	
	GridPane grid;
	Label nameLabel;
	Label idLabel;
	
	StudentItemCorrector currentItem;
	
	def display(StudentItemCorrector item) {
		this.visible = true
		currentItem = item
		setName
		setId
	}
	
	def clearDisplay(){
		this.visible = false;
	}
	
	private def setName(){
		nameLabel.text = "TOTO"
	}
	private def setId(){
		idLabel.text = currentItem.studentId + "";
	}
	
	def setupEvents(){
		
	}
	
}