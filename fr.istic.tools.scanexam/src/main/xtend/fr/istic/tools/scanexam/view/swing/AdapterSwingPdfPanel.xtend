package fr.istic.tools.scanexam.view.swing

import fr.istic.tools.scanexam.presenter.Presenter
import java.awt.Image
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent
import java.util.Optional
import javax.swing.JPanel

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

	/* Presenter gerant le pdf */
	protected var Presenter presenterPdf
	
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
	 * @param presenterPdf Présenter gérant le pdf
	 */
	new(int width, int height, Presenter presenterPdf) {
		this.width = width
		this.height = height
		this.presenterPdf = presenterPdf
		refreshPdf()
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
	 * Met à jour le pdf à afficher
	 */
	def void refreshPdf() {
		if(presenterPdf !== null && presenterPdf.currentPdfPage !== null) {
			pdf = presenterPdf.currentPdfPage
			setScaleOnWidth(scaleOnWidth)
			repaint()
		}
	}

	/**
	 * Met à jour le mode de mise à l'échelle
	 * @param scaleOnWidth Booléen indiquant si l'échelle est calculé par rapport à la largueur
	 */
	def void setScaleOnWidth(boolean scaleOnWidth) {
		if(pdf !== null) {
			this.scaleOnWidth = scaleOnWidth
			if (this.scaleOnWidth) {
				scale = (pdf.getWidth(null) / width) + 1
			} else {
				scale = (pdf.getHeight(null) / height) + 1
			}
		}
	}
	
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
	
	def void setPresenterPdf(Presenter presenterPdf) {
		this.presenterPdf = presenterPdf
	}
	
	def void setView(JPanel view) {
		this.view = Optional::of(view)
	}

}
