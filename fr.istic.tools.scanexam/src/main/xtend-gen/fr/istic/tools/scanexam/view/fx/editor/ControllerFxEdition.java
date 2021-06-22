package fr.istic.tools.scanexam.view.fx.editor;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.GradeScale;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import fr.istic.tools.scanexam.view.fx.FxSettings;
import fr.istic.tools.scanexam.view.fx.PdfManager;
import fr.istic.tools.scanexam.view.fx.editor.Box;
import fr.istic.tools.scanexam.view.fx.editor.BoxType;
import fr.istic.tools.scanexam.view.fx.editor.EdgeLocation;
import fr.istic.tools.scanexam.view.fx.editor.PdfPane;
import fr.istic.tools.scanexam.view.fx.editor.QrCodeZone;
import fr.istic.tools.scanexam.view.fx.editor.QuestionItemEdition;
import fr.istic.tools.scanexam.view.fx.editor.QuestionListEdition;
import fr.istic.tools.scanexam.view.fx.editor.QuestionOptionsEdition;
import fr.istic.tools.scanexam.view.fx.utils.DialogMessageSender;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * This controller controlls the main components of the edition
 * This controlle is associated with a FXML, the static version of the ui.
 * This FX contains "containers" where we will place our dynamic components.
 * These components are initiated during the init method, that is called after the loading of the FXML in the launcher.
 * 
 * This class has differenc sections :
 * 	-Mouse events (mouse inputs for moving the pdf, moving boxes ect).
 * 	-Box management(creating, resizing ect).
 * 	-loading/saving (loading a new model, saving).
 * 	-pdf page management.
 * 	-interactions with the service.
 */
@SuppressWarnings("all")
public class ControllerFxEdition {
  public enum SelectedTool {
    NO_TOOL,
    
    QUESTION_AREA,
    
    ID_AREA,
    
    QR_AREA,
    
    MOVE_CAMERA_TOOL,
    
    MOVE_TOOL,
    
    RESIZE_TOOL;
  }
  
  private Logger logger = LogManager.getLogger();
  
  private ServiceEdition service;
  
  private double maxX;
  
  private double maxY;
  
  private boolean pdfLoaded = false;
  
  /**
   * Permet d'éviter de render plusieurs fois lorsque la valeur de la ChoiceBox est mise à jour par le code
   * (et non l'utilisateur) et qu'elle entraîne un render à cause des bndings.
   */
  private boolean renderFlag = true;
  
  private double mouseOriginX = 0d;
  
  private double mouseOriginY = 0d;
  
  private double objectOriginX = 0d;
  
  private double objectOriginY = 0d;
  
  private Box currentRectangle = null;
  
  private EdgeLocation edge = null;
  
  private double offsetX;
  
  private double offsetY;
  
  private List<Question> questions;
  
  private QrCodeZone qrCodeZone;
  
  /**
   * Setters for the current tool selected
   */
  private ControllerFxEdition.SelectedTool currentTool = ControllerFxEdition.SelectedTool.NO_TOOL;
  
  @FXML
  private ToggleButton createBoxButton;
  
  @FXML
  private ToggleButton createQrButton;
  
  @FXML
  private Button nextPageButton;
  
  @FXML
  private Button previousPageButton;
  
  @FXML
  private ScrollPane questionListContainer;
  
  @FXML
  private ScrollPane gradeListContainer;
  
  @FXML
  private ChoiceBox<Integer> pageChoice;
  
  @FXML
  private Label pageNumberLabel;
  
  @FXML
  private AnchorPane mainPaneContainer;
  
  @Accessors
  private BooleanProperty loadedModel = new SimpleBooleanProperty(this, "Is a model loaded", false);
  
  @Accessors
  private QuestionListEdition questionList;
  
  @Accessors
  private QuestionOptionsEdition questionEditor;
  
  @Accessors
  private PdfManager pdfManager;
  
  @Accessors
  private PdfPane mainPane;
  
  public void init(final ServiceEdition serviceEdition) {
    this.service = serviceEdition;
    PdfManager _pdfManager = new PdfManager();
    this.pdfManager = _pdfManager;
    PdfPane _pdfPane = new PdfPane(this);
    this.mainPane = _pdfPane;
    this.mainPaneContainer.getChildren().add(this.mainPane);
    QuestionListEdition _questionListEdition = new QuestionListEdition(this);
    this.questionList = _questionListEdition;
    this.questionListContainer.setContent(this.questionList);
    QuestionOptionsEdition _questionOptionsEdition = new QuestionOptionsEdition(this);
    this.questionEditor = _questionOptionsEdition;
    this.gradeListContainer.setContent(this.questionEditor);
    final EventHandler<ActionEvent> _function = (ActionEvent event) -> {
      int selectedIndex = this.pageChoice.getSelectionModel().getSelectedIndex();
      if ((!this.renderFlag)) {
        this.selectPage(selectedIndex);
      }
      this.renderFlag = false;
    };
    this.pageChoice.setOnAction(_function);
    this.nextPageButton.disableProperty().bind(this.loadedModel.not());
    this.previousPageButton.disableProperty().bind(this.loadedModel.not());
    this.createBoxButton.disableProperty().bind(this.loadedModel.not());
    this.createQrButton.disableProperty().bind(this.loadedModel.not());
    this.pageChoice.disableProperty().bind(this.loadedModel.not());
  }
  
