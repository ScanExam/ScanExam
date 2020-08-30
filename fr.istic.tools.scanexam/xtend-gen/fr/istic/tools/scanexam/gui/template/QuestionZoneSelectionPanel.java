package fr.istic.tools.scanexam.gui.template;

import fr.istic.tools.scanexam.Exam;
import fr.istic.tools.scanexam.utils.ScanExamXtendUtils;
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
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class QuestionZoneSelectionPanel extends JPanel implements ListSelectionListener {
  BufferedImage image;
  
  Dimension size;
  
  Rectangle clip;
  
  Rectangle resizePatch;
  
  float zoom = 1;
  
  boolean showClip;
  
  private JLabel label;
  
  private JLabel etiquette;
  
  private JList<String> liste;
  
  private List<File> imageFiles;
  
  private int pageindex = 0;
  
  public QuestionZoneSelectionPanel(final Exam exam) {
    try {
      this.imageFiles = IterableExtensions.<File>toList(((Iterable<File>)Conversions.doWrapArray(ScanExamXtendUtils.convertPdfToPng(exam.getFilepath()))));
      this.image = ImageIO.read(this.imageFiles.get(this.pageindex));
      Dimension _dimension = new Dimension(2048, 2000);
      this.size = _dimension;
      this.createClip();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  protected void paintComponent(final Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = ((Graphics2D) g);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int _width = this.image.getWidth();
    int _height = this.image.getHeight();
    g2.drawImage(this.image, this.getX(), this.getY(), Float.valueOf((_width / this.zoom)).intValue(), Float.valueOf((_height / this.zoom)).intValue(), this);
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
  
  private int bound(final int v, final int min, final int max) {
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
    this.label.setText(("Zoom:" + Float.valueOf(this.zoom)));
    this.repaint();
  }
  
  public void zoom() {
    this.zoom = (this.zoom * 0.8f);
    InputOutput.<String>println("zooming");
    this.label.setText(("Zoom:" + Float.valueOf(this.zoom)));
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
  
  private void nextPage() {
    try {
      int _size = this.imageFiles.size();
      int _modulo = ((this.pageindex + 1) % _size);
      this.pageindex = _modulo;
      this.image = ImageIO.read(this.imageFiles.get(this.pageindex));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
    JLabel _jLabel = new JLabel(("Zoom level :" + Float.valueOf(this.zoom)));
    this.label = _jLabel;
    JButton clip = new JButton("clip image");
    final ActionListener _function = (ActionEvent e) -> {
      this.clipImage();
    };
    clip.addActionListener(_function);
    JButton nextPage = new JButton("next page");
    final ActionListener _function_1 = (ActionEvent e) -> {
      this.nextPage();
    };
    clip.addActionListener(_function_1);
    JTextField QuestionId = new JTextField("     ");
    JButton zoom = new JButton("zoom in");
    final ActionListener _function_2 = (ActionEvent e) -> {
      this.zoom();
    };
    zoom.addActionListener(_function_2);
    JButton unzoom = new JButton("zoom out");
    final ActionListener _function_3 = (ActionEvent e) -> {
      this.unzoom();
    };
    unzoom.addActionListener(_function_3);
    this.setVisible(true);
    JPanel panel = new JPanel();
    panel.add(nextPage);
    panel.add(clip);
    panel.add(QuestionId);
    panel.add(this.label);
    panel.add(zoom);
    panel.add(unzoom);
    return panel;
  }
  
  @Override
  public void valueChanged(final ListSelectionEvent e) {
    this.etiquette.setText(this.liste.getSelectedValue());
  }
}
