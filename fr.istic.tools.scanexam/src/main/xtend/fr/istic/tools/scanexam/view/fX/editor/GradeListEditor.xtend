package fr.istic.tools.scanexam.view.fX.editor

import java.util.LinkedList
import java.util.List
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class GradeListEditor extends VBox {
	
	//---Controller---//
	new(ControllerFXEditor controller){
		this.controller = controller;
		allItems = new LinkedList<GradeItemEditor>();
		add = new Button("Add");
		var topBox = new HBox();
		topBox.children.add(add);
		itemContainer = new VBox();
		this.children.addAll(topBox,itemContainer);
		setupEvents(this)
		clearDisplay
	}
	List<GradeItemEditor> allItems;
	
	//----------------//
	
	//---FX vars---//
	Button add;
	VBox itemContainer;
	QuestionItemEditor currentItem;
	ControllerFXEditor controller;
	//-------------//
	
	//---Methods---//
	def select(QuestionItemEditor item) {
		clearDisplay
		currentItem = item;
		add.visible = true
		for (GradeItemEditor g : allItems) {
			if (isGradeOf(g,item)) {
				display(g);
			}
		}
	}
	
	def boolean isGradeOf(GradeItemEditor gItem,QuestionItemEditor qItem) {
		return gItem.gradeQuestionItem == qItem
	}
	
	def clearDisplay(){
		add.visible = false
		itemContainer.children.clear
		currentItem = null
	}
	
	def display(GradeItemEditor item) {
		itemContainer.children.add(item)
	}
	
	def loadGradeItem(GradeItemEditor toAdd){//TODO
		
	}
	
	def newGradeItem() {
		if (currentItem === null) {
			return 
		}
		var gradeItem = new GradeItemEditor(this,currentItem);
		display(gradeItem)
		allItems.add(gradeItem);
		addGradeItemToModel(gradeItem); 
	}
	
	def updateGradeItem(GradeItemEditor toUpdate) {
		updateGradeItemInModel(toUpdate)
	}
	
	def removeGradeItem(GradeItemEditor toRemove) {
		itemContainer.children.remove(toRemove)
		allItems.remove(toRemove)
		removeGradeItemFromModel(toRemove) 
	}
	
	def clear(){
		clearDisplay
		this.itemContainer.children.clear
	}
	//-------------//
	
	//---Model Interactions---//
	def addGradeItemToModel(GradeItemEditor toAdd){
		toAdd.gradeItemId = controller.editor.presenter.presenterMarkingScheme.addEntry(toAdd.gradeQuestionItem.questionId,toAdd.gradeItemName,Float.parseFloat(toAdd.gradeItemPoints))
	}
	def updateGradeItemInModel(GradeItemEditor toUpdate){
		controller.editor.presenter.presenterMarkingScheme.modifyEntry(toUpdate.gradeQuestionItem.questionId,toUpdate.gradeItemId,toUpdate.gradeItemName,Float.parseFloat(toUpdate.gradeItemPoints))
	}
	def removeGradeItemFromModel(GradeItemEditor toRemove){
		controller.editor.presenter.presenterMarkingScheme.removeEntry(toRemove.gradeQuestionItem.questionId,toRemove.gradeItemId)
	}
	//------------------------//
	
	
	
	
	
	//---Setups---//
	def setupEvents(GradeListEditor list){
		add.onAction = new EventHandler<ActionEvent>(){
			
			override handle(ActionEvent event) {
				newGradeItem
			}
			
		}
	}
	//------------//

}