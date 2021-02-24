package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterVueCorrection;
import fr.istic.tools.scanexam.presenter.PresenterVueCreation;
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
  private PresenterVueCreation presenterCreation;
  
  /**
   * Presenter for the correction view
   */
  private PresenterVueCorrection presenterCorrection;
  
  /**
   * setter for the PresenterVueCreation attribute
   * @param {@link PresenterVueCreation} pres instance of the presenter (not null)
   */
  public PresenterVueCreation setPresenterVueCreation(final PresenterVueCreation pres) {
    PresenterVueCreation _xblockexpression = null;
    {
      Objects.<PresenterVueCreation>requireNonNull(pres);
      _xblockexpression = this.presenterCreation = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterVueCreation}
   */
  public PresenterVueCreation getPresenterVueCreation() {
    return this.presenterCreation;
  }
  
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
