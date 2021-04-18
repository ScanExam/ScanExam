package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.mailing.StudentDataManager
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField
import fr.istic.tools.scanexam.view.fx.component.validator.ValidFilePathValidator
import java.io.File
import java.util.Arrays
import java.util.Optional
import java.util.regex.Pattern
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.TextFormatter
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
class ControllerStudentListLoader {

	private enum LoadState {
		SUCCESS,
		X_NOT_VALID,
		Y_NOT_VALID
	}
	
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



	/* Pane principale de la vue */
	@FXML
	public Pane mainPane

	/* Champ du fichier à charger */
	@FXML
	public FormattedTextField txtFldFile

	/* Champ de la première casse */
	@FXML
	public FormattedTextField txtFldFirstCell
	
	/* Bouton de validation */
	@FXML
	public Button btnOk
	
	var ServiceGraduation service

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------


	@FXML
	def void saveAndQuit() {
		val state = loadFile(new File(txtFldFile.text), txtFldFirstCell.text)
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
	}
	
	/**
	 * Initialise le contrôleur
	 */
	def void initialize(ServiceGraduation service) {
		
		this.service = service
		txtFldFile.text = studentListPath
		txtFldFirstCell.text = studentListShift
		
		txtFldFirstCell.textFormatter = new TextFormatter<String> [ change |
			change.text = change.text.toUpperCase
			change
		]
		
		btnOk.disableProperty.bind(txtFldFile.wrongFormattedProperty.or(txtFldFirstCell.wrongFormattedProperty))
		
		txtFldFirstCell.addFormatValidator(text | !cellPattern.matcher(txtFldFirstCell.text).matches ? Optional.of("studentlist.info.badCellFormat") : Optional.empty)
		
		txtFldFile.addFormatValidator(text | supportedFormat.map[f | f.substring(1)].findFirst[f | text.endsWith(f)] !== null ? Optional.empty : Optional.of("studentlist.info.fileNotValid"))
		txtFldFile.addFormatValidator(new ValidFilePathValidator)
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
			alert.setHeaderText(String.format(LanguageManager.translate("studentlist.loadConfirmation.success"), numberPair))
			alert.contentText = studentList
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
	
		/**
	 * Envoie les informations au service
	 * @param file Chemin du fichier contenant la liste des étudiants
	 * @param firstCell Première case à prendre en compte
	 * @return un LoadState représentant l'état terminal du chargement des données
	 */
	def LoadState loadFile(File file, String firstCell) {
		if(!StudentDataManager.isValidX(firstCell))
			return LoadState.X_NOT_VALID
		else if(!StudentDataManager.isValidY(firstCell))
			return LoadState.Y_NOT_VALID
			
		StudentDataManager.loadData(file, firstCell)
		service.studentListPath = file.absolutePath
		service.studentListShift = firstCell
		return LoadState.SUCCESS
	}
	
	/**
	 * @return le nombre de paires parsée par StudentDataManager, -1 si aucune n'a été parsée
	 */
	def int getNumberPair() {
		StudentDataManager.getNameToMailMap().map(map | map.size).orElse(-1)
	}
	
	/**
	 * @return la liste des données parsées sous forme de String. Chaîne vide si aucune données n'a été parsée
	 */
	def String getStudentList() {
		StudentDataManager.getNameToMailMap()
			.map(map | map.entrySet
					   .map(entry | entry.key + " - " + entry.value)
					   .join("\n"))
			.orElse("")
	}
	
	/**
	 * @return le path vers le fichier contenant la liste des étudiants. Chaîne vide si celui n'est pas défini
	 */
	def String getStudentListPath() {
		return ""
		//return service.studentListPath === null ? "" : service.studentListPath
	}
	
	/**
	 * @return la première case à prendre en compte dans le fichier contenant la liste des étudiants
	 */
	def String getStudentListShift() {
		return "A1"
		//return service.studentListShift
	}
}
