package fr.istic.tools.scanexam.view.fx.editor

import fr.istic.tools.scanexam.view.fx.component.RenameField
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextFormatter
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.util.converter.NumberStringConverter

import static fr.istic.tools.scanexam.config.LanguageManager.translate

/**
 * [JavaFX] Détail d'une question
 * @author Stefan Locke, Julien Cochet
 */
class QuestionOptionsEdition extends VBox {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	GridPane grid
	
	RenameField name
	
	/* Modifiable label for the scale of the question */
	RenameField scale
	
	val TextFormatter<Number> formatter
	/* Formatter to allow only number in scale field */
	Label questionId
	Label page
	Label questionCoords
	Label questionDescription // TODO replace with htlm display
	Button remove
	ControllerFxEdition controller

	QuestionItemEdition currentItem

	// ----------------------------------------------------------------------------------------------------
	/*
	 * CONSTRUCTEURS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	new(ControllerFxEdition controller) {
		this.controller = controller

		grid = new GridPane

		var l1 = new Label(translate("question.name"))
		var l2 = new Label(translate("question.id"))
		var l3 = new Label(translate("question.page"))
		var l4 = new Label(translate("question.scale"))
		var l6 = new Label(translate("question.position"))
		var l7 = new Label(translate("question.description"))
		grid.add(l1, 0, 0)
		grid.add(l2, 0, 1)
		grid.add(l3, 0, 2)
		grid.add(l4, 0, 3)
		grid.add(l6, 0, 4)
		grid.add(l7, 0, 5)
		

		
		
		
		name = new RenameField
		
		scale = new RenameField
		formatter = new TextFormatter(new NumberStringConverter)
		scale.setFieldFormatter(formatter)

		questionId = new Label()
		page = new Label();
		questionCoords = new Label();
		questionDescription = new Label();
		remove = new Button(translate("question.remove"));
		
		grid.add(name, 1, 0)
		grid.add(questionId, 1, 1)
		grid.add(page, 1, 2)
		grid.add(scale, 1, 3)
		grid.add(questionCoords, 1, 4)
		grid.add(questionDescription, 1, 5)

		children.addAll(grid, remove)
		hideAll
		setupEvents();
	}

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Selects a question to display
	 * @param item Question sélectionnée
	 */
	def select(QuestionItemEdition item) {
		showAll()
		currentItem = item
		name.text = item.name
		page.text = item.page + ""
		questionId.text = "" + item.questionId
		scale.text = "" + item.scale
		questionCoords.text = "X:" + item.zone.x + "\nY:" + item.zone.y + "\nH:" + item.zone.height + "\nW:" +
			item.zone.width
		questionDescription.text = "No description";
	}

	def showAll() {
		this.visible = true
	}

	def hideAll() {
		this.visible = false
	}

	/**
	 * Called to commit a name change
	 */
	def commitRename() {
		currentItem.name = name.text
		controller.questionList.updateInModel(currentItem)
	}

	/**
	 * Called to commit a scale change
	 */
	def commitRescale() {
		currentItem.scale = scale.formatterValue
		controller.questionList.updateInModel(currentItem)
	}

	def setupEvents() {
		remove.onAction = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				controller.questionList.remove(currentItem)
				controller.mainPane.removeZone(currentItem.zone)
				controller.selectQuestion(null)
			}

		}
		
		name.textProperty.addListener([obs,oldVal,newVal | commitRename ])

		scale.textProperty.addListener([obs,oldVal,newVal | commitRescale])		

		

		//grid.children.findFirst[n | n !== null && GridPane.getRowIndex(n) == 3 && GridPane.getColumnIndex(n) == 2].onMouseClicked = [e | toggleRescale(true)]

		

	}
}
