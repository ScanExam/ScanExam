package fr.istic.tools.scanexam.view.fX.corrector;

import fr.istic.tools.scanexam.view.fX.corrector.ControllerFXCorrector;
import fr.istic.tools.scanexam.view.fX.corrector.StudentItemCorrector;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class StudentListCorrector extends VBox {
  public StudentListCorrector(final ControllerFXCorrector controller) {
    this.controller = controller;
    this.currentIndex = 0;
    this.setSpacing(5);
    this.setupEvents();
  }
  
  private ControllerFXCorrector controller;
  
  private int currentIndex;
  
  public ControllerFXCorrector getController() {
    return this.controller;
  }
  
  public int getCurrentIndex() {
    return this.currentIndex;
  }
  
  public StudentItemCorrector getCurrentItem() {
    Node _get = this.getChildren().get(this.currentIndex);
    return ((StudentItemCorrector) _get);
  }
  
  public boolean addItem(final StudentItemCorrector item) {
    boolean _xblockexpression = false;
    {
      item.setList(this);
      _xblockexpression = this.getChildren().add(item);
    }
    return _xblockexpression;
  }
  
  public boolean removeItem(final StudentItemCorrector item) {
    return this.getChildren().remove(item);
  }
  
  public void clearItems() {
    this.getChildren().clear();
  }
  
  /**
   * Method used for highlighting
   */
  public void focusItem(final StudentItemCorrector item) {
    ObservableList<Node> _children = this.getChildren();
    for (final Node n : _children) {
      {
        StudentItemCorrector question = ((StudentItemCorrector) n);
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
  
  public int selectItem(final StudentItemCorrector item) {
    return this.currentIndex = this.getChildren().indexOf(item);
  }
  
  public Object setupEvents() {
    return null;
  }
}
