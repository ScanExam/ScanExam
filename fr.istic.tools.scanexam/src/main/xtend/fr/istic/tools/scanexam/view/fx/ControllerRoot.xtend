package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.ConfigurationManager
import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.services.api.ServiceEdition
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation
import fr.istic.tools.scanexam.view.fx.utils.DialogMessageSender
import java.io.File
import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.CheckMenuItem
import javafx.scene.control.MenuItem
import javafx.scene.control.Tab
import javafx.scene.image.Image
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager
import org.eclipse.xtend.lib.annotations.Accessors

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
	@FXML
	MenuItem sendMailButton;
	@FXML
	MenuItem pdfExportGradeButton;
	
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
	def saveGraduation() {
		graduationController.saveExam
	}
	
	
	@FXML
	def loadTemplatePressedCorrector() {
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/GraduationLoaderUI.fxml"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.file.loadGraduation"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")))
		loader.<ControllerGraduationLoader>controller.initialize(serviceGraduation, editionController, graduationController)
		dialog.setScene(new Scene(view, 384, 355))
		dialog.setResizable(false)
		dialog.show
	}
	
	@FXML
	def createNewTemplatePressed() {
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/TemplateCreatorUI.fxml"))
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
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/StudentListLoaderUI.fxml"))
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
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/ConfigUI.fxml"))
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
		
		// Vérification de copies avec aucun nom associé
		
		val nameList = serviceGraduation.studentNames
		
		if (nameList.empty) {
			DialogMessageSender.sendTranslateDialog(
				AlertType.WARNING,
				"sendMail.noStudentDataHeader",
				"sendMail.noStudentDataHeader",
				"sendMail.noStudentData"
			);
			return
		}
		
		val studentSheets = serviceGraduation.studentSheets
		val nbSheetWithoutName = if (!nameList.empty)
			studentSheets.filter(x|!nameList.contains(x.studentName)).size as int
		else
			-1
			
		DialogMessageSender.sendDialog(
				AlertType.WARNING,
				LanguageManager.translate("sendMail.noStudentDataHeader"),
				nbSheetWithoutName > 1 ? String.format(LanguageManager.translate("sendMail.notAllStudent"),
					nbSheetWithoutName) : LanguageManager.translate("sendMail.notAllStudent1"),
				null
		)
			
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
	def sendMail() {
		val config = ConfigurationManager.instance
		val rightConfig = config.email == "" || config.emailPassword == "" || config.mailHost == "" ||
			config.mailPort == 0
		if (rightConfig) {
			DialogMessageSender.sendTranslateDialog(
				AlertType.ERROR,
				"error",
				"sendMail.noCredentialTitle",
				"sendMail.noCredentialBody"
			);
		} else {
			val FXMLLoader loader = new FXMLLoader
			loader.setResources(LanguageManager.currentBundle)

			val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/SendMailUI.fxml"))
			val Stage dialog = new Stage

			loader.<ControllerSendMail>controller.init(serviceGraduation, graduationController)

			dialog.setTitle(LanguageManager.translate("menu.edit.sendmail"))
			dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
			dialog.setScene(new Scene(view, 672, 416))
			dialog.setResizable(false);
			dialog.show
		}
	}
	
	@FXML
	def loadStudentCopiesPressed() {
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/GraduationCreatorUI.fxml"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.file.loadStudentSheet"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		loader.<ControllerGraduationCreator>controller.initialize(serviceGraduation, editionController, graduationController)
		dialog.setScene(new Scene(view, 384, 405))
		dialog.setResizable(false);
		dialog.show
	}
	
	@FXML
	def exportToSheets() {
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/StudentSheetExportUI.fxml"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.file.exportToExam"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		loader.<ControllerStudentSheetExport>controller.initialize(editionController,serviceEdition)
		dialog.setScene(new Scene(view, 384, 107))
		dialog.setResizable(false);
		dialog.show
	}
	
	@FXML
	def gradeExport() {
		graduationController.exportGrades
	}
	
	@FXML
	def saveCorrection(){
		graduationController.saveExam
	}
	
	@FXML
	def toggleAutoZoom(){
		graduationController.toAutoZoom = autoZoom.selected
	}
	
	
	def init(ServiceEdition serviceEdition, ServiceGraduation serviceGraduation){
		this.serviceEdition = serviceEdition
		this.serviceGraduation = serviceGraduation
		saveGraduationButton.disableProperty.bind(graduationController.loadedModel.not)
		saveTemplateButton.disableProperty.bind(editionController.loadedModel.not)
		exportToExamButton.disableProperty.bind(editionController.loadedModel.not)
		loadStudentNamesButton.disableProperty.bind(graduationController.loadedModel.not)
		pdfExportButton.disableProperty.bind(graduationController.loadedModel.not)
		sendMailButton.disableProperty.bind(graduationController.loadedModel.not)
		pdfExportGradeButton.disableProperty.bind(graduationController.loadedModel.not)
	}
	
	override initialize(URL location, ResourceBundle resources) {
		
	}
	
}