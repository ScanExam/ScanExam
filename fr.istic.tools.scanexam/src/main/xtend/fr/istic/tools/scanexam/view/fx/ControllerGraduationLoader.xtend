package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.presenter.PresenterGraduationLoader
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField
import fr.istic.tools.scanexam.view.fx.component.validator.ValidFilePathValidator
import java.io.File
import java.util.Objects
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.RadioButton
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.Stage
import javax.annotation.Nullable
import org.apache.logging.log4j.LogManager

/**
 * Contrôleur pour l'UI de chargement d'une correction
 * @author Théo Giraudet
 */
class ControllerGraduationLoader {

	/* Composant racine */
	@FXML
	var VBox mainPane

	/* RadioButton de l'option "Utiliser le modèle chargé" */
	@FXML
	var RadioButton rbUseLoaded

	/* RadioButton de l'option "Charger un nouveau modèle" */
	@FXML
	var RadioButton rbLoadModel

	/* HBox avec les nœuds à activer si deuxième RadioButton sélectionné */
	@FXML
	var HBox hBoxLoad

	/* TextField de la saisie du path pour le nouveau modèle */
	@FXML
	var FormattedTextField txtFldFile

	/* Button de chargement du modèle */
	@FXML
	var Button btnBrowse

	/* TextField de la saisie du path pour les copies */
	@FXML
	var FormattedTextField txtFldFileGraduation

	/* Button de chargement des copies */
	@FXML
	var Button btnBrowseGraduation

	/* TextField pour spécifier le nom de la correction */
	@FXML
	var FormattedTextField txtFldGraduationName

	/* Button de validation du formulaire */
	@FXML
	var Button btnOk

	/* Ce composant ne sert qu'à attraper l'hover event, étant donné que les éléments disabled ne le trigger pas */
	@FXML
	var Pane hoverPane

	var PresenterGraduationLoader presStudentListLoader;

	static val logger = LogManager.logger

	/**
	 * Initialise le composant avec le presenter composé en paramètre
	 * @param loader le presenter
	 */
	def initialize(PresenterGraduationLoader loader) {
		presStudentListLoader = loader
		hBoxLoad.disableProperty.bind(rbLoadModel.selectedProperty.not)

		// Condition pour que le bouton de validation soit désactivé :
		// - Au moins l'un des composants activés est dans un état de "Mauvais format"
		// - Au moins l'un des composants activés est vide
		btnOk.disableProperty.bind(
			txtFldFile.wrongFormattedProperty
			.or(txtFldFileGraduation.wrongFormattedProperty)
			.or(txtFldGraduationName.textProperty.isEmpty)
			.or(txtFldFileGraduation.textProperty.isEmpty)
			.or(rbLoadModel.selectedProperty
				.and(txtFldFile.textProperty.isEmpty)
			)
		)

		// Formattage des TextFields		
		txtFldFile.addFormatValidator(new ValidFilePathValidator(".xmi"))
		txtFldFileGraduation.addFormatValidator(new ValidFilePathValidator(".pdf"))
		hoverPane.onMouseEntered = [e|btnOk.disabled ? shakeEmptyComponents()]

		// Action sur les boutons de chargement de fichiers
		btnBrowse.onAction = [e|loadFile("*.xmi", "file.format.xmi", txtFldFile)]
		btnBrowseGraduation.onAction = [e|loadFile("*.pdf", "file.format.pdf", txtFldFileGraduation)]

		// Si aucun examen n'est chargé, désactiver le RadioButton "Utiliser le modèle chargé"
		if (!presStudentListLoader.hasTemplateLoaded) {
			rbUseLoaded.disable = true
			rbLoadModel.selected = true
		}
	}

	/**
	 * Anime toutes les composants vides
	 */
	private def shakeEmptyComponents() {
		txtFldGraduationName.shakeIfEmpty()
		txtFldFileGraduation.shakeIfEmpty()
		txtFldFile.shakeIfEmpty()
	}

	/**
	 * Affiche un sélectionneur de fichier et met le path dans le composant spécifié
	 * @param format l'extension du fichier à ouvrir (non null)
	 * @param formatDes la description du format (non null)
	 * @param destination le composant dans lequel afficher le path choisi (non null)
	 */
	private def loadFile(String format, String formatDes, FormattedTextField destination) {
		Objects.requireNonNull(format)
		Objects.requireNonNull(formatDes)
		Objects.requireNonNull(destination)
		var fileChooser = new FileChooser
		fileChooser.extensionFilters.add(new ExtensionFilter(LanguageManager.translate(formatDes), format))
		// Si le contenu de txtFldFile est un Path valide, le fileChooser part alors de ce path
		val specifiedFile = new File(destination.text)
		if (specifiedFile.exists && specifiedFile.isDirectory)
			fileChooser.initialDirectory = specifiedFile
		else if (specifiedFile.exists && specifiedFile.isFile)
			fileChooser.initialDirectory = specifiedFile.parentFile
		else
			fileChooser.initialDirectory = new File(
				System.getProperty("user.home") + System.getProperty("file.separator") + "Documents")
		var file = fileChooser.showOpenDialog(mainPane.scene.window)
		if (file !== null) {
			destination.text = file.path
		} else {
			logger.warn("File not chosen")
		}
	}

	@FXML
	def quit() {
		val Stage stage = mainPane.scene.window as Stage
		stage.close();
	}

	@FXML
	def saveAndQuit() {
		if (presStudentListLoader.loadTemplate(txtFldFile.text)) {
			if(presStudentListLoader.loadStudentSheets(txtFldFileGraduation.text, 0)) {
				quit
			} else {}
				//sendDialog(AlertType.ERROR, "studentSheetLoader.graduationConfirmationDialog.title", "studentSheetLoader.graduationConfirmationDialog.fail", null)
		} else
			sendDialog(AlertType.ERROR, "studentSheetLoader.templateConfirmationDialog.title", "studentSheetLoader.templateConfirmationDialog.fail", null)
	}

	/**
	 * Affiche un Dialog avec les informations suivantes :
	 * @param type le type de l'alerte (non null)
	 * @param title le titre le l'alerte (non null)
	 * @param headerText le header de l'alerte (non null)
	 * @param content le contenu de l'alerte
	 */
	private def void sendDialog(AlertType type, String title, String headerText, @Nullable String content) {
		Objects.requireNonNull(type)
		Objects.requireNonNull(title)
		Objects.requireNonNull(headerText)
		
		val alert = new Alert(type)
		val stage = alert.getDialogPane().getScene().getWindow() as Stage
		stage.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")))
		alert.setTitle = LanguageManager.translate(title)
		alert.setHeaderText = LanguageManager.translate(headerText)
		if(content !== null)
			alert.contentText = LanguageManager.translate(content)
		alert.showAndWait
	}
}
