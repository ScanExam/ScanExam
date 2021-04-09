package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.presenter.PresenterSendMail
import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.scene.web.HTMLEditor
import javafx.stage.Stage

/**
 * Classe pour envoyer les corrigés par mail en JavaFX
 * @author Julien Cochet
 */
class AdapterFXSendMail {
	
	/* Controlleur de la configuration */
	var PresenterSendMail presSendMail
	
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
	
	/**
	 * Constructeur
	 */
	new() {
		presSendMail = new PresenterSendMail
	}
	
	@FXML
	def void saveAndQuit() {
		presSendMail.sendMails(txtFldTitle.text, htmlEditor.htmlText)
		quit
	}
	
	@FXML
	def void quit() {
  		val Stage stage = mainPane.scene.window as Stage
  		stage.close();
	}
	
	// ----------------------------------------------------------------------------------------------------
	/*
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def void setPresenterSendMail(PresenterSendMail presSendMail) {
		this.presSendMail = presSendMail
	}
	
}
