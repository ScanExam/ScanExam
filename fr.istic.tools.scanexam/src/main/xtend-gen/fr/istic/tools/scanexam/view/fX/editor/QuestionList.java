package fr.istic.tools.scanexam.view.fX.editor;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.view.fX.editor.Box;
import fr.istic.tools.scanexam.view.fX.editor.BoxType;
import fr.istic.tools.scanexam.view.fX.editor.ControllerFXEditor;
import fr.istic.tools.scanexam.view.fX.editor.EditorQuestionItem;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class QuestionList extends VBox {
  public QuestionList(final ControllerFXEditor controller) {
    this.controller = controller;
    VBox.setVgrow(this, Priority.ALWAYS);
  }
  
  private ControllerFXEditor controller;
  
  public boolean loadQuestion(final EditorQuestionItem item) {
    boolean _xblockexpression = false;
    {
      this.add(item);
      _xblockexpression = this.controller.addZone(item.getZone());
    }
    return _xblockexpression;
  }
  
  /**
   * Create new question and add to model
   */
  public boolean newQuestion(final Box box) {
    boolean _xblockexpression = false;
    {
      BoxType type = BoxType.QUESTION;
      ControllerFXEditor.SelectedTool _selectedTool = this.controller.getSelectedTool();
      if (_selectedTool != null) {
        switch (_selectedTool) {
          case QUESTION_AREA:
            type = BoxType.QUESTION;
            break;
          case ID_AREA:
            type = BoxType.ID;
            break;
          case QR_AREA:
            type = BoxType.QR;
            break;
          default:
            break;
        }
      } else {
      }
      int _currentPdfPageNumber = this.controller.getEditor().getPresenter().getPresenterPdf().currentPdfPageNumber();
      EditorQuestionItem item = new EditorQuestionItem(this, box, type, _currentPdfPageNumber);
      this.addToModel(item);
      _xblockexpression = this.add(item);
    }
    return _xblockexpression;
  }
  
  public boolean removeQuestion(final EditorQuestionItem item) {
    boolean _xblockexpression = false;
    {
      this.removeFocus();
      this.controller.getMainPane().removeZone(item.getZone());
      this.controller.getMainPane().getChildren().remove(item.getZone());
      _xblockexpression = this.remove(item);
    }
    return _xblockexpression;
  }
  
  public int addToModel(final EditorQuestionItem item) {
    return item.setQuestionId(this.controller.getEditor().getPresenter().getPresenterQuestionZone().createQuestion(item.getZone().getX(), item.getZone().getY(), 
      item.getZone().getHeight(), item.getZone().getWidth()));
  }
  
  public void updateInModel(final EditorQuestionItem item) {
    this.controller.getEditor().getPresenter().getPresenterQuestionZone().moveQuestion(item.getQuestionId(), item.getZone().getX(), item.getZone().getY());
    this.controller.getEditor().getPresenter().getPresenterQuestionZone().resizeQuestion(item.getQuestionId(), item.getZone().getHeight(), 
      item.getZone().getWidth());
  }
  
  public void removeFromModel(final EditorQuestionItem item) {
    this.controller.getEditor().getPresenter().getPresenterQuestionZone().removeQuestion(item.getQuestionId());
  }
  
  public void focusQuestion(final EditorQuestionItem item) {
  }
  
  public void changeFocus(final EditorQuestionItem item) {
    ObservableList<Node> _children = this.getChildren();
    for (final Node n : _children) {
      {
        EditorQuestionItem question = ((EditorQuestionItem) n);
        boolean _equals = Objects.equal(question, item);
        if (_equals) {
          question.setFocus(true);
        } else {
          question.setFocus(false);
        }
      }
    }
  }
  
  public void removeFocus() {
    ObservableList<Node> _children = this.getChildren();
    for (final Node e : _children) {
      if ((e instanceof EditorQuestionItem)) {
        ((EditorQuestionItem) e).setFocus(false);
      }
    }
  }
  
  public void changeGradeItems(final EditorQuestionItem newItem) {
    this.controller.changeFocus(newItem);
  }
  
  public void showOnlyPage(final int page) {
    ObservableList<Node> _children = this.getChildren();
    for (final Node e : _children) {
      if ((e instanceof EditorQuestionItem)) {
        this.checkAndDisplay(page, ((EditorQuestionItem) e));
      }
    }
  }
  
  public void checkAndDisplay(final int page, final EditorQuestionItem item) {
    int _page = item.getPage();
    boolean _equals = (_page == page);
    if (_equals) {
      item.getZone().isVisible(true);
    } else {
      item.getZone().isVisible(false);
    }
  }
  
  public boolean add(final EditorQuestionItem item) {
    return this.getChildren().add(item);
  }
  
  public boolean remove(final EditorQuestionItem item) {
    return this.getChildren().remove(item);
  }
  
  public ControllerFXEditor getController() {
    return this.controller;
  }
}
