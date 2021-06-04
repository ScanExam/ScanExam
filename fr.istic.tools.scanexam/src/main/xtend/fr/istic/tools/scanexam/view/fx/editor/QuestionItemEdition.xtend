package fr.istic.tools.scanexam.view.fx.editor

import fr.istic.tools.scanexam.view.fx.FxSettings
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color

import static fr.istic.tools.scanexam.config.LanguageManager.translate

/**
 * [JavaFX] Item question dans la liste de gauche des questions
 * 
 * This component is used as an item for the QuestionListEdition class.
 * We store the name, id , worth, and page position of the question, as well as a  referece to the box on the pdf
 * The main interaction with this class is changing its details such as worth and name ect,
 * setFocus is used when this item is clicked in the list .
 * 
 * Fixes to do : completely link to model, only store names for displaying the list and id, and use those to find the items in the model when we need box dimentions or worth.
 * 
 * 
 * @author Stefan Locke, Julien Cochet
 */
class QuestionItemEdition extends VBox {

	//----------------------------------------------------------------------------------------------------
	/*
	 *	VARIABLES
	 */
	//----------------------------------------------------------------------------------------------------
	
	//--- FX vars ---//
	/* the top container of the questionItem */
	HBox top
	/* the middle container of the questionItem */
	HBox middle
	/* the bottom container of the questionItem */
	HBox bottom
	/* button remote */
	Button remove
	
	//--- Question vars ---//
	/* Zone of the question, a rectangle */
	Box zone
	/* textfield used to rename the question */
	Label name
	/* Id of this question */
	int questionId
	/* page location of this question */
	int page
	/* Scale of the question */
	float scale
	/* the type of this zone */
	BoxType type
	
	//--- Other Vars---//
	/*the container of all the question items */
	QuestionListEdition list

	//----------------------------------------------------------------------------------------------------
	/*
	 *	CONSTRUCTEURS
	 */
	//----------------------------------------------------------------------------------------------------
	
	new(QuestionListEdition list, Box zone) {
		super()
		top = new HBox
		middle = new HBox
		bottom = new HBox
		remove = new Button(translate("question.remove"))
		bottom.children.add(remove)
		
		this.list = list
		this.zone = zone
		this.zone.questionItem = this

		name = new Label("New Question");

		this.children.addAll(top, middle, bottom)

		top.children.addAll(name)

		vgrow = Priority.ALWAYS
		zone.setupEvents

		this.scale  = 1f
		
		this.styleClass.add("ListItem");
		
		this.margin = new Insets(2);
		setupContextMenu
		setupEvents(this)
	}

	new(QuestionListEdition list, Box zone, BoxType type, int page) {
		this(list, zone);
		this.type = type
		this.page = page
	}

	new(QuestionListEdition list, Box zone, String name, int page, int id) {
		this(list, zone)
		this.name.text = name
		this.page = page
		this.questionId = id
	}
	
	//----------------------------------------------------------------------------------------------------
	/*
	 *	METHODES
	 */
	//----------------------------------------------------------------------------------------------------

	// ---------------------//
	// ---METHODS---//
	/*def setNameEditable(){
	 * 	name.editable = true
	 * 	name.selectAll
	 * }
	 * 
	 * def commitNameChange(){
	 * 	name.editable = false
	 * 	list.updateInModel(this)
	 * }
	 */
	// -------------//
	// ---SETUP METHODS---//	
	/**
	 * Used to stetup the context menu (right click), called on the setupevent Method
	 */
	def setupContextMenu() {
	}
	/**
	 * Used to setup events for this item, called at the end of the constructor.
	 */
	def setupEvents(QuestionItemEdition item) {

		remove.onAction = new EventHandler<ActionEvent>() {
			
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

	}

	//----------------------------------------------------------------------------------------------------
	/*
	 *	GETTERS
	 */
	//----------------------------------------------------------------------------------------------------
	
	def getWeight() {
		1
	}
	
	def getScale() {
		scale
	}

	def getZone() {
		zone
	}

	def getQuestionId() {
		questionId
	}
	
	def getPage() {
		page
	}

	def String getName() {
		name.text
	}
	
	def BoxType getType() {
		type
	}
	
	//----------------------------------------------------------------------------------------------------
	/*
	 *	SETTERS
	 */
	//----------------------------------------------------------------------------------------------------
	
	def setQuestionId(int id) {
		questionId = id
	}

	def setPage(int page) {
		this.page = page
	}

	/**
	 * Sets the focused state of the zone and the item in the list
	 * @param b true if we want to highlight this item, false if not.
	 */
	def void setFocus(boolean b) {
		if (b) {
			color = FxSettings.ITEM_HIGHLIGHT_COLOR
			this.name.styleClass.add("focusedText")	
			zone.focus = b
		} else {
			color = FxSettings.ITEM_NORMAL_COLOR
			this.name.styleClass.remove("focusedText")	
			zone.focus = b
		}
	}
	
	/**
	 * Changes the color of the item in the list.
	 * @param color the color we want the background to be
	 */
	def setColor(Color color) {
		var bf = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
		this.background = new Background(bf)
	}

	def void setName(String text) {
		name.text = text
	}
	
	def setScale(float scale) {
		this.scale = scale
	}
}
