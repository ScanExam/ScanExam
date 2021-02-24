package fr.istic.tools.scanexam.view.swing

import fr.istic.tools.scanexam.box.BoxList
import java.awt.Point
import java.awt.event.MouseEvent
import java.io.InputStream
import java.util.Optional
import javax.swing.JPanel

/** 
 * Controlleur Swing du pdf avec swing
 * @author Julien Cochet
 */
class AdapterSwingPdfAndBoxPanel extends AdapterSwingPdfPanel {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Controlleur des boîtes de sélection */
	var AdapterSwingBox selectionController
	
	/* Objet contenant les boîtes de sélection */
	var BoxList selectionBoxes
	

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Constructeur
	 * @param width Largeur de la fenêtre
	 * @param height Hauteur de la fenêtre
	 * @param pdfInput InputStream du pdf
	 * @param selectionBoxes Objet contenant les boîtes de sélection
	 */
	new(int width, int height, InputStream pdfInput, BoxList selectionBoxes) {
		super(width, height, pdfInput)
		this.selectionBoxes = selectionBoxes
		var int selectWidth = pdf.getWidth(null)
		var int selectHeight = pdf.getHeight(null)
		selectionController = new AdapterSwingBox(selectWidth, selectHeight, scale, originX, originY, selectionBoxes)
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Déplacement de le contenu
	 * @param e Mouvement de la souris
	 */
	override void moveOrigin(MouseEvent e) {
		if (lastClickPoint.isPresent()) {
			var Point dragPoint = e.getPoint()
			originX += (dragPoint.x - lastClickPoint.get().getX()) as int
			originY += (dragPoint.y - lastClickPoint.get().getY()) as int
			selectionController.setOriginX(originX)
			selectionController.setOriginY(originY)
			repaint()
			lastClickPoint = Optional::of(dragPoint)
		}
	}

	/** 
	 * Incremente l'échelle
	 * @param value Valeur à ajouter
	 */
	override void incrScale(int value) {
		if ((scale + value) < 1) {
			scale = 1
		} else {
			scale += value
		}
		selectionController.setScale(scale)
		repaint()
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def AdapterSwingBox getSelectionController() {
		return selectionController
	}
	
	def BoxList getSelectionBoxes() {
		return selectionBoxes
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	override void setView(JPanel view) {
		this.view = Optional::of(view)
		selectionController.setView(this.view.get())
	}
	
}
