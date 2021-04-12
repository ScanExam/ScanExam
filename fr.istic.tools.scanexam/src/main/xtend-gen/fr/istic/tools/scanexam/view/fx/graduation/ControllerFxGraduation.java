package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.launcher.LauncherFX;
import fr.istic.tools.scanexam.services.ExamSingleton;
import fr.istic.tools.scanexam.view.AdapterGraduation;
import fr.istic.tools.scanexam.view.fx.AdapterFxGraduation;
import fr.istic.tools.scanexam.view.fx.FxSettings;
import fr.istic.tools.scanexam.view.fx.graduation.Grader;
import fr.istic.tools.scanexam.view.fx.graduation.QuestionItemGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.QuestionListGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.StudentDetails;
import fr.istic.tools.scanexam.view.fx.graduation.StudentItemGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.StudentListGraduation;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
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
public class ControllerFxGraduation {
  public enum SelectedTool {
    NO_TOOL,
    
    MOVE_CAMERA_TOOL;
  }
  
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * High level Controllers to access the Presenters
   */
  private AdapterFxGraduation corrector;
  
  /**
   * setter for the ControllerVueCorrection attribute
   * @param {@link ControllerVueCorrection} controller instance of ControllerVueCorrection (not null)
   */
  public void setAdapterCorrection(final AdapterFxGraduation adapterCor) {
    Objects.<AdapterFxGraduation>requireNonNull(adapterCor);
    this.corrector = adapterCor;
  }
  
  /**
   * @return current {@link ControllerVueCorrection}
   */
  public AdapterFxGraduation getAdapterCorrection() {
    return this.corrector;
  }
  
  private boolean LoadedModel = false;
  
  private Grader grader;
  
  private QuestionListGraduation questionList;
  
  private StudentListGraduation studentList;
  
  private StudentDetails studentDetails;
  
  private boolean botShow = false;
  
  private boolean autoZoom = true;
  
  @FXML
  public VBox root;
  
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
  public Pane mainPane;
  
  @FXML
  public Pane parentPane;
  
  @FXML
  public ScrollPane studentListContainer;
  
  @FXML
  public ScrollPane questionListContainer;
  
  @FXML
  public ImageView imview;
  
  @FXML
  public ScrollPane scrollMain;
  
  @FXML
  public ScrollPane scrollBis;
  
  @FXML
  public VBox studentDetailsContainer;
  
  @FXML
  public VBox questionDetails;
  
  @FXML
  public Spinner<Double> gradeSpinner;
  
  @FXML
  public Spinner<Double> totalGradeSpinner;
  
  @FXML
  public HBox graderContainer;
  
  @FXML
  public Label instructionLabel;
  
  @FXML
  public Object Pressed() {
    return null;
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
    this.load();
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
    this.getAdapterCorrection().getPresenter().exportGrades();
  }
  
  /**
   * Called when a <b>next question</b> button is pressed
   */
  @FXML
  public void nextQuestionPressed() {
    InputOutput.<String>println("Next question method");
    if (this.LoadedModel) {
      this.nextQuestion();
    }
  }
  
  /**
   * Called when a <b>previous question pressed</b> button is pressed
   */
  @FXML
  public void prevQuestionPressed() {
    InputOutput.<String>println("Previous question method");
    if (this.LoadedModel) {
      this.previousQuestion();
    }
  }
  
  /**
   * Called when a <b>next student</b> button is pressed
   */
  @FXML
  public void nextStudentPressed() {
    InputOutput.<String>println("Next student method");
    if (this.LoadedModel) {
      this.nextStudent();
    }
  }
  
  /**
   * Called when a <b>previous student</b> button is pressed
   */
  @FXML
  public void prevStudentPressed() {
    InputOutput.<String>println("Previous student method");
    if (this.LoadedModel) {
      this.previousStudent();
    }
  }
  
  /**
   * Called when a grade update button is pressed
   */
  @FXML
  public void saveGradeButtonPressed() {
    Double _value = this.gradeSpinner.getValue();
    String _plus = ("save Grade method : " + _value);
    String _plus_1 = (_plus + "/");
    Double _value_1 = this.totalGradeSpinner.getValue();
    String _plus_2 = (_plus_1 + _value_1);
    InputOutput.<String>println(_plus_2);
  }
  
  @FXML
  public void swapToEditorPressed() {
    LauncherFX.swapToEditor();
  }
  
  @FXML
  public void mainMouseEvent(final MouseEvent e) {
    this.chooseMouseAction(e);
  }
  
  public AdapterGraduation getAdapter() {
    return this.corrector;
  }
  
  private ControllerFxGraduation.SelectedTool currentTool = ControllerFxGraduation.SelectedTool.NO_TOOL;
  
