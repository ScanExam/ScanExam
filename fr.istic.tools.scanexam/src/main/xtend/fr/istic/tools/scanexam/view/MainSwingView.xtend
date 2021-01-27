package fr.istic.tools.scanexam.view

import java.awt.EventQueue

/** 
 * Classe pour lancer directement la vue de la création d'examen
 * @author Julien Cochet
 */
class MainSwingView {

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * MAIN
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Launch the application.
	 */
	def static void main(String[] args) {
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
