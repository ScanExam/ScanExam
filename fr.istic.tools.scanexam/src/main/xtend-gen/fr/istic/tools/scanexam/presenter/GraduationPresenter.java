package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.presenter.PresenterCopy;
import fr.istic.tools.scanexam.presenter.PresenterMarkingScheme;
import fr.istic.tools.scanexam.presenter.PresenterPdf;
import fr.istic.tools.scanexam.presenter.PresenterQuestion;
import fr.istic.tools.scanexam.services.ExamGraduationService;
import fr.istic.tools.scanexam.view.Adapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

/**
 * Class defining the presenter for the exam correction view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class GraduationPresenter implements Presenter {
  /**
   * Bidirectional associations with the concrete presenters
   * and main controller of the view
   */
  private PresenterQuestion presQuestion;
  
  private PresenterCopy presCopy;
  
  private PresenterMarkingScheme presMarkingScheme;
  
  private GraduationPresenter graduationPresenter;
  
  private PresenterPdf presPdf;
  
  private ExamGraduationService service;
  
  private Adapter<GraduationPresenter> adapter;
  
  public GraduationPresenter(final Adapter<GraduationPresenter> adapter, final ExamGraduationService service) {
    Objects.<ExamGraduationService>requireNonNull(service);
    this.service = service;
    Objects.<Adapter<GraduationPresenter>>requireNonNull(adapter);
    this.adapter = adapter;
  }
  
  /**
   * setter for the PresenterQuestion attribute
   * @param {@link PresenterQuestion} pres instance of the presenter (not null)
   */
  public PresenterQuestion setPresenterQuestion(final PresenterQuestion pres) {
    PresenterQuestion _xblockexpression = null;
    {
      Objects.<PresenterQuestion>requireNonNull(pres);
      _xblockexpression = this.presQuestion = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterQuestion}
   */
  public PresenterQuestion getPresenterQuestion() {
    return this.presQuestion;
  }
  
  /**
   * Setter for {@link PresenterCopy} attribute
   * @param {@link PresenterCopy} pres an instance (not null)
   */
  public PresenterCopy setPresenterCopy(final PresenterCopy pres) {
    PresenterCopy _xblockexpression = null;
    {
      Objects.<PresenterCopy>requireNonNull(pres);
      _xblockexpression = this.presCopy = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterCopy}
   */
  public PresenterCopy getPresenterCopy() {
    return this.presCopy;
  }
  
  /**
   * Setter for {@link PresenterMarkingScheme} attribute
   * @param {@link PresenterMarkingScheme} pres an instance (not null)
   */
  public PresenterMarkingScheme setPresenterMarkingScheme(final PresenterMarkingScheme pres) {
    PresenterMarkingScheme _xblockexpression = null;
    {
      Objects.<PresenterMarkingScheme>requireNonNull(pres);
      _xblockexpression = this.presMarkingScheme = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterMarkingScheme}
   */
  public PresenterMarkingScheme getPresenterMarkingScheme() {
    return this.presMarkingScheme;
  }
  
  /**
   * Sets a {@link ControllerVueCorrection} the link with the view
   * @param {@link ControllerVueCorrection} contr an instance (not null)
   */
  public GraduationPresenter setControllerVueCorrection(final GraduationPresenter contr) {
    GraduationPresenter _xblockexpression = null;
    {
      Objects.<GraduationPresenter>requireNonNull(contr);
      _xblockexpression = this.graduationPresenter = contr;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link ControllerVueCorrection}
   */
  public GraduationPresenter getControllerVueCorrection() {
    return this.graduationPresenter;
  }
  
  /**
   * @return next question
   */
  public int getNextQuestion(final int question) {
    return this.presQuestion.getNextQuestion(question);
  }
  
  /**
   * @param question is the actual question
   * @return previous question
   */
  public int getPreviousQuestion(final int question) {
    return this.presQuestion.getPreviousQuestion(question);
  }
  
  public PresenterPdf setPresenterPdf(final PresenterPdf pres) {
    PresenterPdf _xblockexpression = null;
    {
      Objects.<PresenterPdf>requireNonNull(pres);
      _xblockexpression = this.presPdf = pres;
    }
    return _xblockexpression;
  }
  
  public PresenterPdf getPresenterPdf() {
    return this.presPdf;
  }
  
  @Override
  public BufferedImage getCurrentPdfPage() {
    return this.service.getCurrentPdfPage();
  }
  
  @Override
  public int getCurrentPdfPageNumber() {
    return this.service.getCurrentPageNumber();
  }
  
  @Override
  public void create(final File file) {
    this.service.create(file);
  }
}
