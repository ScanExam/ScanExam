package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.presenter.PresenterQuestionZone
import fr.istic.tools.scanexam.view.swing.BoxList
import fr.istic.tools.scanexam.view.swing.Box

/** 
 * Permet de dessiner des boîtes de sélection
 * @author Julien Cochet
 */
abstract class AdapterBox {
	
	// ----------------------------------------------------------------------------------------------------
	/* 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Présenter gérant l'édition de l'examen
	 */
	var PresenterQuestionZone presenterQst
	
	/** 
	 * Largeur de la fenêtre 
	 */
	protected int windowWidth
	/** 
	 * Hauteur de la fenêtre 
	 */
	protected int windowHeight
	/** 
	 * Echelle pour l'affichage 
	 */
	protected int scale
	/** 
	 * Point d'origine sur l'axe X 
	 */
	protected int originX
	/** 
	 * Point d'origine sur l'axe Y 
	 */
	protected int originY
	/** 
	 * Boîtes de sélection 
	 */
	protected BoxList selectionBoxes
	/** 
	 * Largueur minimum des boîtes 
	 */
	protected final int minWidth = 64
	/** 
	 * Hauteur minimum des boîtes 
	 */
	protected final int minHeight = 32
	/** 
	 * Hauteur des boîtes des titres 
	 */
	protected final int titleHeight = 32

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
	 * @param selectionBoxes Boîtes de sélection
	 */
	new(int windowWidth, int windowHeight, int scale, int originX, int originY, BoxList selectionBoxes) {
		this.windowWidth = windowWidth
		this.windowHeight = windowHeight
		this.selectionBoxes = selectionBoxes
		this.scale = scale
		this.originX = originX
		this.originY = originY
		var double minWidth = ((this.minWidth as double) / this.windowWidth) * this.scale
		var double minHeight = ((this.minHeight as double) / this.windowHeight) * this.scale
		this.selectionBoxes.updateBounds(minWidth, minHeight, -1.0, -1.0)
	}

	// ----------------------------------------------------------------------------------------------------
	/* 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Ajoute une boîte à l'interface graphique
	 * @param x Position x
	 * @param y Position y
	 * @param title Nom de la boîte
	 */
	def protected void createBox(double x, double y) {
		selectionBoxes.addBox(x, y, "Qst " + selectionBoxes.size().toString)
		val Box createdBox = selectionBoxes.getBox(selectionBoxes.size() - 1)
		val int id = presenterQst.createQuestion(x, y, minHeight, minWidth)
		createdBox.setId(id)
		createdBox.setNbPage(presenterQst.getPresenterVueCreation().getPresenterPdf.currentPdfPageNumber)
	}

	/** 
	 * Redimensionne une boîte
	 * @param box Boîte à redimensionner
	 * @param x Nouvelle position x
	 * @param y Nouvelle position y
	 * @param width Nouvelle largueur
	 * @param height Nouvelle hauteur
	 */
	def protected void resizeBox(Box box, double x, double y, double width, double height) {
		selectionBoxes.updateBox(box, x, y, width, height)
		presenterQst.resizeQuestion(box.id, height, width)
	}

	/** 
	 * Déplace une boîte
	 * @param box Boîte à déplacer
	 * @param x Nouvelle position x
	 * @param y Nouvelle position y
	 */
	def protected void moveBox(Box box, double x, double y) {
		box.updateCoordinates(x, y)
		presenterQst.moveQuestion(box.id, x as float, y as float)
	}

	/** 
	 * Supprime une boîte de l'interface graphique
	 * @param box Boîte à surprimer
	 */
	def protected void deleteBox(Box box) {
		selectionBoxes.removeBox(box)
		presenterQst.removeQuestion(box.id)
	}


	// ----------------------------------------------------------------------------------------------------
	/*
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def double getMinWidth() {
		return this.minWidth
	}

	def double getMinHeight() {
		return this.minHeight
	}

	def double getTitleHeight() {
		return this.titleHeight
	}

	def BoxList getSelectionBoxes() {
		return selectionBoxes
	}


	// ----------------------------------------------------------------------------------------------------
	/* 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	def void setPresenter(PresenterQuestionZone presenter) {
		this.presenterQst = presenter
	}
	
	def void setScale(int scale) {
		this.scale = scale
	}

	def void setOriginX(int originX) {
		this.originX = originX
	}

	def void setOriginY(int originY) {
		this.originY = originY
	}
	
}
