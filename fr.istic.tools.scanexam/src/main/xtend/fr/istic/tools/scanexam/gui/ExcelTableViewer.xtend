package fr.istic.tools.scanexam.gui

import java.awt.Color
import java.awt.Dimension
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.JTextArea
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.event.ListSelectionListener
import javax.swing.table.DefaultTableModel

class ExcelTableViewer extends JPanel implements DocumentListener {
	boolean DEBUG = false
	package JTable table
	JScrollPane scrollPane
	package ListSelectionListener listener
	JLabel label
	JTextArea numAnon;
	ScanExamController controler;
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

	def void updateTable() {
		val q= controler.currentQuestionIndex;
		val s = controler.currentStudent
		label.text = s.studentID
		label.repaint
		
		numAnon.text = ""+ s.numAnonymat
		label.repaint
		var GradesTableModel tableModel = new GradesTableModel(s)
		table.setModel(tableModel)
		table.repaint()
		table.editingRow = q
		repaint
	}

	def setAnoNumber() {
		if (numAnon.text.length>0) {
    	try {
    		val number = Long.parseLong(numAnon.text)	
			controler.currentStudent.numAnonymat = number 
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
//    		JOptionPane.showMessageDialog(null, 
//                      '''Wrong format, integer expected «numAnon.text» found''', 
//                      "Format error", 
//                      JOptionPane.ERROR_MESSAGE);
    	}
		}
		
	}
    override void removeUpdate(DocumentEvent e) {setAnoNumber}

    override void insertUpdate(DocumentEvent e) {setAnoNumber}

    override void changedUpdate(DocumentEvent arg0) {setAnoNumber}

	new(ScanExamController c) {
		super()
		controler =c;  
		label = new JLabel("none")
		label.setSize(200,40)
		add(label)
		numAnon=new JTextArea("0");
		this.layout = new BoxLayout(this, BoxLayout.Y_AXIS)
		numAnon.setSize(200,40)
		numAnon.getDocument().addDocumentListener(this);
		

		
		add(numAnon)
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
		updateTable()
	}
}
