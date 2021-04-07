package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.presenter.PresenterQuestionZone;
import fr.istic.tools.scanexam.view.swing.Box;
import fr.istic.tools.scanexam.view.swing.BoxList;

/**
 * Permet de dessiner des boîtes de sélection
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public abstract class AdapterBox {
  /**
   * Présenter gérant l'édition de l'examen
   */
  private PresenterQuestionZone presenterQst;
  
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
  public AdapterBox(final int windowWidth, final int windowHeight, final int scale, final int originX, final int originY, final BoxList selectionBoxes) {
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
    int _size = this.selectionBoxes.size();
    int _minus = (_size - 1);
    final Box createdBox = this.selectionBoxes.getBox(_minus);
    final int id = this.presenterQst.createQuestion(x, y, this.minHeight, this.minWidth);
    createdBox.setId(id);
    createdBox.setNbPage(this.presenterQst.getPresenterVueCreation().getPresenterPdf().currentPdfPageNumber());
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
    this.presenterQst.resizeQuestion(box.getId(), height, width);
  }
  
  /**
   * Déplace une boîte
   * @param box Boîte à déplacer
   * @param x Nouvelle position x
   * @param y Nouvelle position y
   */
  protected void moveBox(final Box box, final double x, final double y) {
    box.updateCoordinates(x, y);
    this.presenterQst.moveQuestion(box.getId(), ((float) x), ((float) y));
  }
  
  /**
   * Supprime une boîte de l'interface graphique
   * @param box Boîte à surprimer
   */
  protected void deleteBox(final Box box) {
    this.selectionBoxes.removeBox(box);
    this.presenterQst.removeQuestion(box.getId());
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
  public void setPresenter(final PresenterQuestionZone presenter) {
    this.presenterQst = presenter;
  }
  
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
