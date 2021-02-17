package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.view.ControllerVueCorrection;
import fr.istic.tools.scanexam.view.ControllerVueCreation;
import fr.istic.tools.scanexam.view.FXMockBackend;
import java.io.IOException;
import java.util.ArrayList;
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
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * Class used by the JavaFX library as a controller for the view.
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class ControllerFX {
  public static class QuestionDetails extends Label {
    private int id;
    
    private String name;
    
    private double x;
    
    private double y;
    
    private double h;
    
    private double w;
    
    private List<Integer> bareme;
    
    public QuestionDetails(final String name) {
      super(name);
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
      Label _label_2 = new Label(((((((("x:" + Double.valueOf(this.x)) + " y:") + Double.valueOf(this.y)) + " h:") + Double.valueOf(this.h)) + " w:") + Double.valueOf(this.w)));
      _children_2.add(_label_2);
      ObservableList<Node> _children_3 = container.getChildren();
      Label _label_3 = new Label(("Bareme: " + this.bareme));
      _children_3.add(_label_3);
      return container;
    }
  }
  
  public static class StudentItem extends Label {
    private int id;
    
    public StudentItem(final int s, final ControllerFX c) {
      super(("Student: " + Integer.valueOf(s)));
      this.id = s;
    }
  }
  
  /**
   * High level Controllers to access the Presenters
   */
  private FXMockBackend test;
  
  private ControllerVueCreation controllerCreation;
  
  private ControllerVueCorrection controllerCorrection;
  
  /**
   * setter for the ControllerVueCreation attribute
   * @param {@link ControllerVueCreation} controller instance of ControllerVueCreation (not null)
   */
  public ControllerVueCreation setControllerVueCreation(final ControllerVueCreation controller) {
    ControllerVueCreation _xblockexpression = null;
    {
      Objects.<ControllerVueCreation>requireNonNull(controller);
      _xblockexpression = this.controllerCreation = controller;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link ControllerVueCreation}
   */
  public ControllerVueCreation getControllerVueCreation() {
    return this.controllerCreation;
  }
  
  /**
   * setter for the ControllerVueCorrection attribute
   * @param {@link ControllerVueCorrection} controller instance of ControllerVueCorrection (not null)
   */
  public ControllerVueCorrection setControllerVueCorrection(final ControllerVueCorrection controller) {
    ControllerVueCorrection _xblockexpression = null;
    {
      Objects.<ControllerVueCorrection>requireNonNull(controller);
      _xblockexpression = this.controllerCorrection = controller;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link ControllerVueCorrection}
   */
  public ControllerVueCorrection getControllerVueCorrection() {
    return this.controllerCorrection;
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
    this.test.nextQuestion();
  }
  
  /**
   * Called when a <b>previous question pressed</b> button is pressed
   */
  @FXML
  public void prevQuestionPressed() {
    InputOutput.<String>println("Previous question method");
    this.test.previousQuestion();
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
  
  @FXML
  public void setZoomArea(final int x, final int y, final int height, final int width) {
    double _selectedX = this.test.selectedX();
    double _selectedY = this.test.selectedY();
    double _selectedHeight = this.test.selectedHeight();
    double _selectedWidth = this.test.selectedWidth();
    Rectangle2D newrect = new Rectangle2D(_selectedX, _selectedY, _selectedHeight, _selectedWidth);
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
  
  public void initQuestions(final List<String> names) {
    for (final String s : names) {
      ObservableList<Label> _items = this.rightList.getItems();
      Label _label = new Label(s);
      _items.add(_label);
    }
  }
  
  public void initStudents(final int nbStudents) {
  }
  
  public void selectedQuestion() {
  }
  
  public void setSelectedQuestion() {
    String _selectedName = this.test.selectedName();
    ControllerFX.QuestionDetails q = new ControllerFX.QuestionDetails(_selectedName);
    q.x = this.test.selectedX();
    q.y = this.test.selectedY();
    q.h = this.test.selectedHeight();
    q.w = this.test.selectedWidth();
    this.questionDetails.getChildren().clear();
    this.questionDetails.getChildren().add(q.getDetails());
    int i = 0;
    ObservableList<Label> _items = this.rightList.getItems();
    for (final Label l : _items) {
      {
        String _text = l.getText();
        String _selectedName_1 = this.test.selectedName();
        boolean _equals = com.google.common.base.Objects.equal(_text, _selectedName_1);
        if (_equals) {
          this.rightList.getSelectionModel().select(i);
        }
        i++;
      }
    }
  }
  
  public void Binds(final Node n) {
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
            break;
        }
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
            break;
        }
      }
      event.consume();
    };
    s.setOnKeyPressed(_function);
    this.Binds(this.scrollMain);
    this.Binds(this.scrollBis);
  }
  
  public void initTests() {
    this.setKeybinds();
    FXMockBackend _fXMockBackend = new FXMockBackend();
    this.test = _fXMockBackend;
    this.test.setControllerFX(this);
    this.test.setQuestions();
  }
}
