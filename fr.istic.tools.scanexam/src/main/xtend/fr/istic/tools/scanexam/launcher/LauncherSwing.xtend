package fr.istic.tools.scanexam.launcher

import java.awt.EventQueue
import fr.istic.tools.scanexam.view.EditorAdapterSwing

/** 
 * Classe pour lancer directement la vue en utilisant la librairie Swing
 * @author Julien Cochet
 */
class LauncherSwing implements Launcher {

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	override void setup() {
		
	}
	
	/** 
	 * Lancement de l'application Swing
	 */
	override void launch() {
		EventQueue.invokeLater([
			try {
				new EditorAdapterSwing
			} catch (Exception e) {
				e.printStackTrace()
			}
		])
	}
	
}
