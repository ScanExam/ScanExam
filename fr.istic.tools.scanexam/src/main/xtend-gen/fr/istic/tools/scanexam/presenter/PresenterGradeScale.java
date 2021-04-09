package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.services.ServiceEdition;
import java.util.Objects;

/**
 * Class to manage an exam marking scheme (french : barème)
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterGradeScale {
  /**
   * Presenter for the creation view
   */
  private Presenter presenter;
  
  private ServiceEdition service;
  
  /**
   * Constructor
   * Constructs a Presenter manipulating the grading schele with the modele
   * @param {@link ExamEditionService} s : the Service API linked with the model
   * @param {@link Presenter} p : a Presenter used by the view
   * @author Benjamin Danlos
   */
  public PresenterGradeScale(final ServiceEdition s, final Presenter p) {
    Objects.<ServiceEdition>requireNonNull(s);
    Objects.<Presenter>requireNonNull(p);
    this.presenter = p;
    this.service = s;
  }
  
  /**
   * setter for the Presenter attribute
   * @param {@link Presenter} p : instance of the presenter (not null)
   */
  public Presenter setPresenterVueCreation(final Presenter pres) {
    Presenter _xblockexpression = null;
    {
      Objects.<Presenter>requireNonNull(pres);
      _xblockexpression = this.presenter = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link Presenter}
   */
  public Presenter getPresenter() {
    return this.presenter;
  }
}
