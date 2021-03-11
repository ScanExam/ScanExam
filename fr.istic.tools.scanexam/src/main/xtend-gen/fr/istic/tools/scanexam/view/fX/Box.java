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
  
  public Box(final String name, final int page, final Box.BoxType type, final double x, final double y, final double h, final double w) {
    super(x, y, w, h);
    this.page = page;
    this.type = type;
    ListViewBox _listViewBox = new ListViewBox(name, this);
    this.listViewBox = _listViewBox;
    this.setFill(Color.rgb(200, 200, 200, 0.2));
    this.setStroke(Color.BLACK);
    this.setStrokeWidth(FXSettings.BOX_BORDER_THICKNESS);
    Text _text = new Text((x + FXSettings.BOX_TEXT_OFFSET_X), (y + FXSettings.BOX_TEXT_OFFSET_Y), name);
    this.text = _text;
    this.text.setFill(FXSettings.BOX_NORMAL_COLOR);
    this.text.textProperty().bind(this.listViewBox.getNameLabel().textProperty());
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
  
  public Box(final int id, final Box.BoxType type) {
    this(id, type, 0, 0);
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
      this.setColor(FXSettings.BOX_HIGHLIGHT_COLOR);
    } else {
      this.setColor(FXSettings.BOX_NORMAL_COLOR);
    }
  }
  
  public void setColor(final Color color) {
    this.setStroke(color);
    this.text.setFill(color);
  }
  
  public void x(final double x) {
    this.setX(x);
    this.text.setX((x + FXSettings.BOX_TEXT_OFFSET_X));
  }
  
  public void y(final double y) {
    this.setY(y);
    this.text.setY((y + FXSettings.BOX_TEXT_OFFSET_Y));
  }
  
  public void height(final double h) {
    this.setHeight(h);
  }
  
  public void width(final double w) {
    this.setWidth(w);
  }
}
