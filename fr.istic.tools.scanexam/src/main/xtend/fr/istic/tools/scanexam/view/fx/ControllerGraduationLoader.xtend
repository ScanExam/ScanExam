package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField
import fr.istic.tools.scanexam.view.fx.component.validator.ValidFilePathValidator
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation
import fr.istic.tools.scanexam.view.fx.utils.DialogMessageSender
import java.io.File
import java.util.Objects
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.RadioButton
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.Stage
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

	/* Button de validation du formulaire */
	@FXML
	var Button btnOk

	/* Ce composant ne sert qu'à attraper l'hover event, étant donné que les éléments disabled ne le trigger pas */
	@FXML
	var Pane hoverPane


	
	var ControllerFxGraduation controllerGraduation;

	static val logger = LogManager.logger

	var ControllerFxEdition controllerEdition
	

	/**
	 * Initialise le composant avec le presenter composé en paramètre
	 * @param loader le presenter
	 */
	def initialize(ServiceGraduation serviceGraduation, ControllerFxEdition controllerEdition, ControllerFxGraduation controllerGraduation) {

		this.controllerGraduation = controllerGraduation
		this.controllerEdition = controllerEdition
		hBoxLoad.disableProperty.bind(rbLoadModel.selectedProperty.not)

		// Condition pour que le bouton de validation soit désactivé :
		// - Au moins l'un des composants activés est dans un état de "Mauvais format"
		// - Au moins l'un des composants activés est vide
		btnOk.disableProperty.bind(
			txtFldFile.wrongFormattedProperty
			.or(txtFldFileGraduation.wrongFormattedProperty)
			.or(txtFldFileGraduation.textProperty.isEmpty)
			.or(rbLoadModel.selectedProperty
				.and(txtFldFile.textProperty.isEmpty)
			)
		)

		// Formattage des TextFields		
		txtFldFile.addFormatValidator(new ValidFilePathValidator(".xmi"))
		txtFldFileGraduation.addFormatValidator(new ValidFilePathValidator(".xmi"))
		hoverPane.onMouseEntered = [e|btnOk.disabled ? shakeEmptyComponents()]

		// Action sur les boutons de chargement de fichiers
		btnBrowse.onAction = [e|loadFile("*.xmi", "file.format.xmi", txtFldFile)]
		btnBrowseGraduation.onAction = [e|loadFile("*.xmi", "file.format.xmi", txtFldFileGraduation)]

		// Si aucun examen n'est chargé, désactiver le RadioButton "Utiliser le modèle chargé"
		if (!serviceGraduation.hasExamLoaded) {
			rbUseLoaded.disable = true
			rbLoadModel.selected = true
		}
	}
	
	/**
	 * Anime toutes les composants vides
	 */
	private def shakeEmptyComponents() {
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

	/**
	 * Valide le formulaire en important les XMI. Affiche un message d'erreur si l'un des XMI n'a pa pu être importé correctement  
	 */
	@FXML
	def valid() {
		if(rbUseLoaded.selected || controllerEdition.loadTemplate(new File(txtFldFile.text))) {
			if(!controllerGraduation.load(new File(txtFldFileGraduation.text)))
				DialogMessageSender.sendTranslateDialog(AlertType.ERROR, "graduationLoader.graduationConfirmationDialog.title", "graduationLoader.graduationConfirmationDialog.fail", null)
			else
				quit
		}
	}
	
}
