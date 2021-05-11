package fr.istic.tools.scanexam.view.fx.editor;

import com.google.common.base.Objects;
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
      QuestionItemEdition item = new QuestionItemEdition(this, box, name, (page + 1), id);
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
    int _currentPdfPageNumber = this.controller.getPdfManager().currentPdfPageNumber();
    String _plus = ("page : " + Integer.valueOf(_currentPdfPageNumber));
    String _plus_1 = (_plus + Integer.valueOf(1));
    InputOutput.<String>println(_plus_1);
    int _currentPdfPageNumber_1 = this.controller.getPdfManager().currentPdfPageNumber();
    int _plus_2 = (_currentPdfPageNumber_1 + 1);
    QuestionItemEdition item = new QuestionItemEdition(this, box, type, _plus_2);
    this.addToModel(item);
    this.add(item);
  }
  
  public void removeQuestion(final QuestionItemEdition item) {
    this.removeFocus();
    this.controller.getMainPane().removeZone(item.getZone());
    this.remove(item);
    this.removeFromModel(item);
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
        this.checkAndDisplay((page + 1), ((QuestionItemEdition) e));
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
    item.setQuestionId(this.controller.createQuestion(_divide, _divide_1, _divide_2, _divide_3));
    this.updateInModel(item);
  }
  
  public void updateInModel(final QuestionItemEdition item) {
    int _questionId = item.getQuestionId();
    double _x = item.getZone().getX();
    double _maxX = this.controller.getMaxX();
    double _divide = (_x / _maxX);
    double _y = item.getZone().getY();
    double _maxY = this.controller.getMaxY();
    double _divide_1 = (_y / _maxY);
    this.controller.moveQuestion(_questionId, _divide, _divide_1);
    int _questionId_1 = item.getQuestionId();
    double _height = item.getZone().getHeight();
    double _maxY_1 = this.controller.getMaxY();
    double _divide_2 = (_height / _maxY_1);
    double _width = item.getZone().getWidth();
    double _maxX_1 = this.controller.getMaxX();
    double _divide_3 = (_width / _maxX_1);
    this.controller.resizeQuestion(_questionId_1, _divide_2, _divide_3);
    this.controller.renameQuestion(item.getQuestionId(), item.getName());
    this.controller.changeQuestionWorth(item.getQuestionId(), item.getScale());
  }
  
  public void removeFromModel(final QuestionItemEdition item) {
    this.controller.removeQuestion(item.getQuestionId());
  }
}
