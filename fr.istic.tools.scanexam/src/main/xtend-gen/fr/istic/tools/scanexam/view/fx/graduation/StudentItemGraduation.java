package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.view.fx.FxSettings;
import fr.istic.tools.scanexam.view.fx.graduation.StudentListGraduation;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

@SuppressWarnings("all")
public class StudentItemGraduation extends HBox {
  public StudentItemGraduation(final int studentId) {
    super();
    String _plus = (Integer.valueOf(studentId) + "");
    Label _label = new Label(_plus);
    this.studentId = _label;
    Label _label_1 = new Label("NOT SET");
    this.name = _label_1;
    this.getChildren().addAll(this.studentId, this.name);
    this.getStyleClass().add("ListItem");
    this.setupEvents();
  }
  
  private StudentListGraduation list;
  
  private Label name;
  
  private Label studentId;
  
  public StudentListGraduation setList(final StudentListGraduation list) {
    return this.list = list;
  }
  
  public void setStudentId(final int id) {
    String _plus = (Integer.valueOf(id) + "");
    this.studentId.setText(_plus);
  }
  
  public int getStudentId() {
    return Integer.parseInt(this.studentId.getText());
  }
  
  public String getStudentName() {
    return this.name.getText();
  }
  
  public void setStudentName(final String name) {
    this.name.setText(name);
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
    final StudentItemGraduation item = this;
    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        StudentItemGraduation.this.list.getController().selectStudent(item);
      }
    });
  }
}
