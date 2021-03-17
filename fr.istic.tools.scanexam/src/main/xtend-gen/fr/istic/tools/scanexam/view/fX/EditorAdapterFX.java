package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.view.EditorAdapter;
import fr.istic.tools.scanexam.view.fX.editor.ControllerFXEditor;

@SuppressWarnings("all")
public class EditorAdapterFX implements EditorAdapter {
  private EditorPresenter presenter;
  
  private ControllerFXEditor controller;
  
  public void setPresenter(final EditorPresenter presenter) {
    this.presenter = presenter;
  }
  
  public void setControllerFXCreator(final ControllerFXEditor controller) {
    this.controller = controller;
  }
  
  public EditorPresenter getPresenter() {
    return this.presenter;
  }
}
