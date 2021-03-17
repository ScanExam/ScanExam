package fr.istic.tools.scanexam.view.fX.editor

import fr.istic.tools.scanexam.view.fX.FXSettings
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.input.MouseEvent
import javafx.event.EventHandler

class Box extends Rectangle {
	
		
		
		new(EditorQuestionItem item,double x, double y , double width , double height) {
			super(x,y,width,height)
			this.questionItem = item
			this.strokeWidth = FXSettings.BOX_BORDER_THICKNESS
			this.stroke = FXSettings.BOX_BORDER_NORMAL_COLOR
			this.fill = FXSettings.BOX_NORMAL_COLOR
			
		}
		new(double x, double y , double width , double height) {
			this(null,x,y,width,height)
		}
		
		EditorQuestionItem questionItem;
	
		
		def getQuestionItem(){
			questionItem
		}
		
		def setQuestionItem(EditorQuestionItem item){
			questionItem = item
		}		
		
		def void isVisible(boolean b){
			this.visible = b;
		}
		
		def setFocus(boolean b){
			if (b) {
				this.stroke = FXSettings.BOX_BORDER_HIGHLIGHT_COLOR
				this.fill = FXSettings.BOX_HIGHLIGHT_COLOR
			}
			else {
				this.stroke = FXSettings.BOX_BORDER_NORMAL_COLOR
				this.fill = FXSettings.BOX_NORMAL_COLOR
			}
		}
		
		def void setColor(Color color) {
			stroke = color
		}
		
		def void x(double x) {
			setX(x);
		}
		def void y(double y) {
			setY(y);
		}
		
		def void height(double h) {
			setHeight(h)
		}
		
		def void width(double w) {
			setWidth(w);
		}
		
		def checkOnNorthBorder(MouseEvent e){
			if (e.x > this.x - FXSettings.ZONE_RESIZE_TOLERANCE && e.x < this.x + this.width + FXSettings.ZONE_RESIZE_TOLERANCE ) {
				if (e.y > this.y - FXSettings.ZONE_RESIZE_TOLERANCE && e.y < this.y + FXSettings.ZONE_RESIZE_TOLERANCE) {
					return true
				}
			}
			return false
		}
		
		def checkOnSouthBorder(MouseEvent e) {
			if (e.x > this.x - FXSettings.ZONE_RESIZE_TOLERANCE && e.x < this.x + this.width + FXSettings.ZONE_RESIZE_TOLERANCE ) {
				if (e.y > this.y - FXSettings.ZONE_RESIZE_TOLERANCE + this.height && e.y < this.y + FXSettings.ZONE_RESIZE_TOLERANCE + this.height) {
					return true
				}
			}
			return false
		}
		
		def checkOnWestBorder(MouseEvent e) {
			if (e.x > this.x - FXSettings.ZONE_RESIZE_TOLERANCE && e.x < this.x + FXSettings.ZONE_RESIZE_TOLERANCE ) {
				if (e.y > this.y - FXSettings.ZONE_RESIZE_TOLERANCE && e.y < this.y + FXSettings.ZONE_RESIZE_TOLERANCE + this.height) {
					return true
				}
			}
			return false
		}
		def checkOnEastBorder(MouseEvent e) {
			if (e.x > this.x - FXSettings.ZONE_RESIZE_TOLERANCE + this.width && e.x < this.x + this.width + FXSettings.ZONE_RESIZE_TOLERANCE + this.width ) {
				if (e.y > this.y - FXSettings.ZONE_RESIZE_TOLERANCE && e.y < this.y + FXSettings.ZONE_RESIZE_TOLERANCE + this.height) {
					return true
				}
			}
			return false
		}
		def checkOnCorner(){
			
		}
		
		def setupEvents(){
			var zone = this
			zone.onMouseMoved = new EventHandler<MouseEvent> {
			
				override handle(MouseEvent event) {
					if (checkOnNorthBorder(event)) {
						print("on north \n")
					}
					if (checkOnSouthBorder(event)) {
						print("on south \n")
					}
					if (checkOnEastBorder(event)) {
						print("on East \n")
					}
					if (checkOnWestBorder(event)) {
						print("on West \n")
					}
				}
			}
		}
		
		

	}