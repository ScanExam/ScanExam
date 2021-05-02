package fr.istic.tools.scanexam.export;

import fr.istic.tools.scanexam.core.Comment;
import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.core.TextComment;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.awt.Color;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class ExportExamToPdf {
  /**
   * Exports a student's PDF file from the PDF document containing all the exam papers WITHOUT ANNOTATIONS OR GRADE
   * @param pdf is the complete pdf document of all students
   * @param sheet is the sheet of the student to export.
   * @param overwriteFile allow file overwriting
   * @return null is return if overwrite is needed but @param overwriteFile is false, else return File.
   */
  public static File exportStudentExamFromCompletePdfToPDF(final PDDocument pdf, final StudentSheet sheet, final File outputPdfFile) {
    try {
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
   * Exports a student's InputStream from the PDF document containing all the exam papers WITHOUT ANNOTATIONS OR GRADE
   * @param pdf is the complete pdf document of all students
   * @param sheet is the sheet of the student to export.
   * @return InputStream of student exam
   */
  public static InputStream exportStudentExamFromCompletePdfToInputStream(final PDDocument pdf, final StudentSheet sheet) {
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
   * Exports a student's InputStream from the PDF document containing all the exam papers WITHOUT ANNOTATIONS OR GRADE
   * @param pdf is the complete pdf document of all students
   * @param sheet is the sheet of the student to export.
   * @return InputStream of student exam
   */
  public static InputStream exportStudentExamFromCompletePdfToInputStream(final InputStream pdf, final StudentSheet sheet) {
    try {
      PDDocument document = PDDocument.load(pdf);
      PDDocument studentDocument = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        studentDocument.addPage(document.getPage((i).intValue()));
      }
      PDStream ps = new PDStream(studentDocument);
      InputStream is = ps.createInputStream();
      document.close();
      return is;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Exports a student's OutputStream from the PDF document containing all the exam papers WITHOUT ANNOTATIONS OR GRADE
   * @param pdf is the complete pdf document of all students
   * @param sheet is the sheet of the student to export.
   * @return OutputStream of student exam
   */
  public static OutputStream exportToOutputStreamToOutputStream(final PDDocument pdf, final StudentSheet sheet) {
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
   * Exports a student's temp PDF file from the PDF document containing all the exam papers WITHOUT ANNOTATIONS OR GRADE
   * The name of the file is examName + studentName
   * @param pdf is the complete pdf document of all students
   * @param sheet is the sheet of the student to export.
   * @return temp File of student exam.
   */
  public static File exportToTempFile(final InputStream pdfStream, final StudentSheet sheet) {
    try {
      final PDDocument pdf = PDDocument.load(pdfStream);
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      File file = File.createTempFile(sheet.getStudentName(), ".pdf");
      document.save(file);
      document.close();
      return file;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * EXPORT a PDF file for each student containing all annotations TO selected folder
   * @param documentInputStream is the PDF of all student exams
   * @param sheets is they studentSheet of they students
   * @param folderForSaving is the Folder for save PDF documents
   * @return collection of temp Files
   */
  public static void exportExamsOfStudentsToPdfsWithAnnotations(final InputStream documentInputStream, final Collection<StudentSheet> sheets, final File folderForSaving) {
    try {
      File tempExam = File.createTempFile("examTemp", ".pdf");
      ExportExamToPdf.exportExamsToAnnotedPdf(documentInputStream, sheets, tempExam);
      PDDocument pdf = PDDocument.load(tempExam);
      for (final StudentSheet sheet : sheets) {
        {
          PDDocument document = new PDDocument();
          EList<Integer> _posPage = sheet.getPosPage();
          for (final Integer i : _posPage) {
            document.addPage(pdf.getPage((i).intValue()));
          }
          String _absolutePath = folderForSaving.getAbsolutePath();
          String _plus = (_absolutePath + File.separator);
          String _studentName = sheet.getStudentName();
          String _plus_1 = (_plus + _studentName);
          String _plus_2 = (_plus_1 + ".pdf");
          File _file = new File(_plus_2);
          document.save(_file);
          document.close();
        }
      }
      pdf.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * EXPORT a Collection of PDF TEMP files for each student containing all annotations TO selected folder
   * @param documentInputStream is the PDF of all student exams
   * @param sheets is they studentSheet of they students
   * @return map of student's name to temp file
   */
  public static Map<String, File> exportExamsOfStudentsToTempPdfsWithAnnotations(final InputStream documentInputStream, final Collection<StudentSheet> sheets) {
    try {
      Map<String, File> tempExams = new HashMap<String, File>();
      File tempExam = File.createTempFile("examTemp", ".pdf");
      ExportExamToPdf.exportExamsToAnnotedPdf(documentInputStream, sheets, tempExam);
      PDDocument pdf = PDDocument.load(tempExam);
      for (final StudentSheet sheet : sheets) {
        {
          PDDocument document = new PDDocument();
          EList<Integer> _posPage = sheet.getPosPage();
          for (final Integer i : _posPage) {
            document.addPage(pdf.getPage((i).intValue()));
          }
          String _studentName = sheet.getStudentName();
          String _plus = ("tempExam" + _studentName);
          File studentExam = File.createTempFile(_plus, ".pdf");
          document.save(studentExam);
          tempExams.put(sheet.getStudentName(), studentExam);
          document.close();
        }
      }
      pdf.close();
      return tempExams;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * EXPORT a PDF TEMP containing all annotations TO temp file
   * @param studentExamDocument is the PDF file of all students
   * @param sheet is the studentSheet of the student
   * @return temp File of annoted PDF.
   */
  public static File exportExamsToTempAnnotedPdf(final InputStream documentInputStream, final Collection<StudentSheet> sheets) {
    try {
      File tempExam = File.createTempFile("examTemp", ".pdf");
      ExportExamToPdf.exportExamsToAnnotedPdf(documentInputStream, sheets, tempExam);
      return tempExam;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Export a TEMP PDF file containing all annotations for a student
   * @param studentExamDocument is the PDF file of the student
   * @param sheet is the studentSheet of the student
   * @return temp File of annoted PDF.
   */
  public static void exportStudentExamToPdfWithAnnotations(final InputStream examDocument, final StudentSheet sheet, final File fileForSaving) {
    try {
      List<StudentSheet> _asList = Arrays.<StudentSheet>asList(sheet);
      ArrayList<StudentSheet> _arrayList = new ArrayList<StudentSheet>(_asList);
      File exam = ExportExamToPdf.exportExamsToTempAnnotedPdf(examDocument, _arrayList);
      PDDocument pdf = PDDocument.load(exam);
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      document.save(fileForSaving);
      document.close();
      pdf.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Export PDF file containing all annotations for a student
   * @param studentExamDocument is the PDF file of the student
   * @param sheet is the studentSheet of the student
   * @return temp File of annoted PDF.
   */
  public static Pair<String, File> exportStudentExamToTempPdfWithAnnotations(final InputStream examDocument, final StudentSheet sheet) {
    try {
      File studentExam = File.createTempFile(sheet.getStudentName(), ".pdf");
      List<StudentSheet> _asList = Arrays.<StudentSheet>asList(sheet);
      ArrayList<StudentSheet> _arrayList = new ArrayList<StudentSheet>(_asList);
      File exam = ExportExamToPdf.exportExamsToTempAnnotedPdf(examDocument, _arrayList);
      PDDocument pdf = PDDocument.load(exam);
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      document.save(studentExam);
      document.close();
      pdf.close();
      return Pair.<String, File>of(sheet.getStudentName(), studentExam);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Add annotations to master PDF that contains all students
   * @param documentInputStream is the PDF file of the student
   * @param sheets is they studentSheet of they students
   * @param fileForSaving is the File for save PDF document
   */
  public static void exportExamsToAnnotedPdf(final InputStream documentInputStream, final Collection<StudentSheet> sheets, final File fileForSaving) {
    try {
      PDDocument document = PDDocument.load(documentInputStream);
      for (final StudentSheet sheet : sheets) {
        {
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
          contentStream_1.setFont(PDType0Font.load(document, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 12);
          contentStream_1.setNonStrokingColor(Color.decode("#FF0000"));
          contentStream_1.beginText();
          float _height = page_1.getMediaBox().getHeight();
          float _minus_6 = (_height - 10);
          contentStream_1.newLineAtOffset(0, _minus_6);
          float _computeGrade = sheet.computeGrade();
          String _plus_1 = ("Note : " + Float.valueOf(_computeGrade));
          contentStream_1.showText(_plus_1);
          contentStream_1.endText();
          contentStream_1.close();
        }
      }
      document.save(fileForSaving);
      document.close();
      documentInputStream.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
