package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.mailing.SendMailTls.LoginResult
import fr.istic.tools.scanexam.presenter.PresenterConfiguration
import fr.istic.tools.scanexam.view.fx.utils.ShakeEffect
import java.net.URL
import java.util.ResourceBundle
import java.util.regex.Pattern
import javafx.collections.FXCollections
import javafx.concurrent.Service
import javafx.concurrent.Task
import javafx.css.PseudoClass


import javafx.scene.Cursor
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.image.Image
import javafx.scene.layout.VBox
import javafx.stage.Stage
import fr.istic.tools.scanexam.utils.ResourcesUtils
import javafx.fxml.FXML
import javafx.fxml.Initializable

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
	public VBox mainPane

	/* Sélection de la langue */
	@FXML
	public ComboBox<String> cmbBxLanguage

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
	 * Constructeur. Initialise les différents champs avec la valeur actuelle de la configuration
	 */
	override initialize(URL location, ResourceBundle resources) {
		presConfig = new PresenterConfiguration

		cmbBxLanguage.value = presConfig.language
		txtFldEmail.text = presConfig.email
		pwdFldEmailPassword.text = presConfig.emailPassword
		txtFldEmailHost.text = presConfig.mailHost
		txtFldEmailPort.text = presConfig.mailPort

		cmbBxLanguage.items = FXCollections.observableArrayList(presConfig.languages)
		txtFldEmailPort.textFormatter = new TextFormatter<String> [ change |
			Pattern.compile("\\d*").matcher(change.text).matches() ? change : null
		]
		txtFldEmailPort.focusedProperty.addListener [ obs, oldVal, newVal |
			!newVal ? txtFldEmailPort.text.equals("") ? txtFldEmailPort.text = "0"
		]
		txtFldEmail.focusedProperty.addListener[obs, oldVal, newVal|!newVal ? verifyMailAddress]

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
	 * Vérifie si l'adresse mail saisie est syntaxiquement valide. Si oui, remplie automatiquement le port et l'host si ceux-ci sont connus.
	 * Si non, bloque la validation du formulaire (jusqu'à syntaxe valide ou textfield vide).
	 */
	def void verifyMailAddress() {
		val PseudoClass errorClass = PseudoClass.getPseudoClass("wrong-format")
		if (txtFldEmail.text !== null && txtFldEmail.text != "" && !presConfig.checkEmailFormat(txtFldEmail.text)) {
			txtFldEmail.pseudoClassStateChanged(errorClass, true)
			ShakeEffect.shake(txtFldEmail)
			btnSave.disable = true
		} else {
			txtFldEmail.pseudoClassStateChanged(errorClass, false)
			btnSave.disable = false
			if (txtFldEmail.text !== null && txtFldEmail.text != "") {
				val infos = presConfig.getSmtpInfos(txtFldEmail.text)
				if (txtFldEmailHost.text === null || txtFldEmailHost.text == "")
					txtFldEmailHost.text = infos.key
				if (txtFldEmailPort.text === null || txtFldEmailPort.text == "0")
					txtFldEmailPort.text = infos.value
			}
		}
	}

	/**
	 * @param name l'adresse mail du login
	 * @param password le mot de passe du login
	 * @param host l'host SMTP
	 * @param port le port SMTP
	 * @return true si le programme a réussi à se connecter à l'adresse mail, false sinon
	 */
	def void checkMail() {
		val Task<LoginResult> task = [
			{
				mainPane.scene.setCursor(Cursor.WAIT)
				val result = presConfig.checkLogin(txtFldEmail.text, pwdFldEmailPassword.text, txtFldEmailHost.text,
					txtFldEmailPort.text)
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
	def void sendCheckMailResult(LoginResult result) {
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
