package fr.istic.tools.scanexam.view.fx.editor;

import fr.istic.tools.scanexam.view.fx.editor.Box;
import fr.istic.tools.scanexam.view.fx.editor.BoxType;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;

/**
 * Donnée de la zone contenant le qr code
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class QrCodeZone {
  /**
   * Zone du qr code
   */
  private Box zone;
  
  /**
   * Échelle de la zone du qr code
   */
  private float scale;
  
  /**
   * Controlleur de l'édition du sujet maître
   */
  private ControllerFxEdition controller;
  
  /**
   * Constructeur
   * @param zone Zone du qr code
   * @param controller Controlleur de l'édition du sujet maître
   */
  public QrCodeZone(final Box zone, final ControllerFxEdition controller) {
    this.zone = zone;
    this.scale = 1.0f;
    this.zone.setupEvents(this.getType());
    this.controller = controller;
    this.addToModel();
    this.setFocus(true);
  }
  
  /**
   * Ajoute les coordonnées de la zone qr code dans le modèle
   */
  public void addToModel() {
    double _x = this.zone.getX();
    double _maxX = this.controller.getMaxX();
    double _divide = (_x / _maxX);
    double _y = this.zone.getY();
    double _maxY = this.controller.getMaxY();
    double _divide_1 = (_y / _maxY);
    double _height = this.zone.getHeight();
    double _maxY_1 = this.controller.getMaxY();
    double _divide_2 = (_height / _maxY_1);
    double _width = this.zone.getWidth();
    double _maxX_1 = this.controller.getMaxX();
    double _divide_3 = (_width / _maxX_1);
    this.controller.createQrCode(_divide, _divide_1, _divide_2, _divide_3);
  }
  
  /**
   * Met à jour les coordonnées de la zone qr code dans le modèle
   */
  public void updateInModel() {
    double _x = this.zone.getX();
    double _maxX = this.controller.getMaxX();
    double _divide = (_x / _maxX);
    double _y = this.zone.getY();
    double _maxY = this.controller.getMaxY();
    double _divide_1 = (_y / _maxY);
    this.controller.moveQrCode(_divide, _divide_1);
    double _height = this.zone.getHeight();
    double _maxY_1 = this.controller.getMaxY();
    double _divide_2 = (_height / _maxY_1);
    double _width = this.zone.getWidth();
    double _maxX_1 = this.controller.getMaxX();
    double _divide_3 = (_width / _maxX_1);
    this.controller.resizeQrCode(_divide_2, _divide_3);
  }
  
  /**
   * Revoie la zone du qr code
   * @return Zone du qr code
   */
  public Box getZone() {
    return this.zone;
  }
  
  /**
   * Revoie l'échelle de la zone du qr code
   * @return Échelle de la zone du qr code
   */
  public float getScale() {
    return this.scale;
  }
  
  /**
   * Revoie le type de box de la zone du qr code
   * @return BoxType.QR
   */
  public BoxType getType() {
    return BoxType.QR;
  }
  
  /**
   * Sélectionne ou non la zone du qr code
   * @param True si l'on veut sélectionner la zone, false sinon
   */
  public void setFocus(final boolean focus) {
    this.zone.setFocus(focus);
  }
  
  /**
   * Met à jour l'échelle de la zone du qr code
   * @return Nouvelle échelle de la zone du qr code
   */
  public void setScale(final float scale) {
    this.scale = scale;
  }
}
