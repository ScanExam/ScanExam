package fr.istic.tools.scanexam.view.fX.editor;

import fr.istic.tools.scanexam.view.fX.FXSettings;
import fr.istic.tools.scanexam.view.fX.editor.ControllerFXEditor;
import fr.istic.tools.scanexam.view.fX.editor.EdgeLocation;
import fr.istic.tools.scanexam.view.fX.editor.PdfPane;
import fr.istic.tools.scanexam.view.fX.editor.QuestionItemEditor;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

@SuppressWarnings("all")
public class Box extends Rectangle {
  public Box(final QuestionItemEditor item, final double x, final double y, final double width, final double height) {
    super(x, y, width, height);
    this.questionItem = item;
    this.setStrokeWidth(FXSettings.BOX_BORDER_THICKNESS);
    this.setStroke(FXSettings.BOX_BORDER_NORMAL_COLOR);
    this.setFill(FXSettings.BOX_NORMAL_COLOR);
  }
  
  public Box(final double x, final double y, final double width, final double height) {
    this(null, x, y, width, height);
  }
  
  private QuestionItemEditor questionItem;
  
  private PdfPane pane;
  
  public QuestionItemEditor getQuestionItem() {
    return this.questionItem;
  }
  
  public QuestionItemEditor setQuestionItem(final QuestionItemEditor item) {
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
      this.setStroke(FXSettings.BOX_BORDER_HIGHLIGHT_COLOR);
      this.setFill(FXSettings.BOX_HIGHLIGHT_COLOR);
    } else {
      this.setStroke(FXSettings.BOX_BORDER_NORMAL_COLOR);
      this.setFill(FXSettings.BOX_NORMAL_COLOR);
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
    if (((e.getX() > (this.getX() - FXSettings.ZONE_RESIZE_TOLERANCE)) && (e.getX() < ((this.getX() + this.getWidth()) + FXSettings.ZONE_RESIZE_TOLERANCE)))) {
      if (((e.getY() > (this.getY() - FXSettings.ZONE_RESIZE_TOLERANCE)) && (e.getY() < (this.getY() + FXSettings.ZONE_RESIZE_TOLERANCE)))) {
        return true;
      }
    }
    return false;
  }
  
  public boolean checkOnSouthBorder(final MouseEvent e) {
    if (((e.getX() > (this.getX() - FXSettings.ZONE_RESIZE_TOLERANCE)) && (e.getX() < ((this.getX() + this.getWidth()) + FXSettings.ZONE_RESIZE_TOLERANCE)))) {
      if (((e.getY() > ((this.getY() - FXSettings.ZONE_RESIZE_TOLERANCE) + this.getHeight())) && (e.getY() < ((this.getY() + FXSettings.ZONE_RESIZE_TOLERANCE) + this.getHeight())))) {
        return true;
      }
    }
    return false;
  }
  
  public boolean checkOnWestBorder(final MouseEvent e) {
    if (((e.getX() > (this.getX() - FXSettings.ZONE_RESIZE_TOLERANCE)) && (e.getX() < (this.getX() + FXSettings.ZONE_RESIZE_TOLERANCE)))) {
      if (((e.getY() > (this.getY() - FXSettings.ZONE_RESIZE_TOLERANCE)) && (e.getY() < ((this.getY() + FXSettings.ZONE_RESIZE_TOLERANCE) + this.getHeight())))) {
        return true;
      }
    }
    return false;
  }
  
  public boolean checkOnEastBorder(final MouseEvent e) {
    if (((e.getX() > ((this.getX() - FXSettings.ZONE_RESIZE_TOLERANCE) + this.getWidth())) && (e.getX() < (((this.getX() + this.getWidth()) + FXSettings.ZONE_RESIZE_TOLERANCE) + this.getWidth())))) {
      if (((e.getY() > (this.getY() - FXSettings.ZONE_RESIZE_TOLERANCE)) && (e.getY() < ((this.getY() + FXSettings.ZONE_RESIZE_TOLERANCE) + this.getHeight())))) {
        return true;
      }
    }
    return false;
  }
  
  public Object checkOnCorner() {
    return null;
  }
  
  public void setupEvents() {
    final Box zone = this;
    zone.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        boolean onNorth = Box.this.checkOnNorthBorder(event);
        boolean onSouth = Box.this.checkOnSouthBorder(event);
        boolean onEast = Box.this.checkOnEastBorder(event);
        boolean onWest = Box.this.checkOnWestBorder(event);
        Box.this.pane.getController().selectQuestion(Box.this.questionItem);
        Box.this.pane.getController().setToResizeTool();
        ControllerFXEditor _controller = Box.this.pane.getController();
        _controller.setEdgeLoc(EdgeLocation.NONE);
        if (onNorth) {
          ControllerFXEditor _controller_1 = Box.this.pane.getController();
          _controller_1.setEdgeLoc(EdgeLocation.NORTH);
        }
        if (onSouth) {
          ControllerFXEditor _controller_2 = Box.this.pane.getController();
          _controller_2.setEdgeLoc(EdgeLocation.SOUTH);
        }
        if (onEast) {
          ControllerFXEditor _controller_3 = Box.this.pane.getController();
          _controller_3.setEdgeLoc(EdgeLocation.EAST);
        }
        if (onWest) {
          ControllerFXEditor _controller_4 = Box.this.pane.getController();
          _controller_4.setEdgeLoc(EdgeLocation.WEST);
        }
        if ((onNorth && onEast)) {
          ControllerFXEditor _controller_5 = Box.this.pane.getController();
          _controller_5.setEdgeLoc(EdgeLocation.NORTHEAST);
        }
        if ((onNorth && onWest)) {
          ControllerFXEditor _controller_6 = Box.this.pane.getController();
          _controller_6.setEdgeLoc(EdgeLocation.NORTHWEST);
        }
        if ((onSouth && onEast)) {
          ControllerFXEditor _controller_7 = Box.this.pane.getController();
          _controller_7.setEdgeLoc(EdgeLocation.SOUTHEAST);
        }
        if ((onSouth && onWest)) {
          ControllerFXEditor _controller_8 = Box.this.pane.getController();
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
