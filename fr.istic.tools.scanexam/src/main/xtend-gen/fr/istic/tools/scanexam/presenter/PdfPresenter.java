package fr.istic.tools.scanexam.presenter;

import java.io.InputStream;

/**
 * Controlleur du pdf
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public abstract class PdfPresenter {
  /**
   * Largeur de la fenêtre
   */
  protected int width;
  
  /**
   * Hauteur de la fenêtre
   */
  protected int height;
  
  /**
   * InputStream du pdf
   */
  protected InputStream pdfInput;
  
  /**
   * Constructeur
   * @param width Largeur de la fenêtre
   * @param height Hauteur de la fenêtre
   * @param pdfPath Chemin vers le pdf
   */
  public PdfPresenter(final int width, final int height, final InputStream pdfInput) {
    this.width = width;
    this.height = height;
    this.pdfInput = pdfInput;
  }
}
