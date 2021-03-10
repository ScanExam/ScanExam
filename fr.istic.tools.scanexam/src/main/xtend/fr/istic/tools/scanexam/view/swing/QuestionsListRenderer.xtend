package fr.istic.tools.scanexam.view.swing

import java.awt.Color
import java.awt.Component
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.ListCellRenderer

class QuestionsListRenderer implements ListCellRenderer<Object> {
	override Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
		boolean cellHasFocus) {
		var JPanel renderer = (value as JPanel)
		renderer.setBackground(if(isSelected) Color.LIGHT_GRAY else list.getBackground())
		return renderer
	}
}
