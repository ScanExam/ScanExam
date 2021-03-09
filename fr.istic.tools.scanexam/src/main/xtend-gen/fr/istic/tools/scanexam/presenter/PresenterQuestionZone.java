package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.services.ExamEditionService;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Class to manage conversions of the view's questions
 * delimitation of a question's zone for the model
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterQuestionZone {
  /**
   * Presenter for the creation view
   */
  private EditorPresenter presenter;
  
  private ExamEditionService service;
  
  public PresenterQuestionZone(final ExamEditionService s, final EditorPresenter p) {
    Objects.<ExamEditionService>requireNonNull(s);
    Objects.<EditorPresenter>requireNonNull(p);
    this.service = s;
    this.presenter = p;
  }
  
  /**
   * setter for the PresenterVueCreation attribute
   * @param {@link PresenterVueCreation} pres instance of the presenter (not null)
   */
  public EditorPresenter setPresenterVueCreation(final EditorPresenter pres) {
    EditorPresenter _xblockexpression = null;
    {
      Objects.<EditorPresenter>requireNonNull(pres);
      _xblockexpression = this.presenter = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterVueCreation}
   */
  public EditorPresenter getPresenterVueCreation() {
    return this.presenter;
  }
  
  public int createQuestion(final double x, final double y, final double height, final double width) {
    return this.service.createQuestion(((float) x), ((float) y), ((float) height), ((float) width));
  }
  
  public void removeQuestion(final int ID) {
    this.service.removeQuestion(ID);
  }
  
  public void renameQuestion(final int ID, final String name) {
    this.service.renameQuestion(ID, name);
  }
  
  public void resizeQuestion(final int ID, final double height, final double width) {
    this.service.rescaleQuestion(ID, ((float) height), ((float) height));
  }
  
  /**
   * changes the x and y coordinates of the {@link Question} identified by the id
   * @param int id : the unique ID of question
   * @param float x : new x position
   * @param float y : new y position
   * @author : Benjamin Danlos
   */
  public void moveQuestion(final int id, final double x, final double y) {
    this.service.moveQuestion(id, ((float) x), ((float) y));
  }
  
  /**
   * --LOADING NEW TEMPLATE--
   */
  public void initLoading() {
    LinkedList<Integer> _linkedList = new LinkedList<Integer>();
    this.pageNumbers = _linkedList;
    LinkedList<Question> _linkedList_1 = new LinkedList<Question>();
    this.questions = _linkedList_1;
    this.questionToLoadIndex = (-1);
  }
  
  private LinkedList<Integer> pageNumbers;
  
  private LinkedList<Question> questions;
  
  private int questionToLoadIndex;
  
  /**
   * Loads the next question into questionToLoad
   * if there is a new question, return true,
   * else return false
   */
  public boolean loadNextQuestion() {
    this.questionToLoadIndex++;
    int _size = this.questions.size();
    boolean _greaterEqualsThan = (this.questionToLoadIndex >= _size);
    if (_greaterEqualsThan) {
      return false;
    }
    return true;
  }
  
  public double currentQuestionX() {
    return this.questions.get(this.questionToLoadIndex).getZone().getX();
  }
  
  public double currentQuestionY() {
    return this.questions.get(this.questionToLoadIndex).getZone().getY();
  }
  
  public double currentQuestionHeight() {
    return this.questions.get(this.questionToLoadIndex).getZone().getHeigth();
  }
  
  public double currentQuestionWidth() {
    return this.questions.get(this.questionToLoadIndex).getZone().getWidth();
  }
  
  public String currentQuestionName() {
    return this.questions.get(this.questionToLoadIndex).getName();
  }
  
  public int currentQuestionId() {
    return this.questions.get(this.questionToLoadIndex).getId();
  }
  
  public int currentQuestionPage() {
    return (this.pageNumbers.get(this.questionToLoadIndex)).intValue();
  }
}
