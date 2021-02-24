package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.view.EditorAdapter;

@SuppressWarnings("all")
public class EditorAdapterFX implements EditorAdapter {
  private EditorPresenter presenter;
  
  @Override
  public void setPresenter(final EditorPresenter presenter) {
    this.presenter = presenter;
  }
}
