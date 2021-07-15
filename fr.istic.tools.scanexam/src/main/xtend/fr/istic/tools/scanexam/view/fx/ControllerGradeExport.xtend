package fr.istic.tools.scanexam.view.fx

import javafx.fxml.FXML
import org.apache.logging.log4j.LogManager
import javafx.scene.layout.Pane
import javafx.scene.control.CheckBox
import javafx.scene.control.MenuButton
import javafx.scene.control.Button
import javafx.stage.Stage
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import fr.istic.tools.scanexam.config.LanguageManager
import java.util.Arrays
import java.io.File
import fr.istic.tools.scanexam.exportation.GradesExportImpl
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation
import javafx.scene.control.MenuItem
import javafx.event.EventHandler
import javafx.event.ActionEvent
import javafx.scene.control.Label

/**
 * Classe gérant l'interface pour l'export des notes en fichier excel
 * @author Julien Cochet
 */
class ControllerGradeExport {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	/** Logger du programme */
	static val logger = LogManager.logger

	/** Pane principale de la vue */
	@FXML
	public Pane mainPane
	/** Indique si les élèves doivent être rangé */
	@FXML
	public CheckBox order
	/** Label devant le menu */
	@FXML
	public Label menuLabel
	/** Menu des manièrre de trier */
	@FXML
	public MenuButton orderByMenu
	/** Bouton de validation */
	@FXML
	public Button btnOk

	/** Controller gérant la correction */
	ControllerFxGraduation controllerGraduation

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	@FXML
	def void saveAndQuit() {
		var fileChooser = new FileChooser
		fileChooser.extensionFilters.add(
			new ExtensionFilter(LanguageManager.translate("exportExcel.fileFormat"), Arrays.asList("*.xlsx")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents")
		var file = fileChooser.showSaveDialog(mainPane.scene.window)

		if (file !== null) {
			if (!file.getName().contains(".xlsx")) {
				file = new File(file.getAbsolutePath() + ".xlsx")
			}
			controllerGraduation.saveTemplate(file.path)
			logger.info("Export grade in Excel")
		} else {
			logger.warn("File not chosen")
		}
		if (order.selected) {
			if (orderByMenu.text == LanguageManager.translate("studentsTab.tableView.ID")) {
				(new GradesExportImpl).exportGrades(controllerGraduation.service.getStudentSheetsOrderBy(0), file)
			} else {
				if (orderByMenu.text == LanguageManager.translate("studentsTab.tableView.lastName")) {
					(new GradesExportImpl).exportGrades(controllerGraduation.service.getStudentSheetsOrderBy(1), file)
				} else {
					(new GradesExportImpl).exportGrades(controllerGraduation.service.getStudentSheetsOrderBy(2), file)
				}
			}
		} else {
			(new GradesExportImpl).exportGrades(controllerGraduation.service.studentSheets, file)
		}
		quit
	}

	@FXML
	def void quit() {
		val Stage stage = mainPane.scene.window as Stage
		stage.close
	}

	/**
	 * Initialise le composant avec le controller gérant la correction
	 * @param controllerGraduation Controller gérant la correction
	 */
	def initialize(ControllerFxGraduation controllerGraduation) {
		this.controllerGraduation = controllerGraduation

		enableOrdering(order.selected)
		order.setOnAction(new EventHandler<ActionEvent> {
			override handle(ActionEvent e) {
				enableOrdering(order.selected)
			}
		})

		val EventHandler<ActionEvent> formatMenuEvent = new EventHandler<ActionEvent> {
			override handle(ActionEvent e) {
				val String selection = (e.source as MenuItem).text
				orderByMenu.text = selection
			}
		}
		orderByMenu.items.add(new MenuItem(LanguageManager.translate("studentsTab.tableView.ID")))
		orderByMenu.items.add(new MenuItem(LanguageManager.translate("studentsTab.tableView.lastName")))
		orderByMenu.items.add(new MenuItem(LanguageManager.translate("studentsTab.tableView.firstName")))
		for (item : orderByMenu.items) {
			item.onAction = formatMenuEvent
		}
	}

	/**
	 * Active ou non les champs se rapportant à la saisie pour le nombre de copies voulues
	 * @param enable True pour activer les champs
	 */
	private def void enableOrdering(boolean enable) {
		menuLabel.disable = !enable
		orderByMenu.disable = !enable
	}
}
