package fr.istic.tools.scanexam.utils;

import fr.istic.tools.scanexam.GradingData;
import fr.istic.tools.scanexam.Question;
import fr.istic.tools.scanexam.QuestionGrade;
import fr.istic.tools.scanexam.StudentGrade;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.DoubleExtensions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

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
                int _length = questionGrade.getGrade().length();
                boolean _notEquals = (_length != 0);
                if (_notEquals) {
                  questionGrade.setValidated(true);
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
      Pair<String, Integer> _mappedTo = Pair.<String, Integer>of("A", Integer.valueOf(5));
      Pair<String, Integer> _mappedTo_1 = Pair.<String, Integer>of("B", Integer.valueOf(4));
      Pair<String, Integer> _mappedTo_2 = Pair.<String, Integer>of("C", Integer.valueOf(3));
      Pair<String, Integer> _mappedTo_3 = Pair.<String, Integer>of("D", Integer.valueOf(2));
      Pair<String, Integer> _mappedTo_4 = Pair.<String, Integer>of("E", Integer.valueOf(1));
      Pair<String, Integer> _mappedTo_5 = Pair.<String, Integer>of("F", Integer.valueOf(0));
      final HashMap<String, Integer> gradeMap = CollectionLiterals.<String, Integer>newHashMap(
        new Pair[] { _mappedTo, _mappedTo_1, _mappedTo_2, _mappedTo_3, _mappedTo_4, _mappedTo_5 });
      final Function1<Question, Double> _function = (Question it) -> {
        return Double.valueOf(it.getWeight());
      };
      final Function2<Double, Double, Double> _function_1 = (Double p1, Double p2) -> {
        return Double.valueOf(DoubleExtensions.operator_plus(p1, p2));
      };
      final Double scale = IterableExtensions.<Double>reduce(ListExtensions.<Question, Double>map(data.getExam().getQuestions(), _function), _function_1);
      int _size_4 = data.getGrades().size();
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_4, true);
      for (final Integer studentId : _doubleDotLessThan_1) {
        {
          final StudentGrade studentGrade = data.getGrades().get((studentId).intValue());
          ScanExamExcelBackend.setStringAt(1, ((studentId).intValue() + 2), 0, ("Student_" + studentId));
          double grade = 0.0;
          EList<QuestionGrade> _questionGrades = studentGrade.getQuestionGrades();
          for (final QuestionGrade questionGrade : _questionGrades) {
            {
              final int column = data.getExam().getQuestions().indexOf(questionGrade.getQuestion());
              ScanExamExcelBackend.setStringAt(1, ((studentId).intValue() + 2), (column + 1), questionGrade.getGrade());
              final Integer qgrade = gradeMap.get(questionGrade.getGrade());
              if ((qgrade != null)) {
                double _grade = grade;
                Integer _get = gradeMap.get(questionGrade.getGrade());
                double _weight = questionGrade.getQuestion().getWeight();
                double _multiply = ((_get).intValue() * _weight);
                grade = (_grade + _multiply);
              }
            }
          }
          final double scaledGrade = (grade / (scale).doubleValue());
          int _size_5 = data.getExam().getQuestions().size();
          int _plus_3 = (_size_5 + 3);
          ScanExamExcelBackend.setStringAt(1, ((studentId).intValue() + 2), _plus_3, ("" + Double.valueOf(scaledGrade)));
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
      final HSSFRow crow = sheet.getRow(rowId);
      if ((crow != null)) {
        final HSSFCell cell = crow.getCell(colId);
        if ((cell != null)) {
          return cell.getRichStringCellValue().getString();
        }
      }
      _xblockexpression = null;
    }
    return ((String)_xblockexpression);
  }
}
