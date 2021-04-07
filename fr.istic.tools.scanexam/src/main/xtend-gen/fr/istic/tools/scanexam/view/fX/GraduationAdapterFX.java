package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.presenter.GraduationPresenter;
import fr.istic.tools.scanexam.view.GraduationAdapter;
import fr.istic.tools.scanexam.view.fX.corrector.ControllerFXCorrector;
import java.io.File;
import java.util.List;

@SuppressWarnings("all")
public class GraduationAdapterFX implements GraduationAdapter {
  private GraduationPresenter presenter;
  
  private ControllerFXCorrector corrector;
  
  public void setController(final ControllerFXCorrector controller) {
    this.corrector = controller;
  }
  
  public void loadFile(final File file) {
  }
  
  @Override
  public void nextQuestion() {
  }
  
  @Override
  public void previousQuestion() {
  }
  
  @Override
  public List<String> questionNames() {
    return null;
  }
  
  @Override
  public void thisQuestion(final int index) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public void showQuestion(final Question question) {
  }
  
  @Override
  public void setPresenter(final GraduationPresenter presenter) {
    this.presenter = presenter;
  }
  
  @Override
  public GraduationPresenter getPresenter() {
    return this.presenter;
  }
}
