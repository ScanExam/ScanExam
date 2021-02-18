package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.presenter.PresenterVueCorrection;
import fr.istic.tools.scanexam.view.ControllerI;
import java.util.LinkedList;
import java.util.Objects;

/**
 * General controller for the Correction view
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class ControllerVueCorrection {
  /**
   * Presenter to send the requests to to interact with the model
   */
  private PresenterVueCorrection presenter;
  
  /**
   * Controller used
   */
  private ControllerI controller;
  
  /**
   * setter for the PresenterVueCorrection attribute
   * @param {@link PresenterVueCorrection} pres instance of the presenter (not null)
   */
  public PresenterVueCorrection setPresenterVueCorrection(final PresenterVueCorrection pres) {
    PresenterVueCorrection _xblockexpression = null;
    {
      Objects.<PresenterVueCorrection>requireNonNull(pres);
      _xblockexpression = this.presenter = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterVueCorrection}
   */
  public PresenterVueCorrection getPresenterVueCorrection() {
    return this.presenter;
  }
  
  /**
   * setter for the Controller attribute
   * @param {@link ControllerFX} pres instance of the Java FX Controller (not null)
   */
  public ControllerI setController(final ControllerI contr) {
    ControllerI _xblockexpression = null;
    {
      Objects.<ControllerI>requireNonNull(contr);
      _xblockexpression = this.controller = contr;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current controller {@link ControllerFX}
   */
  public ControllerI getController() {
    return this.controller;
  }
  
  public LinkedList<String> questionNames() {
    return null;
  }
  
  /**
   * @return next question
   */
  public void nextQuestion() {
  }
  
  /**
   * @param question is the actual question
   * @return previous question
   */
  public void previousQuestion() {
  }
  
  public void thisQuestion(final int index) {
  }
}
