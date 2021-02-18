package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.view.AdapterSwingPdfPanel;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("all")
public class PdfPanel extends JPanel {
  public AdapterSwingPdfPanel adapter;
  
  /**
   * Largeur du panel
   */
  public int width;
  
  /**
   * Hauteur du panel
   */
  public int height;
  
  public PdfPanel(final AdapterSwingPdfPanel pdfPresenter) {
    this.adapter = pdfPresenter;
    int _width = pdfPresenter.getPdf().getWidth(this);
    int _scale = pdfPresenter.getScale();
    int _divide = (_width / _scale);
    this.width = _divide;
    int _height = pdfPresenter.getPdf().getHeight(this);
    int _scale_1 = pdfPresenter.getScale();
    int _divide_1 = (_height / _scale_1);
    this.height = _divide_1;
    pdfPresenter.setView(this);
    this.addMouseWheelListener(pdfPresenter.getMouseHandler());
    this.addMouseListener(pdfPresenter.getMouseHandler());
    this.addMouseMotionListener(pdfPresenter.getMouseHandler());
  }
  
  /**
   * Affichage graphique
   */
  @Override
  protected void paintComponent(final Graphics g) {
    super.paintComponent(g);
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
