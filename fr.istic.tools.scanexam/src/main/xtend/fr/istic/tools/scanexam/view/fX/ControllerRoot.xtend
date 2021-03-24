package fr.istic.tools.scanexam.view.fX

import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Tab

class ControllerRoot {
	
	@FXML
	Tab correctorTab;
	
	@FXML
	Tab editorTab;
	
	
	
	def setEditor(Node n){
		editorTab.content = n
	}
	
	def setCorrector(Node n){
		correctorTab.content = n
	}
	
	
}