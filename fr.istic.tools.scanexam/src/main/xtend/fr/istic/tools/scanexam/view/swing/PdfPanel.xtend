package fr.istic.tools.scanexam.view.swing

import java.awt.Graphics
import javax.swing.JPanel

/**
 * Panel Swing permettant d'afficher un PDF
 */
class PdfPanel extends JPanel {
	
	// ----------------------------------------------------------------------------------------------------
	/* 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Adaptateur de ce Panel, permet de lui apporter des fonctionnalités */
	public var AdapterSwingPdfPanel adapter
	/* Largeur du panel */
	public var int width
	/* Hauteur du panel */
	public var int height
	
	// ----------------------------------------------------------------------------------------------------
	/*
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Constructeur
	 * @param adapter Adaptateur de la classe
	 */
	new(AdapterSwingPdfPanel adapter) {
		this.adapter = adapter
		
		if(this.adapter.getPdf() !== null) {
			width = this.adapter.getPdf().getWidth(this) / this.adapter.getScale()
			height = this.adapter.getPdf().getHeight(this) / this.adapter.getScale()
		}
		this.adapter.setView(this)
        
        addMouseWheelListener(this.adapter.getMouseHandler())
        addMouseListener(this.adapter.getMouseHandler())
        addMouseMotionListener(this.adapter.getMouseHandler())
	}
	
	// ----------------------------------------------------------------------------------------------------
	/* 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Affichage graphique
	 */
	override protected void paintComponent(Graphics g) {
		super.paintComponent(g)
	
		if(this.adapter.getPdf() !== null) {
			width = adapter.getPdf().getWidth(this) / adapter.getScale()
			height = adapter.getPdf().getHeight(this) / adapter.getScale()
	        
	    	g.drawImage(adapter.getPdf(), adapter.getOriginX(), adapter.getOriginY(), width, height, this)
	    }
	}
	
}
