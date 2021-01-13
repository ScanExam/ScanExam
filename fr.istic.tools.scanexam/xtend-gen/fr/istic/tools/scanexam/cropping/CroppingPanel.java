package fr.istic.tools.scanexam.cropping;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class CroppingPanel extends JPanel {
  BufferedImage image;
  
  Dimension size;
  
  Rectangle clip;
  
  Rectangle resizePatch;
  
  float zoom = 1;
  
  boolean showClip;
  
  private Iterable<BufferedImage> images;
  
  public CroppingPanel(final Iterable<BufferedImage> images) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method get(int) is undefined for the type Iterable<BufferedImage>");
  }
  
  public Optional<String> getExtensionByStringHandling(final String filename) {
    throw new Error("Unresolved compilation problems:"
      + "\n+ cannot be resolved.");
  }
  
  public Stream<String> filewalk() {
    try {
      Stream<String> _xblockexpression = null;
      {
        final Stream<Path> walk = Files.walk(Paths.get("./"));
        final Predicate<Path> _function = (Path it) -> {
          return Files.isRegularFile(it);
        };
        final Function<Path, String> _function_1 = (Path it) -> {
          return it.toString();
        };
        _xblockexpression = walk.filter(_function).<String>map(_function_1);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  protected void paintComponent(final Graphics g) {
    throw new Error("Unresolved compilation problems:"
      + "\n- cannot be resolved."
      + "\n- cannot be resolved."
      + "\n/ cannot be resolved."
      + "\n/ cannot be resolved."
      + "\n=== cannot be resolved."
      + "\nThe method println(String) is undefined"
      + "\n/ cannot be resolved"
      + "\n/ cannot be resolved"
      + "\nintValue cannot be resolved"
      + "\nintValue cannot be resolved");
  }
  
  public int bound(final int v, final int min, final int max) {
    return Math.min(Math.max(v, min), max);
  }
  
  public void setClip(final int x, final int y) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method println(String) is undefined"
      + "\nType mismatch: cannot convert from CharSequence to Graphics");
  }
  
  public void unzoom() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method println(String) is undefined"
      + "\n* cannot be resolved.");
  }
  
  public void zoom() {
    throw new Error("Unresolved compilation problems:"
      + "\n* cannot be resolved."
      + "\nThe method println(String) is undefined");
  }
  
  public void resizeClip(final int w, final int h) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method println(String) is undefined");
  }
  
  @Override
  public Dimension getPreferredSize() {
    return this.size;
  }
  
  public int updatePatch() {
    throw new Error("Unresolved compilation problems:"
      + "\n+ cannot be resolved."
      + "\n+ cannot be resolved."
      + "\n- cannot be resolved"
      + "\n- cannot be resolved");
  }
  
  private void createClip() {
    throw new Error("Unresolved compilation problems:"
      + "\n/ cannot be resolved."
      + "\n/ cannot be resolved.");
  }
  
  private void clipImage() {
    throw new Error("Unresolved compilation problems:"
      + "\n- cannot be resolved."
      + "\n- cannot be resolved."
      + "\n- cannot be resolved."
      + "\n- cannot be resolved."
      + "\n/ cannot be resolved"
      + "\n/ cannot be resolved");
  }
  
  public JPanel getUIPanel() {
    final JCheckBox clipBox = new JCheckBox("show clip", this.showClip);
    final ActionListener _function = (ActionEvent e) -> {
      this.showClip = clipBox.isSelected();
      this.repaint();
    };
    clipBox.addActionListener(_function);
    JButton clip = new JButton("clip image");
    final ActionListener _function_1 = (ActionEvent e) -> {
      this.clipImage();
    };
    clip.addActionListener(_function_1);
    JButton zoom = new JButton("zoom");
    final ActionListener _function_2 = (ActionEvent e) -> {
      this.zoom();
    };
    zoom.addActionListener(_function_2);
    JButton unzoom = new JButton("unzoom");
    final ActionListener _function_3 = (ActionEvent e) -> {
      this.unzoom();
    };
    unzoom.addActionListener(_function_3);
    JPanel panel = new JPanel();
    panel.add(clipBox);
    panel.add(clip);
    panel.add(zoom);
    panel.add(unzoom);
    return panel;
  }
}
