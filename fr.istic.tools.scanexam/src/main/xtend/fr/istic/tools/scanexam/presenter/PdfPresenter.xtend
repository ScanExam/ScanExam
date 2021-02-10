package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.box.BoxList
import java.io.InputStream

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
	 * InputStream du pdf 
	 */
	protected InputStream pdfInput
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
	new(int width, int height, InputStream pdfInput, BoxList selectionBoxes) {
		this.width = width
		this.height = height
		this.pdfInput = pdfInput
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
