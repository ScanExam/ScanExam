package fr.istic.tools.scanexam.view.fX

import javafx.scene.control.TextField
import javafx.scene.layout.HBox

class ListViewBox extends HBox{
	new(String text,Box parent) {
		super()
		this.parent = parent
		var field = new TextField(text);
		field.maxWidth =  75;
		this.children.add(field);
		
	}
	Box parent;
	
	def getParentBox(){
		parent
	}
	
	
}