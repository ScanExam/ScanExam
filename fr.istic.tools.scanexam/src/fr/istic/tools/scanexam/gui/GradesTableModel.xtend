package fr.istic.tools.scanexam.gui

import javax.swing.table.AbstractTableModel
import fr.istic.tools.scanexam.GradingData
import fr.istic.tools.scanexam.QuestionGrade
import fr.istic.tools.scanexam.StudentGrade

class GradesTableModel extends AbstractTableModel {
	StudentGrade data

	new(StudentGrade data) {
		this.data = data
	}

	override int getColumnCount() {
		return 2
	}

	override Object getValueAt(int row, int col) {
		var QuestionGrade qgrade = data.getQuestionGrades().get(row)

		switch (col) {
			case 0: {
				return qgrade.getQuestion().getLabel()
			}
			case 1: {
				return qgrade.getGrade()
			}
			default: {
				return "ERROR"
			}
		}
	}

	override int getRowCount() {
		return data.getQuestionGrades().size()
	}

	override String getColumnName(int col) {

		switch (col) {
			case 0: {
				return "Question "
			}
			case 1: {
				return "Grade"
			}
			default: {
				return "Error"
			}
		}
	}
}