  private boolean pdfLoaded = false;
  
  private double maxX;
  
  private double maxY;
  
  private double imageWidth;
  
  private double imageHeight;
  
  public QuestionListGraduation getQuestionList() {
    return this.questionList;
  }
  
  public StudentListGraduation getStudentList() {
    return this.studentList;
  }
  
  public boolean setToAutoZoom(final Boolean b) {
    return this.autoZoom = (b).booleanValue();
  }
  
  public void chooseMouseAction(final MouseEvent e) {
    MouseButton _button = e.getButton();
    boolean _equals = com.google.common.base.Objects.equal(_button, MouseButton.SECONDARY);
    if (_equals) {
      this.moveImage(e);
      return;
    }
    final ControllerFxGraduation.SelectedTool currentTool = this.currentTool;
    if (currentTool != null) {
      switch (currentTool) {
        case NO_TOOL:
          break;
        case MOVE_CAMERA_TOOL:
          this.moveImage(e);
          break;
        default:
          break;
      }
    }
  }
  
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
  
  public void moveImage(final MouseEvent e) {
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = com.google.common.base.Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
      this.mouseOriginX = e.getScreenX();
      this.mouseOriginY = e.getScreenY();
      Object _source = e.getSource();
      Node source = ((Node) _source);
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
    e.consume();
  }
  
  public void zoomTest() {
    this.setZoomArea(0, 0, 100, 200);
  }
  
  @FXML
  public void resetPosition() {
    this.mainPane.setScaleX(1);
    this.mainPane.setScaleY(1);
    this.mainPane.setLayoutX(0);
    this.mainPane.setLayoutY(0);
    this.imview.setViewport(null);
  }
  
  public void init() {
    QuestionListGraduation _questionListGraduation = new QuestionListGraduation(this);
    this.questionList = _questionListGraduation;
    this.questionListContainer.setContent(this.questionList);
    StudentListGraduation _studentListGraduation = new StudentListGraduation(this);
    this.studentList = _studentListGraduation;
    this.studentListContainer.setContent(this.studentList);
    Grader _grader = new Grader(this);
    this.grader = _grader;
    this.graderContainer.getChildren().add(this.grader);
    this.grader.setVisible(false);
    StudentDetails _studentDetails = new StudentDetails();
    this.studentDetails = _studentDetails;
    this.studentDetailsContainer.getChildren().add(this.studentDetails);
    this.binds(this.root);
    this.binds(this.scrollMain);
    this.binds(this.scrollBis);
  }
  
  public void binds(final Node n) {
    final EventHandler<KeyEvent> _function = new EventHandler<KeyEvent>() {
      public void handle(final KeyEvent event) {
        KeyCode _code = event.getCode();
        boolean _matched = false;
        if (com.google.common.base.Objects.equal(_code, FxSettings.BUTTON_NEXT_QUESTION)) {
          _matched=true;
          ControllerFxGraduation.this.nextQuestionPressed();
        }
        if (!_matched) {
          if (com.google.common.base.Objects.equal(_code, FxSettings.BUTTON_PREV_QUESTION)) {
            _matched=true;
            ControllerFxGraduation.this.prevQuestionPressed();
          }
        }
        if (!_matched) {
          if (com.google.common.base.Objects.equal(_code, FxSettings.BUTTON_PREV_STUDENT)) {
            _matched=true;
            ControllerFxGraduation.this.prevStudentPressed();
          }
        }
        if (!_matched) {
          if (com.google.common.base.Objects.equal(_code, FxSettings.BUTTON_NEXT_STUDENT)) {
            _matched=true;
            ControllerFxGraduation.this.nextStudentPressed();
          }
        }
        if (!_matched) {
          ControllerFxGraduation.logger.warn("Key not supported.");
        }
        event.consume();
      }
    };
    n.setOnKeyPressed(_function);
  }
  
  public void setKeybinds() {
    Scene s = this.mainPane.getScene();
    final EventHandler<KeyEvent> _function = new EventHandler<KeyEvent>() {
      public void handle(final KeyEvent event) {
        KeyCode _code = event.getCode();
        boolean _matched = false;
        if (com.google.common.base.Objects.equal(_code, FxSettings.BUTTON_NEXT_QUESTION)) {
          _matched=true;
          ControllerFxGraduation.this.nextQuestionPressed();
        }
        if (!_matched) {
          if (com.google.common.base.Objects.equal(_code, FxSettings.BUTTON_PREV_QUESTION)) {
            _matched=true;
            ControllerFxGraduation.this.prevQuestionPressed();
          }
        }
        if (!_matched) {
          if (com.google.common.base.Objects.equal(_code, FxSettings.BUTTON_PREV_STUDENT)) {
            _matched=true;
            ControllerFxGraduation.this.prevStudentPressed();
          }
        }
        if (!_matched) {
          if (com.google.common.base.Objects.equal(_code, FxSettings.BUTTON_NEXT_STUDENT)) {
            _matched=true;
            ControllerFxGraduation.this.nextStudentPressed();
          }
        }
        if (!_matched) {
          ControllerFxGraduation.logger.warn("Key not supported.");
        }
        event.consume();
      }
    };
    s.setOnKeyPressed(_function);
    this.binds(this.scrollMain);
    this.binds(this.scrollBis);
  }
  
