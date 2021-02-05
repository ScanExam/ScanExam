package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.box.BoxList
import fr.istic.tools.scanexam.controller.PdfPresenterSwing
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
				var PdfPresenterSwing pdfPresenteur = new PdfPresenterSwing(1280, 720, "pfo_example.pdf", new BoxList())
				var SwingView window = new SwingView(pdfPresenteur)
				window.getWindow().setVisible(true)
			} catch (Exception e) {
				e.printStackTrace()
			}
		])
	}
	
}
