package fr.istic.tools.scanexam.controller

import fr.istic.tools.scanexam.presenter.WindowPresenter

/** 
 * Créé une fenêtre avec Swing
 * @author Julien Cochet
 */
class WindowPresenterSwing extends WindowPresenter {
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Constructeur
	 */
	new(String name, int width, int height) {
		super(name, width, height)
		// View de la fenêtre
		var WindowViewSwing windowView = new WindowViewSwing(this)
		// Controlleur du PDF
		var PdfPresenterSwing pdfPresenteur = new PdfPresenterSwing(width, height, "pfo_example.pdf", selectionBoxes)
		// View du PDF
		var PdfViewSwing pdfView = new PdfViewSwing(pdfPresenteur)
		// Ajout du pdf dans la fenêtre
		windowView.add(pdfView)
		// Affichage de la fenêtre
		windowView.setVisible(true)
	}
}
