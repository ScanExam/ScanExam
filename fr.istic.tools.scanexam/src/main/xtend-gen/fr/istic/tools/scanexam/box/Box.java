package fr.istic.tools.scanexam.box;

/**
 * Donnée d'une boîte en deux dimensions
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class Box {
  /**
   * ATTRIBUTS
   */
  private double x;
  
  private double y;
  
  private double width;
  
  private double height;
  
  private String title;
  
  private double minWidth;
  
  private double minHeight;
  
  private double maxWidth;
  
  private double maxHeight;
  
  private int nbPage;
  
  /**
   * Constructeur par défaut
   */
  public Box() {
    this.setMinWidth((-1.0));
    this.setMinHeight((-1.0));
    this.setMaxWidth((-1.0));
    this.setMaxHeight((-1.0));
    this.setX(0.0);
    this.setY(0.0);
    this.setWidth(0.0);
    this.setHeight(0.0);
    this.setTitle("");
  }
  
  /**
   * Constructeur paramétré sans bornes
   * @param x Coordonnée X de la boîte
   * @param y Coordonnée Y de la boîte
   * @param width Largueur de la boîte
   * @param height Hauteur de la boîte
   * @param title Titre de la boîte
   */
  public Box(final double x, final double y, final double width, final double height, final String title) {
    this.setMinWidth((-1.0));
    this.setMinHeight((-1.0));
    this.setMaxWidth((-1.0));
    this.setMaxHeight((-1.0));
    this.setX(x);
    this.setY(y);
    this.setWidth(width);
    this.setHeight(height);
    this.setTitle(title);
  }
  
  /**
   * Constructeur paramétré
   * @param x Coordonnée X de la boîte
   * @param y Coordonnée Y de la boîte
   * @param width Largueur de la boîte
   * @param height Hauteur de la boîte
   * @param title Titre de la boîte
   * @param minWidth Largueur minimum de la boîte
   * @param minHeight Hauteur minimum de la boîte
   * @param maxWidth Largueur maximum de la boîte
   * @param maxHeight Hauteur maximum de la boîte
   */
  public Box(final double x, final double y, final double width, final double height, final String title, final double minWidth, final double minHeight, final double maxWidth, final double maxHeight) {
    this.setMinWidth(minWidth);
    this.setMinHeight(minHeight);
    this.setMaxWidth(maxWidth);
    this.setMaxHeight(maxHeight);
    this.setX(x);
    this.setY(y);
    this.setWidth(width);
    this.setHeight(height);
    this.setTitle(title);
  }
  
  /**
   * METHODES
   */
  public void updateCoordinates(final double x, final double y) {
    this.setX(x);
    this.setY(y);
  }
  
  public void updateSize(final double width, final double height) {
    this.setWidth(width);
    this.setHeight(height);
  }
  
  public void updateBounds(final double minWidth, final double minHeight, final double maxWidth, final double maxHeight) {
    this.setMinWidth(minWidth);
    this.setMinHeight(minHeight);
    this.setMaxWidth(maxWidth);
    this.setMaxHeight(maxHeight);
  }
  
  @Override
  public String toString() {
    return ((((((((this.title + ",") + Double.valueOf(this.x)) + ",") + Double.valueOf(this.y)) + ",") + Double.valueOf(this.width)) + ",") + Double.valueOf(this.height));
  }
  
  /**
   * GETTERS
   */
  public double getX() {
    return this.x;
  }
  
  public double getY() {
    return this.y;
  }
  
  public double getWidth() {
    return this.width;
  }
  
  public double getHeight() {
    return this.height;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public double getMinWidth() {
    return this.minWidth;
  }
  
  public double getMinHeight() {
    return this.minHeight;
  }
  
  public double getMaxWidth() {
    return this.maxWidth;
  }
  
  public double getMaxHeight() {
    return this.maxHeight;
  }
  
  public int getNbPage() {
    return this.nbPage;
  }
  
  public void setNbPage(final int nbPage) {
    this.nbPage = nbPage;
  }
  
  /**
   * SETTERS
   */
  public void setX(final double x) {
    this.x = x;
  }
  
  public void setY(final double y) {
    this.y = y;
  }
  
  public void setWidth(final double width) {
    if ((width < this.minWidth)) {
      this.width = this.minWidth;
    } else {
      if (((this.maxWidth >= 0.0) && (width > this.maxWidth))) {
        this.width = this.maxWidth;
      } else {
        this.width = width;
      }
    }
  }
  
  public void setHeight(final double height) {
    if ((height < this.minHeight)) {
      this.height = this.minHeight;
    } else {
      if (((this.maxHeight >= 0.0) && (height > this.maxHeight))) {
        this.height = this.maxHeight;
      } else {
        this.height = height;
      }
    }
  }
  
  public void setTitle(final String title) {
    this.title = title;
  }
  
  public void setMinWidth(final double minWidth) {
    if ((minWidth < 0.0)) {
      this.minWidth = 0.0;
    } else {
      this.minWidth = minWidth;
    }
    if ((this.width < this.minWidth)) {
      this.width = this.minWidth;
    }
  }
  
  public void setMinHeight(final double minHeight) {
    if ((minHeight < 0.0)) {
      this.minHeight = 0.0;
    } else {
      this.minHeight = minHeight;
    }
    if ((this.height < this.minHeight)) {
      this.height = this.minHeight;
    }
  }
  
  public void setMaxWidth(final double maxWidth) {
    if ((maxWidth < 0.0)) {
      this.maxWidth = (-1.0);
    } else {
      this.maxWidth = maxWidth;
    }
    if (((this.maxWidth >= 0.0) && (this.width > this.maxWidth))) {
      this.width = maxWidth;
    }
  }
  
  public void setMaxHeight(final double maxHeight) {
    if ((maxHeight < 0.0)) {
      this.maxHeight = (-1.0);
    } else {
      this.maxHeight = maxHeight;
    }
    if (((this.maxHeight >= 0.0) && (this.height > this.maxHeight))) {
      this.height = maxHeight;
    }
  }
}
