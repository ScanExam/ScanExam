package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.view.fX.FXSettings;
import fr.istic.tools.scanexam.view.fX.ListViewBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

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
  
  public Box(final String name, final int page, final Box.BoxType type, final double x, final double y, final double h, final double w) {
    super(x, y, h, w);
    this.boxId = Box.newID();
    this.page = page;
    this.type = type;
    ListViewBox _listViewBox = new ListViewBox(name, this);
    this.listViewBox = _listViewBox;
    this.setFill(Color.rgb(200, 200, 200, 0.2));
    this.setStroke(Color.BLACK);
    this.setStrokeWidth(FXSettings.BOX_BORDER_THICKNESS);
    Text _text = new Text(x, (y - 5), name);
    this.text = _text;
  }
  
  public Box(final String name, final int page, final Box.BoxType type, final double x, final double y) {
    this(name, page, type, x, y, 0, 0);
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
  
  private int boxId;
  
  private int page;
  
  private Text text;
  
  public Text getText() {
    return this.text;
  }
  
  public ListViewBox getListViewBox() {
    return this.listViewBox;
  }
  
  public int getPageNumber() {
    return this.page;
  }
  
  public Box.BoxType getType() {
    return this.type;
  }
  
  public int getBoxId() {
    return this.boxId;
  }
  
  public String getName() {
    return this.listViewBox.getName();
  }
  
  public int setBoxId(final int id) {
    return this.boxId = id;
  }
  
  public void setFocus(final boolean b) {
    if (b) {
      this.setStroke(Color.web("#0093ff"));
    } else {
      this.setStroke(Color.BLACK);
    }
  }
}
