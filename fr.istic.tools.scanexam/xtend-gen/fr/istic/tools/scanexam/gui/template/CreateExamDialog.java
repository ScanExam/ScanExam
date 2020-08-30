package fr.istic.tools.scanexam.gui.template;

import fr.istic.tools.scanexam.Exam;
import fr.istic.tools.scanexam.utils.ScanExamXtendFactory;
import fr.istic.tools.scanexam.utils.ScanExamXtendUtils;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;

/**
 * @see https://stackoverflow.com/a/3002830/230513
 */
@SuppressWarnings("all")
class CreateExamDialog {
  private static void display() {
    JTextField field1 = new JTextField(" ");
    JButton button1 = new JButton("select");
    JButton button2 = new JButton("select");
    GridLayout _gridLayout = new GridLayout(0, 1);
    final JPanel panel = new JPanel(_gridLayout);
    JLabel _jLabel = new JLabel("Name :");
    panel.add(_jLabel);
    panel.add(field1);
    final JLabel jLabel = new JLabel("Template file path :");
    panel.add(jLabel);
    panel.add(button1);
    final JLabel jLabel2 = new JLabel("Grades file path :");
    panel.add(jLabel2);
    panel.add(button2);
    final Exam exam = ScanExamXtendFactory.exam("dummy");
    final ActionListener _function = (ActionEvent e) -> {
      String _folderPath = exam.getFolderPath();
      String _plus = ("Template file  :" + _folderPath);
      jLabel.setText(_plus);
    };
    button1.addActionListener(_function);
    final ActionListener _function_1 = (ActionEvent e) -> {
      String _filepath = exam.getFilepath();
      String _plus = ("Grades file  :" + _filepath);
      jLabel2.setText(_plus);
    };
    button2.addActionListener(_function_1);
    int result = JOptionPane.showConfirmDialog(null, panel, "Create a new exam template", JOptionPane.OK_CANCEL_OPTION, 
      JOptionPane.PLAIN_MESSAGE);
    if ((result == JOptionPane.OK_OPTION)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("converting ");
      String _filepath = exam.getFilepath();
      _builder.append(_filepath);
      _builder.append(" to png files");
      System.out.println(_builder);
      final File[] images = ScanExamXtendUtils.convertPdfToPng(exam.getFilepath());
      exam.setNumberOfPages(((List<File>)Conversions.doWrapArray(images)).size());
    } else {
      System.out.println("Cancelled");
    }
  }
  
  public static void main(final String[] args) {
    final Runnable _function = () -> {
      CreateExamDialog.display();
    };
    EventQueue.invokeLater(_function);
    ScanExamXtendUtils.convertPdfToPng("sample_form.pdf");
  }
}
