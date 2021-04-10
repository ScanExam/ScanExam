package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.config.ConfigurationManager
import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.core.config.Config
import java.util.Collection
import java.util.Locale
import fr.istic.tools.scanexam.mailing.SendMailTls

/**
 * Classe pour gérer la fenêtre de configuration
 * @author Julien Cochet
 */
class PresenterConfiguration {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	/* Configuration de l'application */
	val Config config

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Constructeur
	 */
	new() {
		config = ConfigurationManager.instance
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
		val Locale newLocale = LanguageManager.supportedLocales.findFirst[locale|language.equals(toDisplayName(locale))]
		if (newLocale === null)
			throw new IllegalArgumentException(newLocale + " is not supported.")
		needToRestart = newLocale != LanguageManager.currentLanguage
		config.language = newLocale
		config.email = email
		config.emailPassword = emailPassword
		config.mailHost = emailHost
		config.mailPort = Integer.parseInt(emailPort);
		ConfigurationManager.save();
		return needToRestart
	}

	def String getLanguage() {
		toDisplayName(LanguageManager.currentLanguage)
	}

	def String getEmail() {
		config.email
	}

	def String getEmailPassword() {
		config.emailPassword
	}

	def String getMailHost() {
		config.mailHost
	}

	def String getMailPort() {
		config.mailPort.toString();
	}

	def Collection<String> getLanguages() {
		LanguageManager.supportedLocales.map[local|toDisplayName(local)].toList
	}

	/**
	 * @param un locale
	 * @return le displayname du locale dans sa propre langue 
	 */
	private def String toDisplayName(Locale locale) {
		val name = locale.getDisplayName(locale)
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase()
	}
	
	/**
	 * @param name l'adresse mail du login
	 * @param password le mot de passe du login
	 * @param host l'host SMTP
	 * @param port le port SMTP
	 * @return true si le programme a réussi à se connecter à l'adresse mail, false sinon
	 */
	def SendMailTls.LoginResult checkLogin(String name, String password, String host, String port) {
		return SendMailTls.checkLogin(name === null ? "" : name, password === null ? "" : password, host === null ? "" : host, Integer.parseInt(port))
	}

	// ----------------------------------------------------------------------------------------------------
	/*
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	def Config getConfig() {
		return this.config
	}
	
	/**
	 * @param email une adresse email (non nulle)
	 * @return une paire composée de l'Host et du Port SMTP pour cette adresse mail, si ceux-ci se trouvent dans le fichier mailing/configMailFile.properties
	 */
	def getSmtpInfos(String email) {
		SendMailTls.getSmtpInformation(email)
	}

}
