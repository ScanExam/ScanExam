package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.view.EditorAdapter;
import fr.istic.tools.scanexam.view.fX.Box;
import fr.istic.tools.scanexam.view.fX.ControllerFXCreator;
import java.io.File;
import java.io.InputStream;
import javafx.scene.image.Image;

@SuppressWarnings("all")
public class EditorAdapterFX implements EditorAdapter {
  private EditorPresenter presenter;
  
  private ControllerFXCreator controller;
  
  @Override
  public void setPresenter(final EditorPresenter presenter) {
    this.presenter = presenter;
  }
  
  public void loadPdf(final File file) {
    this.presenter.create(file);
  }
  
  public Image pdfPage(final int index) {
    InputStream stream = this.presenter.getCurrentPdfPage();
    return new Image(stream);
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
