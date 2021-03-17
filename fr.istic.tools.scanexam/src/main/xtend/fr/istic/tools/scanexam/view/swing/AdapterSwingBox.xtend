package fr.istic.tools.scanexam.view.swing

import fr.istic.tools.scanexam.presenter.SelectionStateMachine
import fr.istic.tools.scanexam.view.AdapterBox
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.Optional
import javax.swing.JPanel

/** 
 * Permet de dessiner des boîtes de sélection avec Swing
 * @author Julien Cochet
 */
class AdapterSwingBox extends AdapterBox {
	
	// ----------------------------------------------------------------------------------------------------
	/* 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/*Dernière boîte sélectionné par l'utilisateur */
	Box lastBoxSelected
	
	/* Handler pour les événements liés à la souris */
	MouseAdapter mouseHandler
	
	/* Dernier point cliqué par l'utilisateur */
	Optional<Point> lastClickPoint
	
	/* Dernier état */
	int lastState
	
	/*Vue */
	Optional<JPanel> view
	
	/* Liste des panel des questions */
	ListOfQuestionsPanel listQst

	// ----------------------------------------------------------------------------------------------------
	/*
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
	 * @param selectionBoxes Liste des boîtes desinnées
	 */
	new(int windowWidth, int windowHeight, int scale, int originX, int originY, BoxList selectionBoxes) {
		super(windowWidth, windowHeight, scale, originX, originY, selectionBoxes)
		this.lastState = SelectionStateMachine.getState()
		this.mouseHandler = new MouseAdapter() {
			override void mousePressed(MouseEvent e) {
				// Clic gauche
				if (e.getButton() === 1) {
					lastClickPoint = Optional::of(e.getPoint())
					var Optional<Box> pointedBox = checkPoint(e.getPoint())
					if (!pointedBox.isPresent()) {
						if (SelectionStateMachine.getState() === SelectionStateMachine.CREATE) {
							lastState = SelectionStateMachine.CREATE
							createBox(e)
						}
						if (SelectionStateMachine.getState() === SelectionStateMachine.RESIZE) {
							resizeBox(e, lastBoxSelected)
						}
					} else {
						lastBoxSelected = pointedBox.get()
						if (SelectionStateMachine.getState() === SelectionStateMachine.MOVE) {
							moveBox(e, lastBoxSelected)
						}
					}
				}
			}

			override void mouseReleased(MouseEvent e) {
				if (SelectionStateMachine.getState() === SelectionStateMachine.RESIZE) {
					if (lastState === SelectionStateMachine.CREATE) {
						SelectionStateMachine.setState(SelectionStateMachine.CREATE)
					} else {
						SelectionStateMachine.setState(SelectionStateMachine.RESIZE)
					}
				}
				lastState = SelectionStateMachine.getState()
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
	/* 
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
		addQstToList(lastBoxSelected)
		repaint()
		SelectionStateMachine.setState(SelectionStateMachine.RESIZE)
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
	
	/**
	 * Ajoute une question à la liste visuelle des questions
	 * @param Box Boîte lié à la question
	 */
	def void addQstToList(Box box) {
		if(listQst !== null) {
			listQst.addQst(box)
		}
	}

	// ----------------------------------------------------------------------------------------------------
	/* 
	 * GETTER
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Renvoie l'adapter de la souris
	 * @return L'adapter de la souris
	 */
	def MouseAdapter getMouseHandler() {
		return mouseHandler
	}

	// ----------------------------------------------------------------------------------------------------
	/* 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Lie la vue de l'adaptateur de création de boîte
	 * @param view Vue swing
	 */
	def void setView(JPanel view) {
		this.view = Optional::of(view)
	}
	
	/**
	 * Met à jour la largueur de la fenêtre pour l'adaptateur
	 * @param width Largueur de la fenêtre
	 */
	def void setWindowWidth(int width) {
		this.windowWidth = width
	}
	
	/**
	 * Met à jour la hauteur de la fenêtre pour l'adaptateur
	 * @param height Hauteur de la fenêtre
	 */
	def void setWindowHeight(int height) {
		this.windowHeight = height
	}
	
	/**
	 * Met à jour la référence du panel de liste des questions pour l'adaptateur
	 * @param listQst Panel de liste des questions
	 */
	def void setListQst(ListOfQuestionsPanel listQst) {
		this.listQst = listQst
	}
	
}
