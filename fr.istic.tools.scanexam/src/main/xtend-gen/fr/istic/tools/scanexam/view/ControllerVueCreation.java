package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.presenter.PresenterVueCreation;
import fr.istic.tools.scanexam.view.ControllerFX;
import fr.istic.tools.scanexam.view.ControllerSwing;
import java.util.Objects;

/**
 * General controller for the Creation view
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class ControllerVueCreation {
  /**
   * Presenter to send the requests to to interact with the model
   */
  private PresenterVueCreation presenter;
  
  /**
   * Controller used by the JavaFX view
   */
  private ControllerFX controllerFX;
  
  /**
   * Controller used by the Swing view
   */
  private ControllerSwing controllerSwing;
  
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
}
