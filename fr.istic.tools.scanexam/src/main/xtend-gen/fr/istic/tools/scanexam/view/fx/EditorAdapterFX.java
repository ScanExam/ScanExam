package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.presenter.PresenterEdition;
import fr.istic.tools.scanexam.view.AdapterEdition;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFXEditor;

@SuppressWarnings("all")
public class EditorAdapterFX implements AdapterEdition {
  private PresenterEdition presenter;
  
  private ControllerFXEditor controller;
  
  @Override
  public void setPresenter(final PresenterEdition presenter) {
    this.presenter = presenter;
  }
  
  public void setControllerFXCreator(final ControllerFXEditor controller) {
    this.controller = controller;
  }
  
  @Override
  public PresenterEdition getPresenter() {
    return this.presenter;
  }
}
