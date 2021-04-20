package fr.istic.tools.scanexam.view.fx.graduation

import javafx.scene.shape.Rectangle
import javafx.scene.control.TextArea
import java.util.List
import javafx.scene.Node
import javafx.scene.shape.Line

class TextAnotation extends Rectangle {
	new(double x, double y, double height, double width, String text){
		this.text = new TextArea(text);
		line = new Line();
		this.text.wrapText = true;
		this.text.styleClass.add("textAnnotation")
		move(x,y,height,width)
	}
	
	TextArea text;
	Line line;
	
	def List<Node> getAllParts(){
		return List.of(this,text,line)
	}
	
	
	def move(double x, double y,double height, double width){
		text.layoutX = x
		text.layoutY = y
		text.minWidth = width
		text.minHeight = height
		text.maxWidth = width
		text.maxHeight = height
		line.startX = x;
		line.startY = y;
		line.endX = 0;
		line.endY = 0;
		this.x = x;
		this.y = y;
		this.width = width
		this.height = height
	}
	
}