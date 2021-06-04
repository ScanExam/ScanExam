package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.QuestionItemGraduation;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class QuestionListGraduation extends VBox {
  public QuestionListGraduation(final ControllerFxGraduation controller) {
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
  
  public QuestionItemGraduation getCurrentItem() {
    QuestionItemGraduation _xblockexpression = null;
    {
      boolean _noItems = this.noItems();
      if (_noItems) {
        return null;
      }
      Node _get = this.getChildren().get(this.currentIndex);
      _xblockexpression = ((QuestionItemGraduation) _get);
    }
    return _xblockexpression;
  }
  
  public boolean addItem(final QuestionItemGraduation item) {
    boolean _xblockexpression = false;
    {
      item.setList(this);
      _xblockexpression = this.getChildren().add(item);
    }
    return _xblockexpression;
  }
  
  public boolean removeItem(final QuestionItemGraduation item) {
    return this.getChildren().remove(item);
  }
  
  public void clearItems() {
    this.currentIndex = 0;
    this.getChildren().clear();
  }
  
  public boolean noItems() {
    return this.getChildren().isEmpty();
  }
  
  /**
   * Method used for highlighting
   */
  public Class<Void> focusItem(final QuestionItemGraduation item) {
    if ((item == null)) {
      return void.class;
    }
    ObservableList<Node> _children = this.getChildren();
    for (final Node n : _children) {
      {
        QuestionItemGraduation question = ((QuestionItemGraduation) n);
        question.setFocus(false);
      }
    }
    item.setFocus(true);
    return null;
  }
  
  public int selectNextItem() {
    int _xifexpression = (int) 0;
    int _size = this.getChildren().size();
    boolean _lessThan = ((this.currentIndex + 1) < _size);
    if (_lessThan) {
      _xifexpression = this.currentIndex++;
    } else {
      _xifexpression = this.currentIndex = 0;
    }
    return _xifexpression;
  }
  
  public int selectPreviousItem() {
    int _xifexpression = (int) 0;
    if ((this.currentIndex > 0)) {
      _xifexpression = this.currentIndex--;
    } else {
      int _size = this.getChildren().size();
      int _minus = (_size - 1);
      _xifexpression = this.currentIndex = _minus;
    }
    return _xifexpression;
  }
  
  public int selectItem(final QuestionItemGraduation item) {
    return this.currentIndex = this.getChildren().indexOf(item);
  }
  
  public QuestionItemGraduation questionWithId(final int id) {
    final Function1<Node, Boolean> _function = (Node question) -> {
      int _questionId = ((QuestionItemGraduation) question).getQuestionId();
      return Boolean.valueOf((_questionId == id));
    };
    Node _findFirst = IterableExtensions.<Node>findFirst(this.getChildren(), _function);
    return ((QuestionItemGraduation) _findFirst);
  }
  
  public Object setupEvents() {
    return null;
  }
}
