package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.box.BoxList
import fr.istic.tools.scanexam.controller.PdfAndBoxPresenterSwing
import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.awt.EventQueue
import java.io.InputStream

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
				var InputStream pdfInput = ResourcesUtils.getInputStreamResource("/viewResources/pfo_example.pdf")
				var PdfAndBoxPresenterSwing pdfPresenteur = new PdfAndBoxPresenterSwing(1280, 720, pdfInput, new BoxList())
				var ExamCreationSwingView window = new ExamCreationSwingView(pdfPresenteur)
				window.getWindow().setVisible(true)
			} catch (Exception e) {
				e.printStackTrace()
			}
		])
	}
	
}
