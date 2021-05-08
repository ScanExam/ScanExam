package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.view.fx.FxSettings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

@SuppressWarnings("all")
public class QuestionItemGraduation extends VBox {
  public QuestionItemGraduation() {
    super();
    Label _label = new Label();
    this.name = _label;
    this.getChildren().add(this.name);
    this.getStyleClass().add("ListItem");
    this.setupEvents();
  }
  
  private Label name;
  
  private int questionId;
  
  private int page;
  
  private QuestionListGraduation list;
  
  public QuestionListGraduation setList(final QuestionListGraduation list) {
    return this.list = list;
  }
  
  public void setName(final String x) {
    this.name.setText(x);
  }
  
  public int setQuestionId(final int x) {
    return this.questionId = x;
  }
  
  public int setPage(final int x) {
    return this.page = x;
  }
  
  public double getX() {
    double _questionX = this.list.getController().questionX(this.questionId);
    double _imageWidth = this.list.getController().getImageWidth();
    return (_questionX * _imageWidth);
  }
  
  public double getY() {
    double _questionY = this.list.getController().questionY(this.questionId);
    double _imageHeight = this.list.getController().getImageHeight();
    return (_questionY * _imageHeight);
  }
  
  public double getH() {
    double _questionHeight = this.list.getController().questionHeight(this.questionId);
    double _imageHeight = this.list.getController().getImageHeight();
    return (_questionHeight * _imageHeight);
  }
  
  public double getW() {
    double _questionWidth = this.list.getController().questionWidth(this.questionId);
    double _imageWidth = this.list.getController().getImageWidth();
    return (_questionWidth * _imageWidth);
  }
  
  public int getQuestionId() {
    return this.questionId;
  }
  
  public String getName() {
    return this.name.getText();
  }
  
  public int getPage() {
    return this.page;
  }
  
  public float getWorth() {
    return this.list.getController().questionWorth(this.questionId);
  }
  
  public void setFocus(final boolean b) {
    if (b) {
      this.setColor(FxSettings.ITEM_HIGHLIGHT_COLOR);
    } else {
      this.setColor(FxSettings.ITEM_NORMAL_COLOR);
    }
  }
  
  public void setColor(final Color color) {
    BackgroundFill bf = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
    Background _background = new Background(bf);
    this.setBackground(_background);
  }
  
  public void setupEvents() {
    final QuestionItemGraduation me = this;
    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        QuestionItemGraduation.this.list.getController().selectQuestion(me);
      }
    });
  }
}
