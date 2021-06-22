package fr.istic.tools.scanexam.view.fx.editor;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.view.fx.FxSettings;
import fr.istic.tools.scanexam.view.fx.editor.BoxType;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import fr.istic.tools.scanexam.view.fx.editor.EdgeLocation;
import fr.istic.tools.scanexam.view.fx.editor.PdfPane;
import fr.istic.tools.scanexam.view.fx.editor.QuestionItemEdition;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

@SuppressWarnings("all")
public class Box extends Rectangle {
  /**
   * Type de boîte (question, qr code, etc.)
   */
  private BoxType type;
  
  public Box(final QuestionItemEdition item, final BoxType type, final double x, final double y, final double width, final double height) {
    super(x, y, width, height);
    this.type = type;
    this.questionItem = item;
    this.setStrokeWidth(FxSettings.BOX_BORDER_THICKNESS);
    boolean _equals = Objects.equal(this.type, BoxType.QR);
    if (_equals) {
      this.setStroke(FxSettings.QRZONE_BORDER_NORMAL_COLOR);
    } else {
      this.setStroke(FxSettings.BOX_BORDER_NORMAL_COLOR);
    }
    this.setFill(FxSettings.BOX_NORMAL_COLOR);
  }
  
  public Box(final BoxType type, final double x, final double y, final double width, final double height) {
    this(null, type, x, y, width, height);
  }
  
  private QuestionItemEdition questionItem;
  
  private PdfPane pane;
  
  public QuestionItemEdition getQuestionItem() {
    return this.questionItem;
  }
  
  public BoxType getType() {
    return this.type;
  }
  
  public QuestionItemEdition setQuestionItem(final QuestionItemEdition item) {
    return this.questionItem = item;
  }
  
  public PdfPane setPane(final PdfPane pane) {
    return this.pane = pane;
  }
  
  public void isVisible(final boolean b) {
    this.setVisible(b);
  }
  
  public void setFocus(final boolean b) {
    if (b) {
      this.setStroke(FxSettings.BOX_BORDER_HIGHLIGHT_COLOR);
      this.setFill(FxSettings.BOX_HIGHLIGHT_COLOR);
    } else {
      boolean _equals = Objects.equal(this.type, BoxType.QR);
      if (_equals) {
        this.setStroke(FxSettings.QRZONE_BORDER_NORMAL_COLOR);
      } else {
        this.setStroke(FxSettings.BOX_BORDER_NORMAL_COLOR);
      }
      this.setFill(FxSettings.BOX_NORMAL_COLOR);
    }
  }
  
  public void setColor(final Color color) {
    this.setStroke(color);
  }
  
  public void x(final double x) {
    this.setX(x);
  }
  
  public void y(final double y) {
    this.setY(y);
  }
  
  public void height(final double h) {
    this.setHeight(h);
  }
  
  public void width(final double w) {
    this.setWidth(w);
  }
  
  public boolean checkOnNorthBorder(final MouseEvent e) {
    if (((e.getX() > (this.getX() - FxSettings.ZONE_RESIZE_TOLERANCE)) && 
      (e.getX() < ((this.getX() + this.getWidth()) + FxSettings.ZONE_RESIZE_TOLERANCE)))) {
      if (((e.getY() > (this.getY() - FxSettings.ZONE_RESIZE_TOLERANCE)) && (e.getY() < (this.getY() + FxSettings.ZONE_RESIZE_TOLERANCE)))) {
        return true;
      }
    }
    return false;
  }
  
  public boolean checkOnSouthBorder(final MouseEvent e) {
    if (((e.getX() > (this.getX() - FxSettings.ZONE_RESIZE_TOLERANCE)) && 
      (e.getX() < ((this.getX() + this.getWidth()) + FxSettings.ZONE_RESIZE_TOLERANCE)))) {
      if (((e.getY() > ((this.getY() - FxSettings.ZONE_RESIZE_TOLERANCE) + this.getHeight())) && 
        (e.getY() < ((this.getY() + FxSettings.ZONE_RESIZE_TOLERANCE) + this.getHeight())))) {
        return true;
      }
    }
    return false;
  }
  
  public boolean checkOnWestBorder(final MouseEvent e) {
    if (((e.getX() > (this.getX() - FxSettings.ZONE_RESIZE_TOLERANCE)) && (e.getX() < (this.getX() + FxSettings.ZONE_RESIZE_TOLERANCE)))) {
      if (((e.getY() > (this.getY() - FxSettings.ZONE_RESIZE_TOLERANCE)) && 
        (e.getY() < ((this.getY() + FxSettings.ZONE_RESIZE_TOLERANCE) + this.getHeight())))) {
        return true;
      }
    }
    return false;
  }
  
