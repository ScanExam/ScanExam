package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.presenter.PresenterVueCreation;
import fr.istic.tools.scanexam.view.EditorAdapter;

@SuppressWarnings("all")
public class EditorAdapterFX implements EditorAdapter {
  private PresenterVueCreation presenter;
  
  @Override
  public void setPresenter(final Presenter presenter) {
    this.presenter = ((PresenterVueCreation) presenter);
  }
}
