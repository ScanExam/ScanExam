package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.presenter.PresenterStudentListLoader
import fr.istic.tools.scanexam.view.fx.utils.BadFormatDisplayer
import java.io.File
import java.net.URL
import java.util.Arrays
import java.util.ResourceBundle
import java.util.regex.Pattern
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.control.Tooltip
import javafx.scene.layout.Pane
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager

/**
 * Classe pour charger la liste des étudiant en JavaFX
 * @author Julien Cochet
 */
class AdapterFxStudentListLoader implements Initializable {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	/* Logger du programme */
	static val logger = LogManager.logger
	static val Pattern cellPattern = Pattern.compile("[A-Z]+[1-9][0-9]*")

	/* Controlleur de la configuration */
	var PresenterStudentListLoader presStudentList

	/* Pane principale de la vue */
	@FXML
	public Pane mainPane

	/* Champ du fichier à charger */
	@FXML
	public TextField txtFldFile

	/* Champ de la première casse */
	@FXML
	public TextField txtFldFirstCell
	
	/* Bouton de validation */
	@FXML
	public Button btnOk
	
	/*
	 * Caractérise les erreurs dans le formulaire. Si au moins l'une des valeurs est à true, alors le bouton de validation est bloqué  
	 */
	val boolean[] errors = newBooleanArrayOfSize(2)

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------

	override initialize(URL location, ResourceBundle resources) {
		txtFldFile.text = ""
		txtFldFirstCell.text = "A1"
		
		txtFldFirstCell.textFormatter = new TextFormatter<String> [ change |
			change.text = change.text.toUpperCase
			change
		]
		
		txtFldFirstCell.focusedProperty.addListener[obs, oldVal, newVal|!newVal ? verifyFirstCell]
		txtFldFile.focusedProperty.addListener[obs, oldVal, newVal|!newVal ? verifyFilePath]
		txtFldFirstCell.focusedProperty.addListener [ obs, oldVal, newVal |
			!newVal ? txtFldFirstCell.text.equals("") ? txtFldFirstCell.text = "A1"
		]
	}

	@FXML
	def void saveAndQuit() {
		presStudentList.loadFile(new File(txtFldFile.text), txtFldFirstCell.text)
		verifyFilePath
		verifyFirstCell
		quit
	}

	@FXML
	def void quit() {
		val Stage stage = mainPane.scene.window as Stage
		stage.close();
	}

	/**
	 * Ouvre une fenêtre pour sélectionner le fichier souhaité
	 */
	def loadFile() {
		var fileChooser = new FileChooser
		fileChooser.extensionFilters.add(
			new ExtensionFilter("Calc files",
				Arrays.asList("*.ods", "*.ots", "*.sxc", "*.stc", "*.fods", "*.xml", "*.xlsx", "*.xltx", "*.xlsm",
					"*.xlsb", "*.xls", "*.xlc", "*.xlm", "*.xlw", "*.xlk", "*.et", "*.xlt", "*.ett", "*.dif", "*.wk1",
					"*.xls", "*.123", "*.wb2", "*.csv")))
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents")
		var file = fileChooser.showOpenDialog(mainPane.scene.window)
		if (file !== null) {
			txtFldFile.text = file.path
		} else {
			logger.warn("File not chosen")
		}
			verifyFilePath
	}
	
	/**
	 * Vérifie la syntaxe de la cellule spécifiée par l'utilisateur, et refuse l'entrée si celle-ci n'est pas valide
	 */
	private def verifyFirstCell() {
		if(!cellPattern.matcher(txtFldFirstCell.text).matches && txtFldFirstCell.text != "") {
			BadFormatDisplayer.dispBadFormatView(txtFldFirstCell, true)
			errors.set(1, true)
			txtFldFirstCell.tooltip = new Tooltip(LanguageManager.translate("studentlist.info.badCellFormat"));
		} else {
			BadFormatDisplayer.dispBadFormatView(txtFldFirstCell, false)
			errors.set(0, false)
			txtFldFirstCell.tooltip = null
		}
		updateLockingState
	}
	
	
	/**
	 * Vérifie si le chemin spécifié par l'utilisateur pointe bien vers un fichier du bon format, refuse l'entrée si cela n'est pas le cas
	 */
	private def verifyFilePath() {
		val file = new File(txtFldFile.text)
		if(!(file.exists && file.isFile && file.name.endsWith(".xlsx")) && txtFldFile.text != "") { //FIXME considérer les autres extensions ?
			BadFormatDisplayer.dispBadFormatView(txtFldFile, true)
			errors.set(1, true)
			if(!file.exists)
				txtFldFile.tooltip = new Tooltip(LanguageManager.translate("studentlist.info.fileNotExist"))
			else
				txtFldFile.tooltip = new Tooltip(LanguageManager.translate("studentlist.info.fileNotValid"))
		} else {
			BadFormatDisplayer.dispBadFormatView(txtFldFile, false)
			errors.set(1, false)
			txtFldFile.tooltip = null
		}
		updateLockingState
	}
	
	/**
	 * Actualise l'état de l'interface en bloquant ou non le bouton de validation si il y a des erreurs
	 */
	private def updateLockingState() {
		btnOk.disable = errors.get(0) || errors.get(1)
	}

	// ----------------------------------------------------------------------------------------------------
	/*
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	def void setPresenterStudentListLoader(PresenterStudentListLoader presStudentList) {
		this.presStudentList = presStudentList
	}
}
