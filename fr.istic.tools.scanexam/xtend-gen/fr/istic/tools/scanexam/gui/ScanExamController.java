package fr.istic.tools.scanexam.gui;

import fr.istic.tools.scanexam.GradingData;
import fr.istic.tools.scanexam.Question;
import fr.istic.tools.scanexam.QuestionGrade;
import fr.istic.tools.scanexam.StudentGrade;
import fr.istic.tools.scanexam.gui.ExcelTableViewer;
import fr.istic.tools.scanexam.gui.ScanExamPanel;
import fr.istic.tools.scanexam.utils.ScanExamExcelBackend;
import fr.istic.tools.scanexam.utils.ScanExamXtendUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class ScanExamController {
  private int currentStudentIndex = 0;
  
  private int currentQuestionIndex = 0;
  
  private int currentGradeValueIndex = 0;
  
  private GradingData data;
  
  private BufferedImage image;
  
  private ScanExamPanel panel;
  
  private ExcelTableViewer tableView;
  
  private Question prevQuestion;
  
  public ScanExamController(final GradingData data) {
    this.data = data;
  }
  
  public ScanExamPanel setPanel(final ScanExamPanel panel) {
    return this.panel = panel;
  }
  
  public ExcelTableViewer setTableView(final ExcelTableViewer panel) {
    return this.tableView = panel;
  }
  
  public BufferedImage extractQuestionZone() {
    BufferedImage _xblockexpression = null;
    {
      final Question q = this.getCurrentQuestion();
      _xblockexpression = this.image.getSubimage(q.getZone().getX(), q.getZone().getY(), q.getZone().getW(), q.getZone().getH());
    }
    return _xblockexpression;
  }
  
  public Question getCurrentQuestion() {
    return this.data.getExam().getQuestions().get(this.currentQuestionIndex);
  }
  
  public int getCurrentPageIndex() {
    int _numberOfPages = this.data.getExam().getNumberOfPages();
    int _multiply = (_numberOfPages * this.currentStudentIndex);
    int _page = this.getCurrentQuestion().getZone().getPage();
    int _plus = (_multiply + _page);
    return (_plus - 1);
  }
  
  public String loadCurrentPage() {
    try {
      String _xblockexpression = null;
      {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Loading ");
        String _name = this.data.getImages().get(this.getCurrentPageIndex()).getName();
        _builder.append(_name);
        InputOutput.<String>println(_builder.toString());
        this.image = ImageIO.read(this.data.getImages().get(this.getCurrentPageIndex()));
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("\t");
        _builder_1.append("->  image ");
        int _width = this.image.getWidth();
        _builder_1.append(_width, "\t");
        _builder_1.append("x");
        int _height = this.image.getHeight();
        _builder_1.append(_height, "\t");
        _xblockexpression = InputOutput.<String>println(_builder_1.toString());
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public int updateQuestionGradeIndex() {
    int _xblockexpression = (int) 0;
    {
      final EList<String> grades = this.getCurrentQuestion().getGrades();
      final String grade = this.getCurrentQuestionGrade().getGrade();
      this.currentGradeValueIndex = grades.indexOf(grade);
      int _xifexpression = (int) 0;
      if ((this.currentGradeValueIndex == (-1))) {
        int _xifexpression_1 = (int) 0;
        boolean _isValidated = this.getCurrentQuestionGrade().isValidated();
        if (_isValidated) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("Invalid grade ");
          _builder.append(grade);
          _builder.append(" not found in ");
          _builder.append(grades);
          throw new UnsupportedOperationException(_builder.toString());
        } else {
          _xifexpression_1 = this.currentGradeValueIndex = this.getCurrentQuestion().getDefaultGradeIndex();
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public void updateQuestionInfo() {
    this.updateQuestionGradeIndex();
    final Question newq = this.getCurrentQuestion();
    int _page = newq.getZone().getPage();
    int _page_1 = this.prevQuestion.getZone().getPage();
    boolean _notEquals = (_page != _page_1);
    if (_notEquals) {
      this.loadCurrentPage();
    }
    this.panel.updateQuestionZone();
  }
  
  public void getNextQuestion() {
    this.prevQuestion = this.getCurrentQuestion();
    int _size = this.data.getExam().getQuestions().size();
    int _modulo = ((this.currentQuestionIndex + 1) % _size);
    this.currentQuestionIndex = _modulo;
    this.updateQuestionInfo();
  }
  
  public void getPrevQuestion() {
    this.prevQuestion = this.getCurrentQuestion();
    int _size = this.data.getExam().getQuestions().size();
    int _minus = (_size - 1);
    int _plus = (this.currentQuestionIndex + _minus);
    int _size_1 = this.data.getExam().getQuestions().size();
    int _modulo = (_plus % _size_1);
    this.currentQuestionIndex = _modulo;
    this.updateQuestionInfo();
  }
  
  public void increaseGrade() {
    int _size = this.getCurrentQuestion().getGrades().size();
    int _modulo = ((this.currentGradeValueIndex + 1) % _size);
    this.currentGradeValueIndex = _modulo;
    QuestionGrade _currentQuestionGrade = this.getCurrentQuestionGrade();
    _currentQuestionGrade.setValidated(false);
    this.panel.repaint();
  }
  
  public void setGrade(final int value) {
    int _size = this.getCurrentQuestion().getGrades().size();
    boolean _lessThan = (value < _size);
    if (_lessThan) {
      this.currentGradeValueIndex = value;
      QuestionGrade _currentQuestionGrade = this.getCurrentQuestionGrade();
      _currentQuestionGrade.setValidated(false);
    }
    this.panel.repaint();
  }
  
  public void decreaseGrade() {
    int _size = this.getCurrentQuestion().getGrades().size();
    int _plus = (this.currentGradeValueIndex + _size);
    int _minus = (_plus - 1);
    int _size_1 = this.getCurrentQuestion().getGrades().size();
    int _modulo = (_minus % _size_1);
    this.currentGradeValueIndex = _modulo;
    QuestionGrade _currentQuestionGrade = this.getCurrentQuestionGrade();
    _currentQuestionGrade.setValidated(false);
    this.panel.repaint();
  }
  
  public void validateGrade() {
    QuestionGrade _currentQuestionGrade = this.getCurrentQuestionGrade();
    _currentQuestionGrade.setGrade(this.getCurrentQuestion().getGrades().get(this.currentGradeValueIndex));
    QuestionGrade _currentQuestionGrade_1 = this.getCurrentQuestionGrade();
    _currentQuestionGrade_1.setValidated(true);
    this.panel.repaint();
    this.tableView.updateTable(this.getCurrentStudent());
  }
  
  public void nextExam() {
    int _currentStudentIndex = this.currentStudentIndex;
    this.currentStudentIndex = (_currentStudentIndex + 1);
    int _currentPageIndex = this.getCurrentPageIndex();
    int _size = this.data.getImages().size();
    boolean _greaterEqualsThan = (_currentPageIndex >= _size);
    if (_greaterEqualsThan) {
      this.currentStudentIndex = 0;
    }
    this.updateQuestionGradeIndex();
    this.loadCurrentPage();
    this.panel.updateQuestionZone();
    this.tableView.updateTable(this.getCurrentStudent());
  }
  
  public void prevExam() {
    int _currentStudentIndex = this.currentStudentIndex;
    this.currentStudentIndex = (_currentStudentIndex - 1);
    int _currentPageIndex = this.getCurrentPageIndex();
    boolean _lessThan = (_currentPageIndex < 0);
    if (_lessThan) {
      int _size = this.data.getImages().size();
      int _numberOfPages = this.data.getExam().getNumberOfPages();
      int _divide = (_size / _numberOfPages);
      int _minus = (_divide - 1);
      this.currentStudentIndex = _minus;
    }
    this.updateQuestionGradeIndex();
    this.loadCurrentPage();
    this.panel.updateQuestionZone();
    this.tableView.updateTable(this.getCurrentStudent());
  }
  
  public void saveExcel() {
    String _property = System.getProperty("user.dir");
    final JFileChooser chooser = new JFileChooser(_property);
    final FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel file", "xls");
    chooser.setFileFilter(filter);
    final int returnVal = chooser.showSaveDialog(null);
    if ((returnVal == JFileChooser.APPROVE_OPTION)) {
      String _name = chooser.getSelectedFile().getName();
      String _plus = ("You chose to open this file: " + _name);
      System.out.println(_plus);
      ScanExamExcelBackend.save(chooser.getSelectedFile(), this.data);
    }
  }
  
  public void saveExcel(final File file) {
    ScanExamExcelBackend.save(file, this.data);
  }
  
  public GradingData loadXMI() {
    GradingData _xblockexpression = null;
    {
      String _property = System.getProperty("user.dir");
      final JFileChooser chooser = new JFileChooser(_property);
      final FileNameExtensionFilter filter = new FileNameExtensionFilter("XMI grading file", "xmi");
      chooser.setFileFilter(filter);
      final int returnVal = chooser.showOpenDialog(null);
      GradingData _xifexpression = null;
      if ((returnVal == JFileChooser.APPROVE_OPTION)) {
        GradingData _xblockexpression_1 = null;
        {
          String _name = chooser.getSelectedFile().getName();
          String _plus = ("You chose to open this file: " + _name);
          System.out.println(_plus);
          _xblockexpression_1 = this.data = ScanExamXtendUtils.load(chooser.getSelectedFile());
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public GradingData loadExcel() {
    GradingData _xblockexpression = null;
    {
      String _property = System.getProperty("user.dir");
      final JFileChooser chooser = new JFileChooser(_property);
      final FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel file", "xls");
      chooser.setFileFilter(filter);
      final int returnVal = chooser.showOpenDialog(null);
      GradingData _xifexpression = null;
      if ((returnVal == JFileChooser.APPROVE_OPTION)) {
        GradingData _xblockexpression_1 = null;
        {
          String _name = chooser.getSelectedFile().getName();
          String _plus = ("You chose to open this file: " + _name);
          System.out.println(_plus);
          _xblockexpression_1 = this.data = ScanExamExcelBackend.load(this.data, chooser.getSelectedFile());
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public void saveXMI() {
    try {
      String _property = System.getProperty("user.dir");
      final JFileChooser chooser = new JFileChooser(_property);
      final FileNameExtensionFilter filter = new FileNameExtensionFilter("XMI grading file", "xmi");
      chooser.setFileFilter(filter);
      final int returnVal = chooser.showSaveDialog(null);
      if ((returnVal == JFileChooser.APPROVE_OPTION)) {
        String _name = chooser.getSelectedFile().getName();
        String _plus = ("You chose to open this file: " + _name);
        System.out.println(_plus);
        ScanExamXtendUtils.save(chooser.getSelectedFile(), this.data);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public StudentGrade getCurrentStudent() {
    return this.data.getGrades().get(this.currentStudentIndex);
  }
  
  public QuestionGrade getCurrentQuestionGrade() {
    return this.getCurrentStudent().getQuestionGrades().get(this.currentQuestionIndex);
  }
  
  public String getCurrentGradeValue() {
    return this.getCurrentQuestion().getGrades().get(this.currentGradeValueIndex);
  }
  
  public int getCurrentGradeIndex() {
    return this.currentGradeValueIndex;
  }
}
