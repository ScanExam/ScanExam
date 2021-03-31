package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.services.ExamGraduationService;
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
  private ExamGraduationService service;
  
  /**
   * Presenter for the correction view
   */
  private GraduationPresenter presenterCorrection;
  
  /**
   * Constructor
   * @param {@link ExamGraduationService} (not null)
   * Constructs a QuestionPresenter object.
   */
  public PresenterQuestion(final ExamGraduationService service) {
    Objects.<ExamGraduationService>requireNonNull(service);
    this.service = service;
  }
  
  /**
   * setter for the PresenterVueCorrection attribute
   * @param {@link PresenterVueCorrection} pres instance of the presenter (not null)
   */
  public GraduationPresenter setPresenterVueCorrection(final GraduationPresenter pres) {
    GraduationPresenter _xblockexpression = null;
    {
      Objects.<GraduationPresenter>requireNonNull(pres);
      _xblockexpression = this.presenterCorrection = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterVueCreation}
   */
  public GraduationPresenter getPresenterVueCorrection() {
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
  public int nextQuestion() {
    return this.service.nextQuestion();
  }
  
  /**
   * @param question is the actual question
   */
  public int previousQuestion() {
    return this.service.previousQuestion();
  }
  
  public int nextStudent() {
    return this.service.nextStudent();
  }
  
  public int previousStudent() {
    return this.service.previousStudent();
  }
}
