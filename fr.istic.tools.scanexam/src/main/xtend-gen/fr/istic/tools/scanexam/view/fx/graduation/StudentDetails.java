package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.view.fx.graduation.StudentItemGraduation;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class StudentDetails extends VBox {
  public StudentDetails() {
    Label nameRow = new Label("Student name :");
    Label idRow = new Label("Student Id :");
    GridPane _gridPane = new GridPane();
    this.grid = _gridPane;
    this.grid.add(nameRow, 0, 0);
    this.grid.add(idRow, 0, 1);
    Label _label = new Label();
    this.nameLabel = _label;
    Label _label_1 = new Label();
    this.idLabel = _label_1;
    this.grid.add(this.nameLabel, 1, 0);
    this.grid.add(this.idLabel, 1, 1);
    this.getChildren().add(this.grid);
  }
  
  private GridPane grid;
  
  private Label nameLabel;
  
  private Label idLabel;
  
  private StudentItemGraduation currentItem;
  
  public void display(final StudentItemGraduation item) {
    this.setVisible(true);
    this.currentItem = item;
    this.setName();
    this.setId();
  }
  
  public void clearDisplay() {
    this.setVisible(false);
  }
  
  private void setName() {
    this.nameLabel.setText("TOTO");
  }
  
  private void setId() {
    int _studentId = this.currentItem.getStudentId();
    String _plus = (Integer.valueOf(_studentId) + "");
    this.idLabel.setText(_plus);
  }
  
  public Object setupEvents() {
    return null;
  }
}
