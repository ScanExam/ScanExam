package fr.istic.tools.scanexam.view.swing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * JPanel permettant d'afficher une image
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class ImagePanel extends JPanel {
  /**
   * Image affichée
   */
  private BufferedImage image;
  
  /**
   * Constructeur
   */
  public ImagePanel(final InputStream inputStream) {
    try {
      if ((inputStream != null)) {
        this.image = ImageIO.read(inputStream);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Affichage graphique
   */
  @Override
  protected void paintComponent(final Graphics g) {
    super.paintComponent(g);
    if ((this.image != null)) {
      g.drawImage(this.image, 0, 0, this);
    }
  }
}
