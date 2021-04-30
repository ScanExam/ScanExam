package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.io.InputStream;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class Grader extends VBox {
  public static class GradeItem extends VBox {
    public GradeItem(final Grader grader) {
      this.grader = grader;
      HBox _hBox = new HBox();
      this.topRow = _hBox;
      StackPane _stackPane = new StackPane();
      this.stackPane = _stackPane;
      this.text = LanguageManager.translate("grader.defaultText");
      WebView _webView = new WebView();
      this.webView = _webView;
      this.webEngine = this.webView.getEngine();
      this.webEngine.loadContent(this.text);
      this.webView.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
      this.webView.setMinSize(100, 100);
      this.stackPane.getChildren().add(this.webView);
      CheckBox _checkBox = new CheckBox();
      this.check = _checkBox;
      Label _label = new Label("1");
      this.worth = _label;
      String _text = this.worth.getText();
      TextField _textField = new TextField(_text);
      this.worthField = _textField;
      this.worthField.getStyleClass().add("mytext-field");
      String _translate = LanguageManager.translate("grader.button.removeEntry");
      Button _button = new Button(_translate);
      this.remove = _button;
      this.topRow.getChildren().addAll(this.check, this.worthField);
      this.getChildren().addAll(this.topRow, this.stackPane, this.remove);
      this.topRow.getStyleClass().add("GradeItemTopRow");
      this.webView.getStyleClass().add("WebView");
      this.getStyleClass().add("GradeItem");
      this.setAlignment(Pos.CENTER);
      this.setupEvents();
    }
    
    private int id;
    
    private String text;
    
    private HBox topRow;
    
    private Label worth;
    
    private CheckBox check;
    
    private Grader grader;
    
    private StackPane stackPane;
    
    private WebView webView;
    
    private WebEngine webEngine;
    
    private TextField worthField;
    
    private Button remove;
    
    public String getText() {
      return this.text;
    }
    
    public String getWorth() {
      return this.worth.getText();
    }
    
    public int getItemId() {
      return this.id;
    }
    
    public int setItemId(final int id) {
      return this.id = id;
    }
    
    public boolean displayError() {
      boolean _xblockexpression = false;
      {
        this.check.getStyleClass().remove("goodCheckBox");
        _xblockexpression = this.check.getStyleClass().add("badCheckBox");
      }
      return _xblockexpression;
    }
    
    public boolean displaySuccess() {
      boolean _xblockexpression = false;
      {
        this.check.getStyleClass().remove("badCheckBox");
        _xblockexpression = this.check.getStyleClass().add("goodCheckBox");
      }
      return _xblockexpression;
    }
    
    public boolean displayDefault() {
      boolean _xblockexpression = false;
      {
        this.check.getStyleClass().remove("badCheckBox");
        _xblockexpression = this.check.getStyleClass().remove("goodCheckBox");
      }
      return _xblockexpression;
    }
    
    /**
     * Change le text modifié par le HTML Editor
     */
    public void setText(final String text) {
      this.text = text;
      this.webEngine.loadContent(text);
    }
    
    public void changeText(final String text) {
      this.setText(text);
      this.grader.updateEntryInModel(this, this.grader.controller.getQuestionList().getCurrentItem());
    }
    
    public void setWorth(final float worth) {
      String _plus = (Float.valueOf(worth) + "");
      this.worth.setText(_plus);
      String _plus_1 = (Float.valueOf(worth) + "");
      this.worthField.setText(_plus_1);
    }
    
    public void setSelected(final Boolean b) {
      this.check.setSelected((b).booleanValue());
    }
    
    public boolean getSelected() {
      return this.check.isSelected();
    }
    
    public boolean getCheckDisabled() {
      return this.check.isDisabled();
    }
    
    public boolean enterEditMode() {
      boolean _xblockexpression = false;
      {
        this.topRow.getChildren().remove(this.worth);
        this.topRow.getChildren().add(this.worthField);
        this.check.setDisable(true);
        _xblockexpression = this.getChildren().add(this.remove);
      }
      return _xblockexpression;
    }
    
    public void leaveEditMode() {
      this.topRow.getChildren().remove(this.worthField);
      this.getChildren().remove(this.remove);
      this.topRow.getChildren().add(this.worth);
      this.check.setDisable(false);
      this.worth.setText(this.worthField.getText());
    }
    
    public void checkBoxUsed() {
      boolean _isSelected = this.check.isSelected();
      if (_isSelected) {
        this.grader.addPoints(this);
      } else {
        this.grader.removePoints(this);
      }
    }
    
    public void setupEvents() {
      final Grader.GradeItem me = this;
      this.check.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
          GradeItem.this.checkBoxUsed();
        }
      });
      this.remove.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
          GradeItem.this.grader.removeGradeEntry(me);
        }
      });
      this.webView.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(final MouseEvent event) {
          if ((!HTMLView.isHTMLEditorOpen)) {
            boolean _equals = event.getButton().equals(MouseButton.PRIMARY);
            if (_equals) {
              int _clickCount = event.getClickCount();
              boolean _equals_1 = (_clickCount == 2);
              if (_equals_1) {
                GradeItem.this.renderHTMLView();
              }
            }
          }
        }
      });
    }
    
    /**
     * Render the HTML editor
     */
    public void renderHTMLView() {
      try {
        HTMLView.isHTMLEditorOpen = true;
        HTMLView.item = this;
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Editeur HTML");
        InputStream inputLayout = ResourcesUtils.getInputStreamResource("viewResources/HTML.FXML");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.<Parent>load(inputLayout);
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        HTMLView.stage = stage;
        stage.show();
        final EventHandler<WindowEvent> _function = (WindowEvent event) -> {
          HTMLView.isHTMLEditorOpen = false;
        };
        stage.setOnHiding(_function);
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    }
  }
  
  private static final Logger logger = LogManager.getLogger();
  
  public Grader(final ControllerFxGraduation controller) {
    this.controller = controller;
    Label _label = new Label("0");
    this.currentPoints = _label;
    Label slash = new Label("/");
    Label _label_1 = new Label("0");
    this.maxPoints = _label_1;
    HBox pointsBox = new HBox();
    pointsBox.getChildren().addAll(this.currentPoints, slash, this.maxPoints);
    this.editable = false;
    ScrollPane scrollp = new ScrollPane();
    scrollp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollp.getStyleClass().add("GradeList");
    scrollp.setFitToWidth(true);
    VBox _vBox = new VBox();
    this.itemContainer = _vBox;
    String _translate = LanguageManager.translate("grader.button.addEntry");
    Button _button = new Button(_translate);
    this.add = _button;
    this.add.getStyleClass().add("InfinityButton");
    String _translate_1 = LanguageManager.translate("grader.button.enterEdit");
    Button _button_1 = new Button(_translate_1);
    this.editMode = _button_1;
    this.editMode.getStyleClass().add("InfinityButton");
    scrollp.setContent(this.itemContainer);
    this.setCursor(Cursor.MOVE);
    this.itemContainer.setCursor(Cursor.DEFAULT);
    this.add.setCursor(Cursor.DEFAULT);
    this.editMode.setCursor(Cursor.DEFAULT);
    this.currentPoints.setCursor(Cursor.DEFAULT);
    this.maxPoints.setCursor(Cursor.DEFAULT);
    scrollp.setCursor(Cursor.DEFAULT);
    this.getChildren().addAll(pointsBox, scrollp, this.editMode);
    this.setPrefWidth(170);
    this.setMaxHeight(500);
    this.setPrefHeight(Region.USE_COMPUTED_SIZE);
    this.getStyleClass().add("Grader");
    this.setupEvents();
  }
  
  private Label currentPoints;
  
  private Label maxPoints;
  
  private Button add;
  
  private Button editMode;
  
  private VBox itemContainer;
  
  private ControllerFxGraduation controller;
  
  private boolean editable;
  
  private QuestionItemGraduation curentQuestion;
  
  private StudentItemGraduation currentStudent;
  
  public void changeGrader(final QuestionItemGraduation qItem, final StudentItemGraduation sItem) {
    this.clearDisplay();
    this.curentQuestion = qItem;
    this.currentStudent = sItem;
    if (((this.curentQuestion != null) && (this.currentStudent != null))) {
      float _worth = qItem.getWorth();
      String _plus = (Float.valueOf(_worth) + "");
      this.maxPoints.setText(_plus);
      List<Integer> ids = this.controller.getEntryIds(qItem.getQuestionId());
      int _size = ids.size();
      String _plus_1 = ("All ids are :" + Integer.valueOf(_size));
      Grader.logger.info(_plus_1);
      List<Integer> sids = this.controller.getSelectedEntryIds(qItem.getQuestionId());
      int _size_1 = sids.size();
      String _plus_2 = ("selected ids are :" + Integer.valueOf(_size_1));
      Grader.logger.info(_plus_2);
      for (final Integer i : ids) {
        {
          Grader.GradeItem g = new Grader.GradeItem(this);
          g.setItemId((i).intValue());
          g.setText(this.controller.getEntryText((i).intValue(), qItem.getQuestionId()));
          g.setWorth(this.controller.getEntryWorth((i).intValue(), qItem.getQuestionId()));
          this.itemContainer.getChildren().add(g);
          boolean _contains = sids.contains(i);
          if (_contains) {
            g.setSelected(Boolean.valueOf(true));
          } else {
            g.setSelected(Boolean.valueOf(false));
          }
          g.leaveEditMode();
        }
      }
      this.updateCurrentPoints();
    } else {
      Grader.logger.warn("The current Question or current Student is null");
    }
  }
  
  public void createNewGradeEntry() {
    Grader.logger.info("Creating new GradeEntry");
    Grader.GradeItem entry = new Grader.GradeItem(this);
    this.itemContainer.getChildren().add(entry);
    this.addEntryToModel(entry, this.controller.getQuestionList().getCurrentItem());
  }
  
  public void removeGradeEntry(final Grader.GradeItem item) {
    Grader.logger.log(Level.INFO, "Removing GradeEntry");
    this.itemContainer.getChildren().remove(item);
    this.removeEntryFromModel(item, this.controller.getQuestionList().getCurrentItem());
    this.updateCurrentPoints();
  }
  
  public void updateCurrentPoints() {
    double _questionSelectedGradeEntriesTotalWorth = this.controller.getService().getQuestionSelectedGradeEntriesTotalWorth(this.controller.getQuestionList().getCurrentItem().getQuestionId());
    String _plus = ("" + Double.valueOf(_questionSelectedGradeEntriesTotalWorth));
    this.currentPoints.setText(_plus);
  }
  
  public void addEntryToModel(final Grader.GradeItem item, final QuestionItemGraduation qItem) {
    item.setItemId(this.controller.addEntry(qItem.getQuestionId(), item.getText(), Float.parseFloat(item.getWorth())));
  }
  
  /**
   * Modifier un item du barême
   */
  public void updateEntryInModel(final Grader.GradeItem item, final QuestionItemGraduation qItem) {
    Grader.logger.log(Level.INFO, "Updating GradeEntry");
    this.controller.modifyEntry(qItem.getQuestionId(), item.getItemId(), item.getText(), Float.parseFloat(item.getWorth()));
  }
  
  public Object updateEntryWorthInModel() {
    return null;
  }
  
  public void removeEntryFromModel(final Grader.GradeItem item, final QuestionItemGraduation qItem) {
    this.controller.removeEntry(qItem.getQuestionId(), item.id);
  }
  
  public void addPoints(final Grader.GradeItem item) {
    int _studentId = this.controller.getStudentList().getCurrentItem().getStudentId();
    String _plus = ("Adding points for Student ID :" + Integer.valueOf(_studentId));
    String _plus_1 = (_plus + ", for Questions ID :");
    int _questionId = this.controller.getQuestionList().getCurrentItem().getQuestionId();
    String _plus_2 = (_plus_1 + Integer.valueOf(_questionId));
    String _plus_3 = (_plus_2 + ", for Entry ID :");
    String _plus_4 = (_plus_3 + Integer.valueOf(item.id));
    Grader.logger.info(_plus_4);
    boolean over = this.controller.applyGrade(this.controller.getQuestionList().getCurrentItem().getQuestionId(), item.id);
    if (over) {
      item.displaySuccess();
      this.updateCurrentPoints();
    } else {
      item.displayError();
      item.setSelected(Boolean.valueOf(false));
    }
  }
  
  public void removePoints(final Grader.GradeItem item) {
    int _studentId = this.controller.getStudentList().getCurrentItem().getStudentId();
    String _plus = ("Removing points for Student ID :" + Integer.valueOf(_studentId));
    String _plus_1 = (_plus + ", for Questions ID :");
    int _questionId = this.controller.getQuestionList().getCurrentItem().getQuestionId();
    String _plus_2 = (_plus_1 + Integer.valueOf(_questionId));
    String _plus_3 = (_plus_2 + ", for Entry ID :");
    String _plus_4 = (_plus_3 + Integer.valueOf(item.id));
    Grader.logger.log(Level.INFO, _plus_4);
    boolean over = this.controller.removeGrade(this.controller.getQuestionList().getCurrentItem().getQuestionId(), item.id);
    if (over) {
      item.displaySuccess();
      this.updateCurrentPoints();
    } else {
      item.displayError();
      item.setSelected(Boolean.valueOf(true));
    }
  }
  
  public void clearDisplay() {
    this.itemContainer.getChildren().clear();
  }
  
  public boolean toggleEditMode(final boolean active) {
    boolean _xblockexpression = false;
    {
      this.editable = (!this.editable);
      boolean _xifexpression = false;
      if (active) {
        boolean _xblockexpression_1 = false;
        {
          this.editMode.setText(LanguageManager.translate("grader.button.leaveEdit"));
          ObservableList<Node> _children = this.itemContainer.getChildren();
          for (final Node n : _children) {
            ((Grader.GradeItem) n).enterEditMode();
          }
          _xblockexpression_1 = this.getChildren().add(this.add);
        }
        _xifexpression = _xblockexpression_1;
      } else {
        boolean _xblockexpression_2 = false;
        {
          this.editMode.setText(LanguageManager.translate("grader.button.enterEdit"));
          ObservableList<Node> _children = this.itemContainer.getChildren();
          for (final Node n : _children) {
            {
              ((Grader.GradeItem) n).leaveEditMode();
              this.updateEntryInModel(((Grader.GradeItem) n), this.controller.getQuestionList().getCurrentItem());
            }
          }
          _xblockexpression_2 = this.getChildren().remove(this.add);
        }
        _xifexpression = _xblockexpression_2;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public void interactUsingIndex(final int index) {
    if ((index < 1)) {
      Grader.logger.info("Cant select an entry below 1");
      return;
    }
    int _size = this.itemContainer.getChildren().size();
    boolean _greaterThan = (index > _size);
    if (_greaterThan) {
      int _size_1 = this.itemContainer.getChildren().size();
      String _plus = ((("Cant select entry with index :" + Integer.valueOf(index)) + ", there is only ") + Integer.valueOf(_size_1));
      String _plus_1 = (_plus + " entries in the grader");
      Grader.logger.info(_plus_1);
      return;
    }
    Node _get = this.itemContainer.getChildren().get((index - 1));
    Grader.GradeItem item = ((Grader.GradeItem) _get);
    boolean _checkDisabled = item.getCheckDisabled();
    boolean _not = (!_checkDisabled);
    if (_not) {
      boolean _selected = item.getSelected();
      boolean _not_1 = (!_selected);
      item.setSelected(Boolean.valueOf(_not_1));
      item.checkBoxUsed();
    }
  }
  
  public void setupEvents() {
    this.add.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        Grader.this.createNewGradeEntry();
      }
    });
    this.editMode.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        Grader.this.toggleEditMode((!Grader.this.editable));
      }
    });
    final EventHandler<MouseEvent> _function = (MouseEvent event) -> {
      this.controller.setCurrentTool(ControllerFxGraduation.SelectedTool.MOVE_GRADER_TOOL);
    };
    this.setOnMousePressed(_function);
  }
}
