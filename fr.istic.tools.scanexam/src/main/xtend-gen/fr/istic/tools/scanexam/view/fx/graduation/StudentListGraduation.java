package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.StudentItemGraduation;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class StudentListGraduation extends VBox {
  public StudentListGraduation(final ControllerFxGraduation controller) {
    this.controller = controller;
    this.currentIndex = 0;
    this.setSpacing(5);
    this.setupEvents();
  }
  
  private ControllerFxGraduation controller;
  
  private int currentIndex;
  
  public ControllerFxGraduation getController() {
    return this.controller;
  }
  
  public int getCurrentIndex() {
    return this.currentIndex;
  }
  
  public StudentItemGraduation getCurrentItem() {
    Node _get = this.getChildren().get(this.currentIndex);
    return ((StudentItemGraduation) _get);
  }
  
  public boolean addItem(final StudentItemGraduation item) {
    boolean _xblockexpression = false;
    {
      item.setList(this);
      _xblockexpression = this.getChildren().add(item);
    }
    return _xblockexpression;
  }
  
  public boolean removeItem(final StudentItemGraduation item) {
    return this.getChildren().remove(item);
  }
  
  public void clearItems() {
    this.getChildren().clear();
  }
  
  /**
   * Method used for highlighting
   */
  public void focusItem(final StudentItemGraduation item) {
    ObservableList<Node> _children = this.getChildren();
    for (final Node n : _children) {
      {
        StudentItemGraduation question = ((StudentItemGraduation) n);
        question.setFocus(false);
      }
    }
    item.setFocus(true);
  }
  
  public int selectNextItem() {
    int _xblockexpression = (int) 0;
    {
      this.currentIndex++;
      int _size = this.getChildren().size();
      int _minus = (_size - 1);
      _xblockexpression = this.currentIndex = Math.min(this.currentIndex, _minus);
    }
    return _xblockexpression;
  }
  
  public int selectPreviousItem() {
    int _xblockexpression = (int) 0;
    {
      this.currentIndex--;
      _xblockexpression = this.currentIndex = Math.max(this.currentIndex, 0);
    }
    return _xblockexpression;
  }
  
  public int selectItem(final StudentItemGraduation item) {
    return this.currentIndex = this.getChildren().indexOf(item);
  }
  
  public Object setupEvents() {
    return null;
  }
}
