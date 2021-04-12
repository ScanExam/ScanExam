package fr.istic.tools.scanexam.view.fx.editor;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.presenter.PresenterQuestionZone;
import fr.istic.tools.scanexam.view.fx.editor.Box;
import fr.istic.tools.scanexam.view.fx.editor.BoxType;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import fr.istic.tools.scanexam.view.fx.editor.QuestionItemEdition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class QuestionListEdition extends VBox {
  public QuestionListEdition(final ControllerFxEdition controller) {
    this.controller = controller;
    VBox.setVgrow(this, Priority.ALWAYS);
  }
  
  private ControllerFxEdition controller;
  
  public ControllerFxEdition getController() {
    return this.controller;
  }
  
  public boolean loadQuestion(final Box box, final String name, final int page, final int id, final float questionWorth) {
    boolean _xblockexpression = false;
    {
      QuestionItemEdition item = new QuestionItemEdition(this, box, name, page, id);
      item.setScale(questionWorth);
      _xblockexpression = this.add(item);
    }
    return _xblockexpression;
  }
  
  /**
   * Create new question and add to model
   */
  public void newQuestion(final Box box) {
    BoxType type = BoxType.QUESTION;
    ControllerFxEdition.SelectedTool _selectedTool = this.controller.getSelectedTool();
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
    String _plus = ("page : " + Integer.valueOf(_currentPdfPageNumber));
    InputOutput.<String>println(_plus);
    int _currentPdfPageNumber_1 = this.controller.getEditor().getPresenter().getPresenterPdf().currentPdfPageNumber();
    QuestionItemEdition item = new QuestionItemEdition(this, box, type, _currentPdfPageNumber_1);
    this.addToModel(item);
    this.add(item);
  }
  
  public boolean removeQuestion(final QuestionItemEdition item) {
    boolean _xblockexpression = false;
    {
      this.removeFocus();
      this.controller.getMainPane().removeZone(item.getZone());
      _xblockexpression = this.remove(item);
    }
    return _xblockexpression;
  }
  
  public void select(final QuestionItemEdition item) {
    ObservableList<Node> _children = this.getChildren();
    for (final Node n : _children) {
      {
        QuestionItemEdition question = ((QuestionItemEdition) n);
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
      if ((e instanceof QuestionItemEdition)) {
        ((QuestionItemEdition) e).setFocus(false);
      }
    }
  }
  
  public void showOnlyPage(final int page) {
    ObservableList<Node> _children = this.getChildren();
    for (final Node e : _children) {
      if ((e instanceof QuestionItemEdition)) {
        this.checkAndDisplay(page, ((QuestionItemEdition) e));
      }
    }
  }
  
  public void checkAndDisplay(final int page, final QuestionItemEdition item) {
    int _page = item.getPage();
    boolean _equals = (_page == page);
    if (_equals) {
      item.getZone().isVisible(true);
    } else {
      item.getZone().isVisible(false);
    }
  }
  
  public boolean add(final QuestionItemEdition item) {
    return this.getChildren().add(item);
  }
  
  public boolean remove(final QuestionItemEdition item) {
    return this.getChildren().remove(item);
  }
  
  public void clear() {
    this.controller.selectQuestion(null);
    this.getChildren().clear();
  }
  
  public void addToModel(final QuestionItemEdition item) {
    PresenterQuestionZone _presenterQuestionZone = this.controller.getEditor().getPresenter().getPresenterQuestionZone();
    double _x = item.getZone().getX();
    double _maxX = this.controller.getMaxX();
    double _divide = (_x / _maxX);
    double _y = item.getZone().getY();
    double _maxY = this.controller.getMaxY();
    double _divide_1 = (_y / _maxY);
    double _height = item.getZone().getHeight();
    double _maxY_1 = this.controller.getMaxY();
    double _divide_2 = (_height / _maxY_1);
    double _width = item.getZone().getWidth();
    double _maxX_1 = this.controller.getMaxX();
    double _divide_3 = (_width / _maxX_1);
    item.setQuestionId(_presenterQuestionZone.createQuestion(_divide, _divide_1, _divide_2, _divide_3));
    this.updateInModel(item);
  }
  
  public void updateInModel(final QuestionItemEdition item) {
    PresenterQuestionZone _presenterQuestionZone = this.controller.getEditor().getPresenter().getPresenterQuestionZone();
    int _questionId = item.getQuestionId();
    double _x = item.getZone().getX();
    double _maxX = this.controller.getMaxX();
    double _divide = (_x / _maxX);
    double _y = item.getZone().getY();
    double _maxY = this.controller.getMaxY();
    double _divide_1 = (_y / _maxY);
    _presenterQuestionZone.moveQuestion(_questionId, _divide, _divide_1);
    PresenterQuestionZone _presenterQuestionZone_1 = this.controller.getEditor().getPresenter().getPresenterQuestionZone();
    int _questionId_1 = item.getQuestionId();
    double _height = item.getZone().getHeight();
    double _maxY_1 = this.controller.getMaxY();
    double _divide_2 = (_height / _maxY_1);
    double _width = item.getZone().getWidth();
    double _maxX_1 = this.controller.getMaxX();
    double _divide_3 = (_width / _maxX_1);
    _presenterQuestionZone_1.resizeQuestion(_questionId_1, _divide_2, _divide_3);
    this.controller.getEditor().getPresenter().getPresenterQuestionZone().renameQuestion(item.getQuestionId(), item.getName());
    this.controller.getEditor().getPresenter().getPresenterQuestionZone().changeQuestionWorth(item.getQuestionId(), item.getScale());
  }
  
  public void removeFromModel(final QuestionItemEdition item) {
    this.controller.getEditor().getPresenter().getPresenterQuestionZone().removeQuestion(item.getQuestionId());
  }
}
