package fr.istic.tools.scanexam.view.fX

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.control.TextField

class GradeItemHBox extends HBox {
		
		new(){
			super();
			var p = new Pane();
			this.children.add(p)
			
			remove = new Button("-");
			this.children.add(remove)
			field = new NumberTextField();
			field.visible = false
			label = new Label("0");
			p.children.addAll(field,label);
		}
		
		int gradeItemId;
		Button remove;
		NumberTextField field;
		Label label;
		
		Label nameLabel;
		TextField nameTextField;
		
		def String getPoints(){
			return label.text
		}
		
		def void setButtonAction(EventHandler<ActionEvent> handler) {
			remove.onAction = handler
		}
		
		def getGradeItemId(){
			gradeItemId
		}
		
		def setGradeItemId(int id) {
			gradeItemId = id
		}
		
		def setGradeItemName(String name){
			nameLabel.text = name
		}
		def getGradeItemName(){
			nameLabel.text
		}
		
		def getGradeItemPoints(){
			label.text
		}
		
		def setGradeItemPoints(double points) {
			label.text = points + ""
		}
		
		def void setChangePoints(EventHandler<ActionEvent> handler){
			var menu = new ContextMenu();
			var renameMenu = new MenuItem("Change Points")
			renameMenu.onAction = handler
			menu.items.add(renameMenu);
			label.contextMenu = menu
		}
		
		def void setRemoveGradeItemAction(EventHandler<ActionEvent> handler) {
			remove.onAction = handler
		}
		
	
		
		
	}