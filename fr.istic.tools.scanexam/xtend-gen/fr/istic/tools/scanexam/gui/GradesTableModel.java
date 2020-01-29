package fr.istic.tools.scanexam.gui;

import fr.istic.tools.scanexam.QuestionGrade;
import fr.istic.tools.scanexam.StudentGrade;
import fr.istic.tools.scanexam.utils.ScanExamXtendUtils;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("all")
public class GradesTableModel extends AbstractTableModel {
  private StudentGrade data;
  
  public GradesTableModel(final StudentGrade data) {
    this.data = data;
  }
  
  @Override
  public int getColumnCount() {
    return 2;
  }
  
  @Override
  public Object getValueAt(final int row, final int col) {
    int _size = this.data.getQuestionGrades().size();
    boolean _greaterEqualsThan = (row >= _size);
    if (_greaterEqualsThan) {
      switch (col) {
        case 0:
          return "Total";
        case 1:
          return Double.valueOf(ScanExamXtendUtils.computeGrade(this.data));
        default:
          return "ERROR";
      }
    } else {
      QuestionGrade qgrade = this.data.getQuestionGrades().get(row);
      switch (col) {
        case 0:
          return qgrade.getQuestion().getLabel();
        case 1:
          return qgrade.getGrade();
        default:
          return "ERROR";
      }
    }
  }
  
  @Override
  public int getRowCount() {
    int _size = this.data.getQuestionGrades().size();
    return (_size + 1);
  }
  
  @Override
  public String getColumnName(final int col) {
    switch (col) {
      case 0:
        return "Question ";
      case 1:
        return "Grade";
      default:
        return "Error";
    }
  }
}
