package fr.istic.tools.scanexam.view.fX

import java.util.Locale
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.stage.Stage

/**
 * Classe pour gérer la fenêtre de configuration en JavaFX
 * @author Julien Cochet
 */
class AdapterFXConfiguration {
	
	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Controlleur de la configuration */
	var ControllerConfiguration ctrlConfig
	
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
	new() {
		ctrlConfig = new ControllerConfiguration
		/*
		cmbBxLanguage.value = ctrlConfig.config.language
		txtFldEmail.text = ctrlConfig.config.email
		pwdFldEmailPassword.text = ctrlConfig.config.emailPassword
		txtFldEmailHost.text = ctrlConfig.config.mailHost
		txtFldEmailPort.text = ctrlConfig.config.mailPort
		*/
	}
	
	@FXML
	def void saveAndQuit() {
		ctrlConfig.updateConfig(cmbBxLanguage.value, txtFldEmail.text, pwdFldEmailPassword.text, txtFldEmailHost.text, txtFldEmailPort.text)
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
	
	def void setControllerConfiguration(ControllerConfiguration ctrlConfig) {
		this.ctrlConfig = ctrlConfig
	}
	
}
