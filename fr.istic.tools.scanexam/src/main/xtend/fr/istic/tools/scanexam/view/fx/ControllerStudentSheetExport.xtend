package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.presenter.PresenterStudentSheetExport
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField
import java.io.File
import java.util.Optional
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import fr.istic.tools.scanexam.config.LanguageManager

class ControllerStudentSheetExport {

	/* Composant racine */	
	@FXML
	var VBox mainPane
	
	/* TextField pour saisir le nombre de copies voulues */
	@FXML
	var FormattedTextField txtFlbNbSheet
	
	/* Button pour exporter */
	@FXML
	var Button btnExport
	
	var PresenterStudentSheetExport presenter
	
	static val logger = LogManager.logger
	
	/**
	 * Initialise le composant avec le presenter composé en paramètre
	 * @param loader le presenter
	 */
	def initialize(PresenterStudentSheetExport presenter) {
		this.presenter = presenter	
		
		txtFlbNbSheet.addFormatValidator[str | Integer.parseInt(str) > 0 ? Optional.empty : Optional.of("exportStudentSheet.errorZeroValue")]
		btnExport.disableProperty.bind(txtFlbNbSheet.wrongFormattedProperty)	
	}
	
	@FXML
	def exportAndQuit() {
		val fileOpt = loadFolder
		if(fileOpt.isPresent) {
			if(presenter.export(fileOpt.get, Integer.parseInt(txtFlbNbSheet.text)))
				quit
		}
	}
	
	@FXML
	def quit() {
		val Stage stage = mainPane.scene.window as Stage
		stage.close();
	}
	
	
	/**
	 * Affiche un sélectionneur de dossier
	 * @return le chemin du dossier sélectionné par l'utilisateur, Optional.empty si aucun dossier sélectionné
	 */
	private def Optional<File> loadFolder() {
		var fileChooser = new FileChooser
		fileChooser.extensionFilters.add(new ExtensionFilter(LanguageManager.translate("file.format.pdf"), "*.pdf"))
		fileChooser.initialDirectory = new File(
				System.getProperty("user.home") + System.getProperty("file.separator") + "Documents")
		var file = fileChooser.showSaveDialog(mainPane.scene.window)
		if (file !== null) {
			return Optional.of(file)
		} else {
			logger.warn("Folder not chosen")
			return Optional.empty
		}
	}
}