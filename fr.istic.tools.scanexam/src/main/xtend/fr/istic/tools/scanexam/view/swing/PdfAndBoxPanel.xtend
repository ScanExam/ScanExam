package fr.istic.tools.scanexam.view.swing

import fr.istic.tools.scanexam.box.Box
import fr.istic.tools.scanexam.box.BoxList
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Rectangle

class PdfAndBoxPanel extends PdfPanel {
	
	/* Adaptateur swing du gestionnaire de boîtes de sélection */
	public var AdapterSwingBox adapterBox
	/* Couleur de l'intérieur des boîtes */
	val Color selectionColor = new Color(255, 255, 255, 64)
	/* Couleur de l'intérieur des titres */
	val Color titleColor = new Color(191, 191, 191, 191)
	/* Couleur des contours des boîtes */
	val Color outLineColor = Color.BLACK
		
	new(AdapterSwingPdfAndBoxPanel adapter) {
		super(adapter)
		
		this.adapter.setView(this)
        
		this.adapterBox = adapter.getAdapterBox()
		this.adapterBox.getSelectionBoxes().setPanel(this)
        
        addMouseListener(this.adapterBox.getMouseHandler())
        addMouseMotionListener(this.adapterBox.getMouseHandler())
	}
	
	/**
	 * Affichage graphique
	 */
	override protected void paintComponent(Graphics g) {
		super.paintComponent(g)
	
	    var Graphics2D g2d = g.create() as Graphics2D
	    paintSelectionBoxes(g2d, adapterBox.getSelectionBoxes())
	    paintBoxesTitle(g2d, adapterBox.getSelectionBoxes())
	        
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
	    var BoxList boxesTitle = new BoxList(adapterBox.getMinWidth() / width, adapterBox.getTitleHeight() / height, -1.0, adapterBox.getTitleHeight() / height)
		boxesTitle.updateBounds(adapterBox.getMinWidth() / width / adapter.getScale(), adapterBox.getMinHeight() / height, -1.0, -1.0)
		for (Box box : boxes.getList()) {
			boxesTitle.addBox(box.getX() + ((adapter.getOriginX() - box.getWidth()) / width), box.getY() + ((adapter.getOriginY() - adapterBox.getTitleHeight()) / height), box.getWidth(), adapterBox.getTitleHeight() / height, box.getTitle())
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
	        g2d.drawString(box.getTitle(), crtRec.x + (adapterBox.getTitleHeight() / 4) as int, crtRec.y + (adapterBox.getTitleHeight() / 1.5) as int)
	        g2d.drawString("x", crtRec.x + crtRec.width - (adapterBox.getTitleHeight() / 2) as int, crtRec.y + (adapterBox.getTitleHeight() / 1.5) as int)
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
