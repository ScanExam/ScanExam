package fr.istic.tools.scanexam.view.fx.graduation;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.exportation.ExportExamToPdf;
import fr.istic.tools.scanexam.exportation.GradesExportImpl;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.utils.Tuple3;
import fr.istic.tools.scanexam.view.fx.FxSettings;
import fr.istic.tools.scanexam.view.fx.PdfManager;
import fr.istic.tools.scanexam.view.fx.graduation.Grader;
import fr.istic.tools.scanexam.view.fx.graduation.PdfPaneWithAnotations;
import fr.istic.tools.scanexam.view.fx.graduation.QuestionItemGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.QuestionListGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.StudentDetails;
import fr.istic.tools.scanexam.view.fx.graduation.StudentItemGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.StudentListGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.TextAnotation;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
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
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
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
    
    CREATE_ANOTATION_TOOL,
    
    MOVE_ANOTATION_TOOL,
    
    MOVE_POINTER_TOOL,
    
    MOVE_GRADER_TOOL;
  }
  
  private static final Logger logger = LogManager.getLogger();
  
  private BooleanProperty loadedModel = new SimpleBooleanProperty(this, "Is a template loaded", false);
  
  public void setToLoaded() {
    this.loadedModel.set(false);
    this.loadedModel.set(true);
  }
  
  public void toNotLoaded() {
    this.loadedModel.set(false);
  }
  
  public BooleanProperty getLoadedModel() {
    return this.loadedModel;
  }
  
  private Grader grader;
  
  private QuestionListGraduation questionList;
  
  private StudentListGraduation studentList;
  
  private StudentDetails studentDetails;
  
  public PdfPaneWithAnotations mainPane;
  
  private boolean botShow = false;
  
  private boolean autoZoom = true;
  
  /**
   * FXML Components
   */
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
  public ToggleButton annotationModeButton;
  
  @FXML
  public ToggleButton addAnnotationButton;
  
  @Accessors
  private ServiceGraduation service;
  
  @Accessors
  private PdfManager pdfManager;
  
  /**
   * FXML Actions.
   */
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
  public void mainMouseEvent(final MouseEvent e) {
    this.chooseMouseAction(e);
  }
  
  @FXML
  public void parentMouseEvent(final MouseEvent e) {
    boolean _equals = Objects.equal(this.currentTool, ControllerFxGraduation.SelectedTool.MOVE_GRADER_TOOL);
    if (_equals) {
      this.moveGrader(e);
    }
  }
  
  @Accessors
  private ControllerFxGraduation.SelectedTool currentTool = ControllerFxGraduation.SelectedTool.NO_TOOL;
  
  @Accessors
  private double imageWidth;
  
  @Accessors
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
          this.moveImage(e);
          break;
        case MOVE_CAMERA_TOOL:
          this.moveImage(e);
          break;
        case CREATE_ANOTATION_TOOL:
          this.createNewAnotation(e);
          break;
        case MOVE_ANOTATION_TOOL:
          this.moveAnotation(e);
          break;
        case MOVE_POINTER_TOOL:
          this.movePointer(e);
          break;
        case MOVE_GRADER_TOOL:
          return;
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
  
  public void moveGrader(final MouseEvent e) {
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      this.grader.setLayoutX(e.getX());
      this.grader.setLayoutY(e.getY());
    }
    EventType<? extends MouseEvent> _eventType_2 = e.getEventType();
    boolean _equals_2 = Objects.equal(_eventType_2, MouseEvent.MOUSE_RELEASED);
    if (_equals_2) {
      this.currentTool = ControllerFxGraduation.SelectedTool.NO_TOOL;
    }
  }
  
  @Accessors
  private TextAnotation currentAnotation;
  
  public void moveAnotation(final MouseEvent e) {
    double _x = e.getX();
    double _imageViewWidth = this.mainPane.getImageViewWidth();
    double _minus = (_imageViewWidth - FxSettings.BOX_BORDER_THICKNESS);
    double _width = this.currentAnotation.getWidth();
    double _minus_1 = (_minus - _width);
    double mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS, 
      Math.min(_x, _minus_1));
    double _y = e.getY();
    double _imageViewHeight = this.mainPane.getImageViewHeight();
    double _minus_2 = (_imageViewHeight - FxSettings.BOX_BORDER_THICKNESS);
    double _height = this.currentAnotation.getHeight();
    double _minus_3 = (_minus_2 - _height);
    double mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS, 
      Math.min(_y, _minus_3));
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_DRAGGED);
    if (_equals) {
      this.currentAnotation.move(mousePositionX, mousePositionY);
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_RELEASED);
    if (_equals_1) {
      this.updateAnnotation(this.currentAnotation);
      this.currentTool = ControllerFxGraduation.SelectedTool.NO_TOOL;
    }
  }
  
  public void movePointer(final MouseEvent e) {
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
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_DRAGGED);
    if (_equals) {
      this.currentAnotation.movePointer(mousePositionX, mousePositionY);
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_RELEASED);
    if (_equals_1) {
      this.updateAnnotation(this.currentAnotation);
      this.currentTool = ControllerFxGraduation.SelectedTool.NO_TOOL;
    }
  }
  
  @FXML
  public void ZoomImage(final ScrollEvent e) {
    Object _source = e.getSource();
    Node source = ((Node) _source);
    double _deltaY = e.getDeltaY();
    boolean _lessThan = (_deltaY < 0);
    if (_lessThan) {
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
    this.grader.setLayoutX(0);
    this.grader.setLayoutY(0);
    this.mainPane.unZoom();
  }
  
  public void init(final ServiceGraduation serviceGraduation) {
    this.parentPane.getStyleClass().add("parentPane");
    PdfManager _pdfManager = new PdfManager();
    this.pdfManager = _pdfManager;
    this.service = serviceGraduation;
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
    this.parentPane.getChildren().add(this.grader);
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
    final ChangeListener<Boolean> _function_1 = (ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
      if ((newVal).booleanValue()) {
        this.enterAnotationMode();
      } else {
        this.leaveAnotationMode();
      }
    };
    this.annotationModeButton.selectedProperty().addListener(_function_1);
    final ChangeListener<Boolean> _function_2 = (ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
      this.setToCreateAnnotation((newVal).booleanValue());
    };
    this.addAnnotationButton.selectedProperty().addListener(_function_2);
    this.nextQuestionButton.disableProperty().bind(this.loadedModel.not());
    this.prevQuestionButton.disableProperty().bind(this.loadedModel.not());
    this.prevStudentButton.disableProperty().bind(this.loadedModel.not());
    this.nextStudentButton.disableProperty().bind(this.loadedModel.not());
    this.annotationModeButton.disableProperty().bind(this.loadedModel.not());
    this.addAnnotationButton.disableProperty().bind(this.loadedModel.not());
  }
  
  public void setKeybinds() {
    final Scene s = this.mainPane.getScene();
    final EventHandler<KeyEvent> _function = (KeyEvent event) -> {
      Node node = s.getFocusOwner();
      if ((node instanceof TextInputControl)) {
      } else {
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
          if (Objects.equal(_code, FxSettings.BUTTON_INTERACT_GRADER_1)) {
            _matched=true;
            this.grader.interactUsingIndex(1);
          }
        }
        if (!_matched) {
          if (Objects.equal(_code, FxSettings.BUTTON_INTERACT_GRADER_2)) {
            _matched=true;
            this.grader.interactUsingIndex(2);
          }
        }
        if (!_matched) {
          if (Objects.equal(_code, FxSettings.BUTTON_INTERACT_GRADER_3)) {
            _matched=true;
            this.grader.interactUsingIndex(3);
          }
        }
        if (!_matched) {
          if (Objects.equal(_code, FxSettings.BUTTON_INTERACT_GRADER_4)) {
            _matched=true;
            this.grader.interactUsingIndex(4);
          }
        }
        if (!_matched) {
          if (Objects.equal(_code, FxSettings.BUTTON_INTERACT_GRADER_5)) {
            _matched=true;
            this.grader.interactUsingIndex(5);
          }
        }
        if (!_matched) {
          if (Objects.equal(_code, FxSettings.BUTTON_INTERACT_GRADER_6)) {
            _matched=true;
            this.grader.interactUsingIndex(6);
          }
        }
        if (!_matched) {
          if (Objects.equal(_code, FxSettings.BUTTON_INTERACT_GRADER_7)) {
            _matched=true;
            this.grader.interactUsingIndex(7);
          }
        }
        if (!_matched) {
          if (Objects.equal(_code, FxSettings.BUTTON_INTERACT_GRADER_8)) {
            _matched=true;
            this.grader.interactUsingIndex(8);
          }
        }
        if (!_matched) {
          if (Objects.equal(_code, FxSettings.BUTTON_INTERACT_GRADER_9)) {
            _matched=true;
            this.grader.interactUsingIndex(9);
          }
        }
        if (!_matched) {
          if (Objects.equal(_code, FxSettings.BUTTON_INTERACT_GRADER_0)) {
            _matched=true;
            this.grader.interactUsingIndex(10);
          }
        }
        if (!_matched) {
          ControllerFxGraduation.logger.warn("Key not supported.");
        }
        event.consume();
      }
    };
    s.<KeyEvent>addEventFilter(KeyEvent.KEY_PRESSED, _function);
  }
  
  /**
   * Sets the state of loaded model to true, triggering a set of listeners
   * To be used once the service loads a model
   */
  public boolean load(final File file) {
    final Optional<InputStream> streamOpt = this.service.openCorrectionTemplate(file);
    boolean _isPresent = streamOpt.isPresent();
    if (_isPresent) {
      this.pdfManager.create(streamOpt.get());
      this.setToLoaded();
      return true;
    }
    return false;
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
      boolean _contains = file.getName().contains(".xmi");
      boolean _not = (!_contains);
      if (_not) {
        String _absolutePath = file.getAbsolutePath();
        String _plus_2 = (_absolutePath + ".xmi");
        File _file_1 = new File(_plus_2);
        file = _file_1;
      }
      this.saveTemplate(file.getPath());
      ControllerFxGraduation.logger.info("Saving correction file");
    } else {
      ControllerFxGraduation.logger.warn("File not chosen");
    }
  }
  
  /**
   * Exporte la correction des copies au format PDF
   * @param folder Dossier où exporter
   */
  public void exportGraduationToPdf(final File folder) {
    final Function1<StudentSheet, Boolean> _function = (StudentSheet sheet) -> {
      return Boolean.valueOf(((sheet.getStudentName() != null) && (!sheet.getStudentName().matches("\\s*"))));
    };
    final List<StudentSheet> sheets = IterableExtensions.<StudentSheet>toList(IterableExtensions.<StudentSheet>filter(this.service.getStudentSheets(), _function));
    ExportExamToPdf.exportExamsOfStudentsToPdfsWithAnnotations(this.service, this.pdfManager.getPdfInputStream(), sheets, folder, this.mainPane.getImageViewWidth());
  }
  
  /**
   * Cette methode est a apeler une fois que le modele est pret.
   * Pour charger les donne du modele dans lest list etudioant et questions
   */
  public void loaded() {
    this.unLoaded();
    ControllerFxGraduation.logger.info("Loading Vue");
    this.renderCorrectedCopy();
    this.renderStudentCopy();
    this.loadQuestions();
    this.loadStudents();
    this.setSelectedQuestion();
    this.setSelectedStudent();
    double _width = this.parentPane.getWidth();
    double _width_1 = this.grader.getWidth();
    double _minus = (_width - _width_1);
    this.grader.setLayoutX(_minus);
    this.grader.setLayoutY(0);
    this.grader.setVisible(true);
  }
  
  public void unLoaded() {
    ControllerFxGraduation.logger.info("Clearing current Vue");
    this.grader.setVisible(false);
    this.studentDetails.setVisible(false);
    this.questionList.clearItems();
    this.studentList.clearItems();
  }
  
  /**
   * Envoie le nom du modèle au service
   * @param templateName Nom du modèle
   */
  public void sendExamNameToService(final String templateName) {
    this.service.setExamName(templateName);
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
    for (int p = 0; (p < this.service.getPageAmount()); p++) {
      {
        LinkedList<Integer> ids = this.initLoading(p);
        for (final int i : ids) {
          {
            QuestionItemGraduation question = new QuestionItemGraduation();
            question.setPage(p);
            question.setQuestionId(i);
            question.setName(this.questionName(i));
            this.questionList.addItem(question);
          }
        }
      }
    }
    boolean _noItems = this.questionList.noItems();
    if (_noItems) {
      ControllerFxGraduation.logger.warn("The view has received no questions from service");
    }
  }
  
  /**
   * Charge les etudiant present dans le modele.
   * La liste des etudiants est presente dans studentList, qui affiche tout les etudiants.
   */
  public void loadStudents() {
    ControllerFxGraduation.logger.info("Loading Students");
    LinkedList<Integer> ids = this.getStudentIds();
    Collections.<Integer>sort(ids);
    for (final int i : ids) {
      {
        StudentItemGraduation student = new StudentItemGraduation(i);
        String name = this.service.getStudentName(i).orElse("");
        if (((name == null) || (name == ""))) {
          String _translate = LanguageManager.translate("name.default");
          String _plus = (_translate + " ");
          String _plus_1 = (_plus + Integer.valueOf(i));
          student.setStudentName(_plus_1);
        } else {
          student.setStudentName(name);
        }
        this.studentList.addItem(student);
      }
    }
  }
  
  /**
   * Utiliser pour ajouter une anotations a la vue avec la sourie.
   */
  public void createNewAnotation(final MouseEvent e) {
    double _x = e.getX();
    double _imageViewWidth = this.mainPane.getImageViewWidth();
    double _minus = (_imageViewWidth - FxSettings.BOX_BORDER_THICKNESS);
    double _minus_1 = (_minus - TextAnotation.defaultWidth);
    double mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS, 
      Math.min(_x, _minus_1));
    double _y = e.getY();
    double _imageViewHeight = this.mainPane.getImageViewHeight();
    double _minus_2 = (_imageViewHeight - FxSettings.BOX_BORDER_THICKNESS);
    double _minus_3 = (_minus_2 - TextAnotation.defaultHeight);
    double mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS, 
      Math.min(_y, _minus_3));
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
      TextAnotation annot = this.mainPane.addNewAnotation(mousePositionX, mousePositionY);
      this.addAnnotation(annot);
      this.addAnnotationButton.setSelected(false);
    }
  }
  
  public ControllerFxGraduation.SelectedTool setToCreateAnnotation(final boolean b) {
    ControllerFxGraduation.SelectedTool _xifexpression = null;
    if (b) {
      ControllerFxGraduation.SelectedTool _xblockexpression = null;
      {
        if ((!this.annotationMode)) {
          this.annotationModeButton.setSelected(true);
        }
        _xblockexpression = this.currentTool = ControllerFxGraduation.SelectedTool.CREATE_ANOTATION_TOOL;
      }
      _xifexpression = _xblockexpression;
    } else {
      _xifexpression = this.currentTool = ControllerFxGraduation.SelectedTool.NO_TOOL;
    }
    return _xifexpression;
  }
  
  /**
   * Affiche toutes les annotations pour la page courrant et l'etudiant courrant
   */
  public void showAnotations() {
    this.mainPane.displayAnnotationsFor(this.questionList.getCurrentItem(), this.studentList.getCurrentItem());
  }
  
  /**
   * Enleve toutes les annotations de la vue
   */
  public boolean hideAnotations() {
    return this.mainPane.removeAllAnotations();
  }
  
  private boolean annotationMode = false;
  
  private boolean previousZoomMode = true;
  
  /**
   * On rentre dans le mode d'annotations.
   * il faut dezoom, afficher les annotations et metter l'outils courrant au mode anotation.
   */
  public boolean enterAnotationMode() {
    boolean _xblockexpression = false;
    {
      this.mainPane.unZoom();
      this.previousZoomMode = this.autoZoom;
      this.autoZoom = false;
      this.showAnotations();
      _xblockexpression = this.annotationMode = true;
    }
    return _xblockexpression;
  }
  
  public boolean leaveAnotationMode() {
    boolean _xblockexpression = false;
    {
      this.hideAnotations();
      this.mainPane.zoomTo(this.questionList.getCurrentItem().getX(), this.questionList.getCurrentItem().getY(), this.questionList.getCurrentItem().getH(), this.questionList.getCurrentItem().getW());
      this.addAnnotationButton.setSelected(false);
      this.annotationMode = false;
      _xblockexpression = this.autoZoom = this.previousZoomMode;
    }
    return _xblockexpression;
  }
  
  public void nextStudent() {
    this.studentList.selectNextItem();
    this.service.nextSheet();
    this.setSelectedStudent();
  }
  
  public void previousStudent() {
    this.studentList.selectPreviousItem();
    this.service.previousSheet();
    this.setSelectedStudent();
  }
  
  public void selectStudent(final StudentItemGraduation item) {
    this.studentList.selectItem(item);
    this.service.selectSheet(this.studentList.getCurrentItem().getStudentId());
    this.setSelectedStudent();
  }
  
  public void setSelectedStudent() {
    boolean _noItems = this.studentList.noItems();
    boolean _not = (!_noItems);
    if (_not) {
      this.focusStudent(this.studentList.getCurrentItem());
      this.updateDisplayedPage();
      this.updateDisplayedGrader();
      this.updateStudentDetails();
      this.updateDisplayedAnnotations();
    } else {
      ControllerFxGraduation.logger.warn("The student list is Empty");
    }
  }
  
  public void nextQuestion() {
    this.questionList.selectNextItem();
    this.setSelectedQuestion();
  }
  
  public void previousQuestion() {
    this.questionList.selectPreviousItem();
    this.setSelectedQuestion();
  }
  
  public void selectQuestion(final QuestionItemGraduation item) {
    this.questionList.selectItem(item);
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
      this.updateStudentDetails();
      this.updateDisplayedAnnotations();
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
  
  /**
   * Called when we change tabs, used to update information of questions such as worth(does not update name TODO)
   */
  public void changedTab() {
    boolean _noItems = this.questionList.noItems();
    boolean _not = (!_noItems);
    if (_not) {
      this.grader.prepForTabChange();
      this.updateQuestionList();
      this.setSelectedQuestion();
    }
  }
  
  public void updateQuestionList() {
    int selectedId = this.questionList.getCurrentItem().getQuestionId();
    this.questionList.clearItems();
    this.loadQuestions();
    this.selectQuestion(this.questionList.questionWithId(selectedId));
  }
  
  public void renderStudentCopy() {
    ControllerFxGraduation.logger.info("Call to RenderStudentCopy");
    BufferedImage image = this.pdfManager.getCurrentPdfPage();
    this.mainPane.setImage(SwingFXUtils.toFXImage(image, null));
    this.imageWidth = image.getWidth();
    this.imageHeight = image.getHeight();
  }
  
  public void renderCorrectedCopy() {
  }
  
  /**
   * Checks if we need to change the page and changes it if we need to.
   */
  public void updateDisplayedPage() {
    if (((!this.studentList.noItems()) && (!this.questionList.noItems()))) {
      int i = this.service.getAbsolutePageNumber(this.studentList.getCurrentItem().getStudentId(), this.questionList.getCurrentItem().getPage());
      boolean _atCorrectPage = this.pdfManager.atCorrectPage(i);
      boolean _not = (!_atCorrectPage);
      if (_not) {
        ControllerFxGraduation.logger.info("Changing page");
        this.selectPage(this.service.getAbsolutePageNumber(this.studentList.getCurrentItem().getStudentId(), this.questionList.getCurrentItem().getPage()));
      }
    } else {
      ControllerFxGraduation.logger.warn("Cannot find correct page, student list or question is is empty");
    }
  }
  
  /**
   * Changes the zoom to the current questions dimentions
   */
  public void updateDisplayedQuestion() {
    if (this.autoZoom) {
      this.setZoomArea(this.questionList.getCurrentItem().getX(), this.questionList.getCurrentItem().getY(), this.questionList.getCurrentItem().getH(), this.questionList.getCurrentItem().getW());
    }
  }
  
  public void updateDisplayedGrader() {
    if (((!this.studentList.noItems()) && (!this.questionList.noItems()))) {
      this.grader.changeGrader(this.questionList.getCurrentItem(), this.studentList.getCurrentItem());
    } else {
      ControllerFxGraduation.logger.warn("Cannot load grader, student list or question is is empty");
    }
  }
  
  /**
   * Met à jour les détails de l'étudiant
   */
  public void updateStudentDetails() {
    this.studentDetails.updateGrade();
    this.studentDetails.updateQuality();
  }
  
  public void updateDisplayedAnnotations() {
    if (this.annotationMode) {
      this.showAnotations();
    }
  }
  
  public void setZoomArea(final double x, final double y, final double height, final double width) {
    if (this.autoZoom) {
      this.mainPane.zoomTo(x, y, height, width);
    }
  }
  
  public void nextPage() {
    this.pdfManager.nextPdfPage();
    this.renderStudentCopy();
  }
  
  public void previousPage() {
    this.pdfManager.previousPdfPage();
    this.renderStudentCopy();
  }
  
  public void selectPage(final int pageNumber) {
    this.pdfManager.goToPdfPage(pageNumber);
    this.renderStudentCopy();
  }
  
  public List<Integer> getEntryIds(final int questionId) {
    LinkedList<Integer> _xblockexpression = null;
    {
      List<Tuple3<Integer, String, Float>> l = this.service.getQuestionGradeEntries(questionId);
      LinkedList<Integer> result = new LinkedList<Integer>();
      for (final Tuple3<Integer, String, Float> t : l) {
        result.add(t._1);
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public List<Integer> getSelectedEntryIds(final int questionId) {
    return this.service.getQuestionSelectedGradeEntries(questionId);
  }
  
  public String getEntryText(final int entryId, final int questionId) {
    String _xblockexpression = null;
    {
      List<Tuple3<Integer, String, Float>> l = this.service.getQuestionGradeEntries(questionId);
      for (final Tuple3<Integer, String, Float> t : l) {
        if ((entryId == (t._1).intValue())) {
          return t._2;
        }
      }
      _xblockexpression = "Entry not found";
    }
    return _xblockexpression;
  }
  
  public float getEntryWorth(final int entryId, final int questionId) {
    List<Tuple3<Integer, String, Float>> l = this.service.getQuestionGradeEntries(questionId);
    for (final Tuple3<Integer, String, Float> t : l) {
      if ((entryId == (t._1).intValue())) {
        return (t._3).floatValue();
      }
    }
    return (-1);
  }
  
  /**
   * Retourne la note maximale que peut encore obtenir l'étudiant
   * @return Note maximale que peut encore obtenir l'étudiant
   */
  public float getCurrentMaxGrade() {
    return this.service.getCurrentMaxGrade();
  }
  
  public void updateGlobalGrade() {
    this.studentDetails.updateGrade();
  }
  
  /**
   * Retourne la note globale de la copie
   * @return Note globale de la copie
   */
  public float getGlobalGrade() {
    return this.service.getCurrentGrade();
  }
  
  /**
   * Retourne le barème total de l'examen
   * @return Barème total de l'examen
   */
  public float getGlobalScale() {
    return this.service.getGlobalScale();
  }
  
  /**
   * SAVING
   */
  public void saveTemplate(final String path) {
    this.service.saveCorrectionTemplate(path, this.pdfManager.getPdfOutputStream());
  }
  
  /**
   * STUDENTS
   */
  public List<String> getStudentsSuggestedNames(final String start) {
    final Function1<String, Boolean> _function = (String n) -> {
      String _lowerCase = n.toLowerCase();
      String _xifexpression = null;
      if ((start == null)) {
        _xifexpression = "";
      } else {
        _xifexpression = start.toLowerCase();
      }
      return Boolean.valueOf(_lowerCase.contains(_xifexpression));
    };
    return IterableExtensions.<String>toList(IterableExtensions.<String>filter(this.service.getStudentNames(), _function));
  }
  
  public LinkedList<Integer> getStudentIds() {
    LinkedList<Integer> _xblockexpression = null;
    {
      Collection<StudentSheet> list = this.service.getStudentSheets();
      LinkedList<Integer> result = new LinkedList<Integer>();
      if ((list != null)) {
        for (final StudentSheet s : list) {
          result.add(Integer.valueOf(s.getId()));
        }
      } else {
        ControllerFxGraduation.logger.warn("Service returned null studentId list");
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public LinkedList<Integer> initLoading(final int pageNumber) {
    LinkedList<Integer> _xblockexpression = null;
    {
      LinkedList<Integer> ids = new LinkedList<Integer>();
      List<Question> _questionAtPage = this.service.getQuestionAtPage(pageNumber);
      for (final Question q : _questionAtPage) {
        ids.add(Integer.valueOf(q.getId()));
      }
      _xblockexpression = ids;
    }
    return _xblockexpression;
  }
  
  public double questionX(final int id) {
    double _xblockexpression = (double) 0;
    {
      double result = (-1.0);
      for (int i = 0; (i < this.service.getPageAmount()); i++) {
        List<Question> _questionAtPage = this.service.getQuestionAtPage(i);
        for (final Question q : _questionAtPage) {
          int _id = q.getId();
          boolean _equals = (_id == id);
          if (_equals) {
            result = q.getZone().getX();
          }
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public double questionY(final int id) {
    double _xblockexpression = (double) 0;
    {
      double result = (-1.0);
      for (int i = 0; (i < this.service.getPageAmount()); i++) {
        List<Question> _questionAtPage = this.service.getQuestionAtPage(i);
        for (final Question q : _questionAtPage) {
          int _id = q.getId();
          boolean _equals = (_id == id);
          if (_equals) {
            result = q.getZone().getY();
          }
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public double questionHeight(final int id) {
    double _xblockexpression = (double) 0;
    {
      double result = (-1.0);
      for (int i = 0; (i < this.service.getPageAmount()); i++) {
        List<Question> _questionAtPage = this.service.getQuestionAtPage(i);
        for (final Question q : _questionAtPage) {
          int _id = q.getId();
          boolean _equals = (_id == id);
          if (_equals) {
            result = q.getZone().getHeigth();
          }
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public double questionWidth(final int id) {
    double _xblockexpression = (double) 0;
    {
      double result = (-1.0);
      for (int i = 0; (i < this.service.getPageAmount()); i++) {
        List<Question> _questionAtPage = this.service.getQuestionAtPage(i);
        for (final Question q : _questionAtPage) {
          int _id = q.getId();
          boolean _equals = (_id == id);
          if (_equals) {
            result = q.getZone().getWidth();
          }
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public String questionName(final int id) {
    String _xblockexpression = null;
    {
      String result = "";
      for (int i = 0; (i < this.service.getPageAmount()); i++) {
        List<Question> _questionAtPage = this.service.getQuestionAtPage(i);
        for (final Question q : _questionAtPage) {
          int _id = q.getId();
          boolean _equals = (_id == id);
          if (_equals) {
            result = q.getName();
          }
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public float questionWorth(final int id) {
    float _xblockexpression = (float) 0;
    {
      float result = (-1f);
      for (int i = 0; (i < this.service.getPageAmount()); i++) {
        List<Question> _questionAtPage = this.service.getQuestionAtPage(i);
        for (final Question q : _questionAtPage) {
          int _id = q.getId();
          boolean _equals = (_id == id);
          if (_equals) {
            result = q.getGradeScale().getMaxPoint();
          }
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public void exportGrades() {
    FileChooser fileChooser = new FileChooser();
    ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
    String _translate = LanguageManager.translate("exportExcel.fileFormat");
    List<String> _asList = Arrays.<String>asList("*.xlsx");
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter(_translate, _asList);
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
      boolean _contains = file.getName().contains(".xlsx");
      boolean _not = (!_contains);
      if (_not) {
        String _absolutePath = file.getAbsolutePath();
        String _plus_2 = (_absolutePath + ".xlsx");
        File _file_1 = new File(_plus_2);
        file = _file_1;
      }
      this.saveTemplate(file.getPath());
      ControllerFxGraduation.logger.info("Export grade in Excel");
    } else {
      ControllerFxGraduation.logger.warn("File not chosen");
    }
    new GradesExportImpl().exportGrades(this.service.getStudentSheets(), file);
  }
  
  public boolean applyGrade(final int questionId, final int gradeId) {
    return this.service.assignGradeEntry(questionId, gradeId);
  }
  
  public boolean removeGrade(final int questionId, final int gradeId) {
    return this.service.retractGradeEntry(questionId, gradeId);
  }
  
  /**
   * Ajoute une nouvelle entrée à la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle ajouter l'entrée
   * @param desc la description de l'entrée
   * @param point le nombre de point de l'entrée
   * @return l'ID de l'entrée
   */
  public int addEntry(final int questionId, final String desc, final float point) {
    return (this.service.addEntry(questionId, desc, point).get()).intValue();
  }
  
  /**
   * Modifie une entrée de la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle modifier l'entrée
   * @param gradeEntryId l'ID de l'entrée à modifier
   * @param desc la nouvelle description de l'entrée
   * @param point le nouveau nombre de point de l'entrée
   */
  public void modifyEntry(final int questionId, final int gradeEntryId, final String desc, final float point) {
    this.service.modifyEntry(questionId, gradeEntryId, desc, point);
  }
  
  public Object modifyEntryWorth(final int questionId, final int gradeEntryId, final float point) {
    return null;
  }
  
  /**
   * Supprime une entrée de la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle supprimer l'entrée
   * @param gradeEntryId l'ID de l'entrée à supprimer
   */
  public void removeEntry(final int questionId, final int gradeEntryId) {
    this.service.removeEntry(questionId, gradeEntryId);
  }
  
  public void renameStudent(final int studentId, final String newname) {
    this.service.assignStudentName(newname);
  }
  
  public void addAnnotation(final TextAnotation annot) {
    annot.setAnnotId(this.service.addNewAnnotation(annot.getAnnotX(), annot.getAnnotY(), annot.getAnnotW(), annot.getAnnotH(), annot.getAnnotPointerX(), annot.getAnnotPointerY(), annot.getAnnotText(), this.questionList.getCurrentItem().getQuestionId(), this.questionList.getCurrentItem().getPage()));
    int _annotId = annot.getAnnotId();
    String _plus = ("Adding new Annotation to Model : ID = " + Integer.valueOf(_annotId));
    ControllerFxGraduation.logger.info(_plus);
  }
  
  public void updateAnnotation(final TextAnotation annot) {
    int _annotId = annot.getAnnotId();
    String _plus = ("Updating annotation in Model : ID = " + Integer.valueOf(_annotId));
    ControllerFxGraduation.logger.info(_plus);
    this.service.updateAnnotation(annot.getAnnotX(), annot.getAnnotY(), annot.getAnnotW(), annot.getAnnotH(), annot.getAnnotPointerX(), annot.getAnnotPointerY(), annot.getAnnotText(), annot.getAnnotId(), this.questionList.getCurrentItem().getQuestionId(), this.studentList.getCurrentItem().getStudentId());
  }
  
  public void removeAnnotation(final TextAnotation annot) {
    int _annotId = annot.getAnnotId();
    String _plus = ("Removing Annotation from  Model : ID = " + Integer.valueOf(_annotId));
    ControllerFxGraduation.logger.info(_plus);
    this.service.removeAnnotation(annot.getAnnotId(), this.questionList.getCurrentItem().getQuestionId(), this.studentList.getCurrentItem().getStudentId());
  }
  
  @Pure
  public ServiceGraduation getService() {
    return this.service;
  }
  
  public void setService(final ServiceGraduation service) {
    this.service = service;
  }
  
  @Pure
  public PdfManager getPdfManager() {
    return this.pdfManager;
  }
  
  public void setPdfManager(final PdfManager pdfManager) {
    this.pdfManager = pdfManager;
  }
  
  @Pure
  public ControllerFxGraduation.SelectedTool getCurrentTool() {
    return this.currentTool;
  }
  
  public void setCurrentTool(final ControllerFxGraduation.SelectedTool currentTool) {
    this.currentTool = currentTool;
  }
  
  @Pure
  public double getImageWidth() {
    return this.imageWidth;
  }
  
  public void setImageWidth(final double imageWidth) {
    this.imageWidth = imageWidth;
  }
  
  @Pure
  public double getImageHeight() {
    return this.imageHeight;
  }
  
  public void setImageHeight(final double imageHeight) {
    this.imageHeight = imageHeight;
  }
  
  @Pure
  public TextAnotation getCurrentAnotation() {
    return this.currentAnotation;
  }
  
  public void setCurrentAnotation(final TextAnotation currentAnotation) {
    this.currentAnotation = currentAnotation;
  }
}
