package fr.istic.tools.scanexam.export;

import fr.istic.tools.scanexam.core.Comment;
import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.core.HandwritingComment;
import fr.istic.tools.scanexam.core.Line;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.core.TextComment;
import fr.istic.tools.scanexam.services.api.Service;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.awt.Color;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ExportExamToPdf {
  private static Service service;
  
  public ExportExamToPdf(final Service serv) {
    ExportExamToPdf.service = serv;
  }
  
  /**
   * Exports a PDF file to the selected directory.
   */
  public static File exportToPdf(final PDDocument pdf, final StudentSheet sheet, final File outputPdfFile, final Boolean overwriteFile) {
    try {
      if ((outputPdfFile.exists() && (!(overwriteFile).booleanValue()))) {
        return null;
      }
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      document.save(outputPdfFile);
      document.close();
      return outputPdfFile;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Exports a pdf file to the selected directory even if a file already exists.
   */
  public static Object exportToPdfAndOverwriteFile(final PDDocument pdf, final StudentSheet sheet, final File outputPdfFile) {
    try {
      boolean _exists = outputPdfFile.exists();
      if (_exists) {
        return null;
      }
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      document.save(outputPdfFile);
      document.close();
      return null;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Returns an InputStream of the exported PDF file
   */
  public static InputStream exportToInputStream(final PDDocument pdf, final StudentSheet sheet) {
    try {
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      PDStream ps = new PDStream(document);
      InputStream is = ps.createInputStream();
      document.close();
      return is;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Returns an OutputStream of the exported PDF file
   */
  public static OutputStream exportToOutputStream(final PDDocument pdf, final StudentSheet sheet) {
    try {
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      PDStream ps = new PDStream(document);
      OutputStream is = ps.createOutputStream();
      document.close();
      return is;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Exports a Temp PDF file placed in Temp directory.
   */
  public static File exportToTempFile(final PDDocument pdf, final StudentSheet sheet) {
    try {
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      String _examName = ExportExamToPdf.service.getExamName();
      String _studentName = sheet.getStudentName();
      String _plus = (_examName + _studentName);
      File file = File.createTempFile(_plus, ".pdf");
      document.save(file);
      document.close();
      return file;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Export Collection of Temp PDF File
   */
  public static Collection<File> exportToCollection(final PDDocument pdf, final Collection<StudentSheet> sheets) {
    final Function<StudentSheet, File> _function = (StudentSheet s) -> {
      return ExportExamToPdf.exportToTempFile(pdf, s);
    };
    return sheets.stream().<File>map(_function).collect(Collectors.<File>toList());
  }
  
  /**
   * Export pdf with annotations and Grade
   */
  public static void exportToPdfWithAnnotations(final InputStream d, final StudentSheet sheet, final File fileForSaving) {
    try {
      PDDocument document = PDDocument.load(d);
      EList<Grade> _grades = sheet.getGrades();
      for (final Grade g : _grades) {
        EList<Comment> _comments = g.getComments();
        for (final Comment c : _comments) {
          if ((c instanceof TextComment)) {
            String text = ((TextComment)c).getText().replace("\n", "").replace("\r", "");
            int _length = text.length();
            int _divide = (_length / 30);
            int nbLines = (1 + _divide);
            PDPage page = document.getPage(((TextComment)c).getPageId());
            float pageWidht = page.getMediaBox().getUpperRightX();
            float pageHeight = page.getMediaBox().getUpperRightY();
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            int partitionSize = 30;
            float charWidth = 3.5f;
            float charHeight = 9;
            float rectangleBottomLeftCornerX = 0;
            float rectangleBottomLeftCornerY = 0;
            float rectangleWidth = 0;
            float rectangleHeight = 0;
            float _x = ((TextComment)c).getX();
            float _multiply = (_x * pageWidht);
            rectangleBottomLeftCornerX = _multiply;
            int _length_1 = text.length();
            boolean _lessEqualsThan = (_length_1 <= partitionSize);
            if (_lessEqualsThan) {
              int _length_2 = text.length();
              float _multiply_1 = (_length_2 * charWidth);
              rectangleWidth = _multiply_1;
              rectangleHeight = charHeight;
              float _y = ((TextComment)c).getY();
              float _multiply_2 = (pageHeight * _y);
              float _minus = (pageHeight - _multiply_2);
              rectangleBottomLeftCornerY = _minus;
            } else {
              float _y_1 = ((TextComment)c).getY();
              float _multiply_3 = (pageHeight * _y_1);
              float _minus_1 = (pageHeight - _multiply_3);
              float _minus_2 = (_minus_1 - (charHeight * (nbLines - 1)));
              rectangleBottomLeftCornerY = _minus_2;
              rectangleWidth = (partitionSize * charWidth);
              rectangleHeight = (charHeight * nbLines);
            }
            float _pointerX = ((TextComment)c).getPointerX();
            float _multiply_4 = (pageWidht * _pointerX);
            float _pointerY = ((TextComment)c).getPointerY();
            float _multiply_5 = (pageHeight * _pointerY);
            float _minus_3 = (pageHeight - _multiply_5);
            contentStream.moveTo(_multiply_4, _minus_3);
            float _x_1 = ((TextComment)c).getX();
            float _multiply_6 = (pageWidht * _x_1);
            float _plus = (_multiply_6 + (rectangleWidth / 2));
            float _y_2 = ((TextComment)c).getY();
            float _multiply_7 = (pageHeight * _y_2);
            float _minus_4 = (pageHeight - _multiply_7);
            contentStream.lineTo(_plus, _minus_4);
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
            float _x_2 = ((TextComment)c).getX();
            float _multiply_8 = (pageWidht * _x_2);
            float _y_3 = ((TextComment)c).getY();
            float _multiply_9 = (pageHeight * _y_3);
            float _minus_5 = (pageHeight - _multiply_9);
            contentStream.newLineAtOffset(_multiply_8, _minus_5);
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
            contentStream.close();
          }
        }
      }
      PDPage page_1 = document.getPage((sheet.getPosPage().get(0)).intValue());
      PDPageContentStream contentStream_1 = new PDPageContentStream(document, page_1, PDPageContentStream.AppendMode.APPEND, true, true);
      contentStream_1.setFont(PDType0Font.load(document, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 8);
      contentStream_1.beginText();
      float _height = page_1.getMediaBox().getHeight();
      float _minus_6 = (_height - 10);
      contentStream_1.newLineAtOffset(0, _minus_6);
      float _computeGrade = sheet.computeGrade();
      String _plus_1 = ("" + Float.valueOf(_computeGrade));
      contentStream_1.showText(_plus_1);
      contentStream_1.endText();
      contentStream_1.close();
      document.save(fileForSaving);
      document.close();
      d.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Draw lines annotations of fileForSaving
   */
  public static void annotationDrawLinePDF(final PDDocument document, final HandwritingComment[] listLine, final File fileForSaving) {
    try {
      for (final HandwritingComment handwritingComment : listLine) {
        {
          PDPage page = document.getPage(handwritingComment.getPageId());
          PDPageContentStream contentStream = new PDPageContentStream(document, page, 
            PDPageContentStream.AppendMode.APPEND, true, true);
          EList<Line> _lines = handwritingComment.getLines();
          for (final Line l : _lines) {
            {
              contentStream.moveTo(l.getX1(), l.getY1());
              contentStream.lineTo(l.getX2(), l.getY2());
              contentStream.setNonStrokingColor(Color.decode(l.getColor()));
              contentStream.stroke();
              contentStream.fill();
            }
          }
          contentStream.close();
          document.save(fileForSaving);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
