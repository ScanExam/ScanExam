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
import java.util.function.UnaryOperator
import javafx.scene.control.TextFormatter.Change
import javafx.geometry.Pos
import javafx.scene.layout.HBox

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
	/* Controlleur de l'édition */
	ControllerFxEdition controller

	/* Item de la question */
	QuestionItemEdition currentItem

	/* Grille contenant toutes les éléments */
	GridPane grid

	/* Champ du nom de la question */
	RenameField name
	/* Label contenant l'identifiant de la question */
	Label questionId
	/* Label contenant le barème de la question */
	RenameField scale
	/* Label contenant la apge de la question */
	Label page

	/* Fommateur pour n'autorisé que des chiffres dans un champ de texte */
	val TextFormatter<Number> formatter

	/* Bouton de suppression de la question */
	Button remove

	// ----------------------------------------------------------------------------------------------------
	/*
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	
	new(ControllerFxEdition controller) {
		this.controller = controller

		grid = new GridPane

		var l1 = new Label(translate("question.name"))
		var l2 = new Label(translate("question.id"))
		var l3 = new Label(translate("question.scale"))
		var l4 = new Label(translate("question.page"))

		grid.add(l1, 0, 0)
		grid.add(l2, 0, 1)
		grid.add(l3, 0, 2)
		grid.add(l4, 0, 3)

		name = new RenameField
		questionId = new Label
		scale = new RenameField
		var UnaryOperator<Change> integerFilter = [ change |
			{
				var String newText = change.getControlNewText()
				if (newText.matches("([0-9]*([.][0-9]*)?)?")) {
					return change
				}
				return null
			}
		]
		formatter = new TextFormatter<Number>(new NumberStringConverter(), 0, integerFilter)
		scale.setFieldFormatter(formatter)
		page = new Label

		remove = new Button(translate("question.remove"))

		grid.add(name, 1, 0)
		grid.add(questionId, 1, 1)
		grid.add(scale, 1, 2)
		grid.add(page, 1, 3)

		val HBox hbox = new HBox(remove)
		hbox.alignment = Pos.CENTER
		
		children.addAll(grid, hbox)
		hideAll
		setupEvents
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
		questionId.text = "" + item.questionId
		scale.text = "" + item.scale
		page.text = item.page + ""
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
		name.textProperty.addListener([obs, oldVal, newVal|commitRename])
		scale.textProperty.addListener([obs, oldVal, newVal|commitRescale])
	}
}
