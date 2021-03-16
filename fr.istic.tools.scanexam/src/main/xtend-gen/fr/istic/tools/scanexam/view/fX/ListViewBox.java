package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.view.fX.Box;
import fr.istic.tools.scanexam.view.fX.GradeItemHBox;
import fr.istic.tools.scanexam.view.fX.NumberTextField;
import java.util.LinkedList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class ListViewBox extends VBox {
  public ListViewBox(final String text, final Box parent) {
    super();
    HBox _hBox = new HBox();
    this.top = _hBox;
    HBox _hBox_1 = new HBox();
    this.middle = _hBox_1;
    HBox _hBox_2 = new HBox();
    this.bottom = _hBox_2;
    this.id = parent.getBoxId();
    this.parent = parent;
    TextField _textField = new TextField(text);
    this.field = _textField;
    this.field.setMaxWidth(75);
    this.field.setVisible(false);
    Label _label = new Label(text);
    this.nameLabel = _label;
    this.nameLabel.setMinWidth(100);
    Button _button = new Button("+");
    this.addGradeItem = _button;
    VBox _vBox = new VBox();
    this.gradeItemContainer = _vBox;
    Pane p = new Pane();
    this.getChildren().addAll(this.top, this.middle, this.bottom);
    this.top.getChildren().addAll(p);
    this.middle.getChildren().addAll(this.addGradeItem, this.gradeItemContainer);
    p.getChildren().addAll(this.field, this.nameLabel);
    this.makeButtons();
  }
  
  private Label nameLabel;
  
  private Label pointsLabel;
  
  private TextField field;
  
  private NumberTextField numberField;
  
  private HBox top;
  
  private HBox middle;
  
  private HBox bottom;
  
  private int id;
  
  private Box parent;
  
  private Button up;
  
  private Button down;
  
  private Button rm;
  
  private Button resize;
  
  private Button move;
  
  private Button addGradeItem;
  
  private VBox gradeItemContainer;
  
  public Box getParentBox() {
    return this.parent;
  }
  
  public int getBoxId() {
    return this.id;
  }
  
  public boolean makeButtons() {
    boolean _xblockexpression = false;
    {
      Button _button = new Button("remove");
      this.rm = _button;
      Button _button_1 = new Button("resize");
      this.resize = _button_1;
      Button _button_2 = new Button("move");
      this.move = _button_2;
      this.bottom.getChildren().add(this.rm);
      this.bottom.getChildren().add(this.resize);
      _xblockexpression = this.bottom.getChildren().add(this.move);
    }
    return _xblockexpression;
  }
  
  public void setUpAction(final EventHandler<ActionEvent> handler) {
    this.up.setOnAction(handler);
  }
  
  public void setDownAction(final EventHandler<ActionEvent> handler) {
    this.down.setOnAction(handler);
  }
  
  public void setRemoveAction(final EventHandler<ActionEvent> handler) {
    this.rm.setOnAction(handler);
  }
  
  public void setTextCommit(final EventHandler<ActionEvent> handler) {
    this.field.setOnAction(handler);
  }
  
  public void setRenameOption(final EventHandler<ActionEvent> handler) {
    ContextMenu menu = new ContextMenu();
    MenuItem renameMenu = new MenuItem("Rename");
    renameMenu.setOnAction(handler);
    menu.getItems().add(renameMenu);
    this.nameLabel.setContextMenu(menu);
  }
  
  public void setChangePoints(final EventHandler<ActionEvent> handler) {
  }
  
  public void setAddGradeItemAction(final EventHandler<ActionEvent> handler) {
    this.addGradeItem.setOnAction(handler);
  }
  
  public void setMoveAction(final EventHandler<ActionEvent> hander) {
    this.move.setOnAction(hander);
  }
  
  public void setResizeAction(final EventHandler<ActionEvent> hander) {
    this.resize.setOnAction(hander);
  }
  
  public void setLabelText(final String text) {
    this.nameLabel.setText(text);
  }
  
  public String getName() {
    return this.nameLabel.getText();
  }
  
  public void toggleRenaming() {
    boolean _isVisible = this.field.isVisible();
    boolean _not = (!_isVisible);
    this.field.setVisible(_not);
    boolean _isVisible_1 = this.field.isVisible();
    if (_isVisible_1) {
      this.field.requestFocus();
    }
    this.field.selectAll();
    boolean _isVisible_2 = this.nameLabel.isVisible();
    boolean _not_1 = (!_isVisible_2);
    this.nameLabel.setVisible(_not_1);
  }
  
  public void togglePointChange() {
    boolean _isVisible = this.numberField.isVisible();
    boolean _not = (!_isVisible);
    this.numberField.setVisible(_not);
    boolean _isVisible_1 = this.numberField.isVisible();
    if (_isVisible_1) {
      this.numberField.requestFocus();
    }
    this.numberField.selectAll();
    boolean _isVisible_2 = this.pointsLabel.isVisible();
    boolean _not_1 = (!_isVisible_2);
    this.pointsLabel.setVisible(_not_1);
  }
  
  public Label getNameLabel() {
    return this.nameLabel;
  }
  
  public boolean addGradeItem(final GradeItemHBox toAdd) {
    return this.gradeItemContainer.getChildren().add(toAdd);
  }
  
  public boolean removeGradeItem(final GradeItemHBox toRemove) {
    return this.gradeItemContainer.getChildren().remove(toRemove);
  }
  
  public Object removeGradeItem(final int idToRemove) {
    return null;
  }
  
  public LinkedList<GradeItemHBox> getGradeItems() {
    LinkedList<GradeItemHBox> result = new LinkedList<GradeItemHBox>();
    ObservableList<Node> _children = this.gradeItemContainer.getChildren();
    for (final Node node : _children) {
      result.add(((GradeItemHBox) node));
    }
    return result;
  }
}
