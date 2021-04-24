package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fx.editor.HTMLView;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.QuestionItemGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.StudentItemGraduation;
import java.io.InputStream;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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
      Label _label = new Label(
        "This is a test Grate entry name, here you will be able to add a description of this entry, and eventually have some latex/html");
      this.text = _label;
      this.text.setWrapText(true);
      this.text.setMaxWidth(130);
      Insets _insets = new Insets(0, 0, 0, 10);
      VBox.setMargin(this.text, _insets);
      String _text = this.text.getText();
      TextArea _textArea = new TextArea(_text);
      this.textArea = _textArea;
      this.textArea.setWrapText(true);
      this.textArea.setMaxWidth(130);
      Insets _insets_1 = new Insets(10, 0, 0, 10);
      VBox.setMargin(this.textArea, _insets_1);
      CheckBox _checkBox = new CheckBox();
      this.check = _checkBox;
      Label _label_1 = new Label("0.5");
      this.worth = _label_1;
      Insets _insets_2 = new Insets(0, 0, 0, 10);
      this.worth.setPadding(_insets_2);
      String _text_1 = this.worth.getText();
      TextField _textField = new TextField(_text_1);
      this.worthField = _textField;
      Insets _insets_3 = new Insets(0, 0, 0, 10);
      this.worthField.setPadding(_insets_3);
      this.worthField.setMaxWidth(25);
      this.worthField.getStyleClass().add("mytext-field");
      Button _button = new Button("Remove entry");
      this.remove = _button;
      Insets _insets_4 = new Insets(0, 0, 10, 0);
      VBox.setMargin(this, _insets_4);
      this.topRow.getChildren().addAll(this.check, this.worthField, this.remove);
      this.getChildren().addAll(this.topRow, this.textArea);
      this.setupEvents();
    }
    
    private int id;
    
    private HBox topRow;
    
    private Label text;
    
    private Label worth;
    
    private CheckBox check;
    
    private Grader grader;
    
    private TextArea textArea;
    
    private TextField worthField;
    
    private Button remove;
    
    public String getText() {
      return this.text.getText();
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
    
    /**
     * Change le text modifié par le HTML Editor
     */
    public void setText(final String text) {
      this.text.setText(text);
      this.textArea.setText(text);
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
    
    public boolean enterEditMode() {
      boolean _xblockexpression = false;
      {
        this.topRow.getChildren().remove(this.worth);
        this.topRow.getChildren().add(this.worthField);
        this.topRow.getChildren().add(this.remove);
        this.getChildren().remove(this.text);
        _xblockexpression = this.getChildren().add(this.textArea);
      }
      return _xblockexpression;
    }
    
    public boolean leaveEditMode() {
      boolean _xblockexpression = false;
      {
        this.topRow.getChildren().remove(this.worthField);
        this.topRow.getChildren().remove(this.remove);
        this.topRow.getChildren().add(this.worth);
        this.getChildren().remove(this.textArea);
        _xblockexpression = this.getChildren().add(this.text);
      }
      return _xblockexpression;
    }
    
    public boolean commitChanges() {
      boolean _xblockexpression = false;
      {
        this.text.setText(this.textArea.getText());
        this.worth.setText(this.worthField.getText());
        _xblockexpression = this.leaveEditMode();
      }
      return _xblockexpression;
    }
    
    public void setupEvents() {
      final Grader.GradeItem me = this;
      this.check.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
          boolean _isSelected = GradeItem.this.check.isSelected();
          if (_isSelected) {
            GradeItem.this.grader.addPoints(me);
          } else {
            GradeItem.this.grader.removePoints(me);
          }
        }
      });
      this.remove.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
          GradeItem.this.grader.removeGradeEntry(me);
        }
      });
      this.text.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Editeur HTML");
        InputStream inputLayout = ResourcesUtils.getInputStreamResource("viewResources/HTML.FXML");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.<Parent>load(inputLayout);
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
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
    VBox _vBox = new VBox();
    this.itemContainer = _vBox;
    Button _button = new Button("Add new Grade Entry");
    this.add = _button;
    this.add.getStyleClass().add("InfinityButton");
    Button _button_1 = new Button("Toggle Editable");
    this.editMode = _button_1;
    this.editMode.getStyleClass().add("InfinityButton");
    scrollp.setContent(this.itemContainer);
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
      List<Integer> sids = this.controller.getSelectedEntryIds(qItem.getQuestionId());
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
            this.addPointsOf(g);
          } else {
            g.setSelected(Boolean.valueOf(false));
          }
          g.leaveEditMode();
        }
      }
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
    boolean _selected = item.getSelected();
    if (_selected) {
      this.removePointsOf(item);
    }
    this.removeEntryFromModel(item, this.controller.getQuestionList().getCurrentItem());
  }
  
  public void addPointsOf(final Grader.GradeItem item) {
    this.currentPoints.setText("FIX");
  }
  
  public void removePointsOf(final Grader.GradeItem item) {
    this.currentPoints.setText("FIX");
  }
  
  public void addEntryToModel(final Grader.GradeItem item, final QuestionItemGraduation qItem) {
    item.setItemId(this.controller.addEntry(qItem.getQuestionId(), item.getText(), 
      Float.parseFloat(item.getWorth())));
  }
  
  public void updateEntryInModel(final Grader.GradeItem item, final QuestionItemGraduation qItem) {
    this.controller.modifyEntry(qItem.getQuestionId(), item.getItemId(), item.getText(), 
      Float.parseFloat(item.getWorth()));
  }
  
  public void removeEntryFromModel(final Grader.GradeItem item, final QuestionItemGraduation qItem) {
    this.controller.removeEntry(qItem.getQuestionId(), item.id);
  }
  
  public boolean addPoints(final Grader.GradeItem item) {
    boolean _xblockexpression = false;
    {
      int _studentId = this.controller.getStudentList().getCurrentItem().getStudentId();
      String _plus = ("Adding points for Student ID :" + Integer.valueOf(_studentId));
      String _plus_1 = (_plus + ", for Questions ID :");
      int _questionId = this.controller.getQuestionList().getCurrentItem().getQuestionId();
      String _plus_2 = (_plus_1 + Integer.valueOf(_questionId));
      String _plus_3 = (_plus_2 + ", for Entry ID :");
      String _plus_4 = (_plus_3 + Integer.valueOf(item.id));
      Grader.logger.info(_plus_4);
      this.addPointsOf(item);
      _xblockexpression = this.controller.applyGrade(this.controller.getQuestionList().getCurrentItem().getQuestionId(), item.id);
    }
    return _xblockexpression;
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
    this.removePointsOf(item);
    this.controller.removeGrade(this.controller.getQuestionList().getCurrentItem().getQuestionId(), item.id);
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
          ObservableList<Node> _children = this.itemContainer.getChildren();
          for (final Node n : _children) {
            {
              ((Grader.GradeItem) n).commitChanges();
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
  }
}
