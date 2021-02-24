package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.box.Box;
import fr.istic.tools.scanexam.box.BoxList;

/**
 * Permet de dessiner des boîtes de sélection
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public abstract class PresenterBox {
  /**
   * Largeur de la fenêtre
   */
  protected int windowWidth;
  
  /**
   * Hauteur de la fenêtre
   */
  protected int windowHeight;
  
  /**
   * Echelle pour l'affichage
   */
  protected int scale;
  
  /**
   * Point d'origine sur l'axe X
   */
  protected int originX;
  
  /**
   * Point d'origine sur l'axe Y
   */
  protected int originY;
  
  /**
   * Boîtes de sélection
   */
  protected BoxList selectionBoxes;
  
  /**
   * Largueur minimum des boîtes
   */
  protected final int minWidth = 64;
  
  /**
   * Hauteur minimum des boîtes
   */
  protected final int minHeight = 32;
  
  /**
   * Hauteur des boîtes des titres
   */
  protected final int titleHeight = 32;
  
  /**
   * Constructeur
   * @param windowWidth Largeur de la fenêtre
   * @param windowHeight Hauteur de la fenêtre
   * @param scale Echelle pour l'affichage
   * @param originX Point d'origine sur l'axe X
   * @param originY Point d'origine sur l'axe Y
   * @param selectionBoxes Boîtes de sélection
   */
  public PresenterBox(final int windowWidth, final int windowHeight, final int scale, final int originX, final int originY, final BoxList selectionBoxes) {
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;
    this.selectionBoxes = selectionBoxes;
    this.scale = scale;
    this.originX = originX;
    this.originY = originY;
    double minWidth = ((((double) this.minWidth) / this.windowWidth) * this.scale);
    double minHeight = ((((double) this.minHeight) / this.windowHeight) * this.scale);
    this.selectionBoxes.updateBounds(minWidth, minHeight, (-1.0), (-1.0));
  }
  
  /**
   * Ajoute une boîte à l'interface graphique
   * @param x Position x
   * @param y Position y
   * @param title Nom de la boîte
   */
  protected void createBox(final double x, final double y) {
    String _string = Integer.valueOf(this.selectionBoxes.size()).toString();
    String _plus = ("Qst " + _string);
    this.selectionBoxes.addBox(x, y, _plus);
  }
  
  /**
   * Redimensionne une boîte
   * @param box Boîte à redimensionner
   * @param x Nouvelle position x
   * @param y Nouvelle position y
   * @param width Nouvelle largueur
   * @param height Nouvelle hauteur
   */
  protected void resizeBox(final Box box, final double x, final double y, final double width, final double height) {
    this.selectionBoxes.updateBox(box, x, y, width, height);
  }
  
  /**
   * Déplace une boîte
   * @param box Boîte à déplacer
   * @param x Nouvelle position x
   * @param y Nouvelle position y
   */
  protected void moveBox(final Box box, final double x, final double y) {
    box.updateCoordinates(x, y);
  }
  
  /**
   * Supprime une boîte de l'interface graphique
   * @param box Boîte à surprimer
   */
  protected void deleteBox(final Box box) {
    this.selectionBoxes.removeBox(box);
  }
  
  /**
   * GETTERS
   */
  public double getMinWidth() {
    return this.minWidth;
  }
  
  public double getMinHeight() {
    return this.minHeight;
  }
  
  public double getTitleHeight() {
    return this.titleHeight;
  }
  
  public BoxList getSelectionBoxes() {
    return this.selectionBoxes;
  }
  
  /**
   * SETTERS
   */
  public void setScale(final int scale) {
    this.scale = scale;
  }
  
  public void setOriginX(final int originX) {
    this.originX = originX;
  }
  
  public void setOriginY(final int originY) {
    this.originY = originY;
  }
}
