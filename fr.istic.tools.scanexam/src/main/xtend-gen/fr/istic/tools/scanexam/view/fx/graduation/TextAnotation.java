package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.view.fx.graduation.PdfPaneWithAnotations;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

@SuppressWarnings("all")
public class TextAnotation extends VBox {
  public TextAnotation(final double x, final double y, final double height, final double width, final String text, final PdfPaneWithAnotations parent) {
    this.parent = parent;
    TextArea _textArea = new TextArea(text);
    this.text = _textArea;
    Text _text = new Text();
    this.textHolder = _text;
    HBox _hBox = new HBox();
    this.bar = _hBox;
    Label _label = new Label("X");
    this.close = _label;
    this.bar.setMaxHeight(5);
    Pane p = new Pane();
    this.bar.getChildren().addAll(p, this.close);
    this.getChildren().addAll(this.bar, this.text);
    Line _line = new Line();
    this.line = _line;
    Circle _circle = new Circle();
    this.ball = _circle;
    this.ball.setRadius(3);
    this.ball.setCursor(Cursor.MOVE);
    this.setCursor(Cursor.MOVE);
    this.text.setWrapText(true);
    this.text.getStyleClass().add("textAnnotation");
    this.getStyleClass().add("annotationBox");
    this.move(x, y, height, width);
    this.setupEvents();
  }
  
  private TextArea text;
  
  private Line line;
  
  private Circle ball;
  
  private HBox bar;
  
  private Label close;
  
  private PdfPaneWithAnotations parent;
  
  private Text textHolder;
  
  private double oldHeight = 0f;
  
  public List<Node> getAllParts() {
    return List.<Node>of(this.line, this.ball, this);
  }
  
  public void move(final double x, final double y, final double height, final double width) {
    this.setLayoutX(x);
    this.setLayoutY(y);
    this.setMinWidth(width);
    this.setMaxWidth(width);
    this.line.setStartX((x + (width / 2)));
    this.line.setStartY((y + (height / 2)));
    this.line.setEndX((x - 15));
    this.line.setEndY((y - 15));
    this.ball.setCenterX((x - 15));
    this.ball.setCenterY((y - 15));
  }
  
  public void move(final double x, final double y) {
    this.setLayoutX(x);
    this.setLayoutY(y);
    double _maxWidth = this.getMaxWidth();
    double _divide = (_maxWidth / 2);
    double _plus = (x + _divide);
    this.line.setStartX(_plus);
    double _maxHeight = this.getMaxHeight();
    double _divide_1 = (_maxHeight / 2);
    double _plus_1 = (y + _divide_1);
    this.line.setStartY(_plus_1);
  }
  
  public void movePointer(final double x, final double y) {
    this.line.setEndX(x);
    this.line.setEndY(y);
    this.ball.setCenterX(x);
    this.ball.setCenterY(y);
  }
  
  public void setupEvents() {
    this.textHolder.textProperty().bind(this.text.textProperty());
    final ChangeListener<Bounds> _function = (ObservableValue<? extends Bounds> obs, Bounds oldVal, Bounds newVal) -> {
      double _height = newVal.getHeight();
      boolean _notEquals = (this.oldHeight != _height);
      if (_notEquals) {
        this.oldHeight = newVal.getHeight();
        double _height_1 = this.textHolder.getLayoutBounds().getHeight();
        double _plus = (_height_1 + 20);
        this.text.setPrefHeight(_plus);
        System.out.println(this.textHolder.getLayoutBounds().getHeight());
      }
    };
    this.textHolder.layoutBoundsProperty().addListener(_function);
    final EventHandler<MouseEvent> _function_1 = (MouseEvent event) -> {
      this.parent.handleMoveAnnotation(this, event);
    };
    this.setOnMousePressed(_function_1);
    final EventHandler<MouseEvent> _function_2 = (MouseEvent event) -> {
      this.parent.handleMovePointer(this, event);
    };
    this.ball.setOnMousePressed(_function_2);
  }
}
