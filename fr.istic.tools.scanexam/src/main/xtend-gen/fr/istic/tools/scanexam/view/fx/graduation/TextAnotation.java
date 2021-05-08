package fr.istic.tools.scanexam.view.fx.graduation;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

@SuppressWarnings("all")
public class TextAnotation extends VBox {
  private static int anotFontSize = 8;
  
  public static double defaultWidth = 50;
  
  public static double defaultHeight = 50;
  
  public TextAnotation(final double x, final double y, final String text, final PdfPaneWithAnotations parent) {
    this.parent = parent;
    TextArea _textArea = new TextArea(text);
    this.text = _textArea;
    Text _text = new Text();
    this.textHolder = _text;
    Font _font = new Font(TextAnotation.anotFontSize);
    this.textHolder.setFont(_font);
    Font _font_1 = new Font(TextAnotation.anotFontSize);
    this.text.setFont(_font_1);
    this.textHolder.getStyleClass().add("textAnnotation");
    HBox _hBox = new HBox();
    this.bar = _hBox;
    this.bar.setAlignment(Pos.TOP_RIGHT);
    Button _button = new Button("X");
    this.close = _button;
    this.close.setCursor(Cursor.DEFAULT);
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
    this.text.setWrapText(false);
    this.text.getStyleClass().add("textAnnotation");
    this.getStyleClass().add("annotationBox");
    this.text.setMinWidth(TextAnotation.defaultWidth);
    this.text.setMinHeight(TextAnotation.defaultHeight);
    this.text.setPrefWidth(TextAnotation.defaultWidth);
    this.text.setPrefHeight(TextAnotation.defaultHeight);
    this.initPos(x, y);
    this.setupEvents();
    double _height = this.textHolder.getLayoutBounds().getHeight();
    double _plus = (_height + 20);
    this.text.setPrefHeight(_plus);
    double _width = this.textHolder.getLayoutBounds().getWidth();
    double _plus_1 = (_width + 20);
    this.text.setPrefWidth(_plus_1);
    double _imageViewHeight = parent.getImageViewHeight();
    double _layoutY = this.getLayoutY();
    double _minus = (_imageViewHeight - _layoutY);
    this.setMaxHeight(_minus);
    double _imageViewWidth = parent.getImageViewWidth();
    double _layoutX = this.getLayoutX();
    double _minus_1 = (_imageViewWidth - _layoutX);
    this.setMaxWidth(_minus_1);
  }
  
  private int annotId;
  
  private TextArea text;
  
  private Line line;
  
  private Circle ball;
  
  private HBox bar;
  
  private Button close;
  
  private PdfPaneWithAnotations parent;
  
  private Text textHolder;
  
  private double oldHeight = 0f;
  
  private double oldWidth = 0f;
  
  public List<Node> getAllParts() {
    return List.<Node>of(this.line, this.ball, this);
  }
  
  public int getAnnotId() {
    return this.annotId;
  }
  
  public int setAnnotId(final int id) {
    return this.annotId = id;
  }
  
  public double getAnnotX() {
    return this.getLayoutX();
  }
  
  public double getAnnotY() {
    return this.getLayoutY();
  }
  
  public double getAnnotH() {
    return this.getHeight();
  }
  
  public double getAnnotW() {
    return this.getWidth();
  }
  
  public String getAnnotText() {
    return this.text.getText();
  }
  
  public double getAnnotPointerX() {
    return this.ball.getCenterX();
  }
  
  public double getAnnotPointerY() {
    return this.ball.getCenterY();
  }
  
  public void initPos(final double x, final double y) {
    this.setLayoutX(x);
    this.setLayoutY(y);
    double _layoutX = this.getLayoutX();
    double _width = this.getWidth();
    double _divide = (_width / 2);
    double _plus = (_layoutX + _divide);
    this.line.setStartX(_plus);
    double _layoutY = this.getLayoutY();
    double _height = this.getHeight();
    double _divide_1 = (_height / 2);
    double _plus_1 = (_layoutY + _divide_1);
    this.line.setStartY(_plus_1);
    double _layoutX_1 = this.getLayoutX();
    double _minus = (_layoutX_1 - 15);
    this.line.setEndX(_minus);
    double _layoutY_1 = this.getLayoutY();
    double _minus_1 = (_layoutY_1 - 15);
    this.line.setEndY(_minus_1);
    double _layoutX_2 = this.getLayoutX();
    double _minus_2 = (_layoutX_2 - 15);
    this.ball.setCenterX(_minus_2);
    double _layoutY_2 = this.getLayoutY();
    double _minus_3 = (_layoutY_2 - 15);
    this.ball.setCenterY(_minus_3);
  }
  
  public void move(final double x, final double y) {
    this.setLayoutX(x);
    this.setLayoutY(y);
    double _layoutX = this.getLayoutX();
    double _width = this.getWidth();
    double _divide = (_width / 2);
    double _plus = (_layoutX + _divide);
    this.line.setStartX(_plus);
    double _layoutY = this.getLayoutY();
    double _height = this.getHeight();
    double _divide_1 = (_height / 2);
    double _plus_1 = (_layoutY + _divide_1);
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
        double _imageViewHeight = this.parent.getImageViewHeight();
        double _layoutY = this.getLayoutY();
        double _minus = (_imageViewHeight - _layoutY);
        this.setMaxHeight(_minus);
      }
      double _width = newVal.getWidth();
      boolean _notEquals_1 = (this.oldWidth != _width);
      if (_notEquals_1) {
        this.oldWidth = newVal.getWidth();
        double _width_1 = this.textHolder.getLayoutBounds().getWidth();
        double _plus_1 = (_width_1 + 20);
        this.text.setPrefWidth(_plus_1);
        double _imageViewWidth = this.parent.getImageViewWidth();
        double _layoutX = this.getLayoutX();
        double _minus_1 = (_imageViewWidth - _layoutX);
        this.setMaxWidth(_minus_1);
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
    final EventHandler<ActionEvent> _function_3 = (ActionEvent event) -> {
      this.parent.handleRemove(this);
    };
    this.close.setOnAction(_function_3);
    final ChangeListener<String> _function_4 = (ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
      this.parent.handleRename(this);
    };
    this.text.textProperty().addListener(_function_4);
  }
}
