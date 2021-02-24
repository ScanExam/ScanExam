package fr.istic.tools.scanexam.launcher

import java.awt.EventQueue
import fr.istic.tools.scanexam.view.EditorAdapterSwing
import fr.istic.tools.scanexam.presenter.PresenterBindings

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
