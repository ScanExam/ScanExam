package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.box.BoxList;
import fr.istic.tools.scanexam.view.AdapterSwingBox;
import fr.istic.tools.scanexam.view.AdapterSwingPdfPanel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.Optional;
import javax.swing.JPanel;

/**
 * Controlleur Swing du pdf avec swing
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class AdapterSwingPdfAndBoxPanel extends AdapterSwingPdfPanel {
  /**
   * Controlleur des boîtes de sélection
   */
  private AdapterSwingBox selectionController;
  
  /**
   * Objet contenant les boîtes de sélection
   */
  private BoxList selectionBoxes;
  
  /**
   * Constructeur
   * @param width Largeur de la fenêtre
   * @param height Hauteur de la fenêtre
   * @param pdfInput InputStream du pdf
   * @param selectionBoxes Objet contenant les boîtes de sélection
   */
  public AdapterSwingPdfAndBoxPanel(final int width, final int height, final InputStream pdfInput, final BoxList selectionBoxes) {
    super(width, height, pdfInput);
    this.selectionBoxes = selectionBoxes;
    int selectWidth = this.pdf.getWidth(null);
    int selectHeight = this.pdf.getHeight(null);
    AdapterSwingBox _adapterSwingBox = new AdapterSwingBox(selectWidth, selectHeight, this.scale, this.originX, this.originY, selectionBoxes);
    this.selectionController = _adapterSwingBox;
  }
  
  /**
   * Déplacement de le contenu
   * @param e Mouvement de la souris
   */
  @Override
  public void moveOrigin(final MouseEvent e) {
    boolean _isPresent = this.lastClickPoint.isPresent();
    if (_isPresent) {
      Point dragPoint = e.getPoint();
      int _originX = this.originX;
      double _x = this.lastClickPoint.get().getX();
      double _minus = (dragPoint.x - _x);
      this.originX = (_originX + ((int) _minus));
      int _originY = this.originY;
      double _y = this.lastClickPoint.get().getY();
      double _minus_1 = (dragPoint.y - _y);
      this.originY = (_originY + ((int) _minus_1));
      this.selectionController.setOriginX(this.originX);
      this.selectionController.setOriginY(this.originY);
      this.repaint();
      this.lastClickPoint = Optional.<Point>of(dragPoint);
    }
  }
  
  /**
   * Incremente l'échelle
   * @param value Valeur à ajouter
   */
  @Override
  public void incrScale(final int value) {
    if (((this.scale + value) < 1)) {
      this.scale = 1;
    } else {
      int _scale = this.scale;
      this.scale = (_scale + value);
    }
    this.selectionController.setScale(this.scale);
    this.repaint();
  }
  
  /**
   * GETTERS
   */
  public AdapterSwingBox getSelectionController() {
    return this.selectionController;
  }
  
  public BoxList getSelectionBoxes() {
    return this.selectionBoxes;
  }
  
  /**
   * SETTERS
   */
  @Override
  public void setView(final JPanel view) {
    this.view = Optional.<JPanel>of(view);
    this.selectionController.setView(this.view.get());
  }
}
