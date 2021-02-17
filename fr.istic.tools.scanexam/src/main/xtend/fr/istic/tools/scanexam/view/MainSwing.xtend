package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.controller.ExamCreationSwingController
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
	 * Lancement de l'application Swing ---->  launchApp(presenter(session));
	 */
	def static void launchApp() {
		EventQueue.invokeLater([
			try {
				new ExamCreationSwingController
			} catch (Exception e) {
				e.printStackTrace()
			}
		])
	}
	
}
