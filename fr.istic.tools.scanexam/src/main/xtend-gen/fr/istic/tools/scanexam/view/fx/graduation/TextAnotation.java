package fr.istic.tools.scanexam.view.fx.graduation;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

@SuppressWarnings("all")
public class TextAnotation extends Rectangle {
  public TextAnotation(final double x, final double y, final double height, final double width, final String text) {
    TextArea _textArea = new TextArea(text);
    this.text = _textArea;
    Line _line = new Line();
    this.line = _line;
    this.text.setWrapText(true);
    this.text.getStyleClass().add("textAnnotation");
    this.move(x, y, height, width);
  }
  
  private TextArea text;
  
  private Line line;
  
  public List<Node> getAllParts() {
    return List.<Node>of(this, this.text, this.line);
  }
  
  public void move(final double x, final double y, final double height, final double width) {
    this.text.setLayoutX(x);
    this.text.setLayoutY(y);
    this.text.setMinWidth(width);
    this.text.setMinHeight(height);
    this.text.setMaxWidth(width);
    this.text.setMaxHeight(height);
    this.line.setStartX(x);
    this.line.setStartY(y);
    this.line.setEndX(0);
    this.line.setEndY(0);
    this.setX(x);
    this.setY(y);
    this.setWidth(width);
    this.setHeight(height);
  }
}
