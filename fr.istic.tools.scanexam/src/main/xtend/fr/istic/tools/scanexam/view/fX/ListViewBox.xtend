package fr.istic.tools.scanexam.view.fX

import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.control.Button
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.layout.VBox
import javafx.scene.image.ImageView
import fr.istic.tools.scanexam.utils.ResourcesUtils
import javafx.scene.image.Image
import javafx.scene.control.Label

class ListViewBox extends VBox{
	new(String text,Box parent) {
		super()
		top = new HBox();
		bottom = new HBox();
		this.id = parent.boxId;
		this.parent = parent
		field = new TextField(text);
		field.maxWidth =  75;
		nameLabel = new Label(text);
		this.children.add(top);
		this.children.add(bottom)
		
		top.children.add(nameLabel)
		top.children.add(field)
		makeButtons();
		
	}
	Label nameLabel;
	TextField field;
	HBox top;
	HBox bottom;
	int id;
	Box parent;
	Button up;
	Button down;
	Button rm;
	Button resize;
	Button move;
	def getParentBox(){
		parent
	}
	
	def getBoxId(){
		id
	}
	
	def makeButtons(){
		up = new Button("");
		down = new Button("");
		rm = new Button("remove");
		resize = new Button("resize")
		move = new Button("move")
		var upImage = new Image(ResourcesUtils.getInputStreamResource("/viewResources/upArrow.png"),FXSettings.BUTTON_ICON_SIZE,FXSettings.BUTTON_ICON_SIZE,true,true)
		up.graphic = new ImageView(upImage)
		top.children.add(0,up)
		var downImage = new Image(ResourcesUtils.getInputStreamResource("/viewResources/downArrow.png"),FXSettings.BUTTON_ICON_SIZE,FXSettings.BUTTON_ICON_SIZE,true,true)
		down.graphic = new ImageView(downImage)
		bottom.children.add(down);
		bottom.children.add(rm);
		bottom.children.add(resize);
		bottom.children.add(move);
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
	
	def void setTextCommit(EventHandler<ActionEvent> hander){
		field.onAction = hander
	}
	
	def void setMoveAction(EventHandler<ActionEvent> hander){
		move.onAction = hander
	}
	def void setResizeAction(EventHandler<ActionEvent> hander){
		resize.onAction = hander
	}
	
	def void setLabelText(String text) {
		nameLabel.text = text
	}
	
	
}