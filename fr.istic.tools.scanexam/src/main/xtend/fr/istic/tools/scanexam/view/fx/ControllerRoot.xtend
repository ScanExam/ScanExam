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
import javafx.scene.control.TabPane
import fr.istic.tools.scanexam.view.fx.students.ControllerFxStudents
import javafx.scene.control.Alert
import javafx.scene.image.ImageView
import java.awt.Desktop
import java.net.URI

class ControllerRoot implements Initializable {

	@FXML
	Tab correctorTab;
	@FXML
	Tab editorTab;
	@FXML
	Tab studentsTab

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
	@FXML
	MenuItem linkManuallySheetsButton
	@FXML
	TabPane tabPane;

	@Accessors
	ControllerFxGraduation graduationController;

	@Accessors
	ControllerFxEdition editionController;

	@Accessors
	ControllerFxStudents studentsController

	var ServiceEdition serviceEdition
	var ServiceGraduation serviceGraduation

	static val logger = LogManager.logger

	def init() {
		tabPane.selectionModel.selectedItemProperty.addListener([obs, oldVal, newVal|graduationController.changedTab])
	}

	def setEditorNode(Node n) {
		editorTab.content = n
	}

	def setGraduationNode(Node n) {
		correctorTab.content = n
	}

	def setStudentsNode(Node n) {
		studentsTab.content = n
	}

	@FXML
	def loadTemplatePressedEditor() {
		tabPane.getSelectionModel().select(editorTab)
		editionController.loadTemplatePressed
	}

	@FXML
	def saveGraduation() {
		graduationController.saveExam
	}

	@FXML
	def loadTemplatePressedCorrector() {
		tabPane.getSelectionModel().select(correctorTab)
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/GraduationLoaderUI.fxml"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.file.loadGraduation"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")))
		loader.<ControllerGraduationLoader>controller.initialize(serviceGraduation, editionController,
			graduationController)
		dialog.setScene(new Scene(view, 384, 355))
		dialog.setResizable(false)
		dialog.show
	}

	def goToCorrectorTab(int id) {
		tabPane.getSelectionModel().select(correctorTab)
		graduationController.selectStudent(id)

	}

	@FXML
	def createNewTemplatePressed() {
		tabPane.getSelectionModel().select(editorTab)
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
	def SaveTemplatePressed() {
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
		dialog.setScene(new Scene(view, 384, 206))
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
	def linkManuallySheets() {
		val FXMLLoader loader = new FXMLLoader
		loader.resources = LanguageManager.currentBundle
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/ManuallyLinkSheets.fxml"))
		val Stage dialog = new Stage
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		loader.<ControllerLinkManuallySheets>controller.init(serviceGraduation, graduationController.pdfManager,
			graduationController)
		dialog.title = LanguageManager.translate("menu.edit.linkSheetsTitle")
		dialog.setScene(new Scene(view))
		dialog.setResizable(false);
		dialog.show
	}

	@FXML
	def pdfExport() {

		// Vérification de copies avec aucun nom associé
		val nameList = serviceGraduation.studentIds

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
				studentSheets.filter(x|!nameList.contains(x.studentID)).size as int
			else
				-1

		DialogMessageSender.sendDialog(
			AlertType.WARNING,
			LanguageManager.translate("sendMail.noStudentDataHeader"),
			nbSheetWithoutName > 1
				? String.format(LanguageManager.translate("sendMail.notAllStudent"), nbSheetWithoutName)
				: LanguageManager.translate("sendMail.notAllStudent1"),
			null
		)

		// Sélection du dossier	
		var dirChooser = new DirectoryChooser
		dirChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents")
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
		tabPane.getSelectionModel().select(correctorTab)
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/GraduationCreatorUI.fxml"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.file.loadStudentSheet"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		loader.<ControllerGraduationCreator>controller.initialize(serviceGraduation, editionController,
			graduationController, this)
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
		loader.<ControllerStudentSheetExport>controller.initialize(editionController, serviceEdition)
		dialog.setScene(new Scene(view, 384, 160))
		dialog.setResizable(false);
		dialog.show
	}

	@FXML
	def exportStudentsQrCodes() {
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(
			ResourcesUtils.getInputStreamResource("viewResources/StudentsQrCodeDocGeneratorUI.fxml"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.file.exportStudentsQrCodes"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")))
		dialog.setScene(new Scene(view, 384, 374))
		dialog.setResizable(false)
		dialog.show
	}

	@FXML
	def gradeExport() {
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/GradeExportUI.fxml"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.edit.gradeExport"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		loader.<ControllerGradeExport>controller.initialize(graduationController)
		dialog.setScene(new Scene(view, 384, 160))
		dialog.setResizable(false)
		dialog.show
	}

	@FXML
	def saveCorrection() {
		graduationController.saveExam
	}

	@FXML
	def toggleAutoZoom() {
		graduationController.toAutoZoom = autoZoom.selected
	}

	def init(ServiceEdition serviceEdition, ServiceGraduation serviceGraduation) {
		/*
		 * FIXME
		 * la méthode graduationController.loadedModel.not ne vérifie pas si
		 * un élément peut générer une NPE. Plusieurs exemples :
		 * - pour l'export des notes : si le grader ne contient pas encore de notes : NPE
		 * - pour lier les pages mal reconnues : si il n'y a pas de pages mal reconnues : NPE
		 */
		this.serviceEdition = serviceEdition
		this.serviceGraduation = serviceGraduation
		saveGraduationButton.disableProperty.bind(graduationController.loadedModel.not)
		saveTemplateButton.disableProperty.bind(editionController.loadedModel.not)
		exportToExamButton.disableProperty.bind(editionController.loadedModel.not)
		loadStudentNamesButton.disableProperty.bind(graduationController.loadedModel.not)
		pdfExportButton.disableProperty.bind(graduationController.loadedModel.not)
		sendMailButton.disableProperty.bind(graduationController.loadedModel.not)
		pdfExportGradeButton.disableProperty.bind(graduationController.loadedModel.not)
		linkManuallySheetsButton.disableProperty.bind(graduationController.loadedModel.not)
	}

	override initialize(URL location, ResourceBundle resources) {
	}
	
	@FXML
	def openGuide() {
		val Desktop desktop = Desktop.isDesktopSupported ? Desktop.getDesktop : null
	    if (desktop !== null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(URI.create(LanguageManager.translate("guide.link")))
	        } catch (Exception e) {
	            e.printStackTrace
	        }
	    }
	}
	
	@FXML
	def openAbout(){
		val Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(LanguageManager.translate("menu.help.about"));
        alert.setHeaderText(LanguageManager.translate("about.title"));
        val Image image = new Image(ResourcesUtils.getInputStreamResource("istic_logo.png"));
		val ImageView imageView = new ImageView(image);
		alert.setGraphic(imageView);
        alert.setContentText("BEUREL Luca, CARUANA Romain, COCHET Julien, \nDANLOS Benjamin, DEGAS Antoine, DERRIEN Steven, \nGHOUTI TERKI Rida, MA Qian, GIRAUDET Théo, \nGUIBERT Thomas, LALANDE MARCHAND Arthur, LELOUP Alexis, \nLOCKE Stefan, LUMBROSO Marius, PAYS Matthieu​");

 		
        alert.showAndWait();
	}
}
