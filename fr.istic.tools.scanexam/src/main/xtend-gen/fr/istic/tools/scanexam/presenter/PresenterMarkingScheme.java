package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.presenter.GraduationPresenter;
import java.util.Objects;

/**
 * Class to manage an exam marking scheme (french : barème)
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterMarkingScheme {
  /**
   * Presenter for the creation view
   */
  private EditorPresenter presenterCreation;
  
  /**
   * Presenter for the correction view
   */
  private GraduationPresenter presenterCorrection;
  
  /**
   * setter for the PresenterVueCreation attribute
   * @param {@link PresenterVueCreation} pres instance of the presenter (not null)
   */
  public EditorPresenter setPresenterVueCreation(final EditorPresenter pres) {
    EditorPresenter _xblockexpression = null;
    {
      Objects.<EditorPresenter>requireNonNull(pres);
      _xblockexpression = this.presenterCreation = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterVueCreation}
   */
  public EditorPresenter getPresenterVueCreation() {
    return this.presenterCreation;
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
}
