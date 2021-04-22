package fr.istic.tools.scanexam.view.fx.graduation

import java.util.List
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.text.Text

class TextAnotation extends VBox {
	new(double x, double y, double height, double width, String text,PdfPaneWithAnotations parent){
		this.parent = parent
		
		this.text = new TextArea(text);
		textHolder = new Text();
	
		bar = new HBox();
		close = new Label("X");
		
		bar.maxHeight = 5;
		var p = new Pane()
		bar.children.addAll(p,close)
		this.children.addAll(bar,this.text)
		line = new Line();
		
		ball = new Circle();
		ball.radius = 3;
		ball.cursor = Cursor.MOVE
		this.cursor = Cursor.MOVE
		
		this.text.wrapText = true;
		this.text.styleClass.add("textAnnotation")
		this.styleClass.add("annotationBox")
		move(x,y,height,width)
		setupEvents
	}
	
	TextArea text;
	Line line;
	Circle ball;
	HBox bar;
	Label close;
	PdfPaneWithAnotations parent;
	Text textHolder;
	double oldHeight = 0f

	def List<Node> getAllParts(){
		return List.of(line,ball,this)
	}
	
	
	def move(double x, double y,double height, double width){
		this.layoutX = x
		this.layoutY = y
		this.minWidth = width
		this.maxWidth = width
		line.startX = x + width/2;
		line.startY = y + height/2;
		line.endX = x-15;
		line.endY = y-15;
		ball.centerX = x-15;
		ball.centerY = y-15;
	}
	
	def move(double x, double y){
		this.layoutX = x
		this.layoutY = y
		line.startX = x + this.maxWidth/2;
		line.startY = y + this.maxHeight/2;
	}
	
	def movePointer(double x , double y){
		line.endX = x;
		line.endY = y;
		ball.centerX = x;
		ball.centerY = y;
	}
	
	
	
	
	def setupEvents(){
		textHolder.textProperty.bind(this.text.textProperty)
		textHolder.layoutBoundsProperty.addListener([obs,oldVal,newVal | {if (oldHeight != newVal.getHeight()) {
                oldHeight = newVal.getHeight();
                text.setPrefHeight(textHolder.getLayoutBounds().getHeight() + 20); 
                System.out.println(textHolder.getLayoutBounds().getHeight());
            }}])
		this.onMousePressed = [event |parent.handleMoveAnnotation(this,event)]
		ball.onMousePressed = [event | parent.handleMovePointer(this,event)]
	}
	
}