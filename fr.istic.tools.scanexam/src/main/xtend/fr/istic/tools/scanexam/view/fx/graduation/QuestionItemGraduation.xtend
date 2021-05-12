package fr.istic.tools.scanexam.view.fx.graduation

import fr.istic.tools.scanexam.view.fx.FxSettings
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent

class QuestionItemGraduation extends VBox {
	new(){
		super()
		name = new Label()
		this.children.add(name);
		
		this.styleClass.add("ListItem")
		setupEvents
	}
	

	Label name;
	int questionId;
	int page;
	QuestionListGraduation list;
	
	
	def setList(QuestionListGraduation list) {
		this.list = list
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
		list.controller.questionX(questionId) * list.controller.imageWidth
	}
	def getY(){
		list.controller.questionY(questionId) * list.controller.imageHeight
	}
	def getH(){
		list.controller.questionHeight(questionId) * list.controller.imageHeight
		
	}
	def getW(){
		list.controller.questionWidth(questionId)* list.controller.imageWidth
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
	
	def getWorth(){
		list.controller.questionWorth(questionId)
	}
	
	def void setFocus(boolean b) {//sets the color of the zone and the item in the list
		if (b) {
			color = FxSettings.ITEM_HIGHLIGHT_COLOR
			this.name.styleClass.add("focusedText")	
		}
		else {
			color = FxSettings.ITEM_NORMAL_COLOR
			this.name.styleClass.remove("focusedText")	
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