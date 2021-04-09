package fr.istic.tools.scanexam.view.fx.graduation

import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import fr.istic.tools.scanexam.view.fx.FxSettings
import javafx.scene.paint.Color
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.geometry.Insets
import javafx.scene.layout.Background

class StudentItemGraduation extends VBox {
	int studentId;
	new(int studentId) {
		super()
		this.studentId = studentId;
		name = new Label(studentId + "");
		this.children.add(name);
		
		this.styleClass.add("ListItem")
		setupEvents
	}
	StudentListGraduation list;
	Label name;
	//---GETTERS/SETTERS---//
	
	def setList(StudentListGraduation list) {
		this.list = list;
	}
	def setStudentId(int id){
		studentId = id
	}
	def getStudentId(){
		studentId
	}
	
	//---METHODS---//
	def void setFocus(boolean b) {//sets the color of the zone and the item in the list
		if (b) {
			color = FxSettings.ITEM_HIGHLIGHT_COLOR		
		}
		else {
			color = FxSettings.ITEM_NORMAL_COLOR
		}
	}
	
	def setColor(Color color){
		var bf = new BackgroundFill(color,CornerRadii.EMPTY,Insets.EMPTY);
		this.background = new Background(bf)
	}
	
	def setupEvents(){
		val item = this
		this.onMouseClicked = new EventHandler<MouseEvent>(){
			
			override handle(MouseEvent event) {
				list.controller.selectStudent(item);
			}
			
		}
	}
	
}