package fr.istic.tools.scanexam.cropping;

import fr.istic.tools.scanexam.cropping.CroppingPanel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.InputOutput;

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
    Point p = e.getPoint();
    boolean _contains = this.cropping.clip.contains(p);
    if (_contains) {
      this.offset.x = (p.x - this.cropping.clip.x);
      this.offset.y = (p.y - this.cropping.clip.y);
      this.dragging = true;
      final Rectangle clip = this.cropping.clip;
      if ((p.x > ((clip.x + clip.width) - 10))) {
        if ((p.y > ((clip.y + clip.height) - 10))) {
          InputOutput.<String>println("resizing !");
          this.resizing = true;
        }
      }
    }
  }
  
  @Override
  public void mouseReleased(final MouseEvent e) {
    this.dragging = false;
    this.resizing = false;
  }
  
  @Override
  public void mouseDragged(final MouseEvent e) {
    if (this.dragging) {
      int _x = e.getX();
      int x = (_x - this.offset.x);
      int _y = e.getY();
      int y = (_y - this.offset.y);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("at  ");
      _builder.append(x);
      _builder.append("x");
      _builder.append(y);
      InputOutput.<String>println(_builder.toString());
      if ((!this.resizing)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("moving to ");
        _builder_1.append(x);
        _builder_1.append("x");
        _builder_1.append(y);
        InputOutput.<String>println(_builder_1.toString());
        this.cropping.setClip(x, y);
      } else {
        final Rectangle clip = this.cropping.clip;
        int _x_1 = e.getX();
        final int width = (_x_1 - clip.x);
        int _y_1 = e.getY();
        final int height = (_y_1 - clip.y);
        this.cropping.resizeClip(width, height);
      }
    }
  }
}
