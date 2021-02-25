package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.presenter.GraduationPresenter;
import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;

/**
 * Controlleur du pdf
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public abstract class PresenterPdf {
  /**
   * presenter used for the edition of an exam
   * @author Benjamin Danlos
   */
  private EditorPresenter editorPresenter;
  
  /**
   * presenter used for the graduation of student sheets of an exam
   * @author Benjamin Danlos
   */
  private GraduationPresenter graduationPresenter;
  
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
  public PresenterPdf(final EditorPresenter presE, final GraduationPresenter presG) {
    Objects.<EditorPresenter>requireNonNull(presE);
    Objects.<GraduationPresenter>requireNonNull(presG);
    this.editorPresenter = presE;
    this.graduationPresenter = presG;
  }
  
  /**
   * set {@link EditorPresenter} for the association
   * @param {@link EditorPresenter} presenter to make the association with
   * @author Benjamin Danlos
   */
  public EditorPresenter setEditorPresenter(final EditorPresenter p) {
    EditorPresenter _xblockexpression = null;
    {
      Objects.<EditorPresenter>requireNonNull(p);
      _xblockexpression = this.editorPresenter = p;
    }
    return _xblockexpression;
  }
  
  /**
   * set {@link GraduationPresenter} for the association
   * @param {@link GraduationPresenter} presenter to make the association with
   * @author Benjamin Danlos
   */
  public GraduationPresenter setGraduationPresenter(final GraduationPresenter p) {
    GraduationPresenter _xblockexpression = null;
    {
      Objects.<GraduationPresenter>requireNonNull(p);
      _xblockexpression = this.graduationPresenter = p;
    }
    return _xblockexpression;
  }
  
  /**
   * returns the current editor presenter associated
   * @return {@link EditorPresenter}
   * @author Benjamin Danlos
   */
  public EditorPresenter getEditorPresenter() {
    return this.editorPresenter;
  }
  
  /**
   * returns the current graduation presenter associated
   * @return {@link GraduationPresenter}
   * @author Benjamin Danlos
   */
  public GraduationPresenter getGraduationPresenter() {
    return this.graduationPresenter;
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
    this.editorPresenter.getSessionAPI().create(file);
  }
  
  /**
   * get the page number i from the pdf loaded in the model. Use getContents() on the return to get an InputStream if any
   * @param i the page number
   * @throws IllegalArgumentExcpetion if i<0 or document has less pages than i
   * @return {@link PDPage} page of the document
   * @author Benjamin Danlos
   */
  public PDPage getPage(final int i) {
    PDPage _xblockexpression = null;
    {
      PDDocument pdf = this.editorPresenter.getSessionAPI().getDocument();
      if ((i < 0)) {
        throw new IllegalArgumentException((("i can\'t be negative(" + Integer.valueOf(i)) + ")"));
      }
      int _numberOfPages = pdf.getNumberOfPages();
      boolean _lessThan = (_numberOfPages < i);
      if (_lessThan) {
        throw new IllegalArgumentException((("i can\'t be greater than the number of pages in the document (" + Integer.valueOf(i)) + ")"));
      }
      _xblockexpression = pdf.getPage(i);
    }
    return _xblockexpression;
  }
  
  /**
   * returns all the pages in the pdf loaded in the document
   * @return {@link PDPageTree} the pages of the document
   * @author Benjamin Danlos
   */
  public PDPageTree getPages() {
    return this.editorPresenter.getSessionAPI().getDocument().getPages();
  }
}
