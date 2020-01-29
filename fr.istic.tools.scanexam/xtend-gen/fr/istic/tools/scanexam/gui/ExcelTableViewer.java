package fr.istic.tools.scanexam.gui;

import fr.istic.tools.scanexam.StudentGrade;
import fr.istic.tools.scanexam.gui.GradesTableModel;
import fr.istic.tools.scanexam.gui.ScanExamController;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ExcelTableViewer extends JPanel implements DocumentListener {
  private boolean DEBUG = false;
  
  JTable table;
  
  private JScrollPane scrollPane;
  
  ListSelectionListener listener;
  
  private JLabel label;
  
  private JTextArea numAnon;
  
  private ScanExamController controler;
  
  public Object getCellAt(final int row, final int col) {
    return this.table.getModel().getValueAt(row, col);
  }
  
  public Object getActiveCell() {
    return this.table.getModel().getValueAt(this.table.getSelectedRow(), this.table.getSelectedColumn());
  }
  
  public int getSelectedRow() {
    return this.table.getSelectedRow();
  }
  
  public int getSelectedColumns() {
    return this.table.getSelectedColumn();
  }
  
  public void addListSelectionListener(final ListSelectionListener listener) {
    this.listener = listener;
    this.table.getSelectionModel().addListSelectionListener(listener);
  }
  
  public void updateTable() {
    final int q = this.controler.currentQuestionIndex;
    final StudentGrade s = this.controler.getCurrentStudent();
    this.label.setText(s.getStudentID());
    this.label.repaint();
    long _numAnonymat = s.getNumAnonymat();
    String _plus = ("" + Long.valueOf(_numAnonymat));
    this.numAnon.setText(_plus);
    this.label.repaint();
    GradesTableModel tableModel = new GradesTableModel(s);
    this.table.setModel(tableModel);
    this.table.repaint();
    this.table.setEditingRow(q);
    this.repaint();
  }
  
  public void setAnoNumber() {
    int _length = this.numAnon.getText().length();
    boolean _greaterThan = (_length > 0);
    if (_greaterThan) {
      try {
        StudentGrade _currentStudent = this.controler.getCurrentStudent();
        _currentStudent.setNumAnonymat(Long.parseLong(this.numAnon.getText()));
      } catch (final Throwable _t) {
        if (_t instanceof NumberFormatException) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("Wrong format, integer expected ");
          String _text = this.numAnon.getText();
          _builder.append(_text);
          _builder.append(" found");
          JOptionPane.showMessageDialog(null, _builder, 
            "Format error", 
            JOptionPane.ERROR_MESSAGE);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
  }
  
  @Override
  public void removeUpdate(final DocumentEvent e) {
    this.setAnoNumber();
  }
  
  @Override
  public void insertUpdate(final DocumentEvent e) {
    this.setAnoNumber();
  }
  
  @Override
  public void changedUpdate(final DocumentEvent arg0) {
    this.setAnoNumber();
  }
  
  public ExcelTableViewer(final ScanExamController c) {
    super();
    this.controler = c;
    JLabel _jLabel = new JLabel("none");
    this.label = _jLabel;
    this.label.setSize(200, 40);
    this.add(this.label);
    JTextArea _jTextArea = new JTextArea("0");
    this.numAnon = _jTextArea;
    BoxLayout _boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
    this.setLayout(_boxLayout);
    this.numAnon.setSize(200, 40);
    this.numAnon.getDocument().addDocumentListener(this);
    this.add(this.numAnon);
    JTable _jTable = new JTable();
    this.table = _jTable;
    DefaultTableModel _defaultTableModel = new DefaultTableModel();
    this.table.setModel(_defaultTableModel);
    Dimension _dimension = new Dimension(250, 800);
    this.table.setPreferredScrollableViewportSize(_dimension);
    this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    Color light_blue = new Color(205, 235, 255);
    this.table.getTableHeader().setBackground(light_blue);
    this.table.getSelectionModel().addListSelectionListener(this.listener);
    JScrollPane _jScrollPane = new JScrollPane(this.table);
    this.scrollPane = _jScrollPane;
    this.scrollPane.setBackground(light_blue);
    this.scrollPane.getViewport().setBackground(light_blue);
    this.add(this.scrollPane);
    c.setTableView(this);
    this.updateTable();
  }
}
