package fr.istic.tools.scanexam.presenter;

import java.util.Objects;

/**
 * Class to manage the insertion of QRCodes on the exam during its creation
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterQRCode {
  /**
   * Presenter for the creation view
   */
  private PresenterEdition presenter;
  
  /**
   * setter for the PresenterVueCreation attribute
   * @param {@link PresenterVueCreation} pres instance of the presenter (not null)
   */
  public PresenterEdition setPresenterVueCreation(final PresenterEdition pres) {
    PresenterEdition _xblockexpression = null;
    {
      Objects.<PresenterEdition>requireNonNull(pres);
      _xblockexpression = this.presenter = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterVueCreation}
   */
  public PresenterEdition getPresenterVueCreation() {
    return this.presenter;
  }
}
