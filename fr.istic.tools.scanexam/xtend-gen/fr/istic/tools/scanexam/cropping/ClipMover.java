package fr.istic.tools.scanexam.cropping;

import fr.istic.tools.scanexam.cropping.CroppingPanel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

@SuppressWarnings("all")
public class ClipMover extends MouseInputAdapter {
  public enum STATE {
    IDLE,
    
    MOVING,
    
    RESIZING;
  }
  
  CroppingPanel cropping;
  
  Point offset;
  
  boolean dragging;
  
  boolean resizing;
  
  private ClipMover.STATE state;
  
  public ClipMover(final CroppingPanel c) {
    this.cropping = c;
    Point _point = new Point();
    this.offset = _point;
    this.dragging = false;
    this.dragging = this.resizing;
  }
  
  @Override
  public void mousePressed(final MouseEvent e) {
    throw new Error("Unresolved compilation problems:"
      + "\n- cannot be resolved."
      + "\n- cannot be resolved."
      + "\n> cannot be resolved."
      + "\n+ cannot be resolved."
      + "\n> cannot be resolved."
      + "\n+ cannot be resolved."
      + "\nThe method println(String) is undefined"
      + "\n- cannot be resolved"
      + "\n- cannot be resolved");
  }
  
  @Override
  public void mouseReleased(final MouseEvent e) {
    this.dragging = false;
    this.resizing = false;
  }
  
  @Override
  public void mouseDragged(final MouseEvent e) {
    throw new Error("Unresolved compilation problems:"
      + "\n- cannot be resolved."
      + "\n- cannot be resolved."
      + "\nThe method println(String) is undefined"
      + "\n! cannot be resolved."
      + "\nThe method println(String) is undefined"
      + "\n- cannot be resolved."
      + "\n- cannot be resolved.");
  }
}
