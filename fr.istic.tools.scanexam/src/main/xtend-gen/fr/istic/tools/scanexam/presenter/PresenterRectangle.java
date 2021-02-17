package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterVueCreation;
import java.util.Objects;

/**
 * Class to manage conversions of the view's questions
 * delimitation of a question's zone for the model
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterRectangle {
  /**
   * Presenter for the creation view
   */
  private PresenterVueCreation presenter;
  
  /**
   * setter for the PresenterVueCreation attribute
   * @param {@link PresenterVueCreation} pres instance of the presenter (not null)
   */
  public PresenterVueCreation setPresenterVueCreation(final PresenterVueCreation pres) {
    PresenterVueCreation _xblockexpression = null;
    {
      Objects.<PresenterVueCreation>requireNonNull(pres);
      _xblockexpression = this.presenter = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterVueCreation}
   */
  public PresenterVueCreation getPresenterVueCreation() {
    return this.presenter;
  }
}
