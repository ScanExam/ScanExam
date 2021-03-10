package fr.istic.tools.scanexam.view.fX

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox

class ListViewBox extends VBox{
	new(String text,Box parent) {
		super()
		top = new HBox();
		middle = new HBox();
		bottom = new HBox();
		this.id = parent.boxId;
		this.parent = parent
		
		field = new TextField(text);
		field.maxWidth =  75;
		field.visible = false;
		
		nameLabel = new Label(text);
		nameLabel.minWidth = 100;
		
		
		numberField = new NumberTextField();
		numberField.text = "0"
		numberField.visible = false;
		
		pointsLabel = new Label("0");
		pointsLabel.minWidth = 100;
		
		
		
		var p = new Pane();
		var p2 = new Pane();
		
		this.children.addAll(top,middle,bottom)
		
		top.children.addAll(p)
		middle.children.addAll(p2)
		p.children.addAll(field,nameLabel)
		p2.children.addAll(numberField,pointsLabel)
		makeButtons();
		
		
	}
	Label nameLabel;
	Label pointsLabel;
	TextField field;
	
	NumberTextField numberField
	HBox top;
	HBox middle;
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
		//up = new Button("");
		//down = new Button("");
		rm = new Button("remove");
		resize = new Button("resize")
		move = new Button("move")
		//var upImage = new Image(ResourcesUtils.getInputStreamResource("/viewResources/upArrow.png"),FXSettings.BUTTON_ICON_SIZE,FXSettings.BUTTON_ICON_SIZE,true,true)
		//up.graphic = new ImageView(upImage)
		//top.children.add(0,up)
		//var downImage = new Image(ResourcesUtils.getInputStreamResource("/viewResources/downArrow.png"),FXSettings.BUTTON_ICON_SIZE,FXSettings.BUTTON_ICON_SIZE,true,true)
		//down.graphic = new ImageView(downImage)
		//bottom.children.add(down);
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
	
	def void setTextCommit(EventHandler<ActionEvent> handler){
		field.onAction = handler
	}
	
	def void setPointsCommit(EventHandler<ActionEvent> handler){
		numberField.onAction = handler
	}
	def void setRenameOption(EventHandler<ActionEvent> handler){
		var menu = new ContextMenu();
		var renameMenu = new MenuItem("Rename")
		renameMenu.onAction = handler
		menu.items.add(renameMenu);
		nameLabel.contextMenu = menu
	}
	def void setChangePoints(EventHandler<ActionEvent> handler){
		var menu = new ContextMenu();
		var renameMenu = new MenuItem("Change Points")
		renameMenu.onAction = handler
		menu.items.add(renameMenu);
		pointsLabel.contextMenu = menu
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
	def void setPointsText(String text){
		pointsLabel.text = text
	}
	
	def String getName(){
		field.text
	}
	
	def void toggleRenaming(){
		field.visible = !field.visible;
		if (field.visible) field.requestFocus();
		field.selectAll();
		nameLabel.visible = !nameLabel.visible;
	}
	def void togglePointChange(){
		numberField.visible = !numberField.visible;
		if (numberField.visible) numberField.requestFocus();
		numberField.selectAll();
		pointsLabel.visible = !pointsLabel.visible;
	}
	
	def Label getNameLabel(){
		return nameLabel
	}
	
}