package fr.istic.tools.scanexam.utils;

import com.google.common.collect.Iterables;
import fr.istic.tools.scanexam.Exam;
import fr.istic.tools.scanexam.GradingData;
import fr.istic.tools.scanexam.Question;
import fr.istic.tools.scanexam.QuestionGrade;
import fr.istic.tools.scanexam.ScanZone;
import fr.istic.tools.scanexam.ScanexamFactory;
import fr.istic.tools.scanexam.StudentGrade;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Comparator;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class ScanExamXtendFactory {
  public static Exam exam(final String title) {
    Exam _xblockexpression = null;
    {
      final Exam e = ScanexamFactory.eINSTANCE.createExam();
      e.setLabel(title);
      _xblockexpression = e;
    }
    return _xblockexpression;
  }
  
  public static Exam exam(final String title, final String path, final int nPages, final int scale, final Iterable<Question> questions) {
    Exam _xblockexpression = null;
    {
      final Exam e = ScanExamXtendFactory.exam(title);
      e.setNumberOfPages(nPages);
      e.setScale(scale);
      e.setFolderPath(path);
      EList<Question> _questions = e.getQuestions();
      Iterables.<Question>addAll(_questions, questions);
      _xblockexpression = e;
    }
    return _xblockexpression;
  }
  
  public static GradingData gradingData(final Exam exam) {
    GradingData _xblockexpression = null;
    {
      final GradingData e = ScanexamFactory.eINSTANCE.createGradingData();
      String _label = exam.getLabel();
      String _plus = (_label + ".xls");
      e.setExcelFileName(_plus);
      e.setExam(exam);
      String _folderPath = exam.getFolderPath();
      final File dir = new File(_folderPath);
      final File[] pngfiles = dir.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
          return name.toLowerCase().endsWith(".png");
        }
      });
      if (((pngfiles == null) || ((List<File>)Conversions.doWrapArray(pngfiles)).isEmpty())) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("No png files found in folder ");
        String _folderPath_1 = exam.getFolderPath();
        _builder.append(_folderPath_1);
        _builder.append(" ");
        throw new UnsupportedOperationException(_builder.toString());
      }
      EList<File> _images = e.getImages();
      final Comparator<File> _function = (File a, File b) -> {
        return a.getName().compareTo(b.getName());
      };
      List<File> _sortInplace = ListExtensions.<File>sortInplace(((List<File>)Conversions.doWrapArray(pngfiles)), _function);
      Iterables.<File>addAll(_images, _sortInplace);
      int _size = e.getImages().size();
      int _numberOfPages = exam.getNumberOfPages();
      int _modulo = (_size % _numberOfPages);
      boolean _notEquals = (_modulo != 0);
      if (_notEquals) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("Inconsistent number of pages ");
        int _size_1 = e.getImages().size();
        _builder_1.append(_size_1);
        _builder_1.append(" images found, should b multiple by ");
        int _numberOfPages_1 = exam.getNumberOfPages();
        _builder_1.append(_numberOfPages_1);
        _builder_1.append("  ");
        throw new UnsupportedOperationException(_builder_1.toString());
      }
      int _size_2 = e.getImages().size();
      String _plus_1 = ("Nb images " + Integer.valueOf(_size_2));
      InputOutput.<String>println(_plus_1);
      int _size_3 = e.getImages().size();
      int _numberOfPages_2 = exam.getNumberOfPages();
      final int nbStudents = (_size_3 / _numberOfPages_2);
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, nbStudents, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          final StudentGrade grade = ScanExamXtendFactory.studentGrade();
          grade.setStudentID(("student_" + i));
          EList<StudentGrade> _grades = e.getGrades();
          _grades.add(grade);
          EList<Question> _questions = exam.getQuestions();
          for (final Question q : _questions) {
            {
              final QuestionGrade questionGrade = ScanExamXtendFactory.questionGrade(q);
              EList<QuestionGrade> _questionGrades = grade.getQuestionGrades();
              _questionGrades.add(questionGrade);
              EList<File> _images_1 = e.getImages();
              int _numberOfPages_3 = exam.getNumberOfPages();
              int _multiply = ((i).intValue() * _numberOfPages_3);
              int _page = q.getZone().getPage();
              int _plus_2 = (_multiply + _page);
              int _minus = (_plus_2 - 1);
              questionGrade.setFilename(_images_1.get(_minus).getPath());
            }
          }
        }
      }
      _xblockexpression = e;
    }
    return _xblockexpression;
  }
  
  public static StudentGrade studentGrade() {
    return ScanexamFactory.eINSTANCE.createStudentGrade();
  }
  
  public static QuestionGrade questionGrade(final Question q) {
    QuestionGrade _xblockexpression = null;
    {
      final QuestionGrade e = ScanexamFactory.eINSTANCE.createQuestionGrade();
      e.setQuestion(q);
      e.setGrade("");
      e.setValidated(false);
      _xblockexpression = e;
    }
    return _xblockexpression;
  }
  
  public static Question question(final String label) {
    Question _xblockexpression = null;
    {
      final Question r = ScanexamFactory.eINSTANCE.createQuestion();
      r.setLabel(label);
      _xblockexpression = r;
    }
    return _xblockexpression;
  }
  
  public static Question question(final String label, final List<Integer> z, final List<Integer> markZ, final List<String> grades, final int defaultGrade) {
    Question _xblockexpression = null;
    {
      final Question r = ScanExamXtendFactory.question(label);
      Integer _get = z.get(0);
      Integer _get_1 = z.get(1);
      Integer _get_2 = z.get(2);
      Integer _get_3 = z.get(0);
      int _minus = ((_get_2).intValue() - (_get_3).intValue());
      Integer _get_4 = z.get(3);
      Integer _get_5 = z.get(1);
      int _minus_1 = ((_get_4).intValue() - (_get_5).intValue());
      r.setZone(ScanExamXtendFactory.zone((_get).intValue(), (_get_1).intValue(), _minus, _minus_1, (z.get(4)).intValue()));
      Integer _get_6 = markZ.get(0);
      Integer _get_7 = markZ.get(1);
      Integer _get_8 = markZ.get(2);
      Integer _get_9 = markZ.get(0);
      int _minus_2 = ((_get_8).intValue() - (_get_9).intValue());
      Integer _get_10 = markZ.get(3);
      Integer _get_11 = markZ.get(1);
      int _minus_3 = ((_get_10).intValue() - (_get_11).intValue());
      r.setMarkZone(ScanExamXtendFactory.zone((_get_6).intValue(), (_get_7).intValue(), _minus_2, _minus_3, (markZ.get(4)).intValue()));
      EList<String> _grades = r.getGrades();
      Iterables.<String>addAll(_grades, grades);
      r.setDefaultGradeIndex(defaultGrade);
      _xblockexpression = r;
    }
    return _xblockexpression;
  }
  
  public static ScanZone zone(final int x, final int y, final int w, final int h, final int page) {
    ScanZone _xblockexpression = null;
    {
      final ScanZone r = ScanexamFactory.eINSTANCE.createScanZone();
      r.setX(x);
      r.setY(y);
      r.setW(w);
      r.setH(h);
      r.setPage(page);
      _xblockexpression = r;
    }
    return _xblockexpression;
  }
}
