package fr.istic.tools.scanexam.view.fx.graduation

import javafx.scene.shape.Rectangle

class TextAnotation extends Rectangle {
	new(double x, double y, double height, double width, String text){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.text = text
	}
	
	String text
	
}