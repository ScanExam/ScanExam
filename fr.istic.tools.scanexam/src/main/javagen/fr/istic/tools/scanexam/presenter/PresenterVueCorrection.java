package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterCopy;
import fr.istic.tools.scanexam.presenter.PresenterMarkingScheme;
import fr.istic.tools.scanexam.presenter.PresenterQuestion;
import fr.istic.tools.scanexam.view.ControllerVueCorrection;
import java.util.Objects;

/**
 * Class defining the presenter for the exam correction view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterVueCorrection {
  /**
   * Bidirectional associations with the concrete presenters
   * and main controller of the view
   */
  private PresenterQuestion presQuestion;
  
  private PresenterCopy presCopy;
  
  private PresenterMarkingScheme presMarkingScheme;
  
  private ControllerVueCorrection controller;
  
  /**
   * setter for the PresenterQuestion attribute
   * @param {@link PresenterQuestion} pres instance of the presenter (not null)
   */
  public PresenterQuestion setPresenterQuestion(final PresenterQuestion pres) {
    PresenterQuestion _xblockexpression = null;
    {
      Objects.<PresenterQuestion>requireNonNull(pres);
      _xblockexpression = this.presQuestion = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterQuestion}
   */
  public PresenterQuestion getPresenterQuestion() {
    return this.presQuestion;
  }
  
  /**
   * Setter for {@link PresenterCopy} attribute
   * @param {@link PresenterCopy} pres an instance (not null)
   */
  public PresenterCopy setPresenterCopy(final PresenterCopy pres) {
    PresenterCopy _xblockexpression = null;
    {
      Objects.<PresenterCopy>requireNonNull(pres);
      _xblockexpression = this.presCopy = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterCopy}
   */
  public PresenterCopy getPresenterCopy() {
    return this.presCopy;
  }
  
  /**
   * Setter for {@link PresenterMarkingScheme} attribute
   * @param {@link PresenterMarkingScheme} pres an instance (not null)
   */
  public PresenterMarkingScheme setPresenterMarkingScheme(final PresenterMarkingScheme pres) {
    PresenterMarkingScheme _xblockexpression = null;
    {
      Objects.<PresenterMarkingScheme>requireNonNull(pres);
      _xblockexpression = this.presMarkingScheme = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterMarkingScheme}
   */
  public PresenterMarkingScheme getPresenterMarkingScheme() {
    return this.presMarkingScheme;
  }
  
  /**
   * Sets a {@link ControllerVueCorrection} the link with the view
   * @param {@link ControllerVueCorrection} contr an instance (not null)
   */
  public ControllerVueCorrection setControllerVueCorrection(final ControllerVueCorrection contr) {
    ControllerVueCorrection _xblockexpression = null;
    {
      Objects.<ControllerVueCorrection>requireNonNull(contr);
      _xblockexpression = this.controller = contr;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link ControllerVueCorrection}
   */
  public ControllerVueCorrection getControllerVueCorrection() {
    return this.controller;
  }
}
