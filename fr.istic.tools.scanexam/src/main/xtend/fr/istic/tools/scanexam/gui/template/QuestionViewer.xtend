package fr.istic.tools.scanexam.gui.template

import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.event.ListSelectionListener
import javax.swing.table.DefaultTableModel

class QuestionViewer extends JPanel {
	boolean DEBUG = false
	package JTable table
	JScrollPane scrollPane
	package ListSelectionListener listener

	def Object getCellAt(int row) {
		return table.getModel().getValueAt(row,0)
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

	def void updateTable() {
		var int nbRows = 6
		var int nbCols = 2
		var String[] donnees = newArrayOfSize(nbRows)
		var String entete = "title"
		System.out.println('''«nbCols»X«nbRows»''')
		for (var int row = 1; row < nbRows; row++) {
			{
				val _wrVal_donnees = donnees
				val _wrIndx_donnees = row - 1
				_wrVal_donnees.set(_wrIndx_donnees, '''data_«row»_«0»''')
			}
		}
		var ImagesTableModel tableModel = new ImagesTableModel(donnees, entete)
		table.setModel(tableModel)
		table.repaint()
	}

	new() {
		super(new GridLayout(1, 0))
		table = new JTable()
		table.setModel(new DefaultTableModel())
		table.setPreferredScrollableViewportSize(new Dimension(500, 300))
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF)
		var Color light_blue = new Color(205, 235, 255)
		table.getTableHeader().setBackground(light_blue)
		table.getSelectionModel().addListSelectionListener(listener)
		scrollPane = new JScrollPane(table)
		scrollPane.setBackground(light_blue)
		scrollPane.getViewport().setBackground(light_blue)
		add(scrollPane)
		updateTable()
	}
}
