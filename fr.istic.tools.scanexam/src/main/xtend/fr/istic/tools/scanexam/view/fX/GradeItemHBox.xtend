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
		
		new(int questionId){
			super();
			var p1 = new Pane();
			p1.maxWidth = 50
			var p2 = new Pane();
			p2.maxWidth = 50
			this.children.add(p1)
			this.children.add(p2)
			this.questionId = questionId
			
			remove = new Button("-");
			this.children.add(remove)
			
			nameLabel = new Label("Torchon")
			nameTextField = new TextField(nameLabel.text)
			nameTextField.visible = false;
			p1.children.addAll(nameLabel,nameTextField)
			
			label = new Label("0");
			field = new NumberTextField();
			field.text = label.text
			field.visible = false
			
			p2.children.addAll(field,label);
		}
		int questionId;
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
		
		def getGradeQuestionId(){
			questionId
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
		
		def setGradeItemPoints(String points) {
			label.text = points + ""
		}
		
		def void setChangePoints(EventHandler<ActionEvent> handler){
			var menu = new ContextMenu();
			var renameMenu = new MenuItem("Change Points")
			renameMenu.onAction = handler
			menu.items.add(renameMenu);
			label.contextMenu = menu
		}
		
		def void setChangeName(EventHandler<ActionEvent> handler){
			var menu = new ContextMenu();
			var renameMenu = new MenuItem("Rename")
			renameMenu.onAction = handler
			menu.items.add(renameMenu);
			nameLabel.contextMenu = menu
		} 
		
		def void setNameCommit(EventHandler<ActionEvent> handler){
			nameTextField.onAction = handler
		}
		
		def void setPointsCommit(EventHandler<ActionEvent> handler){
			field.onAction = handler
		}
		def void setRemoveGradeItemAction(EventHandler<ActionEvent> handler) {
			remove.onAction = handler
		}
		
		
		def void toggleRenaming(){
			nameTextField.visible = !nameTextField.visible;
			if (nameTextField.visible) nameTextField.requestFocus();
			nameTextField.selectAll();
			nameLabel.visible = !nameLabel.visible;
		}
		def void togglePointChange(){
			field.visible = !field.visible;
			if (field.visible) field.requestFocus();
			field.selectAll();
			label.visible = !label.visible;
		}
		
		def String getPointFieldText(){
			field.text
		}
		
		def String getNameFieldText(){
			nameTextField.text
		}
	
		
		
	}