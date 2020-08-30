package fr.istic.tools.scanexam.gui.template

import javax.swing.table.AbstractTableModel

class ImagesTableModel extends AbstractTableModel {
	package Object[] donnees
	package String titres

	new(Object[] donnees, String titres) {
		this.donnees = donnees
		this.titres = titres
	}

	override int getColumnCount() {
		return 1
	}

	override Object getValueAt(int parm1, int parm2) {
		return {
			val _rdIndx_donnees = parm1
			donnees.get(_rdIndx_donnees)
		}
	}

	override int getRowCount() {
		return donnees.length
	}

	override String getColumnName(int col) {
		return titres
	}
}
