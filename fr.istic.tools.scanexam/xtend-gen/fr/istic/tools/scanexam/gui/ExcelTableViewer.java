package fr.istic.tools.scanexam.gui;

import fr.istic.tools.scanexam.StudentGrade;
import fr.istic.tools.scanexam.gui.GradesTableModel;
import fr.istic.tools.scanexam.gui.ScanExamController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("all")
public class ExcelTableViewer extends JPanel {
  private boolean DEBUG = false;
  
  JTable table;
  
  private JScrollPane scrollPane;
  
  ListSelectionListener listener;
  
  private JLabel label;
  
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
  
  public void updateTable(final StudentGrade s) {
    this.label.setText(s.getStudentID());
    this.label.repaint();
    GradesTableModel tableModel = new GradesTableModel(s);
    this.table.setModel(tableModel);
    this.table.repaint();
    this.repaint();
  }
  
  public ExcelTableViewer(final ScanExamController c) {
    super(new GridLayout(2, 0));
    JLabel _jLabel = new JLabel("none");
    this.label = _jLabel;
    this.add(this.label);
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
    this.updateTable(c.getCurrentStudent());
  }
}
