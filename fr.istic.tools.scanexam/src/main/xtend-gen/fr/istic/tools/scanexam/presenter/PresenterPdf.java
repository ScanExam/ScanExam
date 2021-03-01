package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.services.ExamEditionService;
import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import javafx.scene.image.WritableImage;

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
  private ExamEditionService service;
  
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
  public PresenterPdf(final ExamEditionService s, final Presenter p) {
    Objects.<ExamEditionService>requireNonNull(s);
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
  
  /**
   * Constructeur
   * @param width Largeur de la fenêtre
   * @param height Hauteur de la fenêtre
   * @param pdfPath Chemin vers le pdf
   */
  public PresenterPdf(final int width, final int height, final InputStream pdfInput) {
    this.width = width;
    this.height = height;
    this.pdfInput = pdfInput;
  }
  
  /**
   * tells the api to change the working pdf in the model
   * @param {@link File} path to the pdf file to be loaded
   * @author Benjamin Danlos
   */
  public void loadPDF(final File file) {
    Objects.<File>requireNonNull(file);
  }
  
  /**
   * get the page number i from the pdf loaded in the model. Use getContents() on the return to get an InputStream if any
   * @param i the page number
   * @throws IllegalArgumentExcpetion if i<0 or document has less pages than i
   * @return {@link PDPage} page of the document
   * @author Benjamin Danlos
   */
  public Object getPage(final int i) {
    return null;
  }
  
  /**
   * returns all the pages in the pdf loaded in the document
   * @return {@link PDPageTree} the pages of the document
   * @author Benjamin Danlos
   */
  public Object getPages() {
    return null;
  }
  
  public WritableImage getCurrentPdfPage() {
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
  
  public void create(final File file) {
    this.service.create(file);
  }
}
