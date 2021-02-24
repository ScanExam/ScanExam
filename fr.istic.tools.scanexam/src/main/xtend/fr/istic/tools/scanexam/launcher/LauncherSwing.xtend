package fr.istic.tools.scanexam.launcher

import fr.istic.tools.scanexam.presenter.PresenterBindings
import fr.istic.tools.scanexam.view.swing.EditorAdapterSwing
import java.awt.EventQueue

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
				PresenterBindings.linkEditorPresenter(new EditorAdapterSwing)
			} catch (Exception e) {
				e.printStackTrace()
			}
		])
	}
	
}
