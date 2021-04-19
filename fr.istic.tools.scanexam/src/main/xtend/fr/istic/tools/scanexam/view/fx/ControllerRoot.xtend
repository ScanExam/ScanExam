package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.services.api.ServiceEdition
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation
import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.CheckMenuItem
import javafx.scene.control.MenuItem
import javafx.scene.control.Tab
import javafx.scene.image.Image
import javafx.stage.Stage
import org.eclipse.xtend.lib.annotations.Accessors
import javafx.stage.DirectoryChooser
import java.io.File
import org.apache.logging.log4j.LogManager

class ControllerRoot implements Initializable {
	
	@FXML
	Tab correctorTab;
	@FXML
	Tab editorTab;
	@FXML
	CheckMenuItem autoZoom;
	/* BUTTONS */
	@FXML
	MenuItem saveGraduationButton;
	@FXML
	MenuItem saveTemplateButton;
	@FXML
	MenuItem exportToExamButton;
	@FXML
	MenuItem loadStudentNamesButton;
	@FXML
	MenuItem pdfExportButton;
	
	@Accessors
	ControllerFxGraduation graduationController;
	
	@Accessors
	ControllerFxEdition editionController;
	
	var ServiceEdition serviceEdition
	var ServiceGraduation serviceGraduation
	
	static val logger = LogManager.logger

	
	def setEditorNode(Node n){
		editorTab.content = n
	}
	
	def setGraduationNode(Node n){
		correctorTab.content = n
	}
	
	@FXML
	def loadTemplatePressedEditor(){
		editionController.loadTemplatePressed
	}
	@FXML
	def loadTemplatePressedCorrector() {
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/CorrectionLoaderUI.FXML"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.file.loadGraduation"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")))
		//loader.<ControllerGraduationLoader>controller.initialize(serviceGraduation, editionController, graduationController)
		dialog.setScene(new Scene(view, 384, 355))
		dialog.setResizable(false)
		dialog.show
	}
	
	@FXML
	def createNewTemplatePressed() {
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/TemplateCreatorUI.FXML"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.file.new"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		loader.<ControllerTemplateCreator>controller.initialize(editionController)
		dialog.setScene(new Scene(view, 384, 155))
		dialog.setResizable(false);
		dialog.show
	}
	
	@FXML
	def SaveTemplatePressed(){
		editionController.saveTemplatePressed
	}
	
	@FXML
	def loadStudentList() { 
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/StudentListLoaderUI.FXML"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.file.loadStudentList"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		loader.<ControllerStudentListLoader>controller.initialize(serviceGraduation)
		dialog.setScene(new Scene(view, 384, 160))
		dialog.setResizable(false);
		dialog.show
	}
	
	@FXML
	def updateConfig() {
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/ConfigUI.FXML"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.edit.updateconfig"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		loader.<ControllerConfiguration>controller.initialize()
		dialog.setScene(new Scene(view, 384, 280))
		dialog.setResizable(false);
		dialog.show
	}
	
	@FXML
	def pdfExport() {
		//Sélection du dossier
		var dirChooser = new DirectoryChooser
		dirChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents")
		var directory = dirChooser.showDialog(new Stage)
		if (directory === null) {
			logger.warn("Directory not chosen")
		} else {
			// Export
			graduationController.exportGraduationToPdf(directory)
		}
	}
	
	@FXML
	def sendMail(){
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/SendMailUI.FXML"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.edit.sendmail"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		dialog.setScene(new Scene(view, 672, 416))
		dialog.setResizable(false);
		dialog.show
	}
	
	@FXML
	def loadStudentCopiesPressed() {
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/StudentSheetLoaderUI.FXML"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.file.loadStudentSheet"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		loader.<ControllerGraduationLoader>controller.initialize(serviceGraduation, editionController, graduationController)
		dialog.setScene(new Scene(view, 384, 405))
		dialog.setResizable(false);
		dialog.show
	}
	
	@FXML
	def exportToSheets() {
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/StudentSheetExportUI.FXML"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.file.exportToExam"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		loader.<ControllerStudentSheetExport>controller.initialize(editionController,serviceEdition)
		dialog.setScene(new Scene(view, 384, 107))
		dialog.setResizable(false);
		dialog.show
	}
	
	@FXML
	def saveCorrection(){
		graduationController.saveExam
	}
	
	@FXML
	def toggleAutoZoom(){
		graduationController.toAutoZoom = autoZoom.selected
	}
	
	
	def init(ServiceEdition serviceEdition,ServiceGraduation serviceGraduation){
		this.serviceEdition = serviceEdition
		this.serviceGraduation = serviceGraduation
		saveGraduationButton.disableProperty.bind(graduationController.loadedModel.not)
		saveTemplateButton.disableProperty.bind(editionController.loadedModel.not)
		exportToExamButton.disableProperty.bind(editionController.loadedModel.not)
		loadStudentNamesButton.disableProperty.bind(editionController.loadedModel.not)
		pdfExportButton.disableProperty.bind(graduationController.loadedModel.not)
	}
	
	override initialize(URL location, ResourceBundle resources) {
		
	}
	
}