package fr.istic.tools.scanexam.view.swing

import fr.istic.tools.scanexam.box.BoxList
import fr.istic.tools.scanexam.presenter.Presenter
import java.awt.Point
import java.awt.event.MouseEvent
import java.util.Optional
import javax.swing.JPanel
import fr.istic.tools.scanexam.presenter.PresenterQuestionZone

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
	
	/* Adaptateur des boîtes de sélection */
	var AdapterSwingBox adapterBox
	
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
	 * @param presenterPdf Présenter gérant le pdf
	 * @param selectionBoxes Objet contenant les boîtes de sélection
	 */
	new(int width, int height, Presenter presenterPdf, BoxList selectionBoxes) {
		super(width, height, presenterPdf)
		this.selectionBoxes = selectionBoxes
		if(pdf !== null) {
			var int selectWidth = pdf.getWidth(null)
			var int selectHeight = pdf.getHeight(null)
			adapterBox = new AdapterSwingBox(selectWidth, selectHeight, scale, originX, originY, selectionBoxes)
		} else {
			adapterBox = new AdapterSwingBox(width, height, scale, originX, originY, selectionBoxes)
		}
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
			adapterBox.setOriginX(originX)
			adapterBox.setOriginY(originY)
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
		adapterBox.setScale(scale)
		repaint()
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def AdapterSwingBox getAdapterBox() {
		return adapterBox
	}
	
	def BoxList getSelectionBoxes() {
		return selectionBoxes
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def void setPresenterQst(PresenterQuestionZone presenterQst) {
		adapterBox.setPresenter(presenterQst)
	}
	
	override void setView(JPanel view) {
		this.view = Optional::of(view)
		adapterBox.setView(this.view.get())
	}
	
	override void setScaleOnWidth(boolean scaleOnWidth) {
		if(pdf !== null) {
			super.setScaleOnWidth(scaleOnWidth)
			adapterBox.setWindowWidth(pdf.getWidth(this.view.get()))
			adapterBox.setWindowHeight(pdf.getHeight(this.view.get()))
			incrScale(0)
		}
	}
	
}
