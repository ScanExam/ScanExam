package fr.istic.tools.scanexam.gui;

import fr.istic.tools.scanexam.Question;
import fr.istic.tools.scanexam.ScanZone;
import fr.istic.tools.scanexam.gui.ScanExamController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class ScanExamPanel extends JPanel {
  Dimension size;
  
  BufferedImage clip;
  
  Rectangle resizePatch;
  
  float zoom = 1;
  
  boolean showClip;
  
  private ScanExamController control;
  
  public ScanExamPanel(final ScanExamController control) {
    Dimension _dimension = new Dimension(2048, 2000);
    this.size = _dimension;
    this.control = control;
    control.loadCurrentPage();
    control.setPanel(this);
    this.updateQuestionZone();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("zone ");
    ScanZone _zone = control.getCurrentQuestion().getZone();
    _builder.append(_zone);
    InputOutput.<String>println(_builder.toString());
  }
  
  public void updateQuestionZone() {
    this.clip = this.control.extractQuestionZone();
    this.clip = ScanExamPanel.scale(this.clip, BufferedImage.TYPE_BYTE_GRAY, this.size.width, this.size.height, 0.5, 0.5);
    this.repaint();
  }
  
  public static BufferedImage scale(final BufferedImage sbi, final int imageType, final int dWidth, final int dHeight, final double fWidth, final double fHeight) {
    BufferedImage dbi = null;
    if ((sbi != null)) {
      BufferedImage _bufferedImage = new BufferedImage(dWidth, dHeight, imageType);
      dbi = _bufferedImage;
      final Graphics2D g = dbi.createGraphics();
      final AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
      g.drawRenderedImage(sbi, at);
    }
    return dbi;
  }
  
  @Override
  protected void paintComponent(final Graphics g) {
    try {
      super.paintComponent(g);
      Graphics2D g2 = ((Graphics2D) g);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.drawImage(this.clip, 10, 40, this);
      final Question q = this.control.getCurrentQuestion();
      StringConcatenation _builder = new StringConcatenation();
      ScanZone _markZone = q.getMarkZone();
      _builder.append(_markZone);
      InputOutput.<String>println(_builder.toString());
      Font _font = new Font("Helvetica", Font.BOLD, 20);
      g2.setFont(_font);
      String _label = q.getLabel();
      String _plus = ("Question:" + _label);
      g2.drawString(_plus, 40, 30);
      int _currentGradeIndex = this.control.getCurrentGradeIndex();
      String _plus_1 = ("index=" + Integer.valueOf(_currentGradeIndex));
      g2.drawString(_plus_1, 490, 30);
      String _currentGradeValue = this.control.getCurrentGradeValue();
      String _plus_2 = ("grade:" + _currentGradeValue);
      g2.drawString(_plus_2, 600, 30);
      String _filename = this.control.getCurrentQuestionGrade().getFilename();
      String _name = new File(_filename).getName();
      String _plus_3 = ("file:" + _name);
      g2.drawString(_plus_3, 750, 30);
      Font _font_1 = new Font("Arial", Font.BOLD, 24);
      g2.setFont(_font_1);
      int offsety = 80;
      int index = 0;
      g2.setColor(Color.WHITE);
      int _size = this.control.getCurrentQuestion().getGrades().size();
      int _multiply = (_size * 30);
      int _plus_4 = (_multiply + 30);
      g2.fillRect(20, 70, 80, _plus_4);
      g2.setColor(Color.BLACK);
      int _currentGradeIndex_1 = this.control.getCurrentGradeIndex();
      int _minus = (_currentGradeIndex_1 - 5);
      final int start = Integer.valueOf(Math.max(0, _minus)).intValue();
      int _currentGradeIndex_2 = this.control.getCurrentGradeIndex();
      int _plus_5 = (_currentGradeIndex_2 + 5);
      final int end = Integer.valueOf(Math.min(_plus_5, this.control.getCurrentQuestion().getGrades().size())).intValue();
      boolean _isValidated = this.control.getCurrentQuestionGrade().isValidated();
      if (_isValidated) {
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(start, end, true);
        for (final Integer i : _doubleDotLessThan) {
          {
            int _currentGradeIndex_3 = this.control.getCurrentGradeIndex();
            boolean _equals = ((i).intValue() == _currentGradeIndex_3);
            if (_equals) {
              g2.setColor(Color.RED);
            } else {
              g2.setColor(Color.DARK_GRAY);
            }
            g2.drawString(this.control.getCurrentQuestion().getGrades().get((i).intValue()), 50, offsety);
            int _offsety = offsety;
            offsety = (_offsety + 30);
            index++;
          }
        }
      } else {
        ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(start, end, true);
        for (final Integer i_1 : _doubleDotLessThan_1) {
          {
            int _currentGradeIndex_3 = this.control.getCurrentGradeIndex();
            boolean _equals = ((i_1).intValue() == _currentGradeIndex_3);
            if (_equals) {
              g2.setColor(Color.ORANGE);
            } else {
              g2.setColor(Color.DARK_GRAY);
            }
            g2.drawString(this.control.getCurrentQuestion().getGrades().get((i_1).intValue()), 50, offsety);
            int _offsety = offsety;
            offsety = (_offsety + 30);
            index++;
          }
        }
      }
      g2.setColor(Color.BLACK);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception exception = (Exception)_t;
        JOptionPane.showMessageDialog(null, exception.getStackTrace(), "InfoBox: ", JOptionPane.ERROR_MESSAGE);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Override
  public Dimension getPreferredSize() {
    return this.size;
  }
}
