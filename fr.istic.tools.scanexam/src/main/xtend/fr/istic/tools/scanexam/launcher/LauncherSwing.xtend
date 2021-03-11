package fr.istic.tools.scanexam.launcher

import fr.istic.tools.scanexam.presenter.PresenterBindings
import java.awt.EventQueue
import fr.istic.tools.scanexam.view.swing.GraduationAdapterSwing
import fr.istic.tools.scanexam.view.swing.EditorAdapterSwing

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
	static EditorAdapterSwing edit= new EditorAdapterSwing
	static GraduationAdapterSwing grad
	
	/** 
	 * Lancement de l'application Swing
	 */
	override void launch() {
		EventQueue.invokeLater([
			try {
				PresenterBindings.linkEditorPresenter(edit)
				//PresenterBindings.linkGraduationPresenter(new GraduationAdapterSwing)
			} catch (Exception e) {
				e.printStackTrace()
			}
		])
	}
	
	static def swapToEditor(){
				PresenterBindings.linkEditorPresenter(edit)
		
	}
	
	static def  swapToGraduator(){
		grad = new GraduationAdapterSwing
		PresenterBindings.linkGraduationPresenter(grad)
		
	}
	
	
}
