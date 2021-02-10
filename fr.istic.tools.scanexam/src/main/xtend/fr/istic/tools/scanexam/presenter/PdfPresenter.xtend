package fr.istic.tools.scanexam.presenter

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
	new(int width, int height, InputStream pdfInput) {
		this.width = width
		this.height = height
		this.pdfInput = pdfInput
	}
	
}
