package fr.istic.tools.scanexam.view.fx.corrector;

import fr.istic.tools.scanexam.view.fx.corrector.ControllerFXCorrector;
import fr.istic.tools.scanexam.view.fx.corrector.QuestionItemCorrector;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class QuestionListCorrector extends VBox {
  public QuestionListCorrector(final ControllerFXCorrector controller) {
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
  
  public QuestionItemCorrector getCurrentItem() {
    Node _get = this.getChildren().get(this.currentIndex);
    return ((QuestionItemCorrector) _get);
  }
  
  public boolean addItem(final QuestionItemCorrector item) {
    boolean _xblockexpression = false;
    {
      item.setList(this);
      _xblockexpression = this.getChildren().add(item);
    }
    return _xblockexpression;
  }
  
  public boolean removeItem(final QuestionItemCorrector item) {
    return this.getChildren().remove(item);
  }
  
  public void clearItems() {
    this.getChildren().clear();
  }
  
  /**
   * Method used for highlighting
   */
  public void focusItem(final QuestionItemCorrector item) {
    ObservableList<Node> _children = this.getChildren();
    for (final Node n : _children) {
      {
        QuestionItemCorrector question = ((QuestionItemCorrector) n);
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
  
  public int selectItem(final QuestionItemCorrector item) {
    return this.currentIndex = this.getChildren().indexOf(item);
  }
  
  public Object setupEvents() {
    return null;
  }
}
