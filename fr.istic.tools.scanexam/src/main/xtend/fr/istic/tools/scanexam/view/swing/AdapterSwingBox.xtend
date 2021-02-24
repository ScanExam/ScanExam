package fr.istic.tools.scanexam.view.swing

import fr.istic.tools.scanexam.box.Box
import fr.istic.tools.scanexam.box.BoxList
import fr.istic.tools.scanexam.presenter.SelectionStateMachine
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.Optional
import javax.swing.JPanel
import fr.istic.tools.scanexam.presenter.PresenterBox

/** 
 * Permet de dessiner des boîtes de sélection avec Swing
 * @author Julien Cochet
 */
class AdapterSwingBox extends PresenterBox {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Dernière boîte sélectionné par l'utilisateur 
	 */
	Box lastBoxSelected
	/** 
	 * Indique si la croix d'une boîte à été cliquée 
	 */
	boolean crossCliked
	/** 
	 * Handler pour les événements liés à la souris 
	 */
	MouseAdapter mouseHandler
	/** 
	 * Dernier point cliqué par l'utilisateur 
	 */
	Optional<Point> lastClickPoint
	/** 
	 * Vue 
	 */
	Optional<JPanel> view

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Constructeur
	 * @param windowWidth Largeur de la fenêtre
	 * @param windowHeight Hauteur de la fenêtre
	 * @param scale Echelle pour l'affichage
	 * @param originX Point d'origine sur l'axe X
	 * @param originY Point d'origine sur l'axe Y
	 * @param selectionBoxes Boîtes de sélection
	 */
	new(int windowWidth, int windowHeight, int scale, int originX, int originY, BoxList selectionBoxes) {
		super(windowWidth, windowHeight, scale, originX, originY, selectionBoxes)
		this.mouseHandler = new MouseAdapter() {
			override void mousePressed(MouseEvent e) {
				// Clic gauche
				if (e.getButton() === 1) {
					crossCliked = false
					lastClickPoint = Optional::of(e.getPoint())
					var Optional<Box> pointedBox = checkPoint(e.getPoint())
					if (!pointedBox.isPresent()) {
						SelectionStateMachine.setState(SelectionStateMachine.CREATE)
						createBox(e)
						SelectionStateMachine.setState(SelectionStateMachine.RESIZE)
						resizeBox(e, lastBoxSelected)
					} else {
						lastBoxSelected = pointedBox.get()
						if (!crossCliked) {
							SelectionStateMachine.setState(SelectionStateMachine.MOVE)
							moveBox(e, lastBoxSelected)
						} else {
							SelectionStateMachine.setState(SelectionStateMachine.DELETE)
							deleteBox(lastBoxSelected)
						}
					}
				}
			}

			override void mouseReleased(MouseEvent e) {
				SelectionStateMachine.setState(SelectionStateMachine.IDLE)
				lastClickPoint = Optional::empty()
			}

			override void mouseDragged(MouseEvent e) {
				if (SelectionStateMachine.getState() === SelectionStateMachine.RESIZE) {
					resizeBox(e, lastBoxSelected)
				}
				if (SelectionStateMachine.getState() === SelectionStateMachine.MOVE) {
					moveBox(e, lastBoxSelected)
				}
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Retourne la boîte se trouvant au point indiqué
	 * @param point endroit à observer
	 * @return la boîte
	 */
	def private Optional<Box> checkPoint(Point point) {
		var double x = ((point.getX() - originX) / windowWidth) * scale
		var double y = ((point.getY() - originY) / windowHeight) * scale
		for (Box box : selectionBoxes.getList()) {
			// On regarde pour chaque boîtes si le point est dans le titre de la boîte
			if ((x >= box.getX() && x <= (box.getX() + box.getWidth())) &&
				(y >= (box.getY() - ((titleHeight as double) / windowHeight * scale)) && y <= (box.getY()))) {
				// Si le point est très à droite, on considère la croix de la boîte cliquée
				if (x >= (box.getX() + box.getWidth() - ((titleHeight as double) / windowWidth * scale))) {
					crossCliked = true
				} else {
					crossCliked = false
				}
				return Optional::of(box)
			}
		}
		return Optional::empty()
	}

	/** 
	 * Ajoute une nouvelle boîte
	 * @param e Event
	 */
	def private void createBox(MouseEvent e) {
		var double x = ((lastClickPoint.get().getX() - originX) / windowWidth) * scale
		var double y = ((lastClickPoint.get().getY() - originY) / windowHeight) * scale
		createBox(x, y)
		lastBoxSelected = selectionBoxes.getBox(selectionBoxes.size() - 1)
		repaint()
	}

	/** 
	 * Change la taille d'une boîte de ces coordonnées à l'endroit pointé par la souris
	 * @param e Event
	 * @param box Boîte à modifer
	 */
	def private void resizeBox(MouseEvent e, Box box) {
		var Point dragPoint = e.getPoint()
		var double x = (Math::min(lastClickPoint.get().getX(), dragPoint.x) - originX) / (windowWidth / scale)
		var double y = (Math::min(lastClickPoint.get().getY(), dragPoint.y) - originY) / (windowHeight / scale)
		var double width = Math::max(lastClickPoint.get().getX() - dragPoint.x,
			dragPoint.x - lastClickPoint.get().getX()) / (windowWidth / scale)
		var double height = Math::max(lastClickPoint.get().getY() - dragPoint.y,
			dragPoint.y - lastClickPoint.get().getY()) / (windowHeight / scale)
		resizeBox(box, x, y, width, height)
		repaint()
	}

	/** 
	 * Change la position d'une boîte à l'endroit pointé par la souris
	 * @param e Event
	 * @param box Boîte à bouger
	 */
	def private void moveBox(MouseEvent e, Box box) {
		var Point dragPoint = e.getPoint()
		var double initialX = box.getX() - originX
		var double initialY = box.getY() - originY
		var double dragX = (dragPoint.x - lastClickPoint.get().getX()) / ((windowWidth - originX) / scale)
		var double dragY = (dragPoint.y - lastClickPoint.get().getY()) / ((windowHeight - originY) / scale)
		moveBox(box, initialX + dragX, initialY + dragY)
		repaint()
		lastClickPoint = Optional::of(e.getPoint())
	}

	/** 
	 * Supprime une boîte
	 * @param box Boîte à retirer
	 */
	override protected void deleteBox(Box box) {
		selectionBoxes.removeBox(box)
		repaint()
	}

	/** 
	 * Actualise la vue
	 */
	def private void repaint() {
		if (view.isPresent()) {
			view.get().repaint()
		}
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	def MouseAdapter getMouseHandler() {
		return mouseHandler
	}

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def void setView(JPanel view) {
		this.view = Optional::of(view)
	}
}
