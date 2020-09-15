package fr.istic.tools.scanexam.utils;

import fr.istic.tools.scanexam.GradingData;
import fr.istic.tools.scanexam.Question;
import fr.istic.tools.scanexam.QuestionGrade;
import fr.istic.tools.scanexam.StudentGrade;
import fr.istic.tools.scanexam.utils.ScanExamXtendUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ScanExamExcelBackend {
  private static HSSFWorkbook workbook;
  
  public static GradingData load(final GradingData data, final File f) {
    try {
      GradingData _xblockexpression = null;
      {
        FileInputStream _fileInputStream = new FileInputStream(f);
        HSSFWorkbook _hSSFWorkbook = new HSSFWorkbook(_fileInputStream);
        ScanExamExcelBackend.workbook = _hSSFWorkbook;
        int _size = data.getGrades().size();
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
        for (final Integer studentId : _doubleDotLessThan) {
          {
            final StudentGrade studentGrade = data.getGrades().get((studentId).intValue());
            studentGrade.setStudentID(ScanExamExcelBackend.getStringAt(1, ((studentId).intValue() + 2), 0));
            EList<QuestionGrade> _questionGrades = studentGrade.getQuestionGrades();
            for (final QuestionGrade questionGrade : _questionGrades) {
              {
                final int column = data.getExam().getQuestions().indexOf(questionGrade.getQuestion());
                questionGrade.setGrade(ScanExamExcelBackend.getStringAt(1, ((studentId).intValue() + 2), (column + 1)));
                final String grade = questionGrade.getGrade();
                if (((grade != null) && (grade.length() != 0))) {
                  questionGrade.setValidated(true);
                }
              }
            }
            int _size_1 = data.getExam().getQuestions().size();
            int _plus = (_size_1 + 4);
            final String anonNumb = ScanExamExcelBackend.getStringAt(1, ((studentId).intValue() + 2), _plus);
            if (((anonNumb != null) && (anonNumb.length() > 0))) {
              try {
                studentGrade.setNumAnonymat(Long.parseLong(anonNumb));
              } catch (final Throwable _t) {
                if (_t instanceof NumberFormatException) {
                  StringConcatenation _builder = new StringConcatenation();
                  _builder.append("Wrong format, integer expected but \"");
                  _builder.append(anonNumb);
                  _builder.append("\" found");
                  JOptionPane.showMessageDialog(null, _builder, 
                    "Format error", 
                    JOptionPane.ERROR_MESSAGE);
                } else {
                  throw Exceptions.sneakyThrow(_t);
                }
              }
            }
          }
        }
        _xblockexpression = data;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static void save(final File file, final GradingData data) {
    try {
      HSSFWorkbook _hSSFWorkbook = new HSSFWorkbook();
      ScanExamExcelBackend.workbook = _hSSFWorkbook;
      final HSSFSheet summary = ScanExamExcelBackend.workbook.createSheet("Summary");
      final HSSFSheet sheet = ScanExamExcelBackend.workbook.createSheet("Grades");
      final HSSFSheet scol = ScanExamExcelBackend.workbook.createSheet("Final");
      ScanExamExcelBackend.setStringAt(0, 0, 0, "#Images");
      int _size = data.getImages().size();
      String _plus = ("" + Integer.valueOf(_size));
      ScanExamExcelBackend.setStringAt(0, 0, 1, _plus);
      ScanExamExcelBackend.setStringAt(0, 1, 0, "#Grades");
      int _size_1 = data.getImages().size();
      int _numberOfPages = data.getExam().getNumberOfPages();
      int _divide = (_size_1 / _numberOfPages);
      String _plus_1 = ("" + Integer.valueOf(_divide));
      ScanExamExcelBackend.setStringAt(0, 1, 1, _plus_1);
      ScanExamExcelBackend.setStringAt(0, 1, 0, "#Questions");
      int _size_2 = data.getExam().getQuestions().size();
      String _plus_2 = ("" + Integer.valueOf(_size_2));
      ScanExamExcelBackend.setStringAt(0, 1, 1, _plus_2);
      int _size_3 = data.getExam().getQuestions().size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size_3, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          ScanExamExcelBackend.setStringAt(0, (i).intValue(), 1, ("#Q" + i));
          final Question q = data.getExam().getQuestions().get((i).intValue());
          ScanExamExcelBackend.setStringAt(0, (i).intValue(), 2, q.getLabel());
          int _defaultGradeIndex = q.getDefaultGradeIndex();
          String _plus_3 = ("" + Integer.valueOf(_defaultGradeIndex));
          ScanExamExcelBackend.setStringAt(0, (i).intValue(), 3, _plus_3);
          int _size_4 = q.getGrades().size();
          String _plus_4 = ("" + Integer.valueOf(_size_4));
          ScanExamExcelBackend.setStringAt(0, (i).intValue(), 4, _plus_4);
          ScanExamExcelBackend.setStringAt(0, (i).intValue(), 5, "#zone");
          int _x = q.getZone().getX();
          String _plus_5 = ("" + Integer.valueOf(_x));
          ScanExamExcelBackend.setStringAt(0, (i).intValue(), 6, _plus_5);
          int _y = q.getZone().getY();
          String _plus_6 = ("" + Integer.valueOf(_y));
          ScanExamExcelBackend.setStringAt(0, (i).intValue(), 7, _plus_6);
          int _w = q.getZone().getW();
          String _plus_7 = ("" + Integer.valueOf(_w));
          ScanExamExcelBackend.setStringAt(0, (i).intValue(), 8, _plus_7);
          int _h = q.getZone().getH();
          String _plus_8 = ("" + Integer.valueOf(_h));
          ScanExamExcelBackend.setStringAt(0, (i).intValue(), 9, _plus_8);
          ScanExamExcelBackend.setStringAt(0, (i).intValue(), 10, "#grades");
          int _size_5 = q.getGrades().size();
          ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_5, true);
          for (final Integer j : _doubleDotLessThan_1) {
            String _get = q.getGrades().get((j).intValue());
            String _plus_9 = ("" + _get);
            ScanExamExcelBackend.setStringAt(0, (11 + (j).intValue()), 0, _plus_9);
          }
        }
      }
      int col = 1;
      EList<Question> _questions = data.getExam().getQuestions();
      for (final Question question : _questions) {
        {
          ScanExamExcelBackend.setStringAt(1, 0, col, question.getLabel());
          int _col = col;
          col = (_col + 1);
        }
      }
      int _size_4 = data.getGrades().size();
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_4, true);
      for (final Integer studentId : _doubleDotLessThan_1) {
        {
          final StudentGrade studentGrade = data.getGrades().get((studentId).intValue());
          ScanExamExcelBackend.setStringAt(1, ((studentId).intValue() + 2), 0, ("Student_" + studentId));
          EList<QuestionGrade> _questionGrades = studentGrade.getQuestionGrades();
          for (final QuestionGrade questionGrade : _questionGrades) {
            {
              final int column = data.getExam().getQuestions().indexOf(questionGrade.getQuestion());
              ScanExamExcelBackend.setStringAt(1, ((studentId).intValue() + 2), (column + 1), questionGrade.getGrade());
            }
          }
          int _size_5 = data.getExam().getQuestions().size();
          int _plus_3 = (_size_5 + 3);
          double _computeGrade = ScanExamXtendUtils.computeGrade(studentGrade);
          String _plus_4 = ("" + Double.valueOf(_computeGrade));
          ScanExamExcelBackend.setStringAt(1, ((studentId).intValue() + 2), _plus_3, _plus_4);
        }
      }
      int _size_5 = data.getGrades().size();
      ExclusiveRange _doubleDotLessThan_2 = new ExclusiveRange(0, _size_5, true);
      for (final Integer studentId_1 : _doubleDotLessThan_2) {
        {
          final StudentGrade studentGrade = data.getGrades().get((studentId_1).intValue());
          int _size_6 = data.getExam().getQuestions().size();
          int _plus_3 = (_size_6 + 3);
          double _computeGrade = ScanExamXtendUtils.computeGrade(studentGrade);
          String _plus_4 = ("" + Double.valueOf(_computeGrade));
          ScanExamExcelBackend.setStringAt(1, ((studentId_1).intValue() + 2), _plus_3, _plus_4);
          int _size_7 = data.getExam().getQuestions().size();
          int _plus_5 = (_size_7 + 4);
          long _numAnonymat = studentGrade.getNumAnonymat();
          String _plus_6 = ("" + Long.valueOf(_numAnonymat));
          ScanExamExcelBackend.setStringAt(1, ((studentId_1).intValue() + 2), _plus_5, _plus_6);
        }
      }
      final Function1<StudentGrade, Long> _function = (StudentGrade it) -> {
        return Long.valueOf(it.getNumAnonymat());
      };
      final List<StudentGrade> r = IterableExtensions.<StudentGrade, Long>sortBy(data.getGrades(), _function);
      int _size_6 = r.size();
      ExclusiveRange _doubleDotLessThan_3 = new ExclusiveRange(0, _size_6, true);
      for (final Integer i_1 : _doubleDotLessThan_3) {
        {
          final StudentGrade studentGrade = r.get((i_1).intValue());
          long _numAnonymat = studentGrade.getNumAnonymat();
          String _plus_3 = ("" + Long.valueOf(_numAnonymat));
          ScanExamExcelBackend.setStringAt(2, ((i_1).intValue() + 1), 1, _plus_3);
          double _computeGrade = ScanExamXtendUtils.computeGrade(studentGrade);
          String _plus_4 = ("" + Double.valueOf(_computeGrade));
          ScanExamExcelBackend.setStringAt(2, ((i_1).intValue() + 1), 2, _plus_4);
        }
      }
      FileOutputStream _fileOutputStream = new FileOutputStream(file);
      ScanExamExcelBackend.workbook.write(_fileOutputStream);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static void setStringAt(final int sheetId, final int rowId, final int colId, final String content) {
    HSSFSheet sheet = ScanExamExcelBackend.workbook.getSheetAt(sheetId);
    HSSFRow crow = sheet.getRow(rowId);
    if ((crow == null)) {
      crow = sheet.createRow(rowId);
    }
    HSSFCell cell = crow.getCell(colId);
    if ((cell == null)) {
      cell = crow.createCell(colId);
    }
    cell.setCellValue(content);
  }
  
  public static String getStringAt(final int sheetId, final int rowId, final int colId) {
    Object _xblockexpression = null;
    {
      HSSFSheet sheet = ScanExamExcelBackend.workbook.getSheetAt(sheetId);
      Object _xtrycatchfinallyexpression = null;
      try {
        Object _xblockexpression_1 = null;
        {
          final HSSFRow crow = sheet.getRow(rowId);
          if ((crow != null)) {
            final HSSFCell cell = crow.getCell(colId);
            if ((cell != null)) {
              int _cellType = cell.getCellType();
              switch (_cellType) {
                case Cell.CELL_TYPE_NUMERIC:
                  return Integer.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()).toString();
                case Cell.CELL_TYPE_STRING:
                  return cell.getStringCellValue();
                case Cell.CELL_TYPE_BLANK:
                  return "";
              }
            }
          }
          _xblockexpression_1 = null;
        }
        _xtrycatchfinallyexpression = _xblockexpression_1;
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception exception = (Exception)_t;
          String _message = exception.getMessage();
          String _plus = ((((((("Cannot read string at sheet " + Integer.valueOf(sheetId)) + " cell (") + Integer.valueOf(rowId)) + ",") + Integer.valueOf(colId)) + "), error ") + _message);
          throw new RuntimeException(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _xblockexpression = _xtrycatchfinallyexpression;
    }
    return ((String)_xblockexpression);
  }
}
