package fr.istic.tools.scanexam.view.fX.corrector;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class Grader extends VBox {
  public static class GradeItem extends HBox {
    public GradeItem(final String name, final String points, final int itemId, final int questionId) {
      ToggleButton _toggleButton = new ToggleButton("X");
      this.selected = _toggleButton;
      Label _label = new Label(name);
      this.name = _label;
      Label _label_1 = new Label(points);
      this.worth = _label_1;
      this.itemId = itemId;
      this.questionId = questionId;
      this.getChildren().addAll(this.selected, this.name, this.worth);
    }
    
    public void checkPressed(final List<Integer> ids) {
      boolean _contains = ids.contains(Integer.valueOf(this.itemId));
      if (_contains) {
        this.selected.setSelected(true);
      } else {
        this.selected.setSelected(false);
      }
    }
    
    public boolean checkVisible(final int questionId) {
      if ((this.questionId == questionId)) {
        return true;
      } else {
        return false;
      }
    }
    
    public void toggleVisible(final boolean visible) {
      this.setVisible(visible);
    }
    
    private ToggleButton selected;
    
    private int itemId;
    
    private int questionId;
    
    private Label name;
    
    private Label worth;
  }
  
  public Grader() {
    LinkedList<Grader.GradeItem> _linkedList = new LinkedList<Grader.GradeItem>();
    this.items = _linkedList;
  }
  
  private List<Grader.GradeItem> items;
  
  public void display(final int questionId, final List<Integer> ids) {
    this.getChildren().clear();
    for (final Grader.GradeItem item : this.items) {
      boolean _checkVisible = item.checkVisible(questionId);
      if (_checkVisible) {
        this.getChildren().add(item);
        item.checkPressed(ids);
      }
    }
  }
  
  public void add(final String name, final String points, final int itemId, final int questionId) {
    Grader.GradeItem _gradeItem = new Grader.GradeItem(name, points, itemId, questionId);
    this.items.add(_gradeItem);
  }
}
