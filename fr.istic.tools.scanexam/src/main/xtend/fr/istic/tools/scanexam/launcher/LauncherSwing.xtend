package fr.istic.tools.scanexam.launcher

import java.awt.EventQueue
import fr.istic.tools.scanexam.view.EditorAdapterSwing

/** 
 * Classe pour lancer directement la vue en utilisant la librairie Swing
 * @author Julien Cochet
 */
class LauncherSwing {

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
				new EditorAdapterSwing
			} catch (Exception e) {
				e.printStackTrace()
			}
		])
	}
	
}
