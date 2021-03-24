package fr.istic.tools.scanexam.view.fX.corrector

import fr.istic.tools.scanexam.view.fX.FXSettings
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent

class QuestionItemCorrector extends VBox {
	new(){
		super()
		name = new Label()
		this.children.add(name);
		
		this.styleClass.add("ListItem")
		setupEvents
	}
	
	
	double x;
	double y;
	double height;
	double width;
	Label name;
	int questionId;
	int page;
	QuestionListCorrector list;
	
	
	def setList(QuestionListCorrector list) {
		this.list = list
	}
	
	def setX(double x){
		this.x = x
	}
	def setY(double x){
		this.y = x
	}
	def setH(double x){
		height = x
	}
	def setW(double x){
		width = x
	}
	def setName(String x){
		name.text = x
	}
	def setQuestionId(int x){
		questionId = x
	}
	
	def setPage(int x) {
		page = x
	}
	
	def getX(){
		x
	}
	def getY(){
		y
	}
	def getH(){
		height
	}
	def getW(){
		width
	}
	def getQuestionId(){
		questionId
	}
	def getName(){
		name.text
	}
	
	def getPage(){
		page
	}
	
	def void setFocus(boolean b) {//sets the color of the zone and the item in the list
		if (b) {
			color = FXSettings.ITEM_HIGHLIGHT_COLOR		
		}
		else {
			color = FXSettings.ITEM_NORMAL_COLOR
		}
	}
	def setColor(Color color){
		var bf = new BackgroundFill(color,CornerRadii.EMPTY,Insets.EMPTY);
		this.background = new Background(bf)
	}
	
	def setupEvents(){
		val me = this
		this.onMouseClicked = new EventHandler<MouseEvent>(){
			
			override handle(MouseEvent event) {
				list.controller.selectQuestion(me)
			}
			
		}
	}
}