package fr.istic.tools.scanexam.view.fX.editor

import fr.istic.tools.scanexam.view.fX.FXSettings
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color

class EditorQuestionItem extends VBox{
	
	//---Constructors---//
	new(QuestionList list,Box zone) {
		super()
		top = new HBox();
		middle = new HBox();
		bottom = new HBox();
		remove = new Button("remove");
		bottom.children.add(remove);
		
		this.list = list
		this.zone = zone
		this.zone.questionItem = this
		
		name = new TextField("New Question");
		name.editable = false;
	
		
		this.children.addAll(top,middle,bottom)
	
		top.children.addAll(name)
		
		vgrow = Priority.ALWAYS
		zone.setupEvents
		setupContextMenu
		setupEvents(this)
	}
	new (QuestionList list,Box zone,BoxType type,int page){
		this(list,zone);
		this.type = type
		this.page = page
	}
	
	//------------------//
	
	//---FX VARS---//
	HBox top; //the top container of the questionItem
	HBox middle;//the middle container of the questionItem
	HBox bottom;//the bottom container of the questionItem
	Button remove;
	//-------------//
	
	//---Question vars---//
	Box zone;		//Zone of the question, a rectangle
	TextField name;	//textfield used to rename the question
	int questionId;	//Id of this question
	int page;		//page location of this question
	BoxType type;	//the type of this zone
	//-------------------//
	
	//---Other Vars---//
	QuestionList list 	// the container of all the question items
	//----------------//
	
	//---GETTERS/SETTERS---//
	def getZone(){
	 	zone
	}
	
	def getQuestionId(){
		questionId
	}
	
	def setQuestionId(int id){
		questionId = id
	}
	
	def getPage(){
		page
	}
	
	def setPage(int page){
		this.page = page
	}

	def void setFocus(boolean b) {//sets the color of the zone and the item in the list
			if (b) {
				color = FXSettings.ITEM_HIGHLIGHT_COLOR
				zone.focus = b
			}
			else {
				color = FXSettings.ITEM_NORMAL_COLOR
				zone.focus = b
			}
		}
	def setColor(Color color){
		var bf = new BackgroundFill(color,CornerRadii.EMPTY,Insets.EMPTY);
		this.background = new Background(bf)
	}
	
	def void setName(String text) {
		name.text = text
	}
	
	def String getName(){
		name.text
	}
	//---------------------//
	
	
	//---METHODS---//
	def setNameEditable(){
		name.editable = true
		name.selectAll
	}
	
	def commitNameChange(){
		name.editable = false
		list.updateInModel(this)
	}
	//-------------//


		
	
	//---SETUP METHODS---//	
	def setupContextMenu(){
		var menu = new ContextMenu();
		name.contextMenu = menu
		var menuItem1 = new MenuItem("Rename Question Item");//TODO translate
		menu.items.add(menuItem1);
		menuItem1.onAction = new EventHandler<ActionEvent>(){
			
			override handle(ActionEvent event) {
				setNameEditable
			}
			
		}
	}
	def setupEvents(EditorQuestionItem item){
		
		remove.onAction = new EventHandler<ActionEvent>(){
			
			override handle(ActionEvent event) {
				list.removeQuestion(item)
				list.controller.selectQuestion(null)
			}
			
		}
		
		this.onMouseClicked = new EventHandler<MouseEvent> {
			
			override handle(MouseEvent event) {
				list.controller.selectQuestion(item)
				
			}
			
		}
		
		name.onAction =  new EventHandler<ActionEvent>(){
				
				override handle(ActionEvent event) {
					commitNameChange
				}
			}
		
		
	}
	//-------------------//	
}
