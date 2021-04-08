package fr.istic.tools.scanexam.view.fX.editor

import fr.istic.tools.scanexam.view.fX.FXSettings
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.input.MouseEvent
import javafx.event.EventHandler
import javafx.scene.Cursor

class Box extends Rectangle {
	
		
		//---Controller---//
		new(QuestionItemEditor item,double x, double y , double width , double height) {
			super(x,y,width,height)
			this.questionItem = item
			this.strokeWidth = FXSettings.BOX_BORDER_THICKNESS
			this.stroke = FXSettings.BOX_BORDER_NORMAL_COLOR
			this.fill = FXSettings.BOX_NORMAL_COLOR
			
		}
		new(double x, double y , double width , double height) {
			this(null,x,y,width,height)
		}
		//----------------//
		
		//---FX vars---//
		QuestionItemEditor questionItem;
		PdfPane pane;
		//-------------//
		
		//---GETTERS/SETTERS---//
		def getQuestionItem(){
			questionItem
		}
		
		def setQuestionItem(QuestionItemEditor item){
			questionItem = item
		}
		
		def setPane(PdfPane pane){
			this.pane = pane
		}	
		//---------------------//
		
		//---METHODS---//
		
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
			val zone = this
			zone.onMousePressed = new EventHandler<MouseEvent> {
			
				override handle(MouseEvent event) {
					var onNorth = checkOnNorthBorder(event)
					var onSouth = checkOnSouthBorder(event)
					var onEast = checkOnEastBorder(event)
					var onWest = checkOnWestBorder(event)
					pane.controller.selectQuestion(questionItem)
					pane.controller.setToResizeTool()
					pane.controller.edgeLoc = EdgeLocation.NONE
					if (onNorth) pane.controller.edgeLoc = EdgeLocation.NORTH
					if (onSouth) pane.controller.edgeLoc = EdgeLocation.SOUTH
					if (onEast) pane.controller.edgeLoc = EdgeLocation.EAST
					if (onWest) pane.controller.edgeLoc = EdgeLocation.WEST
					if (onNorth && onEast) pane.controller.edgeLoc = EdgeLocation.NORTHEAST
					if (onNorth && onWest) pane.controller.edgeLoc = EdgeLocation.NORTHWEST
					if (onSouth && onEast) pane.controller.edgeLoc = EdgeLocation.SOUTHEAST
					if (onSouth && onWest) pane.controller.edgeLoc = EdgeLocation.SOUTHWEST
				}
			}
			zone.onMouseMoved = new EventHandler<MouseEvent> {
			
				override handle(MouseEvent event) {
					var onNorth = checkOnNorthBorder(event)
					var onSouth = checkOnSouthBorder(event)
					var onEast = checkOnEastBorder(event)
					var onWest = checkOnWestBorder(event)
					cursor = Cursor.DEFAULT
					if (onNorth)  cursor =Cursor.V_RESIZE
					if (onSouth)  cursor =Cursor.V_RESIZE
					if (onEast) cursor = Cursor.H_RESIZE
					if (onWest) cursor = Cursor.H_RESIZE
					if (onNorth && onEast) cursor = Cursor.NE_RESIZE
					if (onNorth && onWest) cursor = Cursor.NW_RESIZE
					if (onSouth && onEast) cursor = Cursor.NW_RESIZE
					if (onSouth && onWest) cursor = Cursor.NE_RESIZE
				}
			}
		}
		
		

	}