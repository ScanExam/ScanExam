package fr.istic.tools.scanexam.view.fX;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.launcher.LauncherFX;
import fr.istic.tools.scanexam.view.fX.Box;
import fr.istic.tools.scanexam.view.fX.EditorAdapterFX;
import fr.istic.tools.scanexam.view.fX.FXSettings;
import fr.istic.tools.scanexam.view.fX.ListViewBox;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("all")
public class ControllerFXEditor {
  public enum SelectedTool {
    NO_TOOL,
    
    QUESTION_AREA,
    
    ID_AREA,
    
    QR_AREA,
    
    MOVE_CAMERA_TOOL,
    
    MOVE_TOOL,
    
    RESIZE_TOOL;
  }
  
  private EditorAdapterFX editor;
  
  public void setEditorAdapterFX(final EditorAdapterFX editor) {
    this.editor = editor;
  }
  
  private double maxX;
  
  private double maxY;
  
  private boolean pdfLoaded = false;
  
  private Logger logger = LogManager.getLogger();
  
  @FXML
  private Pane mainPane;
  
  @FXML
  private ImageView pdfView;
  
  @FXML
  private ListView<VBox> questionList;
  
  @FXML
  private ChoiceBox<Integer> pageChoice;
  
  @FXML
  private Label introLabel;
  
  @FXML
  private Label currentToolLabel;
  
  @FXML
  private Label pageNumberLabel;
  
  @FXML
  public void pressed() {
  }
  
  @FXML
  public void questionAreaPressed() {
    this.setToQuestionAreaTool();
  }
  
  @FXML
  public void iDAreaPressed() {
    this.setToIDAreaTool();
  }
  
  @FXML
  public void qRArearessed() {
    this.setToQRAreaTool();
  }
  
  @FXML
  public void movePressed() {
    this.setToMoveCameraTool();
  }
  
  @FXML
  public void nextPagePressed() {
    this.nextPage();
  }
  
  @FXML
  public void newTemplatePressed() {
    this.loadPdf();
  }
  
  @FXML
  public void saveTemplatePressed() {
    this.saveTemplate();
  }
  
  @FXML
  public void loadTemplatePressed() {
    this.loadTemplate();
  }
  
  @FXML
  public void previousPagePressed() {
    this.previousPage();
  }
  
  @FXML
  public void switchToCorrectorPressed() {
    LauncherFX.swapToGraduator();
  }
  
  @FXML
  public void mainMouseEvent(final MouseEvent e) {
    if (this.pdfLoaded) {
      MouseButton _button = e.getButton();
      boolean _equals = Objects.equal(_button, MouseButton.SECONDARY);
      if (_equals) {
        this.moveImage(e);
      } else {
        this.chooseMouseAction(e);
      }
    }
  }
  
  public void chooseMouseAction(final MouseEvent e) {
    final ControllerFXEditor.SelectedTool currentTool = this.currentTool;
    if (currentTool != null) {
      switch (currentTool) {
        case QUESTION_AREA:
          this.createBox(e);
          break;
        case ID_AREA:
          this.createBox(e);
          break;
        case QR_AREA:
          this.createBox(e);
          break;
        case MOVE_TOOL:
          this.moveBox(e);
          break;
        case RESIZE_TOOL:
          this.resizeBox(e);
          break;
        case MOVE_CAMERA_TOOL:
          this.moveImage(e);
          break;
        case NO_TOOL:
          break;
        default:
          break;
      }
    }
  }
  
  private double mouseOriginX = 0d;
  
  private double mouseOriginY = 0d;
  
  private double objectOriginX = 0d;
  
  private double objectOriginY = 0d;
  
  private LinkedList<Box> boxes = new LinkedList<Box>();
  
  private Box currentRectangle = null;
  
