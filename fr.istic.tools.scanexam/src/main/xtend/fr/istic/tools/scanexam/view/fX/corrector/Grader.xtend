package fr.istic.tools.scanexam.view.fX.corrector

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.geometry.Insets
import javafx.scene.control.ScrollPane
import javafx.scene.control.ScrollPane.ScrollBarPolicy

class Grader extends VBox {

	new(ControllerFXCorrector controller){
		this.controller = controller
		var scrollp = new ScrollPane();
		
		scrollp.hbarPolicy = ScrollBarPolicy.NEVER
		scrollp.styleClass.add("GradeList")
		
		itemContainer = new VBox();
		add = new Button("Add new Grade Entry");
		
		add.styleClass.add("InfinityButton")
		
		scrollp.content = itemContainer
		this.children.addAll(scrollp,add);
		this.prefWidth = 170;
		this.maxHeight = 500;
		this.prefHeight = USE_COMPUTED_SIZE
		this.styleClass.add("Grader")
		setupEvents
	}
	
	Button add;
	VBox itemContainer;
	ControllerFXCorrector controller;
	
	def displayQuestion(QuestionItemCorrector qItem,StudentItemCorrector sItem) {
		clearDisplay()
		//create each gradeEntry from the model for the question item for the correct student
		
		
	}
	
	def createNewGradeEntry(){
		var entry = new GradeItem();
		itemContainer.children.add(entry)
		addEntryToModel(entry);
	}
	
	
	def addEntryToModel(GradeItem item){
		
	}
	
	def clearDisplay(){
		itemContainer.children.clear();
	}
	
	static class GradeItem extends VBox {
		new(){
			var topRow = new HBox()
			text = new Label("This is a test Grate entry name, here you will be able to add a description of this entry, and eventually have some latec/html");
			check = new CheckBox();
			worth = new Label("5.0");
			text.wrapText = true
			text.maxWidth = 130;
			worth.padding = new Insets(0,0,0,10);
			text.margin = new Insets(0,0,0,10);
			this.margin = new Insets(0,0,10,0);
			topRow.children.addAll(check,worth);
			this.children.addAll(topRow,text);
		}
		Label text;
		Label worth;
		CheckBox check;
	}
	
	
	def setupEvents(){
		add.onAction = new EventHandler<ActionEvent>(){
			
			override handle(ActionEvent event) {
				createNewGradeEntry
			}
			
		}
		
	}
}