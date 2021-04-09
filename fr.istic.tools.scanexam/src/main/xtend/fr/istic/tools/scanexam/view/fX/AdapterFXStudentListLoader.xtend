package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.presenter.PresenterStudentListLoader
import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import java.util.Arrays
import java.io.File
import org.apache.logging.log4j.LogManager

/**
 * Classe pour charger la liste des étudiant en JavaFX
 * @author Julien Cochet
 */
class AdapterFXStudentListLoader {
	
	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Logger du programme */
	static val logger = LogManager.logger
	
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
	
	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Constructeur
	 */
	new() {
		presStudentList = new PresenterStudentListLoader
	}
	
	@FXML
	def void saveAndQuit() {
		presStudentList.sendToService(txtFldFile.text, txtFldFirstCell.text)
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
		fileChooser.extensionFilters.add(new ExtensionFilter("Calc files", Arrays.asList("*.ods", "*.ots", "*.sxc", "*.stc", "*.fods", "*.xml", "*.xlsx", "*.xltx", "*.xlsm", "*.xlsb", "*.xls", "*.xlc", "*.xlm", "*.xlw", "*.xlk", "*.et", "*.xlt", "*.ett", "*.dif", "*.wk1", "*.xls", "*.123", "*.wb2", "*.csv")))
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents")
		var file = fileChooser.showOpenDialog(mainPane.scene.window)
		if (file !== null) {
			txtFldFile.text = file.path
		} else {
			logger.warn("File not chosen")
		}
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
