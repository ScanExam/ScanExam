package fr.istic.tools.scanexam.view.fx.graduation;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.presenter.PresenterGraduation;
import fr.istic.tools.scanexam.view.fx.FxSettings;
import fr.istic.tools.scanexam.view.fx.graduation.Grader;
import fr.istic.tools.scanexam.view.fx.graduation.PdfPaneWithAnotations;
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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
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
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Class used by the JavaFX library as a controller for the view.
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class ControllerFxGraduation {
  public enum SelectedTool {
    NO_TOOL,
    
    MOVE_CAMERA_TOOL,
    
    CREATE_ANOTATION_TOOL;
  }
  
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * High level Controllers to access the Presenters
   */
  @Accessors
  private PresenterGraduation presenter;
  
  @Accessors
  private BooleanProperty loadedModel = new SimpleBooleanProperty(this, "Is a template loaded", false);
  
  private Grader grader;
  
  private QuestionListGraduation questionList;
  
  private StudentListGraduation studentList;
  
  private StudentDetails studentDetails;
  
  public PdfPaneWithAnotations mainPane;
  
  private boolean botShow = false;
  
  private boolean autoZoom = true;
  
  @FXML
  public Label gradeLabel;
  
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
  public Pane parentPane;
  
  @FXML
  public ScrollPane studentListContainer;
  
  @FXML
  public ScrollPane questionListContainer;
  
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
  public Button nextStudentButton;
  
  @FXML
  public Button prevStudentButton;
  
  @FXML
  public Button nextQuestionButton;
  
  @FXML
  public Button prevQuestionButton;
  
  @FXML
  public Object Pressed() {
    return null;
  }
  
  /**
   * Called when a <b>save</b> button is pressed
   */
  @FXML
  public void savePressed() {
    ControllerFxGraduation.logger.info("Save Called");
  }
  
  /**
   * Called when a <b>save a</b> button is pressed
   */
  @FXML
  public void saveAsPressed() {
    ControllerFxGraduation.logger.info("Save as Called");
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
    ControllerFxGraduation.logger.info("Import Called");
  }
  
  /**
   * Called when a <b>export</b> button is pressed
   */
  @FXML
  public void exportPressed() {
    InputOutput.<String>println("Export method");
    this.presenter.exportGrades();
  }
  
  /**
   * Called when a <b>next question</b> button is pressed
   */
  @FXML
  public void nextQuestionPressed() {
    ControllerFxGraduation.logger.info("Next Question Called");
    Boolean _value = this.loadedModel.getValue();
    if ((_value).booleanValue()) {
      this.nextQuestion();
    }
  }
  
  /**
   * Called when a <b>previous question pressed</b> button is pressed
   */
  @FXML
  public void prevQuestionPressed() {
    ControllerFxGraduation.logger.info("Previous Question Called");
    Boolean _value = this.loadedModel.getValue();
    if ((_value).booleanValue()) {
      this.previousQuestion();
    }
  }
  
  /**
   * Called when a <b>next student</b> button is pressed
   */
  @FXML
  public void nextStudentPressed() {
    ControllerFxGraduation.logger.info("Next Student Called");
    Boolean _value = this.loadedModel.getValue();
    if ((_value).booleanValue()) {
      this.nextStudent();
    }
  }
  
  /**
   * Called when a <b>previous student</b> button is pressed
   */
  @FXML
  public void prevStudentPressed() {
    ControllerFxGraduation.logger.info("Previous Student Called");
    Boolean _value = this.loadedModel.getValue();
    if ((_value).booleanValue()) {
      this.previousStudent();
    }
  }
  
  @FXML
  public void swapToEditorPressed() {
  }
  
  @FXML
  public void mainMouseEvent(final MouseEvent e) {
    this.chooseMouseAction(e);
  }
  
  private ControllerFxGraduation.SelectedTool currentTool = ControllerFxGraduation.SelectedTool.NO_TOOL;
  
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
    boolean _equals = Objects.equal(_button, MouseButton.SECONDARY);
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
        case CREATE_ANOTATION_TOOL:
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
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_DRAGGED);
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
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
      this.mouseOriginX = e.getScreenX();
      this.mouseOriginY = e.getScreenY();
      Object _source = e.getSource();
      Node source = ((Node) _source);
      this.objectOriginX = source.getLayoutX();
      this.objectOriginY = source.getLayoutY();
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
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
    this.mainPane.unZoom();
  }
  
  public void init() {
    PdfPaneWithAnotations _pdfPaneWithAnotations = new PdfPaneWithAnotations(this);
    this.mainPane = _pdfPaneWithAnotations;
    this.parentPane.getChildren().add(this.mainPane);
    QuestionListGraduation _questionListGraduation = new QuestionListGraduation(this);
    this.questionList = _questionListGraduation;
    this.questionListContainer.setContent(this.questionList);
    StudentListGraduation _studentListGraduation = new StudentListGraduation(this);
    this.studentList = _studentListGraduation;
    this.studentListContainer.setContent(this.studentList);
    Grader _grader = new Grader(this);
    this.grader = _grader;
    this.graderContainer.getChildren().add(this.grader);
    StudentDetails _studentDetails = new StudentDetails(this);
    this.studentDetails = _studentDetails;
    this.studentDetailsContainer.getChildren().add(this.studentDetails);
    this.unLoaded();
    final ChangeListener<Boolean> _function = (ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
      if ((newVal).booleanValue()) {
        this.loaded();
      } else {
        this.unLoaded();
      }
    };
    this.loadedModel.addListener(_function);
    this.nextQuestionButton.disableProperty().bind(this.loadedModel.not());
    this.prevQuestionButton.disableProperty().bind(this.loadedModel.not());
    this.prevStudentButton.disableProperty().bind(this.loadedModel.not());
    this.nextStudentButton.disableProperty().bind(this.loadedModel.not());
  }
  
  public void binds(final Node n) {
    final EventHandler<KeyEvent> _function = (KeyEvent event) -> {
      KeyCode _code = event.getCode();
      boolean _matched = false;
      if (Objects.equal(_code, FxSettings.BUTTON_NEXT_QUESTION)) {
        _matched=true;
        this.nextQuestionPressed();
      }
      if (!_matched) {
        if (Objects.equal(_code, FxSettings.BUTTON_PREV_QUESTION)) {
          _matched=true;
          this.prevQuestionPressed();
        }
      }
      if (!_matched) {
        if (Objects.equal(_code, FxSettings.BUTTON_PREV_STUDENT)) {
          _matched=true;
          this.prevStudentPressed();
        }
      }
      if (!_matched) {
        if (Objects.equal(_code, FxSettings.BUTTON_NEXT_STUDENT)) {
          _matched=true;
          this.nextStudentPressed();
        }
      }
      if (!_matched) {
        ControllerFxGraduation.logger.warn("Key not supported.");
      }
      event.consume();
    };
    n.setOnKeyPressed(_function);
  }
  
  public void setKeybinds() {
    Scene s = this.mainPane.getScene();
    final EventHandler<KeyEvent> _function = (KeyEvent event) -> {
      KeyCode _code = event.getCode();
      boolean _matched = false;
      if (Objects.equal(_code, FxSettings.BUTTON_NEXT_QUESTION)) {
        _matched=true;
        this.nextQuestionPressed();
      }
      if (!_matched) {
        if (Objects.equal(_code, FxSettings.BUTTON_PREV_QUESTION)) {
          _matched=true;
          this.prevQuestionPressed();
        }
      }
      if (!_matched) {
        if (Objects.equal(_code, FxSettings.BUTTON_PREV_STUDENT)) {
          _matched=true;
          this.prevStudentPressed();
        }
      }
      if (!_matched) {
        if (Objects.equal(_code, FxSettings.BUTTON_NEXT_STUDENT)) {
          _matched=true;
          this.nextStudentPressed();
        }
      }
      if (!_matched) {
        ControllerFxGraduation.logger.warn("Key not supported.");
      }
      event.consume();
    };
    s.setOnKeyPressed(_function);
    this.binds(this.scrollMain);
    this.binds(this.scrollBis);
  }
  
  /**
   * Sets the state of loaded model to true, triggering a set of listeners
   * To be used once the service loads a model
   */
  public void load() {
    this.loadedModel.set(true);
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
      this.presenter.saveTemplate(file.getPath());
      ControllerFxGraduation.logger.info("Saving correction file");
    } else {
      ControllerFxGraduation.logger.warn("File not chosen");
    }
  }
  
  public void loaded() {
    this.renderCorrectedCopy();
    this.renderStudentCopy();
    this.loadQuestions();
    this.loadStudents();
    this.grader.setVisible(true);
    this.questionDetails.setVisible(true);
  }
  
  public void unLoaded() {
    this.grader.setVisible(false);
    this.studentDetails.setVisible(false);
    this.questionList.clearItems();
    this.studentList.clearItems();
  }
  
  public Object update() {
    return null;
  }
  
  public Object selectQuestionWithId(final int id) {
    return null;
  }
  
  public Object selectStudentWithId(final int id) {
    return null;
  }
  
  /**
   * Charge les questions present dans le modele.
   * La liste des etudiants est presente dans studentList, qui affiche tout les etudiants.
   */
  public void loadQuestions() {
    ControllerFxGraduation.logger.info("Loading Questions");
    int currentQuestionId = 0;
    for (int p = 0; (p < this.presenter.getPageAmount()); p++) {
      {
        LinkedList<Integer> ids = this.presenter.initLoading(p);
        for (final int i : ids) {
          {
            QuestionItemGraduation question = new QuestionItemGraduation();
            double _questionX = this.presenter.questionX(i);
            double _multiply = (_questionX * this.imageWidth);
            question.setX(_multiply);
            double _questionY = this.presenter.questionY(i);
            double _multiply_1 = (_questionY * this.imageHeight);
            question.setY(_multiply_1);
            double _questionHeight = this.presenter.questionHeight(i);
            double _multiply_2 = (_questionHeight * this.imageHeight);
            question.setH(_multiply_2);
            double _questionWidth = this.presenter.questionWidth(i);
            double _multiply_3 = (_questionWidth * this.imageWidth);
            question.setW(_multiply_3);
            question.setPage(p);
            question.setQuestionId(i);
            question.setName(this.presenter.questionName(i));
            question.setWorth(Float.valueOf(this.presenter.questionWorth(i)));
            this.questionList.addItem(question);
          }
        }
      }
    }
  }
  
  /**
   * Charge les etudiant present dans le modele.
   * La liste des etudiants est presente dans studentList, qui affiche tout les etudiants.
   */
  public void loadStudents() {
    ControllerFxGraduation.logger.info("Loading Students");
    int currentStudentId = 0;
    LinkedList<Integer> ids = this.presenter.getStudentIds();
    for (final int i : ids) {
      {
        StudentItemGraduation student = new StudentItemGraduation(i);
        this.studentList.addItem(student);
        if ((currentStudentId == i)) {
          this.selectStudent(student);
        }
      }
    }
  }
  
  public boolean createNewAnotation(final MouseEvent e) {
    boolean _xblockexpression = false;
    {
      double _x = e.getX();
      double _imageViewWidth = this.mainPane.getImageViewWidth();
      double _minus = (_imageViewWidth - FxSettings.BOX_BORDER_THICKNESS);
      double mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS, 
        Math.min(_x, _minus));
      double _y = e.getY();
      double _imageViewHeight = this.mainPane.getImageViewHeight();
      double _minus_1 = (_imageViewHeight - FxSettings.BOX_BORDER_THICKNESS);
      double mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS, 
        Math.min(_y, _minus_1));
      _xblockexpression = this.mainPane.addNewAnotation(mousePositionX, mousePositionY);
    }
    return _xblockexpression;
  }
  
  public Object showAnotations() {
    return null;
  }
  
  public boolean hideAnotations() {
    return this.mainPane.removeAllAnotations();
  }
  
  public ControllerFxGraduation.SelectedTool enterAnotationMode() {
    ControllerFxGraduation.SelectedTool _xblockexpression = null;
    {
      this.mainPane.unZoom();
      this.showAnotations();
      _xblockexpression = this.currentTool = ControllerFxGraduation.SelectedTool.CREATE_ANOTATION_TOOL;
    }
    return _xblockexpression;
  }
  
  public ControllerFxGraduation.SelectedTool leaveAnotationMode() {
    ControllerFxGraduation.SelectedTool _xblockexpression = null;
    {
      this.hideAnotations();
      this.mainPane.zoomTo(this.questionList.getCurrentItem().getX(), this.questionList.getCurrentItem().getY(), this.questionList.getCurrentItem().getW(), this.questionList.getCurrentItem().getH());
      _xblockexpression = ControllerFxGraduation.SelectedTool.NO_TOOL;
    }
    return _xblockexpression;
  }
  
  public void renderStudentCopy() {
    BufferedImage image = this.presenter.getPresenterPdf().getCurrentPdfPage();
    this.mainPane.setImage(SwingFXUtils.toFXImage(image, null));
    this.imageWidth = image.getWidth();
    this.imageHeight = image.getHeight();
  }
  
  public void renderCorrectedCopy() {
  }
  
  public void nextStudent() {
    this.studentList.selectNextItem();
    this.presenter.getPresenterQuestion().nextStudent();
    this.setSelectedStudent();
  }
  
  public void previousStudent() {
    this.studentList.selectPreviousItem();
    this.presenter.getPresenterQuestion().previousStudent();
    this.setSelectedStudent();
  }
  
  public void selectStudent(final StudentItemGraduation item) {
    this.studentList.selectItem(item);
    this.setSelectedStudent();
  }
  
  public void setSelectedStudent() {
    boolean _noItems = this.studentList.noItems();
    boolean _not = (!_noItems);
    if (_not) {
      this.focusStudent(this.studentList.getCurrentItem());
      this.updateDisplayedPage();
      this.updateDisplayedGrader();
    } else {
      ControllerFxGraduation.logger.warn("The student list is Empty");
    }
  }
  
  public void nextQuestion() {
    this.questionList.selectNextItem();
    this.presenter.getPresenterQuestion().nextQuestion();
    this.setSelectedQuestion();
  }
  
  public void previousQuestion() {
    this.questionList.selectPreviousItem();
    this.presenter.getPresenterQuestion().previousQuestion();
    this.setSelectedQuestion();
  }
  
  public void selectQuestion(final QuestionItemGraduation item) {
    this.questionList.selectItem(item);
    this.presenter.getPresenterQuestion().selectQuestion(item.getQuestionId());
    this.setSelectedQuestion();
  }
  
  public void setSelectedQuestion() {
    boolean _noItems = this.questionList.noItems();
    boolean _not = (!_noItems);
    if (_not) {
      this.focusQuestion(this.questionList.getCurrentItem());
      this.updateDisplayedPage();
      this.updateDisplayedQuestion();
      this.updateDisplayedGrader();
    } else {
      ControllerFxGraduation.logger.warn("The question list is Empty");
    }
  }
  
  public Class<Void> focusQuestion(final QuestionItemGraduation item) {
    return this.questionList.focusItem(item);
  }
  
  public void focusStudent(final StudentItemGraduation item) {
    this.studentList.focusItem(item);
    this.studentDetails.display(item);
  }
  
  public void updateDisplayedPage() {
    if (((!this.studentList.noItems()) && (!this.questionList.noItems()))) {
      int i = this.presenter.getAbsolutePage(this.studentList.getCurrentItem().getStudentId(), this.questionList.getCurrentItem().getPage());
      boolean _atCorrectPage = this.presenter.getPresenterPdf().atCorrectPage(i);
      boolean _not = (!_atCorrectPage);
      if (_not) {
        ControllerFxGraduation.logger.info("Changing page");
        this.selectPage(this.presenter.getAbsolutePage(this.studentList.getCurrentItem().getStudentId(), this.questionList.getCurrentItem().getPage()));
      }
    } else {
      ControllerFxGraduation.logger.warn("Cannot find correct page, student list or question is is empty");
    }
  }
  
  public void updateDisplayedQuestion() {
    if (this.autoZoom) {
      this.setZoomArea(this.questionList.getCurrentItem().getX(), this.questionList.getCurrentItem().getY(), this.questionList.getCurrentItem().getH(), this.questionList.getCurrentItem().getW());
    }
  }
  
  public void updateDisplayedGrader() {
    if (((!this.studentList.noItems()) && (!this.questionList.noItems()))) {
      this.grader.changeGrader(this.questionList.getCurrentItem(), this.studentList.getCurrentItem());
      this.updateGlobalGrade();
    } else {
      ControllerFxGraduation.logger.warn("Cannot load grader, student list or question is is empty");
    }
  }
  
  public void setZoomArea(final double x, final double y, final double height, final double width) {
    if (this.autoZoom) {
      this.mainPane.zoomTo(x, y, height, width);
    }
  }
  
  public void setGrade(final int studentId, final int questionId, final float grade) {
  }
  
  public void nextPage() {
    this.presenter.getPresenterPdf().nextPdfPage();
    this.renderStudentCopy();
  }
  
  public void previousPage() {
    this.presenter.getPresenterPdf().previousPdfPage();
    this.renderStudentCopy();
  }
  
  public void selectPage(final int pageNumber) {
    this.presenter.getPresenterPdf().goToPdfPage(pageNumber);
    this.renderStudentCopy();
  }
  
  /**
   * Met à jour la note globale affichée
   */
  public void updateGlobalGrade() {
    String _translate = LanguageManager.translate("label.grade");
    String _plus = (_translate + " ");
    float _globalGrade = this.presenter.getGlobalGrade();
    String _plus_1 = (_plus + Float.valueOf(_globalGrade));
    String _plus_2 = (_plus_1 + "/");
    float _globalScale = this.presenter.getGlobalScale();
    String _plus_3 = (_plus_2 + Float.valueOf(_globalScale));
    this.gradeLabel.setText(_plus_3);
  }
  
  @Pure
  public PresenterGraduation getPresenter() {
    return this.presenter;
  }
  
  public void setPresenter(final PresenterGraduation presenter) {
    this.presenter = presenter;
  }
  
  @Pure
  public BooleanProperty getLoadedModel() {
    return this.loadedModel;
  }
  
  public void setLoadedModel(final BooleanProperty loadedModel) {
    this.loadedModel = loadedModel;
  }
}
