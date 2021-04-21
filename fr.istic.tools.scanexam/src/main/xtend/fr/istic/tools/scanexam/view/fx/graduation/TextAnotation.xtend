package fr.istic.tools.scanexam.view.fx.graduation

import java.util.List
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.shape.Line
import javafx.scene.layout.Pane

class TextAnotation extends VBox {
	new(double x, double y, double height, double width, String text){
		this.text = new TextArea(text);

		bar = new HBox();
		close = new Label();
		
		bar.maxHeight = 10;
		var p = new Pane()
		bar.children.addAll(p,close)
		this.children.addAll(bar,this.text)
		line = new Line();
		
		
		
		
		
		this.text.wrapText = true;
		this.text.styleClass.add("textAnnotation")
		this.styleClass.add("annotationBox")
		move(x,y,height,width)
	}
	
	TextArea text;
	Line line;
	HBox bar;
	Label close;
	def List<Node> getAllParts(){
		return List.of(this,line)
	}
	
	
	def move(double x, double y,double height, double width){
		this.layoutX = x
		this.layoutY = y
		this.minWidth = width
		this.minHeight = height
		this.maxWidth = width
		this.maxHeight = height
		line.startX = x;
		line.startY = y;
		line.endX = 0;
		line.endY = 0;
		
	}
	
}