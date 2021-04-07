package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.view.swing.AdapterSwingPdfPanel;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * Panel Swing permettant d'afficher un PDF
 */
@SuppressWarnings("all")
public class PdfPanel extends JPanel {
  /**
   * Adaptateur de ce Panel, permet de lui apporter des fonctionnalités
   */
  public AdapterSwingPdfPanel adapter;
  
  /**
   * Largeur du panel
   */
  public int width;
  
  /**
   * Hauteur du panel
   */
  public int height;
  
  /**
   * Constructeur
   * @param adapter Adaptateur de la classe
   */
  public PdfPanel(final AdapterSwingPdfPanel adapter) {
    this.adapter = adapter;
    Image _pdf = this.adapter.getPdf();
    boolean _tripleNotEquals = (_pdf != null);
    if (_tripleNotEquals) {
      int _width = this.adapter.getPdf().getWidth(this);
      int _scale = this.adapter.getScale();
      int _divide = (_width / _scale);
      this.width = _divide;
      int _height = this.adapter.getPdf().getHeight(this);
      int _scale_1 = this.adapter.getScale();
      int _divide_1 = (_height / _scale_1);
      this.height = _divide_1;
    }
    this.adapter.setView(this);
    this.addMouseWheelListener(this.adapter.getMouseHandler());
    this.addMouseListener(this.adapter.getMouseHandler());
    this.addMouseMotionListener(this.adapter.getMouseHandler());
  }
  
  /**
   * Affichage graphique
   */
  @Override
  protected void paintComponent(final Graphics g) {
    super.paintComponent(g);
    Image _pdf = this.adapter.getPdf();
    boolean _tripleNotEquals = (_pdf != null);
    if (_tripleNotEquals) {
      int _width = this.adapter.getPdf().getWidth(this);
      int _scale = this.adapter.getScale();
      int _divide = (_width / _scale);
      this.width = _divide;
      int _height = this.adapter.getPdf().getHeight(this);
      int _scale_1 = this.adapter.getScale();
      int _divide_1 = (_height / _scale_1);
      this.height = _divide_1;
      g.drawImage(this.adapter.getPdf(), this.adapter.getOriginX(), this.adapter.getOriginY(), this.width, this.height, this);
    }
  }
}
