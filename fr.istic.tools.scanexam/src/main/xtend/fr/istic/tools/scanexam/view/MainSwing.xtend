package fr.istic.tools.scanexam.view

import java.awt.EventQueue

/** 
 * Classe pour lancer directement la vue en utilisant la librairie Swing
 * @author Julien Cochet
 */
class MainSwing {

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Lancement de l'application Swing
	 */
	def static void launchApp() {
		EventQueue.invokeLater([
			try {
				var SwingView window = new SwingView()
				window.getWindow().setVisible(true)
			} catch (Exception e) {
				e.printStackTrace()
			}
		])
	}
	
}
