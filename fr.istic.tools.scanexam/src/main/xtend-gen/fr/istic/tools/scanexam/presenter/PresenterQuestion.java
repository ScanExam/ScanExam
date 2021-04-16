package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterGraduation;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import java.util.Objects;

/**
 * CLass representing a presenter of question in
 * an exam to link the view and the model
 * @author Benjamin Danlos, Matthieu Pays
 */
@SuppressWarnings("all")
public class PresenterQuestion {
  /**
   * Association with the model via the Service API
   */
  private ServiceGraduation service;
  
  /**
   * Presenter for the correction view
   */
  private PresenterGraduation presenterCorrection;
  
  /**
   * Constructor
   * @param {@link ExamGraduationService} (not null)
   * Constructs a QuestionPresenter object.
   */
  public PresenterQuestion(final ServiceGraduation service) {
    Objects.<ServiceGraduation>requireNonNull(service);
    this.service = service;
  }
  
  /**
   * setter for the PresenterVueCorrection attribute
   * @param {@link PresenterVueCorrection} pres instance of the presenter (not null)
   */
  public PresenterGraduation setPresenterVueCorrection(final PresenterGraduation pres) {
    PresenterGraduation _xblockexpression = null;
    {
      Objects.<PresenterGraduation>requireNonNull(pres);
      _xblockexpression = this.presenterCorrection = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterVueCreation}
   */
  public PresenterGraduation getPresenterVueCorrection() {
    return this.presenterCorrection;
  }
  
  /**
   * Set the grade for the current question
   * @param grade to set up
   */
  public Object setGrade(final int grade) {
    return null;
  }
  
  /**
   * @return next question
   */
  public void nextQuestion() {
    this.service.nextQuestion();
  }
  
  /**
   * @param question is the actual question
   */
  public void previousQuestion() {
    this.service.previousQuestion();
  }
  
  public void selectQuestion(final int questionId) {
    this.service.selectQuestion(questionId);
  }
  
  public void nextStudent() {
    this.service.nextSheet();
  }
  
  public void previousStudent() {
    this.service.previousSheet();
  }
}
