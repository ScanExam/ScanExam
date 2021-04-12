package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.presenter.PresenterEdition;
import fr.istic.tools.scanexam.view.AdapterEdition;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;

@SuppressWarnings("all")
public class AdapterFxEdition implements AdapterEdition {
  private PresenterEdition presenter;
  
  private ControllerFxEdition controller;
  
  @Override
  public void setPresenter(final PresenterEdition presenter) {
    this.presenter = presenter;
  }
  
  public void setControllerFXCreator(final ControllerFxEdition controller) {
    this.controller = controller;
  }
  
  @Override
  public PresenterEdition getPresenter() {
    return this.presenter;
  }
}
