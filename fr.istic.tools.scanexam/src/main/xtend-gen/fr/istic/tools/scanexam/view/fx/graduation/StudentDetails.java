package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.view.fx.component.RenameFieldSuggests;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.StudentItemGraduation;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class StudentDetails extends VBox {
  public StudentDetails(final ControllerFxGraduation controller) {
    this.controller = controller;
    Label nameRow = new Label("Student name :");
    Label idRow = new Label("Student Id :");
    GridPane _gridPane = new GridPane();
    this.grid = _gridPane;
    this.grid.add(nameRow, 0, 0);
    this.grid.add(idRow, 0, 1);
    RenameFieldSuggests _renameFieldSuggests = new RenameFieldSuggests();
    this.name = _renameFieldSuggests;
    Label _label = new Label();
    this.idLabel = _label;
    this.grid.add(this.name, 1, 0);
    this.grid.add(this.idLabel, 1, 1);
    this.getChildren().add(this.grid);
    this.setupEvents();
  }
  
  private GridPane grid;
  
  private RenameFieldSuggests name;
  
  private Label idLabel;
  
  private ControllerFxGraduation controller;
  
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
    this.name.setText(this.currentItem.getStudentName());
  }
  
  private void setId() {
    int _studentId = this.currentItem.getStudentId();
    String _plus = (Integer.valueOf(_studentId) + "");
    this.idLabel.setText(_plus);
  }
  
  public void commitRename() {
    String _text = this.name.getText();
    String _plus = ("Renaming to" + _text);
    InputOutput.<String>println(_plus);
    this.currentItem.setStudentName(this.name.getText());
    this.controller.getStudentList().updateInModel(this.currentItem);
  }
  
  public void findSuggestions(final String start) {
    InputOutput.<String>println("Changing");
    List<String> l = this.controller.getPresenter().getStudentsSuggestedNames(start);
    this.name.showSuggestion(l);
  }
  
  public void setupEvents() {
    final ChangeListener<String> _function = (ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
      this.commitRename();
    };
    this.name.getTextProperty().addListener(_function);
    final ChangeListener<String> _function_1 = (ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
      this.findSuggestions(newVal);
    };
    this.name.getFieldTextProperty().addListener(_function_1);
  }
}
