package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.box.Box
import fr.istic.tools.scanexam.box.BoxList
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Rectangle
import javax.swing.JPanel
import fr.istic.tools.scanexam.view.AdapterSwingPdfAndBoxPanel
import fr.istic.tools.scanexam.view.AdapterSwingBox

class PdfAndBoxPanel extends JPanel {
	
	public var AdapterSwingPdfAndBoxPanel adapter
	public var AdapterSwingBox selectionPresenter
	
	/* Largeur du panel */
	public var int width
	/* Hauteur du panel */
	public var int height
	
	/* Couleur de l'intérieur des boîtes */
	val Color selectionColor = new Color(255, 255, 255, 64)
	/* Couleur de l'intérieur des titres */
	val Color titleColor = new Color(191, 191, 191, 191)
	/* Couleur des contours des boîtes */
	val Color outLineColor = Color.BLACK
		
	new(AdapterSwingPdfAndBoxPanel adapter) {
		this.adapter = adapter
		
		width = this.adapter.getPdf().getWidth(this) / this.adapter.getScale()
		height = this.adapter.getPdf().getHeight(this) / this.adapter.getScale()
		this.adapter.setView(this)
        
		this.selectionPresenter = this.adapter.selectionController
		this.selectionPresenter.getSelectionBoxes().setPanel(this)
        
        addMouseWheelListener(this.adapter.getMouseHandler())
        addMouseListener(this.adapter.getMouseHandler())
        addMouseMotionListener(this.adapter.getMouseHandler())
        
        addMouseListener(selectionPresenter.getMouseHandler())
        addMouseMotionListener(selectionPresenter.getMouseHandler())
	}
	
	/**
	 * Affichage graphique
	 */
	override protected void paintComponent(Graphics g) {
		super.paintComponent(g)
	
		width = adapter.getPdf().getWidth(this) / adapter.getScale()
		height = adapter.getPdf().getHeight(this) / adapter.getScale()
	        
	    g.drawImage(adapter.getPdf(), adapter.getOriginX(), adapter.getOriginY(), width, height, this)
		
	    var Graphics2D g2d = g.create() as Graphics2D
	    paintSelectionBoxes(g2d, selectionPresenter.getSelectionBoxes())
	    paintBoxesTitle(g2d, selectionPresenter.getSelectionBoxes())
	        
	    g2d.dispose();
	}
	
	/**
	 * Dessine les boîtes de sélection
	 * @param g2d
	 * @param boxes Boîtes à dessiner
	 */
	 def void paintSelectionBoxes(Graphics2D g2d, BoxList boxes) {
	 	// Coloration de l'intérieur des boîtes
	    g2d.setColor(selectionColor);
	    var BoxList paintedBoxes = new BoxList(boxes.getMinWidth(), boxes.getMinHeight(), boxes.getMaxWidth(), boxes.getMaxHeight())
	    for (Box box : boxes.getList()) {
			paintedBoxes.addBox(box.getX() + ((adapter.getOriginX() - box.getWidth()) / width), box.getY() + ((adapter.getOriginY() - box.getHeight()) / height), box.getWidth(), box.getHeight(), box.getTitle())
		}
	    for (Box paintedBox : paintedBoxes.getList()) {
	        g2d.fill(convertBoxToRectangle(paintedBox))
		}
	        
	    // Affichage des contours des boîtes
	   	g2d.setColor(outLineColor);
	    for (Box paintedBox : paintedBoxes.getList()) {
	        g2d.draw(convertBoxToRectangle(paintedBox));
		}
	}
	    
	/**
	 * Ajoute des titres aux boîtes de sélection
	 * @param g2d
	 * @param boxes Boîtes de sélection auquelles ajouter des titres
	 */
	def void paintBoxesTitle(Graphics2D g2d, BoxList boxes) {
		// Création d'un list de boîtes de titre
	    var BoxList boxesTitle = new BoxList(selectionPresenter.getMinWidth() / width, selectionPresenter.getTitleHeight() / height, -1.0, selectionPresenter.getTitleHeight() / height)
		boxesTitle.updateBounds(selectionPresenter.getMinWidth() / width / adapter.getScale(), selectionPresenter.getMinHeight() / height, -1.0, -1.0)
		for (Box box : boxes.getList()) {
			boxesTitle.addBox(box.getX() + ((adapter.getOriginX() - box.getWidth()) / width), box.getY() + ((adapter.getOriginY() - selectionPresenter.getTitleHeight()) / height), box.getWidth(), selectionPresenter.getTitleHeight() / height, box.getTitle())
		}
	        
	    // Coloration de l'intérieur des titres
	    g2d.setColor(titleColor)
	    for (Box box : boxesTitle.getList()) {
	        g2d.fill(convertBoxToRectangle(box))
		}
	
	    // Affichage des contours des boîtes
	    g2d.setColor(outLineColor);
	    for (Box box : boxesTitle.getList()) {
	        var Rectangle crtRec = convertBoxToRectangle(box)
	        g2d.draw(crtRec)
	        g2d.drawString(box.getTitle(), crtRec.x + (selectionPresenter.getTitleHeight() / 4) as int, crtRec.y + (selectionPresenter.getTitleHeight() / 1.5) as int)
	        g2d.drawString("x", crtRec.x + crtRec.width - (selectionPresenter.getTitleHeight() / 2) as int, crtRec.y + (selectionPresenter.getTitleHeight() / 1.5) as int)
	    }
	}
	    
	/**
	 * Converti un objet de type Box en objet de type Rectangle
	 * @param box
	 * @return La boîtes en Rectangle
	 */
	def Rectangle convertBoxToRectangle(Box box) {
		var int x = (box.getX() * width) as int
		var int y = (box.getY() * height) as int
		var int width = (box.getWidth() * this.width) as int
		var int height = (box.getHeight() * this.height) as int
		return new Rectangle(x, y, width, height)
	}
}
