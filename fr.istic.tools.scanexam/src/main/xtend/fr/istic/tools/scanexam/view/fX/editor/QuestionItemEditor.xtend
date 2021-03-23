package fr.istic.tools.scanexam.view.fX.editor

import fr.istic.tools.scanexam.view.fX.FXSettings
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.util.converter.NumberStringConverter

import static fr.istic.tools.scanexam.config.LanguageManager.translate

/**
 * [JavaFX] Item question dans la liste de gauche des questions
 * @author Stefan Locke, Julien Cochet
 */
class QuestionItemEditor extends VBox {

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
	/* Text field for the scale of the question */
	TextField scaleField
	/* Formatter to allow only number in scale field */
	val TextFormatter<Number> formatter
	
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
	int scale
	/* the type of this zone */
	BoxType type
	
	//--- Other Vars---//
	/*the container of all the question items */
	QuestionListEditor list

	//----------------------------------------------------------------------------------------------------
	/*
	 *	CONSTRUCTEURS
	 */
	//----------------------------------------------------------------------------------------------------
	
	new(QuestionListEditor list, Box zone) {
		super()
		top = new HBox
		
		middle = new HBox
		middle.children.add(new Label(translate("question.scale")))
		scaleField = new TextField
        formatter = new TextFormatter(new NumberStringConverter())
		scaleField.setTextFormatter(formatter)
		middle.children.add(scaleField)
		
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

		this.styleClass.add("QuestionItem")
		this.margin = new Insets(2);
		setupContextMenu
		setupEvents(this)
	}

	new(QuestionListEditor list, Box zone, BoxType type, int page) {
		this(list, zone);
		this.type = type
		this.page = page
	}

	new(QuestionListEditor list, Box zone, String name, int page, int id) {
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
	def setupContextMenu() {
	}

	def setupEvents(QuestionItemEditor item) {

		remove.onAction = new EventHandler<ActionEvent>() {
			
			override handle(ActionEvent event) {
				list.removeQuestion(item)
				list.controller.selectQuestion(null)
			}
			
		}
		
		formatter.valueProperty.addListener([observable, oldValue, newValue |
			scale = newValue.intValue
		]) 

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
	 * Sets the color of the zone and the item in the list
	 * @param b True for highlight color
	 */
	def void setFocus(boolean b) {
		if (b) {
			color = FXSettings.ITEM_HIGHLIGHT_COLOR
			zone.focus = b
		} else {
			color = FXSettings.ITEM_NORMAL_COLOR
			zone.focus = b
		}
	}

	def setColor(Color color) {
		var bf = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
		this.background = new Background(bf)
	}

	def void setName(String text) {
		name.text = text
	}
}
