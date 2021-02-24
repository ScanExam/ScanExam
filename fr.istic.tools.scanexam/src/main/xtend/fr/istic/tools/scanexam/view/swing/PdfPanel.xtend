package fr.istic.tools.scanexam.view.swing

import java.awt.Graphics
import javax.swing.JPanel

class PdfPanel extends JPanel {
	
	public var AdapterSwingPdfPanel adapter
	/* Largeur du panel */
	public var int width
	/* Hauteur du panel */
	public var int height
		
	new(AdapterSwingPdfPanel pdfPresenter) {
		this.adapter = pdfPresenter
		
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
	
		width = adapter.getPdf().getWidth(this) / adapter.getScale()
		height = adapter.getPdf().getHeight(this) / adapter.getScale()
	        
	    g.drawImage(adapter.getPdf(), adapter.getOriginX(), adapter.getOriginY(), width, height, this)
	}
	
}
