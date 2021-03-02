package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.services.ExamEditionService;
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
  public void moveQuestion(final int id, final float x, final float y) {
    this.service.moveQuestion(id, x, y);
  }
}
