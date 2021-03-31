package fr.istic.tools.scanexam.view.fX.corrector;

import fr.istic.tools.scanexam.presenter.GraduationPresenter;
import fr.istic.tools.scanexam.presenter.PresenterMarkingScheme;
import fr.istic.tools.scanexam.view.fX.GraduationAdapterFX;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Grader extends VBox {
  public static class GradeItem extends VBox {
    public GradeItem(final Grader grader) {
      this.grader = grader;
      HBox _hBox = new HBox();
      this.topRow = _hBox;
      Label _label = new Label("This is a test Grate entry name, here you will be able to add a description of this entry, and eventually have some latec/html");
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
      Label _label_1 = new Label("5.0");
      this.worth = _label_1;
      Insets _insets_2 = new Insets(0, 0, 0, 10);
      this.worth.setPadding(_insets_2);
      String _text_1 = this.worth.getText();
      TextField _textField = new TextField(_text_1);
      this.worthField = _textField;
      Insets _insets_3 = new Insets(0, 0, 0, 10);
      this.worthField.setPadding(_insets_3);
      this.worthField.setMaxWidth(25);
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
        this.commitChanges();
        this.topRow.getChildren().remove(this.worthField);
        this.topRow.getChildren().remove(this.remove);
        this.topRow.getChildren().add(this.worth);
        this.getChildren().remove(this.textArea);
        _xblockexpression = this.getChildren().add(this.text);
      }
      return _xblockexpression;
    }
    
    public void commitChanges() {
      this.text.setText(this.textArea.getText());
      this.worth.setText(this.worthField.getText());
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
    }
  }
  
  public Grader(final ControllerFXCorrector controller) {
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
  
  private ControllerFXCorrector controller;
  
  private boolean editable;
  
  public void changeGrader(final QuestionItemCorrector qItem, final StudentItemCorrector sItem) {
    this.clearDisplay();
    float _worth = qItem.getWorth();
    String _plus = (Float.valueOf(_worth) + "");
    this.maxPoints.setText(_plus);
  }
  
  public void createNewGradeEntry() {
    Grader.GradeItem entry = new Grader.GradeItem(this);
    this.itemContainer.getChildren().add(entry);
    this.addEntryToModel(entry, this.controller.getQuestionList().getCurrentItem());
  }
  
  public Object removeGradeEntry(final Grader.GradeItem item) {
    Object _xblockexpression = null;
    {
      this.itemContainer.getChildren().remove(item);
      boolean _selected = item.getSelected();
      if (_selected) {
        this.removePointsOf(item);
      }
      _xblockexpression = this.removeEntryFromModel(item, this.controller.getQuestionList().getCurrentItem());
    }
    return _xblockexpression;
  }
  
  public void addPointsOf(final Grader.GradeItem item) {
    float current = Float.parseFloat(this.currentPoints.getText());
    float _parseFloat = Float.parseFloat(item.getWorth());
    float _plus = (current + _parseFloat);
    current = _plus;
    String _plus_1 = (Float.valueOf(current) + "");
    this.currentPoints.setText(_plus_1);
  }
  
  public void removePointsOf(final Grader.GradeItem item) {
    float current = Float.parseFloat(this.currentPoints.getText());
    float _parseFloat = Float.parseFloat(item.getWorth());
    float _minus = (current - _parseFloat);
    current = _minus;
    String _plus = (Float.valueOf(current) + "");
    this.currentPoints.setText(_plus);
  }
  
  public void addEntryToModel(final Grader.GradeItem item, final QuestionItemCorrector qItem) {
    int _questionId = qItem.getQuestionId();
    String _plus = ((("\n" + qItem) + " ") + Integer.valueOf(_questionId));
    InputOutput.<String>println(_plus);
    String _text = item.getText();
    String _plus_1 = ((("\n" + item) + " ") + _text);
    InputOutput.<String>println(_plus_1);
    float _parseFloat = Float.parseFloat(item.getWorth());
    String _plus_2 = ((("\n" + item) + " ") + Float.valueOf(_parseFloat));
    String _plus_3 = (_plus_2 + "\n");
    InputOutput.<String>println(_plus_3);
    GraduationAdapterFX _adapterCorrection = this.controller.getAdapterCorrection();
    String _plus_4 = ((("\n" + this.controller) + " ") + _adapterCorrection);
    String _plus_5 = (_plus_4 + " ");
    GraduationPresenter _presenter = this.controller.getAdapterCorrection().getPresenter();
    String _plus_6 = (_plus_5 + _presenter);
    String _plus_7 = (_plus_6 + " ");
    PresenterMarkingScheme _presenterMarkingScheme = this.controller.getAdapterCorrection().getPresenter().getPresenterMarkingScheme();
    String _plus_8 = (_plus_7 + _presenterMarkingScheme);
    String _plus_9 = (_plus_8 + "\n");
    InputOutput.<String>println(_plus_9);
  }
  
  public Object updateEntryInModel(final Grader.GradeItem item, final QuestionItemCorrector qItem) {
    return null;
  }
  
  public Object removeEntryFromModel(final Grader.GradeItem item, final QuestionItemCorrector qItem) {
    return null;
  }
  
  public String addPoints(final Grader.GradeItem item) {
    String _xblockexpression = null;
    {
      this.addPointsOf(item);
      _xblockexpression = InputOutput.<String>print("\nAdding points ");
    }
    return _xblockexpression;
  }
  
  public String removePoints(final Grader.GradeItem item) {
    String _xblockexpression = null;
    {
      this.removePointsOf(item);
      _xblockexpression = InputOutput.<String>print("\nRemoving points ");
    }
    return _xblockexpression;
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
            ((Grader.GradeItem) n).leaveEditMode();
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
