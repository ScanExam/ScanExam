package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.presenter.PresenterVueCorrection;
import fr.istic.tools.scanexam.view.ControllerFX;
import fr.istic.tools.scanexam.view.ControllerSwing;
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
   * Controller used by the JavaFX view
   */
  private ControllerFX controllerFX;
  
  /**
   * Controller used by the Swing view
   */
  private ControllerSwing controllerSwing;
  
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
   * setter for the ControllerFX attribute
   * @param {@link ControllerFX} pres instance of the Java FX Controller (not null)
   */
  public ControllerFX setControllerFX(final ControllerFX contr) {
    ControllerFX _xblockexpression = null;
    {
      Objects.<ControllerFX>requireNonNull(contr);
      _xblockexpression = this.controllerFX = contr;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current Java FX controller {@link ControllerFX}
   */
  public ControllerFX getControllerFX() {
    return this.controllerFX;
  }
  
  /**
   * setter for the ControllerSwing attribute
   * @param {@link ControllerSwing} pres instance of the Swing Controller (not null)
   */
  public ControllerSwing setControllerSwing(final ControllerSwing contr) {
    ControllerSwing _xblockexpression = null;
    {
      Objects.<ControllerSwing>requireNonNull(contr);
      _xblockexpression = this.controllerSwing = contr;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current Swing controller {@link ControllerSwing}
   */
  public ControllerSwing getControllerSwing() {
    return this.controllerSwing;
  }
  
  /**
   * @return next question
   */
  public int getNextQuestion(final int question) {
    return this.presenter.getNextQuestion(question);
  }
  
  /**
   * @param question is the actual question
   * @return previous question
   */
  public int getPreviousQuestion(final int question) {
    return this.presenter.getPreviousQuestion(question);
  }
}
