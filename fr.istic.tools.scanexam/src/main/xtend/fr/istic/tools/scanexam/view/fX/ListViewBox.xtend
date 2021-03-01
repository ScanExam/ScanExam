package fr.istic.tools.scanexam.view.fX

import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.control.Button

class ListViewBox extends HBox{
	new(String text,Box parent) {
		super()
		this.parent = parent
		var field = new TextField(text);
		field.maxWidth =  75;
		this.children.add(field);
		makeButtons();
		
	}
	Box parent;
	
	def getParentBox(){
		parent
	}
	
	def makeButtons(){
		var up = new Button("up");
		var down = new Button("down");
		var rm = new Button("remove");
		this.children.add(up);
		this.children.add(down);
		this.children.add(rm);
	}
	
	
}