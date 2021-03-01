package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.view.fX.FXSettings;
import fr.istic.tools.scanexam.view.fX.ListViewBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

@SuppressWarnings("all")
public class Box extends Rectangle {
  public enum BoxType {
    QUESTION,
    
    ID,
    
    QR;
  }
  
  private static int ID = 0;
  
  public static int newID() {
    return Box.ID++;
  }
  
  public Box(final String name, final int page, final Box.BoxType type, final double x, final double y) {
    super(x, y, 0, 0);
    this.id = Box.newID();
    this.page = page;
    this.name = name;
    this.type = type;
    ListViewBox _listViewBox = new ListViewBox(name, this);
    this.listViewBox = _listViewBox;
    this.setFill(Color.rgb(200, 200, 200, 0.2));
    this.setStroke(Color.BLACK);
    this.setStrokeWidth(FXSettings.BOX_BORDER_THICKNESS);
  }
  
  public Box(final int page, final Box.BoxType type, final double x, final double y) {
    this("box", page, type, x, y);
  }
  
  public Box(final Box.BoxType type, final double x, final double y) {
    this(0, type, x, y);
  }
  
  public Box(final Box.BoxType type) {
    this(type, 0, 0);
  }
  
  private ListViewBox listViewBox;
  
  private Box.BoxType type;
  
  private String name;
  
  private int id;
  
  private int page;
  
  public ListViewBox getListViewBox() {
    return this.listViewBox;
  }
  
  public int getPageNumber() {
    return this.page;
  }
  
  public Box.BoxType getType() {
    return this.type;
  }
  
  public int getID() {
    return this.id;
  }
  
  public void setFocus(final boolean b) {
    if (b) {
      this.setStroke(Color.web("#0093ff"));
    } else {
      this.setStroke(Color.BLACK);
    }
  }
}
