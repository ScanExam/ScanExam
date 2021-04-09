package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fx.corrector.ControllerFXCorrector
import fr.istic.tools.scanexam.view.fx.editor.ControllerFXEditor
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.CheckMenuItem
import javafx.scene.control.Tab
import javafx.stage.Stage
import javafx.scene.image.Image

class ControllerRoot {
	
	@FXML
	Tab correctorTab;
	@FXML
	Tab editorTab;
	@FXML
	CheckMenuItem autoZoom;
	
	ControllerFXCorrector corrector;
	ControllerFXEditor editor;
	
	def setEditorController(ControllerFXEditor editor){
		this.editor = editor;
	}
	def setCorrectorController(ControllerFXCorrector corrector){
		this.corrector = corrector;
	}
	
	def setEditor(Node n){
		editorTab.content = n
	}
	
	def setCorrector(Node n){
		correctorTab.content = n
	}
	
	@FXML
	def loadTemplatePressedEditor(){
		editor.loadTemplatePressed
	}
	@FXML
	def loadTemplatePressedCorrector(){
		corrector.loadPressed
	}
	@FXML
	def createNewTemplatePressed(){
		editor.newTemplatePressed
	}
	@FXML
	def SaveTemplatePressed(){
		editor.saveTemplatePressed
	}
	@FXML
	def LoadStudentCopiesPressed(){
		
	}
	
	@FXML
	def loadStudentList() { 
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/StudentListLoaderUI.FXML"))
		val Stage dialog = new Stage
		dialog.setTitle(LanguageManager.translate("menu.file.loadStudentList"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		dialog.setScene(new Scene(view, 384, 160))
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
		dialog.setScene(new Scene(view, 384, 280))
		dialog.show
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
		dialog.show
	}
	
	@FXML
	def toggleAutoZoom(){
		corrector.toAutoZoom = autoZoom.selected
	}
	
	
	
	
}