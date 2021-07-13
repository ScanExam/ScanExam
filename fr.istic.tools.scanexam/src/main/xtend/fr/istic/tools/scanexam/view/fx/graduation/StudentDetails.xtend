package fr.istic.tools.scanexam.view.fx.graduation

import fr.istic.tools.scanexam.view.fx.component.RenameFieldSuggests
import javafx.scene.control.Label
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

	// ----------------------------------------------------------------------------------------------------
	/*
	 * ATTRUBTS
	 */
	// ----------------------------------------------------------------------------------------------------
	/** Controleur JavaFX de graduation */
	ControllerFxGraduation controller

	/** Champ de text contenant l'identifiant de l'étudiant */
	RenameFieldSuggests userId
	/** Champ de text contenant le nom de famille de l'étudiant */
	//RenameFieldSuggests lastName
	/** Champ de text contenant le prénom de l'étudiant */
	//RenameFieldSuggests firstName
	/** Label affichant l'identifiant de l'étudiant pour l'application */
	Label idLabel
	/** Label affichant la note de l'étudiant */
	Label gradeLabel
	/** Cercle affichant la qualité de la copie */
	Circle qualityCircle

	/** Elément courant de notation */
	StudentItemGraduation currentItem

	/** Logger */
	static val logger = LogManager.logger

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Constructeur
	 * @param controller Controleur JavaFX de graduation
	 */
	new(ControllerFxGraduation controller) {
		this.controller = controller
		this.alignment = Pos.CENTER
		var userIdRow = new Label(translate("label.student.name"))
		/*
		var lastNameRow = new Label(translate("Last name"))
		var firstNameRow = new Label(translate("First name"))
		*/
		var idRow = new Label(translate("label.student.id"))
		var gradeRow = new Label(translate("label.student.grade"))
		var qualityRow = new Label(translate("label.student.quality"))
		userIdRow.styleClass.add("RowTitle")
		/*
		lastNameRow.styleClass.add("RowTitle")
		firstNameRow.styleClass.add("RowTitle")
		*/
		idRow.styleClass.add("RowTitle")
		gradeRow.styleClass.add("RowTitle")
		qualityRow.styleClass.add("RowTitle")

		userId = new RenameFieldSuggests
		userId.alignment = Pos.CENTER
		/*
		lastName = new RenameFieldSuggests
		lastName.alignment = Pos.CENTER
		firstName = new RenameFieldSuggests
		firstName.alignment = Pos.CENTER
		*/
		idLabel = new Label
		gradeLabel = new Label
		qualityCircle = new Circle(8, Color.GRAY)

		this.children.add(userIdRow)
		this.children.add(userId)
		/*
		this.children.add(lastNameRow)
		this.children.add(lastName)
		this.children.add(firstNameRow)
		this.children.add(firstName)
		*/
		this.children.add(idRow)
		this.children.add(idLabel)
		this.children.add(gradeRow)
		this.children.add(gradeLabel)
		this.children.add(qualityRow)
		this.children.add(qualityCircle)

		setupEvents
	}

	def setupEvents() {
		userId.textProperty.addListener([obs, oldVal, newVal|commitRename])
		userId.fieldTextProperty.addListener([obs, oldVal, newVal|findUserIdSuggestions(newVal)])
		/*
		lastName.textProperty.addListener([obs, oldVal, newVal|commitRename])
		lastName.fieldTextProperty.addListener([obs, oldVal, newVal|findLastNameSuggestions(newVal)])
		firstName.textProperty.addListener([obs, oldVal, newVal|commitRename])
		firstName.fieldTextProperty.addListener([obs, oldVal, newVal|findFirstNameSuggestions(newVal)])
		*/
	}

	def commitRename() {
		logger.info("Renaming to " + userId.text)
		currentItem.studentName = userId.text
		controller.studentList.updateInModel(currentItem)
	}

	def findUserIdSuggestions(String start) {
		logger.info("Changing")
		var l = controller.getStudentsSuggestedIds(start)
		userId.showSuggestion(l)
	}

	/*
	def findLastNameSuggestions(String start) {
		logger.info("Changing")
		var l = controller.getStudentsSuggestedLastNames(start)
		lastName.showSuggestion(l)
	}

	def findFirstNameSuggestions(String start) {
		logger.info("Changing")
		var l = controller.getStudentsSuggestedFirstNames(start)
		firstName.showSuggestion(l)
	}
	*/

	def display(StudentItemGraduation item) {
		this.visible = true
		currentItem = item
		setUserId
		//setLastName
		//setFirstName
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

	// ----------------------------------------------------------------------------------------------------
	/*
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	private def setUserId() {
		userId.text = currentItem.studentName
	}

	/*
	private def setLastName() {
		lastName.text = currentItem.studentName
	}

	private def setFirstName() {
		firstName.text = currentItem.studentName
	}
	*/
	private def setId() {
		idLabel.text = currentItem.studentId + ""
	}
}
