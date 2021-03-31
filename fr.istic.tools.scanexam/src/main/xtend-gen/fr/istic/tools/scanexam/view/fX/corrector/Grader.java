package fr.istic.tools.scanexam.view.fX.corrector;

import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fX.corrector.ControllerFXCorrector;
import fr.istic.tools.scanexam.view.fX.corrector.QuestionItemCorrector;
import fr.istic.tools.scanexam.view.fX.corrector.StudentItemCorrector;
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
import org.eclipse.xtext.xbase.lib.Exceptions;
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
          boolean _equals = event.getButton().equals(MouseButton.PRIMARY);
          if (_equals) {
            int _clickCount = event.getClickCount();
            boolean _equals_1 = (_clickCount == 2);
            if (_equals_1) {
              GradeItem.this.renderHTMLView();
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
        Stage stage = new Stage();
        stage.setTitle("Editeur HTML");
        InputStream inputLayout = ResourcesUtils.getInputStreamResource("viewResources/HTML.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.<Parent>load(inputLayout);
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
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
    List<Integer> ids = this.controller.getAdapterCorrection().getPresenter().getEntryIds(qItem.getQuestionId());
    List<Integer> sids = this.controller.getAdapterCorrection().getPresenter().getSelectedEntryIds(qItem.getQuestionId());
    int _size = ids.size();
    String _plus_1 = ("size :" + Integer.valueOf(_size));
    InputOutput.<String>print(_plus_1);
    for (final Integer i : ids) {
      {
        Grader.GradeItem g = new Grader.GradeItem(this);
        g.setItemId((i).intValue());
        g.setText(this.controller.getAdapterCorrection().getPresenter().getEntryText((i).intValue(), qItem.getQuestionId()));
        g.setWorth(this.controller.getAdapterCorrection().getPresenter().getEntryWorth((i).intValue(), qItem.getQuestionId()));
        this.itemContainer.getChildren().add(g);
        boolean _contains = sids.contains(i);
        if (_contains) {
          g.setSelected(Boolean.valueOf(true));
          this.addPointsOf(g);
        }
        g.leaveEditMode();
      }
    }
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
    item.setItemId(this.controller.getAdapterCorrection().getPresenter().addEntry(qItem.getQuestionId(), item.getText(), Float.parseFloat(item.getWorth())));
  }
  
  public void updateEntryInModel(final Grader.GradeItem item, final QuestionItemCorrector qItem) {
    this.controller.getAdapterCorrection().getPresenter().modifyEntry(qItem.getQuestionId(), item.getItemId(), item.getText(), Float.parseFloat(item.getWorth()));
  }
  
  public Object removeEntryFromModel(final Grader.GradeItem item, final QuestionItemCorrector qItem) {
    return null;
  }
  
  public String addPoints(final Grader.GradeItem item) {
    String _xblockexpression = null;
    {
      this.addPointsOf(item);
      this.controller.getAdapterCorrection().getPresenter().applyGrade(item.id, this.controller.getQuestionList().getCurrentItem().getQuestionId());
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
