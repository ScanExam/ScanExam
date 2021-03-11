package fr.istic.tools.scanexam.launcher

import fr.istic.tools.scanexam.presenter.PresenterBindings
import java.awt.EventQueue
import fr.istic.tools.scanexam.view.swing.GraduationAdapterSwing

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
	
	/** 
	 * Lancement de l'application Swing
	 */
	override void launch() {
		EventQueue.invokeLater([
			try {
				PresenterBindings.linkGraduationPresenter(new GraduationAdapterSwing)
			} catch (Exception e) {
				e.printStackTrace()
			}
		])
	}
	
}
