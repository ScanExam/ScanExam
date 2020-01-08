package fr.istic.tools.scanexam.gui;

import fr.istic.tools.scanexam.QuestionGrade;
import fr.istic.tools.scanexam.StudentGrade;
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
  
  @Override
  public int getRowCount() {
    return this.data.getQuestionGrades().size();
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
