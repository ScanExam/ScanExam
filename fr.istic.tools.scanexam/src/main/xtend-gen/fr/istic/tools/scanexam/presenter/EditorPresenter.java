package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.presenter.PresenterMarkingScheme;
import fr.istic.tools.scanexam.presenter.PresenterPdf;
import fr.istic.tools.scanexam.presenter.PresenterQRCode;
import fr.istic.tools.scanexam.presenter.PresenterQuestionZone;
import fr.istic.tools.scanexam.services.ExamEditionService;
import fr.istic.tools.scanexam.view.Adapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * Class defining the presenter for the exam creation view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class EditorPresenter implements Presenter {
  /**
   * Bidirectional associations with the concrete presenters
   * and main controller of the view
   */
  private PresenterQRCode presQRCode;
  
  private PresenterQuestionZone presQuestionZone;
  
  private PresenterMarkingScheme presMarkingScheme;
  
  private EditorPresenter editorPresenter;
  
  private ExamEditionService service;
  
  private PresenterPdf presPdf;
  
  private Adapter<EditorPresenter> adapter;
  
  public EditorPresenter(final Adapter<EditorPresenter> adapter, final ExamEditionService service) {
    Objects.<ExamEditionService>requireNonNull(service);
    this.service = service;
    Objects.<Adapter<EditorPresenter>>requireNonNull(adapter);
    this.adapter = adapter;
    PresenterPdf _presenterPdf = new PresenterPdf(service, this);
    this.presPdf = _presenterPdf;
  }
  
  /**
   * setter for the PresenterQRCode attribute
   * @param {@link PresenterQRCode} pres instance of the presenter (not null)
   */
  public PresenterQRCode setPresenterQRCode(final PresenterQRCode pres) {
    PresenterQRCode _xblockexpression = null;
    {
      Objects.<PresenterQRCode>requireNonNull(pres);
      _xblockexpression = this.presQRCode = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterQRCode}
   */
  public PresenterQRCode getPresenterQRCode() {
    return this.presQRCode;
  }
  
  /**
   * Setter for {@link PresenterQuestionZone} attribute
   * @param {@link PresenterQuestionZone} pres an instance (not null)
   */
  public PresenterQuestionZone setPresenterQuestionZone(final PresenterQuestionZone pres) {
    PresenterQuestionZone _xblockexpression = null;
    {
      Objects.<PresenterQuestionZone>requireNonNull(pres);
      _xblockexpression = this.presQuestionZone = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterQuestionZone}
   */
  public PresenterQuestionZone getPresenterQuestionZone() {
    return this.presQuestionZone;
  }
  
  /**
   * Setter for {@link PresenterMarkingScheme} attribute
   * @param {@link PresenterMarkingScheme} pres an instance (not null)
   */
  public PresenterMarkingScheme setPresenterMarkingScheme(final PresenterMarkingScheme pres) {
    PresenterMarkingScheme _xblockexpression = null;
    {
      Objects.<PresenterMarkingScheme>requireNonNull(pres);
      _xblockexpression = this.presMarkingScheme = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterMarkingScheme}
   */
  public PresenterMarkingScheme getPresenterMarkingScheme() {
    return this.presMarkingScheme;
  }
  
  /**
   * Sets a {@link ControllerVueCreation} the link with the view
   * @param {@link ControllerVueCreation} contr an instance (not null)
   */
  public EditorPresenter setControllerVueCreation(final EditorPresenter contr) {
    EditorPresenter _xblockexpression = null;
    {
      Objects.<EditorPresenter>requireNonNull(contr);
      _xblockexpression = this.editorPresenter = contr;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link ControllerVueCreation}
   */
  public EditorPresenter getControllerVueCreation() {
    return this.editorPresenter;
  }
  
  @Override
  public BufferedImage getCurrentPdfPage() {
    return this.service.getCurrentPdfPage();
  }
  
  public void choosePdfPage(final int pageNumber) {
  }
  
  public void nextPdfPage() {
    this.service.nextPage();
  }
  
  public void previousPdfPage() {
    this.service.previousPage();
  }
  
  public int getTotalPdfPageNumber() {
    return this.service.getPageNumber();
  }
  
  public int getCurrentPdfPageNumber() {
    return this.service.getCurrentPageNumber();
  }
  
  @Override
  public void create(final File file) {
    this.service.create(file);
  }
  
  public PDDocument getDocument() {
    return this.service.getDocument();
  }
}
