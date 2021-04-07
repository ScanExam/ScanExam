package fr.istic.tools.scanexam.view.fX

import java.util.Locale
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.stage.Stage
import fr.istic.tools.scanexam.presenter.PresenterConfiguration
import javafx.collections.FXCollections
import fr.istic.tools.scanexam.config.LanguageManager
import javafx.fxml.Initializable
import java.net.URL
import java.util.ResourceBundle

/**
 * Classe pour gérer la fenêtre de configuration en JavaFX
 * @author Julien Cochet
 */

class AdapterFXConfiguration implements Initializable {
	
	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Controlleur de la configuration */
	var PresenterConfiguration presConfig
	
	/* Pane principale de la vue */
	@FXML
	public Pane mainPane
	
	/* Sélection de la langue */
	@FXML
	public ComboBox<Locale> cmbBxLanguage
	
	/* Champ de l'email */
	@FXML
	public TextField txtFldEmail
	
	/* Champ du mot de passe de l'email */
	@FXML
	public PasswordField pwdFldEmailPassword
	
	/* Champ de l'hébergeur de l'email */
	@FXML
	public TextField txtFldEmailHost
	
	/* Champ du port de l'email */
	@FXML
	public TextField txtFldEmailPort
	
	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Constructeur. Initialise les différents champs avec la valeur actuelle de la configuration
	 */
	
	
	override initialize(URL location, ResourceBundle resources) 
	{
		presConfig = new PresenterConfiguration
		
		cmbBxLanguage.value = LanguageManager.currentLanguage
		txtFldEmail.text = presConfig.email
		pwdFldEmailPassword.text = presConfig.emailPassword
		txtFldEmailHost.text = presConfig.mailHost
		txtFldEmailPort.text = presConfig.mailPort
		
		cmbBxLanguage.items = FXCollections.observableArrayList(presConfig.languages)
	}
	
	
	@FXML
	def void saveAndQuit() 
	{
		presConfig.updateConfig(cmbBxLanguage.value, txtFldEmail.text, pwdFldEmailPassword.text, txtFldEmailHost.text, txtFldEmailPort.text)
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
	
	def void setPresenterConfiguration(PresenterConfiguration presConfig) {
		this.presConfig = presConfig
	}
	
	
}
