package fr.istic.tools.scanexam.gui.template;

import fr.istic.tools.scanexam.gui.template.ImagesTableModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class QuestionViewer extends JPanel {
  private boolean DEBUG = false;
  
  JTable table;
  
  private JScrollPane scrollPane;
  
  ListSelectionListener listener;
  
  public Object getCellAt(final int row) {
    return this.table.getModel().getValueAt(row, 0);
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
    int nbRows = 6;
    int nbCols = 2;
    String[] donnees = new String[nbRows];
    String entete = "title";
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(nbCols);
    _builder.append("X");
    _builder.append(nbRows);
    System.out.println(_builder);
    for (int row = 1; (row < nbRows); row++) {
      {
        final String[] _wrVal_donnees = donnees;
        final int _wrIndx_donnees = (row - 1);
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("data_");
        _builder_1.append(row);
        _builder_1.append("_");
        _builder_1.append(0);
        _wrVal_donnees[_wrIndx_donnees] = _builder_1.toString();
      }
    }
    ImagesTableModel tableModel = new ImagesTableModel(donnees, entete);
    this.table.setModel(tableModel);
    this.table.repaint();
  }
  
  public QuestionViewer() {
    super(new GridLayout(1, 0));
    JTable _jTable = new JTable();
    this.table = _jTable;
    DefaultTableModel _defaultTableModel = new DefaultTableModel();
    this.table.setModel(_defaultTableModel);
    Dimension _dimension = new Dimension(500, 300);
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
    this.updateTable();
  }
}
