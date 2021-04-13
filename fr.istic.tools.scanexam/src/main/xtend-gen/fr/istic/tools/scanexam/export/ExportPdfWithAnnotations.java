package fr.istic.tools.scanexam.export;

import fr.istic.tools.scanexam.core.Line;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class ExportPdfWithAnnotations {
  public static void main(final String[] args) {
    try {
      File _file = new File("src/main/resources/resources_annotation/pfo_example.pdf");
      PDDocument document = PDDocument.load(_file);
      ExportPdfWithAnnotations.textAnnotationWithArrowAbsoluteCoords(document, 0, 100, 350, 400, 400, "ffffffff", "10/20");
      document.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static void textAnnotationWithArrowAbsoluteCoords(final PDDocument document, final int nbPage, final float pointerAbsoluteX, final float pointerAbsoluteY, final float textAbsoluteX, final float textAbsoluteY, final String t, final String note) {
    try {
      String text = t.replace("\n", "").replace("\r", "");
      PDPage page = document.getPage(nbPage);
      PDPageContentStream contentStream = new PDPageContentStream(document, page, 
        PDPageContentStream.AppendMode.APPEND, true, true);
      int _length = text.length();
      int _divide = (_length / 30);
      int nbLines = (1 + _divide);
      int partitionSize = 30;
      float charWidth = 3.5f;
      float charHeight = 9;
      float rectangleBottomLeftCornerX = 0;
      float rectangleBottomLeftCornerY = 0;
      float rectangleWidth = 0;
      float rectangleHeight = 0;
      rectangleBottomLeftCornerX = textAbsoluteX;
      int _length_1 = text.length();
      boolean _lessEqualsThan = (_length_1 <= partitionSize);
      if (_lessEqualsThan) {
        int _length_2 = text.length();
        float _multiply = (_length_2 * charWidth);
        rectangleWidth = _multiply;
        rectangleHeight = charHeight;
        rectangleBottomLeftCornerY = textAbsoluteY;
      } else {
        rectangleBottomLeftCornerY = (textAbsoluteY - (charHeight * (nbLines - 1)));
        rectangleWidth = (partitionSize * charWidth);
        rectangleHeight = (charHeight * nbLines);
      }
      contentStream.moveTo(pointerAbsoluteX, pointerAbsoluteY);
      contentStream.lineTo((textAbsoluteX + (rectangleWidth / 2)), textAbsoluteY);
      contentStream.setNonStrokingColor(Color.decode("#0093ff"));
      contentStream.stroke();
      contentStream.fill();
      contentStream.addRect((rectangleBottomLeftCornerX - 2), (rectangleBottomLeftCornerY - 2), (rectangleWidth + 4), 
        (rectangleHeight + 4));
      contentStream.setNonStrokingColor(Color.decode("#000000"));
      contentStream.fill();
      contentStream.addRect(rectangleBottomLeftCornerX, rectangleBottomLeftCornerY, rectangleWidth, rectangleHeight);
      contentStream.setNonStrokingColor(Color.decode("#ffffff"));
      contentStream.fill();
      contentStream.setNonStrokingColor(Color.decode("#000000"));
      contentStream.setFont(PDType0Font.load(document, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 8);
      contentStream.setLeading(7f);
      contentStream.beginText();
      contentStream.newLineAtOffset(textAbsoluteX, textAbsoluteY);
      {
        int i = 0;
        int _length_3 = text.length();
        boolean _lessThan = (i < _length_3);
        boolean _while = _lessThan;
        while (_while) {
          {
            contentStream.showText(text.substring(i, Math.min(text.length(), (i + partitionSize))));
            int _length_4 = text.length();
            boolean _lessThan_1 = (i < _length_4);
            if (_lessThan_1) {
              contentStream.newLine();
            }
          }
          int _i = i;
          i = (_i + partitionSize);
          int _length_4 = text.length();
          boolean _lessThan_1 = (i < _length_4);
          _while = _lessThan_1;
        }
      }
      contentStream.endText();
      contentStream.fill();
      contentStream.beginText();
      float _height = page.getMediaBox().getHeight();
      float _minus = (_height - 10);
      contentStream.newLineAtOffset(0, _minus);
      contentStream.showText(note);
      contentStream.endText();
      contentStream.close();
      File file = new File("src/main/resources/resources_annotation/pfo_example_annotation.pdf");
      document.save(file);
      Desktop.getDesktop().open(file);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static float[] convertHexaToRGBFloat(final String hexaColor) {
    float[] _xblockexpression = null;
    {
      Color color = Color.decode(hexaColor);
      int _red = color.getRed();
      float _divide = (((float) _red) / 255);
      int _green = color.getGreen();
      float _divide_1 = (((float) _green) / 255);
      int _blue = color.getBlue();
      float _divide_2 = (((float) _blue) / 255);
      _xblockexpression = new float[] { _divide, _divide_1, _divide_2 };
    }
    return _xblockexpression;
  }
  
  public static void annotationDrawLinePDF(final PDDocument document, final int nbPage, final ArrayList<Line> listLine) {
    try {
      PDPage page = document.getPage(nbPage);
      PDPageContentStream contentStream = new PDPageContentStream(document, page, 
        PDPageContentStream.AppendMode.APPEND, true, true);
      int _size = listLine.size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          Line lineTMP = listLine.get((i).intValue());
          float originAbsoluteX = lineTMP.getX1();
          float originAbsoluteY = lineTMP.getY1();
          float destinationAbsoluteX = lineTMP.getX2();
          float destinationAbsoluteY = lineTMP.getY2();
          contentStream.moveTo(originAbsoluteX, originAbsoluteY);
          contentStream.lineTo(destinationAbsoluteX, destinationAbsoluteY);
          contentStream.setNonStrokingColor(Color.decode("#0093ff"));
          contentStream.stroke();
          contentStream.fill();
        }
      }
      contentStream.close();
      File file = new File("src/main/resources/resources_annotation/pfo_example_annotation.pdf");
      document.save(file);
      Desktop.getDesktop().open(file);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
