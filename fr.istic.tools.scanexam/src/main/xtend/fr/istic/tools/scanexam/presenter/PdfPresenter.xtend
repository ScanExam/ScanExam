package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.box.BoxList

/** 
 * Controlleur du pdf
 * @author Julien Cochet
 */
abstract class PdfPresenter {
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Largeur de la fenêtre 
	 */
	protected int width
	/** 
	 * Hauteur de la fenêtre 
	 */
	protected int height
	/** 
	 * Chemin vers le pdf 
	 */
	protected String pdfPath
	/** 
	 * Objet contenant les boîtes de sélection 
	 */
	BoxList selectionBoxes

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Constructeur
	 * @param width Largeur de la fenêtre
	 * @param height Hauteur de la fenêtre
	 * @param pdfPath Chemin vers le pdf
	 */
	new(int width, int height, String pdfPath, BoxList selectionBoxes) {
		this.width = width
		this.height = height
		this.pdfPath = pdfPath
		this.selectionBoxes = selectionBoxes
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def BoxList getSelectionBoxes() {
		return selectionBoxes
	}
}
