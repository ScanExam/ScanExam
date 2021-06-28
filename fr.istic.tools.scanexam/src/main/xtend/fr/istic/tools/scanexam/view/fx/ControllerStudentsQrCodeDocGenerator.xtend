package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.qrCode.writer.StudentsQrCodeDocGenerator
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField
import fr.istic.tools.scanexam.view.fx.component.validator.ValidFilePathValidator
import java.io.File
import java.util.Arrays
import java.util.Optional
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager
import javafx.scene.control.CheckBox
import javafx.scene.control.MenuButton
import javafx.scene.control.MenuItem
import javafx.event.EventHandler
import javafx.event.ActionEvent

/**
 * Classe gérant l'interface de génération de document qr code d'élèves
 * @author Julien Cochet
 */
class ControllerStudentsQrCodeDocGenerator {

	private enum LoadState {
		SUCCESS,
		NOT_VALID
	}

	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	/** Logger du programme */
	static val logger = LogManager.logger
	/** Formats supportés pour la liste des étudiants */
	val static supportedFormat = Arrays.asList("*.xlsx", "*.xls")

	/** Pane principale de la vue */
	@FXML
	public Pane mainPane
	/** Champ du fichier à charger */
	@FXML
	public FormattedTextField txtFldFile
	/** Indique si les élèves doivent être rangé par ordre alphabétique */
	@FXML
	public CheckBox alphabeticalOrder
	/** Menu des formats d'étiquettes prédéfinis */
	@FXML
	public MenuButton formatMenu
	/** Largueur des étiquettes */
	@FXML
	public FormattedTextField labelWidth
	/** Hauteur des étiquettes */
	@FXML
	public FormattedTextField labelHeight
	/** Bouton de validation */
	@FXML
	public Button btnOk

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	@FXML
	def void saveAndQuit() {
		val Optional<File> saveFile = selectSavePath
		if (!saveFile.empty && labelWidth.text != "" && labelHeight.text != "") {
			val state = exportStudentsQrCodes(new File(txtFldFile.text),
				Float.parseFloat(labelWidth.text.replace(",", ".")),
				Float.parseFloat(labelHeight.text.replace(",", ".")), alphabeticalOrder.isSelected, saveFile.get)
			if (!btnOk.disable)
				dispDialog(state)
			quit
		}
	}

	@FXML
	def void quit() {
		val Stage stage = mainPane.scene.window as Stage
		stage.close
	}

	/**
	 * Initialise le contrôleur
	 */
	def void initialize() {
		btnOk.disableProperty.bind(txtFldFile.wrongFormattedProperty)

		txtFldFile.addFormatValidator(
			text |
				supportedFormat.map[f|f.substring(1)].findFirst[f|text.endsWith(f)] !== null
					? Optional.empty
					: Optional.of("file.info.fileNotValid")
		)
		txtFldFile.addFormatValidator(new ValidFilePathValidator)

		val EventHandler<ActionEvent> formatMenuEvent = new EventHandler<ActionEvent> {
			override handle(ActionEvent e) {
				val String selection = (e.source as MenuItem).text
				selectFormat(selection)
				formatMenu.text = selection
			}
		}
		formatMenu.items.add(new MenuItem("99.1 x 57.0mm"))
		formatMenu.items.add(new MenuItem("63.5 x 38.1mm"))
		formatMenu.items.add(new MenuItem("45.7 x 21.2mm"))
		formatMenu.items.add(new MenuItem(LanguageManager.translate("studentsQrCodeDoc.custom")))
		for (item : formatMenu.items) {
			item.onAction = formatMenuEvent
		}
	}

	/**
	 * Ouvre une fenêtre pour sélectionner le fichier contenant la liste d'élèves
	 */
	def void loadStudentsList() {
		var fileChooser = new FileChooser
		fileChooser.extensionFilters.add(new ExtensionFilter("XLS files", supportedFormat))
		// Si le contenu de txtFldFile est un Path valide, le fileChooser part alors de ce path
		val specifiedFile = new File(txtFldFile.text)
		if (specifiedFile.exists && specifiedFile.isDirectory)
			fileChooser.initialDirectory = specifiedFile
		else if (specifiedFile.exists && specifiedFile.isFile)
			fileChooser.initialDirectory = specifiedFile.parentFile
		else
			fileChooser.initialDirectory = new File(
				System.getProperty("user.home") + System.getProperty("file.separator") + "Documents")
		var file = fileChooser.showOpenDialog(mainPane.scene.window)
		if (file !== null) {
			txtFldFile.text = file.path
		} else {
			logger.warn("File not chosen")
		}
	}

	/**
	 * Ouvre une fenêtre pour sélectionner le fichier où sauvegarder le pdf généré
	 * @param Fichier où sauvegarder le pdf généré
	 */
	private def Optional<File> selectSavePath() {
		val fileChooser = new FileChooser
		fileChooser.extensionFilters.add(new ExtensionFilter(LanguageManager.translate("file.format.pdf"), "*.pdf"))
		fileChooser.initialDirectory = new File(
			System.getProperty("user.home") + System.getProperty("file.separator") + "Documents")
		val file = fileChooser.showSaveDialog(mainPane.scene.window)
		if (file !== null) {
			return Optional.of(file)
		} else {
			logger.warn("Folder not chosen")
			return Optional.empty
		}
	}

	/**
	 * Affiche un boîte de dialog décrivant la réussite ou non du chargement des données
	 * @param state un LoadState décrivant le réussite ou non du chargement des données
	 */
	private def void dispDialog(LoadState state) {
		val alert = new Alert(AlertType.NONE)
		alert.setTitle(LanguageManager.translate("studentsQrCodeDoc.alert.title"))
		val stage = alert.getDialogPane().getScene().getWindow() as Stage
		stage.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")))
		if (state == LoadState.SUCCESS) {
			alert.alertType = AlertType.CONFIRMATION
			alert.setHeaderText(String.format(LanguageManager.translate("studentsQrCodeDoc.alert.success")))
		} else {
			alert.alertType = AlertType.ERROR
			alert.setHeaderText(LanguageManager.translate("studentsQrCodeDoc.alert.error"))
		}
		alert.showAndWait
	}

	/**
	 * Envoie la liste des étudiants et le fichier où enregistrer le document des qr codes au générateur
	 * @param studentsList Chemin du fichier contenant la liste des étudiants
	 * @param labelWidth Largeur des étiquettes en mm
	 * @param labelHeight Hauteur des étiquettes en mm
	 * @param alphabeticalOrder Indique si les étudiants doivent être mis par ordre alphabetique
	 * @param exportFile Chemin du fichier où enregistrer le document des qr codes
	 * @return un LoadState représentant l'état terminal de l'export des qr codes
	 */
	private def LoadState exportStudentsQrCodes(File studentsList, float labelWidth, float labelHeight,
		boolean alphabeticalOrder, File exportFile) {
		val StudentsQrCodeDocGenerator generator = new StudentsQrCodeDocGenerator
		generator.generateDocument(studentsList, labelWidth, labelHeight, alphabeticalOrder, exportFile)
		return LoadState.SUCCESS
	}

	private def void selectFormat(String itemText) {
		if (itemText != "Personnalisé") {
			val dimensions = itemText.split(" x ")
			labelWidth.text = dimensions.get(0)
			labelHeight.text = dimensions.get(1).substring(0, dimensions.get(1).length - 2)
		}
	}

}
