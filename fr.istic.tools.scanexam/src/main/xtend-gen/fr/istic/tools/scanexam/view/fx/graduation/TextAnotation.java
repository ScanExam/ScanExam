package fr.istic.tools.scanexam.view.fx.graduation;

import javafx.scene.shape.Rectangle;

@SuppressWarnings("all")
public class TextAnotation extends Rectangle {
  public TextAnotation(final double x, final double y, final double height, final double width, final String text) {
    this.setX(x);
    this.setY(y);
    this.setHeight(height);
    this.setWidth(width);
    this.text = text;
  }
  
  private String text;
}
