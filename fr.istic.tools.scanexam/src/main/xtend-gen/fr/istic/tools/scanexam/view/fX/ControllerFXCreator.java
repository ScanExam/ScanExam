package fr.istic.tools.scanexam.view.fX;

import com.google.common.base.Objects;
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
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("all")
public class ControllerFXCreator {
  public enum SelectedTool {
    QUESTION_AREA,
    
    ID_AREA,
    
    QR_AREA,
    
    MOVE_TOOL;
  }
  
  private EditorAdapterFX editor;
  
  @FXML
  private Pane mainPane;
  
  @FXML
  private ImageView pdfView;
  
  @FXML
  private ListView<HBox> questionList;
  
  @FXML
  private ChoiceBox<Integer> pageChoice;
  
  @FXML
  private Label introLabel;
  
  private double maxX;
  
  private double maxY;
  
  private Logger logger = LogManager.getLogger();
  
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
    this.setToMoveTool();
  }
  
  @FXML
  public void nextPagePressed() {
    this.nextPage();
  }
  
  @FXML
  public void previousPagePressed() {
    this.previousPage();
  }
  
  @FXML
  public void mainMouseEvent(final MouseEvent e) {
    this.chooseMouseAction(e);
  }
  
  public void chooseMouseAction(final MouseEvent e) {
    final ControllerFXCreator.SelectedTool currentTool = this.currentTool;
    if (currentTool != null) {
      switch (currentTool) {
        case QUESTION_AREA:
          this.CreateBox(e);
          break;
        case ID_AREA:
          break;
        case QR_AREA:
          break;
        case MOVE_TOOL:
          this.MoveImage(e);
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
  
  public void CreateBox(final MouseEvent e) {
    double mousePositionX = Math.max(FXSettings.BOX_BORDER_THICKNESS, Math.min(e.getX(), this.maxX));
    double mousePositionY = Math.max(FXSettings.BOX_BORDER_THICKNESS, Math.min(e.getY(), this.maxY));
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
          ControllerFXCreator.this.highlightBox(((ListViewBox) _source).getParentBox());
        }
      });
      source.getChildren().add(this.currentRectangle);
      this.logger.debug("Created Box");
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
  
  /**
   * Used to move around the image in the parent pane
   */
  public void MoveImage(final MouseEvent e) {
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
   * Setters for the current tool selected
   */
  private ControllerFXCreator.SelectedTool currentTool = ControllerFXCreator.SelectedTool.MOVE_TOOL;
  
  public void setToMoveTool() {
    this.mainPane.setCursor(Cursor.OPEN_HAND);
    this.currentTool = ControllerFXCreator.SelectedTool.MOVE_TOOL;
  }
  
  public void setToQuestionAreaTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFXCreator.SelectedTool.QUESTION_AREA;
  }
  
  public void setToIDAreaTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFXCreator.SelectedTool.ID_AREA;
  }
  
  public void setToQRAreaTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFXCreator.SelectedTool.QR_AREA;
  }
  
  /**
   * returns a new Box with the right type corresponding to the current tool //TODO maybe move to box as a static method
   */
  private int questionCounter = 1;
  
  public Box createBox(final double x, final double y) {
    Box _switchResult = null;
    final ControllerFXCreator.SelectedTool currentTool = this.currentTool;
    if (currentTool != null) {
      switch (currentTool) {
        case QUESTION_AREA:
          int _plusPlus = this.questionCounter++;
          String _plus = ("Question " + Integer.valueOf(_plusPlus));
          int _currentPdfPageNumber = this.editor.getPresenter().getCurrentPdfPageNumber();
          _switchResult = new Box(_plus, _currentPdfPageNumber, Box.BoxType.QUESTION, x, y);
          break;
        case ID_AREA:
          _switchResult = new Box(Box.BoxType.ID, x, y);
          break;
        case QR_AREA:
          _switchResult = new Box(Box.BoxType.QR, x, y);
          break;
        case MOVE_TOOL:
          _switchResult = null;
          break;
        default:
          break;
      }
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
      this.questionList.getItems().add(box.getListViewBox());
      _xblockexpression = this.boxes.add(box);
    }
    return _xblockexpression;
  }
  
  public Object renameBox(final Box box) {
    return null;
  }
  
  public Object moveBox(final Box box) {
    return null;
  }
  
  public Object resizeBox(final Box box) {
    return null;
  }
  
  /**
   * notifies the rest of the program to the removal of a box
   */
  public void removeBox(final Box box) {
    this.editor.removeBox(box);
  }
  
  /**
   * load a new pdf to start the creation of a new template
   */
  @FXML
  public Double onCreateClick() {
    double _xblockexpression = (double) 0;
    {
      FileChooser fileChooser = new FileChooser();
      ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
      List<String> _asList = Arrays.<String>asList("*.pdf");
      FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("PDF files", _asList);
      _extensionFilters.add(_extensionFilter);
      String _property = System.getProperty("user.home");
      String _property_1 = System.getProperty("file.separator");
      String _plus = (_property + _property_1);
      String _plus_1 = (_plus + "Documents");
      File _file = new File(_plus_1);
      fileChooser.setInitialDirectory(_file);
      File file = fileChooser.showOpenDialog(this.mainPane.getScene().getWindow());
      double _xifexpression = (double) 0;
      if ((file != null)) {
        double _xblockexpression_1 = (double) 0;
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
    return Double.valueOf(_xblockexpression);
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
  public double renderDocument() {
    double _xblockexpression = (double) 0;
    {
      this.introLabel.setVisible(false);
      final BufferedImage image = this.editor.getPresenter().getCurrentPdfPage();
      this.pdfView.setImage(SwingFXUtils.toFXImage(image, null));
      double fitW = this.pdfView.getFitWidth();
      double fitH = this.pdfView.getFitHeight();
      double _xifexpression = (double) 0;
      int _height = image.getHeight();
      int _width = image.getWidth();
      boolean _greaterThan = (_height > _width);
      if (_greaterThan) {
        double _xblockexpression_1 = (double) 0;
        {
          this.maxY = fitH;
          int _width_1 = image.getWidth();
          int _height_1 = image.getHeight();
          int _divide = (_width_1 / _height_1);
          double _multiply = (_divide * fitW);
          _xblockexpression_1 = this.maxX = _multiply;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        double _xblockexpression_2 = (double) 0;
        {
          int _height_1 = image.getHeight();
          int _width_1 = image.getWidth();
          int _divide = (_height_1 / _width_1);
          double _multiply = (_divide * fitH);
          double _minus = (_multiply - FXSettings.BOX_BORDER_THICKNESS);
          this.maxY = _minus;
          _xblockexpression_2 = this.maxX = (fitW - FXSettings.BOX_BORDER_THICKNESS);
        }
        _xifexpression = _xblockexpression_2;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * changes the selected page to load and then renders it
   */
  public double selectPage(final int pageNumber) {
    double _xblockexpression = (double) 0;
    {
      this.editor.getPresenter().choosePdfPage(pageNumber);
      _xblockexpression = this.renderDocument();
    }
    return _xblockexpression;
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
  
  public void setEditorAdapterFX(final EditorAdapterFX editor) {
    this.editor = editor;
  }
}
