package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.controller.PdfPresenterSwing
import java.awt.Graphics
import javax.swing.JPanel

class PdfPanel extends JPanel {
	
	public var PdfPresenterSwing pdfPresenter
	/* Largeur du panel */
	public var int width
	/* Hauteur du panel */
	public var int height
		
	new(PdfPresenterSwing pdfPresenter) {
		this.pdfPresenter = pdfPresenter
		
		width = pdfPresenter.getPdf().getWidth(this) / pdfPresenter.getScale()
		height = pdfPresenter.getPdf().getHeight(this) / pdfPresenter.getScale()
		pdfPresenter.setView(this)
        
        addMouseWheelListener(pdfPresenter.getMouseHandler())
        addMouseListener(pdfPresenter.getMouseHandler())
        addMouseMotionListener(pdfPresenter.getMouseHandler())
	}
	
	/**
	 * Affichage graphique
	 */
	override protected void paintComponent(Graphics g) {
		super.paintComponent(g)
	
		width = pdfPresenter.getPdf().getWidth(this) / pdfPresenter.getScale()
		height = pdfPresenter.getPdf().getHeight(this) / pdfPresenter.getScale()
	        
	    g.drawImage(pdfPresenter.getPdf(), pdfPresenter.getOriginX(), pdfPresenter.getOriginY(), width, height, this)
	}
	
}
