package fr.istic.tools.scanexam.view.fX;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

@SuppressWarnings("all")
public class Box extends Rectangle {
  public enum BoxType {
    QUESTION,
    
    ID,
    
    QR;
  }
  
  public Box(final Box.BoxType type) {
    super(0, 0, 0, 0);
    this.name = "box";
    this.type = type;
    this.setFill(Color.rgb(200, 200, 200, 0.2));
    this.setStroke(Color.BLACK);
    this.setStrokeWidth(1);
  }
  
  public Box(final Box.BoxType type, final double x, final double y) {
    super(x, y, 0, 0);
    this.name = "box";
    this.type = type;
    this.setFill(Color.rgb(200, 200, 200, 0.2));
    this.setStroke(Color.BLACK);
    this.setStrokeWidth(1);
  }
  
  public Box(final int page, final Box.BoxType type, final double x, final double y) {
    super(x, y, 0, 0);
    this.name = "box";
    this.type = type;
    this.setFill(Color.rgb(200, 200, 200, 0.2));
    this.setStroke(Color.BLACK);
    this.setStrokeWidth(1);
  }
  
  public Box(final String name, final int page, final Box.BoxType type, final double x, final double y) {
    super(x, y, 0, 0);
    this.name = "name";
    this.type = type;
    this.setFill(Color.rgb(200, 200, 200, 0.2));
    this.setStroke(Color.BLACK);
    this.setStrokeWidth(1);
  }
  
  public HBox boxItem() {
    HBox container = new HBox();
    TextField field = new TextField();
    container.getChildren().add(field);
    field.setEditable(false);
    field.setText(this.name);
    return container;
  }
  
  private Box.BoxType type;
  
  private String name;
  
  private int page;
}
