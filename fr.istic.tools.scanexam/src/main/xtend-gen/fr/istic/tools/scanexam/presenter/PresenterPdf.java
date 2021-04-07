package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.services.ExamEditionService;
import fr.istic.tools.scanexam.services.Service;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * Controlleur du pdf
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class PresenterPdf {
  /**
   * presenter used for the edition of an exam
   * @author Benjamin Danlos
   */
  private Presenter presenter;
  
  /**
   * Association with the model via the Service API
   */
  private Service service;
  
  /**
   * Largeur de la fenêtre
   */
  protected int width;
  
  /**
   * Hauteur de la fenêtre
   */
  protected int height;
  
  /**
   * InputStream du pdf
   */
  protected InputStream pdfInput;
  
  /**
   * Constructor
   * @author Benjamin Danlos
   * @param {@link EditorPresenter} (not null)
   * @param {@link GraduationPresenter} (not null)
   * Constructs a PDFPresenter object.
   */
  public PresenterPdf(final Service s, final Presenter p) {
    Objects.<Service>requireNonNull(s);
    Objects.<Presenter>requireNonNull(p);
    this.service = s;
    this.presenter = p;
  }
  
  /**
   * set {@link Presenter} for the association
   * @param {@link Presenter} presenter to make the association with
   * @author Benjamin Danlos
   */
  public Presenter setPresenter(final Presenter p) {
    Presenter _xblockexpression = null;
    {
      Objects.<Presenter>requireNonNull(p);
      _xblockexpression = this.presenter = p;
    }
    return _xblockexpression;
  }
  
  /**
   * returns the current presenter associated
   * @return {@link Presenter}
   * @author Benjamin Danlos
   */
  public Presenter getPresenter() {
    return this.presenter;
  }
  
  public BufferedImage getCurrentPdfPage() {
    InputOutput.<Integer>print(Integer.valueOf(this.service.currentPdfPageNumber()));
    return this.service.getCurrentPdfPage();
  }
  
  public void nextPdfPage() {
    this.service.nextPdfPage();
  }
  
  public void previousPdfPage() {
    this.service.previousPdfPage();
  }
  
  public void goToPdfPage(final int page) {
    this.service.goToPdfPage(page);
  }
  
  public int totalPdfPageNumber() {
    return this.service.getPdfsize();
  }
  
  public int currentPdfPageNumber() {
    return this.service.getCurrentPageNumber();
  }
  
  public Object choosePdfPage(final int i) {
    return null;
  }
  
  public PDDocument getDocument() {
    return this.service.getDocument();
  }
  
  public void create(final File file) {
    Objects.<File>requireNonNull(file);
    ((ExamEditionService) this.service).create(file);
  }
  
  public boolean atCorrectPage(final int page) {
    int _currentPdfPageNumber = this.service.currentPdfPageNumber();
    return (page == _currentPdfPageNumber);
  }
}
