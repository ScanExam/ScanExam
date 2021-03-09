package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.view.fX.EditorAdapterFX;
import fr.istic.tools.scanexam.view.fX.GraduationAdapterFX;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * Class used by the JavaFX library as a controller for the view.
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class ControllerFXCorrector {
  public static class QuestionDetails {
    private int id;
    
    private String name;
    
    private double x;
    
    private double y;
    
    private double h;
    
    private double w;
    
    private List<Integer> bareme;
    
    public QuestionDetails(final String name) {
      this.name = name;
      this.id = 0;
      this.x = 0;
      this.y = 0;
      this.h = 0;
      this.w = 0;
      ArrayList<Integer> _arrayList = new ArrayList<Integer>();
      this.bareme = _arrayList;
    }
    
    public Label getLabel() {
      return new Label(("Question :" + this.name));
    }
    
    public VBox getDetails() {
      VBox container = new VBox();
      ObservableList<Node> _children = container.getChildren();
      Label _label = new Label(("Question : " + this.name));
      _children.add(_label);
      ObservableList<Node> _children_1 = container.getChildren();
      Label _label_1 = new Label(("ID :" + Integer.valueOf(this.id)));
      _children_1.add(_label_1);
      ObservableList<Node> _children_2 = container.getChildren();
      Label _label_2 = new Label(("x:" + Double.valueOf(this.x)));
      _children_2.add(_label_2);
      ObservableList<Node> _children_3 = container.getChildren();
      Label _label_3 = new Label(("y:" + Double.valueOf(this.y)));
      _children_3.add(_label_3);
      ObservableList<Node> _children_4 = container.getChildren();
      Label _label_4 = new Label(("h:" + Double.valueOf(this.h)));
      _children_4.add(_label_4);
      ObservableList<Node> _children_5 = container.getChildren();
      Label _label_5 = new Label(("w:" + Double.valueOf(this.w)));
      _children_5.add(_label_5);
      ObservableList<Node> _children_6 = container.getChildren();
      Label _label_6 = new Label(("Bareme: " + this.bareme));
      _children_6.add(_label_6);
      return container;
    }
  }
  
  public static class StudentItem extends Label {
    private int id;
    
    public StudentItem(final int s, final ControllerFXCorrector c) {
      super(("Student: " + Integer.valueOf(s)));
      this.id = s;
    }
  }
  
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * High level Controllers to access the Presenters
   */
  private GraduationAdapterFX corrector;
  
  private EditorAdapterFX editor;
  
  /**
   * setter for the ControllerVueCreation attribute
   * @param {@link ControllerVueCreation} controller instance of ControllerVueCreation (not null)
   */
  public EditorAdapterFX setAdapterEditor(final EditorAdapterFX edit) {
    EditorAdapterFX _xblockexpression = null;
    {
      Objects.<EditorAdapterFX>requireNonNull(edit);
      _xblockexpression = this.editor = edit;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link ControllerVueCreation}
   */
  public EditorAdapterFX getAdapterEditor() {
    return this.editor;
  }
  
  /**
   * setter for the ControllerVueCorrection attribute
   * @param {@link ControllerVueCorrection} controller instance of ControllerVueCorrection (not null)
   */
  public void setAdapterCorrection(final GraduationAdapterFX adapterCor) {
    Objects.<GraduationAdapterFX>requireNonNull(adapterCor);
    this.corrector = adapterCor;
  }
  
  /**
   * @return current {@link ControllerVueCorrection}
   */
  public GraduationAdapterFX getAdapterCorrection() {
    return this.corrector;
  }
  
  private boolean botShow = false;
  
  @FXML
  public Pane topPane;
  
  @FXML
  public Button topButtonHidden;
  
  @FXML
  public Button topButtonActive;
  
  @FXML
  public Button botButtonHidden;
  
  @FXML
  public Button botButtonActive;
  
  @FXML
  public Pane bottomPane;
  
  @FXML
  public Pane imagePane;
  
  @FXML
  public Pane parentPane;
  
  @FXML
  public ListView<Label> leftList;
  
  @FXML
  public ListView<Label> rightList;
  
  @FXML
  public ImageView imview;
  
  public ScrollPane scrollMain;
  
  public ScrollPane scrollBis;
  
  public VBox studentDetails;
  
  public VBox questionDetails;
  
  /**
   * Toggles the visibility of the bottom window
   */
  public void toggleBottom() throws IOException {
    this.bottomPane.setVisible((!this.botShow));
    this.botButtonHidden.setVisible(this.botShow);
    this.botShow = (!this.botShow);
  }
  
  /**
   * Used to resize the window containing the corrected exam
   */
  public void dragBottom(final MouseEvent event) {
    EventType<? extends MouseEvent> _eventType = event.getEventType();
    boolean _equals = com.google.common.base.Objects.equal(_eventType, MouseEvent.MOUSE_DRAGGED);
    if (_equals) {
      double _height = this.bottomPane.getScene().getHeight();
      double _minus = (_height - 100);
      double _height_1 = this.bottomPane.getScene().getHeight();
      double _sceneY = event.getSceneY();
      double _minus_1 = (_height_1 - _sceneY);
      this.bottomPane.setPrefHeight(
        Math.max(0, 
          Math.min(_minus, _minus_1)));
    }
  }
  
  private double mouseOriginX = 0d;
  
  private double mouseOriginY = 0d;
  
  private double objectOriginX = 0d;
  
  private double objectOriginY = 0d;
  
  @FXML
  public void MoveImage(final MouseEvent e) {
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = com.google.common.base.Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
      this.mouseOriginX = e.getScreenX();
      this.mouseOriginY = e.getScreenY();
      Object _source = e.getSource();
      Node source = ((Node) _source);
      InputOutput.<Node>println(source);
      this.objectOriginX = source.getLayoutX();
      this.objectOriginY = source.getLayoutY();
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = com.google.common.base.Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      Object _source_1 = e.getSource();
      Node source_1 = ((Node) _source_1);
      double _screenX = e.getScreenX();
      double _minus = (_screenX - this.mouseOriginX);
      double _plus = (this.objectOriginX + _minus);
      source_1.setLayoutX(_plus);
      double _screenY = e.getScreenY();
      double _minus_1 = (_screenY - this.mouseOriginY);
      double _plus_1 = (this.objectOriginY + _minus_1);
      source_1.setLayoutY(_plus_1);
    }
  }
  
  @FXML
  public void ZoomImage(final ScrollEvent e) {
    Object _source = e.getSource();
    Node source = ((Node) _source);
    double _deltaY = e.getDeltaY();
    boolean _greaterThan = (_deltaY > 0);
    if (_greaterThan) {
      double _scaleX = source.getScaleX();
      double _multiply = (_scaleX * 0.95);
      source.setScaleX(_multiply);
      double _scaleY = source.getScaleY();
      double _multiply_1 = (_scaleY * 0.95);
      source.setScaleY(_multiply_1);
    } else {
      double _scaleX_1 = source.getScaleX();
      double _multiply_2 = (_scaleX_1 * 1.05);
      source.setScaleX(_multiply_2);
      double _scaleY_1 = source.getScaleY();
      double _multiply_3 = (_scaleY_1 * 1.05);
      source.setScaleY(_multiply_3);
    }
  }
  
  /**
   * Called when a <b>save</b> button is pressed
   */
  @FXML
  public void savePressed() {
    InputOutput.<String>println("Saving method");
  }
  
  /**
   * Called when a <b>save a</b> button is pressed
   */
  @FXML
  public void saveAsPressed() {
    InputOutput.<String>println("Saving as method");
  }
  
  /**
   * Called when a <b>load</b> button is pressed
   */
  @FXML
  public void loadPressed() {
    InputOutput.<String>println("Load method");
    this.chooseFile();
  }
  
  /**
   * Called when a <b>import</b> button is pressed
   */
  @FXML
  public void importPressed() {
    InputOutput.<String>println("Import method");
  }
  
  /**
   * Called when a <b>export</b> button is pressed
   */
  @FXML
  public void exportPressed() {
    InputOutput.<String>println("Export method");
  }
  
  /**
   * Called when a <b>next question</b> button is pressed
   */
  @FXML
  public void nextQuestionPressed() {
    InputOutput.<String>println("Next question method");
    this.corrector.nextQuestion();
  }
  
  /**
   * Called when a <b>previous question pressed</b> button is pressed
   */
  @FXML
  public void prevQuestionPressed() {
    InputOutput.<String>println("Previous question method");
    this.corrector.previousQuestion();
  }
  
  /**
   * Called when a <b>next student</b> button is pressed
   */
  @FXML
  public void nextStudentPressed() {
    InputOutput.<String>println("Next student method");
  }
  
  /**
   * Called when a <b>previous student</b> button is pressed
   */
  @FXML
  public void prevStudentPressed() {
    InputOutput.<String>println("Previous student method");
  }
  
  @FXML
  public void addBaremeList() {
  }
  
  @FXML
  public void addQuestionList() {
  }
  
  public void zoomTest() {
    this.setZoomArea(0, 0, 100, 200);
  }
  
  public void setZoomArea(final int x, final int y, final int height, final int width) {
    Rectangle2D newrect = new Rectangle2D(this.currentQuestion.x, this.currentQuestion.y, this.currentQuestion.w, this.currentQuestion.h);
    this.imview.setViewport(newrect);
  }
  
  @FXML
  public void resetPosition() {
    this.imagePane.setScaleX(1);
    this.imagePane.setScaleY(1);
    this.imagePane.setLayoutX(0);
    this.imagePane.setLayoutY(0);
    this.imview.setViewport(null);
  }
  
  private ControllerFXCorrector.QuestionDetails currentQuestion;
  
  public void binds(final Node n) {
    final EventHandler<KeyEvent> _function = (KeyEvent event) -> {
      KeyCode _code = event.getCode();
      if (_code != null) {
        switch (_code) {
          case RIGHT:
            this.nextQuestionPressed();
            break;
          case LEFT:
            this.prevQuestionPressed();
            break;
          case UP:
            this.nextStudentPressed();
            break;
          case DOWN:
            this.prevStudentPressed();
            break;
          default:
            ControllerFXCorrector.logger.warn("Key not supported.");
            break;
        }
      } else {
        ControllerFXCorrector.logger.warn("Key not supported.");
      }
      event.consume();
    };
    n.setOnKeyPressed(_function);
  }
  
  public void setKeybinds() {
    Scene s = this.imagePane.getScene();
    final EventHandler<KeyEvent> _function = (KeyEvent event) -> {
      KeyCode _code = event.getCode();
      if (_code != null) {
        switch (_code) {
          case RIGHT:
            this.nextQuestionPressed();
            break;
          case LEFT:
            this.prevQuestionPressed();
            break;
          case UP:
            this.nextStudentPressed();
            break;
          case DOWN:
            this.prevStudentPressed();
            break;
          default:
            ControllerFXCorrector.logger.warn("Key not supported.");
            break;
        }
      } else {
        ControllerFXCorrector.logger.warn("Key not supported.");
      }
      event.consume();
    };
    s.setOnKeyPressed(_function);
    this.binds(this.scrollMain);
    this.binds(this.scrollBis);
  }
  
  public void chooseFile() {
    FileChooser fileChooser = new FileChooser();
    ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
    List<String> _asList = Arrays.<String>asList("*.xmi");
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("XMI files", _asList);
    _extensionFilters.add(_extensionFilter);
    String _property = System.getProperty("user.home");
    String _property_1 = System.getProperty("file.separator");
    String _plus = (_property + _property_1);
    String _plus_1 = (_plus + "Documents");
    File _file = new File(_plus_1);
    fileChooser.setInitialDirectory(_file);
    File file = fileChooser.showOpenDialog(this.imagePane.getScene().getWindow());
    boolean _notEquals = (!com.google.common.base.Objects.equal(file, null));
    if (_notEquals) {
      this.corrector.loadFile(file);
    } else {
      ControllerFXCorrector.logger.warn("File not chosen");
    }
  }
  
  public void initTests() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method controller(ControllerFXCorrector) is undefined for the type MockFXAdapter");
  }
  
  public void initQuestionNames(final List<String> names) {
    this.rightList.getItems().clear();
    for (final String s : names) {
      ObservableList<Label> _items = this.rightList.getItems();
      Label _label = new Label(s);
      _items.add(_label);
    }
  }
  
  public void initStudentNames(final List<String> names) {
    this.leftList.getItems().clear();
    for (final String s : names) {
      ObservableList<Label> _items = this.leftList.getItems();
      Label _label = new Label(s);
      _items.add(_label);
    }
  }
  
  public void showStudent() {
  }
  
  public void showQuestion(final Question question) {
    String _name = question.getName();
    ControllerFXCorrector.QuestionDetails _questionDetails = new ControllerFXCorrector.QuestionDetails(_name);
    this.currentQuestion = _questionDetails;
    this.currentQuestion.x = question.getZone().getX();
    this.currentQuestion.y = question.getZone().getY();
    this.currentQuestion.h = question.getZone().getHeigth();
    this.currentQuestion.w = question.getZone().getWidth();
    this.currentQuestion.id = question.getId();
    this.questionDetails.getChildren().clear();
    this.questionDetails.getChildren().add(this.currentQuestion.getDetails());
    int i = 0;
    ObservableList<Label> _items = this.rightList.getItems();
    for (final Label l : _items) {
      {
        boolean _equals = l.getText().equals(this.currentQuestion.name);
        if (_equals) {
          this.rightList.getSelectionModel().select(i);
        }
        i++;
      }
    }
  }
}
