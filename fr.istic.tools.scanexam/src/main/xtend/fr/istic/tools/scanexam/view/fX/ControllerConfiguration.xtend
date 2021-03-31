package fr.istic.tools.scanexam.view.fX

import fr.istic.tools.scanexam.config.ConfigurationManager
import fr.istic.tools.scanexam.core.config.Config
import java.util.Locale

/**
 * Classe pour gérer la fenêtre de configuration
 * @author Julien Cochet
 */
class ControllerConfiguration {
	
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
		config.mailPort = emailPort
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
