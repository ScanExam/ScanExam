package fr.istic.tools.scanexam.view.fX.corrector;

import fr.istic.tools.scanexam.view.fX.FXSettings;
import fr.istic.tools.scanexam.view.fX.corrector.QuestionListCorrector;
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
public class QuestionItemCorrector extends VBox {
  public QuestionItemCorrector() {
    super();
    Label _label = new Label();
    this.name = _label;
    this.getChildren().add(this.name);
    this.getStyleClass().add("ListItem");
    this.setupEvents();
  }
  
  private double x;
  
  private double y;
  
  private double height;
  
  private double width;
  
  private Label name;
  
  private int questionId;
  
  private int page;
  
  private float pointsWorth;
  
  private QuestionListCorrector list;
  
  public QuestionListCorrector setList(final QuestionListCorrector list) {
    return this.list = list;
  }
  
  public double setX(final double x) {
    return this.x = x;
  }
  
  public double setY(final double x) {
    return this.y = x;
  }
  
  public double setH(final double x) {
    return this.height = x;
  }
  
  public double setW(final double x) {
    return this.width = x;
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
    return this.x;
  }
  
  public double getY() {
    return this.y;
  }
  
  public double getH() {
    return this.height;
  }
  
  public double getW() {
    return this.width;
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
    return this.pointsWorth;
  }
  
  public float setWorth(final Float worth) {
    return this.pointsWorth = (worth).floatValue();
  }
  
  public void setFocus(final boolean b) {
    if (b) {
      this.setColor(FXSettings.ITEM_HIGHLIGHT_COLOR);
    } else {
      this.setColor(FXSettings.ITEM_NORMAL_COLOR);
    }
  }
  
  public void setColor(final Color color) {
    BackgroundFill bf = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
    Background _background = new Background(bf);
    this.setBackground(_background);
  }
  
  public void setupEvents() {
    final QuestionItemCorrector me = this;
    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        QuestionItemCorrector.this.list.getController().selectQuestion(me);
      }
    });
  }
}