  /**
   * --LOADING NEW TEMPLATE--
   */
  public List<Integer> initLoading(final int pageNumber) {
    LinkedList<Integer> _xblockexpression = null;
    {
      this.questions = this.service.getQuestionAtPage(pageNumber);
      LinkedList<Integer> ids = new LinkedList<Integer>();
      for (final Question q : this.questions) {
        ids.add(Integer.valueOf(q.getId()));
      }
      _xblockexpression = ids;
    }
    return _xblockexpression;
  }
  
  public void save(final File path) {
    final ByteArrayOutputStream outputStream = this.pdfManager.getPdfOutputStream();
    this.service.saveEdition(outputStream, path);
  }
  
  public void close() {
    System.exit(0);
  }
  
  public void chooseMouseAction(final MouseEvent e) {
    final ControllerFxEdition.SelectedTool currentTool = this.currentTool;
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
  
  /**
   * Called When we decide to focus on a specific question
   */
  public void selectQuestion(final QuestionItemEdition item) {
    if ((item == null)) {
      this.questionList.removeFocus();
      this.questionEditor.hideAll();
      return;
    }
    this.currentRectangle = item.getZone();
    if ((this.qrCodeZone != null)) {
      this.qrCodeZone.getZone().setFocus(false);
    }
    this.questionList.select(item);
    this.questionEditor.select(item);
  }
  
  /**
   * Sélectionne la zone de qr code
   */
  public void selectQr() {
    this.questionList.removeFocus();
    this.questionEditor.hideAll();
    this.currentRectangle = this.qrCodeZone.getZone();
    this.currentRectangle.setFocus(true);
  }
  
  /**
   * Called when we click and drag on the pdf with the create question too selected
   * will not create the question if the zone is too small
   */
  public void createBox(final MouseEvent e) {
    double mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getX(), (this.maxX - FxSettings.BOX_BORDER_THICKNESS)));
    double mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getY(), (this.maxY - FxSettings.BOX_BORDER_THICKNESS)));
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
      this.mouseOriginX = mousePositionX;
      this.mouseOriginY = mousePositionY;
      this.currentRectangle = this.createZone(mousePositionX, mousePositionY);
      this.mainPane.addZone(this.currentRectangle);
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      this.edge = EdgeLocation.SOUTHEAST;
      BoxType _type = this.currentRectangle.getType();
      if (_type != null) {
        switch (_type) {
          case QUESTION:
            this.draggedQuestion(mousePositionX, mousePositionY);
            break;
          case QR:
            this.draggedQrCode(mousePositionX, mousePositionY);
            break;
          default:
            break;
        }
      } else {
      }
    }
    EventType<? extends MouseEvent> _eventType_2 = e.getEventType();
    boolean _equals_2 = Objects.equal(_eventType_2, MouseEvent.MOUSE_RELEASED);
    if (_equals_2) {
      if (((this.currentRectangle.getWidth() > FxSettings.MINIMUM_ZONE_SIZE) && 
        (this.currentRectangle.getHeight() > FxSettings.MINIMUM_ZONE_SIZE))) {
        boolean _equals_3 = Objects.equal(this.currentTool, ControllerFxEdition.SelectedTool.QUESTION_AREA);
        if (_equals_3) {
          this.questionList.newQuestion(this.currentRectangle);
        } else {
          boolean _equals_4 = Objects.equal(this.currentTool, ControllerFxEdition.SelectedTool.QR_AREA);
          if (_equals_4) {
            QrCodeZone _qrCodeZone = new QrCodeZone(this.currentRectangle, this);
            this.qrCodeZone = _qrCodeZone;
          } else {
            BoxType _type_1 = this.currentRectangle.getType();
            boolean _equals_5 = Objects.equal(_type_1, BoxType.QR);
            if (_equals_5) {
              this.qrCodeZone.updateInModel();
            }
          }
        }
      } else {
        this.mainPane.removeZone(this.currentRectangle);
      }
      boolean _equals_6 = Objects.equal(this.currentTool, ControllerFxEdition.SelectedTool.QUESTION_AREA);
      if (_equals_6) {
        this.createBoxButton.setSelected(false);
      } else {
        boolean _equals_7 = Objects.equal(this.currentTool, ControllerFxEdition.SelectedTool.QR_AREA);
        if (_equals_7) {
          this.createQrButton.setSelected(false);
        }
      }
      this.setToNoTool();
    }
  }
  
  /**
   * OLD CODE
   * Called when we click on a pdf with the move tool selected
   * the box is limited to inside the pdf
   */
  public void moveBox(final MouseEvent e) {
    double mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getX(), (this.maxX - FxSettings.BOX_BORDER_THICKNESS)));
    double mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getY(), (this.maxY - FxSettings.BOX_BORDER_THICKNESS)));
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      double _width = this.currentRectangle.getWidth();
      double _minus = ((this.maxX - FxSettings.BOX_BORDER_THICKNESS) - _width);
      this.currentRectangle.x(Math.min((mousePositionX + this.offsetX), _minus));
      double _height = this.currentRectangle.getHeight();
      double _minus_1 = ((this.maxY - FxSettings.BOX_BORDER_THICKNESS) - _height);
      this.currentRectangle.y(Math.min((mousePositionY + this.offsetY), _minus_1));
    }
    EventType<? extends MouseEvent> _eventType_2 = e.getEventType();
    boolean _equals_2 = Objects.equal(_eventType_2, MouseEvent.MOUSE_RELEASED);
    if (_equals_2) {
    }
  }
  
  public void resizeBox(final MouseEvent e) {
    double mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getX(), (this.maxX - FxSettings.BOX_BORDER_THICKNESS)));
    double mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getY(), (this.maxY - FxSettings.BOX_BORDER_THICKNESS)));
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
      this.mouseOriginX = mousePositionX;
      this.mouseOriginY = mousePositionY;
      this.objectOriginX = this.currentRectangle.getWidth();
      this.objectOriginY = this.currentRectangle.getHeight();
      double _x = this.currentRectangle.getX();
      double _minus = (mousePositionX - _x);
      this.offsetX = _minus;
      double _y = this.currentRectangle.getY();
      double _minus_1 = (mousePositionY - _y);
      this.offsetY = _minus_1;
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      BoxType _type = this.currentRectangle.getType();
      if (_type != null) {
        switch (_type) {
          case QUESTION:
            this.draggedQuestion(mousePositionX, mousePositionY);
            break;
          case QR:
            this.draggedQrCode(mousePositionX, mousePositionY);
            break;
          default:
            break;
        }
      } else {
      }
    }
    EventType<? extends MouseEvent> _eventType_2 = e.getEventType();
    boolean _equals_2 = Objects.equal(_eventType_2, MouseEvent.MOUSE_RELEASED);
    if (_equals_2) {
      this.setToNoTool();
      BoxType _type_1 = this.currentRectangle.getType();
      if (_type_1 != null) {
        switch (_type_1) {
          case QUESTION:
            this.questionList.updateInModel(this.currentRectangle.getQuestionItem());
            break;
          case QR:
            this.qrCodeZone.updateInModel();
            break;
          default:
            break;
        }
      } else {
      }
    }
  }
  
  /**
   * Gère le redimensionnage des questions
   * @param mouse PositionX Position sur l'axe X de la souris
   * @param mouse PositionX Position sur l'axe Y de la souris
   */
  private void draggedQuestion(final double mousePositionX, final double mousePositionY) {
    final EdgeLocation edge = this.edge;
    if (edge != null) {
      switch (edge) {
        case SOUTH:
          double _y = this.currentRectangle.getY();
          boolean _greaterThan = (mousePositionY > _y);
          if (_greaterThan) {
            double _y_1 = this.currentRectangle.getY();
            double _minus = (mousePositionY - _y_1);
            this.currentRectangle.height(_minus);
          } else {
            this.edge = EdgeLocation.NORTH;
          }
          break;
        case EAST:
          double _x = this.currentRectangle.getX();
          boolean _greaterThan_1 = (mousePositionX > _x);
          if (_greaterThan_1) {
            double _x_1 = this.currentRectangle.getX();
            double _minus_1 = (mousePositionX - _x_1);
            this.currentRectangle.width(_minus_1);
          } else {
            this.edge = EdgeLocation.WEST;
          }
          break;
        case NORTH:
          double _y_2 = this.currentRectangle.getY();
          double _height = this.currentRectangle.getHeight();
          double _plus = (_y_2 + _height);
          boolean _lessThan = (mousePositionY < _plus);
          if (_lessThan) {
            final double initialY = this.currentRectangle.getY();
            this.currentRectangle.y(mousePositionY);
            double _height_1 = this.currentRectangle.getHeight();
            double _minus_2 = (_height_1 - (mousePositionY - initialY));
            this.currentRectangle.height(_minus_2);
          } else {
            this.edge = EdgeLocation.SOUTH;
          }
          break;
        case WEST:
          double _x_2 = this.currentRectangle.getX();
          double _width = this.currentRectangle.getWidth();
          double _plus_1 = (_x_2 + _width);
          boolean _lessThan_1 = (mousePositionX < _plus_1);
          if (_lessThan_1) {
            final double initialX = this.currentRectangle.getX();
            this.currentRectangle.x(mousePositionX);
            double _width_1 = this.currentRectangle.getWidth();
            double _minus_3 = (_width_1 - (mousePositionX - initialX));
            this.currentRectangle.width(_minus_3);
          } else {
            this.edge = EdgeLocation.EAST;
          }
          break;
        case NORTHEAST:
          double _y_3 = this.currentRectangle.getY();
          double _height_2 = this.currentRectangle.getHeight();
          double _plus_2 = (_y_3 + _height_2);
          boolean _lessThan_2 = (mousePositionY < _plus_2);
          if (_lessThan_2) {
            final double initialY_1 = this.currentRectangle.getY();
            this.currentRectangle.y(mousePositionY);
            double _height_3 = this.currentRectangle.getHeight();
            double _minus_4 = (_height_3 - (mousePositionY - initialY_1));
            this.currentRectangle.height(_minus_4);
          } else {
            this.edge = EdgeLocation.SOUTHEAST;
          }
          double _x_3 = this.currentRectangle.getX();
          boolean _greaterThan_2 = (mousePositionX > _x_3);
          if (_greaterThan_2) {
            double _x_4 = this.currentRectangle.getX();
            double _minus_5 = (mousePositionX - _x_4);
            this.currentRectangle.width(_minus_5);
          } else {
            this.edge = EdgeLocation.NORTHWEST;
          }
          break;
        case NORTHWEST:
          double _y_4 = this.currentRectangle.getY();
          double _height_4 = this.currentRectangle.getHeight();
          double _plus_3 = (_y_4 + _height_4);
          boolean _lessThan_3 = (mousePositionY < _plus_3);
          if (_lessThan_3) {
            final double initialY_2 = this.currentRectangle.getY();
            this.currentRectangle.y(mousePositionY);
            double _height_5 = this.currentRectangle.getHeight();
            double _minus_6 = (_height_5 - (mousePositionY - initialY_2));
            this.currentRectangle.height(_minus_6);
          } else {
            this.edge = EdgeLocation.SOUTHWEST;
          }
          double _x_5 = this.currentRectangle.getX();
          double _width_2 = this.currentRectangle.getWidth();
          double _plus_4 = (_x_5 + _width_2);
          boolean _lessThan_4 = (mousePositionX < _plus_4);
          if (_lessThan_4) {
            final double initialX_1 = this.currentRectangle.getX();
            this.currentRectangle.x(mousePositionX);
            double _width_3 = this.currentRectangle.getWidth();
            double _minus_7 = (_width_3 - (mousePositionX - initialX_1));
            this.currentRectangle.width(_minus_7);
          } else {
            this.edge = EdgeLocation.NORTHEAST;
          }
          break;
        case SOUTHEAST:
          double _y_5 = this.currentRectangle.getY();
          boolean _greaterThan_3 = (mousePositionY > _y_5);
          if (_greaterThan_3) {
            double _y_6 = this.currentRectangle.getY();
            double _minus_8 = (mousePositionY - _y_6);
            this.currentRectangle.height(_minus_8);
          } else {
            this.edge = EdgeLocation.NORTHEAST;
          }
          double _x_6 = this.currentRectangle.getX();
          boolean _greaterThan_4 = (mousePositionX > _x_6);
          if (_greaterThan_4) {
            double _x_7 = this.currentRectangle.getX();
            double _minus_9 = (mousePositionX - _x_7);
            this.currentRectangle.width(_minus_9);
          } else {
            this.edge = EdgeLocation.SOUTHWEST;
          }
          break;
        case SOUTHWEST:
          double _y_7 = this.currentRectangle.getY();
          boolean _greaterThan_5 = (mousePositionY > _y_7);
          if (_greaterThan_5) {
            double _y_8 = this.currentRectangle.getY();
            double _minus_10 = (mousePositionY - _y_8);
            this.currentRectangle.height(_minus_10);
          } else {
            this.edge = EdgeLocation.NORTHWEST;
          }
          double _x_8 = this.currentRectangle.getX();
          double _width_4 = this.currentRectangle.getWidth();
          double _plus_5 = (_x_8 + _width_4);
          boolean _lessThan_5 = (mousePositionX < _plus_5);
          if (_lessThan_5) {
            final double initialX_2 = this.currentRectangle.getX();
            this.currentRectangle.x(mousePositionX);
            double _width_5 = this.currentRectangle.getWidth();
            double _minus_11 = (_width_5 - (mousePositionX - initialX_2));
            this.currentRectangle.width(_minus_11);
          } else {
            this.edge = EdgeLocation.SOUTHEAST;
          }
          break;
        case NONE:
          double _width_6 = this.currentRectangle.getWidth();
          double _minus_12 = ((this.maxX - FxSettings.BOX_BORDER_THICKNESS) - _width_6);
          this.currentRectangle.x(
            Math.max(
              Math.min((mousePositionX - this.offsetX), _minus_12), FxSettings.BOX_BORDER_THICKNESS));
          double _height_6 = this.currentRectangle.getHeight();
          double _minus_13 = ((this.maxY - FxSettings.BOX_BORDER_THICKNESS) - _height_6);
          this.currentRectangle.y(
            Math.max(
              Math.min((mousePositionY - this.offsetY), _minus_13), FxSettings.BOX_BORDER_THICKNESS));
          break;
        default:
          break;
      }
    }
  }
  
  /**
   * Gère le redimensionnage du qr code
   * @param mouse PositionX Position sur l'axe X de la souris
   * @param mouse PositionX Position sur l'axe Y de la souris
   */
  private void draggedQrCode(final double mousePositionX, final double mousePositionY) {
    final EdgeLocation edge = this.edge;
    if (edge != null) {
      switch (edge) {
        case SOUTH:
          double _y = this.currentRectangle.getY();
          boolean _greaterThan = (mousePositionY > _y);
          if (_greaterThan) {
            final double initialX = this.currentRectangle.getX();
            final double initialHeight = this.currentRectangle.getHeight();
            double _y_1 = this.currentRectangle.getY();
            double _minus = (mousePositionY - _y_1);
            this.currentRectangle.height(_minus);
            double _height = this.currentRectangle.getHeight();
            double _minus_1 = (_height - initialHeight);
            double _minus_2 = (initialX - _minus_1);
            this.currentRectangle.x(_minus_2);
            this.currentRectangle.width(this.currentRectangle.getHeight());
          } else {
            this.edge = EdgeLocation.NORTH;
          }
          break;
        case EAST:
          double _x = this.currentRectangle.getX();
          boolean _greaterThan_1 = (mousePositionX > _x);
          if (_greaterThan_1) {
            double _x_1 = this.currentRectangle.getX();
            double _minus_3 = (mousePositionX - _x_1);
            this.currentRectangle.width(_minus_3);
            this.currentRectangle.height(this.currentRectangle.getWidth());
          } else {
            this.edge = EdgeLocation.WEST;
          }
          break;
        case NORTH:
          double _y_2 = this.currentRectangle.getY();
          double _height_1 = this.currentRectangle.getHeight();
          double _plus = (_y_2 + _height_1);
          boolean _lessThan = (mousePositionY < _plus);
          if (_lessThan) {
            final double initialY = this.currentRectangle.getY();
            this.currentRectangle.y(mousePositionY);
            double _height_2 = this.currentRectangle.getHeight();
            double _minus_4 = (_height_2 - (mousePositionY - initialY));
            this.currentRectangle.height(_minus_4);
            this.currentRectangle.width(this.currentRectangle.getHeight());
          } else {
            this.edge = EdgeLocation.SOUTH;
          }
          break;
        case WEST:
          double _x_2 = this.currentRectangle.getX();
          double _width = this.currentRectangle.getWidth();
          double _plus_1 = (_x_2 + _width);
          boolean _lessThan_1 = (mousePositionX < _plus_1);
          if (_lessThan_1) {
            final double initialX_1 = this.currentRectangle.getX();
            final double initialY_1 = this.currentRectangle.getY();
            final double initialWidth = this.currentRectangle.getWidth();
            this.currentRectangle.x(mousePositionX);
            double _width_1 = this.currentRectangle.getWidth();
            double _minus_5 = (_width_1 - (mousePositionX - initialX_1));
            this.currentRectangle.width(_minus_5);
            double _width_2 = this.currentRectangle.getWidth();
            double _minus_6 = (_width_2 - initialWidth);
            double _minus_7 = (initialY_1 - _minus_6);
            this.currentRectangle.y(_minus_7);
            this.currentRectangle.height(this.currentRectangle.getWidth());
          } else {
            this.edge = EdgeLocation.EAST;
          }
          break;
        case NORTHEAST:
          if (((mousePositionY < (this.currentRectangle.getY() + this.currentRectangle.getHeight())) && (mousePositionX > this.currentRectangle.getX()))) {
            final double initialY_2 = this.currentRectangle.getY();
            this.currentRectangle.y(mousePositionY);
            double _height_3 = this.currentRectangle.getHeight();
            double _minus_8 = (_height_3 - (mousePositionY - initialY_2));
            this.currentRectangle.height(_minus_8);
            this.currentRectangle.width(this.currentRectangle.getHeight());
          } else {
            this.edge = EdgeLocation.SOUTHWEST;
          }
          break;
        case NORTHWEST:
          if (((mousePositionY < (this.currentRectangle.getY() + this.currentRectangle.getHeight())) && (mousePositionX < (this.currentRectangle.getX() + this.currentRectangle.getWidth())))) {
            final double initialX_2 = this.currentRectangle.getX();
            final double initialY_3 = this.currentRectangle.getY();
            final double initialWidth_1 = this.currentRectangle.getWidth();
            this.currentRectangle.x(mousePositionX);
            double _width_3 = this.currentRectangle.getWidth();
            double _minus_9 = (_width_3 - (mousePositionX - initialX_2));
            this.currentRectangle.width(_minus_9);
            double _width_4 = this.currentRectangle.getWidth();
            double _minus_10 = (_width_4 - initialWidth_1);
            double _minus_11 = (initialY_3 - _minus_10);
            this.currentRectangle.y(_minus_11);
            this.currentRectangle.height(this.currentRectangle.getWidth());
          } else {
            this.edge = EdgeLocation.SOUTHEAST;
          }
          break;
        case SOUTHEAST:
          if (((mousePositionY > this.currentRectangle.getY()) && (mousePositionX > this.currentRectangle.getX()))) {
            double _x_3 = this.currentRectangle.getX();
            double _minus_12 = (mousePositionX - _x_3);
            this.currentRectangle.width(_minus_12);
            this.currentRectangle.height(this.currentRectangle.getWidth());
          } else {
            this.edge = EdgeLocation.NORTHWEST;
          }
          break;
        case SOUTHWEST:
          if (((mousePositionY > this.currentRectangle.getY()) && (mousePositionX < (this.currentRectangle.getX() + this.currentRectangle.getWidth())))) {
            final double initialX_3 = this.currentRectangle.getX();
            final double initialHeight_1 = this.currentRectangle.getHeight();
            double _y_3 = this.currentRectangle.getY();
            double _minus_13 = (mousePositionY - _y_3);
            this.currentRectangle.height(_minus_13);
            double _height_4 = this.currentRectangle.getHeight();
            double _minus_14 = (_height_4 - initialHeight_1);
            double _minus_15 = (initialX_3 - _minus_14);
            this.currentRectangle.x(_minus_15);
            this.currentRectangle.width(this.currentRectangle.getHeight());
          } else {
            this.edge = EdgeLocation.NORTHEAST;
          }
          break;
        case NONE:
          double _width_5 = this.currentRectangle.getWidth();
          double _minus_16 = ((this.maxX - FxSettings.BOX_BORDER_THICKNESS) - _width_5);
          this.currentRectangle.x(
            Math.max(
              Math.min((mousePositionX - this.offsetX), _minus_16), FxSettings.BOX_BORDER_THICKNESS));
          double _height_5 = this.currentRectangle.getHeight();
          double _minus_17 = ((this.maxY - FxSettings.BOX_BORDER_THICKNESS) - _height_5);
          this.currentRectangle.y(
            Math.max(
              Math.min((mousePositionY - this.offsetY), _minus_17), FxSettings.BOX_BORDER_THICKNESS));
          break;
        default:
          break;
      }
    }
  }
  
  /**
   * Used to move around the image in the parent pane
   * Called when we right click on the pdf
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
      this.mainPane.setCursor(Cursor.DEFAULT);
    }
  }
  
  /**
   * returns a new Box with the right type corresponding to the current tool //TODO maybe move to box as a static method
   */
  public Box createZone(final double x, final double y) {
    Box _xifexpression = null;
    boolean _equals = Objects.equal(this.currentTool, ControllerFxEdition.SelectedTool.QUESTION_AREA);
    if (_equals) {
      _xifexpression = new Box(BoxType.QUESTION, x, y, 0, 0);
    } else {
      Box _xifexpression_1 = null;
      boolean _equals_1 = Objects.equal(this.currentTool, ControllerFxEdition.SelectedTool.QR_AREA);
      if (_equals_1) {
        _xifexpression_1 = new Box(BoxType.QR, x, y, 0, 0);
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  /**
   * Envoie le nom du modèle au service
   * @param templateName Nom du modèle
   */
  public void createExamTemplate(final String templateName) {
    this.service.initializeEdition(this.pdfManager.getPdfPageCount());
    this.service.setExamName(templateName);
  }
  
  /**
   * Saves the current model to a XMI file
   */
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
      boolean _contains = file.getName().contains(".xmi");
      boolean _not = (!_contains);
      if (_not) {
        String _absolutePath = file.getAbsolutePath();
        String _plus_2 = (_absolutePath + ".xmi");
        File _file_1 = new File(_plus_2);
        file = _file_1;
      }
      this.save(file);
    } else {
      this.logger.warn("File not chosen");
    }
  }
  
  /**
   * Loads new model from an xmi file
   */
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
        _xifexpression = this.loadTemplate(file);
      } else {
        this.logger.warn("File not chosen");
      }
      _xblockexpression = _xifexpression;
    }
    return Boolean.valueOf(_xblockexpression);
  }
  
  /**
   * Essaye de charger le fichier passé en paramètre comme Template, affiche un DialogMessage en cas d'erreur
   * @param file un fichier à charger
   */
  public boolean loadTemplate(final File file) {
    boolean _xblockexpression = false;
    {
      java.util.Objects.<File>requireNonNull(file);
      final boolean success = this.load(file.getPath());
      if ((!success)) {
        DialogMessageSender.sendTranslateDialog(Alert.AlertType.ERROR, 
          "studentSheetLoader.templateConfirmationDialog.title", 
          "studentSheetLoader.templateConfirmationDialog.fail", null);
      } else {
        this.render();
      }
      _xblockexpression = success;
    }
    return _xblockexpression;
  }
  
  public boolean load(final String path) {
    final Optional<ByteArrayInputStream> stream = this.service.open(path);
    boolean _isPresent = stream.isPresent();
    if (_isPresent) {
      this.pdfManager.create(stream.get());
    }
    return stream.isPresent();
  }
  
  public boolean render() {
    boolean _xblockexpression = false;
    {
      this.clearVue();
      this.initPageSelection();
      this.renderDocument();
      this.loadQrCodeZone();
      this.loadBoxes();
      _xblockexpression = this.postLoad();
    }
    return _xblockexpression;
  }
  
  /**
   * called to load each question from the model into the vue
   */
  public void loadBoxes() {
    for (int p = 0; (p < this.pdfManager.getPdfPageCount()); p++) {
      {
        List<Integer> ids = this.initLoading(p);
        for (final int i : ids) {
          {
            double _questionX = this.questionX(i);
            double _multiply = (_questionX * this.maxX);
            double _questionY = this.questionY(i);
            double _multiply_1 = (_questionY * this.maxY);
            double _questionWidth = this.questionWidth(i);
            double _multiply_2 = (_questionWidth * this.maxX);
            double _questionHeight = this.questionHeight(i);
            double _multiply_3 = (_questionHeight * this.maxY);
            Box box = new Box(
              BoxType.QUESTION, _multiply, _multiply_1, _multiply_2, _multiply_3);
            this.mainPane.addZone(box);
            this.questionList.loadQuestion(box, this.questionName(i), p, i, this.questionWorth(i));
          }
        }
      }
    }
    this.questionList.showOnlyPage(this.pdfManager.currentPdfPageNumber());
  }
  
  /**
   * Chage la zone de qr code
   */
  public boolean loadQrCodeZone() {
    boolean _xblockexpression = false;
    {
      final Optional<fr.istic.tools.scanexam.core.QrCodeZone> qrCodeInService = this.service.getQrCodeZone();
      boolean _xifexpression = false;
      boolean _isPresent = qrCodeInService.isPresent();
      if (_isPresent) {
        boolean _xblockexpression_1 = false;
        {
          float _x = qrCodeInService.get().getX();
          double _multiply = (_x * this.maxX);
          float _y = qrCodeInService.get().getY();
          double _multiply_1 = (_y * this.maxY);
          float _width = qrCodeInService.get().getWidth();
          double _multiply_2 = (_width * this.maxX);
          float _height = qrCodeInService.get().getHeight();
          double _multiply_3 = (_height * this.maxY);
          final Box qrCodeBox = new Box(
            BoxType.QR, _multiply, _multiply_1, _multiply_2, _multiply_3);
          QrCodeZone _qrCodeZone = new QrCodeZone(qrCodeBox, this);
          this.qrCodeZone = _qrCodeZone;
          _xblockexpression_1 = this.mainPane.addZone(this.qrCodeZone.getZone());
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public boolean postLoad() {
    boolean _xblockexpression = false;
    {
      this.loadedModel.set(true);
      _xblockexpression = this.pdfLoaded = true;
    }
    return _xblockexpression;
  }
  
  /**
   * initialise the choicebox containing all the page numbers of the pdf
   * to call whenever we load a new pdf into the editor
   */
  public void initPageSelection() {
    this.pageChoice.getItems().clear();
    for (int i = 1; (i <= this.pdfManager.getPdfPageCount()); i++) {
      boolean _contains = this.pageChoice.getItems().contains(Integer.valueOf(i));
      boolean _not = (!_contains);
      if (_not) {
        this.pageChoice.getItems().add(Integer.valueOf(i));
      }
    }
  }
  
  /**
   * feches the current buffered image in the presenter representing the pdf and converts it and loads into the imageview
   */
  public void renderDocument() {
    final BufferedImage image = this.pdfManager.getCurrentPdfPage();
    this.mainPane.setImage(SwingFXUtils.toFXImage(image, null));
    this.maxX = this.mainPane.getImageViewWidth();
    this.maxY = this.mainPane.getImageViewHeight();
    String _translate = LanguageManager.translate("label.page");
    int _currentPdfPageNumber = this.pdfManager.currentPdfPageNumber();
    int _plus = (_currentPdfPageNumber + 1);
    String _plus_1 = (_translate + Integer.valueOf(_plus));
    String _plus_2 = (_plus_1 + " / ");
    int _pdfPageCount = this.pdfManager.getPdfPageCount();
    String _plus_3 = (_plus_2 + Integer.valueOf(_pdfPageCount));
    this.pageNumberLabel.setText(_plus_3);
    this.renderFlag = true;
    int _currentPdfPageNumber_1 = this.pdfManager.currentPdfPageNumber();
    int _plus_4 = (_currentPdfPageNumber_1 + 1);
    this.pageChoice.setValue(Integer.valueOf(_plus_4));
  }
  
  /**
   * changes the selected page to load and then renders it
   */
  public void selectPage(final int pageNumber) {
    this.pdfManager.goToPdfPage(pageNumber);
    this.renderDocument();
    this.questionList.showOnlyPage(this.pdfManager.currentPdfPageNumber());
  }
  
  /**
   * goes to the next page of the current pdf
   */
  public void nextPage() {
    this.pdfManager.nextPdfPage();
    this.renderDocument();
    this.questionList.showOnlyPage(this.pdfManager.currentPdfPageNumber());
  }
  
  public void previousPage() {
    this.pdfManager.previousPdfPage();
    this.renderDocument();
    this.questionList.showOnlyPage(this.pdfManager.currentPdfPageNumber());
  }
  
  public double getMaxY() {
    return this.maxY;
  }
  
  public double getMaxX() {
    return this.maxX;
  }
  
  public void clearVue() {
    this.mainPane.clear();
    this.questionList.clear();
    this.questionEditor.hideAll();
  }
  
  public void createQrCode(final double x, final double y, final double height, final double width) {
    this.service.createQrCode(((float) x), ((float) y), ((float) height), ((float) width));
  }
  
  public void resizeQrCode(final double height, final double width) {
    this.service.rescaleQrCode(((float) height), ((float) width));
  }
  
  public void moveQrCode(final double x, final double y) {
    this.service.moveQrCode(((float) x), ((float) y));
  }
  
  public int createQuestion(final double x, final double y, final double height, final double width) {
    return this.service.createQuestion(this.pdfManager.getPdfPageIndex(), ((float) x), ((float) y), ((float) height), ((float) width));
  }
  
  public void removeQuestion(final int ID) {
    this.service.removeQuestion(ID);
  }
  
  public void renameQuestion(final int ID, final String name) {
    this.service.renameQuestion(ID, name);
  }
  
  public void resizeQuestion(final int ID, final double height, final double width) {
    this.service.rescaleQuestion(ID, ((float) height), ((float) width));
  }
  
  /**
   * changes the x and y coordinates of the {@link Question} identified by the id
   * @param int id : the unique ID of question
   * @param float x : new x position
   * @param float y : new y position
   * @author : Benjamin Danlos
   */
  public void moveQuestion(final int id, final double x, final double y) {
    this.service.moveQuestion(id, ((float) x), ((float) y));
  }
  
  public void changeQuestionWorth(final int id, final float worth) {
    this.service.modifyMaxPoint(id, worth);
  }
  
  /**
   * Supprime la zone de qr code de l'écran
   */
  public void removeQrCodeZone() {
    if ((this.qrCodeZone != null)) {
      this.mainPane.removeZone(this.qrCodeZone.getZone());
    }
  }
  
  /**
   * Loads the next question into questionToLoad
   * if there is a new question, return true,
   * else return false
   */
  public double questionX(final int id) {
    double _xblockexpression = (double) 0;
    {
      double result = (-1.0);
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          result = q.getZone().getX();
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
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          result = q.getZone().getY();
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
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          result = q.getZone().getHeigth();
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
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          result = q.getZone().getWidth();
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
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          result = q.getName();
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public float questionWorth(final int id) {
    float _xblockexpression = (float) 0;
    {
      float result = 0f;
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          GradeScale _gradeScale = q.getGradeScale();
          boolean _tripleNotEquals = (_gradeScale != null);
          if (_tripleNotEquals) {
            result = q.getGradeScale().getMaxPoint();
          }
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public ControllerFxEdition.SelectedTool getSelectedTool() {
    return this.currentTool;
  }
  
  public EdgeLocation setEdgeLoc(final EdgeLocation edge) {
    return this.edge = edge;
  }
  
  public ControllerFxEdition.SelectedTool setSelectedTool(final ControllerFxEdition.SelectedTool tool) {
    return this.currentTool = tool;
  }
  
  public void setToMoveCameraTool() {
    this.mainPane.setCursor(Cursor.OPEN_HAND);
    this.currentTool = ControllerFxEdition.SelectedTool.MOVE_CAMERA_TOOL;
  }
  
  public void setToQuestionAreaTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFxEdition.SelectedTool.QUESTION_AREA;
  }
  
  public void setToIDAreaTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFxEdition.SelectedTool.ID_AREA;
  }
  
  public void setToQRAreaTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFxEdition.SelectedTool.QR_AREA;
  }
  
  public void setToMoveTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFxEdition.SelectedTool.MOVE_TOOL;
  }
  
  public void setToResizeTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFxEdition.SelectedTool.RESIZE_TOOL;
  }
  
  public void setToNoTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFxEdition.SelectedTool.NO_TOOL;
  }
  
  @FXML
  public void questionAreaPressed() {
    boolean _isSelected = this.createBoxButton.isSelected();
    if (_isSelected) {
      this.setToQuestionAreaTool();
    } else {
      this.setToNoTool();
    }
  }
  
  @FXML
  public void iDAreaPressed() {
    this.setToIDAreaTool();
  }
  
  @FXML
  public void qrAreaPressed() {
    boolean _isSelected = this.createQrButton.isSelected();
    if (_isSelected) {
      this.removeQrCodeZone();
      this.setToQRAreaTool();
    } else {
      this.setToNoTool();
    }
  }
  
  @FXML
  public void movePressed() {
    this.setToMoveCameraTool();
  }
  
  @FXML
  public void nextPagePressed() {
    if (this.pdfLoaded) {
      this.nextPage();
    }
  }
  
  @FXML
  public void saveTemplatePressed() {
    if (this.pdfLoaded) {
      this.saveTemplate();
    }
  }
  
  @FXML
  public void loadTemplatePressed() {
    this.loadTemplate();
  }
  
  @FXML
  public void previousPagePressed() {
    if (this.pdfLoaded) {
      this.previousPage();
    }
  }
  
  @FXML
  public void switchToCorrectorPressed() {
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
  
  @Pure
  public BooleanProperty getLoadedModel() {
    return this.loadedModel;
  }
  
  public void setLoadedModel(final BooleanProperty loadedModel) {
    this.loadedModel = loadedModel;
  }
  
  @Pure
  public QuestionListEdition getQuestionList() {
    return this.questionList;
  }
  
  public void setQuestionList(final QuestionListEdition questionList) {
    this.questionList = questionList;
  }
  
  @Pure
  public QuestionOptionsEdition getQuestionEditor() {
    return this.questionEditor;
  }
  
  public void setQuestionEditor(final QuestionOptionsEdition questionEditor) {
    this.questionEditor = questionEditor;
  }
  
  @Pure
  public PdfManager getPdfManager() {
    return this.pdfManager;
  }
  
  public void setPdfManager(final PdfManager pdfManager) {
    this.pdfManager = pdfManager;
  }
  
  @Pure
  public PdfPane getMainPane() {
    return this.mainPane;
  }
  
  public void setMainPane(final PdfPane mainPane) {
    this.mainPane = mainPane;
  }
}
