package fr.istic.tools.scanexam.cropping;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

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
    this.images = images;
    this.image = ((BufferedImage[])Conversions.unwrapArray(images, BufferedImage.class))[0];
    Dimension _dimension = new Dimension(2048, 2000);
    this.size = _dimension;
    this.showClip = false;
    this.createClip();
  }
  
  public Optional<String> getExtensionByStringHandling(final String filename) {
    final Predicate<String> _function = (String it) -> {
      return it.contains(".");
    };
    final Function<String, String> _function_1 = (String it) -> {
      int _lastIndexOf = filename.lastIndexOf(".");
      int _plus = (_lastIndexOf + 1);
      return it.substring(_plus);
    };
    return Optional.<String>ofNullable(filename).filter(_function).<String>map(_function_1);
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
    super.paintComponent(g);
    Graphics2D g2 = ((Graphics2D) g);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int _width = this.getWidth();
    int _minus = (_width - this.size.width);
    int x = (_minus / 2);
    int _height = this.getHeight();
    int _minus_1 = (_height - this.size.height);
    int y = (_minus_1 / 2);
    int _width_1 = this.image.getWidth();
    int _height_1 = this.image.getHeight();
    g2.drawImage(this.image, x, y, Float.valueOf((_width_1 / this.zoom)).intValue(), Float.valueOf((_height_1 / this.zoom)).intValue(), this);
    if ((this.clip == null)) {
      this.createClip();
    }
    g2.setPaint(Color.red);
    g2.draw(this.clip);
    g2.setPaint(Color.green);
    g2.draw(this.resizePatch);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.clip);
    _builder.append("+");
    _builder.append(this.resizePatch);
    InputOutput.<String>println(_builder.toString());
  }
  
  public int bound(final int v, final int min, final int max) {
    return Math.min(Math.max(v, min), max);
  }
  
  public void setClip(final int x, final int y) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.clip);
    _builder.append(" goes to ");
    InputOutput.<String>print(_builder.toString());
    this.clip.setLocation(this.bound(x, 0, this.size.width), this.bound(y, 0, this.size.height));
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(this.clip);
    _builder_1.append(" ");
    InputOutput.<String>println(_builder_1.toString());
    this.updatePatch();
    this.repaint();
  }
  
  public void unzoom() {
    InputOutput.<String>println("zooming");
    this.zoom = (this.zoom * 1.2f);
    this.repaint();
  }
  
  public void zoom() {
    this.zoom = (this.zoom * 0.8f);
    InputOutput.<String>println("zooming");
    this.repaint();
  }
  
  public void resizeClip(final int w, final int h) {
    this.clip.width = this.bound(w, 20, this.size.width);
    this.clip.height = this.bound(h, 20, this.size.height);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("resizing to ");
    _builder.append(w);
    _builder.append("x");
    _builder.append(h);
    _builder.append(" => ");
    _builder.append(this.clip.width);
    _builder.append("x");
    _builder.append(this.clip.height);
    InputOutput.<String>println(_builder.toString());
    this.updatePatch();
    this.repaint();
  }
  
  @Override
  public Dimension getPreferredSize() {
    return this.size;
  }
  
  public int updatePatch() {
    int _xblockexpression = (int) 0;
    {
      Rectangle _rectangle = new Rectangle(10, 10);
      this.resizePatch = _rectangle;
      this.resizePatch.x = ((this.clip.x + this.clip.width) - 10);
      _xblockexpression = this.resizePatch.y = ((this.clip.y + this.clip.height) - 10);
    }
    return _xblockexpression;
  }
  
  private void createClip() {
    Rectangle _rectangle = new Rectangle(140, 140);
    this.clip = _rectangle;
    this.clip.x = (this.clip.width / 2);
    this.clip.y = (this.clip.height / 2);
    this.updatePatch();
  }
  
  private void clipImage() {
    BufferedImage clipped = null;
    try {
      int w = this.clip.width;
      int h = this.clip.height;
      int _width = this.getWidth();
      int _minus = (_width - this.size.width);
      int x0 = (_minus / 2);
      int _height = this.getHeight();
      int _minus_1 = (_height - this.size.height);
      int y0 = (_minus_1 / 2);
      int x = (this.clip.x - x0);
      int y = (this.clip.y - y0);
      clipped = this.image.getSubimage(x, y, w, h);
    } catch (final Throwable _t) {
      if (_t instanceof RasterFormatException) {
        final RasterFormatException rfe = (RasterFormatException)_t;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("raster format error: ");
        String _message = rfe.getMessage();
        _builder.append(_message);
        System.out.println(_builder);
        return;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    ImageIcon _imageIcon = new ImageIcon(clipped);
    JLabel label = new JLabel(_imageIcon);
    JOptionPane.showMessageDialog(this, label, "clipped image", JOptionPane.PLAIN_MESSAGE);
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
