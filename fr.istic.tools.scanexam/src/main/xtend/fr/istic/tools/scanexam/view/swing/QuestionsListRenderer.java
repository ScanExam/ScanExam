package fr.istic.tools.scanexam.view.swing;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class QuestionsListRenderer implements ListCellRenderer<Object> {
	
	@Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel renderer = (JPanel) value;
        renderer.setBackground(isSelected ? Color.LIGHT_GRAY : list.getBackground());
        return renderer;
    }
}
