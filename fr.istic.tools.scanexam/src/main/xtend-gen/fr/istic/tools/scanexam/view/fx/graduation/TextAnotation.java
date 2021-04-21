package fr.istic.tools.scanexam.view.fx.graduation;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

@SuppressWarnings("all")
public class TextAnotation extends VBox {
  public TextAnotation(final double x, final double y, final double height, final double width, final String text) {
    TextArea _textArea = new TextArea(text);
    this.text = _textArea;
    HBox _hBox = new HBox();
    this.bar = _hBox;
    Label _label = new Label();
    this.close = _label;
    this.bar.setMaxHeight(10);
    Pane p = new Pane();
    this.bar.getChildren().addAll(p, this.close);
    this.getChildren().addAll(this.bar, this.text);
    Line _line = new Line();
    this.line = _line;
    this.text.setWrapText(true);
    this.text.getStyleClass().add("textAnnotation");
    this.getStyleClass().add("annotationBox");
    this.move(x, y, height, width);
  }
  
  private TextArea text;
  
  private Line line;
  
  private HBox bar;
  
  private Label close;
  
  public List<Node> getAllParts() {
    return List.<Node>of(this, this.line);
  }
  
  public void move(final double x, final double y, final double height, final double width) {
    this.setLayoutX(x);
    this.setLayoutY(y);
    this.setMinWidth(width);
    this.setMinHeight(height);
    this.setMaxWidth(width);
    this.setMaxHeight(height);
    this.line.setStartX(x);
    this.line.setStartY(y);
    this.line.setEndX(0);
    this.line.setEndY(0);
  }
}
