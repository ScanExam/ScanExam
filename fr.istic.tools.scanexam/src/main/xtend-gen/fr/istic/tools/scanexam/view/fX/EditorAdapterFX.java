package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.view.EditorAdapter;
import fr.istic.tools.scanexam.view.fX.Box;
import fr.istic.tools.scanexam.view.fX.ControllerFXEditor;

@SuppressWarnings("all")
public class EditorAdapterFX implements EditorAdapter {
  private EditorPresenter presenter;
  
  private ControllerFXEditor controller;
  
  @Override
  public void setPresenter(final EditorPresenter presenter) {
    this.presenter = presenter;
  }
  
  public void addBox(final Box box) {
    box.setBoxId(this.presenter.getPresenterQuestionZone().createQuestion(box.getX(), box.getY(), box.getHeight(), box.getWidth()));
  }
  
  public void removeBox(final Box box) {
    this.presenter.getPresenterQuestionZone().removeQuestion(box.getBoxId());
  }
  
  public void updateBox(final Box box) {
  }
  
  public void setControllerFXCreator(final ControllerFXEditor controller) {
    this.controller = controller;
  }
  
  @Override
  public EditorPresenter getPresenter() {
    return this.presenter;
  }
}
