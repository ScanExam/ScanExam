package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.config.ConfigurationManager
import fr.istic.tools.scanexam.core.config.Config
import java.util.Locale
import java.util.Collection
import fr.istic.tools.scanexam.config.LanguageManager

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
	 */
	def void updateConfig(Locale language, String email, String emailPassword, String emailHost, String emailPort) {
		config.language = language
		config.email = email
		config.emailPassword = emailPassword
		config.mailHost = emailHost
		config.mailPort = Integer.parseInt(emailPort); // FIXME verify emailPort is integer
		
		ConfigurationManager.save();
	}
	
	def Locale getLanguage()
	{
		config.language
	}
	def String getEmail()
	{
		config.email
	}
	def String getEmailPassword()
	{
		config.emailPassword
	}
	def String getMailHost()
	{
		config.mailHost
	}
	def String getMailPort()
	{
		config.mailPort.toString();
	}
	def Collection<Locale> getLanguages()
	{
		LanguageManager.supportedLocales
	}
	
	// ----------------------------------------------------------------------------------------------------
	/*
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def Config getConfig() {
		return this.config
	}
	
}
