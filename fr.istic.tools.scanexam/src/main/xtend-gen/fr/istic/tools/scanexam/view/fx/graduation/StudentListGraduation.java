package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.StudentItemGraduation;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("all")
public class StudentListGraduation extends VBox {
  private static final Logger logger = LogManager.getLogger();
  
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
    StudentItemGraduation _xblockexpression = null;
    {
      boolean _noItems = this.noItems();
      if (_noItems) {
        return null;
      }
      Node _get = this.getChildren().get(this.currentIndex);
      _xblockexpression = ((StudentItemGraduation) _get);
    }
    return _xblockexpression;
  }
  
  public boolean noItems() {
    return this.getChildren().isEmpty();
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
  
  public void updateInModel(final StudentItemGraduation item) {
    StudentListGraduation.logger.info((("Updating " + item) + " to model"));
    this.controller.getAdapter().getPresenter().renameStudent(item.getStudentId(), item.getStudentName());
  }
  
  /**
   * Method used for highlighting
   */
  public Class<Void> focusItem(final StudentItemGraduation item) {
    if ((item == null)) {
      return void.class;
    }
    ObservableList<Node> _children = this.getChildren();
    for (final Node n : _children) {
      {
        StudentItemGraduation question = ((StudentItemGraduation) n);
        question.setFocus(false);
      }
    }
    item.setFocus(true);
    return null;
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
