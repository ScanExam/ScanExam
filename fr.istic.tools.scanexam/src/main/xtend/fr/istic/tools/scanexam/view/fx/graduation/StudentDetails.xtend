package fr.istic.tools.scanexam.view.fx.graduation

import fr.istic.tools.scanexam.view.fx.component.RenameFieldSuggests
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox

import static fr.istic.tools.scanexam.config.LanguageManager.translate
import org.apache.logging.log4j.LogManager
import javafx.scene.shape.Circle
import javafx.scene.paint.Color

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
	/* Cercle affichant la qualité de la copie */
	Circle qualityCircle
	
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
		var qualityRow = new Label(translate("label.student.quality"))
		
		grid = new GridPane
		grid.add(nameRow, 0, 0)
		grid.add(idRow, 0, 2)
		grid.add(gradeRow, 0, 3)
		grid.add(qualityRow, 0, 4)

		name = new RenameFieldSuggests
		idLabel = new Label
		gradeLabel = new Label
		qualityCircle = new Circle(8, Color.GRAY)
		
		grid.add(name, 0, 1)
		grid.add(idLabel, 1, 2)
		grid.add(gradeLabel, 1, 3)
		grid.add(qualityCircle, 1, 4)

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
	
	def updateQuality() {
		val quality = controller.getGlobalGrade / controller.getCurrentMaxGrade
		if (quality >= 0.75) {
			// Couleur pour la meilleure qualité
			qualityCircle.fill = Color.GREEN
		} else if (quality >= 0.5) {
			// Couleur pour la deuxième meilleure qualité
			qualityCircle.fill = Color.YELLOW
		} else if (quality >= 0.25) {
			// Couleur pour la troisième meilleure qualité
			qualityCircle.fill = Color.ORANGE
		} else if (quality >= 0) {
			// Couleur pour la pire qualité
			qualityCircle.fill = Color.RED
		} else {
			// Couleur par défaut
			qualityCircle.fill = Color.GRAY
		}
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
