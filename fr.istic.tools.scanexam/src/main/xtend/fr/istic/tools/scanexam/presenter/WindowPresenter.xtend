package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.box.BoxList

/** 
 * Créé une fenêtre avec Swing
 * @author Julien Cochet
 */
abstract class WindowPresenter {
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Nom de la fenêtre 
	 */
	protected String name
	/** 
	 * Largeur de la fenêtre 
	 */
	protected int width
	/** 
	 * Hauteur de la fenêtre 
	 */
	protected int height
	/** 
	 * Objet contenant les boîtes de sélection 
	 */
	protected BoxList selectionBoxes

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Constructeur
	 */
	new(String name, int width, int height) {
		this.name = name
		this.width = width
		this.height = height
		selectionBoxes = new BoxList()
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def void loadBoxes() {
		selectionBoxes.load("export.txt")
	}

	def void saveBoxes() {
		selectionBoxes.save()
	}

	def void clearBoxes() {
		selectionBoxes.clearList()
	}

	def void exportToXMI() {
		XMIExporter::exportToXMI(selectionBoxes)
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def String getName() {
		return name
	}

	def int getWidth() {
		return width
	}

	def int getHeight() {
		return height
	}

	def BoxList getSelectionBoxes() {
		return selectionBoxes
	}
}
