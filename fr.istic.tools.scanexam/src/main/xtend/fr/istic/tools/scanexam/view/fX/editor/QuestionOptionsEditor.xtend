package fr.istic.tools.scanexam.view.fX.editor

import javafx.scene.layout.VBox
import javafx.scene.control.Label
import javafx.scene.control.Button
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.layout.GridPane
import javafx.scene.input.MouseEvent
import javafx.scene.control.TextField
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue

class QuestionOptionsEditor extends VBox{
	new (ControllerFXEditor controller){
		this.controller = controller
		
		grid = new GridPane();
		
		var l1 = new Label("Name :")
		var l2 = new Label("ID :")
		var l3 = new Label("Page :")
		var l4 = new Label("Weight :")
		var l5 = new Label("Position :")
		var l6 = new Label("Description :")
		grid.add(l1,0,0)
		grid.add(l2,0,1)
		grid.add(l3,0,2)
		grid.add(l4,0,3)
		grid.add(l5,0,4)
		grid.add(l6,0,5)
							
		renameField = new TextField();					

		questionName = new Label();
		questionId = new Label()
		page = new Label();
		questionWeight = new Label();
		questionCoords = new Label();
		questionDescription = new Label();
		remove = new Button("Remove");
		
		grid.add(questionName,1,0)
		grid.add(questionId,1,1)
		grid.add(page,1,2)
		grid.add(questionWeight,1,3)
		grid.add(questionCoords,1,4)
		grid.add(questionDescription,1,5)
		
		children.addAll(grid,remove)	
		hideAll
		setupEvents();
	}
	
	GridPane grid;
	Label questionName;
	TextField renameField;
	Label questionId;
	Label page;
	Label questionWeight;
	Label questionCoords;
	Label questionDescription; //TODO replace with htlm display
	Button remove;
	ControllerFXEditor controller;
	
	QuestionItemEditor currentItem
	
	/**
	 * Selects a question to display
	 */
	def select(QuestionItemEditor item) {
		showAll()
		currentItem = item
		questionName.text = item.name
		renameField.text = item.name
		page.text = item.page + ""
		questionId.text = "" + item.questionId
		questionWeight.text = "" + item.weight;
		questionCoords.text = "X:" + item.zone.x + "\nY:" + item.zone.y + "\nH:" + item.zone.height + "\nW:"+ item.zone.width
		questionDescription.text = "No description";	
	}
	
	def showAll(){
		this.visible = true
	}
	
	def hideAll(){
		this.visible = false
	}
	/**
	 * Toggles if the textfield is the visible element 
	 */
	def toggleRename(boolean b){
		if (b) {
			grid.children.remove(questionName);
			grid.add(renameField,1,0);
		}
		else {
			if (!grid.children.contains(questionName)) {
				grid.children.remove(renameField);
				grid.add(questionName,1,0);
			}
		}
	}
	/**
	 * called to commit a name change
	 *  */
	def commitRename(){
		questionName.text = renameField.text
		currentItem.name =  renameField.text
		controller.questionList.updateInModel(currentItem)
	}
	
	def setupEvents(){
		remove.onAction = new EventHandler<ActionEvent>(){
			
			override handle(ActionEvent event) {
				controller.questionList.remove(currentItem)
				controller.selectQuestion(null)
			}
			
		}
		
		questionName.onMouseClicked = new EventHandler<MouseEvent>(){
			
			override handle(MouseEvent event) {
				toggleRename(true)
			}
			
		}
		
		renameField.onAction = new EventHandler<ActionEvent>(){
			
			override handle(ActionEvent event) {
				print("rename actions\n")
				commitRename();
				toggleRename(false);
				
			}
			
		} 
		renameField.focusedProperty.addListener(new ChangeListener<Boolean>() {
			
			override changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					print("rename actions\n")
					commitRename();
					toggleRename(false);
				}
			}
			
			})
	}
}