package fr.istic.tools.scanexam.gui.template;

import fr.istic.tools.scanexam.Exam;
import fr.istic.tools.scanexam.gui.template.ExamEditorController;
import fr.istic.tools.scanexam.gui.template.QuestionViewer;
import fr.istic.tools.scanexam.gui.template.QuestionZoneSelectionPanel;
import fr.istic.tools.scanexam.utils.ScanExamXtendFactory;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class QuestionSelectionApp {
  public static JFrame frame(final Exam exam) {
    JFrame _xblockexpression = null;
    {
      final JFrame frame = new JFrame("Trombinoscope ISTIC");
      frame.setSize(1000, 1000);
      BoxLayout _boxLayout = new BoxLayout(frame, BoxLayout.Y_AXIS);
      frame.setLayout(_boxLayout);
      final JPanel contentPane = new JPanel();
      BoxLayout _boxLayout_1 = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
      contentPane.setLayout(_boxLayout_1);
      frame.setContentPane(contentPane);
      QuestionZoneSelectionPanel test = new QuestionZoneSelectionPanel(exam);
      test.setSize(800, 400);
      ExamEditorController mover = new ExamEditorController(test);
      test.addMouseListener(mover);
      test.addMouseMotionListener(mover);
      JScrollPane _jScrollPane = new JScrollPane(test);
      contentPane.add(_jScrollPane);
      QuestionViewer _questionViewer = new QuestionViewer();
      JScrollPane _jScrollPane_1 = new JScrollPane(_questionViewer);
      contentPane.add(_jScrollPane_1);
      contentPane.add(test.getUIPanel(), "South");
      frame.setSize(800, 400);
      frame.setLocation(200, 200);
      frame.setVisible(true);
      _xblockexpression = frame;
    }
    return _xblockexpression;
  }
  
  public static void main(final String[] args) throws IOException {
    JTextField field1 = new JTextField(" ");
    JButton button1 = new JButton("select");
    GridLayout _gridLayout = new GridLayout(0, 1);
    final JPanel panel = new JPanel(_gridLayout);
    JLabel _jLabel = new JLabel("Name :");
    panel.add(_jLabel);
    panel.add(field1);
    final JLabel jLabel = new JLabel("Template file path :");
    panel.add(jLabel);
    panel.add(button1);
    final Exam exam = ScanExamXtendFactory.exam("dummy");
    final ActionListener _function = (ActionEvent e) -> {
      exam.setFilepath(QuestionSelectionApp.selectFileDialog(panel));
      String _filepath = exam.getFilepath();
      String _plus = ("Template file  :" + _filepath);
      jLabel.setText(_plus);
    };
    button1.addActionListener(_function);
    int result = JOptionPane.showConfirmDialog(null, panel, "Create a new exam template", JOptionPane.OK_CANCEL_OPTION, 
      JOptionPane.PLAIN_MESSAGE);
    if ((result == JOptionPane.OK_OPTION)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("converting ");
      String _filepath = exam.getFilepath();
      _builder.append(_filepath);
      _builder.append(" to png files");
      System.out.println(_builder);
      JFrame _frame = QuestionSelectionApp.frame(exam);
      new JDialog(_frame);
    } else {
      System.out.println("Cancelled");
    }
  }
  
  public static String selectFileDialog(final JPanel panel) {
    String _xblockexpression = null;
    {
      File _file = new File(".");
      final JFileChooser chooser = new JFileChooser(_file);
      final FileNameExtensionFilter filter = new FileNameExtensionFilter("Adobde PDF", "pdf");
      chooser.setFileFilter(filter);
      final int returnVal = chooser.showOpenDialog(panel);
      if ((returnVal == JFileChooser.APPROVE_OPTION)) {
        String _name = chooser.getSelectedFile().getName();
        String _plus = ("You chose to open this file: " + _name);
        System.out.println(_plus);
        return chooser.getSelectedFile().getName();
      }
      _xblockexpression = "";
    }
    return _xblockexpression;
  }
}
