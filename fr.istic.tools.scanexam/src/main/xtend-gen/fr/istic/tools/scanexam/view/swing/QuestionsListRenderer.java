package fr.istic.tools.scanexam.view.swing;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

@SuppressWarnings("all")
public class QuestionsListRenderer implements ListCellRenderer<Object> {
  @Override
  public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
    JPanel renderer = ((JPanel) value);
    Color _xifexpression = null;
    if (isSelected) {
      _xifexpression = Color.LIGHT_GRAY;
    } else {
      _xifexpression = list.getBackground();
    }
    renderer.setBackground(_xifexpression);
    return renderer;
  }
}
