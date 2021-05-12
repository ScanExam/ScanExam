package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.fx.FxSettings;
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
    this.studentId = studentId;
    String _translate = LanguageManager.translate("name.default");
    Label _label = new Label(_translate);
    this.name = _label;
    this.getChildren().addAll(this.name);
    this.getStyleClass().add("ListItem");
    this.setupEvents();
  }
  
  private StudentListGraduation list;
  
  private Label name;
  
  private int studentId;
  
  public StudentListGraduation setList(final StudentListGraduation list) {
    return this.list = list;
  }
  
  public int setStudentId(final int id) {
    return this.studentId = id;
  }
  
  public int getStudentId() {
    return this.studentId;
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
      this.name.getStyleClass().add("focusedText");
    } else {
      this.setColor(FxSettings.ITEM_NORMAL_COLOR);
      this.name.getStyleClass().remove("focusedText");
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