  public void load() {
    this.loadTemplate();
    this.loadStudentPdfs();
  }
  
  /**
   * Opens a Open dialog box
   * Used to choose a .xmi file representing a already started Graduation
   */
  public void loadTemplate() {
    FileChooser fileChooser = new FileChooser();
    ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
    List<String> _asList = Arrays.<String>asList("*.xmi");
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("XMI files", _asList);
    _extensionFilters.add(_extensionFilter);
    String _property = System.getProperty("user.home");
    String _property_1 = System.getProperty("file.separator");
    String _plus = (_property + _property_1);
    String _plus_1 = (_plus + 
      "Documents");
    File _file = new File(_plus_1);
    fileChooser.setInitialDirectory(_file);
    File file = fileChooser.showOpenDialog(this.mainPane.getScene().getWindow());
    if ((file != null)) {
      this.corrector.getPresenter().openEditionTemplate(file.getPath());
    } else {
      ControllerFxGraduation.logger.warn("File not chosen");
    }
  }
  
  public void loadStudentPdfs() {
    FileChooser fileChooser = new FileChooser();
    ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
    List<String> _asList = Arrays.<String>asList("*.pdf");
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("PDF files", _asList);
    _extensionFilters.add(_extensionFilter);
    String _property = System.getProperty("user.home");
    String _property_1 = System.getProperty("file.separator");
    String _plus = (_property + _property_1);
    String _plus_1 = (_plus + 
      "Documents");
    File _file = new File(_plus_1);
    fileChooser.setInitialDirectory(_file);
    File file = fileChooser.showOpenDialog(this.mainPane.getScene().getWindow());
    if ((file != null)) {
      this.corrector.getPresenter().openCorrectionPdf(file);
      this.renderCorrectedCopy();
      this.renderStudentCopy();
      this.loadQuestions();
      this.loadStudents();
      this.postLoad();
    } else {
      ControllerFxGraduation.logger.warn("File not chosen");
    }
  }
  
  public void saveExam() {
    FileChooser fileChooser = new FileChooser();
    ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
    List<String> _asList = Arrays.<String>asList("*.xmi");
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("XMI files", _asList);
    _extensionFilters.add(_extensionFilter);
    String _property = System.getProperty("user.home");
    String _property_1 = System.getProperty("file.separator");
    String _plus = (_property + _property_1);
    String _plus_1 = (_plus + 
      "Documents");
    File _file = new File(_plus_1);
    fileChooser.setInitialDirectory(_file);
    File file = fileChooser.showSaveDialog(this.mainPane.getScene().getWindow());
    if ((file != null)) {
    } else {
      ControllerFxGraduation.logger.warn("File not chosen");
    }
  }
  
  public void loadQuestions() {
    for (int p = 0; (p < ExamSingleton.instance.getPages().size()); p++) {
      {
        LinkedList<Integer> ids = this.corrector.getPresenter().initLoading(p);
        for (final int i : ids) {
          {
            QuestionItemGraduation question = new QuestionItemGraduation();
            double _questionX = this.corrector.getPresenter().questionX(i);
            double _multiply = (_questionX * this.imageWidth);
            question.setX(_multiply);
            double _questionY = this.corrector.getPresenter().questionY(i);
            double _multiply_1 = (_questionY * this.imageHeight);
            question.setY(_multiply_1);
            double _questionHeight = this.corrector.getPresenter().questionHeight(i);
            double _multiply_2 = (_questionHeight * this.imageHeight);
            question.setH(_multiply_2);
            double _questionWidth = this.corrector.getPresenter().questionWidth(i);
            double _multiply_3 = (_questionWidth * this.imageWidth);
            question.setW(_multiply_3);
            question.setPage(p);
            question.setQuestionId(i);
            question.setName(this.corrector.getPresenter().questionName(i));
            question.setWorth(Float.valueOf(this.corrector.getPresenter().questionWorth(i)));
            this.questionList.addItem(question);
          }
        }
      }
    }
  }
  
  public void loadStudents() {
    LinkedList<Integer> ids = this.corrector.getPresenter().getStudentIds();
    for (final int i : ids) {
      StudentItemGraduation _studentItemGraduation = new StudentItemGraduation(i);
      this.studentList.addItem(_studentItemGraduation);
    }
  }
  
