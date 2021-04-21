package fr.istic.tools.scanexam.view.fx.graduation

import fr.istic.tools.scanexam.view.fx.component.RenameFieldSuggests
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox

import static fr.istic.tools.scanexam.config.LanguageManager.translate
import org.apache.logging.log4j.LogManager

/**
 * Affiche les détails d'un élève lors de la correction de sa copie
 * @author Julien Cochet
 */
class StudentDetails extends VBox {
	
	//----------------------------------------------------------------------------------------------------
	/*
	 * ATTRUBTS
	 */
	//----------------------------------------------------------------------------------------------------
	
	/* Controleur JavaFX de graduation */
	ControllerFxGraduation controller
	
	/* Grille contenant les informations */
	GridPane grid
	/* Champ de text econtenant le nom de l'étudiant */
	RenameFieldSuggests name
	/* Label affichant l'identifiant de l'étudiant */
	Label idLabel
	/* Label affichant l'identifiant de l'étudiant */
	Label gradeLabel
	
	/* Elément courant de notation */
	StudentItemGraduation currentItem
	
	/* Logger */
	static val logger = LogManager.logger
	
	//----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	//----------------------------------------------------------------------------------------------------
	
	/**
	 * Constructeur
	 * @param controller Controleur JavaFX de graduation
	 */
	new(ControllerFxGraduation controller) {
		this.controller = controller
		
		var nameRow = new Label(translate("label.student.name"))
		var idRow = new Label(translate("label.student.id"))
		var gradeRow = new Label(translate("label.student.grade"))

		grid = new GridPane
		grid.add(nameRow, 0, 0)
		grid.add(idRow, 0, 1)
		grid.add(gradeRow, 0, 2)

		name = new RenameFieldSuggests
		idLabel = new Label
		gradeLabel = new Label
		
		grid.add(name, 1, 0)
		grid.add(idLabel, 1, 1)
		grid.add(gradeLabel, 1, 2)

		this.children.add(grid)
		setupEvents
	}

	def setupEvents() {
		name.textProperty.addListener([obs, oldVal, newVal|commitRename])
		name.fieldTextProperty.addListener([obs, oldVal, newVal|findSuggestions(newVal)])
	}

	def commitRename() {
		logger.info("Renaming to" + name.text)
		currentItem.studentName = name.text
		controller.studentList.updateInModel(currentItem)
	}

	def findSuggestions(String start) {
		logger.info("Changing")
		var l = controller.getStudentsSuggestedNames(start)
		name.showSuggestion(l)
	}

	def display(StudentItemGraduation item) {
		this.visible = true
		currentItem = item
		setName
		setId
		updateGrade
	}

	def updateGrade() {
		gradeLabel.text = controller.getGlobalGrade + "/" + controller.getGlobalScale
	}

	def clearDisplay() {
		this.visible = false
	}

	//----------------------------------------------------------------------------------------------------
	/*
	 * SETTERS
	 */
	//----------------------------------------------------------------------------------------------------
	
	private def setName() {
		name.text = currentItem.studentName
	}

	private def setId() {
		idLabel.text = currentItem.studentId + ""
	}

}