  public boolean checkOnEastBorder(final MouseEvent e) {
    if (((e.getX() > ((this.getX() - FxSettings.ZONE_RESIZE_TOLERANCE) + this.getWidth())) && 
      (e.getX() < (((this.getX() + this.getWidth()) + FxSettings.ZONE_RESIZE_TOLERANCE) + this.getWidth())))) {
      if (((e.getY() > (this.getY() - FxSettings.ZONE_RESIZE_TOLERANCE)) && 
        (e.getY() < ((this.getY() + FxSettings.ZONE_RESIZE_TOLERANCE) + this.getHeight())))) {
        return true;
      }
    }
    return false;
  }
  
  public Object checkOnCorner() {
    return null;
  }
  
  public void setupEvents(final BoxType type) {
    final Box zone = this;
    zone.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        boolean onNorth = Box.this.checkOnNorthBorder(event);
        boolean onSouth = Box.this.checkOnSouthBorder(event);
        boolean onEast = Box.this.checkOnEastBorder(event);
        boolean onWest = Box.this.checkOnWestBorder(event);
        boolean _equals = Objects.equal(type, BoxType.QUESTION);
        if (_equals) {
          Box.this.pane.getController().selectQuestion(Box.this.questionItem);
        } else {
          boolean _equals_1 = Objects.equal(type, BoxType.QR);
          if (_equals_1) {
            Box.this.pane.getController().selectQr();
          }
        }
        Box.this.pane.getController().setToResizeTool();
        ControllerFxEdition _controller = Box.this.pane.getController();
        _controller.setEdgeLoc(EdgeLocation.NONE);
        if (onNorth) {
          ControllerFxEdition _controller_1 = Box.this.pane.getController();
          _controller_1.setEdgeLoc(EdgeLocation.NORTH);
        }
        if (onSouth) {
          ControllerFxEdition _controller_2 = Box.this.pane.getController();
          _controller_2.setEdgeLoc(EdgeLocation.SOUTH);
        }
        if (onEast) {
          ControllerFxEdition _controller_3 = Box.this.pane.getController();
          _controller_3.setEdgeLoc(EdgeLocation.EAST);
        }
        if (onWest) {
          ControllerFxEdition _controller_4 = Box.this.pane.getController();
          _controller_4.setEdgeLoc(EdgeLocation.WEST);
        }
        if ((onNorth && onEast)) {
          ControllerFxEdition _controller_5 = Box.this.pane.getController();
          _controller_5.setEdgeLoc(EdgeLocation.NORTHEAST);
        }
        if ((onNorth && onWest)) {
          ControllerFxEdition _controller_6 = Box.this.pane.getController();
          _controller_6.setEdgeLoc(EdgeLocation.NORTHWEST);
        }
        if ((onSouth && onEast)) {
          ControllerFxEdition _controller_7 = Box.this.pane.getController();
          _controller_7.setEdgeLoc(EdgeLocation.SOUTHEAST);
        }
        if ((onSouth && onWest)) {
          ControllerFxEdition _controller_8 = Box.this.pane.getController();
          _controller_8.setEdgeLoc(EdgeLocation.SOUTHWEST);
        }
      }
    });
    zone.setOnMouseMoved(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        boolean onNorth = Box.this.checkOnNorthBorder(event);
        boolean onSouth = Box.this.checkOnSouthBorder(event);
        boolean onEast = Box.this.checkOnEastBorder(event);
        boolean onWest = Box.this.checkOnWestBorder(event);
        Box.this.setCursor(Cursor.DEFAULT);
        if (onNorth) {
          Box.this.setCursor(Cursor.V_RESIZE);
        }
        if (onSouth) {
          Box.this.setCursor(Cursor.V_RESIZE);
        }
        if (onEast) {
          Box.this.setCursor(Cursor.H_RESIZE);
        }
        if (onWest) {
          Box.this.setCursor(Cursor.H_RESIZE);
        }
        if ((onNorth && onEast)) {
          Box.this.setCursor(Cursor.NE_RESIZE);
        }
        if ((onNorth && onWest)) {
          Box.this.setCursor(Cursor.NW_RESIZE);
        }
        if ((onSouth && onEast)) {
          Box.this.setCursor(Cursor.NW_RESIZE);
        }
        if ((onSouth && onWest)) {
          Box.this.setCursor(Cursor.NE_RESIZE);
        }
      }
    });
  }
}
