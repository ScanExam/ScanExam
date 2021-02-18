package fr.istic.tools.scanexam.view

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
 * Classe pour offrir plus d'options à un PdfPanel (zoom, déplacement à la souris)
 * @author Julien Cochet
 */
class AdapterSwingPdfPanel {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Largeur de la fenêtre */
	protected var int width
	
	/* Hauteur de la fenêtre */
	protected var int height
	
	/* PDF a afficher */
	protected var Image pdf
	
	/* Echelle pour l'affichage */
	protected var int scale
	
	/* Point d'origine sur l'axe X */
	protected var int originX
	
	/* Point d'origine sur l'axe Y */
	protected var int originY
	
	/* Indique si la mise à l'échelle se base sur la largueur ou non  */
	protected var boolean scaleOnWidth
	
	/* Vue */
	protected var Optional<JPanel> view
	
	/* Dernier point cliqué par l'utilisateur */
	protected var Optional<Point> lastClickPoint
	
	/* Handler pour les événements liés à la souris */
	protected var MouseAdapter mouseHandler
	
	
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
	 */
	new(int width, int height, InputStream pdfInput) {
		this.width = width
		this.height = height
		setPDF(pdfInput, 0)
		setScaleOnWidth(false)
		originX = 0
		originY = 0
		view = Optional::empty()
		lastClickPoint = Optional::empty()
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
	def protected void moveOrigin(MouseEvent e) {
		if (lastClickPoint.isPresent()) {
			var Point dragPoint = e.getPoint()
			originX += (dragPoint.x - lastClickPoint.get().getX()) as int
			originY += (dragPoint.y - lastClickPoint.get().getY()) as int
			repaint()
			lastClickPoint = Optional::of(dragPoint)
		}
	}

	/** 
	 * Incremente l'échelle
	 * @param value Valeur à ajouter
	 */
	def protected void incrScale(int value) {
		if ((scale + value) < 1) {
			scale = 1
		} else {
			scale += value
		}
		repaint()
	}

	/** 
	 * Actualise la vue
	 */
	def protected void repaint() {
		if (view !== null) {
			view.get().repaint()
		}
	}


	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def Image getPdf() {
		return pdf
	}
	
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


	// ----------------------------------------------------------------------------------------------------
	/** 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
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
	
	def void setScaleOnWidth(boolean scaleOnWidth) {
		this.scaleOnWidth = scaleOnWidth
		if (this.scaleOnWidth) {
			scale = (pdf.getWidth(null) / width) + 1
		} else {
			scale = (pdf.getHeight(null) / height) + 1
		}
	}
	
	def void setView(JPanel view) {
		this.view = Optional::of(view)
	}
	
}
