package fr.istic.tools.scanexam.view.fx.graduation

import fr.istic.tools.scanexam.view.fx.FxSettings
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.HBox
import javafx.scene.paint.Color

class StudentItemGraduation extends HBox {
	new(int studentId) {
		super()
		this.studentId = new Label(studentId+ "");
		name = new Label("NOT SET");
		this.children.addAll(this.studentId,name);
		
		this.styleClass.add("ListItem")
		setupEvents()
	}
	
	StudentListGraduation list;
	Label name;
	Label studentId;
	//---GETTERS/SETTERS---//
	
	def setList(StudentListGraduation list) {
		this.list = list;
	}
	def setStudentId(int id){
		studentId.text = id + ""
	}
	def getStudentId(){
		Integer.parseInt(studentId.text)
	}
	def getStudentName(){
		this.name.text
	}
	def setStudentName(String name){
		this.name.text = name
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