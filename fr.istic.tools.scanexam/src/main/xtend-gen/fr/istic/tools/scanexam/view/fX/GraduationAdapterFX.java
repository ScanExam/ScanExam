package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.presenter.GraduationPresenter;
import fr.istic.tools.scanexam.view.GraduationAdapter;
import java.io.File;
import java.util.List;

@SuppressWarnings("all")
public class GraduationAdapterFX implements GraduationAdapter {
  private GraduationPresenter presenter;
  
  public void loadFile(final File file) {
  }
  
  @Override
  public void setPresenter(final GraduationPresenter presenter) {
    this.presenter = presenter;
  }
  
  @Override
  public void nextQuestion() {
  }
  
  @Override
  public void previousQuestion() {
  }
  
  @Override
  public List<String> questionNames() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public void thisQuestion(final int index) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
