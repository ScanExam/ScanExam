package fr.istic.tools.scanexam.view.swing;

/**
 * Donnée d'une boîte en deux dimensions
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class Box {
  /**
   * Coordonnée X de la boîte
   */
  private double x;
  
  /**
   * Coordonnée Y de la boîte
   */
  private double y;
  
  /**
   * Largueur de la boîte
   */
  private double width;
  
  /**
   * Hauteur de la boîte
   */
  private double height;
  
  /**
   * Titre de la boîte
   */
  private String title;
  
  /**
   * Indentifiant de la boîte (-1 par défaut)
   */
  private int id;
  
  /**
   * Largueur minimum de la boîte (ne peux pas être négatif)
   */
  private double minWidth;
  
  /**
   * Hauteur minimum de la boîte (ne peux pas être négatif)
   */
  private double minHeight;
  
  /**
   * Largueur maximum de la boîte (-1 = pas de maximum)
   */
  private double maxWidth;
  
  /**
   * Hauteur maximum de la boîte (-1 = pas de maximum)
   */
  private double maxHeight;
  
  /**
   * Numero de page de la box
   */
  private int nbPage;
  
  /**
   * Constructeur par défaut
   */
  public Box() {
    this(0.0, 0.0, 0.0, 0.0, "");
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
    this(x, y, width, height, title, (-1.0), (-1.0), (-1.0), (-1.0));
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
    this.setId((-1));
    this.setNbPage((-1));
  }
  
  /**
   * Met à jour les coordonnées de la boîte
   * @param x Nouvelle coordonnée X
   * @param y Nouvelle coordonnée Y
   */
  public void updateCoordinates(final double x, final double y) {
    this.setX(x);
    this.setY(y);
  }
  
  /**
   * Met à jour la taille de la boîte
   * @param width Nouvelle largueur de la boîte
   * @param height Nouvelle hauteur de la boîte
   */
  public void updateSize(final double width, final double height) {
    this.setWidth(width);
    this.setHeight(height);
  }
  
  /**
   * Met à jour les bornes minimums et maximums de la boîte
   * @param minWidth Nouvelle largueur minimale de la boîte
   * @param minHeight Nouvelle hauteur minimale de la boîte
   * @param maxWidth Nouvelle largueur maximale de la boîte
   * @param maxHeight Nouvelle hauteur maximale de la boîte
   */
  public void updateBounds(final double minWidth, final double minHeight, final double maxWidth, final double maxHeight) {
    this.setMinWidth(minWidth);
    this.setMinHeight(minHeight);
    this.setMaxWidth(maxWidth);
    this.setMaxHeight(maxHeight);
  }
  
  /**
   * Retranscrit les informations de la boîte sous forme de String
   * @return Informations de la boîte
   */
  public String toString() {
    return ((((((((((((("Box#" + Integer.valueOf(this.id)) + " \"") + this.title) + "\", ") + Double.valueOf(this.x)) + ", ") + Double.valueOf(this.y)) + ", ") + Double.valueOf(this.width)) + ", ") + Double.valueOf(this.height)) + ", on page ") + Integer.valueOf(this.nbPage));
  }
  
  /**
   * Renvoie la coordonnée X de la boîte
   * @param Coordonnée X de la boîte
   */
  public double getX() {
    return this.x;
  }
  
  /**
   * Renvoie la coordonnée Y de la boîte
   * @param Coordonnée Y de la boîte
   */
  public double getY() {
    return this.y;
  }
  
  /**
   * Renvoie la largueur de la boîte
   * @param Largueur de la boîte
   */
  public double getWidth() {
    return this.width;
  }
  
  /**
   * Renvoie la hauteur de la boîte
   * @param Hauteur de la boîte
   */
  public double getHeight() {
    return this.height;
  }
  
  /**
   * Renvoie le nom de la boîte
   * @param Nom de la boîte
   */
  public String getTitle() {
    return this.title;
  }
  
  /**
   * Renvoie l'indentifiant de la boîte
   * @param Identifiant de la boîte
   */
  public int getId() {
    return this.id;
  }
  
  /**
   * Renvoie la largueur minimale de la boîte
   * @param Largueur minimale de la boîte
   */
  public double getMinWidth() {
    return this.minWidth;
  }
  
  /**
   * Renvoie la hauteur minimale de la boîte
   * @param Hauteur minimale de la boîte
   */
  public double getMinHeight() {
    return this.minHeight;
  }
  
  /**
   * Renvoie la largueur maximale de la boîte
   * @param Largueur maximale de la boîte
   */
  public double getMaxWidth() {
    return this.maxWidth;
  }
  
  /**
   * Renvoie la hauteur maximale de la boîte
   * @param Hauteur maximale de la boîte
   */
  public double getMaxHeight() {
    return this.maxHeight;
  }
  
  /**
   * Renvoie le numéro de la page où se trouve la boîte
   * @return Numéro de page où se trouve la boîte
   */
  public int getNbPage() {
    return this.nbPage;
  }
  
  /**
   * Met à jour la coordonnée X de la boîte
   * @param x Nouvelle coordonnée X de la boîte
   */
  public void setX(final double x) {
    this.x = x;
  }
  
  /**
   * Met à jour la coordonnée Y de la boîte
   * @param y Nouvelle coordonnée Y de la boîte
   */
  public void setY(final double y) {
    this.y = y;
  }
  
  /**
   * Met à jour la largueur de la boîte. La valeur sera automatiquement bornée au minimum et maximum autorisé.
   * @param y Nouvelle largueur de la boîte
   */
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
  
  /**
   * Met à jour la hauteur de la boîte. La valeur sera automatiquement bornée au minimum et maximum autorisé.
   * @param y Nouvelle hauteur de la boîte
   */
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
  
  /**
   * Met à jour le titre de la boîte
   * @param Nouveau nom de la boîte
   */
  public void setTitle(final String title) {
    this.title = title;
  }
  
  /**
   * Met à jour l'identifiant de la boîte
   * @param Nouvel identifiant de la boîte
   */
  public void setId(final int id) {
    this.id = id;
  }
  
  /**
   * Met à jour la largueur minimale de la boîte
   * @param y Nouvelle largueur minimale de la boîte
   */
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
  
  /**
   * Met à jour la hauteur minimale de la boîte
   * @param y Nouvelle hautueur minimale de la boîte
   */
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
  
  /**
   * Met à jour la largueur maximale de la boîte
   * @param y Nouvelle largueur maximale de la boîte
   */
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
  
  /**
   * Met à jour la hauteur maximale de la boîte
   * @param y Nouvelle hauteur maximale de la boîte
   */
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
  
  /**
   * Met à jour le numéro de la page où se trouve la boîte
   * @param Nouveau numéro de page
   */
  public void setNbPage(final int nbPage) {
    this.nbPage = nbPage;
  }
}
