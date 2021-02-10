package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.box.BoxList;

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
   * Chemin vers le pdf
   */
  protected String pdfPath;
  
  /**
   * Objet contenant les boîtes de sélection
   */
  private BoxList selectionBoxes;
  
  /**
   * Constructeur
   * @param width Largeur de la fenêtre
   * @param height Hauteur de la fenêtre
   * @param pdfPath Chemin vers le pdf
   */
  public PdfPresenter(final int width, final int height, final String pdfPath, final BoxList selectionBoxes) {
    this.width = width;
    this.height = height;
    this.pdfPath = pdfPath;
    this.selectionBoxes = selectionBoxes;
  }
  
  /**
   * GETTERS
   */
  public BoxList getSelectionBoxes() {
    return this.selectionBoxes;
  }
}