  public void postLoad() {
    this.LoadedModel = true;
    this.instructionLabel.setVisible(false);
    this.grader.setVisible(true);
    this.setSelectedQuestion();
    this.setSelectedStudent();
  }
  
  public void renderStudentCopy() {
    BufferedImage image = this.corrector.getPresenter().getPresenterPdf().getCurrentPdfPage();
    this.imview.setImage(SwingFXUtils.toFXImage(image, null));
    this.pdfLoaded = true;
    this.imageWidth = image.getWidth();
    this.imageHeight = image.getHeight();
  }
  
  public void renderCorrectedCopy() {
  }
  
  public void nextStudent() {
    this.studentList.selectNextItem();
    this.getAdapterCorrection().getPresenter().getPresenterQuestion().nextStudent();
    this.setSelectedStudent();
  }
  
  public void previousStudent() {
    this.studentList.selectPreviousItem();
    this.getAdapterCorrection().getPresenter().getPresenterQuestion().previousStudent();
    this.setSelectedStudent();
  }
  
  public void selectStudent(final StudentItemGraduation item) {
    this.studentList.selectItem(item);
    this.setSelectedStudent();
  }
  
  public void setSelectedStudent() {
    this.focusStudent(this.studentList.getCurrentItem());
    this.studentDetails.display(this.studentList.getCurrentItem());
    this.display();
    this.displayGrader();
  }
  
  public void nextQuestion() {
    this.questionList.selectNextItem();
    this.getAdapterCorrection().getPresenter().getPresenterQuestion().nextQuestion();
    this.setSelectedQuestion();
  }
  
  public void previousQuestion() {
    this.questionList.selectPreviousItem();
    this.getAdapterCorrection().getPresenter().getPresenterQuestion().previousQuestion();
    this.setSelectedQuestion();
  }
  
  public void selectQuestion(final QuestionItemGraduation item) {
    this.questionList.selectItem(item);
    this.getAdapterCorrection().getPresenter().getPresenterQuestion().selectQuestion(item.getQuestionId());
    this.setSelectedQuestion();
  }
  
  public void setSelectedQuestion() {
    this.focusQuestion(this.questionList.getCurrentItem());
    this.display();
    this.displayQuestion();
    this.displayGrader();
  }
  
  public void focusQuestion(final QuestionItemGraduation item) {
    this.questionList.focusItem(item);
  }
  
  public void focusStudent(final StudentItemGraduation item) {
    this.studentList.focusItem(item);
  }
  
  public void setZoomArea(final int x, final int y, final int height, final int width) {
    if (this.autoZoom) {
      Rectangle2D _rectangle2D = new Rectangle2D(x, y, height, width);
      this.imview.setViewport(_rectangle2D);
    }
  }
  
  public void display() {
    int i = this.corrector.getPresenter().getAbsolutePage(this.studentList.getCurrentItem().getStudentId(), this.questionList.getCurrentItem().getPage());
    boolean _atCorrectPage = this.corrector.getPresenter().getPresenterPdf().atCorrectPage(i);
    boolean _not = (!_atCorrectPage);
    if (_not) {
      ControllerFxGraduation.logger.warn("changing Page");
      this.selectPage(this.corrector.getPresenter().getAbsolutePage(this.studentList.getCurrentItem().getStudentId(), this.questionList.getCurrentItem().getPage()));
    }
  }
  
  public void setZoomArea(final double x, final double y, final double width, final double height) {
    Rectangle2D _rectangle2D = new Rectangle2D(x, y, width, height);
    this.imview.setViewport(_rectangle2D);
  }
  
  public void displayQuestion() {
    if (this.autoZoom) {
      this.setZoomArea(this.questionList.getCurrentItem().getX(), this.questionList.getCurrentItem().getY(), this.questionList.getCurrentItem().getW(), this.questionList.getCurrentItem().getH());
    }
  }
  
  public void displayGrader() {
    this.grader.changeGrader(this.questionList.getCurrentItem(), this.studentList.getCurrentItem());
  }
  
  public void setGrade(final int studentId, final int questionId, final float grade) {
  }
  
  public void nextPage() {
    this.corrector.getPresenter().getPresenterPdf().nextPdfPage();
    this.renderStudentCopy();
  }
  
  public void previousPage() {
    this.corrector.getPresenter().getPresenterPdf().previousPdfPage();
    this.renderStudentCopy();
  }
  
  public void selectPage(final int pageNumber) {
    InputOutput.<String>print((("\npage Number" + Integer.valueOf(pageNumber)) + "\n"));
    this.corrector.getPresenter().getPresenterPdf().goToPdfPage(pageNumber);
    this.renderStudentCopy();
  }
}
