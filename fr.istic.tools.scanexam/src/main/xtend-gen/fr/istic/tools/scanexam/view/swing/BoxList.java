package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.view.swing.Box;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;
import org.eclipse.xtext.xbase.lib.Exceptions;

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
   * METHODES
   */
  public void addBox(final Box box) {
    this.list.add(box);
  }
  
  public void addBox(final double x, final double y) {
    Box _box = new Box(x, y, this.minWidth, this.minHeight, "", this.minWidth, this.minHeight, this.maxWidth, this.maxHeight);
    this.list.add(_box);
  }
  
  public void addBox(final double x, final double y, final String title) {
    Box _box = new Box(x, y, this.minWidth, this.minHeight, title, this.minWidth, this.minHeight, this.maxWidth, this.maxHeight);
    this.list.add(_box);
  }
  
  public void addBox(final double x, final double y, final double width, final double height, final String title) {
    Box _box = new Box(x, y, width, height, title, this.minWidth, this.minHeight, this.maxWidth, this.maxHeight);
    this.list.add(_box);
  }
  
  public void updateBox(final int index, final double x, final double y, final double width, final double height) {
    this.getBox(index).updateCoordinates(x, y);
    this.getBox(index).updateSize(width, height);
  }
  
  public void updateBox(final Box box, final double x, final double y, final double width, final double height) {
    box.updateCoordinates(x, y);
    box.updateSize(width, height);
  }
  
  public void updateBounds(final double minWidth, final double minHeight, final double maxWidth, final double maxHeight) {
    this.setMinWidth(minWidth);
    this.setMinHeight(minHeight);
    this.setMaxWidth(maxWidth);
    this.setMaxHeight(maxHeight);
  }
  
  public void removeBox(final int index) {
    this.list.remove(index);
  }
  
  public void removeBox(final Box box) {
    this.list.remove(box);
  }
  
  public void clearList() {
    this.list.clear();
    this.refreshPanel();
  }
  
  public void save() {
    Path file = Paths.get("export.txt");
    try {
      Files.write(file, this.toStringList(), StandardCharsets.UTF_8);
      System.out.println("Save success");
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        System.out.println("Save fail");
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  public void load(final String path) {
    Path file = Paths.get(path);
    try {
      this.clearList();
      List<String> boxes = Files.readAllLines(file);
      for (int i = 0; (i < boxes.size()); i++) {
        {
          String currentBox = boxes.get(i);
          String[] currentBoxData = currentBox.split(",");
          double x = Double.parseDouble(currentBoxData[1]);
          double y = Double.parseDouble(currentBoxData[2]);
          double width = Double.parseDouble(currentBoxData[3]);
          double height = Double.parseDouble(currentBoxData[4]);
          String title = currentBoxData[0];
          this.addBox(x, y, width, height, title);
        }
      }
      this.refreshPanel();
      System.out.println("Load success");
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        System.out.println("Load fail");
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  public List<String> toStringList() {
    List<String> stringList = new LinkedList<String>();
    for (int i = 0; (i < this.size()); i++) {
      stringList.add(this.getBox(i).toString());
    }
    return stringList;
  }
  
  private void refreshPanel() {
    if ((this.panel != null)) {
      this.panel.repaint();
    }
  }
  
  /**
   * GETTERS
   */
  public List<Box> getList() {
    return this.list;
  }
  
  public Box getBox(final int index) {
    return this.list.get(index);
  }
  
  public int size() {
    return this.list.size();
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
  
  /**
   * SETTERS
   */
  public void setList(final List<Box> list) {
    this.list = list;
  }
  
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
  
  public void setPanel(final JPanel panel) {
    this.panel = panel;
  }
}
