package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.view.EditorAdapter;
import fr.istic.tools.scanexam.view.fX.Box;
import fr.istic.tools.scanexam.view.fX.ControllerFXCreator;

@SuppressWarnings("all")
public class EditorAdapterFX implements EditorAdapter {
  private EditorPresenter presenter;
  
  private ControllerFXCreator controller;
  
  @Override
  public void setPresenter(final EditorPresenter presenter) {
    this.presenter = presenter;
  }
  
  public void addBox(final Box box) {
  }
  
  public void removeBox(final Box box) {
  }
  
  public void updateBox(final Box box) {
  }
  
  public void setControllerFXCreator(final ControllerFXCreator controller) {
    this.controller = controller;
  }
  
  @Override
  public EditorPresenter getPresenter() {
    return this.presenter;
  }
}
