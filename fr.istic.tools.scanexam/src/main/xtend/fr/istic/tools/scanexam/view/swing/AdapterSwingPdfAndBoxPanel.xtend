package fr.istic.tools.scanexam.view.swing

import fr.istic.tools.scanexam.presenter.Presenter
import fr.istic.tools.scanexam.presenter.PresenterQuestionZone
import java.awt.Point
import java.awt.event.MouseEvent
import java.util.Optional
import javax.swing.JPanel

/** 
 * Controlleur Swing du pdf et de l'ajout de boîtes avec swing
 * @author Julien Cochet
 */
class AdapterSwingPdfAndBoxPanel extends AdapterSwingPdfPanel {
	
	// ----------------------------------------------------------------------------------------------------
	/* 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Adaptateur des boîtes de sélection */
	var AdapterSwingBox adapterBox
	
	/* Objet contenant les boîtes de sélection */
	var BoxList selectionBoxes
	

	// ----------------------------------------------------------------------------------------------------
	/*
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
	/* 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Déplacement du pdf
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
	/* 
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Renvoie l'daptateur des boîtes de sélection 
	 * @return L'daptateur des boîtes de sélection 
	 */
	def AdapterSwingBox getAdapterBox() {
		return adapterBox
	}
	
	/**
	 * Renvoie la liste des boîtes de sélection
	 * @return La liste des boîtes de sélection
	 */
	def BoxList getSelectionBoxes() {
		return selectionBoxes
	}

	// ----------------------------------------------------------------------------------------------------
	/* 
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Met à jour le présenter de création de zone de question
	 * @param presenterQst Présenter de création de zone de question
	 */
	def void setPresenterQst(PresenterQuestionZone presenterQst) {
		adapterBox.setPresenter(presenterQst)
	}
	
	/**
	 * Lie la vue de l'adaptateur de création de boîte
	 * @param view Vue swing
	 */
	override void setView(JPanel view) {
		this.view = Optional::of(view)
		adapterBox.setView(this.view.get())
	}
	
	/**
	 * Change la mise à l'échelle. Soit par rapport à la hauteur ou la largueur
	 * @param scaleOnWidth Indique si la mise à l'échelle se fait par rapport à la hauteur ou la largueur
	 */
	override void setScaleOnWidth(boolean scaleOnWidth) {
		if(pdf !== null) {
			super.setScaleOnWidth(scaleOnWidth)
			adapterBox.setWindowWidth(pdf.getWidth(this.view.get()))
			adapterBox.setWindowHeight(pdf.getHeight(this.view.get()))
			incrScale(0)
		}
	}
	
}
