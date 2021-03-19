package fr.istic.tools.scanexam.view.fX.editor;

import fr.istic.tools.scanexam.view.fX.FXSettings;
import fr.istic.tools.scanexam.view.fX.editor.Box;
import fr.istic.tools.scanexam.view.fX.editor.BoxType;
import fr.istic.tools.scanexam.view.fX.editor.QuestionList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

@SuppressWarnings("all")
public class EditorQuestionItem extends VBox {
  public EditorQuestionItem(final QuestionList list, final Box zone) {
    super();
    this.init();
    this.list = list;
    this.zone = zone;
    this.zone.setQuestionItem(this);
    TextField _textField = new TextField("New Question");
    this.name = _textField;
    this.name.setEditable(false);
    this.getChildren().addAll(this.top, this.middle, this.bottom);
    this.top.getChildren().addAll(this.name);
    VBox.setVgrow(this, Priority.ALWAYS);
    zone.setupEvents();
    this.setupContextMenu();
    this.setupEvents(this);
  }
  
  public EditorQuestionItem(final QuestionList list, final Box zone, final BoxType type, final int page) {
    this(list, zone);
    this.type = type;
    this.page = page;
  }
  
  public boolean init() {
    boolean _xblockexpression = false;
    {
      HBox _hBox = new HBox();
      this.top = _hBox;
      HBox _hBox_1 = new HBox();
      this.middle = _hBox_1;
      HBox _hBox_2 = new HBox();
      this.bottom = _hBox_2;
      Button _button = new Button("remove");
      this.remove = _button;
      _xblockexpression = this.bottom.getChildren().add(this.remove);
    }
    return _xblockexpression;
  }
  
  private Box zone;
  
  private TextField name;
  
  private HBox top;
  
  private HBox middle;
  
  private HBox bottom;
  
  private Button remove;
  
  private QuestionList list;
  
  private int questionId;
  
  private int page;
  
  private BoxType type;
  
  public Box getZone() {
    return this.zone;
  }
  
  public int getQuestionId() {
    return this.questionId;
  }
  
  public int setQuestionId(final int id) {
    return this.questionId = id;
  }
  
  public int getPage() {
    return this.page;
  }
  
  public int setPage(final int page) {
    return this.page = page;
  }
  
  public void setFocus(final boolean b) {
    if (b) {
      this.setColor(FXSettings.ITEM_HIGHLIGHT_COLOR);
      this.zone.setFocus(b);
    } else {
      this.setColor(FXSettings.ITEM_NORMAL_COLOR);
      this.zone.setFocus(b);
    }
  }
  
  public void setColor(final Color color) {
    BackgroundFill bf = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
    Background _background = new Background(bf);
    this.setBackground(_background);
  }
  
  public void setName(final String text) {
    this.name.setText(text);
  }
  
  public String getName() {
    return this.name.getText();
  }
  
  public void setNameEditable() {
    this.name.setEditable(true);
    this.name.selectAll();
  }
  
  public void commitNameChange() {
    this.name.setEditable(false);
    this.list.updateInModel(this);
  }
  
  public void setupContextMenu() {
    ContextMenu menu = new ContextMenu();
    this.name.setContextMenu(menu);
    MenuItem menuItem1 = new MenuItem("Rename Question Item");
    menu.getItems().add(menuItem1);
    menuItem1.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        EditorQuestionItem.this.setNameEditable();
      }
    });
  }
  
  public void setupEvents(final EditorQuestionItem item) {
    this.remove.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        EditorQuestionItem.this.list.removeQuestion(item);
        EditorQuestionItem.this.list.getController().selectQuestion(null);
      }
    });
    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        EditorQuestionItem.this.list.getController().selectQuestion(item);
      }
    });
    this.name.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        EditorQuestionItem.this.commitNameChange();
      }
    });
  }
}
