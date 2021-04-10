package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.presenter.PresenterStudentListLoader
import fr.istic.tools.scanexam.presenter.PresenterStudentListLoader.LoadState
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fx.utils.BadFormatDisplayer
import java.io.File
import java.util.Arrays
import java.util.regex.Pattern
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.control.Tooltip
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager

/**
 * Classe pour charger la liste des étudiant en JavaFX
 * @author Julien Cochet
 */
class AdapterFxStudentListLoader {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	/* Logger du programme */
	static val logger = LogManager.logger
	static val Pattern cellPattern = Pattern.compile("[A-Z]+[1-9][0-9]*")
	val static supportedFormat = Arrays.asList("*.ods", "*.ots", "*.sxc", "*.stc", "*.fods", "*.xml", "*.xlsx", "*.xltx", "*.xlsm",
					"*.xlsb", "*.xls", "*.xlc", "*.xlm", "*.xlw", "*.xlk", "*.et", "*.xlt", "*.ett", "*.dif", "*.wk1",
					"*.xls", "*.123", "*.wb2", "*.csv")

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


	@FXML
	def void saveAndQuit() {
		val state = presStudentList.loadFile(new File(txtFldFile.text), txtFldFirstCell.text)
		verifyFilePath
		verifyFirstCell
		if(!btnOk.disable)
			dispDialog(state)
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
		fileChooser.extensionFilters.add( new ExtensionFilter("Calc files", supportedFormat))
		// Si le contenu de txtFldFile est un Path valide, le fileChooser part alors de ce path
		val specifiedFile = new File(txtFldFile.text)
		if(specifiedFile.exists && specifiedFile.isDirectory)
			fileChooser.initialDirectory = specifiedFile
		else if(specifiedFile.exists && specifiedFile.isFile)
			fileChooser.initialDirectory = specifiedFile.parentFile
		else
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
		val rightFormat = supportedFormat.map[f | f.substring(1)].findFirst[f | file.name.endsWith(f)] !== null
		if((!file.exists || !file.isFile || !rightFormat) && txtFldFile.text != "") {
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

	/**
	 * Initialise le contrôleur
	 */
	def void initialize(PresenterStudentListLoader presStudentList) {
		this.presStudentList = presStudentList
		
		txtFldFile.text = presStudentList.studentListPath
		txtFldFirstCell.text = presStudentList.studentListShift
		
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
	
	/**
	 * Affiche un boîte de dialog décrivant la réussite ou non du chargement des données
	 * @param state un LoadState décrivant le réussite ou non du chargement des données
	 */
	def void dispDialog(LoadState state) {
		val alert = new Alert(AlertType.NONE)
		val stage = alert.getDialogPane().getScene().getWindow() as Stage
		stage.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")))
		if (state == LoadState.SUCCESS) {
			alert.alertType = AlertType.CONFIRMATION
			alert.setTitle(LanguageManager.translate("studentlist.loadConfirmation.title"))
			alert.setHeaderText(String.format(LanguageManager.translate("studentlist.loadConfirmation.success"), presStudentList.numberPair))
			alert.contentText = presStudentList.studentList
		} else {
			alert.alertType = AlertType.ERROR
			alert.setTitle(LanguageManager.translate("studentlist.loadConfirmation.title"))
			if (state == LoadState.X_NOT_VALID)
				alert.setHeaderText(LanguageManager.translate("studentlist.loadConfirmation.xNotValid"))
			else
				alert.setHeaderText(LanguageManager.translate("studentlist.loadConfirmation.yNotValid"))
		}
		alert.showAndWait
	}
}
