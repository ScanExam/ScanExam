package fr.istic.tools.scanexam.view.fx.graduation

import fr.istic.tools.scanexam.view.fx.component.RenameFieldSuggests
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox

import static fr.istic.tools.scanexam.config.LanguageManager.translate
import org.apache.logging.log4j.LogManager
import javafx.scene.shape.Circle
import javafx.scene.paint.Color
import javafx.geometry.Pos

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
		this.alignment = Pos.CENTER
		var nameRow = new Label(translate("label.student.name"))
		var idRow = new Label(translate("label.student.id"))
		var gradeRow = new Label(translate("label.student.grade"))
		var qualityRow = new Label(translate("label.student.quality"))
		nameRow.styleClass.add("RowTitle")
		idRow.styleClass.add("RowTitle")
		gradeRow.styleClass.add("RowTitle")
		qualityRow.styleClass.add("RowTitle")
		
		

		name = new RenameFieldSuggests
		name.alignment = Pos.CENTER
		idLabel = new Label
		gradeLabel = new Label
		qualityCircle = new Circle(8, Color.GRAY)
		
		this.children.add(nameRow)
		this.children.add(name)
		this.children.add(idRow)
		this.children.add(idLabel)
		this.children.add(gradeRow)
		this.children.add(gradeLabel)
		this.children.add(qualityRow)
		this.children.add(qualityCircle)
		
		
		

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
