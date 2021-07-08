package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.exportation.SendMailTls.LoginResult
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
import java.util.Locale
import fr.istic.tools.scanexam.core.config.Config
import fr.istic.tools.scanexam.config.ConfigurationManager
import static extension fr.istic.tools.scanexam.utils.extensions.LocaleExtensions.*
import java.util.Collection
import fr.istic.tools.scanexam.exportation.SendMailTls
import javax.crypto.spec.SecretKeySpec

/**
 * Classe pour gérer la fenêtre de configuration en JavaFX
 * @author Julien Cochet
 */
class ControllerConfiguration {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	/** Pane principale de la vue */
	@FXML
	public VBox mainPane

	/** Sélection de la langue */
	@FXML
	public ComboBox<String> cmbBxLanguage

	/** Champ de l'email */
	@FXML
	public FormattedTextField txtFldEmail

	/** Champ du mot de passe de l'email */
	@FXML
	public PasswordField pwdFldEmailPassword

	/** Champ de l'hébergeur de l'email */
	@FXML
	public TextField txtFldEmailHost

	/** Champ du port de l'email */
	@FXML
	public TextField txtFldEmailPort

	/** Bouton de sauvegarde de la configuration */
	@FXML
	public Button btnSave

	/** Bouton pour tester le login à l'adresse mail */
	@FXML
	public Button btnCheckMail

	/** Configuration */
	val Config config = ConfigurationManager.instance

	/** Clé de crytage et ses paramètres */
	val String keyPassword = "To@dstool64"
	val byte[] salt = new String("12345678").bytes
	val int iterationCount = 40000
	val int keyLength = 128
	val SecretKeySpec key = Encryption.createSecretKey(keyPassword.toCharArray, salt, iterationCount, keyLength)

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Initialise les différents champs avec la valeur actuelle de la configuration
	 */
	def initialize() {
		cmbBxLanguage.value = LanguageManager.currentLanguage.capitalizeDisplayName
		txtFldEmail.text = config.email
		pwdFldEmailPassword.text = config.emailPassword !== "" ? Encryption.decrypt(config.emailPassword, key) : ""
		txtFldEmailHost.text = config.mailHost
		txtFldEmailPort.text = config.mailPort.toString

		cmbBxLanguage.items = FXCollections.observableArrayList(languages)

		txtFldEmail.addFormatValidator(new EmailValidator)
		txtFldEmail.focusedProperty.addListener( value, oldVal, newVal |
			!newVal && !txtFldEmail.wrongFormatted ? completeHostInfos
		)
		btnSave.disableProperty.bind(txtFldEmail.wrongFormattedProperty)

		btnCheckMail.onAction = [e|checkMail()]
	}

	@FXML
	def void saveAndQuit() {
		val needRestart = updateConfig(cmbBxLanguage.value, txtFldEmail.text, pwdFldEmailPassword.text,
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

	/**
	 * Met à jour la configuration
	 * @param language Nouvelle langue
	 * @param email Nouvel email
	 * @param emailPassword Nouveau mot de passe de l'email
	 * @param emailHost Nouvel hébergeur de l'email
	 * @param emailPort Nouveau port de l'email
	 * @return true si le programme doit être redémarré pour que l'ensemble de la configuration soit prise en compte
	 */
	def boolean updateConfig(String language, String email, String emailPassword, String emailHost, String emailPort) {
		var needToRestart = false
		val Locale newLocale = LanguageManager.supportedLocales.findFirst [ locale |
			language.equals(capitalizeDisplayName(locale))
		]
		if (newLocale === null)
			throw new IllegalArgumentException(newLocale + " is not supported.")
		needToRestart = newLocale != LanguageManager.currentLanguage
		config.language = newLocale.toString
		config.email = email
		config.emailPassword = Encryption.encrypt(emailPassword, key)
		config.mailHost = emailHost
		config.mailPort = Integer.parseInt(emailPort)
		ConfigurationManager.save
		return needToRestart
	}

	def Collection<String> getLanguages() {
		LanguageManager.supportedLocales.map[local|capitalizeDisplayName(local)].toList
	}

	/**
	 * @param name l'adresse mail du login
	 * @param password le mot de passe du login
	 * @param host l'host SMTP
	 * @param port le port SMTP
	 * @return true si le programme a réussi à se connecter à l'adresse mail, false sinon
	 */
	def SendMailTls.LoginResult checkLogin(String name, String password, String host, String port) {
		return SendMailTls.checkLogin(name === null ? "" : name, password === null ? "" : password,
			host === null ? "" : host, Integer.parseInt(port))
	}

	/**
	 * @param email une adresse email (non nulle)
	 * @return une paire composée de l'Host et du Port SMTP pour cette adresse mail, si ceux-ci se trouvent dans le fichier mailing/configMailFile.properties
	 */
	def getSmtpInfos(String email) {
		SendMailTls.getSmtpInformation(email)
	}

	@FXML
	def void quit() {
		val Stage stage = mainPane.scene.window as Stage
		stage.close
	}

	// ----------------------------------------------------------------------------------------------------
	/*
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Complète les informations de l'hôte à partir de l'adresse mail saisie par l'utilisateur, à condition que l'hôte soit connu
	 */
	private def void completeHostInfos() {
		if (txtFldEmail.text != "") {
			val infos = getSmtpInfos(txtFldEmail.text)
			txtFldEmailHost.text = infos.key
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
				val result = checkLogin(txtFldEmail.text, pwdFldEmailPassword.text, txtFldEmailHost.text,
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
