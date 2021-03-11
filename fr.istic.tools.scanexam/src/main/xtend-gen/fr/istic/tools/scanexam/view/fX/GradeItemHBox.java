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
  public GradeItemHBox() {
    super();
    Pane p = new Pane();
    this.getChildren().add(p);
    Button _button = new Button("-");
    this.remove = _button;
    this.getChildren().add(this.remove);
    NumberTextField _numberTextField = new NumberTextField();
    this.field = _numberTextField;
    this.field.setVisible(false);
    Label _label = new Label("0");
    this.label = _label;
    p.getChildren().addAll(this.field, this.label);
  }
  
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
  
  public void setGradeItemPoints(final double points) {
    String _plus = (Double.valueOf(points) + "");
    this.label.setText(_plus);
  }
  
  public void setChangePoints(final EventHandler<ActionEvent> handler) {
    ContextMenu menu = new ContextMenu();
    MenuItem renameMenu = new MenuItem("Change Points");
    renameMenu.setOnAction(handler);
    menu.getItems().add(renameMenu);
    this.label.setContextMenu(menu);
  }
  
  public void setRemoveGradeItemAction(final EventHandler<ActionEvent> handler) {
    this.remove.setOnAction(handler);
  }
}
