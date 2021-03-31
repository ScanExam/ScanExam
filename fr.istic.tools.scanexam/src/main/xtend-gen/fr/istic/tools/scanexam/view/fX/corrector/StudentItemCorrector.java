package fr.istic.tools.scanexam.view.fX.corrector;

import fr.istic.tools.scanexam.view.fX.FXSettings;
import fr.istic.tools.scanexam.view.fX.corrector.StudentListCorrector;
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
public class StudentItemCorrector extends VBox {
  private int studentId;
  
  public StudentItemCorrector(final int studentId) {
    super();
    this.studentId = studentId;
    String _plus = (Integer.valueOf(studentId) + "");
    Label _label = new Label(_plus);
    this.name = _label;
    this.getChildren().add(this.name);
    this.getStyleClass().add("ListItem");
    this.setupEvents();
  }
  
  private StudentListCorrector list;
  
  private Label name;
  
  public StudentListCorrector setList(final StudentListCorrector list) {
    return this.list = list;
  }
  
  public int setStudentId(final int id) {
    return this.studentId = id;
  }
  
  public int getStudentId() {
    return this.studentId;
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
    final StudentItemCorrector item = this;
    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        StudentItemCorrector.this.list.getController().selectStudent(item);
      }
    });
  }
}
