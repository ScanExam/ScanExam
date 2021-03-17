package fr.istic.tools.scanexam.view.fX.editor;

import fr.istic.tools.scanexam.view.fX.editor.EditorQuestionItem;
import fr.istic.tools.scanexam.view.fX.editor.GradeList;
import fr.istic.tools.scanexam.view.fX.editor.NumberTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

@SuppressWarnings("all")
public class GradeItem extends HBox {
  public GradeItem(final GradeList list, final EditorQuestionItem item) {
    super();
    this.questionItem = item;
    this.list = list;
    HBox h = new HBox();
    this.getChildren().add(h);
    HBox.setHgrow(h, Priority.ALWAYS);
    Button _button = new Button("-");
    this.remove = _button;
    this.getChildren().add(this.remove);
    TextField _textField = new TextField("Temp Grade Item");
    this.name = _textField;
    this.name.setEditable(false);
    NumberTextField _numberTextField = new NumberTextField();
    this.points = _numberTextField;
    this.points.setText("0");
    this.points.setEditable(false);
    h.getChildren().addAll(this.name, this.points);
    this.getStyleClass().add("GradeItemBox");
    this.setupContextMenu();
    this.setupEvents();
  }
  
  private GradeList list;
  
  private EditorQuestionItem questionItem;
  
  private int gradeItemId;
  
  private Button remove;
  
  private NumberTextField points;
  
  private TextField name;
  
  public String getPoints() {
    return this.points.getText();
  }
  
  public void setButtonAction(final EventHandler<ActionEvent> handler) {
    this.remove.setOnAction(handler);
  }
  
  public EditorQuestionItem getGradeQuestionItem() {
    return this.questionItem;
  }
  
  public int getGradeItemId() {
    return this.gradeItemId;
  }
  
  public int setGradeItemId(final int id) {
    return this.gradeItemId = id;
  }
  
  public void setGradeItemName(final String name) {
    this.name.setText(name);
  }
  
  public String getGradeItemName() {
    return this.name.getText();
  }
  
  public String getGradeItemPoints() {
    return this.points.getText();
  }
  
  public void setGradeItemPoints(final String points) {
    this.points.setText(points);
  }
  
  public void setNameEditable() {
    this.name.setEditable(true);
    this.name.selectAll();
  }
  
  public void setPointsEditable() {
    this.points.setEditable(true);
    this.points.selectAll();
  }
  
  public void commitNameChange() {
    this.name.setEditable(false);
  }
  
  public void commitPointsChange() {
    this.points.setEditable(false);
  }
  
  public void setupContextMenu() {
    {
      ContextMenu menu = new ContextMenu();
      this.name.setContextMenu(menu);
      MenuItem menuItem1 = new MenuItem("Rename Grade Item");
      menu.getItems().add(menuItem1);
      menuItem1.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(final ActionEvent event) {
          GradeItem.this.setNameEditable();
        }
      });
    }
    {
      ContextMenu menu = new ContextMenu();
      this.points.setContextMenu(menu);
      MenuItem menuItem1 = new MenuItem("Change points for Grade Item");
      menu.getItems().add(menuItem1);
      menuItem1.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(final ActionEvent event) {
          GradeItem.this.setPointsEditable();
        }
      });
    }
  }
  
  public void setupEvents() {
    final GradeItem item = this;
    this.name.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(final ActionEvent event) {
        GradeItem.this.commitNameChange();
      }
    });
    this.points.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(final ActionEvent event) {
        GradeItem.this.commitPointsChange();
      }
    });
    this.remove.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(final ActionEvent event) {
        GradeItem.this.list.removeGradeItem(item);
      }
    });
  }
}
