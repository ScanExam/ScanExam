package fr.istic.tools.scanexam.view.fx.graduation

import java.util.List
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.geometry.Pos

class TextAnotation extends VBox {
	static int anotFontSize = 8;
	new(double x, double y, String text,PdfPaneWithAnotations parent){
		this.parent = parent
		
		this.text = new TextArea(text);
		textHolder = new Text();
		textHolder.font = new Font(anotFontSize);
		this.text.font = new Font(anotFontSize);
		textHolder.styleClass.add("textAnnotation")
		
		bar = new HBox();
		bar.alignment = Pos.TOP_RIGHT
		close = new Button("X");
		
		bar.maxHeight = 5;
		var p = new Pane()
		bar.children.addAll(p,close)
		this.children.addAll(bar,this.text)
		line = new Line();
		
		ball = new Circle();
		ball.radius = 3;
		ball.cursor = Cursor.MOVE
		this.cursor = Cursor.MOVE
		
		this.text.wrapText = false;
		this.text.styleClass.add("textAnnotation")
		this.styleClass.add("annotationBox")
		initPos(x,y)
		
		this.text.minWidth = 50;
		this.text.minHeight = 50;
		this.text.prefWidth = 50;
		this.text.prefHeight = 50;
		setupEvents
	}

	int annotId;
	TextArea text;
	Line line;
	Circle ball;
	HBox bar;
	Button close;
	PdfPaneWithAnotations parent;
	Text textHolder;
	double oldHeight = 0f
	double oldWidth = 0f
	def List<Node> getAllParts(){
		return List.of(line,ball,this)
	}
	
	def getAnnotId(){
		this.annotId
	}
	def setAnnotId(int id){
		this.annotId = id
	}
	
	def getAnnotX(){
		this.layoutX
	}
	
	def getAnnotY(){
		this.layoutY
	}
	
	def getAnnotH(){
		this.height
	}
	def getAnnotW(){
		this.width
	}
	def getAnnotText(){
		this.text.text
	}
	
	def getAnnotPointerX(){
		this.ball.centerX
	}
	def getAnnotPointerY(){
		this.ball.centerY
	}
	
	def initPos(double x, double y){
		this.layoutX = Math.min(x, parent.imageViewWidth - this.width)
		this.layoutY = Math.min(y, parent.imageViewWidth - this.height)
		line.startX = this.layoutX + this.width/2;
		line.startY = this.layoutY + this.height/2;
		line.endX = this.layoutX -15;
		line.endY = this.layoutY -15;
		ball.centerX = this.layoutX-15;
		ball.centerY = this.layoutY-15;
	}
	
	def move(double x, double y){
		this.layoutX = Math.min(x, parent.imageViewWidth - this.width)
		this.layoutY = Math.min(y, parent.imageViewHeight - this.height)
		line.startX = this.layoutX + this.width/2;
		line.startY = this.layoutY + this.height/2;
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
                this.maxHeight = parent.imageViewHeight - this.layoutY
            }
            if (oldWidth != newVal.width) {
            	oldWidth = newVal.width;
            	text.setPrefWidth(textHolder.getLayoutBounds().getWidth() + 20); 
            	this.maxWidth = parent.imageViewWidth - this.layoutX
            }}])
        text.setPrefHeight(textHolder.getLayoutBounds().getHeight() + 20);
        text.setPrefWidth(textHolder.getLayoutBounds().getWidth() + 20); 
        this.maxHeight = parent.imageViewHeight - this.layoutY
        this.maxWidth = parent.imageViewWidth - this.layoutX
		this.onMousePressed = [event |parent.handleMoveAnnotation(this,event)]
		ball.onMousePressed = [event | parent.handleMovePointer(this,event)]
		
		text.textProperty.addListener([obs,oldVal,newVal | parent.handleRename(this)])
	}
	
}