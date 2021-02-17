package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterVueCorrection;
import java.util.Objects;

/**
 * CLass representing a presenter of an exam copy or subject in
 * an exam to link the view and the model
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterCopy {
  /**
   * Presenter for the correction view
   */
  private PresenterVueCorrection presenterCorrection;
  
  /**
   * setter for the PresenterVueCorrection attribute
   * @param {@link PresenterVueCorrection} pres instance of the presenter (not null)
   */
  public PresenterVueCorrection setPresenterVueCorrection(final PresenterVueCorrection pres) {
    PresenterVueCorrection _xblockexpression = null;
    {
      Objects.<PresenterVueCorrection>requireNonNull(pres);
      _xblockexpression = this.presenterCorrection = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterVueCreation}
   */
  public PresenterVueCorrection getPresenterVueCorrection() {
    return this.presenterCorrection;
  }
}
