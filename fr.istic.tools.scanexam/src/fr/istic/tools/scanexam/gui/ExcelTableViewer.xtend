package fr.istic.tools.scanexam.gui

import javax.swing.*
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener
import javax.swing.table.DefaultTableModel
import fr.istic.tools.scanexam.GradingData
import fr.istic.tools.scanexam.StudentGrade
import java.awt.*
import java.io.File
import java.text.Normalizer

class ExcelTableViewer extends JPanel {
	boolean DEBUG = false
	package JTable table
	JScrollPane scrollPane
	package ListSelectionListener listener
	JLabel label
	def Object getCellAt(int row, int col) {
		return table.getModel().getValueAt(row, col)
	}

	def Object getActiveCell() {
		return table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn())
	}

	def int getSelectedRow() {
		return table.getSelectedRow()
	}

	def int getSelectedColumns() {
		return table.getSelectedColumn()
	}

	def void addListSelectionListener(ListSelectionListener listener) {
		this.listener = listener
		table.getSelectionModel().addListSelectionListener(listener)
	}

	def void updateTable(StudentGrade s) {
		label.text = s.studentID
		label.repaint
		var GradesTableModel tableModel = new GradesTableModel(s)
		table.setModel(tableModel)
		table.repaint()
		repaint
	}

	new(ScanExamController c) {
		super(new GridLayout(2, 0))
		label = new JLabel("none")
		add(label)
		table = new JTable()
		table.setModel(new DefaultTableModel())
		table.setPreferredScrollableViewportSize(new Dimension(250, 800))
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF)
		var Color light_blue = new Color(205, 235, 255)
		table.getTableHeader().setBackground(light_blue)
		table.getSelectionModel().addListSelectionListener(listener)
		scrollPane = new JScrollPane(table)
		scrollPane.setBackground(light_blue)
		scrollPane.getViewport().setBackground(light_blue)
		add(scrollPane)
		c.tableView = this
		updateTable(c.currentStudent)
	}
}