  public void createBox(final MouseEvent e) {
    double mousePositionX = Math.max(FXSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getX(), (this.maxX - FXSettings.BOX_BORDER_THICKNESS)));
    double mousePositionY = Math.max(FXSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getY(), (this.maxY - FXSettings.BOX_BORDER_THICKNESS)));
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
      this.mouseOriginX = mousePositionX;
      this.mouseOriginY = mousePositionY;
      Object _source = e.getSource();
      Pane source = ((Pane) _source);
      this.currentRectangle = this.createBox(mousePositionX, mousePositionY);
      ListViewBox _listViewBox = this.currentRectangle.getListViewBox();
      _listViewBox.<MouseEvent>addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(final MouseEvent event) {
          Object _source = event.getSource();
          ControllerFXEditor.this.highlightBox(((ListViewBox) _source).getParentBox());
        }
      });
      source.getChildren().add(this.currentRectangle);
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      double xDelta = (mousePositionX - this.mouseOriginX);
      double yDelta = (mousePositionY - this.mouseOriginY);
      if ((xDelta > 0)) {
        this.currentRectangle.setWidth(xDelta);
      } else {
        this.currentRectangle.setWidth(Math.abs(xDelta));
        double _abs = Math.abs(xDelta);
        double _minus = (this.mouseOriginX - _abs);
        this.currentRectangle.setX(_minus);
      }
      if ((yDelta > 0)) {
        this.currentRectangle.setHeight(yDelta);
      } else {
        this.currentRectangle.setHeight(Math.abs(yDelta));
        double _abs_1 = Math.abs(yDelta);
        double _minus_1 = (this.mouseOriginY - _abs_1);
        this.currentRectangle.setY(_minus_1);
      }
    }
    EventType<? extends MouseEvent> _eventType_2 = e.getEventType();
    boolean _equals_2 = Objects.equal(_eventType_2, MouseEvent.MOUSE_RELEASED);
    if (_equals_2) {
      this.addBox(this.currentRectangle);
    }
  }
  
  public void moveBox(final MouseEvent e) {
    double mousePositionX = Math.max(FXSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getX(), (this.maxX - FXSettings.BOX_BORDER_THICKNESS)));
    double mousePositionY = Math.max(FXSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getY(), (this.maxY - FXSettings.BOX_BORDER_THICKNESS)));
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      double _width = this.currentRectangle.getWidth();
      double _minus = ((this.maxX - FXSettings.BOX_BORDER_THICKNESS) - _width);
      this.currentRectangle.setX(Math.min(mousePositionX, _minus));
      double _height = this.currentRectangle.getHeight();
      double _minus_1 = ((this.maxY - FXSettings.BOX_BORDER_THICKNESS) - _height);
      this.currentRectangle.setY(Math.min(mousePositionY, _minus_1));
    }
    EventType<? extends MouseEvent> _eventType_2 = e.getEventType();
    boolean _equals_2 = Objects.equal(_eventType_2, MouseEvent.MOUSE_RELEASED);
    if (_equals_2) {
    }
  }
  
  public void resizeBox(final MouseEvent e) {
    double mousePositionX = Math.max(FXSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getX(), (this.maxX - FXSettings.BOX_BORDER_THICKNESS)));
    double mousePositionY = Math.max(FXSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getY(), (this.maxY - FXSettings.BOX_BORDER_THICKNESS)));
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      double _x = this.currentRectangle.getX();
      double _minus = (_x - mousePositionX);
      this.currentRectangle.setWidth(Math.abs(_minus));
      double _y = this.currentRectangle.getY();
      double _minus_1 = (_y - mousePositionY);
      this.currentRectangle.setHeight(Math.abs(_minus_1));
    }
    EventType<? extends MouseEvent> _eventType_2 = e.getEventType();
    boolean _equals_2 = Objects.equal(_eventType_2, MouseEvent.MOUSE_RELEASED);
    if (_equals_2) {
      this.resizeBox(this.currentRectangle);
    }
  }
  
  /**
   * Used to move around the image in the parent pane
   */
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
      this.mainPane.setCursor(Cursor.CLOSED_HAND);
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
    EventType<? extends MouseEvent> _eventType_2 = e.getEventType();
    boolean _equals_2 = Objects.equal(_eventType_2, MouseEvent.MOUSE_RELEASED);
    if (_equals_2) {
      this.mainPane.setCursor(Cursor.OPEN_HAND);
    }
  }
  
  /**
   * Used to zoom in and out the pdf image
   * 
   * Using the scale allows the children of the pane to also scale accordingly
   */
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
  }
  
  /**
   * Setters for the current tool selected
   */
  private ControllerFXEditor.SelectedTool currentTool = ControllerFXEditor.SelectedTool.NO_TOOL;
  
  public void setToMoveCameraTool() {
    this.mainPane.setCursor(Cursor.OPEN_HAND);
    this.currentToolLabel.setText(LanguageManager.translate("label.tool.moveCamera"));
    this.currentTool = ControllerFXEditor.SelectedTool.MOVE_CAMERA_TOOL;
  }
  
  public void setToQuestionAreaTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentToolLabel.setText(LanguageManager.translate("label.tool.questionZone"));
    this.currentTool = ControllerFXEditor.SelectedTool.QUESTION_AREA;
  }
  
  public void setToIDAreaTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentToolLabel.setText(LanguageManager.translate("label.tool.idZone"));
    this.currentTool = ControllerFXEditor.SelectedTool.ID_AREA;
  }
  
  public void setToQRAreaTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentToolLabel.setText(LanguageManager.translate("label.tool.qrZone"));
    this.currentTool = ControllerFXEditor.SelectedTool.QR_AREA;
  }
  
  public void setToMoveTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentToolLabel.setText(LanguageManager.translate("label.tool.moveZone"));
    this.currentTool = ControllerFXEditor.SelectedTool.MOVE_TOOL;
  }
  
  public void setToResizeTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentToolLabel.setText(LanguageManager.translate("label.tool.resizeZone"));
    this.currentTool = ControllerFXEditor.SelectedTool.RESIZE_TOOL;
  }
  
  public void setToNoTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentToolLabel.setText(LanguageManager.translate("label.tool.none"));
    this.currentTool = ControllerFXEditor.SelectedTool.NO_TOOL;
  }
  
  /**
   * returns a new Box with the right type corresponding to the current tool //TODO maybe move to box as a static method
   */
  private int questionCounter = 1;
  
  public Box createBox(final double x, final double y) {
    Box _switchResult = null;
    final ControllerFXEditor.SelectedTool currentTool = this.currentTool;
    if (currentTool != null) {
      switch (currentTool) {
        case QUESTION_AREA:
          int _plusPlus = this.questionCounter++;
          String _plus = ("Question " + Integer.valueOf(_plusPlus));
          int _currentPdfPageNumber = this.editor.getPresenter().getCurrentPdfPageNumber();
          _switchResult = new Box(_plus, _currentPdfPageNumber, Box.BoxType.QUESTION, x, y);
          break;
        case ID_AREA:
          int _currentPdfPageNumber_1 = this.editor.getPresenter().getCurrentPdfPageNumber();
          _switchResult = new Box("ID Zone", _currentPdfPageNumber_1, Box.BoxType.ID, x, y);
          break;
        case QR_AREA:
          int _currentPdfPageNumber_2 = this.editor.getPresenter().getCurrentPdfPageNumber();
          _switchResult = new Box("QR Zone", _currentPdfPageNumber_2, Box.BoxType.QR, x, y);
          break;
        default:
          _switchResult = null;
          break;
      }
    } else {
      _switchResult = null;
    }
    return _switchResult;
  }
  
  /**
   * notifies the rest of the program to the addition of a new box
   * 
   * Called when we finish creating a new box (Mouse release)
   */
  public boolean addBox(final Box box) {
    boolean _xblockexpression = false;
    {
      this.editor.addBox(box);
      ListViewBox lb = box.getListViewBox();
      lb.setRemoveAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
          ControllerFXEditor.this.removeBox(box);
        }
      });
      lb.setTextCommit(new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
          Object _source = event.getSource();
          ControllerFXEditor.this.renameBox(box, ((TextField) _source).getText());
          box.getListViewBox().toggleRenaming();
        }
      });
      lb.setPointsCommit(new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
          Object _source = event.getSource();
          ControllerFXEditor.this.changePoints(box, ((TextField) _source).getText());
          box.getListViewBox().togglePointChange();
        }
      });
      lb.setMoveAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
          ControllerFXEditor.this.setToMoveTool();
          ControllerFXEditor.this.currentRectangle = box;
        }
      });
      lb.setResizeAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
          ControllerFXEditor.this.setToResizeTool();
          ControllerFXEditor.this.currentRectangle = box;
        }
      });
      lb.setRenameOption(new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
          box.getListViewBox().toggleRenaming();
        }
      });
      lb.setChangePoints(new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
          box.getListViewBox().togglePointChange();
        }
      });
      this.questionList.getItems().add(lb);
      _xblockexpression = this.boxes.add(box);
    }
    return _xblockexpression;
  }
  
  public void renameBox(final Box box, final String newName) {
    ListViewBox _listViewBox = box.getListViewBox();
    _listViewBox.setLabelText(newName);
    this.editor.getPresenter().getPresenterQuestionZone().renameQuestion(box.getBoxId(), box.getName());
  }
  
  public void moveBox(final Box box) {
    this.editor.getPresenter().getPresenterQuestionZone().moveQuestion(box.getBoxId(), box.getX(), box.getY());
  }
  
  public void resizeBox(final Box box) {
    this.editor.getPresenter().getPresenterQuestionZone().resizeQuestion(box.getBoxId(), box.getHeight(), box.getWidth());
  }
  
  /**
   * notifies the rest of the program to the removal of a box
   */
  public void removeBox(final Box box) {
    this.questionList.getItems().remove(box.getListViewBox());
    this.mainPane.getChildren().remove(box);
    this.boxes.remove(box);
    this.editor.removeBox(box);
    this.setToNoTool();
  }
  
  public void changePoints(final Box box, final String points) {
    ListViewBox _listViewBox = box.getListViewBox();
    _listViewBox.setPointsText(points);
    int number = Integer.parseInt(points);
    this.editor.getPresenter().getPresenterQuestionZone().changeQuestionWorth(box.getBoxId(), number);
  }
  
  /**
   * load a new pdf to start the creation of a new template
   */
  public Boolean loadPdf() {
    boolean _xblockexpression = false;
    {
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
      boolean _xifexpression = false;
      if ((file != null)) {
        boolean _xblockexpression_1 = false;
        {
          this.editor.getPresenter().create(file);
          _xblockexpression_1 = this.renderDocument();
        }
        _xifexpression = _xblockexpression_1;
      } else {
        this.logger.warn("File not chosen");
      }
      _xblockexpression = _xifexpression;
    }
    return Boolean.valueOf(_xblockexpression);
  }
  
  public void saveTemplate() {
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
      this.editor.getPresenter().save(file.getPath());
    } else {
      this.logger.warn("File not chosen");
    }
  }
  
  public Boolean loadTemplate() {
    boolean _xblockexpression = false;
    {
      FileChooser fileChooser = new FileChooser();
      ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
      List<String> _asList = Arrays.<String>asList("*.xmi");
      FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("XMI Files", _asList);
      _extensionFilters.add(_extensionFilter);
      String _property = System.getProperty("user.home");
      String _property_1 = System.getProperty("file.separator");
      String _plus = (_property + _property_1);
      String _plus_1 = (_plus + 
        "Documents");
      File _file = new File(_plus_1);
      fileChooser.setInitialDirectory(_file);
      File file = fileChooser.showOpenDialog(this.mainPane.getScene().getWindow());
      boolean _xifexpression = false;
      if ((file != null)) {
        boolean _xblockexpression_1 = false;
        {
          this.editor.getPresenter().load(file.getPath());
          this.loadBoxes();
          _xblockexpression_1 = this.renderDocument();
        }
        _xifexpression = _xblockexpression_1;
      } else {
        this.logger.warn("File not chosen");
      }
      _xblockexpression = _xifexpression;
    }
    return Boolean.valueOf(_xblockexpression);
  }
  
  public void loadBoxes() {
    this.editor.getPresenter().getPresenterQuestionZone().initLoading();
    while (this.editor.getPresenter().getPresenterQuestionZone().loadNextQuestion()) {
      {
        String _currentQuestionName = this.editor.getPresenter().getPresenterQuestionZone().currentQuestionName();
        int _currentQuestionPage = this.editor.getPresenter().getPresenterQuestionZone().currentQuestionPage();
        double _currentQuestionX = this.editor.getPresenter().getPresenterQuestionZone().currentQuestionX();
        double _currentQuestionY = this.editor.getPresenter().getPresenterQuestionZone().currentQuestionY();
        double _currentQuestionHeight = this.editor.getPresenter().getPresenterQuestionZone().currentQuestionHeight();
        double _currentQuestionWidth = this.editor.getPresenter().getPresenterQuestionZone().currentQuestionWidth();
        Box box = new Box(_currentQuestionName, _currentQuestionPage, 
          Box.BoxType.QUESTION, _currentQuestionX, _currentQuestionY, _currentQuestionHeight, _currentQuestionWidth);
        this.addBox(box);
      }
    }
  }
  
  /**
   * initialise the choicebox containing all the page numbers of the pdf
   * to call whenever we load a new pdf into the editor
   */
  public void initPageSelection() {
    this.pageChoice.getItems().clear();
  }
  
  /**
   * feches the current buffered image in the presenter representing the pdf and converts it and loads into the imageview
   */
  public boolean renderDocument() {
    boolean _xblockexpression = false;
    {
      int _currentPdfPageNumber = this.editor.getPresenter().getCurrentPdfPageNumber();
      int _plus = (_currentPdfPageNumber + 1);
      String _plus_1 = (Integer.valueOf(_plus) + "/");
      int _totalPdfPageNumber = this.editor.getPresenter().getTotalPdfPageNumber();
      String _plus_2 = (_plus_1 + Integer.valueOf(_totalPdfPageNumber));
      this.pageNumberLabel.setText(_plus_2);
      this.introLabel.setVisible(false);
      final BufferedImage image = this.editor.getPresenter().getCurrentPdfPage();
      this.pdfView.setImage(SwingFXUtils.toFXImage(image, null));
      double fitW = this.pdfView.getFitWidth();
      double fitH = this.pdfView.getFitHeight();
      int _height = image.getHeight();
      int _width = image.getWidth();
      boolean _greaterThan = (_height > _width);
      if (_greaterThan) {
        this.maxY = fitH;
        double _width_1 = this.pdfView.getImage().getWidth();
        double _height_1 = this.pdfView.getImage().getHeight();
        double _divide = (_width_1 / _height_1);
        double _multiply = (_divide * fitW);
        this.maxX = _multiply;
      } else {
        double _height_2 = this.pdfView.getImage().getHeight();
        double _width_2 = this.pdfView.getImage().getWidth();
        double _divide_1 = (_height_2 / _width_2);
        double _multiply_1 = (_divide_1 * fitH);
        this.maxY = _multiply_1;
        this.maxX = fitW;
      }
      _xblockexpression = this.pdfLoaded = true;
    }
    return _xblockexpression;
  }
  
  /**
   * changes the selected page to load and then renders it
   */
  public void selectPage(final int pageNumber) {
    this.editor.getPresenter().choosePdfPage(pageNumber);
    this.renderDocument();
    this.showOnlyPage(this.editor.getPresenter().getCurrentPdfPageNumber());
  }
  
  /**
   * goes to the next page of the current pdf
   */
  public void nextPage() {
    this.editor.getPresenter().nextPdfPage();
    this.renderDocument();
    this.showOnlyPage(this.editor.getPresenter().getCurrentPdfPageNumber());
  }
  
  public void previousPage() {
    this.editor.getPresenter().previousPdfPage();
    this.renderDocument();
    this.showOnlyPage(this.editor.getPresenter().getCurrentPdfPageNumber());
  }
  
  public void showOnlyPage(final int page) {
    for (final Box b : this.boxes) {
      int _pageNumber = b.getPageNumber();
      boolean _equals = (_pageNumber == page);
      if (_equals) {
        b.setVisible(true);
      } else {
        b.setVisible(false);
      }
    }
  }
  
  /**
   * Highlights the Box box, called when we click on a box on the listview
   */
  private Box highlightedBox = null;
  
  public void highlightBox(final Box box) {
    if ((this.highlightedBox != null)) {
      this.highlightedBox.setFocus(false);
    }
    this.highlightedBox = box;
    this.highlightedBox.setFocus(true);
  }
}