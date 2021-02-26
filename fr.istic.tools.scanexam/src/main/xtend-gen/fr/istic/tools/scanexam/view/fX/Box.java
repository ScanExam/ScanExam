package fr.istic.tools.scanexam.view.fX;

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
  
  private Box.BoxType type;
  
  private String name;
}
