package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.view.swing.Box;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Regroupement de plusieurs boîtes
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class BoxList {
  /**
   * ATTRIBUTS
   */
  private List<Box> list;
  
  private double minWidth;
  
  private double minHeight;
  
  private double maxWidth;
  
  private double maxHeight;
  
  private JPanel panel;
  
  /**
   * Constructeur par défaut
   */
  public BoxList() {
    ArrayList<Box> _arrayList = new ArrayList<Box>();
    this.setList(_arrayList);
    this.setMinWidth((-1.0));
    this.setMinHeight((-1.0));
    this.setMaxWidth((-1.0));
    this.setMaxHeight((-1.0));
  }
  
  /**
   * Constructeur paramétré avec création d'une nouvelle liste de boîtes
   * @param minWidth Largueur minimum des boîtes
   * @param minHeight Hauteur minimum des boîtes
   * @param maxWidth Largueur maximum des boîtes
   * @param maxHeight Hauteur maximum des boîtes
   */
  public BoxList(final double minWidth, final double minHeight, final double maxWidth, final double maxHeight) {
    ArrayList<Box> _arrayList = new ArrayList<Box>();
    this.setList(_arrayList);
    this.setMinWidth(minWidth);
    this.setMinHeight(minHeight);
    this.setMaxWidth(maxWidth);
    this.setMaxHeight(maxHeight);
  }
  
  /**
   * Constructeur paramétré
   * @param list Boîtes à importer
   * @param minWidth Largueur minimum des boîtes
   * @param minHeight Hauteur minimum des boîtes
   * @param maxWidth Largueur maximum des boîtes
   * @param maxHeight Hauteur maximum des boîtes
   */
  public BoxList(final List<Box> list, final double minWidth, final double minHeight, final double maxWidth, final double maxHeight) {
    this.setList(list);
    this.setMinWidth(minWidth);
    this.setMinHeight(minHeight);
    this.setMaxWidth(maxWidth);
    this.setMaxHeight(maxHeight);
  }
  
  /**
   * Ajoute une boîte à la liste
   * @param box Boîte à ajouter
   */
  public void addBox(final Box box) {
    this.list.add(box);
  }
  
  /**
   * Créer une nouvelle boîte dans la liste à partir de coordonnées
   * @param x Coordonnée X de la boîte
   * @param y Coordonnée Y de la boîte
   */
  public void addBox(final double x, final double y) {
    this.addBox(x, y, "");
  }
  
  /**
   * Créer une nouvelle boîte dans la liste à partir de coordonnées et d'un nom
   * @param x Coordonnée X de la boîte
   * @param y Coordonnée Y de la boîte
   * @param title Nom de la boîte
   */
  public void addBox(final double x, final double y, final String title) {
    this.addBox(x, y, this.minWidth, this.minHeight, title);
  }
  
  /**
   * Créer une nouvelle boîte dans la liste à partir de coordonnées, de dimensions et d'un nom
   * @param x Coordonnée X de la boîte
   * @param y Coordonnée Y de la boîte
   * @param width Largueur de la boîte
   * @param height Hauteur de la boîte
   * @param title Nom de la boîte
   */
  public void addBox(final double x, final double y, final double width, final double height, final String title) {
    Box _box = new Box(x, y, width, height, title, this.minWidth, this.minHeight, this.maxWidth, this.maxHeight);
    this.list.add(_box);
  }
  
  /**
   * Met à jour les coordonnées et dimension d'une boîte à partir de son identifiant
   * @param index Identifiant de la boîte
   * @param x Nouvelle coordonnée X
   * @param y Nouvelle coordonnée Y
   * @param width Nouvelle largueur
   * @param height Nouvelle hauteur
   */
  public void updateBox(final int index, final double x, final double y, final double width, final double height) {
    this.updateBox(this.getBox(index), x, y, width, height);
  }
  
  /**
   * Met à jour les coordonnées et dimension d'une boîte
   * @param box Boîte à mettre à jour
   * @param x Nouvelle coordonnée X
   * @param y Nouvelle coordonnée Y
   * @param width Nouvelle largueur
   * @param height Nouvelle hauteur
   */
  public void updateBox(final Box box, final double x, final double y, final double width, final double height) {
    box.updateCoordinates(x, y);
    box.updateSize(width, height);
  }
  
  /**
   * Met à jour les bornes minimales et maximales des boîtes
   * @param minWidth Nouvelle largueur minimale
   * @param minHeight Nouvelle hauteur minimale
   * @param maxWidth Nouvelle largueur maximale
   * @param maxHeight Nouvelle hauteur maximale
   */
  public void updateBounds(final double minWidth, final double minHeight, final double maxWidth, final double maxHeight) {
    this.setMinWidth(minWidth);
    this.setMinHeight(minHeight);
    this.setMaxWidth(maxWidth);
    this.setMaxHeight(maxHeight);
  }
  
  /**
   * Retire à boîte de la liste à partir de son identifiant
   * @param index Indentifiant de la boîte
   */
  public void removeBox(final int index) {
    this.list.remove(index);
  }
  
  /**
   * Retire à boîte de la liste
   * @param box Boîte à retirer
   */
  public void removeBox(final Box box) {
    this.list.remove(box);
  }
  
  /**
   * Retirer toutes les boîtes de la liste
   */
  public void clearList() {
    this.list.clear();
    this.refreshPanel();
  }
  
  /**
   * Retranscrit les informations des boîtes sous forme de liste de String
   * @return Informations des boîtes
   */
  public List<String> toStringList() {
    List<String> stringList = new LinkedList<String>();
    for (int i = 0; (i < this.size()); i++) {
      stringList.add(this.getBox(i).toString());
    }
    return stringList;
  }
  
  /**
   * Met à jour la vue
   */
  private void refreshPanel() {
    if ((this.panel != null)) {
      this.panel.repaint();
    }
  }
  
  /**
   * Renvoie la liste des boîtes
   * @return Liste des boîtes
   */
  public List<Box> getList() {
    return this.list;
  }
  
  /**
   * Renvoie une boîte à partir de son identifiant
   * @param index Identifiant de la boîte
   * @return Boîte
   */
  public Box getBox(final int index) {
    return this.list.get(index);
  }
  
  /**
   * Renvoie le nombre de boîtes
   * @return Nombre de boîtes
   */
  public int size() {
    return this.list.size();
  }
  
  /**
   * Renvoie la largueur minimale des boîtes
   * @return Largueur minimale des boîtes
   */
  public double getMinWidth() {
    return this.minWidth;
  }
  
  /**
   * Renvoie la hauteur minimale des boîtes
   * @return Hautuer minimale des boîtes
   */
  public double getMinHeight() {
    return this.minHeight;
  }
  
  /**
   * Renvoie la largueur maximale des boîtes
   * @return Largueur maximale des boîtes
   */
  public double getMaxWidth() {
    return this.maxWidth;
  }
  
  /**
   * Renvoie la hauteur maximale des boîtes
   * @return Hautuer maximale des boîtes
   */
  public double getMaxHeight() {
    return this.maxHeight;
  }
  
  /**
   * Replace la liste des boîtes par celle donnée en paramètre
   * @param list Nouvelle liste de boîtes
   */
  public void setList(final List<Box> list) {
    this.list = list;
  }
  
  /**
   * Met à jour la largueur minimale des boîtes
   * @param minWidth Nouvelle largueur minimale des boîtes
   */
  public void setMinWidth(final double minWidth) {
    if ((minWidth < 0)) {
      this.minWidth = 0;
    } else {
      this.minWidth = minWidth;
    }
    for (int i = 0; (i < this.size()); i++) {
      this.getBox(i).setMinWidth(this.minWidth);
    }
  }
  
  /**
   * Met à jour la hauteur minimale des boîtes
   * @param minWidth Nouvelle hauteur minimale des boîtes
   */
  public void setMinHeight(final double minHeight) {
    if ((minHeight < 0)) {
      this.minHeight = 0;
    } else {
      this.minHeight = minHeight;
    }
    for (int i = 0; (i < this.size()); i++) {
      this.getBox(i).setMinHeight(this.minHeight);
    }
  }
  
  /**
   * Met à jour la largueur maximale des boîtes
   * @param minWidth Nouvelle largueur maximale des boîtes
   */
  public void setMaxWidth(final double maxWidth) {
    if ((maxWidth < 0)) {
      this.maxWidth = (-1);
    } else {
      this.maxWidth = maxWidth;
    }
    for (int i = 0; (i < this.size()); i++) {
      this.getBox(i).setMaxWidth(this.maxWidth);
    }
  }
  
  /**
   * Met à jour la hauteur maximale des boîtes
   * @param minWidth Nouvelle hauteur maximale des boîtes
   */
  public void setMaxHeight(final double maxHeight) {
    if ((maxHeight < 0)) {
      this.maxHeight = (-1);
    } else {
      this.maxHeight = maxHeight;
    }
    for (int i = 0; (i < this.size()); i++) {
      this.getBox(i).setMaxHeight(this.maxHeight);
    }
  }
  
  /**
   * Met à jour la référence vers la vue
   * @param panel Vue swing
   */
  public void setPanel(final JPanel panel) {
    this.panel = panel;
  }
}
