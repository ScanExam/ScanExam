package fr.istic.tools.scanexam.presenter;

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
  private PresenterGraduation presenterCorrection;
  
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
}
