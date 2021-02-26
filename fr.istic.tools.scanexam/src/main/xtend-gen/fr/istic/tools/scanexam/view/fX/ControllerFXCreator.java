package fr.istic.tools.scanexam.view.fX;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.view.fX.Box;
import fr.istic.tools.scanexam.view.fX.EditorAdapterFX;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.xtext.xbase.lib.InputOutput;

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
  
  private Box currentRectangle = null;
  
  public void CreateBox(final MouseEvent e) {
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
      this.mouseOriginX = e.getX();
      this.mouseOriginY = e.getY();
      Object _source = e.getSource();
      Pane source = ((Pane) _source);
      this.currentRectangle = this.createBox(e.getX(), e.getY());
      source.getChildren().add(this.currentRectangle);
      this.logger.debug("Created Box");
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      double _x = e.getX();
      double xDelta = (_x - this.mouseOriginX);
      double _y = e.getY();
      double yDelta = (_y - this.mouseOriginY);
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
  }
  
  public void MoveImage(final MouseEvent e) {
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
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
  }
  
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
  
  public Box createBox(final double x, final double y) {
    Box _switchResult = null;
    final ControllerFXCreator.SelectedTool currentTool = this.currentTool;
    if (currentTool != null) {
      switch (currentTool) {
        case QUESTION_AREA:
          _switchResult = new Box(Box.BoxType.QUESTION, x, y);
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
  
  @FXML
  public void onCreateClick() {
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
    if ((file != null)) {
      this.editor.getPresenter().create(file);
      this.renderDocument();
    } else {
      this.logger.warn("File not chosen");
    }
  }
  
  public void renderDocument() {
    InputStream _currentPdfPage = this.editor.getPresenter().getCurrentPdfPage();
    Image _image = new Image(_currentPdfPage);
    this.pdfView.setImage(_image);
  }
  
  public void displayPDF(final Image pdf) {
    this.pdfView.setImage(pdf);
  }
  
  public void setEditorAdapterFX(final EditorAdapterFX editor) {
    this.editor = editor;
  }
}
