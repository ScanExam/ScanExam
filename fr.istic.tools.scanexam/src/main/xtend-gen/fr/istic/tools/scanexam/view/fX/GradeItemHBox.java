package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.view.fX.NumberTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

@SuppressWarnings("all")
public class GradeItemHBox extends HBox {
  public GradeItemHBox(final int questionId) {
    super();
    Pane p1 = new Pane();
    p1.setMaxWidth(50);
    Pane p2 = new Pane();
    p2.setMaxWidth(50);
    this.getChildren().add(p1);
    this.getChildren().add(p2);
    this.questionId = questionId;
    Button _button = new Button("-");
    this.remove = _button;
    this.getChildren().add(this.remove);
    Label _label = new Label("Torchon");
    this.nameLabel = _label;
    String _text = this.nameLabel.getText();
    TextField _textField = new TextField(_text);
    this.nameTextField = _textField;
    this.nameTextField.setVisible(false);
    p1.getChildren().addAll(this.nameLabel, this.nameTextField);
    Label _label_1 = new Label("0");
    this.label = _label_1;
    NumberTextField _numberTextField = new NumberTextField();
    this.field = _numberTextField;
    this.field.setText(this.label.getText());
    this.field.setVisible(false);
    p2.getChildren().addAll(this.field, this.label);
  }
  
  private int questionId;
  
  private int gradeItemId;
  
  private Button remove;
  
  private NumberTextField field;
  
  private Label label;
  
  private Label nameLabel;
  
  private TextField nameTextField;
  
  public String getPoints() {
    return this.label.getText();
  }
  
  public void setButtonAction(final EventHandler<ActionEvent> handler) {
    this.remove.setOnAction(handler);
  }
  
  public int getGradeQuestionId() {
    return this.questionId;
  }
  
  public int getGradeItemId() {
    return this.gradeItemId;
  }
  
  public int setGradeItemId(final int id) {
    return this.gradeItemId = id;
  }
  
  public void setGradeItemName(final String name) {
    this.nameLabel.setText(name);
  }
  
  public String getGradeItemName() {
    return this.nameLabel.getText();
  }
  
  public String getGradeItemPoints() {
    return this.label.getText();
  }
  
  public void setGradeItemPoints(final String points) {
    this.label.setText((points + ""));
  }
  
  public void setChangePoints(final EventHandler<ActionEvent> handler) {
    ContextMenu menu = new ContextMenu();
    MenuItem renameMenu = new MenuItem("Change Points");
    renameMenu.setOnAction(handler);
    menu.getItems().add(renameMenu);
    this.label.setContextMenu(menu);
  }
  
  public void setChangeName(final EventHandler<ActionEvent> handler) {
    ContextMenu menu = new ContextMenu();
    MenuItem renameMenu = new MenuItem("Rename");
    renameMenu.setOnAction(handler);
    menu.getItems().add(renameMenu);
    this.nameLabel.setContextMenu(menu);
  }
  
  public void setNameCommit(final EventHandler<ActionEvent> handler) {
    this.nameTextField.setOnAction(handler);
  }
  
  public void setPointsCommit(final EventHandler<ActionEvent> handler) {
    this.field.setOnAction(handler);
  }
  
  public void setRemoveGradeItemAction(final EventHandler<ActionEvent> handler) {
    this.remove.setOnAction(handler);
  }
  
  public void toggleRenaming() {
    boolean _isVisible = this.nameTextField.isVisible();
    boolean _not = (!_isVisible);
    this.nameTextField.setVisible(_not);
    boolean _isVisible_1 = this.nameTextField.isVisible();
    if (_isVisible_1) {
      this.nameTextField.requestFocus();
    }
    this.nameTextField.selectAll();
    boolean _isVisible_2 = this.nameLabel.isVisible();
    boolean _not_1 = (!_isVisible_2);
    this.nameLabel.setVisible(_not_1);
  }
  
  public void togglePointChange() {
    boolean _isVisible = this.field.isVisible();
    boolean _not = (!_isVisible);
    this.field.setVisible(_not);
    boolean _isVisible_1 = this.field.isVisible();
    if (_isVisible_1) {
      this.field.requestFocus();
    }
    this.field.selectAll();
    boolean _isVisible_2 = this.label.isVisible();
    boolean _not_1 = (!_isVisible_2);
    this.label.setVisible(_not_1);
  }
  
  public String getPointFieldText() {
    return this.field.getText();
  }
  
  public String getNameFieldText() {
    return this.nameTextField.getText();
  }
}
