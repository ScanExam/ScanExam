package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.view.ControllerVueCorrection;
import fr.istic.tools.scanexam.view.ControllerVueCreation;
import java.util.Objects;

/**
 * Class representing a controller to be used with the Swing view.
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class ControllerSwing {
  /**
   * High level Controllers to access the Presenters
   */
  private ControllerVueCreation controllerCreation;
  
  private ControllerVueCorrection controllerCorrection;
  
  /**
   * setter for the ControllerVueCreation attribute
   * @param {@link ControllerVueCreation} controller instance of ControllerVueCreation (not null)
   */
  public ControllerVueCreation setControllerVueCreation(final ControllerVueCreation controller) {
    ControllerVueCreation _xblockexpression = null;
    {
      Objects.<ControllerVueCreation>requireNonNull(controller);
      _xblockexpression = this.controllerCreation = controller;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link ControllerVueCreation}
   */
  public ControllerVueCreation getControllerVueCreation() {
    return this.controllerCreation;
  }
  
  /**
   * setter for the ControllerVueCorrection attribute
   * @param {@link ControllerVueCorrection} controller instance of ControllerVueCorrection (not null)
   */
  public ControllerVueCorrection setControllerVueCorrection(final ControllerVueCorrection controller) {
    ControllerVueCorrection _xblockexpression = null;
    {
      Objects.<ControllerVueCorrection>requireNonNull(controller);
      _xblockexpression = this.controllerCorrection = controller;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link ControllerVueCorrection}
   */
  public ControllerVueCorrection getControllerVueCorrection() {
    return this.controllerCorrection;
  }
}
