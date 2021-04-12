package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.mailing.SendMailTls.LoginResult
import fr.istic.tools.scanexam.presenter.PresenterConfiguration
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField
import fr.istic.tools.scanexam.view.fx.component.validator.EmailValidator
import javafx.collections.FXCollections
import javafx.concurrent.Service
import javafx.concurrent.Task
import javafx.fxml.FXML
import javafx.scene.Cursor
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.layout.VBox
import javafx.stage.Stage

/**
 * Classe pour gérer la fenêtre de configuration en JavaFX
 * @author Julien Cochet
 */
class AdapterFxConfiguration {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	/* Controlleur de la configuration */
	var PresenterConfiguration presConfig

	/* Pane principale de la vue */
	@FXML
	public VBox mainPane

	/* Sélection de la langue */
	@FXML
	public ComboBox<String> cmbBxLanguage

	/* Champ de l'email */
	@FXML
	public FormattedTextField txtFldEmail

	/* Champ du mot de passe de l'email */
	@FXML
	public PasswordField pwdFldEmailPassword

	/* Champ de l'hébergeur de l'email */
	@FXML
	public TextField txtFldEmailHost

	/* Champ du port de l'email */
	@FXML
	public TextField txtFldEmailPort

	/* Bouton de sauvegarde de la configuration */
	@FXML
	public Button btnSave

	/* Bouton pour tester le login à l'adresse mail */
	@FXML
	public Button btnCheckMail

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Initialise les différents champs avec la valeur actuelle de la configuration
	 */
	def initialize(PresenterConfiguration presConfig) {
		this.presConfig = presConfig

		cmbBxLanguage.value = presConfig.language
		txtFldEmail.text = presConfig.email
		pwdFldEmailPassword.text = presConfig.emailPassword
		txtFldEmailHost.text = presConfig.mailHost
		txtFldEmailPort.text = presConfig.mailPort

		cmbBxLanguage.items = FXCollections.observableArrayList(presConfig.languages)

		txtFldEmail.addFormatValidator(new EmailValidator)
		txtFldEmail.focusedProperty.addListener( value, oldVal, newVal | !newVal && !txtFldEmail.wrongFormatted ? completeHostInfos)
		btnSave.disableProperty.bind(txtFldEmail.wrongFormattedProperty)

		btnCheckMail.onAction = [e|checkMail()]
	}

	@FXML
	def void saveAndQuit() {
		val needRestart = presConfig.updateConfig(cmbBxLanguage.value, txtFldEmail.text, pwdFldEmailPassword.text,
			txtFldEmailHost.text, txtFldEmailPort.text)
		if (needRestart) {
			val alert = new Alert(AlertType.INFORMATION)
			val stage = alert.getDialogPane().getScene().getWindow() as Stage
			stage.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")))
			alert.alertType = AlertType.CONFIRMATION
			alert.setTitle(LanguageManager.translate("config.restartDialog.title"))
			alert.setHeaderText(LanguageManager.translate("config.restartDialog.text"))
			alert.showAndWait
		}
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

	/** 
	 * Complète les informations de l'hôte à partir de l'adresse mail saisie par l'utilisateur, à condition que l'hôte soit connu
	 */
	private def void completeHostInfos() {
		if (txtFldEmail.text != "") {
			val infos = presConfig.getSmtpInfos(txtFldEmail.text)
			if (txtFldEmailHost.text === null || txtFldEmailHost.text == "")
				txtFldEmailHost.text = infos.key
			if (txtFldEmailPort.text === null || txtFldEmailPort.text == "0")
				txtFldEmailPort.text = infos.value
		}
	}

	/**
	 * @param name l'adresse mail du login
	 * @param password le mot de passe du login
	 * @param host l'host SMTP
	 * @param port le port SMTP
	 * @return true si le programme a réussi à se connecter à l'adresse mail, false sinon
	 */
	private def void checkMail() {
		val Task<LoginResult> task = [
			{
				mainPane.scene.setCursor(Cursor.WAIT)
				mainPane.disable = true
				val result = presConfig.checkLogin(txtFldEmail.text, pwdFldEmailPassword.text, txtFldEmailHost.text,
					txtFldEmailPort.text)
				mainPane.disable = false
				mainPane.scene.setCursor(Cursor.DEFAULT)
				result
			}
		]
		val Service<LoginResult> service = [task]
		service.onSucceeded = [e|sendCheckMailResult(service.value)]
		service.start
	}

	/**
	 * Affiche une boîte de dialogue prévenant l'utilisateur de la réussite ou non de la connexion à son adresse mail
	 * @param result un LoginResult
	 */
	private def void sendCheckMailResult(LoginResult result) {
		val alert = new Alert(AlertType.NONE)
		val stage = alert.getDialogPane().getScene().getWindow() as Stage
		stage.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")))
		if (result == LoginResult.SUCCESS) {
			alert.alertType = AlertType.CONFIRMATION
			alert.setTitle(LanguageManager.translate("config.emailConfirmationDialog.title"))
			alert.setHeaderText(LanguageManager.translate("config.emailConfirmationDialog.success"))
		} else {
			alert.alertType = AlertType.ERROR
			alert.setTitle(LanguageManager.translate("config.emailConfirmationDialog.title"))
			if (result == LoginResult.HOST_NOT_FOUND)
				alert.setHeaderText(LanguageManager.translate("config.emailConfirmationDialog.hostNotFound"))
			else
				alert.setHeaderText(LanguageManager.translate("config.emailConfirmationDialog.badLogin"))
		}
		alert.showAndWait
	}
}
