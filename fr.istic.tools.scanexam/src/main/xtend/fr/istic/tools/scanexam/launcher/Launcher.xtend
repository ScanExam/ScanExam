package fr.istic.tools.scanexam.launcher

import fr.istic.tools.scanexam.view.Adapter

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
	 * Lancement de l'application
	 */
	def Adapter launchApp()

}
