package fr.istic.tools.scanexam.view

import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO
import javax.swing.JPanel

/** 
 * JPanel permettant d'afficher une image
 * @author Julien Cochet
 */
class ImagePanel extends JPanel {

	/* Image affichée */
	BufferedImage image

	/**
	 * Constructeur
	 */
	new(InputStream inputStream) {
		if(inputStream !== null) {
			image = ImageIO.read(inputStream)
		}
	}

	/**
	 * Affichage graphique
	 */
	override protected void paintComponent(Graphics g) {
		super.paintComponent(g)
		if(image !== null) {
			g.drawImage(image, 0, 0, this)
		}
	}

}
