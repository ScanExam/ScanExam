package fr.istic.tools.scanexam.view.fx

import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.scene.web.HTMLEditor
import javafx.stage.Stage

/**
 * Classe pour envoyer les corrigés par mail en JavaFX
 * @author Julien Cochet
 */
class ControllerSendMail {

	
	/* Pane principale de la vue */
	@FXML
	public Pane mainPane
	
	/* Champ de texte du titre */
	@FXML
	public TextField txtFldTitle
	
	/* Champ de texte du mail */
	@FXML
	public HTMLEditor htmlEditor
	
	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	

	
	@FXML
	def void saveAndQuit() {
		 // sendMails(txtFldTitle.text, htmlEditor.htmlText) TODO
		quit
	}
	
	@FXML
	def void quit() {
  		val Stage stage = mainPane.scene.window as Stage
  		stage.close();
	}
	
	
	
}