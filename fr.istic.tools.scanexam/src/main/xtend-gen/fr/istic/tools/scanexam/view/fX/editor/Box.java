package fr.istic.tools.scanexam.view.fX.editor;

import fr.istic.tools.scanexam.view.fX.FXSettings;
import fr.istic.tools.scanexam.view.fX.editor.EditorQuestionItem;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Box extends Rectangle {
  public Box(final EditorQuestionItem item, final double x, final double y, final double width, final double height) {
    super(x, y, width, height);
    this.questionItem = item;
    this.setStrokeWidth(FXSettings.BOX_BORDER_THICKNESS);
    this.setStroke(FXSettings.BOX_BORDER_NORMAL_COLOR);
    this.setFill(FXSettings.BOX_NORMAL_COLOR);
  }
  
  public Box(final double x, final double y, final double width, final double height) {
    this(null, x, y, width, height);
  }
  
  private EditorQuestionItem questionItem;
  
  public EditorQuestionItem getQuestionItem() {
    return this.questionItem;
  }
  
  public EditorQuestionItem setQuestionItem(final EditorQuestionItem item) {
    return this.questionItem = item;
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
    zone.setOnMouseMoved(new EventHandler<MouseEvent>() {
      public void handle(final MouseEvent event) {
        boolean _checkOnNorthBorder = Box.this.checkOnNorthBorder(event);
        if (_checkOnNorthBorder) {
          InputOutput.<String>print("on north \n");
        }
        boolean _checkOnSouthBorder = Box.this.checkOnSouthBorder(event);
        if (_checkOnSouthBorder) {
          InputOutput.<String>print("on south \n");
        }
        boolean _checkOnEastBorder = Box.this.checkOnEastBorder(event);
        if (_checkOnEastBorder) {
          InputOutput.<String>print("on East \n");
        }
        boolean _checkOnWestBorder = Box.this.checkOnWestBorder(event);
        if (_checkOnWestBorder) {
          InputOutput.<String>print("on West \n");
        }
      }
    });
  }
}
