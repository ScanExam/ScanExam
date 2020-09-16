package fr.istic.tools.scanexam.gui

import fr.istic.tools.scanexam.QuestionGrade
import fr.istic.tools.scanexam.StudentGrade
import javax.swing.table.AbstractTableModel

import static extension fr.istic.tools.scanexam.utils.ScanExamXtendUtils.*

class GradesTableModel extends AbstractTableModel {
	StudentGrade data

	new(StudentGrade data) {
		this.data = data
	}

	override int getColumnCount() {
		return 2
	}

	override Object getValueAt(int row, int col) {
		if (row>=data.getQuestionGrades().size) {
			switch (col) {
				case 0: {
					return "Total"
				}
				case 1: {
					return data.computeGrade()
				}
				default: {
					return "ERROR"
				}
			}
		} else {
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
	}

	override int getRowCount() {
		return data.getQuestionGrades().size()+1
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
