package fr.istic.tools.scanexam.view.fX;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class QuestionItem extends VBox {
  private double x;
  
  private double y;
  
  private double height;
  
  private double width;
  
  private Label name;
  
  private int questionId;
  
  private int page;
  
  public QuestionItem() {
    super();
    Label _label = new Label();
    this.name = _label;
    this.getChildren().add(this.name);
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
}
