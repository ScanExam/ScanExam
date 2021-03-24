package fr.istic.tools.scanexam.view.fX

import fr.istic.tools.scanexam.view.fX.corrector.ControllerFXCorrector
import fr.istic.tools.scanexam.view.fX.editor.ControllerFXEditor
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Tab

class ControllerRoot {
	
	@FXML
	Tab correctorTab;
	@FXML
	Tab editorTab;
	
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
	def sendMail(){
		println("sending mail")
	}
	
	
	
	
}