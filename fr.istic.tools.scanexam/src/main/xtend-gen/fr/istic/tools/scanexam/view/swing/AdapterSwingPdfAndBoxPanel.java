package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.presenter.PresenterQuestionZone;
import fr.istic.tools.scanexam.view.swing.AdapterSwingBox;
import fr.istic.tools.scanexam.view.swing.AdapterSwingPdfPanel;
import fr.istic.tools.scanexam.view.swing.BoxList;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Optional;
import javax.swing.JPanel;

/**
 * Controlleur Swing du pdf et de l'ajout de boîtes avec swing
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class AdapterSwingPdfAndBoxPanel extends AdapterSwingPdfPanel {
  /**
   * Adaptateur des boîtes de sélection
   */
  private AdapterSwingBox adapterBox;
  
  /**
   * Objet contenant les boîtes de sélection
   */
  private BoxList selectionBoxes;
  
  /**
   * Constructeur
   * @param width Largeur de la fenêtre
   * @param height Hauteur de la fenêtre
   * @param presenterPdf Présenter gérant le pdf
   * @param selectionBoxes Objet contenant les boîtes de sélection
   */
  public AdapterSwingPdfAndBoxPanel(final int width, final int height, final Presenter presenterPdf, final BoxList selectionBoxes) {
    super(width, height, presenterPdf);
    this.selectionBoxes = selectionBoxes;
    if ((this.pdf != null)) {
      int selectWidth = this.pdf.getWidth(null);
      int selectHeight = this.pdf.getHeight(null);
      AdapterSwingBox _adapterSwingBox = new AdapterSwingBox(selectWidth, selectHeight, this.scale, this.originX, this.originY, selectionBoxes);
      this.adapterBox = _adapterSwingBox;
    } else {
      AdapterSwingBox _adapterSwingBox_1 = new AdapterSwingBox(width, height, this.scale, this.originX, this.originY, selectionBoxes);
      this.adapterBox = _adapterSwingBox_1;
    }
  }
  
  /**
   * Déplacement du pdf
   * @param e Mouvement de la souris
   */
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
      this.adapterBox.setOriginX(this.originX);
      this.adapterBox.setOriginY(this.originY);
      this.repaint();
      this.lastClickPoint = Optional.<Point>of(dragPoint);
    }
  }
  
  /**
   * Incremente l'échelle
   * @param value Valeur à ajouter
   */
  public void incrScale(final int value) {
    if (((this.scale + value) < 1)) {
      this.scale = 1;
    } else {
      int _scale = this.scale;
      this.scale = (_scale + value);
    }
    this.adapterBox.setScale(this.scale);
    this.repaint();
  }
  
  /**
   * Renvoie l'daptateur des boîtes de sélection
   * @return L'daptateur des boîtes de sélection
   */
  public AdapterSwingBox getAdapterBox() {
    return this.adapterBox;
  }
  
  /**
   * Renvoie la liste des boîtes de sélection
   * @return La liste des boîtes de sélection
   */
  public BoxList getSelectionBoxes() {
    return this.selectionBoxes;
  }
  
  /**
   * Met à jour le présenter de création de zone de question
   * @param presenterQst Présenter de création de zone de question
   */
  public void setPresenterQst(final PresenterQuestionZone presenterQst) {
    this.adapterBox.setPresenter(presenterQst);
  }
  
  /**
   * Lie la vue de au panel
   * @param view Vue swing
   */
  public void setView(final JPanel view) {
    this.view = Optional.<JPanel>of(view);
    this.adapterBox.setView(this.view.get());
  }
  
  /**
   * Change la mise à l'échelle. Soit par rapport à la hauteur ou la largueur
   * @param scaleOnWidth Indique si la mise à l'échelle se fait par rapport à la hauteur ou la largueur
   */
  public void setScaleOnWidth(final boolean scaleOnWidth) {
    if ((this.pdf != null)) {
      super.setScaleOnWidth(scaleOnWidth);
      this.adapterBox.setWindowWidth(this.pdf.getWidth(this.view.get()));
      this.adapterBox.setWindowHeight(this.pdf.getHeight(this.view.get()));
      this.incrScale(0);
    }
  }
}
