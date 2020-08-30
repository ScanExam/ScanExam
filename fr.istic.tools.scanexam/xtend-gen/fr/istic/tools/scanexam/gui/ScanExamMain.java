package fr.istic.tools.scanexam.gui;

import fr.istic.tools.scanexam.Exam;
import fr.istic.tools.scanexam.GradingData;
import fr.istic.tools.scanexam.gui.BackupTask;
import fr.istic.tools.scanexam.gui.ExcelTableViewer;
import fr.istic.tools.scanexam.gui.ScanExamController;
import fr.istic.tools.scanexam.gui.ScanExamPanel;
import fr.istic.tools.scanexam.instances.ExamIO;
import fr.istic.tools.scanexam.utils.ScanExamExcelBackend;
import fr.istic.tools.scanexam.utils.ScanExamXtendFactory;
import fr.istic.tools.scanexam.utils.ScanExamXtendUtils;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ScanExamMain {
  public static void mapKeyAction(final String name, final JPanel buttonPanel, final int e, final int i, final Function<ActionEvent, Object> action) {
    final KeyStroke prevStudentKS = KeyStroke.getKeyStroke(e, i, true);
    final InputMap inputMap = buttonPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    final ActionMap actionMap = buttonPanel.getActionMap();
    inputMap.put(prevStudentKS, name);
    actionMap.put(name, new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        action.apply(e);
      }
    });
  }
  
  protected static JComponent makeTextPanel(final String text) {
    JPanel panel = new JPanel(false);
    JLabel filler = new JLabel(text);
    filler.setHorizontalAlignment(JLabel.CENTER);
    GridLayout _gridLayout = new GridLayout(1, 1);
    panel.setLayout(_gridLayout);
    panel.add(filler);
    return panel;
  }
  
  public static void main(final String[] args) throws IOException {
    final JFrame frame = new JFrame("Scan Exam");
    frame.setSize(1600, 800);
    BoxLayout _boxLayout = new BoxLayout(frame, BoxLayout.Y_AXIS);
    frame.setLayout(_boxLayout);
    final JPanel contentPane = new JPanel();
    BoxLayout _boxLayout_1 = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
    contentPane.setLayout(_boxLayout_1);
    final JPanel topPane = new JPanel();
    BoxLayout _boxLayout_2 = new BoxLayout(topPane, BoxLayout.X_AXIS);
    topPane.setLayout(_boxLayout_2);
    final JPanel leftPane = new JPanel();
    BoxLayout _boxLayout_3 = new BoxLayout(leftPane, BoxLayout.Y_AXIS);
    leftPane.setLayout(_boxLayout_3);
    final JPanel rightPane = new JPanel();
    BoxLayout _boxLayout_4 = new BoxLayout(rightPane, BoxLayout.Y_AXIS);
    rightPane.setLayout(_boxLayout_4);
    topPane.add(leftPane);
    topPane.add(rightPane);
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Correction mode", null, contentPane, "Does nothing");
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_F1);
    frame.setContentPane(tabbedPane);
    Exam _xtrycatchfinallyexpression = null;
    try {
      Exam _xblockexpression = null;
      {
        boolean _isEmpty = ((List<String>)Conversions.doWrapArray(args)).isEmpty();
        if (_isEmpty) {
          JOptionPane.showMessageDialog(null, "argument missing\nusage : scanexam file", "InfoBox: ", JOptionPane.ERROR_MESSAGE);
          return;
        }
        _xblockexpression = ExamIO.load(args[0]);
      }
      _xtrycatchfinallyexpression = _xblockexpression;
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        String _get = args[0];
        String _plus = ("Could not open exam model file " + _get);
        JOptionPane.showMessageDialog(null, _plus, "InfoBox: ", JOptionPane.ERROR_MESSAGE);
        return;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    final Exam exam = _xtrycatchfinallyexpression;
    GradingData _xtrycatchfinallyexpression_1 = null;
    try {
      _xtrycatchfinallyexpression_1 = ScanExamXtendFactory.gradingData(exam);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception exception = (Exception)_t;
        String _simpleName = exception.getClass().getSimpleName();
        String _plus = (_simpleName + ":");
        String _message = exception.getMessage();
        String _plus_1 = (_plus + _message);
        JOptionPane.showMessageDialog(null, _plus_1, "InfoBox: ", JOptionPane.ERROR_MESSAGE);
        return;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    final GradingData data = _xtrycatchfinallyexpression_1;
    final ScanExamController controler = new ScanExamController(data);
    final Timer timer = new Timer();
    final TimerTask task = new BackupTask(controler);
    timer.schedule(task, 150000, 150000);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final WindowEvent e) {
        try {
          Object _source = e.getSource();
          final JFrame frame = ((JFrame) _source);
          final int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit the application?", 
            "Exit Application", JOptionPane.YES_NO_OPTION);
          if ((result == JOptionPane.YES_OPTION)) {
            final DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date _date = new Date();
            final String data_ = df.format(_date);
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("./backups/");
            String _label = exam.getLabel();
            _builder.append(_label);
            _builder.append("_");
            _builder.append(data_);
            _builder.append(".xmi");
            File _file = new File(_builder.toString());
            ScanExamXtendUtils.save(_file, data);
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("./backups/");
            String _label_1 = exam.getLabel();
            _builder_1.append(_label_1);
            _builder_1.append("_");
            _builder_1.append(data_);
            _builder_1.append(".xls");
            File _file_1 = new File(_builder_1.toString());
            ScanExamExcelBackend.save(_file_1, data);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          }
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    });
    frame.setVisible(true);
    final ScanExamPanel scanPanel = new ScanExamPanel(controler);
    JScrollPane _jScrollPane = new JScrollPane(scanPanel);
    leftPane.add(_jScrollPane);
    ExcelTableViewer _excelTableViewer = new ExcelTableViewer(controler);
    rightPane.add(_excelTableViewer);
    scanPanel.setSize(1600, 400);
    final JPanel buttonPanel = new JPanel();
    JButton prev = new JButton("next Q");
    final ActionListener _function = (ActionEvent e) -> {
      controler.getNextQuestion();
    };
    prev.addActionListener(_function);
    buttonPanel.add(prev);
    JButton next = new JButton("prev Q");
    final ActionListener _function_1 = (ActionEvent e) -> {
      controler.getPrevQuestion();
    };
    next.addActionListener(_function_1);
    buttonPanel.add(next);
    JButton prevStudent = new JButton("prev student");
    final ActionListener _function_2 = (ActionEvent e) -> {
      controler.prevExam();
    };
    prevStudent.addActionListener(_function_2);
    buttonPanel.add(prevStudent);
    JButton nextStudent = new JButton("next student");
    final ActionListener _function_3 = (ActionEvent e) -> {
      controler.nextExam();
    };
    nextStudent.addActionListener(_function_3);
    buttonPanel.add(nextStudent);
    JButton loadXMI = new JButton("load grades ");
    final ActionListener _function_4 = (ActionEvent e) -> {
      controler.loadXMI();
    };
    loadXMI.addActionListener(_function_4);
    buttonPanel.add(loadXMI);
    JButton saveXMI = new JButton("save grades");
    final ActionListener _function_5 = (ActionEvent e) -> {
      controler.saveXMI();
    };
    saveXMI.addActionListener(_function_5);
    buttonPanel.add(saveXMI);
    JButton loadExcel = new JButton("load Excel ");
    final ActionListener _function_6 = (ActionEvent e) -> {
      controler.loadExcel();
    };
    loadExcel.addActionListener(_function_6);
    buttonPanel.add(loadExcel);
    JButton saveExcel = new JButton("save Excel ");
    final ActionListener _function_7 = (ActionEvent e) -> {
      controler.saveExcel();
    };
    saveExcel.addActionListener(_function_7);
    buttonPanel.add(saveExcel);
    contentPane.add(topPane, "North");
    contentPane.add(buttonPanel, "South");
    final KeyStroke validateKS = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);
    final KeyStroke nextAndValidateKS = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.META_DOWN_MASK, true);
    final KeyStroke incGrade = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true);
    final KeyStroke lowerGrade = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true);
    final KeyStroke saveGrades = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK, true);
    final KeyStroke nextQuestion = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.META_DOWN_MASK, true);
    final KeyStroke prevQuestion = KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.META_DOWN_MASK, true);
    final KeyStroke nextStudentKS = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true);
    final KeyStroke prevStudentKS = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true);
    final KeyStroke gotoStudentKS = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK, true);
    final InputMap inputMap = buttonPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    final ActionMap actionMap = buttonPanel.getActionMap();
    final Consumer<List<Integer>> _function_8 = (List<Integer> p) -> {
      Integer _get = p.get(1);
      String _plus = ("SetGradeAndValidate_" + _get);
      final Function<ActionEvent, Object> _function_9 = (ActionEvent e) -> {
        boolean _xblockexpression = false;
        {
          controler.setGrade((p.get(1)).intValue());
          controler.validateGrade();
          controler.nextExam();
          _xblockexpression = true;
        }
        return Boolean.valueOf(_xblockexpression);
      };
      ScanExamMain.mapKeyAction(_plus, buttonPanel, 
        (p.get(0)).intValue(), 
        InputEvent.SHIFT_DOWN_MASK, _function_9);
    };
    Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_1), Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_2), Integer.valueOf(1))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_3), Integer.valueOf(2))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_4), Integer.valueOf(3))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_5), Integer.valueOf(4))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_6), Integer.valueOf(5))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_7), Integer.valueOf(6))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_8), Integer.valueOf(7))))).forEach(_function_8);
    final Consumer<List<Integer>> _function_9 = (List<Integer> p) -> {
      Integer _get = p.get(1);
      String _plus = ("SetGrade_" + _get);
      final Function<ActionEvent, Object> _function_10 = (ActionEvent e) -> {
        boolean _xblockexpression = false;
        {
          controler.setGrade((p.get(1)).intValue());
          _xblockexpression = true;
        }
        return Boolean.valueOf(_xblockexpression);
      };
      ScanExamMain.mapKeyAction(_plus, buttonPanel, 
        (p.get(0)).intValue(), 
        0, _function_10);
    };
    Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_1), Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_2), Integer.valueOf(1))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_3), Integer.valueOf(2))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_4), Integer.valueOf(3))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_5), Integer.valueOf(4))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_6), Integer.valueOf(5))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_7), Integer.valueOf(6))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(KeyEvent.VK_8), Integer.valueOf(7))))).forEach(_function_9);
    inputMap.put(gotoStudentKS, "gotoStudent");
    actionMap.put("gotoStudent", new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        final String firstNumber = JOptionPane.showInputDialog("Enter student ID");
        try {
          final int studentId = Integer.parseInt(firstNumber);
          controler.gotoStudent(studentId);
        } catch (final Throwable _t) {
          if (_t instanceof Exception) {
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        }
      }
    });
    inputMap.put(nextStudentKS, "nextStudent");
    actionMap.put("nextStudent", new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        controler.nextExam();
      }
    });
    inputMap.put(prevStudentKS, "prevStudent");
    actionMap.put("prevStudent", new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        controler.prevExam();
      }
    });
    inputMap.put(nextQuestion, "nextQuestion");
    actionMap.put("nextQuestion", new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        controler.getNextQuestion();
      }
    });
    inputMap.put(prevQuestion, "prevQuestion");
    actionMap.put("prevQuestion", new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        controler.getPrevQuestion();
      }
    });
    inputMap.put(nextAndValidateKS, "nextAndValidateKS");
    actionMap.put("nextAndValidateKS", new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        controler.validateGrade();
        controler.nextExam();
      }
    });
    inputMap.put(validateKS, "validateKS");
    actionMap.put("validateKS", new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        controler.validateGrade();
      }
    });
    inputMap.put(incGrade, "incGrade");
    actionMap.put("incGrade", new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        controler.increaseGrade();
      }
    });
    inputMap.put(lowerGrade, "lowerGrade");
    actionMap.put("lowerGrade", new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        controler.decreaseGrade();
      }
    });
    inputMap.put(saveGrades, "saveGrades");
    actionMap.put("saveGrades", new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        controler.saveExcel();
      }
    });
    frame.setLocation(200, 200);
    frame.setVisible(true);
  }
}
