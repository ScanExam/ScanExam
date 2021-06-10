package fr.istic.tools.scanexam.view.fx.editor;

import fr.istic.tools.scanexam.view.fx.editor.Box;
import fr.istic.tools.scanexam.view.fx.editor.BoxType;

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
   * Constructeur
   * @param zone Zone du qr code
   */
  public QrCodeZone(final Box zone) {
    this.zone = zone;
    this.scale = 1.0f;
    this.zone.setupEvents(this.getType());
    this.setFocus(true);
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
