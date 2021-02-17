package fr.istic.tools.scanexam.controller

import fr.istic.tools.scanexam.presenter.PdfPresenter
import java.awt.Image
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.InputStream
import java.util.Optional
import javax.swing.JPanel
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer

/** 
 * Controlleur Swing du pdf avec swing
 * @author Julien Cochet
 */
class PdfPresenterSwing extends PdfPresenter {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Indique si la mise à l'échelle se base sur la largueur ou non  */
	boolean SCALE_ON_WIDTH = false
	
	/* PDF a afficher */
	var Image pdf
	
	/* Echelle pour l'affichage */
	protected var int scale
	
	/* Point d'origine sur l'axe X */
	protected var int originX
	
	/* Point d'origine sur l'axe Y */
	protected var int originY
	
	/* Handler pour les événements liés à la souris */
	var MouseAdapter mouseHandler
	
	/* Dernier point cliqué par l'utilisateur */
	var Optional<Point> lastClickPoint
	
	/* Vue */
	var Optional<JPanel> view


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
	 * @param selectionBoxes Objet contenant les boîtes de sélection
	 */
	new(int width, int height, InputStream pdfInput) {
		super(width, height, pdfInput)
		setPDF(pdfInput, 0)
		if (this.SCALE_ON_WIDTH) {
			scale = (pdf.getWidth(null) / width) + 1
		} else {
			scale = (pdf.getHeight(null) / height) + 1
		}
		originX = 0
		originY = 0
		lastClickPoint = Optional::empty()
		view = Optional::empty()
		mouseHandler = new MouseAdapter() {
			override void mousePressed(MouseEvent e) {
				// Clic molette ou droit
				if ((e.getButton() === 2) || (e.getButton() === 3)) {
					lastClickPoint = Optional::of(e.getPoint())
				}
			}

			override void mouseReleased(MouseEvent e) {
				lastClickPoint = Optional::empty()
			}

			override void mouseDragged(MouseEvent e) {
				moveOrigin(e)
			}

			override void mouseWheelMoved(MouseWheelEvent e) {
				incrScale(e.getWheelRotation())
			}
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
	def private void moveOrigin(MouseEvent e) {
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
	def private void incrScale(int value) {
		if ((scale + value) < 1) {
			scale = 1
		} else {
			scale += value
		}
		selectionController.setScale(scale)
		repaint()
	}

	/** 
	 * Actualise la vue
	 */
	def private void repaint() {
		if (view != null) {
			view.get().repaint()
		}
	}


	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def int getScale() {
		return scale
	}

	def int getOriginX() {
		return originX
	}

	def int getOriginY() {
		return originY
	}

	def MouseAdapter getMouseHandler() {
		return mouseHandler
	}

	def Image getPdf() {
		return pdf
	}

	def SelectionPresenterSwing getSelectionController() {
		return selectionController
	}


	// ----------------------------------------------------------------------------------------------------
	/** 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def void setView(JPanel view) {
		this.view = Optional::of(view)
		selectionController.setView(this.view.get())
	}
	
	def void setPDF(InputStream pdfInput, int pageindex) {
		try {
			var PDDocument document = PDDocument::load(pdfInput)
			var PDFRenderer renderer = new PDFRenderer(document)
			var BufferedImage img = renderer.renderImageWithDPI(pageindex, 300)
			document.close()
			pdf = img
			repaint()
		} catch (IOException e) {
			e.printStackTrace()
		}
	}
}
