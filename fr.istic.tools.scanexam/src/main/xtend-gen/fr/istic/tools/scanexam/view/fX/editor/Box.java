package fr.istic.tools.scanexam.view.fX.editor;

import fr.istic.tools.scanexam.view.fX.FXSettings;
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
    Box zone = this;
    zone.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        boolean onNorth = Box.this.checkOnNorthBorder(event);
        boolean onSouth = Box.this.checkOnSouthBorder(event);
        boolean onEast = Box.this.checkOnEastBorder(event);
        boolean onWest = Box.this.checkOnWestBorder(event);
        Box.this.pane.getController().selectQuestion(Box.this.questionItem);
      }
    });
    zone.setOnMouseMoved(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        boolean onNorth = Box.this.checkOnNorthBorder(event);
        boolean onSouth = Box.this.checkOnSouthBorder(event);
        boolean onEast = Box.this.checkOnEastBorder(event);
        boolean onWest = Box.this.checkOnWestBorder(event);
        if ((onNorth || onSouth)) {
          Box.this.setCursor(Cursor.V_RESIZE);
        }
        if ((onEast || onWest)) {
          Box.this.setCursor(Cursor.H_RESIZE);
        }
        if ((onSouth && onEast)) {
          Box.this.setCursor(Cursor.NW_RESIZE);
        }
        if ((!(((onEast || onWest) || onNorth) || onSouth))) {
          Box.this.setCursor(Cursor.DEFAULT);
        }
      }
    });
  }
}
