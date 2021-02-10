package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.controller.PdfPresenterSwing;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("all")
public class PdfPanel extends JPanel {
  public PdfPresenterSwing pdfPresenter;
  
  /**
   * Largeur du panel
   */
  public int width;
  
  /**
   * Hauteur du panel
   */
  public int height;
  
  public PdfPanel(final PdfPresenterSwing pdfPresenter) {
    this.pdfPresenter = pdfPresenter;
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
    int _width = this.pdfPresenter.getPdf().getWidth(this);
    int _scale = this.pdfPresenter.getScale();
    int _divide = (_width / _scale);
    this.width = _divide;
    int _height = this.pdfPresenter.getPdf().getHeight(this);
    int _scale_1 = this.pdfPresenter.getScale();
    int _divide_1 = (_height / _scale_1);
    this.height = _divide_1;
    g.drawImage(this.pdfPresenter.getPdf(), this.pdfPresenter.getOriginX(), this.pdfPresenter.getOriginY(), this.width, this.height, this);
  }
}
