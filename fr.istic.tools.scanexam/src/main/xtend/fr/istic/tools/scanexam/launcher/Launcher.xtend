package fr.istic.tools.scanexam.launcher

/** 
 * Interface de lancement de vue 
 * @author Julien Cochet
 */
interface Launcher {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Créé l'adapter et initialise les attributs du presenter
	 */
	def void setup()
	
	/** 
	 * Lance l'application
	 */
	def void launch()

}