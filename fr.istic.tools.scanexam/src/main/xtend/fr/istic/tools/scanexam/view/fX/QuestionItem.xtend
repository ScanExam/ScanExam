package fr.istic.tools.scanexam.view.fX

import javafx.scene.layout.VBox
import javafx.scene.control.Label

class QuestionItem extends VBox {
	double x;
	double y;
	double height;
	double width;
	Label name;
	int questionId;
	int page;
	
	new(){
		super()
		name = new Label()
		this.children.add(name);
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
}