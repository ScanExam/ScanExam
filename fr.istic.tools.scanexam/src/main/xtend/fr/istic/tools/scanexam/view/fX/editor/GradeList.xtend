package fr.istic.tools.scanexam.view.fX.editor

import java.util.LinkedList
import java.util.List
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class GradeList extends VBox {
	
	new(ControllerFXEditor controller){
		this.controller = controller;
		allItems = new LinkedList<GradeItem>();
		add = new Button("Add");
		var topBox = new HBox();
		topBox.children.add(add);
		itemContainer = new VBox();
		this.children.addAll(topBox,itemContainer);
		setupEvents(this)
		clearDisplay
	}
	List<GradeItem> allItems;
	
	Button add;
	VBox itemContainer;
	EditorQuestionItem currentItem;
	ControllerFXEditor controller;
	
	def showFor(EditorQuestionItem item) {
		clearDisplay
		currentItem = item;
		add.visible = true
		for (GradeItem g : allItems) {
			if (isGradeOf(g,item)) {
				display(g);
			}
		}
	}
	
	def loadGradeItem(GradeItem toAdd){//TODO
		
	}
	
	def newGradeItem() {
		if (currentItem === null) {
			return 
		}
		var gradeItem = new GradeItem(this,currentItem);
		display(gradeItem)
		allItems.add(gradeItem);
		addGradeItemToModel(gradeItem); 
	}
	
	def updateGradeItem(GradeItem toUpdate) {
		updateGradeItemInModel(toUpdate)
	}
	
	def removeGradeItem(GradeItem toRemove) {
		itemContainer.children.remove(toRemove)
		allItems.remove(toRemove)
		removeGradeItemFromModel(toRemove) 
	}
	
	def addGradeItemToModel(GradeItem toAdd){
		toAdd.gradeItemId = controller.editor.presenter.presenterMarkingScheme.addEntry(toAdd.gradeQuestionItem.questionId,toAdd.gradeItemName,Float.parseFloat(toAdd.gradeItemPoints))
	}
	def updateGradeItemInModel(GradeItem toUpdate){
		controller.editor.presenter.presenterMarkingScheme.modifyEntry(toUpdate.gradeQuestionItem.questionId,toUpdate.gradeItemId,toUpdate.gradeItemName,Float.parseFloat(toUpdate.gradeItemPoints))
	}
	def removeGradeItemFromModel(GradeItem toRemove){
		controller.editor.presenter.presenterMarkingScheme.removeEntry(toRemove.gradeQuestionItem.questionId,toRemove.gradeItemId)
	}
	
	
	
	
	def boolean isGradeOf(GradeItem gItem,EditorQuestionItem qItem) {
		return gItem.gradeQuestionItem == qItem
	}
	
	def clearDisplay(){
		add.visible = false
		itemContainer.children.clear
		currentItem = null
	}
	
	def display(GradeItem item) {
		itemContainer.children.add(item)
	}
	
	
	def setupEvents(GradeList list){
		add.onAction = new EventHandler<ActionEvent>(){
			
			override handle(ActionEvent event) {
				newGradeItem
			}
			
		}
	}

}