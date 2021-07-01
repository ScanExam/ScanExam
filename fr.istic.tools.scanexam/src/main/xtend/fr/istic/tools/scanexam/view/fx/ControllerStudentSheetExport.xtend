package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl
import fr.istic.tools.scanexam.services.api.ServiceEdition
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition
import java.io.File
import java.util.Optional
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager
import fr.istic.tools.scanexam.qrCode.QrCodeType
import javafx.scene.control.CheckBox
import javafx.event.EventHandler
import javafx.event.ActionEvent
import javafx.scene.control.Label

class ControllerStudentSheetExport {

	/* Composant racine */
	@FXML
	var VBox mainPane

	/** CComposant indiquant si les copies doivent être différenciée */
	@FXML
	var CheckBox copiesDiff

	/* Label devant la saisie pour le nombre de copies voulues */
	@FXML
	var Label lblNbSheet

	/* TextField pour saisir le nombre de copies voulues */
	@FXML
	var FormattedTextField txtFlbNbSheet

	/* Button pour exporter */
	@FXML
	var Button btnExport

	var ControllerFxEdition controllerEdition

	var ServiceEdition service

	static val logger = LogManager.logger

	/**
	 * Initialise le composant avec le presenter composé en paramètre
	 * @param loader le presenter
	 */
	def initialize(ControllerFxEdition controllerEdition, ServiceEdition service) {
		this.service = service
		this.controllerEdition = controllerEdition

		enableNbSheet(copiesDiff.selected)
		copiesDiff.setOnAction(new EventHandler<ActionEvent> {
			override handle(ActionEvent e) {
				enableNbSheet(copiesDiff.selected)
			}
		})

		txtFlbNbSheet.addFormatValidator [ str |
			Integer.parseInt(str) > 0 ? Optional.empty : Optional.of("exportStudentSheet.errorZeroValue")
		]

		btnExport.disableProperty.bind(txtFlbNbSheet.wrongFormattedProperty)
	}

	@FXML
	def exportAndQuit() {
		val fileOpt = loadFolder
		if (fileOpt.isPresent) {
			var boolean exported = copiesDiff.selected
					? export(fileOpt.get, QrCodeType.SHEET_PAGE, Integer.parseInt(txtFlbNbSheet.text))
					: export(fileOpt.get, QrCodeType.PAGE, 1)
			if (exported) {
				quit
			}
		}
	}

	def boolean export(File file, int qrCodeType, int number) {
		// Si aucune zone de qr code n'a été placée, on en crée une dans le modèle
		if (service.qrCodeZone === null) {
			service.createQrCode(0.025f, 0.875f, 0.1f, 0.1f)
		}

		val QRCodeGenerator generator = new QRCodeGeneratorImpl
		generator.createAllExamCopies(controllerEdition.pdfManager.getPdfInputStream, file, service.qrCodeZone.get,
			qrCodeType, number)
		true
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

	/**
	 * Active ou non les champs se rapportant à la saisie pour le nombre de copies voulues
	 * @param enable True pour activer les champs
	 */
	private def void enableNbSheet(boolean enable) {
		lblNbSheet.disable = !enable
		txtFlbNbSheet.disable = !enable
	}
}
