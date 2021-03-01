package fr.istic.tools.scanexam.view.fX

import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.control.Button
import javafx.event.ActionEvent
import javafx.event.EventHandler

class ListViewBox extends HBox{
	new(String text,Box parent) {
		super()
		this.id = parent.boxId;
		this.parent = parent
		var field = new TextField(text);
		field.maxWidth =  75;
		this.children.add(field);
		makeButtons();
		
	}
	int id;
	Box parent;
	Button up;
	Button down;
	Button rm;
	def getParentBox(){
		parent
	}
	
	def getBoxId(){
		id
	}
	
	def makeButtons(){
		up = new Button("up");
		down = new Button("down");
		rm = new Button("remove");
		this.children.add(up);
		this.children.add(down);
		this.children.add(rm);
	}
	def void setUpAction(EventHandler<ActionEvent> handler) {
		up.onAction = handler
	}
	def void setDownAction(EventHandler<ActionEvent> handler) {
		down.onAction = handler
	}
	def void setRemoveAction(EventHandler<ActionEvent> handler) {
		rm.onAction = handler
	}
	
	
}