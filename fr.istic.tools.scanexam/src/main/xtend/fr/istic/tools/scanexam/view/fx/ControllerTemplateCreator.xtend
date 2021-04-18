package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField
import fr.istic.tools.scanexam.view.fx.component.validator.ValidFilePathValidator
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition
import java.io.File
import java.util.Objects
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.Stage
import javax.annotation.Nullable
import org.apache.logging.log4j.LogManager

/**
 * Contrôleur pour l'UI de création d'un modèle d'examen
 * @author Julien Cochet
 */
class ControllerTemplateCreator {

	/* Composant racine */
	@FXML
	var VBox mainPane

	/* TextField pour spécifier le nom du modèle */
	@FXML
	var FormattedTextField txtFldTemplateName

	/* TextField de la saisie du path pour le nouveau modèle */
	@FXML
	var FormattedTextField txtFldTemplateFile

	/* Button de chargement du modèle */
	@FXML
	var Button btnBrowser
	
	/* Button de validation du formulaire */
	@FXML
	var Button btnOk

	/* Ce composant ne sert qu'à attraper l'hover event, étant donné que les éléments disabled ne le trigger pas */
	@FXML
	var Pane hoverPane
	

	
	/* Controller JaveFX de l'edition d'examen */
	var ControllerFxEdition ctrlEditor;
	
	/* Nom du modèle */
	var String templateName
	
	/* Fichier du modèle */
	var File templateFile
	
	
	/*Logger */
	static val logger = LogManager.logger

	/**
	 * Initialise le composant avec le presenter composé en paramètre
	 * @param presenter Presenter de la création du modèle
	 */
	def initialize(ControllerFxEdition ctrlFxEditor) {
	
		ctrlEditor = ctrlFxEditor
		
		// Condition pour que le bouton de validation soit désactivé :
		// - Au moins l'un des composants activés est dans un état de "Mauvais format"
		// - Au moins l'un des composants activés est vide
		btnOk.disableProperty.bind(
			txtFldTemplateFile.wrongFormattedProperty
			.or(txtFldTemplateName.textProperty.isEmpty)
		)

		// Formattage des TextFields		
		txtFldTemplateFile.addFormatValidator(new ValidFilePathValidator(".pdf"))
		hoverPane.onMouseEntered = [e|btnOk.disabled ? shakeEmptyComponents]

		// Action sur les boutons de chargement de fichiers
		btnBrowser.onAction = [e|loadFile("*.pdf", "file.format.pdf", txtFldTemplateFile)]
	}
	def void createTemplate() {
		
		ctrlEditor.pdfManager.create(templateName, templateFile)
	}
	/**
	 * Anime toutes les composants vides
	 */
	private def shakeEmptyComponents() {
		txtFldTemplateName.shakeIfEmpty
		txtFldTemplateFile.shakeIfEmpty
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
			templateFile = file
		} else {
			logger.warn("File not chosen")
		}
	}

	@FXML
	def quit() {
		val Stage stage = mainPane.scene.window as Stage
		stage.close
	}

	@FXML
	def saveAndQuit() {
		if (checkName(txtFldTemplateName.text)) {
			if(checkFilepath(txtFldTemplateFile.text)) {
				templateName = txtFldTemplateName.text
				createTemplate
				ctrlEditor.render 
				quit
			} else {
				sendDialog(AlertType.ERROR, "templateLoader.dialog.title", "templateLoader.dialog.nameFail", null)
			}
		} else {
			sendDialog(AlertType.ERROR, "templateLoader.dialog.title", "templateLoader.dialog.fileFail", null)
		}
	}

	def checkFilepath(String string) {
		true 	// TODO
	}
	
	def checkName(String string) {
		true 	// TODO
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
